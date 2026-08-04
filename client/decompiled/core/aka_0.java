/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from aKa
 */
public class aka_0
extends ael_2 {
    private jg_0 dGE;
    private int blF;
    private int blG;
    private int blH;
    private ArrayList blK;
    private ArrayList dTd;
    private ArrayList blL;
    private mm_0 doc;
    private jg_0 dTe;
    private jg_0 dTf;
    private mm_0 dTg;
    private mm_0 dTh;
    private mm_0 dTi;
    private mm_0 dTj;
    private boolean blP;

    public boolean a(byte[] byArray) {
        int n2;
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.dGE = new jg_0();
        this.blK = new ArrayList();
        this.dTd = new ArrayList();
        this.blL = new ArrayList();
        this.doc = new mm_0();
        this.dTe = new jg_0();
        this.dTf = new jg_0();
        this.dTg = new mm_0();
        this.dTh = new mm_0();
        this.dTi = new mm_0();
        this.dTj = new mm_0();
        this.blF = byteBuffer.getInt();
        this.blG = byteBuffer.getInt();
        this.blH = byteBuffer.getInt();
        int n3 = byteBuffer.getInt();
        for (n2 = n3 - 1; 0 <= n2; --n2) {
            this.dGE.add(byteBuffer.getInt());
        }
        for (n2 = 0; n2 < this.blH - this.blG; ++n2) {
            byte[] byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            this.blK.add(new String(byArray2));
            byte[] byArray3 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray3);
            this.dTd.add(new String(byArray3));
            byte[] byArray4 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray4);
            this.blL.add(new String(byArray4));
            this.doc.add(byteBuffer.getShort());
            this.dTe.add(byteBuffer.getInt());
            this.dTf.add(byteBuffer.getInt());
            this.dTg.add((short)byteBuffer.getInt());
            this.dTh.add((short)byteBuffer.getInt());
            this.dTi.add((short)byteBuffer.getInt());
            this.dTj.add((short)byteBuffer.getInt());
        }
        this.blP = byteBuffer.getInt() != 0;
        return true;
    }

    public int getId() {
        return 27505;
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

    public jg_0 aVx() {
        return this.dGE;
    }

    public String oR(int n2) {
        return (String)this.blK.get(n2);
    }

    public String oS(int n2) {
        return (String)this.dTd.get(n2);
    }

    public String oT(int n2) {
        return (String)this.blL.get(n2);
    }

    public short oU(int n2) {
        return this.doc.get(n2);
    }

    public int oV(int n2) {
        return this.dTe.get(n2);
    }

    public int oW(int n2) {
        return this.dTf.get(n2);
    }

    public short oX(int n2) {
        return this.dTg.get(n2);
    }

    public short oY(int n2) {
        return this.dTh.get(n2);
    }

    public short oZ(int n2) {
        return this.dTi.get(n2);
    }

    public short pa(int n2) {
        return this.dTj.get(n2);
    }

    public boolean VY() {
        return this.blP;
    }
}

