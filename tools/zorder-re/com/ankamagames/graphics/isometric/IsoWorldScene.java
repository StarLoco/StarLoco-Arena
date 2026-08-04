/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.graphics.isometric;

import com.ankamagames.framework.graphics.opengl.base.Scene;
import com.ankamagames.graphics.isometric.IsoCamera;
import com.ankamagames.graphics.isometric.IsoWorldTarget;

public class IsoWorldScene
extends Scene {
    public static final double DEFAULT_CELL_WIDTH = 86.0;
    public static final double DEFAULT_CELL_HEIGHT = 43.0;
    public static final double DEFAULT_ELEVATION_UNIT = 10.0;
    protected double m_cellWidth = 86.0;
    protected double m_cellHeight = 43.0;
    protected double m_elevationUnit = 10.0;
    protected IsoCamera m_isoCamera;

    public IsoWorldScene() {
        this.initializeCamera();
    }

    public double getCellWidth() {
        return this.m_cellWidth;
    }

    public void setCellWidth(double cellWidth) {
        this.m_cellWidth = cellWidth;
    }

    public double getCellHeight() {
        return this.m_cellHeight;
    }

    public void setCellHeight(double cellHeight) {
        this.m_cellHeight = cellHeight;
    }

    public double getElevationUnit() {
        return this.m_elevationUnit;
    }

    public void setElevationUnit(double elevationUnit) {
        this.m_elevationUnit = elevationUnit;
    }

    public IsoCamera getIsoCamera() {
        return this.m_isoCamera;
    }

    public IsoWorldTarget getCameraTarget() {
        return this.m_isoCamera.getTrackingTarget();
    }

    public void setCameraTarget(IsoWorldTarget cameraTarget) {
        this.m_isoCamera.setTrackingTarget(cameraTarget);
    }

    public void alignCameraOnTrackingTarget() {
        this.m_isoCamera.alignOnTrackingTarget();
    }

    public double getDesiredZoomFactor() {
        if (this.m_isoCamera != null) {
            return this.m_isoCamera.getDesiredZoomFactor();
        }
        return 1.0;
    }

    public void setDesiredZoomFactor(double desiredZoomFactor) {
        if (this.m_isoCamera != null) {
            this.m_isoCamera.setDesiredZoomFactor(desiredZoomFactor);
        }
    }

    protected void initializeCamera() {
        this.m_isoCamera = new IsoCamera(0.0, 0.0, 0.0, this);
        this.setCamera(this.m_isoCamera);
    }

    public double isoToScreenX(double isoLocalX, double isoLocalY) {
        return (isoLocalX - isoLocalY) * (this.m_cellWidth / 2.0);
    }

    public double isoToScreenY(double isoLocalX, double isoLocalY) {
        return -(isoLocalX + isoLocalY) * (this.m_cellHeight / 2.0);
    }

    public double screenToIsoX(double screenX, double screenY) {
        double w = this.m_cellWidth / 2.0;
        double h = this.m_cellHeight / 2.0;
        double sy = screenY + h;
        return (sy / h - screenX / w) / 2.0 + screenX / w;
    }

    public double screenToIsoY(double screenX, double screenY) {
        double w = this.m_cellWidth / 2.0;
        double h = this.m_cellHeight / 2.0;
        double sy = screenY + h;
        return (sy / h - screenX / w) / 2.0;
    }
}

