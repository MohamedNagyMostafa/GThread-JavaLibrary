
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import java.util.logging.Level;
import java.util.logging.Logger;
import methods.GThread;
import methods.ScheduleGThreadLinked;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open t
/**
 *
 * @author mohamednagy
 */

public class Test {
    public static void main(String[] args) throws InterruptedException {
        GThread[] gs = new GThread[6];
        
        for(int i = 1 ; i < 7 ; i++){
            final int k = i;
            GThread<String> g1 = new GThread<String>() {
                @Override
                public String onProgress() {
                    try {
                    Util.println("wokring gthread " + String.valueOf(k));
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return "1";
                }

                @Override
                public void onFinished(String object) {
                     Util.println("done gthread " + String.valueOf(k));

                }
            };
            gs[i -1] = g1;
        }
        
        ScheduleGThreadLinked scheduleGThreadLinked = new ScheduleGThreadLinked(2, gs);
        
        scheduleGThreadLinked.start();
        Util.println("Go Sleep");
        Thread.sleep(5000);
        Util.println("Wake up and pause");
        scheduleGThreadLinked.stop();
        Util.println("Go Sleep");
        Thread.sleep(8000);
        Util.println("Wake up and start");
        scheduleGThreadLinked.start();
    }
}
