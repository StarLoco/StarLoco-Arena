/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public abstract class jz
implements Iterable {
    private final ArrayList AI = new ArrayList();
    protected int AJ = Integer.MAX_VALUE;
    private int AK = 0;
    protected Pattern AL = null;
    protected boolean AM = false;
    protected boolean AN = false;
    private int AO = -1;
    private int AP = 0;
    private int AQ = -1;
    private int AR = 0;
    private af_1 AS;
    private boolean AT = false;

    public void mq() {
        adv_0 adv_02 = new adv_0(this, null, false);
        adv_02.setText("");
        this.a(adv_02);
    }

    protected void a(yb_0 yb_02) {
        this.AI.add(yb_02);
        this.AK += yb_02.Fj();
    }

    protected void a(int n2, yb_0 yb_02) {
        this.AI.add(n2, yb_02);
    }

    public void b(yb_0 yb_02) {
        this.AI.remove(yb_02);
        this.AK -= yb_02.Fj();
    }

    public yb_0 ba(int n2) {
        try {
            return (yb_0)this.AI.get(n2);
        }
        catch (Exception exception) {
            return null;
        }
    }

    protected void mr() {
        this.AI.clear();
        this.AK = 0;
    }

    public int ms() {
        return this.AK;
    }

    public int getMaxCharacters() {
        return this.AJ;
    }

    public void setMaxCharacters(int n2) {
        this.AJ = n2;
    }

    public Pattern mt() {
        return this.AL;
    }

    public String getRestrict() {
        if (this.AL != null) {
            return this.AL.pattern();
        }
        return null;
    }

    public void setRestrict(String string) {
        this.AL = string != null ? (this.AM ? Pattern.compile(string, 64) : Pattern.compile(string)) : null;
    }

    public void setUnicodeRestrict(boolean bl2) {
        if (bl2 != this.AM) {
            this.AM = bl2;
            if (this.AL != null) {
                this.setRestrict(this.AL.pattern());
            }
        }
    }

    public boolean mu() {
        return this.AN;
    }

    public void setPassword(boolean bl2) {
        this.AN = bl2;
    }

    public boolean mv() {
        return this.AM;
    }

    public boolean isEmpty() {
        return this.AI.isEmpty();
    }

    public yb_0 mw() {
        if (!this.AI.isEmpty()) {
            return (yb_0)this.AI.get(0);
        }
        return null;
    }

    public yb_0 mx() {
        if (!this.AI.isEmpty()) {
            return (yb_0)this.AI.get(this.AI.size() - 1);
        }
        return null;
    }

    public yb_0 my() {
        int n2 = this.mz();
        if (n2 != -1) {
            return this.ba(n2);
        }
        return null;
    }

    private int mz() {
        if (this.mL()) {
            return this.AQ;
        }
        return this.AO;
    }

    public int mA() {
        if (this.mL()) {
            return this.AR;
        }
        return this.AP;
    }

    public boolean a(yb_0 yb_02, int n2) {
        boolean bl2 = false;
        int n3 = this.AI.indexOf(yb_02);
        if (n3 != this.AO || this.AP != n2) {
            bl2 = true;
        }
        this.AO = n3;
        this.AP = n2;
        return bl2;
    }

    public boolean mB() {
        boolean bl2 = false;
        if (this.AQ != this.AO || this.AP != this.AR) {
            bl2 = true;
        }
        this.AO = this.AQ;
        this.AP = this.AR;
        return bl2;
    }

    public yb_0 mC() {
        int n2 = this.mD();
        if (n2 != -1) {
            return this.ba(n2);
        }
        return null;
    }

    private int mD() {
        if (this.mL()) {
            return this.AO;
        }
        return this.AQ;
    }

    public int mE() {
        if (this.mL()) {
            return this.AP;
        }
        return this.AR;
    }

    public boolean bb(int n2) {
        boolean bl2 = n2 != this.AR;
        this.AR = n2;
        return bl2;
    }

    public boolean bc(int n2) {
        boolean bl2 = n2 != this.AP;
        this.AP = n2;
        return bl2;
    }

    public boolean b(yb_0 yb_02, int n2) {
        int n3 = this.AI.indexOf(yb_02);
        boolean bl2 = n3 != this.AQ || this.AR != n2;
        this.AQ = n3;
        this.AR = n2;
        return bl2;
    }

    public boolean mF() {
        boolean bl2 = this.AQ != this.AO || this.AR != this.AP;
        this.AQ = this.AO;
        this.AR = this.AP;
        return bl2;
    }

    public boolean mG() {
        return this.AO != -1 && this.AQ != -1;
    }

    public boolean isSelectionEmpty() {
        return this.AO == this.AQ && this.AP == this.AR;
    }

    public String getSelectedText() {
        if (this.mG() && !this.isSelectionEmpty()) {
            int n2 = this.mz();
            int n3 = this.mD();
            int n4 = this.mA();
            int n5 = this.mE();
            if (n2 == n3) {
                yb_0 yb_02 = this.my();
                if (yb_02 != null && yb_02.Fg() == di_0.ms) {
                    String string = ((adv_0)yb_02).att();
                    return string.substring(n4, n5);
                }
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = n2; j <= n3; ++j) {
                    yb_0 yb_03 = this.ba(j);
                    if (yb_03.Fg() != di_0.ms) continue;
                    String string = ((adv_0)yb_03).att();
                    if (j == n2) {
                        stringBuilder.append(string.substring(n4));
                        continue;
                    }
                    if (j == n3) {
                        stringBuilder.append(string.substring(0, n5));
                        continue;
                    }
                    stringBuilder.append(string);
                }
                return stringBuilder.toString();
            }
        }
        return null;
    }

    public void a(af_1 af_12) {
        this.AS = af_12;
    }

    public af_1 mH() {
        return this.AS;
    }

    public boolean mI() {
        return this.AT;
    }

    public void setUseHighContrast(boolean bl2) {
        this.AT = bl2;
    }

    public abstract String mJ();

    public abstract void aD(String var1);

    public abstract void aE(String var1);

    public Iterator iterator() {
        return this.AI.iterator();
    }

    public boolean mK() {
        boolean bl2 = this.AQ != -1 || this.AO != -1 || this.AR != 0 || this.AP != 0;
        this.AO = -1;
        this.AP = 0;
        this.AQ = -1;
        this.AR = 0;
        return bl2;
    }

    private boolean mL() {
        if (this.mG()) {
            if (this.AQ < this.AO) {
                return true;
            }
            if (this.AO == this.AQ && this.AR < this.AP) {
                return true;
            }
        }
        return false;
    }

    public void aF(String string) {
        block7: {
            yb_0 yb_02;
            boolean bl2;
            int n2;
            int n3;
            int n4;
            yb_0 yb_03;
            block9: {
                int n5;
                int n6;
                block8: {
                    if (!this.mG()) break block7;
                    yb_03 = this.my();
                    n4 = this.mz();
                    n6 = this.mD();
                    n3 = this.mA();
                    n5 = this.mE();
                    n2 = 0;
                    bl2 = false;
                    if (n4 != n6) break block8;
                    yb_02 = this.my();
                    if (yb_02 == null) break block9;
                    n2 = n5 - n3;
                    if (!yb_02.ae(n3, n5)) break block9;
                    this.b(yb_02);
                    bl2 = true;
                    break block9;
                }
                for (int j = n6; j >= n4; --j) {
                    yb_0 yb_04 = this.ba(j);
                    if (j == n4) {
                        n2 += yb_04.Fj() - 1 - n3;
                        if (!yb_04.ex(n3)) continue;
                        this.b(yb_04);
                        bl2 = true;
                        continue;
                    }
                    if (j == n6) {
                        n2 += n5 + 1;
                        if (!yb_04.ey(n5)) continue;
                        this.b(yb_04);
                        continue;
                    }
                    n2 += yb_04.Fj();
                    this.b(yb_04);
                }
            }
            this.AK -= n2;
            yb_02 = null;
            if (yb_03.Fg() == di_0.ms) {
                yb_02 = (adv_0)yb_03;
            } else {
                yb_02 = new adv_0(this, null, false);
                bl2 = true;
            }
            int n7 = ((adv_0)yb_02).s(string, n3);
            if (bl2) {
                this.a(n4, yb_02);
            }
            this.AK += n7;
            if (this.AK > this.AJ) {
                int n8 = this.AK - this.AJ;
                ((adv_0)yb_02).ae(n3, n3 + n8);
                n7 -= n8;
                this.AK -= n8;
            }
            this.a(yb_02, n3);
            this.mF();
            this.bd(n7);
            this.mB();
        }
    }

    public void mM() {
        if (this.isSelectionEmpty()) {
            this.mP();
        }
        this.aF("");
    }

    public void mN() {
        if (this.isSelectionEmpty()) {
            this.mO();
        }
        this.aF("");
    }

    public boolean bd(int n2) {
        boolean bl2 = false;
        for (int j = 0; j < n2; ++j) {
            bl2 |= this.mO();
        }
        return bl2;
    }

    public boolean mO() {
        yb_0 yb_02 = this.ba(this.AQ);
        if (yb_02 != null && (yb_02.Fj() > this.AR + 1 || yb_02 == this.mx() && yb_02.Fj() >= this.AR + 1)) {
            ++this.AR;
            return true;
        }
        if (this.AQ + 1 <= this.AI.size() - 1) {
            ++this.AQ;
            this.AR = 0;
            return true;
        }
        return false;
    }

    public boolean be(int n2) {
        boolean bl2 = false;
        for (int j = 0; j < n2; ++j) {
            bl2 |= this.mP();
        }
        return bl2;
    }

    public boolean mP() {
        yb_0 yb_02 = this.ba(this.AQ);
        if (yb_02 != null && this.AR - 1 >= 0) {
            --this.AR;
            return true;
        }
        if (this.AQ - 1 >= 0) {
            --this.AQ;
            this.AR = this.ba(this.AQ).Fj() - 1;
            return true;
        }
        return false;
    }
}

