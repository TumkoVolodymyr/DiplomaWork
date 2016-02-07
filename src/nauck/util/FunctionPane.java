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

import java.awt.*;
import java.util.Vector;

// Referenced classes of package nauck.util:
//            FormatString, FunctionDrawContext

public class FunctionPane extends Canvas
{
    class Point
    {

        double x;
        double y;

        public Point(double d, double d1)
        {
            x = d;
            y = d1;
        }
    }


    protected static final Color colors[];
    protected static final int numberOfColors = 9;
    private FunctionDrawContext fdc;
    protected int width;
    protected int height;
    protected Vector graphs[];
    protected String names[];
    protected boolean updateOnly;
    protected boolean clearFirst;
    protected int currentGraph;
    protected Point p;
    protected Point q;

    public FunctionPane(int i, int j, double d, double d1, double d2, double d3)
    {
        updateOnly = false;
        clearFirst = false;
        width = i;
        height = j;
        fdc = new FunctionDrawContext(d, d1, d2, d3, i, j);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(width, height);
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(width, height);
    }

    public void paint(Graphics g)
    {
        if(clearFirst)
        {
            clear(g);
            clearFirst = false;
        }
        fdc.setGraphics(g);
        if(updateOnly)
        {
            g.setClip(fdc.lx, fdc.uy, fdc.ux - fdc.lx, fdc.ly - fdc.uy);
            g.setColor(colors[currentGraph % 9]);
            g.drawLine(fdc.getXCoordinate(p.x), fdc.getYCoordinate(p.y), fdc.getXCoordinate(q.x), fdc.getYCoordinate(q.y));
            g.setClip(0, 0, width + 1, height + 1);
            updateOnly = false;
        }
        draw(g);
    }

    public void print(Graphics g)
    {
        try
        {
            drawPrint(g);
            return;
        }
        catch(NullPointerException _ex)
        {
            return;
        }
    }

    public void clear(Graphics g)
    {
        g.setColor(getBackground());
        g.fillRect(0, 0, width, height);
    }

    public void setNumberOfGraphs(int i)
    {
        graphs = new Vector[i];
        names = new String[i];
        for(int j = 0; j < i; j++)
        {
            graphs[j] = new Vector(300, 100);
            names[j] = "";
        }

    }

    public void setName(int i, String s)
    {
        names[i] = s;
    }

