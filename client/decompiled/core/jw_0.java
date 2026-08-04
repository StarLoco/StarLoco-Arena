/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from Jw
 */
public class jw_0
extends ael_2 {
    private int blF;
    private int blG;
    private int blH;
    private int blI;
    private jg_0 blJ;
    private ArrayList blK;
    private ArrayList blL;
    private mm_0 blM;
    private mm_0 blN;
    private ArrayList bhR;
    private mm_0 blO;
    private boolean blP;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.blF = byteBuffer.getInt();
        this.blG = byteBuffer.getInt();
        this.blH = byteBuffer.getInt();
        this.blI = byteBuffer.getInt();
        this.blJ = new jg_0();
        this.blK = new ArrayList();
        this.blL = new ArrayList();
        this.blM = new mm_0();
        this.blN = new mm_0();
        this.bhR = new ArrayList();
        this.blO = new mm_0();
        for (int j = 0; j < this.blH - this.blG; ++j) {
            this.blJ.add(byteBuffer.getInt());
            byte[] byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            this.blK.add(new String(byArray2));
            byte[] byArray3 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray3);
            this.blL.add(new String(byArray3));
            this.blM.add((short)byteBuffer.getInt());
            this.blN.add((short)byteBuffer.getInt());
            byte[] byArray4 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray4);
            this.bhR.add(new String(byArray4));
            this.blO.add(byteBuffer.getShort());
        }
        this.blP = byteBuffer.get() != 0;
        return true;
    }

    public int getId() {
        return 27509;
    }

    public int VU() {
        return this.blF;
    }

    public int VV() {
        return this.blG;
    }

    public int VW() {
        return this.blH;
    }

    public int VX() {
        return this.blI;
    }

    public int gn(int n2) {
        return this.blJ.get(n2);
    }

    public String go(int n2) {
        return (String)this.blK.get(n2);
    }

    public String gp(int n2) {
        return (String)this.blL.get(n2);
    }

    public short gq(int n2) {
        return this.blM.get(n2);
    }

    public short gr(int n2) {
        return this.blN.get(n2);
    }

    public String gs(int n2) {
        return (String)this.bhR.get(n2);
    }

    public short gt(int n2) {
        return this.blO.get(n2);
    }

    public boolean VY() {
        return this.blP;
    }
}

