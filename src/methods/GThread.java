package methods;

import handlers.GThreadActors;

public abstract class GThread<T> extends GThreadController implements GThreadActors<T>{
    
    // The value of GThread name when it's not have specific name.
    private static final String M_EMPTY_VALUE = "";
    // GThread state when gthread is created but it has not started yet.
    public static final int G_THREAD_IDLE = 0;
    // GThread state when gthread is runinig.
    public static final int G_THREAD_RUNNING = 1;
    // GThread state when gthread process is finished.
    public static final int G_THREAD_TERMINATED = 2;
    // Thread which launch within GThread.
    private Thread mThread;
    // Name of GThread which you can get whenever you need.
    private String mGThreadName;
    // State of GThread, it's going to hold true whenever GThread process 
    // is started otherwise hold false value.
    private int mGThreadState;
    
    public GThread(){
        // Set GThread state as idle state.
        setGThreadState(G_THREAD_IDLE);
        
    }
    /**
     * Start GThread process.
     * Handling with gthread upon it's type.
     * GThread types are 
     *             - GThread connected with scheduler
     *             - GThread connected with scheduler link
     *             - GThread didn't connect with any scheduler
     */
    public void start(){
        launchHandling();
    }
    /**
     * Launch handling progress of gthread and set gthread
     * state as running state.
     */
    private void launchHandling(){
        switch(getGthreadType()){
            case G_THREAD_WITHOUT_SCHEDULE:
                setGThreadState(G_THREAD_RUNNING);
                handlingProgress();
                break;
            case G_THREAD_WITH_LINKED_SCHEDULE:
            case G_THREAD_WITH_SCHEDULE:
                setGThreadState(G_THREAD_RUNNING);
                handlingScheduleProgress();
                break;
        }
    }
    /**
     * Handling gthread when it's connected with scheduler.
     */
    private void handlingScheduleProgress(){ 
        mThread = new Thread(()->{
            T object = onProgress();
            notifyChanging();
            setGThreadState(G_THREAD_TERMINATED);
            onFinished(object);
        });
        mThread.start();
    }
    /**
     * Handling gthread when it's not connected with any scheduler.
     */
    private void handlingProgress(){ 
        mThread = new Thread(()->{
            T object = onProgress();
            setGThreadState(G_THREAD_TERMINATED);
            onFinished(object);
        });
        mThread.start();
    }
    /**
     * Make join for internal thread within GThread.
     * 
     * @throws InterruptedException     Throws when thread get interrupt
     */
    public void join() throws InterruptedException{
        if(mThread != null)
            mThread.join();
    }
    /**
     * Make join for internal thread within GThread.
     * 
     * @param millis    Time of join as milliseconds
     * @throws InterruptedException     Throws when thread get interrupt
     */
    public void join(long millis) throws InterruptedException{
        if(mThread != null)
            mThread.join(millis);
    }
    /**
     * Make join for internal thread within GThread.
     * 
     * @param millis    Time of join as milliseconds
     * @param nanos     Time of join as nanoSeconds
     * @throws InterruptedException     Throws when thread get interrupt
     */
    public void join(long millis, int nanos) throws InterruptedException{
        if(mThread != null)
            mThread.join(millis, nanos);
    }
    /**
     * Set interrupt for internal GThread.
     */
    public void interrupt(){
        if(mThread != null)
            mThread.interrupt();
    }
    /**
     * Get state of internal thread of gthread, Returns true if internal thread
     * is running otherwise return false.
     * 
     * @return  State if internal thread within gthread
     *          (Running or not).
     */
    public boolean isAlive(){
        if(mThread != null)
            return mThread.isAlive();
        else
            return false;
    }
    /**
     * Check if internal Thread  of GThread is interrupted or not.
     * Returns true if it's interrupted otherwise return false.
     * 
     * @return  Interruption state.
     */
    public boolean isInterrupted(){
        if(mThread != null)
            return mThread.isInterrupted();
        else
            return false;
    }
    /**
     * Return gthread name if it contains one Otherwise
     * return empty string.
     * 
     * @return  GThread name
     */
    public String gthreadName(){
        if(mGThreadName != null)
            return mGThreadName;
        else
            return M_EMPTY_VALUE;
    }
    /**
     * Specify name for GThread which can get in the next time.
     * 
     * @param name Gthread name.
     */
    public void setName(String name){
        mGThreadName = name;
    }
    /**
     * Returns one of gthread states, States of gthread are :
     *          - @G_THREAD_IDLE: When gthread is created but not started yet.
     *          _ @G_THREAD_RUNNING: When gthread is running now.
     *          _ @G_THREAD_TERMINATED: When gthread processes are finished.
     * 
     * @return  One of gthread states.
     */
    public int gthreadState(){
        return mGThreadState;
    }
    /**
     * Handling with state of gthread.
     * @param state     New gThread state.
     */
    private void setGThreadState(int state){
        mGThreadState = state;
    }
}
