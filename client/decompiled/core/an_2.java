/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;

/*
 * Renamed from aN
 */
public final class an_2 {
    protected static final Logger a = Logger.getLogger(an_2.class);

    public static boolean a(URL uRL) {
        try {
            InputStream inputStream = uRL.openStream();
            inputStream.close();
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public static boolean o(String string) {
        URL uRL;
        try {
            uRL = new URL(string);
        }
        catch (MalformedURLException malformedURLException) {
            return false;
        }
        return an_2.a(uRL);
    }

    public static URL a(URL uRL, String string) {
        int n2;
        String string2;
        int n3 = 0;
        String string3 = uRL.getFile();
        boolean bl2 = false;
        if (string3 != null) {
            if (uRL.getProtocol().equals("file")) {
                File file = new File(string3);
                if (file.exists()) {
                    bl2 = file.isDirectory();
                }
            } else {
                bl2 = string3.endsWith("/") ? true : !(string2 = string3.substring((n2 = string3.lastIndexOf("/")) + 1, string3.length())).contains(".");
            }
        }
        int n4 = n2 = bl2 ? 0 : 1;
        while (string.startsWith("../")) {
            string = string.substring(3);
            ++n3;
        }
        string2 = uRL.toExternalForm();
        StringBuilder stringBuilder = new StringBuilder();
        String[] stringArray = string2.split("/");
        if (n3 > stringArray.length - 1) {
            a.error((Object)("Impossible de rajouter " + n3 + " ../ au chemin " + uRL.toExternalForm()));
            return null;
        }
        int n5 = stringArray.length - n2 - n3;
        if (n5 > 0) {
            for (int j = 0; j < stringArray.length - n2 - n3; ++j) {
                stringBuilder.append(stringArray[j]).append("/");
            }
        } else {
            stringBuilder.append(uRL.getProtocol()).append(":");
        }
        stringBuilder.append(string);
        return new URL(stringBuilder.toString());
    }
}

