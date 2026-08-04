/*
 * Decompiled with CFR 0.152.
 */
import java.util.Arrays;

/*
 * Renamed from aDF
 */
public abstract class adf_2
extends kB
implements acw_2 {
    static final long serialVersionUID = -3461112548087185871L;
    protected transient Object[] dxM;
    protected acw_2 dxN;
    protected static final Object dxO = new Object();
    protected static final Object dxP = new Object();

    public adf_2() {
        this.dxN = this;
    }

    public adf_2(acw_2 acw_22) {
        this.dxN = acw_22;
    }

    public adf_2(int n2) {
        super(n2);
        this.dxN = this;
    }

    public adf_2(int n2, acw_2 acw_22) {
        super(n2);
        this.dxN = acw_22;
    }

    public adf_2(int n2, float f) {
        super(n2, f);
        this.dxN = this;
    }

    public adf_2(int n2, float f, acw_2 acw_22) {
        super(n2, f);
        this.dxN = acw_22;
    }

    public adf_2 yc() {
        adf_2 adf_22 = (adf_2)super.clone();
        adf_22.dxM = (Object[])this.dxM.clone();
        return adf_22;
    }

    protected int capacity() {
        return this.dxM.length;
    }

    protected void O(int n2) {
        this.dxM[n2] = dxO;
        super.O(n2);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.dxM = new Object[n3];
        Arrays.fill(this.dxM, dxP);
        return n3;
    }

    public boolean f(apx apx2) {
        Object[] objectArray = this.dxM;
        int n2 = objectArray.length;
        while (n2-- > 0) {
            if (objectArray[n2] == dxP || objectArray[n2] == dxO || apx2.a(objectArray[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean contains(Object object) {
        return this.index(object) >= 0;
    }

    protected int index(Object object) {
        acw_2 acw_22 = this.dxN;
        Object[] objectArray = this.dxM;
        int n2 = objectArray.length;
        int n3 = acw_22.aG(object) & Integer.MAX_VALUE;
        int n4 = n3 % n2;
        Object object2 = objectArray[n4];
        if (object2 == dxP) {
            return -1;
        }
        if (object2 == dxO || !acw_22.equals(object2, object)) {
            int n5 = 1 + n3 % (n2 - 2);
            do {
                if ((n4 -= n5) >= 0) continue;
                n4 += n2;
            } while ((object2 = objectArray[n4]) != dxP && (object2 == dxO || !this.dxN.equals(object2, object)));
        }
        return object2 == dxP ? -1 : n4;
    }

    protected int aH(Object object) {
        acw_2 acw_22 = this.dxN;
        Object[] objectArray = this.dxM;
        int n2 = objectArray.length;
        int n3 = acw_22.aG(object) & Integer.MAX_VALUE;
        int n4 = n3 % n2;
        Object object2 = objectArray[n4];
        if (object2 == dxP) {
            return n4;
        }
        if (object2 != dxO && acw_22.equals(object2, object)) {
            return -n4 - 1;
        }
        int n5 = 1 + n3 % (n2 - 2);
        if (object2 != dxO) {
            do {
                if ((n4 -= n5) >= 0) continue;
                n4 += n2;
            } while ((object2 = objectArray[n4]) != dxP && object2 != dxO && !acw_22.equals(object2, object));
        }
        if (object2 == dxO) {
            int n6 = n4;
            while (!(object2 == dxP || object2 != dxO && acw_22.equals(object2, object))) {
                if ((n4 -= n5) < 0) {
                    n4 += n2;
                }
                object2 = objectArray[n4];
            }
            return object2 != dxP ? -n4 - 1 : n6;
        }
        return object2 != dxP ? -n4 - 1 : n4;
    }

    public final int aG(Object object) {
        return object == null ? 0 : object.hashCode();
    }

    public final boolean equals(Object object, Object object2) {
        return object == null ? object2 == null : object.equals(object2);
    }

    protected final void l(Object object, Object object2) {
        throw new IllegalArgumentException("Equal objects must have equal hashcodes. During rehashing, Trove discovered that the following two objects claim to be equal (as in java.lang.Object.equals()) but their hashCodes (or those calculated by your TObjectHashingStrategy) are not equal.This violates the general contract of java.lang.Object.hashCode().  See bullet point two in that method's documentation. object #1 =" + object + "; object #2 =" + object2);
    }
}

