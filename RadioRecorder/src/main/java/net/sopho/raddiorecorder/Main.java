/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sopho.raddiorecorder;

import org.quartz.SchedulerException;

/**
 *
 * @author Dardenne
 */
public class Main {

    public Main() {
    }
    
    public static void main(String args[]) throws SchedulerException {
        Starter starter = new Starter();
        starter.run();
    
}
    
    
    
}
