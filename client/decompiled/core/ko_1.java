/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from ko
 */
public class ko_1 {
    public static final float DZ = 2.0f;
    private static final Logger a = Logger.getLogger(ko_1.class);

    public static void a(float[] fArray, float[] fArray2) {
        fArray[0] = fArray2[0];
        fArray[1] = fArray2[1];
        fArray[2] = fArray2[2];
    }

    public static void b(float[] fArray, float[] fArray2) {
        fArray[0] = fArray2[0];
        fArray[1] = fArray2[1];
        fArray[2] = fArray2[2];
        fArray[0] = fArray[0] + fArray2[3];
        fArray[1] = fArray[1] + fArray2[4];
        fArray[2] = fArray[2] + fArray2[5];
    }

    public static void c(float[] fArray, float[] fArray2) {
        fArray[0] = fArray[0] * fArray2[0];
        fArray[1] = fArray[1] * fArray2[1];
        fArray[2] = fArray[2] * fArray2[2];
        fArray[0] = fArray[0] + fArray2[3];
        fArray[1] = fArray[1] + fArray2[4];
        fArray[2] = fArray[2] + fArray2[5];
    }

    public static void d(float[] fArray, float[] fArray2) {
        fArray[0] = fArray[0] + fArray2[0];
        fArray[1] = fArray[1] + fArray2[1];
        fArray[2] = fArray[2] + fArray2[2];
    }

    public static void e(float[] fArray, float[] fArray2) {
        fArray[0] = fArray[0] * fArray2[0];
        fArray[1] = fArray[1] * fArray2[1];
        fArray[2] = fArray[2] * fArray2[2];
    }

    public static void c(float[] fArray) {
        fArray[0] = 1.0f;
        fArray[1] = 1.0f;
        fArray[2] = 1.0f;
        fArray[3] = 0.0f;
        fArray[4] = 0.0f;
        fArray[5] = 0.0f;
    }
}

