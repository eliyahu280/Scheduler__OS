# OS Scheduler 
Operating system scheduler

Components:

Process, Resources, CPU, Process Scheduling. Short term scheduler (algorithms to choose).

## Project description:

•	Goal of Project:
    Creating Simulators of the of the Operating System with the help of some of the above components.
    The goal is to demonstrate the process life cycle with consideration of it’s resources in accordance with the chosen algorithm and showing how computer multitasking works.
    Each process will be able to divide itself to threads to be able to work in parallel.        
    The process\ Task will deal with synchronization problems, and for multitasking will be used of the  thread pools.
    The Scheduler component assigning resources to each process and will be Singleton.
    
## Task of each Process:
     •	Read\ write from and to a txt file (same one).  
     •	Bounded buffer.     
     •	The dining philosophers.

•	Process Synchronization: Creating Critical Section and dealing with it (Mutual exclusion, Progress, Bounded waiting). 

### Application Description from user perspective:

1.	The user assigns one or more of the tasks mentioned above to the process, number of threads for a process (if any). -define processes.
2.	The user creates process resources requirements (time and memory).
3.	The user determines number of cores in CPU.
4.	The user selects between Preemptive and non-Preemptive scheduler and the algorithm for the short-term scheduler.  
5.  The Program Initializing in accord to the user choices.
6.	The program will run the processes and displaying to the user: CPU utilization, Throughout, Turnaround time, waiting time regarding the selected features of the processes.
      
### Design Patterns(for now):
1.	Bridge – for algorithm.
2.	Factory – for the selected algorithm.
3.	Decorator – to create Tasks and have and more flexible code.
4.	Singleton – for Scheduler and some components.
5.	Command Pattern – for menu.
6.	Strategy.



Note - This Project is still in progress.
