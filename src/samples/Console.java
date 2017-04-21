import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.MessageManager;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

public class Console
{
    private MessageManager comm;
    private MessageFactory msgFactory = new MessageFactory();
    private final static String CR = System.getProperty("line.separator");

    public Console(MessageManager msgmgr)
    {
        this.comm = msgmgr;
    }

    public String parseAndExecuteCommand(String line) throws CommunicationException, Exception
    {
        StringBuffer strbuf = new StringBuffer();

        if (line != null)
        {
            if (line.length() == 0)
            {
                // Go to next line
            }
            else if (line.equals("help"))
            {
                strbuf.append("Available commands:" + CR);
                strbuf.append("open: Opens new session" + CR);
                strbuf.append("close: Closes current session" + CR);
                strbuf.append("help: Displays this" + CR);
                strbuf.append(msgFactory.getHelp());
            }
            else if (line.equals("open"))
            {
                if (!comm.isOpen())
                {
                    comm.open();
                    strbuf.append("Communication established" + CR);
                }
                else
                    strbuf.append("Session already open" + CR);
            }
            else if (line.equals("close"))
            {
                if (comm.isOpen())
                {
                    comm.close();
                    strbuf.append("Session closed" + CR);
                }
                else
                    strbuf.append("No session currently open" + CR);
            }
            else
            {
                StringBuffer output = new StringBuffer();
                RequestMessage msg = msgFactory.createRequestMessage(line, output);
                if (msg != null)
                {
                    if (comm.isOpen())
                    {
                        ReplyMessage reply = comm.execute(msg);
                        if (reply != null)
                            strbuf.append(reply + CR);
                        else
                            strbuf.append("Invalid reply" + CR);
                    }
                    else
                        strbuf.append("No session currently open" + CR);
                }
                else
                    strbuf.append(output);
            }
        }

        return strbuf.toString();
    }

    public void run()
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        run(reader);
    }

    public void run(BufferedReader reader)
    {
        while(true)
        {
            try
            {
                System.out.print("command> ");
                String line = reader.readLine();
                if (line == null)
                {
					if (comm.isOpen())
					{
					    comm.close();
					}
                    System.out.println("Exiting.");
                    break;
                }

                line = line.trim();
                line = line.toLowerCase();
                if (line.equals("exit") || line.equals("quit"))
                {
					if (comm.isOpen())
					{
					    comm.close();
					}
					System.out.println("Exiting.");
					break;
                }

                String result = parseAndExecuteCommand(line);
                System.out.print(result);
                if (line.equals("help"))
                {
                    System.out.println("exit: Exits the console");
                }
            }
            catch(CommunicationException ace)
            {
                System.err.println(ace.getMessage());
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
