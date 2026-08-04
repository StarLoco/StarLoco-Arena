/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;

public final class azf
implements bv_2 {
    private int aW;
    private short HK;
    private byte[] Fe;

    public azf(int n2, short s, byte[] byArray) {
        this.aW = n2;
        this.HK = s;
        this.Fe = byArray;
    }

    public azf() {
    }

    public int getId() {
        return this.aW;
    }

    public short qx() {
        return this.HK;
    }

    public byte[] getData() {
        return this.Fe;
    }

    public void write(DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.aW);
        dataOutputStream.writeShort(this.HK);
        dataOutputStream.writeInt(this.Fe.length);
        dataOutputStream.write(this.Fe);
    }

    public void read(DataInputStream dataInputStream) {
        this.aW = dataInputStream.readInt();
        this.HK = dataInputStream.readShort();
        int n2 = dataInputStream.readInt();
        this.Fe = new byte[n2];
        dataInputStream.read(this.Fe);
    }

    public static int m(DataInputStream dataInputStream) {
        dataInputStream.readInt();
        dataInputStream.readShort();
        int n2 = dataInputStream.readInt();
        dataInputStream.skipBytes(n2);
        return 10 + n2;
    }
}

