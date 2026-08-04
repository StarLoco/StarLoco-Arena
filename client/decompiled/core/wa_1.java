/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.apache.log4j.Logger;

/*
 * Renamed from WA
 */
public class wa_1 {
    protected static Logger a = Logger.getLogger(wa_1.class);
    private static final int bUz = 9;
    private static final boolean bUA = false;
    public static final int bUB = 128;
    private static final byte[] bUC = null;
    private static final Deflater bUD = new Deflater(9, false);
    private static final Inflater bUE = new Inflater();
    private static byte[] bUF = new byte[128];

    private static void iI(int n2) {
        if (bUF.length < n2) {
            bUF = new byte[(int)StrictMath.pow(2.0, StrictMath.ceil(StrictMath.log(n2) / StrictMath.log(2.0)))];
        }
    }

    private static byte[] N(byte[] byArray) {
        byte[] byArray2 = bUC;
        if (byArray == bUC) {
            a.error((Object)"Impossible de compresser une donn\u00e9e : InflatedByteArray \u00e9gal \u00e0 null.");
        } else if (byArray.length == 0) {
            a.error((Object)"Impossible de compresser une donn\u00e9e : InflatedByteArray de longueur \u00e9gale \u00e0 z\u00e9ro.");
        } else {
            bUD.reset();
            bUD.setInput(byArray);
            bUD.finish();
            try {
                wa_1.iI(byArray.length);
                byArray2 = new byte[bUD.deflate(bUF)];
                System.arraycopy(bUF, 0, byArray2, 0, byArray2.length);
            }
            catch (Exception exception) {
                a.error((Object)"Impossible de compresser une donn\u00e9e : ", (Throwable)exception);
            }
        }
        return byArray2;
    }

    private static byte[] a(int n2, byte[] byArray) {
        byte[] byArray2 = bUC;
        if (byArray == bUC) {
            a.error((Object)"Impossible de d\u00e9compresser une donn\u00e9e : DeflatedByteArray \u00e9gal \u00e0 null.");
        } else if (byArray.length == 0) {
            a.error((Object)"Impossible de d\u00e9compresser une donn\u00e9e : DeflatedByteArray de longueur \u00e9gale \u00e0 z\u00e9ro.");
        } else {
            bUE.reset();
            bUE.setInput(byArray);
            try {
                wa_1.iI(n2);
                byArray2 = new byte[n2];
                bUE.inflate(bUF, 0, n2);
                System.arraycopy(bUF, 0, byArray2, 0, n2);
            }
            catch (Exception exception) {
                a.error((Object)"Impossible de d\u00e9compresser une donn\u00e9e : ", (Throwable)exception);
            }
        }
        return byArray2;
    }

    public static byte[] O(byte[] byArray) {
        ByteBuffer byteBuffer;
        byte[] byArray2 = wa_1.N(byArray);
        if (byArray2 == bUC) {
            byteBuffer = ByteBuffer.allocate(4 + byArray.length);
            byteBuffer.putInt(-byArray.length);
            byteBuffer.put(byArray);
        } else {
            byteBuffer = ByteBuffer.allocate(4 + byArray2.length);
            byteBuffer.putInt(byArray.length);
            byteBuffer.put(byArray2);
        }
        return byteBuffer.array();
    }

    public static byte[] P(byte[] byArray) {
        byte[] byArray2;
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.getInt();
        if (n2 < 0) {
            byArray2 = new byte[-n2];
            byteBuffer.get(byArray2);
        } else {
            byte[] byArray3 = new byte[byArray.length - 4];
            byteBuffer.get(byArray3);
            byArray2 = wa_1.a(n2, byArray3);
        }
        return byArray2;
    }
}

