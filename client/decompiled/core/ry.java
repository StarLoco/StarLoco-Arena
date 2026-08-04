/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class ry {
    protected static Logger a = Logger.getLogger(ry.class);
    private int aG;
    private int aH;
    private short wp;

    public ry() {
    }

    public ry(ry ry2) {
        this.l(ry2.aG, ry2.aH, ry2.wp);
    }

    public ry(int[] nArray) {
        this.l(nArray);
    }

    public ry(int n2, int n3, short s) {
        this.l(n2, n3, s);
    }

    public ry(ry ry2, aby_2 aby_22) {
        this.l(ry2.getX() + aby_22.getX(), ry2.getY() + aby_22.getY(), (short)(ry2.wk() + aby_22.Ui()));
    }

    public ry(int n2, int n3) {
        this.aG = n2;
        this.aH = n3;
        this.wp = 0;
    }

    public ry df(int n2) {
        this.aG *= n2;
        this.aH *= n2;
        return this;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || !(object instanceof ry)) {
            return false;
        }
        ry ry2 = (ry)object;
        return this.aG == ry2.aG && this.aH == ry2.aH && this.wp == ry2.wp;
    }

    public int hashCode() {
        long l2 = 1L;
        l2 = 31L * l2 + (long)(this.aG & 0xFF);
        l2 = 31L * l2 + (long)(this.aG >> 8 & 0xFF);
        l2 = 31L * l2 + (long)(this.aG >> 16 & 0xFF);
        l2 = 31L * l2 + (long)(this.aG >> 24 & 0xFF);
        l2 = 31L * l2 + (long)(this.aH & 0xFF);
        l2 = 31L * l2 + (long)(this.aH >> 8 & 0xFF);
        l2 = 31L * l2 + (long)(this.aH >> 16 & 0xFF);
        l2 = 31L * l2 + (long)(this.aH >> 24 & 0xFF);
        l2 = 31L * l2 + (long)(this.wp & 0xFF);
        l2 = 31L * l2 + (long)(this.wp >> 8 & 0xFF);
        l2 = 31L * l2 + (long)(this.wp >> 16 & 0xFF);
        l2 = 31L * l2 + (long)(this.wp >> 24 & 0xFF);
        return (int)(l2 ^ l2 >> 32);
    }

    public String toString() {
        return "{Point3 : (" + this.aG + ", " + this.aH + ", " + this.wp + ") @" + Integer.toHexString(this.hashCode()) + "}";
    }

    public void j(int n2, int n3, int n4) {
        this.aG += n2;
        this.aH += n3;
        this.wp = (short)(this.wp + n4);
    }

    public void b(ry ry2) {
        this.aG += ry2.getX();
        this.aH += ry2.getY();
        this.wp = (short)(this.wp + ry2.wk());
    }

    public void add(int n2, int n3) {
        this.aG += n2;
        this.aH += n3;
    }

    public boolean S(int n2, int n3) {
        return this.aG == n2 && this.aH == n3;
    }

    public boolean k(int n2, int n3, int n4) {
        return this.aG == n2 && this.aH == n3 && this.wp == n4;
    }

    public boolean E(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        ry ry2 = (ry)object;
        return this.aG == ry2.aG && this.aH == ry2.aH;
    }

    public qc_0 c(ry ry2) {
        int n2 = ry2.getX() - this.aG;
        n2 = n2 == 0 ? 0 : (n2 > 0 ? 1 : -1);
        int n3 = ry2.getY() - this.aH;
        n3 = n3 == 0 ? 0 : (n3 > 0 ? 1 : -1);
        return qc_0.aG(n2, n3);
    }

    public qc_0 j(int n2, int n3, short s) {
        int n4 = n2 - this.aG;
        int n5 = n3 - this.aH;
        return qc_0.aG(n4, n5);
    }

    public qc_0 d(ry ry2) {
        int n2 = Math.abs(ry2.getX() - this.aG);
        int n3 = Math.abs(ry2.getY() - this.aH);
        qc_0 qc_02 = this.c(ry2);
        if (qc_02 == null) {
            return null;
        }
        if (qc_02.acL()) {
            return qc_02;
        }
        switch (qc_02) {
            case bEJ: {
                return n2 > n3 ? qc_0.bEK : qc_0.bEQ;
            }
            case bEN: {
                return n2 > n3 ? qc_0.bEO : qc_0.bEM;
            }
            case bEP: {
                return n2 > n3 ? qc_0.bEO : qc_0.bEQ;
            }
            case bEL: {
                return n2 > n3 ? qc_0.bEK : qc_0.bEM;
            }
        }
        return null;
    }

    public qc_0 e(ry ry2) {
        int n2 = ry2.getX() - this.aG;
        n2 = n2 == 0 ? n2 : n2 / Math.abs(n2);
        int n3 = ry2.getY() - this.aH;
        n3 = n3 == 0 ? n3 : n3 / Math.abs(n3);
        return qc_0.aG(n2, n3);
    }

    public int f(ry ry2) {
        return Math.abs(ry2.getX() - this.getX()) + Math.abs(ry2.getY() - this.getY());
    }

    public int k(int n2, int n3, short s) {
        return Math.abs(n2 - this.getX()) + Math.abs(n3 - this.getY());
    }

    public int T(int n2, int n3) {
        return Math.abs(n2 - this.getX()) + Math.abs(n3 - this.getY());
    }

    public int k(int[] nArray) {
        assert (nArray != null && nArray.length >= 2);
        return Math.abs(nArray[0] - this.getX()) + Math.abs(nArray[1] - this.getY());
    }

    public void reset() {
        this.aG = 0;
        this.aH = 0;
        this.wp = 0;
    }

    public void l(int[] nArray) {
        if (nArray.length > 1) {
            this.aG = nArray[0];
            this.aH = nArray[1];
            this.wp = nArray.length > 2 ? (short)nArray[2] : (short)0;
        } else {
            throw new IllegalArgumentException("La longueur du tableau passe en parametre n'est pas adaptee : " + nArray.length);
        }
    }

    public void l(int n2, int n3, short s) {
        this.aG = n2;
        this.aH = n3;
        this.wp = s;
    }

    public void g(ry ry2) {
        this.aG = ry2.aG;
        this.aH = ry2.aH;
        this.wp = ry2.wp;
    }

    public void a(ye_0 ye_02) {
        this.aG += ye_02.acJ()[0];
        this.aH += ye_02.acJ()[1];
    }

    public void d(agv_0 agv_02) {
        this.aG = (int)((float)this.aG + agv_02.getX());
        this.aH = (int)((float)this.aH + agv_02.getY());
        this.wp = (short)((float)this.wp + agv_02.id());
    }

    public void l(int n2, int n3, int n4) {
        this.aG -= n2;
        this.aH -= n3;
        this.wp = (short)(this.wp - n4);
    }

    public void h(ry ry2) {
        this.aG -= ry2.getX();
        this.aH -= ry2.getY();
        this.wp = (short)(this.wp - ry2.wk());
    }

    public int getX() {
        return this.aG;
    }

    public int getY() {
        return this.aH;
    }

    public short wk() {
        return this.wp;
    }

    public void setX(int n2) {
        this.aG = n2;
    }

    public void setY(int n2) {
        this.aH = n2;
    }

    public void T(short s) {
        this.wp = s;
    }

    public int[] toIntArray() {
        return new int[]{this.aG, this.aH, this.wp};
    }

    public boolean U(int n2, int n3) {
        return n2 == this.aG && n3 == this.aH || n2 == this.aG - 1 && n3 == this.aH || n2 == this.aG && n3 == this.aH - 1 || n2 == this.aG + 1 && n3 == this.aH || n2 == this.aG && n3 == this.aH + 1;
    }

    public boolean V(int n2, int n3) {
        return this.U(n2, n3) || n2 == this.aG - 1 && n3 == this.aH - 1 || n2 == this.aG + 1 && n3 == this.aH + 1 || n2 == this.aG - 1 && n3 == this.aH + 1 || n2 == this.aG + 1 && n3 == this.aH - 1;
    }
}

