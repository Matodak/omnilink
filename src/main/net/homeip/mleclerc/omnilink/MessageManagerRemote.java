package net.homeip.mleclerc.omnilink;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

public interface MessageManagerRemote extends MessageManager, Remote
{
    public final static String SERVICE_NAME = "Communication";

    public void open() throws CommunicationException, RemoteException;
    public ReplyMessage execute(RequestMessage query) throws CommunicationException, RemoteException;
    public void close() throws RemoteException;
    public boolean isOpen() throws RemoteException;
}
