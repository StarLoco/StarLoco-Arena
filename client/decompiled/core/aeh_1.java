/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/*
 * Renamed from aeh
 */
public class aeh_1 {
    private Vector col = new Vector();
    private String name = null;
    private Hashtable com = new Hashtable();
    private Vector con = new Vector();

    public void setName(String string) {
        this.name = string;
    }

    public String getName() {
        return this.name;
    }

    public String a(BufferedReader bufferedReader) {
        String string;
        jT jT2 = null;
        while (true) {
            String string2;
            if ((string2 = bufferedReader.readLine()) == null || string2.length() == 0) {
                return null;
            }
            if (string2.charAt(0) == ' ') {
                if (jT2 == null) {
                    if (this.name != null) {
                        this.name = this.name + string2.substring(1);
                        continue;
                    }
                    throw new id_0("Can't start an attribute with a continuation line " + string2);
                }
                jT2.aK(string2);
                continue;
            }
            jT2 = new jT(string2);
            string = this.b(jT2);
            jT2 = this.hC(jT2.getKey());
            if (string != null) break;
        }
        return string;
    }

    public void b(aeh_1 aeh_12) {
        Object object;
        if (this.name == null && aeh_12.getName() != null || this.name != null && !this.name.equalsIgnoreCase(aeh_12.getName())) {
            throw new id_0("Unable to merge sections with different names");
        }
        Enumeration enumeration = aeh_12.atB();
        jT jT2 = null;
        while (enumeration.hasMoreElements()) {
            object = (String)enumeration.nextElement();
            jT jT3 = aeh_12.hC((String)object);
            if (((String)object).equalsIgnoreCase("Class-Path")) {
                if (jT2 == null) {
                    jT2 = new jT();
                    jT2.setName("Class-Path");
                }
                Enumeration enumeration2 = jT3.getValues();
                while (enumeration2.hasMoreElements()) {
                    String string = (String)enumeration2.nextElement();
                    jT2.aJ(string);
                }
                continue;
            }
            this.c(jT3);
        }
        if (jT2 != null) {
            this.c(jT2);
        }
        object = aeh_12.col.elements();
        while (object.hasMoreElements()) {
            this.col.addElement(object.nextElement());
        }
    }

    public void write(PrintWriter printWriter) {
        Object object;
        if (this.name != null) {
            object = new jT("Name", this.name);
            ((jT)object).write(printWriter);
        }
        object = this.atB();
        while (object.hasMoreElements()) {
            String string = (String)object.nextElement();
            jT jT2 = this.hC(string);
            jT2.write(printWriter);
        }
        printWriter.print("\r\n");
    }

    public jT hC(String string) {
        return (jT)this.com.get(string.toLowerCase());
    }

    public Enumeration atB() {
        return this.con.elements();
    }

    public String getAttributeValue(String string) {
        jT jT2 = this.hC(string.toLowerCase());
        if (jT2 == null) {
            return null;
        }
        return jT2.getValue();
    }

    public void removeAttribute(String string) {
        String string2 = string.toLowerCase();
        this.com.remove(string2);
        this.con.removeElement(string2);
    }

    public void a(jT jT2) {
        String string = this.b(jT2);
        if (string != null) {
            throw new eq_2("Specify the section name using the \"name\" attribute of the <section> element rather than using a \"Name\" manifest attribute");
        }
    }

    public String b(jT jT2) {
        if (jT2.getName() == null || jT2.getValue() == null) {
            throw new eq_2("Attributes must have name and value");
        }
        if (jT2.getKey().equalsIgnoreCase("Name")) {
            this.col.addElement("\"Name\" attributes should not occur in the main section and must be the first element in all other sections: \"" + jT2.getName() + ": " + jT2.getValue() + "\"");
            return jT2.getValue();
        }
        if (jT2.getKey().startsWith("From".toLowerCase())) {
            this.col.addElement("Manifest attributes should not start with \"From\" in \"" + jT2.getName() + ": " + jT2.getValue() + "\"");
        } else {
            String string = jT2.getKey();
            if (string.equalsIgnoreCase("Class-Path")) {
                jT jT3 = (jT)this.com.get(string);
                if (jT3 == null) {
                    this.c(jT2);
                } else {
                    this.col.addElement("Multiple Class-Path attributes are supported but violate the Jar specification and may not be correctly processed in all environments");
                    Enumeration enumeration = jT2.getValues();
                    while (enumeration.hasMoreElements()) {
                        String string2 = (String)enumeration.nextElement();
                        jT3.aJ(string2);
                    }
                }
            } else {
                if (this.com.containsKey(string)) {
                    throw new id_0("The attribute \"" + jT2.getName() + "\" may not occur more " + "than once in the same section");
                }
                this.c(jT2);
            }
        }
        return null;
    }

    public Object clone() {
        aeh_1 aeh_12 = new aeh_1();
        aeh_12.setName(this.name);
        Enumeration enumeration = this.atB();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            jT jT2 = this.hC(string);
            aeh_12.c(new jT(jT2.getName(), jT2.getValue()));
        }
        return aeh_12;
    }

    private void c(jT jT2) {
        if (jT2 == null) {
            return;
        }
        String string = jT2.getKey();
        this.com.put(string, jT2);
        if (!this.con.contains(string)) {
            this.con.addElement(string);
        }
    }

    public Enumeration Dz() {
        return this.col.elements();
    }

    public int hashCode() {
        return this.com.hashCode();
    }

    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        if (object == this) {
            return true;
        }
        aeh_1 aeh_12 = (aeh_1)object;
        return this.com.equals(aeh_12.com);
    }

    static void a(aeh_1 aeh_12, jT jT2) {
        aeh_12.c(jT2);
    }
}

