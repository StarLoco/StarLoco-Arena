/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from IE
 */
public class ie_0
extends js_2 {
    private String afO;
    private String afP;
    private acy_0 bhH;
    private wc_0 bhI;
    private boolean initialized = false;
    private String bhJ = "";
    private int bhK;
    private axk bhL;

    public void setPattern(String string) {
        this.afO = string;
    }

    public void eI(String string) {
        this.afP = string;
    }

    public void eJ(String string) {
        this.bhJ = string;
    }

    private void initialize() {
        if (this.initialized) {
            return;
        }
        this.bhK = tD.ct(this.bhJ);
        if (this.afO == null) {
            throw new eq_2("Missing from in containsregex");
        }
        this.bhH = new acy_0();
        this.bhH.setPattern(this.afO);
        this.bhL = this.bhH.U(this.TP());
        if (this.afP == null) {
            return;
        }
        this.bhI = new wc_0();
        this.bhI.setExpression(this.afP);
    }

    public String dV(String string) {
        this.initialize();
        if (!this.bhL.A(string, this.bhK)) {
            return null;
        }
        if (this.bhI == null) {
            return string;
        }
        return this.bhL.b(string, this.bhI.B(this.TP()), this.bhK);
    }
}

