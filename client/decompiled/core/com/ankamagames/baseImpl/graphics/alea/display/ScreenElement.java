/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea.display;

public class ScreenElement
extends ams_2 {
    private byte aIm;
    public short cto;
    public int ctp;
    public int ctq;
    public int NQ;
    public int NS;
    public zl_1 ctr;
    public byte cts;
    public byte aba;
    int bPM;
    byte coF;
    int aoq;
    boolean ctt;
    public long ctu;
    private float[] aE;
    private static final int qL;
    private static final float[] coH;
    private static final float ctv = 0.5f;
    private static final float ctw = 1.0f;
    private static final float ctx = 1.0f;
    public static final int cty = 1;
    public static final int ctz = 2;
    public static final int ctA = 4;
    public static final int ctB = 8;
    public static final int ctC = 16;
    private static final int[] ctD;

    public ScreenElement() {
    }

    public ScreenElement(byte by) {
        this.setType(by);
    }

    public final void setType(byte by) {
        this.aIm = by;
        this.aE = ScreenElement.kw(by);
    }

    public final void b(acf acf2) {
        this.cto = acf2.readShort();
        this.aba = acf2.readByte();
        this.cts = acf2.readByte();
        this.bPM = acf2.readInt();
        this.coF = acf2.readByte();
        this.aoq = acf2.readInt();
        this.ctt = acf2.aqE();
        int n2 = acf2.readInt();
        this.ctr = UF.ig(n2);
        assert (this.ctr != null) : "Element of id " + n2 + " is missing";
        ScreenElement.a(this.aE, this.aIm, acf2);
    }

    public final boolean zr() {
        return this.ctt;
    }

    public final boolean aos() {
        return this.ctr.aos();
    }

    public final short avU() {
        return this.cto;
    }

    public final int avV() {
        return this.ctp;
    }

    public final int avW() {
        return this.ctq;
    }

    public final byte PD() {
        return this.aba;
    }

    public final ry avX() {
        return new ry(this.ctp, this.ctq, this.cto);
    }

    public final short gQ() {
        return (short)(this.cto - this.aba);
    }

    public final long atX() {
        return this.ctu;
    }

    public final zl_1 avY() {
        return this.ctr;
    }

    public int amZ() {
        return this.aoq;
    }

    public final int avZ() {
        return this.bPM;
    }

    public final byte awa() {
        return this.coF;
    }

    public final boolean awb() {
        return (this.aIm & 2) == 2;
    }

    public final boolean awc() {
        return (this.aIm & 0x10) == 16;
    }

    public final void u(float[] fArray) {
        assert (fArray != null && fArray.length >= 3);
        if ((this.aIm & 2) != 2) {
            fArray[2] = 0.5f;
            fArray[1] = 0.5f;
            fArray[0] = 0.5f;
            return;
        }
        int n2 = ScreenElement.kx(this.aIm);
        fArray[0] = this.aE[n2++];
        fArray[1] = this.aE[n2++];
        fArray[2] = this.aE[n2];
    }

    public final void v(float[] fArray) {
        assert (fArray != null && fArray.length >= 4);
        this.u(fArray);
        fArray[3] = this.getAlpha();
    }

    public final float getAlpha() {
        return (this.aIm & 8) == 8 ? this.aE[ScreenElement.kz(this.aIm)] : 1.0f;
    }

    public final void w(float[] fArray) {
        assert ((this.aIm & 0x10) == 16) : "impossible sur un objet non d\u00e9grad\u00e9. tester avec isGradient()";
        assert (fArray != null && fArray.length >= 3);
        if ((this.aIm & 2) != 2) {
            fArray[2] = 0.5f;
            fArray[1] = 0.5f;
            fArray[0] = 0.5f;
            return;
        }
        int n2 = ScreenElement.kA(this.aIm);
        fArray[0] = this.aE[n2++];
        fArray[1] = this.aE[n2++];
        fArray[2] = this.aE[n2];
    }

    public final void x(float[] fArray) {
        assert ((this.aIm & 0x10) == 16) : "impossible sur un objet non d\u00e9grad\u00e9. tester avec isGradient()";
        assert (fArray != null && fArray.length >= 4);
        this.w(fArray);
        fArray[3] = this.getAlpha();
    }

    public final float awd() {
        assert ((this.aIm & 0x10) == 16) : "impossible sur un objet non d\u00e9grad\u00e9. tester avec isGradient()";
        return (this.aIm & 8) == 8 ? this.aE[ScreenElement.kB(this.aIm)] : 1.0f;
    }

    public void d(float[] fArray) {
        assert (fArray != null);
        assert (fArray.length >= 4);
        this.u(coH);
        fArray[0] = fArray[0] * coH[0];
        fArray[1] = fArray[1] * coH[1];
        fArray[2] = fArray[2] * coH[2];
        fArray[3] = fArray[3] * this.getAlpha();
    }

    public final void a(aij_1 aij_12) {
        assert (this.ctr != null);
        aij_12.writeByte(this.aIm);
        aij_12.writeShort(this.cto);
        aij_12.writeByte(this.aba);
        aij_12.writeByte(this.cts);
        aij_12.writeInt(this.bPM);
        aij_12.writeByte(this.coF);
        aij_12.writeInt(this.aoq);
        aij_12.fe(this.ctt);
        aij_12.writeInt(this.ctr.getId());
        for (int j = 0; j < this.aE.length; ++j) {
            aij_12.writeByte((byte)this.aE[j]);
        }
    }

    public final void B(int n2, int n3, short s) {
        this.ctp = n2;
        this.ctq = n3;
        this.cto = s;
    }

    public final void bn(int n2, int n3) {
        this.NS = n2;
        this.NQ = n3;
    }

    public void aA(boolean bl2) {
        this.ctt = bl2;
    }

    public final void setHeight(int n2) {
        this.aba = (byte)n2;
    }

    public final void b(zl_1 zl_12) {
        this.ctr = zl_12;
    }

    public final void aB(byte by) {
        this.cts = by;
    }

    public final void aC(byte by) {
        this.coF = by;
    }

    public void ku(int n2) {
        this.aoq = n2;
    }

    public final void kv(int n2) {
        this.bPM = n2;
    }

    public final void h(float f, float f2, float f3) {
        if ((this.aIm & 2) != 2) {
            return;
        }
        int n2 = ScreenElement.kx(this.aIm);
        this.aE[n2++] = f;
        this.aE[n2++] = f2;
        this.aE[n2] = f3;
    }

    public final void r(float f, float f2, float f3, float f4) {
        if ((this.aIm & 2) == 2) {
            int n2 = ScreenElement.kx(this.aIm);
            this.aE[n2++] = f;
            this.aE[n2++] = f2;
            this.aE[n2] = f3;
        }
        if ((this.aIm & 8) == 8) {
            this.aE[ScreenElement.kz((int)this.aIm)] = f4;
        }
    }

    public final void W(float f) {
        if ((this.aIm & 8) == 8) {
            this.aE[ScreenElement.kz((int)this.aIm)] = f;
        }
    }

    public final void i(float f, float f2, float f3) {
        if ((this.aIm & 0x10) != 16) {
            return;
        }
        if ((this.aIm & 2) != 2) {
            return;
        }
        int n2 = ScreenElement.kx(this.aIm);
        this.aE[n2++] = f;
        this.aE[n2++] = f2;
        this.aE[n2] = f3;
    }

    public final void s(float f, float f2, float f3, float f4) {
        if ((this.aIm & 0x10) != 16) {
            return;
        }
        if ((this.aIm & 2) == 2) {
            int n2 = ScreenElement.kx(this.aIm);
            this.aE[n2++] = f;
            this.aE[n2++] = f2;
            this.aE[n2] = f3;
        }
        if ((this.aIm & 8) == 8) {
            this.aE[ScreenElement.kz((int)this.aIm)] = f4;
        }
    }

    public final void aS(float f) {
        if ((this.aIm & 0x10) != 16) {
            return;
        }
        if ((this.aIm & 8) == 8) {
            this.aE[ScreenElement.kz((int)this.aIm)] = f;
        }
    }

    protected void af() {
        this.ctu = 0L;
        this.aIm = (byte)-1;
        this.aE = null;
    }

    protected void ag() {
        this.ctu = 0L;
    }

    protected void delete() {
        super.delete();
        this.ctu = 0L;
        this.aIm = (byte)-1;
        this.aE = null;
    }

    public static int it() {
        return qL;
    }

    static void a(float[] fArray, int n2, acf acf2) {
        int n3 = 0;
        if ((n2 & 1) == 1) {
            fArray[n3++] = 2.0f * ((float)acf2.readByte() / 255.0f) + 1.0f;
            fArray[n3++] = 2.0f * ((float)acf2.readByte() / 255.0f) + 1.0f;
            fArray[n3++] = 2.0f * ((float)acf2.readByte() / 255.0f) + 1.0f;
        }
        if ((n2 & 2) == 2) {
            assert (n3 == ScreenElement.kx(n2));
            fArray[n3++] = (float)acf2.readByte() / 255.0f + 0.5f;
            fArray[n3++] = (float)acf2.readByte() / 255.0f + 0.5f;
            fArray[n3++] = (float)acf2.readByte() / 255.0f + 0.5f;
        }
        if ((n2 & 4) == 4) {
            assert (n3 == ScreenElement.ky(n2));
            fArray[n3++] = (float)acf2.readByte() / 255.0f;
            fArray[n3++] = (float)acf2.readByte() / 255.0f;
            fArray[n3++] = (float)acf2.readByte() / 255.0f;
        }
        if ((n2 & 8) == 8) {
            assert (n3 == ScreenElement.kz(n2));
            fArray[n3++] = (float)acf2.readByte() / 255.0f + 0.5f;
        }
        if ((n2 & 0x10) == 16) {
            if ((n2 & 2) == 2) {
                assert (n3 == ScreenElement.kA(n2));
                fArray[n3++] = (float)acf2.readByte() / 255.0f + 0.5f;
                fArray[n3++] = (float)acf2.readByte() / 255.0f + 0.5f;
                fArray[n3++] = (float)acf2.readByte() / 255.0f + 0.5f;
            }
            if ((n2 & 8) == 8) {
                assert (n3 == ScreenElement.kB(n2));
                fArray[n3++] = (float)acf2.readByte() / 255.0f + 0.5f;
            }
        }
        assert (n3 == fArray.length);
    }

    static float[] kw(int n2) {
        int n3 = 0;
        n3 += (n2 & 2) == 2 ? 3 : 0;
        n3 += (n2 & 8) == 8 ? 1 : 0;
        n3 *= (n2 & 0x10) == 16 ? 2 : 1;
        n3 += (n2 & 1) == 1 ? 3 : 0;
        return new float[n3 += (n2 & 4) == 4 ? 3 : 0];
    }

    static int kx(int n2) {
        return ctD[n2 & 1];
    }

    static int ky(int n2) {
        return ctD[n2 & 3];
    }

    static int kz(int n2) {
        return ctD[n2 & 7];
    }

    static int kA(int n2) {
        int n3 = n2 & 0xF;
        assert ((n3 & 2) == 2);
        return ctD[n3];
    }

    static int kB(int n2) {
        int n3 = n2 & 0xF;
        assert ((n3 & 8) == 8);
        return ctD[n3 + 16];
    }

    static {
        int n2;
        qL = ScreenElement.L(ScreenElement.class);
        coH = new float[4];
        ctD = new int[32];
        int n3 = 3;
        int n4 = 3;
        int n5 = 3;
        boolean bl2 = true;
        ScreenElement.ctD[0] = 0;
        for (n2 = 1; n2 < 2; ++n2) {
            ScreenElement.ctD[n2] = 3 + ctD[n2 - 1];
        }
        for (n2 = 2; n2 < 4; ++n2) {
            ScreenElement.ctD[n2] = 3 + ctD[n2 - 2];
        }
        for (n2 = 4; n2 < 8; ++n2) {
            ScreenElement.ctD[n2] = 3 + ctD[n2 - 4];
        }
        for (n2 = 8; n2 < 16; ++n2) {
            ScreenElement.ctD[n2] = 1 + ctD[n2 - 8];
        }
        for (n2 = 16; n2 < ctD.length; ++n2) {
            ScreenElement.ctD[n2] = ctD[n2 - 16];
            if ((n2 & 2) != 2) continue;
            int n6 = n2;
            ctD[n6] = ctD[n6] + 3;
        }
    }
}

