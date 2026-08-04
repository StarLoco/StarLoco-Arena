/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.Anm2.Anm;
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometryMesh;
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from gw
 */
public final class gw_2 {
    private static final Logger a;
    private static final Logger sY;
    private static final air sZ;
    private static final aPb ta;
    static final float[] tb;
    static final float[] tc;
    static final float[] td;
    static int te;
    static int tf;
    static int tg;
    static int th;
    private static ak_2 ti;
    private static int tj;
    private static long tk;
    private static ef_1 tl;
    static final ams_1 az;
    private static final pq_0[] tm;
    private static final pq_0 tn;
    private static final int to = -2113168566;
    public static final int tp = 1465070205;
    private static final int tq = 1361332134;
    private static final int tr = 541739097;
    private static final int ts = -1544385936;
    private static final int tt = -945926284;
    private static final int tu = -678076573;
    private static final int tv = -1206723513;
    private static final int tw = 2140055986;
    static final int tx = 2048;
    String ty;
    private final Anm tz;
    private final String tA;
    private final String aJ;
    private lc_1 tB = lc_1.Gf;
    private xM tC = xM.azv;
    private lb_0 tD;
    private float[][] tE;
    private String tF;
    private ju_2 tG;
    private Anm tH;
    private boolean tI;
    private aPb tJ;
    private air tK;
    private air tL;
    private final agf_0 tM;
    private byte tN = (byte)-1;
    private final agf_0 tO;
    float tP;
    float tQ;
    float tR;
    float tS;
    private Matrix44 tT;
    private Matrix44 tU;
    private boolean tV;
    private int tW;
    private int tX;
    private boolean tY = false;
    private final pq_0 tZ = new pq_0();

    public gw_2(Anm anm, String string, String string2) {
        assert (anm != null);
        this.tz = anm;
        this.tz.HE();
        this.setScale(anm.qG.getScale());
        this.tA = string;
        this.aJ = string2;
        this.tH = null;
        this.tG = null;
        this.tB = lc_1.Gf;
        this.tC = xM.azv;
        this.tK = sZ;
        this.tL = air.cye;
        this.tJ = null;
        this.setScale(1.0f);
        this.tM = new agf_0(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE);
        this.tO = new agf_0(0, 0, 0, 0);
        this.tT = new Matrix44();
        this.tT.OH();
        this.tU = new Matrix44();
        this.tU.OH();
        this.tI = false;
        this.tY = false;
    }

    public gw_2(gw_2 gw_22) {
        this(gw_22.tz, gw_22.tA, gw_22.aJ);
        if (gw_22.tD != null) {
            this.tD = new lb_0(gw_22.tD.size());
            ll_0 ll_02 = gw_22.tD.pK();
            while (ll_02.hasNext()) {
                ll_02.fK();
                this.tD.c(ll_02.kR(), ll_02.value());
            }
        }
        assert (this.tA.equals(gw_22.tA));
        assert (this.aJ.equals(gw_22.aJ));
        if (gw_22.tF != null) {
            this.setAnimation(gw_22.tF);
        }
        this.tB = lc_1.b(gw_22.tB);
        this.tC = xM.b(gw_22.tC);
        if (gw_22.tE != null) {
            this.tE = new float[10][];
            for (int j = 0; j < 10; ++j) {
                if (gw_22.tE[j] != null) {
                    this.tE[j] = new float[4];
                    this.tE[j][0] = gw_22.tE[j][0];
                    this.tE[j][1] = gw_22.tE[j][1];
                    this.tE[j][2] = gw_22.tE[j][2];
                    this.tE[j][3] = gw_22.tE[j][3];
                    continue;
                }
                this.tE[j] = null;
            }
        }
        this.tK = gw_22.tK;
        this.tL = gw_22.tL;
        this.tJ = gw_22.tJ;
        this.tM.set(gw_22.tM.aSQ(), gw_22.tM.aSR(), gw_22.tM.aSS(), gw_22.tM.aST());
        this.tT.d(gw_22.tT);
        this.tU.d(gw_22.tU);
        this.tY = gw_22.tY;
        this.tX = gw_22.tX;
        this.setScale(gw_22.getScale());
    }

    public Anm jF() {
        return this.tH;
    }

