/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class aAD
extends ue_0 {
    private long BK;
    private int bLA;
    private int bLB;
    private short bLC;
    private boolean cPq;
    private boolean cPr;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 17, false)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.BK = byteBuffer.getLong();
        boolean bl2 = this.cPr = byteBuffer.get() == 1;
        if (!this.cPr) {
            if (!this.a(byArray.length, 28, false)) {
                return false;
            }
            this.cPq = byteBuffer.get() == 1;
            this.bLA = byteBuffer.getInt();
            this.bLB = byteBuffer.getInt();
            this.bLC = byteBuffer.getShort();
        } else {
            this.cPq = false;
        }
        return true;
    }

    public int getId() {
        return 8112;
    }

    public long no() {
        return this.BK;
    }

    public int aEr() {
        return this.bLA;
    }

    public int aEs() {
        return this.bLB;
    }

    public short aEt() {
        return this.bLC;
    }

    public boolean wi() {
        return this.cPq;
    }

    public boolean wj() {
        return this.cPr;
    }

    public int M() {
        return 0;
    }

    public jl_0 N() {
        return jl_0.bjE;
    }
}

