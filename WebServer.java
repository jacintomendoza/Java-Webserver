import java.io.* ;
import java.net.* ;
import java.util.* ;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;



public final class WebServer
{
  public static void main(String argv[]) throws Exception
  {
    int port = 6789;
    final ServerSocket server = new ServerSocket(port);
    System.out.println("Listening for port " + port);
    // Process HTTP service requests in an infinite loop
    while (true)
    {
      // Listen for a TCP connection request
      Socket requestSocket = server.accept();

      // Object to process request
      HttpRequest request = new HttpRequest();  // Inside brackets is name ?

      // Create thread for request
      Thread t1 = new Thread(request);

      // Start thread
      t1.start();
    }
  }
}

// Establish the listen socket
// Code that runs whenenver running a thread
final class HttpRequest implements Runnable
{

  Socket socket;

  public void run()
  {
    try
    {
      processRequest();
    }
    catch(Exception e)
    {
      //System.out.println(e);
    }
  }

  private void processRequest() throws Exception
  {
    // Get a ref to socket's input and output streams
    InputStream is = socket.getInputStream();//new FileInputReader(connect.getInputStream());
    DataOutputStream os = new DataOutputStream(socket.getOutputStream());// = connect.getOutputStream();

    // Set up input stream filters
    // ?
    BufferedReader br = new BufferedReader(new InputStreamReader(is));

    String requestLine = br.readLine();

    System.out.println();
    System.out.println(requestLine);

    String headerLine = null;
    while ((headerLine = br.readLine()).length() != 0)
    {
      System.out.println(headerLine);
    }

    // Close streams and Socket
    os.close();
    br.close();
    socket.close();
  }
}
