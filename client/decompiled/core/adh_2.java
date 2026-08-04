/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;
import java.awt.geom.Rectangle2D;

/*
 * Renamed from aDH
 */
class adh_2 {
    private String dyR;
    int dyS;
    private Point origin;
    private Rectangle2D dyT;
    private boolean used;

    adh_2(String string, Point point, Rectangle2D rectangle2D, int n2) {
        this.dyR = string;
        this.origin = point;
        this.dyT = rectangle2D;
        this.dyS = n2;
    }

    String aPy() {
        return this.dyR;
    }

    Point aPz() {
        return this.origin;
    }

    int aPA() {
        return (int)(-this.dyT.getMinX());
    }

    int aPB() {
        return (int)(-this.dyT.getMinY());
    }

    Rectangle2D aPC() {
        return this.dyT;
    }

    boolean aPD() {
        return this.used;
    }

    void aPE() {
        this.used = true;
    }

    void aPF() {
        this.used = false;
    }
}

