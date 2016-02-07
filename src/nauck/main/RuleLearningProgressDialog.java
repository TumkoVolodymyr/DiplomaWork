package nauck.main;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.EventObject;
import nauck.util.Help;
import nauck.util.HelpNotAvailableException;
import symantec.itools.awt.*;
import symantec.itools.awt.util.ProgressBar;

public class RuleLearningProgressDialog extends Frame
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == RuleLearningProgressDialog.this)
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
            if(obj == labelButtonRuleLearnOK)
            {
                labelButtonRuleLearnOK_ActionPerformed(actionevent);
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
    Panel panelRuleLearnProgress;
    TextArea statusTextArea;
    Label antecedentLabel;
    ProgressBar antecedentProgressBar;
    Label consequentLabel;
    Label selectLabel;
    ProgressBar consequentProgressBar;
    ProgressBar selectProgressBar;
    LabelButton labelButtonRuleLearnOK;
    LabelButton labelButtonHelp;

    public RuleLearningProgressDialog()
    {
        fComponentsAdjusted = false;
        help = null;
        helpAnchor = null;
        message = new DialogMessage(this, true);
        setLayout(null);
        setVisible(false);
        setSize(637, 480);
        setBackground(new Color(0xc0c0c0));
        panelRuleLearnProgress = new Panel();
        panelRuleLearnProgress.setLayout(null);
        panelRuleLearnProgress.setBounds(5, 5, 627, 420);
        panelRuleLearnProgress.setBackground(new Color(0xfffccb9e));
        add(panelRuleLearnProgress);
        statusTextArea = new TextArea();
        statusTextArea.setBounds(5, 5, 617, 191);
        statusTextArea.setFont(new Font("MonoSpaced", 0, 12));
        panelRuleLearnProgress.add(statusTextArea);
        antecedentLabel = new Label("Searching for antecedents");
        antecedentLabel.setBounds(5, 217, 617, 25);
        panelRuleLearnProgress.add(antecedentLabel);
        antecedentProgressBar = new ProgressBar();
        try
        {
            antecedentProgressBar.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            antecedentProgressBar.setProgressBarColor(new Color(-39322));
        }
        catch(PropertyVetoException _ex) { }
        antecedentProgressBar.setBounds(5, 247, 617, 26);
        antecedentProgressBar.setBackground(new Color(0xfffcbe87));
        panelRuleLearnProgress.add(antecedentProgressBar);
        consequentLabel = new Label("Searching for consequents");
        consequentLabel.setBounds(5, 283, 617, 25);
        panelRuleLearnProgress.add(consequentLabel);
        selectLabel = new Label("Selecting rules");
        selectLabel.setBounds(5, 349, 617, 25);
        panelRuleLearnProgress.add(selectLabel);
        consequentProgressBar = new ProgressBar();
        try
        {
            consequentProgressBar.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            consequentProgressBar.setProgressBarColor(new Color(0xfff5de6d));
        }
        catch(PropertyVetoException _ex) { }
        consequentProgressBar.setBounds(5, 313, 617, 26);
        consequentProgressBar.setBackground(new Color(0xfffcbe87));
        panelRuleLearnProgress.add(consequentProgressBar);
        selectProgressBar = new ProgressBar();
        try
        {
            selectProgressBar.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            selectProgressBar.setProgressBarColor(new Color(0xff78cb76));
        }
        catch(PropertyVetoException _ex) { }
        selectProgressBar.setBounds(5, 379, 617, 26);
        selectProgressBar.setBackground(new Color(0xfffcbe87));
        panelRuleLearnProgress.add(selectProgressBar);
        labelButtonRuleLearnOK = new LabelButton();
        try
        {
            labelButtonRuleLearnOK.setText("Close");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonRuleLearnOK.setBounds(473, 444, 70, 24);
        labelButtonRuleLearnOK.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonRuleLearnOK);
        labelButtonHelp = new LabelButton();
        try
        {
            labelButtonHelp.setText("Help");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonHelp.setBounds(562, 444, 70, 24);
        labelButtonHelp.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonHelp);
        setTitle("Rule Learning");
        setResizable(false);
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymAction symaction = new SymAction();
        labelButtonRuleLearnOK.addActionListener(symaction);
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

    public RuleLearningProgressDialog(String s)
    {
        this();
        setTitle(s);
    }

    public void setVisible(boolean flag)
    {
        if(flag)
        {
            setLocation(150, 150);
        }
        super.setVisible(flag);
    }

    void Dialog1_WindowClosing(WindowEvent windowevent)
    {
        setVisible(false);
    }

    void labelButtonRuleLearnOK_ActionPerformed(ActionEvent actionevent)
    {
        setVisible(false);
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
