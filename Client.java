import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Client {
  public static void main(String args[]) {
    while (true) {
      try {
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_RESET = "\u001B[0m";

        Socket s = new Socket("127.0.0.1", 50000);
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        BufferedReader din = new BufferedReader(new InputStreamReader(s.getInputStream()));

        System.out.println(ANSI_GREEN + "\n# ------------------------------------------------");

        System.out.println("\n# Target IP: " + s.getInetAddress());
        System.out.println("# Target Port: " + s.getPort());

        System.out.println("\n# ------------------------------------------------");

        System.out.println("\n# Connection Established Successfully!\n" + ANSI_RESET);

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
        System.out.println(ANSI_GREEN +"\n# Core: " + core + ", Memory: " + memory + ", Disk: " + disk + "\n" + ANSI_RESET);

        dout.write(("GETS Capable " + core + " " + memory + " " + disk + "\n").getBytes());
        dout.flush();
        System.out.println("SENT: 'GETS Capable " + core + " " + memory + " " + disk + "'");

        str = (String) din.readLine();
        System.out.println("RCVD: '" + str + "'");

        String[] DATA = str.split(" ");
        int records = Integer.parseInt(DATA[1]);
        System.out.println(ANSI_GREEN + "\n# Number of Records: " + records + "\n" + ANSI_RESET);

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

          if (largestCore < currCore) {
            largestServerType = currServer[0];
            firstServerID = Integer.parseInt(currServer[1]);
            largestCore = Integer.parseInt(currServer[4]);
          }

          if (currServer[0].equals(largestServerType)) {
            numLargestServer = Integer.parseInt(currServer[1]);
          }
        }

        int adjNumLargestServer = numLargestServer + 1;
        System.out.println(ANSI_GREEN + "\n# Largest Server Type (first instance): " + largestServerType);
        System.out.println("# Number of '" + largestServerType + "' servers: " + adjNumLargestServer + "\n" + ANSI_RESET);

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