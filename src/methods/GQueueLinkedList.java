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
import java.util.Iterator;
import java.util.LinkedList;
/**
 * Saving the synchronization of add, remove, hasNext to work in
 * schedule manner.
 * @author mohamednagy
 */
class GQueueLinkedList<GThread> extends LinkedList<GThread> {
    // Adding request to synchronizeQueueProcess method.
    private static final int ADD_ITEM_PROCESS = 1;
    // Removing request to synchronizeQueueProcess method.
    private static final int REMOVE_ITEM_PROCESS = 2;
    // Request to synchronizeQueueProcess method to ensure queue has 
    // items or not.
    private static final int CHECK_QUEUE_ITEMS = 3;
    // Request result of check item request, Returns when queue still has 
    // items.
    private static final int HAS_ITEMS = 4;
    // Request result of check item request, Returns when queue does not 
    // contain any item.
    private static final int HAS_NO_ITEMS = 5;
    /**
     * Add gthread to QueueLinkedList. This method use synchronize process
     * to be synchronize with removing and hasNext methods.
     * @param gthread   gthread to add.
     */
    @Override
    public boolean add(GThread gthread) {
        synchronizeQueueProcess(ADD_ITEM_PROCESS, gthread);
        return true;
    }
    /**
     * Remove gthread from QueueLinkedList. This method use synchronize process
     * to be synchronize with adding and hasNext methods.
     * @param gthread   gthread to remove
     */
    @Override
    public boolean remove(Object gthread) {
        synchronizeQueueProcess(REMOVE_ITEM_PROCESS, gthread);
        return true;
    }
    /**
     * Check QueueLinkedList contains items or not. This method use synchronize process
     * to be synchronize with adding and removing methods.
     * @param gthreads contains all items in QueueLinkedList
     */
    public boolean hasNext(Iterator<GThread> gthreads){
        return (synchronizeQueueProcess(CHECK_QUEUE_ITEMS , gthreads) == HAS_ITEMS); 
    }
    /**
     * Synchronize method which contains process of three methods (add- remove- hasNext)
     * and work them in schedule way.
     * @param processType   Request of method to determine which method
     *                      want to work
     * @param object        gthread object which is going to add or
     *                      removed from GQueueLinkedList.
     */
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
}
