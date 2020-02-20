package com.example.belot.resource;

import com.example.belot.entity.Player;

import javax.annotation.Resource;
import java.util.Map;

@Resource
public interface ScoreResource {
    Map<Player, Integer> getPlayerScore();

    void addPlayerScore(Player player, int points, boolean isBolt);

    void createPlayer(Player player);

    void deletePlayer(Player player);

    void addPlayerScore(Player player, boolean b);
//    void editPlayerScore(Player player, boolean b);
}
