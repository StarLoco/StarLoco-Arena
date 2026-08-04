/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public final class akK {
    public static final akK cDL = new akK();
    private boolean aK = false;
    private JFrame cDM;
    private fm_2 cDN;
    final ArrayList cDO = new ArrayList();
    final ArrayList cDP = new ArrayList();
    int aQl = 0;
    int cDQ = 0;

    private akK() {
    }

    public void initialize() {
        if (this.aK) {
            return;
        }
        SwingUtilities.invokeLater(new ce_2(this));
        this.aK = true;
    }

    public void e(ParticleSystem particleSystem) {
        if (!this.aK) {
            return;
        }
        this.cDO.add(particleSystem);
        if (this.cDO.size() > this.aQl) {
            this.aQl = this.cDO.size();
        }
        if (this.cDN != null) {
            this.cDN.b(this.cDO, this.cDP);
            this.cDN.iA().updateUI();
        }
    }

    public void f(ParticleSystem particleSystem) {
        if (!this.aK) {
            return;
        }
        this.cDO.remove(particleSystem);
        if (this.cDN != null) {
            this.cDN.b(this.cDO, this.cDP);
            this.cDN.iA().updateUI();
        }
    }

    public void c(aNH aNH2) {
        if (!this.aK) {
            return;
        }
        this.cDP.add(aNH2);
        if (this.cDP.size() > this.cDQ) {
            this.cDQ = this.cDP.size();
        }
        if (this.cDN != null) {
            this.cDN.b(this.cDO, this.cDP);
            this.cDN.iA().updateUI();
        }
    }

    public void d(aNH aNH2) {
        if (!this.aK) {
            return;
        }
        this.cDP.remove(aNH2);
        if (this.cDN != null) {
            this.cDN.b(this.cDO, this.cDP);
            this.cDN.iA().updateUI();
        }
    }

    static /* synthetic */ JFrame a(akK akK2, JFrame jFrame) {
        akK2.cDM = jFrame;
        return akK2.cDM;
    }

    static /* synthetic */ fm_2 a(akK akK2, fm_2 fm_22) {
        akK2.cDN = fm_22;
        return akK2.cDN;
    }

    static /* synthetic */ fm_2 a(akK akK2) {
        return akK2.cDN;
    }

    static /* synthetic */ JFrame b(akK akK2) {
        return akK2.cDM;
    }
}

