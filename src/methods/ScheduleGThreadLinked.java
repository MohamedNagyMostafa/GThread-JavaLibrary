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
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import handlers.GQueueLinkedList;
import handlers.GShedule;
import exceptions.ScheduleGThreadException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Dynamic container for GThread objects.
 * @author mohamednagy
 */
public class ScheduleGThreadLinked extends GShedule{
    public static final int SCHEDULE_LINK_ACCEPT_RESPONSE  = 1;
    public static final int SCHEDULE_LINK_REJECT_RESPONSE = -1;
    
    public static final int SCHEDULE_LINK_TASKS_RUNNING = 2;
    public static final int SCHEDULE_LINK_TASKS_IDLE = 3;
    public static final int SCHEDULE_LINK_TASKS_FINISHED = 4;
    public static final int SCHEDULE_LINK_TASKS_PAUSED = 5;
    
    private GQueueLinkedList<GThread> mQueueLinkedList;
    private int mSheduleLinkState;
    
    public ScheduleGThreadLinked(int workers, GThread... gThread){
        super(workers);
        init(gThread);
    }
    
    private void init(GThread... gThread){
        mQueueLinkedList = new GQueueLinkedList<>();
        mSheduleLinkState = SCHEDULE_LINK_TASKS_IDLE;
        identifyGThreads(gThread);
    }

    @Override
    public synchronized int start() {
        // Ensure there's no previous schedule in running mode.
        checkAccessing();
        
        mSheduleLinkState = SCHEDULE_LINK_TASKS_RUNNING;
        
        try {
            checkGThreadValidation();
        } catch (ScheduleGThreadException ex) {
            Logger.getLogger(ScheduleGThreadLinked.class.getName()).log(Level.SEVERE, null, ex);
            return GShedule.G_SCHEDULE_START_FAILED;
        }
        
        startScheduleProgress();
       
        return GShedule.G_SCHEDULE_START_SUCCESSFULLY;
    }
    
    public synchronized int add(GThread gThread){
        try {
            checkGThreadValidationAt(gThread);
            mQueueLinkedList.add(gThread);
            checkAddingGThread(gThread);
        } catch (ScheduleGThreadException ex) {
            Logger.getLogger(ScheduleGThreadLinked.class.getName()).log(Level.SEVERE, null, ex);
            return SCHEDULE_LINK_REJECT_RESPONSE;
        }
        return SCHEDULE_LINK_ACCEPT_RESPONSE;
    }
    
    public synchronized int remove(GThread gThread){
        if(mQueueLinkedList.contains(gThread)){
            if(!gThread.isAlive()){
                mQueueLinkedList.remove(gThread);
                                
                return SCHEDULE_LINK_ACCEPT_RESPONSE;
            }else{
                return SCHEDULE_LINK_REJECT_RESPONSE;
            }
        }else{
            return SCHEDULE_LINK_REJECT_RESPONSE;
        }
    }
    
    private void checkAddingGThread(GThread gThread){
        if(mSheduleLinkState == SCHEDULE_LINK_TASKS_FINISHED && !gThread.isAlive()){
                start();
        }
    }
    /**
     * Called when each one of tasks is terminated.
     * @param gthreadID 
     */
    void onItemFinished(GThread gThread){
        mQueueLinkedList.remove(gThread);
        updateWorkers(DECREASE_ONE_WORKER_FROM_WORKERS);        
    }

    private void checkGThreadValidationAt(GThread gThread) throws ScheduleGThreadException {
        switch(gThread.gthreadState()){
                case GThread.G_THREAD_RUNNING:
                    throw new ScheduleGThreadException(ScheduleGThreadException.ALIVE_THREAD_EXCEPTION_MESSAGE);
                case GThread.G_THREAD_TERMINATED:
                    throw new ScheduleGThreadException(ScheduleGThreadException.TERMINATED_THREAD_EXCEPTION_MESSAGE);
            }
    }
    
    private void identifyGThreads(GThread... gThreads){
        mQueueLinkedList.addAll(Arrays.asList(gThreads));
    }
    
    private GThread gthreadHandling(){
        GThread gThread = mQueueLinkedList.poll();
        gThread.setScheduleGThreadLinked(this,gThread);
        gThread.start();
        return gThread;
    }
    
    public int pause(){
        if(mQueueLinkedList.isEmpty() || mSheduleLinkState == SCHEDULE_LINK_TASKS_FINISHED){
            return SCHEDULE_LINK_REJECT_RESPONSE;
        }else{
            Util.println("done");
            mSheduleLinkState = SCHEDULE_LINK_TASKS_PAUSED;
            return SCHEDULE_LINK_ACCEPT_RESPONSE;
        }
    }
    
    public int resume(){
        if(mSheduleLinkState == SCHEDULE_LINK_TASKS_PAUSED){
            Util.println("resume");
            mSheduleLinkState = SCHEDULE_LINK_TASKS_RUNNING;
            return SCHEDULE_LINK_ACCEPT_RESPONSE;
        }else{
            mSheduleLinkState = SCHEDULE_LINK_TASKS_PAUSED;
            return SCHEDULE_LINK_REJECT_RESPONSE;
        }
    }
    
    public int stop(){
        int remainsGThread = mQueueLinkedList.size();
        mQueueLinkedList.clear();
        mSheduleLinkState = SCHEDULE_LINK_TASKS_FINISHED;
        return remainsGThread;
    }
    
    public int state(){
        return mSheduleLinkState;
    }

    @Override
    protected void checkGThreadValidation() throws ScheduleGThreadException {
        for(GThread gThread : mQueueLinkedList.toArray(new GThread[mQueueLinkedList.size()])){
            switch(gThread.gthreadState()){
                case GThread.G_THREAD_RUNNING:
                    throw new ScheduleGThreadException(ScheduleGThreadException.ALIVE_THREAD_EXCEPTION_MESSAGE);
                case GThread.G_THREAD_TERMINATED:
                    throw new ScheduleGThreadException(ScheduleGThreadException.TERMINATED_THREAD_EXCEPTION_MESSAGE);
            }
        }
    }
    /**
     * Waiting if this is the last gthread till its progress is 
     * completed completely or new gthread is inserted.
     * @param gthread 
     */
    private void waitingLastGThread(GThread gthread){
        do{
            if(!mQueueLinkedList.isEmpty())
                break;
        } while(gthread.gthreadState() == GThread.G_THREAD_RUNNING);        
    }
    
    private void waitingPauseOrWorkers(){
        do{} while(!(mCurrentWorker < M_WORKERS_LIMIT &&
                mSheduleLinkState != SCHEDULE_LINK_TASKS_PAUSED));
    }
    
    private void startScheduleProgress(){
        mScheduleGThread = new Thread(() -> {
            while(mQueueLinkedList.hasNext(mQueueLinkedList.iterator())){
                GThread gthread = gthreadHandling();
                updateWorkers(INCREASE_ONE_WORKER_FROM_WORKERS);
                waitingPauseOrWorkers();
                waitingLastGThread(gthread);
            }
            mSheduleLinkState = SCHEDULE_LINK_TASKS_FINISHED; 
        });
        mScheduleGThread.start();
    }
    
    private void checkAccessing(){
        if(mScheduleGThread != null && mScheduleGThread.isAlive())
            mScheduleGThread.interrupt();
    }
}
