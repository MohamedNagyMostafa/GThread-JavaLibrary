
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import java.util.logging.Level;
import java.util.logging.Logger;
import methods.GThread;
import methods.ScheduleGThreadLinked;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mohamednagy
 */
public class Testing {
    public static void main(String[] args) {
        try {
            GThread[] gs = new GThread[5];
            for(int i = 0 ;i < 5 ;i++){
                final int k = i;
                GThread<String> g = new GThread<String>() {
                    @Override
                    public String onProgress() {
                        Util.println("g working " + String.valueOf(k));
                        try {
                            Thread.sleep(5000 + k * 1000);
                        } catch (InterruptedException ex) {
                        }
                        return null;
                    }
                    
                    @Override
                    public void onFinished(String t) {
                        Util.println("finished " + String.valueOf(k));
                    }
                };
                gs[i] = g;
            }
            ScheduleGThreadLinked scheduleGThreadLinked = new ScheduleGThreadLinked(2, gs[0],gs[1],gs[2], gs[3]);
            scheduleGThreadLinked.start();
            Thread.sleep(5500);
            Util.println("pause");
            scheduleGThreadLinked.pause();
            Thread.sleep(6000);
            Util.println("resume");
            scheduleGThreadLinked.resume();
            Util.println("add");
            scheduleGThreadLinked.add(gs[4]);
           
        } catch (InterruptedException ex) {
        }
    }
}
