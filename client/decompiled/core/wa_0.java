/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/*
 * Renamed from Wa
 */
public class wa_0 {
    public static final String bTL = "MRU";
    private final HashSet bTM = new HashSet();
    private final Stack bTN = new Stack();
    private final ArrayList G = new ArrayList();

    public void gx(String string) {
        this.bTM.add(string);
    }

    public void gy(String string) {
        this.bTM.remove(string);
    }

    public void gz(String string) {
        this.gy(string);
        this.bTN.remove(string);
    }

    public void a(lm_1 lm_12) {
        if (lm_12 != null) {
            this.G.add(lm_12);
        }
    }

    public void b(lm_1 lm_12) {
        if (lm_12 != null) {
            this.G.remove(lm_12);
        }
    }

    public void q(String string, boolean bl2) {
        if (string != null) {
            if (!this.bTN.contains(string)) {
                this.bTN.push(string);
            } else if (bl2) {
                this.bTN.remove(string);
                this.bTN.push(string);
            }
        }
    }

    public int size() {
        return this.bTN.size();
    }

    public int k() {
        while (this.bTN.size() > 0) {
            String string = (String)this.bTN.pop();
            if (string == null || !add_1.aOG().kR(string)) continue;
            block4: for (int j = this.G.size() - 1; j >= 0; --j) {
                int n2 = ((lm_1)this.G.get(j)).aR(string);
                switch (n2) {
                    case 0: {
                        continue block4;
                    }
                    default: {
                        this.bTN.push(string);
                        return n2;
                    }
                }
            }
            if (this.gA(string)) continue;
            add_1.aOG().kO(string);
            return 0;
        }
        return 1;
    }

    public boolean gA(String string) {
        return string.startsWith(bTL) || this.bTM.contains(string);
    }
}

