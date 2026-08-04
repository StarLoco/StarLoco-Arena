/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from UK
 */
public class uk_2
extends tj_0 {
    private static Logger a = Logger.getLogger(uk_2.class);
    private int bRx = Integer.MIN_VALUE;
    private int bRy = Integer.MAX_VALUE;
    private int bRz;
    private int bRA;
    private float bRB = Float.MIN_VALUE;
    private float bRC = Float.MIN_VALUE;

    public uk_2(float f, float f2, int n2, int n3, int n4, int n5, adg_2 adg_22, int n6, int n7, ys ys2) {
        super(null, null, adg_22, n6, n7, ys2);
        this.bRx = n2;
        this.bRy = n3;
        this.bRz = n4;
        this.bRA = n5;
        this.bRB = f;
        this.bRC = f2;
    }

    public boolean aS(int n2) {
        if (!super.aS(n2)) {
            return false;
        }
        if (this.amA != null) {
            int n3 = this.bRx != Integer.MIN_VALUE ? (int)this.amA.b(this.bRx, this.bRy, Math.min(this.IP, this.wg / 2), this.wg / 2) : this.bRy;
            double d = this.IP < this.wg / 2 && this.bRB != Float.MIN_VALUE ? (double)this.bRB : (this.bRB != Float.MIN_VALUE && this.IP >= this.wg / 2 ? (double)this.amA.b(this.bRB, this.bRC, 2 * (this.IP - this.wg / 2), this.wg) : (double)this.bRC);
            int n4 = (int)((double)n3 * Math.cos(d)) + this.bRz;
            int n5 = (int)((double)n3 * Math.sin(d)) + this.bRA;
            this.getWidget().setPosition(n4, n5, true);
        }
        return true;
    }

    public void ly() {
        int n2 = (int)((double)this.bRy * Math.cos(this.bRC)) + this.bRz;
        int n3 = (int)((double)this.bRy * Math.sin(this.bRC)) + this.bRA;
        this.getWidget().setPosition(n2, n3, true);
        super.ly();
    }
}

