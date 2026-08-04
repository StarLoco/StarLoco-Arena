/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class Bb
implements zt_2 {
    private static final Logger a = Logger.getLogger(Bb.class);
    protected final zt_2 aIu;
    private final jp_1 aIv;
    private final jp_1 aIw;

    public Bb(zt_2 zt_22, jp_1 jp_12, jp_1 jp_13) {
        this.aIu = zt_22;
        this.aIv = jp_12;
        this.aIw = jp_13;
    }

    public jp_1 c(gj_2 gj_22, int n2) {
        return n2 < gj_22.Pq() ? this.aIv : this.aIw;
    }

    public void d(gj_2 gj_22) {
    }

    public void e(gj_2 gj_22) {
        gj_22.a(this.aIu);
    }

    public static zt_2 a(boolean bl2, abm_2 abm_22, jp_1 jp_12, jp_1 jp_13) {
        if (jp_12 == null && jp_13 == null) {
            return amh_2.aBP();
        }
        if (jp_12 == null) {
            a.warn((Object)"style inconnu pour la marche");
            jp_12 = jp_13;
        }
        if (jp_13 == null) {
            a.warn((Object)"style inconnu pour la course");
            jp_13 = jp_12;
        }
        if (bl2) {
            return new ajt_2(abm_22.Pu(), jp_12, jp_13);
        }
        return new Bb(abm_22.Pu(), jp_12, jp_13);
    }
}

