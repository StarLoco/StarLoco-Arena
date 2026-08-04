/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from hf
 */
public class hf_0
extends ke {
    private qa_1 vc = null;
    private Object dE = null;
    private boolean vd;

    public hf_0(na_1 na_12, qa_1 qa_12, Object object, boolean bl2) {
        this.DK = na_12;
        this.vc = qa_12;
        this.dE = object;
        this.vd = bl2;
    }

    public hf_0(na_1 na_12) {
        this.DK = na_12;
    }

    public void b(qa_1 qa_12) {
        this.vc = qa_12;
    }

    public qa_1 kC() {
        return this.vc;
    }

    public void setSelected(boolean bl2) {
        this.vd = bl2;
    }

    public boolean getSelected() {
        return this.vd;
    }

    public void setValue(Object object) {
        this.dE = object;
    }

    public Object getValue() {
        return this.dE;
    }

    public qe_1 aV() {
        return qe_1.bFp;
    }
}

