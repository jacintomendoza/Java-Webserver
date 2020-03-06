import java.io.* ;
import java.net.* ;
import java.util.* ;
import java.net.ServerSocket;


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
    }


  }

}

// Establish the listen socket


/*
final class HttpRequest implements Runnable
{

}
*/
