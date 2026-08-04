/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from tw
 */
public class tw_1
extends auw_0 {
    public static final tw_1 amY = new tw_1();

    private tw_1() {
    }

    protected byte lV() {
        return 2;
    }

    protected void a(aij_1 aij_12, rd rd2, rd rd3) {
        boolean bl2 = !this.a(rd2, rd3);
        aij_12.fe(bl2);
        aij_12.fe(rd2.afD);
        aij_12.aVj();
        aij_12.writeByte((byte)rd2.afC.ordinal());
        tw_1.a(aij_12, bl2, rd2.r, rd3.r);
    }

    public rd f(acf acf2, float f) {
        boolean bl2 = acf2.aqE();
        boolean bl3 = acf2.aqE();
        byte by = acf2.readByte();
        kp_0 kp_02 = kp_0.values()[by];
        int n2 = tw_1.a(acf2, bl2, f);
        return new rd(n2, kp_02, bl3);
    }

    protected boolean a(rd rd2, rd rd3) {
        return rd2.r == rd3.r;
    }
}

