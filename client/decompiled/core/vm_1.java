/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

/*
 * Renamed from vm
 */
public class vm_1
implements hu_1 {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(ni_2 ni_22) {
        String string = this.b(ni_22);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.getInputStream()));
            do {
                System.err.println(string);
                System.err.flush();
                try {
                    String string2 = bufferedReader.readLine();
                    ni_22.fx(string2);
                }
                catch (IOException iOException) {
                    throw new eq_2("Failed to read input from Console.", iOException);
                }
            } while (!ni_22.OR());
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException iOException) {
                    throw new eq_2("Failed to close input.", iOException);
                }
            }
        }
    }

    protected String b(ni_2 ni_22) {
        String string = ni_22.getPrompt();
        String string2 = ni_22.getDefaultValue();
        if (ni_22 instanceof fk_2) {
            StringBuffer stringBuffer = new StringBuffer(string);
            stringBuffer.append(" (");
            Enumeration enumeration = ((fk_2)ni_22).OQ().elements();
            boolean bl2 = true;
            while (enumeration.hasMoreElements()) {
                String string3;
                if (!bl2) {
                    stringBuffer.append(", ");
                }
                if ((string3 = (String)enumeration.nextElement()).equals(string2)) {
                    stringBuffer.append('[');
                }
                stringBuffer.append(string3);
                if (string3.equals(string2)) {
                    stringBuffer.append(']');
                }
                bl2 = false;
            }
            stringBuffer.append(")");
            return stringBuffer.toString();
        }
        if (string2 != null) {
            return string + " [" + string2 + "]";
        }
        return string;
    }

    protected InputStream getInputStream() {
        return System.in;
    }
}

