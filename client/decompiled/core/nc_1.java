/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Renamed from Nc
 */
class nc_1
implements ra_2 {
    short[] byW;
    int byX;
    boolean byY;
    boolean byZ;
    Iterator bza;
    atD bzb;
    final /* synthetic */ alh_1 bzc;

    nc_1(alh_1 alh_12) {
        this.bzc = alh_12;
        this.byW = this.bzc.cEV.Gj();
        this.byX = -1;
    }

    public boolean wf() {
        if (this.aak()) {
            return true;
        }
        if (this.aaj()) {
            return true;
        }
        return this.aai();
    }

    private boolean aai() {
        if (this.byX >= this.byW.length - 1) {
            return false;
        }
        ++this.byX;
        this.byY = true;
        this.byZ = false;
        return this.aaj();
    }

    private boolean aaj() {
        if (!this.aal()) {
            return false;
        }
        if (this.byZ) {
            return false;
        }
        lf lf2 = (lf)this.bzc.cEV.an(this.byW[this.byX]);
        if (!this.byY) {
            this.byZ = true;
            this.bza = lf2.pS().iterator();
            return this.aak();
        }
        this.byY = false;
        this.bza = lf2.pT().iterator();
        return this.bza.hasNext() ? this.aak() : this.aaj();
    }

    private boolean aak() {
        if (!this.aam()) {
            this.bzb = null;
            return false;
        }
        atD atD2 = (atD)this.bza.next();
        if (!atD2.isPersistent()) {
            return this.wf();
        }
        this.bzb = atD2;
        this.bza.remove();
        return true;
    }

    private boolean aal() {
        return this.byX >= 0 && this.byX < this.byW.length;
    }

    private boolean aam() {
        return this.bza != null && this.bza.hasNext();
    }

    public akv_0 wg() {
        if (this.bzb == null) {
            throw new NoSuchElementException();
        }
        return akv_0.eF(this.bzc.aj).pb(this.byW[this.byX]).fi(this.byZ);
    }

    public atD wh() {
        if (this.bzb == null) {
            throw new NoSuchElementException();
        }
        return this.bzb;
    }
}

