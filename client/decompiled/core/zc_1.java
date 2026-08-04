/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Zc
 */
public class zc_1 {
    private UI ccm;
    public static final String ccn = "java.net.useSystemProxies";
    public static final String cco = "http.proxyHost";
    public static final String ccp = "http.proxyPort";
    public static final String ccq = "https.proxyHost";
    public static final String ccr = "https.proxyPort";
    public static final String ccs = "ftp.proxyHost";
    public static final String cct = "ftp.proxyPort";
    public static final String ccu = "http.nonProxyHosts";
    public static final String ccv = "https.nonProxyHosts";
    public static final String ccw = "ftp.nonProxyHosts";
    public static final String ccx = "http.proxyUser";
    public static final String ccy = "http.proxyPassword";
    public static final String ccz = "socksProxyHost";
    public static final String ccA = "socksProxyPort";
    public static final String ccB = "java.net.socks.username";
    public static final String ccC = "java.net.socks.password";

    public zc_1(UI uI) {
        this.ccm = uI;
    }

    public static String ank() {
        try {
            return System.getProperty(ccn);
        }
        catch (SecurityException securityException) {
            return null;
        }
    }

    public void anl() {
        if (zc_1.ank() == null) {
            String string = this.ccm.getProperty(ccn);
            if (string == null || UI.gh(string)) {
                string = "true";
            }
            String string2 = "setting java.net.useSystemProxies to " + string;
            try {
                this.ccm.l(string2, 4);
                System.setProperty(ccn, string);
            }
            catch (SecurityException securityException) {
                this.ccm.log("Security Exception when " + string2);
            }
        }
    }
}

