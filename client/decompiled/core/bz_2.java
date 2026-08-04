/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.util.Date;

/*
 * Renamed from BZ
 */
public class bz_2
implements kp_2 {
    public static final int aKx = 12;
    protected PrintStream out;
    protected PrintStream err;
    protected int J = 0;
    private long startTime = System.currentTimeMillis();
    protected static final String aKy = ayM.LINE_SEP;
    protected boolean S = false;

    public void eN(int n2) {
        this.J = n2;
    }

    public void l(PrintStream printStream) {
        this.out = new PrintStream(printStream, true);
    }

    public void m(PrintStream printStream) {
        this.err = new PrintStream(printStream, true);
    }

    public void aZ(boolean bl2) {
        this.S = bl2;
    }

    public void a(axv_0 axv_02) {
        this.startTime = System.currentTimeMillis();
    }

    public void b(axv_0 axv_02) {
        Object object;
        Object object2 = axv_02.getException();
        StringBuffer stringBuffer = new StringBuffer();
        if (object2 == null) {
            stringBuffer.append(ayM.LINE_SEP);
            stringBuffer.append(this.IS());
        } else {
            stringBuffer.append(ayM.LINE_SEP);
            stringBuffer.append(this.IR());
            stringBuffer.append(ayM.LINE_SEP);
            while (object2 instanceof eq_2 && (object = ((eq_2)object2).getCause()) != null && ((Throwable)object).toString().equals(((Throwable)object2).getMessage())) {
                object2 = object;
            }
            if (3 <= this.J || !(object2 instanceof eq_2)) {
                stringBuffer.append(ayM.i((Throwable)object2));
            } else {
                stringBuffer.append(((Throwable)object2).toString()).append(aKy);
            }
        }
        stringBuffer.append(ayM.LINE_SEP);
        stringBuffer.append("Total time: ");
        stringBuffer.append(bz_2.bf(System.currentTimeMillis() - this.startTime));
        object = stringBuffer.toString();
        if (object2 == null) {
            this.a((String)object, this.out, 3);
        } else {
            this.a((String)object, this.err, 0);
        }
        this.log((String)object);
    }

    protected String IR() {
        return "BUILD FAILED";
    }

    protected String IS() {
        return "BUILD SUCCESSFUL";
    }

    public void e(axv_0 axv_02) {
        if (2 <= this.J && !axv_02.aKd().getName().equals("")) {
            String string = ayM.LINE_SEP + axv_02.aKd().getName() + ":";
            this.a(string, this.out, axv_02.getPriority());
            this.log(string);
        }
    }

    public void f(axv_0 axv_02) {
    }

    public void g(axv_0 axv_02) {
    }

    public void h(axv_0 axv_02) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    public void i(axv_0 var1_1) {
        var2_2 = var1_1.getPriority();
        if (var2_2 <= this.J) {
            block17: {
                var3_3 = new StringBuffer();
                if (var1_1.adN() != null && !this.S) {
                    var4_4 = var1_1.adN().LF();
                    var5_5 = "[" + (String)var4_4 + "] ";
                    var6_6 = 12 - var5_5.length();
                    var7_7 = new StringBuffer();
                    for (var8_8 = 0; var8_8 < var6_6; ++var8_8) {
                        var7_7.append(" ");
                    }
                    var7_7.append(var5_5);
                    var5_5 = var7_7.toString();
                    var8_9 = null;
                    try {
                        var8_9 = new BufferedReader(new StringReader(var1_1.getMessage()));
                        var9_10 = var8_9.readLine();
                        var10_12 = true;
                        do {
                            if (var10_12) {
                                if (var9_10 == null) {
                                    var3_3.append(var5_5);
                                    break;
                                }
                            } else {
                                var3_3.append(ayM.LINE_SEP);
                            }
                            var10_12 = false;
                            var3_3.append(var5_5).append(var9_10);
                        } while ((var9_10 = var8_9.readLine()) != null);
                        ** if (var8_9 == null) goto lbl-1000
                    }
                    catch (IOException var9_11) {
                        try {
                            var3_3.append(var5_5).append(var1_1.getMessage());
                            ** if (var8_9 == null) goto lbl-1000
                        }
                        catch (Throwable var11_13) {
                            if (var8_9 != null) {
                                ga_2.e(var8_9);
                            }
                            throw var11_13;
                        }
lbl-1000:
                        // 1 sources

                        {
                            ga_2.e(var8_9);
                        }
lbl-1000:
                        // 2 sources

                        {
                            break block17;
                        }
                    }
lbl-1000:
                    // 1 sources

                    {
                        ga_2.e(var8_9);
                    }
lbl-1000:
                    // 2 sources

                    {
                        break block17;
                    }
                }
                var3_3.append(var1_1.getMessage());
            }
            var4_4 = var1_1.getException();
            if (4 <= this.J && var4_4 != null) {
                var3_3.append(ayM.i((Throwable)var4_4));
            }
            var5_5 = var3_3.toString();
            if (var2_2 != 0) {
                this.a(var5_5, this.out, var2_2);
            } else {
                this.a(var5_5, this.err, var2_2);
            }
            this.log(var5_5);
        }
    }

    protected static String bf(long l2) {
        return WG.db(l2);
    }

    protected void a(String string, PrintStream printStream, int n2) {
        printStream.println(string);
    }

    protected void log(String string) {
    }

    protected String IT() {
        Date date = new Date(System.currentTimeMillis());
        DateFormat dateFormat = DateFormat.getDateTimeInstance(3, 3);
        String string = dateFormat.format(date);
        return string;
    }

    protected String j(axv_0 axv_02) {
        UI uI = axv_02.TP();
        return uI != null ? uI.getName() : null;
    }
}

