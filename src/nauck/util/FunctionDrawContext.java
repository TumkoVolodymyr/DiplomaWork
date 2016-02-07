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

import java.awt.FontMetrics;
import java.awt.Graphics;

public class FunctionDrawContext
{

    private Graphics g;
    public int width;
    public int height;
    public double lbx;
    public double ubx;
    public double lby;
    public double uby;
    public double scaleX;
    public double scaleY;
    public int deltaX;
    public int deltaY;
    public double resolution;
    public int lx;
    public int ly;
    public int ux;
    public int uy;
    public int tic;
    public int nXtics;
    public int ascent;
    public int descent;
    public int distance;
    public int xLabelWidth;
    public int xLabelPos;
    public int yLabelPos;

    FunctionDrawContext(double d, double d1, double d2, double d3, int i, int j)
    {
        resolution = 100D;
        tic = 5;
        nXtics = 5;
        distance = 2;
        deltaX = (int)((d1 - d) / 10D);
        if(deltaX < 1)
        {
            deltaX = 1;
        }
        deltaY = (int)((d3 - d2) / 10D);
        if(deltaY < 1)
        {
            deltaY = 1;
        }
        computeParameters(d, d1, d2, d3, i, j);
    }

    public void computeParameters(double d, double d1, double d2, double d3, int i, int j)
    {
        lbx = d;
        ubx = d1;
        lby = d2;
        uby = d3;
        width = i;
        height = j;
        lx = (int)(0.10000000000000001D * (double)i);
        ly = (int)(0.80000000000000004D * (double)j);
        ux = (int)(0.84999999999999998D * (double)i);
        uy = (int)(0.20000000000000001D * (double)j);
        scaleX = (double)(ux - lx) / (d1 - d);
        scaleY = (double)(ly - uy) / (d3 - d2);
    }

    Graphics getGraphics()
    {
        return g;
    }

    void setGraphics(Graphics g1)
    {
        g = g1;
        ascent = g1.getFontMetrics().getMaxAscent();
        descent = g1.getFontMetrics().getMaxDescent();
        xLabelWidth = g1.getFontMetrics().stringWidth(Double.toString(ubx)) / 2;
        xLabelPos = ly + ascent + distance + tic;
        yLabelPos = lx - g1.getFontMetrics().stringWidth("0.0") - distance - tic;
    }

    public int getXCoordinate(double d)
    {
        return lx + (int)((d - lbx) * scaleX);
    }

    public int getYCoordinate(double d)
    {
        return ly - (int)((d - lby) * scaleY);
    }

    public void changeXrange(double d)
    {
        if(d > 0.0D || d < 0.0D && ubx + d * (double)deltaX > lbx)
        {
            computeParameters(lbx, ubx + d * (double)deltaX, lby, uby, width, height);
        }
    }

    public void changeYrange(double d)
    {
        if(d > 0.0D || d < 0.0D && uby + d * (double)deltaY > lby)
        {
            computeParameters(lbx, ubx, lby, uby + d * (double)deltaY, width, height);
        }
    }
}
