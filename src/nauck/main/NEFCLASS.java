package nauck.main;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.text.DateFormat;
import java.util.*;
import nauck.data.DataTable;
import nauck.data.ParseDataFileException;
import nauck.fuzzy.*;
import nauck.util.*;
import symantec.itools.awt.shape.HorizontalLine;

public class NEFCLASS extends Frame {

    class SymWindow extends WindowAdapter {

        public void windowClosing(WindowEvent windowevent) {
            Object obj = windowevent.getSource();
            if (obj == NEFCLASS.this) {
                miExit_Action(null);
            }
        }

        SymWindow() {
        }
    }

    class SymAction
            implements ActionListener {

        public void actionPerformed(ActionEvent actionevent) {
            Object obj = actionevent.getSource();
            if (obj == miAbout) {
                miAbout_Action(actionevent);
                return;
            }
            if (obj == FileExit) {
                miExit_Action(actionevent);
                return;
            }
            if (obj == ProjectSaveAs) {
                ProjectSaveAs_ActionPerformed(actionevent);
                return;
            }
            if (obj == ProjectSave) {
                ProjectSave_ActionPerformed(actionevent);
                return;
            }
            if (obj == ProjectOpen) {
                ProjectOpen_Action(actionevent);
                return;
            }
            if (obj == ProjectClose) {
                ProjectClose_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemEdit) {
                menuItemEdit_ActionPerformed(actionevent);
                return;
            }
            if (obj == ProjectNew) {
                ProjectNew_ActionPerformed(actionevent);
                return;
            }
            if (obj == RulesEdit) {
                RulesEdit_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemFuzzySets) {
                menuItemFuzzySets_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemCreateRuleBase) {
                menuItemCreateRuleBase_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemViewStatistics) {
                menuItemViewStatistics_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemOpenData) {
                menuItemOpenData_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemCreateClassifier) {
                menuItemCreateClassifier_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemTrainFS) {
                menuItemTrainFS_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemStopTraining) {
                menuItemStopTraining_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemClassifyData) {
                menuItemClassifyData_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemClassifyAppData) {
                menuItemClassifyAppData_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemResetFuzzySets) {
                menuItemResetFuzzySets_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemViewError) {
                menuItemViewError_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemRestoreRuleBase) {
                menuItemRestoreRuleBase_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemLoadAppData) {
                menuItemLoadAppData_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemViewAppStatistics) {
                menuItemViewAppStatistics_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemRestoreFuzzySets) {
                menuItemRestoreFuzzySets_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemViewHideAll) {
                menuItemViewHideAll_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemViewShowAll) {
                menuItemViewShowAll_ActionPerformed(actionevent);
                return;
            }
            if (obj == RulesPruning) {
                RulesPruning_ActionPerformed(actionevent);
                return;
            }
            if (obj == RulesExport) {
                RulesExport_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemCreateAndPrune) {
                menuItemCreateAndPrune_ActionPerformed(actionevent);
                return;
            }
            if (obj == menuItemCreatePrunedClassifier) {
                menuItemCreatePrunedClassifier_ActionPerformed(actionevent);
                return;
            }
            if (obj == HelpTopics) {
                HelpTopics_ActionPerformed(actionevent);
                return;
            }
            if (obj == nefclass) {
                setStatus(actionevent.getActionCommand());
            }
        }

        SymAction() {
        }
    }

    Properties environment;
    String helpFile;
    String browser;
    String configFile;
    boolean displayHelp;
    Help help;
    ParameterList parameter;
    DataTable dataTable;
    DataTable appData;
    Nefclass nefclass;
    DialogProjectSpecification projectSpecification;
    FrameStatistics statistics;
    FrameStatistics appStatistics;
    FrameStatistics training;
    FrameStatistics application;
    FrameShowFS fuzzySets;
    RuleLearningProgressDialog ruleLearning;
    FuzzySetLearningProgressDialog fsLearning;
    FunctionLayoutPanel functionPanel;
    DialogRuleEdit ruleEditor;
    DialogMessage message;
    DialogProgress progress;
    DialogClose closeDialog;
    DialogYesNo yesNoDialog;
    DialogProgress progressDialog;
    AboutDialog aboutDialog;
    Font monoFont;
    boolean newProject;
    boolean projectSaved;
    boolean fuzzySetsTrained;
    boolean logging;
    PrintWriter logFile;
    SymAction lSymAction;
    boolean fComponentsAdjusted;
    Panel panel;
    TextField textApplicationData;
    Label labelApplicationData;
    TextField textTrainingData;
    Label labelTrainingData;
    TextField textClassifier;
    Label labelClassifier;
    TextField textStatus;
    Label labelStatus;
    TextField textProject;
    Label labelProject;
    HorizontalLine horizontalLine;
    MenuBar mainMenuNefclass;
    Menu menuProject;
    MenuItem ProjectNew;
    MenuItem ProjectOpen;
    MenuItem menuItemEdit;
    MenuItem ProjectSave;
    MenuItem ProjectSaveAs;
    MenuItem ProjectClose;
    MenuItem menuItemOpenData;
    MenuItem menuItemLoadAppData;
    MenuItem FileExit;
    Menu MenuRun;
    MenuItem menuItemCreateClassifier;
    MenuItem menuItemCreateRuleBase;
    MenuItem menuItemTrainFS;
    MenuItem RulesPruning;
    MenuItem menuItemCreateAndPrune;
    MenuItem menuItemCreatePrunedClassifier;
    MenuItem menuItemRestoreFuzzySets;
    MenuItem menuItemResetFuzzySets;
    MenuItem menuItemClassifyData;
    MenuItem menuItemClassifyAppData;
    MenuItem menuItemStopTraining;
    Menu MenuView;
    MenuItem menuItemViewError;
    MenuItem menuItemFuzzySets;
    MenuItem menuItemViewStatistics;
    MenuItem menuItemViewAppStatistics;
    MenuItem menuItemViewPlots;
    MenuItem menuItemViewHideAll;
    MenuItem menuItemViewShowAll;
    Menu MenuRules;
    MenuItem RulesEdit;
    MenuItem menuItemRestoreRuleBase;
    MenuItem RulesExport;
    Menu menuHelp;
    MenuItem HelpTopics;
    MenuItem miAbout;

