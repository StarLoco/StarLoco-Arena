/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;

/*
 * Renamed from aKF
 */
class akf_0
extends ov_2 {
    private final short maxStack;
    private final short maxLocals;
    private final byte[] dea;
    private final aki_1[] dTK;
    private final ov_2[] dTL;

    akf_0(short s, short s2, short s3, byte[] byArray, aki_1[] aki_1Array, ov_2[] ov_2Array) {
        super(s);
        this.maxStack = s2;
        this.maxLocals = s3;
        this.dea = byArray;
        this.dTK = aki_1Array;
        this.dTL = ov_2Array;
    }

    public static ov_2 a(short s, nw_2 nw_22, DataInputStream dataInputStream) {
        short s2 = dataInputStream.readShort();
        short s3 = dataInputStream.readShort();
        byte[] byArray = nw_2.j(dataInputStream);
        aki_1[] aki_1Array = new aki_1[dataInputStream.readShort()];
        for (int j = 0; j < aki_1Array.length; ++j) {
            aki_1Array[j] = new aki_1(dataInputStream.readShort(), dataInputStream.readShort(), dataInputStream.readShort(), dataInputStream.readShort());
        }
        ov_2[] ov_2Array = new ov_2[dataInputStream.readShort()];
        for (int j = 0; j < ov_2Array.length; ++j) {
            ov_2Array[j] = nw_2.a(nw_22, dataInputStream);
        }
        return new akf_0(s, s2, s3, byArray, aki_1Array, ov_2Array);
    }

    protected void b(DataOutputStream dataOutputStream) {
        int n2;
        dataOutputStream.writeShort(this.maxStack);
        dataOutputStream.writeShort(this.maxLocals);
        dataOutputStream.writeInt(this.dea.length);
        dataOutputStream.write(this.dea);
        dataOutputStream.writeShort(this.dTK.length);
        for (n2 = 0; n2 < this.dTK.length; ++n2) {
            aki_1 aki_12 = this.dTK[n2];
            dataOutputStream.writeShort(aki_12.cel);
            dataOutputStream.writeShort(aki_12.cDg);
            dataOutputStream.writeShort(aki_12.cDh);
            dataOutputStream.writeShort(aki_12.catchType);
        }
        dataOutputStream.writeShort(this.dTL.length);
        for (n2 = 0; n2 < this.dTL.length; ++n2) {
            this.dTL[n2].a(dataOutputStream);
        }
    }
}

