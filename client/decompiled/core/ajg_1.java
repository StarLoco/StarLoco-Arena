/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;

/*
 * Renamed from ajG
 */
class ajg_1
extends za_1 {
    private final float value;

    public ajg_1(float f) {
        this.value = f;
    }

    public Object a(nw_2 nw_22) {
        return new Float(this.value);
    }

    public boolean isWide() {
        return false;
    }

    public void a(DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(4);
        dataOutputStream.writeFloat(this.value);
    }

    public boolean equals(Object object) {
        return object instanceof ajg_1 && ((ajg_1)object).value == this.value;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.value);
    }
}

