/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from abG
 */
public class abg_2
extends atv {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected abg_2(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIMediaDataWrapperUpcast(l2), bl2);
        this.hf = l2;
    }

    protected abg_2(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIMediaDataWrapperUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(abg_2 abg_22) {
        if (abg_22 == null) {
            return 0L;
        }
        return abg_22.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public abg_2 aqk() {
        if (this.hf == 0L) {
            return null;
        }
        return new abg_2(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof abg_2) {
            bl2 = ((abg_2)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public atv aql() {
        atv atv2 = null;
        atv2 = this.aqp();
        if (atv2 == null && (atv2 = this.aqo()) == null && (atv2 = this.aqn()) == null && (atv2 = this.aqq()) == null) {
            atv2 = null;
        }
        return atv2;
    }

    public atv aqm() {
        atv atv2 = null;
        atv2 = this.aqt();
        if (atv2 == null && (atv2 = this.aqs()) == null && (atv2 = this.aqr()) == null && (atv2 = this.aqu()) == null) {
            atv2 = null;
        }
        return atv2;
    }

    public void a(atv atv2) {
        XugglerJNI.IMediaDataWrapper_wrap(this.hf, this, atv.c(atv2), atv2);
    }

    public void cW(boolean bl2) {
        XugglerJNI.IMediaDataWrapper_setKey(this.hf, this, bl2);
    }

    public static abg_2 b(atv atv2) {
        long l2 = XugglerJNI.IMediaDataWrapper_make(atv.c(atv2), atv2);
        return l2 == 0L ? null : new abg_2(l2, false);
    }

    protected ala_1 aqn() {
        long l2 = XugglerJNI.IMediaDataWrapper_getPacket(this.hf, this);
        return l2 == 0L ? null : new ala_1(l2, false);
    }

    protected yX aqo() {
        long l2 = XugglerJNI.IMediaDataWrapper_getAudioSamples(this.hf, this);
        return l2 == 0L ? null : new yX(l2, false);
    }

    protected ayh aqp() {
        long l2 = XugglerJNI.IMediaDataWrapper_getVideoPicture(this.hf, this);
        return l2 == 0L ? null : new ayh(l2, false);
    }

    protected abg_2 aqq() {
        long l2 = XugglerJNI.IMediaDataWrapper_getMediaDataWrapper(this.hf, this);
        return l2 == 0L ? null : new abg_2(l2, false);
    }

    protected ala_1 aqr() {
        long l2 = XugglerJNI.IMediaDataWrapper_unwrapPacket(this.hf, this);
        return l2 == 0L ? null : new ala_1(l2, false);
    }

    protected yX aqs() {
        long l2 = XugglerJNI.IMediaDataWrapper_unwrapAudioSamples(this.hf, this);
        return l2 == 0L ? null : new yX(l2, false);
    }

    protected ayh aqt() {
        long l2 = XugglerJNI.IMediaDataWrapper_unwrapVideoPicture(this.hf, this);
        return l2 == 0L ? null : new ayh(l2, false);
    }

    protected abg_2 aqu() {
        long l2 = XugglerJNI.IMediaDataWrapper_unwrapMediaDataWrapper(this.hf, this);
        return l2 == 0L ? null : new abg_2(l2, false);
    }
}

