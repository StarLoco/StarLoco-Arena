/*
 * Decompiled with CFR 0.152.
 */
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/*
 * Renamed from afO
 */
class afo_2
implements X509TrustManager {
    final /* synthetic */ pl_2 alS;

    afo_2(pl_2 pl_22) {
        this.alS = pl_22;
    }

    public boolean isClientTrusted(X509Certificate[] x509CertificateArray) {
        return true;
    }

    public boolean a(X509Certificate[] x509CertificateArray) {
        return true;
    }

    public void checkClientTrusted(X509Certificate[] x509CertificateArray, String string) {
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArray, String string) {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}

