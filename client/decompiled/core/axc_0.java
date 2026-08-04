/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;
import org.xml.sax.Locator;

/*
 * Renamed from axc
 */
public class axc_0
implements Serializable {
    private String fileName;
    private int lineNumber;
    private int columnNumber;
    public static final axc_0 diY = new axc_0();
    private static final ga_2 xa = ga_2.Qo();

    private axc_0() {
        this(null, 0, 0);
    }

    public axc_0(String string) {
        this(string, 0, 0);
    }

    public axc_0(Locator locator) {
        this(locator.getSystemId(), locator.getLineNumber(), locator.getColumnNumber());
    }

    public axc_0(String string, int n2, int n3) {
        this.fileName = string != null && string.startsWith("file:") ? xa.ec(string) : string;
        this.lineNumber = n2;
        this.columnNumber = n3;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public int getColumnNumber() {
        return this.columnNumber;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.fileName != null) {
            stringBuffer.append(this.fileName);
            if (this.lineNumber != 0) {
                stringBuffer.append(":");
                stringBuffer.append(this.lineNumber);
            }
            stringBuffer.append(": ");
        }
        return stringBuffer.toString();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        return this.toString().equals(object.toString());
    }

    public int hashCode() {
        return this.toString().hashCode();
    }
}

