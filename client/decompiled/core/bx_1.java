/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from BX
 */
public class bx_1
extends ael_2 {
    private long anl;
    private String aiK;
    private String aKm;
    private short aKn;
    private qa_2 ann;

    public long zI() {
        return this.anl;
    }

    public String xW() {
        return this.aiK;
    }

    public String IP() {
        return this.aKm;
    }

    public short IQ() {
        return this.aKn;
    }

    public qa_2 zK() {
        return this.ann;
    }

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.anl = byteBuffer.getLong();
        byte[] byArray2 = new byte[byteBuffer.getInt()];
        byteBuffer.get(byArray2);
        this.aiK = new String(byArray2);
        byte[] byArray3 = new byte[byteBuffer.getInt()];
        byteBuffer.get(byArray3);
        this.aKm = new String(byArray3);
        this.aKn = byteBuffer.getShort();
        this.ann = new qa_2();
        int n2 = byteBuffer.getInt();
        if (n2 > 0) {
            for (int j = n2 - 1; 0 <= j && byteBuffer.hasRemaining(); --j) {
                this.ann.ct(byteBuffer.getLong());
            }
        }
        return true;
    }

    public int getId() {
        return 2307;
    }
}

