/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;

/*
 * Renamed from QW
 */
public class qw_0
implements Serializable {
    private static final long serialVersionUID = 2473626903716082403L;
    public static final String NA = "?";
    private static final String bIb = "org.apache.log4j.Category";
    public static final int bIc = -1;
    public static String bId = "?#?:?" + kJ.sy;
    public static qw_0[] bIe = new qw_0[0];
    int lineNumber;
    String fileName;
    String className;
    String methodName;
    boolean bIf = false;

    public qw_0(String string, String string2, String string3, int n2) {
        this.fileName = string;
        this.className = string2;
        this.methodName = string3;
        this.lineNumber = n2;
    }

    public qw_0(StackTraceElement stackTraceElement) {
        this.className = stackTraceElement.getClassName();
        this.fileName = stackTraceElement.getFileName();
        this.methodName = stackTraceElement.getMethodName();
        this.lineNumber = stackTraceElement.getLineNumber();
        this.bIf = stackTraceElement.isNativeMethod();
    }

    public static qw_0[] a(Throwable throwable, String string) {
        int n2;
        if (throwable == null) {
            return null;
        }
        StackTraceElement[] stackTraceElementArray = throwable.getStackTrace();
        int n3 = -1;
        for (n2 = 0; n2 < stackTraceElementArray.length; ++n2) {
            if (qw_0.z(stackTraceElementArray[n2].getClassName(), string)) {
                n3 = n2 + 1;
                continue;
            }
            if (n3 != -1) break;
        }
        if (n3 == -1) {
            return bIe;
        }
        qw_0[] qw_0Array = new qw_0[stackTraceElementArray.length - n3];
        for (n2 = n3; n2 < stackTraceElementArray.length; ++n2) {
            qw_0Array[n2 - n3] = new qw_0(stackTraceElementArray[n2]);
        }
        return qw_0Array;
    }

    public static boolean z(String string, String string2) {
        return string.equals(string2) || string.equals(bIb);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof qw_0)) {
            return false;
        }
        qw_0 qw_02 = (qw_0)object;
        if (!this.getClassName().equals(qw_02.getClassName())) {
            return false;
        }
        if (!this.getFileName().equals(qw_02.getFileName())) {
            return false;
        }
        if (!this.getMethodName().equals(qw_02.getMethodName())) {
            return false;
        }
        return this.lineNumber == qw_02.lineNumber;
    }

    public String getClassName() {
        return this.className;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.getClassName());
        stringBuffer.append('.');
        stringBuffer.append(this.getMethodName());
        stringBuffer.append('(');
        if (this.isNativeMethod()) {
            stringBuffer.append("Native Method");
        } else if (this.getFileName() == null) {
            stringBuffer.append("Unknown Source");
        } else {
            stringBuffer.append(this.getFileName());
            stringBuffer.append(':');
            stringBuffer.append(this.getLineNumber());
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    public boolean isNativeMethod() {
        return this.bIf;
    }
}

