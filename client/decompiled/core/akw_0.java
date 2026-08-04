/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from akW
 */
public abstract class akw_0 {
    protected static final Logger a = Logger.getLogger(akw_0.class);
    private int aW;
    protected int[] JI;
    protected final long cEr;
    protected final byte cEs;
    public static final byte cEt = 127;

    public akw_0(int[] nArray, long l2, byte by) {
        this.JI = nArray;
        this.cEr = l2;
        this.cEs = by;
    }

    public int nj() {
        return 5 + 4 * this.JI.length + 8 + 1;
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putInt(this.getType());
        byteBuffer.put((byte)this.JI.length);
        for (int j = 0; j < this.JI.length; ++j) {
            byteBuffer.putInt(this.JI[j]);
        }
        byteBuffer.putLong(this.cEr);
        byteBuffer.put(this.cEs);
    }

    public static akw_0 J(ByteBuffer byteBuffer) {
        int n2 = byteBuffer.getInt();
        int[] nArray = new int[byteBuffer.get()];
        for (int j = 0; j < nArray.length; ++j) {
            nArray[j] = byteBuffer.getInt();
        }
        return akw_0.a(n2, nArray, byteBuffer.getLong(), byteBuffer.get());
    }

    public static akw_0 a(int n2, int[] nArray, long l2, byte by) {
        if (n2 == AI.aHw.tI()) {
            return new ahH(nArray, l2, by);
        }
        if (n2 == AI.aHx.tI()) {
            return new adl_2(nArray, l2, by);
        }
        if (n2 == AI.aHy.tI()) {
            return new apl_0(nArray, l2, by);
        }
        if (n2 == AI.aHz.tI()) {
            return new E(nArray, l2, by);
        }
        if (n2 == AI.aHA.tI()) {
            return new ze_1(nArray, l2, by);
        }
        if (n2 == AI.aHB.tI()) {
            return new sc_1(nArray, l2, by);
        }
        if (n2 == AI.aHC.tI()) {
            return new fy_0(nArray, l2, by);
        }
        if (n2 == AI.aHD.tI()) {
            return new arw_0(nArray, l2, by);
        }
        if (n2 == AI.aHE.tI()) {
            return new cc_1(nArray, l2, by);
        }
        if (n2 == AI.aHF.tI()) {
            return new aoi_1(nArray, l2, by);
        }
        if (n2 == AI.aHG.tI()) {
            return new aic_1(nArray, l2, by);
        }
        if (n2 == AI.aHI.tI()) {
            return new nz_1(nArray, l2, by);
        }
        if (n2 == AI.aHJ.tI()) {
            return new ga_0(nArray, l2, by);
        }
        if (n2 == AI.aHK.tI()) {
            return new vm_2(nArray, l2, by);
        }
        if (n2 == AI.aHL.tI()) {
            return new cm_1(nArray, l2, by);
        }
        if (n2 == AI.aHM.tI()) {
            return new arT(nArray, l2, by);
        }
        if (n2 == AI.aHN.tI()) {
            return new avh_0(nArray, l2, by);
        }
        if (n2 == AI.aHO.tI()) {
            return new yt_0(nArray, l2, by);
        }
        if (n2 == AI.aHP.tI()) {
            return new aqm_0(nArray, l2, by);
        }
        if (n2 == AI.aHQ.tI()) {
            return new akl(nArray, l2, by);
        }
        return null;
    }

    public int g(et_2 et_22) {
        adl_0 adl_02 = et_22.NF();
        if (this.JI.length == this.aa() && aap.a(this.cEr, et_22.cu(), adl_02.atf(), et_22.NG(), adl_02.atg(), nr_0.cs(et_22.Ny()))) {
            return this.a(et_22);
        }
        return 0;
    }

    public void c(et_2 et_22) {
        if (this.fc() && this.JI.length == this.aa() && (et_22.NB() != 2 && et_22.NB() != 3 || this.aaF())) {
            this.e(et_22);
        } else {
            a.error((Object)("Erreur de donn\u00e9es, on me demande d'appliquer un effet inapplicable, faites un passage sur l'AGT " + this.getClass()));
        }
    }

    public void b(vy_1 vy_12) {
    }

    public void d(et_2 et_22) {
    }

    protected abstract int aa();

    protected abstract int a(et_2 var1);

    public boolean isNegative() {
        return false;
    }

    public void e(et_2 et_22) {
        this.a(et_22);
    }

    public boolean fc() {
        return false;
    }

    public boolean aaF() {
        return false;
    }

    public int aAi() {
        return 0;
    }

    public int ts() {
        return 0;
    }

    public int azK() {
        return 0;
    }

    public int aAj() {
        return 0;
    }

    public abstract int getType();

    public int[] rg() {
        return this.JI;
    }

    public Object[] aAk() {
        Object[] objectArray = new Object[this.JI.length];
        for (int j = 0; j < this.JI.length; ++j) {
            objectArray[j] = this.JI[j];
        }
        return objectArray;
    }

    public long aAl() {
        return this.cEr;
    }

    public byte aAm() {
        return this.cEs;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public int getId() {
        return this.aW;
    }
}

