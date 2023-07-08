package com.example.jien;

public class Water {
    private static Water instance;
    private int goal;
    private int consumed;

    private Water(int goal) {
        this.goal = goal;
        this.consumed = 0;
    }

    public static Water getInstance() {
        if (instance == null) {
            instance = new Water(0);
        }
        return instance;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getConsumed() {
        return consumed;
    }

    public void addWater(int amount) {
        consumed += amount;
    }

    public int getRemaining() {
        return goal - consumed;
    }
}

