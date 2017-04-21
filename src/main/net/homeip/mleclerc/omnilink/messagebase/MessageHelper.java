package net.homeip.mleclerc.omnilink.messagebase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.Enum;
import net.homeip.mleclerc.omnilink.enumeration.EnumInfo;
import net.homeip.mleclerc.omnilink.enumeration.NameTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

public class MessageHelper
{
    private final static int POLY = 0xA001;

    public static double omniToTemp(short tempCode)
    {
        return omniToCelcius(tempCode);
    }

    public static short tempToOmni(double temp)
    {
        return celciusToOmni(temp);
    }

    public static double omniToCelcius(short tempCode)
    {
        return (tempCode / 2.0) - 40;
    }

    public static short celciusToOmni(double temp)
    {
        return (short) ((40 + temp) * 2);
    }

    public static double omniToFarenheit(short tempCode)
    {
        return (0.9 * tempCode) - 40;
    }

    public static double omniToHumidity(short humidityCode) {
    	double tempCode = omniToFarenheit(humidityCode);
    	return (tempCode >= 0 && tempCode <= 100) ? tempCode : 0;
    }

    public static short[] getCRC(short[] message)
    {
        short[] ret = new short[2];

        int crc = 0;
        for (int i = 1; i < message.length; i++) // starting with the message length byte
            crc = updateCRC(crc, message[i]);

        ret[0] = lowByte(crc);
        ret[1] = highByte(crc);

        return ret;
    }

    public static int updateCRC(int crc, short data)
    {
        crc = crc ^ data;
        for (int i = 1; i <= 8; i++)
        {
            boolean flag = (crc & 1) != 0;
            crc = crc >> 1;
            if (flag) crc = crc ^ POLY;
        }

        return crc;
    }

    public static short lowByte(int word)
    {
        return (short) (word & 0xFF);
    }

    public static short highByte(int word)
    {
        return (short) ((word & 0xFF00) >> 8);
    }

    public static boolean isBitOn(int value, int bitIndex)
    {
        return (((value >> bitIndex) & 0x01) == 1);
    }

    public static int createWord(short highByte, short lowByte)
    {
        return ((highByte << 8) | lowByte);
    }

    public static short getBits(int value, int bitIndex, int bitCount)
    {
        return (short) ((value >> bitIndex) & ((1 << bitCount) - 1));
    }

    public static short[] userCodeToBytes(String userCode) throws CommunicationException
    {
        short[] data = new short[userCode.length()];
        for (int i = 0; i < userCode.length(); i++)
        {
            char c = userCode.charAt(i);
            if (!Character.isDigit(c))
                throw new CommunicationException("Invalid user code: " + userCode);
            data[i] = (short) Character.getNumericValue(c);
        }
        return data;
    }

    public static void validateRange(int firstNumber, int lastNumber) throws CommunicationException
    {
        if (firstNumber <= 0 || lastNumber <= 0 || firstNumber > lastNumber)
            throw new CommunicationException("Invalid range: [" + firstNumber + ", " + lastNumber + "]");
    }

    public static void validateValue(int value, int min, int max) throws CommunicationException
    {
        if (value < min || value > max)
            throw new CommunicationException("Invalid value: " + value);
    }

    public static void validateEnum(Enum enumeration) throws CommunicationException
    {
        if (enumeration == null) {
            throw new CommunicationException("Invalid enum");
        }
    }

    public static Enum valueToEnum(EnumInfo metaInfo, int value) throws CommunicationException
    {
        Enum ret = metaInfo.getByValue(value);
        if (ret == null)
            throw new CommunicationException("Value not found in enum: " + value);
        return ret;
    }

    public static String getText(int byteIndex, short[] data)
    {
        StringBuffer strbuf = new StringBuffer();

        for(int i = byteIndex; i < data.length; i++)
        {
            short c = data[i];
            if (c == 0)
                break;
            strbuf.append((char) c);
        }

        return strbuf.toString();
    }

    public static char toHouseCode(int houseCodeVal)
    {
        char ret;

        if (houseCodeVal >= 0 && houseCodeVal <= 15)
            ret = (char) ('A' + houseCodeVal);
        else
            ret = 0;

        return ret;
    }

