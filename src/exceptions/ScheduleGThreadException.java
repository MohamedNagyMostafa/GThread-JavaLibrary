package exceptions;

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
 *
 * @author mohamednagy
 */
public class ScheduleGThreadException extends Exception{
    // Exception message for thread schedule when pass theard
    // have run before.
    public static final String ALIVE_THREAD_EXCEPTION_MESSAGE = "You set running thread in schedule group";
    // Exception message for thread schedule then pass a terminated thread.
    public static final String TERMINATED_THREAD_EXCEPTION_MESSAGE = "You're set terminated thread in schedule group";
    
    public ScheduleGThreadException(){
        super();
    }
    public ScheduleGThreadException(String message){
        super(message);
    }
    public ScheduleGThreadException(Throwable cause){
        super(cause);
    }
    public ScheduleGThreadException(String message, Throwable cause){
        super(message, cause);
    }
}
