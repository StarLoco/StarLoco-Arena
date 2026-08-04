/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.util.texture.TextureCoords;
import javax.media.opengl.GL;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;

public class Vb {
    private static final int bSb = 9729;
    private static final int bSc = 9729;
    private ph_1 bSd;
    private int bSe = 9729;
    private int bSf = 9729;

    public ph_1 aib() {
        return this.bSd;
    }

    public void b(ph_1 ph_12) {
        this.bSd = ph_12;
        if (ph_12 != null) {
            try {
                GL gL = GLU.getCurrentGL();
                gL.glTexParameterf(ph_12.getTarget(), 10241, this.bSe);
                gL.glTexParameterf(ph_12.getTarget(), 10240, this.bSf);
            }
            catch (GLException gLException) {
                cW.fd().c(this);
            }
        }
    }

    public void aic() {
        if (this.bSd != null) {
            this.bSd.enable();
            this.bSd.bind();
            GL gL = GLU.getCurrentGL();
            gL.glTexParameterf(this.bSd.getTarget(), 10241, this.bSe);
            gL.glTexParameterf(this.bSd.getTarget(), 10240, this.bSf);
            this.bSd.disable();
        }
    }

    public int aid() {
        return this.bSe;
    }

    public void ip(int n2) {
        this.bSe = n2;
        GL gL = GLU.getCurrentGL();
        gL.glTexParameterf(this.bSd.getTarget(), 10241, this.bSe);
    }

    public int aie() {
        return this.bSf;
    }

    public void iq(int n2) {
        this.bSf = n2;
        GL gL = GLU.getCurrentGL();
        gL.glTexParameterf(this.bSd.getTarget(), 10240, this.bSf);
    }

    public void enable() {
        if (this.bSd != null) {
            this.bSd.enable();
        }
    }

    public void disable() {
        if (this.bSd != null) {
            this.bSd.disable();
        }
    }

    public void bind() {
        if (this.bSd != null) {
            this.bSd.bind();
        }
    }

    public void reset() {
        if (this.bSd != null) {
            this.bSd.dispose();
            this.bSd = null;
        }
        this.bSe = 9729;
        this.bSf = 9729;
    }

    public float aif() {
        if (this.bSd != null) {
            return this.bSd.getImageWidth();
        }
        return 0.0f;
    }

    public float aig() {
        if (this.bSd != null) {
            return this.bSd.getImageHeight();
        }
        return 0.0f;
    }

    public TextureCoords getImageTexCoords() {
        if (this.bSd != null) {
            return this.bSd.getImageTexCoords();
        }
        return null;
    }

    public boolean L(int n2, int n3) {
        return this.bSd.L(n2, n3);
    }
}

