package nauck.main;

import java.io.*;
import nauck.data.DataTable;
import nauck.data.ParseDataFileException;
import nauck.fuzzy.*;

public class ParameterList
{

    String projectFileName;
    String projectTitle;
    String projectDescription;
    String dataFileName;
    boolean stateEditLabels;
    boolean stateStatistics;
    int variableNumber;
    boolean fsIndividual;
    int fsNumber;
    boolean stateFSNumber;
    int fsType;
    boolean stateFSType;
    int fsNumbers[];
    int fsTypes[];
    boolean stateFSSpecify;
    int aggregationFunction;
    int classificationFunction;
    boolean stateRuleEdit;
    boolean sizeAutomatic;
    int maxRules;
    boolean stateMaxRules;
    int ruleLearningMethod;
    boolean relearnRuleBase;
    int validationType;
    int validationFactor;
    boolean stateValidationFactor;
    int testPercentage;
    boolean stateTestPercentage;
    int maxEpochs;
    int minEpochs;
    int additionalEpochs;
    int admissibleErrors;
    boolean keepOrder;
    boolean mustOverlap;
    boolean staySymmetric;
    boolean intersect05;
    int ruleWeights;
    double learningRate;

    public ParameterList()
    {
        projectFileName = "";
        projectTitle = "";
        projectDescription = "";
        dataFileName = "";
        stateEditLabels = false;
        stateStatistics = false;
        variableNumber = 0;
        fsIndividual = false;
        fsNumber = 3;
        fsNumbers = null;
        stateFSNumber = true;
        fsType = 0;
        fsTypes = null;
        stateFSType = true;
        stateFSSpecify = false;
        aggregationFunction = 0;
        classificationFunction = 0;
        stateRuleEdit = false;
        sizeAutomatic = false;
        maxRules = 10;
        stateMaxRules = true;
        ruleLearningMethod = 2;
        relearnRuleBase = false;
        validationType = 1;
        validationFactor = 10;
        stateValidationFactor = false;
        testPercentage = 50;
        stateTestPercentage = true;
        maxEpochs = 100;
        minEpochs = 0;
        additionalEpochs = 10;
        admissibleErrors = 0;
        keepOrder = true;
        mustOverlap = true;
        staySymmetric = false;
        intersect05 = false;
        ruleWeights = 0;
        learningRate = 0.01D;
    }

    public void setProjectFileName(String s)
    {
        projectFileName = s;
    }

    public void setProjectTitle(String s)
    {
        projectTitle = s;
    }

    public void setProjectDescription(String s)
    {
        projectDescription = s;
    }

    public void setDataFileName(String s)
    {
        dataFileName = s;
    }

    public void setStateEditLabels(boolean flag)
    {
        stateEditLabels = flag;
    }

    public void setStateStatistics(boolean flag)
    {
        stateStatistics = flag;
    }

    public void setFSIndividual(boolean flag)
    {
        fsIndividual = flag;
    }

    public void setFSParameters(int i)
    {
        if(fsNumbers == null)
        {
            initFSParameters(i);
            return;
        }
        if(fsNumbers.length != i)
        {
            initFSParameters(i);
        }
    }

    protected void initFSParameters(int i)
    {
        fsNumbers = new int[i];
        fsTypes = new int[i];
        for(int j = 0; j < i; j++)
        {
            fsNumbers[j] = fsNumber;
            fsTypes[j] = fsType;
        }

    }

    public void setEqualFSParameters()
    {
        for(int i = 0; i < fsNumbers.length; i++)
        {
            fsNumbers[i] = fsNumber;
            fsTypes[i] = fsType;
        }

    }

    public void setFSParameters(int ai[], int ai1[])
    {
        fsNumbers = ai;
        fsTypes = ai1;
    }

