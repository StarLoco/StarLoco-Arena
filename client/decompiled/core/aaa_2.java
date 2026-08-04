/*
 * Decompiled with CFR 0.152.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/*
 * Renamed from aAA
 */
public class aaa_2
extends ii_2
implements mt_2 {
    private String dpn;
    private String name;
    private boolean caseSensitive = true;
    private boolean dpo = false;
    private boolean dpp = false;
    private boolean amF = false;
    private Pattern dpq;

    public String aMM() {
        return this.dpn;
    }

    public void kf(String string) {
        this.dpn = string;
    }

    public void start() {
        if (this.name == null) {
            this.eg("All Matcher objects must be named");
            return;
        }
        try {
            int n2 = 0;
            if (!this.caseSensitive) {
                n2 |= 2;
            }
            if (this.dpo) {
                n2 |= 0x80;
            }
            if (this.dpp) {
                n2 |= 0x40;
            }
            this.dpq = Pattern.compile(this.dpn, n2);
            this.amF = true;
        }
        catch (PatternSyntaxException patternSyntaxException) {
            this.e("Failed to compile regex [" + this.dpn + "]", patternSyntaxException);
        }
    }

    public void stop() {
        this.amF = false;
    }

    public boolean isStarted() {
        return this.amF;
    }

    public boolean matches(String string) {
        if (this.amF) {
            Matcher matcher = this.dpq.matcher(string);
            return matcher.find();
        }
        throw new Gp("Matcher [" + this.dpn + "] not started");
    }

    public boolean aMN() {
        return this.dpo;
    }

    public void eD(boolean bl2) {
        this.dpo = bl2;
    }

    public boolean aHy() {
        return this.caseSensitive;
    }

    public void setCaseSensitive(boolean bl2) {
        this.caseSensitive = bl2;
    }

    public boolean aMO() {
        return this.dpp;
    }

    public void eE(boolean bl2) {
        this.dpp = bl2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }
}

