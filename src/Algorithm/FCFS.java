package Algorithm;

import Scheduler.CPU;
import Task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class FCFS extends Algorithm{

    ExecutorService es;
    //static int sumTime = 0;//finish = 0;
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
      /*  Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.pcb.getArrivalTime() != o2.pcb.getArrivalTime())
                    return (o1.pcb.getArrivalTime() - o2.pcb.getArrivalTime());
                return o2.pcb.getArrivalTime() - o1.pcb.getArrivalTime();
            }
        });*/
        Collections.sort(tasks,(o1,o2)->{ // sorting the tasks according to arrival time
            if(o1.getPcb().getArrivalTime() != o2.getPcb().getArrivalTime())
                    return (o1.getPcb().getArrivalTime() - o2.getPcb().getArrivalTime());
                    return o2.getPcb().getArrivalTime() - o1.getPcb().getArrivalTime();}
                );
        System.out.println("after sort");
        tasks.forEach(task -> System.out.println(task.getPcb().getArrivalTime()));
        System.out.println("st");

     /* int finish = 0, sumTime = 0;*/


        //es.execute(()->runOneTask(tasks.get(0)));

        // running the tasks
        for(Task t : tasks){
            es.execute(()->runOneTask(t));
            es.execute(()->{
                try
                {
                    Thread.sleep(2000);
                    System.out.println("first execute");
                } catch (InterruptedException e) {e.printStackTrace();}});

            //

            es.execute(()->{
                try
                {
                    Thread.sleep(500);
                    System.out.println("second execute");
                } catch (InterruptedException e) {e.printStackTrace();}});

            es.execute(()-> System.out.println("third execute"));

        }


        System.out.println("sum time is : " + sumTime);
    }


    public void runOneTask(Task task){
        if (task.getPcb().getArrivalTime() > finish.get()) //Task already arrived
        {

            task.run(); // do the reading or Task of the specific task

            int finishT = task.getPcb().getFinishTime() + task.getPcb().getArrivalTime(); // maybe to do it with the real time it took
            task.getPcb().setFinishTime(finishT);//finish time = arrival + burt
            finish.set(task.getPcb().getFinishTime());

        }
	else // the first Task (one of them)
        {
            task.run();
            task.getPcb().setFinishTime(finish.get() + task.getBurstTime());
            finish.set(task.getPcb().getFinishTime());
        }
	    sumTime.set(task.getPcb().getFinishTime() - task.getPcb().getArrivalTime());

    }

}
