package nauck.fuzzy;

import java.awt.FontMetrics;
import java.awt.Graphics;

public class ListFuzzySet extends FuzzySet
  implements Cloneable
{
  double[] list;
  double[] deltaList;
  double factor;
  int lowerBound;
  int upperBound;

  ListFuzzySet(double[] paramArrayOfDouble, int paramInt)
    throws FuzzySetInvalidException
  {
    super(paramInt);
    setParameters(paramArrayOfDouble);
    this.type = 3;
    this.typeName = "LIST";
    this.lastX = -1.0D;
    resetUpdates();
    this.factor = 1.0D;
  }

  public double computeDegree(double paramDouble)
  {
    int i;
    if (paramDouble == (1.0D / 0.0D))
      i = -1;
    else
      i = (int)paramDouble - this.lowerBound;
    if ((i < 0) || (i >= this.list.length))
    {
      this.degree = 1.0D;
      i = -1;
    }
    else
    {
      this.degree = this.list[i];
    }
    this.lastX = i;
    return this.degree;
  }

  public void setParameters(double[] paramArrayOfDouble)
    throws FuzzySetInvalidException
  {
    if (paramArrayOfDouble.length > 3)
    {
      this.lowerBound = (int)paramArrayOfDouble[0];
      this.upperBound = (int)paramArrayOfDouble[1];
      this.list = new double[paramArrayOfDouble.length - 2];
      this.deltaList = new double[paramArrayOfDouble.length - 2];
      System.arraycopy(paramArrayOfDouble, 2, this.list, 0, this.list.length);
      normalize();
      return;
    }
    throw new FuzzySetInvalidException("Неверные параметры для нечетких множеств" + this.name + ", не хватает элементов.");
  }

  public double[] getParameters()
  {
    return this.list;
  }

  public double getSupport()
  {
    return this.list.length;
  }

  private void normalize()
  {
    double d1 = (1.0D / 0.0D);
    double d2 = (-1.0D / 0.0D);
    for (int j = 0; j < this.list.length; j++)
    {
      if (this.list[j] > d2)
        d2 = this.list[j];
      if (this.list[j] >= d1)
        continue;
      d1 = this.list[j];
    }
    int i = d2 != d1 ? 0 : 1;
    for (int k = 0; k < this.list.length; k++)
      if (i != 0)
        this.list[k] = 1.0D;
      else
        this.list[k] = ((this.list[k] - d1) / (d2 - d1));
  }

  public String printParameters()
  {
    String str = this.list.length + "  " + this.lowerBound + "  " + this.upperBound + "  ";
    for (int i = 0; i < this.list.length; i++)
      str = str + this.list[i] + " ";
    return str;
  }

  public void computeUpdates(double paramDouble, boolean paramBoolean)
  {
    if (paramDouble < 0.0D)
      this.factor = this.degree;
    else
      this.factor = (1.0D - this.degree);
    if (this.lastX > -1.0D)
      this.deltaList[(int)this.lastX] += paramDouble * this.factor;
  }

  public void resetUpdates()
  {
    for (int i = 0; i < this.deltaList.length; i++)
      this.deltaList[i] = 0.0D;
  }

  public void checkUpdates(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    for (int i = 0; i < this.deltaList.length; i++)
      this.deltaList[i] *= paramDouble1;
  }

  public void doNotPassRightNeighbor(FuzzySet paramFuzzySet)
  {
  }

  public void doNotPassLeftNeighbor(FuzzySet paramFuzzySet)
  {
  }

    @Override
  public void overlapWithRightNeighbor(FuzzySet paramFuzzySet, double paramDouble, boolean paramBoolean)
  {
  }

  public void computePositionAddUpToOne(FuzzySet paramFuzzySet1, FuzzySet paramFuzzySet2)
  {
  }

  public void computeSpreadAddUpToOne(FuzzySet paramFuzzySet1, FuzzySet paramFuzzySet2)
  {
  }

  public void applyUpdates()
  {
    for (int i = 0; i < this.list.length; i++)
    {
      this.list[i] += this.deltaList[i];
      this.deltaList[i] = 0.0D;
    }
    normalize();
  }

  public void draw(FuzzyDrawContext paramFuzzyDrawContext)
  {
    Graphics localGraphics = paramFuzzyDrawContext.getGraphics();
    localGraphics.setColor(this.color);
    int i = paramFuzzyDrawContext.lx + 2 * this.index;
    int k = (int)(paramFuzzyDrawContext.lx + this.index * 2.0D * localGraphics.getFontMetrics().stringWidth(this.shortName));
    for (int m = 0; m < this.list.length; m++)
    {
      int j = paramFuzzyDrawContext.ly - (int)(this.list[m] * (paramFuzzyDrawContext.ly - paramFuzzyDrawContext.uy));
      localGraphics.drawLine(i, paramFuzzyDrawContext.ly, i, j);
      i = (int)(i + paramFuzzyDrawContext.scale);
    }
    localGraphics.drawString(this.shortName, k, paramFuzzyDrawContext.uy - paramFuzzyDrawContext.distance - paramFuzzyDrawContext.descent);
  }

  public synchronized Object clone()
  {
    return super.clone();
  }

  public synchronized void copyTo(FuzzySet paramFuzzySet)
  {
    super.copyTo(paramFuzzySet);
    if ((paramFuzzySet instanceof ListFuzzySet))
    {
      ((ListFuzzySet)paramFuzzySet).list = new double[this.list.length];
      ((ListFuzzySet)paramFuzzySet).deltaList = new double[this.list.length];
      System.arraycopy(this.list, 0, ((ListFuzzySet)paramFuzzySet).list, 0, this.list.length);
      System.arraycopy(this.deltaList, 0, ((ListFuzzySet)paramFuzzySet).deltaList, 0, this.list.length);
      ((ListFuzzySet)paramFuzzySet).lowerBound = this.lowerBound;
      ((ListFuzzySet)paramFuzzySet).upperBound = this.upperBound;
    }
  }

  public boolean equals(Object paramObject)
  {
    boolean bool = super.equals(paramObject);
    if ((paramObject instanceof ListFuzzySet))
    {
      bool = true;
      for (int i = 0; i < this.list.length; i++)
        bool = (bool) && (this.list[i] == ((ListFuzzySet)paramObject).list[i]);
    }
    return bool;
  }

  public void setDontCare()
  {
    super.setDontCare();
    for (int i = 0; i < this.list.length; i++)
      this.list[i] = 1.0D;
  }
}

/* Location:           W:\nefcalss\nefclass.jar
 * Qualified Name:     nauck.fuzzy.ListFuzzySet
 * JD-Core Version:    0.6.0
 */