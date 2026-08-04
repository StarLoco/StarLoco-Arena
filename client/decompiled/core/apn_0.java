/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/*
 * Renamed from aPn
 */
public abstract class apn_0
extends adw_0
implements xn_1 {
    private final ArrayList epd = new ArrayList();
    private final EnumMap epe = new EnumMap(avr_0.class);
    protected boolean epf;
    private final aea_0 epg = new amp_2(this);

    public final Collection aYW() {
        return this.epd;
    }

    public boolean b(avr_0 avr_02, aox_2 aox_22) {
        boolean bl2 = this.a(avr_02, aox_22);
        if (!bl2) {
            a.warn((Object)("Action non prise en compte par le ModelControler (ClientMapInteractiveElement) : " + avr_02.toString()));
        }
        return bl2;
    }

    public void j() {
        super.j();
        this.epd.clear();
        this.epe.clear();
        this.epf = false;
    }

    public boolean b(axu_0 axu_02) {
        axu_02.a(this);
        return this.epd.add(axu_02);
    }

    public final void aYX() {
        this.epd.clear();
    }

    public abstract List gk();

    protected aea_0 asR() {
        return this.epg;
    }

    protected final aea_0 asS() {
        return aea_0.dBr;
    }

    public dc_0 asT() {
        ry ry2 = this.asL();
        return auU.bW(ry2.getX(), ry2.getY());
    }

    public abstract boolean gf();

    public final void aYY() {
        for (axu_0 axu_02 : this.epd) {
            this.c(axu_02);
        }
    }

    protected void c(axu_0 axu_02) {
        axu_02.update();
    }

    public void aYZ() {
        this.epf = true;
    }

    public void aZa() {
        this.epf = false;
    }

    public abstract void a(axu_0 var1);

    public boolean d(axu_0 axu_02) {
        return this.epd.remove(axu_02);
    }

    protected final void b(avr_0 avr_02) {
        int n2 = this.c(avr_02);
        if (n2 >= 0) {
            Map<String, Long> map = Collections.singletonMap("elementId", this.nD);
            Ky.WG().a(this.c(avr_02), null, map, false);
        }
    }

    final int c(avr_0 avr_02) {
        if (this.epe.containsKey(avr_02)) {
            return (Integer)this.epe.get(avr_02);
        }
        return -1;
    }

    static /* synthetic */ EnumMap e(apn_0 apn_02) {
        return apn_02.epe;
    }
}

