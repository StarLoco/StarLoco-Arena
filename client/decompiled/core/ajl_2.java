/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from ajl
 */
public class ajl_2 {
    protected static final Logger a = Logger.getLogger(ajl_2.class);
    private static final ajl_2 cAi = new ajl_2();
    private final HashMap cAj = new HashMap();
    private final ArrayList cAk = new ArrayList();

    public static ajl_2 aza() {
        return cAi;
    }

    private ajl_2() {
        for (int j = 1; j < 17; ++j) {
            int n2 = 1 << j;
            aex_1 aex_12 = this.kZ(n2);
            if (aex_12 != null) continue;
            throw new RuntimeException("Impossible de cr\u00e9er un des pools par d\u00e9faut : size = " + n2 + " bytes");
        }
    }

    public aex_1 kZ(int n2) {
        Object object = this.cAk.iterator();
        while (object.hasNext()) {
            int n3 = (Integer)object.next();
            if (n3 < n2) continue;
            return (aex_1)this.cAj.get(n3);
        }
        object = new aex_1(n2);
        this.cAj.put(n2, object);
        this.cAk.add(n2);
        return object;
    }

    byte[] a(aJj aJj2, aea_0 ... aea_0Array) {
        if (aea_0Array == null || aea_0Array.length == 0) {
            return new byte[0];
        }
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int[] nArray = new int[aea_0Array.length];
        for (aea_0 object2 : aea_0Array) {
            if (object2 == null) {
                throw new RuntimeException("Impossible de s\u00e9rialiser le BInarSerial : une part est null");
            }
            if (object2 != aea_0.dBr) {
                try {
                    object2.aQy();
                    nArray[n4] = object2.lF();
                }
                catch (Exception exception) {
                    throw new RuntimeException("Error while calling expectedSize()", exception);
                }
                n2 += 1 + nArray[n4];
                ++n3;
            }
            ++n4;
        }
        aex_1 aex_12 = this.kZ(n2 + aea_0Array.length * 5 + 1);
        ByteBuffer byteBuffer = aex_12.aPW();
        byte[] byArray = new byte[n3];
        int[] nArray2 = new int[n3];
        byteBuffer.put((byte)n3);
        byteBuffer.position(1 + n3 * 5);
        n3 = 0;
        n4 = 0;
        for (aea_0 aea_02 : aea_0Array) {
            if (aea_02 != aea_0.dBr) {
                try {
                    int exception = 1 + nArray[n4];
                    aex_1 aex_13 = this.kZ(exception);
                    ByteBuffer byteBuffer2 = aex_13.aPW();
                    byteBuffer2.limit(exception);
                    byte by = (byte)aJj2.a(aea_02);
                    if (by >= 0) {
                        byArray[n3] = by;
                        nArray2[n3] = byteBuffer.position();
                        byteBuffer2.put(by);
                        aea_02.clearError();
                        if (nArray[n4] > 0) {
                            aea_02.c(byteBuffer2);
                        }
                        ++n3;
                    } else {
                        a.error((Object)("Impossible d'ajouter une part non r\u00e9f\u00e9renc\u00e9e : " + aea_02.getClass().getName()));
                    }
                    byteBuffer2.flip();
                    byteBuffer.put(byteBuffer2);
                    aex_13.N(byteBuffer2);
                }
                catch (Exception exception) {
                    aea_02.a("Exception lev\u00e9e lors de la s\u00e9rialisation de la part " + aea_02.getClass().getName(), exception);
                }
            }
            ++n4;
        }
        for (int byArray2 = 0; byArray2 < n3; ++byArray2) {
            byteBuffer.put(1 + byArray2 * 5, byArray[byArray2]);
            byteBuffer.putInt(1 + byArray2 * 5 + 1, nArray2[byArray2]);
        }
        byteBuffer.flip();
        byte[] byArray2 = new byte[byteBuffer.limit() - byteBuffer.position()];
        byteBuffer.get(byArray2);
        aex_12.N(byteBuffer);
        return byArray2;
    }
}

