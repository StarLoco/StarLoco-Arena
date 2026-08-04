/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.util.texture.TextureCoords;

/*
 * Renamed from amL
 */
public class aml_0
extends afd_0 {
    private final short cdV;
    private final short cdW;
    private final short cHV;
    private final short cHW;
    private final short[] cHX;
    static final /* synthetic */ boolean bb;

    public static aml_0 a(short s, short s2, short[] sArray, short[] sArray2) {
        short s3;
        if (sArray.length <= 1 || sArray2.length <= 2) {
            throw new eq_2("pas une animation");
        }
        if (sArray.length * 2 != sArray2.length) {
            throw new eq_2("valeurs incorrectes");
        }
        int n2 = 0;
        for (int j = 0; j < sArray.length; ++j) {
            n2 += sArray[j];
        }
        an_0 an_02 = new an_0();
        an_0 an_03 = new an_0();
        for (s3 = 0; s3 < sArray2.length; s3 += 2) {
            an_02.a(sArray2[s3]);
            an_03.a(sArray2[s3 + 1]);
        }
        s3 = (short)(s / an_02.size());
        short s4 = (short)(s2 / an_03.size());
        return new aml_0(n2, s3, s4, s, s2, sArray, sArray2);
    }

    public static aml_0 b(short s, short s2, short[] sArray, short[] sArray2) {
        short s3;
        if (sArray.length <= 1 || sArray2.length <= 2) {
            throw new eq_2("pas une animation");
        }
        if (sArray.length * 2 != sArray2.length) {
            throw new eq_2("valeurs incorrectes");
        }
        int n2 = 0;
        for (int j = 0; j < sArray.length; ++j) {
            n2 += sArray[j];
        }
        an_0 an_02 = new an_0();
        an_0 an_03 = new an_0();
        for (s3 = 0; s3 < sArray2.length; s3 += 2) {
            an_02.a(sArray2[s3]);
            an_03.a(sArray2[s3 + 1]);
        }
        s3 = (short)(s * an_02.size());
        short s4 = (short)(s2 * an_03.size());
        return new aml_0(n2, s, s2, s3, s4, sArray, sArray2);
    }

    public static aml_0 a(aml_0 aml_02, short s, short s2) {
        return new aml_0(aml_02.bKz, aml_02.cdV, aml_02.cdW, s, s2, aml_02.crA, aml_02.cHX);
    }

    private aml_0(int n2, short s, short s2, short s3, short s4, short[] sArray, short[] sArray2) {
        super(n2, sArray);
        this.cdV = s;
        this.cdW = s2;
        this.cHV = s3;
        this.cHW = s4;
        this.cHX = sArray2;
    }

    public short[] aBQ() {
        return this.cHX;
    }

    public int aev() {
        return this.bKz;
    }

    public short[] aBR() {
        return this.crA;
    }

    public void h(aij_1 aij_12) {
        if (this.crA == null || this.crA.length <= 1) {
            aij_12.writeByte((byte)0);
        } else {
            int n2;
            int n3 = this.crA.length;
            aij_12.writeByte((byte)n3);
            aij_12.writeInt(this.bKz);
            aij_12.writeShort(this.cdV);
            aij_12.writeShort(this.cdW);
            aij_12.writeShort(this.cHV);
            aij_12.writeShort(this.cHW);
            for (n2 = 0; n2 < n3; ++n2) {
                aij_12.writeShort(this.crA[n2]);
            }
            if (!bb && this.cHX.length != 2 * n3) {
                throw new AssertionError();
            }
            for (n2 = 0; n2 < this.cHX.length; ++n2) {
                aij_12.writeShort(this.cHX[n2]);
            }
        }
    }

    public static aml_0 q(acf acf2) {
        return (aml_0)afd_0.b(true, acf2, true);
    }

    public TextureCoords bn(short s) {
        throw new UnsupportedOperationException("utiliser la classe AnimData.Use");
    }

    public int aoi() {
        return this.cdV;
    }

    public int aoj() {
        return this.cdW;
    }

    public short aBS() {
        return this.cHV;
    }

    public short aBT() {
        return this.cHW;
    }

    static /* synthetic */ short[] b(aml_0 aml_02) {
        return aml_02.cHX;
    }

    static /* synthetic */ short c(aml_0 aml_02) {
        return aml_02.cdV;
    }

    static /* synthetic */ short d(aml_0 aml_02) {
        return aml_02.cdW;
    }

    static /* synthetic */ short e(aml_0 aml_02) {
        return aml_02.cHV;
    }

    static /* synthetic */ short f(aml_0 aml_02) {
        return aml_02.cHW;
    }

    /* synthetic */ aml_0(int n2, short s, short s2, short s3, short s4, short[] sArray, short[] sArray2, gr_1 gr_12) {
        this(n2, s, s2, s3, s4, sArray, sArray2);
    }

    static {
        bb = !afd_0.class.desiredAssertionStatus();
    }
}

