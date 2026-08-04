/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.Iterator;

/*
 * Renamed from alh
 */
public class alh_1 {
    long aj;
    short NC;
    final zm_1 cEV = new zm_1();
    static Iterator cEW = new ni_1();
    aeg_2 cEX;

    alh_1() {
    }

    long K() {
        return this.aj;
    }

    void j(long l2) {
        this.aj = l2;
    }

    public void aAw() {
        this.cEV.ao(this.NC);
        this.NC = (short)(this.NC + 1);
    }

    void aAx() {
        if (this.cEX != null) {
            this.cEX.stop();
        }
    }

    public short aAy() {
        return this.NC;
    }

    public void a(atD atD2, short s, boolean bl2) {
        this.bL(s);
        ((lf)this.cEV.an(s)).a(atD2, bl2);
    }

    private void bL(short s) {
        if (!this.cEV.ap(s)) {
            this.cEV.b(s, new lf());
        }
    }

    private boolean aAz() {
        return !this.cEV.ap(this.NC);
    }

    Iterator dr() {
        if (this.aAz()) {
            return cEW;
        }
        this.cEX = new aeg_2(this, ((lf)this.cEV.an(this.NC)).pT());
        return this.cEX;
    }

    Iterator ds() {
        if (this.aAz()) {
            return cEW;
        }
        this.cEX = new aeg_2(this, ((lf)this.cEV.an(this.NC)).pS());
        return this.cEX;
    }

    public ra_2 aAA() {
        return new nc_1(this);
    }

    public void aAB() {
        Object[] objectArray = new lf[this.cEV.size()];
        this.cEV.a(objectArray);
        for (int j = 0; j < objectArray.length; ++j) {
            Object object = objectArray[j];
            ((lf)object).clear();
        }
    }

    public int w() {
        int n2 = 4;
        for (short s : this.cEV.Gj()) {
            n2 += 2 + ((lf)this.cEV.an(s)).w();
        }
        return n2;
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putShort(this.NC);
        byteBuffer.putShort((short)this.cEV.size());
        for (short s : this.cEV.Gj()) {
            byteBuffer.putShort(s);
            ((lf)this.cEV.an(s)).c(byteBuffer);
        }
    }

    protected void a(ahh_0 ahh_02, ByteBuffer byteBuffer) {
        this.NC = byteBuffer.getShort();
        int n2 = byteBuffer.getShort();
        for (int j = 0; j < n2; ++j) {
            short s = byteBuffer.getShort();
            lf lf2 = lf.b(ahh_02, byteBuffer);
            this.cEV.b(s, lf2);
        }
    }

    public static alh_1 d(ahh_0 ahh_02, ByteBuffer byteBuffer) {
        alh_1 alh_12 = new alh_1();
        alh_12.a(ahh_02, byteBuffer);
        return alh_12;
    }

    public alh_1(long l2, short s) {
        this();
        this.NC = s;
        this.aj = l2;
    }
}

