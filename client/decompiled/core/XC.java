/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.sun.opengl.util.BufferUtil;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import org.apache.log4j.Logger;

public final class XC {
    private aew_0[] bYS;
    private final aua_0 FH;
    private final int m_size;
    private final rf aMS;
    private static final Logger a = Logger.getLogger(XC.class);
    private static final boolean DEBUG = false;

    public XC(cq_1 cq_12) {
        this.m_size = cq_12.getBufferSize();
        this.aMS = cq_12.Ll();
        int n2 = cq_12.Lk();
        Buffer buffer = XC.a(this.m_size * n2, this.aMS);
        this.bYS = new aew_0[n2];
        for (int j = 0; j < this.bYS.length; ++j) {
            Buffer buffer2 = XC.a(buffer, j * this.m_size, this.m_size, this.aMS);
            this.bYS[j] = this.a(buffer2, j, this.aMS);
        }
        this.FH = new aua_0(cq_12.Lk());
    }

    public final aew_0 alg() {
        int n2 = this.FH.aHc();
        if (n2 == this.FH.cVC) {
            int n3 = this.bYS.length;
            int n4 = n3 < 4096 ? n3 : 4096;
            aew_0[] aew_0Array = new aew_0[n3 += n4];
            System.arraycopy(this.bYS, 0, aew_0Array, 0, this.bYS.length);
            Buffer buffer = XC.a(this.m_size * n4, this.aMS);
            for (int j = this.bYS.length; j < aew_0Array.length; ++j) {
                int n5 = j - this.bYS.length;
                Buffer buffer2 = XC.a(buffer, n5 * this.m_size, this.m_size, this.aMS);
                aew_0Array[j] = this.a(buffer2, j, this.aMS);
            }
            this.FH.resize(n3);
            this.bYS = aew_0Array;
            n2 = this.FH.aHc();
        }
        return this.bYS[n2];
    }

    public final void a(aew_0 aew_02) {
        this.FH.mm(aew_02.getId());
    }

    public final void alh() {
        this.FH.aHd();
    }

    public final int getSize() {
        return this.m_size;
    }

    public final int ali() {
        return this.bYS.length * this.m_size;
    }

    public final int alj() {
        return this.FH.pz() * this.m_size;
    }

    public final int alk() {
        return this.FH.pA() * this.m_size;
    }

    private aew_0 a(Buffer buffer, int n2, rf rf2) {
        switch (rf2) {
            case afJ: {
                return new uo_2(buffer, this.m_size, n2, this);
            }
            case afK: {
                return new qx(buffer, this.m_size, n2, this);
            }
            case afL: {
                return new zf_1(buffer, this.m_size, n2, this);
            }
        }
        assert (false) : "Buffer type not supported";
        return null;
    }

    private static Buffer a(Buffer buffer, int n2, int n3, rf rf2) {
        switch (rf2) {
            case afJ: {
                buffer.position(n2);
                buffer.limit(n2 + n3);
                ByteBuffer byteBuffer = (ByteBuffer)buffer;
                return byteBuffer.slice();
            }
            case afK: {
                buffer.position(n2 >> 1);
                buffer.limit(n2 + n3 >> 1);
                ShortBuffer shortBuffer = (ShortBuffer)buffer;
                return shortBuffer.slice();
            }
            case afL: {
                buffer.position(n2 >> 2);
                buffer.limit(n2 + n3 >> 2);
                FloatBuffer floatBuffer = (FloatBuffer)buffer;
                return floatBuffer.slice();
            }
        }
        assert (false) : "Buffer type not supported in slice";
        return null;
    }

    private static Buffer a(int n2, rf rf2) {
        switch (rf2) {
            case afJ: {
                return BufferUtil.newByteBuffer(n2);
            }
            case afK: {
                return BufferUtil.newShortBuffer(n2 / 2);
            }
            case afL: {
                return BufferUtil.newFloatBuffer(n2 / 4);
            }
        }
        assert (false) : "Buffer type not supported in createPage";
        return null;
    }

    public void all() {
        a.warn((Object)(this.aMS.name() + " size=" + this.m_size + " : " + this.FH.pA() + "/" + this.FH.getSize()));
    }
}

