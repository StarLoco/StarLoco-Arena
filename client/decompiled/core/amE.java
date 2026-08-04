/*
 * Decompiled with CFR 0.152.
 */
import java.util.LinkedList;
import java.util.List;

public class amE {
    static final un_1[] cHO = new un_1[0];

    public static un_1[] g(Throwable throwable) {
        LinkedList linkedList = new LinkedList();
        amE.a(linkedList, throwable, null);
        return linkedList.toArray(cHO);
    }

    private static void a(List list, Throwable throwable, StackTraceElement[] stackTraceElementArray) {
        Throwable throwable2;
        StackTraceElement[] stackTraceElementArray2 = throwable.getStackTrace();
        int n2 = tk_2.a(stackTraceElementArray2, stackTraceElementArray);
        list.add(amE.a(throwable, stackTraceElementArray));
        for (int j = 0; j < stackTraceElementArray2.length - n2; ++j) {
            list.add(new un_1(stackTraceElementArray2[j]));
        }
        if (n2 != 0) {
            list.add(new un_1("\t... " + n2 + " common frames omitted"));
        }
        if ((throwable2 = throwable.getCause()) != null) {
            amE.a(list, throwable2, stackTraceElementArray2);
        }
    }

    private static un_1 a(Throwable throwable, StackTraceElement[] stackTraceElementArray) {
        String string = "";
        if (stackTraceElementArray != null) {
            string = "Caused by: ";
        }
        String string2 = string + throwable.getClass().getName();
        if (throwable.getMessage() != null) {
            string2 = string2 + ": " + throwable.getMessage();
        }
        return new un_1(string2);
    }
}

