package controller;

import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import main.GUI;
import model.DiceModel;
import model.FieldModel;
import model.WindowPatternModel;
import view.DiceScreen;
import view.FieldScreen;
import view.WindowPatternScreen;

import java.util.ArrayList;
import java.util.Random;

public class WindowController {

    private WindowPatternScreen window1;
    private WindowPatternScreen window2;
    private WindowPatternScreen window3;
    private WindowPatternScreen window4;
    private WindowPatternScreen window5;

    private WindowPatternModel windowPattern1Model;
    private WindowPatternModel windowPattern2Model;
    private WindowPatternModel windowPattern3Model;
    private WindowPatternModel windowPattern4Model;
    private WindowPatternModel windowModel;

    private GameController GC;
    private DiceController DC;
    private RoundScreenController RC;

    private GUI gui;

    private ArrayList<Color> colorsField = new ArrayList<>();
    private ArrayList<Integer> numbers = new ArrayList<>();
    private Random r = new Random();

    private boolean cheatAllPossible = false;
    private boolean cheatBestChoice = false;

    private final DataFormat diceFormat = new DataFormat("MyDice");

    private DiceScreen draggingDice;

    private boolean diceCanBeMoved = false;
    private boolean ignoreEyes = false;
    private boolean ignoreColor = false;
    private boolean ignoreNextToDice = false;

    private boolean extraTurn = false;

    private boolean canOnlyMoveDiceWithSameColorAsDIceOnRoundTrack = false;

    private int dicesChangedByTC = 0;

    private boolean TwoCanbeMoved;

    public WindowController(GUI gui, DatabaseController databaseController) {
        this.gui = gui;

        windowPattern1Model = new WindowPatternModel(databaseController.getWindowPatternQueries());
        windowPattern2Model = new WindowPatternModel(databaseController.getWindowPatternQueries());
        windowPattern3Model = new WindowPatternModel(databaseController.getWindowPatternQueries());
        windowPattern4Model = new WindowPatternModel(databaseController.getWindowPatternQueries());
        windowModel = new WindowPatternModel(databaseController.getWindowPatternQueries());

        window1 = new WindowPatternScreen("kaart 1", windowPattern1Model, "WHITE");
        window2 = new WindowPatternScreen("kaart 2", windowPattern2Model, "WHITE");
        window3 = new WindowPatternScreen("kaart 3", windowPattern3Model, "WHITE");
        window4 = new WindowPatternScreen("kaart 4", windowPattern4Model, "WHITE");
        window5 = new WindowPatternScreen("GEBRUIKT VOOR RANDOM WINDOW", windowModel, "WHITE");

        addColorsField();
        addNumbersField();

        createGrayWindowPattern(window1, windowPattern1Model);
        createGrayWindowPattern(window2, windowPattern2Model);
        createGrayWindowPattern(window3, windowPattern3Model);
        createGrayWindowPattern(window4, windowPattern4Model);
        createGrayWindowPattern(window5, windowModel);

    }

    /**
     * add all the possible colors
     */
    private void addColorsField() {
        colorsField.removeAll(colorsField);
        colorsField.add(Color.CORNFLOWERBLUE);
        colorsField.add(Color.YELLOW);
        colorsField.add(Color.RED);
        colorsField.add(Color.MAGENTA);
        colorsField.add(Color.LIGHTGREEN);
        colorsField.add(Color.LIGHTGRAY);
        colorsField.add(Color.LIGHTGRAY);
        colorsField.add(Color.LIGHTGRAY);
        colorsField.add(Color.LIGHTGRAY);
        colorsField.add(Color.LIGHTGRAY);
        colorsField.add(Color.LIGHTGRAY);
        colorsField.add(Color.LIGHTGRAY);
        colorsField.add(Color.LIGHTGRAY);

    }

    /**
     * add all the possible numbers
     */
    private void addNumbersField() {
        numbers.removeAll(numbers);
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
        numbers.add(0);
    }

