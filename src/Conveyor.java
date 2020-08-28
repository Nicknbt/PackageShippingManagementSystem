/*
Name: Nickolas Trevino
Course: CNT 4714 Spring 2020
Assignment title: Project 2 – Multi-threaded programming in Java
Date: February 12, 2020
Class: <Conveyor>
*/

import java.util.concurrent.locks.ReentrantLock;

public class Conveyor {
    private int curNum;
    public ReentrantLock aLock = new ReentrantLock();

    public Conveyor(int n){
        this.curNum = n;
    }

    public void inConnection(int n){
        System.out.println("Station " + n + ": successfully moves packages on conveyor " + this.curNum);
    }

    public void outConnection(int n){
        System.out.println("Station " + n + ": successfully moves packages on conveyor " + this.curNum);
    }
}
