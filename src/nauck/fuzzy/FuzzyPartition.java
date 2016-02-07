package nauck.fuzzy;

import java.awt.Color;
import java.awt.Graphics;
import nauck.util.FormatString;

public class FuzzyPartition
        implements Cloneable {

    protected int variable;
    protected String name;
    protected double lowerBound;
    protected double upperBound;
    protected FuzzySet[] fuzzySets;
    protected double lastX;
    protected double bestHit;
    protected int indexBestHit;
    protected int lastMin;
    protected double lastMinX;

    public FuzzyPartition(int paramInt, double paramDouble1, double paramDouble2, String paramString) {
        this.variable = paramInt;
        this.lowerBound = paramDouble1;
        this.upperBound = paramDouble2;
        this.name = paramString;
        this.lastX = this.lowerBound;
        this.bestHit = 1.0D;
        this.indexBestHit = 0;
        this.lastMin = -1;
        this.lastMinX = this.upperBound;
    }

    public void createFuzzyPartition(int paramInt1, int paramInt2)
            throws FuzzySetInvalidException {
        double d1 = this.lowerBound;
        double d2 = 0.0D;
        switch (paramInt2) {
            case 0:
                d2 = (this.upperBound - this.lowerBound) / (paramInt1 + 1);
                break;
            case 1:
                d2 = (this.upperBound - this.lowerBound) / (2 * paramInt1 - 1);
                break;
            case 2:
                d2 = (this.upperBound - this.lowerBound) / (paramInt1 - 1);
                break;
            case 3:
                d2 = this.upperBound - this.lowerBound + 1.0D;
                break;
            default:
                throw new FuzzySetInvalidException("Не удается создать нечеткое множество.");
        }
        this.fuzzySets = new FuzzySet[paramInt1];
        for (int i = 0; i < paramInt1; i++) {
            double[] arrayOfDouble;
            switch (paramInt2) {
                case 0:
                    arrayOfDouble = new double[5];
                    arrayOfDouble[0] = d1;
                    double tmp189_188 = (d1 + d2);
                    d1 = tmp189_188;
                    arrayOfDouble[1] = tmp189_188;
                    arrayOfDouble[2] = (d1 + d2);
                    if (i == 0) {
                        arrayOfDouble[3] = 1.0D;
                    } else {
                        arrayOfDouble[3] = 0.0D;
                    }
                    if (i == paramInt1 - 1) {
                        arrayOfDouble[4] = 1.0D;
                    } else {
                        arrayOfDouble[4] = 0.0D;
                    }
                    this.fuzzySets[i] = new TriangularFuzzySet(arrayOfDouble, i);
                    this.fuzzySets[i].setName(paramInt1 - 1, i);
                    break;
                case 1:
                    arrayOfDouble = new double[6];
                    arrayOfDouble[0] = d1;
                    if (i == 0) {
                        arrayOfDouble[1] = d1;
                    } else {
                        double tmp306_305 = (d1 + d2);
                        d1 = tmp306_305;
                        arrayOfDouble[1] = tmp306_305;
                    }
                    double i2 = (d1 + d2);
                    d1 = i2;
                    arrayOfDouble[2] = i2;
                    if (i2 == paramInt1 - 1) {
                        arrayOfDouble[3] = d1;
                    } else {
                        arrayOfDouble[3] = (d1 + d2);
                    }
                    arrayOfDouble[4] = (i2 == 0 ? 1 : 0);
                    arrayOfDouble[5] = (i2 == paramInt1 - 1 ? 1 : 0);
                    this.fuzzySets[i] = new TrapezoidalFuzzySet(arrayOfDouble, i);
                    this.fuzzySets[i].setName(paramInt1 - 1, i);
                    break;
                case 2:
                    arrayOfDouble = new double[5];
                    arrayOfDouble[0] = d1;
                    arrayOfDouble[1] = d2;
                    arrayOfDouble[2] = 1.7D;
                    arrayOfDouble[3] = (i == 0 ? 1 : 0);
                    arrayOfDouble[4] = (i == paramInt1 - 1 ? 1 : 0);
                    this.fuzzySets[i] = new BellShapedFuzzySet(arrayOfDouble, i);
                    this.fuzzySets[i].setName(paramInt1 - 1, i);
                    d1 += d2;
                    break;
                case 3:
                    arrayOfDouble = new double[(int) d2 + 2];
                    arrayOfDouble[0] = this.lowerBound;
                    arrayOfDouble[1] = this.upperBound;
                    this.fuzzySets[i] = new ListFuzzySet(arrayOfDouble, i);
                    this.fuzzySets[i].setName(-1, i);
                    break;
                default:
                    throw new FuzzySetInvalidException("Не удается создать нечеткое множество.");
            }
        }
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public void setFuzzySet(int paramInt, double[] paramArrayOfDouble)
            throws FuzzySetInvalidException {
        if ((paramInt >= 0) && (paramInt < this.fuzzySets.length)) {
            this.fuzzySets[paramInt].setParameters(paramArrayOfDouble);
            return;
        }
        if ((paramInt == this.fuzzySets.length) && (this.fuzzySets[0].getType() == 3)) {
            FuzzySet[] arrayOfFuzzySet = new FuzzySet[paramInt + 1];
            System.arraycopy(this.fuzzySets, 0, arrayOfFuzzySet, 0, this.fuzzySets.length);
            this.fuzzySets = arrayOfFuzzySet;
            this.fuzzySets[paramInt] = new ListFuzzySet(paramArrayOfDouble, paramInt);
            this.fuzzySets[paramInt].setName(-1, paramInt);
            return;
        }
        throw new FuzzySetInvalidException("Индекс нечеткого множества вне диапазона.");
    }

    public FuzzySet addListFuzzySet(double[] paramArrayOfDouble)
            throws FuzzySetInvalidException {
        boolean bool = false;
        int i = -1;
        if (this.fuzzySets[0].getType() == 3) {
            ListFuzzySet localListFuzzySet = new ListFuzzySet(paramArrayOfDouble, this.fuzzySets.length);
            for (int j = 0; (j < this.fuzzySets.length) && (!bool); j++) {
                bool = localListFuzzySet.equals(this.fuzzySets[j]);
                if (!bool) {
                    continue;
                }
                i = j;
            }
            if (!bool) {
                i = this.fuzzySets.length;
                setFuzzySet(this.fuzzySets.length, paramArrayOfDouble);
            }
        }
        if (i > -1) {
            return this.fuzzySets[i];
        }
        return null;
    }

    public void setFuzzySetNames(int paramInt, String paramString1, String paramString2)
            throws FuzzySetInvalidException {
        if ((paramInt >= 0) && (paramInt < this.fuzzySets.length)) {
            this.fuzzySets[paramInt].setNames(paramString1, paramString2);
            return;
        }
        throw new FuzzySetInvalidException("Индекс нечеткого множества вне диапазона.");
    }

    public void draw(FuzzyDrawContext paramFuzzyDrawContext) {
        Graphics localGraphics = paramFuzzyDrawContext.getGraphics();
        localGraphics.setColor(Color.black);
        localGraphics.drawRect(1, 1, paramFuzzyDrawContext.width - 1, paramFuzzyDrawContext.height - 2);
        localGraphics.drawLine(paramFuzzyDrawContext.lx, paramFuzzyDrawContext.ly + paramFuzzyDrawContext.tic, paramFuzzyDrawContext.lx, paramFuzzyDrawContext.uy);
        localGraphics.drawLine(paramFuzzyDrawContext.lx, paramFuzzyDrawContext.ly, paramFuzzyDrawContext.ux, paramFuzzyDrawContext.ly);
        localGraphics.drawLine(paramFuzzyDrawContext.lx, paramFuzzyDrawContext.uy, paramFuzzyDrawContext.lx - paramFuzzyDrawContext.tic, paramFuzzyDrawContext.uy);
        localGraphics.drawLine(paramFuzzyDrawContext.lx, paramFuzzyDrawContext.ly - (paramFuzzyDrawContext.ly - paramFuzzyDrawContext.uy) / 2, paramFuzzyDrawContext.lx - paramFuzzyDrawContext.tic, paramFuzzyDrawContext.ly - (paramFuzzyDrawContext.ly - paramFuzzyDrawContext.uy) / 2);
        localGraphics.drawLine(paramFuzzyDrawContext.lx, paramFuzzyDrawContext.ly, paramFuzzyDrawContext.lx - paramFuzzyDrawContext.tic, paramFuzzyDrawContext.ly);
        localGraphics.drawString(FormatString.doubleString(1.0D, 0, 1), paramFuzzyDrawContext.yLabelPos, paramFuzzyDrawContext.uy);
        localGraphics.drawString(FormatString.doubleString(0.5D, 0, 1), paramFuzzyDrawContext.yLabelPos, paramFuzzyDrawContext.ly - (paramFuzzyDrawContext.ly - paramFuzzyDrawContext.uy) / 2);
        localGraphics.drawString(FormatString.doubleString(0.0D, 0, 1), paramFuzzyDrawContext.yLabelPos, paramFuzzyDrawContext.ly);
        localGraphics.drawString(FormatString.doubleString(this.lowerBound, 0, 1), paramFuzzyDrawContext.lx - paramFuzzyDrawContext.xLabelWidth, paramFuzzyDrawContext.xLabelPos);
        double d1 = (this.upperBound - this.lowerBound) / paramFuzzyDrawContext.nXtics;
        int i = (paramFuzzyDrawContext.ux - paramFuzzyDrawContext.lx) / paramFuzzyDrawContext.nXtics;
        int j = paramFuzzyDrawContext.lx + i;
        double d2 = this.lowerBound + d1;
        while (d2 < this.upperBound) {
            localGraphics.drawLine(j, paramFuzzyDrawContext.ly, j, paramFuzzyDrawContext.ly + paramFuzzyDrawContext.tic);
            localGraphics.drawString(FormatString.doubleString(d2, 0, 1), j - paramFuzzyDrawContext.xLabelWidth, paramFuzzyDrawContext.xLabelPos);
            j += i;
            d2 += d1;
        }
        localGraphics.drawLine(paramFuzzyDrawContext.ux, paramFuzzyDrawContext.ly, paramFuzzyDrawContext.ux, paramFuzzyDrawContext.ly + paramFuzzyDrawContext.tic);
        localGraphics.drawString(FormatString.doubleString(this.upperBound, 0, 1), paramFuzzyDrawContext.ux - paramFuzzyDrawContext.xLabelWidth, paramFuzzyDrawContext.xLabelPos);
        localGraphics.drawString(this.name, (paramFuzzyDrawContext.lx + paramFuzzyDrawContext.ux) / 2 - localGraphics.getFontMetrics().stringWidth(this.name) / 2, paramFuzzyDrawContext.ascent + paramFuzzyDrawContext.distance);
        localGraphics.setColor(Color.red);
        for (int k = 0; k < this.fuzzySets.length; k++) {
            this.fuzzySets[k].draw(paramFuzzyDrawContext);
        }
    }

    public String getName() {
        return this.name;
    }

    public double getLowerBound() {
        return this.lowerBound;
    }

    public double getUpperBound() {
        return this.upperBound;
    }

    public int getNumberOfFuzzySets() {
        return this.fuzzySets.length;
    }

    public FuzzySet getMaximumDegreeFS(double paramDouble) {
        if (paramDouble != this.lastX) {
            findBestHit(paramDouble);
        }
        return this.fuzzySets[this.indexBestHit];
    }

    public int getIndexOfMaximumDegree(double paramDouble) {
        if (paramDouble != this.lastX) {
            findBestHit(paramDouble);
        }
        return this.indexBestHit;
    }

    public double getMaximumDegree(double paramDouble) {
        if (paramDouble != this.lastX) {
            findBestHit(paramDouble);
        }
        return this.bestHit;
    }

    public FuzzySet getFuzzySet(int paramInt) {
        if ((paramInt >= 0) && (paramInt < this.fuzzySets.length)) {
            return this.fuzzySets[paramInt];
        }
        return null;
    }

    public double getFuzzySetSupport(int paramInt) {
        if ((paramInt >= 0) && (paramInt < this.fuzzySets.length)) {
            return this.fuzzySets[paramInt].getSupport();
        }
        return 0.0D;
    }

    public String getFuzzySetName(int paramInt) {
        if ((paramInt >= 0) && (paramInt < this.fuzzySets.length)) {
            return this.fuzzySets[paramInt].toString();
        }
        return "null";
    }

    public String getFuzzySetTypeName(int paramInt) {
        if ((paramInt >= 0) && (paramInt < this.fuzzySets.length)) {
            return this.fuzzySets[paramInt].getTypeName();
        }
        return "null";
    }

    public int getType() {
        int i = -1;
        if ((this.fuzzySets != null) && (this.fuzzySets[0] != null)) {
            i = this.fuzzySets[0].getType();
        }
        return i;
    }

    public int minFSIndex(double paramDouble) {
        double d1 = 1.0D;
        if ((paramDouble != this.lastMinX) || (this.lastMin == -1)) {
            this.lastMinX = paramDouble;
            this.lastMin = -1;
            for (int i = 0; i < this.fuzzySets.length; i++) {
                double d2 = this.fuzzySets[i].getDegree(paramDouble);
                if (d2 >= d1) {
                    continue;
                }
                d1 = d2;
                this.lastMin = i;
            }
        }
        return this.lastMin;
    }

    protected void findBestHit(double paramDouble) {
        this.indexBestHit = -1;
        this.bestHit = 0.0D;
        this.lastX = paramDouble;
        if (paramDouble < this.lowerBound) {
            this.indexBestHit = 0;
            this.bestHit = 1.0D;
            return;
        }
        if (paramDouble > this.upperBound) {
            this.indexBestHit = (this.fuzzySets.length - 1);
            this.bestHit = 1.0D;
            return;
        }
        for (int i = 0; i < this.fuzzySets.length; i++) {
            double d = this.fuzzySets[i].getDegree(paramDouble);
            if (d <= this.bestHit) {
                continue;
            }
            this.indexBestHit = i;
            this.bestHit = d;
        }
    }

    public void applyUpdates(double paramDouble, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
        double d = (this.upperBound - this.lowerBound) / 200.0D;
        for (int i = 0; i < this.fuzzySets.length; i++) {
            FuzzySet localFuzzySet1 = this.fuzzySets[i];
            localFuzzySet1.checkUpdates(paramDouble, this.lowerBound, this.upperBound);
            FuzzySet localFuzzySet2;
            if (i > 0) {
                localFuzzySet2 = this.fuzzySets[(i - 1)];
            } else {
                localFuzzySet2 = null;
            }
            FuzzySet localFuzzySet3;
            if (i < this.fuzzySets.length - 1) {
                localFuzzySet3 = this.fuzzySets[(i + 1)];
            } else {
                localFuzzySet3 = null;
            }
            if ((paramBoolean2) || (paramBoolean3) || (paramBoolean4)) {
                if (localFuzzySet2 != null) {
                    localFuzzySet1.doNotPassLeftNeighbor(localFuzzySet2);
                }
                if (localFuzzySet3 != null) {
                    localFuzzySet1.doNotPassRightNeighbor(localFuzzySet3);
                }
            }
            localFuzzySet1.applyUpdates();
            if ((paramBoolean3) && (!paramBoolean4) && (localFuzzySet3 != null)) {
                localFuzzySet1.overlapWithRightNeighbor(localFuzzySet3, d, paramBoolean1);
            }
            if ((!paramBoolean4) || (localFuzzySet3 == null)) {
                continue;
            }
            localFuzzySet1.computePositionAddUpToOne(localFuzzySet2, localFuzzySet3);
        }
    }

    public void copyTo(FuzzyPartition paramFuzzyPartition) {
        paramFuzzyPartition.variable = this.variable;
        paramFuzzyPartition.lowerBound = this.lowerBound;
        paramFuzzyPartition.upperBound = this.upperBound;
        paramFuzzyPartition.name = this.name;
        paramFuzzyPartition.lastX = this.lastX;
        paramFuzzyPartition.bestHit = this.bestHit;
        paramFuzzyPartition.indexBestHit = this.indexBestHit;
        if (paramFuzzyPartition.fuzzySets.length != this.fuzzySets.length) {
            int i;
            if (paramFuzzyPartition.fuzzySets.length < this.fuzzySets.length) {
                i = paramFuzzyPartition.fuzzySets.length;
            } else {
                i = this.fuzzySets.length;
            }
            FuzzySet[] arrayOfFuzzySet = new FuzzySet[this.fuzzySets.length];
            System.arraycopy(paramFuzzyPartition.fuzzySets, 0, arrayOfFuzzySet, 0, i);
            for (int m = paramFuzzyPartition.fuzzySets.length; m < this.fuzzySets.length; m++) {
                arrayOfFuzzySet[m] = ((FuzzySet) this.fuzzySets[m].clone());
            }
            paramFuzzyPartition.fuzzySets = arrayOfFuzzySet;
        }
        for (int j = 0; j < this.fuzzySets.length; j++) {
            this.fuzzySets[j].copyTo(paramFuzzyPartition.fuzzySets[j]);
        }
    }

    @Override
    public synchronized Object clone() {
        try {
            FuzzyPartition localFuzzyPartition = (FuzzyPartition) super.clone();
            for (int i = 0; i < this.fuzzySets.length; i++) {
                localFuzzyPartition.fuzzySets[i] = ((FuzzySet) this.fuzzySets[i].clone());
            }
            return localFuzzyPartition;
        } catch (CloneNotSupportedException localCloneNotSupportedException) {
        }
        throw new InternalError();
    }

    public void trim() {
        int i = 0;
        int j = 0;
        for (int k = 0; k < this.fuzzySets.length; k++) {
            if (!this.fuzzySets[k].isValid()) {
                continue;
            }
            i++;
        }
        if ((i == 0) && (this.fuzzySets[0].getType() == 3)) {
            i = 1;
            j = 1;
        }
        if (i < this.fuzzySets.length) {
            int m = 0;
            FuzzySet[] arrayOfFuzzySet = new FuzzySet[i];
            if (j != 0) {
                arrayOfFuzzySet[0] = this.fuzzySets[0];
                arrayOfFuzzySet[0].setDontCare();
            } else {
                for (int n = 0; n < this.fuzzySets.length; n++) {
                    if (!this.fuzzySets[n].isValid()) {
                        continue;
                    }
                    arrayOfFuzzySet[m] = this.fuzzySets[n];
                    arrayOfFuzzySet[m].setName(-1, m);
                    arrayOfFuzzySet[m].setIndex(m);
                    m++;
                }
            }
            this.fuzzySets = arrayOfFuzzySet;
        }
    }

    public void invalidateFuzzySets() {
        for (int i = 0; i < this.fuzzySets.length; i++) {
            this.fuzzySets[i].setValid(false);
        }
    }
}