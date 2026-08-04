/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * Renamed from Iv
 */
public class iv_0
implements ahy_2 {
    final ats_0 bho = new ats_0();

    iv_0() {
    }

    public void put(String string, String string2) {
        if (string == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        HashMap hashMap = (HashMap)this.bho.get();
        HashMap<String, String> hashMap2 = new HashMap<String, String>();
        if (hashMap != null) {
            hashMap2.putAll(hashMap);
        }
        this.bho.set(hashMap2);
        hashMap2.put(string, string2);
    }

    public String get(String string) {
        HashMap hashMap = (HashMap)this.bho.get();
        if (hashMap != null && string != null) {
            return (String)hashMap.get(string);
        }
        return null;
    }

    public void remove(String string) {
        HashMap hashMap = (HashMap)this.bho.get();
        HashMap hashMap2 = new HashMap();
        if (hashMap != null) {
            hashMap2.putAll(hashMap);
        }
        this.bho.set(hashMap2);
        hashMap2.remove(string);
    }

    public void clear() {
        HashMap hashMap = (HashMap)this.bho.get();
        if (hashMap != null) {
            hashMap.clear();
            this.bho.remove();
        }
    }

    public Map Lm() {
        return (Map)this.bho.get();
    }

    public Map Uv() {
        HashMap hashMap = (HashMap)this.bho.get();
        if (hashMap == null) {
            return null;
        }
        return new HashMap(hashMap);
    }

    public Set Uw() {
        HashMap hashMap = (HashMap)this.bho.get();
        if (hashMap != null) {
            return hashMap.keySet();
        }
        return null;
    }

    public void e(Map map) {
        HashMap hashMap = (HashMap)this.bho.get();
        HashMap hashMap2 = new HashMap();
        hashMap2.putAll(map);
        this.bho.set(hashMap2);
        if (hashMap != null) {
            hashMap.clear();
            hashMap = null;
        }
    }
}