    public boolean compareFSParameters(int ai[], int ai1[])
    {
        boolean flag = fsNumbers != null && fsTypes != null && ai != null && ai1 != null;
        if(flag)
        {
            flag = fsNumbers.length == ai.length && fsTypes.length == ai1.length && ai.length == ai1.length;
            for(int i = 0; i < fsNumbers.length && flag; i++)
            {
                flag = fsNumbers[i] == ai[i] && fsTypes[i] == ai1[i];
            }

        }
        return flag;
    }

    public boolean compareFSParameters(int i, int j)
    {
        boolean flag = fsNumbers != null && fsTypes != null;
        if(flag)
        {
            for(int k = 0; k < fsNumbers.length && flag; k++)
            {
                flag = fsNumbers[k] == i && fsTypes[k] == j;
            }

        }
        return flag;
    }

    public void setFSNumber(int i)
    {
        fsNumber = i;
    }

    public void setFSType(int i)
    {
        fsType = i;
    }

    public void setStateFSNumber(boolean flag)
    {
        stateFSNumber = flag;
    }

    public void setStateFSType(boolean flag)
    {
        stateFSType = flag;
    }

    public void setStateFSSpecify(boolean flag)
    {
        stateFSSpecify = flag;
    }

    public void setStateRuleEdit(boolean flag)
    {
        stateRuleEdit = flag;
    }

    public void setVariableNumber(int i)
    {
        variableNumber = i;
    }

    public void setAggregationFunction(int i)
    {
        aggregationFunction = i;
    }

    public void setClassificationFunction(int i)
    {
        classificationFunction = i;
    }

    public void setSizeAutomatic(boolean flag)
    {
        sizeAutomatic = flag;
    }

    public void setMaxRules(int i)
    {
        if(i > 0)
        {
            maxRules = i;
        }
    }

    public void setStateMaxRules(boolean flag)
    {
        stateMaxRules = flag;
    }

    public void setRuleLearningMethod(int i)
    {
        ruleLearningMethod = i;
    }

    public void setRelearnRuleBase(boolean flag)
    {
        relearnRuleBase = flag;
    }

    public void setValidationType(int i)
    {
        validationType = i;
    }

    public void setValidationFactor(int i)
    {
        validationFactor = i;
    }

    public void setStateValidationFactor(boolean flag)
    {
        stateValidationFactor = flag;
    }

    public void setTestPercentage(int i)
    {
        testPercentage = i;
    }

    public void setStateTestPercentage(boolean flag)
    {
        stateTestPercentage = flag;
    }

    public void setMaxEpochs(int i)
    {
        maxEpochs = i;
    }

    public void setMinEpochs(int i)
    {
        minEpochs = i;
    }

    public void setAdditionalEpochs(int i)
    {
        additionalEpochs = i;
    }

    public void setAdmissibleErrors(int i)
    {
        admissibleErrors = i;
    }

    public void setKeepOrder(boolean flag)
    {
        keepOrder = flag;
    }

    public void setMustOverlap(boolean flag)
    {
        mustOverlap = flag;
    }

    public void setStaySymmetric(boolean flag)
    {
        staySymmetric = flag;
    }

    public void setIntersect05(boolean flag)
    {
        intersect05 = flag;
    }

    public void setRuleWeights(int i)
    {
        ruleWeights = i;
    }

    public void setLearningRate(double d)
    {
        learningRate = d;
    }

    public String getProjectFileName()
    {
        return projectFileName;
    }

    public String getProjectTitle()
    {
        return projectTitle;
    }

    public String getProjectDescription()
    {
        return projectDescription;
    }

    public String getDataFileName()
    {
        return dataFileName;
    }

    public boolean getStateEditLabels()
    {
        return stateEditLabels;
    }

    public boolean getStateStatistics()
    {
        return stateStatistics;
    }

    public int getVariableNumber()
    {
        return variableNumber;
    }

    public boolean getFSIndividual()
    {
        return fsIndividual;
    }

    public int getFSNumber()
    {
        return fsNumber;
    }

    public int getFSType()
    {
        return fsType;
    }

    public int[] getFSNumbers()
    {
        return fsNumbers;
    }

    public int[] getFSTypes()
    {
        return fsTypes;
    }

