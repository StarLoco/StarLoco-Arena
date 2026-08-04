/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from tk
 */
public class tk_2 {
    static int a(StackTraceElement[] stackTraceElementArray, StackTraceElement[] stackTraceElementArray2) {
        if (stackTraceElementArray2 == null) {
            return 0;
        }
        int n2 = stackTraceElementArray.length - 1;
        int n3 = 0;
        for (int j = stackTraceElementArray2.length - 1; n2 >= 0 && j >= 0 && stackTraceElementArray[n2].equals(stackTraceElementArray2[j]); --n2, --j) {
            ++n3;
        }
        return n3;
    }

    static int a(StackTraceElement[] stackTraceElementArray, pj_1[] pj_1Array) {
        if (pj_1Array == null) {
            return 0;
        }
        int n2 = stackTraceElementArray.length - 1;
        int n3 = 0;
        for (int j = pj_1Array.length - 1; n2 >= 0 && j >= 0 && stackTraceElementArray[n2].equals(pj_1Array[j].acp); --n2, --j) {
            ++n3;
        }
        return n3;
    }
}

