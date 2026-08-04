/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aGF
 */
public class agf_0 {
    public static final int dJp = 16;
    public int bAB = Integer.MIN_VALUE;
    public int bAC = Integer.MAX_VALUE;
    public int bAD = Integer.MIN_VALUE;
    public int bAE = Integer.MAX_VALUE;

    public agf_0(agf_0 agf_02) {
        this.set(agf_02.bAB, agf_02.bAC, agf_02.bAD, agf_02.bAE);
    }

    public agf_0(int n2, int n3, int n4, int n5) {
        this.set(n2, n3, n4, n5);
    }

    public agf_0() {
    }

    public final ByteBuffer Z(ByteBuffer byteBuffer) {
        byteBuffer.putInt(this.bAB);
        byteBuffer.putInt(this.bAD);
        byteBuffer.putInt(this.bAC);
        byteBuffer.putInt(this.bAE);
        return byteBuffer;
    }

    public final ByteBuffer aa(ByteBuffer byteBuffer) {
        this.bAB = byteBuffer.getInt();
        this.bAD = byteBuffer.getInt();
        this.bAC = byteBuffer.getInt();
        this.bAE = byteBuffer.getInt();
        return byteBuffer;
    }

    public final void set(int n2, int n3, int n4, int n5) {
        this.bAB = n2;
        this.bAC = n3;
        this.bAD = n4;
        this.bAE = n5;
    }

    public final int aSQ() {
        return this.bAB;
    }

    public final void ow(int n2) {
        this.bAB = n2;
    }

    public final int aSR() {
        return this.bAC;
    }

    public final void ox(int n2) {
        this.bAC = n2;
    }

    public final int aSS() {
        return this.bAD;
    }

    public final void oy(int n2) {
        this.bAD = n2;
    }

    public final int aST() {
        return this.bAE;
    }

    public final void oz(int n2) {
        this.bAE = n2;
    }

    public final int width() {
        return this.bAC - this.bAB + 1;
    }

    public final int height() {
        return this.bAE - this.bAD + 1;
    }

    public final boolean contains(int n2, int n3) {
        return n2 >= this.bAB && n2 <= this.bAC && n3 >= this.bAD && n3 <= this.bAE;
    }

    public final boolean c(agf_0 agf_02) {
        return this.A(agf_02.bAB, agf_02.bAC, agf_02.bAD, agf_02.bAE);
    }

    public final boolean A(int n2, int n3, int n4, int n5) {
        if (n2 > this.bAC) {
            return false;
        }
        if (n3 < this.bAB) {
            return false;
        }
        if (n4 > this.bAE) {
            return false;
        }
        return n5 >= this.bAD;
    }

    public final void cg(int n2, int n3) {
        this.bAB = Math.min(n2, this.bAB);
        this.bAC = Math.max(n2, this.bAC);
        this.bAD = Math.min(n3, this.bAD);
        this.bAE = Math.max(n3, this.bAE);
    }

    public final void d(agf_0 agf_02) {
        this.bAB = Math.min(agf_02.bAB, this.bAB);
        this.bAC = Math.max(agf_02.bAC, this.bAC);
        this.bAD = Math.min(agf_02.bAD, this.bAD);
        this.bAE = Math.max(agf_02.bAE, this.bAE);
    }

    public String toString() {
        return "(" + this.bAB + ", " + this.bAD + ") - (" + this.bAC + ", " + this.bAE + ")";
    }
}

