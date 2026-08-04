/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from rU
 */
public class ru_2
extends aov_0 {
    private static final Logger a = Logger.getLogger(ru_2.class);
    private static final int ain = 255;
    private static final int aio = 255;
    private static final int aip = 4;
    private static final byte aiq = 3;
    public static final int air = 255;
    public static final int ais = (int)Math.ceil(81.0);
    public static final int ait = 0;
    public static final int aiu = -1;
    private aEG[] aiv = null;
    private ty_1[] aiw = null;
    private static final ty_1[] aix = new ty_1[0];
    private aDv[] aiy = null;
    private axs_0[] aiz = null;
    int[] aiA;
    byte[] aiB;
    boolean aiC;

    public ru_2() {
    }

    public ru_2(short s, short s2) {
        super(s, s2);
    }

    public byte cc() {
        return 0;
    }

    public aDv[] xP() {
        return this.aiy;
    }

    public axs_0[] xQ() {
        return this.aiz;
    }

    public int[] xR() {
        return this.aiA;
    }

    public int W(int n2, int n3) {
        if (this.aiA == null) {
            return -1;
        }
        if (this.aiB == null) {
            return this.aiA[0];
        }
        n3 -= this.EM * 18;
        assert ((n2 -= this.EL * 18) >= 0 && n2 < 18);
        assert (n3 >= 0 && n3 < 18);
        int n4 = n2 + n3 * 18;
        int n5 = this.aiB[n4 / 4] >>> n4 % 4 * 2;
        return this.aiA[n5 & 3];
    }

    public aEG[] xS() {
        return this.aiv;
    }

    public ty_1[] xT() {
        return this.aiw;
    }

    public void a(axs_0[] axs_0Array) {
        this.aiz = axs_0Array;
    }

    public void a(aDv[] aDvArray) {
        this.aiy = aDvArray;
    }

    public void q(int[] nArray) {
        this.aiA = nArray;
        assert (this.aiA.length <= 4) : "Trop d'ambiance diff\u00e9rentes";
        if (this.aiA.length >= 1) {
            this.aiB = new byte[ais];
        }
    }

    public void m(int n2, int n3, int n4) {
        assert (n2 >= 0 && n2 < 18);
        assert (n3 >= 0 && n3 < 18);
        assert (this.aiB != null) : "Il faut d'abord appeler setAmbiancesId";
        assert (n4 < this.aiA.length);
        int n5 = n2 + n3 * 18;
        int n6 = n5 / 4;
        this.aiB[n6] = (byte)(this.aiB[n6] | n4 << n5 % 4 * 2);
    }

    public void a(aEG[] aEGArray) {
        this.aiv = aEGArray;
    }

    public void a(ty_1[] ty_1Array) {
        this.aiw = ty_1Array;
    }

    public void clear() {
        this.aiy = null;
        this.aiz = null;
        this.aiB = null;
        this.aiA = null;
    }

    public void b(acf acf2) {
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/ClientEnvironmentMap.load must not be null");
        }
        super.b(acf2);
        this.d(acf2);
        this.e(acf2);
        this.f(acf2);
        this.g(acf2);
        this.h(acf2);
    }

    public void a(aij_1 aij_12) {
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/ClientEnvironmentMap.save must not be null");
        }
        super.a(aij_12);
        this.b(aij_12);
        this.c(aij_12);
        this.d(aij_12);
        this.e(aij_12);
        this.f(aij_12);
    }

    private void d(acf acf2) {
        int n2 = acf2.readByte() & 0xFF;
        if (n2 == 0) {
            return;
        }
        this.aiy = new aDv[n2];
        for (int j = 0; j < n2; ++j) {
            aDv aDv2 = new aDv();
            aDv2.b(acf2);
            this.aiy[j] = aDv2;
        }
    }

    private void b(aij_1 aij_12) {
        if (this.aiy == null) {
            aij_12.writeByte((byte)0);
            return;
        }
        int n2 = this.aiy.length;
        if (n2 >= 255) {
            throw new eq_2("trop de particules sur la map (" + this.EL + " " + this.EM + ")");
        }
        aij_12.writeByte((byte)(n2 & 0xFF));
        for (int j = 0; j < n2; ++j) {
            this.aiy[j].a(aij_12);
        }
    }

    private void e(acf acf2) {
        int n2 = acf2.readByte() & 0xFF;
        if (n2 == 0) {
            return;
        }
        this.aiz = new axs_0[n2];
        for (int j = 0; j < n2; ++j) {
            axs_0 axs_02 = new axs_0();
            axs_02.b(acf2);
            this.aiz[j] = axs_02;
        }
    }

    private void c(aij_1 aij_12) {
        if (this.aiz == null) {
            aij_12.writeByte((byte)0);
            return;
        }
        int n2 = this.aiz.length;
        if (n2 >= 255) {
            throw new eq_2("trop de sons sur la map (" + this.EL + " " + this.EM + ")");
        }
        aij_12.writeByte((byte)(n2 & 0xFF));
        for (int j = 0; j < n2; ++j) {
            this.aiz[j].a(aij_12);
        }
    }

    private void f(acf acf2) {
        int n2;
        int n3 = acf2.readByte() & 0xFF;
        if (n3 == 0) {
            this.aiB = null;
            this.aiA = null;
            return;
        }
        this.aiA = new int[n3];
        for (n2 = 0; n2 < this.aiA.length; ++n2) {
            this.aiA[n2] = acf2.readInt();
        }
        n2 = acf2.readByte() & 0xFF;
        if (n2 == 0) {
            assert (this.aiA.length == 1);
            this.aiB = null;
            return;
        }
        assert (n2 == ais);
        this.aiB = acf2.jE(n2);
    }

    private void d(aij_1 aij_12) {
        if (this.aiA == null) {
            assert (this.aiB == null);
            aij_12.writeByte((byte)0);
            return;
        }
        int n2 = this.aiA.length;
        assert (this.aiA.length < 4);
        aij_12.writeByte((byte)(n2 & 0xFF));
        for (int j = 0; j < n2; ++j) {
            aij_12.writeInt(this.aiA[j]);
        }
        if (this.aiB == null) {
            assert (this.aiA.length == 1);
            aij_12.writeByte((byte)0);
            return;
        }
        assert (this.aiB.length == ais);
        aij_12.writeByte((byte)(this.aiB.length & 0xFF));
        aij_12.writeBytes(this.aiB);
    }

    protected void g(acf acf2) {
        int n2 = acf2.readByte() & 0xFF;
        if (n2 == 0) {
            return;
        }
        this.aiv = new aEG[n2];
        for (int j = 0; j < n2; ++j) {
            aEG aEG2 = new aEG();
            aEG2.b(acf2);
            this.aiv[j] = aEG2;
        }
    }

    private void e(aij_1 aij_12) {
        if (this.aiv == null) {
            aij_12.writeByte((byte)0);
            return;
        }
        int n2 = this.aiv.length;
        if (n2 >= 255) {
            throw new eq_2("trop de interactivs sur la map (" + this.EL + " " + this.EM + ")");
        }
        aij_12.writeByte((byte)(n2 & 0xFF));
        for (int j = 0; j < n2; ++j) {
            this.aiv[j].a(aij_12);
        }
    }

    protected void h(acf acf2) {
        int n2 = acf2.readByte() & 0xFF;
        if (n2 == 0) {
            this.aiw = aix;
            return;
        }
        this.aiw = new ty_1[n2];
        for (int j = 0; j < n2; ++j) {
            ty_1 ty_12 = new ty_1();
            ty_12.b(acf2);
            this.aiw[j] = ty_12;
        }
    }

    private void f(aij_1 aij_12) {
        if (this.aiw == null) {
            aij_12.writeByte((byte)0);
            return;
        }
        int n2 = this.aiw.length;
        if (n2 >= 255) {
            throw new eq_2("trop de dynamic sur la map (" + this.EL + " " + this.EM + ")");
        }
        aij_12.writeByte((byte)(n2 & 0xFF));
        for (int j = 0; j < n2; ++j) {
            this.aiw[j].a(aij_12);
        }
    }
}

