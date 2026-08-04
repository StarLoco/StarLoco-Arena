/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from kF
 */
public final class kf_0
extends afB {
    private static Logger a = Logger.getLogger(kf_0.class);
    private int fb;
    private int fc;
    private short fs;
    private short ft;
    private adz_1 Fb;
    private int Fc;
    private eu_1 Fd;
    private byte[] Fe;
    private awL Ff = null;

    public kf_0(int n2, int n3, short s, eu_1 eu_12, byte[] byArray) {
        this.fb = n2;
        this.fc = n3;
        this.Fb = new adz_1(this.fb, this.fc);
        this.Fc = s;
        this.Fd = eu_12;
        this.Fe = byArray;
    }

    public kf_0(short s, short s2, short s3, short s4, byte by, eu_1 eu_12, byte[] byArray) {
        this(s, s2, by, eu_12, byArray);
        this.fs = s3;
        this.ft = s4;
    }

    public kf_0(int n2, int n3, short s, eu_1 eu_12, byte[] byArray, int n4, int n5) {
        this.fb = n2;
        this.fc = n3;
        this.Fb = new adz_1(this.fb, this.fc);
        this.Fc = s;
        this.Fd = eu_12;
        this.Fe = new byte[n5];
        System.arraycopy(byArray, n4, this.Fe, 0, n5);
    }

    public kf_0(int n2, int n3, short s, short s2, short s3, eu_1 eu_12, byte[] byArray, int n4, int n5) {
        this(n2, n3, s3, eu_12, byArray, n4, n5);
        this.fs = s;
        this.ft = s2;
    }

    public kf_0(kf_0 kf_02) {
        this.fb = kf_02.fb;
        this.fc = kf_02.fc;
        this.fs = kf_02.fs;
        this.ft = kf_02.ft;
        this.Fb = new adz_1(this.fb, this.fc);
        this.Fc = kf_02.Fc;
        this.Fd = kf_02.Fd != null ? new eu_1(kf_02.Fd) : null;
        if (kf_02.Fe != null) {
            this.Fe = new byte[kf_02.Fe.length];
            System.arraycopy(kf_02.Fe, 0, this.Fe, 0, this.Fe.length);
        } else {
            this.Fe = null;
        }
        if (kf_02.Ff != null) {
            this.Ff = new awL(kf_02.Ff);
        }
    }

    public final int getWidth() {
        return this.fb;
    }

    public final int getHeight() {
        return this.fc;
    }

    public final adz_1 pl() {
        return this.Fb;
    }

    public final short cx() {
        return this.fs;
    }

    public final short cy() {
        return this.ft;
    }

    public void g(short s, short s2) {
        this.fs = s;
        this.ft = s2;
    }

    public final int G(int n2, int n3) {
        assert (n2 < this.fb);
        assert (n3 < this.fc);
        if (this.Fc == 32) {
            int n4 = (n3 * this.fb + n2) * 4;
            return this.Fe[n4 + 3] & 0xFF;
        }
        return 255;
    }

    public final vP H(int n2, int n3) {
        assert (n2 < this.fb);
        assert (n3 < this.fc);
        int n4 = (n3 * this.fb + n2) * ((this.Fc + 7) / 8);
        if (this.Fc == 32) {
            return new vP(this.Fe[n4], this.Fe[n4 + 1], this.Fe[n4 + 2], this.Fe[n4 + 3]);
        }
        if (this.Fc == 24) {
            return new vP(this.Fe[n4], this.Fe[n4 + 1], this.Fe[n4 + 2], -1);
        }
        if (this.Fc == 8) {
            if (this.pm()) {
                return this.Fd.fx(afy_0.aA(this.Fe[n4]));
            }
            return new vP(this.Fe[n4], this.Fe[n4], this.Fe[n4], -1);
        }
        if (this.Fc == 4) {
            if (n4 % 2 == 0) {
                return this.Fd.fx((byte)(this.Fe[n4 / 2] & 0xF0) >> 4);
            }
            return this.Fd.fx((byte)(this.Fe[n4 / 2] & 0xF));
        }
        return null;
    }

    public final void a(int n2, int n3, vP vP2) {
        assert (n2 < this.fb);
        assert (n3 < this.fc);
        int n4 = (n3 * this.fb + n2) * ((this.Fc + 7) / 8);
        if (this.Fc == 32) {
            this.Fe[n4] = vP2.Ci();
            this.Fe[n4 + 1] = vP2.Cj();
            this.Fe[n4 + 2] = vP2.Ck();
            this.Fe[n4 + 3] = vP2.Ch();
        } else if (this.Fc == 24) {
            this.Fe[n4] = vP2.Ci();
            this.Fe[n4 + 1] = vP2.Cj();
            this.Fe[n4 + 2] = vP2.Ck();
        } else assert (false) : "Unsupported color format";
    }

    public final int getBitDepth() {
        return this.Fc;
    }

    public final byte[] getData() {
        return this.Fe;
    }

    public final void setData(byte[] byArray) {
        this.Fe = byArray;
    }

    public final boolean pm() {
        return this.Fd != null;
    }

    public kf_0 pn() {
        if (this.Fc == 24) {
            return new kf_0(this);
        }
        byte[] byArray = new byte[this.fb * this.fc * 3];
        if (this.Fc == 4) {
            gs_0 gs_02 = new gs_0(0);
            for (byte by : this.Fe) {
                this.a(byArray, gs_02, (byte)((by & 0xF0) >> 4));
                this.a(byArray, gs_02, (byte)(by & 0xF));
            }
        } else if (this.Fc == 8) {
            gs_0 gs_03 = new gs_0(0);
            for (byte by : this.Fe) {
                this.a(byArray, gs_03, by);
            }
        } else assert (false) : "Unable to convert " + this.Fc + " bits to RGB24";
        return new kf_0(this.fb, this.fc, 24, null, byArray, 0, byArray.length);
    }

    public kf_0 po() {
        if (this.Fc == 32) {
            return new kf_0(this);
        }
        if (this.Fc != 24) {
            assert (false) : "Unable to convert" + this.Fc + " bits to RGBA32";
            return null;
        }
        byte[] byArray = new byte[this.fb * this.fc * 4];
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.Fe.length) {
            byArray[n2++] = this.Fe[n3++];
            byArray[n2++] = this.Fe[n3++];
            byArray[n2++] = this.Fe[n3++];
            byArray[n2++] = -1;
        }
        return new kf_0(this.fb, this.fc, 32, null, byArray, 0, byArray.length);
    }

    public void a(float f, float f2, gh_0 gh_02) {
        int n2 = Math.round((float)this.getWidth() * f);
        int n3 = Math.round((float)this.getHeight() * f2);
        assert (n2 > 0 && n3 > 0);
        int n4 = this.getBitDepth() >> 3;
        byte[] byArray = new byte[n2 * n3 * n4];
        if (gh_02 == null) {
            int n5 = (int)Math.ceil(1.0f / f);
            int n6 = (int)Math.ceil(1.0f / f2);
            for (int j = 0; j < n3; ++j) {
                for (int i2 = 0; i2 < n2; ++i2) {
                    int n7 = (int)((float)i2 / f);
                    int n8 = (int)((float)j / f2);
                    for (int i3 = 0; i3 < n4; ++i3) {
                        byte by;
                        byArray[(j * n2 + i2) * n4 + i3] = by = this.a(n5, n6, n7, n8, i3);
                    }
                }
            }
        } else {
            for (int j = 0; j < n3; ++j) {
                for (int i4 = 0; i4 < n2; ++i4) {
                    int n9 = (int)((float)i4 / f);
                    int n10 = (int)((float)j / f2);
                    for (int i5 = 0; i5 < n4; ++i5) {
                        byte by;
                        byArray[(j * n2 + i4) * n4 + i5] = by = this.a(gh_02, n9, n10, i5);
                    }
                }
            }
        }
        this.fb = n2;
        this.fc = n3;
        this.Fb = new adz_1(this.fb, this.fc);
        this.Fe = byArray;
    }

    public void pp() {
        this.Fe = null;
    }

    public void bQ(int n2) {
        this.Ff = new awL(this, n2);
    }

    public awL pq() {
        return this.Ff;
    }

    public void a(byte[] byArray, int n2, int n3) {
        this.Ff = new awL(byArray, n2, this.fb, n3);
    }

    public void a(kf_0 kf_02) {
        assert (kf_02.fb <= this.fb && kf_02.fc <= this.fc) : "layer trop grand";
        assert (kf_02.Fc == this.Fc) : "Impossible de merger des layers de profondeur diff\u00e9rente";
        if (this.Fc != 32 || kf_02.Fc != 32) {
            return;
        }
        for (int j = 0; j < kf_02.fb; ++j) {
            for (int i2 = 0; i2 < kf_02.fc; ++i2) {
                int n2 = (i2 * this.fb + j) * ((this.Fc + 7) / 8);
                float f = (float)afy_0.aA(kf_02.Fe[n2 + 3]) / 255.0f;
                if (f == 0.0f) continue;
                this.Fe[n2] = (byte)Math.min(255.0f, (float)afy_0.aA(this.Fe[n2]) * (1.0f - f) + (float)afy_0.aA(kf_02.Fe[n2]) * f);
                this.Fe[n2 + 1] = (byte)Math.min(255.0f, (float)afy_0.aA(this.Fe[n2 + 1]) * (1.0f - f) + (float)afy_0.aA(kf_02.Fe[n2 + 1]) * f);
                this.Fe[n2 + 2] = (byte)Math.min(255.0f, (float)afy_0.aA(this.Fe[n2 + 2]) * (1.0f - f) + (float)afy_0.aA(kf_02.Fe[n2 + 2]) * f);
                this.Fe[n2 + 3] = (byte)Math.min(255.0f, (float)afy_0.aA(this.Fe[n2 + 3]) * (1.0f - f) + (float)afy_0.aA(kf_02.Fe[n2 + 3]));
            }
        }
    }

    public void a(zi_1 zi_12) {
        assert (this.Fc == 32 || this.Fc == 24) : "Unsupported color format for this operation";
        for (int j = 0; j < this.fb; ++j) {
            for (int i2 = 0; i2 < this.fc; ++i2) {
                int n2 = (i2 * this.fb + j) * ((this.Fc + 7) / 8);
                if (!zi_12.b(this.Fe[n2], this.Fe[n2 + 1], this.Fe[n2 + 2], this.Fc == 32 ? this.Fe[n2 + 3] : (byte)-1)) continue;
                zi_12.d(this.Fe, n2);
            }
        }
    }

    public void a(vP vP2, vP vP3) {
        rI rI2 = new rI(vP2.Ci(), vP2.Cj(), vP2.Ck(), vP2.Ch(), vP3.Ci(), vP3.Cj(), vP3.Ck(), vP3.Ch(), this.Fc == 32);
        this.a(rI2);
    }

    public void pr() {
        int n2;
        int n3;
        vP[][] vPArray = new vP[this.fb][this.fc];
        for (n3 = 0; n3 < this.fb; ++n3) {
            for (n2 = 0; n2 < this.fc; ++n2) {
                vPArray[n3][n2] = this.H(this.fb - n3 - 1, n2);
            }
        }
        for (n3 = 0; n3 < this.fb; ++n3) {
            for (n2 = 0; n2 < this.fc; ++n2) {
                this.a(n3, n2, vPArray[n3][n2]);
            }
        }
    }

    public void a(ByteBuffer byteBuffer, int n2, int n3) {
        int n4 = ej_0.aq(n2);
        int n5 = ej_0.aq(n3);
        int n6 = this.Fc / 8;
        if (this.Fe == null || this.Fe.length != n4 * n5 * n6) {
            this.Fe = new byte[n4 * n5 * n6];
        }
        int n7 = n2 * n6;
        int n8 = n4 * n6;
        int n9 = byteBuffer.capacity() / n7;
        int n10 = 0;
        byteBuffer.rewind();
        byte[] byArray = new byte[n7];
        for (int j = 0; j < n9; ++j) {
            byteBuffer.get(byArray, 0, n7);
            System.arraycopy(byArray, 0, this.Fe, n10, n7);
            n10 += n8;
        }
        this.fb = n2;
        this.fc = n3;
        this.Fb = new adz_1(n4, n5);
    }

    protected void delete() {
        this.Fe = null;
        this.Ff = null;
        if (this.Fd != null) {
            this.Fd.HF();
            this.Fd = null;
        }
    }

    private byte a(gh_0 gh_02, int n2, int n3, int n4) {
        int n5 = this.getBitDepth() >> 3;
        int n6 = gh_02.getSize() / 2;
        float[] fArray = gh_02.Pn();
        float f = 0.0f;
        int n7 = -1;
        for (int j = 0; j < gh_02.getSize(); ++j) {
            int n8 = n3 + j - n6;
            if (n8 >= 0 && n8 <= this.getHeight()) {
                int n9 = n8 * this.getWidth();
                for (int i2 = 0; i2 < gh_02.getSize(); ++i2) {
                    int n10 = n2 + i2 - n6;
                    ++n7;
                    if (n10 < 0 || n10 > this.getWidth()) continue;
                    float f2 = this.Fe[(n9 + n10) * n5 + n4] & 0xFF;
                    f += fArray[n7] * f2;
                }
                continue;
            }
            n7 += gh_02.getSize();
        }
        if (f < 0.0f) {
            f = 0.0f;
        } else if (f > 255.0f) {
            f = 255.0f;
        }
        if (f > 127.0f) {
            f -= 256.0f;
        }
        return (byte)f;
    }

    private byte a(int n2, int n3, int n4, int n5, int n6) {
        int n7 = this.getBitDepth() >> 3;
        float f = 1.0f / (float)(n2 * n3);
        float f2 = 0.0f;
        for (int j = 0; j < n3; ++j) {
            int n8 = n5 + j;
            if (n8 < 0 || n8 >= this.getHeight()) continue;
            int n9 = n8 * this.getWidth();
            for (int i2 = 0; i2 < n2; ++i2) {
                int n10 = n4 + i2;
                if (n10 < 0 || n10 >= this.getWidth()) continue;
                float f3 = this.Fe[(n9 + n10) * n7 + n6] & 0xFF;
                f2 += f * f3;
            }
        }
        if (f2 < 0.0f) {
            f2 = 0.0f;
        } else if (f2 > 255.0f) {
            f2 = 255.0f;
        }
        if (f2 > 127.0f) {
            f2 -= 256.0f;
        }
        return (byte)f2;
    }

    private void a(byte[] byArray, gs_0 gs_02, byte by) {
        int n2 = (Integer)gs_02.get();
        vP vP2 = this.Fd.fx(afy_0.aA(by));
        byArray[n2++] = vP2.Ci();
        byArray[n2++] = vP2.Cj();
        byArray[n2++] = vP2.Ck();
        gs_02.set(n2);
    }
}

