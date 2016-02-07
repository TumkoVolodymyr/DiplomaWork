package nauck.fuzzy;

import java.awt.FontMetrics;
import java.awt.Graphics;

public class TrapezoidalFuzzySet extends FuzzySet
  implements Cloneable
{
  double left;
  double centerLeft;
  double centerRight;
  double right;
  double deltaLeft;
  double deltaCenterLeft;
  double deltaCenterRight;
  double deltaCenter;
  double deltaRight;
  double dl;
  double dr;
  double dcl;
  double dcr;
  double factor;
  int rightShift;
  int leftShift;
  boolean leftShouldered;
  boolean rightShouldered;

  TrapezoidalFuzzySet(double[] paramArrayOfDouble, int paramInt)
    throws FuzzySetInvalidException
  {
    super(paramInt);
    setParameters(paramArrayOfDouble);
    this.type = 1;
    this.typeName = "Трапецеедальная";
    this.lastX = this.left;
    resetUpdates();
  }

  public double computeDegree(double paramDouble)
  {
    this.lastX = paramDouble;
    if (((paramDouble <= this.left) && (this.leftShouldered)) || ((paramDouble >= this.right) && (this.rightShouldered)) || ((paramDouble >= this.centerLeft) && (paramDouble <= this.centerRight)) || (paramDouble == (1.0D / 0.0D)))
      this.degree = 1.0D;
    else if ((paramDouble < this.left) || (paramDouble > this.right))
      this.degree = 0.0D;
    else if (paramDouble <= this.centerLeft)
      this.degree = ((paramDouble - this.left) / (this.centerLeft - this.left));
    else
      this.degree = ((this.right - paramDouble) / (this.right - this.centerRight));
    return this.degree;
  }

  public void setParameters(double[] paramArrayOfDouble)
    throws FuzzySetInvalidException
  {
    if (paramArrayOfDouble.length == 6)
    {
      if ((paramArrayOfDouble[0] <= paramArrayOfDouble[1]) && (paramArrayOfDouble[1] <= paramArrayOfDouble[2]) && (paramArrayOfDouble[2] <= paramArrayOfDouble[3]))
      {
        this.left = paramArrayOfDouble[0];
        this.centerLeft = paramArrayOfDouble[1];
        this.centerRight = paramArrayOfDouble[2];
        this.right = paramArrayOfDouble[3];
        this.leftShouldered = (paramArrayOfDouble[4] > 0.0D);
        this.rightShouldered = (paramArrayOfDouble[5] > 0.0D);
        if (this.leftShouldered)
          this.centerLeft = this.left;
        if (this.rightShouldered)
        {
          this.centerRight = this.right;
          return;
        }
      }
      else
      {
        throw new FuzzySetInvalidException("Неверные параметры " + this.name);
      }
    }
    else
      throw new FuzzySetInvalidException("Неверные параметры " + this.name);
  }

  public double[] getParameters()
  {
    double[] arrayOfDouble = new double[6];
    arrayOfDouble[0] = this.left;
    arrayOfDouble[1] = this.centerLeft;
    arrayOfDouble[2] = this.centerRight;
    arrayOfDouble[3] = this.right;
    arrayOfDouble[5] = (this.leftShouldered ? 1 : 0);
    arrayOfDouble[6] = (this.rightShouldered ? 1 : 0);
    return arrayOfDouble;
  }

  public double getSupport()
  {
    return this.right - this.left;
  }

  public String printParameters()
  {
    String str = this.left + "  " + this.centerLeft + "  " + this.centerRight + "  " + this.right;
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
    this.dcl = 0.0D;
    this.dcr = 0.0D;
    if (paramDouble < 0.0D)
      this.factor = this.degree;
    else
      this.factor = (1.0D - this.degree);
    if (this.lastX < this.centerLeft)
    {
      if (this.leftShouldered)
      {
        this.deltaCenter += paramDouble * this.factor * (this.right - this.left);
      }
      else
      {
        this.deltaCenter += -paramDouble * this.factor * (this.right - this.left);
        this.dl = (-paramDouble * this.factor * (this.centerLeft - this.left));
        this.dcl = (-paramDouble * this.factor * (this.centerRight - this.centerLeft));
        if (!paramBoolean)
        {
          this.dr = (-this.dl);
          this.dcr = (-this.dcl);
        }
      }
    }
    else if (this.lastX > this.centerRight)
    {
      if (this.rightShouldered)
      {
        this.deltaCenter += -paramDouble * this.factor * (this.right - this.left);
      }
      else
      {
        this.deltaCenter += paramDouble * this.factor * (this.right - this.left);
        this.dr = (paramDouble * this.factor * (this.right - this.centerRight));
        this.dcr = (paramDouble * this.factor * (this.centerRight - this.centerLeft));
        if (!paramBoolean)
        {
          this.dl = (-this.dr);
          this.dcl = (-this.dcr);
        }
      }
    }
    else
    {
      this.dcr = (paramDouble * this.factor * (this.centerRight - this.centerLeft));
      this.dcl = (-this.dcr);
    }
    this.deltaLeft += this.dl;
    this.deltaRight += this.dr;
    this.deltaCenterLeft += this.dcl;
    this.deltaCenterRight += this.dcr;
  }

  public void resetUpdates()
  {
    this.deltaRight = 0.0D;
    this.deltaCenter = 0.0D;
    this.deltaLeft = 0.0D;
    this.deltaCenterLeft = 0.0D;
    this.deltaCenterRight = 0.0D;
    this.leftShift = 0;
    this.rightShift = 0;
  }

  public void checkUpdates(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d = (paramDouble3 - paramDouble2) / 200.0D;
    this.deltaLeft *= paramDouble1;
    this.deltaCenter *= paramDouble1;
    this.deltaRight *= paramDouble1;
    this.deltaCenterLeft *= paramDouble1;
    this.deltaCenterRight *= paramDouble1;
    this.deltaCenterLeft += this.deltaCenter;
    this.deltaCenterRight += this.deltaCenter;
    this.deltaLeft += this.deltaCenter;
    this.deltaRight += this.deltaRight;
    if (this.leftShouldered)
    {
      this.deltaLeft = 0.0D;
      this.deltaCenterLeft = 0.0D;
    }
    if (this.rightShouldered)
    {
      this.deltaRight = 0.0D;
      this.deltaCenterRight = 0.0D;
    }
    if (this.left + this.deltaLeft < paramDouble2)
      this.deltaLeft = (paramDouble2 - this.left);
    if (this.right + this.deltaRight > paramDouble3)
      this.deltaRight = (paramDouble3 - this.right);
    if (this.centerLeft + this.deltaCenterLeft < paramDouble2 + d)
      this.deltaCenterLeft = (paramDouble2 + d - this.centerLeft);
    if (this.centerRight + this.deltaCenterRight > paramDouble3 - d)
      this.deltaCenterRight = (paramDouble3 - d - this.centerRight);
    if (this.centerLeft + this.deltaCenterLeft > this.centerRight + this.deltaCenterRight)
    {
      this.deltaCenterLeft = 0.0D;
      this.deltaCenterRight = 0.0D;
    }
    if (this.left + this.deltaLeft + d / 2.0D > this.centerLeft + this.deltaCenterLeft)
      this.deltaLeft = (this.centerLeft + this.deltaCenterLeft - this.left - d / 2.0D);
    if (this.right + this.deltaRight - d / 2.0D < this.centerRight + this.deltaCenterRight)
      this.deltaRight = (this.centerRight + this.deltaCenterRight - this.right + d / 2.0D);
  }

  public void doNotPassRightNeighbor(FuzzySet paramFuzzySet)
  {
    if ((paramFuzzySet instanceof TrapezoidalFuzzySet))
    {
      TrapezoidalFuzzySet localTrapezoidalFuzzySet = (TrapezoidalFuzzySet)paramFuzzySet;
      if (this.centerRight + this.deltaCenterRight > localTrapezoidalFuzzySet.centerLeft)
        this.deltaCenterRight = (localTrapezoidalFuzzySet.centerLeft - this.centerRight);
      if (this.centerLeft + this.deltaCenterLeft > this.centerRight)
        this.deltaCenterLeft = (this.centerRight - this.centerLeft);
      if (this.left + this.deltaLeft > this.centerLeft)
        this.deltaLeft = (this.centerLeft - this.left);
      if (this.right + this.deltaRight > localTrapezoidalFuzzySet.right)
        this.deltaRight = (localTrapezoidalFuzzySet.right - this.right);
    }
  }

  public void doNotPassLeftNeighbor(FuzzySet paramFuzzySet)
  {
    if ((paramFuzzySet instanceof TrapezoidalFuzzySet))
    {
      TrapezoidalFuzzySet localTrapezoidalFuzzySet = (TrapezoidalFuzzySet)paramFuzzySet;
      if (this.centerLeft + this.deltaCenterLeft < localTrapezoidalFuzzySet.centerRight)
        this.deltaCenterLeft = (localTrapezoidalFuzzySet.centerRight - this.centerLeft);
      if (this.centerRight + this.deltaCenterRight < this.centerLeft)
        this.deltaCenterRight = (this.centerLeft - this.centerRight);
      if (this.right + this.deltaRight < this.centerRight)
        this.deltaRight = (this.centerRight - this.right);
      if (this.left + this.deltaLeft < localTrapezoidalFuzzySet.left)
        this.deltaLeft = (localTrapezoidalFuzzySet.left - this.left);
    }
  }

  public void overlapWithRightNeighbor(FuzzySet paramFuzzySet, double paramDouble, boolean paramBoolean)
  {
    if ((paramFuzzySet instanceof TrapezoidalFuzzySet))
    {
      TrapezoidalFuzzySet localTrapezoidalFuzzySet = (TrapezoidalFuzzySet)paramFuzzySet;
      if (this.right - localTrapezoidalFuzzySet.left < paramDouble)
      {
        double d = this.right + (localTrapezoidalFuzzySet.left - this.right) / 2.0D;
        if (!paramBoolean)
        {
          this.centerRight += d + paramDouble / 2.0D - this.right;
          localTrapezoidalFuzzySet.centerLeft += d - paramDouble / 2.0D - localTrapezoidalFuzzySet.left;
        }
        this.right = (d + paramDouble / 2.0D);
        localTrapezoidalFuzzySet.left = (d - paramDouble / 2.0D);
      }
    }
  }

  public void computePositionAddUpToOne(FuzzySet paramFuzzySet1, FuzzySet paramFuzzySet2)
  {
    if ((!this.rightShouldered) && (paramFuzzySet2 != null) && ((paramFuzzySet2 instanceof TrapezoidalFuzzySet)))
    {
      TrapezoidalFuzzySet localTrapezoidalFuzzySet1 = (TrapezoidalFuzzySet)paramFuzzySet2;
      TrapezoidalFuzzySet localTrapezoidalFuzzySet2 = null;
      if ((paramFuzzySet1 != null) && ((paramFuzzySet1 instanceof TrapezoidalFuzzySet)))
        localTrapezoidalFuzzySet2 = (TrapezoidalFuzzySet)paramFuzzySet1;
      if ((this.rightShift == 0) || (localTrapezoidalFuzzySet1.leftShift == 0))
      {
        double d = (localTrapezoidalFuzzySet1.left - this.centerRight) / 2.0D;
        this.centerRight += d;
        if (this.centerRight < this.centerLeft)
          this.centerRight = this.centerLeft;
        localTrapezoidalFuzzySet1.left = this.centerRight;
        d = (localTrapezoidalFuzzySet1.centerLeft - this.right) / 2.0D;
        localTrapezoidalFuzzySet1.centerLeft -= d;
        if (localTrapezoidalFuzzySet1.centerLeft > localTrapezoidalFuzzySet1.centerRight)
          localTrapezoidalFuzzySet1.centerLeft = localTrapezoidalFuzzySet1.centerRight;
        this.right = localTrapezoidalFuzzySet1.centerLeft;
        return;
      }
      if (this.rightShift < 0)
      {
        if (this.centerRight > localTrapezoidalFuzzySet1.left)
        {
          this.centerRight = localTrapezoidalFuzzySet1.left;
          if (this.centerRight < this.centerLeft)
          {
            this.centerRight = this.centerLeft;
            localTrapezoidalFuzzySet1.left = this.centerRight;
          }
        }
        else
        {
          localTrapezoidalFuzzySet1.left = this.centerRight;
        }
      }
      else if (this.centerRight < localTrapezoidalFuzzySet1.left)
        this.centerRight = localTrapezoidalFuzzySet1.left;
      else
        localTrapezoidalFuzzySet1.left = this.centerRight;
      if (localTrapezoidalFuzzySet1.leftShift < 0)
      {
        if (localTrapezoidalFuzzySet1.centerLeft > this.right)
        {
          localTrapezoidalFuzzySet1.centerLeft = this.right;
          return;
        }
        this.right = localTrapezoidalFuzzySet1.centerLeft;
        return;
      }
      if (localTrapezoidalFuzzySet1.centerLeft < this.right)
      {
        localTrapezoidalFuzzySet1.centerLeft = this.right;
        if (localTrapezoidalFuzzySet1.centerLeft > localTrapezoidalFuzzySet1.centerRight)
        {
          localTrapezoidalFuzzySet1.centerLeft = localTrapezoidalFuzzySet1.centerRight;
          this.right = localTrapezoidalFuzzySet1.centerLeft;
          return;
        }
      }
      else
      {
        this.right = localTrapezoidalFuzzySet1.centerLeft;
      }
    }
  }

  public void computeSpreadAddUpToOne(FuzzySet paramFuzzySet1, FuzzySet paramFuzzySet2)
  {
    if ((paramFuzzySet1 != null) && ((paramFuzzySet1 instanceof TrapezoidalFuzzySet)))
      this.left = ((TrapezoidalFuzzySet)paramFuzzySet1).centerRight;
    if ((paramFuzzySet2 != null) && ((paramFuzzySet2 instanceof TrapezoidalFuzzySet)))
      this.right = ((TrapezoidalFuzzySet)paramFuzzySet2).centerLeft;
  }

  public void applyUpdates()
  {
    this.left += this.deltaLeft;
    this.right += this.deltaRight;
    this.centerLeft += this.deltaCenterLeft;
    this.centerRight += this.deltaCenterRight;
    if (this.deltaCenterLeft < 0.0D)
      this.leftShift = -1;
    else if (this.deltaCenterLeft > 0.0D)
      this.leftShift = 1;
    else
      this.leftShift = 0;
    if (this.deltaCenterRight < 0.0D)
      this.rightShift = -1;
    else if (this.deltaCenterLeft > 0.0D)
      this.rightShift = 1;
    else
      this.rightShift = 0;
    this.deltaLeft = 0.0D;
    this.deltaRight = 0.0D;
    this.deltaCenterLeft = 0.0D;
    this.deltaCenterRight = 0.0D;
    this.deltaCenter = 0.0D;
  }

  public void draw(FuzzyDrawContext paramFuzzyDrawContext)
  {
    Graphics localGraphics = paramFuzzyDrawContext.getGraphics();
    localGraphics.setColor(this.color);
    int i = paramFuzzyDrawContext.lx + (int)((this.left - paramFuzzyDrawContext.lb) * paramFuzzyDrawContext.scale);
    int j = paramFuzzyDrawContext.lx + (int)((this.centerLeft - paramFuzzyDrawContext.lb) * paramFuzzyDrawContext.scale);
    int k = paramFuzzyDrawContext.lx + (int)((this.centerRight - paramFuzzyDrawContext.lb) * paramFuzzyDrawContext.scale);
    int m = paramFuzzyDrawContext.lx + (int)((this.right - paramFuzzyDrawContext.lb) * paramFuzzyDrawContext.scale);
    localGraphics.drawLine(i, paramFuzzyDrawContext.ly, j, paramFuzzyDrawContext.uy);
    localGraphics.drawLine(j, paramFuzzyDrawContext.uy, k, paramFuzzyDrawContext.uy);
    localGraphics.drawLine(k, paramFuzzyDrawContext.uy, m, paramFuzzyDrawContext.ly);
    localGraphics.drawString(this.shortName, (j + k) / 2 - localGraphics.getFontMetrics().stringWidth(this.shortName) / 2, paramFuzzyDrawContext.uy - paramFuzzyDrawContext.distance - paramFuzzyDrawContext.descent);
  }

  public synchronized Object clone()
  {
    return super.clone();
  }

  public synchronized void copyTo(FuzzySet paramFuzzySet)
  {
    super.copyTo(paramFuzzySet);
    if ((paramFuzzySet instanceof TrapezoidalFuzzySet))
    {
      TrapezoidalFuzzySet localTrapezoidalFuzzySet = (TrapezoidalFuzzySet)paramFuzzySet;
      localTrapezoidalFuzzySet.left = this.left;
      localTrapezoidalFuzzySet.centerLeft = this.centerLeft;
      localTrapezoidalFuzzySet.centerRight = this.centerRight;
      localTrapezoidalFuzzySet.right = this.right;
      localTrapezoidalFuzzySet.leftShouldered = this.leftShouldered;
      localTrapezoidalFuzzySet.rightShouldered = this.rightShouldered;
    }
  }
}

/* Location:           W:\nefcalss\nefclass.jar
 * Qualified Name:     nauck.fuzzy.TrapezoidalFuzzySet
 * JD-Core Version:    0.6.0
 */