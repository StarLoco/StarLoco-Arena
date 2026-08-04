/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.Threading;
import org.apache.log4j.Logger;

public final class Ss
extends ef_1 {
    private int[] bLk;
    private static final Logger a = Logger.getLogger(Ss.class);
    private static final int bLl = pw.bu("DXT1");
    private static final int bLm = pw.bu("DXT3");
    private static final int bLn = pw.bu("DXT5");

    public Ss(long l2, String string, boolean bl2) {
        super(l2, string, bl2);
        this.initialize();
    }

    public Ss(long l2, aon_2 aon_22, boolean bl2) {
        super(l2, aon_22, bl2);
        this.initialize();
    }

    public Ss(long l2, int n2, int n3, boolean bl2) {
        super(l2, n2, n3, bl2);
        this.initialize();
    }

    public final int getID() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.bLk[0];
    }

    public final int afi() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        return this.bLk[3];
    }

    public final boolean e(db_2 db_22) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        if (!Threading.isOpenGLThread()) {
            return false;
        }
        if (this.aPV) {
            return this.l(db_22);
        }
        return this.m(db_22);
    }

    private boolean l(db_2 db_22) {
        GL gL = azd.u(db_22);
        gL.glGenFramebuffersEXT(1, this.bLk, 3);
        gL.glGenTextures(1, this.bLk, 0);
        gL.glGenRenderbuffersEXT(1, this.bLk, 2);
        gL.glBindFramebufferEXT(36160, this.bLk[3]);
        gL.glBindTexture(3553, this.bLk[0]);
        gL.glTexParameteri(3553, 10242, 33071);
        gL.glTexParameteri(3553, 10243, 33071);
        kf_0 kf_02 = this.lB(0);
        int n2 = kf_02.getWidth();
        int n3 = kf_02.getHeight();
        if (this.aPW) {
            gL.glTexParameterf(3553, 10240, 9729.0f);
            gL.glTexParameterf(3553, 10241, 9987.0f);
            gL.glTexImage2D(3553, 0, 4, n2, n3, 0, 6408, 5121, null);
            gL.glGenerateMipmapEXT(3553);
        } else {
            gL.glTexParameteri(3553, 10240, 9729);
            gL.glTexParameteri(3553, 10241, 9729);
            gL.glTexParameteri(3553, 34891, 6409);
            gL.glTexImage2D(3553, 0, 32856, n2, n3, 0, 6408, 5121, null);
        }
        gL.glFramebufferTexture2DEXT(36160, 36064, 3553, this.bLk[0], 0);
        gL.glBindRenderbufferEXT(36161, this.bLk[2]);
        gL.glRenderbufferStorageEXT(36161, 35056, n2, n3);
        gL.glFramebufferRenderbufferEXT(36160, 36096, 36161, this.bLk[2]);
        gL.glFramebufferRenderbufferEXT(36160, 36128, 36161, this.bLk[2]);
        int n4 = gL.glCheckFramebufferStatusEXT(36160);
        boolean bl2 = n4 == 36053;
        gL.glBindFramebufferEXT(36160, 0);
        this.aPS = bl2;
        this.tY = false;
        return bl2;
    }

    private boolean m(db_2 db_22) {
        if (this.qJ != null) {
            if (this.qJ.is()) {
                this.aCK();
            } else if (this.qJ.acI()) {
                this.qJ = null;
            } else {
                return false;
            }
        }
        GL gL = azd.u(db_22);
        gL.glPixelStorei(3317, 1);
        gL.glGenTextures(1, this.bLk, 0);
        if (this.bLk[0] == 0) {
            a.error((Object)"Unable to generate a new texture");
            return false;
        }
        boolean bl2 = this.j(db_22);
        if (bl2 && !this.aPU) {
            this.fu();
        }
        return bl2;
    }

    public boolean j(db_2 db_22) {
        int n2 = 0;
        if (this.aCI().getID() == bLl) {
            n2 = 33777;
        } else if (this.aCI().getID() == bLm) {
            n2 = 33778;
        } else if (this.aCI().getID() == bLn) {
            n2 = 33779;
        }
        GL gL = (GL)db_22.LV();
        gL.glBindTexture(3553, this.bLk[0]);
        gL.glTexParameterf(3553, 10242, 10497.0f);
        gL.glTexParameterf(3553, 10243, 10497.0f);
        gL.glTexParameterf(3553, 10240, 9729.0f);
        gL.glTexParameterf(3553, 10241, 9729.0f);
        aoj_1 aoj_12 = aoj_1.aXZ();
        for (int j = 0; j < this.aCH(); ++j) {
            byte[] byArray;
            kf_0 kf_02 = this.lB(j);
            if (kf_02.getBitDepth() != 32) {
                a.warn((Object)"Setting layer to RGBA32");
                kf_02 = kf_02.po();
            }
            if ((byArray = kf_02.getData()) == null) {
                return false;
            }
            int n3 = byArray.length;
            uo_2 uo_22 = aoj_12.pG(n3);
            ByteBuffer byteBuffer = (ByteBuffer)uo_22.getBuffer();
            byteBuffer.put(byArray, 0, n3);
            byteBuffer.rewind();
            if (this.isCompressed()) {
                gL.glCompressedTexImage2D(3553, j, n2, ej_0.aq(kf_02.getWidth()), ej_0.aq(kf_02.getHeight()), 0, byArray.length, byteBuffer);
            } else {
                n2 = kf_02.getBitDepth() >> 3;
                int n4 = 6407;
                if (kf_02.getBitDepth() == 32) {
                    n4 = 6408;
                }
                gL.glTexImage2D(3553, j, n2, ej_0.aq(kf_02.getWidth()), ej_0.aq(kf_02.getHeight()), 0, n4, 5121, byteBuffer);
            }
            uo_22.release();
        }
        this.aPS = true;
        this.tY = false;
        return true;
    }

    public final void f(db_2 db_22) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        vo_1 vo_12 = vo_1.aik();
        if (!this.is()) {
            this.e(db_22);
        }
        if (this.avb() > this.aPX) {
            this.aPX = this.avb();
        }
        GL gL = azd.u(db_22);
        vo_12.cu(true);
        vo_12.n(db_22);
        gL.glBindTexture(3553, this.bLk[0]);
        vo_12.b(this.aPT);
        vo_12.n(db_22);
    }

    public final void g(db_2 db_22) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        if (!this.MJ()) {
            return;
        }
        this.aPR = false;
        azd.u(db_22).glBindTexture(3553, 0);
    }

    public final void h(db_2 db_22) {
    }

    public final void i(db_2 db_22) {
    }

    protected final void delete() {
        assert (Threading.isOpenGLThread()) : "Trying to release a texture in a non-opengl thread";
        super.delete();
        GL gL = azd.u(arX.cQT.iE());
        gL.glDeleteTextures(1, this.bLk, 0);
        if (this.aPV) {
            gL.glDeleteFramebuffersEXT(1, this.bLk, 3);
            gL.glDeleteRenderbuffersEXT(1, this.bLk, 2);
        }
        this.aPS = false;
        this.aPX = 0;
    }

    public boolean isCompressed() {
        return this.aCI().getID() == bLl || this.aCI().getID() == bLm || this.aCI().getID() == bLn;
    }

    private void initialize() {
        if (this.aPV) {
            this.bLk = new int[4];
            this.bLk[1] = 0;
            this.bLk[2] = 0;
            this.bLk[3] = 0;
        } else {
            this.bLk = new int[1];
        }
        this.bLk[0] = 0;
        this.aPS = false;
    }
}

