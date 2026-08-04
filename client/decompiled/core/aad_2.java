/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.cg.CgGL;

/*
 * Renamed from aad
 */
public class aad_2
extends gJ {
    public boolean GO() {
        String string = this.go.glGetString(7939);
        if (string == null || !string.contains("GL_ARB_fragment_shader")) {
            return false;
        }
        try {
            return CgGL.cgGLIsProfileSupported(7000);
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    public amA GP() {
        return amA.cHE;
    }
}

