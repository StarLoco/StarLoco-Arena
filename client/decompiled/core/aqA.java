/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.StringTokenizer;

public class aqA {
    private StringTokenizer cOo;
    private String cOp = null;
    private boolean cOq = xk_1.cO("netware");
    private boolean cOr;

    public aqA(String string) {
        this.cOo = this.cOq ? new StringTokenizer(string, ":;", true) : new StringTokenizer(string, ":;", false);
        this.cOr = File.pathSeparatorChar == ';';
    }

    public boolean hasMoreTokens() {
        if (this.cOp != null) {
            return true;
        }
        return this.cOo.hasMoreTokens();
    }

    public String nextToken() {
        String string = null;
        if (this.cOp != null) {
            string = this.cOp;
            this.cOp = null;
        } else {
            string = this.cOo.nextToken().trim();
        }
        if (!this.cOq) {
            if (string.length() == 1 && Character.isLetter(string.charAt(0)) && this.cOr && this.cOo.hasMoreTokens()) {
                String string2 = this.cOo.nextToken().trim();
                if (string2.startsWith("\\") || string2.startsWith("/")) {
                    string = string + ":" + string2;
                } else {
                    this.cOp = string2;
                }
            }
        } else {
            String string3;
            if (string.equals(File.pathSeparator) || string.equals(":")) {
                string = this.cOo.nextToken().trim();
            }
            if (this.cOo.hasMoreTokens() && !(string3 = this.cOo.nextToken().trim()).equals(File.pathSeparator)) {
                if (string3.equals(":")) {
                    if (!(string.startsWith("/") || string.startsWith("\\") || string.startsWith(".") || string.startsWith(".."))) {
                        String string4 = this.cOo.nextToken().trim();
                        if (!string4.equals(File.pathSeparator)) {
                            string = string + ":" + string4;
                        } else {
                            string = string + ":";
                            this.cOp = string4;
                        }
                    }
                } else {
                    this.cOp = string3;
                }
            }
        }
        return string;
    }
}

