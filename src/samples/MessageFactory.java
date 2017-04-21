import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import net.homeip.mleclerc.omnilink.enumeration.Enum;
import net.homeip.mleclerc.omnilink.enumeration.EnumInfo;
import net.homeip.mleclerc.omnilink.message.AcknowledgeAlertsCommand;
import net.homeip.mleclerc.omnilink.message.AcknowledgeCommand;
import net.homeip.mleclerc.omnilink.message.ActivateKeypadEmergencyCommand;
import net.homeip.mleclerc.omnilink.message.AllUnitsCommand;
import net.homeip.mleclerc.omnilink.message.AuxiliaryStatusRequest;
import net.homeip.mleclerc.omnilink.message.BypassZoneCommand;
import net.homeip.mleclerc.omnilink.message.ClearMessageCommand;
import net.homeip.mleclerc.omnilink.message.ClearNamesCommand;
import net.homeip.mleclerc.omnilink.message.ExtendedObjectStatusRequest;
import net.homeip.mleclerc.omnilink.message.LogMessageCommand;
import net.homeip.mleclerc.omnilink.message.LoginControl;
import net.homeip.mleclerc.omnilink.message.LogoutControl;
import net.homeip.mleclerc.omnilink.message.MacroButtonCommand;
import net.homeip.mleclerc.omnilink.message.MessageStatusRequest;
import net.homeip.mleclerc.omnilink.message.NotificationsCommand;
import net.homeip.mleclerc.omnilink.message.ObjectPropertiesRequest;
import net.homeip.mleclerc.omnilink.message.ObjectStatusRequest;
import net.homeip.mleclerc.omnilink.message.ObjectTypeCapacityRequest;
import net.homeip.mleclerc.omnilink.message.PhoneCommand;
import net.homeip.mleclerc.omnilink.message.ReadEventRecordRequest;
import net.homeip.mleclerc.omnilink.message.ReadNameRequest;
import net.homeip.mleclerc.omnilink.message.ReadSetupRequest;
import net.homeip.mleclerc.omnilink.message.ReadVoiceNameRequest;
import net.homeip.mleclerc.omnilink.message.RestoreZoneCommand;
import net.homeip.mleclerc.omnilink.message.SecurityCodeValidationRequest;
import net.homeip.mleclerc.omnilink.message.SecurityCommand;
import net.homeip.mleclerc.omnilink.message.SetTimeCommand;
import net.homeip.mleclerc.omnilink.message.ShowMessageCommand;
import net.homeip.mleclerc.omnilink.message.SystemEventsRequest;
import net.homeip.mleclerc.omnilink.message.SystemFeaturesRequest;
import net.homeip.mleclerc.omnilink.message.SystemFormatsRequest;
import net.homeip.mleclerc.omnilink.message.SystemInformationRequest;
import net.homeip.mleclerc.omnilink.message.SystemStatusRequest;
import net.homeip.mleclerc.omnilink.message.SystemTroublesRequest;
import net.homeip.mleclerc.omnilink.message.ThermostatCommand;
import net.homeip.mleclerc.omnilink.message.ThermostatFanModeCommand;
import net.homeip.mleclerc.omnilink.message.ThermostatHoldModeCommand;
import net.homeip.mleclerc.omnilink.message.ThermostatMaxTemperatureCommand;
import net.homeip.mleclerc.omnilink.message.ThermostatMinTemperatureCommand;
import net.homeip.mleclerc.omnilink.message.ThermostatStatusRequest;
import net.homeip.mleclerc.omnilink.message.ThermostatSystemModeCommand;
import net.homeip.mleclerc.omnilink.message.UnitCommand;
import net.homeip.mleclerc.omnilink.message.UnitStatusRequest;
import net.homeip.mleclerc.omnilink.message.UploadEventLogMessageRequest;
import net.homeip.mleclerc.omnilink.message.UploadNameMessageRequest;
import net.homeip.mleclerc.omnilink.message.UploadSetupMessageRequest;
import net.homeip.mleclerc.omnilink.message.WriteNameCommand;
import net.homeip.mleclerc.omnilink.message.WriteVoiceNameCommand;
import net.homeip.mleclerc.omnilink.message.ZoneReadyStatusRequest;
import net.homeip.mleclerc.omnilink.message.ZoneStatusRequest;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

