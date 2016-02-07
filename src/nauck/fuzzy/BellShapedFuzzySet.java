package nauck.fuzzy;

import java.awt.FontMetrics;
import java.awt.Graphics;

public class BellShapedFuzzySet extends FuzzySet
        implements Cloneable {

    double center;
    double width;
    double overlap;
    double deltaCenter;
    double deltaWidth;
    double delta;
    double factor;
    int shift;
    boolean leftShouldered;
    boolean rightShouldered;

    BellShapedFuzzySet(double[] paramArrayOfDouble, int paramInt)
            throws FuzzySetInvalidException {
        super(paramInt);
        setParameters(paramArrayOfDouble);
        this.type = 2;
        this.typeName = "BELLSHAPED";
        this.lastX = (this.center - 2.0D * this.width);
        resetUpdates();
    }

    @Override
    public double computeDegree(double paramDouble) {
        this.lastX = paramDouble;
        if (((paramDouble <= this.center) && (this.leftShouldered)) || ((paramDouble >= this.center) && (this.rightShouldered)) || (paramDouble == (1.0D / 0.0D))) {
            this.degree = 1.0D;
        } else {
            double d = this.overlap * (this.center - paramDouble) / this.width;
            d *= d;
            this.degree = Math.exp(-d);
        }
        return this.degree;
    }

    @Override
    public void setParameters(double[] paramArrayOfDouble)
            throws FuzzySetInvalidException {
        if (paramArrayOfDouble.length == 5) {
            if ((paramArrayOfDouble[1] > 0.0D) && (paramArrayOfDouble[2] > 0.0D)) {
                this.center = paramArrayOfDouble[0];
                this.width = paramArrayOfDouble[1];
                this.overlap = paramArrayOfDouble[2];
                this.leftShouldered = (paramArrayOfDouble[3] > 0.0D);
                this.rightShouldered = (paramArrayOfDouble[4] > 0.0D);
                return;
            }
            throw new FuzzySetInvalidException("Неверные параметры " + this.name);
        }
        throw new FuzzySetInvalidException("Неверные параметры " + this.name);
    }

    @Override
    public double[] getParameters() {
        double[] arrayOfDouble = new double[5];
        arrayOfDouble[0] = this.center;
        arrayOfDouble[1] = this.width;
        arrayOfDouble[2] = this.overlap;
        arrayOfDouble[3] = (this.leftShouldered ? 1 : 0);
        arrayOfDouble[4] = (this.rightShouldered ? 1 : 0);
        return arrayOfDouble;
    }

    @Override
    public double getSupport() {
        return 2.0D * this.width;
    }

    @Override
    public String printParameters() {
        String str = this.center + "  " + this.width + "  " + this.overlap;
        if (this.leftShouldered) {
            str = str + "  1  ";
        } else {
            str = str + "  0  ";
        }
        if (this.rightShouldered) {
            str = str + "  1  ";
        } else {
            str = str + "  0  ";
        }
        return str;
    }

    @Override
    public void computeUpdates(double paramDouble, boolean paramBoolean) {
        this.delta = 0.0D;
        if (paramDouble < 0.0D) {
            this.factor = this.degree;
        } else {
            this.factor = (1.0D - this.degree);
        }
        this.delta = (paramDouble * this.factor * this.center);
        if (this.lastX < this.center) {
            this.deltaCenter -= this.delta;
        } else {
            this.deltaCenter += this.delta;
        }
        this.deltaWidth += this.delta;
    }

    @Override
    public void resetUpdates() {
        this.deltaCenter = 0.0D;
        this.deltaWidth = 0.0D;
        this.shift = 0;
    }

    @Override
    public void checkUpdates(double paramDouble1, double paramDouble2, double paramDouble3) {
        this.deltaCenter *= paramDouble1;
        this.deltaWidth *= paramDouble1;
        if ((this.leftShouldered) || (this.rightShouldered)) {
            this.deltaCenter = 0.0D;
        }
        if (this.width + this.deltaWidth > paramDouble3 - paramDouble2) {
            this.deltaWidth = (paramDouble3 - paramDouble2 - this.width);
        }
        if (this.width + this.deltaWidth < (paramDouble3 - paramDouble2) / 200.0D) {
            this.deltaWidth = ((paramDouble3 - paramDouble2) / 200.0D - this.width);
        }
    }

    @Override
    public void doNotPassRightNeighbor(FuzzySet paramFuzzySet) {
        if ((!this.leftShouldered) && ((paramFuzzySet instanceof BellShapedFuzzySet))) {
            BellShapedFuzzySet localBellShapedFuzzySet = (BellShapedFuzzySet) paramFuzzySet;
            if ((this.center + this.deltaCenter > localBellShapedFuzzySet.center) && (!this.leftShouldered)) {
                this.deltaCenter = (localBellShapedFuzzySet.center - this.center);
            }
        }
    }

    @Override
    public void doNotPassLeftNeighbor(FuzzySet paramFuzzySet) {
        if ((!this.rightShouldered) && ((paramFuzzySet instanceof BellShapedFuzzySet))) {
            BellShapedFuzzySet localBellShapedFuzzySet = (BellShapedFuzzySet) paramFuzzySet;
            if ((this.center + this.deltaCenter < localBellShapedFuzzySet.center) && (!this.rightShouldered)) {
                this.deltaCenter = (localBellShapedFuzzySet.center - this.center);
            }
        }
    }

    @Override
    public void overlapWithRightNeighbor(FuzzySet paramFuzzySet, double paramDouble, boolean paramBoolean) {
        if ((paramFuzzySet instanceof BellShapedFuzzySet)) {
            BellShapedFuzzySet localBellShapedFuzzySet = (BellShapedFuzzySet) paramFuzzySet;
            if (this.center + this.width < localBellShapedFuzzySet.center - localBellShapedFuzzySet.width) {
                double d = (localBellShapedFuzzySet.center - this.center) / 2.0D;
                this.width = (d - this.center);
                localBellShapedFuzzySet.width = (localBellShapedFuzzySet.center - d);
            }
        }
    }

    @Override
    public void computePositionAddUpToOne(FuzzySet paramFuzzySet1, FuzzySet paramFuzzySet2) {
        if ((!this.rightShouldered) && (paramFuzzySet2 != null) && ((paramFuzzySet2 instanceof BellShapedFuzzySet))) {
            BellShapedFuzzySet localBellShapedFuzzySet = (BellShapedFuzzySet) paramFuzzySet2;
            double d1 = 0.5D * (localBellShapedFuzzySet.center - 0.49D * localBellShapedFuzzySet.width - (this.center + 0.49D * this.width));
            double d2 = this.center - 0.49D * this.width;
            double d3 = this.center + 0.49D * this.width + d1;
            if (this.leftShouldered) {
                this.width += 2.0D * d1;
            } else {
                this.center = ((d2 + d3) / 2.0D);
                this.width = (d3 - d2);
            }
            if (((localBellShapedFuzzySet.shift <= 0) && (d1 < 0.0D)) || ((localBellShapedFuzzySet.shift >= 0) && (d1 > 0.0D)) || (localBellShapedFuzzySet.rightShouldered)) {
                localBellShapedFuzzySet.width += 2.0D * d1;
                return;
            }
            localBellShapedFuzzySet.center -= 0.5D * d1;
            localBellShapedFuzzySet.width += d1;
        }
    }

    @Override
    public void computeSpreadAddUpToOne(FuzzySet paramFuzzySet1, FuzzySet paramFuzzySet2) {
    }

    @Override
    public void applyUpdates() {
        this.center += this.deltaCenter;
        this.width += this.deltaWidth;
        if (this.deltaCenter > 0.0D) {
            this.shift = 1;
        } else if (this.deltaCenter < 0.0D) {
            this.shift = -1;
        } else {
            this.shift = 0;
        }
        this.deltaCenter = 0.0D;
        this.deltaWidth = 0.0D;
    }

    @Override
    public void draw(FuzzyDrawContext paramFuzzyDrawContext) {
        Graphics localGraphics = paramFuzzyDrawContext.getGraphics();
        localGraphics.setColor(this.color);
        double d2 = paramFuzzyDrawContext.lb;
        int n = paramFuzzyDrawContext.lx + (int) ((this.center - paramFuzzyDrawContext.lb) * paramFuzzyDrawContext.scale);
        double d1 = paramFuzzyDrawContext.lb;
        double d3 = 0.0D;
        double d4;
        while (d3 <= 0.01D) {
            d4 = this.overlap * (this.center - d1) / this.width;
            d4 *= d4;
            d3 = Math.exp(-d4);
            d2 = d1;
            d1 += paramFuzzyDrawContext.delta;
        }
        int i = paramFuzzyDrawContext.lx + (int) ((d2 - paramFuzzyDrawContext.lb) * paramFuzzyDrawContext.scale);
        int j = paramFuzzyDrawContext.ly - (int) (d3 * (paramFuzzyDrawContext.ly - paramFuzzyDrawContext.uy));
        d1 = d2 + paramFuzzyDrawContext.delta;
        while ((d1 <= paramFuzzyDrawContext.ub) && (d3 > 0.01D)) {
            int k = paramFuzzyDrawContext.lx + (int) ((d1 - paramFuzzyDrawContext.lb) * paramFuzzyDrawContext.scale);
            d4 = this.overlap * (this.center - d1) / this.width;
            d4 *= d4;
            d3 = Math.exp(-d4);
            int m = paramFuzzyDrawContext.ly - (int) (d3 * (paramFuzzyDrawContext.ly - paramFuzzyDrawContext.uy));
            localGraphics.drawLine(i, j, k, m);
            i = k;
            j = m;
            d1 += paramFuzzyDrawContext.delta;
        }
        localGraphics.drawString(this.shortName, n - localGraphics.getFontMetrics().stringWidth(this.shortName) / 2, paramFuzzyDrawContext.uy - paramFuzzyDrawContext.distance - paramFuzzyDrawContext.descent);
    }

    @Override
    public synchronized Object clone() {
        return super.clone();
    }

    @Override
    public synchronized void copyTo(FuzzySet paramFuzzySet) {
        super.copyTo(paramFuzzySet);
        if ((paramFuzzySet instanceof BellShapedFuzzySet)) {
            BellShapedFuzzySet localBellShapedFuzzySet = (BellShapedFuzzySet) paramFuzzySet;
            localBellShapedFuzzySet.center = this.center;
            localBellShapedFuzzySet.width = this.width;
            localBellShapedFuzzySet.overlap = this.overlap;
        }
    }
}

/* Location:           W:\nefcalss\nefclass.jar
 * Qualified Name:     nauck.fuzzy.BellShapedFuzzySet
 * JD-Core Version:    0.6.0
 */