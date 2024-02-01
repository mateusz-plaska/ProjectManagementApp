package com.mplaska.projectmanagementapp.model.database.datastorage;


import com.mplaska.projectmanagementapp.model.observerprincipleinterface.Observable;
import com.mplaska.projectmanagementapp.model.observerprincipleinterface.Observer;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class ListStorage<TaskElement> implements Serializable, Observable {
    private ArrayList<TaskElement> list = new ArrayList<>();
    private transient ArrayList<Observer> observersList = new ArrayList<>();

    public ArrayList<TaskElement> getList() {
        return list;
    }

    public void add(TaskElement element) {
        list.add(element);
        notifyObservers();
    }

    public void edit(int index, TaskElement element) {
        list.set(index, element);
        notifyObservers();
    }

    public void remove(int rowIndex) {
        list.remove(rowIndex);
        notifyObservers();
    }

    public void setList(ArrayList<TaskElement> list) {
        this.list = list;
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observersList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observersList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observersList.forEach(Observer::update);
    }

    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        observersList = new ArrayList<>();
    }
}
