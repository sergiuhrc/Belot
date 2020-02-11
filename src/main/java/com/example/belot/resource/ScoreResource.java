package com.example.belot.resource;

import com.example.belot.entity.Player;

import javax.annotation.Resource;
import java.util.Map;

@Resource
public interface ScoreResource {
    public Map<Player, Integer> getPlayerScore();
    public  void setPlayerScore(Player player, int points,boolean isBolt);
    public  void createPlayer(Player player);
    public  void deletePlayer(Player player);

    void setPlayerScore(Player player, boolean b);
}
