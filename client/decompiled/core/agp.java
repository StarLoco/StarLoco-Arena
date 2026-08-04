/*
 * Decompiled with CFR 0.152.
 */
public class agp
extends np_1 {
    xj_0 cue = null;

    public void a(mv_1 mv_12) {
        this.awp();
        mv_12.b(this.cue);
    }

    public void awp() {
        agf_2 agf_22 = zg_1.a(this.On.SV(), this.On.Tg(), this.On.SW());
        this.cue = new xj_0(this.On.ST(), this.On.M(), this.On.SU(), agf_22, this.On.Tc(), this.On.Td(), this.On.Te(), this.On.Tf(), 0L, new aLc(this.On.Ti()), this.On.SX(), this.On.Tb(), this.On.Th(), this.On.SY(), this.On.SZ(), this.On.Ta(), this.On.Tj(), this.On.Tk());
    }

    public xj_0 awq() {
        if (this.cue == null) {
            this.awp();
        }
        return this.cue;
    }

    public int T() {
        return 0;
    }

    public int getType() {
        return ajr_2.cBl.tI();
    }
}

