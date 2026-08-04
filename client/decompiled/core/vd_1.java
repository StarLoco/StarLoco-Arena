/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.InputStream;
import java.io.Reader;

/*
 * Renamed from Vd
 */
public interface vd_1 {
    public static final ClassLoader bSg = ClassLoader.getSystemClassLoader().getParent();
    public static final String bSh = "org.codehaus.janino.source_debugging.enable";
    public static final String bSi = "org.codehaus.janino.source_debugging.dir";

    public void e(ClassLoader var1);

    public void c(boolean var1, boolean var2, boolean var3);

    public void a(String var1, Reader var2);

    public void h(Reader var1);

    public void k(InputStream var1);

    public void b(String var1, InputStream var2);

    public void a(InputStream var1, String var2);

    public void a(String var1, InputStream var2, String var3);

    public void gk(String var1);

    public void I(String var1, String var2);

    public void B(File var1);

    public void f(File var1, String var2);

    public void gl(String var1);

    public void J(String var1, String var2);
}

