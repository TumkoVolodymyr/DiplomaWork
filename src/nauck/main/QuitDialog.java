package nauck.main;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.EventObject;
import symantec.itools.awt.*;
import symantec.itools.awt.shape.HorizontalLine;

public class QuitDialog extends Dialog
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == QuitDialog.this)
            {
                QuitDialog_WindowClosing(windowevent);
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
            if(obj == noButton)
            {
                noButton_Clicked(actionevent);
                return;
            }
            if(obj == yesButton)
            {
                yesButton_Clicked(actionevent);
            }
        }

        SymAction()
        {
        }
    }


    boolean yesClicked;
    boolean fComponentsAdjusted;
    Panel panelQuit;
    WrappingLabel wrappingLabelQTitle;
    HorizontalLine horizontalLine3;
    WrappingLabel wrappingLabelQInstruct;
    WrappingLabel wrappingLabelQExplain;
    LabelButton yesButton;
    LabelButton noButton;
    HorizontalLine horizontalLine1;

    public QuitDialog(Frame frame, boolean flag)
    {
        super(frame, flag);
        fComponentsAdjusted = false;
        yesClicked = false;
        setLayout(null);
        setVisible(false);
        setSize(430, 170);
        setFont(new Font("Dialog", 0, 12));
        setBackground(new Color(0xc0c0c0));
        panelQuit = new Panel();
        panelQuit.setLayout(null);
        panelQuit.setBounds(3, 3, 424, 125);
        panelQuit.setBackground(new Color(0xede1ff));
        add(panelQuit);
        wrappingLabelQTitle = new WrappingLabel();
        try
        {
            wrappingLabelQTitle.setAlignStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelQTitle.setBounds(5, 5, 414, 25);
        wrappingLabelQTitle.setFont(new Font("Dialog", 1, 14));
        panelQuit.add(wrappingLabelQTitle);
        horizontalLine3 = new HorizontalLine();
        try
        {
            horizontalLine3.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine3.setBounds(5, 31, 410, 2);
        panelQuit.add(horizontalLine3);
        wrappingLabelQInstruct = new WrappingLabel();
        try
        {
            wrappingLabelQInstruct.setAlignStyle(1);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelQInstruct.setBounds(5, 40, 414, 25);
        wrappingLabelQInstruct.setFont(new Font("Dialog", 0, 14));
        panelQuit.add(wrappingLabelQInstruct);
        wrappingLabelQExplain = new WrappingLabel();
        wrappingLabelQExplain.setBounds(5, 70, 414, 48);
        panelQuit.add(wrappingLabelQExplain);
        yesButton = new LabelButton();
        try
        {
            yesButton.setText("Yes");
        }
        catch(PropertyVetoException _ex) { }
        yesButton.setBounds(100, 140, 89, 25);
        yesButton.setFont(new Font("SansSerif", 0, 14));
        add(yesButton);
        noButton = new LabelButton();
        try
        {
            noButton.setText("No");
        }
        catch(PropertyVetoException _ex) { }
        noButton.setBounds(240, 140, 89, 25);
        noButton.setFont(new Font("SansSerif", 0, 14));
        add(noButton);
        horizontalLine1 = new HorizontalLine();
        try
        {
            horizontalLine1.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine1.setBounds(6, 163, 418, 2);
        add(horizontalLine1);
        setTitle("Quit NEFCLASS");
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymAction symaction = new SymAction();
        noButton.addActionListener(symaction);
        yesButton.addActionListener(symaction);
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

    public QuitDialog(Frame frame, String s, boolean flag)
    {
        this(frame, flag);
        setTitle(s);
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

    void QuitDialog_WindowClosing(WindowEvent windowevent)
    {
        dispose();
    }

    void yesButton_Clicked(ActionEvent actionevent)
    {
        yesClicked = true;
        setVisible(false);
    }

    void noButton_Clicked(ActionEvent actionevent)
    {
        dispose();
    }

    public boolean closedWithYes()
    {
        return yesClicked;
    }
}
