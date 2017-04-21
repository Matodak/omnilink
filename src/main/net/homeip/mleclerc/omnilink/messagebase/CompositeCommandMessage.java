package net.homeip.mleclerc.omnilink.messagebase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

@SuppressWarnings("serial")
public class CompositeCommandMessage extends CommandMessage
{
    // List of commands to execute
    private List<CommandMessage> messages = new ArrayList<CommandMessage>();

    protected void addCommand(CommandMessage message)
    {
        messages.add(message);
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		// Not invoked
	}

	public ReplyMessage execute(SystemTypeEnum model, ProtocolTypeEnum protocolType, MessageHandler listener) throws CommunicationException
    {
        for (Iterator iter = messages.iterator(); iter.hasNext(); )
        {
            CommandMessage message = (CommandMessage) iter.next();
            message.execute(model, protocolType, listener);
        }

        // If it gets to this point it means that the set of message has
        // been successfully executed
        return createReplyMessage();
    }
}
