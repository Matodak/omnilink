import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.MessageManager;
import net.homeip.mleclerc.omnilink.SerialCommunication;
import net.homeip.mleclerc.omnilink.enumeration.BasicUnitControlEnum;
import net.homeip.mleclerc.omnilink.enumeration.FanModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.HoldModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SecurityModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.UnitControlEnum;
import net.homeip.mleclerc.omnilink.message.AllUnitsCommand;
import net.homeip.mleclerc.omnilink.message.AuxiliaryStatusReport;
import net.homeip.mleclerc.omnilink.message.AuxiliaryStatusRequest;
import net.homeip.mleclerc.omnilink.message.LoginControl;
import net.homeip.mleclerc.omnilink.message.LogoutControl;
import net.homeip.mleclerc.omnilink.message.MessageStatusReport;
import net.homeip.mleclerc.omnilink.message.MessageStatusRequest;
import net.homeip.mleclerc.omnilink.message.SecurityCommand;
import net.homeip.mleclerc.omnilink.message.SystemEventsReport;
import net.homeip.mleclerc.omnilink.message.SystemEventsRequest;
import net.homeip.mleclerc.omnilink.message.SystemInformationReport;
import net.homeip.mleclerc.omnilink.message.SystemInformationRequest;
import net.homeip.mleclerc.omnilink.message.SystemStatusReport;
import net.homeip.mleclerc.omnilink.message.SystemStatusRequest;
import net.homeip.mleclerc.omnilink.message.ThermostatCommand;
import net.homeip.mleclerc.omnilink.message.ThermostatStatusReport;
import net.homeip.mleclerc.omnilink.message.ThermostatStatusRequest;
import net.homeip.mleclerc.omnilink.message.UnitCommand;
import net.homeip.mleclerc.omnilink.message.UnitStatusReport;
import net.homeip.mleclerc.omnilink.message.UnitStatusRequest;
import net.homeip.mleclerc.omnilink.message.UploadEventLogMessageReport;
import net.homeip.mleclerc.omnilink.message.UploadEventLogMessageRequest;
import net.homeip.mleclerc.omnilink.message.UploadNameMessageReport;
import net.homeip.mleclerc.omnilink.message.UploadNameMessageRequest;
import net.homeip.mleclerc.omnilink.message.ZoneStatusReport;
import net.homeip.mleclerc.omnilink.message.ZoneStatusRequest;

class SerialTestDriver
{
    // Constants used by the test program
    private final static String LOGIN_CODE = "1234";  
    private final static SystemTypeEnum SYSTEM_TYPE = SystemTypeEnum.AEGIS_2000;

    private final static String COM_PORT = "COM1";
    private final static int BAUD_RATE = 9600;
    private final static String INIT_STRING = null; // For modem connection, use "AT&FX0S0=0S10=20&C1&D2";
    private final static String CONNECT_STRING = null; // For modem connection, use "ATS11=150DT###1" + LOGIN_CODE;

    private final static int AREA = 1;
    private final static int FIRST_ZONE = 1;
    private final static int LAST_ZONE = 30;
    private final static int UNIT = 7;
    private final static double MIN_TEMP = 16;
    private final static double MAX_TEMP = 23; // Temperatures are given in degres celcius
                                               // use MessageHelper.celciusToFahrenheit()
                                               // and MessageHelper.fahrenheitToCelcius()
                                               // to convert to fahrenheit.

    public static void main(String[] args)
    {
        MessageManager comm = null;

        try
        {
            // Open communication with the system
            comm = new SerialCommunication(SYSTEM_TYPE, COM_PORT, BAUD_RATE, INIT_STRING, CONNECT_STRING);
            comm.open();

            // User login
            String loginCode = (args.length == 1) ? args[0] : LOGIN_CODE;
            comm.execute(new LoginControl(loginCode));

            // Download names from system
            UploadNameMessageReport reply1 = (UploadNameMessageReport) comm.execute(new UploadNameMessageRequest());
            System.out.println(reply1);

            // Retrieve system info
            SystemInformationReport reply2 = (SystemInformationReport) comm.execute(new SystemInformationRequest());
            System.out.println(reply2);

            // Retrieve system status
            SystemStatusReport reply3 = (SystemStatusReport) comm.execute(new SystemStatusRequest());
            System.out.println(reply3);

            // Retrieve zone status (range)
            ZoneStatusReport reply4 = (ZoneStatusReport) comm.execute(new ZoneStatusRequest(FIRST_ZONE, LAST_ZONE));
            System.out.println(reply4);

            // Retrieve unit status
            UnitStatusReport reply5 = (UnitStatusReport) comm.execute(new UnitStatusRequest(UNIT));
            System.out.println(reply5);

            // Message status
            MessageStatusReport reply6 = (MessageStatusReport) comm.execute(new MessageStatusRequest());
            System.out.println(reply6);

            // Auxiliary status
            AuxiliaryStatusReport reply7 = (AuxiliaryStatusReport) comm.execute(new AuxiliaryStatusRequest(1, 1));
            System.out.println(reply7);

            // Thermostat status
            ThermostatStatusReport reply8 = (ThermostatStatusReport) comm.execute(new ThermostatStatusRequest(1));
            System.out.println(reply8);

            // Download latest system events
            SystemEventsReport reply9 = (SystemEventsReport) comm.execute(new SystemEventsRequest());
            System.out.println(reply9);

            // Download event log from system (takes a little while)
            UploadEventLogMessageReport reply10 = (UploadEventLogMessageReport) comm.execute(new UploadEventLogMessageRequest());
            System.out.println(reply10);

            // Turn on unit
            comm.execute(new UnitCommand(UNIT, UnitControlEnum.ON));

            // Disarm security system
            comm.execute(new SecurityCommand(AREA, SecurityModeEnum.DISARM, AREA));

            // Change current temperature on thermostat
            comm.execute(new ThermostatCommand(AREA, MIN_TEMP, MAX_TEMP, SystemModeEnum.OFF, FanModeEnum.AUTO, HoldModeEnum.HOLD));

            // Turn on all units
            comm.execute(new AllUnitsCommand(BasicUnitControlEnum.ON));
            
            // Turn off all units
            comm.execute(new AllUnitsCommand(BasicUnitControlEnum.OFF));

            // User logout
            comm.execute(new LogoutControl());
        }
        catch(CommunicationException ace)
        {
            ace.printStackTrace();
        }
        catch(Exception ace)
        {
            ace.printStackTrace();
        }
        finally
        {
            if (comm != null)
            {
            	try
            	{
                    comm.close();
            	}
            	catch(Exception ex)
            	{
            		ex.printStackTrace();
            	}
            }
        }
    }
}