    public air jG() {
        return this.tK;
    }

    public air jH() {
        return this.tL;
    }

    public static ef_1 jI() {
        return tl;
    }

    public void reset() {
        this.tz.HF();
        this.tB.clear();
        this.tC.clear();
        if (this.tD != null) {
            this.tD.clear();
            this.tD = null;
        }
        this.tF = null;
        this.tH = null;
        this.tG = null;
        this.tK = sZ;
        this.tL = air.cye;
        this.tJ = null;
        this.tE = null;
        this.tI = false;
        this.tX = 0;
        this.tY = false;
        this.setScale(1.0f);
    }

    public final String getPath() {
        return this.aJ;
    }

    public void a(Anm anm, String ... stringArray) {
        if (anm == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/framework/graphics/engine/Anm2/AnmInstance.applyParts must not be null");
        }
        if (stringArray == null) {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of com/ankamagames/framework/graphics/engine/Anm2/AnmInstance.applyParts must not be null");
        }
        aBp aBp2 = gw_2.b(stringArray);
        if (this.tB == lc_1.Gf) {
            this.tB = new lc_1();
        }
        this.tB.a(anm, aBp2);
        this.jJ();
    }

    public void b(Anm anm, String ... stringArray) {
        if (anm == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/framework/graphics/engine/Anm2/AnmInstance.unapplyParts must not be null");
        }
        if (stringArray == null) {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of com/ankamagames/framework/graphics/engine/Anm2/AnmInstance.unapplyParts must not be null");
        }
        if (this.tB == lc_1.Gf) {
            return;
        }
        aBp aBp2 = gw_2.b(stringArray);
        this.tY |= this.tB.b(anm, aBp2);
        this.jJ();
    }

    private static aBp b(String[] stringArray) {
        aBp aBp2 = new aBp(stringArray.length);
        for (int j = 0; j < stringArray.length; ++j) {
            aBp2.nk(ej_0.Z(stringArray[j]));
        }
        return aBp2;
    }

    public void jJ() {
        this.tC.Ev();
        ait_2[] ait_2Array = this.tz.qG.auB();
        if (ait_2Array == null) {
            return;
        }
        int n2 = ait_2Array.length;
        ll_0 ll_02 = this.tB.pO();
        while (ll_02.hasNext()) {
            ll_02.fK();
            afd_2 afd_22 = (afd_2)ll_02.value();
            Ze ze = afd_22.aRv();
            if (ze == null) continue;
            for (int j = 0; j < n2; ++j) {
                ait_2 ait_22 = ait_2Array[j];
                if (ze.ccG != ait_22.ccG) continue;
                if (this.tC == xM.azv) {
                    this.tC = new xM();
                }
                this.tC.em(ait_22.dPR);
            }
        }
    }

    public void aA(int n2) {
        if (this.tC == xM.azv) {
            this.tC = new xM();
        }
        this.tC.eo(n2);
    }

    public void aB(int n2) {
        assert (this.tC != xM.azv);
        this.tC.ep(n2);
    }

    public void a(int n2, gw_2 gw_22) {
        if (this.tD == null) {
            this.tD = new lb_0();
        }
        if (!this.tz.qD.bY(n2)) {
            a.error((Object)("Unable to attach a child to baseNameCRC (" + n2 + ")"));
        }
        this.tD.c(n2, gw_22);
    }

    public void aC(int n2) {
        if (this.tD == null) {
            return;
        }
        this.tD.remove(n2);
    }

