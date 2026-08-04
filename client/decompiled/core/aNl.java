/*
 * Decompiled with CFR 0.152.
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.xml.sax.Attributes;

public class aNl
extends ka_0 {
    private static final String dZb = "included";
    private static final String dZc = "file";
    private static final String dZd = "url";
    private static final String dZe = "resource";
    private String dZf;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        yx_1 yx_12 = new yx_1();
        this.dZf = null;
        if (!this.d(attributes)) {
            return;
        }
        InputStream inputStream = this.a(qq_02, attributes);
        try {
            if (inputStream != null) {
                this.a(inputStream, yx_12);
                inputStream.close();
            }
        }
        catch (azG azG2) {
            this.e("Error while parsing  " + this.dZf, azG2);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.a(yx_12);
        qq_02.vY().h(yx_12.cbe);
    }

    private boolean d(Attributes attributes) {
        String string = attributes.getValue(dZc);
        String string2 = attributes.getValue(dZd);
        String string3 = attributes.getValue(dZe);
        int n2 = 0;
        if (!dh_2.isEmpty(string)) {
            ++n2;
        }
        if (!dh_2.isEmpty(string2)) {
            ++n2;
        }
        if (!dh_2.isEmpty(string3)) {
            ++n2;
        }
        if (n2 == 0) {
            this.eg("One of \"path\", \"resource\" or \"url\" attributes must be set.");
            return false;
        }
        if (n2 > 1) {
            this.eg("Only one of \"file\", \"url\" or \"resource\" attributes should be set.");
            return false;
        }
        if (n2 == 1) {
            return true;
        }
        throw new IllegalStateException("Count value [" + n2 + "] is not expected");
    }

    private InputStream lJ(String string) {
        try {
            return new FileInputStream(string);
        }
        catch (IOException iOException) {
            String string2 = "File [" + string + "] does not exist.";
            this.e(string2, iOException);
            return null;
        }
    }

    private InputStream lK(String string) {
        URL uRL;
        try {
            uRL = new URL(string);
        }
        catch (MalformedURLException malformedURLException) {
            String string2 = "URL [" + string + "] is not well formed.";
            this.e(string2, malformedURLException);
            return null;
        }
        return this.j(uRL);
    }

    InputStream j(URL uRL) {
        try {
            return uRL.openStream();
        }
        catch (IOException iOException) {
            String string = "Failed to open [" + uRL.toString() + "]";
            this.e(string, iOException);
            return null;
        }
    }

    private InputStream lL(String string) {
        URL uRL = agw_0.ln(string);
        if (uRL == null) {
            String string2 = "Could not find resource corresponding to [" + string + "]";
            this.eg(string2);
            return null;
        }
        return this.j(uRL);
    }

    InputStream a(qq_0 qq_02, Attributes attributes) {
        String string = attributes.getValue(dZc);
        String string2 = attributes.getValue(dZd);
        String string3 = attributes.getValue(dZe);
        if (!dh_2.isEmpty(string)) {
            this.dZf = qq_02.subst(string);
            return this.lJ(this.dZf);
        }
        if (!dh_2.isEmpty(string2)) {
            this.dZf = qq_02.subst(string2);
            return this.lK(this.dZf);
        }
        if (!dh_2.isEmpty(string3)) {
            this.dZf = qq_02.subst(string3);
            return this.lL(this.dZf);
        }
        throw new IllegalStateException("A input stream should have been returned");
    }

    private void a(yx_1 yx_12) {
        xg_0 xg_02;
        List list = yx_12.cbe;
        if (list.size() == 0) {
            return;
        }
        xg_0 xg_03 = (xg_0)list.get(0);
        if (xg_03 != null && xg_03.qName.equalsIgnoreCase(dZb)) {
            list.remove(0);
        }
        if ((xg_02 = (xg_0)list.get(yx_12.cbe.size() - 1)) != null && xg_02.qName.equalsIgnoreCase(dZb)) {
            list.remove(yx_12.cbe.size() - 1);
        }
    }

    private void a(InputStream inputStream, yx_1 yx_12) {
        yx_12.a(this.Pb);
        yx_12.m(inputStream);
    }

    public void a(qq_0 qq_02, String string) {
    }
}