    public static String toRevisionCode(int revision)
    {
        StringBuffer strbuf = new StringBuffer();

        if (revision > 0x00 && revision <= 0x19)
            strbuf.append((char) ('A' + revision - 1));
        else if (revision > 0x7F && revision <= 0xFF)
            strbuf.append('X' + (0xFF - revision + 1));

        return strbuf.toString();
    }

    public static String toPhoneNumber(short[] data, int first, int last)
    {
        StringBuffer strbuf = new StringBuffer();

        for (int i = first; i < last && data[i] != 0 && data[i] != 255; i++)
            strbuf.append((char) data[i]);

        return strbuf.toString();
    }

    public static int getAreaCount(SystemTypeEnum model) throws CommunicationException
    {
        int ret = 0;

        // Validate the model passed in
        validateEnum(model);

        // Extract the security mode for all available areas
        if (model.equals(SystemTypeEnum.HAI_OMNI_LT))
            ret = 1;
        else if (model.equals(SystemTypeEnum.HAI_OMNI) ||
                 model.equals(SystemTypeEnum.HAI_OMNI_II) ||
                 model.equals(SystemTypeEnum.HAI_OMNI_IIE) || model.equals(SystemTypeEnum.HAI_OMNI_IIE_B))
            ret = 2;
        else if (model.equals(SystemTypeEnum.HAI_OMNI_PRO) ||
                 model.equals(SystemTypeEnum.AEGIS_2000) ||
            	model.equals(SystemTypeEnum.HAI_OMNI_PRO_II))        	
            ret = 8;
        else
            throw new CommunicationException("Unrecognized system: " + model);

        return ret;
    }

    public static int getExpansionEnclosureCount(SystemTypeEnum model) throws CommunicationException
    {
        int ret = 0;

        // Validate the model passed in
        validateEnum(model);

        // Extract the security mode for all available areas
        if (model.equals(SystemTypeEnum.HAI_OMNI_LT) ||
        	model.equals(SystemTypeEnum.HAI_OMNI) ||
            model.equals(SystemTypeEnum.HAI_OMNI_II) ||
            model.equals(SystemTypeEnum.HAI_OMNI_IIE) || model.equals(SystemTypeEnum.HAI_OMNI_IIE_B))
            ret = 0;
        else if (model.equals(SystemTypeEnum.HAI_OMNI_PRO) ||
        		 model.equals(SystemTypeEnum.AEGIS_2000))
        	ret = 4;        
        else if (model.equals(SystemTypeEnum.HAI_OMNI_PRO_II))
            ret = 8;
        else
            throw new CommunicationException("Unrecognized system: " + model);

        return ret;
    }

    public static int getMaxZones(SystemTypeEnum model) throws CommunicationException
    {
        int ret = 0;

        // Validate the model passed in
        validateEnum(model);

        // Extract the security mode for all available areas
        if (model.equals(SystemTypeEnum.HAI_OMNI_LT))
            ret = 36;
        else if (model.equals(SystemTypeEnum.HAI_OMNI))
            ret = 45;
        else if (model.equals(SystemTypeEnum.HAI_OMNI_II) ||
                 model.equals(SystemTypeEnum.HAI_OMNI_IIE) || model.equals(SystemTypeEnum.HAI_OMNI_IIE_B))
            ret = 63;
        else if (model.equals(SystemTypeEnum.HAI_OMNI_PRO) ||
                 model.equals(SystemTypeEnum.AEGIS_2000))
            ret = 133;
        else if (model.equals(SystemTypeEnum.HAI_OMNI_PRO_II))
        	ret = 215;
        else
            throw new CommunicationException("Unrecognized system: " + model);

        return ret;
    }

