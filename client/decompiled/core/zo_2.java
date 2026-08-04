/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from Zo
 */
public class zo_2
implements k_0 {
    private String m_name;
    private String ayx;

    public zo_2(String string, String string2) {
        this.m_name = string != null ? string.intern() : null;
        this.ayx = string2 != null ? string2.intern() : null;
    }

    public int getId() {
        return 0;
    }

    public void f(int n2) {
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String string) {
        this.m_name = string != null ? string.intern() : null;
    }

    public String getStringValue() {
        return this.ayx;
    }

    public boolean getBooleanValue() {
        return Boolean.parseBoolean(this.ayx);
    }

    public byte aj() {
        return Byte.parseByte(this.ayx);
    }

    public short ak() {
        return Short.parseShort(this.ayx);
    }

    public int getIntValue() {
        return Integer.parseInt(this.ayx);
    }

    public long getLongValue() {
        return Long.parseLong(this.ayx);
    }

    public float getFloatValue() {
        return Float.parseFloat(this.ayx);
    }

    public double getDoubleValue() {
        return Double.parseDouble(this.ayx);
    }

    public void b(String string) {
        this.ayx = string != null ? string.intern() : null;
    }

    public void b(boolean bl2) {
        this.ayx = bl2 ? "true" : "false";
    }

    public void a(byte by) {
        this.ayx = ("" + by).intern();
    }

    public void g(int n2) {
        this.ayx = ("" + n2).intern();
    }

    public void e(long l2) {
        this.ayx = ("" + l2).intern();
    }

    public void c(float f) {
        this.ayx = ("" + f).intern();
    }

    public void a(double d) {
        this.ayx = ("" + d).intern();
    }

    public void a(k_0 k_02) {
    }

    public void b(k_0 k_02) {
    }

    public ArrayList getChildren() {
        return null;
    }

    public k_0 c(String string) {
        return null;
    }

    public ArrayList d(String string) {
        return null;
    }

    public ArrayList e(String string) {
        return null;
    }

    public k_0 f(String string) {
        return null;
    }

    public void c(k_0 k_02) {
    }

    public void d(k_0 k_02) {
    }

    public ArrayList al() {
        return null;
    }

    public String toString() {
        return this.m_name + "=" + this.ayx;
    }
}

