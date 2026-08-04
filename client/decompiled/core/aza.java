/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class aza
implements oy_0 {
    private final String dna;
    private final String dnb;
    private final Class aTH;
    private final ArrayList dnc = new ArrayList();

    public aza(Class clazz, String string, String string2) {
        this.dna = string;
        this.dnb = string2;
        this.aTH = clazz;
    }

    public aza(Class clazz, String string, String string2, String ... stringArray) {
        this.dna = string;
        this.dnb = string2;
        this.aTH = clazz;
        for (int j = 0; j < stringArray.length; ++j) {
            this.dnc.add(stringArray[j]);
        }
    }

    public void addParam(String string) {
        this.dnc.add(string);
    }

    public Class abM() {
        return this.aTH;
    }

    public String a(Ga ga) {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.dnb != null) {
            if (this.aTH != null) {
                stringBuilder.append("((").append(this.aTH.getSimpleName()).append(")");
            }
            stringBuilder.append(this.dnb);
            if (this.aTH != null) {
                stringBuilder.append(")");
            }
            stringBuilder.append(".");
        }
        stringBuilder.append(this.dna).append("(");
        boolean bl2 = true;
        for (String string : this.dnc) {
            if (!bl2) {
                stringBuilder.append(", ");
            } else {
                bl2 = false;
            }
            stringBuilder.append(string);
        }
        stringBuilder.append(");");
        return stringBuilder.toString();
    }
}

