package com.example.belot.controller;

import com.example.belot.entity.Player;
import com.example.belot.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {
    @Autowired
    ScoreService scoreService;
    Player player = new Player();
    Player player2 = new Player();
    Player player3 = new Player();
    List<Player> listOfPlayers = new ArrayList<>();

    public List<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public void setListOfPlayers(List<Player> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    String winner;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @GetMapping("/3players")
    public ModelAndView homePage(Model model) {
        scoreService.getPlayers().clear();
        player.setName("Serghei");
        player2.setName("Dimon");
        player3.setName("Cristi");
        listOfPlayers.add(player);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);
        if (scoreService.getPlayers().isEmpty()) {
            scoreService.createPlayer(player);
            scoreService.createPlayer(player2);
            scoreService.createPlayer(player3);
        }
        //get last 4 elements from player score
        List<?> tail = player.getScore().subList(Math.max(player.getScore().size() - 4, 0), player.getScore().size());
        List<?> tail2 = player2.getScore().subList(Math.max(player2.getScore().size() - 4, 0), player2.getScore().size());
        List<?> tail3 = player3.getScore().subList(Math.max(player3.getScore().size() - 4, 0), player3.getScore().size());

        model.addAttribute("player_score", tail);
        model.addAttribute("player_score2", tail2);
        model.addAttribute("player_score3", tail3);
        model.addAttribute("player1_bolt", player.getName() + " " + player.getBolt());
        model.addAttribute("player2_bolt", player2.getName() + " " + player2.getBolt());
        model.addAttribute("player3_bolt", player3.getName() + " " + player3.getBolt());
        model.addAttribute("player_name", player.getName());
        model.addAttribute("player_name_second", player2.getName());
        model.addAttribute("player_name_third", player3.getName());


        return new ModelAndView("home-page-test");
    }


    @RequestMapping("/3players/add-score")
    public ModelAndView addScore(@RequestParam(value = "score", required = false) String score, @RequestParam(value = "score2", required = false) String score2, @RequestParam(value = "score3", required = false) String score3) {
        System.out.println("1>>>>>" + score);
        System.out.println("2>>>>>" + score2);
        System.out.println("3>>>>>" + score3);
        try {
            scoreService.addPlayerScore(player, Integer.parseInt(score), false);
        } catch (Exception e) {
            scoreService.addPlayerScore(player, true);
        }
        try {
            scoreService.addPlayerScore(player2, Integer.parseInt(score2), false);
        } catch (Exception e) {
            scoreService.addPlayerScore(player2, true);
        }
        try {
            scoreService.addPlayerScore(player3, Integer.parseInt(score3), false);
        } catch (Exception e) {
            scoreService.addPlayerScore(player3, true);
        }
        System.out.println(scoreService.getPlayers());
        if (!scoreService.getPlayerScore(player).isEmpty()) {
            try {
                String win = scoreService.getWinner(scoreService.getPlayers());
                if (win != null) {
                    setWinner(win);
                    return new ModelAndView("redirect:/win-page-test");
                }
            } catch (Exception e) {
            }
        }

        return new ModelAndView("redirect:/3players");
    }

    @RequestMapping("3players/delete-last-score")
    public ModelAndView deleteLastScore() {
        if (!player.getScore().isEmpty()) {
            scoreService.getPlayerScore(player).remove(player.getScore().size() - 1);
            scoreService.getPlayerScore(player2).remove(player2.getScore().size() - 1);
            scoreService.getPlayerScore(player3).remove(player3.getScore().size() - 1);
        }
        return new ModelAndView("redirect:/3players");
    }

    @RequestMapping("3players/new-game")
    public ModelAndView newGameStart() {
        scoreService.getPlayerScore(player).clear();
        scoreService.getPlayerScore(player2).clear();
        scoreService.getPlayerScore(player3).clear();
        player.setBolt(0);
        player2.setBolt(0);
        player3.setBolt(0);
        return new ModelAndView("redirect:/3players");
    }

    @RequestMapping("3players/edit-last-score")
    public ModelAndView editLastScore(@RequestParam(value = "score", required = false) String score, @RequestParam(value = "score2", required = false) String score2, @RequestParam(value = "score3", required = false) String score3) {
        if (!player.getScore().isEmpty()) {
            int item = player.getScore().size() - 1;

            int first = scoreService.getPlayerScore(player).get(player.getScore().size() - 2);
            int second = scoreService.getPlayerScore(player2).get(player2.getScore().size() - 2);
            int third = scoreService.getPlayerScore(player3).get(player3.getScore().size() - 2);
            try {
                scoreService.getPlayerScore(player).set(item, first + Integer.parseInt(score));
            } catch (Exception e) {
                scoreService.addPlayerScore(player, true);
            }
            try {
                scoreService.getPlayerScore(player2).set(item, second + Integer.parseInt(score2));
            } catch (Exception e) {
                scoreService.addPlayerScore(player2, true);
            }
            try {
                scoreService.getPlayerScore(player3).set(item, third + Integer.parseInt(score3));
            } catch (Exception e) {
                scoreService.addPlayerScore(player3, true);
            }
        }

        return new ModelAndView("redirect:/3players");
    }

    @RequestMapping("/win-page-test")
    public ModelAndView showWinner(Model model) {
        model.addAttribute("winner_name", "You win: " + getWinner());
        return new ModelAndView("win-page-test");
    }

}
