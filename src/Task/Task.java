package Task;


import Scheduler.PCB;

public abstract class Task implements Process {

    protected
    int memory; //maybe a constant number for each type of process
    int processID; //get randomly
    int burstTime; //maybe to do it static, also a constant number for each type
    PCB pcb;
    //for decorator
    protected
    Process ps;

        public
    Task(Process ps) {
          //  super(ps);
            pcb = new PCB();


    }
    public abstract void run();

}
