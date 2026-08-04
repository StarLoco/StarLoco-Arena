/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from cD
 */
public class cd_2
extends ue_0 {
    private long aj;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 16, true)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.aj = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 4520;
    }

    public long K() {
        return this.aj;
    }

    public int M() {
        return 0;
    }

    public jl_0 N() {
        return jl_0.bjH;
    }
}

