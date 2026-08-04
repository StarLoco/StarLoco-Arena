/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aIZ
 */
public class aiz_0
extends ue_0 {
    private long azt;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.azt = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 4902;
    }

    public jl_0 N() {
        return jl_0.bjQ;
    }

    public long Es() {
        return this.azt;
    }

    public int M() {
        return 0;
    }
}

