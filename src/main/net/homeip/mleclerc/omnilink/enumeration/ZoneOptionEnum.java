package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class ZoneOptionEnum extends Enum {
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static ZoneOptionEnum CROSS_ZONING = new ZoneOptionEnum("Cross Zoning", 0);
    public final static ZoneOptionEnum SWINGER_SHUTDOWN = new ZoneOptionEnum("Swinger Shutdown", 1);
    public final static ZoneOptionEnum DIAL_OUT_DELAY = new ZoneOptionEnum("Dial Out Delay", 2);

    public ZoneOptionEnum(String userLabel, int value) {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
