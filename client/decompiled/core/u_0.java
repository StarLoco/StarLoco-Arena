/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from u
 */
public class u_0
extends ue_0 {
    private long aj;
    private qc_0 ak;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.aj = byteBuffer.getLong();
        this.ak = qc_0.hf(byteBuffer.get());
        return true;
    }

    public int getId() {
        return 4522;
    }

    public long K() {
        return this.aj;
    }

    public qc_0 L() {
        return this.ak;
    }

    public int M() {
        return 0;
    }

    public jl_0 N() {
        return jl_0.bjJ;
    }
}

