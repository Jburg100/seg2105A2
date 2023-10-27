
import java.io.BufferedReader;
import java.io.InputStreamReader;
import common.*;


public class ServerConsole extends Thread implements ChatIF{

	final public static int DEFAULT_PORT = 5555;
	private static EchoServer sv;

    public ServerConsole(EchoServer sv) 
  	{
		this.sv = sv;
  	}

	public static void accept() 
  	{
  	  try
  	  {
  	    BufferedReader fromConsole = 
  	      new BufferedReader(new InputStreamReader(System.in));
  	    String message;

  	    while (true) 
  	    {
  	      message = fromConsole.readLine();
		  sv.sendToAllClients(message);
  	    }
  	  } 
  	  catch (Exception ex) 
  	  {
  	    System.out.println
  	      ("Unexpected error while reading from console!");
  	  }
  	}

	public void display(String message) 
	{
		System.out.println("> " + message);
	}

	public static void main(String[] args) 
    {
    	int port = 0; //Port to listen on

    	try
    	{
    	  port = Integer.parseInt(args[0]); //Get port from command line
    	}
    	catch(Throwable t)
    	{
    	  port = DEFAULT_PORT; //Set port to 5555
    	}

    	sv = new EchoServer(port);
	
    	try 
    	{
    	  sv.listen(); //Start listening for connections
		  accept();
    	} 
    	catch (Exception ex) 
    	{
    	  System.out.println("ERROR - Could not listen for clients!");
    	}
    }
    

}
