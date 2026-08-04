/*
 * Decompiled with CFR 0.152.
 */
import java.util.LinkedList;
import java.util.List;

/*
 * Renamed from aqU
 */
public class aqu_0 {
    public static String[] h(Throwable throwable) {
        LinkedList linkedList = new LinkedList();
        aqu_0.a(linkedList, throwable, null);
        return linkedList.toArray(new String[0]);
    }

    private static void a(List list, Throwable throwable, StackTraceElement[] stackTraceElementArray) {
        Throwable throwable2;
        StackTraceElement[] stackTraceElementArray2 = throwable.getStackTrace();
        int n2 = aqu_0.a(stackTraceElementArray2, stackTraceElementArray);
        list.add(aqu_0.b(throwable, stackTraceElementArray));
        for (int j = 0; j < stackTraceElementArray2.length - n2; ++j) {
            list.add("\tat " + stackTraceElementArray2[j].toString());
        }
        if (n2 != 0) {
            list.add("\t... " + n2 + " common frames omitted");
        }
        if ((throwable2 = throwable.getCause()) != null) {
            aqu_0.a(list, throwable2, stackTraceElementArray2);
        }
    }

    private static String b(Throwable throwable, StackTraceElement[] stackTraceElementArray) {
        String string = "";
        if (stackTraceElementArray != null) {
            string = "Caused by: ";
        }
        String string2 = string + throwable.getClass().getName();
        if (throwable.getMessage() != null) {
            string2 = string2 + ": " + throwable.getMessage();
        }
        return string2;
    }

    private static int a(StackTraceElement[] stackTraceElementArray, StackTraceElement[] stackTraceElementArray2) {
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
}

