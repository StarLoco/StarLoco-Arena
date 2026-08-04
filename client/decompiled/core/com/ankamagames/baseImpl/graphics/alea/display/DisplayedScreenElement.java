/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.baseImpl.graphics.alea.display;

import com.ankamagames.baseImpl.graphics.alea.display.ScreenElement;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import com.sun.opengl.util.texture.TextureCoords;
import org.apache.log4j.Logger;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class DisplayedScreenElement
extends ams_2
implements xw_0,
aog_2,
amw_0,
ask_0 {
    public ScreenElement coy;
    byte coz;
    public EntitySprite coA;
    boolean chi = true;
    boolean coB = false;
    private short coC = 0;
    private final float[] aaV = new float[4];
    private float[] coD;
    private int coE = 0;
    private int aoq = 0;
    private byte coF = 0;
    private byte aRz = (byte)3;
    private long coG;
    private static final float[] coH = new float[4];
    private static final Logger a = Logger.getLogger(DisplayedScreenElement.class);
    private static final int qL = DisplayedScreenElement.L(DisplayedScreenElement.class);

    float getAlpha() {
        return this.aaV[3];
    }

    public void b(ScreenElement screenElement) {
        if (this.coy != null) {
            this.delete();
        }
        this.coy = screenElement;
        this.coz = this.coy.ctr.aoq();
        this.coy.HE();
        this.coG = wn_2.o(this.coy.avV(), this.coy.avW(), this.coy.avU());
        this.coE = this.coy.avZ();
        this.coF = this.coy.awa();
        this.aoq = this.coy.amZ();
        this.atU();
    }

    public void atU() {
        yW yW2 = yW.FL();
        zl_1 zl_12 = this.coy.ctr;
        int n2 = zl_12.oo();
        ef_1 ef_12 = alr_1.aAO().ln(n2);
        int n3 = zl_12.aoi();
        int n4 = zl_12.aoj();
        boolean bl2 = zl_12.aoo();
        float f = this.coy.NQ;
        float f2 = this.coy.NS;
        if ((this.coy.ctp + this.coy.ctq) % 2 != 0) {
            f += this.coy.ctq > -this.coy.ctp ? -0.5f : 0.5f;
        }
        GLGeometrySprite gLGeometrySprite = (GLGeometrySprite)yW2.a(GLGeometrySprite.it(), GLGeometrySprite.class);
        gLGeometrySprite.a(air.cya, air.cye);
        gLGeometrySprite.x(f, f2);
        gLGeometrySprite.setSize(n3, n4);
        this.aaV[0] = 0.5f;
        this.aaV[1] = 0.5f;
        this.aaV[2] = 0.5f;
        this.aaV[3] = 1.0f;
        this.coA = (EntitySprite)yW2.a(EntitySprite.it(), EntitySprite.class);
        if (this.coy.zr()) {
            this.coA.dPB |= 2;
        }
        this.coA.a(gLGeometrySprite);
        this.coA.setTexture(ef_12);
        this.coA.b(ahA.axi().ih("transform"));
        this.coA.oM(-180157682);
        this.coA.dPC.bHy[0] = 2.0f;
        this.coA.dPy = this.coy.ctp;
        this.coA.dPz = this.coy.ctq;
        this.coA.dPA = this.coy.cto - this.coy.aba;
        this.coA.bsF = this.coy.aba;
        float f3 = this.coy.cts;
        if (bl2) {
            this.coA.dPy += 0.9f;
            this.coA.dPz += 0.9f;
            f3 += 1.0f;
        }
        this.coA.dPx = aba_2.a(this.coy.ctp, this.coy.ctq, f3 * 0.0625f);
        this.coA.setColor(this.aaV[0], this.aaV[1], this.aaV[2], this.aaV[3]);
        this.coA.EN = this.coy.NS;
        this.coA.EP = this.coy.NS + n3;
        this.coA.EO = this.coy.NQ - n4;
        this.coA.EQ = this.coy.NQ;
        this.bz((short)ej_0.am(Integer.MAX_VALUE));
        gLGeometrySprite.HF();
    }

    public void bz(short s) {
        this.coC = (short)(this.coC + s);
        TextureCoords textureCoords = this.coy.ctr.bn(this.coC);
        this.coA.Hu().k(textureCoords.top(), textureCoords.left(), textureCoords.bottom(), textureCoords.right());
    }

    public final void kd(int n2) {
        zl_1 zl_12 = UF.ig(n2);
        if (zl_12 == null) {
            a.error((Object)("Unable to replace element gfxId : " + n2 + " unknown"));
            return;
        }
        this.coy.NQ -= this.coy.ctr.aoh();
        this.coy.NS += this.coy.ctr.aog();
        this.coy.ctr = zl_12;
        this.coy.NQ += this.coy.ctr.aoh();
        this.coy.NS -= this.coy.ctr.aog();
        this.coA.HF();
        this.atU();
    }

    public final void a(WL wL) {
        wL.c(this);
    }

    public final boolean c(ari_0 ari_02) {
        return this.coA != null && ari_02.y(this.coA.EQ, this.coA.EN, this.coA.EO, this.coA.EP);
    }

    public boolean a(aba_2 aba_22, cp_2 cp_22, ari_0 ari_02) {
        float[] fArray;
        if (!this.chi) {
            return false;
        }
        if (!this.c(ari_02)) {
            this.coB = true;
            return false;
        }
        long l2 = this.atX();
        if (cp_22.m(l2)) {
            return false;
        }
        if (this.coD == null) {
            fArray = qi_1.vV().a(this);
        } else {
            this.coD[3] = qi_1.vV().a(this)[3];
            this.coD[3] = this.coD[3] * 0.5f;
            this.coD[0] = this.coD[0] * this.coD[3];
            this.coD[1] = this.coD[1] * this.coD[3];
            this.coD[2] = this.coD[2] * this.coD[3];
            fArray = this.coD;
        }
        assert (fArray != null);
        System.arraycopy(fArray, 0, coH, 0, 4);
        zo_0 zo_02 = mg_1.qV().al(this.coG);
        if (zo_02 != null) {
            float f = zo_02.IT;
            coH[0] = coH[0] * (zo_02.IQ * f);
            coH[1] = coH[1] * (zo_02.IR * f);
            coH[2] = coH[2] * (zo_02.IS * f);
            coH[3] = coH[3] * f;
        }
        if (fArray[3] < 0.004f) {
            this.coB = true;
            return false;
        }
        this.aRz = axG.a(this.aRz, coH);
        this.coB = false;
        this.s(coH);
        wn_2.Dj().a(aba_22, this, this.aaV[3]);
        cp_22.a(l2, this);
        return true;
    }

    public boolean bl(int n2, int n3) {
        ef_1 ef_12 = this.coA.jI();
        if (ef_12 == null) {
            return false;
        }
        int n4 = n3 - this.coA.EO;
        kf_0 kf_02 = ef_12.lB(0);
        if (n4 >= kf_02.getHeight() || n4 < 0) {
            return false;
        }
        int n5 = n2 - this.coA.EN;
        if (n5 >= kf_02.getWidth() || n5 < 0) {
            return false;
        }
        if (this.coy.ctr.aok()) {
            n5 = kf_02.getWidth() - n5 - 1;
        }
        return kf_02.pq().ca(n5, n4);
    }

    public final ScreenElement atV() {
        return this.coy;
    }

    public final EntitySprite atW() {
        return this.coA;
    }

    public final long atX() {
        return this.coy.ctu;
    }

    public final boolean isVisible() {
        return this.chi && this.aRz == 3 && !this.coB;
    }

    final boolean atY() {
        return this.chi && this.aRz == 3;
    }

    public final void setVisible(boolean bl2) {
        this.chi = bl2;
    }

    public final void dk(boolean bl2) {
        this.aRz = (byte)(bl2 ? 0 : 2);
    }

    public int amZ() {
        return this.aoq;
    }

    public int Ge() {
        return this.coE;
    }

    public void if(int n2) {
        this.coE = n2;
    }

    public byte atZ() {
        return this.coF;
    }

    public void an(byte by) {
        throw new UnsupportedOperationException("on ne peut pas changer le layer. seul le chargement de la map sp\u00e9cifie la valeur");
    }

    public long aua() {
        return this.coG;
    }

    public void a(aba_2 aba_22, Entity3D entity3D, float f, adz_1 adz_12, int n2, fa_0 fa_02, int n3) {
        if (entity3D == null) {
            return;
        }
        byte by = this.coy.ctr.aol();
        float f2 = this.coy.ctr.aom() ? 0.0f : (float)(this.coy.ctr.getVisualHeight() * n2);
        int n4 = this.coy.cto - this.coy.aba;
        float f3 = f2 * (by != 0 ? 0.5f : 1.0f) + (float)(n4 * n2);
        int n5 = this.coy.ctp;
        int n6 = this.coy.ctq;
        float f4 = (float)aba_22.i(n5, n6);
        float f5 = (float)aba_22.j(n5, n6) + f3;
        avz avz2 = new avz();
        avz2.OH();
        avz2.e(f4, f5, 0.0f);
        entity3D.aUM().b(0, avz2);
        entity3D.dPy = this.coy.ctp;
        entity3D.dPz = this.coy.ctq;
        entity3D.dPA = n4;
        entity3D.bsF = 0.0f;
        entity3D.dPx = aba_22.b(this.coy.ctp, this.coy.ctq, n4, 0.0625f * (float)n3);
        fa_02.a(entity3D, by, f2, f, (float)adz_12.getX() * 0.5f, (float)adz_12.getY() * 0.5f);
    }

    public void a(tl_0 tl_02, agW agW2, double d, double d2, int n2, float f) {
    }

    public void r(float[] fArray) {
        this.coD = fArray;
    }

    public static int it() {
        return qL;
    }

    protected void af() {
        this.aRz = (byte)3;
    }

    protected void ag() {
        this.coy.HF();
        this.coy = null;
        this.coz = 0;
        this.chi = true;
        if (this.coA != null) {
            this.coA.HF();
            this.coA = null;
        }
    }

    protected void delete() {
        super.delete();
        this.coy.HF();
        this.coy = null;
        this.coz = 0;
        if (this.coA != null) {
            this.coA.HF();
            this.coA = null;
        }
    }

    private void s(float[] fArray) {
        if (!this.coy.awc()) {
            this.t(fArray);
        }
    }

    private void t(float[] fArray) {
        this.coy.d(fArray);
        if (fArray[0] < 0.0f) {
            fArray[0] = 0.0f;
        } else if (fArray[0] > 1.0f) {
            fArray[0] = 1.0f;
        }
        if (fArray[1] < 0.0f) {
            fArray[1] = 0.0f;
        } else if (fArray[1] > 1.0f) {
            fArray[1] = 1.0f;
        }
        if (fArray[2] < 0.0f) {
            fArray[2] = 0.0f;
        } else if (fArray[2] > 1.0f) {
            fArray[2] = 1.0f;
        }
        if (fArray[3] < 0.0f) {
            fArray[3] = 0.0f;
        } else if (fArray[3] > 1.0f) {
            fArray[3] = 1.0f;
        }
        if (this.aaV[0] == fArray[0] && this.aaV[1] == fArray[1] && this.aaV[2] == fArray[2] && this.aaV[3] == fArray[3]) {
            return;
        }
        this.aaV[0] = fArray[0];
        this.aaV[1] = fArray[1];
        this.aaV[2] = fArray[2];
        this.aaV[3] = fArray[3];
        this.coA.setColor(this.aaV[0], this.aaV[1], this.aaV[2], this.aaV[3]);
        this.coA.Hv();
    }

    public int gn() {
        return this.coy.ctp;
    }

    public int go() {
        return this.coy.ctq;
    }

    public short gp() {
        return this.coy.cto;
    }

    public void e(float[] fArray) {
        if (this.coA != null && this.coy != null) {
            aPb aPb2 = this.coA.getMaterial();
            float[] fArray2 = aPb2.aYK();
            if (fArray[0] == fArray2[0] && fArray[1] == fArray2[1] && fArray[2] == fArray2[2]) {
                return;
            }
            fArray2[0] = fArray[0];
            fArray2[1] = fArray[1];
            fArray2[2] = fArray[2];
            this.coA.Hv();
        }
    }

    public String toString() {
        return "DisplayScreenElement (" + this.coy.ctp + ", " + this.coy.ctq + ", " + this.coy.cto + ")";
    }

    public final boolean aw(byte by) {
        return (this.coz & by) == this.coz;
    }
}

