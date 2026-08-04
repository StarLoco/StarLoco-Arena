/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class TJ
extends ue_0 {
    private long aj;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.aj = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 8106;
    }

    public int M() {
        return 0;
    }

    public jl_0 N() {
        return jl_0.bjM;
    }

    public long K() {
        return this.aj;
    }

    public String toString() {
        return "Uid : " + this.apt + " Fighter : " + this.aj + " " + super.toString();
    }
}

