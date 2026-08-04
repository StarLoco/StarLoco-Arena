/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*
 * Renamed from ayn
 */
public class ayn_0 {
    private Comparator cIj;

    public ayn_0() {
    }

    public ayn_0(Comparator comparator) {
        this.cIj = comparator;
    }

    public int[] a(ArrayList arrayList, boolean bl2) {
        Object[] objectArray = arrayList.toArray(new Object[arrayList.size()]);
        Arrays.sort(objectArray, this.cIj);
        int[] nArray = new int[arrayList.size()];
        int n2 = objectArray.length;
        for (int j = 0; j < n2; ++j) {
            if (bl2) {
                nArray[j] = arrayList.indexOf(objectArray[j]);
                continue;
            }
            nArray[n2 - j - 1] = arrayList.indexOf(objectArray[j]);
        }
        return nArray;
    }
}

