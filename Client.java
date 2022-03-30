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

        // dout.write(("SCHD 0 super-silk 0\n").getBytes());
        // dout.flush();

        str = (String) din.readLine();
        System.out.println("RCVD: '" + str + "'");

        String[] JOBN = str.split(" ");
        String jobID = JOBN[2];
        String core = JOBN[JOBN.length - 3];
        String memory = JOBN[JOBN.length - 2];
        String disk = JOBN[JOBN.length - 1];
        System.out.println("\n# Job ID: " + jobID + ", Core: " + core + ", Memory: " + memory + ", Disk: " + disk + "\n");

        dout.write(("GETS Capable " + core + " " + memory + " " + disk + "\n").getBytes());
        dout.flush();
        System.out.println("SENT: 'GETS Capable " + core + " " + memory + " " + disk + "'");

        str = (String) din.readLine();
        System.out.println("RCVD: '" + str + "'");

        String[] DATA = str.split(" ");
        int records = Integer.valueOf(DATA[1]);
        System.out.println("\n# Number of Records: " + records + "\n");

        dout.write(("OK\n").getBytes());
        dout.flush();
        System.out.println("SENT: 'OK'");

        for (int i = 0; i < records; i++) {
          str = (String) din.readLine();
          System.out.println("RCVD: '" + str + "'");
        }

        // Need to implement a method to find the largest server
        String[] SCHD = str.split(" ");
        String serverType = SCHD[0];
        String serverID = SCHD[1];
        System.out.println("\n# Server Type: " + serverType + ", Server ID: " + serverID + "\n");

        dout.write(("OK\n").getBytes());
        dout.flush();
        System.out.println("SENT: 'OK'");

        str = (String) din.readLine();
        System.out.println("RCVD: '" + str + "'");

        dout.write(("SCHD " + jobID + " " + serverType + " " + serverID + "\n").getBytes());
        dout.flush();
        System.out.println("SENT: 'SCHD " + jobID + " " + serverType + " " + serverID + "'");

        str = (String) din.readLine();
        System.out.println("RCVD: '" + str + "'");

        dout.write(("OK\n").getBytes());
        dout.flush();
        System.out.println("SENT: 'OK'");

        str = (String) din.readLine();
        System.out.println("RCVD: '" + str + "'");

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