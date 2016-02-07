/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nauck.util;

/**
 *
 * @author Volodymyr
 */

import java.io.IOException;
import java.io.OutputStream;

// Referenced classes of package nauck.util:
//            HelpNotAvailableException

public class Help
{

    protected String browser;
    protected String helpFile;
    Process browserProcess;
    boolean browserIsRunning;

    public Help(String s, String s1)
    {
        browser = s;
        helpFile = s1;
        browserProcess = null;
        browserIsRunning = false;
    }

    public void show()
        throws HelpNotAvailableException
    {
        show(null);
    }

    public void show(String s)
        throws HelpNotAvailableException
    {
        String s1 = helpFile;
        if(s != null && s.length() > 0)
        {
            s1 = s1 + "#" + s;
        }
        try
        {
            browserProcess = Runtime.getRuntime().exec(browser + " " + helpFile);
            browserIsRunning = true;
            return;
        }
        catch(IOException _ex)
        {
            browserIsRunning = false;
        }
        throw new HelpNotAvailableException("Invalid command: the browser could not be started.");
    }

    public void setBrowser(String s)
    {
        browser = s;
    }

    public String getBrowser()
    {
        return browser;
    }

    public void setHelpFile(String s)
    {
        helpFile = s;
    }

    public String getHelpFile()
    {
        return helpFile;
    }

    void callHelp()
    {
        if(browserIsRunning)
        {
            try
            {
                browserProcess.exitValue();
                browserIsRunning = false;
            }
            catch(IllegalThreadStateException _ex)
            {
                if(browserIsRunning)
                {
                    try
                    {
                        browserProcess.getOutputStream().write(helpFile.getBytes());
                        browserProcess.getOutputStream().flush();
                    }
                    catch(IOException _ex2)
                    {
                        browserIsRunning = false;
                    }
                    catch(NullPointerException _ex2)
                    {
                        browserIsRunning = false;
                    }
                }
            }
        }
        if(!browserIsRunning)
        {
            try
            {
                browserProcess = Runtime.getRuntime().exec(browser + " " + helpFile);
                browserIsRunning = true;
                return;
            }
            catch(IOException _ex)
            {
                browserIsRunning = false;
            }
            return;
        } else
        {
            return;
        }
    }
}
