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
import exceptions.ScheduleGThreadException;

/**
 *
 * @author mohamednagy
 */
abstract class GShedule {
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
    protected GThread[] M_GTHREADS_ARRAY;
    // Limitation number of workers which execute schedule tasks
    // at the same time.
    protected final int M_WORKERS_LIMIT;
    // Original thread of Schedule gthread.
    protected Thread mScheduleGThread; 
    // Number of current workers which execute tasks.
    protected Integer mCurrentWorker;
    
    protected GShedule(int workers, GThread... gThread){
        M_WORKERS_LIMIT = workers;
        M_GTHREADS_ARRAY = gThread;
        mCurrentWorker = 0;
    }
    
    protected GShedule(int workers){
        M_WORKERS_LIMIT = workers;
        mCurrentWorker = 0;
    }
    /**
     * Synchronize process inwhich workers increase when
     * new task is avaliable and decrease when task is terminated.
     * @param workersChanger    State of workers changing
     */
    protected synchronized void updateWorkers(int workersChanger){
       mCurrentWorker += workersChanger; 
       notify();
    }
    
   
    /**
     * Start schedules processes. Check if all threads in idle mode or not.
     * 
     * @throws ScheduleGThreadException Exception throws when the list of gthreads
     *                                  contain threads which run before or is terminated
     */
    public abstract int start() throws ScheduleGThreadException;
    /**
     * To sure there's no terminated gthread or running gthread is
     * Attached to schedule before running.
     * @throws ScheduleGThreadException 
     */
    protected abstract void checkGThreadValidation() throws ScheduleGThreadException;
}
