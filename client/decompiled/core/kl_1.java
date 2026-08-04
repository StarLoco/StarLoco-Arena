/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/*
 * Renamed from Kl
 */
public class kl_1
extends vi_2
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient short[] Ol;

    public kl_1() {
    }

    public kl_1(int n2) {
        super(n2);
    }

    public kl_1(int n2, float f) {
        super(n2, f);
    }

    public kl_1(ajd_1 ajd_12) {
        super(ajd_12);
    }

    public kl_1(int n2, ajd_1 ajd_12) {
        super(n2, ajd_12);
    }

    public kl_1(int n2, float f, ajd_1 ajd_12) {
        super(n2, f, ajd_12);
    }

    public Object clone() {
        kl_1 kl_12 = (kl_1)super.clone();
        kl_12.Ol = (short[])this.Ol.clone();
        return kl_12;
    }

    public aye WB() {
        return new aye(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.Ol = new short[n3];
        return n3;
    }

    public short h(long l2, short s) {
        short s2 = 0;
        int n2 = this.aO(l2);
        boolean bl2 = true;
        if (n2 < 0) {
            n2 = -n2 - 1;
            s2 = this.Ol[n2];
            bl2 = false;
        }
        byte by = this.bCp[n2];
        this.aty[n2] = l2;
        this.bCp[n2] = 1;
        this.Ol[n2] = s;
        if (bl2) {
            this.Z(by == 0);
        }
        return s2;
    }

    protected void rehash(int n2) {
        int n3 = this.aty.length;
        long[] lArray = this.aty;
        short[] sArray = this.Ol;
        byte[] byArray = this.bCp;
        this.aty = new long[n2];
        this.Ol = new short[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray[n4] != 1) continue;
            long l2 = lArray[n4];
            int n5 = this.aO(l2);
            this.aty[n5] = l2;
            this.Ol[n5] = sArray[n4];
            this.bCp[n5] = 1;
        }
    }

    public short bU(long l2) {
        int n2 = this.az(l2);
        return n2 < 0 ? (short)0 : this.Ol[n2];
    }

    public void clear() {
        super.clear();
        long[] lArray = this.aty;
        short[] sArray = this.Ol;
        byte[] byArray = this.bCp;
        int n2 = lArray.length;
        while (n2-- > 0) {
            lArray[n2] = 0L;
            sArray[n2] = 0;
            byArray[n2] = 0;
        }
    }

    public short bV(long l2) {
        short s = 0;
        int n2 = this.az(l2);
        if (n2 >= 0) {
            s = this.Ol[n2];
            this.O(n2);
        }
        return s;
    }

    public boolean equals(Object object) {
        if (!(object instanceof kl_1)) {
            return false;
        }
        kl_1 kl_12 = (kl_1)object;
        if (kl_12.size() != this.size()) {
            return false;
        }
        return this.a(new xa_1(kl_12));
    }

    public int hashCode() {
        apw_0 apw_02 = new apw_0(this, null);
        this.a(apw_02);
        return apw_02.dY();
    }

    protected void O(int n2) {
        this.Ol[n2] = 0;
        super.O(n2);
    }

    public short[] getValues() {
        short[] sArray = new short[this.size()];
        short[] sArray2 = this.Ol;
        byte[] byArray = this.bCp;
        int n2 = sArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            sArray[n3++] = sArray2[n2];
        }
        return sArray;
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

    public boolean J(short s) {
        byte[] byArray = this.bCp;
        short[] sArray = this.Ol;
        int n2 = sArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || s != sArray[n2]) continue;
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

    public boolean e(cj_1 cj_12) {
        byte[] byArray = this.bCp;
        short[] sArray = this.Ol;
        int n2 = sArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || cj_12.aq(sArray[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean a(ro_1 ro_12) {
        byte[] byArray = this.bCp;
        long[] lArray = this.aty;
        short[] sArray = this.Ol;
        int n2 = lArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || ro_12.g(lArray[n2], sArray[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(ro_1 ro_12) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        long[] lArray = this.aty;
        short[] sArray = this.Ol;
        this.pf();
        try {
            int n2 = lArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || ro_12.g(lArray[n2], sArray[n2])) continue;
                this.O(n2);
                bl2 = true;
            }
        }
        finally {
            this.Y(true);
        }
        return bl2;
    }

    public void a(apk_1 apk_12) {
        byte[] byArray = this.bCp;
        short[] sArray = this.Ol;
        int n2 = sArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            sArray[n2] = apk_12.bR(sArray[n2]);
        }
    }

    public boolean bW(long l2) {
        return this.i(l2, (short)1);
    }

    public boolean i(long l2, short s) {
        int n2 = this.az(l2);
        if (n2 < 0) {
            return false;
        }
        int n3 = n2;
        this.Ol[n3] = (short)(this.Ol[n3] + s);
        return true;
    }

    public short a(long l2, short s, short s2) {
        boolean bl2;
        short s3;
        int n2 = this.aO(l2);
        if (n2 < 0) {
            int n3 = n2 = -n2 - 1;
            short s4 = (short)(this.Ol[n3] + s);
            this.Ol[n3] = s4;
            s3 = s4;
            bl2 = false;
        } else {
            s3 = this.Ol[n2] = s2;
            bl2 = true;
        }
        byte by = this.bCp[n2];
        this.aty[n2] = l2;
        this.bCp[n2] = 1;
        if (bl2) {
            this.Z(by == 0);
        }
        return s3;
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
            short s = objectInput.readShort();
            this.h(l2, s);
        }
    }
}

