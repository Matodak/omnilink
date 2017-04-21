package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.DateFormatEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.TemperatureFormatEnum;
import net.homeip.mleclerc.omnilink.enumeration.TimeFormatEnum;
import net.homeip.mleclerc.omnilink.messagebase.ExpectedReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class SystemFormatsReport extends ExpectedReplyMessage {
	private TemperatureFormatEnum temperatureFormat;
	private TimeFormatEnum timeFormat;
	private DateFormatEnum dateFormat;
	
	protected SystemFormatsReport() {
	}
	
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		super.initialize(model, protocolType, 0x29, 0x04);
	}

	protected void dataChanged(short[] data) throws CommunicationException {
		short temperatureFormatVal = data[0];
		temperatureFormat = (TemperatureFormatEnum) MessageHelper.valueToEnum(TemperatureFormatEnum.metaInfo, temperatureFormatVal);
		short timeFormatVal = data[1];
		timeFormat = (TimeFormatEnum) MessageHelper.valueToEnum(TimeFormatEnum.metaInfo, timeFormatVal);
		short dateFormatVal = data[2];
		dateFormat = (DateFormatEnum) MessageHelper.valueToEnum(DateFormatEnum.metaInfo, dateFormatVal);
	}

	public TemperatureFormatEnum getTemperatureFormat() {
		return temperatureFormat;
	}

	public TimeFormatEnum getTimeFormat() {
		return timeFormat;
	}

	public DateFormatEnum getDateFormat() {
		return dateFormat;
	}

	public String toString() {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append("TEMPERATURE FORMAT: " + getTemperatureFormat() + "\n");
        strbuf.append("TIME FORMAT: " + getTimeFormat() + "\n");
        strbuf.append("DATE FORMAT: " + getDateFormat() + "\n");
        return strbuf.toString();
	}
}
