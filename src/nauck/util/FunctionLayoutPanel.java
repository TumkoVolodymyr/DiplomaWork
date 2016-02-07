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
//            FunctionPane

public class FunctionLayoutPanel extends Panel
{

    protected int width;
    protected int height;
    protected Vector panes;
    protected FunctionPane currentPane;
    protected GridLayout layout;

    public FunctionLayoutPanel(int i, int j, int k)
    {
        width = j;
        height = k;
        layout = new GridLayout(0, 1);
        layout.setVgap(3);
        setLayout(layout);
        panes = new Vector(i, 1);
    }

    public void addFunctionPane(FunctionPane functionpane)
    {
        currentPane = functionpane;
        panes.addElement(functionpane);
        add(functionpane);
    }

    public void paint(Graphics g)
    {
        for(int i = 0; i < panes.size(); i++)
        {
            ((FunctionPane)panes.elementAt(i)).repaint();
        }

    }
}
