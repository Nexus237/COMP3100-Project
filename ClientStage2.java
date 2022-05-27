import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class ClientStage2 {
    public static void main(String args[]) {
        while (true) {
            try {
                // ANSI Escape Codes for coloured outputs
                String ANSI_GREEN = "\u001B[32m";
                String ANSI_RED = "\u001B[31m";
                String ANSI_RESET = "\u001B[0m";

                String host = "127.0.0.1"; // Host IP Address
                int port = 50000; // Host Port Number

                Socket s = new Socket(host, port); // Connect to server with previously specified IP and port
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                BufferedReader din = new BufferedReader(new InputStreamReader(s.getInputStream()));

                System.out.println(ANSI_GREEN + "\n# ------------------------------------------------");

                System.out.println("\n# Target IP: " + s.getInetAddress());
                System.out.println("# Target Port: " + s.getPort());

                System.out.println("\n# ------------------------------------------------");

                System.out.println("\n# Connection Established Successfully!\n" + ANSI_RESET);

                // Handshake Protocol ('HELO' -> 'AUTH')
                dout.write(("HELO\n").getBytes());
                dout.flush();
                System.out.println("SENT: 'HELO'");

                String str = (String) din.readLine();
                System.out.println("RCVD: '" + str + "'");

                String username = System.getProperty("user.name");
                dout.write(("AUTH " + username + "\n").getBytes());
                dout.flush();
                System.out.println("SENT: 'AUTH " + username + "'");
                dout.flush();

                str = (String) din.readLine();
                System.out.println("RCVD: '" + str + "'");
                // End of Handshake Protocol

                // Send REDY for jobs list
                dout.write(("REDY\n").getBytes());
                dout.flush();
                System.out.println("SENT: 'REDY'");

                str = (String) din.readLine();
                System.out.println("RCVD: '" + str + "'");

                String[] currCommand = str.split(" "); // Split the received command into a string array (core, memory,
                                                       // disk, etc.)
                String currCommandType = currCommand[0]; // Command Type (either 'JOBN' or 'NONE')

                while (true) {
                    // For JOBN, store the information of the job to variables
                    String jobID = currCommand[2];
                    String core = currCommand[currCommand.length - 3];
                    String memory = currCommand[currCommand.length - 2];
                    String disk = currCommand[currCommand.length - 1];
                    System.out.println(ANSI_GREEN + "\n# Core: " + core + ", Memory: " + memory + ", Disk: " + disk
                            + "\n" + ANSI_RESET);

                    // Queries the server information based on core, memory & disk
                    dout.write(("GETS Avail " + core + " " + memory + " " + disk + "\n").getBytes());
                    dout.flush();
                    System.out.println("SENT: 'GETS Avail " + core + " " + memory + " " + disk + "'");

                    str = (String) din.readLine();
                    System.out.println("RCVD: '" + str + "'");

                    String[] DATA = str.split(" "); // DATA nRecs recLen
                    int records = Integer.parseInt(DATA[1]); // Number of records derived from the response ('DATA') of
                                                             // the server
                    System.out.println(ANSI_GREEN + "\n# Number of Records: " + records + "\n" + ANSI_RESET);

                    // currCommand = str.split(" ");

                    if (records == 0) {
                        dout.write(("OK\n").getBytes());
                        dout.flush();
                        System.out.println("SENT: 'OK'");

                        str = (String) din.readLine();
                        System.out.println("RCVD: '" + str + "'");

                        dout.write(("GETS Capable " + core + " " + memory + " " + disk + "\n").getBytes());
                        dout.flush();
                        System.out.println(ANSI_RED + "SENT: 'GETS Capable " + core + " " + memory + " " + disk + "'"
                                + ANSI_RESET);

                        str = (String) din.readLine();
                        System.out.println("RCVD: '" + str + "'");

                        DATA = str.split(" ");
                        records = Integer.parseInt(DATA[1]);
                        System.out.println(ANSI_RED + "\n# Number of Records: " + records + "\n" + ANSI_RESET);
                    }

                    dout.write(("OK\n").getBytes());
                    dout.flush();
                    System.out.println("SENT: 'OK'");

                    // String largestServerType = ""; // Stores the largest server type (based on
                    // core count)
                    String firstServerType = ""; // Stores the first server type
                    // int largestCore = 0; // Largest server core count that can run the job
                    // int numLargestType = 0; // Number of the largest server type that can run the
                    // job
                    int firstServerID = 0;

                    // Print jobs list
                    for (int i = 0; i < records; i++) {
                        str = (String) din.readLine();
                        System.out.println("RCVD: '" + str + "'");

                        String[] currServer = str.split(" ");

                        // Compare the current server core count to the largest server core count
                        // if (largestCore < currCore) {
                        // largestServerType = currServer[0];
                        // largestCore = Integer.parseInt(currServer[4]);
                        // }

                        // Checks for the number of servers of the largest server type
                        // if (currServerType.equals(firstServerType)) {
                        // numFirstType = Integer.parseInt(currServer[1]);
                        // }

                        if (i == 0) {
                            firstServerType = currServer[0];
                            firstServerID = Integer.parseInt(currServer[1]);
                        }
                    }

                    System.out.println(ANSI_GREEN + "\n# First Server Type: " + firstServerType + ", First Server ID: "
                            + firstServerID + "\n" + ANSI_RESET);

                    dout.write(("OK\n").getBytes());
                    dout.flush();
                    System.out.println("SENT: 'OK'");

                    str = (String) din.readLine();
                    System.out.println("RCVD: '" + str + "'");

                    // int jobID = 0;
                    // int serverID = 0;

                    // Job Scheduling
                    // while (!(currCommandType.equals("NONE"))) {
                    // if (currCommandType.equals("JOBN")) {

                    dout.write(("SCHD " + jobID + " " + firstServerType + " " + firstServerID + "\n").getBytes());
                    dout.flush();
                    System.out.println("SENT: 'SCHD " + jobID + " " + firstServerType + " " + firstServerID + "'");

                    str = (String) din.readLine();
                    System.out.println("RCVD: '" + str + "'");

                    // // Checks if the server ID is currently the last server ID of the largest
                    // server type
                    // if (serverID + 1 <= numLargestType) {
                    // serverID++;
                    // } else {
                    // serverID = 0;
                    // }
                    // }

                    dout.write(("REDY\n").getBytes());
                    dout.flush();
                    System.out.println("SENT: 'REDY'");

                    str = (String) din.readLine();
                    System.out.println("RCVD: '" + str + "'");

                    currCommand = str.split(" "); // Split the received command (Checking for 'JOBN' or 'NONE')
                    currCommandType = currCommand[0];
                    // }

                    while (currCommandType.equals("JCPL")) {
                        dout.write(("REDY\n").getBytes());
                        dout.flush();
                        System.out.println("SENT: 'REDY'");

                        str = (String) din.readLine();
                        System.out.println("RCVD: '" + str + "'");

                        currCommand = str.split(" "); // Split the received command (Checking for 'JOBN' or 'NONE')
                        currCommandType = currCommand[0];
                    }

                    if (currCommandType.equals("NONE")) {
                        break;
                    } else {
                        jobID = currCommand[2];
                    }

                }

                dout.write(("QUIT\n").getBytes());
                dout.flush();
                System.out.println("SENT: 'QUIT'");

                str = (String) din.readLine();

                System.out.println(ANSI_RED + "\n# Connection Terminated...\n" + ANSI_RESET);

                dout.close();
                s.close();

                System.exit(0);
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
