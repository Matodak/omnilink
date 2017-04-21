package net.homeip.mleclerc.omnilink;

import java.io.InputStream;
import java.io.OutputStream;

import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHandler;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

public class SerialCommunication extends BaseSerialCommunication implements MessageManager
{
    private MessageHandler messageHandler = new MessageHandlerImpl();

    private SystemTypeEnum model;

    public SerialCommunication(SystemTypeEnum model, String comPortStr, int baudRate)
    {
    	super(comPortStr, baudRate);
    	
        this.model = model;
    }

    public SerialCommunication(SystemTypeEnum model, String comPortStr, int baudRate, String initCmd, String localAccessCmd)
    {
        super(comPortStr, baudRate, initCmd, localAccessCmd);

        this.model = model;
    }
    
    public synchronized void open() throws CommunicationException
    {
    	super.open();
    }

    public synchronized ReplyMessage execute(RequestMessage request) throws CommunicationException
    {
    	// Validate parameters
        if (request == null)
            throw new CommunicationException("Null request passed in");
        else if (is == null ||  os == null)
            throw new CommunicationException("Communication not open");
		
        // Execute the request
        return request.execute(model, ProtocolTypeEnum.HAI_OMNI_LINK, messageHandler);
    }

    public synchronized boolean isOpen()
    {
    	return sPort != null;
    }
    
    public synchronized void close()
    {
    	super.close();
    }

    class MessageHandlerImpl implements MessageHandler
    {    	
		public OutputStream onPreHandleRequest(RequestMessage request) throws CommunicationException 
		{
			return os;
		}
		
		public InputStream onPreHandleReply(ReplyMessage reply)	throws CommunicationException 
		{
			return is;
		}

		public void onPostHandleRequest(RequestMessage request, OutputStream os) throws CommunicationException 
		{
		}
	
		public void onPostHandleReply(ReplyMessage reply, InputStream is)	throws CommunicationException 
		{
		}
    }
}
