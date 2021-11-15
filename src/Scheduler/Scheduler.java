package Scheduler;

import Algorithm.*;
import Task.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

//Singleton
public class Scheduler {

    static Algorithm alg;
    static CPU cpu;
    static Queue<Task> queue;
    static int available_cores, cores_in_use;
    static Object object;

    private static volatile Scheduler scheduler_instance;

    public static Scheduler getInstance(ArrayList<Task> t, int numCores, String alg) {
        Scheduler localRef = scheduler_instance;

        if (localRef == null) {
            synchronized (Scheduler.class) {// was (this)
                localRef = scheduler_instance;
                if (localRef == null) {
                    scheduler_instance = localRef = new Scheduler();
                    scheduler_instance.cpu = new CPU(numCores);
                    Class<?> c = null;
                    try {
                        c = Class.forName("Algorithm." + alg);
                        Constructor<?> cons = c.getConstructor(ArrayList.class, CPU.class);
                        object = cons.newInstance(t, cpu);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        localRef.activate();
        return localRef;
    }

    public void activate() {
       /* FCFS f = new FCFS(tasks, cpu);
        f.execute();*/
        //   SJF sjf = new SJF(tasks, cpu);
        //sjf.execute();
        //alg = new SJF(tasks, cpu);
        alg = (Algorithm) object;
        alg.execute();
    }


}
//   https://medium.com/@kevalpatel2106/how-to-make-the-perfect-singleton-de6b951dfdb0