public class MessageFactory
{
    private final static String CR = System.getProperty("line.separator");

    class MessageInfo
    {
        private Class msgClass;
        private List<String[]> paramNames = new ArrayList<String[]>();
        private String description;

        public MessageInfo(Class msgClass, String description)
        {
            this.msgClass = msgClass;
            this.description = description;
        }

        public MessageInfo(Class msgClass, String description, String[] paramNames)
        {
            this(msgClass, description);
            this.paramNames.add(paramNames);
        }

        public MessageInfo(Class msgClass, String description, String[] paramNames1, String[] paramNames2)
        {
            this(msgClass, description, paramNames1);
            this.paramNames.add(paramNames2);
        }

        public MessageInfo(Class msgClass, String description, String[] paramNames1, String[] paramNames2, String[] paramNames3)
        {
            this(msgClass, description, paramNames1, paramNames2);
            this.paramNames.add(paramNames3);
        }

        public MessageInfo(Class msgClass, String description, String[] paramNames1, String[] paramNames2, String[] paramNames3, String[] paramNames4)
        {
            this(msgClass, description, paramNames1, paramNames2, paramNames3);
            this.paramNames.add(paramNames4);
        }

        public MessageInfo(Class msgClass, String description, String[] paramNames1, String[] paramNames2, String[] paramNames3, String[] paramNames4, String[] paramNames5)
        {
            this(msgClass, description, paramNames1, paramNames2, paramNames3, paramNames4);
            this.paramNames.add(paramNames5);
        }

        public Class getMsgClass()
        {
            return msgClass;
        }

        public String getDescription()
        {
            return description;
        }

        public String[] getParamNames(Class[] argTypes)
        {
        	for (Iterator iter = paramNames.iterator(); iter.hasNext(); )
        	{
        		String[] params = (String[]) iter.next();
        		if (params.length == argTypes.length)
        		{
        			return params;
        		}
        	}
            return null;
        }
    }
    private Map<String, MessageInfo> msgMap = new TreeMap<String, MessageInfo>();

