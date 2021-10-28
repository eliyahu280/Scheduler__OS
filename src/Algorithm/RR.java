package Algorithm;

import Scheduler.CPU;
import Task.Task;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class RR extends Algorithm{

    // it is not accurate to use Timer since that RR use real time for each process and at the end of this time
    // it goes to the next process and put the previous in ready queue,
    // only after all the processes were given there time (quantum time) the cycle will start again
    // here each process will get time and at the end of it if the working on the task wasn't complete
    // we'll allocate more time for the next time of the cycle

    ExecutorService es;
    Timer timer;
    //static int sumTime = 0;//finish = 0;
    static AtomicInteger finish = new AtomicInteger();
    static AtomicInteger sumTime = new AtomicInteger(0);

    int sum = 0, time = 0, timeQuantum;

    Queue<Task> readyQ = new LinkedList<Task>();
    boolean[] flag = new boolean[tasks.size()];
    AtomicInteger countRemain , pcsRemainTime;
    Task workingPcs;

    public RR(ArrayList<Task> tasks, CPU cpu, int timeQuantum) {
        super(tasks, cpu);
        es = cpu.es;
        finish.set(0);
        countRemain = new AtomicInteger(0);
        pcsRemainTime = new AtomicInteger(0);
        this.timeQuantum = timeQuantum;
        this .workingPcs = null;
        timer = new Timer();
    }

    @Override
    public void execute() {

        Collections.sort(tasks,(o1, o2)->{ // sorting the tasks by arrival time
            if(o1.getPcb().getArrivalTime() != o2.getPcb().getArrivalTime())
                return (o1.getPcb().getArrivalTime() - o2.getPcb().getArrivalTime());
            return o2.getPcb().getArrivalTime() - o1.getPcb().getArrivalTime();}
        );

        // init time left in each process:

        for(int i = 0 ; i < tasks.size() ; i++)
        {
            tasks.get(i).getPcb().setTimeLeft(tasks.get(i).getBurstTime());
            flag[i] = false;
        }

        while(countRemain.get() != tasks.size() || !readyQ.isEmpty() || workingPcs != null){

            for(int i = 0; i < tasks.size() ; i++)
            {
                if(flag[i] == false && tasks.get(i).getPcb().getArrivalTime() <= time) // add the processes that arrived now to the ready queue
                {
                    readyQ.add(tasks.get(i));
                    flag[i]=true;
                }
            }

            // pcsRemainTime == 0 no process is being executed
            if(!readyQ.isEmpty() || workingPcs != null)
            {
                if(workingPcs == null)
                {
                    workingPcs  = readyQ.remove();
                    pcsRemainTime.set(timeQuantum);

                }
                if(workingPcs.getPcb().getTimeLeft() == 0) // no more time for the curr pcs
                {
                    workingPcs.getPcb().setTimeLeft(time);
                    workingPcs  = null;
                    countRemain.incrementAndGet();
                    continue;
                }

                else  // there is still time for the curr pcs
                {
                    workingPcs.getPcb().setTimeLeft(workingPcs.getPcb().getTimeLeft()-1);

                    pcsRemainTime.decrementAndGet();

                    // process is done
                    if(workingPcs.getPcb().getTimeLeft() == 0)
                    {
                        for(int i = 0 ; i< tasks.size() ; i++)
                            if(tasks.get(i).getProcessID() == workingPcs.getProcessID())
                                tasks.get(i).getPcb().setFinishTime(time + 1);
                        workingPcs = null;
                        countRemain.incrementAndGet();
                    }

                    // time quantum finished and return to queue (pcs not done )
                    else if(pcsRemainTime.get() == 0  && workingPcs.getPcb().getTimeLeft() > 0)
                    {
                        readyQ.add(workingPcs ); // inserting the pcs to the rq again
                        workingPcs =null;
                    }
                }
            }




        }



    }


    public void runOneTask(Task task){// need to be return boolean if the task was complete (due to time limitation)

    }

}
