/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/*
 * Renamed from aiM
 */
public class aim_1
extends ws_2
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient int[] aiN;

    public aim_1() {
    }

    public aim_1(int n2) {
        super(n2);
    }

    public aim_1(int n2, float f) {
        super(n2, f);
    }

    public aim_1(alo_0 alo_02) {
        super(alo_02);
    }

    public aim_1(int n2, alo_0 alo_02) {
        super(n2, alo_02);
    }

    public aim_1(int n2, float f, alo_0 alo_02) {
        super(n2, f, alo_02);
    }

    public Object clone() {
        aim_1 aim_12 = (aim_1)super.clone();
        aim_12.aiN = (int[])this.aiN.clone();
        return aim_12;
    }

    public pk_2 ayx() {
        return new pk_2(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.aiN = new int[n3];
        return n3;
    }

    public int c(byte by, int n2) {
        int n3 = 0;
        int n4 = this.E(by);
        boolean bl2 = true;
        if (n4 < 0) {
            n4 = -n4 - 1;
            n3 = this.aiN[n4];
            bl2 = false;
        }
        byte by2 = this.bCp[n4];
        this.auE[n4] = by;
        this.bCp[n4] = 1;
        this.aiN[n4] = n2;
        if (bl2) {
            this.Z(by2 == 0);
        }
        return n3;
    }

    protected void rehash(int n2) {
        int n3 = this.auE.length;
        byte[] byArray = this.auE;
        int[] nArray = this.aiN;
        byte[] byArray2 = this.bCp;
        this.auE = new byte[n2];
        this.aiN = new int[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray2[n4] != 1) continue;
            byte by = byArray[n4];
            int n5 = this.E(by);
            this.auE[n5] = by;
            this.aiN[n5] = nArray[n4];
            this.bCp[n5] = 1;
        }
    }

    public int aD(byte by) {
        int n2 = this.D(by);
        return n2 < 0 ? 0 : this.aiN[n2];
    }

    public void clear() {
        super.clear();
        byte[] byArray = this.auE;
        int[] nArray = this.aiN;
        byte[] byArray2 = this.bCp;
        int n2 = byArray.length;
        while (n2-- > 0) {
            byArray[n2] = 0;
            nArray[n2] = 0;
            byArray2[n2] = 0;
        }
    }

    public int aE(byte by) {
        int n2 = 0;
        int n3 = this.D(by);
        if (n3 >= 0) {
            n2 = this.aiN[n3];
            this.O(n3);
        }
        return n2;
    }

    public boolean equals(Object object) {
        if (!(object instanceof aim_1)) {
            return false;
        }
        aim_1 aim_12 = (aim_1)object;
        if (aim_12.size() != this.size()) {
            return false;
        }
        return this.a(new D(aim_12));
    }

    public int hashCode() {
        aet_2 aet_22 = new aet_2(this, null);
        this.a(aet_22);
        return aet_22.dY();
    }

    protected void O(int n2) {
        this.aiN[n2] = 0;
        super.O(n2);
    }

    public int[] yb() {
        int[] nArray = new int[this.size()];
        int[] nArray2 = this.aiN;
        byte[] byArray = this.bCp;
        int n2 = nArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            nArray[n3++] = nArray2[n2];
        }
        return nArray;
    }

    public byte[] GF() {
        byte[] byArray = new byte[this.size()];
        byte[] byArray2 = this.auE;
        byte[] byArray3 = this.bCp;
        int n2 = byArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray3[n2] != 1) continue;
            byArray[n3++] = byArray2[n2];
        }
        return byArray;
    }

    public byte[] B(byte[] byArray) {
        int n2 = this.size();
        if (byArray.length < n2) {
            byArray = (byte[])Array.newInstance(byArray.getClass().getComponentType(), n2);
        }
        byte[] byArray2 = this.auE;
        int n3 = byArray2.length;
        int n4 = 0;
        while (n3-- > 0) {
            if (byArray2[n3] == 0 || byArray2[n3] == 2) continue;
            byArray[n4++] = byArray2[n3];
        }
        return byArray;
    }

    public boolean dy(int n2) {
        byte[] byArray = this.bCp;
        int[] nArray = this.aiN;
        int n3 = nArray.length;
        while (n3-- > 0) {
            if (byArray[n3] != 1 || n2 != nArray[n3]) continue;
            return true;
        }
        return false;
    }

    public boolean K(byte by) {
        return this.contains(by);
    }

    public boolean b(amm_2 amm_22) {
        return this.a(amm_22);
    }

    public boolean f(aLR aLR2) {
        byte[] byArray = this.bCp;
        int[] nArray = this.aiN;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || aLR2.eG(nArray[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean a(aca_2 aca_22) {
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.auE;
        int[] nArray = this.aiN;
        int n2 = byArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || aca_22.a(byArray2[n2], nArray[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(aca_2 aca_22) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.auE;
        int[] nArray = this.aiN;
        this.pf();
        try {
            int n2 = byArray2.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || aca_22.a(byArray2[n2], nArray[n2])) continue;
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
        byte[] byArray = this.bCp;
        int[] nArray = this.aiN;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            nArray[n2] = aMV2.pu(nArray[n2]);
        }
    }

    public boolean L(byte by) {
        return this.d(by, 1);
    }

    public boolean d(byte by, int n2) {
        int n3 = this.D(by);
        if (n3 < 0) {
            return false;
        }
        int n4 = n3;
        this.aiN[n4] = this.aiN[n4] + n2;
        return true;
    }

    public int b(byte by, int n2, int n3) {
        boolean bl2;
        int n4;
        int n5 = this.E(by);
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
        byte by2 = this.bCp[n5];
        this.auE[n5] = by;
        this.bCp[n5] = 1;
        if (bl2) {
            this.Z(by2 == 0);
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
            byte by = objectInput.readByte();
            int n3 = objectInput.readInt();
            this.c(by, n3);
        }
    }
}

