package Scheduler;

import Algorithm.Algorithm;
import Task.Task;

import java.util.Queue;

//Singleton
public class Scheduler {

    Algorithm alg;
    CPU cpu;
    Queue<Task> queue;
    int available_cores, cores_in_use;


    private volatile Scheduler scheduler_instance;


    public Scheduler getInstance() {
        Scheduler localRef = scheduler_instance;

        if (localRef == null) {
            synchronized (this) {
                localRef = scheduler_instance;
                if (localRef == null) {
                    scheduler_instance = localRef = new Scheduler();
                }
            }
        }
        return localRef;
    }

}
