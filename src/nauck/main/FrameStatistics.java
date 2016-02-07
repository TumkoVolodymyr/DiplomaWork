package nauck.main;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.EventObject;
import nauck.util.Help;
import nauck.util.HelpNotAvailableException;
import symantec.itools.awt.ButtonBase;
import symantec.itools.awt.LabelButton;

public class FrameStatistics extends Frame
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == FrameStatistics.this)
            {
                Frame1_WindowClosing(windowevent);
            }
        }

        SymWindow()
        {
        }
    }

    class SymAction
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            Object obj = actionevent.getSource();
            if(obj == labelButtonStatisticsClose)
            {
                labelButtonStatisticsClose_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonStatisticsSave)
            {
                labelButtonStatisticsSave_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonHelp)
            {
                labelButtonHelp_actionPerformed(actionevent);
            }
        }

        SymAction()
        {
        }
    }


    Help help;
    String helpAnchor;
    DialogMessage message;
    boolean fComponentsAdjusted;
    FileDialog saveFileDialogStatistics;
    TextArea textAreaStatistics;
    Panel panelStatisticsButtons;
    LabelButton labelButtonStatisticsSave;
    LabelButton labelButtonStatisticsClose;
    LabelButton labelButtonHelp;

    public FrameStatistics()
    {
        fComponentsAdjusted = false;
        help = null;
        helpAnchor = null;
        message = new DialogMessage(this, true);
        GridBagLayout gridbaglayout = new GridBagLayout();
        setLayout(gridbaglayout);
        setVisible(false);
        setSize(637, 470);
        setBackground(new Color(0xc0c0c0));
        saveFileDialogStatistics = new FileDialog(this);
        saveFileDialogStatistics.setMode(1);
        saveFileDialogStatistics.setTitle("Save");
        textAreaStatistics = new TextArea();
        textAreaStatistics.setBounds(5, 5, 627, 425);
        textAreaStatistics.setBackground(new Color(0xffaae8f4));
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 0;
        gridbagconstraints.weightx = 1.0D;
        gridbagconstraints.weighty = 1.0D;
        gridbagconstraints.anchor = 12;
        gridbagconstraints.fill = 1;
        gridbagconstraints.insets = new Insets(5, 5, 0, 5);
        ((GridBagLayout)getLayout()).setConstraints(textAreaStatistics, gridbagconstraints);
        add(textAreaStatistics);
        panelStatisticsButtons = new Panel();
        panelStatisticsButtons.setLayout(null);
        panelStatisticsButtons.setBounds(0, 430, 637, 40);
        panelStatisticsButtons.setBackground(new Color(0xc0c0c0));
        gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.gridx = 0;
        gridbagconstraints.anchor = 16;
        gridbagconstraints.fill = 2;
        gridbagconstraints.insets = new Insets(0, 0, 0, 0);
        ((GridBagLayout)getLayout()).setConstraints(panelStatisticsButtons, gridbagconstraints);
        add(panelStatisticsButtons);
        labelButtonStatisticsSave = new LabelButton();
        try
        {
            labelButtonStatisticsSave.setText("Save");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonStatisticsSave.setBounds(5, 10, 80, 25);
        labelButtonStatisticsSave.setFont(new Font("SansSerif", 0, 14));
        panelStatisticsButtons.add(labelButtonStatisticsSave);
        labelButtonStatisticsClose = new LabelButton();
        try
        {
            labelButtonStatisticsClose.setText("Close");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonStatisticsClose.setBounds(93, 10, 80, 25);
        labelButtonStatisticsClose.setFont(new Font("SansSerif", 0, 14));
        panelStatisticsButtons.add(labelButtonStatisticsClose);
        labelButtonHelp = new LabelButton();
        try
        {
            labelButtonHelp.setText("Help");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonHelp.setBounds(180, 10, 80, 25);
        labelButtonHelp.setFont(new Font("SansSerif", 0, 14));
        panelStatisticsButtons.add(labelButtonHelp);
        setTitle("Statistics");
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymAction symaction = new SymAction();
        labelButtonStatisticsClose.addActionListener(symaction);
        labelButtonStatisticsSave.addActionListener(symaction);
        labelButtonHelp.addActionListener(symaction);
    }

    public FrameStatistics(String s)
    {
        this();
        setTitle(s);
    }

    public void setVisible(boolean flag)
    {
        if(flag)
        {
            setLocation(50, 50);
        }
        super.setVisible(flag);
    }

    public static void main(String args[])
    {
        (new FrameStatistics()).setVisible(true);
    }

    public void addNotify()
    {
        Dimension dimension = getSize();
        super.addNotify();
        if(fComponentsAdjusted)
        {
            return;
        }
        setSize(insets().left + insets().right + dimension.width, insets().top + insets().bottom + dimension.height);
        Component acomponent[] = getComponents();
        for(int i = 0; i < acomponent.length; i++)
        {
            Point point = acomponent[i].getLocation();
            point.translate(insets().left, insets().top);
            acomponent[i].setLocation(point);
        }

        fComponentsAdjusted = true;
    }

    void Frame1_WindowClosing(WindowEvent windowevent)
    {
        setVisible(false);
    }

    void labelButtonStatisticsClose_actionPerformed(ActionEvent actionevent)
    {
        setVisible(false);
    }

    void labelButtonStatisticsSave_actionPerformed(ActionEvent actionevent)
    {
        saveFileDialogStatistics.setFile("*.txt");
        saveFileDialogStatistics.setVisible(true);
        String s1 = saveFileDialogStatistics.getDirectory();
        String s = saveFileDialogStatistics.getFile();
        if(s != null && s1 != null && s != "")
        {
            try
            {
                BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(s1 + s));
                bufferedwriter.write(textAreaStatistics.getText());
                bufferedwriter.newLine();
                bufferedwriter.close();
                return;
            }
            catch(IOException ioexception)
            {
                DialogMessage dialogmessage = new DialogMessage(this, true);
                dialogmessage.showMessage("Write Error!", "Please check the filename.", "The file could not be written. Please make sure that the specified filename is v" +
"alid and that the selected directory is accessible. The System return the follow" +
"ing error message: "
 + ioexception.getMessage());
                return;
            }
        } else
        {
            return;
        }
    }

    public void setHelp(Help help1, String s)
    {
        help = help1;
        helpAnchor = s;
    }

    void labelButtonHelp_actionPerformed(ActionEvent actionevent)
    {
        if(help != null)
        {
            try
            {
                help.show(helpAnchor);
            }
            catch(HelpNotAvailableException _ex)
            {
                help = null;
            }
        }
        if(help == null)
        {
            message.showMessage("Attention!", "NEFCLASS-J cannot display help file.", "NEFCLASS-J cannot display the help file. The reason can be that the file is miss" +
"ing, or that no WWW browser could be started. To display the help file manually," +
" please locate the file NefclassJHelp.htmlthat was included in the distribution " +
"and open it in a WWW browser like, for instance, NETSCAPE."
);
        }
    }
}
