/*
 * Decompiled with CFR 0.152.
 */
import java.util.Locale;

/*
 * Renamed from xk
 */
public class xk_1
implements abe_2 {
    private static final String axj = System.getProperty("os.name").toLowerCase(Locale.US);
    private static final String axk = System.getProperty("os.arch").toLowerCase(Locale.US);
    private static final String axl = System.getProperty("os.version").toLowerCase(Locale.US);
    private static final String axm = System.getProperty("path.separator");
    private String axn;
    private String name;
    private String version;
    private String axo;
    public static final String axp = "windows";
    public static final String axq = "win9x";
    public static final String axr = "winnt";
    public static final String axs = "os/2";
    public static final String axt = "netware";
    public static final String axu = "dos";
    public static final String axv = "mac";
    public static final String axw = "tandem";
    public static final String axx = "unix";
    public static final String axy = "openvms";
    public static final String axz = "z/os";
    public static final String axA = "os/400";

    public xk_1() {
    }

    public xk_1(String string) {
        this.setFamily(string);
    }

    public void setFamily(String string) {
        this.axn = string.toLowerCase(Locale.US);
    }

    public void setName(String string) {
        this.name = string.toLowerCase(Locale.US);
    }

    public void cN(String string) {
        this.axo = string.toLowerCase(Locale.US);
    }

    public void setVersion(String string) {
        this.version = string.toLowerCase(Locale.US);
    }

    public boolean DH() {
        return xk_1.c(this.axn, this.name, this.axo, this.version);
    }

    public static boolean cO(String string) {
        return xk_1.c(string, null, null, null);
    }

    public static boolean isName(String string) {
        return xk_1.c(null, string, null, null);
    }

    public static boolean cP(String string) {
        return xk_1.c(null, null, string, null);
    }

    public static boolean cQ(String string) {
        return xk_1.c(null, null, null, string);
    }

    public static boolean c(String string, String string2, String string3, String string4) {
        boolean bl2 = false;
        if (string != null || string2 != null || string3 != null || string4 != null) {
            boolean bl3 = true;
            boolean bl4 = true;
            boolean bl5 = true;
            boolean bl6 = true;
            if (string != null) {
                boolean bl7 = axj.indexOf(axp) > -1;
                boolean bl8 = false;
                boolean bl9 = false;
                if (bl7) {
                    bl8 = axj.indexOf("95") >= 0 || axj.indexOf("98") >= 0 || axj.indexOf("me") >= 0 || axj.indexOf("ce") >= 0;
                    boolean bl10 = bl9 = !bl8;
                }
                if (string.equals(axp)) {
                    bl3 = bl7;
                } else if (string.equals(axq)) {
                    bl3 = bl7 && bl8;
                } else if (string.equals(axr)) {
                    bl3 = bl7 && bl9;
                } else if (string.equals(axs)) {
                    bl3 = axj.indexOf(axs) > -1;
                } else if (string.equals(axt)) {
                    bl3 = axj.indexOf(axt) > -1;
                } else if (string.equals(axu)) {
                    bl3 = axm.equals(";") && !xk_1.cO(axt);
                } else if (string.equals(axv)) {
                    bl3 = axj.indexOf(axv) > -1;
                } else if (string.equals(axw)) {
                    bl3 = axj.indexOf("nonstop_kernel") > -1;
                } else if (string.equals(axx)) {
                    bl3 = axm.equals(":") && !xk_1.cO(axy) && (!xk_1.cO(axv) || axj.endsWith("x"));
                } else if (string.equals(axz)) {
                    bl3 = axj.indexOf(axz) > -1 || axj.indexOf("os/390") > -1;
                } else if (string.equals(axA)) {
                    bl3 = axj.indexOf(axA) > -1;
                } else if (string.equals(axy)) {
                    bl3 = axj.indexOf(axy) > -1;
                } else {
                    throw new eq_2("Don't know how to detect os family \"" + string + "\"");
                }
            }
            if (string2 != null) {
                bl4 = string2.equals(axj);
            }
            if (string3 != null) {
                bl5 = string3.equals(axk);
            }
            if (string4 != null) {
                bl6 = string4.equals(axl);
            }
            bl2 = bl3 && bl4 && bl5 && bl6;
        }
        return bl2;
    }
}

