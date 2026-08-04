/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from jQ
 */
public class jq_1
implements LM {
    private float fh;
    private vP CQ;
    private vP CR;
    private vP fi;
    private String rF;
    private boolean rH = false;

    public jq_1(float f, vP vP2, vP vP3, vP vP4, String string) {
        this.fh = f;
        this.CQ = vP2;
        this.CR = vP3;
        this.fi = vP4;
        this.rF = string;
        this.rH = true;
    }

    public jq_1(k_0 k_02, DS dS) {
        vP vP2;
        if (!k_02.getName().equalsIgnoreCase("tooltip")) {
            return;
        }
        this.fh = aNX.eab;
        k_0 k_03 = k_02.f("borderWidth");
        if (k_03 != null) {
            this.fh = k_03.getFloatValue();
        }
        this.CQ = aNX.dZY;
        k_03 = k_02.f("backgroundColor");
        if (k_03 != null) {
            vP2 = this.CQ;
            this.CQ = dS.dM(k_03.getStringValue());
            if (this.CQ == null) {
                this.CQ = (vP)if_1.UG().c(vP.class, k_03.getStringValue());
            }
            if (this.CQ == null) {
                this.CQ = vP2;
            }
        }
        this.CR = aNX.dZX;
        k_03 = k_02.f("textColor");
        if (k_03 != null) {
            vP2 = this.CR;
            this.CR = dS.dM(k_03.getStringValue());
            if (this.CR == null) {
                this.CR = (vP)if_1.UG().c(vP.class, k_03.getStringValue());
            }
            if (this.CR == null) {
                this.CR = vP2;
            }
        }
        this.fi = aNX.dZZ;
        k_03 = k_02.f("borderColor");
        if (k_03 != null) {
            vP2 = this.fi;
            this.fi = dS.dM(k_03.getStringValue());
            if (this.fi == null) {
                this.fi = (vP)if_1.UG().c(vP.class, k_03.getStringValue());
            }
            if (this.fi == null) {
                this.fi = vP2;
            }
        }
        this.rF = null;
        k_03 = k_02.f("font");
        if (k_03 != null) {
            this.rF = k_03.getStringValue();
        }
        this.rH = true;
    }

    public void a(DS dS) {
        if (this.rH) {
            dS.a(this.fh, this.CQ, this.CR, this.fi, this.rF);
        }
    }

    public String a(DS dS, Ga ga) {
        if (!this.rH) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("InitLoaderManager.getInstance().addLoader(new TooltipInitLoader(").append(this.fh).append("f, ");
        stringBuilder.append("new ").append(vP.class.getSimpleName()).append("(").append(this.CQ.Cp()).append("f, ").append(this.CQ.Cq()).append("f, ").append(this.CQ.Cr()).append("f, ").append(this.CQ.getAlpha()).append("f), ");
        stringBuilder.append("new ").append(vP.class.getSimpleName()).append("(").append(this.CR.Cp()).append("f, ").append(this.CR.Cq()).append("f, ").append(this.CR.Cr()).append("f, ").append(this.CR.getAlpha()).append("f), ");
        stringBuilder.append("new ").append(vP.class.getSimpleName()).append("(").append(this.fi.Cp()).append("f, ").append(this.fi.Cq()).append("f, ").append(this.fi.Cr()).append("f, ").append(this.fi.getAlpha()).append("f), ");
        stringBuilder.append("\"").append(this.rF).append("\"));");
        return stringBuilder.toString();
    }

    public void a(sf_1 sf_12) {
        if (!this.rH) {
            return;
        }
        String string = sf_12.yg();
        sf_12.a(new aza(null, "loadTooltip", string, this.fh + "f", "new " + vP.class.getSimpleName() + "(" + this.CQ.Cp() + "f, " + this.CQ.Cq() + "f, " + this.CQ.Cr() + "f, " + this.CQ.getAlpha() + "f)", "new " + vP.class.getSimpleName() + "(" + this.CR.Cp() + "f, " + this.CR.Cq() + "f, " + this.CR.Cr() + "f, " + this.CR.getAlpha() + "f)", "new " + vP.class.getSimpleName() + "(" + this.fi.Cp() + "f, " + this.fi.Cq() + "f, " + this.fi.Cr() + "f, " + this.fi.getAlpha() + "f)", "\"" + this.rF + "\""));
    }

    public boolean isInitialized() {
        return this.rH;
    }
}

