/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import javax.crypto.Cipher;
import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;

/*
 * Renamed from azx
 */
public final class azx_0 {
    private static final boolean cR = false;
    public static final Logger a = Logger.getLogger(azx_0.class);
    protected Certificate dnO;
    protected RSAPrivateKey dnP;
    protected Cipher dnQ;
    protected Cipher dnR;
    private static final String dnS = "RSA";

    public azx_0(String string, String string2, String string3, String string4) {
        try {
            KeyStore keyStore = KeyStore.getInstance(string2);
            FileInputStream fileInputStream = new FileInputStream(string);
            keyStore.load(fileInputStream, string4.toCharArray());
            fileInputStream.close();
            this.dnO = keyStore.getCertificate(string3);
            this.dnP = (RSAPrivateKey)keyStore.getKey(string3, string4.toCharArray());
            long l2 = -System.nanoTime();
            this.dnR = Cipher.getInstance(dnS);
            a.debug((Object)("Dur\u00e9e de Cipher.getInstance(ENCRYPTION_ALGO) : " + (l2 += System.nanoTime()) / 1000000L + "ms."));
            l2 = -System.nanoTime();
            this.dnQ = Cipher.getInstance(dnS);
            a.debug((Object)("Dur\u00e9e de Cipher.getInstance(ENCRYPTION_ALGO) : " + (l2 += System.nanoTime()) / 1000000L + "ms."));
            this.dnR.init(2, this.dnP);
            this.dnQ.init(1, this.dnO);
        }
        catch (Exception exception) {
            a.error((Object)"Une erreur est survenue lors de l'initialisation des ciphers : ", (Throwable)exception);
        }
    }

    public azx_0(URL uRL, String string, String string2, String string3) {
        try {
            KeyStore keyStore = KeyStore.getInstance(string);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(uRL.openStream());
            keyStore.load(bufferedInputStream, string3.toCharArray());
            ((InputStream)bufferedInputStream).close();
            this.dnO = keyStore.getCertificate(string2);
            this.dnP = (RSAPrivateKey)keyStore.getKey(string2, string3.toCharArray());
            this.dnR = Cipher.getInstance(dnS);
            this.dnQ = Cipher.getInstance(dnS);
            this.dnR.init(2, this.dnP);
            this.dnQ.init(1, this.dnO);
        }
        catch (Exception exception) {
            a.error((Object)"Une erreur est survenue lors de l'initialisation des ciphers : ", (Throwable)exception);
        }
    }

    public byte[] x(byte[] byArray) {
        if (this.dnQ != null) {
            try {
                return this.dnQ.doFinal(byArray);
            }
            catch (Exception exception) {
                a.error((Object)("Impossible de crypter les donn\u00e9es, raison : " + exception.getMessage()));
                return null;
            }
        }
        a.error((Object)"Impossible de crypter les donn\u00e9es, raison : encoder = null");
        return null;
    }

    public byte[] y(byte[] byArray) {
        if (this.dnR != null) {
            try {
                return this.dnR.doFinal(byArray);
            }
            catch (Exception exception) {
                a.error((Object)("Impossible de d\u00e9crypter les donn\u00e9es, raison : " + exception.getMessage()));
                return null;
            }
        }
        a.error((Object)"Impossible de d\u00e9crypter les donn\u00e9es, raison : decoder = null");
        return null;
    }

    public void kd(String string) {
        if (this.dnP != null) {
            BASE64Encoder bASE64Encoder = new BASE64Encoder();
            String string2 = bASE64Encoder.encode(this.dnP.getEncoded());
            try {
                a.info((Object)("Export de la clef priv\u00e9e vers " + string));
                FileOutputStream fileOutputStream = new FileOutputStream(string);
                PrintStream printStream = new PrintStream(fileOutputStream);
                printStream.println("-----BEGIN PRIVATE KEY-----");
                printStream.println(string2);
                printStream.println("-----END PRIVATE KEY-----");
                fileOutputStream.close();
                printStream.close();
            }
            catch (IOException iOException) {
                a.error((Object)("Impossible d'expoter la clef priv\u00e9e : " + iOException.getMessage()));
            }
        }
    }
}

