/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aCY
 */
public class acy_0
extends avg {
    public static final String bTR = "regexp";
    private boolean dvo = false;
    private static final aum_0 dvp = new aum_0();
    private axk bhL = null;
    private String dvq;
    private boolean dvr = false;

    private void S(UI uI) {
        if (!this.dvo) {
            this.bhL = dvp.K(uI);
            this.dvo = true;
        }
    }

    private void aOC() {
        if (this.dvr) {
            this.bhL.setPattern(this.dvq);
            this.dvr = false;
        }
    }

    public void setPattern(String string) {
        if (this.bhL == null) {
            this.dvq = string;
            this.dvr = true;
        } else {
            this.bhL.setPattern(string);
        }
    }

    public String T(UI uI) {
        this.S(uI);
        if (this.aId()) {
            return this.V(uI).T(uI);
        }
        this.aOC();
        return this.bhL.getPattern();
    }

    public axk U(UI uI) {
        this.S(uI);
        if (this.aId()) {
            return this.V(uI).U(uI);
        }
        this.aOC();
        return this.bhL;
    }

    public acy_0 V(UI uI) {
        return (acy_0)this.O(uI);
    }
}

