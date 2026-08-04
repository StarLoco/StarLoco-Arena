/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from pI
 */
final class pi_1 {
    private afj_0 aco = new afj_0();

    pi_1() {
    }

    qa_2 u(byte by) {
        qa_2 qa_22 = (qa_2)this.aco.bk(by);
        if (qa_22 == null) {
            qa_22 = new qa_2();
            this.aco.b(by, qa_22);
        }
        return qa_22;
    }

    void a(byte by, int n2, int n3) {
        qa_2 qa_22 = this.u(by);
        qa_22.ct(ej_0.o(n2, n3));
    }
}

