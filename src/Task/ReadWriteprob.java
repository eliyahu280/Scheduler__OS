package Task;

import java.io.*;
import java.util.Random;
import java.util.Scanner;


public class ReadWriteprob extends Task {

    public ReadWriteprob(Process p) {
        super(p);
        this.setBurstTime(2); // needs to be some constant - i can measure it before and then define it

        Random rand = new Random();
        int min = 0, max = 10, res;
        res = rand.nextInt(max - min + 1) + min;
        this.setPriority(res);
    }

    @Override
    public void run() {
        Scanner in;
        PrintWriter out;

        try {
            synchronized (this) {
            //    System.out.println("this  " + this.toString());
                out = new PrintWriter(new FileWriter("txtFile"));
                out.println("txt written in file");
                in = new Scanner(new FileReader("txtFile"));

                out.close();
                while (in.hasNext())
                    System.out.println(in.next());

                in.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
