/*
 * Decompiled with CFR 0.152.
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import org.xml.sax.Attributes;

public class Oo
extends ka_0 {
    static final String bBP = "resource";
    static String bBQ = "In <property> element, either the \"file\" attribute alone, or the \"resource\" element alone, or both the \"name\" and \"value\" attributes must be set.";

    public void a(qq_0 qq_02, Properties properties) {
        qq_02.a(properties);
    }

    public void a(qq_0 qq_02, String string, String string2) {
        qq_02.g(string, string2);
    }

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        if ("substitutionProperty".equals(string)) {
            this.ef("[substitutionProperty] element has been deprecated. Plase use the [property] element instead.");
        }
        String string2 = attributes.getValue("name");
        String string3 = attributes.getValue("value");
        if (this.a(attributes)) {
            String string4 = attributes.getValue("file");
            string4 = qq_02.subst(string4);
            try {
                FileInputStream fileInputStream = new FileInputStream(string4);
                this.a(qq_02, fileInputStream);
            }
            catch (IOException iOException) {
                this.e("Could not read properties file [" + string4 + "].", iOException);
            }
        } else if (this.b(attributes)) {
            String string5 = attributes.getValue(bBP);
            URL uRL = agw_0.ln(string5 = qq_02.subst(string5));
            if (uRL == null) {
                this.eg("Could not find resource [" + string5 + "].");
            } else {
                try {
                    InputStream inputStream = uRL.openStream();
                    this.a(qq_02, inputStream);
                }
                catch (IOException iOException) {
                    this.e("Could not read resource file [" + string5 + "].", iOException);
                }
            }
        } else if (this.c(attributes)) {
            string3 = afe_0.hT(string3);
            string3 = string3.trim();
            string3 = qq_02.subst(string3);
            this.a(qq_02, string2, string3);
        } else {
            this.eg(bBQ);
        }
    }

    void a(qq_0 qq_02, InputStream inputStream) {
        Properties properties = new Properties();
        properties.load(inputStream);
        inputStream.close();
        this.a(qq_02, properties);
    }

    boolean a(Attributes attributes) {
        String string = attributes.getValue("file");
        String string2 = attributes.getValue("name");
        String string3 = attributes.getValue("value");
        String string4 = attributes.getValue(bBP);
        return !dh_2.isEmpty(string) && dh_2.isEmpty(string2) && dh_2.isEmpty(string3) && dh_2.isEmpty(string4);
    }

    boolean b(Attributes attributes) {
        String string = attributes.getValue("file");
        String string2 = attributes.getValue("name");
        String string3 = attributes.getValue("value");
        String string4 = attributes.getValue(bBP);
        return !dh_2.isEmpty(string4) && dh_2.isEmpty(string2) && dh_2.isEmpty(string3) && dh_2.isEmpty(string);
    }

    boolean c(Attributes attributes) {
        String string = attributes.getValue("file");
        String string2 = attributes.getValue("name");
        String string3 = attributes.getValue("value");
        String string4 = attributes.getValue(bBP);
        return !dh_2.isEmpty(string2) && !dh_2.isEmpty(string3) && dh_2.isEmpty(string) && dh_2.isEmpty(string4);
    }

    public void a(qq_0 qq_02, String string) {
    }

    public void a(qq_0 qq_02) {
    }
}

