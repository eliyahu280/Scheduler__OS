package Task;

public class ReadWriteprob extends Task{

    public ReadWriteprob(Process p) {
        super(p);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("inside run of Read write");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
