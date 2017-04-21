package net.homeip.mleclerc.omnilink;

@SuppressWarnings("serial")
public class CommunicationException extends Exception
{
    public CommunicationException(String message)
    {
        super(message);
    }

    public CommunicationException(Exception cause)
    {
        super(cause);
    }
}
