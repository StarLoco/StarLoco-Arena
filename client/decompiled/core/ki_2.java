/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ki
 */
class ki_2
implements axq_0 {
    final /* synthetic */ abz_2 DF;

    ki_2(abz_2 abz_22) {
        this.DF = abz_22;
    }

    public void aL(String string) {
        if (abz_2.b(this.DF) == null) {
            return;
        }
        if (string.equals(abz_2.c(this.DF))) {
            this.DF.setClient(null);
            this.DF.setContent(null);
        }
    }
}

