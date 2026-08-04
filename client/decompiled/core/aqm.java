/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public interface aqm {
    public static final int cNT = 0;
    public static final int cNU = 256;
    public static final int MATCH_MULTILINE = 4096;
    public static final int MATCH_SINGLELINE = 65536;

    public void setPattern(String var1);

    public String getPattern();

    public boolean matches(String var1);

    public Vector jb(String var1);

    public boolean A(String var1, int var2);

    public Vector B(String var1, int var2);
}

