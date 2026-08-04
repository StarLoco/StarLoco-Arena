/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea.worldElement;

import com.ankamagames.baseImpl.graphics.alea.WorldElementManager;
import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
import com.ankamagames.framework.kernel.core.maths.Point3;
import com.ankamagames.graphics.isometric.highlight.UniqueHandleReference;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class WorldElement
implements UniqueHandleReference,
Comparable<WorldElement> {
    BasicElement m_element;
    protected int m_state;
    protected int m_paramsCount;
    protected byte[] m_params;
    protected int m_groupInstanceId;
    protected int m_level;
    protected byte m_altitude;
    protected float m_brightness;
    protected float[] m_teint;
    protected float m_altitudeOrder;
    protected Point3 m_coordinates = new Point3();
    protected long m_handle = 1L;

    public WorldElement(int elementId, int paramsCount, byte[] params, int state, int groupId) {
        this.m_element = WorldElementManager.getInstance().getElement(elementId);
        this.m_params = params;
        this.m_paramsCount = paramsCount;
        this.m_groupInstanceId = groupId;
        this.m_state = state;
    }

    @Override
    public long getHandle() {
        return this.m_handle;
    }

    public void SetHandle(long value) {
        this.m_handle = value;
    }

    public void addPrecalculatedInformations(int level, byte altitude, float brightness, float[] teint, float altitudeOrder) {
        this.m_level = level;
        this.m_altitude = altitude;
        this.m_brightness = brightness;
        this.m_teint = teint;
        this.m_altitudeOrder = altitudeOrder;
    }

    public byte getAltitude() {
        return this.m_altitude;
    }

    public void setAltitude(byte altitude) {
        this.m_altitude = altitude;
    }

    public int getLevel() {
        return this.m_level;
    }

    public float getBrightness() {
        return this.m_brightness;
    }

    public float[] getTeint() {
        return this.m_teint;
    }

    public float getAltitudeOrder() {
        return this.m_altitudeOrder;
    }

    public BasicElement getElement() {
        return this.m_element;
    }

    public int getGroupInstanceId() {
        return this.m_groupInstanceId;
    }

    public byte[] getParams() {
        return this.m_params;
    }

    public int getParamsCount() {
        return this.m_paramsCount;
    }

    public int getState() {
        return this.m_state;
    }

    public double getWeight() {
        return 0.0;
    }

    public double getHeight() {
        return 0.0;
    }

    public double getVisualHeight() {
        return 0.0;
    }

    public byte getSlope() {
        return 0;
    }

    @Override
    public int compareTo(WorldElement o) {
        float a2;
        float a1 = this.getAltitude();
        if (a1 < (a2 = (float)o.getAltitude())) {
            return -1;
        }
        if (a1 > a2) {
            return 1;
        }
        return 0;
    }

    public void setCoordinates(int x, int y, byte z) {
        this.m_coordinates.set(x, y, (short)((double)z + this.getHeight()));
    }

    public Point3 getCoordinates() {
        return this.m_coordinates;
    }
}

