/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;

/*
 * Renamed from aIx
 */
public class aix_0 {
    public static final byte dPS = 1;
    public static final byte dPT = 2;
    public static final byte dPU = 3;
    private short dPV;
    private ArrayList dPW;
    private ArrayList dPX;
    private hD dPY;
    private hD dPZ;
    private hD dQa;
    private hD dQb;

    public void a(int n2, long l2, long l3, byte by, short s, byte by2, int n3) {
        this.a(n2, new hD(l2, l3, by, s, by2, n3));
    }

    private void a(int n2, hD hD2) {
        switch (hD2.kN()) {
            case 0: {
                this.c(n2, hD2);
                break;
            }
            case -1: {
                this.d(n2, hD2);
                break;
            }
            case -2: {
                this.e(n2, hD2);
                break;
            }
            default: {
                this.b(n2, hD2);
            }
        }
    }

    private void b(int n2, hD hD2) {
        ArrayList arrayList;
        if (n2 == 1) {
            if (this.dPW == null) {
                this.dPW = new ArrayList();
            }
            arrayList = this.dPW;
        } else if (n2 == 2) {
            if (this.dPX == null) {
                this.dPX = new ArrayList();
            }
            arrayList = this.dPX;
        } else {
            return;
        }
        arrayList.add(hD2);
        Collections.sort(arrayList, fo_2.OT());
    }

    private void c(int n2, hD hD2) {
        if (n2 == 1) {
            this.dPY = hD2;
        } else if (n2 == 2) {
            this.dPZ = hD2;
        }
    }

    private void d(int n2, hD hD2) {
        this.dQa = hD2;
    }

    private void e(int n2, hD hD2) {
        this.dQb = hD2;
    }

    public ArrayList aUY() {
        return this.dPW;
    }

    public ArrayList aUZ() {
        return this.dPX;
    }

    public hD aVa() {
        return this.dPY;
    }

    public hD aVb() {
        return this.dPZ;
    }

    public hD aVc() {
        return this.dQa;
    }

    public hD aVd() {
        return this.dQb;
    }

    public short aVe() {
        return this.dPV;
    }

    public void cs(short s) {
        this.dPV = s;
    }

    final void b(acf acf2) {
        hD hD2;
        int n2;
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/PlayListData.load must not be null");
        }
        this.dPV = acf2.readShort();
        int n3 = acf2.readShort();
        for (n2 = 0; n2 < n3; ++n2) {
            hD2 = new hD();
            hD2.b(acf2);
            this.a(1, hD2);
        }
        n3 = acf2.readShort();
        for (n2 = 0; n2 < n3; ++n2) {
            hD2 = new hD();
            hD2.b(acf2);
            this.a(2, hD2);
        }
        n3 = acf2.readShort();
        for (n2 = 0; n2 < n3; ++n2) {
            hD2 = new hD();
            hD2.b(acf2);
            this.a(3, hD2);
        }
    }

    final void a(aij_1 aij_12) {
        int n2;
        int n3;
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/PlayListData.save must not be null");
        }
        aij_12.writeShort(this.dPV);
        short s = 0;
        if (this.dPW != null) {
            s = (short)(s + (short)this.dPW.size());
        }
        if (this.dPY != null) {
            s = (short)(s + 1);
        }
        aij_12.writeShort(s);
        if (this.dPW != null) {
            n3 = this.dPW.size();
            for (n2 = 0; n2 < n3; ++n2) {
                ((hD)this.dPW.get(n2)).a(aij_12);
            }
        }
        if (this.dPY != null) {
            this.dPY.a(aij_12);
        }
        s = 0;
        if (this.dPX != null) {
            s = (short)(s + (short)this.dPX.size());
        }
        if (this.dPZ != null) {
            s = (short)(s + 1);
        }
        aij_12.writeShort(s);
        if (this.dPX != null) {
            n3 = this.dPX.size();
            for (n2 = 0; n2 < n3; ++n2) {
                ((hD)this.dPX.get(n2)).a(aij_12);
            }
        }
        if (this.dPZ != null) {
            this.dPZ.a(aij_12);
        }
        s = (short)((this.dQa != null ? 1 : 0) + (this.dQb != null ? 1 : 0));
        aij_12.writeShort(s);
        if (this.dQa != null) {
            this.dQa.a(aij_12);
        }
        if (this.dQb != null) {
            this.dQb.a(aij_12);
        }
    }

    public boolean c(aix_0 aix_02) {
        int n2;
        int n3;
        if (aix_02 == this) {
            return true;
        }
        if (aix_02.dPW != null && this.dPW != null) {
            if (aix_02.dPW.size() != this.dPW.size()) {
                return false;
            }
            n3 = this.dPW.size();
            for (n2 = 0; n2 < n3; ++n2) {
                if (((hD)this.dPW.get(n2)).equals(aix_02.dPW.get(n2))) continue;
                return false;
            }
        } else if (aix_02.dPW != null || this.dPW != null) {
            return false;
        }
        if (aix_02.dPX != null && this.dPX != null) {
            if (aix_02.dPX.size() != this.dPX.size()) {
                return false;
            }
            n3 = this.dPX.size();
            for (n2 = 0; n2 < n3; ++n2) {
                if (((hD)this.dPX.get(n2)).equals(aix_02.dPX.get(n2))) continue;
                return false;
            }
        } else if (aix_02.dPX != null || this.dPX != null) {
            return false;
        }
        if (this.dPY != null && aix_02.dPY != null ? !this.dPY.equals(aix_02.dPY) : this.dPY != null || aix_02.dPY != null) {
            return false;
        }
        if (this.dPZ != null && aix_02.dPZ != null ? !this.dPZ.equals(aix_02.dPZ) : this.dPZ != null || aix_02.dPZ != null) {
            return false;
        }
        if (this.dQa != null && aix_02.dQa != null ? !this.dQa.equals(aix_02.dQa) : this.dQa != null || aix_02.dQa != null) {
            return false;
        }
        return !(this.dQb != null && aix_02.dQb != null ? !this.dQb.equals(aix_02.dQb) : this.dQb != null || aix_02.dQb != null);
    }
}

