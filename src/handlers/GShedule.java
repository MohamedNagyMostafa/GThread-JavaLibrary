package handlers;


import exceptions.ScheduleGThreadException;
import methods.GThread;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mohamednagy
 */
public abstract class GShedule {
    // Starting Schedule state value when schedule start successfuly.
    public static final int G_SCHEDULE_START_SUCCESSFULLY = 1;
    // Starting Schedule state value when schedule failed to start.
    public static final int G_SCHEDULE_START_FAILED = -1;
    // Number of worker when schedule is created.
    protected static final int INTIAL_WORKERS_NUMBER = 0;
    // Decrease only one worker from the current workers.
    protected static final int DECREASE_ONE_WORKER_FROM_WORKERS = -1;
    // Increase only one worker from the current workers.
    protected static final int INCREASE_ONE_WORKER_FROM_WORKERS = 1;
    // Array of gthread which must have excuted as schedule way.
    protected final GThread[] M_GTHREADS_ARRAY;
    // Limitation number of workers which execute schedule tasks
    // at the same time.
    protected final int M_WORKERS_LIMIT;
    // Original thread of Schedule gthread.
    protected Thread mScheduleGThread; 
    // Number of current workers which execute tasks.
    protected int mCurrentWorker;
    
    protected GShedule(int workers, GThread... gThread){
        M_WORKERS_LIMIT = workers;
        M_GTHREADS_ARRAY = gThread;
        mCurrentWorker = 0;
    }
    
    /**
     * To sure there's no terminated gthread or running gthread is
     * Attached to schedule before running.
     * @throws ScheduleGThreadException 
     */
    protected void checkGThreadValidation() throws ScheduleGThreadException{
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
     * Synchronize process inwhich workers increase when
     * new task is avaliable and decrease when task is terminated.
     * @param workersChanger    State of workers changing
     */
    protected synchronized void updateWorkers(int workersChanger){
       mCurrentWorker += workersChanger; 
    }
    
   
    /**
     * Start schedules processes. Check if all threads in idle mode or not.
     * 
     * @throws ScheduleGThreadException Exception throws when the list of gthreads
     *                                  contain threads which run before or is terminated
     */
    public abstract int start() throws ScheduleGThreadException;
}
