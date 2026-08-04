/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class arL
implements LM {
    private static final Logger a = Logger.getLogger(arL.class);
    private String rE;
    private String aJ;
    private boolean cQC;
    private boolean rH = false;

    public arL(String string, String string2) {
        this.rE = string;
        this.aJ = string2;
        this.cQC = false;
        this.rH = true;
    }

    public arL(String string, String string2, boolean bl2) {
        this.rE = string;
        this.aJ = string2;
        this.cQC = bl2;
        this.rH = true;
    }

    public arL(k_0 k_02) {
        if (!k_02.getName().equalsIgnoreCase("texture") || k_02.f("path") == null || k_02.f("id") == null) {
            return;
        }
        try {
            if (k_02.f("path") != null) {
                this.aJ = k_02.f("path").getStringValue();
                k_0 k_03 = k_02.f("permanent");
                if (k_03 != null) {
                    this.cQC = k_03.getBooleanValue();
                }
                this.rE = k_02.f("id").getStringValue();
                this.rH = true;
            }
        }
        catch (Exception exception) {
            a.error((Object)"Impossible de cr\u00e9er l'instance de texture", (Throwable)exception);
        }
    }

    public void a(DS dS) {
        if (this.rH) {
            dS.a(this.rE, this.aJ, this.cQC);
        }
    }

    public void a(sf_1 sf_12) {
        if (!this.rH) {
            return;
        }
        String string = sf_12.yg();
        sf_12.a(new aza(null, "loadTexture", string, "\"" + this.rE + "\"", "\"" + this.aJ + "\"", String.valueOf(this.cQC)));
    }

    public boolean isInitialized() {
        return this.rH;
    }
}

