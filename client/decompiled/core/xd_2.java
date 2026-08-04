/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/*
 * Renamed from xd
 */
public class xd_2 {
    public static final String awb = "Manifest-Version";
    public static final String awc = "Signature-Version";
    public static final String awd = "Name";
    public static final String awe = "From";
    public static final String awf = "Class-Path";
    public static final String awg = "1.0";
    public static final int awh = 72;
    public static final int awi = 70;
    public static final String awj = "\r\n";
    public static final String awk = "Manifest attributes should not start with \"From\" in \"";
    public static final String awl = "UTF-8";
    private String awm = "1.0";
    private aeh_1 awn = new aeh_1();
    private Hashtable awo = new Hashtable();
    private Vector awp = new Vector();
    static Class awq;

    /*
     * Exception decompiling
     */
    public static xd_2 Dy() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[CATCHBLOCK]], but top level block is 3[TRYBLOCK]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public xd_2() {
        this.awm = null;
    }

    public xd_2(Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String string = this.awn.a(bufferedReader);
        String string2 = this.awn.getAttributeValue(awb);
        if (string2 != null) {
            this.awm = string2;
            this.awn.removeAttribute(awb);
        }
        String string3 = null;
        while ((string3 = bufferedReader.readLine()) != null) {
            jT jT2;
            if (string3.length() == 0) continue;
            aeh_1 aeh_12 = new aeh_1();
            if (string == null) {
                jT2 = new jT(string3);
                if (!jT2.getName().equalsIgnoreCase(awd)) {
                    throw new id_0("Manifest sections should start with a \"Name\" attribute and not \"" + jT2.getName() + "\"");
                }
                string = jT2.getValue();
            } else {
                jT2 = new jT(string3);
                aeh_12.b(jT2);
            }
            aeh_12.setName(string);
            string = aeh_12.a(bufferedReader);
            this.a(aeh_12);
        }
    }

    public void a(aeh_1 aeh_12) {
        String string = aeh_12.getName();
        if (string == null) {
            throw new eq_2("Sections must have a name");
        }
        this.awo.put(string, aeh_12);
        if (!this.awp.contains(string)) {
            this.awp.addElement(string);
        }
    }

    public void a(jT jT2) {
        if (jT2.getKey() == null || jT2.getValue() == null) {
            throw new eq_2("Attributes must have name and value");
        }
        if (jT2.getKey().equalsIgnoreCase(awb)) {
            this.awm = jT2.getValue();
        } else {
            this.awn.a(jT2);
        }
    }

    public void a(xd_2 xd_22) {
        this.a(xd_22, false);
    }

    public void a(xd_2 xd_22, boolean bl2) {
        if (xd_22 != null) {
            if (bl2) {
                this.awn = (aeh_1)xd_22.awn.clone();
            } else {
                this.awn.b(xd_22.awn);
            }
            if (xd_22.awm != null) {
                this.awm = xd_22.awm;
            }
            Enumeration enumeration = xd_22.DC();
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                aeh_1 aeh_12 = (aeh_1)this.awo.get(string);
                aeh_1 aeh_13 = (aeh_1)xd_22.awo.get(string);
                if (aeh_12 == null) {
                    if (aeh_13 == null) continue;
                    this.a((aeh_1)aeh_13.clone());
                    continue;
                }
                aeh_12.b(aeh_13);
            }
        }
    }

    public void write(PrintWriter printWriter) {
        Object object;
        printWriter.print("Manifest-Version: " + this.awm + awj);
        String string = this.awn.getAttributeValue(awc);
        if (string != null) {
            printWriter.print("Signature-Version: " + string + awj);
            this.awn.removeAttribute(awc);
        }
        this.awn.write(printWriter);
        if (string != null) {
            try {
                object = new jT(awc, string);
                this.awn.a((jT)object);
            }
            catch (id_0 id_02) {
                // empty catch block
            }
        }
        object = this.awp.elements();
        while (object.hasMoreElements()) {
            String string2 = (String)object.nextElement();
            aeh_1 aeh_12 = this.cM(string2);
            aeh_12.write(printWriter);
        }
    }

    public String toString() {
        StringWriter stringWriter = new StringWriter();
        try {
            this.write(new PrintWriter(stringWriter));
        }
        catch (IOException iOException) {
            return null;
        }
        return stringWriter.toString();
    }

    public Enumeration Dz() {
        Vector vector = new Vector();
        Enumeration enumeration = this.awn.Dz();
        while (enumeration.hasMoreElements()) {
            vector.addElement(enumeration.nextElement());
        }
        Enumeration enumeration2 = this.awo.elements();
        while (enumeration2.hasMoreElements()) {
            aeh_1 aeh_12 = (aeh_1)enumeration2.nextElement();
            Enumeration enumeration3 = aeh_12.Dz();
            while (enumeration3.hasMoreElements()) {
                vector.addElement(enumeration3.nextElement());
            }
        }
        return vector.elements();
    }

    public int hashCode() {
        int n2 = 0;
        if (this.awm != null) {
            n2 += this.awm.hashCode();
        }
        n2 += this.awn.hashCode();
        return n2 += this.awo.hashCode();
    }

    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        if (object == this) {
            return true;
        }
        xd_2 xd_22 = (xd_2)object;
        if (this.awm == null ? xd_22.awm != null : !this.awm.equals(xd_22.awm)) {
            return false;
        }
        if (!this.awn.equals(xd_22.awn)) {
            return false;
        }
        return this.awo.equals(xd_22.awo);
    }

    public String DA() {
        return this.awm;
    }

    public aeh_1 DB() {
        return this.awn;
    }

    public aeh_1 cM(String string) {
        return (aeh_1)this.awo.get(string);
    }

    public Enumeration DC() {
        return this.awp.elements();
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

