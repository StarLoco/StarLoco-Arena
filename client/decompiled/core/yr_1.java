/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from yR
 */
public class yr_1
extends ue_0 {
    private static int aDU = 10;
    private long aj;
    private arh_0 aDV;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 16, false)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.aj = byteBuffer.getLong();
        int n2 = (byArray.length - 8) / aDU;
        int n3 = 0;
        this.aDV = new arh_0(n2);
        while (byteBuffer.remaining() != 0) {
            int n4 = byteBuffer.getInt();
            int n5 = byteBuffer.getInt();
            short s = byteBuffer.getShort();
            this.aDV.b(n3++, n4, n5, s);
        }
        return true;
    }

    public int getId() {
        return 4524;
    }

    public long K() {
        return this.aj;
    }

    public int M() {
        return 0;
    }

    public jl_0 N() {
        return jl_0.bjK;
    }

    public arh_0 FJ() {
        return this.aDV;
    }
}

