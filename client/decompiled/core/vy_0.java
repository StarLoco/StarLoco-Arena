/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from vy
 */
public class vy_0 {
    protected static Logger a = Logger.getLogger(vy_0.class);
    public static final vy_0 asW = null;
    private static final String asX = "-";
    private static final String asY = "-";
    private final String asZ;
    private final byte ata;
    private final qa_2 ann;
    private final ArrayList atb;
    private final ArrayList atc;
    private final long atd;

    private vy_0(String string, byte by, qa_2 qa_22, ArrayList arrayList, ArrayList arrayList2, long l2) {
        this.asZ = string;
        this.ata = by;
        this.ann = qa_22;
        this.atb = arrayList;
        this.atc = arrayList2;
        this.atd = l2;
    }

    public String BJ() {
        return this.asZ;
    }

    public byte BK() {
        return this.ata;
    }

    public qa_2 zK() {
        return this.ann;
    }

    public ArrayList BL() {
        return this.atb;
    }

    public ArrayList BM() {
        return this.atc;
    }

    public long BN() {
        return this.atd;
    }

    public String toString() {
        return "(" + this.asZ + ", " + this.ata + ", " + this.atb + ", " + this.atc + ", " + this.atd + ")";
    }

    public static vy_0 x(ByteBuffer byteBuffer) {
        String string;
        byte[] byArray;
        int n2;
        String string2;
        byte[] byArray2 = new byte[byteBuffer.getInt()];
        byteBuffer.get(byArray2);
        try {
            string2 = new String(byArray2, "UTF-8");
        }
        catch (Exception exception) {
            string2 = "-";
        }
        byte by = byteBuffer.get();
        int n3 = byteBuffer.getInt();
        qa_2 qa_22 = new qa_2();
        for (n2 = n3 - 1; 0 <= n2; --n2) {
            qa_22.ct(byteBuffer.getLong());
        }
        int n4 = byteBuffer.getInt();
        ArrayList<String> arrayList = new ArrayList<String>();
        for (n2 = n4 - 1; 0 <= n2; --n2) {
            byArray = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray);
            try {
                string = new String(byArray, "UTF-8");
            }
            catch (Exception exception) {
                string = "-";
            }
            arrayList.add(string);
        }
        int n5 = byteBuffer.getInt();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        for (n2 = n5 - 1; 0 <= n2; --n2) {
            byArray = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray);
            try {
                string = new String(byArray, "UTF-8");
            }
            catch (Exception exception) {
                string = "-";
            }
            arrayList2.add(string);
        }
        long l2 = byteBuffer.getLong();
        return new vy_0(string2, by, qa_22, arrayList, arrayList2, l2);
    }
}

