package nauck.fuzzy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;

public class FuzzyPartitionLayout extends Panel {

    protected int rows = 0;
    protected int width;
    protected int height;
    protected int space;
    protected FuzzySetPane[] fsp;
    protected GridLayout layout;

    public FuzzyPartitionLayout(int paramInt1, int paramInt2, int paramInt3, FuzzyPartition[] paramArrayOfFuzzyPartition) {
        this.width = paramInt2;
        this.height = paramInt3;
        this.space = (paramInt1 + 10);
        this.layout = new GridLayout(0, 1);
        this.layout.setVgap(3);
        setLayout(this.layout);
        this.fsp = new FuzzySetPane[this.space];
        for (int i = 0; i < paramInt1; i++) {
            addFuzzySetPane(paramArrayOfFuzzyPartition[i]);
        }
    }

    public void addFuzzySetPane(FuzzyPartition paramFuzzyPartition) {
        if (this.rows == this.space) {
            this.space += 1;
            FuzzySetPane[] arrayOfFuzzySetPane = new FuzzySetPane[this.space];
            for (int i = 0; i < this.rows; i++) {
                arrayOfFuzzySetPane[i] = this.fsp[i];
            }
            this.fsp = arrayOfFuzzySetPane;
        }
        this.fsp[this.rows] = new FuzzySetPane(this.width, this.height, paramFuzzyPartition);
        add(this.fsp[this.rows]);
        this.rows += 1;
    }

    public void printAll(Graphics paramGraphics) {
        setBackground(Color.white);
        super.printAll(paramGraphics);
    }

    public void paint(Graphics paramGraphics) {
        for (int i = 0; i < this.rows; i++) {
            this.fsp[i].repaint();
        }
    }
}