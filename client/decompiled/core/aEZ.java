/*
 * Decompiled with CFR 0.152.
 */
public abstract class aEZ {
    public abstract int[] b(int ... var1);

    public abstract int[] c(int ... var1);

    static aEZ a(int n2, int n3, qc_0 qc_02, boolean bl2) {
        if (bl2 || qc_02 == qc_0.bET || qc_02 == qc_0.bEK) {
            return new xa_0(n2, n3);
        }
        return new fb(n2, n3, qc_02);
    }
}

