/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from axn
 */
public class axn_0
extends ue_0 {
    private long cGS;
    private int ir;
    private int Lj;
    private int Lk;
    private short Ll;
    private boolean cPq;
    private boolean cPr;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 21, false)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.cGS = byteBuffer.getLong();
        this.ir = byteBuffer.getInt();
        boolean bl2 = this.cPr = byteBuffer.get() == 1;
        if (!this.cPr) {
            if (!this.a(byArray.length, 32, false)) {
                return false;
            }
            this.cPq = byteBuffer.get() == 1;
            this.Lj = byteBuffer.getInt();
            this.Lk = byteBuffer.getInt();
            this.Ll = byteBuffer.getShort();
        } else {
            this.cPq = false;
        }
        return true;
    }

    public int getId() {
        return 8110;
    }

    public long aJX() {
        return this.cGS;
    }

    public int el() {
        return this.ir;
    }

    public int aJY() {
        return this.Lj;
    }

    public int aJZ() {
        return this.Lk;
    }

    public short aKa() {
        return this.Ll;
    }

    public boolean wi() {
        return this.cPq;
    }

    public boolean wj() {
        return this.cPr;
    }

    public int M() {
        return this.ir;
    }

    public jl_0 N() {
        return jl_0.bjC;
    }
}

