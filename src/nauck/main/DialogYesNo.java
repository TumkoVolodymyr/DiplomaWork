package nauck.main;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.EventObject;
import symantec.itools.awt.*;
import symantec.itools.awt.shape.HorizontalLine;

public class DialogYesNo extends Dialog
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == DialogYesNo.this)
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
            if(obj == labelButtonYes)
            {
                labelButtonYes_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonNo)
            {
                labelButtonNo_actionPerformed(actionevent);
            }
        }

        SymAction()
        {
        }
    }


    protected boolean yes;
    boolean fComponentsAdjusted;
    Panel panelMessage;
    WrappingLabel wrappingLabelMTitle;
    HorizontalLine horizontalLine3;
    WrappingLabel wrappingLabelMInstruct;
    WrappingLabel wrappingLabelMExplain;
    LabelButton labelButtonYes;
    HorizontalLine horizontalLine1;
    LabelButton labelButtonNo;

    public DialogYesNo(Frame frame, boolean flag)
    {
        super(frame, flag);
        yes = false;
        fComponentsAdjusted = false;
        setLayout(null);
        setVisible(false);
        setSize(430, 262);
        setFont(new Font("Dialog", 0, 12));
        setBackground(new Color(0xc0c0c0));
        panelMessage = new Panel();
        panelMessage.setLayout(null);
        panelMessage.setBounds(3, 3, 424, 213);
        panelMessage.setBackground(new Color(0xffede1ff));
        add(panelMessage);
        wrappingLabelMTitle = new WrappingLabel();
        try
        {
            wrappingLabelMTitle.setText("Are You Sure?");
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            wrappingLabelMTitle.setAlignStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelMTitle.setBounds(5, 5, 414, 25);
        wrappingLabelMTitle.setFont(new Font("Dialog", 1, 14));
        panelMessage.add(wrappingLabelMTitle);
        horizontalLine3 = new HorizontalLine();
        try
        {
            horizontalLine3.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine3.setBounds(5, 31, 410, 2);
        panelMessage.add(horizontalLine3);
        wrappingLabelMInstruct = new WrappingLabel();
        try
        {
            wrappingLabelMInstruct.setAlignStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelMInstruct.setBounds(5, 42, 414, 41);
        wrappingLabelMInstruct.setFont(new Font("Dialog", 0, 14));
        panelMessage.add(wrappingLabelMInstruct);
        wrappingLabelMExplain = new WrappingLabel();
        wrappingLabelMExplain.setBounds(5, 94, 414, 108);
        panelMessage.add(wrappingLabelMExplain);
        labelButtonYes = new LabelButton();
        try
        {
            labelButtonYes.setText("Yes");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonYes.setBounds(100, 225, 89, 25);
        labelButtonYes.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonYes);
        horizontalLine1 = new HorizontalLine();
        try
        {
            horizontalLine1.setBevelStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine1.setBounds(6, 248, 418, 2);
        add(horizontalLine1);
        labelButtonNo = new LabelButton();
        try
        {
            labelButtonNo.setText("No");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonNo.setBounds(240, 225, 89, 25);
        labelButtonNo.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonNo);
        setTitle("Question");
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymAction symaction = new SymAction();
        labelButtonYes.addActionListener(symaction);
        labelButtonNo.addActionListener(symaction);
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

    public DialogYesNo(Frame frame, String s, boolean flag)
    {
        this(frame, flag);
        setTitle(s);
    }

    public void setVisible(boolean flag)
    {
        if(flag)
        {
            Rectangle rectangle = getParent().getBounds();
            Rectangle rectangle1 = getBounds();
            int i = rectangle.x + (rectangle.width - rectangle1.width) / 2;
            if(i < 0)
            {
                i = 0;
            }
            int j = rectangle.y + (rectangle.height - rectangle1.height) / 2;
            if(j < 0)
            {
                j = 0;
            }
            setLocation(i, j);
        }
        super.setVisible(flag);
    }

    void Dialog1_WindowClosing(WindowEvent windowevent)
    {
        setVisible(false);
    }

    void labelButtonYes_actionPerformed(ActionEvent actionevent)
    {
        yes = true;
        setVisible(false);
    }

    void labelButtonNo_actionPerformed(ActionEvent actionevent)
    {
        yes = false;
        setVisible(false);
    }

    public void showMessage(String s, String s1, String s2)
    {
        try
        {
            wrappingLabelMTitle.setText(s);
            wrappingLabelMInstruct.setText(s1);
            wrappingLabelMExplain.setText(s2);
        }
        catch(PropertyVetoException _ex) { }
        setVisible(true);
    }

    public boolean closedWithYes()
    {
        return yes;
    }
}
