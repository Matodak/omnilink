package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class DateFormatEnum extends Enum {
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static DateFormatEnum MMDD = new DateFormatEnum("MMDD", 1);
    public final static DateFormatEnum DDMM = new DateFormatEnum("DDMM", 2);

    public DateFormatEnum(String userLabel, int value) {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
