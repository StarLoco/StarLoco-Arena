/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.cg.CGpass;
import com.sun.opengl.cg.CGtechnique;
import com.sun.opengl.cg.CgGL;
import javax.media.opengl.Threading;

/*
 * Renamed from VS
 */
public class vs_1
extends aaW {
    private final CGtechnique bTv;

    public vs_1(CGtechnique cGtechnique) {
        this.bTv = cGtechnique;
        this.setName(CgGL.cgGetTechniqueName(cGtechnique));
        this.cgV = new ars_0[this.a(cGtechnique)];
        int n2 = 0;
        CGpass cGpass = CgGL.cgGetFirstPass(cGtechnique);
        while (cGpass != null) {
            this.cgV[n2++] = new sq_0(cGpass);
            cGpass = CgGL.cgGetNextPass(cGpass);
        }
    }

    public boolean o(db_2 db_22) {
        assert (Threading.isOpenGLThread()) : "Technique validation work only in an openGL thread";
        this.cgW = CgGL.cgValidateTechnique(this.bTv);
        return this.cgW;
    }

    private int a(CGtechnique cGtechnique) {
        int n2 = 0;
        CGpass cGpass = CgGL.cgGetFirstPass(cGtechnique);
        while (cGpass != null) {
            ++n2;
            cGpass = CgGL.cgGetNextPass(cGpass);
        }
        return n2;
    }
}

