package Algorithm;

import Scheduler.CPU;
import Task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FCFS extends Algorithm {

    ExecutorService es;
    static AtomicInteger finish = new AtomicInteger();
    static AtomicInteger sumTime = new AtomicInteger(0);

    public FCFS(ArrayList<Task> tasks, CPU cpu) {
        super(tasks, cpu);
        es = cpu.es;
        finish.set(0);
        /*execute();*/
    }

    @Override
    public void execute() {
        //sort the Task according to arrival time

        System.out.println("before sort");
        System.out.println(tasks.size());
        tasks.forEach(task -> System.out.println(task.getPcb().getArrivalTime()));

        Collections.sort(tasks, (o1, o2) -> { // sorting the tasks according to arrival time
                    if (o1.getPcb().getArrivalTime() != o2.getPcb().getArrivalTime())
                        return (o1.getPcb().getArrivalTime() - o2.getPcb().getArrivalTime());
                    return o2.getPcb().getArrivalTime() - o1.getPcb().getArrivalTime();
                }
        );
        System.out.println("after sort");
        tasks.forEach(task -> System.out.println(task.getPcb().getArrivalTime()));
        System.out.println("st");


        for (Task t : tasks) {
            es.execute(() -> runOneTask(t));
            es.execute(() -> {
                try {
                    Thread.sleep(2000);
                    System.out.println("first execute");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            es.execute(() -> {
                try {
                    Thread.sleep(500);
                    System.out.println("second execute");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            es.execute(() -> System.out.println("third execute"));
        }

        es.shutdown();
        try {
            // wait until es is done (or max minutes)
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(es.isTerminated());
        System.out.println("sum time is : " + sumTime);
    }


    public void runOneTask(Task task) {
        if (task.getPcb().getArrivalTime() > finish.get()) //Task already arrived
        {

            task.run(); // do the reading or Task of the specific task

            task.getPcb().setFinishTime(task.getPcb().getArrivalTime() + task.getBurstTime()); // finish = arrival + burst

            int finishT = task.getPcb().getFinishTime() + task.getPcb().getArrivalTime();  // finish from the "beginning measurement of time and not from arrival"

            task.getPcb().setFinishTime(finishT);//finish time = arrival + burt

            finish.set(task.getPcb().getFinishTime());

        } else // the first Task (one of them)
        {
            task.run();

            task.getPcb().setFinishTime(task.getPcb().getArrivalTime() + task.getBurstTime()); // finish = arrival + burst

            task.getPcb().setFinishTime(finish.get() + task.getBurstTime());

            finish.set(task.getPcb().getFinishTime());
        }
        sumTime.set(task.getPcb().getFinishTime() - task.getPcb().getArrivalTime());

    }

}
