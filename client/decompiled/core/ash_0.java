/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/*
 * Renamed from ash
 */
public class ash_0
extends iv_1
implements qO {
    private static final ga_2 xa = ga_2.Qo();
    private static final int cRd = iv_1.g("null file".getBytes());
    private File ll;
    private File bQO;

    public ash_0() {
    }

    public ash_0(File file, String string) {
        this.e(xa.d(file, string));
        this.A(file);
    }

    public ash_0(File file) {
        this.e(file);
    }

    public ash_0(UI uI, String string) {
        this(uI.gg(string));
        this.l(uI);
    }

    public void e(File file) {
        this.aIl();
        this.ll = file;
    }

    public File getFile() {
        return this.aId() ? ((ash_0)this.aIg()).getFile() : this.ll;
    }

    public void A(File file) {
        this.aIl();
        this.bQO = file;
    }

    public File ahg() {
        return this.aId() ? ((ash_0)this.aIg()).ahg() : this.bQO;
    }

    public void a(awq_0 awq_02) {
        if (this.ll != null || this.bQO != null) {
            throw this.aIh();
        }
        super.a(awq_02);
    }

    public String getName() {
        if (this.aId()) {
            return ((iv_1)this.aIg()).getName();
        }
        File file = this.ahg();
        return file == null ? this.aFe().getName() : xa.c(file, this.aFe());
    }

    public boolean lI() {
        return this.aId() ? ((iv_1)this.aIg()).lI() : this.aFe().exists();
    }

    public long getLastModified() {
        return this.aId() ? ((iv_1)this.aIg()).getLastModified() : this.aFe().lastModified();
    }

    public boolean isDirectory() {
        return this.aId() ? ((iv_1)this.aIg()).isDirectory() : this.aFe().isDirectory();
    }

    public long getSize() {
        return this.aId() ? ((iv_1)this.aIg()).getSize() : this.aFe().length();
    }

    public InputStream getInputStream() {
        return this.aId() ? ((iv_1)this.aIg()).getInputStream() : new FileInputStream(this.aFe());
    }

    public OutputStream getOutputStream() {
        if (this.aId()) {
            return ((iv_1)this.aIg()).getOutputStream();
        }
        File file = this.aFe();
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            }
        } else {
            File file2 = file.getParentFile();
            if (file2 != null && !file2.exists()) {
                file2.mkdirs();
            }
        }
        return new FileOutputStream(file);
    }

    public int compareTo(Object object) {
        if (this.aId()) {
            return ((Comparable)this.aIg()).compareTo(object);
        }
        if (this.equals(object)) {
            return 0;
        }
        if (object.getClass().equals(this.getClass())) {
            ash_0 ash_02 = (ash_0)object;
            File file = this.getFile();
            if (file == null) {
                return -1;
            }
            File file2 = ash_02.getFile();
            if (file2 == null) {
                return 1;
            }
            return file.compareTo(file2);
        }
        return super.compareTo(object);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (this.aId()) {
            return this.aIg().equals(object);
        }
        if (!object.getClass().equals(this.getClass())) {
            return false;
        }
        ash_0 ash_02 = (ash_0)object;
        return this.getFile() == null ? ash_02.getFile() == null : this.getFile().equals(ash_02.getFile());
    }

    public int hashCode() {
        if (this.aId()) {
            return this.aIg().hashCode();
        }
        return zd * (this.getFile() == null ? cRd : this.getFile().hashCode());
    }

    public String toString() {
        if (this.aId()) {
            return this.aIg().toString();
        }
        if (this.ll == null) {
            return "(unbound file resource)";
        }
        String string = this.ll.getAbsolutePath();
        return xa.dZ(string).getAbsolutePath();
    }

    public boolean dE() {
        return !this.aId() || ((ash_0)this.aIg()).dE();
    }

    public void aC(long l2) {
        if (this.aId()) {
            ((ash_0)this.aIg()).aC(l2);
            return;
        }
        this.aFe().setLastModified(l2);
    }

    protected File aFe() {
        if (this.getFile() == null) {
            throw new eq_2("file attribute is null!");
        }
        return this.getFile();
    }
}

