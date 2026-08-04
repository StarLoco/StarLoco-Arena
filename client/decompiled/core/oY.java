/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class oY
extends ov_2 {
    private final short aaX;

    oY(short s, short s2) {
        super(s);
        this.aaX = s2;
    }

    public short tS() {
        return this.aaX;
    }

    private static ov_2 a(short s, DataInputStream dataInputStream) {
        return new oY(s, dataInputStream.readShort());
    }

    protected void b(DataOutputStream dataOutputStream) {
        dataOutputStream.writeShort(this.aaX);
    }

    static ov_2 c(short s, DataInputStream dataInputStream) {
        return oY.a(s, dataInputStream);
    }
}

