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
 * Handle with connection between Gthread objects and their containers.
 * @author mohamednagy
 * @param <T> Type of returning object in Gthread.
 */
abstract class GThreadController{
    // Type of GThread donate to there's not any schedule is existed.
    protected static final int G_THREAD_WITHOUT_SCHEDULE = 1;
    // Type of GThread donate to there's schedule is existed.
    protected static final int G_THREAD_WITH_SCHEDULE = 2;
    // Type of GThread donate to there's scheduleLinked is existed.
    protected static final int G_THREAD_WITH_LINKED_SCHEDULE = 3;
    
    private ScheduleGThread mScheduleGThread;
    private ScheduleGThreadLinked mScheduleGThreadLinked;
    private GThread mGThread;
    /**
     * Called when gthreads which connected to container is finished.
     * This method notify container about the finishing.
     */
    protected void notifyChanging(){
        if(mScheduleGThread != null){
            mScheduleGThread.onItemFinished();
        }else{
            mScheduleGThreadLinked.onItemFinished(mGThread);
        }
    }
    /**
     * Set schedule for gthread to can notify when gthread progress is
     * finished completely.
     * @param scheduleGThread   Schedule container which is going to set
     *                          as gthread schedule
     */
    protected void setScheduleGThread(ScheduleGThread scheduleGThread){
        mScheduleGThread = scheduleGThread;
    }
    /**
     * Set schedule linked for gthread to can notify when gthread progress is
     * finished completely.
     * @param scheduleGThread   Schedule linked container which is going to set
     *                          as gthread schedule
     */
    protected void setScheduleGThreadLinked(ScheduleGThreadLinked scheduleGThreadLinked, GThread gThread){
        mScheduleGThreadLinked = scheduleGThreadLinked;
        mGThread = gThread;
    }
   /**
    * Check the type of gthread if it's existing with Schedule/ScheduleLinked
    * or not. 
    */
    protected int getGthreadType(){
        if(mScheduleGThread != null)
            return G_THREAD_WITH_SCHEDULE;
        else if(mScheduleGThreadLinked != null)
            return G_THREAD_WITH_LINKED_SCHEDULE;
        else
            return G_THREAD_WITHOUT_SCHEDULE;
    }
}