    public MessageFactory()
    {
        msgMap.put("getsysteminfo", new MessageInfo(SystemInformationRequest.class, "Retrieves system info"));
        msgMap.put("getsystemstatus", new MessageInfo(SystemStatusRequest.class, "Retrieves system status"));
        msgMap.put("getthermostat", new MessageInfo(ThermostatStatusRequest.class, "Retrieves thermostat status", new String[] {"number"}, new String[] {"fromNumber", "toNumber"}, new String[0]));
        msgMap.put("getunit", new MessageInfo(UnitStatusRequest.class, "Retrieves unit status", new String[] {"fromNumber", "toNumber"}, new String[] {"number"}));
        msgMap.put("getzone", new MessageInfo(ZoneStatusRequest.class, "Retrieves zone status", new String[] {"fromNumber", "toNumber"}, new String[] {"number"}));
        msgMap.put("getnames", new MessageInfo(UploadNameMessageRequest.class, "Retrieves unit,zones names"));
        msgMap.put("geteventlog", new MessageInfo(UploadEventLogMessageRequest.class, "Retrieves event log"));
        msgMap.put("checksecuritycode", new MessageInfo(SecurityCodeValidationRequest.class, "Validates security code", new String[] {"usercode", "area"}, new String[] {"usercode"}));
        msgMap.put("setsecurity", new MessageInfo(SecurityCommand.class, "Sets the security mode", new String[] {"mode", "code"}, new String[] {"area", "mode", "code"}, new String[] {"mode"}));
        msgMap.put("setthermostat", new MessageInfo(ThermostatCommand.class, "Sets thermostat status", new String[] {"tempzone", "lowsetpoint", "highsetpoint", "systemmode", "fanmode", "holdmode"}, new String[] {"lowsetpoint", "highsetpoint", "systemmode", "fanmode", "holdmode"}));
        msgMap.put("setallunits", new MessageInfo(AllUnitsCommand.class, "Sets all units status", new String[] {"area", "control"}, new String[] {"control"}));
        msgMap.put("setunit", new MessageInfo(UnitCommand.class, "Sets unit status", new String[] {"number", "control"}, new String[] {"number", "control", "delay"}));
        msgMap.put("setmintemp", new MessageInfo(ThermostatMinTemperatureCommand.class, "Sets thermostat min temperature", new String[] {"number", "lowsetpoint"}, new String[] {"lowsetpoint"}));
        msgMap.put("setmaxtemp", new MessageInfo(ThermostatMaxTemperatureCommand.class, "Sets thermostat max temperature", new String[] {"number", "highsetpoint"}, new String[] {"highsetpoint"}));
        msgMap.put("setsystemmode", new MessageInfo(ThermostatSystemModeCommand.class, "Sets thermostat system mode", new String[] {"number", "systemmode"}, new String[] {"systemmode"}));
        msgMap.put("setfanmode", new MessageInfo(ThermostatFanModeCommand.class, "Sets thermostat fan mode", new String[] {"number", "fanmode"}, new String[] {"fanmode"}));
        msgMap.put("setholdmode", new MessageInfo(ThermostatHoldModeCommand.class, "Sets thermostat hold mode", new String[] {"number", "holdmode"}, new String[] {"holdmode"}));
        msgMap.put("login", new MessageInfo(LoginControl.class, "Logs into the system", new String[] {"usercode"}));
        msgMap.put("logout", new MessageInfo(LogoutControl.class, "Logs out of the system"));
        msgMap.put("getsystemevents", new MessageInfo(SystemEventsRequest.class, "Retrieves system events"));
        msgMap.put("getauxstatus", new MessageInfo(AuxiliaryStatusRequest.class, "Retrieves auxiliary status", new String[] {"fromNumber", "toNumber"}, new String[] {"number"}));
        msgMap.put("getmessagestatus", new MessageInfo(MessageStatusRequest.class, "Retrieves message status"));
        msgMap.put("showmessage", new MessageInfo(ShowMessageCommand.class, "Shows a message", new String[] { "number" }));
        msgMap.put("logmessage", new MessageInfo(LogMessageCommand.class, "Logs a message", new String[] { "number" }));
        msgMap.put("clearmessage", new MessageInfo(ClearMessageCommand.class, "Clears a message", new String[] { "number" }));
        msgMap.put("bypasszone", new MessageInfo(BypassZoneCommand.class, "Bypasses a zone", new String[] { "zone", "code" }));
        msgMap.put("restorezone", new MessageInfo(RestoreZoneCommand.class, "Restores a zone", new String[] { "zone", "code" }));
        msgMap.put("phone", new MessageInfo(PhoneCommand.class, "Phones a number and say message", new String[] { "phone", "message" }));
        msgMap.put("executebutton", new MessageInfo(MacroButtonCommand.class, "Executes a macro button", new String[] { "button" }));
        msgMap.put("ack", new MessageInfo(AcknowledgeCommand.class, "Determines whether the external device is currently logged in."));
        msgMap.put("settime", new MessageInfo(SetTimeCommand.class, "Sets system time", new String[0], new String[] {"time"}, new String[] {"year", "month", "day", "dayOfWeek", "hour", "minute", "dstOn"}));
        msgMap.put("getsetup", new MessageInfo(UploadSetupMessageRequest.class, "Retrieves setup data"));
        msgMap.put("setnotifications", new MessageInfo(NotificationsCommand.class, "Enable notifications", new String[0], new String[] {"enable"}));
        msgMap.put("getsystemtroubles", new MessageInfo(SystemTroublesRequest.class, "Retrieves system troubles"));
        msgMap.put("getsystemfeatures", new MessageInfo(SystemFeaturesRequest.class, "Retrieves system features"));
        msgMap.put("getsystemformats", new MessageInfo(SystemFormatsRequest.class, "Retrieves system formats"));
        msgMap.put("getobjectcapacity", new MessageInfo(ObjectTypeCapacityRequest.class, "Retrieves object capacity", new String[] {"objecttype"}));
        msgMap.put("getobjectproperties", new MessageInfo(ObjectPropertiesRequest.class, "Retrieves object properties", new String[] {"objecttype", "index"}, new String[] {"objecttype", "index", "relativeDirection"}, new String[] {"objecttype", "index", "relativeDirection", "filter1", "filter2", "filter3"}));
        msgMap.put("getobjectstatus", new MessageInfo(ObjectStatusRequest.class, "Retrieves object status", new String[] {"objecttype", "startingObject", "endingObject"}, new String[] {"objecttype", "objectNo"}));
        msgMap.put("getextendedobjectstatus", new MessageInfo(ExtendedObjectStatusRequest.class, "Retrieves object status", new String[] {"objecttype", "startingObject", "endingObject"}, new String[] {"objecttype", "objectNo"}));
        msgMap.put("getzonereadystatus", new MessageInfo(ZoneReadyStatusRequest.class, "Retrieves the zone ready statuses"));
        msgMap.put("geteventrecord", new MessageInfo(ReadEventRecordRequest.class, "Retrieves an event record", new String[] {"eventNo"}, new String[] {"eventNo", "relativeDirection"}));
        msgMap.put("ackalerts", new MessageInfo(AcknowledgeAlertsCommand.class, "Acknowledge any alerts", new String[0], new String[] {"area"}));
        msgMap.put("activatekeypademergency", new MessageInfo(ActivateKeypadEmergencyCommand.class, "Activate a burglary, fire, or auxiliary keypad emergency alarm", new String[] {"emergency type"}, new String[] {"emergency type", "area"}));
        msgMap.put("getname", new MessageInfo(ReadNameRequest.class, "Retrieves a name", new String[] {"nameType", "objectNo"}));
        msgMap.put("getvoicename", new MessageInfo(ReadVoiceNameRequest.class, "Retrieves a voice name", new String[] {"nameType", "objectNo"}));
        msgMap.put("clearnames", new MessageInfo(ClearNamesCommand.class, "Clears the names of all objects"));
        msgMap.put("writename", new MessageInfo(WriteNameCommand.class, "Writes the name of an object", new String[] {"nameType", "objectNo", "text"}));
        msgMap.put("writevoicename", new MessageInfo(WriteVoiceNameCommand.class, "Writes the voice name of an object", new String[] {"nameType", "objectNo", "phrases"}));
        msgMap.put("readsetup", new MessageInfo(ReadSetupRequest.class, "Retrieves setup data", new String[] {"startIndex", "byteCount"}));
    }

