/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class CI
extends ov_2 {
    private final zs_0[] aMw;

    public CI(short s, zs_0[] zs_0Array) {
        super(s);
        this.aMw = zs_0Array;
    }

    private static ov_2 a(short s, DataInputStream dataInputStream) {
        short s2 = dataInputStream.readShort();
        zs_0[] zs_0Array = new zs_0[s2];
        for (short s3 = 0; s3 < s2; s3 = (short)(s3 + 1)) {
            zs_0Array[s3] = new zs_0(dataInputStream.readShort(), dataInputStream.readShort(), dataInputStream.readShort(), dataInputStream.readShort(), dataInputStream.readShort());
        }
        return new CI(s, zs_0Array);
    }

    protected void b(DataOutputStream dataOutputStream) {
        dataOutputStream.writeShort(this.aMw.length);
        for (int j = 0; j < this.aMw.length; ++j) {
            zs_0 zs_02 = this.aMw[j];
            dataOutputStream.writeShort(zs_02.cel);
            dataOutputStream.writeShort(zs_02.length);
            dataOutputStream.writeShort(zs_02.aao);
            dataOutputStream.writeShort(zs_02.bBZ);
            dataOutputStream.writeShort(zs_02.index);
        }
    }

    static ov_2 e(short s, DataInputStream dataInputStream) {
        return CI.a(s, dataInputStream);
    }
}

