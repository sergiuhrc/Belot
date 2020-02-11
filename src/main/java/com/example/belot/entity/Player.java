package com.example.belot.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    String name;
   public List<Integer> score = new ArrayList<>();

    int bolt;

    public Player() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getScore() {
        return score;
    }

    public void setScore(int points) {

        score.add(points);
    }
    public void setNewScore() {

        score.forEach(e->getScore().remove(e));
    }

    public int getBolt() {
        return bolt;
    }

    public void setBolt(int bolt) {
        this.bolt = bolt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player that = (Player) o;
        return getScore() == that.getScore() &&
                getBolt() == that.getBolt() &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getScore(), getBolt());
    }

    @Override
    public String toString() {
        return "PlayerScore{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", bolt=" + bolt +
                '}';
    }
}
