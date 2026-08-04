/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ti
 */
public class ti_1
implements ma_1 {
    private byte ey;
    private short aGn;
    private short aGo;
    private short aGp;
    private short aGq;
    private short aGr;
    private short bMW;
    private short aGs = 0;
    private short aGt = 0;
    private String m_name;
    private ef_1 tl;
    private zm_1 aGu;

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

    private void i(acf acf2) {
        byte by = acf2.readByte();
        int n2 = acf2.readInt();
        int n3 = acf2.getOffset() + n2;
        acf2.setOffset(n3);
    }

    private void j(acf acf2) {
        byte by = acf2.readByte();
        int n2 = acf2.readInt();
        int n3 = acf2.getOffset() + n2;
        this.aGr = acf2.readShort();
        this.bMW = acf2.readShort();
        acf2.setOffset(n3);
    }

    private void k(acf acf2) {
        byte by = acf2.readByte();
        int n2 = acf2.readInt();
        int n3 = acf2.getOffset() + n2;
        acf2.setOffset(n3);
    }

    private void l(acf acf2) {
        byte by = acf2.readByte();
        int n2 = acf2.readInt();
        this.aGn = (short)(n2 / 20);
        this.aGu = new zm_1(this.aGn);
        this.aGp = Short.MAX_VALUE;
        this.aGq = Short.MIN_VALUE;
        for (int j = 0; j < this.aGn; ++j) {
            qj_0 qj_02 = new qj_0(this);
            char[] cArray = Character.toChars(acf2.readInt());
            qj_02.adD = (short)cArray[0];
            qj_02.EL = acf2.readShort();
            qj_02.EM = acf2.readShort();
            qj_02.adE = acf2.readShort();
            qj_02.adF = acf2.readShort();
            qj_02.adG = acf2.readShort();
            qj_02.adH = acf2.readShort();
            qj_02.adI = acf2.readShort();
            acf2.readByte();
            acf2.readByte();
            this.aGu.b(qj_02.adD, qj_02);
            this.aGp = (short)Math.min(this.aGp, qj_02.adD);
            this.aGq = (short)Math.max(this.aGp, qj_02.adD);
            this.aGs = (short)Math.max(this.aGs, qj_02.adE);
            this.aGt = (short)Math.max(this.aGt, qj_02.adF);
        }
    }

    private void m(acf acf2) {
        if (acf2.available() <= 0) {
            return;
        }
        byte by = acf2.readByte();
        int n2 = acf2.readInt();
        int n3 = n2 / 10;
        for (int j = 0; j < n3; ++j) {
            char[] cArray = Character.toChars(acf2.readInt());
            char[] cArray2 = Character.toChars(acf2.readInt());
            short s = acf2.readShort();
            qj_0 qj_02 = (qj_0)this.aGu.an((short)cArray[0]);
            if (qj_02 == null) continue;
            if (qj_02.adJ == null) {
                qj_02.adJ = new aGz();
            }
            qj_02.adJ.A((short)cArray2[0], s);
        }
    }

    public void l(String string, String string2) {
        this.m_name = string;
        acf acf2 = acf.T(vq_2.readFile(string2 + string + ".fnt"));
        if (acf2.readByte() != 66 || acf2.readByte() != 77 || acf2.readByte() != 70) {
            return;
        }
        this.ey = acf2.readByte();
        this.i(acf2);
        this.j(acf2);
        this.k(acf2);
        this.l(acf2);
        this.m(acf2);
        acf2.close();
        long l2 = -6196766170285080576L + ej_0.aa(string);
        String string3 = string2 + string + "_0.dds";
        if (!an_2.o(string3)) {
            string3 = string2 + string + "_0.DDS";
        }
        this.tl = cx_0.JY().a(arX.cQT.iE(), l2, string3, false);
        this.tl.HE();
    }

    public final String getName() {
        return this.m_name;
    }

    public final qj_0 bl(short s) {
        if (s < this.aGp || s > (this.aGq & 0xFFFF)) {
            return null;
        }
        return (qj_0)this.aGu.an(s);
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
        return 0;
    }

    public short afS() {
        return this.bMW;
    }

    public short afT() {
        return (short)(this.aGr - this.bMW);
    }
}

