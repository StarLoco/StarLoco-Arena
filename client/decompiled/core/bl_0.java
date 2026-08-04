/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from bL
 */
public final class bl_0 {
    private static final int hr;
    private static final String[] hs;
    private static final String[] ht;
    public static boolean hu;

    private bl_0() {
    }

    public static String b(Throwable throwable) {
        return bl_0.a(throwable, Integer.MAX_VALUE);
    }

    public static String a(Throwable throwable, int n2) {
        if (throwable == null) {
            return " [null Throwable in ExceptionFormatter.toString()]";
        }
        StackTraceElement[] stackTraceElementArray = throwable.getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(' ').append(throwable.toString());
        if (stackTraceElementArray.length == 0) {
            stringBuilder.append("emptyStacktrace, current is ").append(bl_0.B(n2));
        } else {
            stringBuilder.append(bl_0.a(stackTraceElementArray, 0, n2));
        }
        if (throwable.getCause() != null) {
            stringBuilder.append(" caused by ").append(bl_0.a(throwable.getCause(), n2));
        }
        return stringBuilder.toString();
    }

    public static String dH() {
        return bl_0.d(1, Integer.MAX_VALUE);
    }

    public static String B(int n2) {
        return bl_0.d(1, n2);
    }

    public static String d(int n2, int n3) {
        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        return bl_0.a(stackTraceElementArray, 2 + hr + n2, n3);
    }

    private static String a(StackTraceElement[] stackTraceElementArray, int n2, int n3) {
        StringBuilder stringBuilder = new StringBuilder("stackTrace=");
        bl_0.a(stringBuilder, stackTraceElementArray, n2, n3, ' ');
        return stringBuilder.toString();
    }

    private static void a(StringBuilder stringBuilder, StackTraceElement[] stackTraceElementArray, int n2, int n3, char c) {
        for (int j = n2; j < stackTraceElementArray.length && j - n2 < n3; ++j) {
            bl_0.a(stringBuilder, stackTraceElementArray[j], c);
        }
    }

    private static StringBuilder a(StringBuilder stringBuilder, StackTraceElement stackTraceElement, char c) {
        return stringBuilder.append(' ').append(bl_0.u(stackTraceElement.getClassName())).append('.').append(stackTraceElement.getMethodName()).append('(').append(stackTraceElement.getFileName()).append(':').append(stackTraceElement.getLineNumber()).append(')').append(c);
    }

    static String u(String string) {
        if (!hu) {
            return string;
        }
        String string2 = bl_0.v(string);
        for (int j = 0; j < hs.length; ++j) {
            String string3 = hs[j];
            if (!string.startsWith(string3)) continue;
            string2 = '[' + ht[j] + ']' + string2;
            break;
        }
        return string2;
    }

    private static String v(String string) {
        int n2 = string.lastIndexOf(46);
        return n2 == -1 ? string : string.substring(n2 + 1);
    }

    static {
        String string = Thread.currentThread().getStackTrace()[0].getMethodName();
        hr = "getStackTrace".equals(string) ? 0 : 1;
        hs = new String[]{"com.ankamagames.wakfu.server.game", "com.ankamagames.wakfu.server.ia", "com.ankamagames.wakfu.server", "com.ankamagames.wakfu.client", "com.ankamagames.wakfu.common", "com.ankamagames.baseImpl", "com.ankamagames.framework"};
        ht = new String[]{"GAME", "IA", "SRV", "CLI", "COMM", "BIMP", "FWK"};
        hu = true;
    }
}

