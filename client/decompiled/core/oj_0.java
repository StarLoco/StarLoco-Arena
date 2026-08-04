/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from oj
 */
public abstract class oj_0 {
    protected final int aW;
    protected final int r;
    protected final aim_1 Ur;
    protected final aMK Us;
    protected final int Ut;
    protected final int Uu;
    protected final boolean Uv;
    protected final boolean Uw;
    protected final int Ux;
    protected final boolean Uy;
    protected final boolean Uz;
    protected final boolean UA;
    protected final byte UB;
    protected final float[] UC;
    protected final akw_0[] UD;
    protected final np_1[] UE;
    protected final short UF;
    protected final short UG;
    protected final short UH;
    protected final byte UI;
    protected final int UJ;
    protected final byte UK;
    protected final int tg;

    protected oj_0(int n2, aMK aMK2, int n3, int n4, int n5, boolean bl2, boolean bl3, int n6, boolean bl4, boolean bl5, boolean bl6, akw_0[] akw_0Array, byte by, float[] fArray, np_1[] np_1Array, short s, short s2, short s3, byte by2, int n7, aim_1 aim_12, byte by3, int n8) {
        this.aW = n2;
        this.Us = aMK2;
        this.r = n4;
        this.Ut = n3;
        this.Uu = n5;
        this.Uv = bl2;
        this.Uw = bl3;
        this.Ux = n6;
        this.Uy = bl4;
        this.Uz = bl5;
        this.UA = bl6;
        this.UD = akw_0Array;
        this.UB = by;
        this.UC = fArray;
        this.UE = np_1Array;
        this.UF = s;
        this.UG = s2;
        this.UH = s3;
        this.UI = by2;
        this.UJ = n7;
        this.Ur = aim_12;
        this.UK = by3;
        this.tg = n8;
    }

    public int getId() {
        return this.aW;
    }

    public void release() {
    }

    public int jf() {
        return this.aW;
    }

    public byte[] cd() {
        byte[] byArray = new byte[4];
        ByteBuffer.wrap(byArray).putInt(this.aW);
        return byArray;
    }

    public boolean b(ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException("AbstractReferenceCoachCard can't be unserialized. Need to be get from AbstractReferenceCoachCardManager");
    }

    public aMK tj() {
        return this.Us;
    }

    public float[] tk() {
        return this.UC;
    }

    public byte tl() {
        return this.UB;
    }

    public int tm() {
        return this.Ut;
    }

    public aqy_0 tn() {
        return ayc_0.aLE().mS(this.Ut);
    }

    public int getValue() {
        return this.r;
    }

    public boolean isUnique() {
        return this.Uv;
    }

    public boolean to() {
        return this.Uw;
    }

    public boolean tp() {
        return this.Uy;
    }

    public boolean tq() {
        return this.Uz;
    }

    public int tr() {
        return this.Uu;
    }

    public int ts() {
        return this.Ux;
    }

    public boolean tt() {
        return this.UA;
    }

    public akw_0[] tu() {
        return this.UD;
    }

    public np_1[] tv() {
        return this.UE;
    }

    public short tw() {
        return this.UF;
    }

    public short tx() {
        return this.UG;
    }

    public boolean ty() {
        return this.tj() == aMK.dYB;
    }

    public short tz() {
        return this.UH;
    }

    public byte tA() {
        return this.UI;
    }

    public int tB() {
        return this.UJ;
    }

    public aim_1 tC() {
        return this.Ur;
    }

    public byte tD() {
        return this.UK;
    }

    public int tE() {
        return this.tg;
    }
}

