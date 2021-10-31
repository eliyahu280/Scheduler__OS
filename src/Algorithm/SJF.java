package Algorithm;

import Scheduler.CPU;
import Task.Task;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// non-preemptive algorithm
public class SJF extends Algorithm {

    AtomicInteger time, esCount;
    ExecutorService es;
    List<Task> activePcs;// prs that arrives in the same time and now it's their turn
    PriorityQueue<Task> pq;
    int sum;
    boolean[] flag;
    volatile boolean stop;
    Runnable stopTask = () -> stop = true;

    public SJF(ArrayList<Task> tasks, CPU cpu) {
        super(tasks, cpu);
        sum = 0;
        time = new AtomicInteger(0);
        flag = new boolean[tasks.size()];
        activePcs = new ArrayList<Task>(); //list of arrived processes
        esCount = new AtomicInteger(cpu.getNumOfCores());
        es = cpu.es;
/*        pq = new PriorityQueue<Task>(1000, (t1, t2) -> {
            if (t1.getBurstTime() != t2.getBurstTime())
                return (t1.getBurstTime() - t2.getBurstTime());
            return t2.getBurstTime() - t1.getBurstTime();

        });*/
    }


    @Override
    public void execute() {

        Collections.sort(tasks, (o1, o2) -> { // sorting the tasks according to arrival time
                    if (o1.getPcb().getArrivalTime() != o2.getPcb().getArrivalTime())
                        return (o1.getPcb().getArrivalTime() - o2.getPcb().getArrivalTime());
                    return o2.getPcb().getArrivalTime() - o1.getPcb().getArrivalTime();
                }
        );

        tasks.forEach(t -> System.out.println(t.getPcb().getArrivalTime()));

        System.out.println("-----------------------------------------------");

        for (int i = 0; i < tasks.size(); ) {
            activePcs.add(tasks.get(i));
            i++;

            while (i < tasks.size() && tasks.get(i).getPcb().getArrivalTime() == activePcs.get(0).getPcb().getArrivalTime()) {
                activePcs.add(tasks.get(i));
                i++;
            }

            Collections.sort(activePcs, (o1, o2) -> { // sorting the tasks according to arrival time
                        if (o1.getBurstTime() != o2.getBurstTime())
                            return (o1.getBurstTime() - o2.getBurstTime());
                        return o2.getBurstTime() - o1.getBurstTime();
                    }
            );

            for (Task t : activePcs) {
                if (esCount.get() == 0) {
                    time.incrementAndGet();
                    esCount.set(cpu.getNumOfCores());
                }

                //update timing of current pcs
                System.out.println("time is :"+ time.get());
                t.setStartTime(time.get());
                t.getPcb().setFinishTime(t.getStartTime() + t.getBurstTime());
                es.execute(() -> {
                    try {
                        runOneTask(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                esCount.decrementAndGet();

            }
            sum+=  activePcs.get(activePcs.size() - 1).getPcb().getFinishTime() - activePcs.get(activePcs.size() - 1).getStartTime();
            activePcs.clear();
            esCount.set(cpu.getNumOfCores());
            time.incrementAndGet();

        }

    //    System.out.println("FINISH LOOP");
        es.execute(stopTask);
        es.shutdown();
        try {
            // wait until es is done (or max minutes)
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
/*

        for (Task t : tasks) {
          //  sum += t.getPcb().getFinishTime() - t.getStartTime();
        }*/

        double meanTurnAround = sum / (double) tasks.size();
        System.out.println("meanTurnAround time is : " + meanTurnAround);
        System.out.println("sum time is : " + sum);

    }

    public void runOneTask(Task task) {

        System.out.println("inside runOneTask");
       /* task.setStartTime(time.get());
        System.out.println("time inside is : "+time.get());
        task.getPcb().setFinishTime(task.getStartTime() + task.getBurstTime());*/
        task.run();


    }
}


