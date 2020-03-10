import java.io.* ;
import java.net.* ;
import java.util.* ;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.net.InetAddress.*;

public final class WebServer
{
  public static void main(String argv[]) throws Exception
  {
    int port = 6789;

    // Listening socket
    final ServerSocket server = new ServerSocket(port);
    System.out.println("Listening for port " + port);
    // Process HTTP service requests in an infinite loop
    while (true)
    {
      // Listen for a TCP connection request
      Socket requestSocket = server.accept();

      // Object to process request
      HttpRequest request = new HttpRequest(requestSocket);

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
  // Used to terminate each line of server response message
  final static String CRLF = "\r\n";
  Socket socket;

  // Constructor
  public HttpRequest(Socket socket) throws Exception
  {
    this.socket = socket;
  }

  public void run()
  {
    try
    {
      processRequest();
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
  }

  private String contentType(String fileName)
  {
    if (fileName.endsWith(".htm") || fileName.endsWith(".html"))
      return "text/html";
    else
      return "text/plain";
  }

  private void processRequest() throws Exception
  {
    // S O C K E T     S T R E A M S
    // Get a ref to socket's input and output streams
    InputStream is = socket.getInputStream();//new FileInputReader(connect.getInputStream());
    DataOutputStream os = new DataOutputStream(socket.getOutputStream());// = connect.getOutputStream();
    // Set up input stream filters
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    // Request line of HTTP request
    String requestLine = br.readLine();

    // R E Q U E S T     M E S S A G E S
    // Display request line
    System.out.println();
    System.out.println(requestLine);

    // H E A D E R     L I N E S
    String headerLine = null;
    while ((headerLine = br.readLine()).length() != 0)
    {
      System.out.println(headerLine);
    }

    // Close streams and Socket
    os.close();
    br.close();
    socket.close();

    // F I L E    N A M E    E X T R A C T I O N
    // Extract filename from request line
    StringTokenizer tokens = new StringTokenizer(requestLine);
    tokens.nextToken();  // Skip over the method, which should be "GET"
    String fileName = tokens.nextToken();
    // Preprend a "."
    fileName = "." + fileName;
    // Open requested file
    FileInputStream fis = null;
    boolean fileExists = true;
    try
    {
      fis = new FileInputStream(fileName);
    }
    catch (FileNotFoundException e)
    {
      fileExists = false;
    }

    // R E S P O N S E    M E S S A G E
    String statusLine = null;
    String contentTypeLine = null;
    String entityBody = null;
    if (fileExists)
    {
      //statusLine @@@@@@@@@@@@@
      contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
    }
    else
    {
      //statusLine @@@@@@
      //contentTypeLine @@@@@@@
      entityBody = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>" + "<BODY>Not Found</BODY></HTML>";
    }

  }
}
