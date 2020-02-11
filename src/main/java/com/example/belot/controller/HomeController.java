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

    @GetMapping("/")
    public ModelAndView homePage(Model model) {
        player.setName("Sergiu");
        player2.setName("Dima");
        player3.setName("Jora");
        listOfPlayers.add(player);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);
        if (scoreService.getPlayers().isEmpty()) {
            scoreService.createPlayer(player);
            scoreService.createPlayer(player2);
        }
        //get last 4 elements from player score
        List<?> tail = player.getScore().subList(Math.max(player.getScore().size() - 4, 0), player.getScore().size());
        List<?> tail2 = player2.getScore().subList(Math.max(player.getScore().size() - 4, 0), player.getScore().size());


        System.out.println(scoreService.getPlayerScore(player));
        System.out.println(scoreService.getPlayerScore(player2));
        model.addAttribute("player_score", tail);
        model.addAttribute("player_score2", tail2);
        model.addAttribute("player_name", player.getName());
        model.addAttribute("player_name_second", player2.getName());

        System.out.println(scoreService.getPlayers());
        if (!scoreService.getPlayerScore(player).isEmpty()) {
            if (scoreService.getWinner(scoreService.getPlayers()) != null) {
                System.out.println("Winner " + scoreService.getWinner(scoreService.getPlayers()));
                if (scoreService.getWinner(scoreService.getPlayers()) != null) {
                    setWinner(String.valueOf(scoreService.getWinner(scoreService.getPlayers()).getName()));
                    return new ModelAndView("redirect:/win-page");
                }
            }
        }
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

            scoreService.setPlayerScore(player, Integer.parseInt(score), false);
            System.out.println("is not Exception");
        } catch (Exception e) {
            System.out.println("exception");
            scoreService.setPlayerScore(player, true);
        }
        try {
            scoreService.setPlayerScore(player2, Integer.parseInt(score2), false);
            System.out.println("is not Exception");
        } catch (Exception e) {
            System.out.println("exception");
            scoreService.setPlayerScore(player2, true);
        }

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/delete-last-score")
    public ModelAndView deleteLastScore() {
        if (!player.getScore().isEmpty()) {
            scoreService.getPlayerScore(player).remove(player.getScore().size() - 1);
            scoreService.getPlayerScore(player2).remove(player2.getScore().size() - 1);
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/new-game")
    public ModelAndView newGameStart() {
        if (!player.getScore().isEmpty()) {
            scoreService.getPlayerScore(player).removeAll(player.getScore());
            scoreService.getPlayerScore(player2).removeAll(player2.getScore());
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/edit-last-score")
    public ModelAndView editLastScore(@RequestParam(value = "score", required = false) int score, @RequestParam(value = "score2", required = false) int score2) {
        if (!player.getScore().isEmpty()) {
            int item = player.getScore().size() - 1;
            int item2 = player.getScore().size() - 1;
            int first = scoreService.getPlayerScore(player).get(player.getScore().size() - 2);
            int second = scoreService.getPlayerScore(player2).get(player2.getScore().size() - 2);
            scoreService.getPlayerScore(player).set(item, first + score);
            scoreService.getPlayerScore(player2).set(item2, second + score2);
        }

        return new ModelAndView("redirect:/");
    }


}
