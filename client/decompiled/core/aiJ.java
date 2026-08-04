/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.isometric.particles.CellParticleSystem;
import com.ankamagames.baseImpl.graphics.isometric.particles.FreeParticleSystem;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import java.io.InputStream;
import org.apache.log4j.Logger;

public class aiJ
extends fh_1 {
    protected static final Logger a = Logger.getLogger(aiJ.class);
    private static aiJ cyV = new aiJ();

    public static aiJ ayv() {
        return cyV;
    }

    public static void a(aiJ aiJ2) {
        cyV = aiJ2;
    }

    protected final cd p(InputStream inputStream) {
        assert (inputStream != null);
        byte[] byArray = new byte[inputStream.available()];
        if (inputStream.read(byArray) <= 0) {
            a.error((Object)"Erreur lors du chargement d'un fichier de particule (fichier vide?)");
        }
        inputStream.close();
        return new cd(this, byArray);
    }

    public final FreeParticleSystem kT(int n2) {
        return this.im(this.az(n2));
    }

    public final FreeParticleSystem bw(int n2, int n3) {
        return this.x(this.az(n2), n3);
    }

    public final FreeParticleSystem im(String string) {
        return this.x(string, rK);
    }

    public FreeParticleSystem x(String string, int n2) {
        return this.b(string, n2, false);
    }

    public FreeParticleSystem b(String string, int n2, boolean bl2) {
        FreeParticleSystem freeParticleSystem = new FreeParticleSystem(bl2);
        try {
            this.a(string, n2, (ParticleSystem)freeParticleSystem);
        }
        catch (Exception exception) {
            a.error((Object)("particle " + string), (Throwable)exception);
        }
        return freeParticleSystem;
    }

    public final CellParticleSystem kU(int n2) {
        return this.bx(n2, rK);
    }

    public CellParticleSystem bx(int n2, int n3) {
        try {
            CellParticleSystem cellParticleSystem = this.ayw();
            this.a(n2, n3, (ParticleSystem)cellParticleSystem);
            if (cellParticleSystem.getDuration() != 0) {
                a.error((Object)("Le systeme de particule attach\u00e9 \u00e0 la cellule " + cellParticleSystem.getX() + "/" + cellParticleSystem.getY() + " poss\u00e8de une dur\u00e9e."));
            }
            return cellParticleSystem;
        }
        catch (Exception exception) {
            a.error((Object)("Impossible de charger le syst\u00e8me de particule : " + n2 + " " + this.az(n2)), (Throwable)exception);
            return null;
        }
    }

    protected CellParticleSystem ayw() {
        return new CellParticleSystem(false);
    }
}

