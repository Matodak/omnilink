package net.homeip.mleclerc.omnilink.messagebase;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

@SuppressWarnings("serial")
public abstract class UploadMessageRequest extends RequestMessage
{
    protected abstract ReplyMessage createSmallReplyMessage();

    public ReplyMessage execute(SystemTypeEnum model, ProtocolTypeEnum protocolType, MessageHandler listener) throws CommunicationException
    {
        // Send the request to the controller
        super.sendRequest(model, protocolType, listener);

        // Create the return object
        UploadMessageReport report = (UploadMessageReport) createReplyMessage();
        
        while(true)
        {
            ReplyMessage reply = createSmallReplyMessage();
            
            // Parse the small reply
            if (!reply.execute(model, protocolType, listener))
                break;

            // Add the info to the report
            report.addData(reply.getData());

            // Send an ack to the controller
            RequestMessage ack = new AcknowledgeMessage();
            ack.sendRequest(model, protocolType, listener);
        }

        report.onCompleted();

        return report;
    }
}
