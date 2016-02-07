package nauck.fuzzy;

public class Variable
        implements Cloneable {

    protected int index;
    protected String name;
    protected double lowerBound;
    protected double upperBound;
    protected double mean;
    protected double variance;

    public Variable(int paramInt, String paramString) {
        this.index = paramInt;
        this.name = paramString;
    }

    public Variable(int paramInt, String paramString, double paramDouble1, double paramDouble2) {
        this(paramInt, paramString);
        this.lowerBound = paramDouble1;
        this.upperBound = paramDouble2;
    }

    public Variable(int paramInt, String paramString, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
        this(paramInt, paramString, paramDouble1, paramDouble2);
        this.mean = paramDouble3;
        this.variance = paramDouble4;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object paramObject) {
        boolean i = false;
        if ((paramObject instanceof Variable)) {
            i = this.index == ((Variable) paramObject).index;
        }
        return i;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.index;
        return hash;
    }
}