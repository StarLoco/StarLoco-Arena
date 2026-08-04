/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;

/*
 * Renamed from VN
 */
public class vn_0
extends ov_2 {
    private final short bTg;

    public vn_0(short s, short s2) {
        super(s);
        this.bTg = s2;
    }

    private static ov_2 a(short s, DataInputStream dataInputStream) {
        return new vn_0(s, dataInputStream.readShort());
    }

    protected void b(DataOutputStream dataOutputStream) {
        dataOutputStream.writeShort(this.bTg);
    }

    static ov_2 f(short s, DataInputStream dataInputStream) {
        return vn_0.a(s, dataInputStream);
    }
}

