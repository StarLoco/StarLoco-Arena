/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Renamed from cN
 */
public class cn_2 {
    private final HashMap jC = new HashMap();
    private String jD = null;
    private boolean jE = true;
    public os_1 jF = null;

    public void a(os_1 os_12) {
        this.jF = os_12;
    }

    public void a(String string, ayn_0 ayn_02) {
        this.jC.put(string, ayn_02);
        if (this.jD == null) {
            this.jD = string;
        }
    }

    public void clear() {
        this.jC.clear();
        this.jD = null;
        this.jE = true;
    }

    public int[] a(ArrayList arrayList) {
        if (this.jD == null) {
            return null;
        }
        ayn_0 ayn_02 = (ayn_0)this.jC.get(this.jD);
        if (ayn_02 != null) {
            return ayn_02.a(arrayList, this.jE);
        }
        return null;
    }

    public int[] a(ArrayList arrayList, String string) {
        ayn_0 ayn_02;
        assert (string != null) : "columnId == null !";
        this.jE = string.equals(this.jD) ? !this.jE : true;
        this.jD = string;
        if (this.jF != null) {
            this.jF.d(this.jD, this.jE);
        }
        if ((ayn_02 = (ayn_0)this.jC.get(this.jD)) == null) {
            return null;
        }
        return ayn_02.a(arrayList, this.jE);
    }

    public boolean isDirect() {
        return this.jE;
    }

    public static int[] R(int n2) {
        int[] nArray = new int[n2];
        for (int j = 0; j < n2; ++j) {
            nArray[j] = j;
        }
        return nArray;
    }
}

