/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class wA {
    private static final wA ava = new wA();
    private final ArrayList avb = new ArrayList();

    public static wA CT() {
        return ava;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean a(Im im) {
        ArrayList arrayList = this.avb;
        synchronized (arrayList) {
            return this.avb.add(im);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(Im im) {
        ArrayList arrayList = this.avb;
        synchronized (arrayList) {
            return this.avb.remove(im);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean CU() {
        ArrayList arrayList = this.avb;
        synchronized (arrayList) {
            return !this.avb.isEmpty();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int CV() {
        ArrayList arrayList = this.avb;
        synchronized (arrayList) {
            return this.avb.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Im[] CW() {
        ArrayList arrayList = this.avb;
        synchronized (arrayList) {
            return this.avb.toArray(new Im[this.avb.size()]);
        }
    }
}

