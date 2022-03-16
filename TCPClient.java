import java.net.*;
import java.io.*;

import java.util.concurrent.TimeUnit;

public class TCPClient{
  public static void main(String args[]){
    while  (true) {
      try{
        Socket s = new Socket("127.0.0.1",50000);
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());
        BufferedReader din=new BufferedReader(new InputStreamReader(s.getInputStream()));

        dout.write(("HELO\n").getBytes());
        System.out.println("SENT: HELO");

        String str = (String)din.readLine();
        System.out.println("RCVD: "+str);

        String username = System.getProperty("user.name");
        dout.write(("AUTH " + username + "\n").getBytes());

        dout.write(("REDY\n").getBytes());
        System.out.println("SENT: REDY");

        str = (String)din.readLine();
        System.out.println("RCVD: "+str);

        dout.write(("QUIT\n").getBytes());
        System.out.println("SENT: QUIT");

        str = (String)din.readLine();
        System.out.println("RCVD: "+str);

        dout.close();
        s.close();
      }
      catch(Exception e){System.out.println(e);}
      try {TimeUnit.SECONDS.sleep(1);}
      catch(InterruptedException e){System.out.println(e);}
    }
  }
}