    public static int getItemNameMaxLength(NameTypeEnum type) throws CommunicationException
    {
        // Validate the type passed in
        validateEnum(type);

    	if (type.equals(NameTypeEnum.ZONE))
    	{
    		return 15;
    	}
    	else if (type.equals(NameTypeEnum.UNIT))
    	{
    		return 12;
    	}
    	else if (type.equals(NameTypeEnum.BUTTON))
    	{
    		return 12;
    	}
    	else if (type.equals(NameTypeEnum.CODE))
    	{
    		return 12;
    	}
    	else if (type.equals(NameTypeEnum.AREA))
    	{
    		return 12;
    	}
    	else if (type.equals(NameTypeEnum.THERMOSTAT))
    	{
    		return 12;
    	}
    	else if (type.equals(NameTypeEnum.MESSAGE))
    	{
    		return 15;
    	}
    	else if (type.equals(NameTypeEnum.USER_SETTING))
    	{
    		return 15;
    	}
    	else if (type.equals(NameTypeEnum.READER))
    	{
    		return 15;
    	}
    	else
    	{
            throw new CommunicationException("Invalid type: " + type);
    	}
    }

    public static int getMessageStatusEntryCount(SystemTypeEnum model) throws CommunicationException
    {
    	int messageStatusEntryCount;
    	
        if (model.equals(SystemTypeEnum.HAI_OMNI_LT))
            messageStatusEntryCount = 5;
        else if (model.equals(SystemTypeEnum.HAI_OMNI))
            throw new CommunicationException("This type of message is not supported by HAI Omni");
        else if (model.equals(SystemTypeEnum.HAI_OMNI_II) ||
                 model.equals(SystemTypeEnum.HAI_OMNI_IIE) || model.equals(SystemTypeEnum.HAI_OMNI_IIE_B))
            messageStatusEntryCount = 17;
        else if (model.equals(SystemTypeEnum.HAI_OMNI_PRO) ||
                 model.equals(SystemTypeEnum.AEGIS_2000) ||
                 model.equals(SystemTypeEnum.HAI_OMNI_PRO_II))
            messageStatusEntryCount = 33;
        else
            throw new CommunicationException("Unrecognized system: " + model);
        
        return messageStatusEntryCount;
    }
    
