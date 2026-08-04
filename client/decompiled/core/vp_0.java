/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.util.texture.TextureCoords;
import java.nio.ByteBuffer;

/*
 * Renamed from Vp
 */
public class vp_0
extends afd_0 {
    protected final TextureCoords[] bSx;

    public vp_0(int n2, short[] sArray, TextureCoords[] textureCoordsArray) {
        super(n2, sArray);
        this.bSx = textureCoordsArray;
    }

    public static vp_0 a(aml_0 aml_02) {
        TextureCoords[] textureCoordsArray = vp_0.a(aml_0.b(aml_02), aml_0.c(aml_02), aml_0.d(aml_02), aml_0.e(aml_02), aml_0.f(aml_02), false);
        return new vp_0(aml_02.bKz, aml_02.crA, textureCoordsArray);
    }

    public static vp_0 a(ByteBuffer byteBuffer, boolean bl2) {
        return vp_0.a(acf.H(byteBuffer), bl2);
    }

    public static vp_0 a(acf acf2, boolean bl2) {
        return (vp_0)afd_0.b(false, acf2, bl2);
    }

    public void h(aij_1 aij_12) {
        throw new UnsupportedOperationException("utiliser la classe AnimData.Export");
    }

    public TextureCoords bn(short s) {
        int n2 = (s & 0xFFFF) % this.bKz;
        for (int j = 0; j < this.crA.length; ++j) {
            if ((n2 -= this.crA[j]) >= 0) continue;
            return this.bSx[j];
        }
        return this.bSx[0];
    }
}

