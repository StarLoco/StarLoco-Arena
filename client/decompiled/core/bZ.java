/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.Map;

public class bZ
implements vU {
    private String name;
    private Ju hX = new arU();
    Map hY = new HashMap();
    Map hZ = new HashMap();

    public Ju ea() {
        return this.hX;
    }

    public void a(Ju ju) {
        if (this.hX == null) {
            throw new IllegalArgumentException("null StatusManager not allowed");
        }
        this.hX = ju;
    }

    public Map eb() {
        return new HashMap(this.hY);
    }

    public void c(String string, String string2) {
        this.hY.put(string, string2);
    }

    public String getProperty(String string) {
        return (String)this.hY.get(string);
    }

    public Object getObject(String string) {
        return this.hZ.get(string);
    }

    public void d(String string, Object object) {
        this.hZ.put(string, object);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        if (string != null && string.equals(this.name)) {
            return;
        }
        if (this.name != null && !"default".equals(this.name)) {
            throw new IllegalStateException("Context has been already given a name");
        }
        this.name = string;
    }
}