    public boolean getStateFSSpecify()
    {
        return stateFSSpecify;
    }

    public boolean fsParametersExist()
    {
        return fsNumbers != null;
    }

    public boolean getStateFSNumber()
    {
        return stateFSNumber;
    }

    public boolean getStateFSType()
    {
        return stateFSType;
    }

    public int getAggregationFunction()
    {
        return aggregationFunction;
    }

    public int getClassificationFunction()
    {
        return classificationFunction;
    }

    public boolean getStateRuleEdit()
    {
        return stateRuleEdit;
    }

    public boolean getSizeAutomatic()
    {
        return sizeAutomatic;
    }

    public int getMaxRules()
    {
        return maxRules;
    }

    public boolean getStateMaxRules()
    {
        return stateMaxRules;
    }

    public int getRuleLearningMethod()
    {
        return ruleLearningMethod;
    }

    public boolean getRelearnRuleBase()
    {
        return relearnRuleBase;
    }

    public int getValidationType()
    {
        return validationType;
    }

    public int getValidationFactor()
    {
        return validationFactor;
    }

    public boolean getStateValidationFactor()
    {
        return stateValidationFactor;
    }

    public int getTestPercentage()
    {
        return testPercentage;
    }

    public boolean getStateTestPercentage()
    {
        return stateTestPercentage;
    }

    public int getMaxEpochs()
    {
        return maxEpochs;
    }

    public int getMinEpochs()
    {
        return minEpochs;
    }

    public int getAdditionalEpochs()
    {
        return additionalEpochs;
    }

    public int getAdmissibleErrors()
    {
        return admissibleErrors;
    }

    public boolean getKeepOrder()
    {
        return keepOrder;
    }

    public boolean getMustOverlap()
    {
        return mustOverlap;
    }

    public boolean getStaySymmetric()
    {
        return staySymmetric;
    }

    public boolean getIntersect05()
    {
        return intersect05;
    }

    public int getRuleWeights()
    {
        return ruleWeights;
    }

    public double getLearningRate()
    {
        return learningRate;
    }

    public void writeParameter(String s)
        throws IOException
    {
        FileWriter filewriter = new FileWriter(s);
        projectFileName = s;
        filewriter.write("PARAMETERLIST\r\n");
        filewriter.write(s + "\r\n");
        filewriter.write(projectTitle + "\r\n");
        filewriter.write(projectDescription + "\r\n");
        filewriter.write("*\r\n");
        filewriter.write(dataFileName + "\r\n");
        filewriter.write(variableNumber + "\r\n");
        filewriter.write(stateEditLabels + "\r\n");
        filewriter.write(stateStatistics + "\r\n");
        filewriter.write(fsIndividual + "\r\n");
        filewriter.write(fsNumber + "\r\n");
        filewriter.write(stateFSNumber + "\r\n");
        filewriter.write(fsType + "\r\n");
        filewriter.write(stateFSType + "\r\n");
        if(variableNumber != 0 && fsIndividual)
        {
            for(int i = 0; i < variableNumber; i++)
            {
                filewriter.write(fsNumbers[i] + "\r\n");
            }

            for(int j = 0; j < variableNumber; j++)
            {
                filewriter.write(fsTypes[j] + "\r\n");
            }

        }
        filewriter.write(stateFSSpecify + "\r\n");
        filewriter.write(aggregationFunction + "\r\n");
        filewriter.write(classificationFunction + "\r\n");
        filewriter.write(stateRuleEdit + "\r\n");
        filewriter.write(sizeAutomatic + "\r\n");
        filewriter.write(maxRules + "\r\n");
        filewriter.write(stateMaxRules + "\r\n");
        filewriter.write(ruleLearningMethod + "\r\n");
        filewriter.write(relearnRuleBase + "\r\n");
        filewriter.write(validationType + "\r\n");
        filewriter.write(validationFactor + "\r\n");
        filewriter.write(stateValidationFactor + "\r\n");
        filewriter.write(testPercentage + "\r\n");
        filewriter.write(stateTestPercentage + "\r\n");
        filewriter.write(maxEpochs + "\r\n");
        filewriter.write(minEpochs + "\r\n");
        filewriter.write(additionalEpochs + "\r\n");
        filewriter.write(admissibleErrors + "\r\n");
        filewriter.write(keepOrder + "\r\n");
        filewriter.write(mustOverlap + "\r\n");
        filewriter.write(staySymmetric + "\r\n");
        filewriter.write(intersect05 + "\r\n");
        filewriter.write(ruleWeights + "\r\n");
        filewriter.write(learningRate + "\r\n");
        filewriter.close();
    }

