package Algorithm;

import Scheduler.CPU;
import Task.Task;

import java.util.ArrayList;
import java.util.List;

public abstract class Algorithm {

    protected ArrayList<Task>tasks;
    protected CPU cpu;

    public Algorithm(ArrayList<Task> tasks, CPU cpu) {
        this.tasks = tasks;
        this.cpu = cpu;
    }

    public abstract void execute();

}
