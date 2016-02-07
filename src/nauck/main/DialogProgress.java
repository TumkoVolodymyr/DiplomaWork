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
import java.beans.*;
import java.util.EventObject;
import symantec.itools.awt.*;
import symantec.itools.awt.shape.HorizontalLine;
import symantec.itools.awt.util.ProgressBar;

public class DialogProgress extends Frame
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == DialogProgress.this)
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

    class SymPropertyChange
        implements PropertyChangeListener
    {

        public void propertyChange(PropertyChangeEvent propertychangeevent)
        {
            Object obj = propertychangeevent.getSource();
            if(obj == progressBar)
            {
                progressBar_propertyChange(propertychangeevent);
            }
        }

        SymPropertyChange()
        {
        }
    }


    boolean fComponentsAdjusted;
    LabelButton labelButtonClose;
    Panel panelProgress;
    ProgressBar progressBar;
    TextArea textArea;
    HorizontalLine horizontalLine1;
    HorizontalLine horizontalLine2;

    public DialogProgress()
    {
        fComponentsAdjusted = false;
        setLayout(null);
        setVisible(false);
        setSize(430, 170);
        setBackground(new Color(0xc0c0c0));
        labelButtonClose = new LabelButton();
        try
        {
            labelButtonClose.setText("Close");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonClose.setBounds(169, 140, 89, 25);
        labelButtonClose.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonClose);
        panelProgress = new Panel();
        panelProgress.setLayout(null);
        panelProgress.setBounds(3, 3, 424, 125);
        panelProgress.setBackground(new Color(0xffaae8f4));
        add(panelProgress);
        progressBar = new ProgressBar();
        try
        {
            progressBar.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            progressBar.setProgressBarColor(new Color(0x5f68be));
        }
        catch(PropertyVetoException _ex) { }
        progressBar.setBounds(5, 100, 414, 20);
        progressBar.setFont(new Font("Dialog", 1, 12));
        progressBar.setForeground(new Color(0xffffff));
        progressBar.setBackground(new Color(0x78d6e7));
        panelProgress.add(progressBar);
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setBounds(5, 5, 414, 89);
        textArea.setBackground(new Color(0xfff0ffff));
        panelProgress.add(textArea);
        horizontalLine1 = new HorizontalLine();
        try
        {
            horizontalLine1.setBevelStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine1.setBounds(6, 163, 166, 2);
        add(horizontalLine1);
        horizontalLine2 = new HorizontalLine();
        try
        {
            horizontalLine2.setBevelStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine2.setBounds(257, 163, 169, 2);
        add(horizontalLine2);
        setTitle("Loading...");
        setResizable(false);
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymAction symaction = new SymAction();
        labelButtonClose.addActionListener(symaction);
        SymPropertyChange sympropertychange = new SymPropertyChange();
        progressBar.addPropertyChangeListener(sympropertychange);
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

    public DialogProgress(String s)
    {
        this();
        setTitle(s);
    }

    public void setVisible(boolean flag)
    {
        if(flag)
        {
            setLocation(0, 100);
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

    void progressBar_propertyChange(PropertyChangeEvent propertychangeevent)
    {
        if(progressBar.getProgressPercent() == 100)
        {
            dispose();
        }
    }
}

