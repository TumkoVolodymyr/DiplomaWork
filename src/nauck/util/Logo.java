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

public class Logo extends Canvas
{

    Image image;

    public Logo(String imageName)
    {
        image = Toolkit.getDefaultToolkit().getImage(imageName);
        repaint();
    }

    public void paint(Graphics g)
    {
        g.drawImage(image, 0, 0, this);
    }
}
