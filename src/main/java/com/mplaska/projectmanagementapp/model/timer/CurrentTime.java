package com.mplaska.projectmanagementapp.model.timer;


import com.mplaska.projectmanagementapp.model.observerprincipleinterface.Observable;
import com.mplaska.projectmanagementapp.model.observerprincipleinterface.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CurrentTime implements Observable {
    private final ArrayList<Observer> observers = new ArrayList<>();
    public static LocalDate getCurrentSystemTime() {
        return LocalDate.now();
    }

    public void startTimer() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                notifyObservers();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 3600000); //1s - 1000ms 1min - 60 000ms 1h - 3 600 000 ms
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}