    public void readParameter(String s)
        throws ParseDataFileException, IOException, NumberFormatException
    {
        FileInputStream fileinputstream = new FileInputStream(s);
        InputStreamReader inputstreamreader = new InputStreamReader(fileinputstream);
        LineNumberReader linenumberreader = new LineNumberReader(inputstreamreader);
        StreamTokenizer streamtokenizer = new StreamTokenizer(linenumberreader);
        boolean flag = false;
        boolean flag2 = true;
        if(streamtokenizer.nextToken() == -3 && streamtokenizer.sval.equalsIgnoreCase("PARAMETERLIST"))
        {
            linenumberreader.readLine();
            projectFileName = linenumberreader.readLine();
            projectFileName = s;
            projectTitle = linenumberreader.readLine();
            try
            {
                if(projectTitle == "" || projectTitle == null || projectTitle.length() == 0)
                {
                    int i = projectFileName.lastIndexOf("/");
                    if(i < 0)
                    {
                        i = projectFileName.lastIndexOf("\\");
                    }
                    String s1;
                    if(i > 0)
                    {
                        s1 = projectFileName.substring(i + 1);
                    } else
                    {
                        s1 = projectFileName;
                    }
                    if(s1.endsWith(".prj"))
                    {
                        s1 = s1.substring(0, s1.length() - ".prj".length());
                    }
                    projectTitle = s1;
                }
            }
            catch(StringIndexOutOfBoundsException _ex)
            {
                projectTitle = "";
            }
            catch(NullPointerException _ex)
            {
                projectTitle = "";
            }
            boolean flag3 = true;
            for(boolean flag1 = false; !flag1;)
            {
                String s2 = linenumberreader.readLine();
                if(!s2.equalsIgnoreCase("*"))
                {
                    if(flag3)
                    {
                        projectDescription = s2;
                        flag3 = false;
                    } else
                    {
                        projectDescription = projectDescription + "\r\n" + s2;
                    }
                } else
                {
                    flag1 = true;
                }
            }

            dataFileName = linenumberreader.readLine();
            variableNumber = (new Integer(linenumberreader.readLine())).intValue();
            String s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                stateEditLabels = true;
            } else
            {
                stateEditLabels = false;
            }
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                stateStatistics = true;
            } else
            {
                stateStatistics = false;
            }
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                fsIndividual = true;
            } else
            {
                fsIndividual = false;
            }
            fsNumber = (new Integer(linenumberreader.readLine())).intValue();
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                stateFSNumber = true;
            } else
            {
                stateFSNumber = false;
            }
            fsType = (new Integer(linenumberreader.readLine())).intValue();
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                stateFSType = true;
            } else
            {
                stateFSType = false;
            }
            if(fsIndividual)
            {
                setFSParameters(variableNumber);
                for(int j = 0; j < variableNumber; j++)
                {
                    fsNumbers[j] = (new Integer(linenumberreader.readLine())).intValue();
                }

                for(int k = 0; k < variableNumber; k++)
                {
                    fsTypes[k] = (new Integer(linenumberreader.readLine())).intValue();
                }

            }
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                stateFSSpecify = true;
            } else
            {
                stateFSSpecify = false;
            }
            aggregationFunction = (new Integer(linenumberreader.readLine())).intValue();
            classificationFunction = (new Integer(linenumberreader.readLine())).intValue();
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                stateRuleEdit = true;
            } else
            {
                stateRuleEdit = false;
            }
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                sizeAutomatic = true;
            } else
            {
                sizeAutomatic = false;
            }
            maxRules = (new Integer(linenumberreader.readLine())).intValue();
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                stateMaxRules = true;
            } else
            {
                stateMaxRules = false;
            }
            ruleLearningMethod = (new Integer(linenumberreader.readLine())).intValue();
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                relearnRuleBase = true;
            } else
            {
                relearnRuleBase = false;
            }
            validationType = (new Integer(linenumberreader.readLine())).intValue();
            validationFactor = (new Integer(linenumberreader.readLine())).intValue();
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                stateValidationFactor = true;
            } else
            {
                stateValidationFactor = false;
            }
            testPercentage = (new Integer(linenumberreader.readLine())).intValue();
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                stateTestPercentage = true;
            } else
            {
                stateTestPercentage = false;
            }
            maxEpochs = (new Integer(linenumberreader.readLine())).intValue();
            minEpochs = (new Integer(linenumberreader.readLine())).intValue();
            additionalEpochs = (new Integer(linenumberreader.readLine())).intValue();
            admissibleErrors = (new Integer(linenumberreader.readLine())).intValue();
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                keepOrder = true;
            } else
            {
                keepOrder = false;
            }
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                mustOverlap = true;
            } else
            {
                mustOverlap = false;
            }
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                staySymmetric = true;
            } else
            {
                staySymmetric = false;
            }
            s3 = linenumberreader.readLine();
            if(s3.equalsIgnoreCase("true"))
            {
                intersect05 = true;
            } else
            {
                intersect05 = false;
            }
            ruleWeights = (new Integer(linenumberreader.readLine())).intValue();
            learningRate = (new Double(linenumberreader.readLine())).doubleValue();
        } else
        {
            throw new ParseDataFileException("Error in line " + Integer.toString(streamtokenizer.lineno()) + " of project file " + s);
        }
        linenumberreader.close();
        inputstreamreader.close();
        fileinputstream.close();
    }

    public Nefclass setupNefclass(DataTable datatable, DataTable datatable1)
        throws NefclassInvalidException
    {
        setFSParameters(datatable.independent);
        Nefclass nefclass = new Nefclass(datatable.independent, datatable.dependent, 0, getAggregationFunction());
        nefclass.setData(datatable);
        if(datatable1 != null)
        {
            nefclass.setApplicationData(datatable1);
        }
        if(fsIndividual)
        {
            nefclass.createPartitions(fsNumbers, fsTypes);
        } else
        {
            nefclass.createPartitions(fsNumber, fsType);
        }
        setupNefclassParameters(nefclass);
        return nefclass;
    }

    public void setupNefclassParameters(Nefclass nefclass)
        throws NefclassInvalidException
    {
        if(validationType == 2)
        {
            nefclass.setValidationMode(validationType, validationFactor);
        } else
        {
            nefclass.setValidationMode(validationType, testPercentage);
        }
        nefclass.setMaxRules(maxRules);
        nefclass.setRuleBaseSizeAutomatic(sizeAutomatic);
        nefclass.setRuleBaseCreateMethod(ruleLearningMethod);
        nefclass.setRelearnRuleBase(relearnRuleBase);
        nefclass.setAsymmetric(!staySymmetric);
        nefclass.setKeepOrder(keepOrder);
        nefclass.setMustOverlap(mustOverlap);
        nefclass.setAddUpToOne(intersect05);
        nefclass.setUseWeights(ruleWeights != 0);
        nefclass.setNormalWeights(ruleWeights != 2);
        nefclass.setMaxEpochs(maxEpochs);
        nefclass.setMinEpochs(minEpochs);
        nefclass.setContinueEpochs(additionalEpochs);
        nefclass.setLearningRate(learningRate);
        nefclass.setStopError(0.0D);
        nefclass.setMisclassifications(admissibleErrors);
    }
}
