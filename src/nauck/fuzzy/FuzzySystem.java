package nauck.fuzzy;

import java.util.Vector;

public abstract class FuzzySystem
{
  protected Vector ruleBase;
  protected int inDimension;
  protected int outDimension;
  protected double[] inputVector;
  protected int currentRuleIndex;

  public FuzzySystem(int paramInt1, int paramInt2)
  {
    this.inDimension = paramInt1;
    this.outDimension = paramInt2;
    this.ruleBase = new Vector(1000, 10);
    this.currentRuleIndex = 0;
  }

  public void addFuzzyRule(FuzzyRule paramFuzzyRule)
  {
    this.ruleBase.addElement(paramFuzzyRule);
  }

  public int getNumberOfRules()
  {
    return this.ruleBase.size();
  }

  public void resetRuleBase()
  {
    this.currentRuleIndex = 0;
  }

  public void setInputVector(double[] paramArrayOfDouble)
  {
    this.inputVector = paramArrayOfDouble;
  }

  public double propagateCurrent()
  {
    if (this.currentRuleIndex < this.ruleBase.size())
      return ((FuzzyRule)this.ruleBase.elementAt(this.currentRuleIndex)).computeDegreeOfFulfilment(this.inputVector);
    return 0.0D;
  }

  public double propagateNext()
  {
    double d;
    if (this.ruleBase.size() > 0)
    {
      d = propagateCurrent();
      this.currentRuleIndex = ((this.currentRuleIndex + 1) % this.ruleBase.size());
    }
    else
    {
      d = 0.0D;
    }
    return d;
  }

  public void propagate(double[] paramArrayOfDouble)
  {
    setInputVector(paramArrayOfDouble);
    for (int i = 0; i < this.ruleBase.size(); i++)
      ((FuzzyClassifierRule)this.ruleBase.elementAt(i)).computeDegreeOfFulfilment(this.inputVector);
  }

  public void deleteAllRules()
  {
    this.ruleBase.removeAllElements();
  }

  public void trimRuleBase()
  {
    int i = 0;
    while (i < this.ruleBase.size())
      if (!((FuzzyClassifierRule)this.ruleBase.elementAt(i)).valid())
        this.ruleBase.removeElementAt(i);
      else
        i++;
    this.ruleBase.trimToSize();
  }

  public int getInputs()
  {
    return this.inDimension;
  }

  public int getOutputs()
  {
    return this.outDimension;
  }

  public String toString()
  {
    String str = "";
    for (int i = 0; i < this.ruleBase.size(); i++)
      str = str + this.ruleBase.elementAt(i).toString() + "\n";
    return str;
  }
}

/* Location:           W:\nefcalss\nefclass.jar
 * Qualified Name:     nauck.fuzzy.FuzzySystem
 * JD-Core Version:    0.6.0
 */