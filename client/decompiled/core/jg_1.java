/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Jg
 */
public class jg_1
extends ue_0 {
    private byte bjt;
    private tO Eb;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.bjt = byteBuffer.get();
        int n2 = byteBuffer.getInt();
        this.Eb = n2 != 0 ? (tO)cw_1.eO().w(n2) : null;
        return true;
    }

    public int getId() {
        return 8100;
    }

    public byte Vw() {
        return this.bjt;
    }

    public tO oQ() {
        return this.Eb;
    }

    public int M() {
        return 0;
    }

    public jl_0 N() {
        return jl_0.bjO;
    }

    public String toString() {
        return "Uid : " + this.apt + " tour : " + this.bjt + " " + super.toString();
    }
}

