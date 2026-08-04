/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.util.texture.TextureCoords;

/*
 * Renamed from afD
 */
public abstract class afd_0 {
    protected final int bKz;
    protected final short[] crA;

    public afd_0(int n2, short[] sArray) {
        if (n2 == 0) {
            throw new eq_2("animation sans frame");
        }
        this.bKz = n2;
        this.crA = sArray;
    }

    public abstract void h(aij_1 var1);

    public abstract TextureCoords bn(short var1);

    public int aev() {
        return this.bKz;
    }

    public String toString() {
        return "dur\u00e9e: " + this.bKz + "ms " + " images: " + this.crA.length;
    }

    public static TextureCoords[] a(short[] sArray, short s, short s2, int n2, int n3, boolean bl2) {
        assert (n2 > 0);
        assert (n3 > 0);
        float f = ej_0.aq(n2);
        float f2 = (float)ej_0.aq(n3) - 0.5f;
        float f3 = (float)s / f;
        float f4 = (float)s2 / f2;
        TextureCoords[] textureCoordsArray = new TextureCoords[sArray.length / 2];
        for (int j = 0; j < textureCoordsArray.length; ++j) {
            float f5 = ((float)sArray[j * 2] + 0.5f) / f;
            float f6 = ((float)sArray[j * 2 + 1] + 0.5f) / f2;
            textureCoordsArray[j] = bl2 ? new TextureCoords(f3 + f5, f4 + f6, f5, f6) : new TextureCoords(f5, f4 + f6, f3 + f5, f6);
        }
        return textureCoordsArray;
    }

    private static afd_0 a(boolean bl2, acf acf2, boolean bl3) {
        int n2 = acf2.readByte() & 0xFF;
        if (n2 == 0) {
            return null;
        }
        int n3 = acf2.readInt();
        short s = acf2.readShort();
        short s2 = acf2.readShort();
        short s3 = acf2.readShort();
        short s4 = acf2.readShort();
        short[] sArray = new short[n2];
        for (int j = 0; j < sArray.length; ++j) {
            sArray[j] = acf2.readShort();
        }
        short[] sArray2 = new short[n2 * 2];
        for (int j = 0; j < sArray2.length; ++j) {
            sArray2[j] = acf2.readShort();
        }
        if (bl2) {
            return new aml_0(n3, s, s2, s3, s4, sArray, sArray2, null);
        }
        TextureCoords[] textureCoordsArray = afd_0.a(sArray2, s, s2, s3, s4, bl3);
        return new vp_0(n3, sArray, textureCoordsArray);
    }

    static /* synthetic */ afd_0 b(boolean bl2, acf acf2, boolean bl3) {
        return afd_0.a(bl2, acf2, bl3);
    }
}

