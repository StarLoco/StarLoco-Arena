/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;

/*
 * Renamed from aCX
 */
public class acx_0
implements apG {
    private static Logger a = Logger.getLogger(acx_0.class);
    private Class ach = ef_1.class;
    private boolean dvn = false;

    public ef_1 ky(String string) {
        return this.m(this.ach, string);
    }

    public ef_1 m(Class clazz, String string) {
        if (string == null || !clazz.equals(this.ach)) {
            return null;
        }
        ef_1 ef_12 = add_1.aOG().yh().dK(string);
        if (ef_12 != null) {
            return ef_12;
        }
        ef_12 = agx_2.aTc().lp(string);
        if (ef_12 != null) {
            return ef_12;
        }
        try {
            URL uRL = new URL(string);
            if (!an_2.a(uRL)) {
                a.warn((Object)("Impossible de lire l'image " + string));
                return null;
            }
            return agx_2.aTc().h(uRL);
        }
        catch (MalformedURLException malformedURLException) {
            URL uRL = this.getClass().getClassLoader().getResource(string);
            if (uRL != null) {
                return agx_2.aTc().h(uRL);
            }
            File file = new File(string);
            if (file.exists()) {
                try {
                    return agx_2.aTc().h(file.toURI().toURL());
                }
                catch (MalformedURLException malformedURLException2) {
                    a.error((Object)"Exception", (Throwable)malformedURLException2);
                }
            }
            a.error((Object)("pas de texture " + string));
            return null;
        }
    }

    public Class uk() {
        return this.ach;
    }

    public boolean ul() {
        return this.dvn;
    }

    public void eK(boolean bl2) {
        this.dvn = bl2;
    }

    public boolean um() {
        return false;
    }

    public String a(zp_1 zp_12, DS dS, Class clazz, String string, afq_1 afq_12) {
        if (string == null || !clazz.equals(this.ach)) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        zp_12.j(this.ach);
        ef_1 ef_12 = add_1.aOG().yh().dK(string);
        if (ef_12 != null) {
            return stringBuilder.append("doc.getTexture(\"").append(string).append("\")").toString();
        }
        ef_12 = agx_2.aTc().lp(string);
        if (ef_12 != null) {
            zp_12.j(agx_2.class);
            return stringBuilder.append("TextureLoader.getInstance().loadTextureDirect(\"").append(string).append("\")").toString();
        }
        if (an_2.o(string)) {
            String string2 = zp_12.GQ();
            zp_12.j(MalformedURLException.class);
            zp_12.j(agx_2.class);
            zp_12.a(new aKI(this.ach, string2, "null"));
            zp_12.a(new azw("try {"));
            zp_12.a(new azw("\tURL url = new URL(\"" + string + "\""));
            zp_12.a(new aKI(null, string2, "TextureLoader.getInstance().loadTexture(url)", true));
            zp_12.a(new azw("} catch (MalformedURLException e) {}"));
            return string2;
        }
        URL uRL = this.getClass().getClassLoader().getResource(string);
        if (uRL != null) {
            zp_12.j(agx_2.class);
            String string3 = zp_12.GQ();
            zp_12.a(new aKI(this.ach, string3, "null"));
            zp_12.a(new azw("{"));
            zp_12.a(new azw("\tURL url = getClass().getClassLoader().getResource(\"" + string + "\""));
            zp_12.a(new aKI(null, string3, "TextureLoader.getInstance().loadTexture(url)", true));
            zp_12.a(new azw("}"));
            return string3;
        }
        File file = new File(string);
        if (file.exists()) {
            try {
                zp_12.j(agx_2.class);
                zp_12.j(File.class);
                zp_12.j(MalformedURLException.class);
                agx_2.aTc().h(file.toURI().toURL());
                String string4 = zp_12.GQ();
                zp_12.a(new aKI(this.ach, string4, "null"));
                zp_12.a(new azw("try {"));
                zp_12.a(new azw("\tFile f = new File(\"" + string + "\""));
                zp_12.a(new aKI(null, string4, "TextureLoader.getInstance().loadTexture(f.toURI().toURL())", true));
                zp_12.a(new azw("} catch (MalformedURLException e) {}"));
                return string4;
            }
            catch (MalformedURLException malformedURLException) {
                a.error((Object)"Exception", (Throwable)malformedURLException);
            }
        }
        a.error((Object)("pas de texture " + string));
        return "null";
    }
}

