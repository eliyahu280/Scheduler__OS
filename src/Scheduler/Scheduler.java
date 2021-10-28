package Scheduler;

import Algorithm.*;
import Task.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

//Singleton
public class Scheduler {

    static Algorithm alg;
    static CPU cpu;
    static Queue<Task> queue;
    static int available_cores, cores_in_use;

    private static volatile Scheduler scheduler_instance;

    public static Scheduler getInstance(ArrayList<Task> t, int numCores, String namealg) {
        Scheduler localRef = scheduler_instance;

        if (localRef == null) {
            synchronized (Scheduler.class) {// was (this)
                localRef = scheduler_instance;
                if (localRef == null) {

                    scheduler_instance = localRef = new Scheduler();
                    scheduler_instance.cpu = new CPU(numCores);

                }
            }
        }
        localRef.activate(t, cpu, "fcf");
        return localRef;
    }


    public void activate(ArrayList<Task> tasks, CPU coreInfo, String fcfs) {
       /* FCFS f = new FCFS(tasks, cpu);
        f.execute();*/
        SJF sjf = new SJF(tasks, cpu);
        sjf.execute();
    }


}
//   https://medium.com/@kevalpatel2106/how-to-make-the-perfect-singleton-de6b951dfdb0