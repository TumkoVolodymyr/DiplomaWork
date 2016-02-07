package nauck.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Volodymyr
 */
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.EventObject;
import symantec.itools.awt.*;
import symantec.itools.awt.shape.HorizontalLine;

public class DialogMessage extends Dialog
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == DialogMessage.this)
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
            if(obj == labelButtonClose)
            {
                labelButtonClose_actionPerformed(actionevent);
            }
        }

        SymAction()
        {
        }
    }


    boolean fComponentsAdjusted;
    Panel panelMessage;
    WrappingLabel wrappingLabelMTitle;
    HorizontalLine horizontalLine3;
    WrappingLabel wrappingLabelMInstruct;
    WrappingLabel wrappingLabelMExplain;
    LabelButton labelButtonClose;
    HorizontalLine horizontalLine1;
    HorizontalLine horizontalLine2;

    public DialogMessage(Frame frame, boolean flag)
    {
        super(frame, flag);
        fComponentsAdjusted = false;
        setLayout(null);
        setVisible(false);
        setSize(430, 262);
        setFont(new Font("Dialog", 0, 12));
        setBackground(new Color(0xc0c0c0));
        panelMessage = new Panel();
        panelMessage.setLayout(null);
        panelMessage.setBounds(3, 3, 424, 213);
        panelMessage.setBackground(new Color(0xede1ff));
        add(panelMessage);
        wrappingLabelMTitle = new WrappingLabel();
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
        wrappingLabelMInstruct.setBounds(5, 40, 414, 41);
        wrappingLabelMInstruct.setFont(new Font("Dialog", 0, 14));
        panelMessage.add(wrappingLabelMInstruct);
        wrappingLabelMExplain = new WrappingLabel();
        wrappingLabelMExplain.setBounds(5, 93, 414, 108);
        panelMessage.add(wrappingLabelMExplain);
        labelButtonClose = new LabelButton();
        try
        {
            labelButtonClose.setText("Close Message");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonClose.setBounds(156, 228, 110, 25);
        labelButtonClose.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonClose);
        horizontalLine1 = new HorizontalLine();
        try
        {
            horizontalLine1.setBevelStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine1.setBounds(0, 252, 166, 2);
        add(horizontalLine1);
        horizontalLine2 = new HorizontalLine();
        try
        {
            horizontalLine2.setBevelStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine2.setBounds(252, 252, 169, 2);
        add(horizontalLine2);
        setTitle("DialogMessage");
        setResizable(false);
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymAction symaction = new SymAction();
        labelButtonClose.addActionListener(symaction);
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

    public DialogMessage(Frame frame, String s, boolean flag)
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

    void labelButtonClose_actionPerformed(ActionEvent actionevent)
    {
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
}
