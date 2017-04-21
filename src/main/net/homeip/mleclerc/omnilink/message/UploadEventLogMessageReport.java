package net.homeip.mleclerc.omnilink.message;

import java.util.Calendar;
import java.util.Date;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.EventTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.UploadMessageReport;

@SuppressWarnings("serial")
public class UploadEventLogMessageReport extends UploadMessageReport
{
	public static class EventLogInfo extends Info
    {
        private EventTypeEnum type;
        private Date date;
        private int p1;
        private int p2;
        private int offset;

        public EventLogInfo(int number) {
            this(number, 1);
        }

        public EventLogInfo(int number, int offset) {
            super(number);            
            this.offset = offset;
        }

        protected void dataChanged(short[] data) throws CommunicationException
        {
            // Extract the date and time
            if (data[offset] > 0)
            {
                // Extract the date
                short month = data[offset + 1];
                short day = data[offset + 2];
                short hour = data[offset + 3];
                short minute = data[offset + 4];
                
                // Guess the year as it is not contained in the logs
                Calendar currDate = Calendar.getInstance();
                int currMonth = currDate.get(Calendar.MONTH);
                int currYear = currDate.get(Calendar.YEAR);
                int year = (month > currMonth + 1) ? currYear - 1 : currYear;
                
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, day);
                cal.set(Calendar.MONTH, month - 1);
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);
                cal.set(Calendar.YEAR, year);

                date = cal.getTime();
            }

            // Extract the event type
            type = (EventTypeEnum) MessageHelper.valueToEnum(EventTypeEnum.metaInfo, data[offset + 5]);

            // Extract P1
            p1 = data[offset + 6];

            // Extract P2
            p2 = MessageHelper.createWord(data[offset + 7], data[offset + 8]);
        }

        public EventTypeEnum getType()
        {
            return type;
        }

        public Date getDate()
        {
            return date;
        }

        public int getP1()
        {
            return p1;
        }

        public int getP2()
        {
            return p2;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("TYPE: " + getType() + "\n");
            strbuf.append("DATE: " + getDate() + "\n");
            strbuf.append("P1: " + getP1() + "\n");
            strbuf.append("P2: " + getP2() + "\n");
            return strbuf.toString();
        }
    }

    protected int getSmallReplyId(short[] data)
    {
        return data[0];
    }

    protected Info createInfo(int number, short[] data)
    {
        return new EventLogInfo(number);
    }
}
