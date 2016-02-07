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
import java.io.IOException;
import java.util.EventObject;
import nauck.data.DataTable;
import nauck.data.ParseDataFileException;
import nauck.fuzzy.*;
import nauck.util.Help;
import nauck.util.HelpNotAvailableException;
import symantec.itools.awt.*;
import symantec.itools.awt.shape.HorizontalLine;
import symantec.itools.awt.shape.VerticalLine;

public class DialogProjectSpecification extends Dialog
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == DialogProjectSpecification.this)
            {
                Dialog1_WindowClosing(windowevent);
            }
        }

        SymWindow()
        {
        }
    }

    class SymItem
        implements ItemListener
    {

        public void itemStateChanged(ItemEvent itemevent)
        {
            Object obj = itemevent.getSource();
            if(obj == radioButtonSingleTest)
            {
                radioButtonSingleTest_ItemStateChanged(itemevent);
                return;
            }
            if(obj == radioButtonTrainingCrossVal)
            {
                radioButtonTrainingCrossVal_ItemStateChanged(itemevent);
                return;
            }
            if(obj == radioButtonTrainingNoVal)
            {
                radioButtonTrainingNoVal_ItemStateChanged(itemevent);
                return;
            }
            if(obj == radioButtonClassificationFSIndividual)
            {
                radioButtonClassificationFSIndividual_ItemStateChanged(itemevent);
                return;
            }
            if(obj == radioButtonClassificationFSSame)
            {
                radioButtonClassificationFSSame_ItemStateChanged(itemevent);
                return;
            }
            if(obj == radioButtonAutomaticFR)
            {
                radioButtonAutomaticFR_ItemStateChanged(itemevent);
                return;
            }
            if(obj == radioButtonMaxNumberFR)
            {
                radioButtonMaxNumberFR_ItemStateChanged(itemevent);
            }
        }

        SymItem()
        {
        }
    }

    class SymAction
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            Object obj = actionevent.getSource();
            if(obj == labelButtonEditLabels)
            {
                labelButtonEditLabels_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonStatistics)
            {
                labelButtonStatistics_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonDataFileOpen)
            {
                labelButtonDataFileOpen_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonProjectSpecificationCancel)
            {
                labelButtonProjectSpecificationCancel_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonProjectSpecificationOK)
            {
                labelButtonProjectSpecificationOK_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonClassifierFSSpecify)
            {
                labelButtonClassifierFSSpecify_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonClassifierChange)
            {
                labelButtonClassifierChange_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonProjectSpecificationHelp)
            {
                labelButtonProjectSpecificationHelp_actionPerformed(actionevent);
            }
        }

        SymAction()
        {
        }
    }


    Help help;
    String helpAnchor;
    DialogMessage message;
    DataTable dataTable;
    Nefclass nefclass;
    ParameterList parameter;
    boolean nefclassWasNull;
    boolean fComponentsAdjusted;
    TabPanel tabPanelProjectSpecification;
    Panel panelProject;
    Panel panelProjectLeft;
    WrappingLabel wrappingLabelProjectTitle;
    TextField textFieldProjectTitle;
    WrappingLabel wrappingLabelProjectDescription;
    TextArea textAreaProjectDescription;
    VerticalLine verticalLinePoject;
    Panel panelProjectRight;
    WrappingLabel wrappingLabelProject;
    WrappingLabel wrappingLabelProjectText1;
    WrappingLabel wrappingLabelProjectText2;
    Panel panelData;
    Panel panelDataLeft;
    WrappingLabel wrappingLabelDataFileName;
    TextField textFieldDataFileName;
    Button labelButtonDataFileOpen;
    WrappingLabel wrappingLabelDataFileDescription;
    TextArea textAreaDataDescription;
    VerticalLine verticalLineData;
    Panel panelDataRight;
    WrappingLabel wrappingLabelDF;
    WrappingLabel wrappingLabelDFText;
    WrappingLabel wrappingLabelDataDF;
    WrappingLabel wrappingLabelDataELText;
    Button labelButtonEditLabels;
    Button labelButtonStatistics;
    WrappingLabel wrappingLabelData4;
    WrappingLabel wrappingLabelDataS;
    Panel panelClassifier;
    Panel panelClassifierLeft;
    WrappingLabel wrappingLabelClassifierFuzzySets;
    RadioButtonGroupPanel radioButtonGroupPanelFuzzySet;
    Checkbox radioButtonClassificationFSSame;
    CheckboxGroup Group1;
    Label labelClassifierFSNumber;
    TextField textFieldClassifierFSNumber;
    Label labelClassifierFSForm;
    Choice choiceClassifierFSForm;
    Checkbox radioButtonClassificationFSIndividual;
    Button labelButtonClassifierFSSpecify;
    HorizontalLine horizontalLineClassifierL1;
    WrappingLabel wrappingLabelClassifierAggregation;
    RadioButtonGroupPanel radioButtonGroupPanelAggregation;
    Checkbox radioButtonClassifierAFMax;
    CheckboxGroup Group2;
    Checkbox radioButtonClassifierAFWS;
    HorizontalLine horizontalLineClassifierL2;
    WrappingLabel wrappingLabelClassifierICR;
    Choice choiceClassifierICR;
    VerticalLine verticalLineClassifier;
    Panel panelClassifierRight;
    WrappingLabel wrappingLabelClassifierFSText;
    Button labelButtonClassifierChange;
    HorizontalLine horizontalLineClassifierR1;
    WrappingLabel wrappingLabelClassifierCR;
    WrappingLabel wrappingLabelClassifierCRText;
    Panel panelRuleCreation;
    Panel panelRuleCreationLeft;
    WrappingLabel wrappingLabelSizeRB;
    RadioButtonGroupPanel radioButtonGroupPanelSizeRB;
    Checkbox radioButtonMaxNumberFR;
    Checkbox radioButtonAutomaticFR;
    TextField textFieldSizeRB;
    HorizontalLine horizontalLineRuleCreationL1;
    WrappingLabel wrappingLabelRLProcedure;
    RadioButtonGroupPanel radioButtonGroupPanelRuleLearning;
    Checkbox radioButtonRuleLearningBest;
    Checkbox radioButtonRuleLearningBestPClass;
    HorizontalLine horizontalLineRuleCreationL2;
    Checkbox checkboxReLearn;
    WrappingLabel wrappingLabelRBReLearn;
    VerticalLine verticalLineRuleCreation;
    Panel panelRuleCreationRight;
    WrappingLabel wrappingLabelRB;
    WrappingLabel wrappingLabelRBText1;
    WrappingLabel wrappingLabelRBText2;
    Panel panelTrainingFSets;
    Panel panelTrainingFSetsLeft;
    WrappingLabel wrappingLabelConstraints;
    Panel panelFFsetConstraints;
    Label labelConstraintsFS;
    Checkbox checkboxRelativeOrder;
    Checkbox checkboxOverlap;
    Checkbox checkboxSymmetrical;
    Checkbox checkboxIntersect;
    RadioButtonGroupPanel radioButtonGroupPanelConstraints;
    Checkbox radioButtonWeightNotUsed;
    Checkbox radioButtonInterval;
    Checkbox radioButtonArbitrary;
    Label labelConstraintsWeights;
    HorizontalLine horizontalLineTrainingFSetsL1;
    Panel panelLearningRate;
    WrappingLabel wrappingLabelLearningRate;
    TextField textFieldLearningRate;
    VerticalLine verticalLineTrainingFSets;
    Panel panelTrainingFSetsRight;
    WrappingLabel wrappingLabelC;
    WrappingLabel wrappingLabelCText;
    WrappingLabel wrappingLabelCText2;
    HorizontalLine horizontalLineTrainingFSetsR1;
    WrappingLabel wrappingLabelLR;
    WrappingLabel wrappingLabelLRText;
    Panel panelTrainingControl;
    Panel panelTrainingControlLeft;
    WrappingLabel wrappingLabelValidation;
    RadioButtonGroupPanel radioButtonGroupPanelValidation;
    Checkbox radioButtonTrainingNoVal;
    Checkbox radioButtonTrainingCrossVal;
    Checkbox radioButtonSingleTest;
    TextField textFieldCrossValNumber;
    TextField textFieldSingleTest;
    Label labelCrossValNumber;
    Label labelSingleTest;
    HorizontalLine horizontalLineTrainingControlL1;
    WrappingLabel wrappingLabelStopControl;
    Panel panelStopControl;
    Label labelNoEpochsMax;
    TextField textFieldNoEpochsMax;
    Label labelNoEpochsMin;
    TextField textFieldNoEpochsMin;
    Label labelAfterOpt;
    TextField textFieldAfterOpt;
    Label labelAdmClassErrors;
    TextField textFieldAdmClassErrors;
    VerticalLine verticalLineTrainingControl;
    Panel panelTrainingControlRight;
    WrappingLabel wrappingLabelV;
    WrappingLabel wrappingLabelCVText;
    WrappingLabel wrappingLabelSTText;
    WrappingLabel wrappingLabelST;
    WrappingLabel wrappingLabelSCText;
    Button labelButtonProjectSpecificationOK;
    Button labelButtonProjectSpecificationCancel;
    Button labelButtonProjectSpecificationHelp;

    public DialogProjectSpecification(Frame frame, boolean flag)
    {
        super(frame, flag);
        fComponentsAdjusted = false;
        help = null;
        helpAnchor = null;
        message = new DialogMessage((Frame)getParent(), true);
        dataTable = null;
        nefclass = null;
        nefclassWasNull = true;
        setLayout(null);
        setVisible(false);
        setSize(580, 360);
        setBackground(new Color(0xc0c0c0));
        tabPanelProjectSpecification = new TabPanel();
        try
        {
            String as[] = new String[6];
            as[0] = new String("Project");
            as[1] = new String("Data");
            as[2] = new String("Classifier");
            as[3] = new String("Rule Creation");
            as[4] = new String("Training FSets");
            as[5] = new String("Training Control");
            tabPanelProjectSpecification.setPanelLabels(as);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            tabPanelProjectSpecification.setCurrentPanelNdx(0);
        }
        catch(PropertyVetoException _ex) { }
        tabPanelProjectSpecification.setBounds(5, 5, 570, 320);
        add(tabPanelProjectSpecification);
        panelProject = new Panel();
        panelProject.setLayout(null);
        panelProject.setVisible(false);
        panelProject.setBounds(12, 33, 546, 276);
        panelProject.setBackground(new Color(0xff94eddf));
        tabPanelProjectSpecification.add(panelProject);
        panelProjectLeft = new Panel();
        panelProjectLeft.setLayout(null);
        panelProjectLeft.setBounds(5, 5, 263, 266);
        panelProject.add(panelProjectLeft);
        wrappingLabelProjectTitle = new WrappingLabel();
        try
        {
            wrappingLabelProjectTitle.setText("Project Title");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelProjectTitle.setBounds(3, 5, 250, 23);
        wrappingLabelProjectTitle.setFont(new Font("Dialog", 1, 12));
        panelProjectLeft.add(wrappingLabelProjectTitle);
        textFieldProjectTitle = new TextField();
        textFieldProjectTitle.setBounds(3, 30, 250, 25);
        panelProjectLeft.add(textFieldProjectTitle);
        wrappingLabelProjectDescription = new WrappingLabel();
        try
        {
            wrappingLabelProjectDescription.setText("Project Description");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelProjectDescription.setBounds(3, 75, 250, 23);
        wrappingLabelProjectDescription.setFont(new Font("Dialog", 1, 12));
        panelProjectLeft.add(wrappingLabelProjectDescription);
        textAreaProjectDescription = new TextArea();
        textAreaProjectDescription.setBounds(3, 105, 250, 150);
        panelProjectLeft.add(textAreaProjectDescription);
        verticalLinePoject = new VerticalLine();
        try
        {
            verticalLinePoject.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        verticalLinePoject.setBounds(273, 10, 2, 256);
        panelProject.add(verticalLinePoject);
        panelProjectRight = new Panel();
        panelProjectRight.setLayout(null);
        panelProjectRight.setBounds(279, 5, 263, 266);
        panelProject.add(panelProjectRight);
        wrappingLabelProject = new WrappingLabel();
        try
        {
            wrappingLabelProject.setText("The Project");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelProject.setBounds(3, 5, 250, 23);
        wrappingLabelProject.setFont(new Font("Dialog", 1, 12));
        panelProjectRight.add(wrappingLabelProject);
        wrappingLabelProjectText1 = new WrappingLabel();
        try
        {
            wrappingLabelProjectText1.setText("A project contains every information concerning a Neuro-Fuzzy System created wit" +
"h the NEFCLASS-Tool.  Default values are given for all parameters needed to crea" +
"te the Neuro-Fuzzy System so that you only have to load a data file in the \"Dat" +
"a\"-tab.  But all parameters can be changed in the other tabs of this dialog. "
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelProjectText1.setBounds(3, 30, 250, 124);
        panelProjectRight.add(wrappingLabelProjectText1);
        wrappingLabelProjectText2 = new WrappingLabel();
        try
        {
            wrappingLabelProjectText2.setText("If you open an existing project your Neuro-Fuzzy System with all parameters is l" +
"oaded as well as the last used data file. So be sure that you haven't moved it m" +
"eanwhile. "
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelProjectText2.setBounds(5, 165, 250, 80);
        panelProjectRight.add(wrappingLabelProjectText2);
        panelData = new Panel();
        panelData.setLayout(null);
        panelData.setVisible(false);
        panelData.setBounds(12, 33, 546, 276);
        panelData.setBackground(new Color(0xffaae8f4));
        tabPanelProjectSpecification.add(panelData);
        panelDataLeft = new Panel();
        panelDataLeft.setLayout(null);
        panelDataLeft.setBounds(5, 5, 263, 266);
        panelData.add(panelDataLeft);
        wrappingLabelDataFileName = new WrappingLabel();
        try
        {
            wrappingLabelDataFileName.setText("Data File Name");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelDataFileName.setBounds(3, 5, 250, 23);
        wrappingLabelDataFileName.setFont(new Font("Dialog", 1, 12));
        panelDataLeft.add(wrappingLabelDataFileName);
        textFieldDataFileName = new TextField();
        textFieldDataFileName.setEditable(false);
        textFieldDataFileName.setBounds(3, 30, 200, 25);
        textFieldDataFileName.setBackground(new Color(0xfff0ffff));
        panelDataLeft.add(textFieldDataFileName);
        labelButtonDataFileOpen = new Button();
        labelButtonDataFileOpen.setLabel("Open");
        labelButtonDataFileOpen.setBackground(new Color(0xffe2e2e2));
        labelButtonDataFileOpen.setBounds(203, 30, 45, 25);
        labelButtonDataFileOpen.setFont(new Font("SansSerif", 0, 13));
        panelDataLeft.add(labelButtonDataFileOpen);
        wrappingLabelDataFileDescription = new WrappingLabel();
        try
        {
            wrappingLabelDataFileDescription.setText("Data File Description");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelDataFileDescription.setBounds(3, 75, 250, 25);
        wrappingLabelDataFileDescription.setFont(new Font("Dialog", 1, 12));
        panelDataLeft.add(wrappingLabelDataFileDescription);
        textAreaDataDescription = new TextArea();
        textAreaDataDescription.setEditable(false);
        textAreaDataDescription.setBounds(3, 102, 250, 150);
        textAreaDataDescription.setBackground(new Color(0xfff0ffff));
        panelDataLeft.add(textAreaDataDescription);
        verticalLineData = new VerticalLine();
        try
        {
            verticalLineData.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        verticalLineData.setBounds(273, 10, 2, 256);
        verticalLineData.setForeground(new Color(0));
        verticalLineData.setBackground(new Color(0xffaae8f4));
        panelData.add(verticalLineData);
        panelDataRight = new Panel();
        panelDataRight.setLayout(null);
        panelDataRight.setBounds(279, 5, 263, 266);
        panelData.add(panelDataRight);
        wrappingLabelDF = new WrappingLabel();
        try
        {
            wrappingLabelDF.setText("The Data File");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelDF.setBounds(3, 5, 250, 23);
        wrappingLabelDF.setFont(new Font("Dialog", 1, 12));
        panelDataRight.add(wrappingLabelDF);
        wrappingLabelDFText = new WrappingLabel();
        try
        {
            wrappingLabelDFText.setText("Here the data file for training is loaded. It is not forbidden to use it for cla" +
"ssification but it is easier to use the main menue to classify data."
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelDFText.setBounds(3, 25, 250, 60);
        panelDataRight.add(wrappingLabelDFText);
        wrappingLabelDataDF = new WrappingLabel();
        try
        {
            wrappingLabelDataDF.setText("View / Edit Labels");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelDataDF.setBounds(2, 100, 250, 23);
        wrappingLabelDataDF.setFont(new Font("Dialog", 1, 12));
        panelDataRight.add(wrappingLabelDataDF);
        wrappingLabelDataELText = new WrappingLabel();
        try
        {
            wrappingLabelDataELText.setText("Here an editor can be invoked for viewing / editing variable labels and the desc" +
"ription of the data file."
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelDataELText.setBounds(3, 122, 167, 66);
        wrappingLabelDataELText.setFont(new Font("Dialog", 0, 12));
        panelDataRight.add(wrappingLabelDataELText);
        labelButtonEditLabels = new Button();
        labelButtonEditLabels.setLabel("Edit Labels");
        labelButtonEditLabels.setBackground(new Color(0xffe2e2e2));
        labelButtonEditLabels.setBounds(175, 155, 85, 25);
        panelDataRight.add(labelButtonEditLabels);
        labelButtonEditLabels.setEnabled(false);
        labelButtonStatistics = new Button();
        labelButtonStatistics.setLabel("Statistics");
        labelButtonStatistics.setBackground(new Color(0xffe2e2e2));
        labelButtonStatistics.setBounds(175, 226, 85, 25);
        panelDataRight.add(labelButtonStatistics);
        labelButtonStatistics.setEnabled(false);
        wrappingLabelData4 = new WrappingLabel();
        try
        {
            wrappingLabelData4.setText("Some descriptive statistics can be viewed.");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelData4.setBounds(1, 222, 167, 32);
        wrappingLabelData4.setFont(new Font("Dialog", 0, 12));
        panelDataRight.add(wrappingLabelData4);
        wrappingLabelDataS = new WrappingLabel();
        try
        {
            wrappingLabelDataS.setText("Statistics");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelDataS.setBounds(3, 200, 250, 23);
        wrappingLabelDataS.setFont(new Font("Dialog", 1, 12));
        panelDataRight.add(wrappingLabelDataS);
        panelClassifier = new Panel();
        panelClassifier.setLayout(null);
        panelClassifier.setVisible(false);
        panelClassifier.setBounds(12, 33, 546, 276);
        panelClassifier.setBackground(new Color(-4449));
        tabPanelProjectSpecification.add(panelClassifier);
        panelClassifierLeft = new Panel();
        panelClassifierLeft.setLayout(null);
        panelClassifierLeft.setBounds(5, 5, 263, 266);
        panelClassifier.add(panelClassifierLeft);
        wrappingLabelClassifierFuzzySets = new WrappingLabel();
        try
        {
            wrappingLabelClassifierFuzzySets.setText("Fuzzy Sets");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelClassifierFuzzySets.setBounds(3, 5, 250, 23);
        wrappingLabelClassifierFuzzySets.setFont(new Font("Dialog", 1, 12));
        panelClassifierLeft.add(wrappingLabelClassifierFuzzySets);
        radioButtonGroupPanelFuzzySet = new RadioButtonGroupPanel();
        try
        {
            radioButtonGroupPanelFuzzySet.setSelectedRadioButtonIndex(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelFuzzySet.setIPadBottom(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelFuzzySet.setBorderColor(new Color(-4449));
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelFuzzySet.setIPadSides(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelFuzzySet.setIPadTop(0);
        }
        catch(PropertyVetoException _ex) { }
        radioButtonGroupPanelFuzzySet.setLayout(null);
        radioButtonGroupPanelFuzzySet.setBounds(3, 28, 255, 105);
        panelClassifierLeft.add(radioButtonGroupPanelFuzzySet);
        Group1 = new CheckboxGroup();
        radioButtonClassificationFSSame = new Checkbox("same for all variables", Group1, true);
        radioButtonClassificationFSSame.setBounds(0, 0, 240, 23);
        radioButtonGroupPanelFuzzySet.add(radioButtonClassificationFSSame);
        labelClassifierFSNumber = new Label("Number:");
        labelClassifierFSNumber.setBounds(35, 29, 60, 20);
        labelClassifierFSNumber.setFont(new Font("Dialog", 0, 12));
        radioButtonGroupPanelFuzzySet.add(labelClassifierFSNumber);
        textFieldClassifierFSNumber = new TextField();
        textFieldClassifierFSNumber.setText("5");
        textFieldClassifierFSNumber.setBounds(110, 26, 40, 23);
        radioButtonGroupPanelFuzzySet.add(textFieldClassifierFSNumber);
        labelClassifierFSForm = new Label("Form:");
        labelClassifierFSForm.setBounds(36, 54, 60, 23);
        labelClassifierFSForm.setFont(new Font("Dialog", 0, 12));
        radioButtonGroupPanelFuzzySet.add(labelClassifierFSForm);
        choiceClassifierFSForm = new Choice();
        choiceClassifierFSForm.addItem("triangular");
        choiceClassifierFSForm.addItem("trapezoidal");
        choiceClassifierFSForm.addItem("bell-shaped");
        choiceClassifierFSForm.addItem("list");
        try
        {
            choiceClassifierFSForm.select(0);
        }
        catch(IllegalArgumentException _ex) { }
        radioButtonGroupPanelFuzzySet.add(choiceClassifierFSForm);
        choiceClassifierFSForm.setBounds(110, 51, 140, 23);
        choiceClassifierFSForm.setBackground(new Color(-1046));
        radioButtonClassificationFSIndividual = new Checkbox("individually for each variable", Group1, false);
        radioButtonClassificationFSIndividual.setBounds(3, 81, 190, 23);
        radioButtonGroupPanelFuzzySet.add(radioButtonClassificationFSIndividual);
        labelButtonClassifierFSSpecify = new Button();
        labelButtonClassifierFSSpecify.setLabel("Specify");
        labelButtonClassifierFSSpecify.setBackground(new Color(0xffe2e2e2));
        labelButtonClassifierFSSpecify.setBounds(199, 79, 50, 23);
        labelButtonClassifierFSSpecify.setFont(new Font("SansSerif", 0, 12));
        radioButtonGroupPanelFuzzySet.add(labelButtonClassifierFSSpecify);
        labelButtonClassifierFSSpecify.setEnabled(false);
        horizontalLineClassifierL1 = new HorizontalLine();
        try
        {
            horizontalLineClassifierL1.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLineClassifierL1.setBounds(10, 140, 243, 2);
        panelClassifierLeft.add(horizontalLineClassifierL1);
        wrappingLabelClassifierAggregation = new WrappingLabel();
        try
        {
            wrappingLabelClassifierAggregation.setText("Aggregation Function");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelClassifierAggregation.setBounds(3, 150, 250, 23);
        wrappingLabelClassifierAggregation.setFont(new Font("Dialog", 1, 12));
        panelClassifierLeft.add(wrappingLabelClassifierAggregation);
        radioButtonGroupPanelAggregation = new RadioButtonGroupPanel();
        try
        {
            radioButtonGroupPanelAggregation.setSelectedRadioButtonIndex(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelAggregation.setIPadBottom(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelAggregation.setBorderColor(new Color(-4449));
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelAggregation.setIPadSides(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelAggregation.setIPadTop(0);
        }
        catch(PropertyVetoException _ex) { }
        radioButtonGroupPanelAggregation.setLayout(null);
        radioButtonGroupPanelAggregation.setBounds(3, 170, 255, 23);
        panelClassifierLeft.add(radioButtonGroupPanelAggregation);
        Group2 = new CheckboxGroup();
        radioButtonClassifierAFMax = new Checkbox("maximum", Group2, true);
        radioButtonClassifierAFMax.setBounds(3, 0, 90, 23);
        radioButtonGroupPanelAggregation.add(radioButtonClassifierAFMax);
        radioButtonClassifierAFWS = new Checkbox("weighted sum", Group2, false);
        radioButtonClassifierAFWS.setBounds(100, 0, 100, 23);
        radioButtonGroupPanelAggregation.add(radioButtonClassifierAFWS);
        horizontalLineClassifierL2 = new HorizontalLine();
        try
        {
            horizontalLineClassifierL2.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLineClassifierL2.setBounds(10, 200, 243, 2);
        panelClassifierLeft.add(horizontalLineClassifierL2);
        wrappingLabelClassifierICR = new WrappingLabel();
        try
        {
            wrappingLabelClassifierICR.setText("Interpretation of Classification Result");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelClassifierICR.setBounds(3, 212, 250, 23);
        wrappingLabelClassifierICR.setFont(new Font("Dialog", 1, 12));
        panelClassifierLeft.add(wrappingLabelClassifierICR);
        choiceClassifierICR = new Choice();
        choiceClassifierICR.addItem("winner takes all (wta)");
        try
        {
            choiceClassifierICR.select(0);
        }
        catch(IllegalArgumentException _ex) { }
        panelClassifierLeft.add(choiceClassifierICR);
        choiceClassifierICR.setBounds(3, 231, 250, 40);
        choiceClassifierICR.setBackground(new Color(-1046));
        verticalLineClassifier = new VerticalLine();
        try
        {
            verticalLineClassifier.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        verticalLineClassifier.setBounds(273, 10, 2, 256);
        panelClassifier.add(verticalLineClassifier);
        panelClassifierRight = new Panel();
        panelClassifierRight.setLayout(null);
        panelClassifierRight.setBounds(279, 5, 263, 266);
        panelClassifier.add(panelClassifierRight);
        wrappingLabelClassifierFSText = new WrappingLabel();
        try
        {
            wrappingLabelClassifierFSText.setText("The numbers and types of fuzzy sets can be set the same for each variable or can" +
" be specified individually. If you select \"individually for each variable\" pre" +
"ss the specify button to invoke the editor."
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelClassifierFSText.setBounds(0, 30, 250, 100);
        panelClassifierRight.add(wrappingLabelClassifierFSText);
        labelButtonClassifierChange = new Button();
        labelButtonClassifierChange.setLabel("Invoke Rule Editor");
        labelButtonClassifierChange.setBackground(new Color(0xffe2e2e2));
        labelButtonClassifierChange.setBounds(48, 239, 150, 23);
        panelClassifierRight.add(labelButtonClassifierChange);
        labelButtonClassifierChange.setEnabled(false);
        horizontalLineClassifierR1 = new HorizontalLine();
        try
        {
            horizontalLineClassifierR1.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLineClassifierR1.setBounds(10, 140, 243, 2);
        panelClassifierRight.add(horizontalLineClassifierR1);
        wrappingLabelClassifierCR = new WrappingLabel();
        try
        {
            wrappingLabelClassifierCR.setText("Insert Prior Knowledge / Edit an  Existing Rule Base");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelClassifierCR.setBounds(3, 148, 157, 33);
        wrappingLabelClassifierCR.setFont(new Font("SansSerif", 1, 12));
        panelClassifierRight.add(wrappingLabelClassifierCR);
        wrappingLabelClassifierCRText = new WrappingLabel();
        try
        {
            wrappingLabelClassifierCRText.setText("Prior knowledge in form of rules can be specified with the rule editor. If there" +
" is an inserted or learnd rule base it can be edited."
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelClassifierCRText.setBounds(0, 186, 250, 47);
        panelClassifierRight.add(wrappingLabelClassifierCRText);
        panelRuleCreation = new Panel();
        panelRuleCreation.setLayout(null);
        panelRuleCreation.setVisible(false);
        panelRuleCreation.setBounds(12, 33, 546, 276);
        panelRuleCreation.setBackground(new Color(0xfffccb9e));
        tabPanelProjectSpecification.add(panelRuleCreation);
        panelRuleCreationLeft = new Panel();
        panelRuleCreationLeft.setLayout(null);
        panelRuleCreationLeft.setBounds(5, 5, 263, 266);
        panelRuleCreation.add(panelRuleCreationLeft);
        wrappingLabelSizeRB = new WrappingLabel();
        try
        {
            wrappingLabelSizeRB.setText("Size of the Rule Base");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelSizeRB.setBounds(3, 5, 250, 23);
        wrappingLabelSizeRB.setFont(new Font("Dialog", 1, 12));
        panelRuleCreationLeft.add(wrappingLabelSizeRB);
        radioButtonGroupPanelSizeRB = new RadioButtonGroupPanel();
        try
        {
            radioButtonGroupPanelSizeRB.setSelectedRadioButtonIndex(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelSizeRB.setBorderColor(new Color(0xfffccb9e));
        }
        catch(PropertyVetoException _ex) { }
        radioButtonGroupPanelSizeRB.setLayout(null);
        radioButtonGroupPanelSizeRB.setBounds(3, 30, 255, 55);
        panelRuleCreationLeft.add(radioButtonGroupPanelSizeRB);
        radioButtonMaxNumberFR = new Checkbox("max. number of fuzzy rules", Group1, true);
        radioButtonMaxNumberFR.setBounds(0, 0, 175, 23);
        radioButtonGroupPanelSizeRB.add(radioButtonMaxNumberFR);
        radioButtonAutomaticFR = new Checkbox("automatically determine number of rules", Group1, false);
        radioButtonAutomaticFR.setBounds(0, 27, 246, 23);
        radioButtonGroupPanelSizeRB.add(radioButtonAutomaticFR);
        textFieldSizeRB = new TextField();
        textFieldSizeRB.setBounds(180, 0, 60, 23);
        radioButtonGroupPanelSizeRB.add(textFieldSizeRB);
        horizontalLineRuleCreationL1 = new HorizontalLine();
        try
        {
            horizontalLineRuleCreationL1.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLineRuleCreationL1.setBounds(10, 95, 243, 2);
        panelRuleCreationLeft.add(horizontalLineRuleCreationL1);
        wrappingLabelRLProcedure = new WrappingLabel();
        try
        {
            wrappingLabelRLProcedure.setText("Rule Learning Procedure");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelRLProcedure.setBounds(3, 105, 250, 23);
        wrappingLabelRLProcedure.setFont(new Font("Dialog", 1, 12));
        panelRuleCreationLeft.add(wrappingLabelRLProcedure);
        radioButtonGroupPanelRuleLearning = new RadioButtonGroupPanel();
        try
        {
            radioButtonGroupPanelRuleLearning.setSelectedRadioButtonIndex(1);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelRuleLearning.setIPadBottom(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelRuleLearning.setBorderColor(new Color(0xfffccb9e));
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelRuleLearning.setIPadSides(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelRuleLearning.setIPadTop(0);
        }
        catch(PropertyVetoException _ex) { }
        radioButtonGroupPanelRuleLearning.setLayout(null);
        radioButtonGroupPanelRuleLearning.setBounds(3, 128, 250, 55);
        panelRuleCreationLeft.add(radioButtonGroupPanelRuleLearning);
        radioButtonRuleLearningBest = new Checkbox("best", Group1, false);
        radioButtonRuleLearningBest.setBounds(0, 0, 200, 23);
        radioButtonGroupPanelRuleLearning.add(radioButtonRuleLearningBest);
        radioButtonRuleLearningBestPClass = new Checkbox("best per class", Group1, true);
        radioButtonRuleLearningBestPClass.setBounds(0, 27, 200, 23);
        radioButtonGroupPanelRuleLearning.add(radioButtonRuleLearningBestPClass);
        horizontalLineRuleCreationL2 = new HorizontalLine();
        try
        {
            horizontalLineRuleCreationL2.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLineRuleCreationL2.setBounds(10, 193, 243, 2);
        panelRuleCreationLeft.add(horizontalLineRuleCreationL2);
        checkboxReLearn = new Checkbox("re-learn the rule base");
        checkboxReLearn.setBounds(4, 226, 250, 20);
        panelRuleCreationLeft.add(checkboxReLearn);
        wrappingLabelRBReLearn = new WrappingLabel();
        try
        {
            wrappingLabelRBReLearn.setText("Rule Base");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelRBReLearn.setBounds(3, 203, 250, 23);
        wrappingLabelRBReLearn.setFont(new Font("Dialog", 1, 12));
        panelRuleCreationLeft.add(wrappingLabelRBReLearn);
        verticalLineRuleCreation = new VerticalLine();
        try
        {
            verticalLineRuleCreation.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        verticalLineRuleCreation.setBounds(273, 10, 2, 256);
        panelRuleCreation.add(verticalLineRuleCreation);
        panelRuleCreationRight = new Panel();
        panelRuleCreationRight.setLayout(null);
        panelRuleCreationRight.setBounds(279, 5, 263, 266);
        panelRuleCreation.add(panelRuleCreationRight);
        wrappingLabelRB = new WrappingLabel();
        try
        {
            wrappingLabelRB.setText("The Rule Base");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelRB.setBounds(3, 5, 250, 23);
        wrappingLabelRB.setFont(new Font("Dialog", 1, 12));
        panelRuleCreationRight.add(wrappingLabelRB);
        wrappingLabelRBText1 = new WrappingLabel();
        try
        {
            wrappingLabelRBText1.setText("To specify the size of the rule base is an optimization problem. You get the bes" +
"t classification results when all patterns of the data set are covered by the ru" +
"le base. The option \"automatically determine ...\" will do this. This might res" +
"ult in a lot of rules. On the other hand the interpretabillity is higher  with a" +
" small number of rules. So  you can specify a max. number. The option \"best\" s" +
"elects the best rules from all.  Classes represented by weak rules might not app" +
"ear in the rule base. In this case it is better to use \"best per class\". "
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelRBText1.setBounds(3, 25, 255, 183);
        panelRuleCreationRight.add(wrappingLabelRBText1);
        wrappingLabelRBText2 = new WrappingLabel();
        try
        {
            wrappingLabelRBText2.setText("The option re-learn the rulebase can be used if a rulebase exists that should be" +
" used as prior knowledge for a new learning process."
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelRBText2.setBounds(3, 211, 250, 47);
        panelRuleCreationRight.add(wrappingLabelRBText2);
        panelTrainingFSets = new Panel();
        panelTrainingFSets.setLayout(null);
        panelTrainingFSets.setBounds(12, 33, 546, 276);
        panelTrainingFSets.setBackground(new Color(0xffaaffd5));
        tabPanelProjectSpecification.add(panelTrainingFSets);
        panelTrainingFSetsLeft = new Panel();
        panelTrainingFSetsLeft.setLayout(null);
        panelTrainingFSetsLeft.setBounds(5, 5, 263, 266);
        panelTrainingFSets.add(panelTrainingFSetsLeft);
        wrappingLabelConstraints = new WrappingLabel();
        try
        {
            wrappingLabelConstraints.setText("Constraints");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelConstraints.setBounds(3, 5, 250, 23);
        wrappingLabelConstraints.setFont(new Font("Dialog", 1, 12));
        panelTrainingFSetsLeft.add(wrappingLabelConstraints);
        panelFFsetConstraints = new Panel();
        panelFFsetConstraints.setLayout(null);
        panelFFsetConstraints.setBounds(3, 27, 255, 92);
        panelTrainingFSetsLeft.add(panelFFsetConstraints);
        labelConstraintsFS = new Label("Fuzzy sets must:");
        labelConstraintsFS.setBounds(0, 0, 95, 23);
        panelFFsetConstraints.add(labelConstraintsFS);
        checkboxRelativeOrder = new Checkbox("keep their relative order");
        checkboxRelativeOrder.setBounds(100, 0, 150, 23);
        panelFFsetConstraints.add(checkboxRelativeOrder);
        checkboxRelativeOrder.setState(true);
        checkboxOverlap = new Checkbox("always overlap");
        checkboxOverlap.setBounds(100, 23, 150, 23);
        panelFFsetConstraints.add(checkboxOverlap);
        checkboxOverlap.setState(true);
        checkboxSymmetrical = new Checkbox("be symmetrical");
        checkboxSymmetrical.setBounds(100, 46, 150, 23);
        panelFFsetConstraints.add(checkboxSymmetrical);
        checkboxIntersect = new Checkbox("intersect at 0.5");
        checkboxIntersect.setBounds(100, 69, 150, 23);
        panelFFsetConstraints.add(checkboxIntersect);
        radioButtonGroupPanelConstraints = new RadioButtonGroupPanel();
        try
        {
            radioButtonGroupPanelConstraints.setSelectedRadioButtonIndex(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelConstraints.setIPadBottom(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelConstraints.setBorderColor(new Color(0xffaaffd5));
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelConstraints.setIPadSides(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelConstraints.setIPadTop(0);
        }
        catch(PropertyVetoException _ex) { }
        radioButtonGroupPanelConstraints.setLayout(null);
        radioButtonGroupPanelConstraints.setBounds(3, 126, 255, 70);
        radioButtonGroupPanelConstraints.setBackground(new Color(0xffaaffd5));
        panelTrainingFSetsLeft.add(radioButtonGroupPanelConstraints);
        radioButtonWeightNotUsed = new Checkbox("are not used");
        radioButtonWeightNotUsed.setBounds(100, 0, 120, 23);
        radioButtonGroupPanelConstraints.add(radioButtonWeightNotUsed);
        radioButtonWeightNotUsed.setState(true);
        radioButtonInterval = new Checkbox("stay within [0, 1]");
        radioButtonInterval.setBounds(100, 23, 120, 23);
        radioButtonGroupPanelConstraints.add(radioButtonInterval);
        radioButtonArbitrary = new Checkbox("may be arbitrary");
        radioButtonArbitrary.setBounds(100, 46, 120, 23);
        radioButtonGroupPanelConstraints.add(radioButtonArbitrary);
        labelConstraintsWeights = new Label("Rule weights");
        labelConstraintsWeights.setBounds(0, 0, 95, 20);
        radioButtonGroupPanelConstraints.add(labelConstraintsWeights);
        horizontalLineTrainingFSetsL1 = new HorizontalLine();
        try
        {
            horizontalLineTrainingFSetsL1.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLineTrainingFSetsL1.setBounds(10, 204, 243, 2);
        panelTrainingFSetsLeft.add(horizontalLineTrainingFSetsL1);
        panelLearningRate = new Panel();
        panelLearningRate.setLayout(null);
        panelLearningRate.setBounds(3, 225, 250, 30);
        panelTrainingFSetsLeft.add(panelLearningRate);
        wrappingLabelLearningRate = new WrappingLabel();
        try
        {
            wrappingLabelLearningRate.setText("Learning Rate");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelLearningRate.setBounds(0, 0, 90, 23);
        wrappingLabelLearningRate.setFont(new Font("Dialog", 1, 12));
        panelLearningRate.add(wrappingLabelLearningRate);
        textFieldLearningRate = new TextField();
        textFieldLearningRate.setText("0.01");
        textFieldLearningRate.setBounds(100, 0, 60, 23);
        panelLearningRate.add(textFieldLearningRate);
        verticalLineTrainingFSets = new VerticalLine();
        try
        {
            verticalLineTrainingFSets.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        verticalLineTrainingFSets.setBounds(273, 10, 2, 256);
        panelTrainingFSets.add(verticalLineTrainingFSets);
        panelTrainingFSetsRight = new Panel();
        panelTrainingFSetsRight.setLayout(null);
        panelTrainingFSetsRight.setBounds(279, 5, 263, 266);
        panelTrainingFSets.add(panelTrainingFSetsRight);
        wrappingLabelC = new WrappingLabel();
        try
        {
            wrappingLabelC.setText("Constraints");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelC.setBounds(5, 3, 250, 23);
        wrappingLabelC.setFont(new Font("Dialog", 1, 12));
        panelTrainingFSetsRight.add(wrappingLabelC);
        wrappingLabelCText = new WrappingLabel();
        try
        {
            wrappingLabelCText.setText("To ensure the interpretability of the classifier the learning algorithm for the " +
"fuzzy sets can be constrained. The more constraints you select the harder  it be" +
"comes to optimize the classifier. For more detailed information press the \"Help" +
"\" button."
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelCText.setBounds(5, 27, 255, 91);
        panelTrainingFSetsRight.add(wrappingLabelCText);
        wrappingLabelCText2 = new WrappingLabel();
        try
        {
            wrappingLabelCText2.setText("If the interpretation of the classifier is not important, you can try to use wei" +
"ghts for the fuzzy rules.Rule weights are not used by default, because they have" +
" no clear semantics."
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelCText2.setBounds(5, 125, 257, 72);
        panelTrainingFSetsRight.add(wrappingLabelCText2);
        horizontalLineTrainingFSetsR1 = new HorizontalLine();
        try
        {
            horizontalLineTrainingFSetsR1.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLineTrainingFSetsR1.setBounds(10, 204, 243, 2);
        panelTrainingFSetsRight.add(horizontalLineTrainingFSetsR1);
        wrappingLabelLR = new WrappingLabel();
        try
        {
            wrappingLabelLR.setText("Learning Rate");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelLR.setBounds(5, 212, 90, 23);
        wrappingLabelLR.setFont(new Font("Dialog", 1, 12));
        panelTrainingFSetsRight.add(wrappingLabelLR);
        wrappingLabelLRText = new WrappingLabel();
        try
        {
            wrappingLabelLRText.setText("Step size for the fuzzy set learning process.");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelLRText.setBounds(4, 236, 250, 23);
        panelTrainingFSetsRight.add(wrappingLabelLRText);
        panelTrainingControl = new Panel();
        panelTrainingControl.setLayout(null);
        panelTrainingControl.setVisible(false);
        panelTrainingControl.setBounds(12, 33, 546, 276);
        panelTrainingControl.setBackground(new Color(-13108));
        tabPanelProjectSpecification.add(panelTrainingControl);
        panelTrainingControlLeft = new Panel();
        panelTrainingControlLeft.setLayout(null);
        panelTrainingControlLeft.setBounds(5, 5, 263, 266);
        panelTrainingControl.add(panelTrainingControlLeft);
        wrappingLabelValidation = new WrappingLabel();
        try
        {
            wrappingLabelValidation.setText("Validation");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelValidation.setBounds(3, 5, 250, 23);
        wrappingLabelValidation.setFont(new Font("Dialog", 1, 12));
        panelTrainingControlLeft.add(wrappingLabelValidation);
        radioButtonGroupPanelValidation = new RadioButtonGroupPanel();
        try
        {
            radioButtonGroupPanelValidation.setSelectedRadioButtonIndex(2);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelValidation.setIPadBottom(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelValidation.setBorderColor(new Color(-13108));
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelValidation.setIPadSides(0);
        }
        catch(PropertyVetoException _ex) { }
        try
        {
            radioButtonGroupPanelValidation.setIPadTop(0);
        }
        catch(PropertyVetoException _ex) { }
        radioButtonGroupPanelValidation.setLayout(null);
        radioButtonGroupPanelValidation.setBounds(5, 30, 250, 80);
        radioButtonGroupPanelValidation.setBackground(new Color(-13108));
        panelTrainingControlLeft.add(radioButtonGroupPanelValidation);
        radioButtonTrainingNoVal = new Checkbox("no validation", Group1, false);
        radioButtonTrainingNoVal.setBounds(0, 0, 100, 23);
        radioButtonGroupPanelValidation.add(radioButtonTrainingNoVal);
        radioButtonTrainingCrossVal = new Checkbox("cross validation", Group1, false);
        radioButtonTrainingCrossVal.setBounds(0, 27, 115, 23);
        radioButtonGroupPanelValidation.add(radioButtonTrainingCrossVal);
        radioButtonSingleTest = new Checkbox("single test", Group1, true);
        radioButtonSingleTest.setBounds(0, 54, 115, 23);
        radioButtonGroupPanelValidation.add(radioButtonSingleTest);
        textFieldCrossValNumber = new TextField();
        textFieldCrossValNumber.setText("10");
        textFieldCrossValNumber.setBounds(136, 27, 50, 23);
        radioButtonGroupPanelValidation.add(textFieldCrossValNumber);
        textFieldCrossValNumber.setEnabled(false);
        textFieldSingleTest = new TextField();
        textFieldSingleTest.setText("50");
        textFieldSingleTest.setBounds(136, 54, 50, 23);
        radioButtonGroupPanelValidation.add(textFieldSingleTest);
        labelCrossValNumber = new Label("times");
        labelCrossValNumber.setBounds(190, 27, 32, 23);
        radioButtonGroupPanelValidation.add(labelCrossValNumber);
        labelSingleTest = new Label("%");
        labelSingleTest.setBounds(190, 54, 29, 23);
        radioButtonGroupPanelValidation.add(labelSingleTest);
        horizontalLineTrainingControlL1 = new HorizontalLine();
        try
        {
            horizontalLineTrainingControlL1.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLineTrainingControlL1.setBounds(10, 120, 243, 2);
        horizontalLineTrainingControlL1.setBackground(new Color(-13108));
        panelTrainingControlLeft.add(horizontalLineTrainingControlL1);
        wrappingLabelStopControl = new WrappingLabel();
        try
        {
            wrappingLabelStopControl.setText("Stop Control");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelStopControl.setBounds(3, 130, 250, 23);
        wrappingLabelStopControl.setFont(new Font("Dialog", 1, 12));
        panelTrainingControlLeft.add(wrappingLabelStopControl);
        panelStopControl = new Panel();
        panelStopControl.setLayout(null);
        panelStopControl.setBounds(3, 155, 255, 110);
        panelTrainingControlLeft.add(panelStopControl);
        labelNoEpochsMax = new Label("Maximum No. of Epochs");
        labelNoEpochsMax.setBounds(0, 0, 190, 23);
        panelStopControl.add(labelNoEpochsMax);
        textFieldNoEpochsMax = new TextField();
        textFieldNoEpochsMax.setText("100");
        textFieldNoEpochsMax.setBounds(195, 0, 50, 25);
        panelStopControl.add(textFieldNoEpochsMax);
        labelNoEpochsMin = new Label("Minimum No. of Epochs");
        labelNoEpochsMin.setBounds(0, 27, 190, 23);
        panelStopControl.add(labelNoEpochsMin);
        textFieldNoEpochsMin = new TextField();
        textFieldNoEpochsMin.setText("0");
        textFieldNoEpochsMin.setBounds(195, 27, 50, 23);
        panelStopControl.add(textFieldNoEpochsMin);
        labelAfterOpt = new Label("No. of Epochs after Optimum");
        labelAfterOpt.setBounds(0, 54, 190, 23);
        panelStopControl.add(labelAfterOpt);
        textFieldAfterOpt = new TextField();
        textFieldAfterOpt.setText("10");
        textFieldAfterOpt.setBounds(195, 54, 50, 23);
        panelStopControl.add(textFieldAfterOpt);
        labelAdmClassErrors = new Label("Admissible Classification Errors");
        labelAdmClassErrors.setBounds(0, 81, 190, 23);
        panelStopControl.add(labelAdmClassErrors);
        textFieldAdmClassErrors = new TextField();
        textFieldAdmClassErrors.setText("0");
        textFieldAdmClassErrors.setBounds(195, 81, 50, 23);
        panelStopControl.add(textFieldAdmClassErrors);
        verticalLineTrainingControl = new VerticalLine();
        try
        {
            verticalLineTrainingControl.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        verticalLineTrainingControl.setBounds(273, 10, 2, 256);
        panelTrainingControl.add(verticalLineTrainingControl);
        panelTrainingControlRight = new Panel();
        panelTrainingControlRight.setLayout(null);
        panelTrainingControlRight.setBounds(279, 5, 263, 266);
        panelTrainingControl.add(panelTrainingControlRight);
        wrappingLabelV = new WrappingLabel();
        try
        {
            wrappingLabelV.setText("Validation");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelV.setBounds(3, 5, 250, 23);
        wrappingLabelV.setFont(new Font("Dialog", 1, 12));
        panelTrainingControlRight.add(wrappingLabelV);
        wrappingLabelCVText = new WrappingLabel();
        try
        {
            wrappingLabelCVText.setText("Cross Validation: The maximum number is the number of patterns. ");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelCVText.setBounds(3, 27, 250, 35);
        wrappingLabelCVText.setFont(new Font("Dialog", 0, 12));
        panelTrainingControlRight.add(wrappingLabelCVText);
        wrappingLabelSTText = new WrappingLabel();
        try
        {
            wrappingLabelSTText.setText(" Single Test: The percentage of patterns withhold from data file for testing.");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelSTText.setBounds(3, 66, 250, 35);
        wrappingLabelSTText.setFont(new Font("Dialog", 0, 12));
        panelTrainingControlRight.add(wrappingLabelSTText);
        wrappingLabelST = new WrappingLabel();
        try
        {
            wrappingLabelST.setText("Stop Control");
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelST.setBounds(3, 130, 250, 23);
        wrappingLabelST.setFont(new Font("Dialog", 1, 12));
        panelTrainingControlRight.add(wrappingLabelST);
        wrappingLabelSCText = new WrappingLabel();
        try
        {
            wrappingLabelSCText.setText("These options guarantee that the algorithm will stop after a reasonible time and" +
"/or a good classification result. For more detailled information press the \"Hel" +
"p\" button."
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelSCText.setBounds(1, 157, 250, 65);
        wrappingLabelSCText.setFont(new Font("Dialog", 0, 12));
        panelTrainingControlRight.add(wrappingLabelSCText);
        labelButtonProjectSpecificationOK = new Button();
        labelButtonProjectSpecificationOK.setLabel("OK");
        labelButtonProjectSpecificationOK.setBounds(304, 328, 70, 24);
        labelButtonProjectSpecificationOK.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonProjectSpecificationOK);
        labelButtonProjectSpecificationCancel = new Button();
        labelButtonProjectSpecificationCancel.setLabel("Cancel");
        labelButtonProjectSpecificationCancel.setBounds(382, 328, 70, 24);
        labelButtonProjectSpecificationCancel.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonProjectSpecificationCancel);
        labelButtonProjectSpecificationHelp = new Button();
        labelButtonProjectSpecificationHelp.setLabel("Help");
        labelButtonProjectSpecificationHelp.setBounds(495, 328, 70, 24);
        labelButtonProjectSpecificationHelp.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonProjectSpecificationHelp);
        setTitle("Project Specification");
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymItem symitem = new SymItem();
        radioButtonSingleTest.addItemListener(symitem);
        radioButtonTrainingCrossVal.addItemListener(symitem);
        radioButtonClassificationFSSame.addItemListener(symitem);
        SymAction symaction = new SymAction();
        labelButtonEditLabels.addActionListener(symaction);
        labelButtonStatistics.addActionListener(symaction);
        labelButtonDataFileOpen.addActionListener(symaction);
        labelButtonProjectSpecificationCancel.addActionListener(symaction);
        labelButtonProjectSpecificationOK.addActionListener(symaction);
        labelButtonClassifierFSSpecify.addActionListener(symaction);
        radioButtonClassificationFSIndividual.addItemListener(symitem);
        labelButtonClassifierChange.addActionListener(symaction);
        radioButtonTrainingNoVal.addItemListener(symitem);
        radioButtonAutomaticFR.addItemListener(symitem);
        radioButtonMaxNumberFR.addItemListener(symitem);
        labelButtonProjectSpecificationHelp.addActionListener(symaction);
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

    public DialogProjectSpecification(Frame frame, String s, boolean flag)
    {
        this(frame, flag);
        setTitle(s);
    }

    public synchronized void show()
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
        super.show();
    }

    void Dialog1_WindowClosing(WindowEvent windowevent)
    {
        dispose();
    }

    void labelButtonDataFileOpen_actionPerformed(ActionEvent actionevent)
    {
        boolean flag = false;
        FileDialog filedialog = new FileDialog((Frame)getParent(), "Load Data File", 0);
        filedialog.setFile("*.dat");
        filedialog.show();
        String s2 = filedialog.getDirectory();
        String s1 = filedialog.getFile();
        String s = s2 + s1;
        if(s1 == null || s2 == null)
        {
            s = "";
        }
        if(s != "")
        {
            if(!nefclassWasNull && nefclass != null && dataTable != null)
            {
                flag = nefclass.usesData(dataTable);
            }
            DataTable datatable = readDataFile(s);
            if(datatable != null)
            {
                dataTable = datatable;
                if(nefclass == null)
                {
                    try
                    {
                        nefclass = parameter.setupNefclass(dataTable, null);
                    }
                    catch(NefclassInvalidException nefclassinvalidexception)
                    {
                        nefclass = null;
                        message.showMessage("Attention!", "It is not possible to create the classifier.", "The creation of a new classifier object failed. There is probably something wron" +
"g with the training data: "
 + nefclassinvalidexception.getMessage());
                    }
                }
                textFieldDataFileName.setText(s);
                textAreaDataDescription.setText(dataTable.name);
                labelButtonEditLabels.setEnabled(true);
                labelButtonStatistics.setEnabled(true);
                labelButtonClassifierChange.setEnabled(true);
                return;
            }
            if(flag)
            {
                try
                {
                    nefclass.setData(dataTable);
                    textFieldDataFileName.setText(s);
                    textAreaDataDescription.setText(dataTable.name);
                    labelButtonEditLabels.setEnabled(true);
                    labelButtonStatistics.setEnabled(true);
                    labelButtonClassifierChange.setEnabled(true);
                    return;
                }
                catch(NefclassInvalidException _ex)
                {
                    message.showMessage("Internal Error!", "Please check the validity of all parameters.", "Sorry, this error shouldn't happen. The current NEFCLASS system is invalid, prob" +
"ably due to a bug in the software. Please try again, after checking all NEFCLASS" +
" parameters. Please consider to submit a bug report."
);
                }
                return;
            }
            dataTable = null;
            textFieldDataFileName.setText("");
            textAreaDataDescription.setText("");
            labelButtonEditLabels.setEnabled(false);
            labelButtonStatistics.setEnabled(false);
            labelButtonClassifierChange.setEnabled(false);
        }
    }

    protected DataTable readDataFile(String s)
    {
        DataTable datatable = null;
        DialogProgress dialogprogress = new DialogProgress();
        dialogprogress.setVisible(true);
        try
        {
            datatable = new DataTable(s, dialogprogress.textArea, dialogprogress.progressBar);
            if(nefclass != null)
            {
                nefclass.setData(datatable);
            }
        }
        catch(ParseDataFileException parsedatafileexception)
        {
            datatable = null;
            message.showMessage("Read Error!", "The format of data file is invalid.", "Please load another data file for this project. The format of the data file is n" +
"ot valid: "
 + parsedatafileexception.getMessage());
        }
        catch(IOException _ex)
        {
            datatable = null;
            message.showMessage("Read Error!", "The data file cannot be read.", "An I/O error occured while trying to read a data file for the project. Please ma" +
"ke sure that the data file exists and that the correct data filename is specifie" +
"d in the project dialog. "
);
        }
        catch(NefclassInvalidException nefclassinvalidexception)
        {
            if(nefclassWasNull)
            {
                nefclass = null;
            } else
            {
                datatable = null;
                message.showMessage("Read Error!", "The data file cannot be used by the current classifier.", "Please load another data file for this project. The format of the data file is n" +
"ot valid: "
 + nefclassinvalidexception.getMessage());
            }
        }
        return datatable;
    }

    void labelButtonEditLabels_actionPerformed(ActionEvent actionevent)
    {
        DialogEditLabels dialogeditlabels = new DialogEditLabels((Frame)getParent(), true);
        dialogeditlabels.setHelp(help, "el");
        dialogeditlabels.setDataTable(dataTable);
        dialogeditlabels.setVisible(true);
        textAreaDataDescription.setText(dataTable.name);
        if(dialogeditlabels.wasClosedWithOK() && nefclass != null)
        {
            nefclass.updateVarNames();
        }
    }

    void labelButtonStatistics_actionPerformed(ActionEvent actionevent)
    {
        if(dataTable.isComplete())
        {
            FrameStatistics framestatistics = new FrameStatistics("Loading Statistics of " + dataTable.filename + ", please wait...");
            framestatistics.setHelp(help, "st");
            framestatistics.show();
            framestatistics.textAreaStatistics.setVisible(false);
            dataTable.writeStatistics(framestatistics.textAreaStatistics);
            framestatistics.textAreaStatistics.setVisible(true);
            framestatistics.setTitle("Statistics of " + dataTable.filename);
            return;
        } else
        {
            message.showMessage("Attention!", "Please wait until data is loaded completely.", "A data file is currently being loaded. Please invoke this command again, after l" +
"oading is complete."
);
            return;
        }
    }

    void radioButtonClassificationFSSame_ItemStateChanged(ItemEvent itemevent)
    {
        textFieldClassifierFSNumber.setEnabled(true);
        choiceClassifierFSForm.setEnabled(true);
        labelButtonClassifierFSSpecify.setEnabled(false);
    }

    void radioButtonClassificationFSIndividual_ItemStateChanged(ItemEvent itemevent)
    {
        if(dataTable != null)
        {
            if(dataTable.isReady())
            {
                parameter.setFSParameters(dataTable.independent);
                textFieldClassifierFSNumber.setEnabled(false);
                choiceClassifierFSForm.setEnabled(false);
                labelButtonClassifierFSSpecify.setEnabled(true);
                return;
            } else
            {
                message.showMessage("Attention!", "Please wait until data is loaded completely.", "A data file is currently being loaded. Please invoke this command again, after l" +
"oading is complete."
);
                radioButtonClassificationFSSame.setState(true);
                return;
            }
        } else
        {
            radioButtonClassificationFSSame.setState(true);
            return;
        }
    }

    void labelButtonClassifierFSSpecify_actionPerformed(ActionEvent actionevent)
    {
        if(dataTable != null)
        {
            if(dataTable.isReady())
            {
                DialogSpecifyFP dialogspecifyfp = new DialogSpecifyFP((Frame)getParent(), true);
                dialogspecifyfp.setHelp(help, "fp");
                parameter.setFSParameters(dataTable.independent);
                dialogspecifyfp.createFPPanel(dataTable.getInputVarNames(), parameter.getFSNumbers(), parameter.getFSTypes());
                dialogspecifyfp.setVisible(true);
                if(dialogspecifyfp.closedWithOK())
                {
                    if(nefclass.getNumberOfRules() > 0)
                    {
                        if(!parameter.compareFSParameters(dialogspecifyfp.getFSNumbers(), dialogspecifyfp.getFSTypes()))
                        {
                            DialogYesNo dialogyesno = new DialogYesNo((Frame)getParent(), true);
                            dialogyesno.showMessage("Attention!", "The rule base will be deleted to use the new fuzzy sets.", "You have specified new parameters for the fuzzy partitions. There is a rule base" +
" that uses the old fuzzy partitions. To use these new parameters, the current ru" +
"le base must be deleted. Continue?"
);
                            if(dialogyesno.closedWithYes())
                            {
                                nefclass.deleteAllRules();
                                parameter.setFSParameters(dialogspecifyfp.getFSNumbers(), dialogspecifyfp.getFSTypes());
                                try
                                {
                                    nefclass.createPartitions(dialogspecifyfp.getFSNumbers(), dialogspecifyfp.getFSTypes());
                                    return;
                                }
                                catch(NefclassInvalidException _ex)
                                {
                                    message.showMessage("Internal Error!", "Please check the validity of all parameters.", "Sorry, this error shouldn't happen. The current NEFCLASS system is invalid, prob" +
"ably due to a bug in the software. Please try again, after checking all NEFCLASS" +
" parameters. Please consider to submit a bug report."
);
                                }
                                return;
                            }
                        }
                    } else
                    {
                        parameter.setFSParameters(dialogspecifyfp.getFSNumbers(), dialogspecifyfp.getFSTypes());
                        try
                        {
                            nefclass.createPartitions(dialogspecifyfp.getFSNumbers(), dialogspecifyfp.getFSTypes());
                            return;
                        }
                        catch(NefclassInvalidException _ex)
                        {
                            message.showMessage("Internal Error!", "Please check the validity of all parameters.", "Sorry, this error shouldn't happen. The current NEFCLASS system is invalid, prob" +
"ably due to a bug in the software. Please try again, after checking all NEFCLASS" +
" parameters. Please consider to submit a bug report."
);
                        }
                        return;
                    }
                }
            } else
            {
                message.showMessage("Attention!", "Please wait until data is loaded completely.", "A data file is currently being loaded. Please invoke this command again, after l" +
"oading is complete."
);
            }
            return;
        } else
        {
            message.showMessage("Attention!", "Please load training data.", "No training data was loaded until now. To use this command, please load a data f" +
"ile via the Project dialog or via the Project menu."
);
            return;
        }
    }

    void labelButtonClassifierChange_actionPerformed(ActionEvent actionevent)
    {
        if(nefclass != null)
        {
            DialogRuleEdit dialogruleedit = new DialogRuleEdit((Frame)getParent(), true);
            dialogruleedit.setHelp(help, "re");
            dialogruleedit.setNefclass(nefclass);
            dialogruleedit.setVisible(true);
        }
    }

    void radioButtonAutomaticFR_ItemStateChanged(ItemEvent itemevent)
    {
        textFieldSizeRB.setEnabled(false);
    }

    void radioButtonMaxNumberFR_ItemStateChanged(ItemEvent itemevent)
    {
        textFieldSizeRB.setEnabled(true);
    }

    void radioButtonSingleTest_ItemStateChanged(ItemEvent itemevent)
    {
        textFieldSingleTest.setEnabled(true);
        textFieldCrossValNumber.setEnabled(false);
    }

    void radioButtonTrainingCrossVal_ItemStateChanged(ItemEvent itemevent)
    {
        textFieldCrossValNumber.setEnabled(true);
        textFieldSingleTest.setEnabled(false);
    }

    void radioButtonTrainingNoVal_ItemStateChanged(ItemEvent itemevent)
    {
        textFieldCrossValNumber.setEnabled(false);
        textFieldSingleTest.setEnabled(false);
    }

    void labelButtonProjectSpecificationCancel_actionPerformed(ActionEvent actionevent)
    {
        updateParameterCancel();
        setVisible(false);
    }

    void labelButtonProjectSpecificationOK_actionPerformed(ActionEvent actionevent)
    {
        updateParameterOK();
        setVisible(false);
    }

    public void setParameter(ParameterList parameterlist)
    {
        parameter = parameterlist;
        textFieldProjectTitle.setText(parameterlist.getProjectTitle());
        textAreaProjectDescription.setText(parameterlist.getProjectDescription());
        textFieldDataFileName.setText(parameterlist.getDataFileName());
        if(dataTable != null)
        {
            textAreaDataDescription.setText(dataTable.name);
        } else
        if(parameterlist.getDataFileName() != "")
        {
            textAreaDataDescription.setText("ATTENTION: data not available.\nPlease load a new file.");
        } else
        {
            textAreaDataDescription.setText("");
        }
        labelButtonEditLabels.setEnabled(parameterlist.getStateEditLabels());
        labelButtonStatistics.setEnabled(parameterlist.getStateStatistics());
        labelButtonClassifierFSSpecify.setEnabled(parameterlist.getStateFSSpecify());
        if(parameterlist.getFSIndividual())
        {
            radioButtonClassificationFSIndividual.setState(true);
        } else
        {
            radioButtonClassificationFSSame.setState(true);
        }
        textFieldClassifierFSNumber.setText(Integer.toString(parameterlist.getFSNumber()));
        textFieldClassifierFSNumber.setEnabled(parameterlist.getStateFSNumber());
        choiceClassifierFSForm.select(parameterlist.getFSType());
        choiceClassifierFSForm.setEnabled(parameterlist.getStateFSType());
        labelButtonClassifierFSSpecify.setEnabled(parameterlist.getStateFSSpecify());
        if(parameterlist.getAggregationFunction() == 0)
        {
            radioButtonClassifierAFMax.setState(true);
        } else
        {
            radioButtonClassifierAFWS.setState(true);
        }
        choiceClassifierICR.select(parameterlist.getClassificationFunction());
        labelButtonClassifierChange.setEnabled(parameterlist.getStateRuleEdit());
        if(parameterlist.getSizeAutomatic())
        {
            radioButtonAutomaticFR.setState(true);
        } else
        {
            radioButtonMaxNumberFR.setState(true);
        }
        textFieldSizeRB.setText(Integer.toString(parameterlist.getMaxRules()));
        textFieldSizeRB.setEnabled(parameterlist.getStateMaxRules());
        if(parameterlist.getRuleLearningMethod() == 1)
        {
            radioButtonRuleLearningBest.setState(true);
        } else
        {
            radioButtonRuleLearningBestPClass.setState(true);
        }
        checkboxReLearn.setState(parameterlist.getRelearnRuleBase());
        if(parameterlist.getValidationType() == 0)
        {
            radioButtonTrainingNoVal.setState(true);
        } else
        if(parameterlist.getValidationType() == 2)
        {
            radioButtonTrainingCrossVal.setState(true);
        } else
        {
            radioButtonSingleTest.setState(true);
        }
        textFieldCrossValNumber.setText(Integer.toString(parameterlist.getValidationFactor()));
        textFieldCrossValNumber.setEnabled(parameterlist.getStateValidationFactor());
        textFieldSingleTest.setText(Integer.toString(parameterlist.getTestPercentage()));
        textFieldSingleTest.setEnabled(parameterlist.getStateTestPercentage());
        textFieldNoEpochsMax.setText(Integer.toString(parameterlist.getMaxEpochs()));
        textFieldNoEpochsMin.setText(Integer.toString(parameterlist.getMinEpochs()));
        textFieldAfterOpt.setText(Integer.toString(parameterlist.getAdditionalEpochs()));
        textFieldAdmClassErrors.setText(Integer.toString(parameterlist.getAdmissibleErrors()));
        checkboxRelativeOrder.setState(parameterlist.getKeepOrder());
        checkboxOverlap.setState(parameterlist.getMustOverlap());
        checkboxSymmetrical.setState(parameterlist.getStaySymmetric());
        checkboxIntersect.setState(parameterlist.getIntersect05());
        if(parameterlist.getRuleWeights() == 0)
        {
            radioButtonWeightNotUsed.setState(true);
        } else
        if(parameterlist.getRuleWeights() == 1)
        {
            radioButtonInterval.setState(true);
        } else
        {
            radioButtonArbitrary.setState(true);
        }
        textFieldLearningRate.setText(Double.toString(parameterlist.getLearningRate()));
    }

    protected void updateParameterOK()
    {
        boolean flag1 = false;
        parameter.setProjectTitle(textFieldProjectTitle.getText());
        parameter.setProjectDescription(textAreaProjectDescription.getText());
        parameter.setDataFileName(textFieldDataFileName.getText());
        parameter.setStateEditLabels(labelButtonEditLabels.isEnabled());
        parameter.setStateStatistics(labelButtonStatistics.isEnabled());
        if(dataTable != null && !parameter.fsParametersExist())
        {
            parameter.setFSParameters(dataTable.independent);
        }
        int i;
        try
        {
            i = (new Integer(textFieldClassifierFSNumber.getText())).intValue();
        }
        catch(NumberFormatException _ex)
        {
            i = parameter.getFSNumber();
        }
        int j = choiceClassifierFSForm.getSelectedIndex();
        boolean flag = radioButtonGroupPanelFuzzySet.getSelectedRadioButton() == radioButtonClassificationFSIndividual;
        if(nefclass != null)
        {
            if(parameter.getFSIndividual() && !flag && !parameter.compareFSParameters(i, j) || !parameter.getFSIndividual() && !flag && (i != parameter.getFSNumber() || j != parameter.getFSType()))
            {
                boolean flag2 = true;
                if(nefclass.getNumberOfRules() > 0)
                {
                    DialogYesNo dialogyesno = new DialogYesNo((Frame)getParent(), true);
                    dialogyesno.showMessage("Attention!", "The rule base will be deleted to use the new fuzzy sets.", "You have specified new parameters for the fuzzy partitions. There is a rule base" +
" that uses the old fuzzy partitions. To use these new parameters, the current ru" +
"le base must be deleted. Continue?"
);
                    flag2 = dialogyesno.closedWithYes();
                }
                if(flag2)
                {
                    parameter.setFSNumber(i);
                    parameter.setFSType(j);
                    parameter.setFSIndividual(flag);
                    try
                    {
                        nefclass.createPartitions(i, j);
                    }
                    catch(NefclassInvalidException _ex)
                    {
                        message.showMessage("Internal Error!", "Please check the validity of all parameters.", "Sorry, this error shouldn't happen. The current NEFCLASS system is invalid, prob" +
"ably due to a bug in the software. Please try again, after checking all NEFCLASS" +
" parameters. Please consider to submit a bug report."
);
                    }
                }
            } else
            {
                parameter.setFSNumber(i);
                parameter.setFSType(j);
                parameter.setFSIndividual(flag);
            }
        } else
        {
            parameter.setFSNumber(i);
            parameter.setFSType(j);
            parameter.setFSIndividual(flag);
        }
        parameter.setStateFSNumber(textFieldClassifierFSNumber.isEnabled());
        parameter.setStateFSType(choiceClassifierFSForm.isEnabled());
        parameter.setStateFSSpecify(labelButtonClassifierFSSpecify.isEnabled());
        if(radioButtonGroupPanelAggregation.getSelectedRadioButton() == radioButtonClassifierAFMax)
        {
            parameter.setAggregationFunction(0);
        } else
        {
            parameter.setAggregationFunction(1);
        }
        parameter.setClassificationFunction(choiceClassifierICR.getSelectedIndex());
        parameter.setStateRuleEdit(labelButtonClassifierChange.isEnabled());
        parameter.setSizeAutomatic(radioButtonGroupPanelSizeRB.getSelectedRadioButton() == radioButtonAutomaticFR);
        try
        {
            parameter.setMaxRules((new Integer(textFieldSizeRB.getText())).intValue());
        }
        catch(NumberFormatException _ex) { }
        parameter.setStateMaxRules(textFieldSizeRB.isEnabled());
        if(radioButtonGroupPanelRuleLearning.getSelectedRadioButton() == radioButtonRuleLearningBest)
        {
            parameter.setRuleLearningMethod(1);
        } else
        {
            parameter.setRuleLearningMethod(2);
        }
        parameter.setRelearnRuleBase(checkboxReLearn.getState());
        if(radioButtonGroupPanelValidation.getSelectedRadioButton() == radioButtonTrainingNoVal)
        {
            parameter.setValidationType(0);
        } else
        if(radioButtonGroupPanelValidation.getSelectedRadioButton() == radioButtonTrainingCrossVal)
        {
            parameter.setValidationType(2);
        } else
        {
            parameter.setValidationType(1);
        }
        try
        {
            parameter.setValidationFactor((new Integer(textFieldCrossValNumber.getText())).intValue());
        }
        catch(NumberFormatException _ex) { }
        parameter.setStateValidationFactor(textFieldCrossValNumber.isEnabled());
        try
        {
            parameter.setTestPercentage((new Integer(textFieldSingleTest.getText())).intValue());
        }
        catch(NumberFormatException _ex) { }
        parameter.setStateTestPercentage(textFieldSingleTest.isEnabled());
        try
        {
            parameter.setMaxEpochs((new Integer(textFieldNoEpochsMax.getText())).intValue());
        }
        catch(NumberFormatException _ex) { }
        try
        {
            parameter.setMinEpochs((new Integer(textFieldNoEpochsMin.getText())).intValue());
        }
        catch(NumberFormatException _ex) { }
        try
        {
            parameter.setAdditionalEpochs((new Integer(textFieldAfterOpt.getText())).intValue());
        }
        catch(NumberFormatException _ex) { }
        try
        {
            parameter.setAdmissibleErrors((new Integer(textFieldAdmClassErrors.getText())).intValue());
        }
        catch(NumberFormatException _ex) { }
        parameter.setKeepOrder(checkboxRelativeOrder.getState());
        parameter.setMustOverlap(checkboxOverlap.getState());
        parameter.setStaySymmetric(checkboxSymmetrical.getState());
        parameter.setIntersect05(checkboxIntersect.getState());
        if(radioButtonGroupPanelConstraints.getSelectedRadioButton() == radioButtonWeightNotUsed)
        {
            parameter.setRuleWeights(0);
        } else
        if(radioButtonGroupPanelConstraints.getSelectedRadioButton() == radioButtonInterval)
        {
            parameter.setRuleWeights(1);
        } else
        {
            parameter.setRuleWeights(2);
        }
        try
        {
            parameter.setLearningRate((new Double(textFieldLearningRate.getText())).doubleValue());
            return;
        }
        catch(NumberFormatException _ex)
        {
            return;
        }
    }

    public DataTable getDataTable()
    {
        return dataTable;
    }

    public void setDataTable(DataTable datatable)
    {
        dataTable = datatable;
    }

    public void setNefclass(Nefclass nefclass1)
    {
        nefclass = nefclass1;
        nefclassWasNull = nefclass1 == null;
    }

    public Nefclass getNefclass()
    {
        return nefclass;
    }

    protected void updateParameterCancel()
    {
        if(dataTable != null)
        {
            parameter.setDataFileName(textFieldDataFileName.getText());
            parameter.setStateEditLabels(labelButtonEditLabels.isEnabled());
            parameter.setStateStatistics(labelButtonStatistics.isEnabled());
            parameter.setStateRuleEdit(labelButtonClassifierChange.isEnabled());
        }
    }

    public void setHelp(Help help1, String s)
    {
        help = help1;
        helpAnchor = s;
    }

    void labelButtonProjectSpecificationHelp_actionPerformed(ActionEvent actionevent)
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

