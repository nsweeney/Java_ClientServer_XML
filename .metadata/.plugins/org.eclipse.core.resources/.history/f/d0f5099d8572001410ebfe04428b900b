package week12.io;
 
import java.net.ServerSocket;
import java.net.Socket;
 
import java.io.IOException;
 
/**
 * This is the server implementation.
 * It listens for a client request, the creates a new ClientConn to setup
 * an async connection and then returns to listen for the next connection attempt.
 * 
 * @author scottl
 *
 */
public class AtmServer
{
 
    public static void main(String[] args) throws IOException
    {
        new AtmServer().start();
    }
     
    public void start()
    {
        initializeServer();
         
        startListening();
    }
     
    /**
     * Initialize the server.
     * If server can't be initialized (socket error) then the app exits. 
     */
    private void initializeServer()
    {
        // Utilize error handling
        try
        {
            server = new ServerSocket(m_port); /* start listening on the port */
            log("Initialized server on port: " + Integer.toString(m_port));
        }
        catch(IOException e)
        {
            System.err.println("Could not listen on port: " + m_port);
            System.err.println(e);
            System.exit(1);
        }
    }
     
    /**
     * This supports multiple clients connecting to it.
     * The server method accept() blocks until a client requests a connection.
     * At that point the method returns and assigns a Socket instance to
     * the client variable.
     * This client instance is then wrapped in the ClientConn object which
     * handles the communications between client and the server. 
     * A thread is used to handle this communication
     * The listener goes back and listens for another client.
     *  
     */
    private void startListening()
    {
        Socket client = null;
        while(true)
        {
            try
            {
                client = server.accept();
            }
            catch(IOException e)
            {
                System.err.println("Accept failed.");
                System.err.println(e);
                System.exit(1);
            }
             
             
            // start a new thread to handle this client
            Thread t = new Thread(new AtmClientConn(client));
            t.start();
        }
    }
     
    private void log(String msg)
    {
        System.out.println(msg);
    }
     
    /** port to listen on */
    private int m_port = 1001; 
     
    /** 
     * We use the ServerSocket to create a listener on a specific port
     */
    private ServerSocket server = null;
}