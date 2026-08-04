/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;

/*
 * Renamed from ahZ
 */
class ahz_2
extends za_1 {
    private final long value;

    public ahz_2(long l2) {
        this.value = l2;
    }

    public Object a(nw_2 nw_22) {
        return new Long(this.value);
    }

    public boolean isWide() {
        return true;
    }

    public void a(DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(5);
        dataOutputStream.writeLong(this.value);
    }

    public boolean equals(Object object) {
        return object instanceof ahz_2 && ((ahz_2)object).value == this.value;
    }

    public int hashCode() {
        return (int)this.value ^ (int)(this.value >> 32);
    }
}

