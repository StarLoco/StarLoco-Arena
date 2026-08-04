/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;

/*
 * Renamed from im
 */
public class im_1
extends ov_2 {
    private final short[] ye;

    public im_1(short s, short[] sArray) {
        super(s);
        this.ye = sArray;
    }

    public short[] lw() {
        short[] sArray = new short[this.ye.length];
        System.arraycopy(this.ye, 0, sArray, 0, sArray.length);
        return sArray;
    }

    private static ov_2 a(short s, DataInputStream dataInputStream) {
        return new im_1(s, nw_2.i(dataInputStream));
    }

    protected void b(DataOutputStream dataOutputStream) {
        nw_2.b(dataOutputStream, this.ye);
    }

    static ov_2 b(short s, DataInputStream dataInputStream) {
        return im_1.a(s, dataInputStream);
    }
}

