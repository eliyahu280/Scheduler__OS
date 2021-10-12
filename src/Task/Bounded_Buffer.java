package Task;

public class Bounded_Buffer extends Task{

    public Bounded_Buffer(Process p) {
        super(p);
    }

    @Override
    public void run() {


        long time0=System.nanoTime();


        //code in between

        long time1=System.nanoTime();


    this.getPcb().setFinishTime((int) ( time1 - time0));
    }
}
