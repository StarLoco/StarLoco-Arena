/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;

class aok
extends za_1 {
    private final int value;

    public aok(int n2) {
        this.value = n2;
    }

    public Object a(nw_2 nw_22) {
        return new Integer(this.value);
    }

    public boolean isWide() {
        return false;
    }

    public void a(DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(3);
        dataOutputStream.writeInt(this.value);
    }

    public boolean equals(Object object) {
        return object instanceof aok && ((aok)object).value == this.value;
    }

    public int hashCode() {
        return this.value;
    }
}

