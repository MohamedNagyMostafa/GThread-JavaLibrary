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

interface GThreadActors<T> {
    
    /**
     * Called start method in GThread is called, It contains the GThread
     * process which GThread have to do and return the output of these process
     * after GThread process finished.
     * 
     * @return  Result object of GThread processes.
     */
    abstract T onProgress();
    
    /**
     * Called when GThread have been finished it's process and 
     * return the process result as a parameter to make programmer
     * have ability to access on the GThread output and handle with it
     * simply.
     * 
     * @param object    The output of the GThread process
     */
    abstract void onFinished(T object);
    
}
