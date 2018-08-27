package sample;

import java.util.ArrayList;

public class Database {
    private ArrayList<Worker> workers = new ArrayList<>();

    public void add(Worker worker){
        workers.add(worker);
    }

    public void remove(){
        // TODO: 23.08.2018 implement removeWorker function
    }

    public void removeAll(){
        workers.clear();
    }

    public ArrayList<Worker> getAll(){
        return workers;
    }

    public boolean isFreeID(int ID){
        for (Worker worker : workers) {
            if (worker.getID() == ID){
                return false;
            }
        }
        return true;
    }

}
