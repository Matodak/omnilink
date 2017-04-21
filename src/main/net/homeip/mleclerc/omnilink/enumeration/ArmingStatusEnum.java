package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class ArmingStatusEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static ArmingStatusEnum DISARMED = new ArmingStatusEnum("Disarmed", 0);
    public final static ArmingStatusEnum ARMED = new ArmingStatusEnum("Armed", 1);
    public final static ArmingStatusEnum BYPASSED_BY_USER = new ArmingStatusEnum("Bypassed by User", 2);
    public final static ArmingStatusEnum BYPASSED_BY_SYSTEM = new ArmingStatusEnum("Bypassed by System", 3);

    public ArmingStatusEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
