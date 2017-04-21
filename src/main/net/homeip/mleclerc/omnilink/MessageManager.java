package net.homeip.mleclerc.omnilink;

import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

public interface MessageManager
{
    public void open() throws CommunicationException, Exception;
    public ReplyMessage execute(RequestMessage request) throws CommunicationException, Exception;
    public void close() throws Exception;
    public boolean isOpen() throws Exception;
}
