/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.log4j.Logger;

/*
 * Renamed from ed
 */
public class ed_1 {
    private static Logger a = Logger.getLogger(ed_1.class);
    private static final String nS = ",";
    private static final String nT = "%s_";
    private static final String nU = "%s_%d";
    private final Properties nV = new Properties();

    public String getString(String string) {
        String string2 = this.nV.getProperty(string);
        if (string2 == null) {
            throw new aih_2("Il n'existe pas de propri\u00e9t\u00e9: " + string);
        }
        return string2;
    }

    public ArrayList U(String string) {
        int n2 = 1;
        ArrayList<String> arrayList = new ArrayList<String>();
        String string2 = String.format(nU, string, n2);
        while (this.nV.containsKey(string2)) {
            arrayList.add(this.getString(string2));
            string2 = String.format(nU, string, ++n2);
        }
        return arrayList;
    }

    public String[] getStringArray(String string) {
        String string2 = this.getString(string);
        return this.Y(string2);
    }

    public ArrayList V(String string) {
        ArrayList arrayList = this.U(string);
        ArrayList<String[]> arrayList2 = new ArrayList<String[]>();
        for (String string2 : arrayList) {
            arrayList2.add(this.Y(string2));
        }
        return arrayList2;
    }

    public int W(String string) {
        String string2 = this.getString(string);
        try {
            return Integer.parseInt(string2);
        }
        catch (NumberFormatException numberFormatException) {
            throw new aih_2("La propri\u00e9t\u00e9 " + string + " n'est pas un int.");
        }
    }

    public float getFloat(String string) {
        String string2 = this.getString(string);
        try {
            return Float.valueOf(string2).floatValue();
        }
        catch (NumberFormatException numberFormatException) {
            throw new aih_2("La propri\u00e9t\u00e9 " + string + " n'est pas un float.");
        }
    }

    public double getDouble(String string) {
        String string2 = this.getString(string);
        try {
            return Double.valueOf(string2);
        }
        catch (NumberFormatException numberFormatException) {
            throw new aih_2("La propri\u00e9t\u00e9 " + string + " n'est pas un double.");
        }
    }

    public boolean getBoolean(String string) {
        String string2 = this.getString(string);
        return Boolean.valueOf(string2);
    }

    public void setString(String string, String string2) {
        if (this.nV != null) {
            this.nV.setProperty(string, string2);
        }
    }

    public void a(String string, ArrayList arrayList) {
        int n2 = 1;
        for (String string2 : arrayList) {
            String string3 = String.format(nU, string, n2++);
            this.setString(string3, string2);
        }
    }

    public void b(String string, ArrayList arrayList) {
        int n2 = 1;
        for (String[] stringArray : arrayList) {
            String string2 = String.format(nU, string, n2++);
            this.a(string2, stringArray);
        }
    }

    public void a(String string, String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < stringArray.length; ++j) {
            stringBuilder.append(stringArray[j]);
            if (j >= stringArray.length - 1) continue;
            stringBuilder.append(nS);
        }
        this.setString(string, stringBuilder.toString());
    }

    public void g(String string, int n2) {
        this.setString(string, Integer.toString(n2));
    }

    public void setFloat(String string, float f) {
        this.setString(string, Float.toString(f));
    }

    public void setDouble(String string, double d) {
        this.setString(string, Double.toString(d));
    }

    public void setBoolean(String string, boolean bl2) {
        this.setString(string, Boolean.toString(bl2));
    }

    public boolean d(InputStream inputStream) {
        try {
            this.nV.load(inputStream);
        }
        catch (IOException iOException) {
            return false;
        }
        return true;
    }

    public boolean c(URL uRL) {
        try {
            if (uRL != null) {
                return this.d(uRL.openStream());
            }
            a.error((Object)"url nulle au load.");
            return false;
        }
        catch (IOException iOException) {
            a.error((Object)"Exception", (Throwable)iOException);
            return false;
        }
    }

    public boolean load(String string) {
        try {
            File file = new File(string);
            if (file.exists()) {
                return this.d(new FileInputStream(file));
            }
            URL uRL = this.getClass().getClassLoader().getResource(string);
            if (uRL != null) {
                return this.c(uRL);
            }
            a.error((Object)("Impossible de trouver le fichier de propri\u00e9t\u00e9 " + string));
            return false;
        }
        catch (FileNotFoundException fileNotFoundException) {
            return false;
        }
    }

    public boolean save(String string) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(string);
            this.nV.store(fileOutputStream, null);
            ((OutputStream)fileOutputStream).close();
        }
        catch (FileNotFoundException fileNotFoundException) {
            return false;
        }
        catch (IOException iOException) {
            return false;
        }
        return true;
    }

    public void X(String string) {
        String string2 = String.format(nT, string);
        Enumeration enumeration = this.nV.keys();
        while (enumeration.hasMoreElements()) {
            String string3;
            Object k2 = enumeration.nextElement();
            if (!(k2 instanceof String) || !(string3 = (String)k2).startsWith(string2)) continue;
            this.nV.remove(string3);
        }
    }

    private String[] Y(String string) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, nS);
        Vector<String> vector = new Vector<String>();
        while (stringTokenizer.hasMoreTokens()) {
            vector.addElement(stringTokenizer.nextToken());
        }
        Object[] objectArray = new String[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }
}

