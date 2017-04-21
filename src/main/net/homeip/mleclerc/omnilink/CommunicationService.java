package net.homeip.mleclerc.omnilink;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

public class CommunicationService extends UnicastRemoteObject implements MessageManagerRemote
{
	private static final long serialVersionUID = -7077320152541410174L;
	
	private MessageManager delegate;

    public CommunicationService(MessageManager delegate) throws RemoteException
    {
    	this(delegate, 0);
    }

    public CommunicationService(MessageManager delegate, int rmiServicePort) throws RemoteException
    {
    	super(rmiServicePort);
    	
    	this.delegate = delegate;
    }
    
    /**
     * @deprecated
     */
    public CommunicationService(SystemTypeEnum model, String comPortStr, int baudRate) throws CommunicationException, RemoteException
    {
        this(model, comPortStr, baudRate, null, null);
    }

    /**
     * @deprecated
     */
    public CommunicationService(SystemTypeEnum model, String comPortStr, int baudRate, String initCmd, String localAccessCmd) throws CommunicationException, RemoteException
    {
        this(model, comPortStr, baudRate, initCmd, localAccessCmd, 0);
    }

    /**
     * @deprecated
     */
    public CommunicationService(SystemTypeEnum model, String comPortStr, int baudRate, String initCmd, String localAccessCmd, int rmiServicePort) throws CommunicationException, RemoteException
    {
    	this(new SerialCommunication(model, comPortStr, baudRate, initCmd, localAccessCmd), rmiServicePort);
    }

    public void open() throws CommunicationException, RemoteException
    {
    	try
    	{
    		delegate.open();
    	}
    	catch(Exception ex)
    	{
    		throw new RemoteException("Error", ex);
    	}
    }

    public ReplyMessage execute(RequestMessage query) throws CommunicationException, RemoteException
    {
    	try
    	{
            return delegate.execute(query);
    	}
    	catch(Exception ex)
    	{
    		throw new RemoteException("Error", ex);
    	}
    }

    public void close() throws RemoteException
    {
    	try
    	{
            delegate.close();
    	}
    	catch(Exception ex)
    	{
    		throw new RemoteException("Error", ex);
    	}
    }

    public boolean isOpen() throws RemoteException
    {
    	try
    	{
            return delegate.isOpen();
    	}
    	catch(Exception ex)
    	{
    		throw new RemoteException("Error", ex);
    	}
    }
}

