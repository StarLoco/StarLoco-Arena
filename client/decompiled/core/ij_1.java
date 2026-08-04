/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from IJ
 */
public class ij_1
extends ael_2 {
    private ArrayList bhR;
    private short fA;
    private int bhS;
    private ArrayList bhT;
    private mm_0 bhU;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.fA = byteBuffer.getShort();
        this.bhS = byteBuffer.getInt();
        int n2 = byteBuffer.getInt();
        this.bhR = new ArrayList();
        this.bhT = new ArrayList();
        this.bhU = new mm_0();
        for (int j = 0; j < n2; ++j) {
            byte[] byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            this.bhR.add(new String(byArray2));
            byte[] byArray3 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray3);
            this.bhT.add(new String(byArray3));
            this.bhU.add((short)byteBuffer.getInt());
        }
        return true;
    }

    public int getId() {
        return 27503;
    }

    public int getSize() {
        return this.bhR.size();
    }

    public String gj(int n2) {
        return (String)this.bhR.get(n2);
    }

    public short cB() {
        return this.fA;
    }

    public int UI() {
        return this.bhS;
    }

    public String gk(int n2) {
        return (String)this.bhT.get(n2);
    }

    public short gl(int n2) {
        return this.bhU.get(n2);
    }
}

