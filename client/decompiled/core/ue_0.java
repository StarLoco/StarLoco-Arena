/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from ue
 */
public abstract class ue_0
extends ael_2 {
    protected static final int aps = 8;
    protected int apt;
    private int apu = -1;

    public abstract int M();

    public abstract jl_0 N();

    protected void o(ByteBuffer byteBuffer) {
        this.apt = byteBuffer.getInt();
        this.apu = byteBuffer.getInt();
    }

    public int Ao() {
        return this.apt;
    }

    public int Ap() {
        return this.apu;
    }
}

