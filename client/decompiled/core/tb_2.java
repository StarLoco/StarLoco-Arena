/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from tB
 */
public class tb_2
extends ael_2 {
    private long anl;
    private String aiK;
    private String anm;
    private long Ho;
    private short Gm;
    private short fA;
    private qa_2 ann;

    public long zI() {
        return this.anl;
    }

    public String xW() {
        return this.aiK;
    }

    public String zJ() {
        return this.anm;
    }

    public long qX() {
        return this.Ho;
    }

    public short qY() {
        return this.Gm;
    }

    public short cB() {
        return this.fA;
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
        this.anm = new String(byArray3);
        this.Ho = byteBuffer.getLong();
        this.Gm = byteBuffer.getShort();
        this.fA = byteBuffer.getShort();
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
        return 23110;
    }
}

