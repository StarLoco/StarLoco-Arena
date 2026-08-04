/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Renamed from bP
 */
public class bp_2
extends cr_2
implements wb_2 {
    private String hz = null;
    private acy_0 hA = null;
    private axk hB = null;
    public static final String hC = "expression";

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{containsregexpselector expression: ");
        stringBuffer.append(this.hz);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void setExpression(String string) {
        this.hz = string;
    }

    public void a(vj_0[] vj_0Array) {
        super.a(vj_0Array);
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                String string = vj_0Array[j].getName();
                if (hC.equalsIgnoreCase(string)) {
                    this.setExpression(vj_0Array[j].getValue());
                    continue;
                }
                this.eC("Invalid parameter " + string);
            }
        }
    }

    public void dQ() {
        if (this.hz == null) {
            this.eC("The expression attribute is required");
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
                String string = null;
                bufferedReader = null;
                this.validate();
                if (iv_12.isDirectory()) {
                    return true;
                }
                if (this.hA == null) {
                    this.hA = new acy_0();
                    this.hA.setPattern(this.hz);
                    this.hB = this.hA.U(this.TP());
                }
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(iv_12.getInputStream()));
                }
                catch (Exception exception) {
                    throw new eq_2("Could not get InputStream from " + iv_12.lJ(), exception);
                }
                try {
                    try {
                        string = bufferedReader.readLine();
                        while (string != null) {
                            if (this.hB.matches(string)) {
                                bl3 = true;
                                Object var6_8 = null;
                                break block14;
                            }
                            string = bufferedReader.readLine();
                        }
                        bl2 = false;
                        break block15;
                    }
                    catch (IOException iOException) {
                        throw new eq_2("Could not read " + iv_12.lJ());
                    }
                }
                catch (Throwable throwable) {
                    Object var6_10 = null;
                    try {
                        bufferedReader.close();
                        throw throwable;
                    }
                    catch (Exception exception) {
                        throw new eq_2("Could not close " + iv_12.lJ());
                    }
                }
            }
            try {}
            catch (Exception exception) {
                throw new eq_2("Could not close " + iv_12.lJ());
            }
            bufferedReader.close();
            return bl3;
        }
        Object var6_9 = null;
        try {}
        catch (Exception exception) {
            throw new eq_2("Could not close " + iv_12.lJ());
        }
        bufferedReader.close();
        return bl2;
    }
}

