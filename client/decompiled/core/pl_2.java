/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import org.apache.log4j.Logger;

/*
 * Renamed from PL
 */
public class pl_2
implements atG {
    private byte[] bEj;
    protected static final Logger a = Logger.getLogger(pl_2.class);
    private static pl_2 bEk = new pl_2();

    public static pl_2 acq() {
        return bEk;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 108: {
                nW.sL();
                return false;
            }
            case 27526: {
                jg_2 jg_22 = (jg_2)pr_02;
                aez_0 aez_02 = new aez_0();
                aez_02.S(jg_22.lZ());
                aez_02.bg(jg_22.lY());
                aez_02.bh(jg_22.lX());
                aez_02.aQn().k(jg_22.ma());
                aez_02.release();
                zz_0.a(aez_02, new sL(this, jg_22));
                return false;
            }
            case 27528: {
                eq_1 eq_12 = (eq_1)pr_02;
                try {
                    String string;
                    String string2;
                    TrustManager[] trustManagerArray = new TrustManager[]{new afo_2(this)};
                    SSLContext sSLContext = SSLContext.getInstance("TLS");
                    sSLContext.init(null, trustManagerArray, new SecureRandom());
                    SSLSocket sSLSocket = (SSLSocket)sSLContext.getSocketFactory().createSocket("ws.ankama.lan", 443);
                    sSLSocket.startHandshake();
                    String string3 = string2 = "--bloubloubloublou\r\n";
                    string3 = string3 + "Content-Disposition: form-data; name=\"external\"\r\n\r\n";
                    string3 = string3 + apN.aDK().Ln().getId() + "\r\n";
                    string3 = string3 + string2;
                    string3 = string3 + "Content-Disposition: form-data; name=\"account\"\r\n\r\n";
                    string3 = string3 + apN.aDK().Ln().getId() + "\r\n";
                    string3 = string3 + string2;
                    string3 = string3 + "Content-Disposition: form-data; name=\"universe\"\r\n\r\n";
                    string3 = string3 + "5\r\n";
                    string3 = string3 + string2;
                    string3 = string3 + "Content-Disposition: form-data; name=\"avatar\"; filename=\"test.png\"\r\nContent-Type: image/png\r\nContent-Length: " + this.bEj.length + "\r\n\r\n";
                    String string4 = "\r\n" + string2 + "--\r\n";
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(sSLSocket.getOutputStream());
                    String string5 = "POST /ankama/Accounts_Game/UploadAvatar HTTP/1.1\r\n";
                    string5 = string5 + "Host:ws.ankama.lan\r\n";
                    string5 = string5 + "Content-Length: " + (string3.length() + this.bEj.length + string4.length()) + "\r\n";
                    string5 = string5 + "Content-Type: multipart/form-data; boundary=bloubloubloublou\r\n\r\n";
                    bufferedOutputStream.write(string5.getBytes());
                    bufferedOutputStream.write(string3.getBytes());
                    bufferedOutputStream.write(this.bEj);
                    bufferedOutputStream.write(string4.getBytes());
                    bufferedOutputStream.flush();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sSLSocket.getInputStream()));
                    while ((string = bufferedReader.readLine()) != null) {
                        a.info((Object)string);
                    }
                    sSLSocket.close();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                a.info((Object)eq_12.OB());
                if (eq_12.OB()) {
                    // empty if block
                }
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    static /* synthetic */ byte[] a(pl_2 pl_22, byte[] byArray) {
        pl_22.bEj = byArray;
        return byArray;
    }
}

