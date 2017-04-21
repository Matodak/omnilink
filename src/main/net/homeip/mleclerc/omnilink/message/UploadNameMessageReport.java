package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.NameTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.UploadMessageReport;

@SuppressWarnings("serial")
public class UploadNameMessageReport extends UploadMessageReport
{
    private int nameNo = 1;

    public static class NameInfo extends Info
    {
        private NameTypeEnum type;
        private int item;
        private String text;

        public NameInfo(int number)
        {
            super(number);
        }

        protected void dataChanged(short[] data) throws CommunicationException
        {
            // Extract the item type
            type = (NameTypeEnum) MessageHelper.valueToEnum(NameTypeEnum.metaInfo, data[0]);

            // Get the maximum length of the item name
            int itemNameMaxLength = MessageHelper.getItemNameMaxLength(type);
           
            // Determine the number of bytes used to stored the item number
            if (data.length == itemNameMaxLength + 3) // 3 => 1 byte for type + 1 byte for item # + 1 byte terminating zero 
            {
            	// Item number is stored as one byte
            	
                // Extract the item number
                item = data[1];
                
                // Extract the item name
                text = MessageHelper.getText(2, data);
            }
            else if (data.length == itemNameMaxLength + 4) // 4 => 1 byte for type + 2 bytes for item # + 1 byte terminating zero 
            {
            	// Item number is stored as two bytes
            	item = MessageHelper.createWord(data[1], data[2]);
            	
                // Extract the item name
                text = MessageHelper.getText(3, data);
            }
        }

        public int getItem()
        {
            return item;
        }
        
		public int getNumber() {
			return item;
		}

		public int getObjectNo() {
			return item;
		}

        public NameTypeEnum getType()
        {
            return type;
        }

        public String getText()
        {
            return text;
        }
        
        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("TYPE: " + getType() + "\n");
            strbuf.append("TEXT: " + getText() + "\n");
            return strbuf.toString();
        }
    }

    protected int getSmallReplyId(short[] data)
    {
        // There is no unique ID stored in this type of reply.
        // Generate one instead
        return nameNo++;
    }

    protected Info createInfo(int number, short[] data)
    {
        return new NameInfo(number);
    }
}
