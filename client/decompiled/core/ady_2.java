/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUtessellatorCallback;

/*
 * Renamed from ady
 */
class ady_2
implements GLUtessellatorCallback {
    private GL go;
    private GLU gp;
    final /* synthetic */ ava_0 cnd;

    public ady_2(ava_0 ava_02, GL gL, GLU gLU) {
        this.cnd = ava_02;
        this.go = gL;
        this.gp = gLU;
    }

    public void begin(int n2) {
        this.go.glBegin(n2);
    }

    public void beginData(int n2, Object object) {
    }

    public void combine(double[] dArray, Object[] objectArray, float[] fArray, Object[] objectArray2) {
    }

    public void combineData(double[] dArray, Object[] objectArray, float[] fArray, Object[] objectArray2, Object object) {
    }

    public void edgeFlag(boolean bl2) {
    }

    public void edgeFlagData(boolean bl2, Object object) {
    }

    public void end() {
        this.go.glEnd();
    }

    public void endData(Object object) {
    }

    public void error(int n2) {
        String string = this.gp.gluErrorString(n2);
        System.err.println("Tessellation Error: " + string);
    }

    public void errorData(int n2, Object object) {
    }

    public void vertex(Object object) {
        double[] dArray = (double[])object;
        this.go.glVertex2dv(dArray, 0);
    }

    public void vertexData(Object object, Object object2) {
    }
}

