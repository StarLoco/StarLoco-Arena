/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/*
 * Renamed from ano
 */
public class ano_2
extends aMP
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient int[] aiN;

    public ano_2() {
    }

    public ano_2(int n2) {
        super(n2);
    }

    public ano_2(int n2, float f) {
        super(n2, f);
    }

    public ano_2(ui_0 ui_02) {
        super(ui_02);
    }

    public ano_2(int n2, ui_0 ui_02) {
        super(n2, ui_02);
    }

    public ano_2(int n2, float f, ui_0 ui_02) {
        super(n2, f, ui_02);
    }

    public Object clone() {
        ano_2 ano_22 = (ano_2)super.clone();
        ano_22.aiN = (int[])this.aiN.clone();
        return ano_22;
    }

    public hp_0 aCq() {
        return new hp_0(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.aiN = new int[n3];
        return n3;
    }

    public int bz(int n2, int n3) {
        int n4 = 0;
        int n5 = this.pr(n2);
        boolean bl2 = true;
        if (n5 < 0) {
            n5 = -n5 - 1;
            n4 = this.aiN[n5];
            bl2 = false;
        }
        byte by = this.bCp[n5];
        this.dYH[n5] = n2;
        this.bCp[n5] = 1;
        this.aiN[n5] = n3;
        if (bl2) {
            this.Z(by == 0);
        }
        return n4;
    }

    protected void rehash(int n2) {
        int n3 = this.dYH.length;
        int[] nArray = this.dYH;
        int[] nArray2 = this.aiN;
        byte[] byArray = this.bCp;
        this.dYH = new int[n2];
        this.aiN = new int[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray[n4] != 1) continue;
            int n5 = nArray[n4];
            int n6 = this.pr(n5);
            this.dYH[n6] = n5;
            this.aiN[n6] = nArray2[n4];
            this.bCp[n6] = 1;
        }
    }

    public int get(int n2) {
        int n3 = this.hJ(n2);
        return n3 < 0 ? 0 : this.aiN[n3];
    }

    public void clear() {
        super.clear();
        int[] nArray = this.dYH;
        int[] nArray2 = this.aiN;
        byte[] byArray = this.bCp;
        int n2 = nArray.length;
        while (n2-- > 0) {
            nArray[n2] = 0;
            nArray2[n2] = 0;
            byArray[n2] = 0;
        }
    }

    public int bv(int n2) {
        int n3 = 0;
        int n4 = this.hJ(n2);
        if (n4 >= 0) {
            n3 = this.aiN[n4];
            this.O(n4);
        }
        return n3;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ano_2)) {
            return false;
        }
        ano_2 ano_22 = (ano_2)object;
        if (ano_22.size() != this.size()) {
            return false;
        }
        return this.a(new aru_0(ano_22));
    }

    public int hashCode() {
        aub_0 aub_02 = new aub_0(this, null);
        this.a(aub_02);
        return aub_02.dY();
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

    public boolean bY(int n2) {
        return this.contains(n2);
    }

    public boolean e(aLR aLR2) {
        return this.a(aLR2);
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

    public boolean a(vl_1 vl_12) {
        byte[] byArray = this.bCp;
        int[] nArray = this.dYH;
        int[] nArray2 = this.aiN;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || vl_12.ba(nArray[n2], nArray2[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(vl_1 vl_12) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        int[] nArray = this.dYH;
        int[] nArray2 = this.aiN;
        this.pf();
        try {
            int n2 = nArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || vl_12.ba(nArray[n2], nArray2[n2])) continue;
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

    public boolean cm(int n2) {
        return this.bA(n2, 1);
    }

    public boolean bA(int n2, int n3) {
        int n4 = this.hJ(n2);
        if (n4 < 0) {
            return false;
        }
        int n5 = n4;
        this.aiN[n5] = this.aiN[n5] + n3;
        return true;
    }

    public int I(int n2, int n3, int n4) {
        boolean bl2;
        int n5;
        int n6 = this.pr(n2);
        if (n6 < 0) {
            int n7 = n6 = -n6 - 1;
            int n8 = this.aiN[n7] + n3;
            this.aiN[n7] = n8;
            n5 = n8;
            bl2 = false;
        } else {
            n5 = this.aiN[n6] = n4;
            bl2 = true;
        }
        byte by = this.bCp[n6];
        this.dYH[n6] = n2;
        this.bCp[n6] = 1;
        if (bl2) {
            this.Z(by == 0);
        }
        return n5;
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
            int n4 = objectInput.readInt();
            this.bz(n3, n4);
        }
    }
}

