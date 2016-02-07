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
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.TextComponent;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.EventObject;
import java.util.Vector;
import nauck.fuzzy.FuzzyClassifierRule;
import nauck.fuzzy.FuzzyPartition;
import nauck.fuzzy.FuzzyRule;
import nauck.fuzzy.FuzzySystem;
import nauck.fuzzy.Nefclass;
import nauck.fuzzy.Variable;
import nauck.util.FormatString;
import nauck.util.Help;
import nauck.util.HelpNotAvailableException;
import symantec.itools.awt.BevelStyle;
import symantec.itools.awt.ButtonBase;
import symantec.itools.awt.LabelButton;
import symantec.itools.awt.shape.HorizontalLine;
import symantec.itools.awt.shape.VerticalLine;

public class DialogRuleEdit extends Dialog
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == DialogRuleEdit.this)
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
            if(obj == labelButtonOK)
            {
                labelButtonOK_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonCancel)
            {
                labelButtonCancel_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonSet)
            {
                labelButtonSet_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonRemoveTerm)
            {
                labelButtonRemoveTerm_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonRemoveAll)
            {
                labelButtonRemoveAll_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonModifyRule)
            {
                labelButtonModifyRule_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonAddRule)
            {
                labelButtonAddRule_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonRemoveRule)
            {
                labelButtonRemoveRule_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonRemoveAllRules)
            {
                labelButtonRemoveAllRules_actionPerformed(actionevent);
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

    class SymFocus extends FocusAdapter
    {

        public void focusGained(FocusEvent focusevent)
        {
            Object obj = focusevent.getSource();
            if(obj == variables)
            {
                variables_FocusGained(focusevent);
                return;
            }
            if(obj == fuzzySets)
            {
                fuzzySets_FocusGained(focusevent);
                return;
            }
            if(obj == antecedent)
            {
                antecedent_FocusGained(focusevent);
                return;
            }
            if(obj == consequent)
            {
                consequent_FocusGained(focusevent);
                return;
            }
            if(obj == allRules)
            {
                allRules_FocusGained(focusevent);
            }
        }

        SymFocus()
        {
        }
    }

    class SymItem
        implements ItemListener
    {

        public void itemStateChanged(ItemEvent itemevent)
        {
            Object obj = itemevent.getSource();
            if(obj == variables)
            {
                variables_ItemStateChanged(itemevent);
                return;
            }
            if(obj == allRules)
            {
                allRules_ItemStateChanged(itemevent);
                return;
            }
            if(obj == antecedent)
            {
                antecedent_ItemStateChanged(itemevent);
            }
        }

        SymItem()
        {
        }
    }


    Help help;
    String helpAnchor;
    protected static final String antecedentLabel = "Antecedent of Rule ";
    protected Nefclass nefclass;
    protected Vector ruleBase;
    protected FuzzyPartition partitions[];
    protected FuzzyClassifierRule rule;
    protected int inputs;
    protected int outputs;
    protected int ruleNumber;
    protected int selectedVariable;
    protected int selectedFuzzySet;
    protected int selectedClass;
    protected int selectedTerm;
    protected int selectedRule;
    protected boolean changed;
    protected int antecedentFuzzySets[];
    protected DialogMessage message;
    boolean fComponentsAdjusted;
    HorizontalLine horizontalLine1;
    HorizontalLine horizontalLine2;
    VerticalLine verticalLine1;
    LabelButton labelButtonCancel;
    LabelButton labelButtonOK;
    LabelButton labelButtonHelp;
    Panel panelSelect;
    java.awt.List variables;
    Label labelRuleEditVariableList;
    java.awt.List fuzzySets;
    Label labelRuleEditFuzzySet;
    LabelButton labelButtonSet;
    Panel panelOneRule;
    java.awt.List antecedent;
    Label labelRuleEditAntecedent;
    Label labelRuleEditTHEN;
    java.awt.List consequent;
    Label labelRuleEditConsequent;
    Label labelRuleEditIF;
    LabelButton labelButtonRemoveTerm;
    LabelButton labelButtonModifyRule;
    LabelButton labelButtonRemoveAll;
    LabelButton labelButtonAddRule;
    Panel panelAllRules;
    java.awt.List allRules;
    Panel panelRemoveRule;
    LabelButton labelButtonRemoveRule;
    LabelButton labelButtonRemoveAllRules;
    TextArea helpText;

    public DialogRuleEdit(Frame frame, boolean flag)
    {
        super(frame, flag);
        fComponentsAdjusted = false;
        help = null;
        helpAnchor = null;
        message = new DialogMessage((Frame)getParent(), true);
        changed = false;
        setLayout(null);
        setVisible(false);
        setSize(637, 480);
        horizontalLine1 = new HorizontalLine();
        try
        {
            horizontalLine1.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine1.setBounds(5, 131, 630, 2);
        add(horizontalLine1);
        horizontalLine2 = new HorizontalLine();
        try
        {
            horizontalLine2.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine2.setBounds(5, 301, 630, 2);
        add(horizontalLine2);
        verticalLine1 = new VerticalLine();
        try
        {
            verticalLine1.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        verticalLine1.setBounds(320, 2, 2, 126);
        add(verticalLine1);
        labelButtonCancel = new LabelButton();
        try
        {
            labelButtonCancel.setText("Cancel");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonCancel.setBounds(426, 448, 83, 26);
        labelButtonCancel.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonCancel);
        labelButtonOK = new LabelButton();
        try
        {
            labelButtonOK.setText("OK");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonOK.setBounds(333, 448, 83, 26);
        labelButtonOK.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonOK);
        labelButtonHelp = new LabelButton();
        try
        {
            labelButtonHelp.setText("Help");
        }
        catch(PropertyVetoException _ex) { }
        labelButtonHelp.setBounds(551, 448, 83, 26);
        labelButtonHelp.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonHelp);
        panelSelect = new Panel();
        panelSelect.setLayout(null);
        panelSelect.setBounds(5, 2, 310, 126);
        panelSelect.setBackground(new Color(0xffee9f));
        add(panelSelect);
        variables = new java.awt.List(4);
        panelSelect.add(variables);
        variables.setBounds(5, 27, 128, 95);
        variables.setBackground(new Color(-1046));
        labelRuleEditVariableList = new Label("Variables");
        labelRuleEditVariableList.setBounds(5, 3, 116, 23);
        labelRuleEditVariableList.setFont(new Font("Dialog", 1, 12));
        panelSelect.add(labelRuleEditVariableList);
        fuzzySets = new java.awt.List(4);
        panelSelect.add(fuzzySets);
        fuzzySets.setBounds(138, 27, 128, 95);
        fuzzySets.setBackground(new Color(-1046));
        labelRuleEditFuzzySet = new Label("Fuzzy Sets");
        labelRuleEditFuzzySet.setBounds(140, 3, 123, 23);
        labelRuleEditFuzzySet.setFont(new Font("Dialog", 1, 12));
        panelSelect.add(labelRuleEditFuzzySet);
        labelButtonSet = new LabelButton();
        try
        {
            labelButtonSet.setText("Set");
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            labelButtonSet.setButtonColor(new Color(0xffe2e2e2));
        }
        catch(PropertyVetoException _ex) { }
        labelButtonSet.setBounds(271, 96, 34, 26);
        panelSelect.add(labelButtonSet);
        panelOneRule = new Panel();
        panelOneRule.setLayout(null);
        panelOneRule.setBounds(5, 137, 630, 160);
        panelOneRule.setBackground(new Color(0xffee9f));
        add(panelOneRule);
        antecedent = new java.awt.List(4);
        panelOneRule.add(antecedent);
        antecedent.setBounds(28, 23, 339, 98);
        antecedent.setBackground(new Color(-1046));
        labelRuleEditAntecedent = new Label("Antecedent of Rule ");
        labelRuleEditAntecedent.setBounds(29, 0, 166, 23);
        labelRuleEditAntecedent.setFont(new Font("Dialog", 1, 12));
        labelRuleEditAntecedent.setForeground(new Color(0));
        panelOneRule.add(labelRuleEditAntecedent);
        labelRuleEditTHEN = new Label("Then");
        labelRuleEditTHEN.setBounds(383, 19, 40, 23);
        labelRuleEditTHEN.setFont(new Font("Dialog", 1, 12));
        labelRuleEditTHEN.setForeground(new Color(0));
        panelOneRule.add(labelRuleEditTHEN);
        consequent = new java.awt.List(4);
        consequent.addItem("");
        panelOneRule.add(consequent);
        consequent.setBounds(427, 23, 191, 98);
        consequent.setBackground(new Color(-1046));
        labelRuleEditConsequent = new Label("Consequent (Class)");
        labelRuleEditConsequent.setBounds(427, 0, 188, 23);
        labelRuleEditConsequent.setFont(new Font("Dialog", 1, 12));
        labelRuleEditConsequent.setForeground(new Color(0));
        panelOneRule.add(labelRuleEditConsequent);
        labelRuleEditIF = new Label("IF");
        labelRuleEditIF.setBounds(7, 19, 20, 23);
        labelRuleEditIF.setFont(new Font("Dialog", 1, 12));
        labelRuleEditIF.setForeground(new Color(0));
        panelOneRule.add(labelRuleEditIF);
        labelButtonRemoveTerm = new LabelButton();
        try
        {
            labelButtonRemoveTerm.setText("Remove Term");
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            labelButtonRemoveTerm.setButtonColor(new Color(0xffe2e2e2));
        }
        catch(PropertyVetoException _ex) { }
        labelButtonRemoveTerm.setBounds(192, 128, 85, 26);
        panelOneRule.add(labelButtonRemoveTerm);
        labelButtonModifyRule = new LabelButton();
        try
        {
            labelButtonModifyRule.setText("Modify Rule");
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            labelButtonModifyRule.setButtonColor(new Color(0xffe2e2e2));
        }
        catch(PropertyVetoException _ex) { }
        labelButtonModifyRule.setBounds(443, 128, 85, 26);
        panelOneRule.add(labelButtonModifyRule);
        labelButtonRemoveAll = new LabelButton();
        try
        {
            labelButtonRemoveAll.setText("Remove All");
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            labelButtonRemoveAll.setButtonColor(new Color(0xffe2e2e2));
        }
        catch(PropertyVetoException _ex) { }
        labelButtonRemoveAll.setBounds(282, 128, 85, 26);
        panelOneRule.add(labelButtonRemoveAll);
        labelButtonAddRule = new LabelButton();
        try
        {
            labelButtonAddRule.setText("Add Rule");
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            labelButtonAddRule.setButtonColor(new Color(0xffe2e2e2));
        }
        catch(PropertyVetoException _ex) { }
        labelButtonAddRule.setBounds(533, 128, 85, 26);
        panelOneRule.add(labelButtonAddRule);
        panelAllRules = new Panel();
        panelAllRules.setLayout(null);
        panelAllRules.setBounds(5, 307, 630, 132);
        panelAllRules.setBackground(new Color(0xffee9f));
        add(panelAllRules);
        allRules = new java.awt.List(4);
        panelAllRules.add(allRules);
        allRules.setBounds(5, 5, 620, 122);
        allRules.setBackground(new Color(-1046));
        panelRemoveRule = new Panel();
        panelRemoveRule.setLayout(null);
        panelRemoveRule.setBounds(5, 439, 250, 33);
        panelRemoveRule.setBackground(new Color(0xffee9f));
        add(panelRemoveRule);
        labelButtonRemoveRule = new LabelButton();
        try
        {
            labelButtonRemoveRule.setText("Remove Rule");
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            labelButtonRemoveRule.setButtonColor(new Color(0xffe2e2e2));
        }
        catch(PropertyVetoException _ex) { }
        labelButtonRemoveRule.setBounds(5, 1, 85, 26);
        panelRemoveRule.add(labelButtonRemoveRule);
        labelButtonRemoveAllRules = new LabelButton();
        try
        {
            labelButtonRemoveAllRules.setText("Remove All");
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            labelButtonRemoveAllRules.setButtonColor(new Color(0xffe2e2e2));
        }
        catch(PropertyVetoException _ex) { }
        labelButtonRemoveAllRules.setBounds(95, 1, 85, 26);
        panelRemoveRule.add(labelButtonRemoveAllRules);
        helpText = new TextArea("", 0, 0, 1);
        helpText.setEditable(false);
        helpText.setBounds(327, 2, 307, 126);
        helpText.setBackground(new Color(0xffffff));
        add(helpText);
        setTitle("Edit Rules");
        setResizable(false);
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymAction symaction = new SymAction();
        labelButtonOK.addActionListener(symaction);
        labelButtonCancel.addActionListener(symaction);
        SymFocus symfocus = new SymFocus();
        variables.addFocusListener(symfocus);
        fuzzySets.addFocusListener(symfocus);
        antecedent.addFocusListener(symfocus);
        consequent.addFocusListener(symfocus);
        allRules.addFocusListener(symfocus);
        SymItem symitem = new SymItem();
        variables.addItemListener(symitem);
        allRules.addItemListener(symitem);
        labelButtonSet.addActionListener(symaction);
        labelButtonRemoveTerm.addActionListener(symaction);
        labelButtonRemoveAll.addActionListener(symaction);
        labelButtonModifyRule.addActionListener(symaction);
        labelButtonAddRule.addActionListener(symaction);
        labelButtonRemoveRule.addActionListener(symaction);
        labelButtonRemoveAllRules.addActionListener(symaction);
        antecedent.addItemListener(symitem);
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

    public DialogRuleEdit(Frame frame, String s, boolean flag)
    {
        this(frame, flag);
        setTitle(s);
    }

    public void setVisible(boolean flag)
    {
        if(flag)
        {
            setLocation(100, 50);
        }
        super.setVisible(flag);
    }

    void Dialog1_WindowClosing(WindowEvent windowevent)
    {
        setVisible(false);
    }

    void labelButtonOK_actionPerformed(ActionEvent actionevent)
    {
        if(changed)
        {
            if(nefclass.isRuleBaseCopyConsistent())
            {
                nefclass.setRuleBaseCopy();
                message.showMessage("Rule Base Accepted!", "The rule base was accepted.", "The rule base was accepted and is now used by the classifier. If you need to res" +
"tore the previous rule base use the entry 'Restore Rule Base' from the 'Rule' me" +
"nu."
);
                setVisible(false);
                return;
            } else
            {
                message.showMessage("Rule Base Error!", "Please correct the rule base.", "The rule base contains errors and cannot be saved. Please correct the following " +
"problem: "
 + nefclass.getConsistencyCheckMessage());
                return;
            }
        } else
        {
            setVisible(false);
            return;
        }
    }

    void labelButtonCancel_actionPerformed(ActionEvent actionevent)
    {
        setVisible(false);
    }

    public void resetDialog()
    {
        variables.removeAll();
        fuzzySets.removeAll();
        antecedent.removeAll();
        consequent.removeAll();
        allRules.removeAll();
        helpText.setText("To enter a new rule to the rule base, select a variable and a fuzzy set. Then pr" +
"ess the Set button, to add a a new term to the antecedent. When the antecedent i" +
"s complete select a consequent and press Add Rule to create a new rule.\nClick o" +
"n each list to obtain more information.\nTo save your changes, leave with OK, to" +
" discard all modifications, use Cancel."
);
    }

    protected void setFuzzySets(int i)
    {
        fuzzySets.removeAll();
        for(int j = 0; j < partitions[i].getNumberOfFuzzySets(); j++)
        {
            fuzzySets.add(partitions[i].getFuzzySetName(j));
        }

    }

    protected void setAntecedent(FuzzyClassifierRule fuzzyclassifierrule)
    {
        selectedVariable = -1;
        resetAntecedent();
        for(int j = 0; j < inputs; j++)
        {
            int i = fuzzyclassifierrule.getAntecedentFuzzySetIndex(j);
            if(i >= 0)
            {
                antecedentFuzzySets[j] = i;
                if(selectedVariable == -1)
                {
                    selectedVariable = j;
                    selectedFuzzySet = i;
                }
                String s = partitions[j].getName() + " is " + partitions[j].getFuzzySetName(i);
                if(j < inputs - 1)
                {
                    antecedent.add(s + " and");
                } else
                {
                    antecedent.add(s);
                }
            }
        }

        if(selectedVariable > -1)
        {
            antecedent.select(0);
            variables.select(selectedVariable);
            fuzzySets.select(selectedFuzzySet);
        }
        labelRuleEditAntecedent.setText("Antecedent of Rule " + fuzzyclassifierrule.getName());
    }

    protected void resetAntecedent()
    {
        antecedent.removeAll();
        labelRuleEditAntecedent.setText("Antecedent of Rule ");
        for(int i = 0; i < inputs; i++)
        {
            antecedentFuzzySets[i] = -1;
        }

    }

    protected void createNewRule()
    {
        if(antecedent.getItemCount() > 0 && consequent.getSelectedIndex() >= 0)
        {
            rule = new FuzzyClassifierRule("R" + ruleNumber, nefclass.getTNormType(), inputs, new Variable(consequent.getSelectedIndex(), consequent.getSelectedItem()));
            for(int i = 0; i < inputs; i++)
            {
                if(antecedentFuzzySets[i] > -1)
                {
                    rule.addAntecedent(new Variable(i, partitions[i].getName()), partitions[i].getFuzzySet(antecedentFuzzySets[i]));
                }
            }

            return;
        } else
        {
            rule = null;
            return;
        }
    }

    protected int getVariableIndex(String s)
    {
        boolean flag = false;
        int i = -1;
        for(int j = 0; j < inputs && !flag; j++)
        {
            if(s.startsWith(partitions[j].getName()))
            {
                flag = true;
                i = j;
            }
        }

        return i;
    }

    public void setNefclass(Nefclass nefclass1)
    {
        changed = false;
        resetDialog();
        nefclass = nefclass1;
        if(nefclass1 != null)
        {
            ruleBase = nefclass1.getRuleBaseCopy();
            inputs = nefclass1.getInputs();
            outputs = nefclass1.getOutputs();
            ruleNumber = ruleBase.size();
            antecedentFuzzySets = new int[inputs];
            resetAntecedent();
            partitions = nefclass1.getPartitions();
            for(int i = 0; i < partitions.length; i++)
            {
                variables.add(partitions[i].getName());
            }

            variables.select(0);
            selectedVariable = 0;
            setFuzzySets(selectedVariable);
            if(ruleBase.size() > 0)
            {
                setAntecedent((FuzzyClassifierRule)ruleBase.elementAt(0));
            }
            for(int j = 0; j < outputs; j++)
            {
                consequent.add(nefclass1.getClassName(j));
            }

            for(int k = 0; k < ruleBase.size(); k++)
            {
                rule = (FuzzyClassifierRule)ruleBase.elementAt(k);
                if(nefclass1.getUseWeights())
                {
                    allRules.add(rule.getName() + ": " + rule.toString() + " (" + FormatString.doubleString(rule.getWeight(), 0, 2) + "), performance = " + FormatString.doubleString(rule.getPerformance(), 0, 2));
                } else
                {
                    allRules.add(rule.getName() + ": " + rule.toString() + ", performance = " + FormatString.doubleString(rule.getPerformance(), 0, 2));
                }
            }

            if(ruleBase.size() > 0)
            {
                rule = (FuzzyClassifierRule)ruleBase.elementAt(0);
                allRules.select(0);
                selectedRule = 0;
                consequent.select(rule.getClassIndex());
            }
        }
    }

    void variables_FocusGained(FocusEvent focusevent)
    {
        helpText.setText("The variable list displays the names of all available variables. To enter a new " +
"term to the antecedent of the selected rule or to change the term for this varia" +
"ble select an appropriate fuzzy set from the Fuzzy Sets list and press Set."
);
    }

    void fuzzySets_FocusGained(FocusEvent focusevent)
    {
        helpText.setText("The Fuzzy Sets list shows all available fuzzy sets of the currently selected var" +
"iable of the Variables list. The content of the list changes, if you select anot" +
"her variable. After selecting a suitable fuzzy set for the selected variable pre" +
"ss Set to enter or modify the linguistic term of the selected variable in the an" +
"tecedent of the selected rule."
);
    }

    void antecedent_FocusGained(FocusEvent focusevent)
    {
        helpText.setText("The Antecedent list shows all linguistic terms of the antecedent of the selected" +
" rule. You can remove the selected entry by clicking on Remove Term.Clicking on " +
"Remove All deletes all entries. To add or modify an entry, select the variable c" +
"ontained in the term, select an appropriate fuzzy set and click on Set (see also" +
" the information for the Variables and Fuzzy Sets lists.)"
);
    }

    void consequent_FocusGained(FocusEvent focusevent)
    {
        helpText.setText("The Consquent list shows the names of all available classes. After creating an a" +
"ntecedent select an appropriate class from this list to complete the rule. If yo" +
"u press on Add Rule, a new rule will be added to the end of the rule base. If yo" +
"u click on Modify Rule the currently selected rule will be replaced by the rule " +
"given by the current antecedent and consequent."
);
    }

    void allRules_FocusGained(FocusEvent focusevent)
    {
        helpText.setText("The All Rule list shows the complete rule base. You can delete the selected rule" +
" by pressing Remove Rule. The button Remove All deletes the whole rule base. To " +
"enter new rules create an antecedent, select a consequent and press Add Rule. Pr" +
"ess Modify Rule to replace the selected rule by the rule given by the entries in" +
" Antecedent and Consequent."
);
    }

    void variables_ItemStateChanged(ItemEvent itemevent)
    {
        selectedVariable = variables.getSelectedIndex();
        setFuzzySets(selectedVariable);
        if(antecedentFuzzySets[selectedVariable] > -1)
        {
            selectedFuzzySet = antecedentFuzzySets[selectedVariable];
            fuzzySets.select(selectedFuzzySet);
        }
    }

    void antecedent_ItemStateChanged(ItemEvent itemevent)
    {
        int i = getVariableIndex(antecedent.getSelectedItem());
        if(i > -1)
        {
            variables.select(i);
            fuzzySets.select(antecedentFuzzySets[i]);
        }
    }

    void allRules_ItemStateChanged(ItemEvent itemevent)
    {
        selectedRule = allRules.getSelectedIndex();
        rule = (FuzzyClassifierRule)ruleBase.elementAt(selectedRule);
        consequent.select(rule.getClassIndex());
        setAntecedent(rule);
    }

    void labelButtonSet_actionPerformed(ActionEvent actionevent)
    {
        int i = variables.getSelectedIndex();
        int j = fuzzySets.getSelectedIndex();
        int k = antecedent.getItemCount();
        int l = 0;
        if(i > -1 && j > -1)
        {
            for(int i1 = 0; i1 < i; i1++)
            {
                if(antecedentFuzzySets[i1] > -1)
                {
                    l++;
                }
            }

            antecedentFuzzySets[i] = j;
            String s = variables.getSelectedItem() + " is " + fuzzySets.getSelectedItem();
            if(l < k)
            {
                if(getVariableIndex(antecedent.getItem(l)) == i)
                {
                    if(l < k - 1)
                    {
                        s = s + " and";
                    }
                    antecedent.replaceItem(s, l);
                    return;
                } else
                {
                    s = s + " and";
                    antecedent.add(s, l);
                    return;
                }
            }
            if(k > 0)
            {
                antecedent.replaceItem(antecedent.getItem(k - 1) + " and", k - 1);
            }
            antecedent.add(s, l);
        }
    }

    void labelButtonRemoveTerm_actionPerformed(ActionEvent actionevent)
    {
        selectedTerm = antecedent.getSelectedIndex();
        if(selectedTerm >= 0)
        {
            String s = antecedent.getSelectedItem();
            int i = getVariableIndex(s);
            antecedent.delItem(selectedTerm);
            antecedentFuzzySets[i] = -1;
            int k = antecedent.getItemCount();
            if(k > 0 && selectedTerm == k)
            {
                String s1 = antecedent.getItem(k - 1);
                s1 = s1.substring(0, s1.length() - 4);
                antecedent.replaceItem(s1, k - 1);
            }
            if(selectedTerm >= k)
            {
                selectedTerm = k - 1;
            }
            if(selectedTerm >= 0)
            {
                antecedent.select(selectedTerm);
                String s2 = antecedent.getSelectedItem();
                int j = getVariableIndex(s2);
                variables.select(j);
                variables_ItemStateChanged(null);
            }
        }
    }

    void labelButtonRemoveAll_actionPerformed(ActionEvent actionevent)
    {
        resetAntecedent();
    }

    void labelButtonModifyRule_actionPerformed(ActionEvent actionevent)
    {
        int i = allRules.getSelectedIndex();
        if(i > -1)
        {
            String s = ((FuzzyClassifierRule)ruleBase.elementAt(i)).getName();
            createNewRule();
            if(rule != null && i > -1)
            {
                changed = true;
                rule.setName(s);
                ruleBase.setElementAt(rule, i);
                if(nefclass.getUseWeights())
                {
                    allRules.replaceItem(rule.getName() + ": " + rule.toString() + " (" + FormatString.doubleString(rule.getWeight(), 0, 2) + "), performance = " + FormatString.doubleString(rule.getPerformance(), 0, 2), i);
                } else
                {
                    allRules.replaceItem(rule.getName() + ": " + rule.toString() + ", performance = " + FormatString.doubleString(rule.getPerformance(), 0, 2), i);
                }
                allRules.select(i);
            }
        }
    }

    void labelButtonAddRule_actionPerformed(ActionEvent actionevent)
    {
        createNewRule();
        if(rule != null)
        {
            changed = true;
            ruleBase.addElement(rule);
            if(nefclass.getUseWeights())
            {
                allRules.add(rule.getName() + ": " + rule.toString() + " (" + FormatString.doubleString(rule.getWeight(), 0, 2) + "), performance = " + FormatString.doubleString(rule.getPerformance(), 0, 2));
            } else
            {
                allRules.add(rule.getName() + ": " + rule.toString() + ", performance = " + FormatString.doubleString(rule.getPerformance(), 0, 2));
            }
            ruleNumber++;
            allRules.select(allRules.getItemCount() - 1);
            labelRuleEditAntecedent.setText("Antecedent of Rule " + rule.getName());
        }
    }

    void labelButtonRemoveRule_actionPerformed(ActionEvent actionevent)
    {
        int i = allRules.getSelectedIndex();
        if(i > -1)
        {
            changed = true;
            allRules.delItem(i);
            ruleBase.removeElementAt(i);
            rule = null;
            resetAntecedent();
            if(i == ruleBase.size())
            {
                ruleNumber--;
            }
            if(i < ruleBase.size())
            {
                selectedRule = i;
            } else
            {
                selectedRule = ruleBase.size() - 1;
            }
            if(selectedRule > -1)
            {
                allRules.select(selectedRule);
                rule = (FuzzyClassifierRule)ruleBase.elementAt(selectedRule);
                setAntecedent(rule);
                return;
            }
            ruleNumber = 0;
        }
    }

    void labelButtonRemoveAllRules_actionPerformed(ActionEvent actionevent)
    {
        allRules.removeAll();
        ruleNumber = 0;
        ruleBase.removeAllElements();
        resetAntecedent();
        selectedRule = -1;
        rule = null;
        changed = true;
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