    public NEFCLASS() {
        fComponentsAdjusted = false;
        parameter = new ParameterList();
        dataTable = null;
        appData = null;
        nefclass = null;
        projectSpecification = new DialogProjectSpecification(this, true);
        statistics = null;
        appStatistics = null;
        training = null;
        application = null;
        fuzzySets = null;
        ruleLearning = null;
        fsLearning = null;
        functionPanel = null;
        ruleEditor = null;
        message = new DialogMessage(this, true);
        yesNoDialog = new DialogYesNo(this, true);
        progress = null;
        closeDialog = null;
        progressDialog = null;
        aboutDialog = null;
        monoFont = null;
        newProject = true;
        projectSaved = true;
        fuzzySetsTrained = false;
        logging = false;
        displayHelp = true;
        help = null;
        setLayout(null);
        setVisible(false);
        setSize(640, 52);
        setFont(new Font("SansSerif", 0, 12));
        setBackground(new Color(0xc0c0c0));
        panel = new Panel();
        panel.setLayout(null);
        panel.setBounds(0, 2, 640, 49);
        panel.setBackground(new Color(0xc0c0c0));
        add(panel);
        textApplicationData = new TextField();
        textApplicationData.setEditable(false);
        textApplicationData.setText("not available");
        textApplicationData.setBounds(363, 21, 120, 26);
        panel.add(textApplicationData);
        labelApplicationData = new Label(" application data");
        labelApplicationData.setBounds(363, 1, 104, 20);
        labelApplicationData.setFont(new Font("SansSerif", 1, 12));
        panel.add(labelApplicationData);
        textTrainingData = new TextField();
        textTrainingData.setEditable(false);
        textTrainingData.setText("not available");
        textTrainingData.setBounds(242, 21, 120, 26);
        panel.add(textTrainingData);
        labelTrainingData = new Label(" training data");
        labelTrainingData.setBounds(242, 1, 104, 20);
        labelTrainingData.setFont(new Font("SansSerif", 1, 12));
        panel.add(labelTrainingData);
        textClassifier = new TextField();
        textClassifier.setEditable(false);
        textClassifier.setText("not available");
        textClassifier.setBounds(121, 21, 120, 26);
        panel.add(textClassifier);
        labelClassifier = new Label(" classifier");
        labelClassifier.setBounds(121, 0, 120, 20);
        labelClassifier.setFont(new Font("SansSerif", 1, 12));
        panel.add(labelClassifier);
        textStatus = new TextField();
        textStatus.setEditable(false);
        textStatus.setText("ready");
        textStatus.setBounds(0, 21, 120, 26);
        panel.add(textStatus);
        labelStatus = new Label(" status");
        labelStatus.setBounds(0, 0, 120, 20);
        labelStatus.setFont(new Font("SansSerif", 1, 12));
        panel.add(labelStatus);
        textProject = new TextField();
        textProject.setEditable(false);
        textProject.setText("not available");
        textProject.setBounds(484, 21, 120, 26);
        panel.add(textProject);
        labelProject = new Label(" project");
        labelProject.setBounds(484, 0, 104, 20);
        labelProject.setFont(new Font("SansSerif", 1, 12));
        panel.add(labelProject);
        horizontalLine = new HorizontalLine();
        try {
            horizontalLine.setBevelStyle(0);
        } catch (PropertyVetoException _ex) {
        }
        horizontalLine.setBounds(0, 0, 640, 2);
        add(horizontalLine);
        setTitle("NEFCLASS");
        mainMenuNefclass = new MenuBar();
        menuProject = new Menu("Project");
        ProjectNew = new MenuItem("New Project");
        ProjectNew.setShortcut(new MenuShortcut(78, false));
        menuProject.add(ProjectNew);
        ProjectOpen = new MenuItem("Open Project");
        ProjectOpen.setShortcut(new MenuShortcut(79, false));
        menuProject.add(ProjectOpen);
        menuItemEdit = new MenuItem("Edit Project");
        menuItemEdit.setShortcut(new MenuShortcut(69, false));
        menuProject.add(menuItemEdit);
        ProjectSave = new MenuItem("Save");
        ProjectSave.setShortcut(new MenuShortcut(83, false));
        menuProject.add(ProjectSave);
        ProjectSaveAs = new MenuItem("Save as");
        ProjectSaveAs.setShortcut(new MenuShortcut(83, true));
        menuProject.add(ProjectSaveAs);
        ProjectClose = new MenuItem("Close Project");
        ProjectClose.setShortcut(new MenuShortcut(67, true));
        menuProject.add(ProjectClose);
        menuProject.addSeparator();
        menuItemOpenData = new MenuItem("Load Training Data");
        menuItemOpenData.setShortcut(new MenuShortcut(68, false));
        menuProject.add(menuItemOpenData);
        menuItemLoadAppData = new MenuItem("Load Application Data");
        menuItemLoadAppData.setShortcut(new MenuShortcut(68, true));
        menuProject.add(menuItemLoadAppData);
//        menuProject.addSeparator();
//        FileExit = new MenuItem("Exit NEFCLASS");
//        FileExit.setShortcut(new MenuShortcut(88, false));
//        menuProject.add(FileExit);
        mainMenuNefclass.add(menuProject);
        MenuRun = new Menu("Classifier");
        menuItemCreateClassifier = new MenuItem("Create Classifier");
        MenuRun.add(menuItemCreateClassifier);
        menuItemCreateRuleBase = new MenuItem("Create Rule Base Only");
        MenuRun.add(menuItemCreateRuleBase);
        menuItemTrainFS = new MenuItem("Train Fuzzy Sets Only");
        MenuRun.add(menuItemTrainFS);
        MenuRun.addSeparator();
        RulesPruning = new MenuItem("Prune Classifier");
        MenuRun.add(RulesPruning);
        menuItemCreateAndPrune = new MenuItem("Create Classifier and Prune It");
        MenuRun.add(menuItemCreateAndPrune);
        menuItemCreatePrunedClassifier = new MenuItem("Create a Pruned Classifier");
        MenuRun.add(menuItemCreatePrunedClassifier);
        MenuRun.addSeparator();
        menuItemRestoreFuzzySets = new MenuItem("Restore Fuzzy Sets");
        MenuRun.add(menuItemRestoreFuzzySets);
        menuItemResetFuzzySets = new MenuItem("Reset Fuzzy Sets");
        MenuRun.add(menuItemResetFuzzySets);
        MenuRun.addSeparator();
        menuItemClassifyData = new MenuItem("Classify Training Data");
        MenuRun.add(menuItemClassifyData);
        menuItemClassifyAppData = new MenuItem("Classify Application Data");
        MenuRun.add(menuItemClassifyAppData);
        MenuRun.addSeparator();
        menuItemStopTraining = new MenuItem("Stop Training Process");
        MenuRun.add(menuItemStopTraining);
        mainMenuNefclass.add(MenuRun);
        MenuView = new Menu("View");
        menuItemViewError = new MenuItem("Error");
        MenuView.add(menuItemViewError);
        menuItemFuzzySets = new MenuItem("Fuzzy Sets");
        MenuView.add(menuItemFuzzySets);
        menuItemViewStatistics = new MenuItem("Statistics Training Data");
        MenuView.add(menuItemViewStatistics);
        menuItemViewAppStatistics = new MenuItem("Statistics Application Data");
        MenuView.add(menuItemViewAppStatistics);
        menuItemViewPlots = new MenuItem("Plots");
        MenuView.add(menuItemViewPlots);
        MenuView.addSeparator();
        menuItemViewHideAll = new MenuItem("Hide All");
        MenuView.add(menuItemViewHideAll);
        menuItemViewShowAll = new MenuItem("Show All");
        MenuView.add(menuItemViewShowAll);
        mainMenuNefclass.add(MenuView);
        MenuRules = new Menu("Rules");
        RulesEdit = new MenuItem("Edit");
        MenuRules.add(RulesEdit);
        menuItemRestoreRuleBase = new MenuItem("Restore Rule Base");
        MenuRules.add(menuItemRestoreRuleBase);
        RulesExport = new MenuItem("Export");
        MenuRules.add(RulesExport);
        mainMenuNefclass.add(MenuRules);
        menuHelp = new Menu("Help");
        mainMenuNefclass.setHelpMenu(menuHelp);
        HelpTopics = new MenuItem("Help Topics...");
        menuHelp.add(HelpTopics);
        miAbout = new MenuItem("About..");
        menuHelp.add(miAbout);
        mainMenuNefclass.add(menuHelp);
        setMenuBar(mainMenuNefclass);
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        lSymAction = new SymAction();
        miAbout.addActionListener(lSymAction);
//        FileExit.addActionListener(lSymAction);
        ProjectSave.addActionListener(lSymAction);
        ProjectSaveAs.addActionListener(lSymAction);
        ProjectOpen.addActionListener(lSymAction);
        ProjectClose.addActionListener(lSymAction);
        menuItemEdit.addActionListener(lSymAction);
        ProjectNew.addActionListener(lSymAction);
        RulesEdit.addActionListener(lSymAction);
        menuItemFuzzySets.addActionListener(lSymAction);
        menuItemCreateRuleBase.addActionListener(lSymAction);
        menuItemViewStatistics.addActionListener(lSymAction);
        menuItemOpenData.addActionListener(lSymAction);
        menuItemCreateClassifier.addActionListener(lSymAction);
        menuItemTrainFS.addActionListener(lSymAction);
        menuItemStopTraining.addActionListener(lSymAction);
        menuItemClassifyData.addActionListener(lSymAction);
        menuItemClassifyAppData.addActionListener(lSymAction);
        menuItemResetFuzzySets.addActionListener(lSymAction);
        menuItemViewError.addActionListener(lSymAction);
        menuItemRestoreRuleBase.addActionListener(lSymAction);
        menuItemLoadAppData.addActionListener(lSymAction);
        menuItemViewAppStatistics.addActionListener(lSymAction);
        menuItemRestoreFuzzySets.addActionListener(lSymAction);
        menuItemViewHideAll.addActionListener(lSymAction);
        menuItemViewShowAll.addActionListener(lSymAction);
        RulesPruning.addActionListener(lSymAction);
        RulesExport.addActionListener(lSymAction);
        menuItemCreateAndPrune.addActionListener(lSymAction);
        menuItemCreatePrunedClassifier.addActionListener(lSymAction);
        HelpTopics.addActionListener(lSymAction);
        setStatus("ready");
        setupEnvironment();
    }

