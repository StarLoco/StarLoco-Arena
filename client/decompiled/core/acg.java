/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class acg
extends ue_0 {
    private long cjr;
    private long cjs;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 24, true)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.cjr = byteBuffer.getLong();
        this.cjs = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 4506;
    }

    public long aqF() {
        return this.cjr;
    }

    public long aqG() {
        return this.cjs;
    }

    public int M() {
        return 0;
    }

    public jl_0 N() {
        return jl_0.bjG;
    }
}

