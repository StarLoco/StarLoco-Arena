/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Qa {
    private URL aHf;
    private InputStream aHg;
    private byte[] Fe;
    private int bEH;
    private boolean aPS;
    private boolean bEI;

    public Qa(URL uRL) {
        this.aHf = uRL;
        this.aPS = false;
        this.bEI = false;
    }

    public void acH() {
        assert (!this.aPS) : "Stream must not be call if the file is already loaded";
        assert (!this.bEI) : "Stream must not be call if the file loading has failed";
        try {
            if (this.aHg == null) {
                this.aHg = new BufferedInputStream(this.aHf.openStream());
                this.Fe = new byte[this.aHg.available()];
                this.bEH = 0;
            }
            this.bEH += this.aHg.read(this.Fe, this.bEH, this.Fe.length - this.bEH);
            if (this.bEH == this.Fe.length) {
                this.aHg.close();
                this.aPS = true;
            }
        }
        catch (IOException iOException) {
            if (this.aHg != null) {
                this.aHg.close();
            }
            this.bEI = true;
            throw iOException;
        }
    }

    public final boolean is() {
        return this.aPS;
    }

    public final boolean acI() {
        return this.bEI;
    }

    public final URL getURL() {
        return this.aHf;
    }

    public final byte[] getData() {
        return this.Fe;
    }
}

