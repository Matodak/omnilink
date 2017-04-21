import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.NetworkCommunication;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.Message;

public class NetworkMain
{
    private final static SystemTypeEnum SYSTEM_TYPE = SystemTypeEnum.AEGIS_2000;
    private final static String IP_ADDRESS = "192.168.1.3";
    private final static int PORT = 4369;
    private final static String PRIVATE_KEY = "a0-b1-c2-d3-e4-f5-a6-b7-c8-d9-e0-f1-a2-b3-c4-d5";
    private final static ProtocolTypeEnum PROTOCOL_TYPE = ProtocolTypeEnum.HAI_OMNI_LINK; // or ProtocolTypeEnum.HAI_OMNI_LINK_II for Omni-Link II

    public static void main(String[] args) throws CommunicationException
    {
    	NetworkCommunication comm = new NetworkCommunication(SYSTEM_TYPE, IP_ADDRESS, PORT, NetworkCommunication.DEFAULT_TIMEOUT, PRIVATE_KEY, PROTOCOL_TYPE);
    	if (PROTOCOL_TYPE == ProtocolTypeEnum.HAI_OMNI_LINK_II) {
    		// Register a listener to receive notifications from the controller
	        comm.addListener(new NetworkCommunication.NotificationListener() {
				public void notify(Message notification) {
					System.out.println("Notification: " + notification);
				}
			});
    	}
        Console cons = new Console(comm);
        cons.run();
    }
}
