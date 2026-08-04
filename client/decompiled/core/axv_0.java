/*
 * Decompiled with CFR 0.152.
 */
import java.util.EventObject;

/*
 * Renamed from axv
 */
public class axv_0
extends EventObject {
    private static final long serialVersionUID = 4538050075952288486L;
    private UI hL;
    private id_2 afp;
    private dm_1 afr;
    private String message;
    private int djv = 3;
    private Throwable exception;

    public axv_0(UI uI) {
        super(uI);
        this.hL = uI;
        this.afp = null;
        this.afr = null;
    }

    public axv_0(id_2 id_22) {
        super(id_22);
        this.hL = id_22.TP();
        this.afp = id_22;
        this.afr = null;
    }

    public axv_0(dm_1 dm_12) {
        super(dm_12);
        this.hL = dm_12.TP();
        this.afp = dm_12.LE();
        this.afr = dm_12;
    }

    public void D(String string, int n2) {
        this.message = string;
        this.djv = n2;
    }

    public void setException(Throwable throwable) {
        this.exception = throwable;
    }

    public UI TP() {
        return this.hL;
    }

    public id_2 aKd() {
        return this.afp;
    }

    public dm_1 adN() {
        return this.afr;
    }

    public String getMessage() {
        return this.message;
    }

    public int getPriority() {
        return this.djv;
    }

    public Throwable getException() {
        return this.exception;
    }
}

