package nauck.fuzzy;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;

public class SpecifyFPPanel extends Panel {

    SpecifyFuzzyPartition[] specifications;
    protected GridLayout layout = new GridLayout(0, 1);

    public SpecifyFPPanel(String[] paramArrayOfString, int[] paramArrayOfInt1, int[] paramArrayOfInt2) {
        this.layout.setVgap(0);
        setLayout(this.layout);
        setSize(350, 25);
        this.specifications = new SpecifyFuzzyPartition[paramArrayOfString.length];
        for (int i = 0; i < paramArrayOfString.length; i++) {
            this.specifications[i] = new SpecifyFuzzyPartition(paramArrayOfString[i], paramArrayOfInt1[i], paramArrayOfInt2[i]);
            add(this.specifications[i]);
        }
    }

    @Override
    public void paint(Graphics paramGraphics) {
        for (int i = 0; i < this.specifications.length; i++) {
            this.specifications[i].repaint();
        }
    }

    public int[] getFSNumbers() {
        int[] arrayOfInt = new int[this.specifications.length];
        for (int i = 0; i < this.specifications.length; i++) {
            arrayOfInt[i] = this.specifications[i].getFSNumber();
        }
        return arrayOfInt;
    }

    public int[] getFSTypes() {
        int[] arrayOfInt = new int[this.specifications.length];
        for (int i = 0; i < this.specifications.length; i++) {
            arrayOfInt[i] = this.specifications[i].getFSType();
        }
        return arrayOfInt;
    }

    public void setFSNumbers(int paramInt) {
        for (int i = 0; i < this.specifications.length; i++) {
            this.specifications[i].setFSNumber(paramInt);
        }
    }

    public void setFSTypes(int paramInt) {
        for (int i = 0; i < this.specifications.length; i++) {
            this.specifications[i].setFSType(paramInt);
        }
    }
}