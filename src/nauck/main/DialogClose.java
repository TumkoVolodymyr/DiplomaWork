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
import symantec.itools.awt.*;
import symantec.itools.awt.shape.HorizontalLine;

public class DialogClose extends Dialog
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == DialogClose.this)
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
            if(obj == labelButtonSave)
            {
                labelButtonSave_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonClose)
            {
                labelButtonClose_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonCancel)
            {
                labelButtonCancel_actionPerformed(actionevent);
            }
        }

        SymAction()
        {
        }
    }


    int closeMode;
    boolean fComponentsAdjusted;
    Panel panelCloseMessage;
    WrappingLabel wrappingLabelCTitle;
    HorizontalLine horizontalLine3;
    WrappingLabel wrappingLabelCInstruct;
    WrappingLabel wrappingLabelCExplain;
    LabelButton labelButtonSave;
    HorizontalLine horizontalLine1;
    LabelButton labelButtonClose;
    LabelButton labelButtonCancel;

    public DialogClose(Frame frame, boolean flag)
    {
        super(frame, flag);
        fComponentsAdjusted = false;
        closeMode = 0;
        setLayout(null);
        setVisible(false);
        setSize(430, 170);
        setFont(new Font("Dialog", 0, 12));
        setBackground(new Color(0xc0c0c0));
        panelCloseMessage = new Panel();
        panelCloseMessage.setLayout(null);
        panelCloseMessage.setBounds(3, 3, 424, 125);
        panelCloseMessage.setBackground(new Color(0xede1ff));
        add(panelCloseMessage);
        wrappingLabelCTitle = new WrappingLabel();
        try
        {
            wrappingLabelCTitle.setAlignStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelCTitle.setBounds(5, 5, 414, 25);
        wrappingLabelCTitle.setFont(new Font("Dialog", 1, 14));
        panelCloseMessage.add(wrappingLabelCTitle);
        horizontalLine3 = new HorizontalLine();
        try
        {
            horizontalLine3.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine3.setBounds(5, 31, 410, 2);
        panelCloseMessage.add(horizontalLine3);
        wrappingLabelCInstruct = new WrappingLabel();
        try
        {
            wrappingLabelCInstruct.setAlignStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelCInstruct.setBounds(5, 40, 414, 25);
        wrappingLabelCInstruct.setFont(new Font("Dialog", 0, 14));
        panelCloseMessage.add(wrappingLabelCInstruct);
        wrappingLabelCExplain = new WrappingLabel();
        wrappingLabelCExplain.setBounds(5, 70, 414, 48);
        panelCloseMessage.add(wrappingLabelCExplain);
        labelButtonSave = new LabelButton();
        try
        {
            labelButtonSave.setText("Save");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonSave.setBounds(72, 140, 80, 25);
        labelButtonSave.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonSave);
        horizontalLine1 = new HorizontalLine();
        try
        {
            horizontalLine1.setBevelStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine1.setBounds(6, 163, 418, 2);
        add(horizontalLine1);
        labelButtonClose = new LabelButton();
        try
        {
            labelButtonClose.setText("Close");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonClose.setBounds(175, 140, 80, 25);
        labelButtonClose.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonClose);
        labelButtonCancel = new LabelButton();
        try
        {
            labelButtonCancel.setText("Cancel");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonCancel.setBounds(278, 140, 80, 25);
        labelButtonCancel.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonCancel);
        setTitle("DialogClose");
        setResizable(false);
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymAction symaction = new SymAction();
        labelButtonSave.addActionListener(symaction);
        labelButtonClose.addActionListener(symaction);
        labelButtonCancel.addActionListener(symaction);
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

    public DialogClose(Frame frame, String s, boolean flag)
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
        closeMode = 0;
        setVisible(false);
    }

    void labelButtonSave_actionPerformed(ActionEvent actionevent)
    {
        closeMode = 1;
        setVisible(false);
    }

    void labelButtonCancel_actionPerformed(ActionEvent actionevent)
    {
        closeMode = 2;
        setVisible(false);
    }

    public boolean closedWithSave()
    {
        return closeMode == 1;
    }

    public boolean wasCanceled()
    {
        return closeMode == 2;
    }

    public void showMessage(String s, String s1, String s2)
    {
        try
        {
            wrappingLabelCTitle.setText(s);
            wrappingLabelCInstruct.setText(s1);
            wrappingLabelCExplain.setText(s2);
        }
        catch(PropertyVetoException _ex) { }
        setVisible(true);
    }
}
