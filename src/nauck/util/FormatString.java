/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nauck.util;

/**
 *
 * @author Volodymyr
 */

public class FormatString
{

    public static final String pad(String s, int i, boolean flag)
    {
        int j = i - s.length();
        if(j < 1)
        {
            return s;
        }
        StringBuffer stringbuffer = new StringBuffer(s);
        if(flag)
        {
            for(int k = 0; k < j; k++)
            {
                stringbuffer.append(' ');
            }

        } else
        {
            for(int l = 0; l < j; l++)
            {
                stringbuffer.insert(0, ' ');
            }

        }
        return stringbuffer.toString();
    }

    public static final String line(char c, int i)
    {
        StringBuffer stringbuffer = new StringBuffer(c);
        for(int j = 0; j < i; j++)
        {
            stringbuffer.append(c);
        }

        return stringbuffer.toString();
    }

    public static final String doubleString(double d, int i, int j)
    {
        double d1 = 1.0D;
        StringBuffer stringbuffer;
        if(d == 0.0D)
        {
            if(j == 0)
            {
                stringbuffer = new StringBuffer("0");
            } else
            {
                stringbuffer = new StringBuffer("0.0");
            }
        } else
        {
            for(int k = 0; k < j; k++)
            {
                d1 *= 10D;
            }

            stringbuffer = new StringBuffer(Double.toString(Math.rint(d * d1) / d1));
        }
        if(j > 0)
        {
            int l = j - (stringbuffer.length() - stringbuffer.toString().indexOf('.') - 1);
            for(int j1 = 0; j1 < l; j1++)
            {
                stringbuffer.append('0');
            }

        }
        i -= stringbuffer.length();
        for(int i1 = 0; i1 < i; i1++)
        {
            stringbuffer.insert(0, ' ');
        }

        return stringbuffer.toString();
    }

    public FormatString()
    {
    }
}

