package com.example.belot.entity;

public class Game {
     private   int points;

    public Game(int points) {
        this.points = points;
    }

    public  int getPoints() {
        return points;
    }

    public  void setPoints(int points) {
       this.points = points;
    }

    @Override
    public String toString() {
        return "Game{" +
                "points=" + points +
                '}';
    }
}
