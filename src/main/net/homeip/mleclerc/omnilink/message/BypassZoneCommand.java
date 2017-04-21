package net.homeip.mleclerc.omnilink.message;

@SuppressWarnings("serial")
public class BypassZoneCommand extends BaseZoneCommand
{
    public BypassZoneCommand(int zone, int code) {
        super(4, zone, code);
    }
}
