import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.NetworkCommunicationServer;

public class NetworkServer 
{
    private final static String COM_PORT = "COM1";
    private final static int BAUD_RATE = 9600;
    private final static String INIT_STRING = null; // For modem connection, use "AT&FX0S0=0S10=20&C1&D2";
    private final static String CONNECT_STRING = null; // For modem connection, use "ATS11=150DT###1" + LOGIN_CODE;

    private final static int PORT = 4369;
    private final static String PRIVATE_KEY = "a0-b1-c2-d3-e4-f5-a6-b7-c8-d9-e0-f1-a2-b3-c4-d5";

	public static void main(String[] args) throws CommunicationException
	{
		NetworkCommunicationServer app = new NetworkCommunicationServer(COM_PORT, BAUD_RATE, INIT_STRING, CONNECT_STRING, PORT, PRIVATE_KEY);
		app.run();
	}
}