    public static int getExpectedSystemStatusReportLength(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException
    {
        if (model.equals(SystemTypeEnum.HAI_OMNI_LT))
            return 0x10;
        else if (model.equals(SystemTypeEnum.HAI_OMNI) ||
                 model.equals(SystemTypeEnum.HAI_OMNI_II) || 
                 model.equals(SystemTypeEnum.HAI_OMNI_IIE) || model.equals(SystemTypeEnum.HAI_OMNI_IIE_B))
            return 0x11;
        else if (model.equals(SystemTypeEnum.HAI_OMNI_PRO) ||
                 model.equals(SystemTypeEnum.AEGIS_2000))
            return 0x1F;
        else if (model.equals(SystemTypeEnum.HAI_OMNI_PRO_II))
            return 0x27;
        else
            throw new CommunicationException("Unrecognized system: " + model);
    }
    
    public static double celciusToFahrenheit(double tempCelcius)
    {
        return ((9/5) * tempCelcius) + 32;
    }

    public static double fahrenheitToCelcius(double tempFahrenheit)
    {
        return (5/9) * (tempFahrenheit - 32);
    }
    
    public static int hex2decimal(String s) 
    {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) 
        {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }
    
    public static int getCalendarDayOfWeek(int omniLinkDayOfWeek) {
    	switch(omniLinkDayOfWeek) {
	    	case 1: return Calendar.MONDAY;
	    	case 2: return Calendar.TUESDAY;
	    	case 3:	return Calendar.WEDNESDAY;
	    	case 4: return Calendar.THURSDAY;
	    	case 5: return Calendar.FRIDAY;
	    	case 6:	return Calendar.SATURDAY;
	    	case 7: return Calendar.SUNDAY;
    	}
    	
    	return 0;
    }
    
    public static int getOmniLinkDayOfWeek(int calendarDayOfWeek) {
    	switch(calendarDayOfWeek) {
	    	case Calendar.MONDAY: return 1;
	    	case Calendar.TUESDAY: return 2;
	    	case Calendar.WEDNESDAY: return 3;
	    	case Calendar.THURSDAY: return 4;
	    	case Calendar.FRIDAY: return 5;
	    	case Calendar.SATURDAY: return 6;
	    	case Calendar.SUNDAY: return 7;
    	}
    	
    	return 0;
    }
    
    public static int getMaxUnits(SystemTypeEnum model) throws CommunicationException
    {
    	int maxUnitNumber;
    	
        if (model.equals(SystemTypeEnum.HAI_OMNI_LT) ||
        	model.equals(SystemTypeEnum.HAI_OMNI) ||
        	model.equals(SystemTypeEnum.HAI_OMNI_II) ||
            model.equals(SystemTypeEnum.HAI_OMNI_IIE) || model.equals(SystemTypeEnum.HAI_OMNI_IIE_B))
        	maxUnitNumber = 64;
        else if (model.equals(SystemTypeEnum.HAI_OMNI_PRO) ||
                 model.equals(SystemTypeEnum.AEGIS_2000) ||
                 model.equals(SystemTypeEnum.HAI_OMNI_PRO_II))
        	maxUnitNumber = 128;
        else
            throw new CommunicationException("Unrecognized system: " + model);
        
        return maxUnitNumber;
    }
    
    public static <T> String getEnumItemsString(Collection<T> items) {
    	StringBuffer strbuf = new StringBuffer();
        if (items.size() > 0) {
	        for (Iterator<T> iter = items.iterator(); iter.hasNext(); ) {
	        	T option = iter.next();
	            strbuf.append(option);
	            if (iter.hasNext()) {
	            	strbuf.append(", ");
	            }
	        }
        } else {
        	strbuf.append("[None]");
        }
        
        return strbuf.toString();
    }
    
    /**
     * Converts a signed integer to an (unsigned) short value.
     */
    public static short relativeDirectionToByte(int relativeDirection) {
    	if (relativeDirection == 1) {
    		return 1;
    	} else if (relativeDirection == 0){
    		return 0;
    	} else if (relativeDirection == -1) {
    		return 254;
    	}
    	
    	return 0;
    }
    
    public static int getVoiceNameMaxPhraseCount(NameTypeEnum type) throws CommunicationException {
		// Validate the type passed in
		validateEnum(type);

		if (type.equals(NameTypeEnum.ZONE)) {
			return 6;
		} else if (type.equals(NameTypeEnum.UNIT)) {
			return 6;
		} else if (type.equals(NameTypeEnum.BUTTON)) {
			return 6;
		} else if (type.equals(NameTypeEnum.CODE)) {
			return 6;
		} else if (type.equals(NameTypeEnum.AREA)) {
			return 6;
		} else if (type.equals(NameTypeEnum.THERMOSTAT)) {
			return 6;
		} else if (type.equals(NameTypeEnum.MESSAGE)) {
			return 6;
		} else if (type.equals(NameTypeEnum.USER_SETTING)) {
			return 6;
		} else if (type.equals(NameTypeEnum.READER)) {
			return 6;
		} else {
			throw new CommunicationException("Invalid type: " + type);
		}
    }
    
    public static int[] getPhrases(int byteIndex, short[] data)
    {
    	ArrayList<Integer> phrases = new ArrayList<Integer>();

        for(int i = byteIndex; i < data.length; i += 2)
        {
            int phraseNo = createWord(data[i], data[i + 1]);
            if (phraseNo == 0)
                break;
            phrases.add(phraseNo);
        }

        int[] ret = new int[phrases.size()];
        for (int i = 0; i < phrases.size(); i++) {
        	ret[i] = phrases.get(i);
        }
        return ret;
    }
    
    public static short[] textToBytes(String text, int textLength) throws CommunicationException
    {
        short[] data = new short[textLength + 1];
        for (int i = 0; i < textLength; i++)
        {
            char c = text.charAt(i);
            data[i] = (short) c;
        }
        data[textLength] = 0;
        return data;
    }
    
    public static short[] toPhraseData(int[] phrases, int phraseCount) throws CommunicationException
    {
        short[] data = new short[(phraseCount  + 1) * 2];
        for (int i = 0; i < phraseCount; i++)
        {
            data[i * 2] = highByte(phrases[i]);
            data[(i * 2) + 1] = lowByte(phrases[i]);
        }
        data[(phraseCount * 2)] = 0;
        data[(phraseCount * 2) + 1] = 0;
        return data;
    }
    
    public static short byte2short(byte b) {
    	return (short) ((short) b & 0xff);
    }
}