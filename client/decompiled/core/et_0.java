/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from et
 */
public class et_0
implements Du,
aFy {
    private double oF;
    private double oG;
    private double oH;
    private int oI = Integer.MIN_VALUE;
    private int oJ = Integer.MIN_VALUE;
    private int oK;
    private ArrayList oL = null;

    public et_0() {
    }

    public et_0(double d, double d2, double d3) {
        this.oF = d;
        this.oG = d2;
        this.oH = d3;
    }

    public et_0(Du du) {
        this.oF = du.getWorldX();
        this.oG = du.getWorldY();
        this.oH = du.getAltitude();
    }

    public float hA() {
        return 0.0f;
    }

    public short gp() {
        return (short)Math.floor(this.oH);
    }

    public double getAltitude() {
        return this.oH;
    }

    public int gn() {
        return (int)Math.floor(this.oF);
    }

    public int go() {
        return (int)Math.floor(this.oG);
    }

    public double getWorldX() {
        return this.oF;
    }

    public double getWorldY() {
        return this.oG;
    }

    public void b(double d, double d2) {
        this.oF = d;
        this.oG = d2;
    }

    public void a(double d, double d2, double d3) {
        this.oF = d;
        this.oG = d2;
        this.oH = d3;
    }

    public int getScreenX() {
        return this.oI;
    }

    public int getScreenY() {
        return this.oJ;
    }

    public void ai(int n2) {
        if (this.oI == n2) {
            return;
        }
        this.oI = n2;
        this.hD();
    }

    public void aj(int n2) {
        if (this.oJ == n2) {
            return;
        }
        this.oJ = n2;
        this.hD();
    }

    public void ak(int n2) {
        if (this.oK == n2) {
            return;
        }
        this.oK = n2;
        this.hD();
    }

    public int hB() {
        return this.oK;
    }

    public boolean hC() {
        return this.oI != Integer.MIN_VALUE && this.oJ != Integer.MIN_VALUE;
    }

    public void a(fj_0 fj_02) {
        if (this.oL == null) {
            this.oL = new ArrayList();
        }
        this.oL.add(fj_02);
    }

    public void b(fj_0 fj_02) {
        if (this.oL == null) {
            return;
        }
        this.oL.remove(fj_02);
        if (this.oL.size() == 0) {
            this.oL = null;
            this.oI = Integer.MIN_VALUE;
            this.oJ = Integer.MIN_VALUE;
        }
    }

    protected void hD() {
        if (this.oL != null) {
            for (int j = 0; j < this.oL.size(); ++j) {
                ((fj_0)this.oL.get(j)).a(this, this.oI, this.oJ, this.oK);
            }
        }
    }
}

