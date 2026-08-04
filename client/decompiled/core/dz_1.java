/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;

/*
 * Renamed from dz
 */
public class dz_1
extends anv {
    private final short jd;
    private final short je;

    public dz_1(short s, short s2) {
        this.jd = s;
        this.je = s2;
    }

    public short eP() {
        return this.je;
    }

    public boolean isWide() {
        return false;
    }

    public void a(DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(11);
        dataOutputStream.writeShort(this.jd);
        dataOutputStream.writeShort(this.je);
    }

    public boolean equals(Object object) {
        return object instanceof dz_1 && ((dz_1)object).jd == this.jd && ((dz_1)object).je == this.je;
    }

    public int hashCode() {
        return this.jd + (this.je << 16);
    }
}

