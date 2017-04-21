package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class NetworkMessageTypeEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static NetworkMessageTypeEnum NO_MESSAGE = new NetworkMessageTypeEnum("No message", 0);
    public final static NetworkMessageTypeEnum CLIENT_REQUEST_NEW_SESSION = new NetworkMessageTypeEnum("Client request new session", 1);
    public final static NetworkMessageTypeEnum CONTROLLER_ACKNOWLEDGE_NEW_SESSION = new NetworkMessageTypeEnum("Controller acknowledge new session", 2);
    public final static NetworkMessageTypeEnum CLIENT_REQUEST_SECURE_CONNECTION = new NetworkMessageTypeEnum("Client request secure connection", 3);
    public final static NetworkMessageTypeEnum CONTROLLER_ACKNOWLEDGE_SECURE_CONNECTION = new NetworkMessageTypeEnum("Controller acknowledge secure connection", 4);
    public final static NetworkMessageTypeEnum CLIENT_SESSION_TERMINATED = new NetworkMessageTypeEnum("Client session terminated", 5);
    public final static NetworkMessageTypeEnum CONTROLLER_SESSION_TERMINATED = new NetworkMessageTypeEnum("Controller session terminated", 6);
    public final static NetworkMessageTypeEnum CONTROLLER_CANNOT_START_NEW_SESSION = new NetworkMessageTypeEnum("Controller cannot start new session", 7);
    public final static NetworkMessageTypeEnum OMNI_LINK_MESSAGE = new NetworkMessageTypeEnum("Omni-Link message", 16); 
    public final static NetworkMessageTypeEnum OMNI_LINK_MESSAGE_II = new NetworkMessageTypeEnum("Omni-Link message", 32); 
    
    public NetworkMessageTypeEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
