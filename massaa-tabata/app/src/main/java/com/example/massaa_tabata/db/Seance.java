package com.example.massaa_tabata.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.massaa_tabata.data.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "seance")
public class Seance implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private int prepTime;
    private int nbSequences;
    private int nbCycles;
    private int workTime;
    private int restTime;
    private int longRestTime;

    /*
     * Getters and Setters
     * */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getNbSequences() {
        return nbSequences;
    }

    public void setNbSequences(int nbSequences) {
        this.nbSequences = nbSequences;
    }

    public int getLongRestTime() {
        return longRestTime;
    }

    public void setLongRestTime(int longRestTime) {
        this.longRestTime = longRestTime;
    }

    public int getNbCycles() {
        return nbCycles;
    }

    public void setNbCycles(int nbCycles) {
        this.nbCycles = nbCycles;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public List<Pair<String, Integer>> getRoutine() {
        List<Pair<String, Integer>> timeList = new ArrayList<>();

        timeList.add(new Pair<>("Préparation", getPrepTime()));
        for (int i = 0; i < getNbSequences(); i++) {
            for (int j = 0; j < getNbCycles() - 1; j++) {
                timeList.add(new Pair<>("Travail", getWorkTime()));
                timeList.add(new Pair<>("Repos", getRestTime()));
            }
            timeList.add(new Pair<>("Travail", getWorkTime()));
            timeList.add(new Pair<>("Repos long", getLongRestTime()));
        }

        return timeList;
    }
}