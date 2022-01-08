package com.example.massaa_tabata.data;

/**
 * Custom class Pair : paire activité, durée
 * @param <Label> label de l'activité
 * @param <Time> durée de l'activité
 */
public class Pair<Label, Time> {
    private Label label;
    private Time time;

    public Pair(Label label, Time time) {
        this.label = label;
        this.time = time;
    }

    public Label getLabel() {
        return label;
    }

    public Time getTime() {
        return time;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
