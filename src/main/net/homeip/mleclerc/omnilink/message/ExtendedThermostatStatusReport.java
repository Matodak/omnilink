package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ThermostatHCHDStatusEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class ExtendedThermostatStatusReport extends ThermostatStatusReport
{
    public class ExtendedThermostatStatusInfo extends ThermostatStatusInfo {
        private double humidity;
        private double humiditySetPoint;
        private double dehumiditySetPoint;
        private double outdoorTemp;
        private ThermostatHCHDStatusEnum hchdStatus;

        public ExtendedThermostatStatusInfo(int number) {
            super(number);
        }

        protected void dataChanged(short[] data) throws CommunicationException {
        	super.dataChanged(data);
        	
			humidity = MessageHelper.omniToHumidity(data[7]);
			humiditySetPoint = MessageHelper.omniToHumidity(data[8]);
			dehumiditySetPoint = MessageHelper.omniToHumidity(data[9]);
			outdoorTemp = MessageHelper.omniToCelcius(data[10]);
			hchdStatus = (ThermostatHCHDStatusEnum) MessageHelper.valueToEnum(ThermostatHCHDStatusEnum.metaInfo, data[11]);
        }

        public double getHumidity() {
        	return humidity;
        }

        public double getHumiditySetPoint() {
        	return humiditySetPoint;
        }

        public double getDehumiditySetPoint() {
        	return dehumiditySetPoint;
        }

        public double getOutdoorTemperature() {
        	return outdoorTemp;
        }
        
        public ThermostatHCHDStatusEnum getHCHDStatus() {
        	return hchdStatus;
        }

        public String toString() {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("HUMIDITY: " + getHumidity() + "\n");
            strbuf.append("HUMIDITY SETPOINT: " + getHumiditySetPoint() + "\n");
            strbuf.append("DEHUMIDITY SETPOINT: " + getDehumiditySetPoint() + "\n");
            strbuf.append("OUTDOOR TEMPERATURE: " + getOutdoorTemperature() + "\n");
            strbuf.append("HCHD STATUS: " + getHCHDStatus() + "\n");
            return strbuf.toString();
        }
    }

    public ExtendedThermostatStatusReport() {
    }

	protected void initialize(SystemTypeEnum model,ProtocolTypeEnum protocolType) throws CommunicationException {
    	ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		super.initialize(model, protocolType, ExtendedObjectStatusReport.MESSAGE_TYPE, 14, ObjectTypeEnum.THERMOSTAT, true);
	}
	
    public Info createInfo(int number, short[] data) {
        return new ExtendedThermostatStatusInfo(number);
    }
}
