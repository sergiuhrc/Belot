package com.example.belot.service;

import com.example.belot.entity.Game;
import com.example.belot.entity.Player;
import com.example.belot.resource.ScoreResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService implements ScoreResource {
    private static final int MINUSTEN = -10;
    List<Player> players = new ArrayList<>();
    Map<Player, Integer> playerScore = new HashMap<>();
    Game game = new Game(101);
    @Autowired
    ScoreService scoreService;

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
    public void addPlayerScore(Player player, int points, boolean isBolt) {
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
            if (isBolt) {
                int bolt = player.getBolt() + 1;
                player.setBolt(bolt);
                player.setScore(player.getScore().get(lastScore));
                playerScore.put(player, points);
            } else {
                player.setScore(points);
                playerScore.put(player, points);
            }
        }
        if (player.getBolt() == 3) {
            player.setScore(player.getScore().get(lastScore) + MINUSTEN);

        }
    }

    public String getWinner(List<Player> players) {

        System.out.println("----------->" + game.getPoints());
        int countPlayersAboveMaxPoints = 0;
        List<Player> playerList = new ArrayList<>();
        List<Player> winner = new ArrayList<>();
        for (Player player : players) {
            int lastScore = player.getScore().size() - 1;
            int score = player.getScore().get(lastScore);
            if (score >= game.getPoints()) {
                countPlayersAboveMaxPoints++;
                playerList.add(player);
            }
        }
        System.out.println("PlayerList" + playerList);
        if (countPlayersAboveMaxPoints == 1 && game.getPoints() == 101) {
            System.out.println("Found winner on score 101: " + playerList.get(0));
            winner.add(playerList.get(0));
            game.setPoints(101);
            return winner.get(0).getName();
        } else if (countPlayersAboveMaxPoints == 1 && game.getPoints() == 151) {
            System.out.println("Found winner on score 151: " + playerList.get(0));
            winner.add(playerList.get(0));
            game.setPoints(101);

            return winner.get(0).getName();
        } else if (countPlayersAboveMaxPoints > 1) {
            System.out.println("Jucam pina la 151");
            System.out.println("Else statment: " + playerList);
            game.setPoints(151);
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
    public void addPlayerScore(Player player, boolean isBolt) {
        try {
            int lastScore = player.getScore().get(player.getScore().size() - 1);
            if (isBolt) {

                player.setBolt(player.getBolt() + 1);
                if (player.getBolt() == 3) {
                    System.out.println("Is bolt 3");
                    player.setScore((lastScore) + (MINUSTEN));
                    player.setBolt(0);
                } else if (player.getBolt() < 3) {
                    if (player.getScore().isEmpty()) {
                        player.setScore(0);
                    } else {
                        player.setScore(lastScore);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            int lastScore = player.getScore().size() - 1;
            if (isBolt) {
                player.setBolt(player.getBolt() + 1);
                if (player.getScore().isEmpty()) {
                    player.setScore(0);
                } else {
                    System.out.println("Not here");
                    player.setScore(lastScore);
                }
            }

        }
    }


//    @Override
//    public void editPlayerScore(Player player, boolean isBolt) {
//        try {
//            int lastScore = player.getScore().size() - 1;
//            if (isBolt) {
//
//                player.setBolt(player.getBolt() + 1);
//                if (player.getBolt() == 3) {
//                    player.setScore(player.getScore().get(lastScore) + MINUSTEN);
//                    player.setBolt(0);
//                } else if (player.getBolt() < 3) {
//                    if (player.getScore().isEmpty()) {
//                        player.setScore(0);
//                    } else {
//                        Integer previousScore = scoreService.getPlayerScore(player).get(lastScore);
//                        scoreService.getPlayerScore(player).set(lastScore, previousScore);
//                    }
//                }
//            }
//        } catch (ArrayIndexOutOfBoundsException e) {
//            int lastScore = player.getScore().size() - 1;
//            if (isBolt) {
//                if (player.getScore().isEmpty()) {
//                    player.setScore(0);
//                } else {
//                    Integer previousScore = scoreService.getPlayerScore(player).get(lastScore);
//                    scoreService.getPlayerScore(player).set(lastScore, previousScore);
//                }
//            }
//
//        }
//    }

}
