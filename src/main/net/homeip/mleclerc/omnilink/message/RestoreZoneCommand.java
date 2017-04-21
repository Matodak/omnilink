package net.homeip.mleclerc.omnilink.message;

@SuppressWarnings("serial")
public class RestoreZoneCommand extends BaseZoneCommand {
    public RestoreZoneCommand(int zone, int code) {
        super(5, zone, code);
    }
}
