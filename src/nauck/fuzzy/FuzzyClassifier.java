package nauck.fuzzy;

public class FuzzyClassifier extends FuzzySystem {

    public static final int MAX_CLASS = 0;
    public static final int MEAN_CLASS = 1;
    public static final int WINNER_TAKES_ALL = 0;
    public static final int NOT_CLASSIFIED = -1;
    protected double[] classification;
    protected double[] maxClass;
    protected int[] maxRule;
    protected int[] rulesPerClass;
    protected int howToClassify;
    protected int winnerClass;

    public FuzzyClassifier(int paramInt1, int paramInt2, int paramInt3) {
        super(paramInt1, paramInt2);
        this.howToClassify = paramInt3;
        this.classification = new double[paramInt2];
        this.maxClass = new double[paramInt2];
        this.maxRule = new int[paramInt2];
        this.rulesPerClass = new int[paramInt2];
    }

    public void setDimensions(int paramInt1, int paramInt2) {
        this.inDimension = paramInt1;
        this.outDimension = paramInt2;
        this.classification = new double[paramInt2];
        this.maxClass = new double[paramInt2];
        this.maxRule = new int[paramInt2];
        this.rulesPerClass = new int[paramInt2];
    }

    public int classify(double[] paramArrayOfDouble) {
        resetRuleBase();
        resetClassification();
        setInputVector(paramArrayOfDouble);
        for (int j = 0; j < this.ruleBase.size(); j++) {
            double d1 = ((FuzzyClassifierRule) this.ruleBase.elementAt(j)).computeDegreeOfFulfilment(this.inputVector);
            if (d1 <= 0.0D) {
                continue;
            }
            int i = ((FuzzyClassifierRule) this.ruleBase.elementAt(j)).getClassIndex();
            this.rulesPerClass[i] += 1;
            if (this.maxClass[i] < d1) {
                this.maxRule[i] = j;
                this.maxClass[i] = d1;
            }
            if (this.howToClassify == 0) {
                this.classification[i] = this.maxClass[i];
            } else {
                this.classification[i] += d1;
            }
        }
        double d2 = 0.0D;
        this.winnerClass = -1;
        for (int k = 0; k < this.outDimension; k++) {
            if ((this.howToClassify == 1) && (this.rulesPerClass[k] != 0)) {
                this.classification[k] /= this.rulesPerClass[k];
            }
            if (d2 == this.classification[k]) {
                this.winnerClass = -1;
            } else {
                if (d2 >= this.classification[k]) {
                    continue;
                }
                d2 = this.classification[k];
                this.winnerClass = k;
            }
        }
        return this.winnerClass;
    }

    protected void resetClassification() {
        for (int i = 0; i < this.outDimension; i++) {
            this.classification[i] = 0.0D;
            this.rulesPerClass[i] = 0;
            this.maxRule[i] = -1;
            this.maxClass[i] = 0.0D;
        }
    }
}

/* Location:           W:\nefcalss\nefclass.jar
 * Qualified Name:     nauck.fuzzy.FuzzyClassifier
 * JD-Core Version:    0.6.0
 */