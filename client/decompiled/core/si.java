/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;

class si
extends za_1 {
    private final double value;

    public si(double d) {
        this.value = d;
    }

    public Object a(nw_2 nw_22) {
        return new Double(this.value);
    }

    public boolean isWide() {
        return true;
    }

    public void a(DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(6);
        dataOutputStream.writeDouble(this.value);
    }

    public boolean equals(Object object) {
        return object instanceof si && ((si)object).value == this.value;
    }

    public int hashCode() {
        long l2 = Double.doubleToLongBits(this.value);
        return (int)l2 ^ (int)(l2 >> 32);
    }
}

