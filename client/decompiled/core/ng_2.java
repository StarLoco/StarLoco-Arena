/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Ng
 */
public class ng_2
extends ael_2 {
    public static final String bzg = "";
    public static final String bzh = "";
    public static final String bzi = "";
    private wy_1 bzj;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.bzj = new wy_1();
        int n2 = byteBuffer.getInt();
        for (int j = n2 - 1; 0 <= j; --j) {
            String string;
            String string2;
            String string3;
            long l2 = byteBuffer.getLong();
            boolean bl2 = byteBuffer.get() != 0;
            byte by = byteBuffer.get();
            short s = byteBuffer.getShort();
            boolean bl3 = byteBuffer.get() != 0;
            int[] nArray = new int[byteBuffer.getInt()];
            for (int i2 = nArray.length - 1; 0 <= i2; --i2) {
                nArray[i2] = byteBuffer.getInt();
            }
            byte[] byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            try {
                string3 = new String(byArray2, "UTF-8");
            }
            catch (Exception exception) {
                string3 = "";
            }
            byte[] byArray3 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray3);
            try {
                string2 = new String(byArray3, "UTF-8");
            }
            catch (Exception exception) {
                string2 = "";
            }
            byte[] byArray4 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray4);
            try {
                string = new String(byArray4, "UTF-8");
            }
            catch (Exception exception) {
                string = "";
            }
            if (string3.equals("NAME CENSORED")) {
                string3 = aon_0.aYc().getString("nameCensored");
            }
            if (string2.equals("DESCRIPTION CENSORED")) {
                string2 = aon_0.aYc().getString("descriptionCensored");
            }
            if (string.equals("ANKAMA")) {
                string = aon_0.aYc().getString("DemonsHours");
            }
            byte by2 = byteBuffer.get();
            this.bzj.a(l2, bl2, by, s, bl3, nArray, string3, string2, string, by2);
        }
        return true;
    }

    public wy_1 aao() {
        return this.bzj;
    }

    public int getId() {
        return 28602;
    }
}

