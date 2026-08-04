/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintStream;
import java.io.PrintWriter;

/*
 * Renamed from eQ
 */
public class eq_2
extends RuntimeException {
    private static final long serialVersionUID = -5419014565354664240L;
    private Throwable pV;
    private axc_0 pW = axc_0.diY;

    public eq_2() {
    }

    public eq_2(String string) {
        super(string);
    }

    public eq_2(String string, Throwable throwable) {
        super(string);
        this.pV = throwable;
    }

    public eq_2(String string, Throwable throwable, axc_0 axc_02) {
        this(string, throwable);
        this.pW = axc_02;
    }

    public eq_2(Throwable throwable) {
        super(throwable.toString());
        this.pV = throwable;
    }

    public eq_2(String string, axc_0 axc_02) {
        super(string);
        this.pW = axc_02;
    }

    public eq_2(Throwable throwable, axc_0 axc_02) {
        this(throwable);
        this.pW = axc_02;
    }

    public Throwable getException() {
        return this.pV;
    }

    public Throwable getCause() {
        return this.getException();
    }

    public String toString() {
        return this.pW.toString() + this.getMessage();
    }

    public void a(axc_0 axc_02) {
        this.pW = axc_02;
    }

    public axc_0 hW() {
        return this.pW;
    }

    public void printStackTrace() {
        this.printStackTrace(System.err);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void printStackTrace(PrintStream printStream) {
        PrintStream printStream2 = printStream;
        synchronized (printStream2) {
            super.printStackTrace(printStream);
            if (this.pV != null) {
                printStream.println("--- Nested Exception ---");
                this.pV.printStackTrace(printStream);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void printStackTrace(PrintWriter printWriter) {
        PrintWriter printWriter2 = printWriter;
        synchronized (printWriter2) {
            super.printStackTrace(printWriter);
            if (this.pV != null) {
                printWriter.println("--- Nested Exception ---");
                this.pV.printStackTrace(printWriter);
            }
        }
    }
}

