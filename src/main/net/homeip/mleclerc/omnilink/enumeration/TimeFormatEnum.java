package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class TimeFormatEnum extends Enum {
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static TimeFormatEnum TIME_12_HOUR = new TimeFormatEnum("12 HR", 1);
    public final static TimeFormatEnum TIME_24_HOUR = new TimeFormatEnum("24 HR", 2);

    public TimeFormatEnum(String userLabel, int value) {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
