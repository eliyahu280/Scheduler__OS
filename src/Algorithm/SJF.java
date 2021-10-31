package Algorithm;

import Scheduler.CPU;
import Task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// non-preemptive algorithm
public class SJF extends Algorithm {

    static AtomicInteger time, esCount;
    ExecutorService es;
    List<Task> activePcs;// prs that arrives in the same time and now it's their turn
    Queue<Task> readyQueue;
    PriorityBlockingQueue<Task> pq;
    int sum;
    boolean[] flag;
    Thread run;
    volatile boolean stop;
    Runnable stopTask = () -> stop = true;
    Runnable runnable;

    public SJF(ArrayList<Task> tasks, CPU cpu) {
        super(tasks, cpu);
        sum = 0;
        time = new AtomicInteger(0);
        flag = new boolean[tasks.size()];
        activePcs = new ArrayList<Task>(); //list of arrived processes
        pq = new PriorityBlockingQueue<Task>(1000, (t1, t2) -> {
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





        tasks.forEach(t -> System.out.println(t.getPcb().getArrivalTime()));

        for (int i = 0; i < tasks.size(); i++)  // need to add increase for time
        {

           // tasks.get(i).setStartTime(time.get());
            pq.add(tasks.get(i));

          //  time.incrementAndGet();
        }
        pq.forEach(p -> System.out.println(p.getBurstTime()));



        time.set(0);
        System.out.println("----------------------------------");

        for(int i = 0 ; i < tasks.size() ; i++){
            if(!pq.isEmpty()){
                if(pq.peek().getPcb().getArrivalTime() == i)
                {
                    if(esCount.get() == 0){
                        time.incrementAndGet();
                        esCount.set(cpu.getNumOfCores());
                    }
                    es.execute(() -> {
                        try {
                            runOneTask(pq.take());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    esCount.decrementAndGet();
                }
            }
            time.incrementAndGet();
        }




        /*pq.forEach(p -> {
            if(esCount.get() == 0){
                time.incrementAndGet();
                esCount.set(cpu.getNumOfCores());
            }
            es.execute(() -> {
                try {
                    runOneTask(pq.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            esCount.decrementAndGet();
        });*/

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

        task.setStartTime(time.get());
        task.getPcb().setFinishTime(task.getStartTime() + task.getBurstTime());

        }
    }


