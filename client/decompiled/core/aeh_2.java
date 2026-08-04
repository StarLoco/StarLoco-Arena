/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aeH
 */
class aeh_2
implements aoU {
    int cpk;
    final qa_2 cpl = new qa_2();
    final /* synthetic */ mg_1 cpm;

    private aeh_2(mg_1 mg_12) {
        this.cpm = mg_12;
    }

    public boolean a(long l2, zo_0 zo_02) {
        if (zo_02.js(this.cpk)) {
            this.cpl.ct(l2);
        }
        return true;
    }

    void clean() {
        for (int j = this.cpl.size() - 1; j >= 0; --j) {
            mg_1.a(this.cpm).u(this.cpl.hn(j));
        }
        this.cpl.clear();
    }

    /* synthetic */ aeh_2(mg_1 mg_12, abF abF2) {
        this(mg_12);
    }
}

