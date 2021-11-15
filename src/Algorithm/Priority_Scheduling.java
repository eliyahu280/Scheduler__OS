package Algorithm;

import Scheduler.CPU;
import Task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Priority_Scheduling extends Algorithm {

    //needs parameter that will determine who gets the highest priority

    AtomicInteger time, esCount;
    ExecutorService es;
    List<Task> activePcs;// prs that arrives in the same time and now it's their turn
    PriorityBlockingQueue<Task>pq;
    int sum;
    boolean[] flag;
    volatile boolean stop;
    Runnable stopTask = () -> stop = true;
    Thread t;

    public Priority_Scheduling(ArrayList<Task> tasks, CPU cpu) {
        super(tasks, cpu);
        sum = 0;
        time = new AtomicInteger(0);
        flag = new boolean[tasks.size()];
        activePcs = new ArrayList<Task>(); //list of arrived processes
        esCount = new AtomicInteger(cpu.getNumOfCores());
        es = cpu.es;

        // the greater the number more the higher priority
        pq = new PriorityBlockingQueue<Task>(1000, (t1, t2) -> {
            if (t1.getPriority() != t2.getPriority())
                return (t2.getPriority() - t1.getPriority());
            return t1.getPriority() - t2.getPriority();
        });
    }

    @Override
    public void execute() {
        Collections.sort(tasks, (o1, o2) -> { // sorting the tasks according to arrival time
                    if (o1.getPcb().getArrivalTime() != o2.getPcb().getArrivalTime())
                        return (o1.getPcb().getArrivalTime() - o2.getPcb().getArrivalTime());
                    return o2.getPcb().getArrivalTime() - o1.getPcb().getArrivalTime();
                }
        );

        // checking ----------------------------------------------------------------------
               /* System.out.println("checking ");
                tasks.forEach(t -> System.out.println(t.getPcb().getArrivalTime()));
                System.out.println("priority before queue");
                for(Task t : tasks)
                {
                    System.out.println(t.getPriority());
                    pq.put(t);
                }
                System.out.println("------------------------------------------------------");

                for (Task t : pq)
                {
                    try {
                        Task a = pq.take();
                        System.out.println(a.getPriority());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("checking end");*/
        // checking end----------------------------------------------------------------------

        t = new Thread(()->{while (!stop){
            es.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Task task = pq.take();
                        task.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }});t.start();

        for(Task t : tasks)
        {
            //System.out.println(t.getPriority());
            pq.put(t);
        }

        es.execute(stopTask);
        es.shutdown();
        try {
            // wait until es is done (or max minutes)
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double meanTurnAround = sum / (double) tasks.size();
        System.out.println("meanTurnAround time is : " + meanTurnAround);
        System.out.println("sum time is : " + sum);


    }


}
