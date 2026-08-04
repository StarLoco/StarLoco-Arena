/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;

/*
 * Renamed from aOM
 */
class aom_0
extends za_1 {
    private final short emO;

    public aom_0(short s) {
        this.emO = s;
    }

    public Object a(nw_2 nw_22) {
        return nw_22.aP(this.emO);
    }

    public boolean isWide() {
        return false;
    }

    public void a(DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(8);
        dataOutputStream.writeShort(this.emO);
    }

    public boolean equals(Object object) {
        return object instanceof aom_0 && ((aom_0)object).emO == this.emO;
    }

    public int hashCode() {
        return this.emO;
    }
}

