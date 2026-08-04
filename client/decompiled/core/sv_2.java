/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from sv
 */
class sv_2
implements apx {
    final /* synthetic */ add_1 ajf;

    sv_2(add_1 add_12) {
        this.ajf = add_12;
    }

    public boolean execute(String string) {
        if (add_1.a(this.ajf) != null) {
            add_1.a(this.ajf).gz(string);
        }
        for (axq_0 axq_02 : add_1.b(this.ajf)) {
            axq_02.aL(string);
        }
        if (add_1.c(this.ajf).size() > 0) {
            add_1.b(this.ajf).removeAll(add_1.c(this.ajf));
            add_1.c(this.ajf).clear();
        }
        return true;
    }
}

