/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from auR
 */
public class aur_0
extends dm_1 {
    private String uri = "";
    private ClassLoader cXq;

    public void setURI(String string) {
        if (string.equals("antlib:org.apache.tools.ant")) {
            string = "";
        }
        if (string.startsWith("ant:")) {
            throw new eq_2("Attempt to use a reserved URI " + string);
        }
        this.uri = string;
    }

    public String getURI() {
        return this.uri;
    }

    public void g(ClassLoader classLoader) {
        this.cXq = classLoader;
    }

    public ClassLoader aHH() {
        return this.cXq;
    }
}

