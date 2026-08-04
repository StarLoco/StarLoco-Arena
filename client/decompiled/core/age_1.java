/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aGE
 */
class age_1 {
    private final String dJm;
    private final String ceA;
    private final int dJn;
    final /* synthetic */ anr_0 dJo;

    public age_1(anr_0 anr_02, String string, String string2, int n2) {
        this.dJo = anr_02;
        this.ceA = string2;
        this.dJn = n2;
        this.dJm = string;
    }

    public age_1(anr_0 anr_02, String string, String string2) {
        this.dJo = anr_02;
        this.ceA = string2;
        this.dJn = 0;
        this.dJm = string;
    }

    public String getFunctionName() {
        return this.ceA;
    }

    public int getParamCount() {
        return this.dJn;
    }

    public String aSP() {
        return this.dJm;
    }
}

