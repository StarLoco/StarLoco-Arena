/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from avf
 */
public class avf_0
extends ael_2 {
    private static int aDU = 10;
    private long axC;
    private arh_0 aDV;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 8, false)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.axC = byteBuffer.getLong();
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
        return 4500;
    }

    public long DJ() {
        return this.axC;
    }

    public arh_0 FJ() {
        return this.aDV;
    }
}

