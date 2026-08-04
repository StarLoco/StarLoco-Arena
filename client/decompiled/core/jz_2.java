/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from jZ
 */
public final class jz_2
extends lJ {
    private static final short fn = 1;
    private int aW;
    private int Dn;
    private int Do;
    private int Dp;
    private jg_0 Dq = new jg_0();
    private int Dr;
    private boolean Ds;
    private boolean Dt;
    private boolean Du;
    private boolean Dv;
    private boolean Dw;
    private boolean Dx;
    private int Dy;
    private int Dz;
    private byte DA;
    private int DB;
    private ArrayList DC = new ArrayList();

    public jz_2() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUR.getId();
    }

    public byte[] cr() {
        int n2 = 0;
        int n3 = this.DC.size();
        for (int j = 0; j < n3; ++j) {
            n2 += ((byte[])this.DC.get(j)).length + 4;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(17 + 4 * this.Dq.size() + 4 + 1 + 1 + 1 + 1 + 1 + 1 + 4 + 4 + 1 + 1 + n2 + 4);
        byteBuffer.putInt(this.aW);
        byteBuffer.putInt(this.Dn);
        byteBuffer.putInt(this.Do);
        byteBuffer.putInt(this.Dp);
        byteBuffer.put((byte)this.Dq.size());
        int n4 = this.Dq.size();
        for (n3 = 0; n3 < n4; ++n3) {
            byteBuffer.putInt(this.Dq.bu(n3));
        }
        byteBuffer.putInt(this.Dr);
        byteBuffer.put(this.Ds ? (byte)1 : 0);
        byteBuffer.put(this.Dt ? (byte)1 : 0);
        byteBuffer.put(this.Du ? (byte)1 : 0);
        byteBuffer.put(this.Dv ? (byte)1 : 0);
        byteBuffer.put(this.Dw ? (byte)1 : 0);
        byteBuffer.put(this.Dx ? (byte)1 : 0);
        byteBuffer.putInt(this.Dy);
        byteBuffer.putInt(this.Dz);
        byteBuffer.put(this.DA);
        byteBuffer.put((byte)this.DC.size());
        n4 = this.DC.size();
        for (n3 = 0; n3 < n4; ++n3) {
            byteBuffer.putInt(((byte[])this.DC.get(n3)).length);
            byteBuffer.put((byte[])this.DC.get(n3));
        }
        byteBuffer.putInt(this.DB);
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            int n3;
            this.aW = byteBuffer.getInt();
            this.Dn = byteBuffer.getInt();
            this.Do = byteBuffer.getInt();
            this.Dp = byteBuffer.getInt();
            int n4 = byteBuffer.get();
            for (n3 = 0; n3 < n4; ++n3) {
                this.Dq.add(byteBuffer.getInt());
            }
            this.Dr = byteBuffer.getInt();
            this.Ds = byteBuffer.get() == 1;
            this.Dt = byteBuffer.get() == 1;
            this.Du = byteBuffer.get() == 1;
            this.Dv = byteBuffer.get() == 1;
            this.Dw = byteBuffer.get() == 1;
            this.Dx = byteBuffer.get() == 1;
            this.Dy = byteBuffer.getInt();
            this.Dz = byteBuffer.getInt();
            this.DA = byteBuffer.get();
            n3 = byteBuffer.get();
            for (int j = 0; j < n3; ++j) {
                byte[] byArray = new byte[byteBuffer.getInt()];
                byteBuffer.get(byArray);
                this.DC.add(byArray);
            }
            this.DB = byteBuffer.getInt();
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new jz_2();
    }

    public int getId() {
        return this.aW;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public int ok() {
        return this.Dn;
    }

    public void by(int n2) {
        this.Dn = n2;
    }

    public int ol() {
        return this.Do;
    }

    public void bz(int n2) {
        this.Do = n2;
    }

    public int om() {
        return this.Dp;
    }

    public void bA(int n2) {
        this.Dp = n2;
    }

    public void y(int n2) {
        if (!this.Dq.contains(n2)) {
            this.Dq.add(n2);
        }
    }

    public jg_0 on() {
        return this.Dq;
    }

    public int oo() {
        return this.Dr;
    }

    public void bB(int n2) {
        this.Dr = n2;
    }

    public boolean op() {
        return this.Ds;
    }

    public void Q(boolean bl2) {
        this.Ds = bl2;
    }

    public boolean oq() {
        return this.Dt;
    }

    public void R(boolean bl2) {
        this.Dt = bl2;
    }

    public boolean or() {
        return this.Du;
    }

    public void S(boolean bl2) {
        this.Du = bl2;
    }

    public boolean os() {
        return this.Dv;
    }

    public void T(boolean bl2) {
        this.Dv = bl2;
    }

    public int ot() {
        return this.Dy;
    }

    public void bC(int n2) {
        this.Dy = n2;
    }

    public int ou() {
        return this.Dz;
    }

    public void bD(int n2) {
        this.Dz = n2;
    }

    public boolean ov() {
        return this.Dw;
    }

    public void U(boolean bl2) {
        this.Dw = bl2;
    }

    public void h(byte[] byArray) {
        this.DC.add(byArray);
    }

    public ArrayList ow() {
        return this.DC;
    }

    public byte ox() {
        return this.DA;
    }

    public void o(byte by) {
        this.DA = by;
    }

    public boolean oy() {
        return this.Dx;
    }

    public void V(boolean bl2) {
        this.Dx = bl2;
    }

    public int oz() {
        return this.DB;
    }

    public void bE(int n2) {
        this.DB = n2;
    }
}

