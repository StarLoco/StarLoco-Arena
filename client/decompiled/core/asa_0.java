/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;

/*
 * Renamed from asa
 */
public class asa_0
extends ov_2 {
    private final to_2[] cQY;

    public asa_0(short s, to_2[] to_2Array) {
        super(s);
        this.cQY = to_2Array;
    }

    private static ov_2 a(short s, DataInputStream dataInputStream) {
        to_2[] to_2Array = new to_2[dataInputStream.readShort()];
        for (int n2 = 0; n2 < to_2Array.length; n2 = (int)((short)(n2 + 1))) {
            to_2Array[n2] = new to_2(dataInputStream.readShort(), dataInputStream.readShort());
        }
        return new asa_0(s, to_2Array);
    }

    protected void b(DataOutputStream dataOutputStream) {
        dataOutputStream.writeShort(this.cQY.length);
        for (int j = 0; j < this.cQY.length; ++j) {
            dataOutputStream.writeShort(this.cQY[j].bOI);
            dataOutputStream.writeShort(this.cQY[j].lineNumber);
        }
    }

    static ov_2 i(short s, DataInputStream dataInputStream) {
        return asa_0.a(s, dataInputStream);
    }
}

