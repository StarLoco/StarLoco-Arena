/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLContext;
import javax.media.opengl.glu.GLU;
import org.apache.log4j.Logger;

public class cW
extends zr_2
implements zt_1 {
    private GLContext kl;
    private static Logger a = Logger.getLogger(cW.class);
    private static final cW km = new cW();
    private ArrayList kn;
    private final jg_0 ko = new jg_0();
    private final ArrayList kp = new ArrayList();
    private final ArrayList kq = new ArrayList();

    protected cW() {
        super(new df_0(), new ck(), false);
        this.kn = new ArrayList();
        this.iO(60);
    }

    public static cW fd() {
        return km;
    }

    public static int S(int n2) {
        --n2;
        n2 |= n2 >> 1;
        n2 |= n2 >> 2;
        n2 |= n2 >> 4;
        n2 |= n2 >> 8;
        n2 |= n2 >> 16;
        return ++n2;
    }

    public static ph_1 a(int n2, int n3, boolean bl2, int n4, boolean bl3) {
        int n5 = n2;
        int n6 = n3;
        if (bl2) {
            n5 = cW.S(n5);
            n6 = cW.S(n6);
        }
        ByteBuffer byteBuffer = BufferUtil.newByteBuffer(n5 * n6 * 4);
        ph_1 ph_12 = new ph_1(3553, n2, n3, byteBuffer, 6408, false, 9728, 9728, 10496, 10496);
        try {
            ph_12.initialize();
        }
        catch (Exception exception) {
            cW.km.kp.add(ph_12);
        }
        return ph_12;
    }

    public static Vb a(int n2, int n3, byte[] byArray, int n4) {
        ph_1 ph_12 = new ph_1(3553, n2, n3, ByteBuffer.wrap(byArray), n4, false);
        if (byArray.length != n2 * n3 * 4) {
            throw new RuntimeException("Unsupported image mode");
        }
        try {
            ph_12.initialize();
        }
        catch (Exception exception) {
            cW.km.kp.add(ph_12);
        }
        Vb vb = new Vb();
        vb.b(ph_12);
        return vb;
    }

    public static Vb a(BufferedImage bufferedImage) {
        ph_1 ph_12 = new ph_1(3553, bufferedImage, 32768, false);
        switch (bufferedImage.getType()) {
            case 2: 
            case 3: 
            case 6: 
            case 7: {
                break;
            }
            default: {
                throw new RuntimeException("Unsupported image type");
            }
        }
        try {
            ph_12.initialize();
        }
        catch (Exception exception) {
            cW.km.kp.add(ph_12);
        }
        Vb vb = new Vb();
        vb.b(ph_12);
        return vb;
    }

    public static ph_1 F(String string) {
        InputStream inputStream = null;
        try {
            URL uRL = new URL(string);
            inputStream = uRL.openStream();
        }
        catch (Exception exception) {
            File file = new File(string);
            try {
                inputStream = new FileInputStream(file);
            }
            catch (FileNotFoundException fileNotFoundException) {
                a.error((Object)("Fichier introuvable : " + string));
            }
        }
        return cW.c(new BufferedInputStream(inputStream));
    }

    public static ph_1 c(InputStream inputStream) {
        int n2;
        int n3;
        int n4;
        ByteBuffer byteBuffer;
        TextureData textureData = null;
        if (inputStream != null) {
            textureData = TextureIO.newTextureData(inputStream, 6408, 6408, false, "tga");
            byteBuffer = (ByteBuffer)textureData.getBuffer();
            n4 = textureData.getWidth();
            n3 = textureData.getHeight();
        } else {
            n4 = 30;
            n3 = 30;
            byte[] byArray = new byte[4 * n4 * n3];
            for (n2 = 0; n2 < 4 * n4 * n3; n2 += 4) {
                byArray[n2] = -128;
                byArray[n2 + 1] = 0;
                byArray[n2 + 2] = -128;
                byArray[n2 + 3] = -128;
            }
            byteBuffer = ByteBuffer.wrap(byArray);
        }
        n2 = byteBuffer.limit() / (n4 * n3);
        int n5 = n2 == 3 ? 32992 : 32993;
        if (byteBuffer != null) {
            ph_1 ph_12 = new ph_1(3553, n4, n3, byteBuffer, n5, true);
            try {
                ph_12.initialize();
            }
            catch (Exception exception) {
                cW.km.kp.add(ph_12);
            }
            if (textureData != null) {
                textureData.flush();
            }
            return ph_12;
        }
        a.error((Object)"createRawTextureFromFile => null");
        return null;
    }

    public static anm_0 a(String string, boolean bl2) {
        return cW.a(string, bl2, true);
    }

    public static anm_0 a(String string, boolean bl2, boolean bl3) {
        zG zG2 = (zG)cW.fd().aox();
        anm_0 anm_02 = (anm_0)zG2.api();
        zG2.setFileName(string);
        zG2.cR(bl2);
        if (bl3) {
            ph_1 ph_12 = null;
            try {
                ph_12 = cW.F(string);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
            anm_02.b(ph_12);
        }
        return anm_02;
    }

    public static anm_0 G(String string) {
        return cW.a(string, true);
    }

    private void a(ph_1 ph_12) {
        int n2;
        GL gL = GLU.getCurrentGL();
        int[] nArray = new int[1];
        gL.glGenTextures(nArray.length, nArray, 0);
        ph_12.cE(nArray[0]);
        int n3 = 4 * ph_12.getWidth();
        ByteBuffer byteBuffer = ByteBuffer.allocate(n3 * ph_12.getHeight());
        ByteBuffer byteBuffer2 = ph_12.getData();
        ph_12.l(null);
        byte[] byArray = new byte[ph_12.getImageWidth() * 4];
        byte[] byArray2 = new byte[(ph_12.getWidth() - ph_12.getImageWidth()) * 4];
        if (ph_12.tX()) {
            n2 = n3 * ph_12.getImageHeight();
            for (int j = 0; j < ph_12.getImageHeight(); ++j) {
                byteBuffer2.get(byArray);
                byteBuffer.position(n2 -= n3);
                byteBuffer.put(byArray);
            }
        } else {
            for (n2 = 0; n2 < ph_12.getImageHeight(); ++n2) {
                byteBuffer2.get(byArray);
                byteBuffer.put(byArray);
                byteBuffer.put(byArray2);
            }
        }
        byteBuffer.rewind();
        n2 = 0;
        switch (ph_12.tW()) {
            case 6408: 
            case 32993: {
                n2 = 3;
                break;
            }
            case 32768: {
                n2 = 0;
            }
        }
        byteBuffer.rewind();
        ph_12.bind();
        gL.glTexImage2D(ph_12.getTarget(), 0, 4, ph_12.getWidth(), ph_12.getHeight(), 0, ph_12.tW(), 5121, byteBuffer);
        gL.glTexParameterf(3553, 10242, ph_12.ua());
        gL.glTexParameterf(3553, 10243, ph_12.ub());
        gL.glTexParameterf(3553, 10240, ph_12.tY());
        gL.glTexParameterf(3553, 10241, ph_12.tZ());
    }

    private void fe() {
        for (int j = this.kp.size() - 1; j >= 0; --j) {
            ph_1 ph_12 = (ph_1)this.kp.get(j);
            ph_12.initialize();
            if (!ph_12.isInitialized()) continue;
            this.kp.remove(j);
        }
    }

    public void c(Vb vb) {
        this.kq.add(vb);
    }

    public void a(aej_1 aej_12) {
        this.kn.add(aej_12);
    }

    public void b(aej_1 aej_12) {
        this.kn.remove(aej_12);
    }

    public void H(String string) {
        for (aej_1 aej_12 : this.kn) {
            aej_12.kZ(string);
        }
    }

    public void I(String string) {
        for (aej_1 aej_12 : this.kn) {
            aej_12.la(string);
        }
    }

    public void e(String string, String string2) {
        for (aej_1 aej_12 : this.kn) {
            aej_12.ar(string, string2);
        }
    }

    public void T(int n2) {
        if (!this.ko.contains(n2)) {
            this.ko.add(n2);
        }
    }

    public void update() {
        super.update();
        if (this.ko.size() > 0) {
            int[] nArray = this.ko.nm();
            GLU.getCurrentGL().glDeleteTextures(nArray.length, nArray, 0);
            this.ko.clear();
        }
        if (this.kp.size() > 0) {
            this.fe();
        }
        if (this.kq.size() > 0) {
            for (int j = this.kq.size() - 1; j >= 0; --j) {
                ((Vb)this.kq.get(j)).aic();
            }
            this.kq.clear();
        }
    }

    public void a(Frame frame, GLContext gLContext) {
        GLCanvas gLCanvas = new GLCanvas(null, null, gLContext, null);
        this.kl = gLCanvas.getContext();
        frame.add(gLCanvas);
    }
}

