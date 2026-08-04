/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from arl
 */
public class arl_0
extends ael_2 {
    private byte[] cPl;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.cPl = new byte[byteBuffer.getShort()];
        byteBuffer.get(this.cPl);
        return true;
    }

    public byte[] aEj() {
        return this.cPl;
    }

    public int getId() {
        return 510;
    }
}

