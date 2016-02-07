package nauck.fuzzy;

public class FuzzyClassifierRule extends FuzzyRule
        implements Cloneable {

    Variable consequent;

    public FuzzyClassifierRule(String paramString, int paramInt1, int paramInt2, Variable paramVariable) {
        super(paramString, paramInt1, paramInt2);
        this.consequent = paramVariable;
    }

    public void setConsequent(Variable paramVariable, double paramDouble) {
        this.weight = paramDouble;
        addConsequent(paramVariable, null);
    }

    public void setConsequent(Variable paramVariable) {
        addConsequent(paramVariable, null);
    }

    @Override
    public void addConsequent(Variable paramVariable, Object paramObject) {
        this.consequent = paramVariable;
    }

    @Override
    public void deleteConsequent(Variable paramVariable) {
        this.consequent = null;
    }

    public void deleteConsequent() {
        this.consequent = null;
    }

    @Override
    public double computeOutput() {
        return this.hit;
    }

    public int getClassIndex() {
        return this.consequent.getIndex();
    }

    public String getClassName() {
        return this.consequent.toString();
    }

    @Override
    public String toString() {
        return super.toString() + " then " + this.consequent.toString();
    }

    @Override
    public synchronized Object clone() {
        return super.clone();
    }

    @Override
    public boolean equals(Object paramObject) {
        if ((paramObject instanceof FuzzyClassifierRule)) {
            return (super.equals(paramObject)) && (equalConsequent((FuzzyClassifierRule) paramObject));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.consequent != null ? this.consequent.hashCode() : 0);
        return hash;
    }

    public boolean equalAntecedent(FuzzyRule paramFuzzyRule) {
        return super.equals(paramFuzzyRule);
    }

    public boolean equalConsequent(FuzzyClassifierRule paramFuzzyClassifierRule) {
        if (this.consequent == paramFuzzyClassifierRule.consequent) {
            return true;
        }
        if (this.consequent != null) {
            return this.consequent.equals(paramFuzzyClassifierRule.consequent);
        }
        return false;
    }
}