    public RequestMessage createRequestMessage(String cmdLine, StringBuffer output)
    {
        StringTokenizer strtok = new StringTokenizer(cmdLine);
        String cmdName = strtok.nextToken();

        try
        {
            MessageInfo msgInfo = (MessageInfo) msgMap.get(cmdName);
            if (msgInfo != null)
            {
                Class msgClass = (Class) msgInfo.getMsgClass();
                List<String> argList = new ArrayList<String>();
                while(strtok.hasMoreTokens())
                {
                    String token = strtok.nextToken();
                    token = token.replace('_', ' ');
                    argList.add(token);
                }

                Constructor[] constrs = msgClass.getConstructors();
                Constructor constr = null;
                for (int i = 0; i < constrs.length; i++)
                {
                    if (constrs[i].getParameterTypes().length == argList.size())
                    {
                        constr = constrs[i];
                        break;
                    }
                }
                if (constr == null)
                {
                    // No arg was specified
                    output.append("Invalid number of arguments" + CR);
                    output.append("Command syntax:" + CR);
                    output.append(getMsgHelp(cmdName) + CR);
                    return null;
                }

                Object args[] = new Object[argList.size()];
                Class[] argTypes = constr.getParameterTypes();
                Iterator iter = argList.iterator();
                for (int i = 0; i < argTypes.length; i++)
                {
                    String arg = (String) iter.next();
                    Class argType = argTypes[i];
                    if (argType == Integer.TYPE)
                        args[i] = Integer.valueOf(arg);
                    else if (argType == Double.TYPE)
                        args[i] = Double.valueOf(arg);
                    else if (argType == String.class)
                        args[i] = arg;
                    else if (Enum.class.isAssignableFrom(argType))
                    {
                        Field field = argType.getField("metaInfo");
                        EnumInfo enumInfo = (EnumInfo) field.get(argType);
                        Enum enumeration = enumInfo.getByUserLabel(arg);
                        args[i] = enumeration;
                    }
                    else if (argType == Boolean.TYPE) {
                    	args[i] = Boolean.valueOf(arg);
                    }
                    else if (argType == Calendar.class) {
                    	long time = Long.parseLong(arg);
                    	Calendar calendar = Calendar.getInstance();
                    	calendar.setTimeInMillis(time);
                    	args[i] = calendar;
                    }
                    else if (argType.isArray() && argType.getComponentType() == Integer.TYPE) {
                    	String[] strValues = arg.split(",");
                    	int[] values = new int[strValues.length];
                    	for (int j = 0; j < strValues.length; j++) {
                    		int intValue = Integer.valueOf(strValues[j]);
                    		values[j] = intValue;
                    	}
                    	args[i] = values;
                    }
                    else
                        output.append("Unknown type: " + argType + CR);
                }

                RequestMessage msg = (RequestMessage) constr.newInstance(args);

                return msg;
            }
            else
            {
                output.append("Invalid command: " + cmdName + CR);
            }
        }
        catch(NumberFormatException nfe)
        {
            output.append("Invalid argument types supplied" + CR);
            output.append("Command syntax:" + CR);
            output.append(getMsgHelp(cmdName) + CR);
        }
        catch(InvocationTargetException ite)
        {
            output.append("Invalid argument types supplied" + CR);
            output.append("Command syntax:" + CR);
            output.append(getMsgHelp(cmdName) + CR);
        }
        catch(NoSuchFieldException nsfe)
        {
            nsfe.printStackTrace();
        }
        catch(IllegalAccessException iae)
        {
            iae.printStackTrace();
        }
        catch(InstantiationException ie)
        {
            ie.printStackTrace();
        }

        return null;
    }

