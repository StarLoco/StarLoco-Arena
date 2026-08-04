/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/*
 * Renamed from lb
 */
public class lb_0
extends aMP
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient Object[] iN;

    public lb_0() {
    }

    public lb_0(int n2) {
        super(n2);
    }

    public lb_0(int n2, float f) {
        super(n2, f);
    }

    public lb_0(ui_0 ui_02) {
        super(ui_02);
    }

    public lb_0(int n2, ui_0 ui_02) {
        super(n2, ui_02);
    }

    public lb_0(int n2, float f, ui_0 ui_02) {
        super(n2, f, ui_02);
    }

    public lb_0 pJ() {
        lb_0 lb_02 = (lb_0)super.clone();
        lb_02.iN = (Object[])this.iN.clone();
        return lb_02;
    }

    public ll_0 pK() {
        return new ll_0(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.iN = new Object[n3];
        return n3;
    }

    public Object c(int n2, Object object) {
        Object object2 = null;
        int n3 = this.pr(n2);
        boolean bl2 = true;
        if (n3 < 0) {
            n3 = -n3 - 1;
            object2 = this.iN[n3];
            bl2 = false;
        }
        byte by = this.bCp[n3];
        this.dYH[n3] = n2;
        this.bCp[n3] = 1;
        this.iN[n3] = object;
        if (bl2) {
            this.Z(by == 0);
        }
        return object2;
    }

    protected void rehash(int n2) {
        int n3 = this.dYH.length;
        int[] nArray = this.dYH;
        Object[] objectArray = this.iN;
        byte[] byArray = this.bCp;
        this.dYH = new int[n2];
        this.iN = new Object[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray[n4] != 1) continue;
            int n5 = nArray[n4];
            int n6 = this.pr(n5);
            this.dYH[n6] = n5;
            this.iN[n6] = objectArray[n4];
            this.bCp[n6] = 1;
        }
    }

    public Object get(int n2) {
        int n3 = this.hJ(n2);
        return n3 < 0 ? null : this.iN[n3];
    }

    public void clear() {
        super.clear();
        int[] nArray = this.dYH;
        Object[] objectArray = this.iN;
        byte[] byArray = this.bCp;
        int n2 = nArray.length;
        while (n2-- > 0) {
            nArray[n2] = 0;
            objectArray[n2] = null;
            byArray[n2] = 0;
        }
    }

    public Object remove(int n2) {
        Object object = null;
        int n3 = this.hJ(n2);
        if (n3 >= 0) {
            object = this.iN[n3];
            this.O(n3);
        }
        return object;
    }

    public boolean equals(Object object) {
        if (!(object instanceof lb_0)) {
            return false;
        }
        lb_0 lb_02 = (lb_0)object;
        if (lb_02.size() != this.size()) {
            return false;
        }
        return this.a(new aif_1(lb_02));
    }

    public int hashCode() {
        abl abl2 = new abl(this, null);
        this.a(abl2);
        return abl2.dY();
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

    public int[] pL() {
        int[] nArray = new int[this.size()];
        int[] nArray2 = this.dYH;
        byte[] byArray = this.bCp;
        int n2 = nArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            nArray[n3++] = nArray2[n2];
        }
        return nArray;
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

    public boolean bY(int n2) {
        return this.contains(n2);
    }

    public boolean e(aLR aLR2) {
        return this.a(aLR2);
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

    public boolean a(zD zD2) {
        byte[] byArray = this.bCp;
        int[] nArray = this.dYH;
        Object[] objectArray = this.iN;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || zD2.b(nArray[n2], objectArray[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(zD zD2) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        int[] nArray = this.dYH;
        Object[] objectArray = this.iN;
        this.pf();
        try {
            int n2 = nArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || zD2.b(nArray[n2], objectArray[n2])) continue;
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
            int n3 = objectInput.readInt();
            Object object = objectInput.readObject();
            this.c(n3, object);
        }
    }
}

