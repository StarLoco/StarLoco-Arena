/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from xY
 */
public class xy_2
extends ue_0 {
    private long aAb;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.aAb = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 4901;
    }

    public jl_0 N() {
        return jl_0.bjP;
    }

    public long EF() {
        return this.aAb;
    }

    public int M() {
        return 0;
    }
}

