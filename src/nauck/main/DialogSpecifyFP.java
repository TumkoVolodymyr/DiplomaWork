/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nauck.main;

/**
 *
 * @author Volodymyr
 */
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.EventObject;
import nauck.fuzzy.SpecifyFPPanel;
import nauck.util.Help;
import nauck.util.HelpNotAvailableException;
import symantec.itools.awt.BevelStyle;
import symantec.itools.awt.WrappingLabel;
import symantec.itools.awt.shape.HorizontalLine;

public class DialogSpecifyFP extends Dialog
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == DialogSpecifyFP.this)
            {
                Dialog1_WindowClosing(windowevent);
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
            if(obj == labelButtonSpecifyFPCancel)
            {
                labelButtonSpecifyFPCancel_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonSpecifyFPOK)
            {
                labelButtonSpecifyFPOK_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonSetFS)
            {
                labelButtonSetFS_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonSetFT)
            {
                labelButtonSetFT_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonSpecifyFPHelp)
            {
                labelButtonSpecifyFPHelp_actionPerformed(actionevent);
            }
        }

        SymAction()
        {
        }
    }


    Help help;
    String helpAnchor;
    DialogMessage message;
    SpecifyFPPanel fpPanel;
    boolean closeWithOK;
    boolean fComponentsAdjusted;
    Panel panelSpecifyFP;
    ScrollPane scrollPaneSpecifyFP;
    Label labelSameNumber;
    TextField textFieldSameNumber;
    Label labelSameType;
    Choice choiceFSTypes;
    WrappingLabel wrappingLabelSetFP1;
    WrappingLabel wrappingLabelSetFP2;
    HorizontalLine horizontalLine1;
    Button labelButtonSetFS;
    HorizontalLine horizontalLine2;
    Button labelButtonSetFT;
    HorizontalLine horizontalLine3;
    Button labelButtonSpecifyFPOK;
    Button labelButtonSpecifyFPCancel;
    Button labelButtonSpecifyFPHelp;

    public DialogSpecifyFP(Frame frame, boolean flag)
    {
        super(frame, flag);
        fComponentsAdjusted = false;
        help = null;
        helpAnchor = null;
        message = new DialogMessage((Frame)getParent(), true);
        closeWithOK = false;
        setLayout(null);
        setVisible(false);
        setSize(580, 360);
        setFont(new Font("Dialog", 0, 12));
        setBackground(new Color(0xc0c0c0));
        panelSpecifyFP = new Panel();
        panelSpecifyFP.setLayout(null);
        panelSpecifyFP.setBounds(5, 5, 570, 320);
        panelSpecifyFP.setBackground(new Color(-4449));
        add(panelSpecifyFP);
        scrollPaneSpecifyFP = new ScrollPane(0);
        scrollPaneSpecifyFP.setBounds(3, 3, 370, 245);
        scrollPaneSpecifyFP.setBackground(new Color(-1053));
        panelSpecifyFP.add(scrollPaneSpecifyFP);
        labelSameNumber = new Label("Set number of fuzzy sets for each variable to:");
        labelSameNumber.setBounds(3, 255, 260, 22);
        panelSpecifyFP.add(labelSameNumber);
        textFieldSameNumber = new TextField();
        textFieldSameNumber.setBounds(328, 255, 60, 23);
        panelSpecifyFP.add(textFieldSameNumber);
        labelSameType = new Label("Set type of fuzzy sets for each variable to:");
        labelSameType.setBounds(3, 290, 260, 22);
        panelSpecifyFP.add(labelSameType);
        choiceFSTypes = new Choice();
        choiceFSTypes.addItem("triangular");
        choiceFSTypes.addItem("trapezoidal");
        choiceFSTypes.addItem("bell-shaped");
        try
        {
            choiceFSTypes.select(0);
        }
        catch(IllegalArgumentException _ex) { }
        panelSpecifyFP.add(choiceFSTypes);
        choiceFSTypes.setBounds(270, 290, 118, 23);
        choiceFSTypes.setBackground(new Color(-1053));
        wrappingLabelSetFP1 = new WrappingLabel();
        try
        {
            wrappingLabelSetFP1.setText("Here you can set the number and type for every variable. For making it easy you " +
"can first initialize all variables with the values most common and then only cha" +
"nge some. "
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelSetFP1.setBounds(383, 3, 177, 93);
        panelSpecifyFP.add(wrappingLabelSetFP1);
        wrappingLabelSetFP2 = new WrappingLabel();
        try
        {
            wrappingLabelSetFP2.setText("Please note that these values are not transmitted to the 'classifier-tab' in the" +
" project specification dialog.  If you set all variables to the same value in th" +
"is dialog and then decide to use the 'all the same' option in the 'classifier-ta" +
"b' you might have different values."
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelSetFP2.setBounds(383, 103, 177, 138);
        panelSpecifyFP.add(wrappingLabelSetFP2);
        horizontalLine1 = new HorizontalLine();
        try
        {
            horizontalLine1.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine1.setBounds(383, 245, 177, 2);
        panelSpecifyFP.add(horizontalLine1);
        labelButtonSetFS = new Button();
        labelButtonSetFS.setLabel("Set");
        labelButtonSetFS.setBackground(new Color(0xffe2e2e2));
        labelButtonSetFS.setBounds(388, 255, 45, 22);
        labelButtonSetFS.setFont(new Font("SansSerif", 0, 13));
        panelSpecifyFP.add(labelButtonSetFS);
        horizontalLine2 = new HorizontalLine();
        try
        {
            horizontalLine2.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine2.setBounds(3, 277, 430, 2);
        panelSpecifyFP.add(horizontalLine2);
        labelButtonSetFT = new Button();
        labelButtonSetFT.setLabel("Set");
        labelButtonSetFT.setBackground(new Color(0xffe2e2e2));
        labelButtonSetFT.setBounds(388, 290, 45, 22);
        labelButtonSetFT.setFont(new Font("SansSerif", 0, 13));
        panelSpecifyFP.add(labelButtonSetFT);
        horizontalLine3 = new HorizontalLine();
        try
        {
            horizontalLine3.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine3.setBounds(3, 312, 430, 2);
        panelSpecifyFP.add(horizontalLine3);
        labelButtonSpecifyFPOK = new Button();
        labelButtonSpecifyFPOK.setLabel("OK");
        labelButtonSpecifyFPOK.setBounds(315, 332, 70, 24);
        labelButtonSpecifyFPOK.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonSpecifyFPOK);
        labelButtonSpecifyFPCancel = new Button();
        labelButtonSpecifyFPCancel.setLabel("Cancel");
        labelButtonSpecifyFPCancel.setBounds(393, 332, 70, 24);
        labelButtonSpecifyFPCancel.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonSpecifyFPCancel);
        labelButtonSpecifyFPHelp = new Button();
        labelButtonSpecifyFPHelp.setLabel("Help");
        labelButtonSpecifyFPHelp.setBounds(506, 332, 70, 24);
        labelButtonSpecifyFPHelp.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonSpecifyFPHelp);
        setTitle("Specify Fuzzy Partition");
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymAction symaction = new SymAction();
        labelButtonSpecifyFPCancel.addActionListener(symaction);
        labelButtonSpecifyFPOK.addActionListener(symaction);
        labelButtonSetFS.addActionListener(symaction);
        labelButtonSetFT.addActionListener(symaction);
        labelButtonSpecifyFPHelp.addActionListener(symaction);
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

    public DialogSpecifyFP(Frame frame, String s, boolean flag)
    {
        this(frame, flag);
        setTitle(s);
    }

    public void setVisible(boolean flag)
    {
        if(flag)
        {
            Rectangle rectangle = getParent().bounds();
            Rectangle rectangle1 = bounds();
            int i = rectangle.x + (rectangle.width - rectangle1.width) / 2;
            int j = rectangle.y + (rectangle.height - rectangle1.height) / 2;
            if(i < 0)
            {
                i = 0;
            }
            if(j < 0)
            {
                j = 0;
            }
            move(i, j);
        }
        super.setVisible(flag);
    }

    void Dialog1_WindowClosing(WindowEvent windowevent)
    {
        setVisible(false);
    }

    void labelButtonSpecifyFPCancel_actionPerformed(ActionEvent actionevent)
    {
        closeWithOK = false;
        setVisible(false);
    }

    void labelButtonSpecifyFPOK_actionPerformed(ActionEvent actionevent)
    {
        closeWithOK = true;
        setVisible(false);
    }

    public void createFPPanel(String as[], int ai[], int ai1[])
    {
        fpPanel = new SpecifyFPPanel(as, ai, ai1);
        scrollPaneSpecifyFP.add(fpPanel);
    }

    public int[] getFSNumbers()
    {
        return fpPanel.getFSNumbers();
    }

    public int[] getFSTypes()
    {
        return fpPanel.getFSTypes();
    }

    public boolean closedWithOK()
    {
        return closeWithOK;
    }

    void labelButtonSetFS_actionPerformed(ActionEvent actionevent)
    {
        try
        {
            int i = Integer.parseInt(textFieldSameNumber.getText());
            fpPanel.setFSNumbers(i);
            return;
        }
        catch(NumberFormatException _ex)
        {
            return;
        }
    }

    void labelButtonSetFT_actionPerformed(ActionEvent actionevent)
    {
        fpPanel.setFSTypes(choiceFSTypes.getSelectedIndex());
    }

    public void setHelp(Help help1, String s)
    {
        help = help1;
        helpAnchor = s;
    }

    void labelButtonSpecifyFPHelp_actionPerformed(ActionEvent actionevent)
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

