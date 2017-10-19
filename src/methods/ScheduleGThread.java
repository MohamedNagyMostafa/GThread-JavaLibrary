package methods;
/*
 * Copyright [2017] Mohamed Nagy Mostafa Mohamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * GThread Class advanced version for threads, You can handle with upcoming object 
 * which come after thread processes are done on object.
 * 
 * @author mohamednagy
 * @param <T>   Type of object which you're going to get back after GThread done
 *              it's work completely
 */
import handlers.GShedule;
import exceptions.ScheduleGThreadException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ScheduleGThread extends GShedule{
    /**
     * ScheduleGThread constructor in which initialize an initial state of
     * schedule.
     * 
     * @param <T>
     * @param workers   Number of workers who are going to execute tasks
     * @param gThread   List of gthreads which must have execute as schedule way
     */
    public <T>ScheduleGThread(int workers, GThread... gThread){
        super(workers, gThread);
        init();
    }
    /**
     * Identify gthreads to it's schedule and set initial value for current
     * workers.
     * 
     * @throws ScheduleGThreadException Exception throws when the list of gthreads
     *                                  contain threads which run before or is terminated
     */
    private void init(){
        mCurrentWorker = INTIAL_WORKERS_NUMBER;
    }
    /**
     * Start schedule process. Check if all threads in idle mode or not.
     */
    @Override
    public int start(){
        try {
            checkGThreadValidation();
        } catch (ScheduleGThreadException ex) {
            Logger.getLogger(ScheduleGThread.class.getName()).log(Level.SEVERE, null, ex);
            return GShedule.G_SCHEDULE_START_FAILED;
        }

        mScheduleGThread = new Thread(() -> {
            for (GThread gThread : M_GTHREADS_ARRAY) {
                identifyGThread(gThread);
                gThread.start();
                
                updateWorkers(INCREASE_ONE_WORKER_FROM_WORKERS);
                while(mCurrentWorker >= M_WORKERS_LIMIT);
            }
            
            allTasksExceuted();
        });
        mScheduleGThread.start();
        
        return GShedule.G_SCHEDULE_START_SUCCESSFULLY;
    }
    /**
     * Called when each one of tasks is terminated.
     */
    public void onItemFinished(){
        updateWorkers(DECREASE_ONE_WORKER_FROM_WORKERS);        
    }
    /**
     * Set schedule as a parent to specific gthread.
     * @param gThread   Tasks which are going to execute in schedule
     */
    private void identifyGThread(GThread gThread){
        gThread.setScheduleGThread(this);
   }
    /**
     * insure that there's no task is work.
     */
    private void allTasksExceuted(){
      for(GThread gThread : M_GTHREADS_ARRAY){
          try {
              gThread.join();
          } catch (InterruptedException ex) {
              Logger.getLogger(ScheduleGThread.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
      onScheduleFinished();
    }

    @Override
    protected void checkGThreadValidation() throws ScheduleGThreadException {
        for(GThread gThread : M_GTHREADS_ARRAY){
            switch(gThread.gthreadState()){
                case GThread.G_THREAD_RUNNING:
                    throw new ScheduleGThreadException(ScheduleGThreadException.ALIVE_THREAD_EXCEPTION_MESSAGE);
                case GThread.G_THREAD_TERMINATED:
                    throw new ScheduleGThreadException(ScheduleGThreadException.TERMINATED_THREAD_EXCEPTION_MESSAGE);
            }
        }
    }
    
    /**
     * Called when schedule tasks are finished completely.
     */
    public abstract void onScheduleFinished();
}
