# COMP3100 - Project (Stage #1) 
> **Name**: Rohan Eapen George
>
> **Student ID**: 45664609

**Project Title**: Job Scheduler for Distributed Systems

#### Project Description: 
* Stage 1 - Design and implementation of a client-side simulator with a simple job dispatcher (LRR)
* Stage 2 - 

#### Usage
1. Compile the client-side simulator (Client.java) - `javac Client.java`
2. Run the client-side simulator (Client.java):
    - To run the client-side simulator with a configuration file:
        - `java Client`
        - Run the provided server-side simulator (ds-server) - `./ds-server -c '../../configs/sample-configs/ds-sample-config0[N].xml' -v breif -n` [Sample configuration files are NOT included in this repostiory]
   - To run the demoS1 shell script:
        - `chmod +x ds-client ds-server`
        - `tar -xvf S1Demo.tar`
        - `./demoS1.sh Client.class -n`
