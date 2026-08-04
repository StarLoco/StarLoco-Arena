/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.util.BufferUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

/*
 * Renamed from axl
 */
class axl_0 {
    int djo = 0;
    FloatBuffer djp;
    FloatBuffer djq;
    boolean djr;
    int djs;
    int djt;
    final /* synthetic */ afg_0 bKu;

    axl_0(afg_0 afg_02) {
        this.bKu = afg_02;
        GL gL = GLU.getCurrentGL();
        this.djq = BufferUtil.newFloatBuffer(1200);
        this.djp = BufferUtil.newFloatBuffer(800);
        this.djr = afg_0.a(afg_02, gL);
        if (this.djr) {
            try {
                int[] nArray = new int[2];
                gL.glGenBuffers(2, IntBuffer.wrap(nArray));
                this.djs = nArray[0];
                this.djt = nArray[1];
                gL.glBindBuffer(34962, this.djs);
                gL.glBufferData(34962, 4800, null, 35040);
                gL.glBindBuffer(34962, this.djt);
                gL.glBufferData(34962, 3200, null, 35040);
            }
            catch (Exception exception) {
                afg_0.a(afg_02, false);
                this.djr = false;
            }
        }
    }

    public void glTexCoord2f(float f, float f2) {
        this.djp.put(f);
        this.djp.put(f2);
    }

    public void glVertex3f(float f, float f2, float f3) {
        this.djq.put(f);
        this.djq.put(f2);
        this.djq.put(f3);
        ++this.djo;
        if (this.djo >= 400) {
            this.aJU();
        }
    }

    private void aJU() {
        if (afg_0.w(this.bKu)) {
            this.aJV();
        } else {
            this.aJW();
        }
    }

    private void aJV() {
        if (this.djo > 0) {
            GL gL = GLU.getCurrentGL();
            this.djq.rewind();
            this.djp.rewind();
            gL.glEnableClientState(32884);
            if (this.djr) {
                gL.glBindBuffer(34962, this.djs);
                gL.glBufferSubData(34962, 0, this.djo * 12, this.djq);
                gL.glVertexPointer(3, 5126, 0, 0L);
            } else {
                gL.glVertexPointer(3, 5126, 0, this.djq);
            }
            gL.glEnableClientState(32888);
            if (this.djr) {
                gL.glBindBuffer(34962, this.djt);
                gL.glBufferSubData(34962, 0, this.djo * 8, this.djp);
                gL.glTexCoordPointer(2, 5126, 0, 0L);
            } else {
                gL.glTexCoordPointer(2, 5126, 0, this.djp);
            }
            gL.glDrawArrays(7, 0, this.djo);
            this.djq.rewind();
            this.djp.rewind();
            this.djo = 0;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void aJW() {
        if (this.djo > 0) {
            GL gL = GLU.getCurrentGL();
            gL.glBegin(7);
            try {
                int n2 = this.djo / 4;
                this.djq.rewind();
                this.djp.rewind();
                for (int j = 0; j < n2; ++j) {
                    gL.glTexCoord2f(this.djp.get(), this.djp.get());
                    gL.glVertex3f(this.djq.get(), this.djq.get(), this.djq.get());
                    gL.glTexCoord2f(this.djp.get(), this.djp.get());
                    gL.glVertex3f(this.djq.get(), this.djq.get(), this.djq.get());
                    gL.glTexCoord2f(this.djp.get(), this.djp.get());
                    gL.glVertex3f(this.djq.get(), this.djq.get(), this.djq.get());
                    gL.glTexCoord2f(this.djp.get(), this.djp.get());
                    gL.glVertex3f(this.djq.get(), this.djq.get(), this.djq.get());
                }
            }
            catch (Exception exception) {
                afg_0.a.error((Object)"Exception", (Throwable)exception);
            }
            finally {
                gL.glEnd();
                this.djq.rewind();
                this.djp.rewind();
                this.djo = 0;
            }
        }
    }

    static /* synthetic */ void a(axl_0 axl_02) {
        axl_02.aJU();
    }
}

