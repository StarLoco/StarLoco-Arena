/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.util.Arrays;

public class sd
extends adf_2
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient float[] aiQ;

    public sd() {
    }

    public sd(int n2) {
        super(n2);
    }

    public sd(int n2, float f) {
        super(n2, f);
    }

    public sd(acw_2 acw_22) {
        super(acw_22);
    }

    public sd(int n2, acw_2 acw_22) {
        super(n2, acw_22);
    }

    public sd(int n2, float f, acw_2 acw_22) {
        super(n2, f, acw_22);
    }

    public agh_1 yd() {
        return new agh_1(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.aiQ = new float[n3];
        return n3;
    }

    public float a(Object object, float f) {
        float f2 = 0.0f;
        int n2 = this.aH(object);
        boolean bl2 = true;
        if (n2 < 0) {
            n2 = -n2 - 1;
            f2 = this.aiQ[n2];
            bl2 = false;
        }
        Object object2 = this.dxM[n2];
        this.dxM[n2] = object;
        this.aiQ[n2] = f;
        if (bl2) {
            this.Z(object2 == dxP);
        }
        return f2;
    }

    protected void rehash(int n2) {
        int n3 = this.dxM.length;
        Object[] objectArray = this.dxM;
        float[] fArray = this.aiQ;
        this.dxM = new Object[n2];
        Arrays.fill(this.dxM, dxP);
        this.aiQ = new float[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (objectArray[n4] == dxP || objectArray[n4] == dxO) continue;
            Object object = objectArray[n4];
            int n5 = this.aH(object);
            if (n5 < 0) {
                this.l(this.dxM[-n5 - 1], object);
            }
            this.dxM[n5] = object;
            this.aiQ[n5] = fArray[n4];
        }
    }

    public float I(Object object) {
        int n2 = this.index(object);
        return n2 < 0 ? 0.0f : this.aiQ[n2];
    }

    public void clear() {
        super.clear();
        Object[] objectArray = this.dxM;
        float[] fArray = this.aiQ;
        int n2 = objectArray.length;
        while (n2-- > 0) {
            objectArray[n2] = dxP;
            fArray[n2] = 0.0f;
        }
    }

    public float J(Object object) {
        float f = 0.0f;
        int n2 = this.index(object);
        if (n2 >= 0) {
            f = this.aiQ[n2];
            this.O(n2);
        }
        return f;
    }

    public boolean equals(Object object) {
        if (!(object instanceof sd)) {
            return false;
        }
        sd sd2 = (sd)object;
        if (sd2.size() != this.size()) {
            return false;
        }
        return this.a(new air_0(sd2));
    }

    public sd ye() {
        sd sd2 = (sd)super.yc();
        sd2.aiQ = new float[this.aiQ.length];
        for (int j = 0; j < sd2.aiQ.length; ++j) {
            sd2.aiQ[j] = this.aiQ[j];
        }
        return sd2;
    }

    protected void O(int n2) {
        this.aiQ[n2] = 0.0f;
        super.O(n2);
    }

    public float[] yf() {
        float[] fArray = new float[this.size()];
        float[] fArray2 = this.aiQ;
        Object[] objectArray = this.dxM;
        int n2 = fArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (objectArray[n2] == dxP || objectArray[n2] == dxO) continue;
            fArray[n3++] = fArray2[n2];
        }
        return fArray;
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

    public boolean Q(float f) {
        Object[] objectArray = this.dxM;
        float[] fArray = this.aiQ;
        int n2 = fArray.length;
        while (n2-- > 0) {
            if (objectArray[n2] == dxP || objectArray[n2] == dxO || f != fArray[n2]) continue;
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

    public boolean e(lw_2 lw_22) {
        Object[] objectArray = this.dxM;
        float[] fArray = this.aiQ;
        int n2 = fArray.length;
        while (n2-- > 0) {
            if (objectArray[n2] == dxP || objectArray[n2] == dxO || lw_22.ag(fArray[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean a(Bg bg) {
        Object[] objectArray = this.dxM;
        float[] fArray = this.aiQ;
        int n2 = objectArray.length;
        while (n2-- > 0) {
            if (objectArray[n2] == dxP || objectArray[n2] == dxO || bg.c(objectArray[n2], fArray[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(Bg bg) {
        boolean bl2 = false;
        Object[] objectArray = this.dxM;
        float[] fArray = this.aiQ;
        this.pf();
        try {
            int n2 = objectArray.length;
            while (n2-- > 0) {
                if (objectArray[n2] == dxP || objectArray[n2] == dxO || bg.c(objectArray[n2], fArray[n2])) continue;
                this.O(n2);
                bl2 = true;
            }
        }
        finally {
            this.Y(true);
        }
        return bl2;
    }

    public void a(PZ pZ) {
        Object[] objectArray = this.dxM;
        float[] fArray = this.aiQ;
        int n2 = fArray.length;
        while (n2-- > 0) {
            if (objectArray[n2] == null || objectArray[n2] == dxO) continue;
            fArray[n2] = pZ.an(fArray[n2]);
        }
    }

    public boolean H(Object object) {
        return this.b(object, 1.0f);
    }

    public boolean b(Object object, float f) {
        int n2 = this.index(object);
        if (n2 < 0) {
            return false;
        }
        int n3 = n2;
        this.aiQ[n3] = this.aiQ[n3] + f;
        return true;
    }

    public float a(Object object, float f, float f2) {
        boolean bl2;
        float f3;
        int n2 = this.aH(object);
        if (n2 < 0) {
            int n3 = n2 = -n2 - 1;
            float f4 = this.aiQ[n3] + f;
            this.aiQ[n3] = f4;
            f3 = f4;
            bl2 = false;
        } else {
            f3 = this.aiQ[n2] = f2;
            bl2 = true;
        }
        Object object2 = this.dxM[n2];
        this.dxM[n2] = object;
        if (bl2) {
            this.Z(object2 == dxP);
        }
        return f3;
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
            float f = objectInput.readFloat();
            this.a(object, f);
        }
    }
}

