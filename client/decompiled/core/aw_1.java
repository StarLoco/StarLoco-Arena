/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/*
 * Renamed from aw
 */
public class aw_1
implements er_0 {
    private static final int cp = 255;
    private static final int cq = 8192;
    private String algorithm = "MD5";
    private String cr = null;
    private MessageDigest cs = null;
    private int ct = 8192;

    public void setAlgorithm(String string) {
        this.algorithm = string;
    }

    public void m(String string) {
        this.cr = string;
    }

    public void bc() {
        if (this.cs != null) {
            return;
        }
        if (this.cr != null && !"".equals(this.cr) && !"null".equals(this.cr)) {
            try {
                this.cs = MessageDigest.getInstance(this.algorithm, this.cr);
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new eq_2(noSuchAlgorithmException);
            }
            catch (NoSuchProviderException noSuchProviderException) {
                throw new eq_2(noSuchProviderException);
            }
        }
        try {
            this.cs = MessageDigest.getInstance(this.algorithm);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new eq_2(noSuchAlgorithmException);
        }
    }

    public boolean isValid() {
        return "SHA".equalsIgnoreCase(this.algorithm) || "MD5".equalsIgnoreCase(this.algorithm);
    }

    public String b(File file) {
        this.bc();
        String string = null;
        try {
            if (!file.canRead()) {
                return null;
            }
            FileInputStream fileInputStream = null;
            byte[] byArray = new byte[this.ct];
            try {
                this.cs.reset();
                fileInputStream = new FileInputStream(file);
                DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, this.cs);
                while (digestInputStream.read(byArray, 0, this.ct) != -1) {
                }
                digestInputStream.close();
                fileInputStream.close();
                fileInputStream = null;
                byte[] byArray2 = this.cs.digest();
                StringBuffer stringBuffer = new StringBuffer();
                for (int j = 0; j < byArray2.length; ++j) {
                    String string2 = Integer.toHexString(0xFF & byArray2[j]);
                    if (string2.length() < 2) {
                        stringBuffer.append("0");
                    }
                    stringBuffer.append(string2);
                }
                string = stringBuffer.toString();
            }
            catch (Exception exception) {
                return null;
            }
        }
        catch (Exception exception) {
            return null;
        }
        return string;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<DigestAlgorithm:");
        stringBuffer.append("algorithm=").append(this.algorithm);
        stringBuffer.append(";provider=").append(this.cr);
        stringBuffer.append(">");
        return stringBuffer.toString();
    }
}