    /**
     * Functionality for toolcard 4
     */
    void buyTC4() {
        diceCanBeMoved = true;
        TwoCanbeMoved = true;
        dicesChangedByTC = 0;
    }


    boolean isDraggingDiceFull() {
        return draggingDice != null;
    }

    void setDraggingDiceNull() {
        draggingDice = null;
    }


    /**
     * Functionality for toolcard 3
     */
    void buyTC3() {
        diceCanBeMoved = true;
        ignoreEyes = true;
    }

    /**
     * Functionality for toolcard 2
     */
    void buyTC2() {
        diceCanBeMoved = true;
        ignoreColor = true;
    }

    /**
     * Functionality for toolcard 9
     */
    void buyTC9() {
        ignoreNextToDice = true;
        diceCanBeMoved = true;
    }

    /**
     * Functionality for toolcard 12
     */
    void buyTC12() {
        diceCanBeMoved = true;
        TwoCanbeMoved = true;
        setCanOnlyMoveDiceWithSameColorAsDIceOnRoundTrackTrue();
    }

    /**
     * resets all the booleans where a dice does not have to go through all the requirments
     * used for toolcards
     *
     * @param TwoCanBeMoved = set true if two die can be moved in one turn
     */
    public void changedDiceBoard(boolean TwoCanBeMoved) {
        dicesChangedByTC++;

        if (dicesChangedByTC > 0 && !TwoCanBeMoved || dicesChangedByTC > 1) {
            diceCanBeMoved = false;
            ignoreEyes = false;
            ignoreColor = false;
            ignoreNextToDice = false;
            setCanOnlyMoveDiceWithSameColorAsDIceOnRoundTrackFalse();
            dicesChangedByTC = 0;
        }
    }

    /**
     * create a random window
     *
     * @return a random window
     */
    public WindowPatternModel createRandomWindow() {
        // all rows
        for (int row = 1; row < 5; row++) {
            // first row
            if (row == 1) {
                // every field in that row
                for (int column = 0; column < 5; column++) {
                    // check color of field left
                    int highColor = colorsField.size();
                    for (int z = 0; z < colorsField.size(); z++) {
                        if (column > 0
                                && windowModel.getFieldOfWindow(column - 1, row).getColor() == colorsField.get(z)) {
                            if (!colorsField.get(z).equals(Color.LIGHTGRAY)) {
                                colorsField.remove(z);
                                highColor--;
                            }
                        }
                    }

                    // check eyes left
                    // highnumber is the amount of places in the array, its 10 because you want to
                    // have more chances to get 0 eyes out of it
                    int highNumber = numbers.size();
                    for (int k = 0; k < numbers.size(); k++) {
                        if (column > 0 && windowModel.getFieldOfWindow(column - 1, row).getEyes() == numbers.get(k)) {
                            numbers.remove(k);
                            highNumber--;
                        }
                    }

                    // generate random numbers
                    int resultColor = r.nextInt(highColor);
                    int resultNumber = r.nextInt(highNumber);

                    // check if field is gray, than it has no eyes
                    if (colorsField.get(resultColor).equals(Color.LIGHTGRAY)) {
                        windowModel.getFieldOfWindow(column, row).setColorAndEyes(colorsField.get(resultColor),
                                numbers.get(resultNumber));

                    } else {
                        windowModel.getFieldOfWindow(column, row).setColorAndEyes(colorsField.get(resultColor), 0);
                    }

                    addColorsField();
                    addNumbersField();

                }

            }

            // row 2
            else {
                // all fields in that row
                for (int column = 0; column < 5; column++) {
                    int highColor = colorsField.size();
                    // every color above
                    for (int z = 0; z < colorsField.size(); z++) {
                        if (windowModel.getFieldOfWindow(column, row - 1).getColor() == colorsField.get(z)) {
                            // gray fields can be placed next to each other
                            if (!colorsField.get(z).equals(Color.LIGHTGRAY)) {
                                colorsField.remove(z);
                                highColor--;
                            }
                        }
                    }
                    // every color left
                    for (int z = 0; z < colorsField.size(); z++) {
                        if (column > 0
                                && windowModel.getFieldOfWindow(column - 1, row).getColor() == colorsField.get(z)) {
                            if (!colorsField.get(z).equals(Color.LIGHTGRAY)) {
                                colorsField.remove(z);
                                highColor--;
                            }
                        }
                    }

                    int highNumber = numbers.size();
                    // check eyes above
                    for (int k = 0; k < numbers.size(); k++) {
                        if (windowModel.getFieldOfWindow(column, row - 1).getEyes() == numbers.get(k)) {
                            numbers.remove(k);
                            highNumber--;
                        }
                    }

                    // check eyes left
                    for (int k = 0; k < numbers.size(); k++) {
                        if (column > 0 && windowModel.getFieldOfWindow(column - 1, row).getEyes() == numbers.get(k)) {
                            numbers.remove(k);
                            highNumber--;
                        }
                    }

                    Random r = new Random();
                    int resultColor = r.nextInt(highColor);
                    int resultNumber = r.nextInt(highNumber);

                    // check if field is gray, than it has no eyes
                    if (colorsField.get(resultColor).equals(Color.LIGHTGRAY)) {
                        windowModel.getFieldOfWindow(column, row).setColorAndEyes(colorsField.get(resultColor),
                                numbers.get(resultNumber));
                    } else {
                        windowModel.getFieldOfWindow(column, row).setColorAndEyes(colorsField.get(resultColor), 0);
                    }

                    // fill the array again
                    addColorsField();
                    addNumbersField();
                }
            }

        }
        return windowModel;
    }

