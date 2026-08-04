/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;

public class axL {
    private static final Logger a = Logger.getLogger(axL.class);
    private static final axL dkg = new axL();
    private String aJ;
    private aBp dkh = new aBp();
    private asu_0 dki = new asu_0();

    private axL() {
    }

    public static axL aKF() {
        return dkg;
    }

    public void reset() {
        this.dki.reset();
        this.dkh.clear();
    }

    public String getPath() {
        return this.aJ;
    }

    public void setPath(String string) {
        this.aJ = string;
    }

    public boolean C(int[] nArray) {
        return this.dkh.F(nArray);
    }

    public boolean mJ(int n2) {
        return this.dkh.nk(n2);
    }

    public void mK(int n2) {
        assert (this.aJ != null) : "PaperMapManager : Path undefined";
        String string = String.format(this.aJ, n2);
        try {
            URL uRL = new URL(string);
            this.dki.b(new acf(new BufferedInputStream(uRL.openStream())));
        }
        catch (MalformedURLException malformedURLException) {
            a.warn((Object)("Problem during PaperMapManager Load : invalid URL " + string));
        }
        catch (IOException iOException) {
            a.warn((Object)"Exception during PaperMapManager Load : ", (Throwable)iOException);
        }
    }

    public aBp mh(int n2) {
        return this.dki.mh(n2);
    }

    public jg_0 aKG() {
        jg_0 jg_02 = new jg_0();
        this.a(new HA(this, jg_02));
        return jg_02;
    }

    public void a(iq_0 iq_02) {
        if (this.dkh == null) {
            return;
        }
        if (!this.dkh.isEmpty()) {
            this.dkh.a(new IH(this, iq_02));
        }
    }

    public void a(tf_0 tf_02) {
        if (this.dkh == null) {
            return;
        }
        if (!this.dkh.isEmpty()) {
            this.dkh.a(new ig_0(this, tf_02));
        }
    }

    static /* synthetic */ asu_0 a(axL axL2) {
        return axL2.dki;
    }
}

