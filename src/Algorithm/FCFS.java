package Algorithm;

import Scheduler.CPU;
import Task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FCFS extends Algorithm{

    public FCFS(ArrayList<Task> tasks, CPU cpu) {
        super(tasks, cpu);
        execute();
    }

    @Override
    public void execute() {
        //sort the Task according to arrival time
   /*     Collections.sort(tasks,(t1,t2)->{
          int res =  t1.pcb.getArrivalTime() - t2.pcb.getArrivalTime();
          if(res == 0)
              return t1.pcb.getArrivalTime();
             // res = t1.pcb.getArrivalTime();
          else if(res > 0)
              return t2.pcb.getArrivalTime();

          else
          return t1.pcb.getArrivalTime();

             // res = t1.pcb
        });
*/
        System.out.println("before sort");
        System.out.println(tasks.size());
        tasks.forEach(task -> System.out.println(task.pcb.getArrivalTime()));
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.pcb.getArrivalTime() != o2.pcb.getArrivalTime())
                    return (o1.pcb.getArrivalTime() - o2.pcb.getArrivalTime());
                return o2.pcb.getArrivalTime() - o1.pcb.getArrivalTime();
            }
        });
        System.out.println("after sort");
        tasks.forEach(task -> System.out.println(task.pcb.getArrivalTime()));
        System.out.println("st");


    }
}
