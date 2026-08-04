/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from rF
 */
public final class rf_2
extends lJ {
    private static final short fn = 1;
    private int aW;
    private String ahG;
    private int ahH;
    private int ahI;
    private int ahJ;
    private int ahK;
    private int it;
    private int[] ahL;
    private int[] ahM;
    private int[] ahN;
    private int[] ahO;
    private String ahP;
    private boolean ahQ = false;
    private final ArrayList iM = new ArrayList();

    public rf_2() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUK.getId();
    }

    public byte[] cr() {
        Object object;
        Object object22;
        int n2 = 4;
        for (Object object22 : this.iM) {
            n2 += ((Ht)object22).cr().length + 4 + 4 + 2;
        }
        Object object3 = aey_0.hH(this.ahG);
        object22 = aey_0.hH(this.ahP);
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2 + 4 + 4 + ((Object)object3).length + 4 + ((Object)object22).length + 4 + 4 + 4 + 4 + 4 + 16 + (this.ahL == null ? 0 : 4 * this.ahL.length) + (this.ahM == null ? 0 : 4 * this.ahM.length) + (this.ahN == null ? 0 : 4 * this.ahN.length) + (this.ahO == null ? 0 : 4 * this.ahO.length));
        byteBuffer.putInt(this.aW);
        byteBuffer.putInt(((Object)object3).length);
        byteBuffer.put((byte[])object3);
        byteBuffer.putInt(((Object)object22).length);
        byteBuffer.put((byte[])object22);
        byteBuffer.putInt(this.ahH);
        byteBuffer.putInt(this.ahI);
        byteBuffer.putInt(this.ahJ);
        byteBuffer.putInt(this.ahK);
        byteBuffer.putInt(this.it);
        if (this.ahL != null) {
            byteBuffer.putInt(this.ahL.length);
            for (Object object4 : this.ahL) {
                byteBuffer.putInt((int)object4);
            }
        } else {
            byteBuffer.putInt(0);
        }
        if (this.ahM != null) {
            byteBuffer.putInt(this.ahM.length);
            for (Object object4 : this.ahM) {
                byteBuffer.putInt((int)object4);
            }
        } else {
            byteBuffer.putInt(0);
        }
        if (this.ahN != null) {
            byteBuffer.putInt(this.ahN.length);
            for (Object object4 : this.ahN) {
                byteBuffer.putInt((int)object4);
            }
        } else {
            byteBuffer.putInt(0);
        }
        if (this.ahO != null) {
            byteBuffer.putInt(this.ahO.length);
            object = this.ahO;
            int n3 = ((int[])object).length;
            for (int j = 0; j < n3; ++j) {
                Object object4;
                object4 = object[j];
                byteBuffer.putInt((int)object4);
            }
        } else {
            byteBuffer.putInt(0);
        }
        byteBuffer.putInt(this.iM.size());
        object = this.iM.iterator();
        while (object.hasNext()) {
            Ht ht = (Ht)object.next();
            byteBuffer.putInt(ht.qw());
            byteBuffer.putShort(ht.qx());
            byte[] byArray = ht.cr();
            byteBuffer.putInt(byArray.length);
            byteBuffer.put(byArray);
        }
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            int n3;
            this.aW = byteBuffer.getInt();
            byte[] byArray = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray);
            this.ahG = aey_0.V(byArray);
            byte[] byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            this.ahP = aey_0.V(byArray2);
            this.ahH = byteBuffer.getInt();
            this.ahI = byteBuffer.getInt();
            this.ahJ = byteBuffer.getInt();
            this.ahK = byteBuffer.getInt();
            this.it = byteBuffer.getInt();
            int n4 = byteBuffer.getInt();
            this.ahL = new int[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.ahL[n3] = byteBuffer.getInt();
            }
            n4 = byteBuffer.getInt();
            this.ahM = new int[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.ahM[n3] = byteBuffer.getInt();
            }
            n4 = byteBuffer.getInt();
            this.ahN = new int[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.ahN[n3] = byteBuffer.getInt();
            }
            n4 = byteBuffer.getInt();
            this.ahO = new int[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.ahO[n3] = byteBuffer.getInt();
            }
            n3 = byteBuffer.getInt();
            for (int j = 0; j < n3; ++j) {
                int n5 = byteBuffer.getInt();
                short s2 = byteBuffer.getShort();
                byte[] byArray3 = new byte[byteBuffer.getInt()];
                byteBuffer.get(byArray3);
                Ht ht = new Ht();
                ht.a(ByteBuffer.wrap(byArray3), n5, s2);
                if (ht.isCritical()) {
                    this.ahQ = true;
                }
                this.a(ht);
            }
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new rf_2();
    }

    public int getId() {
        return this.aW;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public String getType() {
        return this.ahG;
    }

    public void setType(String string) {
        this.ahG = string;
    }

    public int xp() {
        return this.ahH;
    }

    public void dq(int n2) {
        this.ahH = n2;
    }

    public int xq() {
        return this.ahI;
    }

    public void dr(int n2) {
        this.ahI = n2;
    }

    public int xr() {
        return this.ahJ;
    }

    public void ds(int n2) {
        this.ahJ = n2;
    }

    public int xs() {
        return this.ahK;
    }

    public void dt(int n2) {
        this.ahK = n2;
    }

    public int[] xt() {
        return this.ahL;
    }

    public void m(int[] nArray) {
        this.ahL = nArray;
    }

    public int[] xu() {
        return this.ahM;
    }

    public void n(int[] nArray) {
        this.ahM = nArray;
    }

    public int[] xv() {
        return this.ahN;
    }

    public void o(int[] nArray) {
        this.ahN = nArray;
    }

    public int[] xw() {
        return this.ahO;
    }

    public void p(int[] nArray) {
        this.ahO = nArray;
    }

    public int eA() {
        return this.it;
    }

    public void L(int n2) {
        this.it = n2;
    }

    public String xx() {
        return this.ahP;
    }

    public void bR(String string) {
        this.ahP = string;
    }

    public ArrayList eC() {
        return this.iM;
    }

    public void a(Ht ht) {
        this.iM.add(ht);
    }

    public boolean xy() {
        return this.ahQ;
    }
}

