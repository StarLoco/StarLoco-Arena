/*
 * Decompiled with CFR 0.152.
 */
import java.io.InputStream;
import java.net.URL;

/*
 * Renamed from AE
 */
public class ae_1
implements auk {
    private final URL aHf;
    private InputStream aHg;

    public ae_1(URL uRL) {
        this.aHf = uRL;
    }

    public void Hm() {
        InputStream inputStream = this.aHf.openStream();
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = inputStream.read();
        while (n2 != -1 && stringBuilder.length() < 3) {
            stringBuilder.append((char)n2);
            n2 = inputStream.read();
        }
        inputStream.close();
        boolean bl2 = !stringBuilder.toString().toUpperCase().equals("OGG");
        this.aHg = bl2 ? new azj(this.aHf.openStream()) : this.aHf.openStream();
    }

    public void reset() {
        if (this.aHg != null) {
            this.aHg.close();
        }
        this.Hm();
    }

    public void close() {
        if (this.aHg != null) {
            this.aHg.close();
            this.aHg = null;
        }
    }

    public boolean Hn() {
        return false;
    }

    public void seek(long l2) {
    }

    public long length() {
        return 0L;
    }

    public long tell() {
        return 0L;
    }

    public int read() {
        return this.aHg.read();
    }

    public int read(byte[] byArray) {
        return this.aHg.read(byArray);
    }

    public int read(byte[] byArray, int n2, int n3) {
        return this.aHg.read(byArray, n2, n3);
    }

    public String getDescription() {
        return this.aHf.toString();
    }
}

