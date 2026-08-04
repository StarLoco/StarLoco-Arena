/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * Renamed from gz
 */
public class gz_2
implements bc_1 {
    File ll = null;
    RandomAccessFile ua = null;
    private final Bk ub = LD.p(this.getClass());

    public gz_2() {
        this.ub.debug("Initializing file protocol handler without file");
        this.ll = null;
    }

    public gz_2(File file) {
        this.ub.j("Initializing file protocol handler: {}", file);
        this.ll = file;
    }

    public gz_2(String string) {
        this.ub.j("Initializing file protocol handler: {}", string);
        string = this.aq(string);
        this.ll = string != null ? new File(string) : null;
    }

    public int de() {
        this.ub.j("Closing file: {}", this.ll);
        try {
            this.ua.close();
        }
        catch (IOException iOException) {
            this.ub.m("Error closing file: {}", this.ll);
            iOException.printStackTrace();
            return -1;
        }
        this.ub.j("Succesfully closed file: {}", this.ll);
        return 0;
    }

    public int d(String string, int n2) {
        String string2;
        int n3 = -1;
        this.ub.b("attempting to open {} with flags {}", string == null ? this.ll : string, (Object)n2);
        if (this.ua != null) {
            this.de();
        }
        if (this.ll == null && (string = this.aq(string)) != null) {
            this.ll = new File(string);
        }
        this.ub.j("Opening file: {}", this.ll);
        switch (n2) {
            case 2: {
                string2 = "rw";
                break;
            }
            case 1: {
                string2 = "rw";
                break;
            }
            case 0: {
                string2 = "r";
                break;
            }
            default: {
                this.ub.m("Invalid flag passed to open: {}", this.ll);
                return n3;
            }
        }
        this.ub.b("read mode \"{}\" for file: {}", (Object)string2, (Object)this.ll);
        try {
            this.ua = new RandomAccessFile(this.ll, string2);
            n3 = 0;
        }
        catch (Exception exception) {
            this.ub.e("Could not find file: {}; ex: {}", this.ll, (Object)exception);
            return n3;
        }
        this.ub.j("Opened file: {}", this.ll);
        return n3;
    }

    public int a(byte[] byArray, int n2) {
        try {
            int n3 = -1;
            n3 = this.ua.read(byArray, 0, n2);
            return n3;
        }
        catch (IOException iOException) {
            this.ub.m("Got IO exception reading from file: {}", this.ll);
            iOException.printStackTrace();
            return -1;
        }
    }

    public long a(long l2, int n2) {
        try {
            long l3;
            if (n2 == 0) {
                l3 = l2;
            } else if (n2 == 1) {
                l3 = this.ua.getFilePointer() + l2;
            } else if (n2 == 2) {
                l3 = this.ua.length() + l2;
            } else {
                if (n2 == 65536) {
                    return (int)this.ua.length();
                }
                this.ub.e("invalid seek value \"{}\" for file: {}", n2, (Object)this.ll);
                return -1L;
            }
            this.ua.seek(l3);
            this.ub.b("seeking to \"{}\" in: {}", l3, (Object)this.ll);
            return l3;
        }
        catch (IOException iOException) {
            this.ub.e("got io exception \"{}\" while seeking in: {}", (Object)iOException.getMessage(), (Object)this.ll);
            iOException.printStackTrace();
            return -1L;
        }
    }

    public int b(byte[] byArray, int n2) {
        try {
            this.ua.write(byArray, 0, n2);
            return n2;
        }
        catch (IOException iOException) {
            this.ub.m("Got error writing to file: {}", this.ll);
            iOException.printStackTrace();
            return -1;
        }
    }

    private String aq(String string) {
        int n2;
        String string2 = string;
        if (string != null && string.length() > 0 && (n2 = string.indexOf(":")) > 0) {
            string2 = string.substring(n2 + 1);
        }
        this.ub.b("url->filename: {}->{}", (Object)string, (Object)string2);
        return string2;
    }

    public boolean e(String string, int n2) {
        return false;
    }
}

