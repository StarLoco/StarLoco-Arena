/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea.display;

import com.ankamagames.baseImpl.graphics.alea.display.DisplayedCell;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
import com.ankamagames.framework.kernel.core.common.MonitoredPool;
import com.ankamagames.framework.kernel.core.common.ObjectFactory;
import com.ankamagames.framework.kernel.core.common.Poolable;
import com.ankamagames.framework.kernel.core.maths.Point3;
import com.ankamagames.graphics.isometric.highlight.HighLightMesh;
import com.ankamagames.graphics.isometric.highlight.HighLightedElement;
import com.ankamagames.graphics.isometric.highlight.UniqueHandleReference;
import com.ankamagames.graphics.isometric.lines.Segment;
import com.ankamagames.graphics.isometric.lines.SegmentMesh;
import org.apache.commons.pool.ObjectPool;

public class DisplayedElement
implements HighLightedElement,
Poolable {
    private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory<DisplayedElement>(){

        @Override
        public DisplayedElement makeObject() {
            return new DisplayedElement();
        }
    });
    private DisplayedCell m_displayedCell;
    private WorldElement m_worldElement;
    private Mesh2D m_mesh;
    private float m_screenX;
    private float m_screenY;
    private float m_screenTopY;
    private float m_zOrder;
    private int m_level;
    private float m_altitude;
    private float m_brightness;
    private float[] m_teint;
    private int m_state;
    private boolean m_visible = true;
    private double m_distanceFromTopToMouse;

    public void setDisplayedCell(DisplayedCell displayedCell) {
        this.m_displayedCell = displayedCell;
    }

    public void setWorldElement(WorldElement worldElement) {
        this.m_worldElement = worldElement;
    }

    public DisplayedCell getDisplayedCell() {
        return this.m_displayedCell;
    }

    public void setScreenPosition(float screenX, float screenY, float screenTopY) {
        this.m_screenX = screenX;
        this.m_screenY = screenY;
        this.m_screenTopY = screenTopY;
    }

    public Point3 getCoordinates() {
        return this.m_worldElement.getCoordinates();
    }

    public WorldElement getWorldElement() {
        return this.m_worldElement;
    }

    public Mesh2D getMesh() {
        return this.m_mesh;
    }

    public void setMesh(Mesh2D mesh) {
        this.m_mesh = mesh;
    }

    public float getScreenX() {
        return this.m_screenX;
    }

    public float getScreenY() {
        return this.m_screenY;
    }

    public float getScreenTopY() {
        return this.m_screenTopY;
    }

    public void setZOrder(float zOrder) {
        this.m_zOrder = zOrder;
    }

    public float getZOrder() {
        return this.m_zOrder;
    }

    public boolean isVisible() {
        return this.m_visible;
    }

    public void setVisible(boolean visible) {
        this.m_visible = visible;
    }

    public float getAltitude() {
        return this.m_altitude;
    }

    public void setAltitude(float height) {
        this.m_altitude = height;
    }

    public int getLevel() {
        return this.m_level;
    }

    public void setLevel(int level) {
        this.m_level = level;
    }

    public float getBrightness() {
        return this.m_brightness;
    }

    public void setBrightness(float light) {
        this.m_brightness = light;
    }

    public float[] getTeint() {
        return this.m_teint;
    }

    public void setTeint(float[] teint) {
        this.m_teint = teint;
    }

    public int getState() {
        return this.m_state;
    }

    public void setState(int state) {
        this.m_state = state;
    }

    public double getDistanceFromTopToMouse() {
        return this.m_distanceFromTopToMouse;
    }

    public boolean rectHitTest(double x, double y) {
        if (this.m_mesh != null) {
            double top;
            double bottom;
            double left = this.m_screenX - this.m_mesh.getHotX();
            double right = left + (double)this.m_mesh.getWidth();
            if (x >= left && x <= right && y >= (bottom = (top = (double)(this.m_screenY + this.m_mesh.getHotY())) - (double)this.m_mesh.getHeight()) && y <= top) {
                return true;
            }
        }
        return false;
    }

    public boolean fineHitTest(double x, double y, double minAlphaLevel) {
        if (this.m_mesh != null) {
            int imgY;
            BaseTexture texture = this.m_mesh.getTexture();
            if (texture == null) {
                return false;
            }
            int imgX = (int)(x - (double)this.m_screenX + (double)this.m_mesh.getHotX());
            return texture.getAlpha(imgX, imgY = (int)((double)(this.m_screenY + this.m_mesh.getHotY()) - y)) >= minAlphaLevel;
        }
        return false;
    }

    public void calculateDistanceFromTopToMouse(double mouseX, double mouseY) {
        this.m_distanceFromTopToMouse = Math.sqrt(Math.pow(mouseX - (double)this.m_screenX, 2.0) + Math.pow(mouseY - (double)this.m_screenTopY, 2.0));
    }

    public UniqueHandleReference getLayerReference() {
        return this.getWorldElement();
    }

    public void transformHighLightMesh(HighLightMesh mesh, int elevationUnit) {
        if (mesh == null) {
            return;
        }
        byte slope = this.getWorldElement().getSlope();
        double height = this.getWorldElement().getVisualHeight();
        float heightPx = (float)height * (float)elevationUnit;
        float screenX = this.getScreenX();
        float screenY = slope == 0 ? this.getScreenTopY() : this.getScreenY();
        mesh.setZOrder(this.getZOrder() + 1.0E-6f);
        float size = mesh.getSize();
        mesh.setVertices(new float[]{-size, 0.0f, 0.0f, size}, new float[]{(slope & 1) == 1 ? heightPx : 0.0f, -size / 2.0f + ((slope & 8) == 8 ? heightPx : 0.0f), size / 2.0f + ((slope & 2) == 2 ? heightPx : 0.0f), (slope & 4) == 4 ? heightPx : 0.0f});
        mesh.setScreenPosition(screenX, screenY);
        mesh.computeTextureCoordinate();
    }

    public void transformLineMesh(SegmentMesh mesh, Segment segment, double cellWidth, double cellHeight, int elevationUnit, float frustumHeight) {
        byte slope = this.getWorldElement().getSlope();
        float halfCellHeight = (float)cellHeight * 0.5f;
        float screenX = this.getScreenX();
        float screenY = slope == 0 ? this.getScreenTopY() : this.getScreenY();
        mesh.setZOrder(this.getZOrder() + 1.0E-6f + halfCellHeight / frustumHeight);
        mesh.setStart(screenX, screenY);
        screenX = (float)((double)screenX + (double)(segment.getX() - segment.getY()) * cellWidth * 0.5);
        screenY += (float)(-segment.getY() - segment.getX()) * halfCellHeight + (float)(segment.getZ() * elevationUnit);
        if (segment.isBehind()) {
            float alpha = 75.0f / (100.0f - (float)(4 * segment.getZ()));
            if (alpha < 0.05f) {
                alpha = 0.05f;
            }
            mesh.setAlpha(alpha);
        }
        mesh.setEnd(screenX, screenY);
    }

    public static DisplayedElement checkOut() {
        try {
            return (DisplayedElement)m_staticPool.borrowObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void release() {
        try {
            m_staticPool.returnObject(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCheckOut() {
    }

    public void onCheckIn() {
        this.m_mesh = null;
        this.m_worldElement = null;
        this.m_displayedCell = null;
    }
}

