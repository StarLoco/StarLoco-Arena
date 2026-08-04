/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.isometric.particles.CellParticleSystem;
import com.ankamagames.baseImpl.graphics.isometric.particles.IsoParticleSystem;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import org.apache.log4j.Logger;

public class gC {
    private static final boolean DEBUG = false;
    protected wX ud;
    protected String aJ;
    protected arc_0 ue;
    protected final cp_2 uf = new cp_2();
    private int ug = Integer.MIN_VALUE;
    private static final Logger a = Logger.getLogger(gC.class);
    private aL uh;
    private qd_1 ui;
    private static final gC uj = new gC();

    private gC() {
    }

    public final void setPath(String string) {
        this.aJ = string;
    }

    public final void a(arc_0 arc_02) {
        this.ue = arc_02;
    }

    public final ru_2 a(short s, short s2, int n2) {
        long l2 = gC.a(s, s2, n2);
        return (ru_2)this.uf.t(l2);
    }

    public final ru_2 f(int n2, int n3, int n4) {
        long l2 = hy_2.aO(n2);
        long l3 = hy_2.aP(n3);
        return (ru_2)this.uf.t(gC.a(l2, l3, n4));
    }

    protected static long a(long l2, long l3, int n2) {
        assert (l2 > -32768L && l2 < 32767L) : "faut pas d\u00e9conner non plus un short \u00e7a suffit pour la taille du monde";
        assert (l3 > -32768L && l3 < 32767L) : "faut pas d\u00e9conner non plus un short \u00e7a suffit pour la taille du monde";
        return (l2 += 32767L) << 48 | (l3 += 32767L) << 32 | (long)n2;
    }

    private static String a(String string, int n2, short s, short s2) {
        assert (string != null && string.contains("%d") && string.endsWith("/"));
        return String.format(string, n2) + s + '_' + s2;
    }

    public final void b(short s, short s2, int n2) {
        ru_2 ru_22;
        assert (this.ue != null) : "Il faut d'abord appeler setMapFactory";
        long l2 = gC.a(s, s2, n2);
        if (this.uf.m(l2)) {
            this.Q(l2);
            return;
        }
        String string = gC.a(this.aJ, n2, s, s2);
        acf acf2 = acf.T(vq_2.readFile(string));
        byte by = acf2.readByte();
        if (by != (ru_22 = this.ue.aEE()).cc()) {
            a.warn((Object)"version de map d'enviornenemt client incorrect");
        }
        this.a(ru_22, acf2);
        this.uf.a(l2, ru_22);
    }

    public void b(aL aL2) {
        this.uh = aL2;
    }

    public void a(qd_1 qd_12) {
        this.ui = qd_12;
    }

    public void aH(int n2) {
        this.ug = n2;
    }

    private void Q(long l2) {
        ru_2 ru_22 = (ru_2)this.uf.t(l2);
        if (ru_22 != null && !ru_22.aiC) {
            this.a(ru_22);
        }
    }

