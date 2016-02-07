package nauck.fuzzy;

import java.awt.FontMetrics;
import java.awt.Graphics;

public class TriangularFuzzySet extends FuzzySet
  implements Cloneable
{
  double left;
  double center;
  double right;
  double deltaLeft;
  double dl;
  double deltaCenter;
  double deltaRight;
  double dr;
  double factor;
  int shift;
  boolean leftShouldered;
  boolean rightShouldered;

  TriangularFuzzySet(double[] paramArrayOfDouble, int paramInt)
    throws FuzzySetInvalidException
  {
    super(paramInt);
    this.typeName = "Треугольная";
    this.type = 0;
    setParameters(paramArrayOfDouble);
    this.lastX = this.left;
    this.deltaLeft = 0.0D;
    this.deltaRight = 0.0D;
    this.deltaCenter = 0.0D;
    this.factor = 1.0D;
    this.shift = 0;
  }

  public double computeDegree(double paramDouble)
  {
    this.lastX = paramDouble;
    if (((paramDouble <= this.center) && (this.leftShouldered)) || ((paramDouble >= this.center) && (this.rightShouldered)) || (paramDouble == (1.0D / 0.0D)))
      this.degree = 1.0D;
    else if ((paramDouble < this.left) || (paramDouble > this.right))
      this.degree = 0.0D;
    else if (paramDouble <= this.center)
      this.degree = ((paramDouble - this.left) / (this.center - this.left));
    else
      this.degree = ((this.right - paramDouble) / (this.right - this.center));
    return this.degree;
  }

  public void setParameters(double[] paramArrayOfDouble)
    throws FuzzySetInvalidException
  {
    if (paramArrayOfDouble.length == 5)
    {
      if ((paramArrayOfDouble[0] <= paramArrayOfDouble[1]) && (paramArrayOfDouble[1] <= paramArrayOfDouble[2]))
      {
        this.left = paramArrayOfDouble[0];
        this.center = paramArrayOfDouble[1];
        this.right = paramArrayOfDouble[2];
        this.leftShouldered = (paramArrayOfDouble[3] > 0.0D);
        this.rightShouldered = (paramArrayOfDouble[4] > 0.0D);
        return;
      }
      throw new FuzzySetInvalidException("Неверные параметры " + this.name);
    }
    throw new FuzzySetInvalidException("Неверные параметры " + this.name);
  }

  public double[] getParameters()
  {
    double[] arrayOfDouble = new double[5];
    arrayOfDouble[0] = this.left;
    arrayOfDouble[1] = this.center;
    arrayOfDouble[2] = this.right;
    if (this.leftShouldered)
      arrayOfDouble[3] = 1.0D;
    else
      arrayOfDouble[3] = 0.0D;
    if (this.rightShouldered)
      arrayOfDouble[4] = 1.0D;
    else
      arrayOfDouble[4] = 0.0D;
    return arrayOfDouble;
  }

  public double getSupport()
  {
    return this.right - this.left;
  }

  public String printParameters()
  {
    String str = this.left + "  " + this.center + "  " + this.right;
    if (this.leftShouldered)
      str = str + "  1  ";
    else
      str = str + "  0  ";
    if (this.rightShouldered)
      str = str + "  1  ";
    else
      str = str + "  0  ";
    return str;
  }

  public void computeUpdates(double paramDouble, boolean paramBoolean)
  {
    this.dr = 0.0D;
    this.dl = 0.0D;
    if (paramDouble < 0.0D)
      this.factor = this.degree;
    else
      this.factor = (1.0D - this.degree);
    if (this.lastX < this.center)
    {
      if (this.leftShouldered)
      {
        this.deltaCenter += paramDouble * this.factor * (this.right - this.left);
      }
      else
      {
        this.deltaCenter += -paramDouble * this.factor * (this.right - this.left);
        this.dl = (-paramDouble * this.factor * (this.center - this.left));
        if (!paramBoolean)
          this.dr = (-this.dl);
      }
    }
    else if (this.rightShouldered)
    {
      this.deltaCenter += -paramDouble * this.factor * (this.right - this.left);
    }
    else
    {
      this.deltaCenter += paramDouble * this.factor * (this.right - this.left);
      this.dr = (paramDouble * this.factor * (this.right - this.center));
      if (!paramBoolean)
        this.dl = (-this.dr);
    }
    this.deltaLeft += this.dl;
    this.deltaRight += this.dr;
  }

  public void computeUpdatesBackUp(double paramDouble, boolean paramBoolean)
  {
    double d = paramDouble * (this.right - this.left);
    if (this.lastX < this.center)
    {
      this.deltaCenter -= d;
      if (paramBoolean)
      {
        this.deltaLeft -= 2.0D * d;
        this.deltaRight -= d;
        return;
      }
      this.deltaLeft -= 2.0D * d;
      return;
    }
    this.deltaCenter += d;
    if (paramBoolean)
    {
      this.deltaRight += 2.0D * d;
      this.deltaLeft += d;
      return;
    }
    this.deltaRight += 2.0D * d;
  }

  public void resetUpdates()
  {
    this.deltaRight = 0.0D;
    this.deltaCenter = 0.0D;
    this.deltaLeft = 0.0D;
  }

  public void checkUpdates(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d = (paramDouble3 - paramDouble2) / 200.0D;
    this.deltaLeft *= paramDouble1;
    this.deltaCenter *= paramDouble1;
    this.deltaRight *= paramDouble1;
    this.deltaLeft += this.deltaCenter;
    this.deltaRight += this.deltaRight;
    if (this.leftShouldered)
      this.deltaLeft = 0.0D;
    if (this.rightShouldered)
      this.deltaRight = 0.0D;
    if (this.left + this.deltaLeft < paramDouble2)
      this.deltaLeft = (paramDouble2 - this.left);
    if (this.right + this.deltaRight > paramDouble3)
      this.deltaRight = (paramDouble3 - this.right);
    if (this.center + this.deltaCenter < paramDouble2 + d)
      this.deltaCenter = (paramDouble2 + d - this.center);
    if (this.center + this.deltaCenter > paramDouble3 - d)
      this.deltaCenter = (paramDouble3 - d - this.center);
    if (this.left + this.deltaLeft + d / 2.0D > this.center + this.deltaCenter)
      this.deltaLeft = (this.center + this.deltaCenter - this.left - d / 2.0D);
    if (this.right + this.deltaRight - d / 2.0D < this.center + this.deltaCenter)
      this.deltaRight = (this.center + this.deltaCenter - this.right + d / 2.0D);
  }

  public void doNotPassRightNeighbor(FuzzySet paramFuzzySet)
  {
    if ((paramFuzzySet instanceof TriangularFuzzySet))
    {
      TriangularFuzzySet localTriangularFuzzySet = (TriangularFuzzySet)paramFuzzySet;
      if (this.left + this.deltaLeft > localTriangularFuzzySet.left)
        this.deltaLeft = (localTriangularFuzzySet.left - this.left);
      if (this.center + this.deltaCenter > localTriangularFuzzySet.center)
        this.deltaCenter = (localTriangularFuzzySet.center - this.center);
      if (this.right + this.deltaRight > localTriangularFuzzySet.right)
        this.deltaRight = (localTriangularFuzzySet.right - this.right);
    }
  }

  public void doNotPassLeftNeighbor(FuzzySet paramFuzzySet)
  {
    if ((paramFuzzySet instanceof TriangularFuzzySet))
    {
      TriangularFuzzySet localTriangularFuzzySet = (TriangularFuzzySet)paramFuzzySet;
      if (this.left + this.deltaLeft < localTriangularFuzzySet.left)
        this.deltaLeft = (localTriangularFuzzySet.left - this.left);
      if (this.center + this.deltaCenter < localTriangularFuzzySet.center)
        this.deltaCenter = (localTriangularFuzzySet.center - this.center);
      if (this.right + this.deltaRight < localTriangularFuzzySet.right)
        this.deltaRight = (localTriangularFuzzySet.right - this.right);
    }
  }

  public void overlapWithRightNeighbor(FuzzySet paramFuzzySet, double paramDouble, boolean paramBoolean)
  {
    TriangularFuzzySet localTriangularFuzzySet = (TriangularFuzzySet)paramFuzzySet;
    if (this.right - localTriangularFuzzySet.left < paramDouble)
    {
      double d = this.right + (localTriangularFuzzySet.left - this.right) / 2.0D;
      this.right = (d + paramDouble / 2.0D);
      localTriangularFuzzySet.left = (d - paramDouble / 2.0D);
      if (!paramBoolean)
      {
        this.center = ((this.right - this.left) / 2.0D);
        localTriangularFuzzySet.center = ((localTriangularFuzzySet.right - localTriangularFuzzySet.left) / 2.0D);
      }
    }
  }

  public void computePositionAddUpToOne(FuzzySet paramFuzzySet1, FuzzySet paramFuzzySet2)
  {
    if ((!this.rightShouldered) && (paramFuzzySet2 != null) && ((paramFuzzySet2 instanceof TriangularFuzzySet)))
    {
      TriangularFuzzySet localTriangularFuzzySet1 = (TriangularFuzzySet)paramFuzzySet2;
      TriangularFuzzySet localTriangularFuzzySet2 = null;
      if ((paramFuzzySet1 != null) && ((paramFuzzySet1 instanceof TriangularFuzzySet)))
        localTriangularFuzzySet2 = (TriangularFuzzySet)paramFuzzySet1;
      if ((this.shift == 0) || (localTriangularFuzzySet1.shift == 0))
      {
        double d = (localTriangularFuzzySet1.left - this.center) / 2.0D;
        this.center += d;
        localTriangularFuzzySet1.left -= d;
        d = (localTriangularFuzzySet1.center - this.right) / 2.0D;
        this.right += d;
        localTriangularFuzzySet1.center -= d;
        if ((!this.leftShouldered) && (localTriangularFuzzySet2 != null))
        {
          localTriangularFuzzySet2.right = this.center;
          return;
        }
      }
      else
      {
        if (this.shift < 0)
        {
          if (this.center > localTriangularFuzzySet1.left)
          {
            this.center = localTriangularFuzzySet1.left;
            if ((!this.leftShouldered) && (localTriangularFuzzySet2 != null))
              localTriangularFuzzySet2.right = this.center;
          }
          else
          {
            localTriangularFuzzySet1.left = this.center;
          }
        }
        else if (this.center < localTriangularFuzzySet1.left)
        {
          this.center = localTriangularFuzzySet1.left;
          if ((!this.leftShouldered) && (localTriangularFuzzySet2 != null))
            localTriangularFuzzySet2.right = this.center;
        }
        else
        {
          localTriangularFuzzySet1.left = this.center;
        }
        if (localTriangularFuzzySet1.shift < 0)
        {
          if (localTriangularFuzzySet1.center > this.right)
          {
            localTriangularFuzzySet1.center = this.right;
            return;
          }
          this.right = localTriangularFuzzySet1.center;
          return;
        }
        if (localTriangularFuzzySet1.center < this.right)
        {
          localTriangularFuzzySet1.center = this.right;
          return;
        }
        this.right = localTriangularFuzzySet1.center;
      }
    }
  }

  public void computeSpreadAddUpToOne(FuzzySet paramFuzzySet1, FuzzySet paramFuzzySet2)
  {
    if ((paramFuzzySet1 != null) && ((paramFuzzySet1 instanceof TriangularFuzzySet)))
      this.left = ((TriangularFuzzySet)paramFuzzySet1).center;
    if ((paramFuzzySet2 != null) && ((paramFuzzySet2 instanceof TriangularFuzzySet)))
      this.right = ((TriangularFuzzySet)paramFuzzySet2).center;
  }

  public void applyUpdates()
  {
    this.left += this.deltaLeft;
    this.right += this.deltaRight;
    this.center += this.deltaCenter;
    if (this.deltaCenter < 0.0D)
      this.shift = -1;
    else if (this.deltaCenter > 0.0D)
      this.shift = 1;
    else
      this.shift = 0;
    this.deltaLeft = 0.0D;
    this.deltaRight = 0.0D;
    this.deltaCenter = 0.0D;
  }

  public void draw(FuzzyDrawContext paramFuzzyDrawContext)
  {
    Graphics localGraphics = paramFuzzyDrawContext.getGraphics();
    localGraphics.setColor(this.color);
    int i = paramFuzzyDrawContext.lx + (int)((this.left - paramFuzzyDrawContext.lb) * paramFuzzyDrawContext.scale);
    int j = paramFuzzyDrawContext.lx + (int)((this.center - paramFuzzyDrawContext.lb) * paramFuzzyDrawContext.scale);
    int k = paramFuzzyDrawContext.lx + (int)((this.right - paramFuzzyDrawContext.lb) * paramFuzzyDrawContext.scale);
    if (this.leftShouldered)
      localGraphics.drawLine(i, paramFuzzyDrawContext.uy, j, paramFuzzyDrawContext.uy);
    else
      localGraphics.drawLine(i, paramFuzzyDrawContext.ly, j, paramFuzzyDrawContext.uy);
    if (this.rightShouldered)
      localGraphics.drawLine(j, paramFuzzyDrawContext.uy, k, paramFuzzyDrawContext.uy);
    else
      localGraphics.drawLine(j, paramFuzzyDrawContext.uy, k, paramFuzzyDrawContext.ly);
    localGraphics.drawString(this.shortName, j - localGraphics.getFontMetrics().stringWidth(this.shortName) / 2, paramFuzzyDrawContext.uy - paramFuzzyDrawContext.distance - paramFuzzyDrawContext.descent);
  }

  public synchronized Object clone()
  {
    return super.clone();
  }

  public synchronized void copyTo(FuzzySet paramFuzzySet)
  {
    super.copyTo(paramFuzzySet);
    if ((paramFuzzySet instanceof TriangularFuzzySet))
    {
      TriangularFuzzySet localTriangularFuzzySet = (TriangularFuzzySet)paramFuzzySet;
      localTriangularFuzzySet.left = this.left;
      localTriangularFuzzySet.center = this.center;
      localTriangularFuzzySet.right = this.right;
      localTriangularFuzzySet.leftShouldered = this.leftShouldered;
      localTriangularFuzzySet.rightShouldered = this.rightShouldered;
    }
  }
}

/* Location:           W:\nefcalss\nefclass.jar
 * Qualified Name:     nauck.fuzzy.TriangularFuzzySet
 * JD-Core Version:    0.6.0
 */