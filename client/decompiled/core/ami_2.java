/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.cg.CgGL;

/*
 * Renamed from amI
 */
public class ami_2
extends gJ {
    public boolean GO() {
        String string = this.go.glGetString(7939);
        if (string == null || !string.contains("GL_ARB_vertex_shader")) {
            return false;
        }
        try {
            return CgGL.cgGLIsProfileSupported(6150);
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    public amA GP() {
        return amA.cHF;
    }
}

