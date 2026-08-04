/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

/*
 * Renamed from aEK
 */
public class aek_1
implements er_0 {
    private String algorithm = "CRC";
    private Checksum dBL = null;

    public void setAlgorithm(String string) {
        this.algorithm = string;
    }

    public void aQP() {
        if (this.dBL != null) {
            return;
        }
        if ("CRC".equalsIgnoreCase(this.algorithm)) {
            this.dBL = new CRC32();
        } else if ("ADLER".equalsIgnoreCase(this.algorithm)) {
            this.dBL = new Adler32();
        } else {
            throw new eq_2(new NoSuchAlgorithmException());
        }
    }

    public boolean isValid() {
        return "CRC".equalsIgnoreCase(this.algorithm) || "ADLER".equalsIgnoreCase(this.algorithm);
    }

    public String b(File file) {
        this.aQP();
        String string = null;
        try {
            if (file.canRead()) {
                this.dBL.reset();
                FileInputStream fileInputStream = new FileInputStream(file);
                CheckedInputStream checkedInputStream = new CheckedInputStream(fileInputStream, this.dBL);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(checkedInputStream);
                while (bufferedInputStream.read() != -1) {
                }
                string = Long.toString(checkedInputStream.getChecksum().getValue());
                bufferedInputStream.close();
            }
        }
        catch (Exception exception) {
            string = null;
        }
        return string;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<ChecksumAlgorithm:");
        stringBuffer.append("algorithm=").append(this.algorithm);
        stringBuffer.append(">");
        return stringBuffer.toString();
    }
}

