/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aEf
 */
public class aef_1
extends ael_2 {
    private iz_0 dzD;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.getInt();
        this.dzD = ((iz_0)tb_0.zl().dD(n2)).h(byteBuffer);
        return true;
    }

    public int getId() {
        return 17005;
    }

    public iz_0 aPT() {
        return this.dzD;
    }
}

