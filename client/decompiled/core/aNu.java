/*
 * Decompiled with CFR 0.152.
 */
import java.awt.font.GlyphVector;

class aNu
implements cn_1 {
    private static final acl_0 uG = new ym_0(new nd_0());
    private boolean dZn;
    private GlyphVector dZo;

    private aNu() {
    }

    public static aNu a(GlyphVector glyphVector) {
        aNu aNu2;
        try {
            aNu2 = (aNu)uG.adr();
        }
        catch (Exception exception) {
            dt_2.dT().error((Object)"Probl\u00e8me au borrowObject.");
            aNu2 = new aNu();
            aNu2.b();
        }
        aNu2.dZo = glyphVector;
        return aNu2;
    }

    public GlyphVector aXw() {
        return this.dZo;
    }

    public void bi(boolean bl2) {
        this.dZn = bl2;
    }

    public boolean MJ() {
        return this.dZn;
    }

    public void b() {
        this.dZn = true;
    }

    public void j() {
        this.dZo = null;
    }

    public void release() {
        try {
            uG.af(this);
        }
        catch (Exception exception) {
            this.j();
        }
    }

    /* synthetic */ aNu(akf_2 akf_22) {
        this();
    }
}