    public String getHelp()
    {
        StringBuffer strbuf = new StringBuffer();

        for (Iterator iter = msgMap.keySet().iterator(); iter.hasNext(); )
        {
            String msgStr = (String) iter.next();
            String msgHelp = getMsgHelp(msgStr);
            strbuf.append(msgHelp);
        }

        return strbuf.toString();
    }

    public String getMsgHelp(String msgStr)
    {
        StringBuffer strbuf = new StringBuffer();

        try
        {
            MessageInfo msgInfo = (MessageInfo) msgMap.get(msgStr);
            Class msgClass = (Class) msgInfo.getMsgClass();

            Constructor[] constrs = msgClass.getConstructors();
            for (int i = 0; i < constrs.length; i++)
            {
                strbuf.append(msgStr);

                Constructor constr = constrs[i];
                Class[] argTypes = constr.getParameterTypes();
                String[] argNames = msgInfo.getParamNames(argTypes);
                for (int j = 0; j < argTypes.length; j++)
                {
                    strbuf.append(" <");
                    Class argType = argTypes[j];
                    String argName = argNames[j];
                    strbuf.append(argName + ":");
                    if (argType == String.class)
                        strbuf.append("string");
                    else if (argType == Calendar.class) {
                    	strbuf.append("calendar");
                    }
                    else if (argType.isArray() && argType.getComponentType() == Integer.TYPE) {
                    	strbuf.append("value1,value2,...,valueN");
                    }
                    else if (Enum.class.isAssignableFrom(argType))
                    {
                        Field field = argType.getField("metaInfo");
                        EnumInfo enumInfo = (EnumInfo) field.get(argType);
                        for (Iterator iter2 = enumInfo.members().iterator(); iter2.hasNext(); )
                        {
                            Enum enumeration = (Enum) iter2.next();
                            String str = enumeration.toString();
                            str = str.replace(' ', '_');
                            strbuf.append(str);
                            if (iter2.hasNext())
                                strbuf.append("|");
                        }
                    }
                    else
                        strbuf.append(argType);
                    strbuf.append(">");
                }
                strbuf.append(": " + msgInfo.getDescription() + "\n");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return strbuf.toString();
    }
}
