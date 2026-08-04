/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import org.apache.log4j.Logger;

/*
 * Renamed from acG
 */
public class acg_1 {
    private static final Logger a = Logger.getLogger(acg_1.class);
    private static final acg_1 ckJ = new acg_1();
    private final lb_0 ckK = new lb_0(200, 1.0f);
    private String aJ = "./";

    public static acg_1 arw() {
        return ckJ;
    }

    public final void clear() {
        this.ckK.clear();
    }

    public final void setPath(String string) {
        this.aJ = string;
    }

    public final void jJ(int n2) {
        this.clear();
        String string = String.format(this.aJ, n2);
        try {
            this.load(string);
        }
        catch (IOException iOException) {
            a.error((Object)("Probl\u00e8me lors du chargement des infos de group " + string), (Throwable)iOException);
        }
    }

    private void load(String string) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(vq_2.readFile(string));
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        int n2 = byteBuffer.getShort() & 0xFFFF;
        for (int j = 0; j < n2; ++j) {
            short s = byteBuffer.getShort();
            int n3 = byteBuffer.get() & 0xFF;
            int[] nArray = new int[n3];
            for (int i2 = 0; i2 < n3; ++i2) {
                nArray[i2] = byteBuffer.getShort();
            }
            this.ckK.c(s, nArray);
        }
        this.ckK.compact();
    }

    public final boolean bj(int n2, int n3) {
        if (n2 == 0) {
            return n3 <= 0;
        }
        int[] nArray = (int[])this.ckK.get(n2);
        if (nArray == null) {
            return true;
        }
        return Arrays.binarySearch(nArray, n3) >= 0;
    }
}

