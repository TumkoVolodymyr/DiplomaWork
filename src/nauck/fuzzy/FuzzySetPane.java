package nauck.fuzzy;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;

public class FuzzySetPane extends Canvas {

    private FuzzyDrawContext fdc;
    protected FuzzyPartition fs;
    protected int width;
    protected int height;

    public FuzzySetPane(int paramInt1, int paramInt2, FuzzyPartition paramFuzzyPartition) {
        this.width = paramInt1;
        this.height = paramInt2;
        this.fs = paramFuzzyPartition;
        this.fdc = new FuzzyDrawContext(paramFuzzyPartition.lowerBound, paramFuzzyPartition.upperBound, paramInt1, paramInt2);
    }

    public Dimension getPreferredSize() {
        return new Dimension(this.width, this.height);
    }

    public Dimension getMinimumSize() {
        return new Dimension(this.width, this.height);
    }

    public void paint(Graphics paramGraphics) {
        clear(paramGraphics);
        this.fdc.setGraphics(paramGraphics);
        this.fs.draw(this.fdc);
    }

    public void clear(Graphics paramGraphics) {
        paramGraphics.setColor(getBackground());
        paramGraphics.fillRect(0, 0, this.width, this.height);
    }
}

/* Location:           W:\nefcalss\nefclass.jar
 * Qualified Name:     nauck.fuzzy.FuzzySetPane
 * JD-Core Version:    0.6.0
 */