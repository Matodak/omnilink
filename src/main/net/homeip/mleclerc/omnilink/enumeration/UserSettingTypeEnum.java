package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class UserSettingTypeEnum extends Enum {
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static UserSettingTypeEnum NOT_USED = new UserSettingTypeEnum("Not Used", 0);
    public final static UserSettingTypeEnum NUMBER = new UserSettingTypeEnum("Number", 1);
    public final static UserSettingTypeEnum DURATION = new UserSettingTypeEnum("Duration", 2);
    public final static UserSettingTypeEnum TEMPERATURE = new UserSettingTypeEnum("Temperature", 3);
    public final static UserSettingTypeEnum HUMIDITY = new UserSettingTypeEnum("Humidity", 4);
    public final static UserSettingTypeEnum DAY = new UserSettingTypeEnum("Day", 5);
    public final static UserSettingTypeEnum TIME = new UserSettingTypeEnum("Time", 6);
    public final static UserSettingTypeEnum DAY_OF_WEEK = new UserSettingTypeEnum("Day of Week", 7);
    public final static UserSettingTypeEnum LEVEL = new UserSettingTypeEnum("Levely", 8);

    public UserSettingTypeEnum(String userLabel, int value) {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
