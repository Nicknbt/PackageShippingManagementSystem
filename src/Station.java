/*
Name: Nickolas Trevino
Course: CNT 4714 Spring 2020
Assignment title: Project 2 – Multi-threaded programming in Java
Date: February 12, 2020
Class: <Station>
*/

import java.util.Random;

public class Station implements Runnable{
    private int maxStations;
    private int numStations;
    private int workLoad;
    private int output;
    private int input;
    Random rand = new Random();
    private int n = rand.nextInt(1500);
    private Conveyor out;
    private Conveyor in;

    public Station(int load, int num, int max){
        this.workLoad = load;
        this.numStations = num;
        this.maxStations = max;
        this.setInputNum();
        this.setOutputNum();

        System.out.println("Station " + numStations + ": Workload set. Station " + this.numStations + " has " + this.workLoad + " package groups to move");
    }

    public void doWork(){
        this.in.inConnection(this.numStations);
        this.out.outConnection(this.numStations);
        this.workLoad = workLoad - 1;
        System.out.println("Station " + this.numStations + ": has " + this.workLoad + " package groups left to move.\n\n");
    }
    public void setInput(Conveyor i){
        this.in = i;
    }
    public void setOutput(Conveyor o){
        this.out = o;
    }
    public int getInput() {
        return input;
    }
    public int getOutput() {
        return output;
    }
    public void setOutputNum(){
        if(this.numStations == 0){
            this.output = this.maxStations - 1;
        }
        else{
            this.output = this.numStations;
        }
        System.out.println("Station " + numStations + ": Out-connection is set to conveyor " + this.output);
    }
    public void setInputNum(){
        if(numStations == 0){
            this.input = 0;
        }
        else{
            this.input = this.numStations - 1;
        }
        System.out.println("Station " + numStations + ": In-connection is set to conveyor " + this.input);
    }

    @Override
    public void run() {
        while(this.workLoad != 0){
            if(in.aLock.tryLock()){
                System.out.println("Station " + this.numStations + ": holds lock on (granted access) to conveyor " + this.input);

                if(out.aLock.tryLock()){
                    System.out.println("Station " + this.numStations + ": holds lock on (granted access) to conveyor " + this.output);
                    doWork();
                }
                else{
                    System.out.println("Station " + this.numStations + ": unlocks (released access) to conveyor " + this.input);
                    in.aLock.unlock();
                    try{
                        Thread.sleep(n);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if(in.aLock.isHeldByCurrentThread()){
                    System.out.println("Station " + this.numStations + ": unlocks (released access) to conveyor " + this.input);
                    in.aLock.unlock();
                }
                if(out.aLock.isHeldByCurrentThread()){
                    System.out.println("Station " + this.numStations + ": unlocks (released access) to conveyor " + this.output);
                    out.aLock.unlock();
                }

                try{
                    Thread.sleep(n);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.print("\n\n* * Station " + numStations + ": Workload successfully completed * *\n\n");
    }
}
