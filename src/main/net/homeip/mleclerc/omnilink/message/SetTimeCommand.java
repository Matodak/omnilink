package net.homeip.mleclerc.omnilink.message;

import java.util.Calendar;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.AcknowledgeReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class SetTimeCommand extends RequestMessage {
	private int year;
	private int month;
	private int day;
	private int dayOfWeek;
	private int hour;
	private int minute;
	private boolean dstOn;
	
	public SetTimeCommand() {
		// Updates system to current time
		this(Calendar.getInstance());
	}

	public SetTimeCommand(Calendar calendar) {		
		year = calendar.get(Calendar.YEAR) - 2000;
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
		dayOfWeek = MessageHelper.getOmniLinkDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		dstOn = calendar.getTimeZone().inDaylightTime(calendar.getTime());
	}
	
	public SetTimeCommand(int year, int month, int day, int dayOfWeek, int hour, int minute, boolean dstOn) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.dayOfWeek = dayOfWeek;
		this.hour = hour;
		this.minute = minute;
		this.dstOn = dstOn;
	}
	
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		MessageHelper.validateValue(year, 0, 99);
		MessageHelper.validateValue(month, 1, 12);
		MessageHelper.validateValue(day, 1, 31);
		MessageHelper.validateValue(dayOfWeek, 1, 7);
		MessageHelper.validateValue(hour, 0, 23);
		MessageHelper.validateValue(minute, 0, 59);
		
		short[] data = new short[7];
		data[0] = (short) year;
		data[1] = (short) month;
		data[2] = (short) day;
		data[3] = (short) dayOfWeek;
		data[4] = (short) hour;
		data[5] = (short) minute;
		data[6] = (short) (dstOn ? 1: 0);
		
		if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
			super.initialize(model, protocolType, 0x13, 0x08, data);
		} else {
			super.initialize(model, protocolType, 0x10, 0x08, data);
		}
	}

	protected ReplyMessage createReplyMessage() {
        return new AcknowledgeReplyMessage();
    }
}
