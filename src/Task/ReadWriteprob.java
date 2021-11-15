package Task;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Random;
import java.net.URL;
import java.util.Scanner;



public class ReadWriteprob extends Task{

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
        System.out.println("inside run of Read write lala");
        Scanner in;
        PrintWriter out;

        try {
            in=new Scanner(new FileReader("txtFile"));
            out=new PrintWriter(new FileWriter("txtFile"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



      /*  try {
            Thread.sleep(10);
           // System.out.println("inside run of Read write");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


}
