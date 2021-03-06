package queries;

import java.util.ArrayList;

public class CardQueries {
    private StandardQueries standardQuerie;

    public CardQueries(StandardQueries standardQueries) {
        this.standardQuerie = standardQueries;
    }

    public ArrayList<ArrayList<Object>> checkBoughtTCForRound(int idgame, int playerID, int round, int infirstturn) {

        return standardQuerie.selectQuery("Select gametoolcard from gamefavortoken",
                " where idgame=? and idplayer=? and round=? and inFirstTurn=? ", "" + idgame + "\0" + playerID + "\0" + round + "\0" + infirstturn + "");

    }


    public ArrayList<ArrayList<Object>> CheckTCBought(int tc, int idGame) {

        return standardQuerie.selectQuery("Select idfavortoken from gamefavortoken",
                " where gametoolcard=? and idgame=? ", "" + tc + "\0" + idGame + "");

    }

    public ArrayList<ArrayList<Object>> CheckIDFT(int playerID, int idgame) {
        return standardQuerie.selectQuery("Select idfavortoken from gamefavortoken",
                " where idGame=? and idplayer=? and gametoolcard is null", "" + idgame + "\0" + playerID + "");


    }

    public ArrayList<ArrayList<Object>> getGameToolcardID(int idgame) {
        return standardQuerie.selectQuery("Select gametoolcard from gametoolcard",
                " where idGame=? ", "" + idgame + "");

    }


    public void BuyTC(int tc, int FT, int GameID, int playerID, int round, int inFirstTurn) {
        standardQuerie.updateQuery("update gamefavortoken Set gametoolcard=?, round=?, inFirstTurn=?", "" + tc + "\0" + round + "\0" + inFirstTurn + "",
                " where idfavortoken=?  and idgame=? and idplayer=?", "" + FT + "\0" + GameID + "\0" + playerID + "");

    }

    public void BuyTCPrice2(int tc, int FT, int FT2, int GameID, int playerID, int round, int inFirstTurn) {

        standardQuerie.updateQuery("update gamefavortoken Set gametoolcard=?, round=?, inFirstTurn=?", "" + tc + "\0" + round + "\0" + inFirstTurn + "",
                " where idfavortoken=? or idfavortoken=?  and idgame=? and idplayer=?",
                "" + FT + "\0" + FT2 + "\0" + GameID + "\0" + playerID + "");

    }

    public ArrayList<ArrayList<Object>> CheckAmountFTonTC(int tc, int playerID, int idgame) {
        return standardQuerie.selectQuery("Select idfavortoken from gamefavortoken",
                " where gametoolcard=? and idgame=? and idplayer=?", "" + tc + "\0" + idgame + "\0" + playerID + "");


    }

    public ArrayList<ArrayList<Object>> getToolcard(int tc, int idgame) {

        return standardQuerie.selectQuery("Select idtoolcard from gametoolcard", " where gametoolcard=? and idGame=?", "" + tc + "\0" + idgame + "");

    }

    public ArrayList<ArrayList<Object>> getOBJCard(int idgame) {
        return standardQuerie.selectQuery("Select idpublic_objectivecard from sharedpublic_objectivecard", " where idGame=?", "" + idgame + "");

    }

    public void updateDiceOnTable(int eyes, int dienumber, int GameID, String color) {
        standardQuerie.updateQuery("update gamedie Set eyes=?", "" + eyes + "",
                " where dienumber=?  and idgame=? and diecolor=?", "" + dienumber + "\0" + GameID + "\0" + color + "");

    }

    public ArrayList<ArrayList<Object>> getPrivateOBJCard(int gameID, int playerID) {
        return standardQuerie.selectQuery("Select private_objectivecard_color from player", " where game_idgame=? and idplayer=?", "" + gameID + "\0" + playerID + "");
    }


}



