<h1>
GThread Library
</h1>

<h2>
Brief
</h2>

<p>
A Background threading system that works with efficient manner. Java programmers have a full control on their threads in a few lines of code while saving memory.
</p>

<h2>
Features
</h2>
		<ul>
			<li>
				You can access the thread's output when the thread's progress is finished.
			</li>
			<li>
				GThread have containers which run threads in scheduled manner.
			</li>
			<li>
		      		Containers have ability to specify limit number of running threads and the same time.
			</li>
		</ul>

<h2>How it works</h2>
	<ul>
		<li>
		<h3>GThread</h3>
		<ul>
			<li>
				You specify the type of object which you want to return when GThread process is over.
				GThread is an abstract class with two abstract methods: </li><li>
        
<b>onProgress()</b>: in which you write your code that you want to execute when GThread starts running.</li><li>
        
<b>onFinished(Object)</B>: This method is called when GThread progress is finished. The incoming object is that the object which is going to update during GThread progress. The type of this object is generic. </li>
                                   
 <img src = "https://i.imgur.com/S9KCsB3.png"/>
 
<li>You can start GThread by calling <b>start()</b> method.</li>
</ul>
<h3>Methods</h3>
<ul>
<li>
<b>start ()</b>: To start GThread progress.
</li>
<li>
<b>onProgress ()</b>: Called when GThread starts.
</li>
<li>
<b>onFinished ()</b>: Called when GThread progress ends.
</li>
</ul>
</li>
     <li>
     <h3>Containers</h3>
     <ul>
     <li>
     <h4>SheduleGThread</h4>
     <ul>
     <li>
     Container which takes GThreads and the limits the GThreads running at the same time to a specific number. SheduleGThread take the number of threads you want to execute in the same time and array of GThread. <b>ScheduleGThread</b> is an abstract class also with <b>onScheduleFinished()</b> method which is called when ScheduleGThread finishs all GThreads progresses. You can start SheduleGThread by calling <b>start ()</b> method.   
- ScheduleGThread first parameter is the limit number (e.g. <b>2</b> in the following image as a number of GThreads which are going to run together at the same time).

<b>Note: ScheduleGthread has verargs argument so you can set GThreads as an array or individualy.</b>
     <img src="https://i.imgur.com/Cc01G3i.png"/>
     <br/>
- To demystify the first parameter of ScheduleGThread let's take an example about how it really work.

- Let’s assume that you have <b>10 GThreads</b> and you want to run them <b>two by two</b> in order.

- <p>Initial state of Schedule when you create instance and you don't launch it yet. (Waiting: GThreads are hold but in idle state | In Working Stage: Current GThreads which are running| Finished: GThreads which finished their progress)<p>

  
<br/>
  <img src= "https://i.imgur.com/BR7pnfB.png"/>
<br/>

- Now you called <b>start ()</b> method for scheduleGThread ... it's going to run first two GThreads <b>only because you set ScheduleGThread limit to 2</b>.

<br/>
   <img src= "https://i.imgur.com/ToN7UYe.png"/>
<br/>

- As we can see the first two GThreads only are running and the others in waiting state.

<br/>
   <img src= "https://i.imgur.com/NfDPAvJ.png"/>
<br/>

- Okey, Now assume that one of GThread is finished what will happen? The next GThread is going to start... Yes it's amazing, right? :D 

<br/>
   <img src= "https://i.imgur.com/mzVeXgM.png"/>
<br/>

- Then this process will repeat until all GThreads in waiting state are finished. After that the <b>onScheduleFinished()</b> method will be called.

<br/>
   <img src="https://i.imgur.com/QicMb32.png"/>
<br/>

<h3>Methods</h3>
<ul>
<li>
<b>start()</b>: Returns 1 if ScheduleGThread is started successfully otherwise returns -1. (We are going to talk about this point in details later)</n>
</li>
</ul>
</li>
</ul>
</li>
<li>


<h4>ScheduleGThreadLinked</h4>

<ul>
<li>
It's similer to SheduleGThread, but more dynamic. In ScheduleGThread you initialize the instance with array of GThreads and the limit of the of GThreads which are going to run at the same time. ScheduleGThreadLinked takes the same parameters and it functions same as ScheduleGThread but you can add GThread using <b>add (Gthread gthread)</b> method and remove GThread using <b>remove(GThread gthread)</b> method during ScheduleGThreadLinked running state.

<br/>
<img src="https://i.imgur.com/YQAkqnN.png"/>
<br/>
- To be more clear let's assume that we have 3 GThread we want to run, the first two are passed to ScheduleGThreadLinked and we set limitation of GThread running to 2, then we start the process by <b>start()</b> method then we add the next thread. So in initial state of ScheduleGThreadLinked is going to be something like that
<br/>
<img src="https://i.imgur.com/wWYadaJ.png"/>
<br/>
- Then ScheduleGThread is going to start both gthread1 and gthread2 because its limitation is 2 as we said before. 
<br/>
<img src="https://i.imgur.com/Hj552q7.png"/>
<br/>
- Okay, Assume gthread1 is finished and the gthread2 is still running then what will happen when we add gthread3.. ? Yes, as you thought. gthread3 is going to run automatically because your limit is set to 2 and gthread2 is the only GThread running now.
<br/>
<img src="https://i.imgur.com/W0pdl29.png"/>
<br/>
- Now you can think that ScheduleGThreadLinked takes a lot of memory whenever GThread which are contained by ScheduleGThreadLinked are finished. Because it's waiting another GThread!! I want to reassure you because it's just going to start when it has GThreads need to execute otherwise it is going to stop completely and when you add new gthread then it create itself automatically and start again. 
</li>
<h3>Methods</h3>
<ul>
<li>
<b>start()</b>: Starts the ScheduleGThreadLinked. Returns 1 if ScheduleGThreadLinked started successfully otherwise returns -1. (We are going to talk about this point in details later).
</li>
<li>
<b>add(GThread gthread)</b>: To add new GThread to ScheduleGThreadLinked. Returns 1 when adding process is succeeded otherwise returns -1. (We are going to talk about this point in details later).
</li>
<li>
<b>remove(GThread gthread)</b>: To remove existing GThread. Returns 1 when removing process is succeeded and returns -1 when you try to remove a running GThread.
</li>
</ul>
</ul>
</ul>
<h3> ScheduleGThread Exception </h3>
<ul>
<li>
This exception happen when you try to add a running or terminated GThread to any container (SheduleGThread - SheduleGThreadLinked) at any point.
</li>
</ul>

