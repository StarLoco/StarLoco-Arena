/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/*
 * Renamed from cp
 */
public class cp_2
extends vi_2
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient Object[] iN;

    public cp_2() {
    }

    public cp_2(int n2) {
        super(n2);
    }

    public cp_2(int n2, float f) {
        super(n2, f);
    }

    public cp_2(ajd_1 ajd_12) {
        super(ajd_12);
    }

    public cp_2(int n2, ajd_1 ajd_12) {
        super(n2, ajd_12);
    }

    public cp_2(int n2, float f, ajd_1 ajd_12) {
        super(n2, f, ajd_12);
    }

    public cp_2 eH() {
        cp_2 cp_22 = (cp_2)super.clone();
        cp_22.iN = (Object[])this.iN.clone();
        return cp_22;
    }

    public akz_0 eI() {
        return new akz_0(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.iN = new Object[n3];
        return n3;
    }

    public Object a(long l2, Object object) {
        Object object2 = null;
        int n2 = this.aO(l2);
        boolean bl2 = true;
        if (n2 < 0) {
            n2 = -n2 - 1;
            object2 = this.iN[n2];
            bl2 = false;
        }
        byte by = this.bCp[n2];
        this.aty[n2] = l2;
        this.bCp[n2] = 1;
        this.iN[n2] = object;
        if (bl2) {
            this.Z(by == 0);
        }
        return object2;
    }

    protected void rehash(int n2) {
        int n3 = this.aty.length;
        long[] lArray = this.aty;
        Object[] objectArray = this.iN;
        byte[] byArray = this.bCp;
        this.aty = new long[n2];
        this.iN = new Object[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray[n4] != 1) continue;
            long l2 = lArray[n4];
            int n5 = this.aO(l2);
            this.aty[n5] = l2;
            this.iN[n5] = objectArray[n4];
            this.bCp[n5] = 1;
        }
    }

    public Object t(long l2) {
        int n2 = this.az(l2);
        return n2 < 0 ? null : this.iN[n2];
    }

    public void clear() {
        super.clear();
        long[] lArray = this.aty;
        Object[] objectArray = this.iN;
        byte[] byArray = this.bCp;
        int n2 = lArray.length;
        while (n2-- > 0) {
            lArray[n2] = 0L;
            objectArray[n2] = null;
            byArray[n2] = 0;
        }
    }

    public Object u(long l2) {
        Object object = null;
        int n2 = this.az(l2);
        if (n2 >= 0) {
            object = this.iN[n2];
            this.O(n2);
        }
        return object;
    }

    public boolean equals(Object object) {
        if (!(object instanceof cp_2)) {
            return false;
        }
        cp_2 cp_22 = (cp_2)object;
        if (cp_22.size() != this.size()) {
            return false;
        }
        return this.a(new eo_0(cp_22));
    }

    public int hashCode() {
        agr_0 agr_02 = new agr_0(this, null);
        this.a(agr_02);
        return agr_02.dY();
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

    public long[] eJ() {
        long[] lArray = new long[this.size()];
        long[] lArray2 = this.aty;
        byte[] byArray = this.bCp;
        int n2 = lArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            lArray[n3++] = lArray2[n2];
        }
        return lArray;
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

    public boolean v(long l2) {
        return this.m(l2);
    }

    public boolean a(px_1 px_12) {
        return this.b(px_12);
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

    public boolean a(aoU aoU2) {
        byte[] byArray = this.bCp;
        long[] lArray = this.aty;
        Object[] objectArray = this.iN;
        int n2 = lArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || aoU2.b(lArray[n2], objectArray[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(aoU aoU2) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        long[] lArray = this.aty;
        Object[] objectArray = this.iN;
        this.pf();
        try {
            int n2 = lArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || aoU2.b(lArray[n2], objectArray[n2])) continue;
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
            long l2 = objectInput.readLong();
            Object object = objectInput.readObject();
            this.a(l2, object);
        }
    }
}

