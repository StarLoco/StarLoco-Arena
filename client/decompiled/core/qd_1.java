/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ankamagames.baseImpl.graphics.isometric.maskableLayer.MaskableElementAddedListener
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.isometric.maskableLayer.MaskableElementAddedListener;
import com.ankamagames.baseImpl.graphics.isometric.particles.CellParticleSystem;
import com.ankamagames.baseImpl.graphics.isometric.particles.FreeParticleSystem;
import com.ankamagames.baseImpl.graphics.isometric.particles.IsoParticleSystem;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from qd
 */
public class qd_1
implements aHq {
    private static final Logger a = Logger.getLogger(qd_1.class);
    private static final int adi = 2916;
    private static final int adj = 8100;
    private static final qd_1 adk = new qd_1();
    protected MaskableElementAddedListener adl;
    private final Map adm = new ConcurrentHashMap();
    private byte adn;
    private volatile boolean ado = true;

    public static qd_1 uW() {
        return adk;
    }

    public void ak(boolean bl2) {
        this.ado = bl2;
    }

    public boolean uX() {
        return this.ado;
    }

    public void a(CellParticleSystem cellParticleSystem) {
        if (this.a((IsoParticleSystem)cellParticleSystem)) {
            return;
        }
        this.b(cellParticleSystem);
    }

    public boolean a(IsoParticleSystem isoParticleSystem) {
        for (IsoParticleSystem isoParticleSystem2 : this.adm.values()) {
            if (isoParticleSystem2.getId() != isoParticleSystem.getId()) continue;
            return true;
        }
        return false;
    }

    public IsoParticleSystem cJ(int n2) {
        return (IsoParticleSystem)this.adm.get(n2);
    }

    public void b(IsoParticleSystem isoParticleSystem) {
        if (!this.ado) {
            return;
        }
        assert (!this.adm.containsKey(isoParticleSystem.getId()));
        isoParticleSystem.HE();
        this.adm.put(isoParticleSystem.getId(), isoParticleSystem);
        this.a(isoParticleSystem, (int)isoParticleSystem.getX(), (int)isoParticleSystem.getY(), (int)isoParticleSystem.id());
        akK.cDL.e(isoParticleSystem);
    }

    public void cK(int n2) {
        this.b(n2, false);
    }

    public void b(int n2, boolean bl2) {
        IsoParticleSystem isoParticleSystem = (IsoParticleSystem)this.adm.get(n2);
        if (isoParticleSystem == null) {
            return;
        }
        if (bl2) {
            isoParticleSystem.alQ();
        } else {
            isoParticleSystem.kill();
        }
    }

    public void c(int n2, boolean bl2) {
        IsoParticleSystem isoParticleSystem = (IsoParticleSystem)this.adm.get(n2);
        if (isoParticleSystem != null) {
            isoParticleSystem.setVisible(bl2);
        }
    }

    public void uY() {
        for (IsoParticleSystem isoParticleSystem : this.adm.values()) {
            isoParticleSystem.agM();
        }
        if (!this.ado) {
            return;
        }
        Iterator iterator = this.adm.values().iterator();
        while (iterator.hasNext()) {
            IsoParticleSystem isoParticleSystem;
            isoParticleSystem = (IsoParticleSystem)iterator.next();
            if (isoParticleSystem.avb() > 0) continue;
            isoParticleSystem.HF();
            iterator.remove();
        }
    }

    public void a(aba_2 aba_22, int n2) {
        if (!this.ado) {
            return;
        }
        float f = (float)n2 / 1000.0f;
        if (f > 0.04f) {
            f = 0.04f;
        }
        for (IsoParticleSystem isoParticleSystem : this.adm.values()) {
            if (isoParticleSystem.avb() == 0 || isoParticleSystem.agL() > this.adn) continue;
            isoParticleSystem.a(f);
        }
    }

    public void a(aba_2 aba_22, float f, float f2) {
        if (!this.ado) {
            return;
        }
        Iterator iterator = this.adm.values().iterator();
        while (iterator.hasNext()) {
            IsoParticleSystem isoParticleSystem = (IsoParticleSystem)iterator.next();
            if (isoParticleSystem.avb() <= 0) {
                isoParticleSystem.HF();
                iterator.remove();
                continue;
            }
            if (isoParticleSystem.agL() > this.adn) continue;
            boolean bl2 = true;
            if (isoParticleSystem.Gg()) {
                bl2 = aba_22.a(isoParticleSystem, isoParticleSystem, Math.round(isoParticleSystem.getX()), Math.round(isoParticleSystem.getY()), isoParticleSystem.id(), (isoParticleSystem.bZF ? 5.0f : 9.0f) * 0.0625f);
            }
            switch (this.a(isoParticleSystem, aba_22, (int)f, (int)f2)) {
                case aqr: {
                    isoParticleSystem.a(aba_22);
                    break;
                }
                case aqt: {
                    try {
                        isoParticleSystem.iZ(1);
                    }
                    catch (Exception exception) {
                        while (isoParticleSystem.avb() >= 0) {
                            isoParticleSystem.HF();
                        }
                        iterator.remove();
                        a.error((Object)"probl\u00e8me lors de l'arr\u00eat d'un syst\u00e8me de particule", (Throwable)exception);
                    }
                    break;
                }
                case aqs: {
                    isoParticleSystem.agK();
                }
            }
            if (bl2) continue;
            ajh_2.b(isoParticleSystem);
        }
    }

    private ur_2 a(IsoParticleSystem isoParticleSystem, aba_2 aba_22, int n2, int n3) {
        double d = isoParticleSystem.getX() - (float)n2;
        double d2 = isoParticleSystem.getY() - (float)n3;
        double d3 = Math.pow(d, 2.0) + Math.pow(d2, 2.0);
        if (d3 > 8100.0) {
            return ur_2.aqt;
        }
        if (!isoParticleSystem.isVisible()) {
            return ur_2.aqs;
        }
        if (d3 < 2916.0) {
            if (aba_22.vC().a(isoParticleSystem)[3] > 0.0f) {
                return ur_2.aqr;
            }
            return ur_2.aqs;
        }
        return ur_2.aqs;
    }

    public void O(int n2, int n3) {
        for (IsoParticleSystem isoParticleSystem : this.adm.values()) {
            if ((int)isoParticleSystem.getX() != n2 || (int)isoParticleSystem.getY() != n3) continue;
            isoParticleSystem.kill();
        }
    }

    private void a(IsoParticleSystem isoParticleSystem, int n2, int n3, int n4) {
        ajh_2.b(isoParticleSystem);
    }

    public void b(Du du) {
        for (IsoParticleSystem isoParticleSystem : this.adm.values()) {
            FreeParticleSystem freeParticleSystem;
            if (!(isoParticleSystem instanceof FreeParticleSystem) || (freeParticleSystem = (FreeParticleSystem)isoParticleSystem).qF() != du) continue;
            this.cK(isoParticleSystem.getId());
        }
    }

    public void v(byte by) {
        this.adn = by;
    }
}

