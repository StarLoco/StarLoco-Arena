/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;

/*
 * Renamed from Ya
 */
public abstract class ya_2
extends mk {
    public final any_2 aU(String string) {
        File file = this.cx(string);
        if (file == null) {
            return null;
        }
        return new ayq_0(file);
    }

    protected abstract File cx(String var1);
}

