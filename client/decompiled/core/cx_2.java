/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;

/*
 * Renamed from cx
 */
public class cx_2
extends anv {
    private final short jd;
    private final short je;

    public cx_2(short s, short s2) {
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
        dataOutputStream.writeByte(9);
        dataOutputStream.writeShort(this.jd);
        dataOutputStream.writeShort(this.je);
    }

    public boolean equals(Object object) {
        return object instanceof cx_2 && ((cx_2)object).jd == this.jd && ((cx_2)object).je == this.je;
    }

    public int hashCode() {
        return this.jd + (this.je << 16);
    }
}

