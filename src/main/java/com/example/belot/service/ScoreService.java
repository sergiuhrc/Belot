package com.example.belot.service;

import com.example.belot.entity.Game;
import com.example.belot.entity.Player;
import com.example.belot.resource.ScoreResource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScoreService implements ScoreResource {
    List<Player> players = new ArrayList<>();
    Map<Player, Integer> playerScore = new HashMap<>();
    private static final int MINUSTEN = -10;


    public List<Integer> getPlayerScore(Player player) {
        return player.getScore();
    }

    public void setNullPlayerScore(Player player) {
        player.setNewScore();
    }

    @Override
    public Map<Player, Integer> getPlayerScore() {
        return playerScore;
    }

    @Override
    public void setPlayerScore(Player player, int points, boolean isBolt) {
//        if (player.getScore().size() == 0) {
//            player.setScore(0);
//        }

        int lastScore = player.getScore().size() - 1;
        try {
            int score = points + player.getScore().get(lastScore);
            if (isBolt) {
                int bolt = player.getBolt() + 1;
                player.setBolt(bolt);
                player.setScore(player.getScore().get(lastScore));
                playerScore.put(player, score);
            } else {

                player.setScore(score);
                playerScore.put(player, score);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            int score = points;
            if (isBolt) {
                int bolt = player.getBolt() + 1;
                player.setBolt(bolt);
                player.setScore(player.getScore().get(lastScore));
                playerScore.put(player, score);
            } else {

                player.setScore(score);
                playerScore.put(player, score);
            }
        }


        if (player.getBolt() == 3) {
            player.setScore(player.getScore().get(lastScore) + MINUSTEN);
            player.setBolt(0);
//            playerScore.put(player, (player.getScore().get(lastScore) + MINUSTEN));
        }
    }

    Game game = new Game(101);
//to do
    public Player getWinner(List<Player> players) {


        List<Player> listOfPlayers = new ArrayList<>();
        for (Player player : players) {
            int lastScore = player.getScore().size() - 1;
            int score = player.getScore().get(lastScore);
            if (score >= game.getPoints()) {
                listOfPlayers.add(player);
            }
        }

        if (!listOfPlayers.isEmpty() && listOfPlayers.size() >= 1 && game.getPoints() == 101) {

            List<Integer> integerArrayList = new ArrayList<>();
            for (Player player : listOfPlayers) {
                int lastScore = player.getScore().size() - 1;
                int last = listOfPlayers.get(0).getScore().get(lastScore);
                integerArrayList.add(last);
            }
            System.out.println(">>>>>>>" + integerArrayList);
            if (integerArrayList.size() == 1) {
                System.out.println(integerArrayList.size());
                Integer getMaxPointsPlayer = Collections.max(integerArrayList);
                for (Player player : listOfPlayers) {
                    int lastScore = player.getScore().size() - 1;
                    if (player.getScore().get(lastScore).equals(getMaxPointsPlayer)) {
                        return player;
                    }
                }
            } else {
                System.out.println("Else" + integerArrayList.size());
                game.setPoints(151);
            }

            System.out.println("Jucam pina la 151");
        } else if (game.getPoints() == 151) {
            List<Integer> integerArrayList = new ArrayList<>();
            for (Player player : listOfPlayers) {
                int lastScore = player.getScore().size() - 1;
                int last = listOfPlayers.get(0).getScore().get(lastScore);
                integerArrayList.add(last);
            }
            Integer getMaxPointsPlayer = Collections.max(integerArrayList);
            for (Player player : listOfPlayers) {
                int lastScore = player.getScore().size() - 1;
                if (player.getScore().get(lastScore).equals(getMaxPointsPlayer)) {
                    return player;
                }
            }

        }

        return null;
    }


    @Override
    public void createPlayer(Player player) {
        players.add(player);
    }

    @Override
    public void deletePlayer(Player player) {
        players.remove(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void setPlayerScore(Player player, boolean isBolt) {
        int lastScore = player.getScore().size() - 1;
        if (isBolt) {

            player.setBolt(player.getBolt() + 1);
            System.out.println(player.getBolt());
            if (player.getBolt() == 3) {
                System.out.println("Is bolt 3");
                player.setScore(player.getScore().get(lastScore) + MINUSTEN);
                player.setBolt(0);
            } else if (player.getBolt() < 3) {
                player.setScore(player.getScore().get(lastScore));
            }
        }
    }


}
