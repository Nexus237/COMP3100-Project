# COMP3100 - Distributed Systems (Project)
> **Name**: Rohan Eapen George
>
> **Student ID**: 45664609

#### Table of Contents 
- [Project Description](#project-description)
- [Initial Configuration](#initial-configuration)
- [Usage (For Stage #1)](#stage1)
- [Usage (For Stage #2)](#stage2)


## Project Description

* **Project Title -** Job Scheduler for Distributed Systems
* **Stage 1 -**  Design and implement a vanilla version of client-side simulator that includes basic scheduling functionalities and a simple job dispatcher.
* **Stage 2 -** Design and implement one or more new scheduling algorithms that shall be assessed in comparison with a set of baseline algorithms including First-Fit (FF), Best-Fit (BF) and Worst-Fit (WF)

## Initial Configuration
<div id="configuration"></div>

1. Clone the repository

   ```sh
   git clone https://github.com/Nexus237/COMP3100-Project.git
   ```
2. Change directory to the repository
   ```sh
   cd COMP3100-Project
   ```

## Usage (For Stage #1)
<div id="stage1"></div>

1. Compile the client-side simulator (Client.java) - 

   ```sh
   javac Client.java
   ```
2. Run the client-side simulator (Client.java):
   - To run the demoS1 shell script:

      ```sh
      chmod +x ds-client ds-server
      ```
      ```sh
      tar -xvf S1Demo.tar
      ```
      ```sh
      ./demoS1.sh Client.class -n
      ```
       
    - To run the client-side simulator with a configuration file:

      ```sh
      java Client
      ```
        * Run the provided server-side simulator (ds-server) - 

         ```sh
         ./ds-server -c 'PATH/CONFIG_FILE.xml' -v brief -n
         ```
         Replace `PATH/CONFIG_FILE.xml` with the actual configuration file.
        * Example - `./ds-server -c '../../configs/sample-configs/ds-sample-config01.xml' -v brief -n`
        <br /> (**NOTE -** Configuration files are *NOT* included in this repostiory.)

## Usage (For Stage #2)
<div id="stage2"></div>

1. Compile the client-side simulator (ClientStage2.java) - 

   ```sh
   javac ClientStage2.java
   ```
2. Run the client-side simulator (ClientStage2.java):
   - To run the stage2 test script:

      ```sh
      chmod +x ds-client ds-server
      ```
      Requires unzip to be installed on the system - `sudo apt-get install unzip`
      ```sh
      unzip stage2-test.zip
      ```
      ```sh
      ./stage2-test-x86 "java ClientStage2" -o tt -n
      ```
       
    - To run the client-side simulator with a configuration file:

      ```sh
      java ClientStage2
      ```
        * Run the provided server-side simulator (ds-server) -  

         ```sh
         ./ds-server -c 'PATH/CONFIG_FILE.xml' -v brief -n
         ```
         Replace `PATH/CONFIG_FILE.xml` with the actual configuration file.
        * Example - `./ds-server -c '../../configs/sample-configs/ds-sample-config01.xml' -v brief -n`
        <br /> (**NOTE -** Configuration files are *NOT* included in this repostiory.)
