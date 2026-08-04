/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Sg
extends alp {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected Sg(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIContainerFormatUpcast(l2), bl2);
        this.hf = l2;
    }

    protected Sg(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIContainerFormatUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(Sg sg) {
        if (sg == null) {
            return 0L;
        }
        return sg.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public Sg aeJ() {
        if (this.hf == 0L) {
            return null;
        }
        return new Sg(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof Sg) {
            bl2 = ((Sg)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName() + "@" + this.hashCode() + "[");
        if (this.aeX()) {
            stringBuilder.append("iname:" + this.aeO() + ";");
            stringBuilder.append("ilongname:" + this.aeP() + ";");
        }
        if (this.aeW()) {
            stringBuilder.append("oname:" + this.aeQ() + ";");
            stringBuilder.append("olongname:" + this.aeR() + ";");
            stringBuilder.append("omimetype:" + this.aeS() + ";");
            stringBuilder.append("oextensions:" + this.aeY() + ";");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public List aeK() {
        LinkedList<avh> linkedList = new LinkedList<avh>();
        HashSet<avh> hashSet = new HashSet<avh>();
        int n2 = this.afc();
        for (int j = 0; j < n2; ++j) {
            avh avh2 = this.hE(j);
            if (avh2 != avh.cYB && !hashSet.contains((Object)avh2)) {
                linkedList.add(avh2);
            }
            hashSet.add(avh2);
        }
        return linkedList;
    }

    public List aeL() {
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        HashSet<Integer> hashSet = new HashSet<Integer>();
        int n2 = this.afc();
        for (int j = 0; j < n2; ++j) {
            int n3 = this.hF(j);
            avh avh2 = this.hE(j);
            if (avh2 != avh.cYB && !hashSet.contains(n3)) {
                linkedList.add(n3);
            }
            hashSet.add(n3);
        }
        return linkedList;
    }

    public static Collection aeM() {
        HashSet<Sg> hashSet = new HashSet<Sg>();
        int n2 = Sg.afd();
        for (int j = 0; j < n2; ++j) {
            Sg sg = Sg.hG(j);
            if (sg == null) continue;
            hashSet.add(sg);
        }
        return hashSet;
    }

    public static Collection aeN() {
        HashSet<Sg> hashSet = new HashSet<Sg>();
        int n2 = Sg.afe();
        for (int j = 0; j < n2; ++j) {
            Sg sg = Sg.hH(j);
            if (sg == null) continue;
            hashSet.add(sg);
        }
        return hashSet;
    }

    public avh a(auc_0 auc_02) {
        return this.a(auc_02, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public avh c(avh avh2) {
        alp alp2 = null;
        try {
            if (avh2 == null || avh2 == avh.cYB) {
                throw new IllegalArgumentException("null inputCodecId");
            }
            alp2 = ch_1.b(avh2);
            if (alp2 == null) {
                throw new UnsupportedOperationException("could not find decoding codec");
            }
            avh avh3 = this.b((ch_1)alp2);
            return avh3;
        }
        finally {
            if (alp2 != null) {
                alp2.delete();
            }
        }
    }

    public avh b(ch_1 ch_12) {
        if (ch_12 == null) {
            throw new IllegalArgumentException();
        }
        return this.a(ch_12.KL(), ch_12.KK());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public avh a(auc_0 auc_02, avh avh2) {
        alp alp2 = null;
        alp alp3 = null;
        try {
            if (auc_02 == null) {
                throw new IllegalArgumentException("null codec type");
            }
            if (!this.aeW()) {
                throw new IllegalArgumentException("passed output container format, actally an input container format");
            }
            if (avh2 != null && avh2 != avh.cYB) {
                alp3 = ch_1.a(avh2);
                if (alp3 == null) {
                    throw new IllegalArgumentException("could not find input codec id");
                }
                if (((ch_1)alp3).KL() != auc_02) {
                    throw new IllegalArgumentException("inputCodecId of different type than expected");
                }
            }
            avh avh3 = null;
            switch (auc_02) {
                case cVP: {
                    avh3 = this.aeZ();
                    break;
                }
                case cVO: {
                    avh3 = this.afa();
                    break;
                }
                case cVR: {
                    avh3 = this.afb();
                }
            }
            if (avh3 != null && avh3 != avh.cYB) {
                if (alp2 != null) {
                    alp2.delete();
                }
                alp2 = ch_1.a(avh3);
            }
            if (alp2 != null) {
                avh avh4 = avh3;
                return avh4;
            }
            if (alp3 != null) {
                if (alp2 != null) {
                    alp2.delete();
                }
                if ((alp2 = ch_1.a(avh2)) != null && this.e(((ch_1)alp2).KK())) {
                    avh avh5 = ((ch_1)alp2).KK();
                    return avh5;
                }
            }
            for (avh avh6 : this.aeK()) {
                if (alp2 != null) {
                    alp2.delete();
                }
                if ((alp2 = ch_1.a(avh6)) == null || ((ch_1)alp2).KL() != auc_02) continue;
                avh3 = ((ch_1)alp2).KK();
                if (!((ch_1)alp2).canEncode()) continue;
                break;
            }
            if (avh3 == null || avh3 == avh.cYB) {
                throw new UnsupportedOperationException("could not guess codec");
            }
            Object object = avh3;
            return object;
        }
        finally {
            if (alp2 != null) {
                alp2.delete();
            }
            if (alp3 != null) {
                alp3.delete();
            }
        }
    }

    public int fO(String string) {
        return XugglerJNI.IContainerFormat_setInputFormat(this.hf, this, string);
    }

    public int h(String string, String string2, String string3) {
        return XugglerJNI.IContainerFormat_setOutputFormat(this.hf, this, string, string2, string3);
    }

    public String aeO() {
        return XugglerJNI.IContainerFormat_getInputFormatShortName(this.hf, this);
    }

    public String aeP() {
        return XugglerJNI.IContainerFormat_getInputFormatLongName(this.hf, this);
    }

    public String aeQ() {
        return XugglerJNI.IContainerFormat_getOutputFormatShortName(this.hf, this);
    }

    public String aeR() {
        return XugglerJNI.IContainerFormat_getOutputFormatLongName(this.hf, this);
    }

    public String aeS() {
        return XugglerJNI.IContainerFormat_getOutputFormatMimeType(this.hf, this);
    }

    public static Sg aeT() {
        long l2 = XugglerJNI.IContainerFormat_make();
        return l2 == 0L ? null : new Sg(l2, false);
    }

    public int aeU() {
        return XugglerJNI.IContainerFormat_getInputFlags(this.hf, this);
    }

    public void hC(int n2) {
        XugglerJNI.IContainerFormat_setInputFlags(this.hf, this, n2);
    }

    public boolean a(abu_2 abu_22) {
        return XugglerJNI.IContainerFormat_getInputFlag(this.hf, this, abu_22.dZ());
    }

    public void a(abu_2 abu_22, boolean bl2) {
        XugglerJNI.IContainerFormat_setInputFlag(this.hf, this, abu_22.dZ(), bl2);
    }

    public int aeV() {
        return XugglerJNI.IContainerFormat_getOutputFlags(this.hf, this);
    }

    public void hD(int n2) {
        XugglerJNI.IContainerFormat_setOutputFlags(this.hf, this, n2);
    }

    public boolean b(abu_2 abu_22) {
        return XugglerJNI.IContainerFormat_getOutputFlag(this.hf, this, abu_22.dZ());
    }

    public void b(abu_2 abu_22, boolean bl2) {
        XugglerJNI.IContainerFormat_setOutputFlag(this.hf, this, abu_22.dZ(), bl2);
    }

    public boolean aeW() {
        return XugglerJNI.IContainerFormat_isOutput(this.hf, this);
    }

    public boolean aeX() {
        return XugglerJNI.IContainerFormat_isInput(this.hf, this);
    }

    public String aeY() {
        return XugglerJNI.IContainerFormat_getOutputExtensions(this.hf, this);
    }

    public avh aeZ() {
        return avh.mu(XugglerJNI.IContainerFormat_getOutputDefaultAudioCodec(this.hf, this));
    }

    public avh afa() {
        return avh.mu(XugglerJNI.IContainerFormat_getOutputDefaultVideoCodec(this.hf, this));
    }

    public avh afb() {
        return avh.mu(XugglerJNI.IContainerFormat_getOutputDefaultSubtitleCodec(this.hf, this));
    }

    public int afc() {
        return XugglerJNI.IContainerFormat_getOutputNumCodecsSupported(this.hf, this);
    }

    public avh hE(int n2) {
        return avh.mu(XugglerJNI.IContainerFormat_getOutputCodecID(this.hf, this, n2));
    }

    public int hF(int n2) {
        return XugglerJNI.IContainerFormat_getOutputCodecTag__SWIG_0(this.hf, this, n2);
    }

    public int d(avh avh2) {
        return XugglerJNI.IContainerFormat_getOutputCodecTag__SWIG_1(this.hf, this, avh2.dZ());
    }

    public boolean e(avh avh2) {
        return XugglerJNI.IContainerFormat_isCodecSupportedForOutput(this.hf, this, avh2.dZ());
    }

    public static int afd() {
        return XugglerJNI.IContainerFormat_getNumInstalledInputFormats();
    }

    public static Sg hG(int n2) {
        long l2 = XugglerJNI.IContainerFormat_getInstalledInputFormat(n2);
        return l2 == 0L ? null : new Sg(l2, false);
    }

    public static int afe() {
        return XugglerJNI.IContainerFormat_getNumInstalledOutputFormats();
    }

    public static Sg hH(int n2) {
        long l2 = XugglerJNI.IContainerFormat_getInstalledOutputFormat(n2);
        return l2 == 0L ? null : new Sg(l2, false);
    }
}

