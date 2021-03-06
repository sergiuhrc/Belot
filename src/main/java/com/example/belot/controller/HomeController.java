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
public class HomeController {
    @Autowired
    ScoreService scoreService;
    Player player = new Player();
    Player player2 = new Player();
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

    @GetMapping("/index")
    public ModelAndView indexPage(Model model) {


        return new ModelAndView("index");
    }

    @GetMapping("/2players")
    public ModelAndView homePage(Model model) {
        scoreService.getPlayers().clear();
        player.setName("Sergiu");
        player2.setName("Dima");
        listOfPlayers.add(player);
        listOfPlayers.add(player2);
        if (scoreService.getPlayers().isEmpty()) {
            scoreService.createPlayer(player);
            scoreService.createPlayer(player2);
        }
        //get last 4 elements from player score
        List<?> tail = player.getScore().subList(Math.max(player.getScore().size() - 4, 0), player.getScore().size());
        List<?> tail2 = player2.getScore().subList(Math.max(player2.getScore().size() - 4, 0), player2.getScore().size());


        System.out.println(scoreService.getPlayerScore(player));
        System.out.println(scoreService.getPlayerScore(player2));
        model.addAttribute("player_score", tail);
        model.addAttribute("player_score2", tail2);
        model.addAttribute("player1_bolt", player.getName() + " " + player.getBolt());
        model.addAttribute("player2_bolt", player2.getName() + " " + player2.getBolt());
        model.addAttribute("player_name", player.getName());
        model.addAttribute("player_name_second", player2.getName());


        return new ModelAndView("home-page");
    }

    @RequestMapping("/win-page")
    public ModelAndView showWinner(Model model) {
        model.addAttribute("winner_name", "You win: " + getWinner());
        return new ModelAndView("win-page");
    }


    @RequestMapping("/add-score")
    public ModelAndView addScore(@RequestParam(value = "score", required = false) String score, @RequestParam(value = "score2", required = false) String score2) {

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
        System.out.println(scoreService.getPlayers());
        if (!scoreService.getPlayerScore(player).isEmpty()) {
            try {
                String win = scoreService.getWinner(scoreService.getPlayers());
                if (win != null) {
                    setWinner(win);
                    return new ModelAndView("redirect:/win-page");
                }
            } catch (Exception e) {
            }
        }

        return new ModelAndView("redirect:/2players");
    }

    @RequestMapping("/delete-last-score")
    public ModelAndView deleteLastScore() {
        if (!player.getScore().isEmpty()) {
            scoreService.getPlayerScore(player).remove(player.getScore().size() - 1);
            scoreService.getPlayerScore(player2).remove(player2.getScore().size() - 1);
        }
        return new ModelAndView("redirect:/2players");
    }

    @RequestMapping("/new-game")
    public ModelAndView newGameStart() {
        scoreService.getPlayerScore(player).removeAll(scoreService.getPlayerScore(player));
        scoreService.getPlayerScore(player2).removeAll(scoreService.getPlayerScore(player2));

        return new ModelAndView("redirect:/2players");
    }

    @RequestMapping("/edit-last-score")
    public ModelAndView editLastScore(@RequestParam(value = "score", required = false) String score, @RequestParam(value = "score2", required = false) String score2) {
        if (!player.getScore().isEmpty() && !player2.getScore().isEmpty()) {
            int item = player.getScore().size() - 1;
            int item2 = player.getScore().size() - 1;
            try {
                scoreService.getPlayerScore(player).set(item, Integer.parseInt(score));
            } catch (Exception e) {
                scoreService.addPlayerScore(player, true);
            }
            try {
                scoreService.getPlayerScore(player2).set(item2, Integer.parseInt(score2));
            } catch (Exception e) {
                scoreService.addPlayerScore(player2, true);
            }
        }

        return new ModelAndView("redirect:/2players");
    }


}
