/*
 * Decompiled with CFR 0.152.
 */
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * Renamed from kh
 */
public class kh_0
implements bc_1 {
    private final Bk ub = LD.p(this.getClass());
    public static final boolean DN = true;
    private final DataInput DO;
    private final DataOutput DP;
    private final boolean DQ;
    private Object DR = null;

    public kh_0(DataInput dataInput) {
        this(dataInput, null, true);
    }

    public kh_0(DataOutput dataOutput) {
        this(null, dataOutput, true);
    }

    public kh_0(RandomAccessFile randomAccessFile) {
        this(randomAccessFile, randomAccessFile, true);
    }

    public kh_0(DataInput dataInput, DataOutput dataOutput, boolean bl2) {
        if (dataInput == null && dataOutput == null) {
            throw new IllegalArgumentException("must pass one non null stream");
        }
        this.DO = dataInput;
        this.DP = dataOutput;
        this.DQ = bl2;
    }

    public int de() {
        int n2 = 0;
        try {
            if (this.DR != null && this.DQ && this.DR instanceof Closeable) {
                ((Closeable)this.DR).close();
            }
        }
        catch (IOException iOException) {
            this.ub.e("could not close stream {}: {}", this.DR, (Object)iOException);
            n2 = -1;
        }
        this.DR = null;
        return n2;
    }

    public int d(String string, int n2) {
        if (this.DR != null) {
            this.ub.j("attempting to open already open handler: {}", this.DR);
            return -1;
        }
        switch (n2) {
            case 2: {
                if (this.DO != null && this.DP != null && this.DO == this.DP && this.DO instanceof RandomAccessFile) {
                    this.DR = this.DO;
                    break;
                }
                this.ub.debug("do not support read/write mode for Java IO Handlers");
                return -1;
            }
            case 1: {
                this.DR = this.DP;
                if (this.DR != null) break;
                this.ub.m("No OutputStream specified for writing: {}", string);
                return -1;
            }
            case 0: {
                this.DR = this.DO;
                if (this.DR != null) break;
                this.ub.m("No InputStream specified for reading: {}", string);
                return -1;
            }
            default: {
                this.ub.m("Invalid flag passed to open: {}", string);
                return -1;
            }
        }
        return 0;
    }

    public int a(byte[] byArray, int n2) {
        int n3 = -1;
        if (this.DR == null || !(this.DR instanceof DataInput)) {
            return -1;
        }
        try {
            if (this.DR instanceof RandomAccessFile) {
                RandomAccessFile randomAccessFile = (RandomAccessFile)this.DR;
                return randomAccessFile.read(byArray, 0, n2);
            }
            if (this.DR instanceof DataInputStream) {
                DataInputStream dataInputStream = (DataInputStream)this.DR;
                return dataInputStream.read(byArray, 0, n2);
            }
            DataInput dataInput = (DataInput)this.DR;
            try {
                dataInput.readFully(byArray, 0, n2);
                n3 = n2;
            }
            catch (EOFException eOFException) {
                n3 = -1;
            }
            return n3;
        }
        catch (IOException iOException) {
            this.ub.e("Got IO exception reading from channel: {}; {}", this.DR, (Object)iOException);
            return -1;
        }
    }

    public long a(long l2, int n2) {
        if (this.DR == null) {
            return -1L;
        }
        if (!(this.DR instanceof RandomAccessFile)) {
            return -1L;
        }
        RandomAccessFile randomAccessFile = (RandomAccessFile)this.DR;
        try {
            long l3;
            if (n2 == 0) {
                l3 = l2;
            } else if (n2 == 1) {
                l3 = randomAccessFile.getFilePointer() + l2;
            } else if (n2 == 2) {
                l3 = randomAccessFile.length() + l2;
            } else {
                if (n2 == 65536) {
                    return (int)randomAccessFile.length();
                }
                this.ub.e("invalid seek value \"{}\" for file: {}", n2, (Object)randomAccessFile);
                return -1L;
            }
            randomAccessFile.seek(l3);
            return l3;
        }
        catch (IOException iOException) {
            this.ub.b("got io exception \"{}\" while seeking in: {}", (Object)iOException.getMessage(), (Object)randomAccessFile);
            return -1L;
        }
    }

    public int b(byte[] byArray, int n2) {
        if (this.DR == null || !(this.DR instanceof DataOutput)) {
            return -1;
        }
        try {
            DataOutput dataOutput = (DataOutput)this.DR;
            dataOutput.write(byArray, 0, n2);
            return n2;
        }
        catch (IOException iOException) {
            this.ub.e("Got error writing to file: {}; {}", this.DR, (Object)iOException);
            return -1;
        }
    }

    public boolean e(String string, int n2) {
        if (this.DO != null && this.DO instanceof RandomAccessFile) {
            return false;
        }
        return this.DP == null || !(this.DP instanceof RandomAccessFile);
    }

    public DataInput oJ() {
        return this.DO;
    }

    public DataOutput oK() {
        return this.DP;
    }

    public Object oL() {
        return this.DR;
    }

    public boolean oM() {
        return this.DQ;
    }
}

