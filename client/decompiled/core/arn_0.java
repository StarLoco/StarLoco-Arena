/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from arn
 */
public class arn_0
extends ue_0 {
    private long BK;
    private ve_0 cPp;
    private int bLA;
    private int bLB;
    private short bLC;
    private boolean cPq;
    private boolean cPr;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 21, false)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.BK = byteBuffer.getLong();
        this.cPp = (ve_0)aca_0.aOq().E(byteBuffer.getInt());
        boolean bl2 = this.cPr = byteBuffer.get() == 1;
        if (!this.cPr) {
            if (!this.a(byArray.length, 32, true)) {
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
        return 8108;
    }

    public long no() {
        return this.BK;
    }

    public ve_0 aEq() {
        return this.cPp;
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
        if (this.cPp != null) {
            return this.cPp.getId();
        }
        return 0;
    }

    public jl_0 N() {
        return jl_0.bjD;
    }
}

