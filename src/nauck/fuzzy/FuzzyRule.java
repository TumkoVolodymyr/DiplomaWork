package nauck.fuzzy;

import java.util.Arrays;

public abstract class FuzzyRule
        implements Cloneable {

    protected static final double EPSILON = 0.01D;
    protected String name;
    protected LinguisticExpression[] antecedent;
    protected TNorm tNorm;
    protected double hit;
    protected double error;
    protected boolean isValid;
    protected double performance;
    protected double minDegree;
    protected int indexMinDegree;
    protected double weight;
    protected double deltaW;

    public FuzzyRule(String paramString, int paramInt1, int paramInt2) {
        this.name = paramString;
        this.tNorm = new TNorm(paramInt1);
        this.antecedent = new LinguisticExpression[paramInt2];
        this.isValid = false;
        this.weight = 1.0D;
        this.deltaW = 0.0D;
        this.performance = 0.0D;
    }

    @Override
    public boolean equals(Object paramObject) {
        boolean i = false;
        if ((paramObject instanceof FuzzyRule)) {
            if ((this.antecedent != null) && (((FuzzyRule) paramObject).antecedent != null)) {
                i = this.antecedent.length == ((FuzzyRule) paramObject).antecedent.length;
                int j = 0;
                do {
                    if (this.antecedent[j] != ((FuzzyRule) paramObject).antecedent[j]) {
                        if ((this.antecedent[j] != null) && (((FuzzyRule) paramObject).antecedent[j] != null)) {
                            i = i && this.antecedent[j].equals(((FuzzyRule) paramObject).antecedent[j]);
                        } else {
                            i = false;
                        }
                    }
                    j++;
                    if (j >= this.antecedent.length) {
                        break;
                    }
                } while (i);
            }
        }
        return i;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Arrays.deepHashCode(this.antecedent);
        return hash;
    }

    public boolean isGeneralizationOf(FuzzyRule paramFuzzyRule) {
        int i = 0;
        int j = 0;
        if ((this.antecedent != null) && (paramFuzzyRule.antecedent != null)) {
            i = this.antecedent.length != paramFuzzyRule.antecedent.length ? 0 : 1;
            for (int k = 0; (k < this.antecedent.length) && (i != 0); k++) {
                if ((this.antecedent[k] == null) && (paramFuzzyRule.antecedent[k] != null)) {
                    j = 1;
                } else {
                    if ((this.antecedent[k] == null) || (paramFuzzyRule.antecedent[k] == null)) {
                        continue;
                    }
                    i = (i == 0) || (!this.antecedent[k].equals(paramFuzzyRule.antecedent[k])) ? 0 : 1;
                }
            }
        }
        return (i != 0) && (j != 0);
    }

    public boolean isSpecializationOf(FuzzyRule paramFuzzyRule) {
        int i = 0;
        int j = 0;
        if ((this.antecedent != null) && (paramFuzzyRule.antecedent != null)) {
            i = this.antecedent.length != paramFuzzyRule.antecedent.length ? 0 : 1;
            for (int k = 0; (k < this.antecedent.length) && (i != 0); k++) {
                if ((this.antecedent[k] != null) && (paramFuzzyRule.antecedent[k] == null)) {
                    j = 1;
                } else {
                    if ((this.antecedent[k] == null) || (paramFuzzyRule.antecedent[k] == null)) {
                        continue;
                    }
                    i = (i == 0) || (!this.antecedent[k].equals(paramFuzzyRule.antecedent[k])) ? 0 : 1;
                }
            }
        }
        return (i != 0) && (j != 0);
    }

    public void addAntecedent(Variable paramVariable, FuzzySet paramFuzzySet) {
        this.antecedent[paramVariable.index] = new LinguisticExpression(paramVariable, paramFuzzySet);
    }

    public void deleteAntecedent(Variable paramVariable) {
        this.antecedent[paramVariable.index] = null;
    }

    public void deleteAntecedent(int paramInt) {
        this.antecedent[paramInt] = null;
    }

    public FuzzySet getAntecedentFuzzySet(int paramInt) {
        if (this.antecedent[paramInt] != null) {
            return this.antecedent[paramInt].getFuzzySet();
        }
        return null;
    }

    public int getAntecedentFuzzySetIndex(int paramInt) {
        if (this.antecedent[paramInt] != null) {
            return this.antecedent[paramInt].getFuzzySetIndex();
        }
        return -1;
    }

    public boolean variableIsUsed(int paramInt) {
        return this.antecedent[paramInt] != null;
    }

    public boolean usesTerm(int paramInt1, int paramInt2) {
        if (this.antecedent[paramInt1] != null) {
            return this.antecedent[paramInt1].getFuzzySetIndex() == paramInt2;
        }
        return false;
    }

    public int numberOfVariablesUsed() {
        int i = 0;
        for (int j = 0; j < this.antecedent.length; j++) {
            if (this.antecedent[j] == null) {
                continue;
            }
            i++;
        }
        return i;
    }

    public abstract void addConsequent(Variable paramVariable, Object paramObject);

    public abstract void deleteConsequent(Variable paramVariable);

    @Override
    public String toString() {
        String str = antecedentToString();
        return str;
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public String getName() {
        return this.name;
    }

    private String antecedentToString() {
        String str = "if ";
        int i = numberOfVariablesUsed();
        for (int j = 0; j < this.antecedent.length; j++) {
            if (this.antecedent[j] == null) {
                continue;
            }
            str = str + this.antecedent[j].toString();
            i--;
            if (i <= 0) {
                continue;
            }
            str = str + " and ";
        }
        return str;
    }

    public double getDegreeOfFulfilment() {
        return this.hit;
    }

    public double computeDegreeOfFulfilment(double[] paramArrayOfDouble) {
        this.hit = 1.0D;
        this.minDegree = 2.0D;
        this.indexMinDegree = -1;
        for (int i = 0; (i < this.antecedent.length) && (this.hit > 0.0D); i++) {
            if (this.antecedent[i] == null) {
                continue;
            }
            double d = this.antecedent[i].match(paramArrayOfDouble[i]);
            this.hit = this.tNorm.and(this.hit, d);
            if (d >= this.minDegree) {
                continue;
            }
            this.minDegree = d;
            this.indexMinDegree = i;
        }
        return this.hit * this.weight;
    }

    public boolean valid() {
        return this.isValid;
    }

    public void setValid(boolean paramBoolean) {
        this.isValid = paramBoolean;
    }

    public double getPerformance() {
        return this.performance;
    }

    public void setPerformance(double paramDouble) {
        this.performance = paramDouble;
    }

    abstract double computeOutput();

    public void computeError(double paramDouble) {
        this.error = (paramDouble * (this.hit * (1.0D - this.hit) + 0.01D));
        this.deltaW += this.error * this.weight;
    }

    public double getError() {
        return this.error;
    }

    public void computeFuzzySetUpdates(boolean paramBoolean1, boolean paramBoolean2) {
        if (this.error != 0.0D) {
            if (paramBoolean2) {
                this.antecedent[this.indexMinDegree].computeUpdates(this.error, paramBoolean1);
                return;
            }
            for (int i = 0; i < this.antecedent.length; i++) {
                if (this.antecedent[i] == null) {
                    continue;
                }
                this.antecedent[i].computeUpdates(this.error, paramBoolean1);
            }
        }
    }

    public void updateWeight(boolean paramBoolean, double paramDouble) {
        this.weight += paramDouble * this.deltaW;
        this.deltaW = 0.0D;
        if (paramBoolean) {
            if (this.weight > 1.0D) {
                this.weight = 1.0D;
            }
            if (this.weight < 0.0D) {
                this.weight = 0.0D;
            }
        }
    }

    public double getWeight() {
        return this.weight;
    }

    @Override
    public synchronized Object clone() {
        try {
            FuzzyRule localFuzzyRule = (FuzzyRule) super.clone();
            localFuzzyRule.antecedent = ((LinguisticExpression[]) this.antecedent.clone());
            return localFuzzyRule;
        } catch (CloneNotSupportedException localCloneNotSupportedException) {
        }
        throw new InternalError();
    }
}