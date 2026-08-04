/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.StringTokenizer;

public class eX
extends cr_2 {
    public int min = -1;
    public int max = -1;
    public static final String pY = "min";
    public static final String pZ = "max";

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{depthselector min: ");
        stringBuffer.append(this.min);
        stringBuffer.append(" max: ");
        stringBuffer.append(this.max);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void as(int n2) {
        this.min = n2;
    }

    public void at(int n2) {
        this.max = n2;
    }

    public void a(vj_0[] vj_0Array) {
        super.a(vj_0Array);
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                String string = vj_0Array[j].getName();
                if (pY.equalsIgnoreCase(string)) {
                    try {
                        this.as(Integer.parseInt(vj_0Array[j].getValue()));
                    }
                    catch (NumberFormatException numberFormatException) {
                        this.eC("Invalid minimum value " + vj_0Array[j].getValue());
                    }
                    continue;
                }
                if (pZ.equalsIgnoreCase(string)) {
                    try {
                        this.at(Integer.parseInt(vj_0Array[j].getValue()));
                    }
                    catch (NumberFormatException numberFormatException) {
                        this.eC("Invalid maximum value " + vj_0Array[j].getValue());
                    }
                    continue;
                }
                this.eC("Invalid parameter " + string);
            }
        }
    }

    public void dQ() {
        if (this.min < 0 && this.max < 0) {
            this.eC("You must set at least one of the min or the max levels.");
        }
        if (this.max < this.min && this.max > -1) {
            this.eC("The maximum depth is lower than the minimum.");
        }
    }

    public boolean a(File file, String string, File file2) {
        this.validate();
        int n2 = -1;
        String string2 = file.getAbsolutePath();
        String string3 = file2.getAbsolutePath();
        StringTokenizer stringTokenizer = new StringTokenizer(string2, File.separator);
        StringTokenizer stringTokenizer2 = new StringTokenizer(string3, File.separator);
        while (stringTokenizer2.hasMoreTokens()) {
            String string4 = stringTokenizer2.nextToken();
            if (stringTokenizer.hasMoreTokens()) {
                String string5 = stringTokenizer.nextToken();
                if (string5.equals(string4)) continue;
                throw new eq_2("File " + string + " does not appear within " + string2 + "directory");
            }
            if (this.max <= -1 || ++n2 <= this.max) continue;
            return false;
        }
        if (stringTokenizer.hasMoreTokens()) {
            throw new eq_2("File " + string + " is outside of " + string2 + "directory tree");
        }
        return this.min <= -1 || n2 >= this.min;
    }
}

