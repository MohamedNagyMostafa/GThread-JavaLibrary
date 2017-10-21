package methods;


import java.util.Iterator;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mohamednagy
 */
class GQueueLinkedList<GThread> extends LinkedList<GThread> {
    private static final int ADD_ITEM_PROCESS = 1;
    private static final int REMOVE_ITEM_PROCESS = 2;
    private static final int CHECK_QUEUE_ITEMS = 3;
    private static final int HAS_ITEMS = 4;
    private static final int HAS_NO_ITEMS = 5;

    @Override
    public boolean add(GThread gthread) {
        synchronizeQueueProcess(ADD_ITEM_PROCESS, gthread);
        return true;
    }

    @Override
    public boolean remove(Object gthread) {
        synchronizeQueueProcess(REMOVE_ITEM_PROCESS, gthread);
        return true;
    }

    public boolean hasNext(Iterator<GThread> gthreads){
        return (synchronizeQueueProcess(CHECK_QUEUE_ITEMS , gthreads) == HAS_ITEMS); 
    }
    
    public synchronized <H> int synchronizeQueueProcess(int processType, H object){
        switch(processType){
            case ADD_ITEM_PROCESS:
                super.add((GThread) object);
                break;
            case REMOVE_ITEM_PROCESS:
                super.remove(object);
                break;
            case CHECK_QUEUE_ITEMS:
                return (((Iterator<GThread>) object).hasNext())? HAS_ITEMS : HAS_NO_ITEMS;
        }
        
        return 1;
    }
    
    @Override
    public Iterator<GThread> iterator() {
        return super.iterator(); //To change body of generated methods, choose Tools | Templates.
    }
   
     
}
