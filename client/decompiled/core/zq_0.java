/*
 * Decompiled with CFR 0.152.
 */
import java.util.zip.CRC32;

/*
 * Renamed from zQ
 */
public class zq_0
implements ma_1 {
    private short aGm;
    private short aGn;
    private short aGo;
    private short aGp;
    private short aGq;
    private short aGr;
    private short aGs = 0;
    private short aGt = 0;
    private String m_name;
    private ef_1 tl;
    private zm_1 aGu;
    private static final CRC32 qM = new CRC32();

    public ma_1 b(int n2, float f) {
        String string = abw_1.getType(this.m_name);
        return abw_1.e(string, n2, (int)f);
    }

    public float getSize() {
        return abw_1.kj(this.m_name);
    }

    public int getStyle() {
        return abw_1.ki(this.m_name);
    }

    public void l(String string, String string2) {
        this.m_name = string;
        acf acf2 = acf.T(vq_2.readFile(string2 + string + ".tab"));
        boolean bl2 = string.contains("bordered");
        acf2.setOffset(10);
        if (bl2) {
            this.aGm = (short)2;
        }
        this.aGn = acf2.readShort();
        this.aGo = acf2.readShort();
        this.aGp = acf2.readShort();
        this.aGq = acf2.readShort();
        this.aGr = (short)(acf2.readShort() + this.aGm);
        this.aGu = new zm_1(this.aGn);
        for (int j = 0; j < this.aGn; ++j) {
            tx_2 tx_22 = new tx_2(this);
            tx_22.adD = acf2.readShort();
            acf2.readShort();
            tx_22.EL = (short)(acf2.readShort() - this.aGm);
            tx_22.EM = (short)(acf2.readShort() - this.aGm);
            tx_22.adE = (short)(acf2.readShort() + 2 * this.aGm);
            tx_22.adF = (short)(acf2.readShort() + 2 * this.aGm);
            tx_22.aom = acf2.readShort();
            tx_22.aon = acf2.readShort();
            this.aGu.b(tx_22.adD, tx_22);
            if (tx_22.adE > this.aGs) {
                this.aGs = tx_22.adE;
            }
            if (tx_22.adF <= this.aGt) continue;
            this.aGt = tx_22.adF;
        }
        acf2.close();
        qM.reset();
        qM.update(string.getBytes());
        long l2 = -6196766170285080576L + qM.getValue();
        String string3 = string2 + string + "000.DDS";
        if (!an_2.o(string3)) {
            string3 = string2 + string + "000.tga";
        }
        this.tl = cx_0.JY().a(arX.cQT.iE(), l2, string3, false);
        this.tl.HE();
    }

    public final String getName() {
        return this.m_name;
    }

    public final tx_2 ar(short s) {
        if (s < this.aGp || s > (this.aGq & 0xFFFF)) {
            return null;
        }
        return (tx_2)this.aGu.an(s);
    }

    public final ef_1 jI() {
        return this.tl;
    }

    public final int getCellHeight() {
        return this.aGr;
    }

    public short GT() {
        return this.aGs;
    }

    public short GU() {
        return this.aGt;
    }

    public short qL() {
        return this.aGm;
    }
}

