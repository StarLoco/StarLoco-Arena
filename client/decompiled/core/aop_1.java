/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Renamed from aoP
 */
public class aop_1
extends cr_2
implements wb_2 {
    private String cLo = null;
    private boolean uX = true;
    private boolean cLp = false;
    public static final String hC = "expression";
    public static final String DT = "text";
    public static final String va = "casesensitive";
    public static final String cLq = "ignorewhitespace";

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{containsselector text: ");
        stringBuffer.append('\"').append(this.cLo).append('\"');
        stringBuffer.append(" casesensitive: ");
        stringBuffer.append(this.uX ? "true" : "false");
        stringBuffer.append(" ignorewhitespace: ");
        stringBuffer.append(this.cLp ? "true" : "false");
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void setText(String string) {
        this.cLo = string;
    }

    public void K(boolean bl2) {
        this.uX = bl2;
    }

    public void dL(boolean bl2) {
        this.cLp = bl2;
    }

    public void a(vj_0[] vj_0Array) {
        super.a(vj_0Array);
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                String string = vj_0Array[j].getName();
                if (DT.equalsIgnoreCase(string)) {
                    this.setText(vj_0Array[j].getValue());
                    continue;
                }
                if (va.equalsIgnoreCase(string)) {
                    this.K(UI.gh(vj_0Array[j].getValue()));
                    continue;
                }
                if (cLq.equalsIgnoreCase(string)) {
                    this.dL(UI.gh(vj_0Array[j].getValue()));
                    continue;
                }
                this.eC("Invalid parameter " + string);
            }
        }
    }

    public void dQ() {
        if (this.cLo == null) {
            this.eC("The text attribute is required");
        }
    }

    public boolean a(File file, String string, File file2) {
        return this.a(new ash_0(file2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean a(iv_1 iv_12) {
        boolean bl2;
        BufferedReader bufferedReader;
        block15: {
            boolean bl3;
            block14: {
                this.validate();
                if (iv_12.isDirectory()) return true;
                if (this.cLo.length() == 0) {
                    return true;
                }
                String string = this.cLo;
                if (!this.uX) {
                    string = this.cLo.toLowerCase();
                }
                if (this.cLp) {
                    string = zr_1.gY(string);
                }
                bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(iv_12.getInputStream()));
                }
                catch (Exception exception) {
                    throw new eq_2("Could not get InputStream from " + iv_12.lJ(), exception);
                }
                try {
                    try {
                        String string2 = bufferedReader.readLine();
                        while (string2 != null) {
                            if (!this.uX) {
                                string2 = string2.toLowerCase();
                            }
                            if (this.cLp) {
                                string2 = zr_1.gY(string2);
                            }
                            if (string2.indexOf(string) > -1) {
                                bl3 = true;
                                Object var7_9 = null;
                                break block14;
                            }
                            string2 = bufferedReader.readLine();
                        }
                        bl2 = false;
                        break block15;
                    }
                    catch (IOException iOException) {
                        throw new eq_2("Could not read " + iv_12.lJ());
                    }
                }
                catch (Throwable throwable) {
                    Object var7_11 = null;
                    ga_2.e(bufferedReader);
                    throw throwable;
                }
            }
            ga_2.e(bufferedReader);
            return bl3;
        }
        Object var7_10 = null;
        ga_2.e(bufferedReader);
        return bl2;
    }
}

