/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

public final class aeI
extends lJ {
    private static final short fn = 1;
    private int aW;
    private int aRC;
    private int cpn;
    private short cpo;
    private short cpp;
    private int ir;
    private final ArrayList iM = new ArrayList();
    private int fq;
    private jg_0 cpq = new jg_0();
    private boolean cpr;
    private short cps;
    private short cpt;

    public aeI() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cVb.getId();
    }

    public byte[] cr() {
        int n2 = 4;
        for (Object object : this.iM) {
            n2 += ((Ht)object).cr().length + 4 + 4 + 2;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2 + 4 + 4 + 4 + 2 + 2 + 4 + 4 + 1 + this.cpq.size() * 4 + 1 + 2 + 2);
        byteBuffer.putInt(this.aW);
        byteBuffer.putInt(this.aRC);
        byteBuffer.putInt(this.cpn);
        byteBuffer.putShort(this.cpo);
        byteBuffer.putShort(this.cpp);
        byteBuffer.putInt(this.ir);
        byteBuffer.putInt(this.fq);
        byteBuffer.putInt(this.iM.size());
        for (Ht ht : this.iM) {
            byteBuffer.putInt(ht.qw());
            byteBuffer.putShort(ht.qx());
            byte[] byArray = ht.cr();
            byteBuffer.putInt(byArray.length);
            byteBuffer.put(byArray);
        }
        byteBuffer.put((byte)this.cpq.size());
        for (int j = 0; j < this.cpq.size(); ++j) {
            byteBuffer.putInt(this.cpq.bu(j));
        }
        byteBuffer.put(this.cpr ? (byte)1 : 0);
        byteBuffer.putShort(this.cps);
        byteBuffer.putShort(this.cpt);
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            int n3;
            int n4;
            this.aW = byteBuffer.getInt();
            this.aRC = byteBuffer.getInt();
            this.cpn = byteBuffer.getInt();
            this.cpo = byteBuffer.getShort();
            this.cpp = byteBuffer.getShort();
            this.ir = byteBuffer.getInt();
            this.fq = byteBuffer.getInt();
            int n5 = byteBuffer.getInt();
            for (n4 = 0; n4 < n5; ++n4) {
                n3 = byteBuffer.getInt();
                short s2 = byteBuffer.getShort();
                byte[] byArray = new byte[byteBuffer.getInt()];
                byteBuffer.get(byArray);
                Ht ht = new Ht();
                ht.a(ByteBuffer.wrap(byArray), n3, s2);
                this.a(ht);
            }
            n4 = byteBuffer.get();
            for (n3 = 0; n3 < n4; ++n3) {
                this.cpq.add(byteBuffer.getInt());
            }
            this.cpr = byteBuffer.get() == 1;
            this.cps = byteBuffer.getShort();
            this.cpt = byteBuffer.getShort();
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new aeI();
    }

    public void a(Ht ht) {
        this.iM.add(ht);
    }

    public ArrayList eC() {
        return this.iM;
    }

    public int getId() {
        return this.aW;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public int NH() {
        return this.aRC;
    }

    public void fw(int n2) {
        this.aRC = n2;
    }

    public int aus() {
        return this.cpn;
    }

    public void ki(int n2) {
        this.cpn = n2;
    }

    public short aut() {
        return this.cpo;
    }

    public void bA(short s) {
        this.cpo = s;
    }

    public short auu() {
        return this.cpp;
    }

    public void bB(short s) {
        this.cpp = s;
    }

    public int el() {
        return this.ir;
    }

    public void I(int n2) {
        this.ir = n2;
    }

    public int cv() {
        return this.fq;
    }

    public void x(int n2) {
        this.fq = n2;
    }

    public jg_0 auv() {
        return this.cpq;
    }

    public void kj(int n2) {
        this.cpq.add(n2);
    }

    public boolean auw() {
        return this.cpr;
    }

    public void dp(boolean bl2) {
        this.cpr = bl2;
    }

    public short aux() {
        return this.cps;
    }

    public void bC(short s) {
        this.cps = s;
    }

    public short auy() {
        return this.cpt;
    }

    public void bD(short s) {
        this.cpt = s;
    }
}

