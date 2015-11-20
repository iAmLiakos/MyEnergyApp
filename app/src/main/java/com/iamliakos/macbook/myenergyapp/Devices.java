package com.iamliakos.macbook.myenergyapp;

import java.util.concurrent.atomic.AtomicLong;
/**--Class Devices
 *---Created by macbook on 11/11/15.
 */
public class Devices {

    //characteristics
    //int id;
    String name;
    String power;

    //costructors
    public Devices(){
    }
    //constructor2
    public Devices(String name, String power){
        //this.id = id;
        this.name = name;
        this.power = power;
    }
    //constructor3
    public Devices(String name){
        this.name = name;
    }

    //getters and setters

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPower() {
        return this.power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    //devices@xxxxxxx -> name+power
    public String toString() {
        return this.name + " " +this.power;
    }
}
