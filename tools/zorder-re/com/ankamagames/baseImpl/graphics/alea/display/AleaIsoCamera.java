/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea.display;

import com.ankamagames.graphics.isometric.IsoCamera;
import com.ankamagames.graphics.isometric.IsoWorldScene;

public class AleaIsoCamera
extends IsoCamera {
    private int m_cameraGroupInstanceId = 0;
    private int m_cameraGroupLevel = 0;

    public AleaIsoCamera() {
    }

    public AleaIsoCamera(double worldX, double worldY, double altitude, IsoWorldScene scene) {
        super(worldX, worldY, altitude, scene);
    }

    public void setCameraGroupInstanceId(int cameraGroupInstanceId) {
        this.m_cameraGroupInstanceId = cameraGroupInstanceId;
    }

    public int getCameraGroupInstanceId() {
        return this.m_cameraGroupInstanceId;
    }

    public void setCameraGroupLevel(int cameraGroupLevel) {
        this.m_cameraGroupLevel = cameraGroupLevel;
    }

    public int getCameraGroupLevel() {
        return this.m_cameraGroupLevel;
    }
}

