package week12.io;
 
import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;
 
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
 
import week12.AtmException;
import week12.app.SystemIdGenerator;
import week12.util.LoginRequest;
import week12.xml.XmlUtility;
 
public class AtmServerProtocol
{
    //public ClientConn conn;
 
    public AtmServerProtocol(AtmClientConn c)
    {
        m_conn = c;
    }
     
    public AtmClientConn getClientConnection()
    {
        return m_conn;
    }
     
    public void setClientConnection(AtmClientConn conn)
    {
        m_conn = conn;
    }
 
 
    public long getAccountID()
    {
        return m_accountID;
    }
 
    public void setAccountID(long m_accountID)
    {
        this.m_accountID = m_accountID;
    }
 
    public int getPin()
    {
        return m_pin;
    }
 
    public void setPin(int m_pin)
    {
        this.m_pin = m_pin;
    }
 
    private void log(String msg)
    {
        System.err.println(msg);
    }
 
    public boolean isAuthenticated()
    {
        return !(getAccountID() == -1 || getPin() == -1);
    }
     
    /**
     * Implements the authentication protocol. This consists of checking that
     * the message starts with the NICK command and that the nick following it
     * is not already in use. returns: msg_OK if authenticated msg_NICK_IN_USE
     * if the specified nick is already in use msg_SPECIFY_NICK if the message
     * does not start with the NICK command
     */
    private String authenticate(LoginRequest msg)
    {
            if(add_account(msg.getUserId(), m_conn))
            {
                log("Account " + msg.getUserId() + " added.");
                setAccountID(msg.getUserId());
                setPin(msg.getPin());
                return msg_OK;
            }
            else
            {
                return msg_ACCOUNT_IN_USE;
            }
    }
 
 
    /**
     * Process a message coming from the client
     */
    public String process(String msg)
    {
        LoginRequest request = null;
        String msgCallback =null;
 
        try
        {
            java.io.StringReader reader = new StringReader(msg);
 
            org.jdom2.input.SAXBuilder builder = new SAXBuilder();
            Document dom = builder.build(reader);
            request = (LoginRequest)XmlUtility.xmlToObject(dom);
 
            if(isAuthenticated())
            {
                msgCallback = msg_OK;
            }
            else {
                msgCallback = authenticate(request);
            }
             
        }
        catch(IOException ex)
        {
            log(" Failed: LoginRequest " + ex.getMessage());
            msgCallback = msg_SEND_FAILED;
        }
        catch(JDOMException ex)
        {
            log(" Failed: LoginRequest " + ex.getMessage());
            msgCallback = msg_INVALID;
        }
        catch(AtmException ex)
        {
            log(" Failed: LoginRequest " + ex.getMessage());
            msgCallback = msg_INVALID;
        }
 
        log("Server: processing");
         
        if (msgCallback == msg_OK)
        {
            SystemIdGenerator m_generator = SystemIdGenerator.getInstance();
            long sessionID = m_generator.getNextId();
             
            String response = "<login-response version=\"1.0\" logged-in=\"true\" session-id=\"" + Long.toString(sessionID) + "\" />";
             
            return response;
 
        }
        else
        {
            log("Invalid message: " + msg);
            return msgCallback;
        }
    }
     
    /**
     * Adds an account id to the hash table returns false if the nick is already in the
     * table, true otherwise
     */
    private static boolean add_account(Long accountID, AtmClientConn c)
    {
        if(accountIDS.containsKey(accountIDS))
        {
            return false;
        }
        else
        {
            accountIDS.put(accountID, c);
            return true;
        }
    }
     
    private long m_accountID = -1;
    private int m_pin = -1;
    private AtmClientConn m_conn;
   
    /* a hash table from user nicks to the corresponding connections */
    private static Hashtable<Long, AtmClientConn> accountIDS = new Hashtable<Long, AtmClientConn>();
 
    private static final String msg_OK = "OK";
    private static final String msg_ACCOUNT_IN_USE = "ACCOUNT ALREADY IN USE";
    private static final String msg_INVALID = "INVALID COMMAND";
    private static final String msg_SEND_FAILED = "FAILED TO SEND";
 
}