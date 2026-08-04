/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/*
 * Renamed from zm
 */
public class zm_1
extends us
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient Object[] iN;

    public zm_1() {
    }

    public zm_1(int n2) {
        super(n2);
    }

    public zm_1(int n2, float f) {
        super(n2, f);
    }

    public zm_1(Nh nh) {
        super(nh);
    }

    public zm_1(int n2, Nh nh) {
        super(n2, nh);
    }

    public zm_1(int n2, float f, Nh nh) {
        super(n2, f, nh);
    }

    public zm_1 Gh() {
        zm_1 zm_12 = (zm_1)super.clone();
        zm_12.iN = (Object[])this.iN.clone();
        return zm_12;
    }

    public dk_1 Gi() {
        return new dk_1(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.iN = new Object[n3];
        return n3;
    }

    public Object b(short s, Object object) {
        Object object2 = null;
        int n2 = this.ac(s);
        boolean bl2 = true;
        if (n2 < 0) {
            n2 = -n2 - 1;
            object2 = this.iN[n2];
            bl2 = false;
        }
        byte by = this.bCp[n2];
        this.aqv[n2] = s;
        this.bCp[n2] = 1;
        this.iN[n2] = object;
        if (bl2) {
            this.Z(by == 0);
        }
        return object2;
    }

    protected void rehash(int n2) {
        int n3 = this.aqv.length;
        short[] sArray = this.aqv;
        Object[] objectArray = this.iN;
        byte[] byArray = this.bCp;
        this.aqv = new short[n2];
        this.iN = new Object[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray[n4] != 1) continue;
            short s = sArray[n4];
            int n5 = this.ac(s);
            this.aqv[n5] = s;
            this.iN[n5] = objectArray[n4];
            this.bCp[n5] = 1;
        }
    }

    public Object an(short s) {
        int n2 = this.ab(s);
        return n2 < 0 ? null : this.iN[n2];
    }

    public void clear() {
        super.clear();
        short[] sArray = this.aqv;
        Object[] objectArray = this.iN;
        byte[] byArray = this.bCp;
        int n2 = sArray.length;
        while (n2-- > 0) {
            sArray[n2] = 0;
            objectArray[n2] = null;
            byArray[n2] = 0;
        }
    }

    public Object ao(short s) {
        Object object = null;
        int n2 = this.ab(s);
        if (n2 >= 0) {
            object = this.iN[n2];
            this.O(n2);
        }
        return object;
    }

    public boolean equals(Object object) {
        if (!(object instanceof zm_1)) {
            return false;
        }
        zm_1 zm_12 = (zm_1)object;
        if (zm_12.size() != this.size()) {
            return false;
        }
        return this.a(new ht_0(zm_12));
    }

    public int hashCode() {
        qT qT2 = new qT(this, null);
        this.a(qT2);
        return qT2.dY();
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

    public short[] Gj() {
        short[] sArray = new short[this.size()];
        short[] sArray2 = this.aqv;
        byte[] byArray = this.bCp;
        int n2 = sArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            sArray[n3++] = sArray2[n2];
        }
        return sArray;
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

    public boolean ap(short s) {
        return this.contains(s);
    }

    public boolean f(cj_1 cj_12) {
        return this.a(cj_12);
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

    public boolean a(hm_0 hm_02) {
        byte[] byArray = this.bCp;
        short[] sArray = this.aqv;
        Object[] objectArray = this.iN;
        int n2 = sArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || hm_02.a(sArray[n2], objectArray[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(hm_0 hm_02) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        short[] sArray = this.aqv;
        Object[] objectArray = this.iN;
        this.pf();
        try {
            int n2 = sArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || hm_02.a(sArray[n2], objectArray[n2])) continue;
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
            short s = objectInput.readShort();
            Object object = objectInput.readObject();
            this.b(s, object);
        }
    }
}

