/*
 * Decompiled with CFR 0.152.
 */
public class ql {
    public static final void a(String string, Throwable throwable) {
        System.err.println(string);
        System.err.println("Reported exception:");
        throwable.printStackTrace();
    }

    public static final void bC(String string) {
        System.err.println("SLF4J: " + string);
    }
}

