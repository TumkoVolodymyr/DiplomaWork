/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nauck.fuzzy;

/**
 *
 * @author Volodymyr
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.*;
import java.text.DateFormat;
import java.util.*;
import nauck.data.DataTable;
import nauck.util.*;
import symantec.itools.awt.util.ProgressBar;

// Referenced classes of package nauck.fuzzy:
//            FuzzyClassifier, NefclassInvalidException, Variable, FuzzyClassifierRule, 
//            FuzzySetInvalidException, FuzzyPartition, FuzzyRule, FuzzySystem, 
//            FuzzySet, TNorm

public class Nefclass extends FuzzyClassifier
    implements Runnable
{

    public static final String VERSION = "NEFCLASS-J 1.0";
    public static final String COPYRIGHT = "(c) U. Nauck, Braunschweig, 1999";
    public static final int MIN_RL = 0;
    public static final int RL_SIMPLE = 0;
    public static final int RL_BEST = 1;
    public static final int RL_BEST_PER_CLASS = 2;
    public static final int MAX_RL = 2;
    public static final boolean RB_SIZE_AUTOMATIC = true;
    public static final boolean RB_SIZE_FIXED = false;
    public static final int MIN_VALIDATION = 0;
    public static final int NO_VALIDATION = 0;
    public static final int TEST_VALIDATION = 1;
    public static final int CROSS_VALIDATION = 2;
    public static final int MAX_VALIDATION = 2;
    public static final int MIN_RULE_WEIGHTS = 0;
    public static final int NO_RULE_WEIGHTS = 0;
    public static final int NORMAL_RULE_WEIGHTS = 1;
    public static final int ARBITRARY_RULE_WEIGHTS = 2;
    public static final int MAX_RULE_WEIGHTS = 2;
    public static final String RULELEARN_STATUS = "rule learning";
    public static final String FSETLEARN_STATUS = "training";
    public static final String PRUNING_STATUS = "pruning";
    public static final String CLASSIFY_STATUS = "classifying";
    public static final String READY_STATUS = "ready";
    protected String description;
    protected String classNames[];
    protected int maxRules;
    protected DataTable dataTable;
    protected DataTable applicationData;
    protected Vector ruleBaseCopy;
    protected String consistencyCheckMessage;
    protected Vector ruleBaseBackUp;
    protected FuzzyPartition partitions[];
    protected FuzzyPartition bestPartitions[];
    protected FuzzyPartition initPartitions[];
    protected FuzzyPartition savePartitions[];
    protected boolean useNamesFromData;
    protected boolean individually;
    protected boolean saved;
    protected int ruleBaseCreateMethod;
    protected boolean ruleBaseSizeAutomatic;
    protected boolean relearnRuleBase;
    protected TNorm tNorm;
    protected int selected;
    protected PrintWriter logFile;
    protected boolean logging;
    protected TextArea statusArea;
    protected TextArea ruleStatusArea;
    protected TextArea fsStatusArea;
    protected TextArea resultTextArea;
    protected ProgressBar pBar1;
    protected ProgressBar pBar2;
    protected ProgressBar pBar3;
    protected ProgressBar pBar4;
    protected FunctionLayoutPanel flPanel;
    protected FunctionPane errorGraph;
    protected Thread learningThread;
    protected Thread pruningThread;
    protected boolean running;
    protected int confusionMatrix[][];
    protected int unclassified;
    protected int misclassified;
    protected double error;
    protected double estimatedError;
    protected double estimatedErrorStdError;
    protected boolean trainingDataIsClassified;
    protected boolean validationDataIsClassified;
    protected boolean completeDataTableIsClassified;
    protected boolean applicationDataIsClassified;
    protected boolean validationModeIsSet;
    protected boolean performancesComputed;
    protected int validationMode;
    protected int validationPart;
    protected int maxEpochs;
    protected int minEpochs;
    protected int continueEpochs;
    protected int trainingEpochs;
    protected double learningRate;
    protected double misclassPercent;
    protected int misclassifications;
    protected double stopError;
    protected boolean keepOrder;
    protected boolean mustOverlap;
    protected boolean asymmetric;
    protected boolean addUpToOne;
    protected boolean useWeights;
    protected boolean normalWeights;
    protected boolean batchLearning;
    protected boolean minOnly;
    protected boolean invokeRuleLearning;
    protected boolean invokeFuzzySetLearning;
    protected boolean invokePruning;
    protected boolean invokeInternalPruning;
    protected boolean externalStop;
    protected boolean hasCategoricalVariables;
    protected BufferedReader buffer;
    protected StreamTokenizer tokenizer;
    ActionListener actionListener;

    public Nefclass(int i, int j, int k, int l)
    {
        super(i, j, l);
        tNorm = new TNorm(k);
        classNames = new String[j];
        description = "";
        confusionMatrix = new int[j][j + 1];
        useNamesFromData = true;
        trainingDataIsClassified = false;
        validationDataIsClassified = false;
        completeDataTableIsClassified = false;
        applicationDataIsClassified = false;
        selected = 1;
        validationMode = 0;
        validationPart = 0;
        maxEpochs = 500;
        minEpochs = 0;
        continueEpochs = 100;
        learningRate = 0.01D;
        misclassPercent = 0.0D;
        misclassifications = 0;
        stopError = 0.0D;
        asymmetric = true;
        keepOrder = true;
        mustOverlap = true;
        addUpToOne = false;
        useWeights = false;
        normalWeights = true;
        batchLearning = true;
        minOnly = false;
        saved = true;
        ruleStatusArea = null;
        fsStatusArea = null;
        resultTextArea = null;
        statusArea = null;
        pBar1 = null;
        pBar2 = null;
        pBar3 = null;
        pBar4 = null;
        flPanel = null;
        externalStop = false;
        validationModeIsSet = false;
        invokePruning = false;
        invokeInternalPruning = false;
        logging = false;
        performancesComputed = false;
        actionListener = null;
        hasCategoricalVariables = false;
    }

    public int getTNormType()
    {
        return tNorm.getType();
    }

    public String getClassName(int i)
    {
        if(i < classNames.length)
        {
            return classNames[i];
        } else
        {
            return "null";
        }
    }

    public void run()
    {
        printParameters();
        try
        {
            if(invokePruning)
            {
                pruning();
            } else
            {
                learning();
            }
        }
        catch(NefclassInvalidException nefclassinvalidexception)
        {
            System.out.println("Learning is not possible. \n" + nefclassinvalidexception.getMessage());
        }
        catch(NullPointerException nullpointerexception)
        {
            System.out.println("A null pointer exception occured during learning.\n" + nullpointerexception.getMessage());
        }
        catch(ArrayIndexOutOfBoundsException arrayindexoutofboundsexception)
        {
            System.out.println("Illegal array index during learning.\n" + arrayindexoutofboundsexception.getMessage());
        }
        finally
        {
            running = false;
            invokePruning = false;
            invokeInternalPruning = false;
            System.out.println("Learning stopped.");
            postEvent("ready");
            learningThread.stop();
        }
    }

    public void stopTraining()
    {
        externalStop = true;
    }

    public void setData(DataTable datatable)
        throws NefclassInvalidException
    {
        if(datatable != null)
        {
            if(datatable.independent == super.inDimension && datatable.dependent == super.outDimension && datatable.dependent > 0)
            {
                dataTable = datatable;
                trainingDataIsClassified = false;
                performancesComputed = false;
                if(useNamesFromData)
                {
                    for(int i = 0; i < super.outDimension; i++)
                    {
                        classNames[i] = dataTable.varNames[super.inDimension + i];
                    }

                    return;
                } else
                {
                    return;
                }
            } else
            {
                throw new NefclassInvalidException("The dimensions of the data table do not match the dimensions of the classifier, " +
"or there is no class information."
);
            }
        } else
        {
            throw new NefclassInvalidException("Trying to assign a null pointer as Nefclass training data.");
        }
    }

    public void setApplicationData(DataTable datatable)
        throws NefclassInvalidException
    {
        if(datatable != null)
        {
            if(datatable.independent == super.inDimension && (datatable.dependent == super.outDimension || datatable.dependent == 0))
            {
                applicationData = datatable;
                applicationDataIsClassified = false;
                return;
            } else
            {
                throw new NefclassInvalidException("The dimensions of the data table do not match the dimensions of the classifier.");
            }
        } else
        {
            throw new NefclassInvalidException("Trying to assign a null pointer as Nefclass application data.");
        }
    }

    public boolean usesData(DataTable datatable)
    {
        return datatable == dataTable;
    }

    public boolean usesApplicationData(DataTable datatable)
    {
        return datatable == applicationData;
    }

    public void setMaxRules(int i)
    {
        if(i > 0)
        {
            maxRules = i;
        }
    }

    public void setRuleBaseCreateMethod(int i)
    {
        if(i >= 0 && i <= 2)
        {
            ruleBaseCreateMethod = i;
        }
    }

    public void setRuleBaseSizeAutomatic(boolean flag)
    {
        ruleBaseSizeAutomatic = flag;
    }

    public void setRelearnRuleBase(boolean flag)
    {
        relearnRuleBase = flag;
    }

    public void setValidationMode(int i, int j)
    {
        if(dataTable != null)
        {
            if(i >= 0 && validationMode <= 2)
            {
                if(i != validationMode)
                {
                    validationModeIsSet = false;
                }
                validationMode = i;
            } else
            {
                validationMode = 0;
                validationModeIsSet = false;
            }
            if(i == 1 && j >= 0 && j <= 100)
            {
                validationPart = j;
                return;
            }
            if(i == 2 && j >= 2 && j <= dataTable.rows)
            {
                validationPart = j;
                return;
            } else
            {
                validationMode = 0;
                validationPart = 0;
                validationModeIsSet = false;
                return;
            }
        } else
        {
            validationMode = 0;
            validationPart = 0;
            return;
        }
    }

    public void setMaxEpochs(int i)
    {
        maxEpochs = i;
    }

    public void setMinEpochs(int i)
    {
        minEpochs = i;
    }

    public void setContinueEpochs(int i)
    {
        continueEpochs = i;
    }

    public void setLearningRate(double d)
    {
        learningRate = d;
    }

    public void setAsymmetric(boolean flag)
    {
        asymmetric = flag;
    }

    public void setKeepOrder(boolean flag)
    {
        keepOrder = flag;
    }

    public void setMustOverlap(boolean flag)
    {
        mustOverlap = flag;
    }

    public void setAddUpToOne(boolean flag)
    {
        addUpToOne = flag;
    }

    public void setUseWeights(boolean flag)
    {
        useWeights = flag;
    }

    public boolean getUseWeights()
    {
        return useWeights;
    }

    public void setNormalWeights(boolean flag)
    {
        normalWeights = flag;
    }

    public void setStopError(double d)
    {
        stopError = d;
    }

    public void setMisclassifications(double d)
    {
        if(d > 0.0D && d < 1.0D)
        {
            misclassPercent = d;
            misclassifications = 0;
            return;
        } else
        {
            misclassifications = Math.round((float)d);
            misclassPercent = 0.0D;
            return;
        }
    }

    protected int[] readParameters(String s)
        throws NefclassInvalidException, IOException
    {
        int ai[];
        double ad[];
        double ad1[];
        if(tokenizer.nextToken() == -2)
        {
            super.inDimension = (int)tokenizer.nval;
            ad = new double[super.inDimension];
            ad1 = new double[super.inDimension];
            ai = new int[super.inDimension];
        } else
        {
            throw new NefclassInvalidException(s + "\nNumber of inputs is not correctly specified.");
        }
        if(tokenizer.nextToken() == -2)
        {
            int i = (int)tokenizer.nval;
            for(int j = 0; j < i; j++)
            {
                FuzzyClassifierRule fuzzyclassifierrule = new FuzzyClassifierRule("R" + j, tNorm.getType(), super.inDimension, null);
                fuzzyclassifierrule.setValid(true);
                addFuzzyRule(fuzzyclassifierrule);
            }

        } else
        {
            throw new NefclassInvalidException(s + "\nNumber of rules is not correctly specified.");
        }
        if(tokenizer.nextToken() == -2)
        {
            super.outDimension = (int)tokenizer.nval;
            setDimensions(super.inDimension, super.outDimension);
        } else
        {
            throw new NefclassInvalidException(s + "\nNumber of classes is not correctly specified.");
        }
        if(tokenizer.nextToken() == -2)
        {
            maxRules = (int)tokenizer.nval;
        } else
        {
            throw new NefclassInvalidException(s + "\nNumber of rules is not correctly specified.");
        }
        for(int k = 0; k < super.inDimension; k++)
        {
            if(tokenizer.nextToken() == -2)
            {
                ai[k] = (int)tokenizer.nval;
            } else
            {
                throw new NefclassInvalidException(s + "\nNumber of fuzzy sets is not correctly specified.");
            }
        }

        partitions = new FuzzyPartition[super.inDimension];
        bestPartitions = new FuzzyPartition[super.inDimension];
        initPartitions = new FuzzyPartition[super.inDimension];
        savePartitions = new FuzzyPartition[super.inDimension];
        for(int l = 0; l < super.inDimension; l++)
        {
            if(tokenizer.nextToken() == -2)
            {
                ad[l] = tokenizer.nval;
            } else
            {
                throw new NefclassInvalidException(s + "\nA lower bound is not correctly specified.");
            }
            if(tokenizer.nextToken() == -2)
            {
                ad1[l] = tokenizer.nval;
            } else
            {
                throw new NefclassInvalidException(s + "\nAn upper bound is not correctly specified.");
            }
            partitions[l] = new FuzzyPartition(l, ad[l], ad1[l], "Var " + l);
            bestPartitions[l] = new FuzzyPartition(l, ad[l], ad1[l], "Var " + l);
            initPartitions[l] = new FuzzyPartition(l, ad[l], ad1[l], "Var " + l);
            savePartitions[l] = new FuzzyPartition(l, ad[l], ad1[l], "Var " + l);
        }

        return ai;
    }

    public void readFuzzySets(int ai[], boolean flag, boolean flag1, boolean flag2, String s)
        throws NefclassInvalidException, IOException
    {
        for(int i = 0; i < super.inDimension; i++)
        {
            double ad[];
            byte byte0;
            if(flag)
            {
                if(tokenizer.nextToken() == -3)
                {
                    if(tokenizer.sval.equalsIgnoreCase("TRIANGULAR"))
                    {
                        byte0 = 0;
                        ad = new double[5];
                    } else
                    if(tokenizer.sval.equalsIgnoreCase("TRAPEZOIDAL"))
                    {
                        byte0 = 1;
                        ad = new double[6];
                    } else
                    if(tokenizer.sval.equalsIgnoreCase("BELLSHAPED"))
                    {
                        byte0 = 2;
                        ad = new double[5];
                    } else
                    if(tokenizer.sval.equalsIgnoreCase("LIST"))
                    {
                        byte0 = 3;
                        ad = null;
                        hasCategoricalVariables = true;
                    } else
                    {
                        throw new NefclassInvalidException(s + "\nIllegal specifier for a fuzzy set type.");
                    }
                } else
                {
                    throw new NefclassInvalidException(s + "\nMissing specifier for fuzzy set type.");
                }
            } else
            {
                byte0 = 0;
                ad = new double[5];
            }
            try
            {
                partitions[i].createFuzzyPartition(ai[i], byte0);
                bestPartitions[i].createFuzzyPartition(ai[i], byte0);
                initPartitions[i].createFuzzyPartition(ai[i], byte0);
                savePartitions[i].createFuzzyPartition(ai[i], byte0);
            }
            catch(FuzzySetInvalidException fuzzysetinvalidexception)
            {
                throw new NefclassInvalidException(s + "\nCannot create fuzzy partition:\n" + fuzzysetinvalidexception.getMessage());
            }
            for(int j = 0; j < ai[i]; j++)
            {
                if(byte0 == 3)
                {
                    if(tokenizer.nextToken() == -2)
                    {
                        ad = new double[(int)tokenizer.nval + 2];
                    } else
                    {
                        throw new NefclassInvalidException(s + "\nIllegal fuzzy set parameter.");
                    }
                }
                for(int k = 0; k < ad.length; k++)
                {
                    if(tokenizer.nextToken() == -2)
                    {
                        ad[k] = tokenizer.nval;
                    } else
                    {
                        throw new NefclassInvalidException(s + "\nIllegal fuzzy set parameter.");
                    }
                }

                try
                {
                    partitions[i].setFuzzySet(j, ad);
                    if(flag1)
                    {
                        String s1;
                        String s2;
                        if(tokenizer.nextToken() == -3)
                        {
                            s1 = tokenizer.sval;
                            s2 = s1;
                        } else
                        {
                            throw new NefclassInvalidException(s + "\nName of fuzzy set is missing.");
                        }
                        if(flag2)
                        {
                            if(tokenizer.nextToken() == -3)
                            {
                                s2 = tokenizer.sval;
                            } else
                            {
                                throw new NefclassInvalidException(s + "\nShort name of fuzzy set is missing.");
                            }
                        }
                        partitions[i].setFuzzySetNames(j, s1, s2);
                    }
                }
                catch(FuzzySetInvalidException fuzzysetinvalidexception1)
                {
                    throw new NefclassInvalidException(s + "\nCannot create fuzzy set due to illegal parameters:\n" + fuzzysetinvalidexception1.getMessage());
                }
            }

        }

    }

    protected void readFuzzyRules(String s)
        throws NefclassInvalidException, IOException
    {
        for(int j = 0; j < super.inDimension; j++)
        {
            for(int k = 0; k < super.ruleBase.size(); k++)
            {
                int i;
                if(tokenizer.nextToken() == -2 && tokenizer.nval >= 0.0D && tokenizer.nval <= (double)partitions[j].getNumberOfFuzzySets())
                {
                    i = (int)tokenizer.nval;
                } else
                {
                    throw new NefclassInvalidException(s + "\nIllegal fuzzy set index in fuzzy rule specifications.");
                }
                FuzzyClassifierRule fuzzyclassifierrule = (FuzzyClassifierRule)super.ruleBase.elementAt(k);
                if(i > 0)
                {
                    i--;
                    fuzzyclassifierrule.addAntecedent(new Variable(j, partitions[j].getName()), partitions[j].getFuzzySet(i));
                } else
                {
                    fuzzyclassifierrule.deleteAntecedent(i);
                }
            }

        }

    }

    protected void readRuleWeights(String as[], String s)
        throws NefclassInvalidException, IOException
    {
        for(int j = 0; j < super.ruleBase.size(); j++)
        {
            int i;
            if(tokenizer.nextToken() == -2 && tokenizer.nval > 0.0D && tokenizer.nval <= (double)super.outDimension)
            {
                i = (int)tokenizer.nval;
                i--;
            } else
            {
                throw new NefclassInvalidException(s + "\nIllegal class index in fuzzy rule specifications.");
            }
            double d;
            if(tokenizer.nextToken() == -2)
            {
                d = tokenizer.nval;
            } else
            {
                throw new NefclassInvalidException(s + "\nIllegal rule weight in fuzzy rule specifications.");
            }
            ((FuzzyClassifierRule)super.ruleBase.elementAt(j)).setConsequent(new Variable(i, as[i]), d);
        }

    }

    public void read(String s)
        throws NefclassInvalidException, IOException
    {
        String s1 = "An error occured while trying to read a classifier from\n" + s;
        buffer = new BufferedReader(new FileReader(s));
        tokenizer = new StreamTokenizer(buffer);
        int ai[] = null;
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        tokenizer.commentChar(35);
        tokenizer.commentChar(37);
        tokenizer.slashStarComments(true);
        tokenizer.slashSlashComments(true);
        tokenizer.parseNumbers();
        tokenizer.eolIsSignificant(false);
        while(buffer.ready()) 
        {
            if(tokenizer.nextToken() == -3)
            {
                if(tokenizer.sval.equalsIgnoreCase("PARAMETERS"))
                {
                    ai = readParameters(s1);
                    classNames = new String[super.outDimension];
                } else
                if(tokenizer.sval.equalsIgnoreCase("USESUM"))
                {
                    super.howToClassify = 1;
                } else
                if(tokenizer.sval.equalsIgnoreCase("USEMAXIMUM"))
                {
                    super.howToClassify = 0;
                } else
                if(tokenizer.sval.equalsIgnoreCase("DESCRIPTION"))
                {
                    buffer.readLine();
                    description = buffer.readLine();
                } else
                if(tokenizer.sval.equalsIgnoreCase("INDIVIDUAL"))
                {
                    individually = true;
                } else
                if(tokenizer.sval.equalsIgnoreCase("SAME"))
                {
                    individually = false;
                } else
                if(tokenizer.sval.equalsIgnoreCase("VNAMES"))
                {
                    buffer.readLine();
                    for(int i = 0; i < super.inDimension; i++)
                    {
                        partitions[i].setName(buffer.readLine());
                    }

                    for(int k = 0; k < super.outDimension; k++)
                    {
                        classNames[k] = buffer.readLine();
                        useNamesFromData = false;
                    }

                } else
                if(tokenizer.sval.equalsIgnoreCase("TYPES"))
                {
                    flag = true;
                } else
                if(tokenizer.sval.equalsIgnoreCase("NAMES"))
                {
                    flag1 = true;
                    flag2 = false;
                } else
                if(tokenizer.sval.equalsIgnoreCase("TWONAMES"))
                {
                    flag1 = true;
                    flag2 = true;
                } else
                if(tokenizer.sval.equalsIgnoreCase("FUZZY"))
                {
                    readFuzzySets(ai, flag, flag1, flag2, s1);
                } else
                if(tokenizer.sval.equalsIgnoreCase("MATRIX"))
                {
                    readFuzzyRules(s1);
                } else
                if(tokenizer.sval.equalsIgnoreCase("WEIGHTS"))
                {
                    readRuleWeights(classNames, s1);
                } else
                if(tokenizer.sval.equalsIgnoreCase("END"))
                {
                    flag3 = true;
                } else
                {
                    throw new NefclassInvalidException(s1 + "Unknown token.");
                }
            }
        }
        for(int j = 0; j < super.inDimension; j++)
        {
            partitions[j].copyTo(bestPartitions[j]);
            partitions[j].copyTo(initPartitions[j]);
            partitions[j].copyTo(savePartitions[j]);
        }

        confusionMatrix = new int[super.outDimension][super.outDimension + 1];
        saved = true;
        performancesComputed = false;
        buffer.close();
    }

    public void write(String s)
        throws IOException
    {
        DateFormat dateformat = DateFormat.getDateTimeInstance(1, 1, Locale.US);
        dateformat.setTimeZone(TimeZone.getDefault());
        String s1 = dateformat.format(new Date());
        BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(s));
        bufferedwriter.write("% NEFCLASS file created by ");
        bufferedwriter.write("NEFCLASS-J 1.0");
        bufferedwriter.newLine();
        bufferedwriter.write("% (c) U. Nauck, Braunschweig, 1999");
        bufferedwriter.newLine();
        bufferedwriter.write("% Filename: " + s);
        bufferedwriter.newLine();
        bufferedwriter.write("% This file was created at " + s1);
        bufferedwriter.newLine();
        bufferedwriter.write("%");
        bufferedwriter.newLine();
        bufferedwriter.write("% " + description);
        bufferedwriter.newLine();
        bufferedwriter.newLine();
        bufferedwriter.write("% This are the structure parameters");
        bufferedwriter.newLine();
        bufferedwriter.write("PARAMETERS");
        bufferedwriter.newLine();
        bufferedwriter.write(Integer.toString(super.inDimension));
        bufferedwriter.write("  ");
        bufferedwriter.write(Integer.toString(super.ruleBase.size()));
        bufferedwriter.write("  ");
        bufferedwriter.write(Integer.toString(super.outDimension));
        bufferedwriter.write("  ");
        bufferedwriter.write(Integer.toString(maxRules));
        bufferedwriter.newLine();
        for(int i = 0; i < super.inDimension; i++)
        {
            bufferedwriter.write(Integer.toString(partitions[i].getNumberOfFuzzySets()));
            bufferedwriter.newLine();
        }

        for(int j = 0; j < super.inDimension; j++)
        {
            bufferedwriter.write(Double.toString(partitions[j].getLowerBound()));
            bufferedwriter.write("  ");
            bufferedwriter.write(Double.toString(partitions[j].getUpperBound()));
            bufferedwriter.newLine();
        }

        bufferedwriter.newLine();
        if(super.howToClassify == 1)
        {
            bufferedwriter.write("USESUM");
        } else
        {
            bufferedwriter.write("USEMAXIMUM");
        }
        bufferedwriter.newLine();
        bufferedwriter.newLine();
        if(individually)
        {
            bufferedwriter.write("INDIVIDUAL");
        } else
        {
            bufferedwriter.write("SAME");
        }
        bufferedwriter.newLine();
        bufferedwriter.newLine();
        bufferedwriter.write("% This are the names of the variables and classes");
        bufferedwriter.newLine();
        bufferedwriter.write("VNAMES");
        bufferedwriter.newLine();
        for(int k = 0; k < super.inDimension; k++)
        {
            bufferedwriter.write(partitions[k].getName());
            bufferedwriter.newLine();
        }

        for(int l = 0; l < super.outDimension; l++)
        {
            bufferedwriter.write(classNames[l]);
            bufferedwriter.newLine();
        }

        bufferedwriter.newLine();
        bufferedwriter.write("% This are the parameters of the fuzzy sets");
        bufferedwriter.newLine();
        bufferedwriter.write("TWONAMES");
        bufferedwriter.newLine();
        bufferedwriter.write("TYPES");
        bufferedwriter.newLine();
        bufferedwriter.write("FUZZY");
        bufferedwriter.newLine();
        for(int i1 = 0; i1 < super.inDimension; i1++)
        {
            bufferedwriter.write(partitions[i1].getFuzzySet(0).getTypeName());
            bufferedwriter.newLine();
            for(int j1 = 0; j1 < partitions[i1].getNumberOfFuzzySets(); j1++)
            {
                bufferedwriter.write(partitions[i1].getFuzzySet(j1).printParameters());
                bufferedwriter.write("  ");
                bufferedwriter.write(partitions[i1].getFuzzySet(j1).getNames());
                bufferedwriter.newLine();
            }

        }

        bufferedwriter.newLine();
        bufferedwriter.write("% This are the antecedents of the rules (one column for each rule)");
        bufferedwriter.newLine();
        bufferedwriter.write("MATRIX");
        bufferedwriter.newLine();
        for(int k1 = 0; k1 < super.inDimension; k1++)
        {
            for(int l1 = 0; l1 < super.ruleBase.size(); l1++)
            {
                bufferedwriter.write(Integer.toString(((FuzzyRule)super.ruleBase.elementAt(l1)).getAntecedentFuzzySetIndex(k1) + 1));
                bufferedwriter.write("  ");
            }

            bufferedwriter.newLine();
        }

        bufferedwriter.newLine();
        bufferedwriter.write("% This are the consequents and weights of the rules");
        bufferedwriter.newLine();
        bufferedwriter.write("WEIGHTS");
        bufferedwriter.newLine();
        for(int i2 = 0; i2 < super.ruleBase.size(); i2++)
        {
            bufferedwriter.write(Integer.toString(((FuzzyClassifierRule)super.ruleBase.elementAt(i2)).getClassIndex() + 1));
            bufferedwriter.write("  ");
            bufferedwriter.write(Double.toString(((FuzzyRule)super.ruleBase.elementAt(i2)).getWeight()));
            bufferedwriter.newLine();
        }

        bufferedwriter.newLine();
        bufferedwriter.write("END");
        bufferedwriter.newLine();
        bufferedwriter.close();
        saved = true;
    }

    public void createPartitions(int i, int j)
        throws NefclassInvalidException
    {
        if(super.inDimension <= 0)
        {
            throw new NefclassInvalidException("Trying to create fuzzy partitions for 0 variables.");
        }
        partitions = new FuzzyPartition[super.inDimension];
        bestPartitions = new FuzzyPartition[super.inDimension];
        initPartitions = new FuzzyPartition[super.inDimension];
        savePartitions = new FuzzyPartition[super.inDimension];
        hasCategoricalVariables = j == 3;
        try
        {
            for(int k = 0; k < super.inDimension; k++)
            {
                partitions[k] = new FuzzyPartition(k, dataTable.min[k], dataTable.max[k], dataTable.varNames[k]);
                bestPartitions[k] = new FuzzyPartition(k, dataTable.min[k], dataTable.max[k], dataTable.varNames[k]);
                initPartitions[k] = new FuzzyPartition(k, dataTable.min[k], dataTable.max[k], dataTable.varNames[k]);
                savePartitions[k] = new FuzzyPartition(k, dataTable.min[k], dataTable.max[k], dataTable.varNames[k]);
                partitions[k].createFuzzyPartition(i, j);
                bestPartitions[k].createFuzzyPartition(i, j);
                initPartitions[k].createFuzzyPartition(i, j);
                savePartitions[k].createFuzzyPartition(i, j);
            }

            return;
        }
        catch(FuzzySetInvalidException fuzzysetinvalidexception)
        {
            System.out.println(fuzzysetinvalidexception.getMessage());
        }
        partitions = null;
        throw new NefclassInvalidException("Nefclass cannot create fuzzy partions due to illegal parameters.");
    }

    public void createPartitions(int ai[], int ai1[])
        throws NefclassInvalidException
    {
        partitions = new FuzzyPartition[ai.length];
        bestPartitions = new FuzzyPartition[ai.length];
        initPartitions = new FuzzyPartition[ai.length];
        savePartitions = new FuzzyPartition[ai.length];
        try
        {
            for(int i = 0; i < ai.length; i++)
            {
                if(ai1[i] == 3)
                {
                    hasCategoricalVariables = true;
                }
                partitions[i] = new FuzzyPartition(i, dataTable.min[i], dataTable.max[i], dataTable.varNames[i]);
                bestPartitions[i] = new FuzzyPartition(i, dataTable.min[i], dataTable.max[i], dataTable.varNames[i]);
                initPartitions[i] = new FuzzyPartition(i, dataTable.min[i], dataTable.max[i], dataTable.varNames[i]);
                savePartitions[i] = new FuzzyPartition(i, dataTable.min[i], dataTable.max[i], dataTable.varNames[i]);
                partitions[i].createFuzzyPartition(ai[i], ai1[i]);
                bestPartitions[i].createFuzzyPartition(ai[i], ai1[i]);
                initPartitions[i].createFuzzyPartition(ai[i], ai1[i]);
                savePartitions[i].createFuzzyPartition(ai[i], ai1[i]);
            }

            return;
        }
        catch(FuzzySetInvalidException fuzzysetinvalidexception)
        {
            System.out.println(fuzzysetinvalidexception.getMessage());
        }
        partitions = null;
        throw new NefclassInvalidException("Nefclass cannot create fuzzy partions due to illegal parameters.");
    }

    public void initializePartitions()
        throws NefclassInvalidException
    {
        FuzzyPartition afuzzypartition[] = new FuzzyPartition[partitions.length];
        try
        {
            for(int i = 0; i < partitions.length; i++)
            {
                afuzzypartition[i] = new FuzzyPartition(i, dataTable.min[i], dataTable.max[i], dataTable.varNames[i]);
                afuzzypartition[i].createFuzzyPartition(partitions[i].getNumberOfFuzzySets(), partitions[i].getFuzzySet(0).getType());
            }

            backupPartitions();
            for(int j = 0; j < partitions.length; j++)
            {
                afuzzypartition[j].copyTo(partitions[j]);
            }

            return;
        }
        catch(FuzzySetInvalidException fuzzysetinvalidexception)
        {
            System.out.println(fuzzysetinvalidexception.getMessage());
        }
        partitions = null;
        throw new NefclassInvalidException("Nefclass cannot create new initial fuzzy partions due to illegal parameters.");
    }

    public FuzzyPartition[] copyPartitions(FuzzyPartition afuzzypartition[])
        throws NefclassInvalidException
    {
        FuzzyPartition afuzzypartition1[] = new FuzzyPartition[afuzzypartition.length];
        try
        {
            for(int i = 0; i < afuzzypartition.length; i++)
            {
                afuzzypartition1[i] = new FuzzyPartition(i, dataTable.min[i], dataTable.max[i], dataTable.varNames[i]);
                afuzzypartition1[i].createFuzzyPartition(afuzzypartition[i].getNumberOfFuzzySets(), afuzzypartition[i].getFuzzySet(0).getType());
            }

            for(int j = 0; j < afuzzypartition.length; j++)
            {
                afuzzypartition[j].copyTo(afuzzypartition1[j]);
            }

        }
        catch(FuzzySetInvalidException fuzzysetinvalidexception)
        {
            afuzzypartition1 = null;
            throw new NefclassInvalidException("Nefclass cannot create new initial fuzzy partions due to illegal parameters: " + fuzzysetinvalidexception.getMessage());
        }
        return afuzzypartition1;
    }

    public void setInitialPartitions()
    {
        backupPartitions();
        for(int i = 0; i < partitions.length; i++)
        {
            partitions[i].copyTo(initPartitions[i]);
        }

    }

    protected void copyPartitions(FuzzyPartition afuzzypartition[], FuzzyPartition afuzzypartition1[])
    {
        for(int i = 0; i < afuzzypartition.length; i++)
        {
            afuzzypartition[i].copyTo(afuzzypartition1[i]);
        }

    }

    protected void resetPartitions()
    {
        for(int i = 0; i < partitions.length; i++)
        {
            initPartitions[i].copyTo(partitions[i]);
        }

    }

    public void backupPartitions()
    {
        for(int i = 0; i < partitions.length; i++)
        {
            partitions[i].copyTo(savePartitions[i]);
        }

    }

    public void restorePartitions()
    {
        for(int i = 0; i < partitions.length; i++)
        {
            partitions[i].copyTo(bestPartitions[i]);
        }

        for(int j = 0; j < partitions.length; j++)
        {
            savePartitions[j].copyTo(partitions[j]);
        }

        for(int k = 0; k < partitions.length; k++)
        {
            bestPartitions[k].copyTo(savePartitions[k]);
        }

    }

    public void learning(boolean flag, boolean flag1)
        throws NefclassInvalidException
    {
        invokeRuleLearning = flag;
        invokeFuzzySetLearning = flag1;
        externalStop = false;
        running = true;
        learningThread = new Thread(this);
        learningThread.start();
    }

    protected void computeRulePerformance(Vector vector)
    {
        double ad[] = new double[super.outDimension];
        for(int i = 0; i < vector.size(); i++)
        {
            for(int j = 0; j < super.outDimension; j++)
            {
                ad[j] = 0.0D;
            }

            double d1 = 0.0D;
            FuzzyClassifierRule fuzzyclassifierrule = (FuzzyClassifierRule)vector.elementAt(i);
            for(int k = 0; k < dataTable.rows; k++)
            {
                if(dataTable.rowSelector[k] != selected)
                {
                    double d = fuzzyclassifierrule.computeDegreeOfFulfilment(dataTable.data[k]);
                    ad[dataTable.classIndex[k]] += d;
                }
            }

            for(int l = 0; l < super.outDimension; l++)
            {
                d1 -= ad[l];
            }

            d1 += 2D * ad[fuzzyclassifierrule.getClassIndex()];
            d1 /= dataTable.unSelectedRows;
            fuzzyclassifierrule.setPerformance(d1);
        }

        performancesComputed = true;
    }

    protected void createRuleBase()
        throws NefclassInvalidException
    {
        double ad[] = new double[super.outDimension];
        boolean flag = false;
        int ai[] = new int[super.inDimension];
        boolean aflag[] = new boolean[super.inDimension];
        postEvent("rule learning");
        for(int j = 0; j < super.ruleBase.size(); j++)
        {
            ((FuzzyClassifierRule)super.ruleBase.elementAt(j)).setValid(false);
            ((FuzzyClassifierRule)super.ruleBase.elementAt(j)).deleteConsequent();
            boolean flag1 = true;
        }

        printMessage("Searching for rules in the training data...");
        for(int k = 0; k < dataTable.rows && !externalStop; k++)
        {
            if(pBar1 != null)
            {
                try
                {
                    pBar1.setProgressPercent((100 * (k + 1)) / dataTable.rows);
                }
                catch(PropertyVetoException _ex) { }
            }
            if(dataTable.rowSelector[k] != selected)
            {
                FuzzyClassifierRule fuzzyclassifierrule = new FuzzyClassifierRule("R" + super.ruleBase.size(), tNorm.getType(), super.inDimension, null);
                if(!dataTable.hasMissingValues[k])
                {
                    for(int l = 0; l < super.inDimension; l++)
                    {
                        fuzzyclassifierrule.addAntecedent(new Variable(l, dataTable.varNames[l]), partitions[l].getMaximumDegreeFS(dataTable.data[k][l]));
                    }

                    if(isRuleConsistentWithRuleBase(fuzzyclassifierrule, super.ruleBase) == -1)
                    {
                        addFuzzyRule(fuzzyclassifierrule);
                        if(logging)
                        {
                            logFile.println("Pattern " + FormatString.pad(Integer.toString(k), 5, false) + " created rule " + super.ruleBase.size());
                        }
                    }
                } else
                {
                    boolean flag2 = false;
                    for(int i1 = 0; i1 < super.inDimension; i1++)
                    {
                        if(dataTable.data[k][i1] == (1.0D / 0.0D) && partitions[i1].getType() != 3)
                        {
                            aflag[i1] = true;
                            ai[i1] = 0;
                        } else
                        {
                            aflag[i1] = false;
                            ai[i1] = partitions[i1].getIndexOfMaximumDegree(dataTable.data[k][i1]);
                        }
                    }

                    while(!flag2) 
                    {
                        for(int k1 = 0; k1 < super.inDimension; k1++)
                        {
                            fuzzyclassifierrule.addAntecedent(new Variable(k1, dataTable.varNames[k1]), partitions[k1].getFuzzySet(ai[k1]));
                        }

                        if(isRuleConsistentWithRuleBase(fuzzyclassifierrule, super.ruleBase) == -1)
                        {
                            addFuzzyRule(fuzzyclassifierrule);
                            if(logging)
                            {
                                logFile.println("Pattern " + FormatString.pad(Integer.toString(k), 5, false) + " with missing values created rule " + super.ruleBase.size());
                            }
                        }
                        boolean flag3 = false;
                        for(int i2 = 0; i2 < super.inDimension && !flag3; i2++)
                        {
                            if(aflag[i2])
                            {
                                ai[i2]++;
                                if(ai[i2] >= partitions[i2].getNumberOfFuzzySets())
                                {
                                    aflag[i2] = false;
                                } else
                                {
                                    flag3 = true;
                                }
                            }
                        }

                        flag2 = true;
                        for(int k2 = 0; k2 < super.inDimension && !flag3; k2++)
                        {
                            flag2 = flag2 && !aflag[k2];
                        }

                        if(!flag2)
                        {
                            fuzzyclassifierrule = new FuzzyClassifierRule("R" + super.ruleBase.size(), tNorm.getType(), super.inDimension, null);
                        }
                    }
                }
                fuzzyclassifierrule = null;
            }
        }

        if(hasCategoricalVariables)
        {
            printMessage("There are categorical variables.");
            printMessage("Creating new list of rule base candidates.");
            if(logging)
            {
                logFile.println("\nThere are categorical variables. The fuzzy sets\nfor these variables are now c" +
"reated for all possible rules.\nthis will result in a new larger set of rule bas" +
"e candidates."
);
            }
            processCategoricalVariables();
        }
        printMessage("\n" + super.ruleBase.size() + " possible rules found. Now determine the " + "optimal consequents.");
        if(logging)
        {
            logFile.println("\n\nPerformances of Rule Candidates per Class\n");
        }
        for(int j1 = 0; j1 < super.ruleBase.size() && !externalStop; j1++)
        {
            FuzzyClassifierRule fuzzyclassifierrule1 = (FuzzyClassifierRule)super.ruleBase.elementAt(j1);
            if(pBar2 != null)
            {
                try
                {
                    pBar2.setProgressPercent((100 * (j1 + 1)) / super.ruleBase.size());
                }
                catch(PropertyVetoException _ex) { }
            }
            for(int l1 = 0; l1 < super.outDimension; l1++)
            {
                ad[l1] = 0.0D;
            }

            for(int j2 = 0; j2 < dataTable.rows; j2++)
            {
                if(dataTable.rowSelector[j2] != selected)
                {
                    double d = fuzzyclassifierrule1.computeDegreeOfFulfilment(dataTable.data[j2]);
                    ad[dataTable.classIndex[j2]] += d;
                }
            }

            if(logging)
            {
                logFile.print(FormatString.pad(Integer.toString(j1), 5, false) + ") ");
            }
            double d2 = 0.0D;
            double d1 = -1D;
            int i = 0;
            for(int l2 = 0; l2 < super.outDimension && !externalStop; l2++)
            {
                if(logging)
                {
                    logFile.print(FormatString.doubleString(ad[l2], 10, 4));
                }
                d2 -= ad[l2];
                if(ad[l2] > d1)
                {
                    d1 = ad[l2];
                    i = l2;
                }
            }

            d2 += 2D * d1;
            d2 /= dataTable.unSelectedRows;
            fuzzyclassifierrule1.setPerformance(d2);
            fuzzyclassifierrule1.setConsequent(new Variable(i, dataTable.varNames[super.inDimension + i]));
            if(logging)
            {
                logFile.println(", performance = " + FormatString.doubleString(d2, 10, 4) + ", Class = " + dataTable.varNames[super.inDimension + i]);
            }
        }

        performancesComputed = true;
        printMessage("Selection of consequents is complete.");
        if(logging)
        {
            printMessage("Writing the rules to the log file, please wait.");
            logFile.println("\n\n" + super.ruleBase.size() + " rules found in the data:\n\n" + toString());
        }
        if(externalStop)
        {
            super.ruleBase.removeAllElements();
            printMessage("External stop, changes to rule base are discarded.");
            return;
        }
        switch(ruleBaseCreateMethod)
        {
        case 0: // '\0'
            simpleRuleLearning();
            break;

        case 1: // '\001'
            bestRuleLearning(ruleBaseSizeAutomatic, pBar3);
            break;

        case 2: // '\002'
            bestPerClassRuleLearning(ruleBaseSizeAutomatic, pBar3);
            break;

        default:
            throw new NefclassInvalidException("Cannot invoke a non-existing rule learning method.");
        }
        if(hasCategoricalVariables)
        {
            trimListPartitions();
        }
    }

    protected void processCategoricalVariables()
        throws NefclassInvalidException
    {
        Vector vector = super.ruleBase;
        super.ruleBase = new Vector(1000, 10);
        for(int i = 0; i < vector.size(); i++)
        {
            if(pBar1 != null)
            {
                try
                {
                    pBar1.setProgressPercent((100 * (i + 1)) / vector.size());
                }
                catch(PropertyVetoException _ex) { }
            }
            boolean flag = false;
            FuzzyClassifierRule fuzzyclassifierrule1 = (FuzzyClassifierRule)vector.elementAt(i);
            for(int j = 0; j < super.outDimension && !flag; j++)
            {
                flag = true;
                FuzzyClassifierRule fuzzyclassifierrule = new FuzzyClassifierRule("R" + super.ruleBase.size(), tNorm.getType(), super.inDimension, null);
                for(int k = 0; k < super.inDimension; k++)
                {
                    if(fuzzyclassifierrule1.variableIsUsed(k))
                    {
                        FuzzySet fuzzyset = null;
                        if(partitions[k].getType() == 3)
                        {
                            flag = false;
                            double ad[] = getFrequencies(fuzzyclassifierrule1, k, j);
                            if(ad != null)
                            {
                                try
                                {
                                    fuzzyset = partitions[k].addListFuzzySet(ad);
                                }
                                catch(FuzzySetInvalidException fuzzysetinvalidexception)
                                {
                                    throw new NefclassInvalidException("\nCannot create new list fuzzy set:\n" + fuzzysetinvalidexception.getMessage());
                                }
                            }
                        } else
                        {
                            fuzzyset = fuzzyclassifierrule1.getAntecedentFuzzySet(k);
                        }
                        if(fuzzyset != null)
                        {
                            fuzzyclassifierrule.addAntecedent(new Variable(k, dataTable.varNames[k]), fuzzyset);
                        } else
                        {
                            fuzzyclassifierrule.deleteAntecedent(k);
                        }
                    } else
                    {
                        fuzzyclassifierrule.deleteAntecedent(k);
                    }
                }

                if(isRuleConsistentWithRuleBase(fuzzyclassifierrule, super.ruleBase) == -1)
                {
                    addFuzzyRule(fuzzyclassifierrule);
                }
            }

        }

        vector.removeAllElements();
    }

    protected double[] getFrequencies(FuzzyClassifierRule fuzzyclassifierrule, int i, int j)
    {
        int k = (int)dataTable.getRealMin(i);
        int l = (int)dataTable.getRealMax(i);
        int i1 = 0;
        double ad[] = new double[(l - k) + 3];
        ad[0] = k;
        ad[1] = l;
        for(int j1 = 0; j1 < dataTable.rows; j1++)
        {
            if(dataTable.rowSelector[j1] != selected && dataTable.classIndex[j1] == j && dataTable.data[j1][i] != (1.0D / 0.0D) && fuzzyclassifierrule.computeDegreeOfFulfilment(dataTable.data[j1]) > 0.0D)
            {
                i1++;
                ad[((int)dataTable.data[j1][i] - k) + 2]++;
            }
        }

        if(i1 > 0)
        {
            return ad;
        } else
        {
            return null;
        }
    }

    protected void trimListPartitions()
    {
        for(int i = 0; i < super.inDimension; i++)
        {
            if(partitions[i].getType() == 3)
            {
                partitions[i].invalidateFuzzySets();
            }
        }

        for(int j = 0; j < super.ruleBase.size(); j++)
        {
            for(int k = 0; k < super.inDimension; k++)
            {
                FuzzyClassifierRule fuzzyclassifierrule = (FuzzyClassifierRule)super.ruleBase.elementAt(j);
                if(partitions[k].getType() == 3 && fuzzyclassifierrule.variableIsUsed(k))
                {
                    fuzzyclassifierrule.getAntecedentFuzzySet(k).setValid(true);
                }
            }

        }

        for(int l = 0; l < super.inDimension; l++)
        {
            if(partitions[l].getType() == 3)
            {
                partitions[l].trim();
            }
        }

    }

    protected void simpleRuleLearning()
    {
        printMessage("Simple rule learning: selecting the first " + Integer.toString(maxRules) + " rules.");
        for(int i = 0; i < maxRules && i < super.ruleBase.size(); i++)
        {
            ((FuzzyClassifierRule)super.ruleBase.elementAt(i)).setValid(true);
        }

        trimRuleBase();
        printMessage("Rule base created.");
        if(logging)
        {
            logFile.println(toString());
        }
    }

    protected void bestRuleLearning(boolean flag, ProgressBar progressbar)
    {
        boolean aflag[] = new boolean[dataTable.rows];
        if(flag)
        {
            for(int l = 0; l < aflag.length; l++)
            {
                aflag[l] = false;
            }

        }
        if(flag)
        {
            printMessage("Best rule learning, size of rule base is determined automatically.");
        } else
        {
            printMessage("Best rule learning: selecting the best " + maxRules + " rules.");
        }
        boolean flag2 = false;
        int j = 0;
        for(int i1 = 0; i1 < super.ruleBase.size() && !flag2; i1++)
        {
            boolean flag1 = false;
            double d = -2 * dataTable.rows;
            Object obj = null;
            for(int j1 = 0; j1 < super.ruleBase.size(); j1++)
            {
                FuzzyClassifierRule fuzzyclassifierrule = (FuzzyClassifierRule)super.ruleBase.elementAt(j1);
                double d1 = fuzzyclassifierrule.getPerformance();
                if(d1 > d && !fuzzyclassifierrule.valid())
                {
                    d = d1;
                    int i = j1;
                    obj = fuzzyclassifierrule;
                }
            }

            if(flag)
            {
                int k = determineCoverage(((FuzzyRule) (obj)), aflag);
                if(k > 0)
                {
                    j += k;
                    ((FuzzyRule) (obj)).setValid(true);
                    flag2 = j == dataTable.unSelectedRows;
                    if(progressbar != null)
                    {
                        try
                        {
                            progressbar.setProgressPercent((100 * j) / dataTable.unSelectedRows);
                        }
                        catch(PropertyVetoException _ex) { }
                    }
                } else
                {
                    ((FuzzyRule) (obj)).setPerformance(-2 * dataTable.rows);
                }
            } else
            {
                ((FuzzyRule) (obj)).setValid(true);
                flag2 = i1 >= maxRules - 1;
                if(progressbar != null)
                {
                    try
                    {
                        progressbar.setProgressPercent((100 * (i1 + 1)) / maxRules);
                    }
                    catch(PropertyVetoException _ex) { }
                }
            }
        }

        printMessage("Best rules selected, trimming the rule base.");
        trimRuleBase();
        printMessage("Rule base with " + super.ruleBase.size() + " rules created.");
        if(logging)
        {
            logFile.println(toString());
        }
        if(!flag)
        {
            for(int k1 = 0; k1 < super.ruleBase.size(); k1++)
            {
                FuzzyClassifierRule fuzzyclassifierrule1 = (FuzzyClassifierRule)super.ruleBase.elementAt(k1);
                j += determineCoverage(fuzzyclassifierrule1, aflag);
            }

            if(j != dataTable.unSelectedRows)
            {
                printMessage("WARNING: This rule base covers only " + (100 * j) / dataTable.unSelectedRows + "% of all training patterns,\n" + "but this may improve during fuzzy set learning.");
                return;
            } else
            {
                printMessage("This rule base covers all patterns.");
                return;
            }
        } else
        {
            printMessage("This rule base covers all patterns.");
            return;
        }
    }

    protected void bestPerClassRuleLearning(boolean flag, ProgressBar progressbar)
    {
        boolean aflag[] = new boolean[super.outDimension];
        int ai[] = new int[super.outDimension];
        boolean aflag1[] = new boolean[dataTable.rows];
        for(int l1 = 0; l1 < aflag1.length; l1++)
        {
            aflag1[l1] = false;
        }

        int j;
        if(flag)
        {
            j = 1;
        } else
        {
            if(maxRules % super.outDimension != 0)
            {
                maxRules = (maxRules + super.outDimension) - maxRules % super.outDimension;
            }
            j = maxRules / super.outDimension;
        }
        for(int i2 = 0; i2 < super.outDimension; i2++)
        {
            aflag[i2] = false;
            ai[i2] = j;
        }

        if(flag)
        {
            printMessage("Best per class rule learning: the number of rules is determined automatically.");
        } else
        {
            printMessage("Best per class rule learning: selecting the best " + j + " rules for each class.");
        }
        boolean flag3 = false;
        int k = 0;
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        for(int j2 = 0; j2 < super.ruleBase.size() && !flag3; j2++)
        {
            boolean flag1 = false;
            double d = -2 * dataTable.rows;
            Object obj = null;
            boolean flag2 = false;
            for(int k2 = 0; k2 < super.ruleBase.size(); k2++)
            {
                FuzzyClassifierRule fuzzyclassifierrule = (FuzzyClassifierRule)super.ruleBase.elementAt(k2);
                if(!fuzzyclassifierrule.valid() && !aflag[fuzzyclassifierrule.getClassIndex()])
                {
                    double d1 = fuzzyclassifierrule.getPerformance();
                    if(d1 > d)
                    {
                        flag2 = true;
                        d = d1;
                        int i = k2;
                        obj = fuzzyclassifierrule;
                    }
                }
            }

            if(!flag2)
            {
                for(int i3 = 0; i3 < aflag.length; i3++)
                {
                    if(!aflag[i3])
                    {
                        i1 += ai[i3];
                        ai[i3] = 0;
                        aflag[i3] = true;
                        l++;
                        k++;
                    }
                }

            } else
            if(flag)
            {
                int k1 = determineCoverage(((FuzzyRule) (obj)), aflag1);
                if(k1 > 0)
                {
                    j1 += k1;
                    aflag[((FuzzyClassifierRule) (obj)).getClassIndex()] = true;
                    l++;
                    ((FuzzyRule) (obj)).setValid(true);
                } else
                {
                    ((FuzzyRule) (obj)).setPerformance(-2 * dataTable.rows);
                }
            } else
            {
                aflag[((FuzzyClassifierRule) (obj)).getClassIndex()] = true;
                ai[((FuzzyClassifierRule) (obj)).getClassIndex()]--;
                l++;
                i1++;
                ((FuzzyRule) (obj)).setValid(true);
            }
            if(l == super.outDimension)
            {
                for(int j3 = 0; j3 < aflag.length; j3++)
                {
                    aflag[j3] = ai[j3] == 0;
                }

                l = k;
            }
            if(flag)
            {
                flag3 = j1 >= dataTable.unSelectedRows || k == super.outDimension;
                if(progressbar != null)
                {
                    try
                    {
                        progressbar.setProgressPercent((100 * j1) / dataTable.unSelectedRows);
                    }
                    catch(PropertyVetoException _ex) { }
                }
            } else
            {
                flag3 = i1 >= maxRules;
                if(progressbar != null)
                {
                    try
                    {
                        progressbar.setProgressPercent((100 * i1) / maxRules);
                    }
                    catch(PropertyVetoException _ex) { }
                }
            }
        }

        printMessage("Best rules per class selected, trimming the rule base.");
        trimRuleBase();
        printMessage("Rule base with " + super.ruleBase.size() + " rules created.");
        if(logging)
        {
            logFile.println(toString());
        }
        if(!flag)
        {
            for(int l2 = 0; l2 < super.ruleBase.size(); l2++)
            {
                FuzzyClassifierRule fuzzyclassifierrule1 = (FuzzyClassifierRule)super.ruleBase.elementAt(l2);
                j1 += determineCoverage(fuzzyclassifierrule1, aflag1);
            }

            if(j1 != dataTable.unSelectedRows)
            {
                printMessage("WARNING: This rule base covers only " + (100 * j1) / dataTable.unSelectedRows + "% of all training patterns,\n" + "but this may improve during fuzzy set learning.");
                return;
            } else
            {
                printMessage("This rule base covers all patterns.");
                return;
            }
        } else
        {
            printMessage("This rule base covers all patterns.");
            return;
        }
    }

    protected int determineCoverage(FuzzyRule fuzzyrule, boolean aflag[])
    {
        int i = 0;
        for(int j = 0; j < dataTable.rows; j++)
        {
            if(!aflag[j] && dataTable.rowSelector[j] != selected && fuzzyrule.computeDegreeOfFulfilment(dataTable.data[j]) > 0.0D)
            {
                aflag[j] = true;
                i++;
            }
        }

        return i;
    }

    public FuzzyPartition[] getPartitions()
    {
        return partitions;
    }

    protected void printMessage(String s)
    {
        if(statusArea != null)
        {
            statusArea.append(s + "\n");
        } else
        {
            System.out.println(s);
        }
        if(logging)
        {
            logFile.println(s);
        }
    }

    public boolean isRunning()
    {
        return running;
    }

    protected void resetConfusionMatrix()
    {
        for(int i = 0; i < super.outDimension; i++)
        {
            for(int j = 0; j <= super.outDimension; j++)
            {
                confusionMatrix[i][j] = 0;
            }

        }

    }

    public double classifyTrainingData()
    {
        double d = classify(dataTable, false, false, selected, null);
        trainingDataIsClassified = true;
        validationDataIsClassified = false;
        completeDataTableIsClassified = false;
        applicationDataIsClassified = false;
        return d;
    }

    public double classifyValidationData()
    {
        double d = classify(dataTable, false, true, selected, null);
        validationDataIsClassified = true;
        completeDataTableIsClassified = false;
        trainingDataIsClassified = false;
        applicationDataIsClassified = false;
        return d;
    }

    public double classifyTrainingAndValidationData(BufferedWriter bufferedwriter)
    {
        double d = classifyAll(dataTable, bufferedwriter);
        completeDataTableIsClassified = true;
        trainingDataIsClassified = false;
        validationDataIsClassified = false;
        applicationDataIsClassified = false;
        return d;
    }

    public double classifyApplicationData(BufferedWriter bufferedwriter)
    {
        double d = classifyAll(applicationData, bufferedwriter);
        completeDataTableIsClassified = true;
        trainingDataIsClassified = false;
        validationDataIsClassified = false;
        applicationDataIsClassified = true;
        return d;
    }

    protected double classifyAll(DataTable datatable, BufferedWriter bufferedwriter)
    {
        return classify(datatable, true, false, -1, bufferedwriter);
    }

    protected double classify(DataTable datatable, boolean flag, boolean flag1, int i, BufferedWriter bufferedwriter)
    {
        resetConfusionMatrix();
        double d1 = 0.0D;
        double d = 0.0D;
        unclassified = 0;
        misclassified = 0;
        int l = 0;
        for(int i1 = 0; i1 < datatable.rows; i1++)
        {
            if(flag || flag1 == (datatable.rowSelector[i1] == i))
            {
                l++;
                int k = classify(datatable.data[i1]);
                int j;
                if(datatable.isClassified())
                {
                    j = datatable.classIndex[i1];
                } else
                {
                    j = k;
                }
                if(k == -1)
                {
                    unclassified++;
                    misclassified++;
                    d = 1.0D;
                    d1++;
                    if(datatable.isClassified())
                    {
                        confusionMatrix[j][super.outDimension]++;
                    } else
                    {
                        confusionMatrix[0][super.outDimension]++;
                    }
                } else
                {
                    if(k != j)
                    {
                        misclassified++;
                    }
                    if(datatable.isClassified())
                    {
                        confusionMatrix[j][k]++;
                        d = patternError(datatable, i1);
                        d1 += d;
                    } else
                    {
                        confusionMatrix[0][k]++;
                    }
                }
                if(bufferedwriter != null)
                {
                    try
                    {
                        bufferedwriter.write("pattern " + datatable.getLabel(i1) + " ");
                        if(datatable.isClassified())
                        {
                            bufferedwriter.write("\t(" + classNames[j] + ") ");
                        }
                        if(k == -1)
                        {
                            bufferedwriter.write("\tthe pattern was not classified. ");
                        } else
                        {
                            bufferedwriter.write("\tthe classifier predicted " + classNames[k] + ". ");
                        }
                        if(datatable.isClassified())
                        {
                            bufferedwriter.write("\tError = " + FormatString.doubleString(d, 0, 6));
                            if(k == j)
                            {
                                bufferedwriter.write(" => correct");
                            } else
                            {
                                bufferedwriter.write(" => false");
                            }
                        }
                        bufferedwriter.newLine();
                        if(datatable.isClassified())
                        {
                            bufferedwriter.write("  target: ");
                            for(int j1 = 0; j1 < super.outDimension; j1++)
                            {
                                bufferedwriter.write(FormatString.doubleString(datatable.data[i1][super.inDimension + j1], 6, 3));
                            }

                            bufferedwriter.newLine();
                        }
                        bufferedwriter.write("  output: ");
                        for(int k1 = 0; k1 < super.outDimension; k1++)
                        {
                            bufferedwriter.write(FormatString.doubleString(super.classification[k1], 6, 3));
                        }

                        bufferedwriter.newLine();
                        bufferedwriter.newLine();
                    }
                    catch(IOException _ex) { }
                }
            }
        }

        if(bufferedwriter != null)
        {
            try
            {
                if(datatable.isClassified())
                {
                    bufferedwriter.write(l + " patterns processed, " + misclassified + " misclassifications (including " + unclassified + " unclassfied patterns).");
                    bufferedwriter.newLine();
                } else
                {
                    bufferedwriter.write(l + " patterns processed, " + unclassified + " patterns could not be classified.");
                    bufferedwriter.newLine();
                }
            }
            catch(IOException _ex) { }
        }
        return d1;
    }

    protected double patternError(DataTable datatable, int i)
    {
        double d = 0.0D;
        double d2 = 0.0D;
        for(int j = 0; j < super.outDimension; j++)
        {
            double d1 = super.classification[j] - datatable.data[i][super.inDimension + j];
            d2 += d1 * d1;
        }

        return d2;
    }

    public String getTrainingDataConfusionMatrix()
    {
        if(trainingDataIsClassified)
        {
            return getConfusionMatrix(dataTable, dataTable.unSelectedRows);
        } else
        {
            return "The training data was not classified until now.";
        }
    }

    protected String getConfusionMatrix(DataTable datatable, int i)
    {
        StringBuffer stringbuffer = new StringBuffer(1000);
        stringbuffer.append("     | Predicted Class\n");
        stringbuffer.append("     | ");
        for(int i1 = 0; i1 < super.outDimension; i1++)
        {
            stringbuffer.append(FormatString.pad(Integer.toString(i1), 18, false));
            stringbuffer.append(" | ");
        }

        stringbuffer.append(FormatString.pad("n.c.", 18, false));
        stringbuffer.append(" | ");
        stringbuffer.append(FormatString.pad("sum", 18, false));
        stringbuffer.append(" | ");
        stringbuffer.append("\n");
        stringbuffer.append(FormatString.line('-', 6 + (super.outDimension + 2) * 21));
        stringbuffer.append("\n");
        int l;
        if(datatable.isClassified())
        {
            l = super.outDimension;
        } else
        {
            l = 1;
        }
        for(int j1 = 0; j1 < l; j1++)
        {
            if(l > 1)
            {
                stringbuffer.append(FormatString.pad(Integer.toString(j1), 4, false));
            } else
            {
                stringbuffer.append("    ");
            }
            stringbuffer.append(" | ");
            int j = 0;
            for(int k1 = 0; k1 <= super.outDimension; k1++)
            {
                j += confusionMatrix[j1][k1];
                stringbuffer.append(FormatString.pad(Integer.toString(confusionMatrix[j1][k1]), 8, false));
                stringbuffer.append(" (");
                double d = ((double)confusionMatrix[j1][k1] * 100D) / (double)i;
                stringbuffer.append(FormatString.doubleString(d, 6, 2));
                stringbuffer.append("%)");
                stringbuffer.append(" | ");
            }

            stringbuffer.append(FormatString.pad(Integer.toString(j), 8, false));
            stringbuffer.append(" (");
            double d1 = ((double)j * 100D) / (double)i;
            stringbuffer.append(FormatString.doubleString(d1, 6, 2));
            stringbuffer.append("%)");
            stringbuffer.append(" | ");
            stringbuffer.append("\n");
        }

        stringbuffer.append(FormatString.line('-', 6 + (super.outDimension + 2) * 21));
        stringbuffer.append("\n");
        if(l > 1)
        {
            stringbuffer.append(FormatString.pad("sum", 4, false));
            stringbuffer.append(" | ");
            for(int l1 = 0; l1 <= super.outDimension; l1++)
            {
                int k = 0;
                for(int j2 = 0; j2 < super.outDimension; j2++)
                {
                    k += confusionMatrix[j2][l1];
                }

                stringbuffer.append(FormatString.pad(Integer.toString(k), 8, false));
                stringbuffer.append(" (");
                double d2 = ((double)k * 100D) / (double)i;
                stringbuffer.append(FormatString.doubleString(d2, 6, 2));
                stringbuffer.append("%)");
                stringbuffer.append(" | ");
            }

            stringbuffer.append(FormatString.pad(Integer.toString(i), 8, false));
            stringbuffer.append(" (");
            double d3 = 100D;
            stringbuffer.append(FormatString.doubleString(d3, 6, 2));
            stringbuffer.append("%)");
            stringbuffer.append(" | ");
            stringbuffer.append("\n");
            stringbuffer.append(FormatString.line('-', 6 + (super.outDimension + 2) * 21));
            stringbuffer.append("\n");
            stringbuffer.append("\n");
            stringbuffer.append("Correct: ");
            stringbuffer.append(i - misclassified);
            stringbuffer.append(" (");
            stringbuffer.append(FormatString.doubleString(100D - (100D * (double)misclassified) / (double)i, 0, 2));
            stringbuffer.append("%), Misclassified: ");
            stringbuffer.append(misclassified);
            stringbuffer.append(" (");
            stringbuffer.append(FormatString.doubleString((100D * (double)misclassified) / (double)i, 0, 2));
            stringbuffer.append("%)");
        }
        stringbuffer.append("\n\n");
        for(int i2 = 0; i2 < super.outDimension; i2++)
        {
            stringbuffer.append(FormatString.pad(Integer.toString(i2), 4, false));
            stringbuffer.append(": ");
            stringbuffer.append(classNames[i2]);
            stringbuffer.append("\n");
        }

        stringbuffer.append("n.c.: not classified\n\n");
        return stringbuffer.toString();
    }

    protected String getConfusionMatrixTeX(DataTable datatable, int i)
    {
        StringBuffer stringbuffer = new StringBuffer(1000);
        String s = "";
        for(int i1 = 0; i1 < super.outDimension + 2; i1++)
        {
            s = s + "rr|";
        }

        stringbuffer.append("\\begin{tabular}{|r|" + s + "}\n\\hline\n");
        stringbuffer.append("~ & \\multicolumn{" + 2 * (super.outDimension + 2) + "}{l|}{Predicted Class}\\\\\n\\hline\n ~ & ");
        for(int j1 = 0; j1 < super.outDimension; j1++)
        {
            stringbuffer.append("\\multicolumn{2}{c|}{" + j1 + "} &\n");
        }

        stringbuffer.append("\\multicolumn{2}{c|}{n.c.} & \\multicolumn{2}{c|}{sum}\\\\\\hline\n");
        int l;
        if(datatable.isClassified())
        {
            l = super.outDimension;
        } else
        {
            l = 1;
        }
        for(int k1 = 0; k1 < l; k1++)
        {
            if(l > 1)
            {
                stringbuffer.append(k1 + " & ");
            } else
            {
                stringbuffer.append("~ & ");
            }
            int j = 0;
            for(int l1 = 0; l1 <= super.outDimension; l1++)
            {
                j += confusionMatrix[k1][l1];
                stringbuffer.append(confusionMatrix[k1][l1] + " & (");
                double d = ((double)confusionMatrix[k1][l1] * 100D) / (double)i;
                stringbuffer.append(FormatString.doubleString(d, 0, 2) + "\\%) & ");
            }

            stringbuffer.append(j + " & (");
            double d1 = ((double)j * 100D) / (double)i;
            stringbuffer.append(FormatString.doubleString(d1, 0, 2) + "\\%)\\\\\n");
        }

        stringbuffer.append("\\hline\n");
        if(l > 1)
        {
            stringbuffer.append("sum & ");
            for(int i2 = 0; i2 <= super.outDimension; i2++)
            {
                int k = 0;
                for(int k2 = 0; k2 < super.outDimension; k2++)
                {
                    k += confusionMatrix[k2][i2];
                }

                stringbuffer.append(k + " & (");
                double d2 = ((double)k * 100D) / (double)i;
                stringbuffer.append(FormatString.doubleString(d2, 0, 2) + "\\%) & ");
            }

            stringbuffer.append(i + " & (100.00\\%)\\\\\\hline\n");
            stringbuffer.append("\\end{tabular}\n\n\n");
            stringbuffer.append("Correct: " + (i - misclassified) + " (");
            stringbuffer.append(FormatString.doubleString(100D - (100D * (double)misclassified) / (double)i, 0, 2) + "\\%), Misclassified: " + misclassified + " (");
            stringbuffer.append(FormatString.doubleString((100D * (double)misclassified) / (double)i, 0, 2) + "\\%)");
        }
        stringbuffer.append("\n\n");
        stringbuffer.append("\\begin{tabular}{r@{~:~}l}\n");
        for(int j2 = 0; j2 < super.outDimension; j2++)
        {
            stringbuffer.append(j2 + " & " + classNames[j2] + "\\\\\n");
        }

        stringbuffer.append("n.c. & not classified\\\\\n\\end{tabular}\n\n");
        return stringbuffer.toString();
    }

    public void setValidationMode()
    {
        dataTable.resetRowSelection();
        switch(validationMode)
        {
        case 0: // '\0'
            validationPart = 0;
            break;

        case 1: // '\001'
            dataTable.selectPercentageOfRows(validationPart);
            validationPart = (100 * dataTable.unSelectedRows) / dataTable.rows;
            break;

        case 2: // '\002'
            dataTable.selectSubsets(validationPart);
            break;
        }
        selected = 1;
        validationModeIsSet = true;
    }

    protected double computeFuzzySetUpdates()
    {
        double d1 = 0.0D;
        unclassified = 0;
        misclassified = 0;
        for(int l = 0; l < dataTable.rows; l++)
        {
            if(selected != dataTable.rowSelector[l])
            {
                int j = classify(dataTable.data[l]);
                int i = dataTable.classIndex[l];
                if(j == -1)
                {
                    unclassified++;
                    misclassified++;
                    d1++;
                } else
                {
                    if(j != i)
                    {
                        misclassified++;
                    }
                    double d = patternError(dataTable, l);
                    d1 += d;
                }
                for(int i1 = 0; i1 < super.ruleBase.size(); i1++)
                {
                    FuzzyClassifierRule fuzzyclassifierrule = (FuzzyClassifierRule)super.ruleBase.elementAt(i1);
                    int k = fuzzyclassifierrule.getClassIndex();
                    fuzzyclassifierrule.computeError(dataTable.data[l][super.inDimension + k] - super.classification[k]);
                    fuzzyclassifierrule.computeFuzzySetUpdates(asymmetric, minOnly);
                }

                if(!batchLearning)
                {
                    for(int k1 = 0; k1 < super.inDimension; k1++)
                    {
                        partitions[k1].applyUpdates(learningRate, asymmetric, keepOrder, mustOverlap, addUpToOne);
                    }

                    if(useWeights)
                    {
                        for(int i2 = 0; i2 < super.ruleBase.size(); i2++)
                        {
                            ((FuzzyClassifierRule)super.ruleBase.elementAt(i2)).updateWeight(normalWeights, learningRate);
                        }

                    }
                }
            }
        }

        if(batchLearning)
        {
            for(int j1 = 0; j1 < super.inDimension; j1++)
            {
                partitions[j1].applyUpdates(learningRate / (double)dataTable.unSelectedRows, asymmetric, keepOrder, mustOverlap, addUpToOne);
            }

            if(useWeights)
            {
                for(int l1 = 0; l1 < super.ruleBase.size(); l1++)
                {
                    ((FuzzyClassifierRule)super.ruleBase.elementAt(l1)).updateWeight(normalWeights, learningRate / (double)dataTable.unSelectedRows);
                }

            }
        }
        return d1;
    }

    protected void trainFuzzySets()
    {
        String s = "";
        postEvent("training");
        printMessage("Training Fuzzy Sets.");
        int i = 0;
        int k = 0;
        int j = 0;
        int l = dataTable.rows;
        double d = dataTable.rows;
        if(pBar4 != null)
        {
            try
            {
                pBar4.setProgressPercent(0);
            }
            catch(PropertyVetoException _ex) { }
        }
        if(logging)
        {
            logFile.println("\nCurrent Result\t\t\t\tBest Result\nCycle\twrong\terror\t\t\tcycle\twrong\terro" +
"r"
);
        }
        boolean flag;
        do
        {
            i++;
            if(pBar4 != null)
            {
                try
                {
                    pBar4.setProgressPercent((100 * i) / maxEpochs);
                }
                catch(PropertyVetoException _ex) { }
            }
            error = computeFuzzySetUpdates();
            if(validationMode == 2 || validationMode == 1)
            {
                error = classifyValidationData();
            } else
            if(!batchLearning)
            {
                error = classifyTrainingData();
            }
            if(misclassified < l || error < d)
            {
                k = 0;
                j = i;
                l = misclassified;
                d = error;
                s = FormatString.doubleString(error, 0, 6);
                for(int i1 = 0; i1 < super.inDimension; i1++)
                {
                    partitions[i1].copyTo(bestPartitions[i1]);
                }

            } else
            {
                k++;
            }
            if(logging)
            {
                logFile.println(i + "\t" + misclassified + "\t" + FormatString.doubleString(error, 0, 6) + "\t\t" + j + "\t" + l + "\t" + s);
            }
            if(errorGraph != null)
            {
                errorGraph.addPointToGraph(i, error, 0);
                errorGraph.addPointToGraph(i, misclassified, 1);
            }
            flag = i >= maxEpochs || continueEpochs > 0 && k > continueEpochs && i >= minEpochs || error <= stopError || misclassified <= misclassifications || (double)misclassified / (double)dataTable.rows <= misclassPercent || externalStop;
        } while(!flag);
        if(pBar4 != null)
        {
            try
            {
                pBar4.setProgressPercent(100);
            }
            catch(PropertyVetoException _ex) { }
        }
        printMessage("\nTraining fuzzy sets stopped after epoch " + i);
        printMessage("Best classifier was found in cycle " + j + " (" + l + " misclassfications, error = " + FormatString.doubleString(d, 0, 6) + ")");
        printMessage("Restoring best solution.\n");
        for(int j1 = 0; j1 < super.inDimension; j1++)
        {
            bestPartitions[j1].copyTo(partitions[j1]);
        }

    }

    protected void learning()
        throws NefclassInvalidException
    {
        statusArea = ruleStatusArea;
        if(!validationModeIsSet)
        {
            setValidationMode();
        }
        switch(validationMode)
        {
        case 0: // '\0'
        case 1: // '\001'
            statusArea = resultTextArea;
            printMessage("Starting the training process using " + (100D - (double)validationPart) + "% of all cases.");
            if(invokeRuleLearning && !externalStop)
            {
                statusArea = ruleStatusArea;
                if(super.ruleBase.size() == 0)
                {
                    createRuleBase();
                } else
                if(super.ruleBase.size() > 0 && relearnRuleBase)
                {
                    printMessage("Using existing rule base as prior knowledge.");
                    backupRuleBase();
                    createRuleBase();
                } else
                {
                    printMessage("There is a rule base, skipping rule learning.");
                }
            }
            if(invokeFuzzySetLearning && !externalStop)
            {
                newErrorGraph();
                statusArea = fsStatusArea;
                backupPartitions();
                trainFuzzySets();
            }
            statusArea = resultTextArea;
            printMessage("Performance on training data (" + (100D - (double)validationPart) + "% of all cases):");
            error = classifyTrainingData();
            printMessage(dataTable.unSelectedRows + " patterns, " + misclassified + " misclassifications (error = " + FormatString.doubleString(error, 0, 6) + ")");
            if(logging)
            {
                logFile.println();
                logFile.println(getConfusionMatrix(dataTable, dataTable.unSelectedRows));
            }
            if(validationMode == 1)
            {
                printMessage("Performance on validation data (" + validationPart + "% of all cases):");
                error = classifyValidationData();
                printMessage(dataTable.selectedRows + " patterns, " + misclassified + " misclassifications (error = " + FormatString.doubleString(error, 0, 6) + ")");
                if(logging)
                {
                    logFile.println();
                    logFile.println(getConfusionMatrix(dataTable, dataTable.selectedRows));
                }
            }
            if(invokeInternalPruning)
            {
                pruning();
            }
            break;

        case 2: // '\002'
            Vector vector = copyRuleBase(super.ruleBase);
            setInitialPartitions();
            statusArea = resultTextArea;
            printMessage("Starting the training process using " + validationPart + "-fold cross validation.");
            estimatedError = 0.0D;
            estimatedErrorStdError = 0.0D;
            if(invokeFuzzySetLearning)
            {
                backupPartitions();
            }
            if(invokeRuleLearning && super.ruleBase.size() > 0 && relearnRuleBase)
            {
                backupRuleBase();
            }
            for(int j = 1; j <= validationPart && !externalStop; j++)
            {
                selected = j;
                printMessage("Validation cycle " + selected + " of " + validationPart);
                dataTable.countSelection(selected);
                if(invokeRuleLearning)
                {
                    try
                    {
                        if(pBar1 != null)
                        {
                            pBar1.setProgressPercent(0);
                        }
                        if(pBar2 != null)
                        {
                            pBar2.setProgressPercent(0);
                        }
                        if(pBar3 != null)
                        {
                            pBar3.setProgressPercent(0);
                        }
                    }
                    catch(PropertyVetoException _ex) { }
                    statusArea = ruleStatusArea;
                    if(super.ruleBase.size() == 0)
                    {
                        createRuleBase();
                    } else
                    if(super.ruleBase.size() > 0 && relearnRuleBase)
                    {
                        printMessage("Using existing rule base as prior knowledge.");
                        createRuleBase();
                    } else
                    {
                        printMessage("There is a rule base, skipping rule learning.");
                    }
                }
                if(invokeFuzzySetLearning)
                {
                    newErrorGraph();
                    statusArea = fsStatusArea;
                    trainFuzzySets();
                }
                statusArea = resultTextArea;
                printMessage("\nResult of validaton cycle " + selected + ":");
                error = classifyTrainingData();
                printMessage("Training data: " + dataTable.unSelectedRows + " patterns, " + misclassified + " misclassifications (error = " + FormatString.doubleString(error, 0, 6) + ")");
                if(logging)
                {
                    logFile.println();
                    logFile.println(getConfusionMatrix(dataTable, dataTable.unSelectedRows));
                }
                error = classifyValidationData();
                printMessage("Validation data: " + dataTable.selectedRows + " patterns, " + misclassified + " misclassifications (error = " + FormatString.doubleString(error, 0, 6) + ")");
                if(logging)
                {
                    logFile.println();
                    logFile.println(getConfusionMatrix(dataTable, dataTable.selectedRows));
                }
                if(invokeInternalPruning)
                {
                    pruning();
                }
                double d = (double)misclassified / (double)dataTable.selectedRows;
                printMessage("Mean = " + misclassified + " / " + dataTable.selectedRows + " = " + FormatString.doubleString(d, 0, 6));
                estimatedError += d;
                estimatedErrorStdError += d * d;
                printMessage(FormatString.line('=', 40));
                printMessage("\n");
                super.ruleBase.removeAllElements();
                super.ruleBase = copyRuleBase(vector);
                resetPartitions();
                statusArea.setText("");
            }

            double d1 = estimatedErrorStdError - (estimatedError * estimatedError) / (double)validationPart;
            estimatedErrorStdError = d1 / ((double)validationPart - 1.0D);
            estimatedErrorStdError = Math.sqrt(estimatedErrorStdError / (double)validationPart);
            estimatedError /= validationPart;
            d1 = Math.sqrt(d1 / (double)validationPart);
            printMessage("Final cycle, using now all cases for traininig.");
            validationMode = 0;
            int i = validationPart;
            validationPart = 0;
            dataTable.resetRowSelection();
            selected = 1;
            if(invokeRuleLearning && !externalStop)
            {
                if(super.ruleBase.size() == 0)
                {
                    createRuleBase();
                } else
                if(super.ruleBase.size() > 0 && relearnRuleBase)
                {
                    printMessage("Using existing rule base as prior knowledge.");
                    createRuleBase();
                } else
                {
                    printMessage("There is a rule base, skipping rule learning.");
                }
            }
            if(invokeFuzzySetLearning && !externalStop)
            {
                newErrorGraph();
                statusArea = fsStatusArea;
                trainFuzzySets();
            }
            statusArea = resultTextArea;
            printMessage("\nFinal result, using the complete data set:");
            error = classifyTrainingAndValidationData(null);
            printMessage(dataTable.unSelectedRows + " patterns, " + misclassified + " misclassifications (error = " + FormatString.doubleString(error, 0, 6) + ")");
            if(logging)
            {
                logFile.println();
                logFile.println(getConfusionMatrix(dataTable, dataTable.unSelectedRows));
            }
            if(invokeInternalPruning)
            {
                pruning();
            }
            printMessage("Estimated number of misclassifications per pattern for unseen data:");
            printMessage("mean = " + FormatString.doubleString(estimatedError, 0, 6) + ", standard deviation = " + FormatString.doubleString(d1, 0, 6) + ", n = " + i);
            printMessage("99% confidence interval for the error estimation: ");
            printMessage(FormatString.doubleString(estimatedError, 0, 6) + " +/- " + FormatString.doubleString(2.5800000000000001D * estimatedErrorStdError, 0, 6));
            validationMode = 2;
            validationPart = i;
            validationModeIsSet = false;
            break;
        }
        if(logging)
        {
            logFile.flush();
        }
    }

    protected void newErrorGraph()
    {
        if(flPanel != null)
        {
            if((validationMode == 1 || validationMode == 2) && validationPart > 0 && validationModeIsSet)
            {
                error = classifyValidationData();
            } else
            {
                error = classifyTrainingData();
            }
            int i = flPanel.getParent().getSize().width - 25;
            errorGraph = new FunctionPane(i, (int)((2D * (double)i) / 5D), 0.0D, maxEpochs, 0.0D, Math.max(misclassified, (int)error) + 1);
            errorGraph.setNumberOfGraphs(2);
            errorGraph.setName(0, "Error");
            errorGraph.setName(1, "Misclassifications");
            flPanel.add(errorGraph);
            flPanel.validate();
            flPanel.getParent().validate();
            ((ScrollPane)flPanel.getParent()).setScrollPosition(0, flPanel.getSize().height);
            flPanel.getParent().repaint();
            return;
        } else
        {
            errorGraph = null;
            return;
        }
    }

    public void classifyApplicationData(TextArea textarea)
    {
        postEvent("classifying");
        try
        {
            BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter("Result.log"));
            bufferedwriter.write("Classification of data file " + applicationData.filename);
            bufferedwriter.newLine();
            bufferedwriter.newLine();
            if(textarea != null)
            {
                textarea.append("Classification of  application data:\n\n");
            }
            double d = classifyApplicationData(bufferedwriter);
            if(textarea != null)
            {
                textarea.append(getConfusionMatrix(applicationData, applicationData.rows));
                if(applicationData.isClassified())
                {
                    textarea.append("Error: " + FormatString.doubleString(d, 0, 6));
                }
                textarea.append("\n\nThe detailed classification result was written to 'result.log'.");
            }
            bufferedwriter.close();
        }
        catch(IOException _ex) { }
        postEvent("ready");
    }

    public void classifyData(TextArea textarea)
    {
        postEvent("classifying");
        if(!performancesComputed)
        {
            computeRulePerformance(super.ruleBase);
        }
        try
        {
            BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter("Result.log"));
            bufferedwriter.write("Classification of data file " + dataTable.filename);
            bufferedwriter.newLine();
            bufferedwriter.newLine();
            if(textarea != null)
            {
                textarea.append("Classification of " + dataTable.filename + ":\n\n");
            }
            if(validationMode == 1 && textarea != null && validationModeIsSet)
            {
                double d = classifyTrainingData();
                textarea.append("Classification of the data used for training (" + dataTable.unSelectedRows + " cases = " + (100D - (double)validationPart) + "%):\n\n");
                textarea.append(getConfusionMatrix(dataTable, dataTable.unSelectedRows));
                textarea.append("Error: " + FormatString.doubleString(d, 0, 6));
                d = classifyValidationData();
                textarea.append("\n\nClassification of the data used for validation (" + dataTable.selectedRows + " cases = " + validationPart + "%):\n\n");
                textarea.append(getConfusionMatrix(dataTable, dataTable.selectedRows));
                textarea.append("Error: " + FormatString.doubleString(d, 0, 6));
            }
            if(textarea != null)
            {
                textarea.append("\n\nClassification of the complete data set:\n\n");
            }
            double d1 = classifyTrainingAndValidationData(bufferedwriter);
            if(textarea != null)
            {
                textarea.append(getConfusionMatrix(dataTable, dataTable.rows));
                textarea.append("Error: " + FormatString.doubleString(d1, 0, 6));
                textarea.append("\n\nThe detailed classification result was written to 'result.log'.");
            }
            bufferedwriter.close();
        }
        catch(IOException _ex) { }
        postEvent("ready");
    }

    public boolean backupRuleBase()
    {
        boolean flag = false;
        if(super.ruleBase != null && super.ruleBase.size() > 0)
        {
            ruleBaseBackUp = copyRuleBase(super.ruleBase);
            flag = true;
        }
        return flag;
    }

    protected Vector copyRuleBase(Vector vector)
    {
        Vector vector1 = (Vector)vector.clone();
        for(int i = 0; i < vector.size(); i++)
        {
            FuzzyClassifierRule fuzzyclassifierrule = (FuzzyClassifierRule)vector.elementAt(i);
            vector1.setElementAt((FuzzyClassifierRule)fuzzyclassifierrule.clone(), i);
        }

        return vector1;
    }

    protected void copyRuleBase()
    {
        ruleBaseCopy = (Vector)super.ruleBase.clone();
        for(int i = 0; i < super.ruleBase.size(); i++)
        {
            FuzzyClassifierRule fuzzyclassifierrule = (FuzzyClassifierRule)super.ruleBase.elementAt(i);
            ruleBaseCopy.setElementAt((FuzzyClassifierRule)fuzzyclassifierrule.clone(), i);
        }

    }

    public Vector getRuleBaseCopy()
    {
        copyRuleBase();
        return ruleBaseCopy;
    }

    public int isRuleConsistentWithRuleBase(FuzzyClassifierRule fuzzyclassifierrule, Vector vector)
    {
        String s = "";
        FuzzyClassifierRule fuzzyclassifierrule1 = null;
        int i = -1;
        boolean flag3 = false;
        boolean flag2 = false;
        for(int j = 0; j < vector.size() && i == -1; j++)
        {
            fuzzyclassifierrule1 = (FuzzyClassifierRule)vector.elementAt(j);
            if(fuzzyclassifierrule1 != fuzzyclassifierrule)
            {
                flag2 = fuzzyclassifierrule.equalAntecedent(fuzzyclassifierrule1);
                boolean flag;
                boolean flag1;
                if(!flag2)
                {
                    flag1 = fuzzyclassifierrule.isSpecializationOf(fuzzyclassifierrule1);
                    if(!flag1)
                    {
                        flag = fuzzyclassifierrule.isGeneralizationOf(fuzzyclassifierrule1);
                        if(flag)
                        {
                            s = " is a generalization of ";
                        }
                    } else
                    {
                        flag = false;
                        s = " is a specialization of ";
                    }
                } else
                {
                    flag = false;
                    flag1 = false;
                    s = " is equal to ";
                }
                if(flag2 || flag || flag1)
                {
                    i = j;
                }
                if(i > -1)
                {
                    flag3 = !fuzzyclassifierrule.equalConsequent(fuzzyclassifierrule1);
                }
            } else
            {
                flag2 = true;
            }
        }

        if(i > -1)
        {
            if(flag2 && !flag3)
            {
                s = "The rules " + fuzzyclassifierrule.getName() + " and " + fuzzyclassifierrule1.getName() + " are identical";
            } else
            {
                s = "The antecdent of rule " + fuzzyclassifierrule.getName() + s + "rule " + fuzzyclassifierrule1.getName();
            }
            if(flag3)
            {
                s = s + ". Both rules are contradictory, because the consequents are " + "different. One of these two rules must be deleted.";
            } else
            {
                s = s + ". One of these two rules is superfluous and must be " + "deleted.";
            }
        }
        consistencyCheckMessage = s;
        return i;
    }

    public boolean isRuleBaseCopyConsistent()
    {
        boolean flag = true;
        for(int i = 0; i < ruleBaseCopy.size() && flag; i++)
        {
            flag = flag && isRuleConsistentWithRuleBase((FuzzyClassifierRule)ruleBaseCopy.elementAt(i), ruleBaseCopy) == -1;
        }

        if(flag)
        {
            consistencyCheckMessage = "The rule base copy is consistent.";
        }
        return flag;
    }

    public String getConsistencyCheckMessage()
    {
        return consistencyCheckMessage;
    }

    public void setRuleBaseCopy()
    {
        if(ruleBaseCopy != null)
        {
            ruleBaseBackUp = super.ruleBase;
            super.ruleBase = ruleBaseCopy;
        }
    }

    public boolean restoreRuleBase()
    {
        boolean flag = false;
        if(ruleBaseBackUp != null && ruleBaseBackUp.size() > 0)
        {
            ruleBaseCopy = super.ruleBase;
            super.ruleBase = ruleBaseBackUp;
            ruleBaseBackUp = ruleBaseCopy;
            flag = true;
        }
        return flag;
    }

    public void setInterface(ProgressBar progressbar, ProgressBar progressbar1, ProgressBar progressbar2, ProgressBar progressbar3, TextArea textarea, TextArea textarea1, TextArea textarea2, 
            FunctionLayoutPanel functionlayoutpanel)
    {
        ruleStatusArea = textarea;
        fsStatusArea = textarea1;
        resultTextArea = textarea2;
        statusArea = textarea;
        pBar1 = progressbar;
        pBar2 = progressbar1;
        pBar3 = progressbar2;
        pBar4 = progressbar3;
        flPanel = functionlayoutpanel;
    }

    protected boolean[] variablesUsedInRuleBase(Vector vector)
    {
        boolean aflag[] = new boolean[super.inDimension];
        for(int i = 0; i < super.inDimension; i++)
        {
            aflag[i] = false;
        }

        for(int j = 0; j < super.inDimension; j++)
        {
            for(int k = 0; k < vector.size() && !aflag[j]; k++)
            {
                aflag[j] = ((FuzzyRule)vector.elementAt(k)).variableIsUsed(j);
            }

        }

        return aflag;
    }

    protected int[] rulesPerClass(Vector vector)
    {
        int ai[] = new int[super.outDimension];
        for(int i = 0; i < super.outDimension; i++)
        {
            ai[i] = 0;
        }

        for(int j = 0; j < vector.size(); j++)
        {
            ai[((FuzzyClassifierRule)vector.elementAt(j)).getClassIndex()]++;
        }

        return ai;
    }

    protected int[] classificationsPerRule()
    {
        int ai[] = new int[super.ruleBase.size()];
        for(int j = 0; j < super.ruleBase.size(); j++)
        {
            ai[j] = 0;
        }

        for(int k = 0; k < dataTable.rows; k++)
        {
            if(dataTable.rowSelector[k] != selected)
            {
                int i = classify(dataTable.data[k]);
                if(i == dataTable.classIndex[k] && super.maxRule[i] > -1)
                {
                    ai[super.maxRule[i]]++;
                }
            }
        }

        return ai;
    }

    protected boolean makeRuleBaseCopyConsistent()
    {
        int ai[] = rulesPerClass(ruleBaseCopy);
        boolean flag = false;
        boolean flag1 = false;
        int i = 0;
        for(FuzzyClassifierRule fuzzyclassifierrule = (FuzzyClassifierRule)ruleBaseCopy.elementAt(0); !flag1 && fuzzyclassifierrule != null;)
        {
            int j = isRuleConsistentWithRuleBase(fuzzyclassifierrule, ruleBaseCopy);
            if(j > -1)
            {
                FuzzyClassifierRule fuzzyclassifierrule1 = (FuzzyClassifierRule)ruleBaseCopy.elementAt(j);
                if(fuzzyclassifierrule.getPerformance() < fuzzyclassifierrule1.getPerformance())
                {
                    if(ai[fuzzyclassifierrule.getClassIndex()] > 1)
                    {
                        ruleBaseCopy.removeElementAt(i);
                        ai[fuzzyclassifierrule.getClassIndex()]--;
                        if(i < ruleBaseCopy.size())
                        {
                            fuzzyclassifierrule = (FuzzyClassifierRule)ruleBaseCopy.elementAt(i);
                        } else
                        {
                            flag1 = true;
                            flag = true;
                        }
                    } else
                    if(ai[fuzzyclassifierrule1.getClassIndex()] > 1)
                    {
                        ruleBaseCopy.removeElementAt(j);
                        ai[fuzzyclassifierrule1.getClassIndex()]--;
                    } else
                    {
                        flag1 = true;
                    }
                } else
                if(ai[fuzzyclassifierrule1.getClassIndex()] > 1)
                {
                    ruleBaseCopy.removeElementAt(j);
                    ai[fuzzyclassifierrule1.getClassIndex()]--;
                } else
                if(ai[fuzzyclassifierrule.getClassIndex()] > 1)
                {
                    ruleBaseCopy.removeElementAt(i);
                    ai[fuzzyclassifierrule.getClassIndex()]--;
                    if(i < ruleBaseCopy.size())
                    {
                        fuzzyclassifierrule = (FuzzyClassifierRule)ruleBaseCopy.elementAt(i);
                    } else
                    {
                        flag1 = true;
                        flag = true;
                    }
                } else
                {
                    flag1 = true;
                }
            } else
            {
                flag1 = ++i >= ruleBaseCopy.size();
                if(!flag1)
                {
                    fuzzyclassifierrule = (FuzzyClassifierRule)ruleBaseCopy.elementAt(i);
                } else
                {
                    flag = true;
                }
            }
        }

        return flag;
    }

    protected boolean pruneByCorrelation()
    {
        double d = 2D;
        int i = -1;
        boolean flag = false;
        boolean flag1 = false;
        boolean aflag[] = variablesUsedInRuleBase(super.ruleBase);
        printMessage("Prune variables by correlation.");
        for(int j = 0; j < super.inDimension; j++)
        {
            if(Math.abs(dataTable.correlations[j][super.inDimension]) < d && aflag[j])
            {
                d = Math.abs(dataTable.correlations[j][super.inDimension]);
                i = j;
            }
        }

        if(i > -1)
        {
            printMessage("Trying to remove variable " + partitions[i].getName());
            copyRuleBase();
            for(int k = 0; k < ruleBaseCopy.size(); k++)
            {
                FuzzyClassifierRule fuzzyclassifierrule = (FuzzyClassifierRule)ruleBaseCopy.elementAt(k);
                if(fuzzyclassifierrule.numberOfVariablesUsed() > 1 && fuzzyclassifierrule.variableIsUsed(i))
                {
                    fuzzyclassifierrule.deleteAntecedent(i);
                    flag1 = true;
                }
            }

            if(flag1)
            {
                printMessage("Variable " + partitions[i].getName() + " removed.");
                printMessage("Checking rule base for consistency.");
                computeRulePerformance(ruleBaseCopy);
                flag = makeRuleBaseCopyConsistent();
                if(!flag)
                {
                    printMessage("It is not possible to make the rule base consistent.");
                }
            } else
            {
                printMessage("Variable " + partitions[i].getName() + " cannot be removed.");
                flag = false;
            }
        }
        return flag;
    }

    protected boolean pruneByClassification()
    {
        int ai[] = rulesPerClass(super.ruleBase);
        int ai1[] = classificationsPerRule();
        int i = dataTable.rows;
        int j = -1;
        int k = 0;
        boolean flag = false;
        boolean flag1 = false;
        printMessage("Prune rules by classification frequency.");
        while(!flag) 
        {
            for(int l = 0; l < super.ruleBase.size(); l++)
            {
                if(ai1[l] < i)
                {
                    i = ai1[l];
                    j = l;
                }
            }

            if(j > -1)
            {
                if(ai[((FuzzyClassifierRule)super.ruleBase.elementAt(j)).getClassIndex()] > 1)
                {
                    flag = true;
                } else
                {
                    ai1[j] = dataTable.rows;
                    k++;
                    j = -1;
                    flag = k >= super.ruleBase.size();
                }
            } else
            {
                flag = true;
            }
        }
        if(j > -1)
        {
            copyRuleBase();
            ruleBaseCopy.removeElementAt(j);
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        return flag1;
    }

    protected boolean pruneByRedundancy()
    {
        int ai[][] = new int[super.inDimension][];
        printMessage("Prune linguistic terms by minimum frequency.");
        for(int i1 = 0; i1 < super.inDimension; i1++)
        {
            ai[i1] = new int[partitions[i1].getNumberOfFuzzySets()];
            for(int j1 = 0; j1 < ai[i1].length; j1++)
            {
                boolean flag = false;
                for(int l1 = 0; l1 < super.ruleBase.size() && !flag; l1++)
                {
                    FuzzyClassifierRule fuzzyclassifierrule = (FuzzyClassifierRule)super.ruleBase.elementAt(l1);
                    flag = fuzzyclassifierrule.usesTerm(i1, j1) && fuzzyclassifierrule.numberOfVariablesUsed() > 1;
                }

                if(flag)
                {
                    ai[i1][j1] = 0;
                } else
                {
                    ai[i1][j1] = dataTable.rows;
                }
            }

        }

        for(int k1 = 0; k1 < dataTable.rows; k1++)
        {
            if(dataTable.rowSelector[k1] != selected)
            {
                for(int i2 = 0; i2 < super.inDimension; i2++)
                {
                    int l = partitions[i2].minFSIndex(dataTable.data[k1][i2]);
                    if(l > -1)
                    {
                        ai[i2][l]++;
                    }
                }

            }
        }

        int k = dataTable.rows;
        int i = -1;
        int j = -1;
        for(int j2 = 0; j2 < super.inDimension; j2++)
        {
            for(int k2 = 0; k2 < ai[j2].length; k2++)
            {
                if(ai[j2][k2] < k)
                {
                    k = ai[j2][k2];
                    i = j2;
                    j = k2;
                }
            }

        }

        boolean flag1 = false;
        if(i > -1)
        {
            copyRuleBase();
            double d = (100D * (double)ai[i][j]) / (double)dataTable.unSelectedRows;
            printMessage("Try to remove the term '" + partitions[i].getName() + " is " + partitions[i].getFuzzySetName(j) + "' that is minimal in " + FormatString.doubleString(d, 0, 2) + "%.");
            for(int l2 = 0; l2 < ruleBaseCopy.size(); l2++)
            {
                FuzzyClassifierRule fuzzyclassifierrule1 = (FuzzyClassifierRule)ruleBaseCopy.elementAt(l2);
                if(fuzzyclassifierrule1.usesTerm(i, j) && fuzzyclassifierrule1.numberOfVariablesUsed() > 1)
                {
                    fuzzyclassifierrule1.deleteAntecedent(i);
                    flag1 = true;
                }
            }

        }
        boolean flag2;
        if(flag1)
        {
            printMessage("Term removed, checking rule base for consistency.");
            flag2 = makeRuleBaseCopyConsistent();
            if(!flag2)
            {
                printMessage("It is not possible to make the rule base consistent.");
            }
        } else
        {
            flag2 = false;
        }
        return flag2;
    }

    protected boolean pruneByFuzzySetSupport()
    {
        double ad[][] = new double[super.inDimension][];
        printMessage("Prune linguistic terms by fuzzy set support.");
        for(int k = 0; k < super.inDimension; k++)
        {
            if(partitions[k].getNumberOfFuzzySets() > 2)
            {
                ad[k] = new double[partitions[k].getNumberOfFuzzySets()];
                for(int l = 0; l < ad[k].length; l++)
                {
                    boolean flag = false;
                    for(int j1 = 0; j1 < super.ruleBase.size() && !flag; j1++)
                    {
                        FuzzyClassifierRule fuzzyclassifierrule = (FuzzyClassifierRule)super.ruleBase.elementAt(j1);
                        flag = fuzzyclassifierrule.usesTerm(k, l) && fuzzyclassifierrule.numberOfVariablesUsed() > 1;
                    }

                    if(flag)
                    {
                        ad[k][l] = (100D * partitions[k].getFuzzySetSupport(l)) / (partitions[k].getUpperBound() - partitions[k].getLowerBound());
                    } else
                    {
                        ad[k][l] = 0.0D;
                    }
                }

            }
        }

        double d = 0.0D;
        int i = -1;
        int j = -1;
        for(int i1 = 0; i1 < super.inDimension; i1++)
        {
            if(partitions[i1].getNumberOfFuzzySets() > 2)
            {
                for(int k1 = 0; k1 < ad[i1].length; k1++)
                {
                    if(ad[i1][k1] > d)
                    {
                        d = ad[i1][k1];
                        i = i1;
                        j = k1;
                    }
                }

            }
        }

        boolean flag1 = false;
        if(i > -1)
        {
            copyRuleBase();
            printMessage("Try to remove the term '" + partitions[i].getName() + " is " + partitions[i].getFuzzySetName(j) + "' where the fuzzy set covers  " + FormatString.doubleString(ad[i][j], 0, 2) + "% of the domain.");
            for(int l1 = 0; l1 < ruleBaseCopy.size(); l1++)
            {
                FuzzyClassifierRule fuzzyclassifierrule1 = (FuzzyClassifierRule)ruleBaseCopy.elementAt(l1);
                if(fuzzyclassifierrule1.usesTerm(i, j) && fuzzyclassifierrule1.numberOfVariablesUsed() > 1)
                {
                    fuzzyclassifierrule1.deleteAntecedent(i);
                    flag1 = true;
                }
            }

        }
        boolean flag2;
        if(flag1)
        {
            printMessage("Term removed, checking rule base for consistency.");
            flag2 = makeRuleBaseCopyConsistent();
            if(!flag2)
            {
                printMessage("It is not possible to make the rule base consistent.");
            }
        } else
        {
            flag2 = false;
        }
        return flag2;
    }

    public void automaticPruning()
        throws NefclassInvalidException
    {
        invokePruning = true;
        learning(false, true);
    }

    public void createAndPrune()
        throws NefclassInvalidException
    {
        invokePruning = true;
        invokeInternalPruning = false;
        learning(true, true);
    }

    public void mixedCreateAndPrune()
        throws NefclassInvalidException
    {
        invokePruning = false;
        invokeInternalPruning = true;
        learning(true, true);
    }

    protected void pruning()
        throws NefclassInvalidException
    {
        boolean flag = false;
        boolean flag1 = true;
        int k = 0;
        boolean flag3 = false;
        postEvent("pruning");
        statusArea = ruleStatusArea;
        if(invokeRuleLearning && !invokeInternalPruning)
        {
            printMessage("Initial learning process to create a classifier.");
            if(!validationModeIsSet)
            {
                setValidationMode();
            }
            learning();
            postEvent("pruning");
            invokeRuleLearning = false;
        }
        if(invokeInternalPruning)
        {
            flag3 = true;
            invokeInternalPruning = false;
            k = validationMode;
            invokeRuleLearning = false;
            if(validationMode == 2)
            {
                validationMode = 1;
            }
        } else
        {
            backupRuleBase();
            backupPartitions();
        }
        printMessage("Starting to prune the classifier with " + super.ruleBase.size() + " rules.");
        if(flag3 && k == 2)
        {
            printMessage("This is the pruning process for validation cycle " + selected);
        }
        if(!validationModeIsSet)
        {
            setValidationMode();
        }
        double d;
        if(validationMode == 1)
        {
            d = classifyValidationData();
            printMessage("Performance on validation data (" + dataTable.selectedRows + " patterns) before pruning:");
        } else
        {
            d = classifyTrainingData();
            printMessage("Performance on training data (" + dataTable.unSelectedRows + " patterns) before pruning:");
        }
        int i = misclassified;
        printMessage(i + " misclassifications (error = " + FormatString.doubleString(d, 0, 6) + ")");
        if(logging)
        {
            logFile.println();
            if(validationMode == 1)
            {
                logFile.println(getConfusionMatrix(dataTable, dataTable.selectedRows));
            } else
            {
                logFile.println(getConfusionMatrix(dataTable, dataTable.unSelectedRows));
            }
        }
        for(int j = 0; j < 4 && !externalStop;)
        {
            statusArea = ruleStatusArea;
            boolean flag2 = false;
            switch(j)
            {
            case 0: // '\0'
                flag = pruneByCorrelation();
                break;

            case 1: // '\001'
                flag = pruneByClassification();
                break;

            case 2: // '\002'
                flag = pruneByRedundancy();
                break;

            case 3: // '\003'
                flag = pruneByFuzzySetSupport();
                break;
            }
            if(flag && !externalStop)
            {
                Vector vector = super.ruleBase;
                FuzzyPartition afuzzypartition[] = copyPartitions(partitions);
                super.ruleBase = ruleBaseCopy;
                printMessage("The rule base now contains " + super.ruleBase.size() + " rules");
                printMessage("Starting the training process for the fuzzy sets.");
                if(!validationModeIsSet)
                {
                    setValidationMode();
                }
                if(!externalStop)
                {
                    learning();
                    postEvent("pruning");
                }
                if(validationMode == 1)
                {
                    error = classifyValidationData();
                } else
                {
                    error = classifyTrainingData();
                }
                if(error > d && misclassified > i)
                {
                    printMessage("Pruning could not improve the classifier.");
                    printMessage("The previous version is restored.\n");
                    copyPartitions(afuzzypartition, partitions);
                    super.ruleBase = vector;
                    flag2 = true;
                } else
                {
                    d = error;
                    i = misclassified;
                    printMessage("The classifier was improved.\n");
                    if(logging)
                    {
                        logFile.println("The pruned rule base contains " + super.ruleBase.size() + " rules:");
                        logFile.println(toString());
                        logFile.println();
                    }
                }
            } else
            {
                j++;
            }
            if(flag2)
            {
                j++;
            }
        }

        computeRulePerformance(super.ruleBase);
        printMessage("Further pruning cannot improve the classfier.\n");
        printMessage("Final pruning result:");
        printMessage("The rule base contains " + super.ruleBase.size() + " rules.");
        if(logging)
        {
            logFile.println(toString());
            logFile.println();
        }
        if(validationMode == 1)
        {
            error = classifyValidationData();
            printMessage("Performance on validation data (" + dataTable.selectedRows + " patterns):");
        } else
        {
            error = classifyTrainingData();
            printMessage("Performance on training data (" + dataTable.unSelectedRows + " patterns):");
        }
        printMessage(misclassified + " misclassifications (error = " + FormatString.doubleString(error, 0, 6) + ").");
        if(logging)
        {
            if(validationMode == 1)
            {
                logFile.println(getConfusionMatrix(dataTable, dataTable.selectedRows));
            } else
            {
                logFile.println(getConfusionMatrix(dataTable, dataTable.unSelectedRows));
            }
        }
        if(flag3)
        {
            invokeInternalPruning = true;
            invokeRuleLearning = true;
            validationMode = k;
        }
        if(hasCategoricalVariables)
        {
            trimListPartitions();
        }
    }

    public void setLogFile(PrintWriter printwriter)
    {
        logFile = printwriter;
    }

    public void setLogging(boolean flag)
    {
        logging = flag;
    }

    protected void printParameters()
    {
        if(logging)
        {
            StringBuffer stringbuffer = new StringBuffer(100);
            logFile.println("Training data: " + dataTable.filename);
            logFile.println("Training for at most " + maxEpochs + " cycles " + "and for at least " + minEpochs + " cycles.");
            if(continueEpochs > 0)
            {
                logFile.println("Continue for " + continueEpochs + " cycles after a local optimum was found.");
            }
            logFile.println("Learning will stop at " + misclassifications + " misclassifications.");
            switch(validationMode)
            {
            case 0: // '\0'
                logFile.println("There is no validation.");
                break;

            case 1: // '\001'
                logFile.println(validationPart + "% of the data are used for " + "single validation (test validation mode)");
                break;

            case 2: // '\002'
                logFile.println("Validation mode is " + validationPart + "-fold cross validation.");
                break;
            }
            logFile.println("Parameter: LearningRate = " + learningRate + ", Fuzzy set constraints: ");
            if(keepOrder)
            {
                stringbuffer.append("keep order, ");
            }
            if(mustOverlap)
            {
                stringbuffer.append("must overlap, ");
            }
            if(!asymmetric)
            {
                stringbuffer.append("must stay symmetrical, ");
            }
            if(addUpToOne)
            {
                stringbuffer.append("degrees of membership must add up to 1,");
            }
            logFile.println(stringbuffer);
            if(useWeights)
            {
                if(normalWeights)
                {
                    logFile.println("normalized rule weights are used (weights are from [0,1])");
                } else
                {
                    logFile.println("rule weights are used and can assume arbitrary values.");
                }
            } else
            {
                logFile.println("rule weights are not used.");
            }
            if(super.ruleBase.size() > 0)
            {
                logFile.println("There are " + super.ruleBase.size() + " initial rules: ");
                logFile.println(toString());
            }
            if(invokeRuleLearning)
            {
                if(super.ruleBase.size() > 0)
                {
                    if(relearnRuleBase)
                    {
                        logFile.println("The rules are used as prior knowledge.");
                    } else
                    {
                        logFile.println("Because 'relearn rule base' is not selected, rule learning will not be invoked.");
                    }
                }
                if(super.ruleBase.size() == 0 || relearnRuleBase)
                {
                    if(ruleBaseSizeAutomatic)
                    {
                        logFile.println("The number of rules will be determined automatically.");
                    } else
                    {
                        switch(ruleBaseCreateMethod)
                        {
                        case 0: // '\0'
                            logFile.println("The rule base will consist of  the first " + maxRules + " rules.");
                            break;

                        case 1: // '\001'
                            logFile.println("The rule base will consist of the best " + maxRules + " rules.");
                            break;

                        case 2: // '\002'
                            int i = maxRules;
                            if(i % super.outDimension != 0)
                            {
                                i = (i + super.outDimension) - i % super.outDimension;
                            }
                            logFile.print("The rule base will consist of " + i + " rules using the best ");
                            if(i / super.outDimension == 1)
                            {
                                logFile.println("rule for each class.");
                            } else
                            {
                                logFile.println(i / super.outDimension + " rules for each class.");
                            }
                            break;
                        }
                    }
                }
            } else
            {
                logFile.println("Rule learning will not be invoked.");
            }
            if(individually)
            {
                logFile.println("Each variable uses an indvidual number and type of fuzzy sets:");
                for(int j = 0; j < super.inDimension; j++)
                {
                    logFile.println(partitions[j].getName() + " uses " + partitions[j].getNumberOfFuzzySets() + " " + partitions[j].getFuzzySetTypeName(0) + " fuzzy sets.");
                }

            } else
            {
                logFile.println("Each variable uses " + partitions[0].getNumberOfFuzzySets() + " " + partitions[0].getFuzzySetTypeName(0) + " fuzzy sets.");
            }
            if(invokeFuzzySetLearning)
            {
                logFile.println("Fuzzy sets will be trained.");
            } else
            {
                logFile.println("Fuzzy set learning will not be invoked.");
            }
            if(invokePruning)
            {
                if(invokeRuleLearning)
                {
                    logFile.println("The created classifier will be pruned.");
                } else
                {
                    logFile.println("This is an automatic pruning cycle.");
                }
            }
            if(invokeInternalPruning)
            {
                if(validationMode == 2)
                {
                    logFile.println("Each created classifier will be pruned.");
                } else
                {
                    logFile.println("The created classifier will be pruned.");
                }
            }
            logFile.println();
        }
    }

    public void updateVarNames()
    {
        if(dataTable != null)
        {
            for(int i = 0; i < partitions.length; i++)
            {
                partitions[i].setName(dataTable.varNames[i]);
                bestPartitions[i].setName(dataTable.varNames[i]);
                initPartitions[i].setName(dataTable.varNames[i]);
                savePartitions[i].setName(dataTable.varNames[i]);
            }

            for(int j = 0; j < super.outDimension; j++)
            {
                classNames[j] = dataTable.varNames[super.inDimension + j];
            }

        }
    }

    public void addActionListener(ActionListener actionlistener)
    {
        actionListener = actionlistener;
    }

    public void removeActionListener()
    {
        actionListener = null;
    }

    protected void postEvent(String s)
    {
        if(actionListener != null)
        {
            actionListener.actionPerformed(new ActionEvent(this, 1001, s));
        }
    }
    public static void main(String[] args) {
       
    }
}

