/*
Name: Nickolas Trevino
Course: CNT 4714 Spring 2020
Assignment title: Project 2 – Multi-threaded programming in Java
Date: February 12, 2020
Class: <Routing>
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Routing {
    File theFile = new File("config.txt");
    static int maxRoutingStations = 10;

    public Routing () throws FileNotFoundException {
        fileInfo();
    }

    public void fileInfo () throws FileNotFoundException{
        Scanner sc = new Scanner(theFile);
        int numRoutingStations = sc.nextInt();

        if(numRoutingStations > maxRoutingStations){
            System.out.println("Sorry, you exceeded the amount of Routing Stations. Please try again.\n");
            System.exit(0);
        }

        int [] workLoad = new int[numRoutingStations];
        Conveyor[] allConveyors = new Conveyor[numRoutingStations];
        Station[] allStations = new Station[numRoutingStations];

        for(int i = 0; i < numRoutingStations; i++){
            allConveyors[i] = new Conveyor(i);
        }

        System.out.println("* * * SIMULATION BEGINS * * *\n");
        ExecutorService sim = Executors.newFixedThreadPool(numRoutingStations);

        for(int i = 0; i < numRoutingStations; i++){
            workLoad[i] = sc.nextInt();
            allStations[i] = new Station(workLoad[i], i, numRoutingStations);
            allStations[i].setInput(allConveyors[allStations[i].getInput()]);
            allStations[i].setOutput(allConveyors[allStations[i].getOutput()]);

            sim.execute(allStations[i]);
        }
        sim.shutdown();
    }

    public static void main(String [] args) throws FileNotFoundException {
        Routing theRouting = new Routing();
    }
}
