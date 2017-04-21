package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class TroubleTypeEnum extends Enum {
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static TroubleTypeEnum FREEZE = new TroubleTypeEnum("Freeze", 1);
    public final static TroubleTypeEnum BATTERY_LOW = new TroubleTypeEnum("Battery Low", 2);
    public final static TroubleTypeEnum AC_POWER = new TroubleTypeEnum("AC Power", 3);
    public final static TroubleTypeEnum PHONE_LINE = new TroubleTypeEnum("Phone line", 4);
    public final static TroubleTypeEnum DIGITAL_COMMUNICATOR = new TroubleTypeEnum("Digital Communication", 5);
    public final static TroubleTypeEnum FUSE = new TroubleTypeEnum("Fuse", 6);
    public final static TroubleTypeEnum FREEZE_2 = new TroubleTypeEnum("Freeze", 7);
    public final static TroubleTypeEnum BATTERY_LOW_2 = new TroubleTypeEnum("Battery Low", 8);

    public TroubleTypeEnum(String userLabel, int value) {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
