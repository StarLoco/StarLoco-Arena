/*
 * Decompiled with CFR 0.152.
 */
public class fF
implements LM {
    private String rE;
    private String aJ;
    private String rF;
    private boolean rG;
    private boolean rH = false;

    public fF(String string, String string2, String string3) {
        this.rE = string;
        this.aJ = string2;
        this.rF = string3;
        this.rG = false;
        this.rH = true;
    }

    public fF(String string, String string2, String string3, boolean bl2) {
        this.rE = string;
        this.aJ = string2;
        this.rF = string3;
        this.rG = bl2;
        this.rH = true;
    }

    public fF(k_0 k_02) {
        if (!k_02.getName().equalsIgnoreCase("font") || k_02.f("font") == null || k_02.f("id") == null) {
            return;
        }
        this.rG = false;
        if (k_02.f("bordered") != null) {
            this.rG = k_02.f("bordered").getBooleanValue();
        }
        this.aJ = k_02.f("path").getStringValue();
        this.rF = k_02.f("font").getStringValue();
        this.rE = k_02.f("id").getStringValue();
        this.rH = true;
    }

    public void a(DS dS) {
        if (this.rH) {
            dS.a(this.rE, this.aJ, this.rF, this.rG);
        }
    }

    public void a(sf_1 sf_12) {
        if (!this.rH) {
            return;
        }
        String string = sf_12.yg();
        sf_12.a(new aza(null, "loadFont", string, "\"" + this.rE + "\"", "\"" + this.aJ + "\"", "\"" + this.rF + "\"", String.valueOf(this.rG)));
    }

    public boolean isInitialized() {
        return true;
    }
}

