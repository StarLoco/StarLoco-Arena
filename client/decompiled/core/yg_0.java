/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from Yg
 */
public class yg_0
implements JG {
    protected static final Logger a = Logger.getLogger(yg_0.class);
    protected acl_0 uG;
    protected byte axW;
    private final cp_2 caq = new cp_2();
    private int car;

    public void release() {
        if (this.uG != null) {
            try {
                this.uG.af(this);
                this.uG = null;
            }
            catch (Exception exception) {
                a.error((Object)"ne peut arriver normalement");
            }
        } else {
            this.j();
        }
    }

    public void b() {
        this.axW = (byte)-1;
        this.car = 0;
        this.caq.clear();
    }

    public void j() {
        this.axW = (byte)-1;
        akz_0 akz_02 = this.caq.eI();
        while (akz_02.hasNext()) {
            akz_02.fK();
            alp_0 alp_02 = (alp_0)akz_02.value();
            if (alp_02.PH() != this) continue;
            alp_02.a((yg_0)null);
        }
        this.caq.clear();
    }

    public byte lV() {
        return this.axW;
    }

    public void as(byte by) {
        this.axW = by;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("(").append(this.axW);
        this.caq.a(new aCo(this, stringBuilder));
        return stringBuilder.append(")").toString();
    }

    public void j(alp_0 alp_02) {
        this.caq.a(alp_02.getId(), alp_02);
        yg_0 yg_02 = alp_02.PH();
        if (yg_02 != this) {
            if (yg_02 != null && !yg_02.xg()) {
                a.error((Object)"On ajoute un gars \u00e0 une team alors qu'il est dans une autre ?");
                yg_02.k(alp_02);
            }
            alp_02.a(this);
        }
    }

    public void k(alp_0 alp_02) {
        if (!this.caq.v(alp_02.getId())) {
            a.error((Object)("On essaye de virer un gars d'une team qui ne le contient pas : nombre de fighters : " + this.caq.size() + " teamId " + this.axW + " fighterId " + alp_02.getId()), (Throwable)new Exception());
            return;
        }
        this.caq.u(alp_02.getId());
        alp_02.a((yg_0)null);
    }

    public alp_0 dk(long l2) {
        return (alp_0)this.caq.t(l2);
    }

    public Iterator amp() {
        return new agw_2(this.caq);
    }

    public cp_2 amq() {
        return this.caq;
    }

    public int amr() {
        return this.caq.size();
    }

    public byte[] ams() {
        return new byte[]{this.axW};
    }

    public void R(byte[] byArray) {
        this.axW = byArray[0];
    }

    public boolean l(alp_0 alp_02) {
        return this.caq.v(alp_02.getId());
    }

    public int amt() {
        return this.car;
    }

    public void je(int n2) {
        this.car = Math.max(this.car + n2, 0);
    }

    public boolean xg() {
        return false;
    }
}

