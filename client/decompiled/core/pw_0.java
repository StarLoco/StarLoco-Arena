/*
 * Decompiled with CFR 0.152.
 */
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Renamed from Pw
 */
public class pw_0
extends ParseException {
    private static final String bDU = "Erreur au parse d'une date";
    private static final String bDV = awZ.diD.getChar() + "[" + awZ.diM + "]";
    private static final String bDW = awZ.diD.getChar() + "[^" + awZ.diM + "]";
    private final String bDX;
    private final String bDY;

    public pw_0(String string, String string2) {
        super(bDU, -1);
        this.bDX = string;
        this.bDY = string2;
    }

    public String getFormat() {
        return this.bDX;
    }

    public String ace() {
        return this.bDY;
    }

    public String getMessage() {
        Matcher matcher = Pattern.compile(bDW).matcher(this.bDX);
        if (matcher.find()) {
            return "Variable " + matcher.group() + " inconnue dans le FORMAT [ " + this.bDX + " ]";
        }
        matcher = Pattern.compile(bDV + bDV).matcher(this.bDX);
        if (matcher.find()) {
            return "Variables " + matcher.group() + " sans s\u00e9parateur dans le FORMAT [ " + this.bDX + " ]";
        }
        if (!Pattern.compile(rc_1.bG(this.bDX)).matcher(this.bDY).matches()) {
            return "DATE [" + this.bDY + "] incompatible avec le FORMAT [ " + this.bDX + " ]";
        }
        return "Parse impossible pour le FORMAT [ " + this.bDX + " ] et la DATE [ " + this.bDY + " ]";
    }
}

