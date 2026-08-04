/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class abe
extends ov_2 {
    abe(short s) {
        super(s);
    }

    private static ov_2 a(short s, DataInputStream dataInputStream) {
        return new abe(s);
    }

    protected void b(DataOutputStream dataOutputStream) {
    }

    static ov_2 g(short s, DataInputStream dataInputStream) {
        return abe.a(s, dataInputStream);
    }
}

