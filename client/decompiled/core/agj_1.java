/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Dimension;
import org.apache.log4j.Logger;

/*
 * Renamed from agj
 */
public class agj_1
extends Dimension
implements Cloneable {
    private static Logger a = Logger.getLogger(agj_1.class);
    private float ctM = -1.0f;
    private float ctN = -1.0f;

    public agj_1() {
    }

    public agj_1(int n2, int n3) {
        this.width = n2;
        this.height = n3;
    }

    public agj_1(agj_1 agj_12) {
        this.width = agj_12.width;
        this.height = agj_12.height;
        this.ctM = agj_12.ctM;
        this.ctN = agj_12.ctN;
    }

    public agj_1(float f, float f2) {
        this.ctM = f;
        this.ctN = f2;
    }

    public agj_1(int n2, float f) {
        this.width = n2;
        this.ctN = f;
    }

    public agj_1(float f, int n2) {
        this.ctM = f;
        this.height = n2;
    }

    public float awi() {
        return this.ctM;
    }

    public float awj() {
        return this.ctN;
    }

    public void bp(int n2, int n3) {
        this.width = n2;
        this.height = n3;
    }

    public void M(float f, float f2) {
        this.ctN = f2;
        this.ctM = f;
    }

    public void setHeight(int n2) {
        this.height = n2;
    }

    public void aU(float f) {
        this.ctN = f;
    }

    public void setWidth(int n2) {
        this.width = n2;
    }

    public void aV(float f) {
        this.ctM = f;
    }

    public boolean awk() {
        return this.ctM != -1.0f || this.ctN != -1.0f;
    }

    public boolean a(agj_1 agj_12) {
        if (agj_12 == null) {
            return false;
        }
        return agj_12.height == this.height && agj_12.width == this.width && agj_12.ctN == this.ctN && agj_12.ctM == this.ctM;
    }

    public agj_1 awl() {
        agj_1 agj_12 = new agj_1(this.width, this.height);
        agj_12.aU(this.ctN);
        agj_12.aV(this.ctM);
        return agj_12;
    }
}

