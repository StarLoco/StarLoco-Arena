/*
 * Decompiled with CFR 0.152.
 */
class IU {
    private static int ys = 0;

    private IU() {
    }

    static /* synthetic */ int iw() {
        return ys++;
    }

    static /* synthetic */ int aT(int n2) {
        ys = n2;
        return ys;
    }
}

