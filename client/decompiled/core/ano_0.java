/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/*
 * Renamed from aNo
 */
public class ano_0
extends adf_2
implements Externalizable,
Map {
    static final long serialVersionUID = 1L;
    protected transient Object[] iN;

    public ano_0() {
    }

    public ano_0(acw_2 acw_22) {
        super(acw_22);
    }

    public ano_0(int n2) {
        super(n2);
    }

    public ano_0(int n2, acw_2 acw_22) {
        super(n2, acw_22);
    }

    public ano_0(int n2, float f) {
        super(n2, f);
    }

    public ano_0(int n2, float f, acw_2 acw_22) {
        super(n2, f, acw_22);
    }

    public ano_0(Map map) {
        this(map.size());
        this.putAll(map);
    }

    public ano_0(Map map, acw_2 acw_22) {
        this(map.size(), acw_22);
        this.putAll(map);
    }

    public ano_0 aXt() {
        ano_0 ano_02 = (ano_0)super.yc();
        ano_02.iN = (Object[])this.iN.clone();
        return ano_02;
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.iN = new Object[n3];
        return n3;
    }

    public Object put(Object object, Object object2) {
        Object object3 = null;
        int n2 = this.aH(object);
        boolean bl2 = true;
        if (n2 < 0) {
            n2 = -n2 - 1;
            object3 = this.iN[n2];
            bl2 = false;
        }
        Object object4 = this.dxM[n2];
        this.dxM[n2] = object;
        this.iN[n2] = object2;
        if (bl2) {
            this.Z(object4 == dxP);
        }
        return object3;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Map)) {
            return false;
        }
        Map map = (Map)object;
        if (map.size() != this.size()) {
            return false;
        }
        return this.a(new jc_1(map));
    }

    public int hashCode() {
        aCV aCV2 = new aCV(this, null);
        this.a(aCV2);
        return aCV2.dY();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{");
        this.a(new ann_1(this, stringBuffer));
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public boolean b(apx apx2) {
        return this.f(apx2);
    }

    public boolean a(apx apx2) {
        Object[] objectArray = this.iN;
        Object[] objectArray2 = this.dxM;
        int n2 = objectArray.length;
        while (n2-- > 0) {
            if (objectArray2[n2] == dxP || objectArray2[n2] == dxO || apx2.a(objectArray[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean a(nm_1 nm_12) {
        Object[] objectArray = this.dxM;
        Object[] objectArray2 = this.iN;
        int n2 = objectArray.length;
        while (n2-- > 0) {
            if (objectArray[n2] == dxP || objectArray[n2] == dxO || nm_12.i(objectArray[n2], objectArray2[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(nm_1 nm_12) {
        boolean bl2 = false;
        Object[] objectArray = this.dxM;
        Object[] objectArray2 = this.iN;
        this.pf();
        try {
            int n2 = objectArray.length;
            while (n2-- > 0) {
                if (objectArray[n2] == dxP || objectArray[n2] == dxO || nm_12.i(objectArray[n2], objectArray2[n2])) continue;
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
        Object[] objectArray = this.iN;
        Object[] objectArray2 = this.dxM;
        int n2 = objectArray.length;
        while (n2-- > 0) {
            if (objectArray2[n2] == dxP || objectArray2[n2] == dxO) continue;
            objectArray[n2] = ahc2.execute(objectArray[n2]);
        }
    }

    protected void rehash(int n2) {
        int n3 = this.dxM.length;
        Object[] objectArray = this.dxM;
        Object[] objectArray2 = this.iN;
        this.dxM = new Object[n2];
        Arrays.fill(this.dxM, dxP);
        this.iN = new Object[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (objectArray[n4] == dxP || objectArray[n4] == dxO) continue;
            Object object = objectArray[n4];
            int n5 = this.aH(object);
            if (n5 < 0) {
                this.l(this.dxM[-n5 - 1], object);
            }
            this.dxM[n5] = object;
            this.iN[n5] = objectArray2[n4];
        }
    }

    public Object get(Object object) {
        int n2 = this.index(object);
        return n2 < 0 ? null : this.iN[n2];
    }

    public void clear() {
        if (this.size() == 0) {
            return;
        }
        super.clear();
        Object[] objectArray = this.dxM;
        Object[] objectArray2 = this.iN;
        int n2 = objectArray.length;
        while (n2-- > 0) {
            objectArray[n2] = dxP;
            objectArray2[n2] = null;
        }
    }

    public Object remove(Object object) {
        Object object2 = null;
        int n2 = this.index(object);
        if (n2 >= 0) {
            object2 = this.iN[n2];
            this.O(n2);
        }
        return object2;
    }

    protected void O(int n2) {
        this.iN[n2] = null;
        super.O(n2);
    }

    public Collection values() {
        return new aeb(this);
    }

    public Set keySet() {
        return new YA(this);
    }

    public Set entrySet() {
        return new UD(this);
    }

    public boolean containsValue(Object object) {
        Object[] objectArray = this.dxM;
        Object[] objectArray2 = this.iN;
        if (null == object) {
            int n2 = objectArray2.length;
            while (n2-- > 0) {
                if (objectArray[n2] == dxP || objectArray[n2] == dxO || object != objectArray2[n2]) continue;
                return true;
            }
        } else {
            int n3 = objectArray2.length;
            while (n3-- > 0) {
                if (objectArray[n3] == dxP || objectArray[n3] == dxO || object != objectArray2[n3] && !object.equals(objectArray2[n3])) continue;
                return true;
            }
        }
        return false;
    }

    public boolean containsKey(Object object) {
        return this.contains(object);
    }

    public void putAll(Map map) {
        this.ensureCapacity(map.size());
        for (Map.Entry entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
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
            Object object = objectInput.readObject();
            Object object2 = objectInput.readObject();
            this.put(object, object2);
        }
    }
}

