/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/*
 * Renamed from abA
 */
public class aba_0
extends vi_2
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient long[] cis;

    public aba_0() {
    }

    public aba_0(int n2) {
        super(n2);
    }

    public aba_0(int n2, float f) {
        super(n2, f);
    }

    public aba_0(ajd_1 ajd_12) {
        super(ajd_12);
    }

    public aba_0(int n2, ajd_1 ajd_12) {
        super(n2, ajd_12);
    }

    public aba_0(int n2, float f, ajd_1 ajd_12) {
        super(n2, f, ajd_12);
    }

    public Object clone() {
        aba_0 aba_02 = (aba_0)super.clone();
        aba_02.cis = (long[])this.cis.clone();
        return aba_02;
    }

    public tq_2 aqi() {
        return new tq_2(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.cis = new long[n3];
        return n3;
    }

    public long l(long l2, long l3) {
        long l4 = 0L;
        int n2 = this.aO(l2);
        boolean bl2 = true;
        if (n2 < 0) {
            n2 = -n2 - 1;
            l4 = this.cis[n2];
            bl2 = false;
        }
        byte by = this.bCp[n2];
        this.aty[n2] = l2;
        this.bCp[n2] = 1;
        this.cis[n2] = l3;
        if (bl2) {
            this.Z(by == 0);
        }
        return l4;
    }

    protected void rehash(int n2) {
        int n3 = this.aty.length;
        long[] lArray = this.aty;
        long[] lArray2 = this.cis;
        byte[] byArray = this.bCp;
        this.aty = new long[n2];
        this.cis = new long[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray[n4] != 1) continue;
            long l2 = lArray[n4];
            int n5 = this.aO(l2);
            this.aty[n5] = l2;
            this.cis[n5] = lArray2[n4];
            this.bCp[n5] = 1;
        }
    }

    public long du(long l2) {
        int n2 = this.az(l2);
        return n2 < 0 ? 0L : this.cis[n2];
    }

    public void clear() {
        super.clear();
        long[] lArray = this.aty;
        long[] lArray2 = this.cis;
        byte[] byArray = this.bCp;
        int n2 = lArray.length;
        while (n2-- > 0) {
            lArray[n2] = 0L;
            lArray2[n2] = 0L;
            byArray[n2] = 0;
        }
    }

    public long dv(long l2) {
        long l3 = 0L;
        int n2 = this.az(l2);
        if (n2 >= 0) {
            l3 = this.cis[n2];
            this.O(n2);
        }
        return l3;
    }

    public boolean equals(Object object) {
        if (!(object instanceof aba_0)) {
            return false;
        }
        aba_0 aba_02 = (aba_0)object;
        if (aba_02.size() != this.size()) {
            return false;
        }
        return this.a(new dk_2(aba_02));
    }

    public int hashCode() {
        aqx_0 aqx_02 = new aqx_0(this, null);
        this.a(aqx_02);
        return aqx_02.dY();
    }

    protected void O(int n2) {
        this.cis[n2] = 0L;
        super.O(n2);
    }

    public long[] aqj() {
        long[] lArray = new long[this.size()];
        long[] lArray2 = this.cis;
        byte[] byArray = this.bCp;
        int n2 = lArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            lArray[n3++] = lArray2[n2];
        }
        return lArray;
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

    public boolean dw(long l2) {
        byte[] byArray = this.bCp;
        long[] lArray = this.cis;
        int n2 = lArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || l2 != lArray[n2]) continue;
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

    public boolean f(px_1 px_12) {
        byte[] byArray = this.bCp;
        long[] lArray = this.cis;
        int n2 = lArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || px_12.aM(lArray[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean a(sg_1 sg_12) {
        byte[] byArray = this.bCp;
        long[] lArray = this.aty;
        long[] lArray2 = this.cis;
        int n2 = lArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || sg_12.f(lArray[n2], lArray2[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(sg_1 sg_12) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        long[] lArray = this.aty;
        long[] lArray2 = this.cis;
        this.pf();
        try {
            int n2 = lArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || sg_12.f(lArray[n2], lArray2[n2])) continue;
                this.O(n2);
                bl2 = true;
            }
        }
        finally {
            this.Y(true);
        }
        return bl2;
    }

    public void a(aaj_1 aaj_12) {
        byte[] byArray = this.bCp;
        long[] lArray = this.cis;
        int n2 = lArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            lArray[n2] = aaj_12.em(lArray[n2]);
        }
    }

    public boolean bW(long l2) {
        return this.m(l2, 1L);
    }

    public boolean m(long l2, long l3) {
        int n2 = this.az(l2);
        if (n2 < 0) {
            return false;
        }
        int n3 = n2;
        this.cis[n3] = this.cis[n3] + l3;
        return true;
    }

    public long f(long l2, long l3, long l4) {
        boolean bl2;
        long l5;
        int n2 = this.aO(l2);
        if (n2 < 0) {
            int n3 = n2 = -n2 - 1;
            long l6 = this.cis[n3] + l3;
            this.cis[n3] = l6;
            l5 = l6;
            bl2 = false;
        } else {
            l5 = this.cis[n2] = l4;
            bl2 = true;
        }
        byte by = this.bCp[n2];
        this.aty[n2] = l2;
        this.bCp[n2] = 1;
        if (bl2) {
            this.Z(by == 0);
        }
        return l5;
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
            long l3 = objectInput.readLong();
            this.l(l2, l3);
        }
    }
}

