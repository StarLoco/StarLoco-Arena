/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.util.j2d.TextureRenderer;
import java.awt.EventQueue;
import java.awt.Frame;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

class aLE
implements GLEventListener {
    private GLU dVS = new GLU();
    private Frame dVT;
    final /* synthetic */ afg_0 bKu;

    aLE(afg_0 afg_02, Frame frame) {
        this.bKu = afg_02;
        this.dVT = frame;
    }

    public void display(GLAutoDrawable gLAutoDrawable) {
        GL gL = gLAutoDrawable.getGL();
        gL.glClear(16640);
        if (afg_0.f(this.bKu) == null) {
            return;
        }
        TextureRenderer textureRenderer = afg_0.t(this.bKu);
        int n2 = textureRenderer.getWidth();
        int n3 = textureRenderer.getHeight();
        textureRenderer.beginOrthoRendering(n2, n3);
        textureRenderer.drawOrthoRect(0, 0);
        textureRenderer.endOrthoRendering();
        if (this.dVT.getWidth() != n2 || this.dVT.getHeight() != n3) {
            EventQueue.invokeLater(new my_1(this, n2, n3));
        }
    }

    public void init(GLAutoDrawable gLAutoDrawable) {
    }

    public void reshape(GLAutoDrawable gLAutoDrawable, int n2, int n3, int n4, int n5) {
    }

    public void displayChanged(GLAutoDrawable gLAutoDrawable, boolean bl2, boolean bl3) {
    }

    static /* synthetic */ Frame a(aLE aLE2) {
        return aLE2.dVT;
    }
}

