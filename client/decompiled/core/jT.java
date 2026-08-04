/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;

public class jT {
    private static final int CT = 68;
    private static final int MAX_NAME_LENGTH = 70;
    private String name = null;
    private Vector values = new Vector();
    private int CU = 0;

    public jT() {
    }

    public jT(String string) {
        this.parse(string);
    }

    public jT(String string, String string2) {
        this.name = string;
        this.setValue(string2);
    }

    public int hashCode() {
        int n2 = 0;
        if (this.name != null) {
            n2 += this.getKey().hashCode();
        }
        return n2 += this.values.hashCode();
    }

    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        if (object == this) {
            return true;
        }
        jT jT2 = (jT)object;
        String string = this.getKey();
        String string2 = jT2.getKey();
        if (string == null && string2 != null || string != null && !string.equals(string2)) {
            return false;
        }
        return this.values.equals(jT2.values);
    }

    public void parse(String string) {
        int n2 = string.indexOf(": ");
        if (n2 == -1) {
            throw new id_0("Manifest line \"" + string + "\" is not valid as it does not " + "contain a name and a value separated by ': ' ");
        }
        this.name = string.substring(0, n2);
        this.setValue(string.substring(n2 + 2));
    }

    public void setName(String string) {
        this.name = string;
    }

    public String getName() {
        return this.name;
    }

    public String getKey() {
        if (this.name == null) {
            return null;
        }
        return this.name.toLowerCase();
    }

    public void setValue(String string) {
        if (this.CU >= this.values.size()) {
            this.values.addElement(string);
            this.CU = this.values.size() - 1;
        } else {
            this.values.setElementAt(string, this.CU);
        }
    }

    public String getValue() {
        if (this.values.size() == 0) {
            return null;
        }
        String string = "";
        Enumeration enumeration = this.getValues();
        while (enumeration.hasMoreElements()) {
            String string2 = (String)enumeration.nextElement();
            string = string + string2 + " ";
        }
        return string.trim();
    }

    public void aJ(String string) {
        ++this.CU;
        this.setValue(string);
    }

    public Enumeration getValues() {
        return this.values.elements();
    }

    public void aK(String string) {
        String string2 = (String)this.values.elementAt(this.CU);
        this.setValue(string2 + string.substring(1));
    }

    public void write(PrintWriter printWriter) {
        Enumeration enumeration = this.getValues();
        while (enumeration.hasMoreElements()) {
            this.a(printWriter, (String)enumeration.nextElement());
        }
    }

    private void a(PrintWriter printWriter, String string) {
        String string2 = null;
        int n2 = this.name.getBytes("UTF-8").length;
        if (n2 > 68) {
            if (n2 > 70) {
                throw new IOException("Unable to write manifest line " + this.name + ": " + string);
            }
            printWriter.print(this.name + ": " + "\r\n");
            string2 = " " + string;
        } else {
            string2 = this.name + ": " + string;
        }
        while (string2.getBytes("UTF-8").length > 70) {
            int n3 = 70;
            if (n3 >= string2.length()) {
                n3 = string2.length() - 1;
            }
            String string3 = string2.substring(0, n3);
            while (string3.getBytes("UTF-8").length > 70 && n3 > 0) {
                string3 = string2.substring(0, --n3);
            }
            if (n3 == 0) {
                throw new IOException("Unable to write manifest line " + this.name + ": " + string);
            }
            printWriter.print(string3 + "\r\n");
            string2 = " " + string2.substring(n3);
        }
        printWriter.print(string2 + "\r\n");
    }
}