    public NEFCLASS(String s) {
        this();
        setTitle(s);
    }

    public synchronized void show() {
        super.show();
    }

    public static void main(String args[]) {
        (new NEFCLASS()).show();
    }

    public void addNotify() {
        Dimension dimension = getSize();
        super.addNotify();
        if (fComponentsAdjusted) {
            return;
        }
        setSize(insets().left + insets().right + dimension.width, insets().top + insets().bottom + dimension.height);
        Component acomponent[] = getComponents();
        for (int i = 0; i < acomponent.length; i++) {
            Point point = acomponent[i].getLocation();
            point.translate(insets().left, insets().top);
            acomponent[i].setLocation(point);
        }

        fComponentsAdjusted = true;
    }

    void Frame1_WindowClosing(WindowEvent windowevent) {
        dispose();
//        System.exit(0);
    }

    void miAbout_Action(ActionEvent actionevent) {
        if (aboutDialog == null) {
            aboutDialog = new AboutDialog(this, true);
        }
        aboutDialog.setVisible(true);
    }

    void ProjectNew_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (newProject) {
            parameter = new ParameterList();
            if (dataTable != null) {
                parameter.setDataFileName(dataTable.filename);
                parameter.setStateEditLabels(true);
                parameter.setStateStatistics(true);
                parameter.setStateRuleEdit(true);
            }
            projectSpecification.setDataTable(dataTable);
            projectSpecification.setNefclass(nefclass);
            projectSpecification.setParameter(parameter);
            projectSpecification.setVisible(true);
            dataTable = projectSpecification.getDataTable();
            if (dataTable != null) {
                parameter.setVariableNumber(dataTable.independent);
            }
            nefclass = projectSpecification.getNefclass();
            try {
                if (nefclass != null) {
                    parameter.setupNefclassParameters(nefclass);
                    setupNefclassInterface();
                }
            } catch (NefclassInvalidException _ex) {
                message.showMessage("Internal Error!", "Please check the validity of all parameters.", "Sorry, this error shouldn't happen. The current NEFCLASS system is invalid, prob"
                        + "ably due to a bug in the software. Please try again, after checking all NEFCLASS"
                        + " parameters. Please consider to submit a bug report."
                );
            }
            newProject = false;
            projectSaved = false;
        } else {
            message.showMessage("Attention!", "Please close the current project first.", "You can only work with one NEFCLASS-Project at a time. The existing project can "
                    + "be changed with 'Project/Edit Project' or closed with 'Project/Close Project'."
            );
        }
        setStatus("ready");
    }

    void ProjectOpen_Action(ActionEvent actionevent) {
        
        boolean flag = false;
        if (newProject) {
            FileDialog filedialog = new FileDialog(this, "Open Project File", 0);
            filedialog.setFile("*.prj");
            filedialog.show();
            String s2 = filedialog.getDirectory();
            String s1 = filedialog.getFile();
            String s;
            if (s2 != null) {
                if (s1 != null) {
                    s = s2 + s1;
                } else {
                    s = s1;
                }
            } else {
                s = "";
            }
            if (s != "") {
                parameter = new ParameterList();
                boolean flag1;
                try {
                    parameter.readParameter(s);
                    flag1 = true;
                } catch (ParseDataFileException parsedatafileexception) {
                    flag1 = false;
                    message.showMessage("Read Error!", "Please choose another project.", "Please select another file. The specified file does not contain a valid project."
                            + " This can mean that the file is no project file or that it was corrupted in some"
                            + " way. "
                            + parsedatafileexception.getMessage());
                } catch (IOException _ex) {
                    flag1 = false;
                    message.showMessage("Read Error!", "Please choose a valid project filename.", "An I/O error occured while trying to read the project file. Please make sure tha"
                            + "t the filename you specified for the project is valid and that the file is acces"
                            + "sible."
                    );
                }
                if (flag1) {
                    readProjectClassifier();
                    dataTable = readDataFile(parameter.getDataFileName(), true);
                    if (dataTable == null) {
                        parameter.setStateEditLabels(false);
                        parameter.setStateRuleEdit(false);
                        parameter.setStateStatistics(false);
                    }
                    projectSpecification.setDataTable(dataTable);
                    projectSpecification.setNefclass(nefclass);
                    projectSpecification.setParameter(parameter);
                    projectSpecification.setVisible(true);
                    dataTable = projectSpecification.getDataTable();
                    if (dataTable != null) {
                        parameter.setVariableNumber(dataTable.independent);
                    }
                    nefclass = projectSpecification.getNefclass();
                    try {
                        if (nefclass != null) {
                            parameter.setupNefclassParameters(nefclass);
                            setupNefclassInterface();
                        }
                    } catch (NefclassInvalidException _ex) {
                        message.showMessage("Internal Error!", "Please check the validity of all parameters.", "Sorry, this error shouldn't happen. The current NEFCLASS system is invalid, prob"
                                + "ably due to a bug in the software. Please try again, after checking all NEFCLASS"
                                + " parameters. Please consider to submit a bug report."
                        );
                    }
                    newProject = false;
                    projectSaved = false;
                }
            }
        } else {
            message.showMessage("Attention!", "Please close the current project first.", "You can only work with one NEFCLASS-Project at a time. The existing project can "
                    + "be changed with 'Project/Edit Project' or closed with 'Project/Close Project'."
            );
        }
        setStatus("ready");
    }

    void readProjectClassifier() {
        String s1 = parameter.getProjectFileName();
        String s;
        if (s1.endsWith(".prj")) {
            StringBuffer stringbuffer = new StringBuffer(s1);
            stringbuffer.setLength(stringbuffer.length() - ".prj".length());
            s = stringbuffer.toString() + ".cls";
        } else {
            s = s1 + ".cls";
        }
        try {
            nefclass = new Nefclass(0, 0, 0, parameter.getAggregationFunction());
            nefclass.read(s);
            return;
        } catch (IOException _ex) {
            message.showMessage("Read Error!", "The classifier of this project is missing.", "An I/O error occured while trying to read the classifier of this project. A new "
                    + "classifier with empty rule base was created instead.Please make sure that a clas"
                    + "sifier file resides in the same diretory as its project file. "
            );
            nefclass = null;
            return;
        } catch (NefclassInvalidException nefclassinvalidexception) {
            message.showMessage("Read Error!", "The classifier of this project is invalid.", "The classifier file of this project is not valid. A new classifier with empty ru"
                    + "le base will be created instead.The reason can be that the file does not contain"
                    + " a classifier or that it was corrupted in some way: "
                    + nefclassinvalidexception.getMessage());
        }
        nefclass = null;
    }

    void menuItemEdit_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        projectSpecification.setDataTable(dataTable);
        projectSpecification.setNefclass(nefclass);
        projectSpecification.setParameter(parameter);
        projectSpecification.setVisible(true);
        dataTable = projectSpecification.getDataTable();
        if (dataTable != null) {
            parameter.setVariableNumber(dataTable.independent);
        }
        nefclass = projectSpecification.getNefclass();
        try {
            if (nefclass != null) {
                parameter.setupNefclassParameters(nefclass);
                setupNefclassInterface();
            }
        } catch (NefclassInvalidException _ex) {
            message.showMessage("Internal Error!", "Please check the validity of all parameters.", "Sorry, this error shouldn't happen. The current NEFCLASS system is invalid, prob"
                    + "ably due to a bug in the software. Please try again, after checking all NEFCLASS"
                    + " parameters. Please consider to submit a bug report."
            );
        }
        newProject = false;
        projectSaved = false;
        setStatus("ready");
    }

    protected void projectSaveAs(String s) {
        FileDialog filedialog = new FileDialog(this, "Save Project File", 1);
        filedialog.setFile(s);
        filedialog.setVisible(true);
        String s2 = filedialog.getDirectory();
        String s1 = filedialog.getFile();
        if (s1 != null) {
            if (s2 != null) {
                s = s2 + s1;
            } else {
                s = s1;
            }
        } else {
            s = "";
        }
        if (s != "") {
            try {
                parameter.writeParameter(s);
                projectSaved = true;
            } catch (IOException _ex) {
                message.showMessage("Attention!", "Please check the project filename.", "An I/O error occured while trying to save the project. Please make sure that the"
                        + " filename you specified for the project is valid and that the destination drive "
                        + "is accessible."
                );
                projectSaved = false;
            }
            if (nefclass != null && projectSaved) {
                String s3;
                if (s.endsWith(".prj")) {
                    StringBuffer stringbuffer = new StringBuffer(s);
                    stringbuffer.setLength(stringbuffer.length() - ".prj".length());
                    s3 = stringbuffer.toString() + ".cls";
                } else {
                    s3 = s + ".cls";
                }
                try {
                    nefclass.write(s3);
                    return;
                } catch (IOException _ex) {
                    message.showMessage("Attention!", "Please check the project filename.", "An I/O error occured while trying to save the classifier. Please make sure that "
                            + "the filename you specified for the project is valid and that the destination dri"
                            + "ve is accessible."
                    );
                }
                projectSaved = false;
                return;
            }
        } else {
            projectSaved = false;
        }
    }

    void ProjectSave_ActionPerformed(ActionEvent actionevent) {
        boolean flag = true;
        String s = parameter.getProjectFileName();
        if (s != "") {
            try {
                parameter.writeParameter(s);
                projectSaved = true;
            } catch (IOException _ex) {
                message.showMessage("Attention!", "Please check the project filename.", "An I/O error occured while trying to save the project. Please make sure that the"
                        + " filename you specified for the project is valid and that the destination drive "
                        + "is accessible."
                );
                projectSaved = false;
            }
        } else {
            if (parameter.getProjectTitle() != "") {
                try {
                    s = parameter.getProjectTitle();
                    if (!s.endsWith(".prj")) {
                        s = s + ".prj";
                    }
                    parameter.writeParameter(s);
                    projectSaved = true;
                } catch (IOException _ex) {
                    message.showMessage("Attention!", "Please check the project filename.", "An I/O error occured while trying to save the project. Please make sure that the"
                            + " filename you specified for the project is valid and that the destination drive "
                            + "is accessible."
                    );
                    projectSaved = false;
                    s = "";
                    parameter.setProjectFileName("");
                    flag = false;
                }
            } else {
                projectSaveAs("*.prj");
                flag = false;
            }
        }
        if (flag && nefclass != null) {
            String s1;
            if (s.endsWith(".prj")) {
                StringBuffer stringbuffer = new StringBuffer(s);
                stringbuffer.setLength(stringbuffer.length() - ".prj".length());
                s1 = stringbuffer.toString() + ".cls";
            } else {
                s1 = s + ".cls";
            }
            try {
                nefclass.write(s1);
            } catch (IOException _ex) {
                message.showMessage("Attention!", "Please check the project filename.", "An I/O error occured while trying to save the classifier. Please make sure that "
                        + "the filename you specified for the project is valid and that the destination dri"
                        + "ve is accessible."
                );
                projectSaved = false;
            }
        }
        setStatus("ready");
    }

    void ProjectSaveAs_ActionPerformed(ActionEvent actionevent) {
        String s = parameter.getProjectFileName();
        if (s != "") {
            projectSaveAs(s);
        } else {
            if (parameter.getProjectTitle() != "") {
                projectSaveAs(parameter.getProjectTitle() + ".prj");
            } else {
                projectSaveAs("*.prj");
            }
        }
        setStatus("ready");
    }

    void ProjectClose_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (!projectSaved) {
            DialogClose dialogclose = new DialogClose(this, true);
            dialogclose.showMessage("Attention!", "The project has changed, do you want to save it?", "The current project state was not saved. Please click on the 'Save-button' to in"
                    + "voke a 'Save As Dialog'. Clicking on the 'Close-button' will close the project w"
                    + "ithout saving it."
            );
            if (!dialogclose.wasCanceled()) {
                if (dialogclose.closedWithSave()) {
                    ProjectSaveAs_ActionPerformed(null);
                }
                parameter = new ParameterList();
                nefclass = null;
                dataTable = null;
                appData = null;
                newProject = true;
                projectSaved = true;
                fuzzySetsTrained = false;
                closeAllNefclassFrames();
                closeLogFile();
                setStatus("ready");
                return;
            }
        } else {
            parameter = new ParameterList();
            nefclass = null;
            dataTable = null;
            appData = null;
            newProject = true;
            projectSaved = true;
            fuzzySetsTrained = false;
            closeAllNefclassFrames();
            closeLogFile();
        }
    }

    void menuItemOpenData_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        boolean flag = false;
        FileDialog filedialog = new FileDialog(this, "Load Data File", 0);
        filedialog.setFile("*.dat");
        filedialog.show();
        String s2 = filedialog.getDirectory();
        String s1 = filedialog.getFile();
        String s = s2 + s1;
        if (s1 == null || s2 == null) {
            s = "";
        }
        if (s != "") {
            if (nefclass != null && dataTable != null) {
                flag = nefclass.usesData(dataTable);
            }
            DataTable datatable = readDataFile(s, true);
            if (datatable != null) {
                dataTable = datatable;
                if (nefclass == null) {
                    try {
                        nefclass = parameter.setupNefclass(dataTable, appData);
                        setupNefclassInterface();
                        newProject = false;
                        projectSaved = false;
                    } catch (NefclassInvalidException nefclassinvalidexception) {
                        nefclass = null;
                        message.showMessage("Attention!", "It is not possible to create the classifier.", "The creation of a new classifier object failed. There is probably something wron"
                                + "g with the training data: "
                                + nefclassinvalidexception.getMessage());
                    }
                }
                parameter.setDataFileName(s);
                parameter.setStateEditLabels(true);
                parameter.setStateRuleEdit(true);
                parameter.setStateStatistics(true);
            } else {
                if (flag) {
                    try {
                        nefclass.setData(dataTable);
                        parameter.setDataFileName(dataTable.filename);
                        parameter.setStateEditLabels(true);
                        parameter.setStateRuleEdit(true);
                        parameter.setStateStatistics(true);
                    } catch (NefclassInvalidException _ex) {
                        message.showMessage("Internal Error!", "Please check the validity of all parameters.", "Sorry, this error shouldn't happen. The current NEFCLASS system is invalid, prob"
                                + "ably due to a bug in the software. Please try again, after checking all NEFCLASS"
                                + " parameters. Please consider to submit a bug report."
                        );
                    }
                } else {
                    dataTable = null;
                    parameter.setDataFileName("");
                    parameter.setStateEditLabels(false);
                    parameter.setStateRuleEdit(false);
                    parameter.setStateStatistics(false);
                }
            }
        }
        setStatus("ready");
    }

    void menuItemLoadAppData_ActionPerformed(ActionEvent actionevent) {
        boolean flag = false;
        if (nefclass != null) {
            FileDialog filedialog = new FileDialog(this, "Load Application Data File", 0);
            filedialog.setFile("*.dat");
            filedialog.show();
            String s2 = filedialog.getDirectory();
            String s1 = filedialog.getFile();
            String s = s2 + s1;
            if (s1 == null || s2 == null) {
                s = "";
            }
            if (s != "") {
                if (appData != null) {
                    flag = nefclass.usesApplicationData(appData);
                }
                DataTable datatable = readDataFile(s, false);
                if (datatable != null) {
                    appData = datatable;
                } else {
                    if (flag) {
                        try {
                            nefclass.setApplicationData(appData);
                        } catch (NefclassInvalidException _ex) {
                            message.showMessage("Internal Error!", "Please check the validity of all parameters.", "Sorry, this error shouldn't happen. The current NEFCLASS system is invalid, prob"
                                    + "ably due to a bug in the software. Please try again, after checking all NEFCLASS"
                                    + " parameters. Please consider to submit a bug report."
                            );
                        }
                    } else {
                        appData = null;
                    }
                }
            }
        } else {
            message.showMessage("Attention!", "Please specify a NEFCLASS system.", "There is no NEFCLASS system. Please create a new project or load one from a file"
                    + ". The commands can be found in the Project menu."
            );
        }
        setStatus("ready");
    }

    protected DataTable readDataFile(String s, boolean flag) {
        if (progressDialog == null) {
            progressDialog = new DialogProgress();
        } else {
            progressDialog.textArea.setText("");
        }
        progressDialog.setVisible(true);
        DataTable datatable;
        try {
            datatable = new DataTable(s, progressDialog.textArea, progressDialog.progressBar);
            if (nefclass != null) {
                if (flag) {
                    nefclass.setData(datatable);
                } else {
                    nefclass.setApplicationData(datatable);
                }
            }
        } catch (ParseDataFileException parsedatafileexception) {
            datatable = null;
            message.showMessage("Read Error!", "The format of data file is invalid.", "Please load another data file for this project. The format of the data file is n"
                    + "ot valid: "
                    + parsedatafileexception.getMessage());
        } catch (IOException _ex) {
            datatable = null;
            message.showMessage("Read Error!", "The data file cannot be read.", "An I/O error occured while trying to read a data file for the project. Please ma"
                    + "ke sure that the data file exists and that the correct data filename is specifie"
                    + "d in the project dialog. "
            );
        } catch (NefclassInvalidException nefclassinvalidexception) {
            datatable = null;
            message.showMessage("Read Error!", "The data file cannot be used by the current classifier.", "Please load another data file for this project. The format of the data file is n"
                    + "ot valid: "
                    + nefclassinvalidexception.getMessage());
        }
        return datatable;
    }

    void miExit_Action(ActionEvent actionevent) {
        QuitDialog quitdialog = new QuitDialog(this, true);
        ProjectClose_ActionPerformed(null);
        try {
            quitdialog.wrappingLabelQTitle.setText("NEFCLASS");
            quitdialog.wrappingLabelQInstruct.setText("Do you really want to quit?");
        } catch (PropertyVetoException _ex) {
        }
        quitdialog.show();
        if (quitdialog.closedWithYes()) {
            closeLogFile();
            dispose();
            //System.exit(0);
        }
    }

    void RulesEdit_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (nefclass != null) {
            if (ruleEditor == null) {
                ruleEditor = new DialogRuleEdit(this, true);
                ruleEditor.setHelp(help, "re");
            }
            ruleEditor.setNefclass(nefclass);
            ruleEditor.setVisible(true);
        } else {
            message.showMessage("Attention!", "Please specify a NEFCLASS system.", "There is no NEFCLASS system. Please create a new project or load one from a file"
                    + ". The commands can be found in the Project menu."
            );
        }
        setStatus("ready");
    }

    void menuItemFuzzySets_ActionPerformed(ActionEvent actionevent) {
        if (nefclass != null) {
            if (fuzzySets == null) {
                fuzzySets = new FrameShowFS();
                fuzzySets.setHelp(help, "fs");
            }
            if (fuzzySets.isEmpty()) {
                fuzzySets.setPartitions(nefclass.getPartitions());
            }
            fuzzySets.setVisible(true);
            return;
        } else {
            message.showMessage("Attention!", "Please specify a NEFCLASS system.", "There is no NEFCLASS system. Please create a new project or load one from a file"
                    + ". The commands can be found in the Project menu."
            );
            return;
        }
    }

    void menuItemViewStatistics_ActionPerformed(ActionEvent actionevent) {
        if (dataTable != null) {
            if (dataTable.isComplete()) {
                if (statistics == null) {
                    statistics = new FrameStatistics();
                    statistics.setHelp(help, "st");
                }
                statistics.textAreaStatistics.setText("");
                statistics.setTitle("Loading Statistics of " + dataTable.filename + ", please wait...");
                statistics.setVisible(true);
                statistics.textAreaStatistics.setVisible(false);
                dataTable.writeStatistics(statistics.textAreaStatistics);
                statistics.textAreaStatistics.setVisible(true);
                statistics.setTitle("Statistics of " + dataTable.filename);
                return;
            } else {
                message.showMessage("Attention!", "Please wait until data is loaded completely.", "A data file is currently being loaded. Please invoke this command again, after l"
                        + "oading is complete."
                );
                return;
            }
        } else {
            message.showMessage("Attention!", "Please load training data.", "No training data was loaded until now. To use this command, please load a data f"
                    + "ile via the Project dialog or via the Project menu."
            );
            return;
        }
    }

    void menuItemViewAppStatistics_ActionPerformed(ActionEvent actionevent) {
        if (appData != null) {
            if (appData.isComplete()) {
                if (appStatistics == null) {
                    appStatistics = new FrameStatistics();
                    appStatistics.setHelp(help, "st");
                }
                appStatistics.textAreaStatistics.setText("");
                appStatistics.setTitle("Loading Statistics of " + appData.filename + ", please wait...");
                appStatistics.setVisible(true);
                appStatistics.textAreaStatistics.setVisible(false);
                appData.writeStatistics(appStatistics.textAreaStatistics);
                appStatistics.textAreaStatistics.setVisible(true);
                appStatistics.setTitle("Statistics of " + dataTable.filename);
                return;
            } else {
                message.showMessage("Attention!", "Please wait until data is loaded completely.", "A data file is currently being loaded. Please invoke this command again, after l"
                        + "oading is complete."
                );
                return;
            }
        } else {
            message.showMessage("Attention!", "Please load training data.", "No training data was loaded until now. To use this command, please load a data f"
                    + "ile via the Project dialog or via the Project menu."
            );
            return;
        }
    }

    public void setupNefclassInterface() {
        if (nefclass != null) {
            nefclass.addActionListener(lSymAction);
            if (ruleLearning == null) {
                ruleLearning = new RuleLearningProgressDialog();
                ruleLearning.setHelp(help, "rl");
                monoFont = new Font("Monospaced", 0, ruleLearning.statusTextArea.getFont().getSize());
                ruleLearning.statusTextArea.setFont(monoFont);
            }
            if (fsLearning == null) {
                fsLearning = new FuzzySetLearningProgressDialog();
                fsLearning.setHelp(help, "fsl");
                int i = fsLearning.pane.getSize().width - 25;
                functionPanel = new FunctionLayoutPanel(1, i, (9 * i) / 16);
                fsLearning.pane.add(functionPanel);
                fsLearning.pane.validate();
            }
            nefclass.setInterface(ruleLearning.antecedentProgressBar, ruleLearning.consequentProgressBar, ruleLearning.selectProgressBar, fsLearning.bar, ruleLearning.statusTextArea, fsLearning.statusTextArea, ruleLearning.statusTextArea, functionPanel);
            if (!logging) {
                newLogFile();
            }
        }
    }

    void menuItemCreateClassifier_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (dataTable != null) {
            if (dataTable.isComplete()) {
                try {
                    if (nefclass == null) {
                        parameter.setupNefclass(dataTable, appData);
                        setupNefclassInterface();
                    }
                    showTrainingFrames();
                    nefclass.learning(true, true);
                    fuzzySetsTrained = true;
                    newProject = false;
                    projectSaved = false;
                    return;
                } catch (NefclassInvalidException nefclassinvalidexception) {
                    message.showMessage("Attention!", "It is not possible to create the classifier.", "The creation of a new classifier object failed. There is probably something wron"
                            + "g with the training data: "
                            + nefclassinvalidexception.getMessage());
                }
                return;
            } else {
                message.showMessage("Attention!", "Please wait until data is loaded completely.", "A data file is currently being loaded. Please invoke this command again, after l"
                        + "oading is complete."
                );
                return;
            }
        } else {
            message.showMessage("Attention!", "Please load training data.", "No training data was loaded until now. To use this command, please load a data f"
                    + "ile via the Project dialog or via the Project menu."
            );
            return;
        }
    }

    void menuItemCreateRuleBase_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (dataTable != null) {
            if (dataTable.isComplete()) {
                try {
                    if (nefclass == null) {
                        parameter.setupNefclass(dataTable, appData);
                        setupNefclassInterface();
                    }
                    showRuleLearningFrame();
                    nefclass.learning(true, false);
                    newProject = false;
                    projectSaved = false;
                    return;
                } catch (NefclassInvalidException nefclassinvalidexception) {
                    message.showMessage("Attention!", "It is not possible to create the classifier.", "The creation of a new classifier object failed. There is probably something wron"
                            + "g with the training data: "
                            + nefclassinvalidexception.getMessage());
                }
                return;
            } else {
                message.showMessage("Attention!", "Please wait until data is loaded completely.", "A data file is currently being loaded. Please invoke this command again, after l"
                        + "oading is complete."
                );
                return;
            }
        } else {
            message.showMessage("Attention!", "Please load training data.", "No training data was loaded until now. To use this command, please load a data f"
                    + "ile via the Project dialog or via the Project menu."
            );
            return;
        }
    }

    void menuItemTrainFS_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (dataTable != null) {
            if (dataTable.isComplete()) {
                try {
                    if (nefclass == null) {
                        parameter.setupNefclass(dataTable, appData);
                        setupNefclassInterface();
                    }
                    showFuzzySetLearningFrame();
                    fuzzySetsTrained = true;
                    nefclass.learning(false, true);
                    newProject = false;
                    projectSaved = false;
                    return;
                } catch (NefclassInvalidException nefclassinvalidexception) {
                    message.showMessage("Attention!", "It is not possible to create the classifier.", "The creation of a new classifier object failed. There is probably something wron"
                            + "g with the training data: "
                            + nefclassinvalidexception.getMessage());
                }
                return;
            } else {
                message.showMessage("Attention!", "Please wait until data is loaded completely.", "A data file is currently being loaded. Please invoke this command again, after l"
                        + "oading is complete."
                );
                return;
            }
        } else {
            message.showMessage("Attention!", "Please load training data.", "No training data was loaded until now. To use this command, please load a data f"
                    + "ile via the Project dialog or via the Project menu."
            );
            return;
        }
    }

    void menuItemCreateAndPrune_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (dataTable != null) {
            if (dataTable.isComplete()) {
                try {
                    if (nefclass == null) {
                        parameter.setupNefclass(dataTable, appData);
                        setupNefclassInterface();
                    }
                    showTrainingFrames();
                    nefclass.createAndPrune();
                    newProject = false;
                    projectSaved = false;
                    return;
                } catch (NefclassInvalidException nefclassinvalidexception) {
                    message.showMessage("Attention!", "It is not possible to create the classifier.", "The creation of a new classifier object failed. There is probably something wron"
                            + "g with the training data: "
                            + nefclassinvalidexception.getMessage());
                }
                return;
            } else {
                message.showMessage("Attention!", "Please wait until data is loaded completely.", "A data file is currently being loaded. Please invoke this command again, after l"
                        + "oading is complete."
                );
                return;
            }
        } else {
            message.showMessage("Attention!", "Please load training data.", "No training data was loaded until now. To use this command, please load a data f"
                    + "ile via the Project dialog or via the Project menu."
            );
            return;
        }
    }

    void menuItemCreatePrunedClassifier_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (dataTable != null) {
            if (dataTable.isComplete()) {
                try {
                    if (nefclass == null) {
                        parameter.setupNefclass(dataTable, appData);
                        setupNefclassInterface();
                    }
                    showTrainingFrames();
                    nefclass.mixedCreateAndPrune();
                    newProject = false;
                    projectSaved = false;
                    return;
                } catch (NefclassInvalidException nefclassinvalidexception) {
                    message.showMessage("Attention!", "It is not possible to create the classifier.", "The creation of a new classifier object failed. There is probably something wron"
                            + "g with the training data: "
                            + nefclassinvalidexception.getMessage());
                }
                return;
            } else {
                message.showMessage("Attention!", "Please wait until data is loaded completely.", "A data file is currently being loaded. Please invoke this command again, after l"
                        + "oading is complete."
                );
                return;
            }
        } else {
            message.showMessage("Attention!", "Please load training data.", "No training data was loaded until now. To use this command, please load a data f"
                    + "ile via the Project dialog or via the Project menu."
            );
            return;
        }
    }

    void menuItemStopTraining_ActionPerformed(ActionEvent actionevent) {
        if (nefclass != null) {
            if (nefclass.isRunning()) {
                nefclass.stopTraining();
                message.showMessage("Attention!", "The learning process was stopped.", "The training process of the classifier was interrupted. To continue the learning"
                        + " process use one of the first three commands of the 'Classifier' menu."
                );
                return;
            } else {
                message.showMessage("Attention!", "There is no learning process.", "The classifier is currently not trained. Use this command to stop an active lear"
                        + "ning process. To start a learning process use one of the first three commands of"
                        + " the 'Classifier' menu."
                );
                return;
            }
        } else {
            message.showMessage("Attention!", "Please specify a NEFCLASS system.", "There is no NEFCLASS system. Please create a new project or load one from a file"
                    + ". The commands can be found in the Project menu."
            );
            return;
        }
    }

    void menuItemClassifyData_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (dataTable != null) {
            if (dataTable.isComplete()) {
                if (nefclass != null) {
                    if (nefclass.getNumberOfRules() > 0) {
                        if (training == null) {
                            training = new FrameStatistics();
                            training.setHelp(help, "cl");
                        }
                        training.setTitle("Classifying training data, please wait...");
                        training.textAreaStatistics.setText("");
                        training.setVisible(true);
                        if (monoFont == null) {
                            monoFont = new Font("Monospaced", 0, training.textAreaStatistics.getFont().getSize());
                        }
                        training.textAreaStatistics.setVisible(false);
                        training.textAreaStatistics.setFont(monoFont);
                        nefclass.classifyData(training.textAreaStatistics);
                        training.setTitle("Classification Result for the Training Data");
                        training.textAreaStatistics.setVisible(true);
                        return;
                    } else {
                        message.showMessage("Attention!", "Please create a rule base.", "The current NEFCLASS system has no rule base. You must either invoke rule learni"
                                + "ng or enter rule manually via the rule editor."
                        );
                        return;
                    }
                } else {
                    message.showMessage("Attention!", "Please specify a NEFCLASS system.", "There is no NEFCLASS system. Please create a new project or load one from a file"
                            + ". The commands can be found in the Project menu."
                    );
                    return;
                }
            } else {
                message.showMessage("Attention!", "Please wait until data is loaded completely.", "A data file is currently being loaded. Please invoke this command again, after l"
                        + "oading is complete."
                );
                return;
            }
        } else {
            message.showMessage("Attention!", "Please load training data.", "No training data was loaded until now. To use this command, please load a data f"
                    + "ile via the Project dialog or via the Project menu."
            );
            return;
        }
    }

    void menuItemClassifyAppData_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (appData != null) {
            if (appData.isComplete()) {
                if (nefclass != null) {
                    if (nefclass.getNumberOfRules() > 0) {
                        if (application == null) {
                            application = new FrameStatistics();
                            application.setHelp(help, "cl");
                        }
                        application.setTitle("Classifying application data, please wait...");
                        application.textAreaStatistics.setText("");
                        application.setVisible(true);
                        if (monoFont == null) {
                            monoFont = new Font("Monospaced", 0, application.textAreaStatistics.getFont().getSize());
                        }
                        application.textAreaStatistics.setVisible(false);
                        application.textAreaStatistics.setFont(monoFont);
                        nefclass.classifyApplicationData(application.textAreaStatistics);
                        application.setTitle("Classification Result for the Application Data");
                        application.textAreaStatistics.setVisible(true);
                        return;
                    } else {
                        message.showMessage("Attention!", "Please create a rule base.", "The current NEFCLASS system has no rule base. You must either invoke rule learni"
                                + "ng or enter rule manually via the rule editor."
                        );
                        return;
                    }
                } else {
                    message.showMessage("Attention!", "Please specify a NEFCLASS system.", "There is no NEFCLASS system. Please create a new project or load one from a file"
                            + ". The commands can be found in the Project menu."
                    );
                    return;
                }
            } else {
                message.showMessage("Attention!", "Please wait until data is loaded completely.", "A data file is currently being loaded. Please invoke this command again, after l"
                        + "oading is complete."
                );
                return;
            }
        } else {
            message.showMessage("Attention!", "Please load application data.", "No application data was loaded until now. To use this command, please load a dat"
                    + "a file via the Project menu."
            );
            return;
        }
    }

    public void showTrainingFrames() {
        showFuzzySetLearningFrame();
        showRuleLearningFrame();
    }

    public void showFuzzySetLearningFrame() {
        fsLearning.statusTextArea.setText("");
        functionPanel.removeAll();
        fsLearning.setVisible(true);
    }

    public void showRuleLearningFrame() {
        ruleLearning.statusTextArea.setText("");
        try {
            ruleLearning.antecedentProgressBar.setProgressPercent(0);
            ruleLearning.consequentProgressBar.setProgressPercent(0);
            ruleLearning.selectProgressBar.setProgressPercent(0);
            fsLearning.bar.setProgressPercent(0);
        } catch (PropertyVetoException _ex) {
        }
        ruleLearning.setVisible(true);
    }

    public void closeAllNefclassFrames() {
        if (fsLearning != null) {
            fsLearning.statusTextArea.setText("");
            fsLearning.setVisible(false);
        }
        if (fuzzySets != null) {
            fuzzySets.setVisible(false);
            fuzzySets.reset();
        }
        if (functionPanel != null) {
            functionPanel.removeAll();
        }
        if (ruleLearning != null) {
            ruleLearning.statusTextArea.setText("");
            try {
                ruleLearning.antecedentProgressBar.setProgressPercent(0);
                ruleLearning.consequentProgressBar.setProgressPercent(0);
                ruleLearning.selectProgressBar.setProgressPercent(0);
                fsLearning.bar.setProgressPercent(0);
            } catch (PropertyVetoException _ex) {
            }
            ruleLearning.setVisible(false);
        }
        if (statistics != null) {
            statistics.textAreaStatistics.setText("");
            statistics.setVisible(false);
        }
        if (appStatistics != null) {
            appStatistics.textAreaStatistics.setText("");
            appStatistics.setVisible(false);
        }
        if (training != null) {
            training.textAreaStatistics.setText("");
            training.setVisible(false);
        }
        if (application != null) {
            application.textAreaStatistics.setText("");
            application.setVisible(false);
        }
    }

    public void hideAll() {
        if (fsLearning != null) {
            fsLearning.setVisible(false);
        }
        if (fuzzySets != null) {
            fuzzySets.setVisible(false);
        }
        if (ruleLearning != null) {
            ruleLearning.setVisible(false);
        }
        if (statistics != null) {
            statistics.setVisible(false);
        }
        if (appStatistics != null) {
            appStatistics.setVisible(false);
        }
        if (training != null) {
            training.setVisible(false);
        }
        if (application != null) {
            application.setVisible(false);
        }
    }

    public void showAll() {
        if (fsLearning != null && fuzzySetsTrained) {
            fsLearning.setVisible(true);
        }
        if (fuzzySets != null && nefclass != null) {
            fuzzySets.setVisible(true);
        }
        if (ruleLearning != null && nefclass != null) {
            ruleLearning.setVisible(true);
        }
        if (statistics != null && dataTable != null) {
            statistics.setVisible(true);
        }
        if (appStatistics != null && appData != null) {
            appStatistics.setVisible(true);
        }
        if (training != null && nefclass != null && dataTable != null) {
            training.setVisible(true);
        }
        if (application != null && nefclass != null && appData != null) {
            application.setVisible(true);
        }
    }

    void menuItemRestoreFuzzySets_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (nefclass != null) {
            nefclass.restorePartitions();
            if (fuzzySets != null && fuzzySets.isVisible()) {
                fuzzySets.scrollPaneShowFS.getComponent(0).repaint();
            }
            message.showMessage("Information", "The fuzzy sets were restored to their previous state.", "The current state of the fuzzy sets was saved and the fuzzy partitions of the cl"
                    + "assifier were restored to the previously saved state. Use this command again to "
                    + "restore the saved copy of the fuzzy sets."
            );
        }
        setStatus("ready");
    }

    void menuItemResetFuzzySets_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (nefclass != null && dataTable != null) {
            yesNoDialog.showMessage("Are You Sure?", "The fuzzy sets will be reset to their initial state.", "You are about to reset the fuzzy partitions of the current classifier to an init"
                    + "ial state of equally distributed fuzzy sets. You can return the current state of"
                    + " fuzzy sets by using 'Restore Fuzzy Sets' from the 'Classifier' menu. Continue?"
            );
            if (yesNoDialog.closedWithYes()) {
                try {
                    nefclass.initializePartitions();
                    if (fuzzySets != null && fuzzySets.isVisible()) {
                        fuzzySets.scrollPaneShowFS.getComponent(0).repaint();
                    }
                } catch (NefclassInvalidException _ex) {
                    message.showMessage("Internal Error!", "Please check the validity of all parameters.", "Sorry, this error shouldn't happen. The current NEFCLASS system is invalid, prob"
                            + "ably due to a bug in the software. Please try again, after checking all NEFCLASS"
                            + " parameters. Please consider to submit a bug report."
                    );
                }
            }
        }
        setStatus("ready");
    }

    void menuItemViewError_ActionPerformed(ActionEvent actionevent) {
        if (fuzzySetsTrained) {
            fsLearning.setVisible(true);
            return;
        } else {
            message.showMessage("Attention!", "The classifier was not trained yet.", "There was no training process invoked until now. To train a classifier load trai"
                    + "ning data and then use a command from the classifer menu."
            );
            return;
        }
    }

    void menuItemRestoreRuleBase_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (nefclass != null) {
            if (nefclass.restoreRuleBase()) {
                message.showMessage("Information", "The previous rule base was restored.", "The rule base of the current classifier was saved and restored to the state befo"
                        + "re the last rule editor session, rule base learning cycle, or restore command. U"
                        + "se 'Restore Rule Base' again to go back to the saved copy of the rule base."
                );
            } else {
                message.showMessage("Attention!", "No rule base backup available.", "The rule base could not be restored. There probably was no backup of the rule ba"
                        + "se, because the previous rule base was empty."
                );
            }
        } else {
            message.showMessage("Attention!", "Please specify a NEFCLASS system.", "There is no NEFCLASS system. Please create a new project or load one from a file"
                    + ". The commands can be found in the Project menu."
            );
        }
        setStatus("ready");
    }

    public void setStatus(String s) {
        textStatus.setText(s);
        if (dataTable != null) {
            int i = dataTable.filename.lastIndexOf("/");
            if (i < 0) {
                i = dataTable.filename.lastIndexOf("\\");
            }
            String s1;
            if (i > 0) {
                s1 = dataTable.filename.substring(i + 1);
            } else {
                s1 = dataTable.filename;
            }
            textTrainingData.setText(s1);
        } else {
            textTrainingData.setText("not available");
        }
        if (appData != null) {
            int j = appData.filename.lastIndexOf("/");
            if (j < 0) {
                j = appData.filename.lastIndexOf("\\");
            }
            String s2;
            if (j > 0) {
                s2 = appData.filename.substring(j + 1);
            } else {
                s2 = appData.filename;
            }
            textApplicationData.setText(s2);
        } else {
            textApplicationData.setText("not available");
        }
        if (nefclass != null) {
            String s3 = nefclass.getNumberOfRules() + " " + "rules";
            if (fuzzySetsTrained) {
                s3 = s3 + ", " + "trained";
            }
            textClassifier.setText(s3);
        } else {
            textClassifier.setText("not available");
        }
        if (projectSaved) {
            textProject.setText("not modified");
            return;
        } else {
            textProject.setText("modified");
            return;
        }
    }

    void menuItemViewHideAll_ActionPerformed(ActionEvent actionevent) {
        hideAll();
    }

    void menuItemViewShowAll_ActionPerformed(ActionEvent actionevent) {
        showAll();
    }

    void RulesPruning_ActionPerformed(ActionEvent actionevent) {
        if (checkRunning()) {
            return;
        }
        if (dataTable != null) {
            if (dataTable.isComplete()) {
                try {
                    if (nefclass == null) {
                        parameter.setupNefclass(dataTable, appData);
                        setupNefclassInterface();
                    }
                    if (nefclass.getNumberOfRules() > 0) {
                        showTrainingFrames();
                        fuzzySetsTrained = true;
                        nefclass.automaticPruning();
                        newProject = false;
                        projectSaved = false;
                        return;
                    } else {
                        message.showMessage("Attention!", "Please create a rule base.", "The current NEFCLASS system has no rule base. You must either invoke rule learni"
                                + "ng or enter rule manually via the rule editor."
                        );
                        return;
                    }
                } catch (NefclassInvalidException nefclassinvalidexception) {
                    message.showMessage("Attention!", "It is not possible to create the classifier.", "The creation of a new classifier object failed. There is probably something wron"
                            + "g with the training data: "
                            + nefclassinvalidexception.getMessage());
                }
                return;
            } else {
                message.showMessage("Attention!", "Please wait until data is loaded completely.", "A data file is currently being loaded. Please invoke this command again, after l"
                        + "oading is complete."
                );
                return;
            }
        } else {
            message.showMessage("Attention!", "Please load training data.", "No training data was loaded until now. To use this command, please load a data f"
                    + "ile via the Project dialog or via the Project menu."
            );
            return;
        }
    }

    void RulesExport_ActionPerformed(ActionEvent actionevent) {
        if (nefclass != null) {
            if (nefclass.getNumberOfRules() > 0) {
                FileDialog filedialog = new FileDialog(this, "Export Rule Base to a Text File", 1);
                filedialog.setFile("*.txt");
                filedialog.setVisible(true);
                String s1 = filedialog.getDirectory();
                String s = filedialog.getFile();
                try {
                    BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(s1 + s));
                    DateFormat dateformat = DateFormat.getDateTimeInstance(1, 1, Locale.US);
                    dateformat.setTimeZone(TimeZone.getDefault());
                    String s2 = dateformat.format(new Date());
                    bufferedwriter.write("% NEFCLASS rule file file created by ");
                    bufferedwriter.write("NEFCLASS-J");
                    bufferedwriter.write(" 1.0");
                    bufferedwriter.write(" (c) Ulrike Nauck, Braunschweig, 1999");
                    bufferedwriter.newLine();
                    bufferedwriter.write("% Filename: " + s1 + s);
                    bufferedwriter.newLine();
                    bufferedwriter.write("% This file was created at " + s2);
                    bufferedwriter.newLine();
                    bufferedwriter.newLine();
                    bufferedwriter.write(nefclass.toString());
                    bufferedwriter.newLine();
                    bufferedwriter.close();
                    return;
                } catch (IOException ioexception) {
                    message.showMessage("Write Error!", "Please check the filename.", "The file could not be written. Please make sure that the specified filename is v"
                            + "alid and that the selected directory is accessible. The System return the follow"
                            + "ing error message: "
                            + ioexception.getMessage());
                }
                return;
            } else {
                message.showMessage("Attention!", "Please create a rule base.", "The current NEFCLASS system has no rule base. You must either invoke rule learni"
                        + "ng or enter rule manually via the rule editor."
                );
                return;
            }
        } else {
            message.showMessage("Attention!", "Please specify a NEFCLASS system.", "There is no NEFCLASS system. Please create a new project or load one from a file"
                    + ". The commands can be found in the Project menu."
            );
            return;
        }
    }

    public void newLogFile() {
        try {
            if (logging) {
                logFile.close();
            }
            if (nefclass != null) {
                logFile = new PrintWriter(new FileOutputStream("Nefclass.log"));
                DateFormat dateformat = DateFormat.getDateTimeInstance(1, 1, Locale.US);
                dateformat.setTimeZone(TimeZone.getDefault());
                String s = dateformat.format(new Date());
                logFile.print("% NEFCLASS log file file created by ");
                logFile.print("NEFCLASS-J");
                logFile.print(" 1.0");
                logFile.println(" (c) Ulrike Nauck, Braunschweig, 1999");
                logFile.println("% This file was created at " + s);
                logFile.println();
                logging = true;
                nefclass.setLogFile(logFile);
                nefclass.setLogging(logging);
                return;
            } else {
                logging = false;
                return;
            }
        } catch (IOException _ex) {
            logging = false;
        }
    }

    public void closeLogFile() {
        if (logging) {
            logFile.close();
            logging = false;
        }
    }

    public void flushLogFile() {
        if (logging) {
            logFile.flush();
        }
    }

    public boolean checkRunning() {
        boolean flag = false;
        if (nefclass != null && nefclass.isRunning()) {
            message.showMessage("Attention!", "There is an active learning process.", "The classifier is currently being trained. You cannot use this commannd before t"
                    + "he learning process has terminated.To stop the learning process you can use the "
                    + "'Stop Learning' command from the 'Classifier' menu."
            );
            flag = true;
        }
        return flag;
    }

    protected String getFile(String s, String s1, String s2) {
        String s3;
        if (s != null) {
            if (environment.getProperty(s) != null) {
                File file = new File(environment.getProperty(s));
                if (!file.exists() || !file.canRead() || !file.isFile()) {
                    s3 = null;
                } else {
                    s3 = file.getPath();
                }
            } else {
                s3 = null;
            }
        } else {
            File file1 = new File(environment.getProperty("user.home") + environment.getProperty("file.separator") + s1, s2);
            if (!file1.exists() || !file1.canRead() || !file1.isFile()) {
                file1 = new File(environment.getProperty("user.dir"), s2);
                if (!file1.exists() || !file1.canRead() || !file1.isFile()) {
                    s3 = null;
                } else {
                    s3 = file1.getPath();
                }
            } else {
                s3 = file1.getPath();
            }
        }
        return s3;
    }

    protected void readConfigFile() {
        Object obj = null;
        Object obj1 = null;
        try {
            BufferedReader bufferedreader = new BufferedReader(new FileReader(configFile));
            for (String s = bufferedreader.readLine(); s != null; s = bufferedreader.readLine()) {
                String s1 = s.toUpperCase();
                if (s1.startsWith("BROWSER")) {
                    browser = bufferedreader.readLine();
                }
                if (s1.startsWith("HELPFILE")) {
                    helpFile = bufferedreader.readLine();
                }
            }

            return;
        } catch (IOException _ex) {
            message.showMessage("Read Error!", "An I/O error occured while reading the configuration file.", "NEFCLASS-J was not able to read the configuration file. You may not be able to d"
                    + "isplay the help information. Please check the location and format of the configu"
                    + "ration file. NEFCLASS-J tried to read "
                    + configFile);
        }
    }

    protected void setupEnvironment() {
        helpFile = null;
        browser = null;
        environment = System.getProperties();
        if (environment.getProperty("config") != null) {
            configFile = getFile("config", "nefclass", "Nefclass.cfg");
        } else {
            configFile = getFile(null, "nefclass", "Nefclass.cfg");
        }
        if (configFile != null) {
            readConfigFile();
        } else {
            message.showMessage("Read Error!", "The configuration file was not found.", "NEFCLASS-J was not able to find the configuration file. The resason for this can"
                    + " be that the configuration file is missing or that you have provided an invalid "
                    + "name in a command line option. You may not be able to display the help informati"
                    + "on. Please refer to the readme.txt file of this distribution for more informatio"
                    + "n. "
            );
        }
        if (environment.getProperty("help") != null) {
            helpFile = getFile("help", "nefclass", "NefclassJHelp.html");
            if (helpFile == null) {
                message.showMessage("Read Error!", "NEFCLASS-J cannot read the help file.", "NEFCLASS-J was not able to read the help file specified in the configuration fil"
                        + "e and will now try to locate the default help file. The error was caused by this"
                        + " file name: "
                        + environment.getProperty("help"));
            }
        } else {
            if (helpFile != null) {
                File file = new File(helpFile);
                if (!file.exists() || !file.canRead() || !file.isFile()) {
                    message.showMessage("Read Error!", "NEFCLASS-J cannot read the help file.", "NEFCLASS-J was not able to read the help file. You will not be able to display t"
                            + "he help information. Please check the location of the help file."
                            + helpFile);
                    helpFile = null;
                }
            }
        }
        if (helpFile == null) {
            helpFile = getFile(null, "nefclass", "doc" + environment.getProperty("file.separator") + "NefclassJHelp.html");
            if (helpFile == null) {
                message.showMessage("Read Error!", "NEFCLASS-J cannot read the help file.", "NEFCLASS-J was not able to read the help file. You will not be able to display t"
                        + "he help information. Please check the location of the help file."
                );
                displayHelp = false;
            }
        }
        if (environment.getProperty("browser") != null) {
            browser = environment.getProperty("browser");
        }
        if (browser == null) {
            displayHelp = false;
        }
        if (displayHelp) {
            help = new Help(browser, helpFile);
            projectSpecification.setHelp(help, "projectdialog");
        }
        System.out.println("Config file:     " + configFile);
        System.out.println("Help file:       " + helpFile);
        System.out.println("Browser command: " + browser);
    }

    void HelpTopics_ActionPerformed(ActionEvent actionevent) {
        if (displayHelp) {
            if (help == null) {
                help = new Help(browser, helpFile);
            }
            try {
                help.show();
            } catch (HelpNotAvailableException _ex) {
                displayHelp = false;
            }
        }
        if (!displayHelp) {
            message.showMessage("Attention!", "NEFCLASS-J cannot display help file.", "NEFCLASS-J cannot display the help file. The reason can be that the file is miss"
                    + "ing, or that no WWW browser could be started. To display the help file manually,"
                    + " please locate the file NefclassJHelp.htmlthat was included in the distribution "
                    + "and open it in a WWW browser like, for instance, NETSCAPE."
            );
        }
    }
}
