/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.isometric.particles.FreeParticleSystem;
import org.apache.log4j.Logger;

/*
 * Renamed from awE
 */
public class awe_0
extends jw_1 {
    private static final Logger a = Logger.getLogger(awe_0.class);
    private int dib;
    private int aMB;
    private int aMC;

    public boolean a(arp_0 arp_02) {
        try {
            FreeParticleSystem freeParticleSystem = aiJ.ayv().kT(this.dib);
            freeParticleSystem.eC(arp_02.aEZ());
            qd_1.uW().b(freeParticleSystem);
            if (arp_02 instanceof ahh_1) {
                ahh_1 ahh_12 = (ahh_1)arp_02;
                freeParticleSystem.setPosition((float)(ahh_12.getWorldX() + (double)this.aMB), (float)(ahh_12.getWorldY() + (double)this.aMC), ahh_12.gp());
                ajh_2.b(freeParticleSystem);
            }
        }
        catch (Exception exception) {
            a.error((Object)("AddParticle (" + this.dib + ") depuis un animatedElement " + arp_02), (Throwable)exception);
        }
        return false;
    }

    public void a(byte by, acf acf2) {
        this.dib = acf2.readInt();
        if (by == 3) {
            this.aMB = acf2.readShort();
            this.aMC = acf2.readShort();
        }
    }

    public void a(aij_1 aij_12) {
    }

    public aro ek() {
        return aro.cPA;
    }

    public int getSize() {
        return super.getSize() + 12;
    }
}

