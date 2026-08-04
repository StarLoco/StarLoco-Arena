/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/*
 * Renamed from aFj
 */
public class afj_0
extends ws_2
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient Object[] iN;

    public afj_0() {
    }

    public afj_0(int n2) {
        super(n2);
    }

    public afj_0(int n2, float f) {
        super(n2, f);
    }

    public afj_0(alo_0 alo_02) {
        super(alo_02);
    }

    public afj_0(int n2, alo_0 alo_02) {
        super(n2, alo_02);
    }

    public afj_0(int n2, float f, alo_0 alo_02) {
        super(n2, f, alo_02);
    }

    public afj_0 aRI() {
        afj_0 afj_02 = (afj_0)super.clone();
        afj_02.iN = (Object[])this.iN.clone();
        return afj_02;
    }

    public gk_1 aRJ() {
        return new gk_1(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.iN = new Object[n3];
        return n3;
    }

    public Object b(byte by, Object object) {
        Object object2 = null;
        int n2 = this.E(by);
        boolean bl2 = true;
        if (n2 < 0) {
            n2 = -n2 - 1;
            object2 = this.iN[n2];
            bl2 = false;
        }
        byte by2 = this.bCp[n2];
        this.auE[n2] = by;
        this.bCp[n2] = 1;
        this.iN[n2] = object;
        if (bl2) {
            this.Z(by2 == 0);
        }
        return object2;
    }

    protected void rehash(int n2) {
        int n3 = this.auE.length;
        byte[] byArray = this.auE;
        Object[] objectArray = this.iN;
        byte[] byArray2 = this.bCp;
        this.auE = new byte[n2];
        this.iN = new Object[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray2[n4] != 1) continue;
            byte by = byArray[n4];
            int n5 = this.E(by);
            this.auE[n5] = by;
            this.iN[n5] = objectArray[n4];
            this.bCp[n5] = 1;
        }
    }

    public Object bk(byte by) {
        int n2 = this.D(by);
        return n2 < 0 ? null : this.iN[n2];
    }

    public void clear() {
        super.clear();
        byte[] byArray = this.auE;
        Object[] objectArray = this.iN;
        byte[] byArray2 = this.bCp;
        int n2 = byArray.length;
        while (n2-- > 0) {
            byArray[n2] = 0;
            objectArray[n2] = null;
            byArray2[n2] = 0;
        }
    }

    public Object bl(byte by) {
        Object object = null;
        int n2 = this.D(by);
        if (n2 >= 0) {
            object = this.iN[n2];
            this.O(n2);
        }
        return object;
    }

    public boolean equals(Object object) {
        if (!(object instanceof afj_0)) {
            return false;
        }
        afj_0 afj_02 = (afj_0)object;
        if (afj_02.size() != this.size()) {
            return false;
        }
        return this.a(new aun_0(afj_02));
    }

    public int hashCode() {
        ahx_1 ahx_12 = new ahx_1(this, null);
        this.a(ahx_12);
        return ahx_12.dY();
    }

    protected void O(int n2) {
        this.iN[n2] = null;
        super.O(n2);
    }

    public Object[] getValues() {
        Object[] objectArray = new Object[this.size()];
        Object[] objectArray2 = this.iN;
        byte[] byArray = this.bCp;
        int n2 = objectArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            objectArray[n3++] = objectArray2[n2];
        }
        return objectArray;
    }

    public Object[] a(Object[] objectArray) {
        if (objectArray.length < this._size) {
            objectArray = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), this._size);
        }
        Object[] objectArray2 = this.iN;
        byte[] byArray = this.bCp;
        int n2 = objectArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            objectArray[n3++] = objectArray2[n2];
        }
        return objectArray;
    }

    public byte[] GF() {
        byte[] byArray = new byte[this.size()];
        byte[] byArray2 = this.auE;
        byte[] byArray3 = this.bCp;
        int n2 = byArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray3[n2] != 1) continue;
            byArray[n3++] = byArray2[n2];
        }
        return byArray;
    }

    public boolean containsValue(Object object) {
        byte[] byArray = this.bCp;
        Object[] objectArray = this.iN;
        if (null == object) {
            int n2 = objectArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || object != objectArray[n2]) continue;
                return true;
            }
        } else {
            int n3 = objectArray.length;
            while (n3-- > 0) {
                if (byArray[n3] != 1 || object != objectArray[n3] && !object.equals(objectArray[n3])) continue;
                return true;
            }
        }
        return false;
    }

    public boolean K(byte by) {
        return this.contains(by);
    }

    public boolean b(amm_2 amm_22) {
        return this.a(amm_22);
    }

    public boolean a(apx apx2) {
        byte[] byArray = this.bCp;
        Object[] objectArray = this.iN;
        int n2 = objectArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || apx2.a(objectArray[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean a(aom_1 aom_12) {
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.auE;
        Object[] objectArray = this.iN;
        int n2 = byArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || aom_12.a(byArray2[n2], objectArray[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(aom_1 aom_12) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.auE;
        Object[] objectArray = this.iN;
        this.pf();
        try {
            int n2 = byArray2.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || aom_12.a(byArray2[n2], objectArray[n2])) continue;
                this.O(n2);
                bl2 = true;
            }
        }
        finally {
            this.Y(true);
        }
        return bl2;
    }

    public void a(ahc ahc2) {
        byte[] byArray = this.bCp;
        Object[] objectArray = this.iN;
        int n2 = objectArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            objectArray[n2] = ahc2.execute(objectArray[n2]);
        }
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(0);
        objectOutput.writeInt(this._size);
        atr atr2 = new atr(objectOutput);
        if (!this.a(atr2)) {
            throw atr2.cTR;
        }
    }

    public void readExternal(ObjectInput objectInput) {
        objectInput.readByte();
        int n2 = objectInput.readInt();
        this.N(n2);
        while (n2-- > 0) {
            byte by = objectInput.readByte();
            Object object = objectInput.readObject();
            this.b(by, object);
        }
    }
}

