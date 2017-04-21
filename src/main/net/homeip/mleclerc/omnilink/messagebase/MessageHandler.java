package net.homeip.mleclerc.omnilink.messagebase;

import java.io.InputStream;
import java.io.OutputStream;

import net.homeip.mleclerc.omnilink.CommunicationException;

public interface MessageHandler 
{
	OutputStream onPreHandleRequest(RequestMessage request) throws CommunicationException;
	void onPostHandleRequest(RequestMessage request, OutputStream os) throws CommunicationException;
	InputStream onPreHandleReply(ReplyMessage reply) throws CommunicationException;
	void onPostHandleReply(ReplyMessage reply, InputStream is) throws CommunicationException;
}
