/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/*
 * Renamed from PK
 */
public class pk_0
extends vi_2
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient byte[] aFu;

    public pk_0() {
    }

    public pk_0(int n2) {
        super(n2);
    }

    public pk_0(int n2, float f) {
        super(n2, f);
    }

    public pk_0(ajd_1 ajd_12) {
        super(ajd_12);
    }

    public pk_0(int n2, ajd_1 ajd_12) {
        super(n2, ajd_12);
    }

    public pk_0(int n2, float f, ajd_1 ajd_12) {
        super(n2, f, ajd_12);
    }

    public Object clone() {
        pk_0 pk_02 = (pk_0)super.clone();
        pk_02.aFu = (byte[])this.aFu.clone();
        return pk_02;
    }

    public alz_1 acp() {
        return new alz_1(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.aFu = new byte[n3];
        return n3;
    }

    public byte a(long l2, byte by) {
        byte by2 = 0;
        int n2 = this.aO(l2);
        boolean bl2 = true;
        if (n2 < 0) {
            n2 = -n2 - 1;
            by2 = this.aFu[n2];
            bl2 = false;
        }
        byte by3 = this.bCp[n2];
        this.aty[n2] = l2;
        this.bCp[n2] = 1;
        this.aFu[n2] = by;
        if (bl2) {
            this.Z(by3 == 0);
        }
        return by2;
    }

    protected void rehash(int n2) {
        int n3 = this.aty.length;
        long[] lArray = this.aty;
        byte[] byArray = this.aFu;
        byte[] byArray2 = this.bCp;
        this.aty = new long[n2];
        this.aFu = new byte[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray2[n4] != 1) continue;
            long l2 = lArray[n4];
            int n5 = this.aO(l2);
            this.aty[n5] = l2;
            this.aFu[n5] = byArray[n4];
            this.bCp[n5] = 1;
        }
    }

    public byte cn(long l2) {
        int n2 = this.az(l2);
        return n2 < 0 ? (byte)0 : this.aFu[n2];
    }

    public void clear() {
        super.clear();
        long[] lArray = this.aty;
        byte[] byArray = this.aFu;
        byte[] byArray2 = this.bCp;
        int n2 = lArray.length;
        while (n2-- > 0) {
            lArray[n2] = 0L;
            byArray[n2] = 0;
            byArray2[n2] = 0;
        }
    }

    public byte co(long l2) {
        byte by = 0;
        int n2 = this.az(l2);
        if (n2 >= 0) {
            by = this.aFu[n2];
            this.O(n2);
        }
        return by;
    }

    public boolean equals(Object object) {
        if (!(object instanceof pk_0)) {
            return false;
        }
        pk_0 pk_02 = (pk_0)object;
        if (pk_02.size() != this.size()) {
            return false;
        }
        return this.a(new avb(pk_02));
    }

    public int hashCode() {
        anu_0 anu_02 = new anu_0(this, null);
        this.a(anu_02);
        return anu_02.dY();
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

    public long[] h(long[] lArray) {
        int n2 = this.size();
        if (lArray.length < n2) {
            lArray = (long[])Array.newInstance(lArray.getClass().getComponentType(), n2);
        }
        long[] lArray2 = this.aty;
        int n3 = lArray2.length;
        int n4 = 0;
        while (n3-- > 0) {
            if (lArray2[n3] == 0L || lArray2[n3] == 2L) continue;
            lArray[n4++] = lArray2[n3];
        }
        return lArray;
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

    public boolean v(long l2) {
        return this.m(l2);
    }

    public boolean a(px_1 px_12) {
        return this.b(px_12);
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

    public boolean a(asz_0 asz_02) {
        byte[] byArray = this.bCp;
        long[] lArray = this.aty;
        byte[] byArray2 = this.aFu;
        int n2 = lArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || asz_02.c(lArray[n2], byArray2[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(asz_0 asz_02) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        long[] lArray = this.aty;
        byte[] byArray2 = this.aFu;
        this.pf();
        try {
            int n2 = lArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || asz_02.c(lArray[n2], byArray2[n2])) continue;
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

    public boolean bW(long l2) {
        return this.b(l2, (byte)1);
    }

    public boolean b(long l2, byte by) {
        int n2 = this.az(l2);
        if (n2 < 0) {
            return false;
        }
        int n3 = n2;
        this.aFu[n3] = (byte)(this.aFu[n3] + by);
        return true;
    }

    public byte a(long l2, byte by, byte by2) {
        boolean bl2;
        byte by3;
        int n2 = this.aO(l2);
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
        this.aty[n2] = l2;
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
            long l2 = objectInput.readLong();
            byte by = objectInput.readByte();
            this.a(l2, by);
        }
    }
}

