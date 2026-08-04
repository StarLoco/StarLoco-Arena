/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.sun.opengl.util.Animator;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import org.apache.log4j.Logger;

/*
 * Renamed from pG
 */
public class pg_2
extends GLCanvas {
    private static Logger a = Logger.getLogger(pg_2.class);
    private bx_2 wU;
    private Animator acm = new Animator(this);

    private static GLCapabilities a(ath_0 ath_02) {
        GLCapabilities gLCapabilities = new GLCapabilities();
        gLCapabilities.setHardwareAccelerated(true);
        gLCapabilities.setDoubleBuffered(ath_02.cUk);
        gLCapabilities.setSampleBuffers(false);
        gLCapabilities.setDepthBits(ath_02.cUl);
        gLCapabilities.setStencilBits(ath_02.cUm);
        switch (ath_02.cSt) {
            case 16: {
                gLCapabilities.setRedBits(4);
                gLCapabilities.setGreenBits(4);
                gLCapabilities.setBlueBits(4);
                gLCapabilities.setAlphaBits(4);
                break;
            }
            case 32: {
                gLCapabilities.setAlphaBits(8);
            }
            case 24: {
                gLCapabilities.setRedBits(8);
                gLCapabilities.setGreenBits(8);
                gLCapabilities.setBlueBits(8);
            }
        }
        return gLCapabilities;
    }

    public pg_2(ath_0 ath_02) {
        super(pg_2.a(ath_02));
    }

    public Animator un() {
        return this.acm;
    }

    public bx_2 kW() {
        return this.wU;
    }

    public void a(bx_2 bx_22) {
        if (bx_22 != this.wU && bx_22 != null) {
            if (this.wU != null) {
                this.removeGLEventListener(this.wU);
                this.removeMouseListener(this.wU);
                this.removeMouseMotionListener(this.wU);
                this.removeKeyListener(this.wU);
                this.removeFocusListener(this.wU);
            }
            this.wU = bx_22;
            this.addGLEventListener(this.wU);
            this.addMouseListener(this.wU);
            this.addMouseMotionListener(this.wU);
            this.addMouseWheelListener(this.wU);
            this.addKeyListener(this.wU);
            this.addFocusListener(this.wU);
        }
    }
}

