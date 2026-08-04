/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

/*
 * Renamed from wn
 */
public class wn_0
implements aqD {
    private File auq = null;
    private Properties aur = new Properties();
    private boolean aus = false;
    private boolean aut = true;

    public wn_0() {
    }

    public wn_0(File file) {
        this.auq = file;
    }

    public void m(File file) {
        this.auq = file;
    }

    public File CF() {
        return this.auq;
    }

    public boolean isValid() {
        return this.auq != null;
    }

    public void load() {
        if (this.auq != null && this.auq.isFile() && this.auq.canRead()) {
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(this.auq));
                this.aur.load(bufferedInputStream);
                bufferedInputStream.close();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        this.aus = true;
        this.aut = false;
    }

    public void save() {
        if (!this.aut) {
            return;
        }
        if (this.auq != null && this.aur.propertyNames().hasMoreElements()) {
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this.auq));
                this.aur.store(bufferedOutputStream, null);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        this.aut = false;
    }

    public void delete() {
        this.aur = new Properties();
        this.auq.delete();
        this.aus = true;
        this.aut = false;
    }

    public Object get(Object object) {
        if (!this.aus) {
            this.load();
        }
        try {
            return this.aur.getProperty(String.valueOf(object));
        }
        catch (ClassCastException classCastException) {
            return null;
        }
    }

    public void put(Object object, Object object2) {
        this.aur.put(String.valueOf(object), String.valueOf(object2));
        this.aut = true;
    }

    public Iterator iterator() {
        Vector vector = new Vector();
        Enumeration<?> enumeration = this.aur.propertyNames();
        while (enumeration.hasMoreElements()) {
            vector.add(enumeration.nextElement());
        }
        return vector.iterator();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<PropertiesfileCache:");
        stringBuffer.append("cachefile=").append(this.auq);
        stringBuffer.append(";noOfEntries=").append(this.aur.size());
        stringBuffer.append(">");
        return stringBuffer.toString();
    }
}

