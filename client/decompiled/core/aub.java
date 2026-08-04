/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public final class aub
extends lJ {
    private static final short fn = 1;
    private short ash;
    private byte asn;
    private byte cVG;
    private int cVH;
    private int cVI;
    private np_1[] UE = jn_1.bkb;
    private short fA;
    private byte cVJ;
    private int Hp;
    private int cVK;
    private boolean cVL;
    private aim_1[] cVM = new aim_1[5];

    public aub() {
        super((short)1);
        for (int j = 0; j < this.cVM.length; ++j) {
            this.cVM[j] = new aim_1();
        }
    }

    public int cq() {
        return atr_0.cVd.getId();
    }

    public byte[] cr() {
        int n2 = 13;
        ++n2;
        for (np_1 np_12 : this.UE) {
            n2 += np_12.nj();
        }
        n2 += 10;
        ++n2;
        for (int j = 0; j < this.cVM.length; ++j) {
            n2 += 1 + this.cVM[j].size() * 5;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putShort(this.ash);
        byteBuffer.put(this.asn);
        byteBuffer.put(this.cVG);
        byteBuffer.put(this.cVJ);
        byteBuffer.putInt(this.cVH);
        byteBuffer.putInt(this.cVI);
        byteBuffer.put((byte)this.UE.length);
        for (np_1 np_12 : this.UE) {
            byteBuffer.put(np_12.cd());
        }
        byteBuffer.putShort(this.fA);
        byteBuffer.putInt(this.Hp);
        byteBuffer.putInt(this.cVK);
        byteBuffer.put((byte)(this.cVL ? 1 : 0));
        for (int j = 0; j < this.cVM.length; ++j) {
            aim_1 aim_12 = this.cVM[j];
            byte[] byArray = aim_12.GF();
            byteBuffer.put((byte)byArray.length);
            for (int i2 = 0; i2 < aim_12.size(); ++i2) {
                byteBuffer.put(byArray[i2]);
                byteBuffer.putInt(aim_12.aD(byArray[i2]));
            }
        }
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            int n3;
            this.ash = byteBuffer.getShort();
            this.asn = byteBuffer.get();
            this.cVG = byteBuffer.get();
            this.cVJ = byteBuffer.get();
            this.cVH = byteBuffer.getInt();
            this.cVI = byteBuffer.getInt();
            byte by = byteBuffer.get();
            if (by == 0) {
                this.UE = jn_1.bkb;
            } else {
                this.UE = new np_1[by];
                for (n3 = 0; n3 < this.UE.length; ++n3) {
                    this.UE[n3] = np_1.j(byteBuffer);
                }
            }
            this.fA = byteBuffer.getShort();
            this.Hp = byteBuffer.getInt();
            this.cVK = byteBuffer.getInt();
            this.cVL = byteBuffer.get() != 0;
            for (n3 = 0; n3 < this.cVM.length; ++n3) {
                aim_1 aim_12 = this.cVM[n3];
                int n4 = byteBuffer.get();
                for (int j = 0; j < n4; ++j) {
                    aim_12.c(byteBuffer.get(), byteBuffer.getInt());
                }
            }
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new aub();
    }

    public short Bw() {
        return this.ash;
    }

    public byte BE() {
        return this.asn;
    }

    public byte aHe() {
        return this.cVG;
    }

    public int aHf() {
        return this.cVH;
    }

    public int aHg() {
        return this.cVI;
    }

    public np_1[] tv() {
        return this.UE;
    }

    public short cB() {
        return this.fA;
    }

    public byte aHh() {
        return this.cVJ;
    }

    public int qo() {
        return this.Hp;
    }

    public int aHi() {
        return this.cVK;
    }

    public boolean aHj() {
        return this.cVL;
    }

    public aim_1[] aHk() {
        return this.cVM;
    }

    public void ca(short s) {
        this.ash = s;
    }

    public void aU(byte by) {
        this.asn = by;
    }

    public void aV(byte by) {
        this.cVG = by;
    }

    public void mn(int n2) {
        this.cVH = n2;
    }

    public void mo(int n2) {
        this.cVI = n2;
    }

    public void M(short s) {
        this.fA = s;
    }

    public void aW(byte by) {
        this.cVJ = by;
    }

    public void kV(int n2) {
        this.Hp = n2;
    }

    public void mp(int n2) {
        this.cVK = n2;
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

    public void eg(boolean bl2) {
        this.cVL = bl2;
    }

    public void b(int n2, byte by, int n3) {
        this.cVM[n2].c(by, n3);
    }
}

