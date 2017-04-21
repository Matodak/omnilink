import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.SerialCommunication;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

public class SerialMain
{
    private final static SystemTypeEnum SYSTEM_TYPE = SystemTypeEnum.AEGIS_2000;
    private final static String COM_PORT = "COM1";
    private final static int BAUD_RATE = 9600;
    private final static String INIT_STRING = null; // For modem connection, use "AT&FX0S0=0S10=20&C1&D2";
    private final static String CONNECT_STRING = null; // For modem connection, use "ATS11=150DT###1" + LOGIN_CODE;

    public static void main(String[] args) throws CommunicationException
    {
        SerialCommunication comm = new SerialCommunication(SYSTEM_TYPE, COM_PORT, BAUD_RATE, INIT_STRING, CONNECT_STRING);
        Console cons = new Console(comm);
        cons.run();
    }
}
