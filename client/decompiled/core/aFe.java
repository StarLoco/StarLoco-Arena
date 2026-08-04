/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.log4j.Logger;

public abstract class aFe
implements zt_1 {
    public static final Logger a = Logger.getLogger(aFe.class);
    protected sP dFc = new aev_1(this, null);
    private String aJ;
    private final HashMap dFd = new HashMap();
    private long dFe;
    private long dFf;
    private long dFg;
    private long dFh;
    private boolean dFi = true;

    protected aFe(long l2, boolean bl2) {
        this.dFf = l2;
        this.dFi = bl2;
    }

    public abstract String getExtension();

    public abstract FilenameFilter getFilenameFilter();

    protected abstract sP g(InputStream var1);

    private sP ld(String string) {
        Object object;
        InputStream inputStream;
        String string2;
        block7: {
            assert (this.aJ != null);
            assert (string != null);
            string2 = this.aJ + string;
            inputStream = null;
            try {
                object = new URL(string2);
                inputStream = ((URL)object).openStream();
            }
            catch (Exception exception) {
                File file = new File(string2);
                if (!file.exists()) break block7;
                inputStream = new FileInputStream(file);
            }
        }
        if (inputStream == null) {
            a.error((Object)("Impossible d'ouvrir un stream pour le fichier " + string2));
            object = this.dFc;
        } else {
            object = this.g(new BufferedInputStream(inputStream));
            this.dFe += ((sP)object).ef();
            this.aRw();
            assert (this.dFe >= 0L);
        }
        this.dFd.put(string, object);
        return object;
    }

    private void aRw() {
        Iterator iterator = this.dFd.values().iterator();
        while (iterator.hasNext() && this.dFf < this.dFe && this.dFd.size() > 0) {
            sP sP2 = (sP)iterator.next();
            this.dFe -= sP2.ef();
            iterator.remove();
            ++this.dFg;
            assert (this.dFd.size() == 0 && this.dFe == 0L || this.dFd.size() > 0 && this.dFe > 0L);
        }
    }

    public final Object le(String string) {
        sP sP2;
        assert (string != null);
        sP sP3 = sP2 = this.dFi ? (sP)this.dFd.get(string) : null;
        if (sP2 == null) {
            sP2 = this.ld(string);
            ++this.dFh;
        }
        return sP2.get();
    }

    public final void eu(long l2) {
        this.dFf = l2;
    }

    public final String getPath() {
        return this.aJ;
    }

    public final void setPath(String string) {
        assert (string != null);
        this.aJ = string;
    }

    public void aRx() {
        this.d(Long.MAX_VALUE, 1.0f);
        this.eu(this.dFe);
    }

    public void aRy() {
        this.dFd.clear();
    }

    public void d(long l2, float f) {
        block12: {
            this.eu(l2);
            assert (this.aJ != null);
            l2 = (int)((float)this.dFf * f);
            try {
                URL uRL = new URL(this.aJ);
                if (uRL.getProtocol().equals("file")) {
                    File file = new File(uRL.getPath() + File.separator);
                    String[] stringArray = file.list(this.getFilenameFilter());
                    for (int j = 0; j < stringArray.length && this.dFe < l2; ++j) {
                        this.ld(stringArray[j]);
                    }
                    break block12;
                }
                if (!uRL.getProtocol().equals("jar")) break block12;
                JarFile jarFile = null;
                try {
                    jarFile = new JarFile(uRL.getPath());
                    Enumeration<JarEntry> enumeration = jarFile.entries();
                    while (enumeration.hasMoreElements() && this.dFe < l2) {
                        String string = enumeration.nextElement().getName();
                        if (!string.endsWith(this.getExtension())) continue;
                        this.ld(string);
                    }
                }
                catch (Exception exception) {
                    a.error((Object)"Exception", (Throwable)exception);
                }
                if (jarFile != null) {
                    jarFile.close();
                }
            }
            catch (Exception exception) {
                a.error((Object)("Impossible de pr\u00e9charger le contenu de " + this.aJ + " depuis une URL(tentative depuis un fichier"));
                try {
                    File file = new File(this.aJ + File.separator);
                    String[] stringArray = file.list(this.getFilenameFilter());
                    for (int j = 0; j < stringArray.length && this.dFe < l2; ++j) {
                        this.ld(stringArray[j]);
                    }
                }
                catch (Exception exception2) {
                    a.error((Object)"Exception", (Throwable)exception2);
                }
            }
        }
        a.info((Object)("PRELOAD " + this));
    }

    public void a(aej_1 aej_12) {
    }

    public void I(String string) {
    }

    public void e(String string, String string2) {
    }

    public void H(String string) {
    }

    public void b(aej_1 aej_12) {
    }

    public String toString() {
        return this.getClass().getSimpleName() + ": cacheSize=" + this.dFd.size() + "\t size=" + this.dFe + "octets/ " + this.dFf + " missCache=" + this.dFh + " notEnoughSize=" + this.dFg;
    }
}

