import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Client {
  public static void main(String args[]) {
    while (true) {
      try {
        Socket s = new Socket("127.0.0.1", 50000);
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        BufferedReader din = new BufferedReader(new InputStreamReader(s.getInputStream()));

        System.out.println("\nConnection Established Successfully!\n");

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

        dout.write(("REDY\n").getBytes());
        dout.flush();
        System.out.println("SENT: 'REDY'");

        str = (String) din.readLine();
        System.out.println("RCVD: '" + str + "'");

        String[] currCommand = str.split(" ");
        String currCommandType = currCommand[0];
        String core = currCommand[currCommand.length - 3];
        String memory = currCommand[currCommand.length - 2];
        String disk = currCommand[currCommand.length - 1];
        System.out.println("\n# Core: " + core + ", Memory: " + memory + ", Disk: " + disk + "\n");

        dout.write(("GETS Capable " + core + " " + memory + " " + disk + "\n").getBytes());
        dout.flush();
        System.out.println("SENT: 'GETS Capable " + core + " " + memory + " " + disk + "'");

        str = (String) din.readLine();
        System.out.println("RCVD: '" + str + "'");

        String[] DATA = str.split(" ");
        int records = Integer.parseInt(DATA[1]);
        System.out.println("\n# Number of Records: " + records + "\n");

        dout.write(("OK\n").getBytes());
        dout.flush();
        System.out.println("SENT: 'OK'");

        String largestServerType = "";
        int firstServerID = 0;
        int largestCore = 0;
        int numLargestServer = 0;

        for (int i = 0; i < records; i++) {
          str = (String) din.readLine();
          System.out.println("RCVD: '" + str + "'");

          String[] currServer = str.split(" ");
          int currCore = Integer.parseInt(currServer[4]);

          if (currCore > largestCore) {
            largestServerType = currServer[0];
            firstServerID = Integer.parseInt(currServer[1]);
            largestCore = Integer.parseInt(currServer[4]);
          }

          if (currServer[0].equals(largestServerType)) {
            numLargestServer = Integer.parseInt(currServer[1]);
          }
        }

        dout.write(("OK\n").getBytes());
        dout.flush();
        System.out.println("SENT: 'OK'");

        str = (String) din.readLine();
        System.out.println("RCVD: '" + str + "'");

        int jobID = 0;
        int serverID = firstServerID;

        while (!(currCommandType.equals("NONE"))) {
          if (currCommandType.equals("JOBN")) {
            dout.write(("SCHD " + jobID + " " + largestServerType + " " + serverID + "\n").getBytes());
            dout.flush();
            System.out.println("SENT: 'SCHD " + jobID + " " + largestServerType + " " + serverID + "'");

            str = (String) din.readLine();
            System.out.println("RCVD: '" + str + "'");

            if (serverID + 1 <= numLargestServer) {
              serverID++;
            } else {
              serverID = firstServerID;
            }

            jobID++;
          }
          dout.write(("REDY\n").getBytes());
          dout.flush();
          System.out.println("SENT: 'REDY'");

          str = (String) din.readLine();
          System.out.println("RCVD: '" + str + "'");

          currCommand = str.split(" ");
          currCommandType = currCommand[0];
        }

        dout.write(("QUIT\n").getBytes());
        dout.flush();
        System.out.println("SENT: 'QUIT'");

        str = (String) din.readLine();
        System.out.println("\nConnection Terminated...\n");

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