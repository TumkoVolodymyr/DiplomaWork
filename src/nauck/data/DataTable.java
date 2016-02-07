/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nauck.data;

/**
 *
 * @author Volodymyr
 */
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.*;
import nauck.util.FormatString;
import symantec.itools.awt.util.ProgressBar;

// Referenced classes of package nauck.data:
//            ParseDataFileException

public class DataTable
    implements Runnable
{

    public static int NOT_SELECTED;
    public static int MAX_CATEGORIES = 100;
    public int rows;
    public int columns;
    public int independent;
    public int dependent;
    public int casesWithMV;
    public int missing[];
    public String caseLabels[];
    public double data[][];
    public int classIndex[];
    public int rowSelector[];
    public boolean hasMissingValues[];
    public int selectedRows;
    public int unSelectedRows;
    public int columnSelector[];
    public int selectedColumns;
    public double means[];
    public double stdDeviations[];
    public double max[];
    public double min[];
    public double realMax[];
    public double realMin[];
    public double sum[];
    public int casesPerClass[];
    public double sqrsum[];
    public double correlations[][];
    public String name;
    public String filename;
    public String varNames[];
    protected String line;
    protected String lineSeparator;
    protected boolean nameComplete;
    protected boolean missingValues;
    protected boolean rangesGiven;
    protected boolean rangesPresent;
    protected boolean ident;
    protected boolean stop;
    protected boolean dataReady;
    protected boolean headerReady;
    protected TextArea text;
    protected ProgressBar bar;
    protected Thread readThread;
    protected BufferedReader buffer;
    protected StreamTokenizer dataFile;

    public void run()
    {
        try
        {
            readData();
        }
        catch(ParseDataFileException _ex)
        {
            System.out.println("Parse error");
        }
        catch(IOException _ex)
        {
            System.out.println("IO error");
        }
        finally
        {
            text = null;
            bar = null;
            readThread.stop();
        }
    }

    public DataTable(String s)
        throws ParseDataFileException, IOException
    {
        dataReady = false;
        headerReady = false;
        text = null;
        bar = null;
        filename = s;
        casesWithMV = 0;
        missingValues = false;
        read();
    }

    public DataTable(String s, TextArea textarea, ProgressBar progressbar)
        throws ParseDataFileException, IOException
    {
        dataReady = false;
        headerReady = false;
        text = textarea;
        bar = progressbar;
        filename = s;
        read();
    }

    protected void readData()
        throws ParseDataFileException, IOException
    {
        boolean flag = false;
        if(dataFile.nextToken() == -2)
        {
            rows = (int)dataFile.nval;
            data = new double[rows][columns];
            classIndex = new int[rows];
            rowSelector = new int[rows];
            hasMissingValues = new boolean[rows];
            if(ident)
            {
                caseLabels = new String[rows];
            }
            if(text != null)
            {
                text.append("Reading " + Integer.toString(rows) + " cases ... ");
            }
        } else
        {
            String s = "Error in line " + Integer.toString(dataFile.lineno()) + " of data file " + filename + " Expecting a value to specify the number of cases.";
            if(text != null)
            {
                text.append("\n" + s);
            }
            throw new ParseDataFileException(s);
        }
        for(int j = 0; j < rows; j++)
        {
            rowSelector[j] = NOT_SELECTED;
            hasMissingValues[j] = false;
            if(ident)
            {
                if(dataFile.nextToken() == -2)
                {
                    caseLabels[j] = Integer.toString((int)dataFile.nval);
                } else
                if(dataFile.nextToken() == -3)
                {
                    caseLabels[j] = dataFile.sval;
                } else
                {
                    caseLabels[j] = String.valueOf((char)dataFile.ttype);
                }
            }
            if(bar != null)
            {
                int i = (int)(((double)(j + 1) * 100D) / (double)rows);
                try
                {
                    bar.setProgressPercent(i);
                }
                catch(PropertyVetoException _ex) { }
            }
            classIndex[j] = -1;
            for(int k = 0; k < columns; k++)
            {
                if(dataFile.nextToken() == -2)
                {
                    data[j][k] = dataFile.nval;
                    sum[k] += data[j][k];
                    sqrsum[k] += data[j][k] * data[j][k];
                    if(k >= independent && data[j][k] > (double)classIndex[j])
                    {
                        classIndex[j] = k - independent;
                    }
                    for(int i1 = 0; i1 <= k; i1++)
                    {
                        if(k < independent && data[j][i1] != (1.0D / 0.0D))
                        {
                            correlations[i1][k] += data[j][i1] * data[j][k];
                        } else
                        if(k == columns - 1 && i1 < independent && dependent > 0 && data[j][i1] != (1.0D / 0.0D))
                        {
                            correlations[i1][independent] += data[j][i1] * (double)classIndex[j];
                        }
                    }

                    if(j > 0)
                    {
                        realMin[k] = data[j][k] >= realMin[k] ? realMin[k] : data[j][k];
                        realMax[k] = data[j][k] <= realMax[k] ? realMax[k] : data[j][k];
                    } else
                    {
                        realMin[k] = data[j][k];
                        realMax[k] = data[j][k];
                    }
                } else
                if(dataFile.ttype == 63 && k < independent)
                {
                    hasMissingValues[j] = true;
                    data[j][k] = (1.0D / 0.0D);
                    missing[k]++;
                    missingValues = true;
                } else
                {
                    String s1 = "Error in line " + Integer.toString(dataFile.lineno()) + " of data file " + filename + " Expecting a number (case " + Integer.toString(j) + ", column " + Integer.toString(k) + ").";
                    if(text != null)
                    {
                        text.append("\n" + s1);
                    }
                    throw new ParseDataFileException(s1);
                }
            }

            if(hasMissingValues[j])
            {
                casesWithMV++;
            }
            if(dependent > 0)
            {
                casesPerClass[classIndex[j]]++;
            }
        }

        rangesPresent = true;
        for(int l = 0; l < columns; l++)
        {
            if(rangesGiven)
            {
                min[l] = realMin[l] >= min[l] ? min[l] : realMin[l];
                max[l] = realMax[l] <= max[l] ? max[l] : realMax[l];
            } else
            {
                min[l] = realMin[l];
                max[l] = realMax[l];
            }
            means[l] = sum[l] / (double)(rows - missing[l]);
            stdDeviations[l] = Math.sqrt(((sqrsum[l] - 2D * means[l] * sum[l]) + (double)(rows - missing[l]) * means[l] * means[l]) / (double)(rows - missing[l]));
            if(!missingValues)
            {
                for(int j1 = l; j1 <= independent; j1++)
                {
                    if(j1 < independent)
                    {
                        correlations[l][j1] = (correlations[l][j1] - (sum[l] * sum[j1]) / (double)rows) / Math.sqrt((sqrsum[l] - (sum[l] * sum[l]) / (double)rows) * (sqrsum[j1] - (sum[j1] * sum[j1]) / (double)rows));
                    } else
                    if(dependent > 0)
                    {
                        double d = 0.0D;
                        double d1 = 0.0D;
                        for(int j2 = 1; j2 < dependent; j2++)
                        {
                            d += sum[j2 + independent] * (double)j2;
                            d1 += sum[j2 + independent] * (double)j2 * (double)j2;
                        }

                        correlations[l][j1] = (correlations[l][j1] - (sum[l] * d) / (double)rows) / Math.sqrt((sqrsum[l] - (sum[l] * sum[l]) / (double)rows) * (d1 - (d * d) / (double)rows));
                    }
                }

            }
        }

        if(missingValues)
        {
            double ad[][] = new double[independent][independent + 1];
            double ad1[][] = new double[independent][independent + 1];
            int ai[][] = new int[independent][independent + 1];
            for(int k1 = 0; k1 < independent; k1++)
            {
                for(int l1 = 0; l1 <= independent; l1++)
                {
                    ad1[k1][l1] = 0.0D;
                    ad[k1][l1] = 0.0D;
                    ai[k1][l1] = 0;
                }

            }

            for(int i2 = 0; i2 < rows; i2++)
            {
                for(int k2 = 0; k2 < independent; k2++)
                {
                    for(int i3 = 0; i3 <= independent; i3++)
                    {
                        if(i3 < independent)
                        {
                            if(data[i2][k2] != (1.0D / 0.0D) && data[i2][i3] != (1.0D / 0.0D))
                            {
                                ad[k2][i3] += data[i2][k2] * data[i2][k2];
                                ad1[k2][i3] += data[i2][k2];
                                ai[k2][i3]++;
                            }
                        } else
                        if(data[i2][k2] != (1.0D / 0.0D))
                        {
                            ai[k2][i3]++;
                            ad[k2][i3] += classIndex[i2] * classIndex[i2];
                            ad1[k2][i3] += classIndex[i2];
                        }
                    }

                }

            }

            for(int l2 = 0; l2 < independent; l2++)
            {
                for(int j3 = l2; j3 <= independent; j3++)
                {
                    if(j3 < independent)
                    {
                        correlations[l2][j3] = (correlations[l2][j3] - (ad1[l2][j3] * ad1[j3][l2]) / (double)ai[l2][j3]) / Math.sqrt((ad[l2][j3] - (ad1[l2][j3] * ad1[l2][j3]) / (double)ai[l2][j3]) * (ad[j3][l2] - (ad1[j3][l2] * ad1[j3][l2]) / (double)ai[l2][j3]));
                    } else
                    if(dependent > 0)
                    {
                        correlations[l2][j3] = (correlations[l2][j3] - (ad1[l2][l2] * ad1[l2][j3]) / (double)ai[l2][l2]) / Math.sqrt((ad[l2][l2] - (ad1[l2][l2] * ad1[l2][l2]) / (double)ai[l2][l2]) * (ad[l2][j3] - (ad1[l2][j3] * ad1[l2][j3]) / (double)ai[l2][l2]));
                    }
                }

            }

        }
        if(text != null)
        {
            text.append("finished.\n");
        }
        unSelectedRows = rows;
        selectedRows = 0;
        buffer.close();
        dataReady = true;
    }

    protected void read()
        throws ParseDataFileException, IOException
    {
        buffer = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        dataFile = new StreamTokenizer(buffer);
        lineSeparator = System.getProperty("line.separator");
        if(lineSeparator == null)
        {
            lineSeparator = "\n";
        }
        if(text != null)
        {
            text.append("Processing " + filename + " ...\n");
        }
        dataFile.commentChar(35);
        dataFile.commentChar(37);
        dataFile.slashStarComments(true);
        dataFile.slashSlashComments(true);
        dataFile.parseNumbers();
        dataFile.eolIsSignificant(false);
        if(dataFile.nextToken() == -3 && dataFile.sval.equalsIgnoreCase("DIMENSIONS"))
        {
            if(dataFile.nextToken() == -2)
            {
                independent = (int)dataFile.nval;
            } else
            {
                throw new ParseDataFileException("Error in line " + Integer.toString(dataFile.lineno()) + " of data file " + filename + " Expecting an integer (number of input variables).");
            }
            if(dataFile.nextToken() == -2)
            {
                dependent = (int)dataFile.nval;
            } else
            {
                throw new ParseDataFileException("Error in line " + Integer.toString(dataFile.lineno()) + " of data file " + filename + " Expecting an integer (number of class labels).");
            }
            if(text != null)
            {
                text.append("The data contains " + Integer.toString(independent) + " input features and " + Integer.toString(dependent) + " classes.\n");
            }
            rangesGiven = false;
            rangesPresent = false;
            ident = false;
            name = "";
            stop = false;
            columns = independent + dependent;
            selectedColumns = 0;
            selectedRows = 0;
            columnSelector = new int[columns];
            varNames = new String[columns];
            max = new double[columns];
            min = new double[columns];
            realMax = new double[columns];
            realMin = new double[columns];
            means = new double[columns];
            stdDeviations = new double[columns];
            sum = new double[columns];
            sqrsum = new double[columns];
            missing = new int[columns];
            correlations = new double[independent + 1][independent + 1];
            if(dependent > 0)
            {
                casesPerClass = new int[dependent];
            }
            for(int i = 0; i < columns; i++)
            {
                sum[i] = 0.0D;
                sqrsum[i] = 0.0D;
                missing[i] = 0;
                columnSelector[i] = NOT_SELECTED;
                if(i < independent)
                {
                    varNames[i] = "Var " + Integer.toString(i + 1);
                } else
                {
                    varNames[i] = "Class " + Integer.toString((i - independent) + 1);
                }
                for(int l = 0; l <= independent; l++)
                {
                    if(i <= independent)
                    {
                        correlations[i][l] = 0.0D;
                    }
                }

            }

            for(int i1 = 0; i1 < dependent; i1++)
            {
                casesPerClass[i1] = 0;
            }

        } else
        {
            throw new ParseDataFileException("Error in line " + Integer.toString(dataFile.lineno()) + " of data file " + filename + " Expecting DIMENSIONS keyword, but found " + dataFile.sval + ".");
        }
        do
        {
            if(dataFile.nextToken() == -3)
            {
                if(dataFile.sval.equalsIgnoreCase("NAME"))
                {
                    buffer.readLine();
                    name = buffer.readLine();
                    if(text != null)
                    {
                        text.append("Name: " + name + "\n");
                    }
                } else
                if(dataFile.sval.equalsIgnoreCase("VARNAMES"))
                {
                    if(text != null)
                    {
                        text.append("Names of Variables:\n");
                    }
                    buffer.readLine();
                    for(int j = 0; j < columns; j++)
                    {
                        varNames[j] = buffer.readLine();
                        if(text != null)
                        {
                            text.append("   " + varNames[j]);
                        }
                    }

                } else
                if(dataFile.sval.equalsIgnoreCase("INRANGES"))
                {
                    if(text != null)
                    {
                        text.append("Specified Ranges of Variables:\n");
                    }
                    for(int k = 0; k < independent; k++)
                    {
                        if(dataFile.nextToken() == -2)
                        {
                            if(rangesPresent)
                            {
                                min[k] = dataFile.nval >= realMin[k] ? realMin[k] : dataFile.nval;
                            } else
                            {
                                min[k] = dataFile.nval;
                            }
                        } else
                        {
                            throw new ParseDataFileException("Error in line " + Integer.toString(dataFile.lineno()) + " of data file " + filename + " Expecting lower bound of " + Integer.toString(k) + ". input variable.");
                        }
                        if(dataFile.nextToken() == -2)
                        {
                            if(rangesPresent)
                            {
                                max[k] = dataFile.nval <= realMax[k] ? realMax[k] : dataFile.nval;
                            } else
                            {
                                max[k] = dataFile.nval;
                            }
                        } else
                        {
                            throw new ParseDataFileException("Error in line " + Integer.toString(dataFile.lineno()) + " of data file " + filename + " Expecting upper bound of " + Integer.toString(k) + ". input variable.");
                        }
                        if(text != null)
                        {
                            text.append("   " + varNames[k] + ": [" + Double.toString(min[k]) + ", " + Double.toString(max[k]) + "]\n");
                        }
                    }

                    rangesGiven = true;
                } else
                if(dataFile.sval.equalsIgnoreCase("IDENT"))
                {
                    ident = true;
                    if(text != null)
                    {
                        text.append("The cases are labelled.\n");
                    }
                } else
                if(dataFile.sval.equalsIgnoreCase("PATTERNS") || dataFile.sval.equalsIgnoreCase("PATTERN") || dataFile.sval.equalsIgnoreCase("DATA"))
                {
                    headerReady = true;
                    readThread = new Thread(this);
                    readThread.start();
                    stop = true;
                } else
                if(dataFile.sval.equalsIgnoreCase("STARTNAME"))
                {
                    buffer.readLine();
                    for(nameComplete = false; !nameComplete;)
                    {
                        line = buffer.readLine();
                        if(!line.trim().equalsIgnoreCase("ENDNAME"))
                        {
                            name = name + line;
                        } else
                        {
                            nameComplete = true;
                        }
                        if(!nameComplete)
                        {
                            name = name + lineSeparator;
                        }
                    }

                } else
                {
                    throw new ParseDataFileException("Error in line " + Integer.toString(dataFile.lineno()) + " of data file " + filename + " Found unexpected keyword: " + dataFile.sval);
                }
            } else
            {
                if(dataFile.ttype == -2)
                {
                    throw new ParseDataFileException("Error in line " + Integer.toString(dataFile.lineno()) + " of data file " + filename + " Found a number instead of a keyword: " + dataFile.nval);
                }
                if(dataFile.ttype == -1)
                {
                    stop = true;
                } else
                {
                    throw new ParseDataFileException("Error in line " + Integer.toString(dataFile.lineno()) + " of data file " + filename + " Parse error, unexpected token found." + " This is a source code error in class DataTable." + " Please report this error to the developers.");
                }
            }
        } while(!stop);
    }

    public void copy(String s)
        throws IOException
    {
        String s2 = "";
        String s3 = null;
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        String s1;
        if(s.equalsIgnoreCase(filename))
        {
            s1 = "_nfcjdat.tmp";
        } else
        {
            s1 = s;
        }
        BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(s1));
        BufferedReader bufferedreader = new BufferedReader(new FileReader(filename));
        line = bufferedreader.readLine();
        if(line != null)
        {
            s2 = line.trim().toUpperCase();
        }
        while(line != null) 
        {
            if(s2.equals("NAME") || s2.equals("STARTNAME"))
            {
                if(s2.equals("NAME"))
                {
                    bufferedreader.readLine();
                } else
                {
                    while(!s2.equals("ENDNAME") && line != null) 
                    {
                        line = buffer.readLine();
                        if(line != null)
                        {
                            s2 = line.trim().toUpperCase();
                        }
                    }
                }
                if(flag2)
                {
                    bufferedwriter.write(s3);
                    bufferedwriter.newLine();
                    flag2 = false;
                    s3 = null;
                }
                bufferedwriter.write("STARTNAME");
                bufferedwriter.newLine();
                bufferedwriter.write(name);
                bufferedwriter.newLine();
                bufferedwriter.write("ENDNAME");
                bufferedwriter.newLine();
                flag = true;
            } else
            if(s2.equals("VARNAMES"))
            {
                if(flag2)
                {
                    bufferedwriter.write(s3);
                    bufferedwriter.newLine();
                    flag2 = false;
                    s3 = null;
                }
                bufferedwriter.write("VARNAMES");
                bufferedwriter.newLine();
                for(int i = 0; i < columns; i++)
                {
                    buffer.readLine();
                    bufferedwriter.write(varNames[i]);
                    bufferedwriter.newLine();
                }

                flag1 = true;
            } else
            if(s2.startsWith("PATTERN") || s2.equals("DATA"))
            {
                if(!flag)
                {
                    bufferedwriter.write("STARTNAME");
                    bufferedwriter.newLine();
                    bufferedwriter.write(name);
                    bufferedwriter.newLine();
                    bufferedwriter.write("ENDNAME");
                    bufferedwriter.newLine();
                    bufferedwriter.newLine();
                    flag = true;
                }
                if(!flag1)
                {
                    bufferedwriter.write("VARNAMES");
                    bufferedwriter.newLine();
                    for(int j = 0; j < columns; j++)
                    {
                        bufferedwriter.write(varNames[j]);
                        bufferedwriter.newLine();
                    }

                    bufferedwriter.newLine();
                    flag1 = true;
                }
                if(flag2)
                {
                    bufferedwriter.write(s3);
                    bufferedwriter.newLine();
                    flag2 = false;
                    s3 = null;
                }
                bufferedwriter.write(s2);
                bufferedwriter.newLine();
            } else
            if(s2.startsWith("%") || s2.startsWith("#") || s2.startsWith("//"))
            {
                if(flag2)
                {
                    s3 = s3 + lineSeparator + line;
                } else
                {
                    s3 = line;
                    flag2 = true;
                }
            } else
            {
                if(flag2)
                {
                    bufferedwriter.write(s3);
                    bufferedwriter.newLine();
                    flag2 = false;
                    s3 = null;
                }
                bufferedwriter.write(line);
                bufferedwriter.newLine();
            }
            line = bufferedreader.readLine();
            if(line != null)
            {
                s2 = line.trim().toUpperCase();
            }
        }
        bufferedreader.close();
        bufferedwriter.close();
        if(!s.equalsIgnoreCase(s1))
        {
            File file = new File(s1);
            File file1 = new File(s);
            if(file1.delete())
            {
                if(!file.renameTo(file1))
                {
                    throw new IOException("Cannot rename the temporary data file '_nfcjdat.tmp' to '" + s + "', check if the temporary file can be used.");
                }
            } else
            {
                throw new IOException("Cannot overwrite the data file '" + s + "', the data should be available in '_nfcjdat.tmp'.");
            }
        }
    }

    public boolean isReady()
    {
        return headerReady;
    }

    public boolean isComplete()
    {
        return dataReady;
    }

    public synchronized void writeStatistics(TextArea textarea)
    {
        String s = " | ";
        Font font = new Font("Monospaced", 0, textarea.getFont().getSize());
        textarea.setFont(font);
        textarea.append("Statistics for " + filename + "\n\n");
        textarea.append("Number of input features : " + independent + "\n");
        textarea.append("Number of classes        : " + dependent + "\n");
        textarea.append("Number of cases          : " + rows + "\n");
        textarea.append("Cases with missing values: " + casesWithMV + "\n\n");
        textarea.append("Input Features\n");
        textarea.append("--------------\n\n");
        textarea.append("Variable         |     mean     |std. deviation|    minimum   |    maximum   ");
        String s1;
        if(rangesGiven)
        {
            textarea.append("| minimum given| maximum given");
            s1 = FormatString.line('-', 123);
        } else
        {
            s1 = FormatString.line('-', 93);
        }
        textarea.append("|    missing   |\n");
        textarea.append(s1 + "\n");
        for(int i = 0; i < independent; i++)
        {
            textarea.append(FormatString.pad(varNames[i], 16, true) + s);
            textarea.append(FormatString.doubleString(means[i], 12, 2) + s);
            textarea.append(FormatString.doubleString(stdDeviations[i], 12, 2) + s);
            textarea.append(FormatString.doubleString(realMin[i], 12, 2) + s);
            textarea.append(FormatString.doubleString(realMax[i], 12, 2) + s);
            if(rangesGiven)
            {
                textarea.append(FormatString.doubleString(min[i], 12, 2) + s);
                textarea.append(FormatString.doubleString(max[i], 12, 2) + s);
            }
            textarea.append(FormatString.pad(FormatString.pad(Integer.toString(missing[i]), 7, false), 12, true));
            textarea.append("\n");
        }

        int j = (int)(Math.log(rows) / Math.log(10D)) + 1;
        textarea.append("\n\nClasses\n");
        textarea.append("-------\n\n");
        if(dependent > 0)
        {
            for(int k = 0; k < dependent; k++)
            {
                textarea.append(FormatString.pad(varNames[k + independent], 30, true) + ": " + FormatString.pad(Integer.toString(casesPerClass[k]), j, false) + " cases\n");
            }

        } else
        {
            textarea.append("The data is not classified.\n");
        }
        textarea.append("\n\nCorrelation Table\n-----------------\n\n     |");
        s = " |";
        for(int l = 0; l < independent; l++)
        {
            textarea.append(FormatString.pad(Integer.toString(l + 1), 5, false) + "  ");
        }

        s1 = FormatString.line('-', 13 + 7 * independent);
        textarea.append("  class\n" + s1 + "\n");
        for(int i1 = 0; i1 < independent; i1++)
        {
            textarea.append(FormatString.pad(Integer.toString(i1 + 1), 4, false) + s);
            for(int j1 = 0; j1 <= independent; j1++)
            {
                if(i1 <= j1)
                {
                    textarea.append(FormatString.doubleString(correlations[i1][j1], 7, 2));
                } else
                {
                    textarea.append("       ");
                }
            }

            textarea.append("\n");
        }

    }

    public String[] getInputVarNames()
    {
        String as[] = new String[independent];
        for(int i = 0; i < independent; i++)
        {
            as[i] = varNames[i];
        }

        return as;
    }

    public void resetRowSelection()
    {
        for(int i = 0; i < rows; i++)
        {
            rowSelector[i] = NOT_SELECTED;
            selectedRows = 0;
            unSelectedRows = rows;
        }

    }

    public void resetColumnSelection()
    {
        for(int i = 0; i < columns; i++)
        {
            columnSelector[i] = NOT_SELECTED;
            selectedColumns = 0;
        }

    }

    public void selectPercentageOfRows(double d)
    {
        selectPercentageOfRows(1, d);
    }

    protected void selectPercentageOfRows(int i, double d)
    {
        resetRowSelection();
        if(d > 100D)
        {
            d = 100D;
        }
        if(d > 0.0D)
        {
            d *= 0.01D;
            shuffle();
            sortByClassIndex();
            int k = 0;
            for(int l = 0; l < dependent; l++)
            {
                int j = Math.round((float)((double)casesPerClass[l] * d));
                for(int i1 = k; i1 < k + j; i1++)
                {
                    rowSelector[i1] = i;
                    selectedRows++;
                }

                k += casesPerClass[l];
            }

            unSelectedRows = rows - selectedRows;
        }
    }

    public void selectSubsets(int i)
    {
        if(rows > i)
        {
            shuffle();
            sortByClassIndex();
            for(int j = 0; j < rows; j++)
            {
                rowSelector[j] = 1 + j % i;
            }

            return;
        }
        for(int k = 0; k < rows; k++)
        {
            rowSelector[k] = k + 1;
        }

    }

    public void countSelection(int i)
    {
        selectedRows = 0;
        unSelectedRows = 0;
        for(int j = 0; j < rows; j++)
        {
            if(rowSelector[j] == i)
            {
                selectedRows++;
            }
            unSelectedRows = rows - selectedRows;
        }

    }

    public void sortByClassIndex()
    {
        boolean flag = true;
        for(int i = 0; i < rows - 1 && flag; i++)
        {
            flag = false;
            for(int j = 0; j < rows - i - 1; j++)
            {
                if(classIndex[j] > classIndex[j + 1])
                {
                    swap(j, j + 1);
                    flag = true;
                }
            }

        }

    }

    protected void swap(int i, int j)
    {
        double ad[] = data[j];
        data[j] = data[i];
        data[i] = ad;
        int k = classIndex[j];
        classIndex[j] = classIndex[i];
        classIndex[i] = k;
        if(ident)
        {
            String s = caseLabels[j];
            caseLabels[j] = caseLabels[i];
            caseLabels[i] = s;
        }
    }

    public void shuffle()
    {
        for(int j = 0; j < rows; j++)
        {
            int i = Math.round((float)(Math.random() * (double)rows));
            if(i >= rows)
            {
                i = rows - 1;
            }
            swap(j, i);
        }

    }

    public void dump()
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                System.out.print(data[i][j] + " ");
            }

            System.out.println();
        }

    }

    public void dump(TextArea textarea)
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                textarea.append(data[i][j] + " ");
            }

            textarea.append(varNames[independent + classIndex[i]] + "\n");
        }

    }

    public String getLabel(int i)
    {
        if(i < rows)
        {
            if(ident)
            {
                return caseLabels[i];
            } else
            {
                return (new Integer(i)).toString();
            }
        } else
        {
            return "invalid";
        }
    }

    public boolean isClassified()
    {
        return dependent > 0;
    }

    public double[] frequencies(int i, boolean flag, int j)
    {
        if(i >= columns)
        {
            return null;
        }
        int k = (int)realMin[i];
        int l = (int)realMax[i];
        if(l - k >= MAX_CATEGORIES)
        {
            return null;
        }
        double ad[] = new double[(l - k) + 3];
        ad[0] = k;
        ad[1] = l;
        for(int i1 = 0; i1 < rows; i1++)
        {
            if((!flag || flag && rowSelector[i1] == NOT_SELECTED) && (dependent < 0 || j < 0 || j == classIndex[i1]) && data[i1][i] != (1.0D / 0.0D))
            {
                ad[((int)data[i1][i] - k) + 2]++;
            }
        }

        return ad;
    }

    public double getRealMin(int i)
    {
        return realMin[i];
    }

    public double getRealMax(int i)
    {
        return realMax[i];
    }

}

