/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/*
 * Renamed from oi
 */
public class oi_2
extends ahT {
    private boolean immediateFlush = true;
    private String encoding;
    private Writer writer;
    private ei_2 Uq;

    public void setImmediateFlush(boolean bl2) {
        this.immediateFlush = bl2;
    }

    public boolean getImmediateFlush() {
        return this.immediateFlush;
    }

    public void start() {
        int n2 = 0;
        if (this.Uq == null) {
            this.b(new aIX("No layout set for the appender named \"" + this.name + "\".", this));
            ++n2;
        }
        if (this.writer == null) {
            this.b(new aIX("No writer set for the appender named \"" + this.name + "\".", this));
            ++n2;
        }
        if (n2 == 0) {
            super.start();
        }
    }

    protected void z(Object object) {
        if (!this.isStarted()) {
            return;
        }
        this.A(object);
    }

    public synchronized void stop() {
        this.closeWriter();
        super.stop();
    }

    protected void closeWriter() {
        if (this.writer != null) {
            try {
                this.writeFooter();
                this.writer.close();
                this.writer = null;
            }
            catch (IOException iOException) {
                this.b(new aIX("Could not close writer for WriterAppener.", this, iOException));
            }
        }
    }

    protected OutputStreamWriter createWriter(OutputStream outputStream) {
        OutputStreamWriter outputStreamWriter;
        block2: {
            outputStreamWriter = null;
            String string = this.getEncoding();
            try {
                outputStreamWriter = string != null ? new OutputStreamWriter(outputStream, string) : new OutputStreamWriter(outputStream);
            }
            catch (IOException iOException) {
                this.b(new aIX("Error initializing output writer.", this, iOException));
                if (string == null) break block2;
                this.b(new aIX("Unsupported encoding?", this));
            }
        }
        return outputStreamWriter;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String string) {
        this.encoding = string;
    }

    public void a(ei_2 ei_22) {
        this.Uq = ei_22;
    }

    public ei_2 ti() {
        return this.Uq;
    }

    void writeHeader() {
        if (this.Uq != null && this.writer != null) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                this.b(stringBuilder, this.Uq.hf());
                this.b(stringBuilder, this.Uq.hg());
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(kJ.sy);
                    this.c(stringBuilder.toString(), true);
                }
            }
            catch (IOException iOException) {
                this.bgs = false;
                this.b(new aIX("Failed to write header for appender named [" + this.name + "].", this, iOException));
            }
        }
    }

    private void b(StringBuilder stringBuilder, String string) {
        if (string != null) {
            stringBuilder.append(string);
        }
    }

    void writeFooter() {
        if (this.Uq != null && this.writer != null) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                this.b(stringBuilder, this.Uq.hh());
                this.b(stringBuilder, this.Uq.hi());
                if (stringBuilder.length() > 0) {
                    this.c(stringBuilder.toString(), true);
                }
            }
            catch (IOException iOException) {
                this.bgs = false;
                this.b(new aIX("Failed to write footer for appender named [" + this.name + "].", this, iOException));
            }
        }
    }

    public synchronized void setWriter(Writer writer) {
        this.closeWriter();
        this.writer = writer;
        this.writeHeader();
    }

    protected void c(String string, boolean bl2) {
        this.writer.write(string);
        if (bl2) {
            this.writer.flush();
        }
    }

    protected void A(Object object) {
        if (!this.isStarted()) {
            return;
        }
        try {
            this.c(this.Uq.e(object), this.immediateFlush);
        }
        catch (IOException iOException) {
            this.bgs = false;
            this.b(new aIX("IO failure in appender", this, iOException));
        }
    }
}

