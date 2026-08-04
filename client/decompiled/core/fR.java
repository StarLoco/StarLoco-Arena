/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

class fR
implements Comparator {
    private static fR rX = new fR();

    private fR() {
    }

    public int a(qa_1 qa_12, qa_1 qa_13) {
        return qa_12.getTreeDepth() - qa_13.getTreeDepth();
    }

    static /* synthetic */ fR jp() {
        return rX;
    }
}

