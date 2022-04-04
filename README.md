# COMP3100 - Project (Stage #1)
> **Name**: Rohan Eapen George
>
> **Student ID**: 45664609

## Project Description
* **Project Title -** Job Scheduler for Distributed Systems
* **Stage 1 -**  Design and implement a vanilla version of client-side simulator that includes basic scheduling functionalities and a simple job dispatcher.
* **Stage 2 -** Design and implement one or more new scheduling algorithms that shall be assessed in comparison with a set of baseline algorithms including First-Fit (FF), Best-Fit (BF) and Worst-Fit (WF)

## Initial Configuration
1. Clone the repository - `git clone https://github.com/Nexus237/COMP3100-Project.git`
2. Change directory to the repository - `cd COMP3100-Project`

## Usage
1. Compile the client-side simulator (Client.java) - `javac Client.java`
2. Run the client-side simulator (Client.java):
   - To run the demoS1 shell script:
        * `chmod +x ds-client ds-server`
        * `tar -xvf S1Demo.tar`
        * `./demoS1.sh Client.class -n`
       
    - To run the client-side simulator with a configuration file:
        * `java Client`
        * Run the provided server-side simulator (ds-server) - `./ds-server -c 'PATH/CONFIG_FILE.xml' -v brief -n` 
        * Example - `./ds-server -c '../../configs/sample-configs/ds-sample-config01.xml' -v brief -n`
        <br /> (**NOTE -** Configuration files are *NOT* included in this repostiory.)
