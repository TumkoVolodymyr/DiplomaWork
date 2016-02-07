package nauck.main;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.EventObject;
import java.util.Properties;
import nauck.util.*;
import symantec.itools.awt.*;
import symantec.itools.awt.shape.HorizontalLine;
import symantec.itools.awt.util.ProgressBar;

public class FuzzySetLearningProgressDialog extends Frame
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == FuzzySetLearningProgressDialog.this)
            {
                Dialog_WindowClosing(windowevent);
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
            if(obj == closeButton)
            {
                closeButton_ActionPerformed(actionevent);
                return;
            }
            if(obj == buttonYlarger)
            {
                buttonYlarger_actionPerformed(actionevent);
                return;
            }
            if(obj == buttonYsmaller)
            {
                buttonYsmaller_actionPerformed(actionevent);
                return;
            }
            if(obj == buttonXsmaller)
            {
                buttonXsmaller_actionPerformed(actionevent);
                return;
            }
            if(obj == buttonXlarger)
            {
                buttonXlarger_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonPrint)
            {
                labelButtonPrint_actionPerformed(actionevent);
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
    private static Properties printprefs = new Properties();
    boolean fComponentsAdjusted;
    LabelButton closeButton;
    Panel panelGraphs;
    ScrollPane pane;
    LabelButton buttonYlarger;
    LabelButton buttonYsmaller;
    LabelButton buttonXsmaller;
    LabelButton buttonXlarger;
    LabelButton labelButtonHelp;
    Panel panelProgress;
    TextArea statusTextArea;
    ProgressBar bar;
    HorizontalLine horizontalLine1;
    LabelButton labelButtonPrint;

    public FuzzySetLearningProgressDialog()
    {
        fComponentsAdjusted = false;
        help = null;
        helpAnchor = null;
        message = new DialogMessage(this, true);
        setLayout(null);
        setVisible(false);
        setSize(638, 480);
        setForeground(new Color(0));
        setBackground(new Color(0xc0c0c0));
        closeButton = new LabelButton();
        try
        {
            closeButton.setText("Close");
        }
        catch(PropertyVetoException _ex) { }
        closeButton.setBounds(463, 449, 70, 24);
        closeButton.setFont(new Font("SansSerif", 0, 14));
        add(closeButton);
        panelGraphs = new Panel();
        panelGraphs.setLayout(null);
        panelGraphs.setBounds(5, 5, 627, 300);
        panelGraphs.setBackground(new Color(0xffaaffd5));
        add(panelGraphs);
        pane = new ScrollPane(0);
        pane.setBounds(72, 23, 525, 220);
        pane.setBackground(new Color(0xffe6fff2));
        panelGraphs.add(pane);
        buttonYlarger = new LabelButton();
        try
        {
            buttonYlarger.setText(">");
        }
        catch(PropertyVetoException _ex) { }
        buttonYlarger.setBounds(29, 24, 25, 25);
        buttonYlarger.setFont(new Font("Dialog", 1, 12));
        panelGraphs.add(buttonYlarger);
        buttonYsmaller = new LabelButton();
        try
        {
            buttonYsmaller.setText("<");
        }
        catch(PropertyVetoException _ex) { }
        buttonYsmaller.setBounds(29, 53, 25, 25);
        buttonYsmaller.setFont(new Font("Dialog", 1, 12));
        panelGraphs.add(buttonYsmaller);
        buttonXsmaller = new LabelButton();
        try
        {
            buttonXsmaller.setText("<");
        }
        catch(PropertyVetoException _ex) { }
        buttonXsmaller.setBounds(541, 255, 25, 25);
        buttonXsmaller.setFont(new Font("Dialog", 1, 12));
        panelGraphs.add(buttonXsmaller);
        buttonXlarger = new LabelButton();
        try
        {
            buttonXlarger.setText(">");
        }
        catch(PropertyVetoException _ex) { }
        buttonXlarger.setBounds(572, 255, 25, 25);
        buttonXlarger.setFont(new Font("Dialog", 1, 12));
        panelGraphs.add(buttonXlarger);
        labelButtonHelp = new LabelButton();
        try
        {
            labelButtonHelp.setText("Help");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonHelp.setBounds(552, 449, 70, 24);
        labelButtonHelp.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonHelp);
        panelProgress = new Panel();
        panelProgress.setLayout(null);
        panelProgress.setBounds(5, 311, 627, 125);
        panelProgress.setBackground(new Color(0xffaaffd5));
        add(panelProgress);
        statusTextArea = new TextArea();
        statusTextArea.setEditable(false);
        statusTextArea.setBounds(5, 5, 617, 72);
        statusTextArea.setFont(new Font("MonoSpaced", 0, 12));
        statusTextArea.setBackground(new Color(0xffe6fff2));
        panelProgress.add(statusTextArea);
        bar = new ProgressBar();
        try
        {
            bar.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            bar.setProgressBarColor(new Color(0xff5f68be));
        }
        catch(PropertyVetoException _ex) { }
        bar.setBounds(5, 88, 617, 26);
        bar.setForeground(new Color(0xffffff));
        bar.setBackground(new Color(0xff86e3b5));
        panelProgress.add(bar);
        horizontalLine1 = new HorizontalLine();
        try
        {
            horizontalLine1.setBevelStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine1.setBounds(5, 307, 627, 2);
        add(horizontalLine1);
        labelButtonPrint = new LabelButton();
        try
        {
            labelButtonPrint.setText("Print");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonPrint.setBounds(374, 449, 70, 24);
        labelButtonPrint.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonPrint);
        setTitle("Fuzzy Set Learning");
        setResizable(false);
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymAction symaction = new SymAction();
        closeButton.addActionListener(symaction);
        buttonYlarger.addActionListener(symaction);
        buttonYsmaller.addActionListener(symaction);
        buttonXsmaller.addActionListener(symaction);
        buttonXlarger.addActionListener(symaction);
        labelButtonPrint.addActionListener(symaction);
        labelButtonHelp.addActionListener(symaction);
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

    public FuzzySetLearningProgressDialog(String s)
    {
        this();
        setTitle(s);
    }

    public void setVisible(boolean flag)
    {
        if(flag)
        {
            setLocation(125, 125);
        }
        super.setVisible(flag);
    }

    void Dialog_WindowClosing(WindowEvent windowevent)
    {
        setVisible(false);
    }

    void closeButton_ActionPerformed(ActionEvent actionevent)
    {
        setVisible(false);
    }

    void buttonYlarger_actionPerformed(ActionEvent actionevent)
    {
        Component component = pane.getComponent(0);
        if(component instanceof FunctionLayoutPanel)
        {
            FunctionLayoutPanel functionlayoutpanel = (FunctionLayoutPanel)component;
            for(int i = 0; i < functionlayoutpanel.getComponentCount(); i++)
            {
                Component component1 = functionlayoutpanel.getComponent(i);
                if(component1 instanceof FunctionPane)
                {
                    ((FunctionPane)component1).changeYrange(1.0D);
                }
            }

        }
    }

    void buttonYsmaller_actionPerformed(ActionEvent actionevent)
    {
        Component component = pane.getComponent(0);
        if(component instanceof FunctionLayoutPanel)
        {
            FunctionLayoutPanel functionlayoutpanel = (FunctionLayoutPanel)component;
            for(int i = 0; i < functionlayoutpanel.getComponentCount(); i++)
            {
                Component component1 = functionlayoutpanel.getComponent(i);
                if(component1 instanceof FunctionPane)
                {
                    ((FunctionPane)component1).changeYrange(-1D);
                }
            }

        }
    }

    void buttonXsmaller_actionPerformed(ActionEvent actionevent)
    {
        Component component = pane.getComponent(0);
        if(component instanceof FunctionLayoutPanel)
        {
            FunctionLayoutPanel functionlayoutpanel = (FunctionLayoutPanel)component;
            for(int i = 0; i < functionlayoutpanel.getComponentCount(); i++)
            {
                Component component1 = functionlayoutpanel.getComponent(i);
                if(component1 instanceof FunctionPane)
                {
                    ((FunctionPane)component1).changeXrange(-1D);
                }
            }

        }
    }

    void buttonXlarger_actionPerformed(ActionEvent actionevent)
    {
        Component component = pane.getComponent(0);
        if(component instanceof FunctionLayoutPanel)
        {
            FunctionLayoutPanel functionlayoutpanel = (FunctionLayoutPanel)component;
            for(int i = 0; i < functionlayoutpanel.getComponentCount(); i++)
            {
                Component component1 = functionlayoutpanel.getComponent(i);
                if(component1 instanceof FunctionPane)
                {
                    ((FunctionPane)component1).changeXrange(1.0D);
                }
            }

        }
    }

    void labelButtonPrint_actionPerformed(ActionEvent actionevent)
    {
        if(pane.getComponent(0) instanceof Container)
        {
            Container container = (Container)pane.getComponent(0);
            int i = container.getComponentCount();
            if(i > 0)
            {
                Toolkit toolkit = getToolkit();
                PrintJob printjob = toolkit.getPrintJob(this, "Error Graphs", printprefs);
                if(printjob != null)
                {
                    Dimension dimension1 = printjob.getPageDimension();
                    for(int j = 0; j < container.getComponentCount(); j++)
                    {
                        Component component = container.getComponent(j);
                        if(component != null)
                        {
                            Graphics g = printjob.getGraphics();
                            if(g != null)
                            {
                                Dimension dimension = component.getSize();
                                g.translate((dimension1.width - dimension.width) / 2, (dimension1.height - dimension.height) / 2);
                                g.setClip(0, 0, dimension.width, dimension.height);
                                component.print(g);
                                g.dispose();
                            }
                        }
                    }

                    printjob.end();
                }
            }
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
