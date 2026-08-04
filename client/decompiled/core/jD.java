/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class jD
extends ue_0 {
    private long Bd;
    private long Be;
    private long Bf;
    private boolean Bg;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.Bg = byteBuffer.get() == 1;
        this.Bd = byteBuffer.getLong();
        this.Be = byteBuffer.getLong();
        this.Bf = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 6200;
    }

    public int M() {
        return 0;
    }

    public jl_0 N() {
        return jl_0.bjL;
    }

    public boolean mQ() {
        return this.Bg;
    }

    public long mR() {
        return this.Bd;
    }

    public long mS() {
        return this.Bf;
    }

    public long mT() {
        return this.Be;
    }
}

