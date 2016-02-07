package nauck.fuzzy;

import java.awt.FontMetrics;
import java.awt.Graphics;

public class FuzzyDrawContext
{
  private Graphics g;
  public int width;
  public int height;
  public double lb;
  public double ub;
  public double scale;
  public double delta;
  public double resolution = 100.0D;
  public int lx;
  public int ly;
  public int ux;
  public int uy;
  public int tic = 5;
  public int nXtics = 5;
  public int ascent;
  public int descent;
  public int distance = 2;
  public int xLabelWidth;
  public int xLabelPos;
  public int yLabelPos;

  FuzzyDrawContext(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2)
  {
    this.lb = paramDouble1;
    this.ub = paramDouble2;
    this.width = paramInt1;
    this.height = paramInt2;
    this.lx = (int)(0.1D * paramInt1);
    this.ly = (int)(0.8D * paramInt2);
    this.ux = (int)(0.85D * paramInt1);
    this.uy = (int)(0.2D * paramInt2);
    this.scale = ((this.ux - this.lx) / (paramDouble2 - paramDouble1));
    this.delta = ((paramDouble2 - paramDouble1) / this.resolution);
  }

  Graphics getGraphics()
  {
    return this.g;
  }

  void setGraphics(Graphics paramGraphics)
  {
    this.g = paramGraphics;
    this.ascent = paramGraphics.getFontMetrics().getMaxAscent();
    this.descent = paramGraphics.getFontMetrics().getMaxDescent();
    this.xLabelWidth = (paramGraphics.getFontMetrics().stringWidth(Double.toString(this.ub)) / 2);
    this.xLabelPos = (this.ly + this.ascent + this.distance + this.tic);
    this.yLabelPos = (this.lx - paramGraphics.getFontMetrics().stringWidth("0.0") - this.distance - this.tic);
  }
}

/* Location:           W:\nefcalss\nefclass.jar
 * Qualified Name:     nauck.fuzzy.FuzzyDrawContext
 * JD-Core Version:    0.6.0
 */