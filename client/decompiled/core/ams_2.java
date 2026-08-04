/*
 * Decompiled with CFR 0.152.
 */
import java.util.zip.CRC32;

/*
 * Renamed from aMS
 */
public abstract class ams_2
extends afB {
    private int dYN;
    private kp_1 dYO = null;
    private static final CRC32 qM = new CRC32();

    public final int aXl() {
        return this.dYN;
    }

    public static int L(Class clazz) {
        qM.reset();
        qM.update(clazz.getName().getBytes());
        return (int)qM.getValue();
    }

    protected void avf() {
        if (this.dYO == null) {
            this.delete();
        } else {
            this.ag();
            this.dYO.a(this);
        }
    }

    protected abstract void af();

    protected abstract void ag();

    final void a(int n2, kp_1 kp_12) {
        this.dYN = n2;
        this.dYO = kp_12;
    }
}

