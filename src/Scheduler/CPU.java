package Scheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CPU {

    int numOfCores;
    public ExecutorService es;// es = Executors.newFixedThreadPool(numOfCores);
    int timeQuantum;

    public CPU(int num){
        numOfCores = num;
        this.timeQuantum = 2;
        es = Executors.newFixedThreadPool(numOfCores);
    }

    public int getNumOfCores() {
        return numOfCores;
    }
    public int getTimeQuantum() {
        return timeQuantum;
    }
}
