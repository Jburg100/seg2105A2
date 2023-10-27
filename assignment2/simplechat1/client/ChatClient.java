// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  // Added if statements to this method. Changed for E50 JMB
  public void handleMessageFromClientUI(String message)
  {
    if(message.charAt(0) != '#'){
      try
      {
        if(!isConnected()){
          System.out.println("Cannot send message, please connect to a server");
        }
        else{
          sendToServer(message);
        }
      }
      catch(IOException e)
      {
        clientUI.display
          ("Could not send message to server.  Terminating client.");
        quit();
      }
    }
    else if(message.equals("#quit")){
      quit();
    }
    else if(message.equals("#logoff")){
      try{
        closeConnection();
      }
      catch(Exception IOException){
        System.out.println("logged off");
      }

    }
    else if(message.equals("#login")){
      try {
        openConnection();
      } catch (IOException e) {
        System.out.println("Problem when connecting to" + getHost() + "\nPort Number: " + getPort());
      }
    }
    else if(message.substring(0, 8).equals("#sethost")){
      if(isConnected()){
        System.out.println("Still connected to a server. \nPlease disconnect before connecting to a new one(#logoff)");
      }
      else{
        setHost(message.substring(9, message.length()));
      }
    }
    else if(message.substring(0, 8).equals("#setport")){
      if(isConnected()){
        System.out.println("Still connected to a server. \nPlease disconnect before connecting to a new one(#logoff)");
      }
      else{
        setPort(Integer.parseInt(message.substring(9, message.length())));
      }
    }
    else if(message.substring(0, 8).equals("#gethost")){
      System.out.println("Host: " + getHost());
    }
    else if(message.substring(0, 8).equals("#getport")){
      System.out.println("Port: " + getPort());
    }
    else{
      System.out.println("This is not a recognizable command");
    }

  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }

  //I overrid this method to display to the user when the server shuts down Changed for E49'JMB
  public void connectionClosed(){
    System.out.println("Connection has closed");
  }
  //When this is called it will let the user know the error, quit, then call connectionCLosed Changed for E49'JMB
  public void connectionException(Exception exception){
    System.out.println("Exception thrown while waiting for server: " + exception);
    quit();
    connectionClosed();
    System.out.println("Server has shutdown and has closed");
  }
}
//End of ChatClient class
