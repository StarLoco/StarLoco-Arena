/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/*
 * Renamed from VY
 */
public class vy_1
extends us
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient byte[] aFu;

    public vy_1() {
    }

    public vy_1(int n2) {
        super(n2);
    }

    public vy_1(int n2, float f) {
        super(n2, f);
    }

    public vy_1(Nh nh) {
        super(nh);
    }

    public vy_1(int n2, Nh nh) {
        super(n2, nh);
    }

    public vy_1(int n2, float f, Nh nh) {
        super(n2, f, nh);
    }

    public Object clone() {
        vy_1 vy_12 = (vy_1)super.clone();
        vy_12.aFu = (byte[])this.aFu.clone();
        return vy_12;
    }

    public Ii aiZ() {
        return new Ii(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.aFu = new byte[n3];
        return n3;
    }

    public byte b(short s, byte by) {
        byte by2 = 0;
        int n2 = this.ac(s);
        boolean bl2 = true;
        if (n2 < 0) {
            n2 = -n2 - 1;
            by2 = this.aFu[n2];
            bl2 = false;
        }
        byte by3 = this.bCp[n2];
        this.aqv[n2] = s;
        this.bCp[n2] = 1;
        this.aFu[n2] = by;
        if (bl2) {
            this.Z(by3 == 0);
        }
        return by2;
    }

    protected void rehash(int n2) {
        int n3 = this.aqv.length;
        short[] sArray = this.aqv;
        byte[] byArray = this.aFu;
        byte[] byArray2 = this.bCp;
        this.aqv = new short[n2];
        this.aFu = new byte[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray2[n4] != 1) continue;
            short s = sArray[n4];
            int n5 = this.ac(s);
            this.aqv[n5] = s;
            this.aFu[n5] = byArray[n4];
            this.bCp[n5] = 1;
        }
    }

    public byte bp(short s) {
        int n2 = this.ab(s);
        return n2 < 0 ? (byte)0 : this.aFu[n2];
    }

    public void clear() {
        super.clear();
        short[] sArray = this.aqv;
        byte[] byArray = this.aFu;
        byte[] byArray2 = this.bCp;
        int n2 = sArray.length;
        while (n2-- > 0) {
            sArray[n2] = 0;
            byArray[n2] = 0;
            byArray2[n2] = 0;
        }
    }

    public byte bq(short s) {
        byte by = 0;
        int n2 = this.ab(s);
        if (n2 >= 0) {
            by = this.aFu[n2];
            this.O(n2);
        }
        return by;
    }

    public boolean equals(Object object) {
        if (!(object instanceof vy_1)) {
            return false;
        }
        vy_1 vy_12 = (vy_1)object;
        if (vy_12.size() != this.size()) {
            return false;
        }
        return this.a(new kp(vy_12));
    }

    public int hashCode() {
        bw_0 bw_02 = new bw_0(this, null);
        this.a(bw_02);
        return bw_02.dY();
    }

    protected void O(int n2) {
        this.aFu[n2] = 0;
        super.O(n2);
    }

    public byte[] GE() {
        byte[] byArray = new byte[this.size()];
        byte[] byArray2 = this.aFu;
        byte[] byArray3 = this.bCp;
        int n2 = byArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray3[n2] != 1) continue;
            byArray[n3++] = byArray2[n2];
        }
        return byArray;
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

    public short[] i(short[] sArray) {
        int n2 = this.size();
        if (sArray.length < n2) {
            sArray = (short[])Array.newInstance(sArray.getClass().getComponentType(), n2);
        }
        short[] sArray2 = this.aqv;
        int n3 = sArray2.length;
        int n4 = 0;
        while (n3-- > 0) {
            if (sArray2[n3] == 0 || sArray2[n3] == 2) continue;
            sArray[n4++] = sArray2[n3];
        }
        return sArray;
    }

    public boolean J(byte by) {
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.aFu;
        int n2 = byArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || by != byArray2[n2]) continue;
            return true;
        }
        return false;
    }

    public boolean ap(short s) {
        return this.contains(s);
    }

    public boolean f(cj_1 cj_12) {
        return this.a(cj_12);
    }

    public boolean c(amm_2 amm_22) {
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.aFu;
        int n2 = byArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || amm_22.aH(byArray2[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean a(aht_0 aht_02) {
        byte[] byArray = this.bCp;
        short[] sArray = this.aqv;
        byte[] byArray2 = this.aFu;
        int n2 = sArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || aht_02.a(sArray[n2], byArray2[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(aht_0 aht_02) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        short[] sArray = this.aqv;
        byte[] byArray2 = this.aFu;
        this.pf();
        try {
            int n2 = sArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || aht_02.a(sArray[n2], byArray2[n2])) continue;
                this.O(n2);
                bl2 = true;
            }
        }
        finally {
            this.Y(true);
        }
        return bl2;
    }

    public void a(aqI aqI2) {
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.aFu;
        int n2 = byArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            byArray2[n2] = aqI2.aQ(byArray2[n2]);
        }
    }

    public boolean br(short s) {
        return this.c(s, (byte)1);
    }

    public boolean c(short s, byte by) {
        int n2 = this.ab(s);
        if (n2 < 0) {
            return false;
        }
        int n3 = n2;
        this.aFu[n3] = (byte)(this.aFu[n3] + by);
        return true;
    }

    public byte a(short s, byte by, byte by2) {
        boolean bl2;
        byte by3;
        int n2 = this.ac(s);
        if (n2 < 0) {
            int n3 = n2 = -n2 - 1;
            byte by4 = (byte)(this.aFu[n3] + by);
            this.aFu[n3] = by4;
            by3 = by4;
            bl2 = false;
        } else {
            by3 = this.aFu[n2] = by2;
            bl2 = true;
        }
        byte by5 = this.bCp[n2];
        this.aqv[n2] = s;
        this.bCp[n2] = 1;
        if (bl2) {
            this.Z(by5 == 0);
        }
        return by3;
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
            byte by = objectInput.readByte();
            this.b(s, by);
        }
    }
}

