/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Kw
 */
public class kw_2
extends ue_0 {
    private long aj;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.aj = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 8104;
    }

    public long K() {
        return this.aj;
    }

    public int M() {
        return 0;
    }

    public jl_0 N() {
        return jl_0.bjN;
    }

    public String toString() {
        return "Uid : " + this.apt + " fighter : " + this.aj + " " + super.toString();
    }
}

