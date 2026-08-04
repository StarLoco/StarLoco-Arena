/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from UJ
 */
public class uj_0
extends ael_2 {
    private byte bQW;
    private byte bQX;
    private short bQY;
    private int bQZ;
    private int bRa;
    private int bRb;
    private int bRc;
    private int bRd;
    private int bRe;
    private int bRf;
    private ArrayList bRg;
    private jg_0 bRh;
    private boolean bRi;
    private int bRj;
    private int bRk;
    private int bRl;
    private int bRm;
    private ArrayList bRn;
    private jg_0 bRo;
    private boolean bRp;
    private int bRq;
    private int bRr;
    private int bRs;
    private int bRt;
    private ArrayList bRu;
    private jg_0 bRv;
    private boolean bRw;

    public boolean a(byte[] byArray) {
        byte[] byArray2;
        int n2;
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.bQW = byteBuffer.get();
        this.bQX = byteBuffer.get();
        this.bQY = byteBuffer.getShort();
        this.bQZ = byteBuffer.getInt();
        this.bRa = byteBuffer.getInt();
        this.bRb = byteBuffer.getInt();
        this.bRc = byteBuffer.getInt();
        this.bRd = byteBuffer.getInt();
        this.bRe = byteBuffer.getInt();
        this.bRf = byteBuffer.getInt();
        this.bRg = new ArrayList();
        this.bRh = new jg_0();
        for (n2 = 0; n2 < this.bRe - this.bRd; ++n2) {
            byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            this.bRg.add(new String(byArray2));
            this.bRh.add(byteBuffer.getInt());
        }
        this.bRi = byteBuffer.get() != 0;
        this.bRj = byteBuffer.getInt();
        this.bRk = byteBuffer.getInt();
        this.bRl = byteBuffer.getInt();
        this.bRm = byteBuffer.getInt();
        this.bRn = new ArrayList();
        this.bRo = new jg_0();
        for (n2 = 0; n2 < this.bRl - this.bRk; ++n2) {
            byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            this.bRn.add(new String(byArray2));
            this.bRo.add(byteBuffer.getInt());
        }
        this.bRp = byteBuffer.get() != 0;
        this.bRq = byteBuffer.getInt();
        this.bRr = byteBuffer.getInt();
        this.bRs = byteBuffer.getInt();
        this.bRt = byteBuffer.getInt();
        this.bRu = new ArrayList();
        this.bRv = new jg_0();
        for (n2 = 0; n2 < this.bRs - this.bRr; ++n2) {
            byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            this.bRu.add(new String(byArray2));
            this.bRv.add(byteBuffer.getInt());
        }
        this.bRw = byteBuffer.get() != 0;
        return true;
    }

    public int getId() {
        return 27507;
    }

    public byte ahs() {
        return this.bQW;
    }

    public byte aht() {
        return this.bQX;
    }

    public short ahu() {
        return this.bQY;
    }

    public int ahv() {
        return this.bQZ;
    }

    public int ahw() {
        return this.bRa;
    }

    public int ahx() {
        return this.bRb;
    }

    public int ahy() {
        return this.bRc;
    }

    public int ahz() {
        return this.bRd;
    }

    public int ahA() {
        return this.bRe;
    }

    public int ahB() {
        return this.bRf;
    }

    public String ij(int n2) {
        return (String)this.bRg.get(n2);
    }

    public int ik(int n2) {
        return this.bRh.get(n2);
    }

    public boolean ahC() {
        return this.bRi;
    }

    public int ahD() {
        return this.bRj;
    }

    public int ahE() {
        return this.bRk;
    }

    public int ahF() {
        return this.bRl;
    }

    public int ahG() {
        return this.bRm;
    }

    public String il(int n2) {
        return (String)this.bRn.get(n2);
    }

    public int im(int n2) {
        return this.bRo.get(n2);
    }

    public boolean ahH() {
        return this.bRp;
    }

    public int ahI() {
        return this.bRq;
    }

    public int ahJ() {
        return this.bRr;
    }

    public int ahK() {
        return this.bRs;
    }

    public int ahL() {
        return this.bRt;
    }

    public String in(int n2) {
        return (String)this.bRu.get(n2);
    }

    public int io(int n2) {
        return this.bRv.get(n2);
    }

    public boolean ahM() {
        return this.bRw;
    }
}

