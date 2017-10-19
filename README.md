<h1>
GThread Library
</h1>

<h2>
Brief
</h2>

<p>
Background thread work with efficient manner. Java programmers have a full control on their threads in a few lines of code with saving memory.
</p>

<h2>
Features
</h2>
		<ul>
			<li>
			Programmer can access on the thread output when its progress finished.
			</li>
			<li>
			GThread have containers which run threads in manner scheduling.
			</li>
      <li>
      Containers have ability to specify limit number of running threads and the same time.
      </li>
		</ul>

<h2>How it works</h2>
	<ul>
		<li>
		<h3>Gthread</h3>
		<ul>
			<li>
				You specify the type of object which you want to return when gthread process is over.
				GThread is abstract class with two abstract methods: </li><li>
        
<b>onProgress()</b>: in which you write your code that you want to execute when gthread start to run.</li><li>
        
<b>onFinished(Object)</B>: This method called when gethread progress is finished. The incoming object is that the object which is going to update during gthread progress.
                                   Type of this object upon the type you'll write when you create gthread object. </li>
                                   
 <img src = "https://i.imgur.com/S9KCsB3.png"/>
 
<li>You can start gthread by call <b>start()</b> method.</li>
</ul>
<h3>Methods</h3>
<ul>
<li>
<b>start ()</b>: To start gthread progress.
</li>
<li>
<b>onProgress ()</b>: Called when gthread start.
</li>
<li>
<b>onFinished ()</b>: Called when gthread progress end.
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
     Container which take number of gthreads and the limit of gthreads which will work in the same time. SheduleGThread take the number of threads you want to execute in the same time and array of gthread.ScheduleGThread is an abstract class also with <b>onScheduleFinished()</b> method which called when ScheduleGThread finish all gthreads progress. You can start SheduleGThread by call <b>start ()</b> method.   

<b>Note: ScheduleGthread has verargs argument so you can set gthreads as array or individual.</b>
     <img src="https://i.imgur.com/Cc01G3i.png"/>
     
- In previous image you can note that ScheduleGThread takes number <b>'2'</b> as a number of gthreads which are going to work together at the same time.

- To demystify the first parameter of ScheduleGThread let's take an example about how it really work.

- Let’s assume that you have <b>ten of gthreads</b> and you want to run them <b<two  by two in order</b> ... what I mean that you want two run first to gthreads then whenever one of them is finished run another one and so on like below images which describe this process.

- <p>Initial state of Schedule when you create instance and you don't launch it yet. (Waiting: gthreads are hold but in idle state | In Working Stage: Current gthreads which are running| Finished: gthreads which finished their progress)<p>

  

  <img src= "https://i.imgur.com/BR7pnfB.png"/>


- Now you're called <b>start ()</b> method for scheduleGThread ... it's going to run first two gthreads only because you set gthread <b>number 2</b> as a limitation of running gthreads in the same time.


   <img src= "https://i.imgur.com/ToN7UYe.png"/>


- As we can see the first two gthreads only are running and the others in waiting state.


   <img src= "https://i.imgur.com/NfDPAvJ.png"/>


- Okey, Now assume that one of gthread is finished what will happen..? So the next thread is going to start... Yes it's amazing right :D 


   <img src= "https://i.imgur.com/mzVeXgM.png"/>


- Then this process will repeat until all gthreads in waiting state is finished. After that the <b>onScheduleFinished()</b> will be called.


   <img src="https://i.imgur.com/QicMb32.png"/>
   
<h3>Methods</h3>
<ul>
<li>
<b>start()</b>: Returns 1 if ScheduleGThread is starting successful otherwise return -1. Almost that's happened when you pass a running Gthreads to container (We are going to talk about this point in details later)</n>
</li>
</ul>
</li>
</ul>
</li>
<li>


<h4>ScheduleGThreadLinked</h4>

<ul>
<li>
It's similer to SheduleGThread but with more dynamic. In ScheduleGThread you add array or gthreads and the limit of the number of threads which are going to run in the same time. ScheduleGThreadLinked takes the same parameters and it's process like to ScheduleGThread but you can add using <b>add (Gthread gthread)</b> method and remove gthreads using <b>remove(GThread gthread)</b> method during ScheduleGThreadLinked working.


<img src="https://i.imgur.com/YQAkqnN.png"/>

- To be more clear let's assume that we have 3 gthreads we want to run, the first two we are passed them to ScheduleGThreadLinked and we set limitation of gthread running is 2 then we start its process by <b>start()</b> method then we add the next thread. So in initial state of ScheduleGThreadLinked is going to be something like that

<img src="https://i.imgur.com/wWYadaJ.png"/>

- Then ScheduleGThread is going to start both gthread1 and gthread2 because its limitation is 2 as we say before. 

<img src="https://i.imgur.com/Hj552q7.png"/>

- Okay, Assume the first gthread is finished and the second gthread in running state then what will happen when we add gthread3.. ? Yes that's as you thought. Gthread3 is going to run because your running gthread limitation is two and gthread2 only which is running now.

<img src="https://i.imgur.com/W0pdl29.png"/>

- Now you can thought that ScheduleGThreadLinked takes a lot of memory whenever gthreads which are contained by ScheduleGThreadLinked are finished… Because it waiting another gthread .. !! ... I want to reassure you because it's just going to start when it has gthreads need to execute otherwise it is going to stop completely and when you add new gthread then it create itself automatically and start again. 
</li>
<h3>Methods</h3>
<ul>
<li>
<b>start()</b>: Returns 1 if ScheduleGThreadLinked is starting successfully otherwise return -1. Almost that's happened when you pass a running Gthreads to container (We are going to talk about this point in details later)</n>
</li>
<li>
<b>add(GThread gthread)</b>: To add new Gthread to ScheduleGThreadLinked and return 1 when adding process is succeed otherwise return -1. 
(Add process accept when GThread what you add was not running before or terminated)
</li>
<li>
<b>remove(GThread gthread)</b>: To remove existed Gthread and return 1 when removing process is succeed otherwise return -1. 
(Remove process accept when GThread which selected isn't running now)
</li>
</ul>
</ul>
</ul>
<h3> Schedule GThread Exception </h3>
<ul>
<li>
<b>This exception happen when you try to add a running or terminated gthread to any container (SheduleGThread - SheduleGThreadLinked).</b>
</li>
</ul>

