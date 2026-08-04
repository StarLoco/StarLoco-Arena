/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from anc
 */
public class anc_0
extends ael_2 {
    private short cIr;
    private short fA;
    private int cIs = 0;
    private ArrayList bhR;
    private qa_2 bXz;
    private qa_2 cIt;
    private qa_2 cIu;
    private long cIv;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.cIr = byteBuffer.getShort();
        this.fA = byteBuffer.getShort();
        this.cIs = byteBuffer.getInt();
        int n2 = byteBuffer.getInt();
        this.bhR = new ArrayList();
        this.bXz = new qa_2();
        this.cIt = new qa_2();
        this.cIu = new qa_2();
        for (int j = 0; j < n2; ++j) {
            byte[] byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            this.bhR.add(new String(byArray2));
            this.bXz.ct(byteBuffer.getLong());
            this.cIt.ct(byteBuffer.getLong());
            this.cIu.ct(byteBuffer.getLong());
        }
        this.cIv = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 27511;
    }

    public int getSize() {
        return this.bhR.size();
    }

    public short aCd() {
        return this.cIr;
    }

    public short cB() {
        return this.fA;
    }

    public int aCe() {
        return this.cIs;
    }

    public String gj(int n2) {
        return (String)this.bhR.get(n2);
    }

    public long iT(int n2) {
        return this.bXz.get(n2);
    }

    public long lw(int n2) {
        return this.cIt.get(n2);
    }

    public long lx(int n2) {
        return this.cIu.get(n2);
    }

    public long aCf() {
        return this.cIv;
    }
}

