/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/*
 * Renamed from asY
 */
public class asy_0
extends JPanel {
    private Graphics cSL;
    private Image cSM;

    public asy_0(int n2, int n3) {
        Dimension dimension = new Dimension(n2, n3);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);
        this.setMinimumSize(dimension);
        this.aFT();
    }

    private boolean aFT() {
        this.cSM = this.createImage(this.getWidth(), this.getHeight());
        if (this.cSM == null) {
            return false;
        }
        this.cSL = this.cSM.getGraphics();
        this.cSL.clearRect(0, 0, this.getWidth(), this.getHeight());
        this.cSL.setColor(Color.GRAY);
        this.cSL.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
        return true;
    }

    public void paint(Graphics graphics) {
        if (!this.aFT()) {
            super.paint(graphics);
        }
        graphics.drawImage(this.cSM, 0, 0, null);
    }

    public void update(Graphics graphics) {
        this.paint(graphics);
    }
}

