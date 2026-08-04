/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from zf
 */
public class zf_0 {
    ArrayList aEu = new ArrayList();

    public zf_0() {
    }

    public zf_0(String string) {
        this();
        if (string == null) {
            return;
        }
        int n2 = 0;
        while (true) {
            String string2;
            int n3;
            if ((n3 = string.indexOf(47, n2)) == -1) {
                string2 = string.substring(n2);
                if (string2 == null || string2.length() <= 0) break;
                this.aEu.add(string.substring(n2));
                break;
            }
            string2 = string.substring(n2, n3);
            if (string2.length() > 0) {
                this.aEu.add(string2);
            }
            n2 = n3 + 1;
        }
    }

    public Object clone() {
        zf_0 zf_02 = new zf_0();
        zf_02.aEu.addAll(this.aEu);
        return zf_02;
    }

    public void push(String string) {
        this.aEu.add(string);
    }

    public int size() {
        return this.aEu.size();
    }

    public String get(int n2) {
        return (String)this.aEu.get(n2);
    }

    public void pop() {
        if (!this.aEu.isEmpty()) {
            this.aEu.remove(this.aEu.size() - 1);
        }
    }

    public String Ga() {
        if (!this.aEu.isEmpty()) {
            int n2 = this.aEu.size();
            return (String)this.aEu.get(n2 - 1);
        }
        return null;
    }

    public int a(zf_0 zf_02) {
        String string;
        String string2;
        if (zf_02 == null) {
            return 0;
        }
        int n2 = this.aEu.size();
        int n3 = zf_02.aEu.size();
        if (n2 == 0 || n3 == 0) {
            return 0;
        }
        int n4 = n2 <= n3 ? n2 : n3;
        int n5 = 0;
        for (int j = 1; j <= n4 && (string2 = (String)this.aEu.get(n2 - j)).equals(string = (String)zf_02.aEu.get(n3 - j)); ++j) {
            ++n5;
        }
        return n5;
    }

    public int b(zf_0 zf_02) {
        String string;
        String string2;
        if (zf_02 == null) {
            return 0;
        }
        int n2 = this.aEu.size();
        int n3 = zf_02.aEu.size();
        if (n2 == 0 || n3 == 0) {
            return 0;
        }
        int n4 = n2 <= n3 ? n2 : n3;
        int n5 = 0;
        for (int j = 0; j < n4 && (string2 = (String)this.aEu.get(j)).equals(string = (String)zf_02.aEu.get(j)); ++j) {
            ++n5;
        }
        return n5;
    }

    public boolean equals(Object object) {
        if (object == null || !(object instanceof zf_0)) {
            return false;
        }
        zf_0 zf_02 = (zf_0)object;
        if (zf_02.size() != this.size()) {
            return false;
        }
        int n2 = this.size();
        for (int j = 0; j < n2; ++j) {
            if (this.get(j).equals(zf_02.get(j))) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        int n2 = 0;
        int n3 = this.size();
        for (int j = 0; j < n3; ++j) {
            n2 ^= this.get(j).hashCode();
        }
        return n2;
    }

    public String toString() {
        int n2 = this.aEu.size();
        String string = "";
        for (int j = 0; j < n2; ++j) {
            string = string + "[" + (String)this.aEu.get(j) + "]";
        }
        return string;
    }
}

