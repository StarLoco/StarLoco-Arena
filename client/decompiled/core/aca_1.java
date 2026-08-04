/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aCa
 */
public class aca_1 {
    private final JX nr;
    private final String mO;
    private jJ[] ns;
    private boolean dtR;
    final /* synthetic */ ef_2 dtS;

    public aca_1(ef_2 ef_22, JX jX, String string, jJ[] jJArray, boolean bl2) {
        this.dtS = ef_22;
        this.nr = jX;
        this.mO = string;
        this.ns = jJArray;
        this.dtR = bl2;
    }

    public boolean aOl() {
        return this.dtR;
    }

    public int eA() {
        return this.nr.getId();
    }

    public void eH(boolean bl2) {
        this.dtR = bl2;
    }

    public boolean a(afs_1 afs_12) {
        this.nr.a(this.mO, this.ns, afs_12.aih());
        return false;
    }
}

