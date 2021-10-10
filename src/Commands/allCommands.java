package Commands;

import Scheduler.Scheduler;
import Task.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class allCommands {

    private static class Shared {
        static String instruction;
        static Scheduler scheduler;
        static int numOfProcesses;
        static ArrayList<Task> alltasks = new ArrayList<Task>();
        static HashMap<Task, Integer> numProcessType;
        static String algName;
        static int numCore;
    }

    private Shared sharedState = new Shared();


    public allCommands() {


    }

    public abstract class Command {

        protected String description;

        public Command(String description) {
            this.description = description;
        }

        public abstract void execute();

    }

    public class CreateProcesses extends Command {


        public CreateProcesses() {
            super("1. create precesses\n");
        }

        @Override
        public void execute() {
            System.out.println("enter Tasks and the amount. when ending press 'e'");

            System.out.println("1. Read Write from text \n" + "2. Bounded Buffer \n" + "3. The dining Philosophers \n");

            int task, amount;
            String w = "e";
            boolean b = true;
            sharedState.alltasks = new ArrayList<Task>();

            while (b) {
                System.out.println("select task");
                Scanner myObj = new Scanner(System.in);
                task = myObj.nextInt();

                switch (task) {
                    case 1: {
                        System.out.println("please enter amount ");
                        // myObj = new Scanner(System.in);
                        amount = myObj.nextInt();
                        sharedState.numProcessType = new HashMap<Task, Integer>();//init
                        // sharedState.numProcessType.put()
                        sharedState.alltasks.add(new ReadWriteprob(new ConcreteTask()));
                        sharedState.numOfProcesses += amount;
                        break;
                    }
                    case 2: {

                        System.out.println("please enter amount ");
                        // myObj = new Scanner(System.in);
                        amount = myObj.nextInt();
                        sharedState.alltasks.add(new Bounded_Buffer(new ConcreteTask()));

                        sharedState.numOfProcesses += amount;

                        break;
                    }
                    case 3: {
                        System.out.println("please enter amount ");
                        // myObj = new Scanner(System.in);
                        amount = myObj.nextInt();
                        sharedState.alltasks.add(new Dining_Philosppers(new ConcreteTask()));
                        sharedState.numOfProcesses += amount;
                        break;
                    }
                }
            }
        }

    }

    public class CreateCPU extends Command {

        public CreateCPU() {
            super("\n2. create cpu\n");
        }

        @Override
        public void execute() {

            System.out.println("num of cores");
            Scanner in = new Scanner(System.in);
            //int num = in.nextInt();
            sharedState.numCore = in.nextInt();

        }
    }
    public class SelectALG extends Command {

        public SelectALG() {
            super("3. select algorithm\n");
        }

        @Override
        public void execute() {
            System.out.println("1. FCFS\n" + "2. SJF\n" + "3. Priority\n" + "4. RR\n" + "5. MultiLevel\n" + "6. MultiLevel_FeedBack\n");
            Scanner s = new Scanner(System.in);
            int algName = s.nextInt();

            // can use factory pattern


        }
    }
    public class Running extends Command {

        public Running() {
            super("4. run the application\n");
        }

        @Override
        public void execute() {

            sharedState.scheduler.getInstance();
        }
    }


}
