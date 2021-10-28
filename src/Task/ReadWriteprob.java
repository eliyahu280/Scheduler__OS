package Task;

public class ReadWriteprob extends Task{

    public ReadWriteprob(Process p) {
        super(p);
        this.setBurstTime(2); // needs to be some constant - i can measure it before and then define it
    }

    @Override
    public void run() {
        System.out.println("inside run of Read write lala");

        try {
            Thread.sleep(10);
           // System.out.println("inside run of Read write");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
