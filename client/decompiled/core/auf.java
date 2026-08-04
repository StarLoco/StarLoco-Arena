/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

public class auf
extends aMP
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient long[] cis;

    public auf() {
    }

    public auf(int n2) {
        super(n2);
    }

    public auf(int n2, float f) {
        super(n2, f);
    }

    public auf(ui_0 ui_02) {
        super(ui_02);
    }

    public auf(int n2, ui_0 ui_02) {
        super(n2, ui_02);
    }

    public auf(int n2, float f, ui_0 ui_02) {
        super(n2, f, ui_02);
    }

    public Object clone() {
        auf auf2 = (auf)super.clone();
        auf2.cis = (long[])this.cis.clone();
        return auf2;
    }

    public aiw_0 aHl() {
        return new aiw_0(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.cis = new long[n3];
        return n3;
    }

    public long k(int n2, long l2) {
        long l3 = 0L;
        int n3 = this.pr(n2);
        boolean bl2 = true;
        if (n3 < 0) {
            n3 = -n3 - 1;
            l3 = this.cis[n3];
            bl2 = false;
        }
        byte by = this.bCp[n3];
        this.dYH[n3] = n2;
        this.bCp[n3] = 1;
        this.cis[n3] = l2;
        if (bl2) {
            this.Z(by == 0);
        }
        return l3;
    }

    protected void rehash(int n2) {
        int n3 = this.dYH.length;
        int[] nArray = this.dYH;
        long[] lArray = this.cis;
        byte[] byArray = this.bCp;
        this.dYH = new int[n2];
        this.cis = new long[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray[n4] != 1) continue;
            int n5 = nArray[n4];
            int n6 = this.pr(n5);
            this.dYH[n6] = n5;
            this.cis[n6] = lArray[n4];
            this.bCp[n6] = 1;
        }
    }

    public long get(int n2) {
        int n3 = this.hJ(n2);
        return n3 < 0 ? 0L : this.cis[n3];
    }

    public void clear() {
        super.clear();
        int[] nArray = this.dYH;
        long[] lArray = this.cis;
        byte[] byArray = this.bCp;
        int n2 = nArray.length;
        while (n2-- > 0) {
            nArray[n2] = 0;
            lArray[n2] = 0L;
            byArray[n2] = 0;
        }
    }

    public long remove(int n2) {
        long l2 = 0L;
        int n3 = this.hJ(n2);
        if (n3 >= 0) {
            l2 = this.cis[n3];
            this.O(n3);
        }
        return l2;
    }

    public boolean equals(Object object) {
        if (!(object instanceof auf)) {
            return false;
        }
        auf auf2 = (auf)object;
        if (auf2.size() != this.size()) {
            return false;
        }
        return this.a(new kj_2(auf2));
    }

    public int hashCode() {
        rj_0 rj_02 = new rj_0(this, null);
        this.a(rj_02);
        return rj_02.dY();
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

    public int[] pL() {
        int[] nArray = new int[this.size()];
        int[] nArray2 = this.dYH;
        byte[] byArray = this.bCp;
        int n2 = nArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            nArray[n3++] = nArray2[n2];
        }
        return nArray;
    }

    public int[] h(int[] nArray) {
        int n2 = this.size();
        if (nArray.length < n2) {
            nArray = (int[])Array.newInstance(nArray.getClass().getComponentType(), n2);
        }
        int[] nArray2 = this.dYH;
        int n3 = nArray2.length;
        int n4 = 0;
        while (n3-- > 0) {
            if (nArray2[n3] == 0 || nArray2[n3] == 2) continue;
            nArray[n4++] = nArray2[n3];
        }
        return nArray;
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

    public boolean bY(int n2) {
        return this.contains(n2);
    }

    public boolean e(aLR aLR2) {
        return this.a(aLR2);
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

    public boolean a(xf xf2) {
        byte[] byArray = this.bCp;
        int[] nArray = this.dYH;
        long[] lArray = this.cis;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || xf2.c(nArray[n2], lArray[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(xf xf2) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        int[] nArray = this.dYH;
        long[] lArray = this.cis;
        this.pf();
        try {
            int n2 = nArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || xf2.c(nArray[n2], lArray[n2])) continue;
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

    public boolean cm(int n2) {
        return this.l(n2, 1L);
    }

    public boolean l(int n2, long l2) {
        int n3 = this.hJ(n2);
        if (n3 < 0) {
            return false;
        }
        int n4 = n3;
        this.cis[n4] = this.cis[n4] + l2;
        return true;
    }

    public long a(int n2, long l2, long l3) {
        boolean bl2;
        long l4;
        int n3 = this.pr(n2);
        if (n3 < 0) {
            int n4 = n3 = -n3 - 1;
            long l5 = this.cis[n4] + l2;
            this.cis[n4] = l5;
            l4 = l5;
            bl2 = false;
        } else {
            l4 = this.cis[n3] = l3;
            bl2 = true;
        }
        byte by = this.bCp[n3];
        this.dYH[n3] = n2;
        this.bCp[n3] = 1;
        if (bl2) {
            this.Z(by == 0);
        }
        return l4;
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
            int n3 = objectInput.readInt();
            long l2 = objectInput.readLong();
            this.k(n3, l2);
        }
    }
}

