/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import org.xml.sax.InputSource;

/*
 * Renamed from bf
 */
public abstract class bf_2
extends ii_2 {
    protected jh_1 fm;

    public final void b(URL uRL) {
        try {
            InputStream inputStream = uRL.openStream();
            this.a(inputStream);
            inputStream.close();
        }
        catch (IOException iOException) {
            String string = "Could not open URL [" + uRL + "].";
            this.e(string, iOException);
            throw new azG(string, iOException);
        }
    }

    public final void p(String string) {
        this.c(new File(string));
    }

    public final void c(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            this.a(fileInputStream);
        }
        catch (IOException iOException) {
            String string = "Could not open [" + file.getName() + "].";
            this.e(string, iOException);
            throw new azG(string, iOException);
        }
        finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                }
                catch (IOException iOException) {
                    String string = "Could not close [" + file.getName() + "].";
                    this.e(string, iOException);
                    throw new azG(string, iOException);
                }
            }
        }
    }

    public final void a(InputStream inputStream) {
        this.a(new InputSource(inputStream));
    }

    protected abstract void a(aom_2 var1);

    protected abstract void a(jh_1 var1);

    protected zf_0 co() {
        return new zf_0();
    }

    protected void cp() {
        arz_0 arz_02 = new arz_0(this.Pb);
        this.a(arz_02);
        this.fm = new jh_1(this.Pb, arz_02, this.co());
        qq_0 qq_02 = this.fm.Vy();
        qq_02.a(this.Pb);
        this.a(this.fm);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(InputSource inputSource) {
        yx_1 yx_12 = new yx_1();
        yx_12.a(this.Pb);
        yx_12.b(inputSource);
        this.cp();
        vU vU2 = this.Pb;
        synchronized (vU2) {
            this.fm.g(yx_12.cbe);
        }
    }

    public void a(List list) {
        this.cp();
        aax_2 aax_22 = new aax_2(this.fm);
        aax_22.g(list);
    }
}

