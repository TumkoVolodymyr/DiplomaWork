package nauck.fuzzy;

public final class TNorm
{
  private static final int FIRST_TNORM = 0;
  public static final int MIN = 0;
  public static final int PROD = 1;
  public static final int LUKA = 2;
  private static final int LAST_TNORM = 2;
  private int type;

  public TNorm(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt <= 2))
    {
      this.type = paramInt;
      return;
    }
    paramInt = 0;
  }

  public int getType()
  {
    return this.type;
  }

  public double and(double paramDouble1, double paramDouble2)
  {
    switch (this.type)
    {
    case 0:
      if (paramDouble1 < paramDouble2)
        return paramDouble1;
      return paramDouble2;
    case 1:
      return paramDouble1 * paramDouble2;
    case 2:
      double d = paramDouble1 + paramDouble2 - 1.0D;
      if (d < 0.0D)
        return 0.0D;
      return d;
    }
    if (paramDouble1 < paramDouble2)
      return paramDouble1;
    return paramDouble2;
  }

  public double and(double[] paramArrayOfDouble)
  {
    double d = 1.0D;
    int i = paramArrayOfDouble.length;
    for (int j = 0; j < i; j++)
      d = and(d, paramArrayOfDouble[j]);
    return d;
  }
}