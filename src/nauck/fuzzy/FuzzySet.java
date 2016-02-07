package nauck.fuzzy;

import java.awt.Color;

public abstract class FuzzySet
        implements Cloneable {

    public static final int FS_TRIANGLE = 0;
    public static final int FS_TRAPEZOID = 1;
    public static final int FS_BELLSHAPED = 2;
    public static final int FS_LIST = 3;
    public static final int FS_MIN_WIDTH_PERCENT = 1;
    protected static String[][] longNames = {{"don't care"}, {"small", "large"}, {"small", "medium", "large"}, {"very small", "small", "large", "very large"}, {"very small", "small", "medium", "large", "very large"}, {"extremly small", "very small", "small", "large", "very large", "extremely large"}, {"extremly small", "very small", "small", "medium", "large", "very large", "extremely large"}};
    protected static String[][] shortNames = {{"dc"}, {"sm", "lg"}, {"sm", "md", "lg"}, {"vsm", "sm", "lg", "vlg"}, {"vsm", "sm", "md", "lg", "vlg"}, {"xsm", "vsm", "sm", "lg", "vlg", "xlg"}, {"xsm", "vsm", "sm", "md", "lg", "vlg", "xlg"}};
    static Color[] colors = {Color.red, Color.green, Color.blue, Color.magenta, Color.darkGray, Color.cyan, Color.pink, Color.gray, Color.orange};
    static int numberOfColors = 9;
    protected double lastX;
    protected double degree;
    protected String name;
    protected String shortName;
    protected int type;
    protected String typeName;
    protected int index;
    protected Color color;
    protected boolean valid;

    FuzzySet(int paramInt) {
        this.index = paramInt;
        this.lastX = 0.0D;
        this.degree = 0.0D;
        this.name = "FuzzySet";
        this.shortName = "fs";
        this.typeName = "ABSTRACT";
        this.type = -1;
        this.valid = true;
    }

    public double getDegree(double paramDouble) {
        if (paramDouble == this.lastX) {
            return this.degree;
        }
        return computeDegree(paramDouble);
    }

    public void setName(int paramInt1, int paramInt2) {
        if ((paramInt1 >= 0) && (paramInt1 < 7) && (paramInt1 >= paramInt2)) {
            this.name = longNames[paramInt1][paramInt2];
            this.shortName = shortNames[paramInt1][paramInt2];
        } else {
            this.name = ("fuzzy" + Integer.toString(paramInt2));
            this.shortName = ("fs" + Integer.toString(paramInt2));
        }
        this.color = colors[(paramInt2 % numberOfColors)];
    }

    public void setIndex(int paramInt) {
        this.index = paramInt;
    }

    public void setNames(String paramString1, String paramString2) {
        this.name = paramString1;
        this.shortName = paramString2;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getNames() {
        return this.name + " " + this.shortName;
    }

    public int getIndex() {
        return this.index;
    }

    public int getType() {
        return this.type;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public abstract double computeDegree(double paramDouble);

    public abstract void setParameters(double[] paramArrayOfDouble)
            throws FuzzySetInvalidException;

    public abstract double[] getParameters();

    public abstract double getSupport();

    public abstract String printParameters();

    public abstract void draw(FuzzyDrawContext paramFuzzyDrawContext);

    public abstract void computeUpdates(double paramDouble, boolean paramBoolean);

    public abstract void resetUpdates();

    public abstract void doNotPassRightNeighbor(FuzzySet paramFuzzySet);

    public abstract void doNotPassLeftNeighbor(FuzzySet paramFuzzySet);

    public abstract void checkUpdates(double paramDouble1, double paramDouble2, double paramDouble3);

    public abstract void overlapWithRightNeighbor(FuzzySet paramFuzzySet, double paramDouble, boolean paramBoolean);

    public abstract void computePositionAddUpToOne(FuzzySet paramFuzzySet1, FuzzySet paramFuzzySet2);

    abstract void computeSpreadAddUpToOne(FuzzySet paramFuzzySet1, FuzzySet paramFuzzySet2);

    public abstract void applyUpdates();

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object paramObject) {
        if ((paramObject instanceof FuzzySet)) {
            return ((FuzzySet) paramObject).name.equals(this.name);
        }
        return false;
    }

    @Override
    public synchronized Object clone() {
        try {
            FuzzySet localFuzzySet = (FuzzySet) super.clone();
            return localFuzzySet;
        } catch (CloneNotSupportedException localCloneNotSupportedException) {
        }
        throw new InternalError();
    }

    public void copyTo(FuzzySet paramFuzzySet) {
        paramFuzzySet.lastX = this.lastX;
        paramFuzzySet.degree = this.degree;
        paramFuzzySet.name = this.name;
        paramFuzzySet.shortName = this.shortName;
    }

    public void setValid(boolean paramBoolean) {
        this.valid = paramBoolean;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setDontCare() {
        this.index = 0;
        setName(0, 0);
    }
}