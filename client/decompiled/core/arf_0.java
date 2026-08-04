/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * Renamed from arf
 */
public class arf_0
extends Exception {
    private Throwable cPd = null;
    private static final Method cPe = arf_0.aEi();
    static Class cPf;
    static Class cPg;

    static Method aEi() {
        try {
            return (cPg == null ? (cPg = arf_0.a("java.lang.Exception")) : cPg).getDeclaredMethod("initCause", cPf == null ? (cPf = arf_0.a("java.lang.Throwable")) : cPf);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    public arf_0() {
    }

    public arf_0(String string) {
        super(string);
    }

    public arf_0(String string, Throwable throwable) {
        super(string);
        this.initCause(throwable);
    }

    public arf_0(Throwable throwable) {
        super(throwable == null ? null : throwable.getMessage());
        this.initCause(throwable);
    }

    public Throwable initCause(Throwable throwable) {
        if (cPe == null) {
            this.cPd = throwable;
        } else {
            try {
                cPe.invoke(this, throwable);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new RuntimeException("Calling \"initCause()\"");
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new RuntimeException("Calling \"initCause()\"");
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new RuntimeException("Calling \"initCause()\"");
            }
        }
        return this;
    }

    public Throwable getCause() {
        return this.cPd;
    }

    public void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        if (this.cPd == null) {
            return;
        }
        printStream.print("Caused by: ");
        this.cPd.printStackTrace(printStream);
    }

    public void printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
        if (this.cPd == null) {
            return;
        }
        printWriter.print("Caused by: ");
        this.cPd.printStackTrace(printWriter);
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

