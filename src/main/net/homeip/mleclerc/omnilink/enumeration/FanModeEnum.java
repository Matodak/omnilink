package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class FanModeEnum extends Enum
{
     public final static EnumInfo metaInfo = new EnumInfo();

     public final static FanModeEnum AUTO = new FanModeEnum("Auto", 0);
     public final static FanModeEnum ON = new FanModeEnum("On", 1);
     public final static FanModeEnum CYCLE = new FanModeEnum("On", 2);

     public FanModeEnum(String userLabel, int value)
     {
         super(userLabel, value);
         metaInfo.add(this);
    }
}
