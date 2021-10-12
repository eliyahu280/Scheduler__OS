package Commands;

import Scheduler.Scheduler;
import Task.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                        for(int i = 0; i< amount; i++){

                            sharedState.alltasks.add(new ReadWriteprob(new ConcreteTask()));
                            sharedState.numOfProcesses += amount;
                        }
                        break;
                    }
                    case 2: {

                        System.out.println("please enter amount ");
                        // myObj = new Scanner(System.in);
                        amount = myObj.nextInt();
                        for(int i = 0; i< amount; i++) {
                            sharedState.alltasks.add(new Bounded_Buffer(new ConcreteTask()));

                            sharedState.numOfProcesses += amount;
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("please enter amount ");
                        // myObj = new Scanner(System.in);
                        amount = myObj.nextInt();
                        for(int i = 0; i< amount; i++) {
                            sharedState.alltasks.add(new Dining_Philosppers(new ConcreteTask()));
                            sharedState.numOfProcesses += amount;
                        }
                        break;
                    }
                }
                System.out.println("\nwould you like to add another Process?" + "\n Y" + "N\n");

                Scanner word = new Scanner(System.in);
                w = word.nextLine();

                b = (w.equals("Y") || w.equals("y")) ? true : false;
                //word.close();
              //  myObj.close();
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
            System.out.println("kfj");
            sharedState.scheduler= Scheduler.getInstance(sharedState.alltasks,sharedState.numCore,"FCFS");
            //sharedState.scheduler.activate();
          //  sharedState.scheduler.activate(sharedState.alltasks,sharedState.numCore,"FCFS");
            //Scheduler c = Scheduler.getInstance();

            // sharedState.scheduler
        }
    }

    public class DisplayResult extends Command {

        public DisplayResult() {
            super("5. display result\n");
        }

        @Override
        public void execute() {
        }
    }
    public class ChangeALG extends Command {

        public ChangeALG() {
            super("6. change algorithm");
        }

        @Override
        public void execute() {

        }
    }

    public class Exit extends Command {

        public Exit() {
            super("7. Exit \n");
        }

        @Override
        public void execute() {

        }
    }
}