    public void a(gw_2 gw_22) {
        if (this.tD == null) {
            return;
        }
        ll_0 ll_02 = this.tD.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            if (ll_02.value() != gw_22) continue;
            this.tD.remove(ll_02.kR());
            return;
        }
    }

    public boolean jK() {
        boolean bl2 = false;
        if (this.ty != null) {
            this.setAnimation(this.ty);
            if (this.ty == null) {
                bl2 = true;
                this.tY = true;
            }
        }
        return bl2;
    }

    public void setAnimation(String string) {
        aek_0 aek_02 = this.tz.qG;
        String string2 = this.ao(string);
        if (string2 != string) {
            this.tI = true;
            this.tZ.f(-1.0f, 0.0f, 0.0f, 1.0f);
        } else {
            this.tI = false;
            this.tZ.uu();
        }
        this.tF = string2;
        this.tG = null;
        this.ty = null;
        this.tY = true;
        Fa fa = aek_02.hO(string2);
        if (fa == null) {
            sY.error((Object)("Animation " + string2 + " not found (" + this.aJ + this.tA + ")!"));
            return;
        }
        assert (fa.m_name.equals(string2));
        if (fa.aUs == -1) {
            assert (this.tz.is());
            this.a(this.tz, this.tz.aw(fa.asw));
        } else {
            String string3 = aek_02.kk(fa.aUs);
            xw_1.EB().a(string3, fa, this);
        }
        this.tN = (byte)-1;
    }

    public ju_2 jL() {
        return this.tG;
    }

    public boolean jM() {
        return this.ty != null;
    }

    public void c(ArrayList arrayList) {
        Fa[] faArray = this.tz.qG.auz();
        for (int j = 0; j < faArray.length; ++j) {
            arrayList.add(faArray[j].m_name);
        }
    }

    public final void a(air air2, air air3) {
        this.tK = air2;
        this.tL = air3;
    }

    public boolean jN() {
        boolean bl2 = this.tB.fZ();
        if (bl2) {
            this.jJ();
        }
        return bl2;
    }

    public void a(int n2, Entity3D entity3D, int n3) {
        int n4;
        entity3D.clear();
        if (this.tG == null) {
            a.error((Object)"On ne peut pas mettre a jour une animation nulle");
            return;
        }
        this.setScale(this.tz.qG.getScale());
        this.tX = n4 = this.aG(n2);
        this.tY = true;
        te = 0;
        tj = -1;
        tk = 0L;
        tf = 0;
        tg = 0;
        th = 0;
        tl = null;
        this.tP = Float.MAX_VALUE;
        this.tQ = -3.4028235E38f;
        this.tR = Float.MAX_VALUE;
        this.tS = -3.4028235E38f;
        this.tZ.acF = 0;
        this.a(n4, this.tG.Dc, this.tG.og(), entity3D, this.tZ, this.tH, 0);
        if (te != 0) {
            this.a(entity3D);
        }
        this.tM.set((int)this.tP, (int)this.tQ, (int)this.tR, (int)this.tS);
    }

    public void r(float f) {
        agf_0 agf_02 = this.tM;
        if (this.tN == 0) {
            return;
        }
        if (agf_02.bAB == Integer.MAX_VALUE || agf_02.bAD == Integer.MAX_VALUE || agf_02.bAC == Integer.MIN_VALUE || agf_02.bAE == Integer.MIN_VALUE) {
            this.tO.set(agf_02.bAB, agf_02.bAC, agf_02.bAD, agf_02.bAE);
            this.tN = (byte)-1;
            return;
        }
        if (this.tN == -1) {
            this.tO.set(agf_02.bAB, agf_02.bAC, agf_02.bAD, agf_02.bAE);
            this.tN = 0;
            return;
        }
        float f2 = f / 300.0f;
        this.tN = 0;
        int n2 = this.b(this.tO.bAB, agf_02.bAB, f2);
        int n3 = this.b(this.tO.bAC, agf_02.bAC, f2);
        int n4 = this.b(this.tO.bAD, agf_02.bAD, f2);
        int n5 = this.b(this.tO.bAE, agf_02.bAE, f2);
        this.tO.set(n2, n3, n4, n5);
    }

    private int b(int n2, int n3, float f) {
        float f2 = n3 - n2;
        if (Math.abs(f2) <= 1.0f) {
            return n2;
        }
        this.tN = 1;
        return (int)Math.ceil((float)n2 + f2 * f);
    }

    public static aPb jO() {
        return ta;
    }

    public void setMaterial(aPb aPb2) {
        this.tJ = aPb2;
    }

    public agf_0 jP() {
        return this.tM;
    }

    public int aD(int n2) {
        int n3 = this.aG(n2);
        ju_2 ju_22 = this.tG;
        if (this.tG.Dc.length == 1 && this.tG.Dc[0].Db.length == 1) {
            ju_22 = this.tH.t(this.tG.Dc[0].Db[0].fL);
        }
        if (ju_22 == null) {
            return 1;
        }
        if (n3 >= ju_22.Dc.length) {
            if (ju_22.og()) {
                return n3 % ju_22.Dc.length;
            }
            return ju_22.Dc.length - 1;
        }
        return n3;
    }

    public float getScale() {
        return this.tz.qG.getScale();
    }

    public void setScale(float f) {
        if (this.tI) {
            this.tZ.f(-f, 0.0f, 0.0f, f);
        } else {
            this.tZ.f(f, 0.0f, 0.0f, f);
        }
    }

    public final boolean cL() {
        return this.tH != null && this.tH.cL();
    }

    public int jQ() {
        assert (this.tG != null);
        if (this.tG.Dc.length == 1 && this.tG.Dc[0].Db.length == 1) {
            ju_2 ju_22 = this.tH.t(this.tG.Dc[0].Db[0].fL);
            if (ju_22 == null) {
                return 1;
            }
            return ju_22.Dc.length;
        }
        return this.tG.Dc.length;
    }

    public float[] aE(int n2) {
        if (this.tE == null) {
            return null;
        }
        return this.tE[n2];
    }

    public void a(int n2, float[] fArray) {
        if (this.tE == null) {
            this.tE = new float[10][];
        }
        this.tE[n2] = fArray;
    }

    public void aF(int n2) {
        if (this.tE == null) {
            return;
        }
        this.tE[n2] = null;
    }

    public void a(ArrayList arrayList, int n2, int n3) {
        int n4;
        assert (this.tG != null);
        if (n2 == n3 && n2 != 0) {
            return;
        }
        int n5 = this.jQ();
        int n6 = n2 == 0 ? -1 : this.aD(n3);
        int n7 = n4 = n2 == 0 ? 0 : this.aD(n2);
        if (n6 + 1 > n4) {
            int n8;
            jw_1[] jw_1Array;
            int n9;
            for (n9 = n6 + 1; n9 <= n5 - 1; ++n9) {
                jw_1Array = this.tG.Dc[n9].awa;
                for (n8 = 0; n8 < jw_1Array.length; ++n8) {
                    arrayList.add(jw_1Array[n8]);
                }
            }
            for (n9 = 0; n9 <= n4; ++n9) {
                jw_1Array = this.tG.Dc[n9].awa;
                for (n8 = 0; n8 < jw_1Array.length; ++n8) {
                    arrayList.add(jw_1Array[n8]);
                }
            }
        } else {
            for (int j = n6 + 1; j <= n4; ++j) {
                jw_1[] jw_1Array = this.tG.Dc[j].awa;
                for (int i2 = 0; i2 < jw_1Array.length; ++i2) {
                    arrayList.add(jw_1Array[i2]);
                }
            }
        }
    }

    private int aG(int n2) {
        return (int)(this.tH.qx.getFrameRate() * (float)n2 / 1000.0f);
    }

    public final Matrix44 jR() {
        return this.tT;
    }

    public final Matrix44 jS() {
        return this.tU;
    }

    public final boolean is() {
        return this.tz.is();
    }

    public final int an(String string) {
        Anm anm;
        ju_2 ju_22;
        if (!this.tz.is()) {
            a.error((Object)("Impossible de r\u00e9cup\u00e9rer la duree de l'animation " + string + " dans " + this.aJ + this.tA + " : la definition n'est pas chargee"));
            return 0;
        }
        aek_0 aek_02 = this.tz.qG;
        Fa fa = aek_02.hO(string = this.ao(string));
        if (fa == null) {
            sY.error((Object)("Animation " + string + " not found (" + this.aJ + this.tA + ")!"));
            return 0;
        }
        assert (fa.m_name.equals(string));
        if (fa.aUs == -1) {
            ju_22 = this.tz.aw(fa.asw);
            anm = this.tH;
        } else {
            String string2 = this.aJ + aek_02.kk(fa.aUs) + ".anm";
            try {
                anm = xw_1.EB().f(string2, true);
                ju_22 = anm != null ? anm.aw(fa.asw) : null;
            }
            catch (IOException iOException) {
                a.error((Object)"", (Throwable)iOException);
                return 0;
            }
        }
        return gw_2.a(ju_22, anm);
    }

    private String ao(String string) {
        if (this.tz.qG.auG()) {
            char c = string.charAt(0);
            switch (c) {
                case '4': {
                    string = "0" + string.substring(1);
                    break;
                }
                case '3': {
                    string = "1" + string.substring(1);
                    break;
                }
                case '7': {
                    string = "5" + string.substring(1);
                }
            }
        }
        return string;
    }

    private static int a(ju_2 ju_22, Anm anm) {
        if (ju_22 == null || anm == null) {
            return 0;
        }
        if (ju_22.Dc.length == 1 && ju_22.Dc[0].Db.length == 1 && (ju_22 = anm.t(ju_22.Dc[0].Db[0].fL)) == null) {
            return 0;
        }
        int n2 = ju_22.getFrameCount();
        if (n2 == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int)((float)(1000 * n2) / anm.qx.getFrameRate());
    }

    public final int jT() {
        return gw_2.a(this.tG, this.tH);
    }

    public final boolean jU() {
        if (this.tD == null) {
            return this.tY;
        }
        ll_0 ll_02 = this.tD.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            gw_2 gw_22 = (gw_2)ll_02.value();
            if (!gw_22.jU()) continue;
            return true;
        }
        return true;
    }

    public final void jV() {
        this.tY = false;
    }

    public final void jW() {
        this.tY = true;
    }

    public final void a(boolean bl2, int n2) {
        this.tV = bl2;
        this.tW = n2;
    }

    void a(Anm anm, ju_2 ju_22) {
        if (anm != null && anm.avb() == 0) {
            anm.HE();
        }
        this.tH = anm;
        this.tG = ju_22;
        this.tN = 1;
    }

    private ju_2 a(Anm anm, String string) {
        return (ju_2)anm.qC.get(ej_0.Z(string));
    }

    private void a(Anm anm, ArrayList arrayList) {
        for (int j = 0; j < anm.qA.length; ++j) {
            if (anm.qA[j] == null || anm.qA[j].m_name == null || !anm.qA[j].m_name.toLowerCase().contains("anim")) continue;
            arrayList.add(anm.qA[j].m_name);
        }
    }

    private void a(ArrayList arrayList, int n2, xc_2[] xc_2Array, Anm anm) {
        xc_2 xc_22 = xc_2Array[n2 % xc_2Array.length];
        jw_1[] jw_1Array = xc_22.awa;
        int n3 = jw_1Array.length;
        for (int j = 0; j < n3; ++j) {
            arrayList.add(jw_1Array[j]);
        }
        for (abd_0 abd_02 : xc_22.Db) {
            ju_2 ju_22;
            hn_2 hn_22 = anm.v(abd_02.fL);
            if (hn_22 != null) {
                ju_22 = (ju_2)this.tz.qC.get(hn_22.asw);
                if (ju_22 == null) continue;
                this.a(arrayList, ju_22, n2, this.tz);
                continue;
            }
            ju_22 = (ju_2)anm.qB.an(abd_02.fL);
            if (ju_22 == null) continue;
            this.a(arrayList, ju_22, n2, anm);
        }
    }

    private boolean a(ju_2 ju_22) {
        return false;
    }

    private void a(int n2, xc_2[] xc_2Array, boolean bl2, Entity3D entity3D, pq_0 pq_02, Anm anm, int n3) {
        xc_2 xc_22 = !this.tV || n3 != 0 ? (n2 >= xc_2Array.length ? (bl2 ? xc_2Array[n2 % xc_2Array.length] : xc_2Array[xc_2Array.length - 1]) : xc_2Array[n2]) : xc_2Array[this.tW % xc_2Array.length];
        for (int j = 0; j < xc_22.Db.length; ++j) {
            ju_2 ju_22;
            Object object;
            abd_0 abd_02 = xc_22.Db[j];
            pq_0 pq_03 = tm[n3];
            pq_03.acF = pq_02.acF;
            abd_02.a(pq_02, pq_03);
            if (pq_03.IT == 0.0f) continue;
            if (anm.qE.size() != 0 && (object = (hn_2)anm.qE.an(abd_02.fL)) != null) {
                ju_22 = (ju_2)this.tz.qC.get(((hn_2)object).asw);
                if (!this.b(ju_22)) continue;
                this.a(ju_22, n2, entity3D, pq_03, this.tz, n3);
                continue;
            }
            ju_22 = (ju_2)anm.qB.an(abd_02.fL);
            if (this.b(ju_22)) {
                this.a(ju_22, n2, entity3D, pq_03, anm, n3);
                continue;
            }
            object = (ana_1)anm.qz.an(abd_02.fL);
            if (object == null || pq_03.IT <= 0.004f) continue;
            float f = ((ana_1)object).Gv * pq_03.acx + ((ana_1)object).Gw * pq_03.acz + pq_03.acB;
            float f2 = ((ana_1)object).Gv * -pq_03.acy + ((ana_1)object).Gw * -pq_03.acA - pq_03.acC;
            float f3 = pq_03.acz * (float)((ana_1)object).adF;
            float f4 = -pq_03.acA * (float)((ana_1)object).adF;
            float f5 = pq_03.acx * (float)((ana_1)object).adE;
            float f6 = -pq_03.acy * (float)((ana_1)object).adE;
            int n4 = ((ana_1)object).dZA & 0xFFFF;
            vk_2 vk_22 = anm.qy[n4];
            long l2 = anm.a(vk_22);
            if (tk != l2) {
                if (te != 0) {
                    this.a(entity3D);
                }
                tj = n4;
                tk = l2;
                tl = cx_0.JY().bt(l2);
            }
            float f7 = f3 + f;
            float f8 = f4 + f2;
            float f9 = f5 + f3 + f;
            float f10 = f6 + f4 + f2;
            float f11 = f5 + f;
            float f12 = f6 + f2;
            gw_2.tb[gw_2.tf++] = f;
            gw_2.tb[gw_2.tf++] = f2;
            gw_2.tb[gw_2.tf++] = f7;
            gw_2.tb[gw_2.tf++] = f8;
            gw_2.tb[gw_2.tf++] = f9;
            gw_2.tb[gw_2.tf++] = f10;
            gw_2.tb[gw_2.tf++] = f11;
            gw_2.tb[gw_2.tf++] = f12;
            ti.b(f, f2);
            ti.b(f7, f8);
            ti.b(f9, f10);
            ti.b(f11, f12);
            if (f > this.tQ) {
                this.tQ = f;
            } else if (f < this.tP) {
                this.tP = f;
            }
            if (f7 > this.tQ) {
                this.tQ = f7;
            } else if (f7 < this.tP) {
                this.tP = f7;
            }
            if (f9 > this.tQ) {
                this.tQ = f9;
            } else if (f9 < this.tP) {
                this.tP = f9;
            }
            if (f11 > this.tQ) {
                this.tQ = f11;
            } else if (f11 < this.tP) {
                this.tP = f11;
            }
            if (f2 > this.tS) {
                this.tS = f2;
            } else if (f2 < this.tR) {
                this.tR = f2;
            }
            if (f8 > this.tS) {
                this.tS = f8;
            } else if (f8 < this.tR) {
                this.tR = f8;
            }
            if (f10 > this.tS) {
                this.tS = f10;
            } else if (f10 < this.tR) {
                this.tR = f10;
            }
            if (f12 > this.tS) {
                this.tS = f12;
            } else if (f12 < this.tR) {
                this.tR = f12;
            }
            float f13 = pq_03.IQ;
            float f14 = pq_03.IR;
            float f15 = pq_03.IS;
            float f16 = pq_03.IT;
            f13 *= 0.5f;
            f14 *= 0.5f;
            f15 *= 0.5f;
            f13 = ej_0.b(f13, 0.0f, 1.0f);
            f14 = ej_0.b(f14, 0.0f, 1.0f);
            f15 = ej_0.b(f15, 0.0f, 1.0f);
            f16 = ej_0.b(f16, 0.0f, 1.0f);
            gw_2.tc[gw_2.tg++] = f13;
            gw_2.tc[gw_2.tg++] = f14;
            gw_2.tc[gw_2.tg++] = f15;
            gw_2.tc[gw_2.tg++] = f16;
            gw_2.tc[gw_2.tg++] = f13;
            gw_2.tc[gw_2.tg++] = f14;
            gw_2.tc[gw_2.tg++] = f15;
            gw_2.tc[gw_2.tg++] = f16;
            gw_2.tc[gw_2.tg++] = f13;
            gw_2.tc[gw_2.tg++] = f14;
            gw_2.tc[gw_2.tg++] = f15;
            gw_2.tc[gw_2.tg++] = f16;
            gw_2.tc[gw_2.tg++] = f13;
            gw_2.tc[gw_2.tg++] = f14;
            gw_2.tc[gw_2.tg++] = f15;
            gw_2.tc[gw_2.tg++] = f16;
            gw_2.td[gw_2.th++] = ((ana_1)object).bsB;
            gw_2.td[gw_2.th++] = ((ana_1)object).bsD;
            gw_2.td[gw_2.th++] = ((ana_1)object).bsB;
            gw_2.td[gw_2.th++] = ((ana_1)object).bsA;
            gw_2.td[gw_2.th++] = ((ana_1)object).bsC;
            gw_2.td[gw_2.th++] = ((ana_1)object).bsA;
            gw_2.td[gw_2.th++] = ((ana_1)object).bsC;
            gw_2.td[gw_2.th++] = ((ana_1)object).bsD;
            ++te;
        }
    }

    private boolean b(ju_2 ju_22) {
        return ju_22 != null && !this.tC.contains(ju_22.CY) && !this.a(ju_22);
    }

    private void a(Entity3D entity3D) {
        int n2 = te * 4;
        VertexBufferPCT vertexBufferPCT = new VertexBufferPCT(n2);
        vertexBufferPCT.c(tb, tf);
        vertexBufferPCT.e(tc, tg);
        vertexBufferPCT.g(td, th);
        vertexBufferPCT.dz(n2);
        GLGeometryMesh gLGeometryMesh = (GLGeometryMesh)yW.FL().a(GLGeometryMesh.it(), GLGeometryMesh.class);
        gLGeometryMesh.a(this.tK, this.tL);
        az.HE();
        gLGeometryMesh.a(jB.Ba, vertexBufferPCT, az, false, tc);
        entity3D.a(gLGeometryMesh, tl, this.tJ);
        gLGeometryMesh.c(ti);
        te = 0;
        tf = 0;
        tg = 0;
        th = 0;
        ti.reset();
    }

    private void a(ju_2 ju_22, int n2, Entity3D entity3D, pq_0 pq_02, Anm anm, int n3) {
        Object object;
        int n4 = ju_22.CZ & 0x3F;
        if (this.tB.pM() && (object = this.tB.bZ(ju_22.CX)) != null) {
            Object object2;
            if (ju_22.m_name != null && n4 != 0 && n4 != 9 && n4 != 6 && n4 != 7 && this.tE != null && n4 != pq_02.acF) {
                object2 = this.tE[pq_02.acF];
                if (object2 != null) {
                    pq_02.IQ /= object2[0];
                    pq_02.IR /= object2[1];
                    pq_02.IS /= object2[2];
                    pq_02.IT /= object2[3];
                }
                if ((object2 = (Object)this.tE[n4]) != null) {
                    pq_02.IQ *= object2[0];
                    pq_02.IR *= object2[1];
                    pq_02.IS *= object2[2];
                    pq_02.IT *= object2[3];
                    pq_02.acF = (byte)n4;
                }
            }
            object2 = ((afd_2)object).dFa;
            assert (object2 != null);
            this.a(n2, ((ju_2)object2).Dc, ((ju_2)object2).og(), entity3D, pq_02, ((afd_2)object).dEZ, n3 + 1);
            return;
        }
        if (this.tE != null && n4 != 0 && n4 != pq_02.acF) {
            object = this.tE[pq_02.acF];
            if (object != null) {
                pq_02.IQ /= object[0];
                pq_02.IR /= object[1];
                pq_02.IS /= object[2];
                pq_02.IT /= object[3];
            }
            if ((object = (Object)this.tE[n4]) != null) {
                pq_02.IQ *= object[0];
                pq_02.IR *= object[1];
                pq_02.IS *= object[2];
                pq_02.IT *= object[3];
                pq_02.acF = (byte)n4;
            }
        }
        if (ju_22.m_name != null) {
            switch (ju_22.CY) {
                case -2113168566: {
                    this.a(pq_02, this.tT.Pn());
                    break;
                }
                case 1465070205: {
                    this.a(pq_02, this.tU.Pn());
                }
            }
        }
        if (this.tD != null && this.tD.contains(ju_22.CY)) {
            object = (gw_2)this.tD.get(ju_22.CY);
            tn.m(pq_02.acB, pq_02.acC);
            if (this.tI && !((gw_2)object).tI) {
                tn.f(-pq_02.acx, pq_02.acy, -pq_02.acz, pq_02.acA);
            } else {
                tn.f(pq_02.acx, pq_02.acy, pq_02.acz, pq_02.acA);
            }
            super.a(this, entity3D, tn);
        }
        this.a(n2, ju_22.Dc, ju_22.og(), entity3D, pq_02, anm, n3 + 1);
    }

    private void a(pq_0 pq_02, float[] fArray) {
        fArray[0] = pq_02.acx;
        fArray[1] = pq_02.acy;
        fArray[4] = pq_02.acz;
        fArray[5] = pq_02.acA;
        fArray[12] = pq_02.acB;
        fArray[13] = -pq_02.acC;
        if (this.tI) {
            fArray[0] = -fArray[0];
        }
    }

    private void a(gw_2 gw_22, Entity3D entity3D, pq_0 pq_02) {
        assert (this.tG != null);
        if (this.tz != null) {
            this.setScale(this.tz.qG.getScale());
        }
        this.tP = Float.MAX_VALUE;
        this.tQ = Float.MIN_VALUE;
        this.tR = Float.MAX_VALUE;
        this.tS = Float.MIN_VALUE;
        this.a(this.tX, this.tG.Dc, this.tG.og(), entity3D, pq_02, this.tH, 0);
        if (te != 0) {
            this.a(entity3D);
        }
        this.tM.set((int)this.tP, (int)this.tQ, (int)this.tR, (int)this.tS);
    }

    private void a(ArrayList arrayList, ju_2 ju_22, int n2, Anm anm) {
        afd_2 afd_22 = this.tB.bZ(ju_22.CX);
        if (afd_22 != null) {
            assert (afd_22.dFa != null);
            this.a(arrayList, n2, afd_22.dFa.Dc, afd_22.dEZ);
            return;
        }
        this.a(arrayList, n2, ju_22.Dc, anm);
    }

    public final boolean ap(String string) {
        return this.tz.qG.hO(string = this.ao(string)) != null;
    }

    public boolean jX() {
        return this.tz.qG.auE();
    }

    public float jY() {
        return this.tz.qG.jY();
    }

    public agf_0 jZ() {
        return this.tO;
    }

    public final void b(acj_1 acj_12) {
        if (this.tz.is()) {
            acj_12.aqK();
        } else {
            this.tz.a(acj_12);
        }
    }

    public float getMinX() {
        return this.tP;
    }

    public float getMinY() {
        return this.tR;
    }

    public final String ka() {
        return this.aJ + this.tA + ".anm";
    }

    public final boolean kb() {
        return this.tB.pN();
    }

    public lc_1 kc() {
        return this.tB.pQ();
    }

    public void a(lc_1 lc_12) {
        this.tB.clear();
        this.tB = lc_12.pQ();
        this.jJ();
    }

    public xM kd() {
        return this.tC.Ew();
    }

    public void a(xM xM2) {
        this.tC.clear();
        this.tC = xM2.Ew();
    }

    public void ke() {
        this.tB.clear();
        this.jJ();
    }

    public void a(Anm anm) {
        this.tB.d(anm);
        this.jJ();
    }

    static {
        int n2;
        a = Logger.getLogger(gw_2.class);
        sY = Logger.getLogger((String)"animation");
        sZ = air.cya;
        ti = new ak_2();
        ta = aPb.enf;
        ta.H(0.0f, 0.0f, 0.0f, 0.0f);
        tb = new float[4096];
        tc = new float[8192];
        td = new float[4096];
        az = new ams_1(2048);
        for (n2 = 0; n2 < 2048; ++n2) {
            az.add((short)n2);
        }
        n2 = 32;
        tm = new pq_0[32];
        for (int j = 0; j < tm.length; ++j) {
            gw_2.tm[j] = new pq_0();
        }
        tn = new pq_0();
    }
}

