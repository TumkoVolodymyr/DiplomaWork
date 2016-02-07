package nauck.fuzzy;

public class LinguisticExpression
        implements Cloneable {

    protected Variable variable;
    protected FuzzySet value;

    public LinguisticExpression(Variable paramVariable, FuzzySet paramFuzzySet) {
        this.variable = paramVariable;
        this.value = paramFuzzySet;
    }

    public double match(double paramDouble) {
        return this.value.getDegree(paramDouble);
    }

    public int getIndex() {
        return this.variable.getIndex();
    }

    public int getFuzzySetIndex() {
        return this.value.getIndex();
    }

    public FuzzySet getFuzzySet() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.variable.toString() + " is " + this.value.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.variable != null ? this.variable.hashCode() : 0);
        hash = 37 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object paramObject) {
        boolean i = false;
        if ((paramObject instanceof LinguisticExpression)) {
            i = ((LinguisticExpression) paramObject).variable.equals(this.variable) && 
                    ((LinguisticExpression) paramObject).value.equals(this.value);
        }
        return i;
    }

    public void computeUpdates(double paramDouble, boolean paramBoolean) {
        this.value.computeUpdates(paramDouble, paramBoolean);
    }

    @Override
    public synchronized Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException localCloneNotSupportedException) {
        }
        throw new InternalError();
    }
}