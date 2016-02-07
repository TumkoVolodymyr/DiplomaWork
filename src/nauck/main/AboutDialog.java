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
import nauck.util.Logo;
import symantec.itools.awt.*;
import symantec.itools.awt.shape.HorizontalLine;

public class AboutDialog extends Dialog
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == AboutDialog.this)
            {
                AboutDialog_WindowClosing(windowevent);
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
            if(obj == okButton)
            {
                okButton_Clicked(actionevent);
            }
        }

        SymAction()
        {
        }
    }


    Logo logo;
    WrappingLabel title;
    WrappingLabel version;
    WrappingLabel programmerTitle;
    WrappingLabel programmer;
    WrappingLabel algorithmTitle;
    WrappingLabel algorithm;
    WrappingLabel copyright;
    HorizontalLine horizontalLine1;
    Button okButton;
    HorizontalLine horizontalLine3;
    boolean fComponentsAdjusted;

    public AboutDialog(Frame frame, boolean flag)
    {
        super(frame, flag);
        fComponentsAdjusted = false;
        logo = new Logo("images/nefclass_logo.gif");
        logo.setBounds(21, 20, 150, 184);
        add(logo);
        setLayout(null);
        setVisible(false);
        setSize(445, 280);
        setFont(new Font("SansSerif", 0, 12));
        setBackground(new Color(0xc0c0c0));
        title = new WrappingLabel();
        try
        {
            title.setText("NEFCLASS-J");
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            title.setAlignStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        title.setBounds(180, 15, 260, 25);
        title.setFont(new Font("SansSerif", 1, 16));
        add(title);
        version = new WrappingLabel();
        try
        {
            version.setText("Version 1.0");
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            version.setAlignStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        version.setBounds(180, 40, 260, 20);
        version.setFont(new Font("SansSerif", 0, 12));
        add(version);
        programmerTitle = new WrappingLabel();
        try
        {
            programmerTitle.setText("Programming and GUI Design");
        }
        catch(PropertyVetoException _ex) { }
        programmerTitle.setBounds(190, 75, 250, 20);
        programmerTitle.setFont(new Font("SansSerif", 1, 12));
        add(programmerTitle);
        programmer = new WrappingLabel();
        try
        {
            programmer.setText("Ulrike Nauck");
        }
        catch(PropertyVetoException _ex) { }
        programmer.setBounds(190, 95, 250, 20);
        programmer.setFont(new Font("SansSerif", 0, 12));
        add(programmer);
        algorithmTitle = new WrappingLabel();
        try
        {
            algorithmTitle.setText("NEFCLASS Model and Learning Algorithm");
        }
        catch(PropertyVetoException _ex) { }
        algorithmTitle.setBounds(190, 130, 250, 20);
        algorithmTitle.setFont(new Font("SansSerif", 1, 12));
        add(algorithmTitle);
        algorithm = new WrappingLabel();
        try
        {
            algorithm.setText("Dr. Detlef Nauck");
        }
        catch(PropertyVetoException _ex) { }
        algorithm.setBounds(190, 150, 250, 20);
        algorithm.setFont(new Font("SansSerif", 0, 12));
        add(algorithm);
        copyright = new WrappingLabel();
        try
        {
            copyright.setText("(c) Ulrike Nauck, Braunschweig, 1999");
        }
        catch(PropertyVetoException _ex) { }
        copyright.setBounds(190, 198, 250, 20);
        copyright.setFont(new Font("SansSerif", 1, 12));
        add(copyright);
        horizontalLine1 = new HorizontalLine();
        try
        {
            horizontalLine1.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine1.setBounds(190, 65, 240, 2);
        add(horizontalLine1);
        okButton = new Button();
        okButton.setLabel("OK");
        okButton.setBounds(182, 242, 75, 25);
        add(okButton);
        horizontalLine3 = new HorizontalLine();
        try
        {
            horizontalLine3.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine3.setBounds(15, 267, 415, 2);
        add(horizontalLine3);
        setTitle("About");
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymAction symaction = new SymAction();
        okButton.addActionListener(symaction);
    }

    public AboutDialog(Frame frame, String s, boolean flag)
    {
        this(frame, flag);
        setTitle(s);
    }

    public void addNotify()
    {
        getSize();
        super.addNotify();
        if(fComponentsAdjusted)
        {
            return;
        }
        setSize(insets().left + insets().right + getSize().width, insets().top + insets().bottom + getSize().height);
        Component acomponent[] = getComponents();
        for(int i = 0; i < acomponent.length; i++)
        {
            Point point = acomponent[i].getLocation();
            point.translate(insets().left, insets().top);
            acomponent[i].setLocation(point);
        }

        fComponentsAdjusted = true;
    }

    public synchronized void show()
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
        move(i, j);
        super.show();
    }

    void AboutDialog_WindowClosing(WindowEvent windowevent)
    {
        dispose();
    }

    void okButton_Clicked(ActionEvent actionevent)
    {
        dispose();
    }
}
