/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.Anm2.Anm;

/*
 * Renamed from amg
 */
public abstract class amg_1
extends abm_2 {
    private boolean cGv = true;
    private final zm_1 cGw = new zm_1();

    public amg_1(long l2) {
        super(l2);
        this.a(adg_0.aPh().kS("outline"));
        this.eS(false);
    }

    public void iG(String string) {
        if (string == null) {
            return;
        }
        try {
            int n2 = Integer.valueOf(string);
            String string2 = n2 < 1000 || n2 == 7000 || n2 == 7001 ? mu_1.rM().getString("playerGfxPath") : mu_1.rM().getString("npcGfxPath");
            string2 = String.format(string2, string);
            this.aP(1.0f);
            this.b(string2, true);
            this.lq(string);
        }
        catch (Exception exception) {
            a.error((Object)"Erreur dans  la cr\u00e9ation DescriptorLibrary : ", (Throwable)exception);
        }
    }

    public void a(aji_0 aji_02) {
        super.a(aji_02);
    }

    protected final void a(short s, String string, String ... stringArray) {
        Anm anm = amg_1.lr(string);
        this.a(anm, stringArray);
        this.cGw.b(s, anm);
    }

    protected abstract aeo_2 af(short var1);

    public final void aBx() {
        dk_1 dk_12 = this.cGw.Gi();
        while (dk_12.hasNext()) {
            dk_12.fK();
            aeo_2 aeo_22 = this.af(dk_12.fL());
            this.a(aeo_22, (Anm)dk_12.value());
        }
        this.cGw.clear();
    }

    protected final void bN(short s) {
        aeo_2 aeo_22 = this.af(s);
        if (aeo_22 == null) {
            return;
        }
        Anm anm = (Anm)this.cGw.ao(s);
        if (anm != null) {
            this.a(aeo_22, anm);
        }
    }

    private void a(aeo_2 aeo_22, Anm anm) {
        String[] stringArray = aeo_22.ES();
        this.c(anm, stringArray);
    }

    protected final void iH(String string) {
        if (this.cAF == null) {
            return;
        }
        this.aTq();
        this.cAF.ke();
        if (string == null) {
            return;
        }
        Anm anm = amg_1.lr(string);
        this.cAF.a(anm);
    }
}

