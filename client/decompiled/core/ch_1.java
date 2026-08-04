/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from CH
 */
public class ch_1
extends alp {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected ch_1(long l2, boolean bl2) {
        super(XugglerJNI.SWIGICodecUpcast(l2), bl2);
        this.hf = l2;
    }

    protected ch_1(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGICodecUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(ch_1 ch_12) {
        if (ch_12 == null) {
            return 0L;
        }
        return ch_12.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public ch_1 KC() {
        if (this.hf == 0L) {
            return null;
        }
        return new ch_1(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof ch_1) {
            bl2 = ((ch_1)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName() + "@" + this.hashCode() + "[");
        stringBuilder.append("type=" + (Object)((Object)this.KL()) + ";");
        stringBuilder.append("id=" + (Object)((Object)this.KK()) + ";");
        stringBuilder.append("name=" + this.getName() + ";");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static Collection KD() {
        HashSet<ch_1> hashSet = new HashSet<ch_1>();
        int n2 = ch_1.KP();
        for (int j = 0; j < n2; ++j) {
            ch_1 ch_12 = ch_1.eU(j);
            if (ch_12 == null) continue;
            hashSet.add(ch_12);
        }
        return hashSet;
    }

    public List KE() {
        LinkedList<xv_1> linkedList = new LinkedList<xv_1>();
        int n2 = this.KQ();
        for (int j = 0; j < n2; ++j) {
            xv_1 xv_12 = this.eV(j);
            if (xv_12 == null) continue;
            linkedList.add(xv_12);
        }
        return linkedList;
    }

    public List KF() {
        LinkedList<yi_0> linkedList = new LinkedList<yi_0>();
        int n2 = this.KR();
        for (int j = 0; j < n2; ++j) {
            yi_0 yi_02 = this.eW(j);
            if (yi_02 == null || yi_02 == yi_0.aAM) continue;
            linkedList.add(yi_02);
        }
        return linkedList;
    }

    public List KG() {
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        int n2 = this.KS();
        for (int j = 0; j < n2; ++j) {
            int n3 = this.eX(j);
            if (n3 == 0) continue;
            linkedList.add(n3);
        }
        return linkedList;
    }

    public List KH() {
        LinkedList<aby> linkedList = new LinkedList<aby>();
        int n2 = this.KT();
        for (int j = 0; j < n2; ++j) {
            aby aby2 = this.eY(j);
            if (aby2 == null || aby2 == aby.cih) continue;
            linkedList.add(aby2);
        }
        return linkedList;
    }

    public List KI() {
        LinkedList<Long> linkedList = new LinkedList<Long>();
        int n2 = this.KU();
        for (int j = 0; j < n2; ++j) {
            long l2 = this.eZ(j);
            if (l2 == 0L) continue;
            linkedList.add(l2);
        }
        return linkedList;
    }

    public String getName() {
        return XugglerJNI.ICodec_getName(this.hf, this);
    }

    public int KJ() {
        return XugglerJNI.ICodec_getIDAsInt(this.hf, this);
    }

    public avh KK() {
        return avh.mu(XugglerJNI.ICodec_getID(this.hf, this));
    }

    public auc_0 KL() {
        return auc_0.mq(XugglerJNI.ICodec_getType(this.hf, this));
    }

    public boolean KM() {
        return XugglerJNI.ICodec_canDecode(this.hf, this);
    }

    public boolean canEncode() {
        return XugglerJNI.ICodec_canEncode(this.hf, this);
    }

    public static ch_1 a(avh avh2) {
        long l2 = XugglerJNI.ICodec_findEncodingCodec(avh2.dZ());
        return l2 == 0L ? null : new ch_1(l2, false);
    }

    public static ch_1 eS(int n2) {
        long l2 = XugglerJNI.ICodec_findEncodingCodecByIntID(n2);
        return l2 == 0L ? null : new ch_1(l2, false);
    }

    public static ch_1 dB(String string) {
        long l2 = XugglerJNI.ICodec_findEncodingCodecByName(string);
        return l2 == 0L ? null : new ch_1(l2, false);
    }

    public static ch_1 b(avh avh2) {
        long l2 = XugglerJNI.ICodec_findDecodingCodec(avh2.dZ());
        return l2 == 0L ? null : new ch_1(l2, false);
    }

    public static ch_1 eT(int n2) {
        long l2 = XugglerJNI.ICodec_findDecodingCodecByIntID(n2);
        return l2 == 0L ? null : new ch_1(l2, false);
    }

    public static ch_1 dC(String string) {
        long l2 = XugglerJNI.ICodec_findDecodingCodecByName(string);
        return l2 == 0L ? null : new ch_1(l2, false);
    }

    public static ch_1 a(Sg sg, String string, String string2, String string3, auc_0 auc_02) {
        long l2 = XugglerJNI.ICodec_guessEncodingCodec(Sg.a(sg), sg, string, string2, string3, auc_02.dZ());
        return l2 == 0L ? null : new ch_1(l2, false);
    }

    public String KN() {
        return XugglerJNI.ICodec_getLongName(this.hf, this);
    }

    public int KO() {
        return XugglerJNI.ICodec_getCapabilities(this.hf, this);
    }

    public boolean a(aJg aJg2) {
        return XugglerJNI.ICodec_hasCapability(this.hf, this, aJg2.dZ());
    }

    public static int KP() {
        return XugglerJNI.ICodec_getNumInstalledCodecs();
    }

    public static ch_1 eU(int n2) {
        long l2 = XugglerJNI.ICodec_getInstalledCodec(n2);
        return l2 == 0L ? null : new ch_1(l2, false);
    }

    public int KQ() {
        return XugglerJNI.ICodec_getNumSupportedVideoFrameRates(this.hf, this);
    }

    public xv_1 eV(int n2) {
        long l2 = XugglerJNI.ICodec_getSupportedVideoFrameRate(this.hf, this, n2);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public int KR() {
        return XugglerJNI.ICodec_getNumSupportedVideoPixelFormats(this.hf, this);
    }

    public yi_0 eW(int n2) {
        return yi_0.ev(XugglerJNI.ICodec_getSupportedVideoPixelFormat(this.hf, this, n2));
    }

    public int KS() {
        return XugglerJNI.ICodec_getNumSupportedAudioSampleRates(this.hf, this);
    }

    public int eX(int n2) {
        return XugglerJNI.ICodec_getSupportedAudioSampleRate(this.hf, this, n2);
    }

    public int KT() {
        return XugglerJNI.ICodec_getNumSupportedAudioSampleFormats(this.hf, this);
    }

    public aby eY(int n2) {
        return aby.jz(XugglerJNI.ICodec_getSupportedAudioSampleFormat(this.hf, this, n2));
    }

    public int KU() {
        return XugglerJNI.ICodec_getNumSupportedAudioChannelLayouts(this.hf, this);
    }

    public long eZ(int n2) {
        return XugglerJNI.ICodec_getSupportedAudioChannelLayout(this.hf, this, n2);
    }
}

