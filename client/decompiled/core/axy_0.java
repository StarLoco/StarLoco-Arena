/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from axY
 */
public class axy_0
extends adg_2 {
    private static Logger a = Logger.getLogger(axy_0.class);
    public static final String TAG = "Video";
    protected String aJ;
    protected sm_2 dkr;
    protected og_1 dks;
    private final sv_0 dkt = new sf_0(this);
    public static final int dku = "path".hashCode();
    public static final int dkv = "onTimeChange".hashCode();

    public boolean isAppearanceCompatible(Zb zb) {
        return true;
    }

    public void a(na_1 na_12) {
        super.a(na_12);
    }

    protected void pX() {
        super.pX();
        if (this.arC != null && this.dkr != null && this.dkr.getEntity() != null) {
            this.arC.i(this.dkr.getEntity());
        }
    }

    public String getTag() {
        return TAG;
    }

    public String getPath() {
        return this.aJ;
    }

    public void setPath(String string) {
        this.aJ = string;
    }

    public void play() {
        if (this.aJ == null) {
            return;
        }
        rr_0 rr_02 = this.dks.abB();
        if (rr_02 == null) {
            rr_02 = new rr_0(this.aJ);
            rr_02.initialize();
            this.dks.a(rr_02);
        }
        if (!rr_02.isStarted()) {
            rr_02.start();
            tg_2.bOB.b(rr_02);
            agj_1 agj_12 = this.getSize();
            int n2 = (int)agj_12.getWidth();
            int n3 = (int)agj_12.getHeight();
            this.dkr.setWidth(n2);
            this.dkr.setHeight(n3);
        }
        this.setNeedsToPreProcess();
    }

    public og_1 getVideoRenderer() {
        return this.dks;
    }

    public void setSize(int n2, int n3) {
        super.setSize(n2, n3);
        this.dkr.setWidth(n2);
        this.dkr.setHeight(n3);
        this.setNeedsToPreProcess();
    }

    public void setPaused(boolean bl2) {
        rr_0 rr_02 = this.dks.abB();
        rr_02.setPaused(bl2);
        this.setNeedsToPreProcess();
    }

    public boolean isPaused() {
        rr_0 rr_02 = this.dks.abB();
        return rr_02.isPaused();
    }

    public boolean isInitialized() {
        return this.dks.abB() != null;
    }

    public void reset() {
        rr_0 rr_02 = this.dks.abB();
        rr_02.reset();
        this.setNeedsToPreProcess();
    }

    public long getDuration() {
        rr_0 rr_02 = this.dks.abB();
        return rr_02.getDuration();
    }

    public long getFrameCount() {
        rr_0 rr_02 = this.dks.abB();
        return rr_02.getFrameCount();
    }

    public void seek(long l2) {
        rr_0 rr_02 = this.dks.abB();
        rr_02.seek(l2);
        if (!rr_02.isStarted()) {
            this.play();
        }
    }

    public long getVideoPosition() {
        rr_0 rr_02 = this.dks.abB();
        return rr_02.wt();
    }

    public void setOnTimeChange(ez_1 ez_12) {
        this.a(qe_1.bFK, ez_12, false);
    }

    public void j() {
        super.j();
        this.dkr.j();
        add_1.aOG().aON().b(this.dkt);
    }

    public void b() {
        super.b();
        Zb zb = Zb.checkOut();
        zb.setWidget(this);
        this.a(zb);
        this.setNeedsToPreProcess();
        this.dks = new og_1();
        if (add_1.aOG().aON() != null) {
            add_1.aOG().aON().a(this.dkt);
        }
        this.dkr = new sm_2();
        this.dkr.a(this.dks);
        this.dkr.b();
        this.dkr.setFlipVerticaly(true);
        this.setMinSize(new agj_1(32, 32));
        this.setSize(this.getMinSize());
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.dks.abB() != null && this.dks.abB().isInitialized() && this.cLZ != null) {
            this.dkr.a(this.aLd, this.cLZ.getMargin(), this.cLZ.getBorder(), this.cLZ.getPadding());
            if (this.dks.abB() != null && this.dks.abB().isInitialized() && this.dks.abB().isStarted() && !this.dks.abB().ws()) {
                gm_0 gm_02 = new gm_0(this);
                gm_02.S(this.dks.abB().wt());
                gm_02.setValue(this.dks.abB().wt());
                this.f(gm_02);
                return true;
            }
        }
        return bl2;
    }

    public void a(air_1 air_12) {
        axy_0 axy_02 = (axy_0)air_12;
        super.a(air_12);
        axy_02.setPath(this.getPath());
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == dku) {
            this.setPath(string);
        } else if (n2 == dkv) {
            this.setOnTimeChange((ez_1)if_12.c(ez_1.class, string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != dku) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setPath((String)object);
        return true;
    }
}

