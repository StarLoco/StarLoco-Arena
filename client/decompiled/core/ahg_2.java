/*
 * Decompiled with CFR 0.152.
 */
import java.util.zip.CRC32;

/*
 * Renamed from ahg
 */
public class ahg_2 {
    protected int asw;
    protected String m_name;
    private static final CRC32 amw = new CRC32();

    public ahg_2() {
    }

    public ahg_2(String string) {
        this.setName(string);
    }

    public final String getName() {
        return this.m_name;
    }

    public final void setName(String string) {
        this.m_name = string;
        amw.reset();
        amw.update(string.getBytes());
        this.asw = (int)amw.getValue();
    }

    public final int awT() {
        return this.asw;
    }
}

