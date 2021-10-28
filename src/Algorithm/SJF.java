package Algorithm;

import Scheduler.CPU;
import Task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// non-preemptive algorithm
public class SJF extends Algorithm {

    static AtomicInteger time,esCount;
    ExecutorService es;
    List<Task> activePcs;// prs that arrives in the same time and now it's their turn
    Queue<Task>readyQueue;
    PriorityBlockingQueue<Task>pq;
    int sum;
    boolean[] flag;
    Thread run;
    volatile boolean stop;
    Runnable stopTask=()->stop=true;
    Runnable runnable;

    public SJF(ArrayList<Task> tasks, CPU cpu) {
        super(tasks, cpu);
        sum = 0;
        time = new AtomicInteger(0);
        flag = new boolean[tasks.size()];
        activePcs = new ArrayList<Task>(); //list of arrived processes
        pq = new PriorityBlockingQueue<Task>(1000,(t1,t2)-> {
            if (t1.getBurstTime() != t2.getBurstTime())
                return (t1.getBurstTime() - t2.getBurstTime());
            return t2.getBurstTime() - t1.getBurstTime();

        });
        esCount = new AtomicInteger(cpu.getNumOfCores());
        es = cpu.es;
    }


    @Override
    public void execute() {


        Collections.sort(tasks, (o1, o2) -> { // sorting the tasks according to arrival time
                    if (o1.getPcb().getArrivalTime() != o2.getPcb().getArrivalTime())
                        return (o1.getPcb().getArrivalTime() - o2.getPcb().getArrivalTime());
                    return o2.getPcb().getArrivalTime() - o1.getPcb().getArrivalTime();
                }
        );


         //   while(!stop){


            tasks.forEach(t-> System.out.println(t.getPcb().getArrivalTime()));

                for(int i = 0 ; i < tasks.size() ; i ++)  // need to add increase for time
                {
                    time.set(cpu.getNumOfCores());

                    tasks.get(i).setStartTime(time.get());

                    pq.add(tasks.get(i));

                    time.incrementAndGet();
                }

                System.out.println("----------------------------------");
                pq.forEach(p->{es.execute(()->runOneTask(p));});
                pq.forEach(p-> System.out.println(p.getBurstTime()));
                es.execute(stopTask);
      //      }

       // es.execute(stopTask);

        System.out.println("FINISH LOOP");


        // after inserting all the tasks to the Executor
        es.shutdown();

        try {
            // wait until es is done (or max minutes)
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
            //1run.join();
        } catch (InterruptedException e) {
            System.out.println("exception 2");
            e.printStackTrace();
        }


        for (Task t : tasks) {
            sum += t.getPcb().getFinishTime() - t.getPcb().getArrivalTime();
        }
        double meanTurnAround = sum / (double) tasks.size();

        System.out.println("meanTurnAround time is : " + meanTurnAround);
        System.out.println("sum time is : " + sum);

    }


    public void runOneTask(Task task) {

         task.run();

        System.out.println("inside runOneTask");
        if (!activePcs.isEmpty()) {

                for (int j = 0; j < tasks.size(); j++) {

                    if (tasks.get(j).getProcessID() == task.getProcessID()) {

                        tasks.get(j).getPcb().setFinishTime(tasks.get(j).getStartTime() + tasks.get(j).getBurstTime());
                        System.out.println("started time" + tasks.get(j).getStartTime());
                        System.out.println("burst time" +tasks.get(j).getBurstTime());
                     //   activePcs.remove(task);
                        //countRemain.incrementAndGet();
                        break;
                    }
                  //  System.out.println("countRemain inside runOneTask:"+ startTime.get());
                    System.out.println("activePcs inside runOneTask:" + activePcs.size());
                }
               /* Iterator<Task> iter = tasks.iterator();
                Iterator<Task> iterActive = activePcs.iterator();
                while (iter.hasNext()) {

                    Task ts = iterActive.next();

                    if (ts.getProcessID() == task.getProcessID()) {
                        iter.getPcb().setFinishTime(time.get() + 1);
                        iter.remove();
                        countRemain.incrementAndGet();
                        break;
                    }
                }*/
           // }
        }
    }


}
