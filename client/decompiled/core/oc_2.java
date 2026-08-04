/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.cg.CgGL;
import java.nio.IntBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

/*
 * Renamed from oC
 */
public final class oc_2 {
    private int aay;
    private int aaz;
    private int aaA;
    private GL go;

    public oc_2() {
    }

    public oc_2(int n2, int n3, int n4) {
        this.aay = n2;
        this.aaz = n3;
        this.aaA = n4;
        this.go = GLU.getCurrentGL();
    }

    public boolean e(GL gL) {
        boolean bl2 = this.cy(CgGL.cgGLGetLatestProfile(8));
        boolean bl3 = this.cz(CgGL.cgGLGetLatestProfile(9));
        boolean bl4 = this.tK() >= this.aaA;
        return bl2 && bl3 && bl4;
    }

    public boolean cy(int n2) {
        return this.cA(n2) >= this.cA(this.aay);
    }

    public boolean cz(int n2) {
        return this.cB(n2) >= this.cB(this.aaz);
    }

    public int tK() {
        IntBuffer intBuffer = asM.aFI().md(1);
        this.go.glGetIntegerv(34018, intBuffer);
        int n2 = intBuffer.get(0);
        asM.aFI().a(intBuffer);
        return n2;
    }

    public boolean tL() {
        return this.go.isExtensionAvailable("GL_EXT_texture_rectangle");
    }

    public boolean tM() {
        return this.go.isExtensionAvailable("GL_EXT_framebuffer_object");
    }

    private int cA(int n2) {
        int n3 = 0;
        switch (n2) {
            case 6150: {
                n3 = 11;
                break;
            }
            case 7001: {
                n3 = 30;
                break;
            }
            case 6148: {
                n3 = 20;
                break;
            }
            case 6146: {
                n3 = 11;
                break;
            }
            case 6153: {
                n3 = 11;
                break;
            }
            case 6154: {
                n3 = 20;
                break;
            }
            case 6155: {
                n3 = 29;
            }
        }
        return n3;
    }

    private int cB(int n2) {
        int n3 = 0;
        switch (n2) {
            case 7000: {
                n3 = 20;
                break;
            }
            case 6151: {
                n3 = 30;
                break;
            }
            case 6149: {
                n3 = 20;
                break;
            }
            case 6147: {
                n3 = 13;
                break;
            }
            case 6159: {
                n3 = 11;
                break;
            }
            case 6161: {
                n3 = 13;
                break;
            }
            case 6162: {
                n3 = 20;
                break;
            }
            case 6163: {
                n3 = 29;
            }
        }
        return n3;
    }
}

