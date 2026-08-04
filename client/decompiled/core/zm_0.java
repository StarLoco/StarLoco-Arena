/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.sun.opengl.util.BufferUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import javax.media.opengl.GL;
import org.apache.log4j.Logger;

/*
 * Renamed from ZM
 */
public class zm_0 {
    protected static final Logger a = Logger.getLogger(zm_0.class);
    private static final zm_0 cee = new zm_0();
    private adr cef;

    private zm_0() {
    }

    public static zm_0 aot() {
        return cee;
    }

    public void m(GL gL) {
        this.cef = new adr(this, gL.glGetString(7938));
    }

    public adr aou() {
        return this.cef;
    }

    public boolean bh(int n2, int n3) {
        if (this.cef == null) {
            a.error((Object)"Il faut appeler la fonction readVersion(GL)");
        }
        return this.cef.a(new adr(this, n2, n3, 0)) < 0;
    }

    public static void a(GL gL, HashMap hashMap) {
        hashMap.put("Renderer", gL.glGetString(7937));
        hashMap.put("Vendor", gL.glGetString(7936));
        hashMap.put("Version", gL.glGetString(7938));
        hashMap.put("Extensions", gL.glGetString(7939));
    }

    public static void n(GL gL) {
        a.info((Object)"------- GL dump ---------");
        IntBuffer intBuffer = BufferUtil.newIntBuffer(4);
        intBuffer.rewind();
        boolean bl2 = gL.glIsEnabled(2929);
        boolean bl3 = gL.glIsEnabled(3089);
        boolean bl4 = gL.glIsEnabled(3008);
        boolean bl5 = gL.glIsEnabled(2960);
        boolean bl6 = gL.glIsEnabled(2884);
        a.info((Object)("GL_RENDERER : " + gL.glGetString(7937)));
        a.info((Object)("GL_VENDOR : " + gL.glGetString(7936)));
        a.info((Object)("GL_VERSION : " + gL.glGetString(7938)));
        a.info((Object)("GL_DEPTH_TEST = " + bl2));
        a.info((Object)("GL_SCISSOR_TEST = " + bl3));
        a.info((Object)("GL_ALPHA_TEST = " + bl4));
        a.info((Object)("GL_STENCIL_TEST = " + bl5));
        a.info((Object)("GL_CULL_FACE = " + bl6));
        gL.glGetIntegerv(2978, intBuffer);
        a.info((Object)("GL_VIEWPORT = [" + intBuffer.get(0) + ";" + intBuffer.get(1) + "] - [" + intBuffer.get(2) + ";" + intBuffer.get(3) + "]"));
        gL.glGetIntegerv(3088, intBuffer);
        a.info((Object)("GL_SCISSOR_BOX = [" + intBuffer.get(0) + ";" + intBuffer.get(1) + "] - [" + intBuffer.get(2) + ";" + intBuffer.get(3) + "]"));
    }

    public static String o(GL gL) {
        StringBuilder stringBuilder = new StringBuilder();
        IntBuffer intBuffer = BufferUtil.newIntBuffer(4);
        intBuffer.rewind();
        boolean bl2 = gL.glIsEnabled(2929);
        boolean bl3 = gL.glIsEnabled(3089);
        boolean bl4 = gL.glIsEnabled(3008);
        boolean bl5 = gL.glIsEnabled(2960);
        boolean bl6 = gL.glIsEnabled(2884);
        stringBuilder.append("\tGL_RENDERER : " + gL.glGetString(7937));
        stringBuilder.append("\n\tGL_VENDOR : " + gL.glGetString(7936));
        stringBuilder.append("\n\tGL_VERSION : " + gL.glGetString(7938));
        stringBuilder.append("\n\tGL_DEPTH_TEST = " + bl2);
        stringBuilder.append("\n\tGL_SCISSOR_TEST = " + bl3);
        stringBuilder.append("\n\tGL_ALPHA_TEST = " + bl4);
        stringBuilder.append("\n\tGL_STENCIL_TEST = " + bl5);
        stringBuilder.append("\n\tGL_CULL_FACE = " + bl6);
        gL.glGetIntegerv(2978, intBuffer);
        stringBuilder.append("\n\tGL_VIEWPORT = [" + intBuffer.get(0) + ";" + intBuffer.get(1) + "] - [" + intBuffer.get(2) + ";" + intBuffer.get(3) + "]");
        gL.glGetIntegerv(3088, intBuffer);
        stringBuilder.append("\n\tGL_SCISSOR_BOX = [" + intBuffer.get(0) + ";" + intBuffer.get(1) + "] - [" + intBuffer.get(2) + ";" + intBuffer.get(3) + "]");
        return stringBuilder.toString();
    }

    public static String c(FloatBuffer floatBuffer) {
        String string = "\n";
        for (int j = 0; j < 4; ++j) {
            string = string + "\t";
            for (int i2 = 0; i2 < 4; ++i2) {
                string = string + "[" + floatBuffer.get(j * 4 + i2) + "] ";
            }
            string = string + "\n";
        }
        return string;
    }

    public static void aov() {
        try {
            String string;
            File file = new File("GLFlags.txt");
            if (!file.exists()) {
                a.error((Object)("cannot open file " + file.getName()));
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("GLFlags2.txt"));
            while ((string = bufferedReader.readLine()) != null) {
                bufferedWriter.write("m_flags.add( new GLFlagDesc( " + string + ", \"" + string + "\" ) );\n");
            }
            bufferedWriter.close();
            bufferedReader.close();
        }
        catch (FileNotFoundException fileNotFoundException) {
            a.error((Object)"Exception", (Throwable)fileNotFoundException);
        }
        catch (IOException iOException) {
            a.error((Object)"Exception", (Throwable)iOException);
        }
    }
}

