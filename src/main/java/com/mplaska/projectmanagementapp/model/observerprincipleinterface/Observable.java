package com.mplaska.projectmanagementapp.model.observerprincipleinterface;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}


