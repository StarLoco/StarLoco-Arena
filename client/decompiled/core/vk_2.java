/*
 * Decompiled with CFR 0.152.
 */
import java.util.zip.CRC32;

/*
 * Renamed from vk
 */
public class vk_2 {
    public String m_name;
    public int asw;
    private static CRC32 amw = new CRC32();

    public final void b(acf acf2) {
        this.m_name = acf2.readString();
        this.asw = acf2.readInt();
    }

    public final int getSize() {
        return 4 + this.m_name.length();
    }

    public void setName(String string) {
        this.m_name = string;
        amw.reset();
        amw.update(this.m_name.getBytes());
        this.asw = (int)amw.getValue();
    }
}

