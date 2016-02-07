package nauck.main;


public class Resources
{

    static final String programTitle = "NEFCLASS-J";
    static final String versionString = "1.0";
    static final String dateString = "January 15, 1999";
    static final String copyright = "(c) Ulrike Nauck, Braunschweig, 1999";
    static final String projectFileType = ".prj";
    static final String projectWildcard = "*.prj";
    static final String nefclassFileType = ".cls";
    static final String nefclassWildcard = "*.cls";
    static final String dataFileType = ".dat";
    static final String dataWildcard = "*.dat";
    static final String textWildcard = "*.txt";
    static final String logFilename = "Nefclass.log";
    static final String resultFilename = "result.log";
    static final String configFilename = "Nefclass.cfg";
    static final String configProperty = "config";
    static final String helpFilename = "NefclassJHelp.html";
    static final String helpDir = "doc";
    static final String helpProperty = "help";
    static final String nefclassDir = "nefclass";
    static final String browserProperty = "browser";
    static final String attentionTitle = "Attention!";
    static final String informationTitle = "Information";
    static final String readErrorTitle = "Read Error!";
    static final String writeErrorTitle = "Write Error!";
    static final String internalBugTitle = "Internal Error!";
    static final String ruleBaseErrorTitle = "Rule Base Error!";
    static final String ruleBaseOKTitle = "Rule Base Accepted!";
    static final String areYouSureTitle = "Are You Sure?";
    static final String checkFilenameMsg = "Please check the filename.";
    static final String pleaseWaitMsg = "Please wait until data is loaded completely.";
    static final String writeFileErrorMsg = "The file could not be written. Please make sure that the specified filename is v" +
"alid and that the selected directory is accessible. The System return the follow" +
"ing error message: "
;
    static final String dataNotReadyMsg = "A data file is currently being loaded. Please invoke this command again, after l" +
"oading is complete."
;
    static final String loadDataMsg = "Please load training data.";
    static final String loadAppDataMsg = "Please load application data.";
    static final String dataNotAvailableMsg = "No training data was loaded until now. To use this command, please load a data f" +
"ile via the Project dialog or via the Project menu."
;
    static final String appDataNotAvailableMsg = "No application data was loaded until now. To use this command, please load a dat" +
"a file via the Project menu."
;
    static final String trainFirstMsg = "Please create a rule base.";
    static final String noRulesMsg = "The current NEFCLASS system has no rule base. You must either invoke rule learni" +
"ng or enter rule manually via the rule editor."
;
    static final String defineNefclassMsg = "Please specify a NEFCLASS system.";
    static final String noNefclassMsg = "There is no NEFCLASS system. Please create a new project or load one from a file" +
". The commands can be found in the Project menu."
;
    static final String recheckParametersMsg = "Please check the validity of all parameters.";
    static final String internalBugMsg = "Sorry, this error shouldn't happen. The current NEFCLASS system is invalid, prob" +
"ably due to a bug in the software. Please try again, after checking all NEFCLASS" +
" parameters. Please consider to submit a bug report."
;
    static final String projectFileMsg = "Please check the project filename.";
    static final String nefclassNotCreatedMsg = "It is not possible to create the classifier.";
    static final String nefclassIOErrorMsg = "The classifier of this project is missing.";
    static final String nefclassParseErrorMsg = "The classifier of this project is invalid.";
    static final String nefclassWriteErrorMsg = "An I/O error occured while trying to save the classifier. Please make sure that " +
"the filename you specified for the project is valid and that the destination dri" +
"ve is accessible."
;
    static final String nefclassReadErrorMsg = "An I/O error occured while trying to read the classifier of this project. A new " +
"classifier with empty rule base was created instead.Please make sure that a clas" +
"sifier file resides in the same diretory as its project file. "
;
    static final String nefclassCreateErrorMsg = "The creation of a new classifier object failed. There is probably something wron" +
"g with the training data: "
;
    static final String invalidNefclassFileMsg = "The classifier file of this project is not valid. A new classifier with empty ru" +
"le base will be created instead.The reason can be that the file does not contain" +
" a classifier or that it was corrupted in some way: "
;
    static final String notRunningMsg = "There is no learning process.";
    static final String noLearningProcessMsg = "The classifier is currently not trained. Use this command to stop an active lear" +
"ning process. To start a learning process use one of the first three commands of" +
" the 'Classifier' menu."
;
    static final String learningStoppedMsg = "The learning process was stopped.";
    static final String trainingInterruptedMsg = "The training process of the classifier was interrupted. To continue the learning" +
" process use one of the first three commands of the 'Classifier' menu."
;
    static final String nefclassIsRunningMsg = "There is an active learning process.";
    static final String stopNefclassFirstMsg = "The classifier is currently being trained. You cannot use this commannd before t" +
"he learning process has terminated.To stop the learning process you can use the " +
"'Stop Learning' command from the 'Classifier' menu."
;
    static final String projectIOErrorMsg = "Please choose a valid project filename.";
    static final String projectParseErrorMsg = "Please choose another project.";
    static final String closeProjectMsg = "Please close the current project first.";
    static final String projectNotSavedMsg = "The project has changed, do you want to save it?";
    static final String projectWriteErrorMsg = "An I/O error occured while trying to save the project. Please make sure that the" +
" filename you specified for the project is valid and that the destination drive " +
"is accessible."
;
    static final String projectReadErrorMsg = "An I/O error occured while trying to read the project file. Please make sure tha" +
"t the filename you specified for the project is valid and that the file is acces" +
"sible."
;
    static final String invalidProjectFileMsg = "Please select another file. The specified file does not contain a valid project." +
" This can mean that the file is no project file or that it was corrupted in some" +
" way. "
;
    static final String onlyOneProjectMsg = "You can only work with one NEFCLASS-Project at a time. The existing project can " +
"be changed with 'Project/Edit Project' or closed with 'Project/Close Project'."
;
    static final String projectSaveBeforeCloseMsg = "The current project state was not saved. Please click on the 'Save-button' to in" +
"voke a 'Save As Dialog'. Clicking on the 'Close-button' will close the project w" +
"ithout saving it."
;
    static final String dataIOErrorMsg = "The data file cannot be read.";
    static final String dataParseErrorMsg = "The format of data file is invalid.";
    static final String dataDoesNotFitMsg = "The data file cannot be used by the current classifier.";
    static final String dataNotWrittenMsg = "The data file could not be saved completely.";
    static final String dataWriteErrorMsg = "An I/O error occured while trying to save the data. Please make sure that the fi" +
"lename you specified for the data is valid and that the destination drive is acc" +
"essible. "
;
    static final String dataReadErrorMsg = "An I/O error occured while trying to read a data file for the project. Please ma" +
"ke sure that the data file exists and that the correct data filename is specifie" +
"d in the project dialog. "
;
    static final String invalidDataFileMsg = "Please load another data file for this project. The format of the data file is n" +
"ot valid: "
;
    static final String trainClassifierMsg = "The classifier was not trained yet.";
    static final String noTrainingProcessMsg = "There was no training process invoked until now. To train a classifier load trai" +
"ning data and then use a command from the classifer menu."
;
    static final String invalidRuleBaseMsg = "Please correct the rule base.";
    static final String ruleBaseErrorMsg = "The rule base contains errors and cannot be saved. Please correct the following " +
"problem: "
;
    static final String ruleBaseSavedMsg = "The rule base was accepted.";
    static final String ruleBaseBackupMsg = "The rule base was accepted and is now used by the classifier. If you need to res" +
"tore the previous rule base use the entry 'Restore Rule Base' from the 'Rule' me" +
"nu."
;
    static final String restoreRulesMsg = "The previous rule base was restored.";
    static final String explainRestoreMsg = "The rule base of the current classifier was saved and restored to the state befo" +
"re the last rule editor session, rule base learning cycle, or restore command. U" +
"se 'Restore Rule Base' again to go back to the saved copy of the rule base."
;
    static final String noRestoreRulesMsg = "No rule base backup available.";
    static final String noRuleBaseBackupMsg = "The rule base could not be restored. There probably was no backup of the rule ba" +
"se, because the previous rule base was empty."
;
    static final String resetFuzzySetsMsg = "The fuzzy sets will be reset to their initial state.";
    static final String explainResetMsg = "You are about to reset the fuzzy partitions of the current classifier to an init" +
"ial state of equally distributed fuzzy sets. You can return the current state of" +
" fuzzy sets by using 'Restore Fuzzy Sets' from the 'Classifier' menu. Continue?"
;
    static final String restoreFuzzySetsMsg = "The fuzzy sets were restored to their previous state.";
    static final String explainRestoreFSMsg = "The current state of the fuzzy sets was saved and the fuzzy partitions of the cl" +
"assifier were restored to the previously saved state. Use this command again to " +
"restore the saved copy of the fuzzy sets."
;
    static final String rulesWillBeDeletedMsg = "The rule base will be deleted to use the new fuzzy sets.";
    static final String deleteRulesMsg = "You have specified new parameters for the fuzzy partitions. There is a rule base" +
" that uses the old fuzzy partitions. To use these new parameters, the current ru" +
"le base must be deleted. Continue?"
;
    static final String configReadMsg = "An I/O error occured while reading the configuration file.";
    static final String configReadErrorMsg = "NEFCLASS-J was not able to read the configuration file. You may not be able to d" +
"isplay the help information. Please check the location and format of the configu" +
"ration file. NEFCLASS-J tried to read "
;
    static final String configFileMsg = "The configuration file was not found.";
    static final String configFileNotFoundMsg = "NEFCLASS-J was not able to find the configuration file. The resason for this can" +
" be that the configuration file is missing or that you have provided an invalid " +
"name in a command line option. You may not be able to display the help informati" +
"on. Please refer to the readme.txt file of this distribution for more informatio" +
"n. "
;
    static final String helpReadMsg = "NEFCLASS-J cannot read the help file.";
    static final String helpReadErrorMsg = "NEFCLASS-J was not able to read the help file. You will not be able to display t" +
"he help information. Please check the location of the help file."
;
    static final String helpReadErrorFileMsg = "NEFCLASS-J was not able to read the help file specified in the configuration fil" +
"e and will now try to locate the default help file. The error was caused by this" +
" file name: "
;
    static final String helpErrorMsg = "NEFCLASS-J cannot display help file.";
    static final String howToDisplayHelpMsg = "NEFCLASS-J cannot display the help file. The reason can be that the file is miss" +
"ing, or that no WWW browser could be started. To display the help file manually," +
" please locate the file NefclassJHelp.htmlthat was included in the distribution " +
"and open it in a WWW browser like, for instance, NETSCAPE."
;
    static final String RE_FirstHelp = "To enter a new rule to the rule base, select a variable and a fuzzy set. Then pr" +
"ess the Set button, to add a a new term to the antecedent. When the antecedent i" +
"s complete select a consequent and press Add Rule to create a new rule.\nClick o" +
"n each list to obtain more information.\nTo save your changes, leave with OK, to" +
" discard all modifications, use Cancel."
;
    static final String RE_VariablesHelp = "The variable list displays the names of all available variables. To enter a new " +
"term to the antecedent of the selected rule or to change the term for this varia" +
"ble select an appropriate fuzzy set from the Fuzzy Sets list and press Set."
;
    static final String RE_FuzzySetsHelp = "The Fuzzy Sets list shows all available fuzzy sets of the currently selected var" +
"iable of the Variables list. The content of the list changes, if you select anot" +
"her variable. After selecting a suitable fuzzy set for the selected variable pre" +
"ss Set to enter or modify the linguistic term of the selected variable in the an" +
"tecedent of the selected rule."
;
    static final String RE_AntecedentHelp = "The Antecedent list shows all linguistic terms of the antecedent of the selected" +
" rule. You can remove the selected entry by clicking on Remove Term.Clicking on " +
"Remove All deletes all entries. To add or modify an entry, select the variable c" +
"ontained in the term, select an appropriate fuzzy set and click on Set (see also" +
" the information for the Variables and Fuzzy Sets lists.)"
;
    static final String RE_ConsequentHelp = "The Consquent list shows the names of all available classes. After creating an a" +
"ntecedent select an appropriate class from this list to complete the rule. If yo" +
"u press on Add Rule, a new rule will be added to the end of the rule base. If yo" +
"u click on Modify Rule the currently selected rule will be replaced by the rule " +
"given by the current antecedent and consequent."
;
    static final String RE_AllRulesHelp = "The All Rule list shows the complete rule base. You can delete the selected rule" +
" by pressing Remove Rule. The button Remove All deletes the whole rule base. To " +
"enter new rules create an antecedent, select a consequent and press Add Rule. Pr" +
"ess Modify Rule to replace the selected rule by the rule given by the entries in" +
" Antecedent and Consequent."
;
    static final String noProjectStatus = "no project";
    static final String projectModifiedStatus = "modified";
    static final String projectSavedStatus = "not modified";
    static final String noDataStatus = "not available";
    static final String noAppDataStatus = "not available";
    static final String ruleStatus = "rules";
    static final String trainedStatus = "trained";
    static final String noClassifierStatus = "not available";
    static final String readyStatus = "ready";
    static final String ruleLearningStatus = "rule learning";
    static final String pruningStatus = "pruning";
    static final String trainingStatus = "training";
    static final String classifyingStatus = "classifying";
    static final String projectSpecificationHelp = "projectdialog";
    static final String ruleEditHelp = "re";
    static final String editLabelsHelp = "el";
    static final String specifyFPHelp = "fp";
    static final String statisticsHelp = "st";
    static final String classifyHelp = "cl";
    static final String showFSHelp = "fs";
    static final String fsLearningHelp = "fsl";
    static final String ruleLearningHelp = "rl";

    public Resources()
    {
    }
}
