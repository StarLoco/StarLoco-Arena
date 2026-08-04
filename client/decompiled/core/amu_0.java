/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from aMU
 */
public class amu_0
extends ael_2 {
    private int dGN;
    private int dnY;
    private int dGL;
    private int dGM;
    private int dGO = 0;
    private ArrayList dYP;
    private ArrayList bhR;
    private mm_0 dYQ;
    private boolean blP;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.dnY = byteBuffer.getInt();
        this.dGL = byteBuffer.getInt();
        this.dGM = byteBuffer.getInt();
        this.dGN = byteBuffer.getInt();
        this.dGO = byteBuffer.getInt();
        this.dYP = new ArrayList();
        this.bhR = new ArrayList();
        this.dYQ = new mm_0();
        for (int j = 0; j < this.dGM - this.dGL; ++j) {
            byte[] byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            byte[] byArray3 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray3);
            this.dYP.add(new String(byArray2));
            this.bhR.add(new String(byArray3));
            this.dYQ.add(byteBuffer.getShort());
        }
        this.blP = byteBuffer.get() != 0;
        return true;
    }

    public int getId() {
        return 27515;
    }

    public int aMb() {
        return this.dnY;
    }

    public int aRN() {
        return this.dGL;
    }

    public int aXm() {
        return this.dGM;
    }

    public int aXn() {
        return this.dGN;
    }

    public int aRO() {
        return this.dGO;
    }

    public String ps(int n2) {
        return (String)this.dYP.get(n2);
    }

    public String gs(int n2) {
        return (String)this.bhR.get(n2);
    }

    public short pt(int n2) {
        return this.dYQ.get(n2);
    }

    public boolean VY() {
        return this.blP;
    }
}