    public void addPointToGraph(double d, double d1, int i)
    {
        q = new Point(d, d1);
        if(graphs[i].size() > 0)
        {
            p = (Point)graphs[i].lastElement();
        }
        graphs[i].addElement(q);
        currentGraph = i;
        if(p != null)
        {
            updateOnly = true;
            repaint();
        }
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void changeXrange(double d)
    {
        fdc.changeXrange(d);
        clearFirst = true;
        repaint();
    }

    public void changeYrange(double d)
    {
        fdc.changeYrange(d);
        clearFirst = true;
        repaint();
    }

    protected void draw(Graphics g)
    {
        g.setColor(Color.black);
        g.drawRect(1, 1, fdc.width - 1, fdc.height - 2);
        g.drawLine(fdc.lx, fdc.ly + fdc.tic, fdc.lx, fdc.uy);
        g.drawLine(fdc.lx, fdc.ly, fdc.ux, fdc.ly);
        g.drawLine(fdc.lx, fdc.uy, fdc.lx - fdc.tic, fdc.uy);
        g.drawLine(fdc.lx, fdc.ly, fdc.lx - fdc.tic, fdc.ly);
        g.drawString(FormatString.doubleString(fdc.uby, 0, 2), fdc.yLabelPos, fdc.uy);
        g.drawString(Double.toString(0.0D), fdc.yLabelPos, fdc.ly);
        g.drawString(FormatString.doubleString(fdc.lbx, 0, 2), fdc.lx - fdc.xLabelWidth, fdc.xLabelPos);
        int i = (int)((fdc.ubx - fdc.lbx) / (double)fdc.nXtics);
        int j = (fdc.ux - fdc.lx) / fdc.nXtics;
        int k = fdc.lx + j;
        for(int l = (int)fdc.lbx + i; (double)l < fdc.ubx - (double)fdc.nXtics; l += i)
        {
            g.drawLine(k, fdc.ly, k, fdc.ly + fdc.tic);
            g.drawString(FormatString.doubleString(l, 0, 2), k - fdc.xLabelWidth, fdc.xLabelPos);
            k += j;
        }

        g.drawLine(fdc.ux, fdc.ly, fdc.ux, fdc.ly + fdc.tic);
        g.drawString(FormatString.doubleString(fdc.ubx, 0, 2), fdc.ux - fdc.xLabelWidth, fdc.xLabelPos);
        for(int i1 = 0; i1 < graphs.length; i1++)
        {
            g.setColor(colors[i1 % 9]);
            if(i1 == 0)
            {
                g.drawString(names[i1] + " ", fdc.lx, fdc.ascent + fdc.distance);
            } else
            {
                g.drawString(names[i1] + " ", fdc.lx + g.getFontMetrics().stringWidth(names[i1 - 1] + " "), fdc.ascent + fdc.distance);
            }
        }

        g.setClip(fdc.lx, fdc.uy, fdc.ux - fdc.lx, fdc.ly - fdc.uy);
        for(int j1 = 0; j1 < graphs.length; j1++)
        {
            g.setColor(colors[j1 % 9]);
            for(int k1 = 1; k1 < graphs[j1].size(); k1++)
            {
                Point point = (Point)graphs[j1].elementAt(k1 - 1);
                Point point1 = (Point)graphs[j1].elementAt(k1);
                g.drawLine(fdc.getXCoordinate(point.x), fdc.getYCoordinate(point.y), fdc.getXCoordinate(point1.x), fdc.getYCoordinate(point1.y));
            }

        }

        g.setClip(0, 0, width + 1, height + 1);
    }

    protected void drawPrint(Graphics g)
    {
        g.setColor(Color.black);
        g.drawRect(1, 1, fdc.width - 1, fdc.height - 2);
        g.drawLine(fdc.lx, fdc.ly + fdc.tic, fdc.lx, fdc.uy);
        g.drawLine(fdc.lx, fdc.ly, fdc.ux, fdc.ly);
        g.drawLine(fdc.lx, fdc.uy, fdc.lx - fdc.tic, fdc.uy);
        g.drawLine(fdc.lx, fdc.ly, fdc.lx - fdc.tic, fdc.ly);
        int i = (int)((fdc.ubx - fdc.lbx) / (double)fdc.nXtics);
        int j = (fdc.ux - fdc.lx) / fdc.nXtics;
        int k = fdc.lx + j;
        for(int l = (int)fdc.lbx + i; (double)l < fdc.ubx - (double)fdc.nXtics; l += i)
        {
            g.drawLine(k, fdc.ly, k, fdc.ly + fdc.tic);
            k += j;
        }

        g.drawLine(fdc.ux, fdc.ly, fdc.ux, fdc.ly + fdc.tic);
        g.setClip(fdc.lx, fdc.uy, fdc.ux - fdc.lx, fdc.ly - fdc.uy);
        for(int i1 = 0; i1 < graphs.length; i1++)
        {
            g.setColor(colors[i1 % 9]);
            for(int j1 = 1; j1 < graphs[i1].size(); j1++)
            {
                Point point = (Point)graphs[i1].elementAt(j1 - 1);
                Point point1 = (Point)graphs[i1].elementAt(j1);
                g.drawLine(fdc.getXCoordinate(point.x), fdc.getYCoordinate(point.y), fdc.getXCoordinate(point1.x), fdc.getYCoordinate(point1.y));
            }

        }

        g.setClip(0, 0, width + 1, height + 1);
        for(int k1 = 0; k1 < graphs.length; k1++)
        {
            g.setColor(colors[k1 % 9]);
            if(k1 == 0)
            {
                g.drawString(names[k1] + " ", fdc.lx, fdc.ascent + fdc.distance);
            } else
            {
                g.drawString(names[k1] + " ", fdc.lx + g.getFontMetrics().stringWidth(names[k1 - 1] + " "), fdc.ascent + fdc.distance);
            }
        }

        g.drawString(FormatString.doubleString(fdc.uby, 0, 2), fdc.yLabelPos, fdc.uy);
        g.drawString(Double.toString(0.0D), fdc.yLabelPos, fdc.ly);
        g.drawString(FormatString.doubleString(fdc.lbx, 0, 2), fdc.lx - fdc.xLabelWidth, fdc.xLabelPos);
        i = (int)((fdc.ubx - fdc.lbx) / (double)fdc.nXtics);
        j = (fdc.ux - fdc.lx) / fdc.nXtics;
        k = fdc.lx + j;
        for(int l1 = (int)fdc.lbx + i; (double)l1 < fdc.ubx - (double)fdc.nXtics; l1 += i)
        {
            g.drawString(FormatString.doubleString(l1, 0, 2), k - fdc.xLabelWidth, fdc.xLabelPos);
            k += j;
        }

        g.drawString(FormatString.doubleString(fdc.ubx, 0, 2), fdc.ux - fdc.xLabelWidth, fdc.xLabelPos);
    }

    static 
    {
        colors = (new Color[] {
            Color.red, Color.blue, Color.magenta, Color.green, Color.darkGray, Color.cyan, Color.pink, Color.gray, Color.orange
        });
    }
}
