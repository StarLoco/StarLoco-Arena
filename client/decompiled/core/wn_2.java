/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.isometric.highlight.HighLightEntity;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from wN
 */
public final class wn_2
implements aHq {
    private static final Logger a = Logger.getLogger(wn_2.class);
    private static final wn_2 avt = new wn_2();
    private final HashMap avu;
    private final adz_1 avv = new adz_1();
    private ef_1 tl;
    final ArrayList avw = new ArrayList();
    private static final boolean avx = true;
    private static age_2 avy = new afa_2();

    private wn_2() {
        this.avu = new HashMap(18);
    }

    public static wn_2 Dj() {
        return avt;
    }

    public void cG(String string) {
        if (this.tl != null) {
            this.tl.HF();
        }
        adz_1 adz_12 = new adz_1();
        String string2 = vq_2.gs(string);
        long l2 = ej_0.aa(string2);
        this.tl = cx_0.JY().a(arX.cQT.iE(), l2, string, adz_12, false);
        this.tl.HE();
        this.avv.set(adz_12.getX(), adz_12.getY());
    }

    public aaR a(String string, fa_0 fa_02) {
        return this.a(string, this.tl, this.avv, fa_02);
    }

    public aaR cH(String string) {
        assert (this.tl != null) : "Aucune texture par d\u00e9faut n'est d\u00e9finie !";
        return this.a(string, this.tl, this.avv);
    }

    public aaR a(String string, ef_1 ef_12, adz_1 adz_12) {
        return this.a(string, ef_12, adz_12, fa_0.ry);
    }

    public aaR a(String string, ef_1 ef_12, adz_1 adz_12, fa_0 fa_02) {
        this.cI(string);
        aaR aaR2 = new aaR(string, ef_12, adz_12, fa_02);
        this.avu.put(string, aaR2);
        return aaR2;
    }

    public void cI(String string) {
        aaR aaR2 = (aaR)this.avu.remove(string);
        if (aaR2 != null) {
            aaR2.release();
        }
    }

    public aaR cJ(String string) {
        return (aaR)this.avu.get(string);
    }

    private static void a(aaR aaR2) {
        assert (aaR2 != null);
        aaR2.clear();
    }

    public void cK(String string) {
        aaR aaR2 = this.cJ(string);
        if (aaR2 != null) {
            wn_2.a(aaR2);
        }
    }

    public void clear() {
        for (aaR aaR2 : this.avu.values()) {
            wn_2.a(aaR2);
        }
    }

    public final void a(aba_2 aba_22, ask_0 ask_02, float f) {
        int n2 = this.avw.size();
        if (n2 == 0) {
            return;
        }
        int n3 = (int)Math.floor(aba_22.aNA());
        float f2 = 43.0f;
        for (int j = 0; j < n2; ++j) {
            long l2;
            aaR aaR2 = (aaR)this.avw.get(j);
            HighLightEntity highLightEntity = aaR2.dt(l2 = ask_02.aua());
            if (highLightEntity == null || !aaR2.cgN.add(l2)) continue;
            int n4 = 4;
            if (!highLightEntity.aRc) {
                if (highLightEntity.aFz() == 0) {
                    a.error((Object)("probl\u00e8me d'hightlight " + ask_02.toString()));
                    highLightEntity.aRc = true;
                    return;
                }
                float[] fArray = aaR2.Aa();
                GeometryMesh geometryMesh = (GeometryMesh)highLightEntity.ma(0);
                geometryMesh.setColor(fArray[0] * 0.5f, fArray[1] * 0.5f, fArray[2] * 0.5f, fArray[3] * f);
                ask_02.a(aba_22, highLightEntity, 43.0f, aaR2.apv(), n3, aaR2.apu(), 4);
                highLightEntity.aRc = true;
                assert (highLightEntity.avb() >= 0);
            }
            aba_22.b(highLightEntity, true);
        }
    }

    public void a(aba_2 aba_22, int n2) {
        avy.b(aba_22);
        this.avw.clear();
        for (aaR aaR2 : this.avu.values()) {
            aaR2.c(avy);
            if (!aaR2.isVisible() || aaR2.isEmpty()) continue;
            aaR2.cgN.clear();
            this.b(aaR2);
        }
    }

    private void b(aaR aaR2) {
        int n2 = this.avw.size();
        if (n2 == 0) {
            this.avw.add(aaR2);
            return;
        }
        if (aaR2.cgL >= ((aaR)this.avw.get((int)(n2 - 1))).cgL) {
            this.avw.add(aaR2);
            return;
        }
        for (int j = 0; j < n2; ++j) {
            if (aaR2.cgL >= ((aaR)this.avw.get((int)j)).cgL) continue;
            this.avw.add(j, aaR2);
        }
    }

    public void a(aba_2 aba_22, float f, float f2) {
    }

    public boolean a(long l2, String string) {
        aaR aaR2 = this.cJ(string);
        if (aaR2 == null) {
            a.error((Object)("le layer " + string + " n'exsite pas"));
            return false;
        }
        aaR2.ct(l2);
        return true;
    }

    public boolean i(ry ry2) {
        long l2 = wn_2.j(ry2);
        for (aaR aaR2 : this.avu.values()) {
            if (!aaR2.m(l2)) continue;
            return true;
        }
        return false;
    }

    public void b(long l2, String string) {
        aaR aaR2 = this.cJ(string);
        if (aaR2 == null) {
            a.error((Object)("le layer " + string + " n'exsite pas"));
            return;
        }
        aaR2.l(l2);
    }

    void a(String string, long l2) {
    }

    void b(String string, long l2) {
    }

    public static long o(int n2, int n3, int n4) {
        return wi_2.u(n2, n3, (short)n4);
    }

    public static long j(ry ry2) {
        return wn_2.o(ry2.getX(), ry2.getY(), ry2.wk());
    }

    public static ry aT(long l2) {
        return wi_2.dc(l2);
    }
}

