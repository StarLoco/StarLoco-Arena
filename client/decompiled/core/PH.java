/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public class PH
extends avg
implements Cloneable {
    private Vector bEi = new Vector();

    public void a(avt_0 avt_02) {
        this.bEi.addElement(avt_02);
    }

    public Vector acn() {
        return this.bEi;
    }

    public void a(nw_1 nw_12) {
        this.bEi.addElement(nw_12);
    }

    public void a(ij_0 ij_02) {
        this.bEi.addElement(ij_02);
    }

    public void a(xz_1 xz_12) {
        this.bEi.addElement(xz_12);
    }

    public void a(km_0 km_02) {
        this.bEi.addElement(km_02);
    }

    public void a(xb xb2) {
        this.bEi.addElement(xb2);
    }

    public void a(aFF aFF2) {
        this.bEi.addElement(aFF2);
    }

    public void a(acq_1 acq_12) {
        this.bEi.addElement(acq_12);
    }

    public void a(afb_0 afb_02) {
        this.bEi.addElement(afb_02);
    }

    public void a(wo_2 wo_22) {
        this.bEi.addElement(wo_22);
    }

    public void a(vd_0 vd_02) {
        this.bEi.addElement(vd_02);
    }

    public void a(cd_0 cd_02) {
        this.bEi.addElement(cd_02);
    }

    public void a(Xg xg) {
        this.bEi.addElement(xg);
    }

    public void a(ef_0 ef_02) {
        this.bEi.addElement(ef_02);
    }

    public void a(tD tD2) {
        this.bEi.addElement(tD2);
    }

    public void a(gi_1 gi_12) {
        this.bEi.addElement(gi_12);
    }

    public void a(ie_0 ie_02) {
        this.bEi.addElement(ie_02);
    }

    public void a(arM arM2) {
        this.bEi.addElement(arM2);
    }

    public void a(acr_1 acr_12) {
        this.bEi.addElement(acr_12);
    }

    public void a(aax_1 aax_12) {
        this.bEi.addElement(aax_12);
    }

    public void a(agh_0 agh_02) {
        this.bEi.addElement(agh_02);
    }

    public void a(awq_0 awq_02) {
        if (!this.bEi.isEmpty()) {
            throw this.aIh();
        }
        Object object = awq_02.P(this.TP());
        if (!(object instanceof PH)) {
            String string = awq_02.aJC() + " doesn't refer to a FilterChain";
            throw new eq_2(string);
        }
        PH pH = (PH)object;
        this.bEi = pH.acn();
        super.a(awq_02);
    }

    public void a(gx_2 gx_22) {
        this.bEi.addElement(gx_22);
    }
}

