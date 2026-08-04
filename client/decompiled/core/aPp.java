/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public final class aPp
extends lJ {
    private static final short fn = 1;
    private int bnc;
    private int eph;
    private int r;
    private aim_1 epi = new aim_1();
    private int Ut;
    private int Uu;
    private float[] UC;
    private boolean Uv;
    private boolean Uw;
    private int Ux;
    private boolean Uy;
    private boolean Uz;
    private boolean UA;
    private akw_0[] UD = new akw_0[0];
    private byte epj;
    private int epk;
    private int awv;
    private np_1[] UE = jn_1.bkb;
    private short UF;
    private short UG;
    private short UH;
    private byte UI;
    private byte UB;
    private int UJ;
    private byte UK;
    private int tg;

    public aPp() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUG.getId();
    }

    public byte[] cr() {
        int n2;
        int n3;
        int n4 = 17 + 5 * this.epi.size() + 4 + 1 + 4 + (this.UC != null ? this.UC.length * 4 : 0) + 1 + 1 + 4 + 1 + 1 + 1 + 1 + 1 + 4 + 4 + 2 + 2 + 2 + 1 + 4 + 1 + 4;
        for (n3 = 0; n3 < this.UD.length; ++n3) {
            n4 += this.UD[n3].nj();
        }
        ++n4;
        for (n3 = 0; n3 < this.UE.length; ++n3) {
            n4 += this.UE[n3].nj();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n4);
        byteBuffer.putInt(this.bnc);
        byteBuffer.putInt(this.eph);
        byteBuffer.putInt(this.Ut);
        byteBuffer.putInt(this.r);
        byteBuffer.put((byte)this.epi.size());
        byte[] byArray = this.epi.GF();
        for (int j = 0; j < byArray.length; ++j) {
            byteBuffer.put(byArray[j]);
            byteBuffer.putInt(this.epi.aD(byArray[j]));
        }
        byteBuffer.putInt(this.Uu);
        byteBuffer.put(this.UB);
        if (this.UC != null) {
            byteBuffer.putInt(this.UC.length);
            for (float f : this.UC) {
                byteBuffer.putFloat(f);
            }
        } else {
            byteBuffer.putInt(0);
        }
        byteBuffer.put((byte)(this.Uv ? 1 : 0));
        byteBuffer.put((byte)(this.Uw ? 1 : 0));
        byteBuffer.putInt(this.Ux);
        byteBuffer.put((byte)(this.Uy ? 1 : 0));
        byteBuffer.put((byte)(this.Uz ? 1 : 0));
        byteBuffer.put((byte)(this.UA ? 1 : 0));
        byteBuffer.put((byte)this.UD.length);
        for (n2 = 0; n2 < this.UD.length; ++n2) {
            this.UD[n2].c(byteBuffer);
        }
        byteBuffer.put(this.epj);
        byteBuffer.putInt(this.epk);
        byteBuffer.putInt(this.awv);
        byteBuffer.put((byte)this.UE.length);
        for (n2 = 0; n2 < this.UE.length; ++n2) {
            byteBuffer.put(this.UE[n2].cd());
        }
        byteBuffer.putShort(this.UF);
        byteBuffer.putShort(this.UG);
        byteBuffer.putShort(this.UH);
        byteBuffer.put(this.UI);
        byteBuffer.putInt(this.UJ);
        byteBuffer.put(this.UK);
        byteBuffer.putInt(this.tg);
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            int n3;
            int n4;
            this.bnc = byteBuffer.getInt();
            this.eph = byteBuffer.getInt();
            this.Ut = byteBuffer.getInt();
            this.r = byteBuffer.getInt();
            int n5 = byteBuffer.get();
            for (n4 = 0; n4 < n5; ++n4) {
                this.epi.c(byteBuffer.get(), byteBuffer.getInt());
            }
            this.Uu = byteBuffer.getInt();
            this.UB = byteBuffer.get();
            n4 = byteBuffer.getInt();
            this.UC = new float[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.UC[n3] = byteBuffer.getFloat();
            }
            this.Uv = byteBuffer.get() == 1;
            this.Uw = byteBuffer.get() == 1;
            this.Ux = byteBuffer.getInt();
            this.Uy = byteBuffer.get() == 1;
            this.Uz = byteBuffer.get() == 1;
            this.UA = byteBuffer.get() == 1;
            this.UD = new akw_0[byteBuffer.get()];
            for (n3 = 0; n3 < this.UD.length; ++n3) {
                this.UD[n3] = akw_0.J(byteBuffer);
            }
            this.epj = byteBuffer.get();
            this.epk = byteBuffer.getInt();
            this.awv = byteBuffer.getInt();
            int n6 = byteBuffer.get();
            this.UE = n6 == 0 ? jn_1.bkb : new np_1[n6];
            for (n3 = 0; n3 < n6; ++n3) {
                this.UE[n3] = np_1.j(byteBuffer);
            }
            this.UF = byteBuffer.getShort();
            this.UG = byteBuffer.getShort();
            this.UH = byteBuffer.getShort();
            this.UI = byteBuffer.get();
            this.UJ = byteBuffer.getInt();
            this.UK = byteBuffer.get();
            this.tg = byteBuffer.getInt();
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new aPp();
    }

    public int aZb() {
        return this.bnc;
    }

    public void gy(int n2) {
        this.bnc = n2;
    }

    public int aZc() {
        return this.eph;
    }

    public void pU(int n2) {
        this.eph = n2;
    }

    public int getValue() {
        return this.r;
    }

    public void setValue(int n2) {
        this.r = n2;
    }

    public int tm() {
        return this.Ut;
    }

    public void eA(int n2) {
        this.Ut = n2;
    }

    public int tr() {
        return this.Uu;
    }

    public void pV(int n2) {
        this.Uu = n2;
    }

    public float[] tk() {
        return this.UC;
    }

    public akw_0[] tu() {
        return this.UD;
    }

    public void J(float[] fArray) {
        this.UC = fArray;
    }

    public boolean isUnique() {
        return this.Uv;
    }

    public boolean to() {
        return this.Uw;
    }

    public boolean tq() {
        return this.Uz;
    }

    public boolean tp() {
        return this.Uy;
    }

    public int ts() {
        return this.Ux;
    }

    public boolean tt() {
        return this.UA;
    }

    public void fw(boolean bl2) {
        this.Uv = bl2;
    }

    public void fx(boolean bl2) {
        this.Uw = bl2;
    }

    public void fy(boolean bl2) {
        this.Uy = bl2;
    }

    public void fz(boolean bl2) {
        this.Uz = bl2;
    }

    public void pW(int n2) {
        this.Ux = n2;
    }

    public void fA(boolean bl2) {
        this.UA = bl2;
    }

    public boolean pX(int n2) {
        for (int j = 0; j < this.UD.length; ++j) {
            if (this.UD[j].getId() != n2) continue;
            return true;
        }
        return false;
    }

    public void a(akw_0 akw_02) {
        if (this.UD == null) {
            this.UD = new akw_0[]{akw_02};
        } else {
            akw_0[] akw_0Array = this.UD;
            this.UD = new akw_0[this.UD.length + 1];
            System.arraycopy(akw_0Array, 0, this.UD, 1, akw_0Array.length);
            this.UD[0] = akw_02;
        }
    }

    public byte aZd() {
        return this.epj;
    }

    public void bw(byte by) {
        this.epj = by;
    }

    public int aZe() {
        return this.epk;
    }

    public void pY(int n2) {
        this.epk = n2;
    }

    public int getRank() {
        return this.awv;
    }

    public void pZ(int n2) {
        this.awv = n2;
    }

    public np_1[] tv() {
        return this.UE;
    }

    public void a(np_1 np_12) {
        np_1[] np_1Array = this.UE;
        this.UE = new np_1[this.UE.length + 1];
        System.arraycopy(np_1Array, 0, this.UE, 0, np_1Array.length);
        this.UE[this.UE.length - 1] = np_12;
    }

    public boolean fR(int n2) {
        for (np_1 np_12 : this.UE) {
            if (np_12.getId() != n2) continue;
            return true;
        }
        return false;
    }

    public short tw() {
        return this.UF;
    }

    public void cm(short s) {
        this.UF = s;
    }

    public short tx() {
        return this.UG;
    }

    public void cy(short s) {
        this.UG = s;
    }

    public short tz() {
        return this.UH;
    }

    public void bK(short s) {
        this.UH = s;
    }

    public byte tA() {
        return this.UI;
    }

    public void aF(byte by) {
        this.UI = by;
    }

    public byte tl() {
        return this.UB;
    }

    public void bx(byte by) {
        this.UB = by;
    }

    public int tB() {
        return this.UJ;
    }

    public void qa(int n2) {
        this.UJ = n2;
    }

    public aim_1 aZf() {
        return this.epi;
    }

    public void e(byte by, int n2) {
        this.epi.c(by, n2);
    }

    public byte tD() {
        return this.UK;
    }

    public void by(byte by) {
        this.UK = by;
    }

    public int tE() {
        return this.tg;
    }

    public void qb(int n2) {
        this.tg = n2;
    }
}

