/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from xK
 */
public class xk_0
extends ue_0 {
    private long azt;
    private long azu;
    private qc_0 ak;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.azt = byteBuffer.getLong();
        this.azu = byteBuffer.getLong();
        this.ak = qc_0.hf(byteBuffer.getInt());
        return true;
    }

    public int getId() {
        return 4900;
    }

    public jl_0 N() {
        return jl_0.bjP;
    }

    public long Es() {
        return this.azt;
    }

    public ry Et() {
        ry ry2 = new ry(wi_2.dd(this.azu), wi_2.de(this.azu), wi_2.df(this.azu));
        return ry2;
    }

    public qc_0 Eu() {
        return this.ak;
    }

    public int M() {
        return 0;
    }
}

