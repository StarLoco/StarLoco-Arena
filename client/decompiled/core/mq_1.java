/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import com.ankamagames.xulor2.graphics.XulorParticleSystem;
import java.io.InputStream;
import org.apache.log4j.Logger;

/*
 * Renamed from mq
 */
public class mq_1
extends fh_1 {
    private static final mq_1 JM = new mq_1();
    private static final Logger a = Logger.getLogger(mq_1.class);

    public static mq_1 rk() {
        return JM;
    }

    protected final yd_1 f(InputStream inputStream) {
        assert (inputStream != null);
        byte[] byArray = new byte[inputStream.available()];
        if (inputStream.read(byArray) <= 0) {
            a.error((Object)"Erreur lors du chargement d'un script (fichier vide?)");
        }
        inputStream.close();
        return new yd_1(this, byArray);
    }

    public final XulorParticleSystem aX(String string) {
        return this.j(string, rK);
    }

    public XulorParticleSystem j(String string, int n2) {
        XulorParticleSystem xulorParticleSystem = new XulorParticleSystem();
        try {
            this.a(string, n2, (ParticleSystem)xulorParticleSystem);
        }
        catch (Exception exception) {
            a.error((Object)("erreur de cr\u00e9ation de particule " + string), (Throwable)exception);
            return null;
        }
        return xulorParticleSystem;
    }
}

