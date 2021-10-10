package Scheduler;

import java.util.Random;

public class PCB {

    private
    int  arrivalTime, finishTime, timeLeft;

    public PCB() {
        int randArrivalTime;

        //generating number between 0 - 10
        Random rand = new Random();
        int min = 0, max = 10;
        randArrivalTime = rand.nextInt(max - min + 1) + min;

        this.arrivalTime = randArrivalTime; //need to take from computer
        this.finishTime = 0; //need to be calculate with the algorithm
        this.timeLeft = 0; //same as above
    }



    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }
}
