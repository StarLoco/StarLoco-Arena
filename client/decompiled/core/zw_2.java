/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from Zw
 */
public class zw_2
extends alp
implements agk_1 {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected zw_2(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIStreamCoderUpcast(l2), bl2);
        this.hf = l2;
    }

    protected zw_2(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIStreamCoderUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long b(zw_2 zw_22) {
        if (zw_22 == null) {
            return 0L;
        }
        return zw_22.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public zw_2 any() {
        if (this.hf == 0L) {
            return null;
        }
        return new zw_2(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof zw_2) {
            bl2 = ((zw_2)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public char[] anz() {
        char[] cArray = new char[4];
        int n2 = this.anP();
        cArray[0] = (char)(n2 & 0xFF);
        cArray[1] = (char)(n2 >> 8 & 0xFF);
        cArray[2] = (char)(n2 >> 16 & 0xFF);
        cArray[3] = (char)(n2 >> 24 & 0xFF);
        return cArray;
    }

    public void a(char[] cArray) {
        if (cArray == null || cArray.length != 4) {
            throw new IllegalArgumentException();
        }
        int n2 = 0;
        n2 = (cArray[3] << 24) + (cArray[2] << 16) + (cArray[1] << 8) + cArray[0];
        this.jp(n2);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        auc_0 auc_02 = this.anE();
        stringBuilder.append(this.getClass().getName() + "@" + this.hashCode() + "[");
        stringBuilder.append("codec=" + this.anD() + ";");
        stringBuilder.append("time base=" + this.HN() + ";");
        stringBuilder.append("frame rate=" + this.HM() + ";");
        switch (auc_02) {
            case cVO: {
                stringBuilder.append("pixel type=" + (Object)((Object)this.anJ()) + ";");
                stringBuilder.append("width=" + this.getWidth() + ";");
                stringBuilder.append("height=" + this.getHeight() + ";");
                break;
            }
            case cVP: {
                stringBuilder.append("sample rate=" + this.zv() + ";");
                stringBuilder.append("channels=" + this.getChannels() + ";");
                break;
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public Collection ala() {
        LinkedList<String> linkedList = new LinkedList<String>();
        int n2 = this.alf();
        for (int j = 0; j < n2; ++j) {
            aoc_2 aoc_22 = this.iX(j);
            String string = aoc_22.getName();
            linkedList.add(string);
        }
        return linkedList;
    }

    public di anA() {
        int n2 = this.anT();
        if (n2 <= 0) {
            return null;
        }
        di di2 = di.a(this, n2);
        if (di2 == null) {
            return null;
        }
        if (this.a(di2, 0, n2) != n2) {
            di2.delete();
            di2 = null;
        }
        return di2;
    }

    public anp anB() {
        return anp.lz(XugglerJNI.IStreamCoder_getDirection(this.hf, this));
    }

    public at_2 anC() {
        long l2 = XugglerJNI.IStreamCoder_getStream(this.hf, this);
        return l2 == 0L ? null : new at_2(l2, false);
    }

    public ch_1 anD() {
        long l2 = XugglerJNI.IStreamCoder_getCodec(this.hf, this);
        return l2 == 0L ? null : new ch_1(l2, false);
    }

    public auc_0 anE() {
        return auc_0.mq(XugglerJNI.IStreamCoder_getCodecType(this.hf, this));
    }

    public avh anF() {
        return avh.mu(XugglerJNI.IStreamCoder_getCodecID(this.hf, this));
    }

    public void c(ch_1 ch_12) {
        XugglerJNI.IStreamCoder_setCodec__SWIG_0(this.hf, this, ch_1.a(ch_12), ch_12);
    }

    public void f(avh avh2) {
        XugglerJNI.IStreamCoder_setCodec__SWIG_1(this.hf, this, avh2.dZ());
    }

    public int anG() {
        return XugglerJNI.IStreamCoder_getBitRate(this.hf, this);
    }

    public void ji(int n2) {
        XugglerJNI.IStreamCoder_setBitRate(this.hf, this, n2);
    }

    public int anH() {
        return XugglerJNI.IStreamCoder_getBitRateTolerance(this.hf, this);
    }

    public void jj(int n2) {
        XugglerJNI.IStreamCoder_setBitRateTolerance(this.hf, this, n2);
    }

    public int getHeight() {
        return XugglerJNI.IStreamCoder_getHeight(this.hf, this);
    }

    public void setHeight(int n2) {
        XugglerJNI.IStreamCoder_setHeight(this.hf, this, n2);
    }

    public int getWidth() {
        return XugglerJNI.IStreamCoder_getWidth(this.hf, this);
    }

    public void setWidth(int n2) {
        XugglerJNI.IStreamCoder_setWidth(this.hf, this, n2);
    }

    public xv_1 HN() {
        long l2 = XugglerJNI.IStreamCoder_getTimeBase(this.hf, this);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public void k(xv_1 xv_12) {
        XugglerJNI.IStreamCoder_setTimeBase(this.hf, this, xv_1.b(xv_12), xv_12);
    }

    public xv_1 HM() {
        long l2 = XugglerJNI.IStreamCoder_getFrameRate(this.hf, this);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public void l(xv_1 xv_12) {
        XugglerJNI.IStreamCoder_setFrameRate(this.hf, this, xv_1.b(xv_12), xv_12);
    }

    public int anI() {
        return XugglerJNI.IStreamCoder_getNumPicturesInGroupOfPictures(this.hf, this);
    }

    public void jk(int n2) {
        XugglerJNI.IStreamCoder_setNumPicturesInGroupOfPictures(this.hf, this, n2);
    }

    public yi_0 anJ() {
        return yi_0.ev(XugglerJNI.IStreamCoder_getPixelType(this.hf, this));
    }

    public void a(yi_0 yi_02) {
        XugglerJNI.IStreamCoder_setPixelType(this.hf, this, yi_02.dZ());
    }

    public int zv() {
        return XugglerJNI.IStreamCoder_getSampleRate(this.hf, this);
    }

    public void jl(int n2) {
        XugglerJNI.IStreamCoder_setSampleRate(this.hf, this, n2);
    }

    public aby anK() {
        return aby.jz(XugglerJNI.IStreamCoder_getSampleFormat(this.hf, this));
    }

    public void b(aby aby2) {
        XugglerJNI.IStreamCoder_setSampleFormat(this.hf, this, aby2.dZ());
    }

    public int getChannels() {
        return XugglerJNI.IStreamCoder_getChannels(this.hf, this);
    }

    public void jm(int n2) {
        XugglerJNI.IStreamCoder_setChannels(this.hf, this, n2);
    }

    public int anL() {
        return XugglerJNI.IStreamCoder_getAudioFrameSize(this.hf, this);
    }

    public int anM() {
        return XugglerJNI.IStreamCoder_getGlobalQuality(this.hf, this);
    }

    public void jn(int n2) {
        XugglerJNI.IStreamCoder_setGlobalQuality(this.hf, this, n2);
    }

    public int getFlags() {
        return XugglerJNI.IStreamCoder_getFlags(this.hf, this);
    }

    public void jo(int n2) {
        XugglerJNI.IStreamCoder_setFlags(this.hf, this, n2);
    }

    public boolean a(ape_1 ape_12) {
        return XugglerJNI.IStreamCoder_getFlag(this.hf, this, ape_12.dZ());
    }

    public void a(ape_1 ape_12, boolean bl2) {
        XugglerJNI.IStreamCoder_setFlag(this.hf, this, ape_12.dZ(), bl2);
    }

    public long anN() {
        return XugglerJNI.IStreamCoder_getNextPredictedPts(this.hf, this);
    }

    public int anO() {
        return XugglerJNI.IStreamCoder_open(this.hf, this);
    }

    public int de() {
        return XugglerJNI.IStreamCoder_close(this.hf, this);
    }

    public int a(yX yX2, ala_1 ala_12, int n2) {
        return XugglerJNI.IStreamCoder_decodeAudio(this.hf, this, yX.b(yX2), yX2, ala_1.d(ala_12), ala_12, n2);
    }

    public int a(ayh ayh2, ala_1 ala_12, int n2) {
        return XugglerJNI.IStreamCoder_decodeVideo(this.hf, this, ayh.a(ayh2), ayh2, ala_1.d(ala_12), ala_12, n2);
    }

    public int a(ala_1 ala_12, ayh ayh2, int n2) {
        return XugglerJNI.IStreamCoder_encodeVideo(this.hf, this, ala_1.d(ala_12), ala_12, ayh.a(ayh2), ayh2, n2);
    }

    public int a(ala_1 ala_12, yX yX2, long l2) {
        return XugglerJNI.IStreamCoder_encodeAudio(this.hf, this, ala_1.d(ala_12), ala_12, yX.b(yX2), yX2, l2);
    }

    public static zw_2 a(anp anp2) {
        long l2 = XugglerJNI.IStreamCoder_make__SWIG_0(anp2.dZ());
        return l2 == 0L ? null : new zw_2(l2, false);
    }

    public int anP() {
        return XugglerJNI.IStreamCoder_getCodecTag(this.hf, this);
    }

    public void jp(int n2) {
        XugglerJNI.IStreamCoder_setCodecTag(this.hf, this, n2);
    }

    public int alf() {
        return XugglerJNI.IStreamCoder_getNumProperties(this.hf, this);
    }

    public aoc_2 iX(int n2) {
        long l2 = XugglerJNI.IStreamCoder_getPropertyMetaData__SWIG_0(this.hf, this, n2);
        return l2 == 0L ? null : new aoc_2(l2, false);
    }

    public aoc_2 gI(String string) {
        long l2 = XugglerJNI.IStreamCoder_getPropertyMetaData__SWIG_1(this.hf, this, string);
        return l2 == 0L ? null : new aoc_2(l2, false);
    }

    public int K(String string, String string2) {
        return XugglerJNI.IStreamCoder_setProperty__SWIG_0(this.hf, this, string, string2);
    }

    public int a(String string, double d) {
        return XugglerJNI.IStreamCoder_setProperty__SWIG_1(this.hf, this, string, d);
    }

    public int e(String string, long l2) {
        return XugglerJNI.IStreamCoder_setProperty__SWIG_2(this.hf, this, string, l2);
    }

    public int r(String string, boolean bl2) {
        return XugglerJNI.IStreamCoder_setProperty__SWIG_3(this.hf, this, string, bl2);
    }

    public int a(String string, xv_1 xv_12) {
        return XugglerJNI.IStreamCoder_setProperty__SWIG_4(this.hf, this, string, xv_1.b(xv_12), xv_12);
    }

    public String gJ(String string) {
        return XugglerJNI.IStreamCoder_getPropertyAsString(this.hf, this, string);
    }

    public double gK(String string) {
        return XugglerJNI.IStreamCoder_getPropertyAsDouble(this.hf, this, string);
    }

    public long gL(String string) {
        return XugglerJNI.IStreamCoder_getPropertyAsLong(this.hf, this, string);
    }

    public xv_1 gM(String string) {
        long l2 = XugglerJNI.IStreamCoder_getPropertyAsRational(this.hf, this, string);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public boolean gN(String string) {
        return XugglerJNI.IStreamCoder_getPropertyAsBoolean(this.hf, this, string);
    }

    public boolean isOpen() {
        return XugglerJNI.IStreamCoder_isOpen(this.hf, this);
    }

    public int anQ() {
        return XugglerJNI.IStreamCoder_getDefaultAudioFrameSize(this.hf, this);
    }

    public void jq(int n2) {
        XugglerJNI.IStreamCoder_setDefaultAudioFrameSize(this.hf, this, n2);
    }

    public static zw_2 a(anp anp2, zw_2 zw_22) {
        long l2 = XugglerJNI.IStreamCoder_make__SWIG_1(anp2.dZ(), zw_2.b(zw_22), zw_22);
        return l2 == 0L ? null : new zw_2(l2, false);
    }

    public long anR() {
        return XugglerJNI.IStreamCoder_getNumDroppedFrames(this.hf, this);
    }

    public void cI(boolean bl2) {
        XugglerJNI.IStreamCoder_setAutomaticallyStampPacketsForStream(this.hf, this, bl2);
    }

    public boolean anS() {
        return XugglerJNI.IStreamCoder_getAutomaticallyStampPacketsForStream(this.hf, this);
    }

    public void g(avh avh2) {
        XugglerJNI.IStreamCoder_setCodecID(this.hf, this, avh2.dZ());
    }

    public int a(di di2, int n2, int n3, boolean bl2) {
        return XugglerJNI.IStreamCoder_setExtraData(this.hf, this, di.a(di2), di2, n2, n3, bl2);
    }

    public int a(di di2, int n2, int n3) {
        return XugglerJNI.IStreamCoder_getExtraData(this.hf, this, di.a(di2), di2, n2, n3);
    }

    public int anT() {
        return XugglerJNI.IStreamCoder_getExtraDataSize(this.hf, this);
    }
}

