/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.media.opengl.GL;

public class oV
extends ep_2 {
    private float[] aaV = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
    private int aaW = 2;
    private ArrayList c = new ArrayList();

    public oV() {
        this.a(afb_2.aRp());
    }

    public void e(ArrayList arrayList) {
        this.c = arrayList;
    }

    public void setColor(Color color) {
        if (color == null) {
            return;
        }
        color.getComponents(this.aaV);
    }

    public Color getColor() {
        return new Color(this.aaV[0], this.aaV[1], this.aaV[2], this.aaV[3]);
    }

    public int getLineWidth() {
        return this.aaW;
    }

    public void setLineWidth(int n2) {
        this.aaW = n2;
    }

    public void b(GL gL) {
        if (this.aQv) {
            gL.glColor4fv(this.aaV, 0);
            gL.glLineWidth(this.aaW);
            gL.glBegin(1);
            for (Line2D line2D : this.c) {
                gL.glVertex2d(line2D.getX1(), line2D.getY1());
                gL.glVertex2d(line2D.getX2(), line2D.getY2());
            }
            gL.glEnd();
        }
    }

    public void j() {
        this.c.clear();
        this.aaV = null;
    }
}

