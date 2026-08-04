/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.util.Arrays;

/*
 * Renamed from sa
 */
public class sa_1
extends adf_2
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient int[] aiN;

    public sa_1() {
    }

    public sa_1(int n2) {
        super(n2);
    }

    public sa_1(int n2, float f) {
        super(n2, f);
    }

    public sa_1(acw_2 acw_22) {
        super(acw_22);
    }

    public sa_1(int n2, acw_2 acw_22) {
        super(n2, acw_22);
    }

    public sa_1(int n2, float f, acw_2 acw_22) {
        super(n2, f, acw_22);
    }

    public atx_0 xZ() {
        return new atx_0(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.aiN = new int[n3];
        return n3;
    }

    public int b(Object object, int n2) {
        int n3 = 0;
        int n4 = this.aH(object);
        boolean bl2 = true;
        if (n4 < 0) {
            n4 = -n4 - 1;
            n3 = this.aiN[n4];
            bl2 = false;
        }
        Object object2 = this.dxM[n4];
        this.dxM[n4] = object;
        this.aiN[n4] = n2;
        if (bl2) {
            this.Z(object2 == dxP);
        }
        return n3;
    }

    protected void rehash(int n2) {
        int n3 = this.dxM.length;
        Object[] objectArray = this.dxM;
        int[] nArray = this.aiN;
        this.dxM = new Object[n2];
        Arrays.fill(this.dxM, dxP);
        this.aiN = new int[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (objectArray[n4] == dxP || objectArray[n4] == dxO) continue;
            Object object = objectArray[n4];
            int n5 = this.aH(object);
            if (n5 < 0) {
                this.l(this.dxM[-n5 - 1], object);
            }
            this.dxM[n5] = object;
            this.aiN[n5] = nArray[n4];
        }
    }

    public int get(Object object) {
        int n2 = this.index(object);
        return n2 < 0 ? 0 : this.aiN[n2];
    }

    public void clear() {
        super.clear();
        Object[] objectArray = this.dxM;
        int[] nArray = this.aiN;
        int n2 = objectArray.length;
        while (n2-- > 0) {
            objectArray[n2] = dxP;
            nArray[n2] = 0;
        }
    }

    public int G(Object object) {
        int n2 = 0;
        int n3 = this.index(object);
        if (n3 >= 0) {
            n2 = this.aiN[n3];
            this.O(n3);
        }
        return n2;
    }

    public boolean equals(Object object) {
        if (!(object instanceof sa_1)) {
            return false;
        }
        sa_1 sa_12 = (sa_1)object;
        if (sa_12.size() != this.size()) {
            return false;
        }
        return this.a(new ox(sa_12));
    }

    public sa_1 ya() {
        sa_1 sa_12 = (sa_1)super.yc();
        sa_12.aiN = new int[this.aiN.length];
        for (int j = 0; j < sa_12.aiN.length; ++j) {
            sa_12.aiN[j] = this.aiN[j];
        }
        return sa_12;
    }

    protected void O(int n2) {
        this.aiN[n2] = 0;
        super.O(n2);
    }

    public int[] yb() {
        int[] nArray = new int[this.size()];
        int[] nArray2 = this.aiN;
        Object[] objectArray = this.dxM;
        int n2 = nArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (objectArray[n2] == dxP || objectArray[n2] == dxO) continue;
            nArray[n3++] = nArray2[n2];
        }
        return nArray;
    }

    public Object[] keys() {
        Object[] objectArray = new Object[this.size()];
        Object[] objectArray2 = this.dxM;
        int n2 = objectArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (objectArray2[n2] == dxP || objectArray2[n2] == dxO) continue;
            objectArray[n3++] = objectArray2[n2];
        }
        return objectArray;
    }

    public Object[] d(Object[] objectArray) {
        int n2 = this.size();
        if (objectArray.length < n2) {
            objectArray = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), n2);
        }
        Object[] objectArray2 = this.dxM;
        int n3 = objectArray2.length;
        int n4 = 0;
        while (n3-- > 0) {
            if (objectArray2[n3] == dxP || objectArray2[n3] == dxO) continue;
            objectArray[n4++] = objectArray2[n3];
        }
        return objectArray;
    }

    public boolean dy(int n2) {
        Object[] objectArray = this.dxM;
        int[] nArray = this.aiN;
        int n3 = nArray.length;
        while (n3-- > 0) {
            if (objectArray[n3] == dxP || objectArray[n3] == dxO || n2 != nArray[n3]) continue;
            return true;
        }
        return false;
    }

    public boolean containsKey(Object object) {
        return this.contains(object);
    }

    public boolean b(apx apx2) {
        return this.f(apx2);
    }

    public boolean f(aLR aLR2) {
        Object[] objectArray = this.dxM;
        int[] nArray = this.aiN;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (objectArray[n2] == dxP || objectArray[n2] == dxO || aLR2.eG(nArray[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean a(aDN aDN2) {
        Object[] objectArray = this.dxM;
        int[] nArray = this.aiN;
        int n2 = objectArray.length;
        while (n2-- > 0) {
            if (objectArray[n2] == dxP || objectArray[n2] == dxO || aDN2.a(objectArray[n2], nArray[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(aDN aDN2) {
        boolean bl2 = false;
        Object[] objectArray = this.dxM;
        int[] nArray = this.aiN;
        this.pf();
        try {
            int n2 = objectArray.length;
            while (n2-- > 0) {
                if (objectArray[n2] == dxP || objectArray[n2] == dxO || aDN2.a(objectArray[n2], nArray[n2])) continue;
                this.O(n2);
                bl2 = true;
            }
        }
        finally {
            this.Y(true);
        }
        return bl2;
    }

    public void a(aMV aMV2) {
        Object[] objectArray = this.dxM;
        int[] nArray = this.aiN;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (objectArray[n2] == null || objectArray[n2] == dxO) continue;
            nArray[n2] = aMV2.pu(nArray[n2]);
        }
    }

    public boolean H(Object object) {
        return this.c(object, 1);
    }

    public boolean c(Object object, int n2) {
        int n3 = this.index(object);
        if (n3 < 0) {
            return false;
        }
        int n4 = n3;
        this.aiN[n4] = this.aiN[n4] + n2;
        return true;
    }

    public int a(Object object, int n2, int n3) {
        boolean bl2;
        int n4;
        int n5 = this.aH(object);
        if (n5 < 0) {
            int n6 = n5 = -n5 - 1;
            int n7 = this.aiN[n6] + n2;
            this.aiN[n6] = n7;
            n4 = n7;
            bl2 = false;
        } else {
            n4 = this.aiN[n5] = n3;
            bl2 = true;
        }
        Object object2 = this.dxM[n5];
        this.dxM[n5] = object;
        if (bl2) {
            this.Z(object2 == dxP);
        }
        return n4;
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
            Object object = objectInput.readObject();
            int n3 = objectInput.readInt();
            this.b(object, n3);
        }
    }
}

