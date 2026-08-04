/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from Xn
 */
public class xn_2
extends ael_2 {
    private short fA;
    private int bXy = 0;
    private mm_0 blO;
    private qa_2 bXz;
    private ArrayList bhR;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.fA = byteBuffer.getShort();
        this.bXy = byteBuffer.getInt();
        int n2 = byteBuffer.getInt();
        this.blO = new mm_0();
        this.bXz = new qa_2();
        this.bhR = new ArrayList();
        for (int j = 0; j < n2; ++j) {
            this.blO.add(byteBuffer.getShort());
            this.bXz.ct(byteBuffer.getLong());
            byte[] byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            this.bhR.add(new String(byArray2));
        }
        return true;
    }

    public int getId() {
        return 27513;
    }

    public int getSize() {
        return this.bhR.size();
    }

    public short cB() {
        return this.fA;
    }

    public int akP() {
        return this.bXy;
    }

    public short iS(int n2) {
        return this.blO.get(n2);
    }

    public String gj(int n2) {
        return (String)this.bhR.get(n2);
    }

    public long iT(int n2) {
        return this.bXz.get(n2);
    }
}