    protected void a(ru_2 ru_22, acf acf2) {
        if (ru_22 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/EnvironmentMapManager.loadMap must not be null");
        }
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/EnvironmentMapManager.loadMap must not be null");
        }
        ru_22.b(acf2);
        this.a(ru_22);
    }

    protected void a(ru_2 ru_22) {
        axs_0[] axs_0Array;
        Object object;
        if (ru_22 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/EnvironmentMapManager.loadDynamicDataFrom must not be null");
        }
        if (ru_22.aiC) {
            a.error((Object)("chargement d'une map d\u00e9j\u00e0 charg\u00e9e  " + this.c(ru_22)));
            return;
        }
        aDv[] aDvArray = ru_22.xP();
        if (aDvArray != null && this.ui != null) {
            for (int j = 0; j < aDvArray.length; ++j) {
                aDv aDv2 = aDvArray[j];
                object = aiJ.ayv().bx(aDv2.dxq, aDv2.ata);
                if (object == null) {
                    a.warn((Object)("Erreur de cr\u00e9ation du systeme de particule " + aDv2));
                    continue;
                }
                float f = (float)ru_22.lI(aDv2.cFs) + (float)aDv2.dxr / 100.0f;
                float f2 = (float)ru_22.lJ(aDv2.cFt) + (float)aDv2.dxs / 100.0f;
                float f3 = (float)aDv2.wp + (float)aDv2.dxt / 10.0f;
                ((ParticleSystem)object).setPosition(f, f2, f3);
                ((IsoParticleSystem)object).ao(aDv2.bPN);
                assert (aDv2.dxp == null);
                aDv2.dxp = object;
                this.ui.a((CellParticleSystem)object);
            }
        }
        if ((axs_0Array = ru_22.xQ()) != null && this.uh != null) {
            for (int j = 0; j < axs_0Array.length; ++j) {
                object = axs_0Array[j];
                ws_0 ws_02 = arp.aEu().lS(((axs_0)object).cgU);
                int n2 = ru_22.lI(((axs_0)object).cFs);
                int n3 = ru_22.lJ(((axs_0)object).cFt);
                assert (((axs_0)object).dju == null);
                ((axs_0)object).dju = this.uh.a(ws_02, n2, n3, ((axs_0)object).wp);
            }
        }
        ru_22.aiC = true;
        if (this.ud != null) {
            this.ud.d(ru_22);
        }
    }

    public void b(short s, short s2) {
        assert (this.ug != Integer.MIN_VALUE) : "il faut d'abord appler setWorldId";
        this.b(s, s2, this.ug);
    }

    public void c(short s, short s2) {
        long l2 = gC.a(s, s2, this.ug);
        this.d(s, s2);
        this.uf.u(l2);
    }

    protected void b(ru_2 ru_22) {
        axs_0[] axs_0Array;
        if (ru_22 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/EnvironmentMapManager.unloadDynamicDataFrom must not be null");
        }
        if (!ru_22.aiC) {
            return;
        }
        aDv[] aDvArray = ru_22.xP();
        if (aDvArray != null && this.ui != null) {
            for (int j = 0; j < aDvArray.length; ++j) {
                CellParticleSystem cellParticleSystem = aDvArray[j].dxp;
                if (cellParticleSystem == null) continue;
                cellParticleSystem.kill();
                aDvArray[j].dxp = null;
            }
        }
        if ((axs_0Array = ru_22.xQ()) != null && this.uh != null) {
            for (int j = 0; j < axs_0Array.length; ++j) {
                or_1 or_12 = axs_0Array[j].dju;
                if (or_12 == null) continue;
                or_12.release();
                axs_0Array[j].dju = null;
            }
        }
        ru_22.aiC = false;
        if (this.ud != null) {
            this.ud.e(ru_22);
        }
    }

    private void d(short s, short s2) {
        ru_2 ru_22 = this.e(s, s2);
        if (ru_22 == null) {
            a.warn((Object)("D\u00e9chargement d'une map non charg\u00e9e (" + s + " " + s2 + ")"));
            return;
        }
        this.b(ru_22);
    }

    public final void kf() {
        akz_0 akz_02 = this.uf.eI();
        while (akz_02.hasNext()) {
            akz_02.fK();
            this.b((ru_2)akz_02.value());
        }
    }

    public final ru_2 e(short s, short s2) {
        assert (this.ug != Integer.MIN_VALUE) : "il faut d'abord appeler setWorldId";
        return this.a(s, s2, this.ug);
    }

    public final ru_2 p(int n2, int n3) {
        assert (this.ug != Integer.MIN_VALUE) : "il faut d'abord appeler setWorldId";
        return this.f(n2, n3, this.ug);
    }

    public void reset() {
        this.uf.clear();
        this.ug = Integer.MIN_VALUE;
    }

    public static gC kg() {
        return uj;
    }

    private String c(ru_2 ru_22) {
        if (ru_22 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/EnvironmentMapManager.mapCoordString must not be null");
        }
        return "(" + ru_22.pi() + " " + ru_22.pj() + " @" + this.ug + ")";
    }

    public static void main(String[] stringArray) {
        gC gC2 = new gC();
        int n2 = -15;
        int n3 = 3;
        int n4 = 39;
        for (int j = -32767; j < Short.MAX_VALUE; ++j) {
            for (int i2 = -32767; i2 < Short.MAX_VALUE; ++i2) {
                ru_2 ru_22;
                long l2 = gC.a(j, i2, n4);
                if (gC2.uf.m(l2)) {
                    ru_22 = (ru_2)gC2.uf.t(l2);
                    System.out.println(" i= " + j + "  j=" + i2 + "  /  " + " x=" + ru_22.EL + "  y=" + ru_22.EM);
                    return;
                }
                ru_22 = new ru_2((short)j, (short)i2);
                assert (ru_22 != null) : "la factory ne doit pas renvoyer une map null";
                gC2.uf.a(l2, ru_22);
            }
        }
        ru_2 ru_23 = (ru_2)gC2.uf.t(gC.a(n2, n3, n4));
        System.out.println("BreakPoup");
    }

    public void a(wX wX2) {
        this.ud = wX2;
    }
}

