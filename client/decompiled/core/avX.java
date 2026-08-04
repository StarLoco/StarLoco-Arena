/*
 * Decompiled with CFR 0.152.
 */
class avX
implements apx {
    ahf_1 dhm = new ahf_1();
    boolean dhn;
    final /* synthetic */ axw co;

    private avX(axw axw2) {
        this.co = axw2;
    }

    public boolean a(alp_0 alp_02) {
        if (alp_02.PH() == null || alp_02.PH().xg()) {
            return true;
        }
        this.dhm.add(alp_02.PH());
        this.dhn |= alp_02.getId() > 0L;
        return this.dhm.size() < 2 || !this.dhn;
    }

    /* synthetic */ avX(axw axw2, iz_2 iz_22) {
        this(axw2);
    }
}

