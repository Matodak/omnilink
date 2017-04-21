package net.homeip.mleclerc.omnilink;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class BaseSerialCommunication
{
    private final static String MODULE_NAME = "Communication";
    private final static String OK_STRING = "OK";
    private final static String CONNECT_STRING = "CONNECT";

    private String comPortStr;
    private int baudRate;
    private String initCmd;
    private String localAccessCmd;

    protected SerialPort sPort;
    protected InputStream is;
    protected OutputStream os;

    public BaseSerialCommunication(String comPortStr, int baudRate)
    {
        this.comPortStr = comPortStr;
        this.baudRate = baudRate;
    }

    public BaseSerialCommunication(String comPortStr, int baudRate, String initCmd, String localAccessCmd)
    {
        this(comPortStr, baudRate);

        this.initCmd = initCmd;
        this.localAccessCmd = localAccessCmd;
    }

    protected void open() throws CommunicationException
    {
        try
        {
            openConnection(comPortStr, baudRate);
            if (initCmd != null)
                initConnection(initCmd);
            if (localAccessCmd != null)
                setupConnection(localAccessCmd);
        }
        catch(NoSuchPortException npe)
        {
            throw new CommunicationException(npe);
        }
        catch(PortInUseException piue)
        {
            throw new CommunicationException("Port already in use");
        }
        catch(UnsupportedCommOperationException ucoe)
        {
            throw new CommunicationException(ucoe);
        }
        catch(IOException ioe)
        {
            throw new CommunicationException(ioe);
        }
        catch(InterruptedException ie)
        {
            throw new CommunicationException(ie);
        }
    }

    protected void close()
    {
        if (sPort != null)
        {
            sPort.close();
            try { Thread.sleep(1000); } catch(InterruptedException e) {}
            sPort = null;
        }
    }

    private void openConnection(String comPortStr, int baudRate) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException
    {
        // Obtain a CommPortIdentifier object for the port you want to open.
        CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(comPortStr);

        // Open the port represented by the CommPortIdentifier object. Don't wait
        sPort = (SerialPort) portId.open(MODULE_NAME, 0);

        // Set the parameters of the connection.
        sPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        sPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);

        // Open the input and output streams for the connection. If they won't
        // open, close the port before throwing an exception.
        os = sPort.getOutputStream();
        is = sPort.getInputStream();

        // Set notifyOnDataAvailable to true to allow event driven input.
        sPort.notifyOnDataAvailable(true);

        // Set notifyOnBreakInterrup to allow event driven break handling.
        sPort.notifyOnBreakInterrupt(true);

        // Set receive timeout to allow breaking out of polling loop during
        // input handling.
        sPort.enableReceiveTimeout(30);
    }

    private void initConnection(String initCmd) throws CommunicationException, IOException
    {
        getReplyString();
        os.write(new String(initCmd + "\r").getBytes());
        os.flush();
        String reply = getReplyString();
        if (reply.indexOf(OK_STRING) < 0)
            throw new CommunicationException("Error initializing modem");
    }

    private void setupConnection(String localAccessCmd) throws CommunicationException, IOException, InterruptedException
    {
        os.write(new String(localAccessCmd + "\r").getBytes());
        getReplyString();
        while (true)
        {
            String reply = getReplyString();
            if (reply.indexOf(CONNECT_STRING) >= 0)
                break;
            else if (reply.length() > 0)
                throw new CommunicationException("Error connecting with modem");
        }

        // Wait a little while
        Thread.sleep(1000);
    }

    private String getReplyString() throws IOException
    {
        StringBuffer strbuf = new StringBuffer();
        int data;
        while((data = is.read()) != -1)
            strbuf.append((char) data);

        return strbuf.toString();
    }
}
