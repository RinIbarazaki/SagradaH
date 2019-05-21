package queries;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserListQueries {
    private StandardQueries queries;

    public UserListQueries(StandardQueries queries){ this.queries = queries; }

    public ArrayList<ArrayList<Object>> getUserList(){
       return queries.selectQuery("SELECT username FROM account");
    }

    public ArrayList<ArrayList<Object>> getUserStats(){
        ArrayList<ArrayList<Object>> gameStats = new ArrayList<>(
                queries.selectQuery("SELECT p.username, MAX(p.score), COUNT(p2.game_idgame), COUNT(p.game_idgame) - COUNT(p2.game_idgame)" +
                " FROM player p LEFT JOIN (SELECT game_idgame, MAX(score) as max FROM player GROUP BY game_idgame) p2" +
                " ON p.game_idgame = p2.game_idgame AND p2.max = p.score" +
                " GROUP BY p.username"));

        ArrayList<ArrayList<Object>> dieColors = new ArrayList<>(
                queries.selectQuery("SELECT s.username, s.diecolor FROM (" +
                " SELECT p.username, g.diecolor, COUNT(g.diecolor) as diecount FROM player p" +
                " INNER JOIN playerframefield p2 ON p.idplayer = p2.player_idplayer" +
                " INNER JOIN gamedie g on p2.idgame = g.idgame and p2.dienumber = g.dienumber and p2.diecolor = g.diecolor" +
                " GROUP BY p.username, g.diecolor) s" +
                " INNER JOIN (SELECT s.username, MAX(s.diecount) as maxDieColor" +
                " FROM (SELECT p.username, g.diecolor, COUNT(*) as diecount FROM player p" +
                " INNER JOIN playerframefield p2 on p.idplayer = p2.player_idplayer" +
                " INNER JOIN gamedie g on p2.idgame = g.idgame and p2.dienumber = g.dienumber and p2.diecolor = g.diecolor" +
                " GROUP BY p.username, g.diecolor) s" +
                " GROUP BY s.username) as m ON s.username = m.username AND s.diecount = m.maxDieColor"));

        ArrayList<ArrayList<Object>> eyes = new ArrayList<>(
                queries.selectQuery("SELECT s.username, s.eyes FROM (" +
                " SELECT p.username, g.eyes, COUNT(g.eyes) as diecount FROM player p" +
                " INNER JOIN playerframefield p2 ON p.idplayer = p2.player_idplayer" +
                " INNER JOIN gamedie g on p2.idgame = g.idgame and p2.dienumber = g.dienumber and p2.diecolor = g.diecolor" +
                " GROUP BY p.username, g.eyes) s" +
                " INNER JOIN (SELECT s.username, MAX(s.diecount) as maxEyes" +
                " FROM (SELECT p.username, g.eyes, COUNT(g.eyes) as diecount FROM player p" +
                " INNER JOIN playerframefield p2 on p.idplayer = p2.player_idplayer" +
                " INNER JOIN gamedie g on p2.idgame = g.idgame and p2.dienumber = g.dienumber and p2.diecolor = g.diecolor" +
                " GROUP BY p.username, g.eyes) s" +
                " GROUP BY s.username) as m ON s.username = m.username AND s.diecount = m.maxEyes"));

        ArrayList<ArrayList<Object>> names = getUserList();
        ArrayList<ArrayList<Object>> uniquePlayers = new ArrayList<>();

        for(Object rows : names){
            for(Object name : names){
                String nameVal = name.toString();
                uniquePlayers.addAll(queries.selectQuery("SELECT DISTINCT p.username FROM player p" +
                        " INNER JOIN (SELECT p2.game_idgame FROM player p2 WHERE username=? AND playstatus_playstatus = 'uitgespeeld')" +
                        " AS games ON p.game_idgame = p.game_idgame" +
                        " WHERE p.playstatus_playstatus = 'uitgespeeld' AND username !=?", " ", "" + nameVal +"\0"+ nameVal + ""));
            }
        }

        return groupStats(gameStats, dieColors, eyes, uniquePlayers);
    }

    private ArrayList<ArrayList<Object>> groupStats(ArrayList<ArrayList<Object>> gameStats, ArrayList<ArrayList<Object>> dieColor,
                                                    ArrayList<ArrayList<Object>> eyes, ArrayList<ArrayList<Object>> uniquePlayers){
        ArrayList<ArrayList<Object>> grouped = new ArrayList<>();
        grouped.addAll(gameStats);
        grouped.addAll(dieColor);
        grouped.addAll(eyes);
        grouped.addAll(uniquePlayers);

        return grouped;
    }

    public ArrayList<ArrayList<Object>> getUserList(Object sort){
        String sortVal = sort.toString();
        switch (sortVal){
            case "Gewonnen potjes": return queries.selectQuery("SELECT p.username" +
                    " FROM player p LEFT JOIN (SELECT game_idgame, MAX(score) as max FROM player GROUP BY game_idgame) p2" +
                    " ON p.game_idgame = p2.game_idgame AND p2.max = p.score" +
                    " GROUP BY p.username ORDER BY COUNT(p2.game_idgame) DESC");
            case "Alfabetisch": return queries.selectQuery("SELECT username FROM account");
            default: return null;
        }
    }
}
