/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

/*
 * Renamed from aem
 */
public abstract class aem_2
implements vd_1 {
    public abstract void a(String var1, Reader var2);

    public final void h(Reader reader) {
        this.a(null, reader);
    }

    public final void k(InputStream inputStream) {
        this.b(null, inputStream);
    }

    public final void b(String string, InputStream inputStream) {
        this.a(string, inputStream, null);
    }

    public final void a(InputStream inputStream, String string) {
        this.h(string == null ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, string));
    }

    public final void a(String string, InputStream inputStream, String string2) {
        this.a(string, string2 == null ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, string2));
    }

    public void gk(String string) {
        this.I(null, string);
    }

    public void I(String string, String string2) {
        try {
            this.a(string, new StringReader(string2));
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            throw new RuntimeException("SNO: StringReader throws IOException");
        }
    }

    public final void B(File file) {
        this.f(file, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void f(File file, String string) {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            this.a(file.getAbsolutePath(), string == null ? new InputStreamReader(fileInputStream) : new InputStreamReader((InputStream)fileInputStream, string));
            ((InputStream)fileInputStream).close();
            fileInputStream = null;
        }
        finally {
            if (fileInputStream != null) {
                try {
                    ((InputStream)fileInputStream).close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    public final void gl(String string) {
        this.J(string, null);
    }

    public final void J(String string, String string2) {
        this.f(new File(string), string2);
    }

    public static String i(Reader reader) {
        int n2;
        StringBuffer stringBuffer = new StringBuffer();
        char[] cArray = new char[4096];
        while ((n2 = reader.read(cArray)) != -1) {
            stringBuffer.append(cArray, 0, n2);
        }
        String string = stringBuffer.toString();
        return string;
    }

    public abstract void c(boolean var1, boolean var2, boolean var3);

    public abstract void e(ClassLoader var1);
}

