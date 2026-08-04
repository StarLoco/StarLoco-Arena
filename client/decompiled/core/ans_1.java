/*
 * Decompiled with CFR 0.152.
 */
import java.util.Stack;

/*
 * Renamed from aNs
 */
public class ans_1
extends Stack {
    private static final long serialVersionUID = -5555522620060077046L;

    public static ans_1 a(Stack stack) {
        if (stack instanceof ans_1) {
            return (ans_1)stack;
        }
        ans_1 ans_12 = new ans_1();
        if (stack != null) {
            ans_12.addAll(stack);
        }
        return ans_12;
    }

    public ans_1() {
    }

    public ans_1(Object object) {
        this.push(object);
    }

    public synchronized boolean contains(Object object) {
        return this.indexOf(object) >= 0;
    }

    public synchronized int indexOf(Object object, int n2) {
        for (int j = n2; j < this.size(); ++j) {
            if (this.get(j) != object) continue;
            return j;
        }
        return -1;
    }

    public synchronized int lastIndexOf(Object object, int n2) {
        for (int j = n2; j >= 0; --j) {
            if (this.get(j) != object) continue;
            return j;
        }
        return -1;
    }
}

