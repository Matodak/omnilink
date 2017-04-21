package net.homeip.mleclerc.omnilink.message;

import java.util.ArrayList;
import java.util.Collection;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.FeatureTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ExpectedReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class SystemFeaturesReport extends ExpectedReplyMessage {
	private int featureCount;
	Collection<FeatureTypeEnum> features = new ArrayList<FeatureTypeEnum>();
	
	protected SystemFeaturesReport() {
	}
	
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		super.initialize(model, protocolType, 0x1D, 0); // Variable message length
	}

	protected void checkMessageLength(int messageLength) throws CommunicationException {
		featureCount = messageLength - 1;
	}
	
	protected void dataChanged(short[] data) throws CommunicationException {
		for (int i = 0; i < featureCount; i++) {
			int featureId = data[i];
			FeatureTypeEnum feature = (FeatureTypeEnum) MessageHelper.valueToEnum(FeatureTypeEnum.metaInfo, featureId);
			features.add(feature);
		}
	}

	public Collection<FeatureTypeEnum> getFeatures() {
		return features;
	}
	
	public String toString() {
		StringBuffer strbuf = new StringBuffer();
        strbuf.append("FEATURES: " + MessageHelper.getEnumItemsString(getFeatures()) + "\n");
        return strbuf.toString();
	}
}