    /**
     * @param b = the dice you picked up pick up a dice and store it
     */
    public void dragButton(DiceScreen b) {
        b.setOnDragDetected(e -> {
            if (GC.getGameModel().getPlayer(0).selectCurrentPlayer()) {
                Dragboard db = b.startDragAndDrop(TransferMode.MOVE);
                db.setDragView(b.snapshot(null, null));
                ClipboardContent cc = new ClipboardContent();
                cc.put(diceFormat, " ");
                db.setContent(cc);
                draggingDice = b;
                makeEveryBorderBlack();
            }
        });

        b.setOnDragDone(e -> {
            draggingDice = null;
        });
    }

    /**
     * @param pane = the field you are hovering on check if you can place the dice
     *             on the right field
     */
    private void addDropHandling(FieldScreen pane) {
        pane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            // check if you have a dice and you want to place it on your own board
            if (db.hasContent(diceFormat) && draggingDice != null && pane.getParent() == window1) {
                e.acceptTransferModes(TransferMode.MOVE);
                if (cheatAllPossible && !cheatBestChoice) {
                    whichPlacementIsPossible(draggingDice.getDiceModel(), null);
                } else if (!cheatAllPossible && cheatBestChoice) {
                    bestPossiblePlace(draggingDice.getDiceModel());
                }
            }
        });

        pane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            // check if dice meets all the requirements
            if (db.hasContent(diceFormat)
                    && (draggingDice.getDiceModel().getEyes() == pane.getFieldModel().getEyes()
                    || pane.getFieldModel().getEyes() == 0 || ignoreEyes)
                    && (draggingDice.getDiceModel().getColor() == pane.getFieldModel().getColor()

                    || pane.getFieldModel().getColor() == Color.LIGHTGRAY || ignoreColor)
                    && (DC.getDiceOnTableModel().isDiceOnTable(draggingDice.getDiceModel()) || diceCanBeMoved)
                    && !pane.getFieldModel().hasDice()
                    && (meetsNextToDiceRequirements(pane.getFieldModel(), draggingDice.getDiceModel())
                    || ignoreNextToDice)
                    && (isDiceNextToAnotherDice(pane.getFieldModel(), draggingDice.getDiceModel())
                    || ignoreNextToDice)) {

                if (DC.getDiceOnTableModel().isDiceOnTable(draggingDice.getDiceModel()) && !ignoreEyes && !ignoreColor
                        && (GC.getGameModel().canPlayerPlaceADiceInThisRoundFromTheTable() || getExtraTurn())) {
                    changedDiceBoard(TwoCanbeMoved);
                    if (!GC.getGameModel().canPlayerPlaceADiceInThisRoundFromTheTable()) {
                        setExtraTurnFalse();
                    }

                    DC.getDiceOnTableModel().removeDiceFromTable(draggingDice.getDiceModel());

                    pane.getFieldModel().addDice(draggingDice.getDiceModel());

                    GC.getGameModel().getPlayer(0).setDiceOnWindowPatternAndGiveFirstTurn(
                            (pane.getFieldModel().getColumn() + 1), pane.getFieldModel().getRow(),
                            draggingDice.getDiceModel().getDiceNumber(),
                            draggingDice.getDiceModel().getColorForQuerie(), GC.getGameModel().getInFirstTurn(),
                            GC.getGameModel().getGameID());

                    e.setDropCompleted(true);

                    calculatePoints();

                } else if (windowPattern1Model.diceOnWindow(draggingDice.getDiceModel())) {
                    if (!canOnlyMoveDiceWithSameColorAsDIceOnRoundTrack || GC.getGameModel()
                            .checkIfSameColorDiceIsOnRoundTrack(draggingDice.getDiceModel().getColor())) {
                        windowPattern1Model.removeDiceFromWindowPattern(draggingDice.getDiceModel());

                        pane.getFieldModel().addDice(draggingDice.getDiceModel());

                        GC.getGameModel().getPlayer(0).removeDiceOnWindowPattern(
                                draggingDice.getDiceModel().getDiceNumber(),
                                draggingDice.getDiceModel().getColorForQuerie());

                        GC.getGameModel().getPlayer(0).setDiceOnWindowPattern((pane.getFieldModel().getColumn() + 1),
                                pane.getFieldModel().getRow(), draggingDice.getDiceModel().getDiceNumber(),
                                draggingDice.getDiceModel().getColorForQuerie());

                        e.setDropCompleted(true);

                        changedDiceBoard(TwoCanbeMoved);

                    }

                    calculatePoints();
                }
            }

            makeEveryBorderBlack();

        });


    }

    /**
     * @param field = the field you want to place a die on
     * @param dice  = the dice you are holding
     * @return true when dice can be placed next to another dice checks if a dice
     * can be placed next to another dice
     */
    private boolean meetsNextToDiceRequirements(FieldModel field, DiceModel dice) {
        int column = getColumnAndRowOfField(field)[0];
        int row = getColumnAndRowOfField(field)[1];
        boolean accept = true;

        // check left
        try {
            if (windowPattern1Model.getFieldOfWindow(column - 1, row).hasDice()
                    && windowPattern1Model.getFieldOfWindow(column - 1, row).getDice() != dice) {
                if (windowPattern1Model.getFieldOfWindow(column - 1, row).getDice().getColor()
                        .equals(draggingDice.getDiceModel().getColor())
                        || windowPattern1Model.getFieldOfWindow(column - 1, row).getDice().getEyes() == draggingDice
                        .getDiceModel().getEyes()) {
                    accept = false;
                }
            }

        } catch (Exception e2) {
            // e2.printStackTrace();
        }

        // check right
        try {
            if (windowPattern1Model.getFieldOfWindow(column + 1, row).hasDice()
                    && windowPattern1Model.getFieldOfWindow(column + 1, row).getDice() != dice) {
                if (windowPattern1Model.getFieldOfWindow(column + 1, row).getDice().getColor()
                        .equals(draggingDice.getDiceModel().getColor())
                        || windowPattern1Model.getFieldOfWindow(column + 1, row).getDice().getEyes() == draggingDice
                        .getDiceModel().getEyes()) {
                    accept = false;

                }
            }

        } catch (Exception e2) {
            // e2.printStackTrace();
        }

        // check above
        try {
            if (windowPattern1Model.getFieldOfWindow(column, row - 1).hasDice()
                    && windowPattern1Model.getFieldOfWindow(column, row - 1).getDice() != dice) {
                if (windowPattern1Model.getFieldOfWindow(column, row - 1).getDice().getColor()
                        .equals(draggingDice.getDiceModel().getColor())
                        || windowPattern1Model.getFieldOfWindow(column, row - 1).getDice().getEyes() == draggingDice
                        .getDiceModel().getEyes()) {
                    accept = false;

                }
            }

        } catch (Exception e2) {
            // e2.printStackTrace();
        }

        // check bottom
        try {
            if (windowPattern1Model.getFieldOfWindow(column, row + 1).hasDice()
                    && windowPattern1Model.getFieldOfWindow(column, row + 1).getDice() != dice) {
                if (windowPattern1Model.getFieldOfWindow(column, row + 1).getDice().getColor()
                        .equals(draggingDice.getDiceModel().getColor())
                        || windowPattern1Model.getFieldOfWindow(column, row + 1).getDice().getEyes() == draggingDice
                        .getDiceModel().getEyes()) {
                    accept = false;

                }
            }

        } catch (Exception e2) {
            // e2.printStackTrace();
        }
        return accept;
    }

    /**
     * calculate the score
     *
     * @return the score
     */
    private int calculatePoints() {
        int points = 0;
        for (int j = 1; j < 5; j++) {
            for (int i = 0; i < 5; i++) {
                if (windowPattern1Model.getFieldOfWindow(i, j).hasDice())
                    points = points + windowPattern1Model.getFieldOfWindow(i, j).getDice().getEyes();
            }
        }
        return points;
    }

    /**
     * @param field = the field you want to place a die on
     * @param dice  = the dice you are holding
     * @return true when dice is next to another dice checks if dice is next to
     * another dice
     */
    private boolean isDiceNextToAnotherDice(FieldModel field, DiceModel dice) {
        // Checks if dice is diagonally, vertically or horizontally next to another dice
        boolean isNextToAnotherDice = false;
        int column = getColumnAndRowOfField(field)[0];
        int row = getColumnAndRowOfField(field)[1];

        try {
            // top-left
            if (windowPattern1Model.getFieldOfWindow(column - 1, row - 1).hasDice()
                    && windowPattern1Model.getFieldOfWindow(column - 1, row - 1).getDice() != dice) {
                isNextToAnotherDice = true;
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        try {
            // top
            if (windowPattern1Model.getFieldOfWindow(column, row - 1).hasDice()
                    && windowPattern1Model.getFieldOfWindow(column, row - 1).getDice() != dice) {
                isNextToAnotherDice = true;
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        try {
            // top-right
            if (windowPattern1Model.getFieldOfWindow(column + 1, row - 1).hasDice()
                    && windowPattern1Model.getFieldOfWindow(column + 1, row - 1).getDice() != dice) {
                isNextToAnotherDice = true;
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        try {
            // middle-left
            if (windowPattern1Model.getFieldOfWindow(column - 1, row).hasDice()
                    && windowPattern1Model.getFieldOfWindow(column - 1, row).getDice() != dice) {
                isNextToAnotherDice = true;
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        try {
            // middle-right
            if (windowPattern1Model.getFieldOfWindow(column + 1, row).hasDice()
                    && windowPattern1Model.getFieldOfWindow(column + 1, row).getDice() != dice) {
                isNextToAnotherDice = true;
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        try {
            // bottom-left
            if (windowPattern1Model.getFieldOfWindow(column - 1, row + 1).hasDice()
                    && windowPattern1Model.getFieldOfWindow(column - 1, row + 1).getDice() != dice) {
                isNextToAnotherDice = true;
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        try {
            // bottom
            if (windowPattern1Model.getFieldOfWindow(column, row + 1).hasDice()
                    && windowPattern1Model.getFieldOfWindow(column, row + 1).getDice() != dice) {
                isNextToAnotherDice = true;
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        try {
            // bottom-right
            if (windowPattern1Model.getFieldOfWindow(column + 1, row + 1).hasDice()
                    && windowPattern1Model.getFieldOfWindow(column + 1, row + 1).getDice() != dice) {
                isNextToAnotherDice = true;
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        if (calculatePoints() == 0 && (row == 1 || row == 4 || column == 0 || column == 4)) {
            isNextToAnotherDice = true;
        }

        return isNextToAnotherDice;
    }

    public WindowPatternScreen getWindow1() {
        return window1;
    }

    public WindowPatternScreen getWindow2() {
        return window2;
    }

    public WindowPatternScreen getWindow3() {
        return window3;
    }

    public WindowPatternScreen getWindow4() {
        return window4;
    }

    /**
     * @param windowModel = window that needs to be gray make the fields of a window
     *                    gray
     */
    public void makeWindowsGray(WindowPatternModel windowModel) {
        for (int row = 1; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                windowModel.getFieldOfWindow(column, row).setColorAndEyes(Color.LIGHTGRAY, 0);
            }

        }
    }

    /**
     * @param dice   = dice you are holding
     * @param fields = all the fields on a window checks where the dice can be
     *               placed
     */
    private void whichPlacementIsPossible(DiceModel dice, ArrayList<FieldModel> fields) {
        for (int row = 1; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                if ((dice.getEyes() == window1.getWindowPatternModel().getFieldOfWindow(column, row).getEyes()
                        || window1.getWindowPatternModel().getFieldOfWindow(column, row).getEyes() == 0 || ignoreEyes)
                        && (dice.getColor() == window1.getWindowPatternModel().getFieldOfWindow(column, row).getColor()
                        || window1.getWindowPatternModel().getFieldOfWindow(column, row)
                        .getColor() == Color.LIGHTGRAY
                        || ignoreColor)
                        && (DC.getDiceOnTableModel().isDiceOnTable(draggingDice.getDiceModel()) || diceCanBeMoved)
                        && !window1.getWindowPatternModel().getFieldOfWindow(column, row).hasDice()
                        && meetsNextToDiceRequirements(window1.getWindowPatternModel().getFieldOfWindow(column, row),
                        dice)
                        && isDiceNextToAnotherDice(window1.getWindowPatternModel().getFieldOfWindow(column, row),
                        dice)) {

                    if ((windowPattern1Model.diceOnWindow(dice) && (!ignoreEyes || !ignoreColor))
                            || (DC.getDiceOnTableModel().isDiceOnTable(draggingDice.getDiceModel())) && !ignoreEyes
                            && !ignoreColor) {
                        if (fields != null) {
                            fields.add(windowPattern1Model.getFieldOfWindow(column, row));
                        } else {
                            window1.setCheat(column, row);
                        }
                    }

                }
            }
        }

    }

    /**
     * @param dice = dice you are holding checks the best possible place/places that
     *             a dice can be placed
     */
    private void bestPossiblePlace(DiceModel dice) {
        ArrayList<FieldModel> allFields = new ArrayList<>();
        int highestPoints = 0;
        ArrayList<FieldModel> allBestFields = new ArrayList<>();

        whichPlacementIsPossible(dice, allFields);

        try {
            if (allFields.size() != 0) {
                for (FieldModel field : allFields) {
                    int column = getColumnAndRowOfField(field)[0];
                    int row = getColumnAndRowOfField(field)[1];
                    int points = 0;
                    // top
                    try {
                        if ((windowPattern1Model.getFieldOfWindow(column, row - 1).getColor() != dice.getColor()
                                || windowPattern1Model.getFieldOfWindow(column, row - 1).getColor() == Color.LIGHTGRAY)
                                && (windowPattern1Model.getFieldOfWindow(column, row - 1).getEyes() != dice.getEyes()
                                || windowPattern1Model.getFieldOfWindow(column, row - 1).getEyes() == 0)) {
                            points++;
                        } else {
                            points = points - 2;
                        }
                    } catch (Exception e) {
                        // e.printStackTrace();
                    }

                    // middle-left
                    try {
                        if ((windowPattern1Model.getFieldOfWindow(column - 1, row).getColor() != dice.getColor()
                                || windowPattern1Model.getFieldOfWindow(column - 1, row).getColor() == Color.LIGHTGRAY)
                                && (windowPattern1Model.getFieldOfWindow(column - 1, row).getEyes() != dice.getEyes()
                                || windowPattern1Model.getFieldOfWindow(column - 1, row).getEyes() == 0)) {
                            points++;
                        } else {
                            points = points - 2;
                        }
                    } catch (Exception e) {
                        // e.printStackTrace();
                    }

                    // middle-right
                    try {
                        if ((windowPattern1Model.getFieldOfWindow(column + 1, row).getColor() != dice.getColor()
                                || windowPattern1Model.getFieldOfWindow(column + 1, row).getColor() == Color.LIGHTGRAY)
                                && (windowPattern1Model.getFieldOfWindow(column + 1, row).getEyes() != dice.getEyes()
                                || windowPattern1Model.getFieldOfWindow(column + 1, row).getEyes() == 0)) {
                            points++;
                        } else {
                            points = points - 2;
                        }
                    } catch (Exception e) {
                        // e.printStackTrace();
                    }

                    // bottom
                    try {

                        if ((windowPattern1Model.getFieldOfWindow(column, row + 1).getColor() != dice.getColor()
                                || windowPattern1Model.getFieldOfWindow(column, row + 1).getColor() == Color.LIGHTGRAY)
                                && (windowPattern1Model.getFieldOfWindow(column, row + 1).getEyes() != dice.getEyes()
                                || windowPattern1Model.getFieldOfWindow(column, row + 1).getEyes() == 0)) {
                            points++;
                        } else {
                            points = points - 2;
                        }

                    } catch (Exception e) {
                        // e.printStackTrace();
                    }

                    if (field.getColor() != Color.LIGHTGRAY || field.getEyes() != 0) {
                        points++;
                    }

                    if (points == highestPoints) {
                        allBestFields.add(field);
                    } else if (points > highestPoints) {
                        allBestFields.clear();
                        allBestFields.add(field);
                        highestPoints = points;
                    }
                }

                for (FieldModel bestField : allBestFields) {
                    window1.setCheat(bestField.getColumn(), bestField.getRow());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * make every border black
     */
    private void makeEveryBorderBlack() {
        for (int row = 1; row < 5; row++) {
            for (int column = 0; column < 5; column++) {

                window1.setNormal(column, row);
            }
        }
    }

    void setCheatAllPossible(boolean b) {
        cheatAllPossible = b;
    }

    void setCheatBestChoice(boolean b) {
        cheatBestChoice = b;
    }

    void setGameController(GameController GC) {
        this.GC = GC;
    }

    /**
     * @param windowPatternModel = window model
     * @return the difficulty of a window calculates the difficulty of a window
     */
    public int calculateDifficulty(WindowPatternModel windowPatternModel) {
        int difficulty = 0;
        for (int row = 1; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                if (windowPatternModel.getFieldOfWindow(column, row).getColor() != Color.LIGHTGRAY) {
                    difficulty++;
                }

                if (windowPatternModel.getFieldOfWindow(column, row).getEyes() > 0
                        && windowPatternModel.getFieldOfWindow(column, row).getEyes() < 7) {
                    difficulty++;
                }
            }
        }

        if (difficulty > 0 && difficulty <= 4) {
            difficulty = 1;
        } else if (difficulty > 4 && difficulty <= 8) {
            difficulty = 2;
        } else if (difficulty > 8 && difficulty <= 11) {
            difficulty = 3;
        } else if (difficulty > 11 && difficulty <= 14) {
            difficulty = 4;
        } else if (difficulty > 14 && difficulty <= 17) {
            difficulty = 5;
        } else if (difficulty > 17 && difficulty <= 20) {
            difficulty = 6;
        } else {
            difficulty = 0;
        }

        return difficulty;
    }

    void setDiceController(DiceController DC) {
        this.DC = DC;
    }

    /**
     * @param windowScreen = window screen
     * @param windowModel  = window model make all the fields for a window and give
     *                     the screen the right model and calculate the difficulty
     */
    private void createGrayWindowPattern(WindowPatternScreen windowScreen, WindowPatternModel windowModel) {
        for (int row = 1; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                FieldModel fieldModel = new FieldModel(column, row, Color.LIGHTGRAY);
                FieldScreen fieldScreen = new FieldScreen(fieldModel, this);
                fieldModel.setEyes(0);
                addDropHandling(fieldScreen);
                windowScreen.add(fieldScreen, column, row);
                windowModel.addFieldToWindow(fieldModel);
            }
        }

        calculateDifficulty(windowModel);
    }

    /**
     * @param field = field on window model 1
     * @return the column and row of a field on window model 1 check what the row
     * and column of a field are
     */
    private int[] getColumnAndRowOfField(FieldModel field) {
        for (int row = 1; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                if (field.equals(windowPattern1Model.getFieldOfWindow(column, row))) {
                    return new int[]{column, row};
                }
            }
        }
        return null;
    }

    /**
     * select a dice on the window, used for switching dice with dice on roundtrack
     */
    void selectDiceOnWindow() {
        GC.stopTimer();
        for (Node node : window1.getChildren()) {
            if (node instanceof FieldScreen) {
                FieldScreen result = (FieldScreen) node;
                for (Node node2 : result.getChildren()) {
                    if (node2 instanceof DiceScreen) {
                        DiceScreen dice = (DiceScreen) node2;
                        dice.setGlowBorder();
                        dice.setOnMouseClicked(e -> switchDiceWithRoundTrack(dice,
                                (result.getFieldModel().getColumn() + 1), result.getFieldModel().getRow()));

                    }
                }
            }
        }
    }

    /**
     * give all the dices on window 1 a white border
     */
    private void setDiceWhiteBorder() {
        for (Node node : window1.getChildren()) {
            if (node instanceof FieldScreen) {
                FieldScreen result = (FieldScreen) node;
                for (Node node2 : result.getChildren()) {
                    if (node2 instanceof DiceScreen) {
                        DiceScreen dice = (DiceScreen) node2;
                        dice.makeBorderWhite();
                        dice.setOnMouseClicked(null);
                    }
                }
            }
        }

    }

    /**
     * @param dice   = the dice you clicked on
     * @param column = the dice column location
     * @param row    = the dice row location handles switching dice on window with
     *               dice on roundtrack
     */
    private void switchDiceWithRoundTrack(DiceScreen dice, int column, int row) {
        GC.stopTimer();
        setDiceWhiteBorder();
        dice.setGlowBorder();
        gui.handleGoToRoundTrack();
        int diceNumberWindow = dice.getDiceModel().getDiceNumber();
        String diceColorWindow = dice.getDiceModel().getColorForQuerie();
        RC.clickDiceOnRoundTrack(diceNumberWindow, diceColorWindow, column, row);

        // cant go back to gameScreen untill dice ont roundtrack clicked

    }

    void setExtraTurnTrue() {
        extraTurn = true;
    }

    public void setExtraTurnFalse() {
        extraTurn = false;
    }

    private boolean getExtraTurn() {
        return extraTurn;
    }

    void setCanOnlyMoveDiceWithSameColorAsDIceOnRoundTrackFalse() {
        canOnlyMoveDiceWithSameColorAsDIceOnRoundTrack = false;
    }

    private void setCanOnlyMoveDiceWithSameColorAsDIceOnRoundTrackTrue() {
        canOnlyMoveDiceWithSameColorAsDIceOnRoundTrack = true;
    }

    void setDiceCanBeMovedFalse() {
        diceCanBeMoved = false;
    }

    void setRoundController(RoundScreenController RC) {
        this.RC = RC;
    }

    GameController getGameController() {
        return GC;
    }
}
