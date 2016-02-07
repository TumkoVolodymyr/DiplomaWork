package nauck.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.List;
import java.awt.Panel;
import java.awt.Point;
import java.awt.PrintJob;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import nauck.fuzzy.FuzzyPartition;
import nauck.fuzzy.FuzzyPartitionLayout;
import nauck.util.Help;
import nauck.util.HelpNotAvailableException;
import symantec.itools.awt.ButtonBase;
import symantec.itools.awt.LabelButton;
import symantec.itools.awt.WrappingLabel;

public class FrameShowFS extends Frame
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == FrameShowFS.this)
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
            if(obj == labelButtonShowFSClose)
            {
                labelButtonShowFSClose_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonVarNameSelectAll)
            {
                labelButtonVarNameSelectAll_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonVarNameDeselect)
            {
                labelButtonVarNameDeselect_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonShowFS)
            {
                labelButtonShowFS_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonShowFSPrint)
            {
                labelButtonShowFSPrint_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonShowFSHelp)
            {
                labelButtonShowFSHelp_actionPerformed(actionevent);
            }
        }

        SymAction()
        {
        }
    }


    Help help;
    String helpAnchor;
    DialogMessage message;
    protected FuzzyPartitionLayout graphs;
    protected FuzzyPartition partitions[];
    protected FuzzyPartition showPartitions[];
    protected boolean empty;
    private static java.util.Properties printprefs = new java.util.Properties();
    boolean fComponentsAdjusted;
    LabelButton labelButtonShowFSPrint;
    LabelButton labelButtonShowFSClose;
    LabelButton labelButtonShowFSHelp;
    Panel panelShowFS;
    ScrollPane scrollPaneShowFS;
    LabelButton labelButtonVarNameSelectAll;
    LabelButton labelButtonVarNameDeselect;
    WrappingLabel wrappingLabelVarList;
    List listVarNames;
    LabelButton labelButtonShowFS;
    WrappingLabel wrappingLabel1;

    public FrameShowFS()
    {
        fComponentsAdjusted = false;
        help = null;
        helpAnchor = null;
        message = new DialogMessage(this, true);
        empty = true;
        setLayout(null);
        setVisible(false);
        setSize(637, 480);
        setBackground(new Color(0xc0c0c0));
        labelButtonShowFSPrint = new LabelButton();
        try
        {
            labelButtonShowFSPrint.setText("Print");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonShowFSPrint.setBounds(345, 451, 80, 25);
        labelButtonShowFSPrint.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonShowFSPrint);
        labelButtonShowFSClose = new LabelButton();
        try
        {
            labelButtonShowFSClose.setText("Close");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonShowFSClose.setBounds(436, 451, 80, 25);
        labelButtonShowFSClose.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonShowFSClose);
        labelButtonShowFSHelp = new LabelButton();
        try
        {
            labelButtonShowFSHelp.setText("Help");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonShowFSHelp.setBounds(551, 451, 80, 25);
        labelButtonShowFSHelp.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonShowFSHelp);
        panelShowFS = new Panel();
        panelShowFS.setLayout(null);
        panelShowFS.setBounds(3, 3, 631, 439);
        panelShowFS.setBackground(new Color(-4449));
        add(panelShowFS);
        scrollPaneShowFS = new ScrollPane(0);
        scrollPaneShowFS.setBounds(5, 27, 400, 406);
        scrollPaneShowFS.setBackground(new Color(-1053));
        panelShowFS.add(scrollPaneShowFS);
        labelButtonVarNameSelectAll = new LabelButton();
        try
        {
            labelButtonVarNameSelectAll.setText("Select All");
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            labelButtonVarNameSelectAll.setButtonColor(new Color(0xffe2e2e2));
        }
        catch(PropertyVetoException _ex) { }
        labelButtonVarNameSelectAll.setBounds(565, 406, 60, 26);
        labelButtonVarNameSelectAll.setFont(new Font("Dialog", 0, 12));
        panelShowFS.add(labelButtonVarNameSelectAll);
        labelButtonVarNameDeselect = new LabelButton();
        try
        {
            labelButtonVarNameDeselect.setText("Deselect");
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            labelButtonVarNameDeselect.setButtonColor(new Color(0xffe2e2e2));
        }
        catch(PropertyVetoException _ex) { }
        labelButtonVarNameDeselect.setBounds(487, 406, 60, 26);
        labelButtonVarNameDeselect.setFont(new Font("Dialog", 0, 12));
        panelShowFS.add(labelButtonVarNameDeselect);
        wrappingLabelVarList = new WrappingLabel();
        try
        {
            wrappingLabelVarList.setText("List of Variables");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelVarList.setBounds(410, 4, 214, 23);
        wrappingLabelVarList.setFont(new Font("Dialog", 1, 13));
        panelShowFS.add(wrappingLabelVarList);
        listVarNames = new List(4);
        listVarNames.setMultipleMode(true);
        panelShowFS.add(listVarNames);
        listVarNames.setBounds(410, 27, 214, 372);
        listVarNames.setFont(new Font("Dialog", 0, 12));
        listVarNames.setBackground(new Color(-1053));
        labelButtonShowFS = new LabelButton();
        try
        {
            labelButtonShowFS.setText("Show");
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            labelButtonShowFS.setButtonColor(new Color(0xffe2e2e2));
        }
        catch(PropertyVetoException _ex) { }
        labelButtonShowFS.setBounds(411, 406, 60, 26);
        labelButtonShowFS.setFont(new Font("Dialog", 0, 12));
        panelShowFS.add(labelButtonShowFS);
        wrappingLabel1 = new WrappingLabel();
        try
        {
            wrappingLabel1.setText("Fuzzy Sets");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabel1.setBounds(5, 4, 214, 23);
        wrappingLabel1.setFont(new Font("Dialog", 1, 13));
        panelShowFS.add(wrappingLabel1);
        setTitle("Fuzzy Sets");
        setResizable(false);
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymAction symaction = new SymAction();
        labelButtonShowFSClose.addActionListener(symaction);
        labelButtonVarNameSelectAll.addActionListener(symaction);
        labelButtonVarNameDeselect.addActionListener(symaction);
        labelButtonShowFS.addActionListener(symaction);
        labelButtonShowFSPrint.addActionListener(symaction);
        labelButtonShowFSHelp.addActionListener(symaction);
    }

    public FrameShowFS(String s)
    {
        this();
        setTitle(s);
    }

    public void setVisible(boolean flag)
    {
        if(flag)
        {
            setLocation(100, 100);
        }
        super.setVisible(flag);
    }

    public static void main(String args[])
    {
        (new FrameShowFS()).setVisible(true);
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

    void labelButtonShowFSClose_actionPerformed(ActionEvent actionevent)
    {
        setVisible(false);
    }

    protected FuzzyPartition[] getPrintPartitions(int i)
    {
        int j = showPartitions.length - i;
        scrollPaneShowFS.getSize();
        if(j > 0)
        {
            if(j > 3)
            {
                j = 3;
            }
            FuzzyPartition afuzzypartition[] = new FuzzyPartition[j];
            for(int k = 0; k < j; k++)
            {
                afuzzypartition[k] = showPartitions[k + i];
            }

            return afuzzypartition;
        } else
        {
            return null;
        }
    }

    protected void displayPartitions()
    {
        scrollPaneShowFS.removeAll();
        graphs = null;
        labelButtonShowFSPrint.setEnabled(false);
        if(showPartitions != null)
        {
            int i = scrollPaneShowFS.getSize().width - 25;
            graphs = new FuzzyPartitionLayout(showPartitions.length, i, (9 * i) / 16, showPartitions);
            scrollPaneShowFS.add(graphs);
            scrollPaneShowFS.validate();
            labelButtonShowFSPrint.setEnabled(true);
        }
        empty = false;
    }

    public void setPartitions(FuzzyPartition afuzzypartition[])
    {
        partitions = afuzzypartition;
        showPartitions = afuzzypartition;
        for(int i = 0; i < afuzzypartition.length; i++)
        {
            listVarNames.add(afuzzypartition[i].getName());
        }

        labelButtonVarNameSelectAll_actionPerformed(null);
        displayPartitions();
    }

    public void reset()
    {
        scrollPaneShowFS.removeAll();
        listVarNames.removeAll();
        graphs = null;
        empty = true;
        labelButtonShowFSPrint.setEnabled(false);
    }

    public boolean isEmpty()
    {
        return empty;
    }

    void labelButtonVarNameSelectAll_actionPerformed(ActionEvent actionevent)
    {
        for(int i = 0; i < listVarNames.getItemCount(); i++)
        {
            listVarNames.select(i);
        }

    }

    void labelButtonVarNameDeselect_actionPerformed(ActionEvent actionevent)
    {
        for(int i = 0; i < listVarNames.getItemCount(); i++)
        {
            listVarNames.deselect(i);
        }

    }

    void labelButtonShowFS_actionPerformed(ActionEvent actionevent)
    {
        int ai[] = listVarNames.getSelectedIndexes();
        if(ai.length > 0)
        {
            showPartitions = new FuzzyPartition[ai.length];
            for(int i = 0; i < ai.length; i++)
            {
                showPartitions[i] = partitions[ai[i]];
            }

        } else
        {
            showPartitions = null;
        }
        displayPartitions();
    }

    void labelButtonShowFSPrint_actionPerformed(ActionEvent actionevent)
    {
        Toolkit toolkit = getToolkit();
        PrintJob printjob = toolkit.getPrintJob(this, "Fuzzy Sets", printprefs);
        Dimension dimension1 = printjob.getPageDimension();
        if(printjob != null)
        {
            FuzzyPartition afuzzypartition[] = showPartitions;
            for(int i = 0; i < afuzzypartition.length; i += 3)
            {
                showPartitions = getPrintPartitions(i);
                displayPartitions();
                Graphics g = printjob.getGraphics();
                Dimension dimension = graphs.getSize();
                g.translate((dimension1.width - dimension.width) / 2, (dimension1.height - dimension.height) / 2);
                g.setClip(0, 0, dimension.width, dimension.height);
                graphs.printAll(g);
                g.dispose();
                showPartitions = afuzzypartition;
            }

            showPartitions = afuzzypartition;
            displayPartitions();
            printjob.end();
        }
    }

    public void setHelp(Help help1, String s)
    {
        help = help1;
        helpAnchor = s;
    }

    void labelButtonShowFSHelp_actionPerformed(ActionEvent actionevent)
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
