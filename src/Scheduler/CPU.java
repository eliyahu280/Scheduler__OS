package Scheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CPU {

    int numOfCores;
    ExecutorService es;// es = Executors.newFixedThreadPool(numOfCores);

    public CPU(int num){
        numOfCores = num;
        es = Executors.newFixedThreadPool(numOfCores);
    }

}
