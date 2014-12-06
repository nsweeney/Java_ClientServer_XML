package week12.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class AtmClientConn implements Runnable
{
	/**
	 * Constructor
	 * @param client The client socket to communicate over
	 */
	public AtmClientConn(Socket client)
	{
		try
		{
			/* obtain an input stream to this client ... */
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));

			/* ... and an output stream to the same client */
			out = new PrintWriter(client.getOutputStream(), true);
		}
		catch(IOException e)
		{
			System.err.println(e);
			return;
		}
	}

	@Override
	public void run()
	{
		String msg, response;
		
		// Create a protocol handler instance
		// We have separated the communication protocol from the physical
		// communication channel.
		AtmServerProtocol protocol = new AtmServerProtocol(this);
		
		try
		{
			/*
			 * loop reading lines from the client which are processed according
			 * to our protocol and the resulting response is sent back to the
			 * client
			 */
			while((msg = in.readLine()) != null)
			{
				response = protocol.process(msg);
				System.out.println(response);
				out.println(response);
			}
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
	}

	/**
	 * Send the provided message on the output socket to the client.
	 * @param msg String message to send.
	 */
	public void sendMsg(String msg)
	{
		out.println(msg);
	}
	
	/** The client socket reference */
	public Socket client;
	
	/** Receives input from the client */
	public BufferedReader in;
	
	/** Sends data to the client */
	public PrintWriter out;	
}