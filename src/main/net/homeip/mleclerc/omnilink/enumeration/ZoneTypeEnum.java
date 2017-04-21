package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class ZoneTypeEnum extends Enum {
	public final static EnumInfo metaInfo = new EnumInfo();
	
	public final static ZoneTypeEnum ENTRY_EXIT = new ZoneTypeEnum("Entry/Exit", 0);
	public final static ZoneTypeEnum PERIMETER = new ZoneTypeEnum("Perimiter", 1);
	public final static ZoneTypeEnum NIGHT_INTERIOR = new ZoneTypeEnum("Night Interior", 2);
	public final static ZoneTypeEnum AWAY_INTERIOR = new ZoneTypeEnum("Away Interior", 3);
	public final static ZoneTypeEnum DOUBLE_ENTRY_DELAY = new ZoneTypeEnum("Double Entry Delay", 4);
	public final static ZoneTypeEnum QUADRUPLE_ENTRY_DELAY = new ZoneTypeEnum("Quadruple Entry Delay", 5);
	public final static ZoneTypeEnum LATCHING_PERIMETER = new ZoneTypeEnum("Latching Perimeter", 6);
	public final static ZoneTypeEnum LATCHING_NIGHT_INTERIOR = new ZoneTypeEnum("Latching Night Interior", 7);
	public final static ZoneTypeEnum LATCHING_AWAY_INTERIOR = new ZoneTypeEnum("Latching Away Interior", 8);
	public final static ZoneTypeEnum PANIC = new ZoneTypeEnum("Panic", 16);
	public final static ZoneTypeEnum POLICE_EMERGENCY = new ZoneTypeEnum("Police Emergency", 17);
	public final static ZoneTypeEnum DURESS = new ZoneTypeEnum("Duress", 18);
	public final static ZoneTypeEnum TAMPER = new ZoneTypeEnum("Tamper", 19);
	public final static ZoneTypeEnum LATCHING_TAMPER = new ZoneTypeEnum("Latching Tamper", 20);
	public final static ZoneTypeEnum FIRE = new ZoneTypeEnum("Fire", 32);
	public final static ZoneTypeEnum FIRE_EMERGENCY = new ZoneTypeEnum("Fire Emergency", 33);
	public final static ZoneTypeEnum GAS_ALARM = new ZoneTypeEnum("Gas Alarm", 34);
	public final static ZoneTypeEnum AUXILIARY_EMERGENCY = new ZoneTypeEnum("Auxiliary Emergency", 48);
	public final static ZoneTypeEnum TROUBLE = new ZoneTypeEnum("Trouble", 49);
	public final static ZoneTypeEnum FREEZE = new ZoneTypeEnum("Freeze", 54);
	public final static ZoneTypeEnum WATER = new ZoneTypeEnum("Water", 55);
	public final static ZoneTypeEnum FIRE_TAMPER = new ZoneTypeEnum("Fire Tamper", 56);
	public final static ZoneTypeEnum AUXILIARY = new ZoneTypeEnum("Auxiliary", 64);
	public final static ZoneTypeEnum KEYSWITCH_INPUT = new ZoneTypeEnum("Keyswitch Input", 65);
	public final static ZoneTypeEnum PROGRAMMABLE_ENERGY_SAVER_MODULE = new ZoneTypeEnum("Programmable Energy Saver Module", 80);
	public final static ZoneTypeEnum OUTDOOR_TEMPERATURE = new ZoneTypeEnum("Outdoor Temperature", 81);
	public final static ZoneTypeEnum TEMPERATURE = new ZoneTypeEnum("Temperature", 82);
	public final static ZoneTypeEnum TEMPERATURE_ALARM = new ZoneTypeEnum("Temperature Alarm", 83);
	public final static ZoneTypeEnum HUMIDITY = new ZoneTypeEnum("Humidity", 84);
	public final static ZoneTypeEnum EXTENDED_RANGE_OUTDOOR_TEMPERATURE = new ZoneTypeEnum("Extended Range Outdoor Temperature", 85);
	public final static ZoneTypeEnum EXTENDED_RANGE_TEMPERATURE = new ZoneTypeEnum("Extended Range Temperature", 86);
	public final static ZoneTypeEnum EXTENDED_RANGE_TEMPERATURE_ALARM = new ZoneTypeEnum("Extended Range Temperature Alarm", 87);

    public ZoneTypeEnum(String userLabel, int value) {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
