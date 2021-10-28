package Task;


import Scheduler.PCB;

public abstract class Task implements Process {

    protected
    int memory; //maybe a constant number for each type of process
    int processID; //get randomly
    int burstTime, startTime; //maybe to do it static, also a constant number for each type
    PCB pcb;
    //for decorator
    protected
    Process ps;

        public
    Task(Process ps) {
          //  super(ps);
            pcb = new PCB();
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public PCB getPcb() {
        return pcb;
    }

    public void setPcb(PCB pcb) {
        this.pcb = pcb;
    }

    public abstract void run();

}
