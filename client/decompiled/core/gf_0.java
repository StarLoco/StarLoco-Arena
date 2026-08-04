/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

/*
 * Renamed from gf
 */
public class gf_0 {
    private final Set sg = new HashSet();
    private final boolean sh;
    private static final Logger a = Logger.getLogger(gf_0.class);
    private final ry si = new ry();
    private final ry sj = new ry();

    public gf_0(Iterable iterable, boolean bl2) {
        for (int[] nArray : iterable) {
            this.sg.add(new ry(nArray[0], nArray[1]));
        }
        this.sh = bl2;
    }

    public Iterable a(int n2, int n3, short s, int n4, int n5, short s2, ye_0 ye_02) {
        aEZ aEZ2 = this.a(n2, n3, n4, n5, ye_02);
        ArrayList<int[]> arrayList = new ArrayList<int[]>(this.sg.size());
        for (ry ry2 : this.sg) {
            arrayList.add(aEZ2.b(ry2.getX(), ry2.getY()));
        }
        return arrayList;
    }

    public boolean a(int n2, int n3, short s, int n4, int n5, short s2, ye_0 ye_02, int n6, int n7, short s3) {
        aEZ aEZ2 = this.a(n2, n3, n4, n5, ye_02);
        this.si.l(aEZ2.c(n6, n7));
        return this.sg.contains(this.si);
    }

    public boolean a(int n2, int n3, short s, int n4, int n5, short s2, ye_0 ye_02, int n6, int n7, short s3, byte n8) {
        aEZ aEZ2 = this.a(n2, n3, n4, n5, ye_02);
        this.si.l(aEZ2.c(n6, n7));
        if (n8 <= 0) {
            return this.sg.contains(this.si);
        }
        for (int j = -n8; j <= n8; ++j) {
            for (int i2 = -n8; i2 <= n8; ++i2) {
                this.sj.l(this.si.getX() + j, this.si.getY() + i2, (short)0);
                if (!this.sg.contains(this.sj)) continue;
                return true;
            }
        }
        return false;
    }

    private aEZ a(int n2, int n3, int n4, int n5, ye_0 ye_02) {
        qc_0 qc_02 = new aby_2(n2 - n4, n3 - n5, 0).e(ye_02);
        return aEZ.a(n2, n3, qc_02, this.sh);
    }
}

