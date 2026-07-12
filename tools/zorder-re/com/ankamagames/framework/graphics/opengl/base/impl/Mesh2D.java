/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.opengl.base.impl;

import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
import com.ankamagames.framework.graphics.opengl.base.Mesh;
import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2DManager;
import com.ankamagames.framework.graphics.opengl.base.material.Material;
import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Matrix2D;
import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.RotateSkew2D;
import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Rotation2D;
import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Scaling2D;
import com.ankamagames.framework.graphics.opengl.base.matrices.transformation3D.HotSpot3D;
import com.ankamagames.framework.graphics.opengl.base.matrices.transformation3D.Position3D;
import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
import com.ankamagames.framework.kernel.core.resource.ResourceContext;
import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.texture.TextureCoords;

public class Mesh2D
extends Mesh {
    protected float m_width;
    protected float m_height;
    protected float m_hotX;
    protected float m_hotY;
    protected float m_posZ;
    protected boolean m_flip;
    protected boolean m_flipChanged;
    protected HotSpot3D m_hotCenter;
    protected Position3D m_offset;
    protected Position3D m_position;
    protected RotateSkew2D m_rotateSkew;
    protected Rotation2D m_rotation;
    protected Scaling2D m_scaling;
    protected Matrix2D m_transformation;
    private static int m_mesh2Dcount = 0;

    public Mesh2D() {
        ++m_mesh2Dcount;
        this.m_hotCenter = new HotSpot3D();
        this.m_offset = new Position3D();
        this.m_position = new Position3D();
        this.m_rotateSkew = new RotateSkew2D();
        this.m_rotation = new Rotation2D();
        this.m_scaling = new Scaling2D();
        this.initialize();
    }

    public Material getMaterial() {
        return (Material)this.m_material;
    }

    public static int getMesh2Dcount() {
        return m_mesh2Dcount;
    }

    public void initialize() {
        if (this.isInitialized()) {
            return;
        }
        super.initialize();
        this.m_width = 10.0f;
        this.m_height = 10.0f;
        this.pushMatrixBack(this.m_position, 5888);
        this.pushMatrixBack(this.m_rotateSkew, 5888);
        this.pushMatrixBack(this.m_rotation, 5888);
        this.pushMatrixBack(this.m_scaling, 5888);
        this.pushMatrixBack(this.m_hotCenter, 5888);
        this.m_glType = 5;
        if (this.m_vertexBuffer == null) {
            this.m_vertexBuffer = BufferUtil.newFloatBuffer(16);
        }
        if (this.m_vertexColorBuffer == null) {
            this.m_vertexColorBuffer = BufferUtil.newFloatBuffer(16);
        }
        if (this.m_texCoordBuffer == null) {
            this.m_texCoordBuffer = BufferUtil.newFloatBuffer(8);
        }
        if (this.m_vertexIndexBuffer == null) {
            this.m_vertexIndexBuffer = BufferUtil.newShortBuffer(6);
        }
        try {
            this.m_vertexBuffer.rewind();
            this.m_vertexColorBuffer.rewind();
            this.m_texCoordBuffer.rewind();
            this.m_vertexIndexBuffer.rewind();
            this.m_vertexBuffer.put(new float[]{0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f});
            this.m_vertexColorBuffer.put(new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f});
            this.m_texCoordBuffer.put(new float[]{0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f});
            short[] sArray = new short[6];
            sArray[1] = 1;
            sArray[2] = 2;
            sArray[3] = 2;
            sArray[4] = 3;
            sArray[5] = 1;
            this.m_vertexIndexBuffer.put(sArray);
        }
        catch (Exception e) {
            System.err.println("Exception : " + e.getMessage() + "\n\t+ Deux buffers ont la m\u00eame addresse");
        }
        this.m_vertexBuffer.flip();
        this.m_vertexColorBuffer.flip();
        this.m_texCoordBuffer.flip();
        this.m_vertexIndexBuffer.flip();
    }

    public void uninitialize() {
        if (!this.isInitialized()) {
            return;
        }
        super.uninitialize();
        this.m_flip = false;
        this.m_flipChanged = false;
        this.m_effectEnabled = false;
        this.m_position.reset();
        this.m_rotateSkew.reset();
        this.m_rotation.reset();
        this.m_scaling.reset();
        this.m_hotCenter.reset();
        this.m_offset.reset();
        this.m_transformation = null;
        this.m_width = 0.0f;
        this.m_height = 0.0f;
        this.m_hotX = 0.0f;
        this.m_hotY = 0.0f;
        this.m_posZ = 0.0f;
        this.m_flip = false;
        this.m_flipChanged = false;
        this.clearMatrices(5888);
        this.clearMatrices(5889);
        this.clearMatrices(5890);
    }

    public void computeTextureCoordinate() {
        BaseTexture texture = this.getTexture();
        if (texture != null) {
            TextureCoords coords = texture.getImageTexCoords();
            if (coords != null) {
                if (this.m_flip) {
                    this.m_texCoordBuffer.put(0, coords.right());
                    this.m_texCoordBuffer.put(1, coords.bottom());
                    this.m_texCoordBuffer.put(2, coords.right());
                    this.m_texCoordBuffer.put(3, coords.top());
                    this.m_texCoordBuffer.put(4, coords.left());
                    this.m_texCoordBuffer.put(5, coords.bottom());
                    this.m_texCoordBuffer.put(6, coords.left());
                    this.m_texCoordBuffer.put(7, coords.top());
                } else {
                    this.m_texCoordBuffer.put(0, coords.left());
                    this.m_texCoordBuffer.put(1, coords.bottom());
                    this.m_texCoordBuffer.put(2, coords.left());
                    this.m_texCoordBuffer.put(3, coords.top());
                    this.m_texCoordBuffer.put(4, coords.right());
                    this.m_texCoordBuffer.put(5, coords.bottom());
                    this.m_texCoordBuffer.put(6, coords.right());
                    this.m_texCoordBuffer.put(7, coords.top());
                }
            }
            this.setWidth(texture.getImageWidth());
            this.setHeight(texture.getImageHeight());
        }
    }

    public void setColor(float r, float g, float b, float a) {
        float[] diffuse = this.getMaterial().getDiffuse();
        this.getMaterial().setUseDiffuse(true);
        if (diffuse[0] != r || diffuse[1] != g || diffuse[2] != b || diffuse[3] != a) {
            this.getMaterial().setDiffuse(r, g, b, a);
            this.m_materialChanged = true;
            this.applyMaterial();
        }
    }

    public void applyMaterial() {
        if (this.m_materialChanged || this.m_material != null && this.getMaterial().isDiffuseChanged()) {
            this.m_vertexColorBuffer.rewind();
            if (this.m_material == null || !this.getMaterial().useDiffuse()) {
                this.m_vertexColorBuffer.put(Material.WhiteColor);
                this.m_vertexColorBuffer.put(Material.WhiteColor);
                this.m_vertexColorBuffer.put(Material.WhiteColor);
                this.m_vertexColorBuffer.put(Material.WhiteColor);
            } else {
                this.m_vertexColorBuffer.put(this.getMaterial().getDiffuseBottomLeft());
                this.m_vertexColorBuffer.put(this.getMaterial().getDiffuseTopLeft());
                this.m_vertexColorBuffer.put(this.getMaterial().getDiffuseBottomRight());
                this.m_vertexColorBuffer.put(this.getMaterial().getDiffuseTopRight());
            }
            this.m_vertexColorBuffer.rewind();
            this.m_materialChanged = false;
        }
    }

    public void process(long realTime, int frameCount) {
        super.process(realTime, frameCount);
        Mesh2DManager.getInstance().tagResourceInUse(this);
    }

    public boolean isHitTestable() {
        return false;
    }

    public float getPosZ() {
        return this.m_posZ;
    }

    public void setZOrder(float posZ) {
        this.m_posZ = posZ;
        this.m_position.setZ(this.m_posZ);
    }

    public float getPosX() {
        return this.m_position.getX();
    }

    public float getPosY() {
        return this.m_position.getY();
    }

    public float getWidth() {
        return this.m_width;
    }

    public void setWidth(float width) {
        this.m_width = width;
        this.m_vertexBuffer.put(8, this.m_width);
        this.m_vertexBuffer.put(12, this.m_width);
    }

    public float getHeight() {
        return this.m_height;
    }

    public void setHeight(float height) {
        this.m_height = height;
        this.m_vertexBuffer.put(5, this.m_height);
        this.m_vertexBuffer.put(13, this.m_height);
        if (this.m_transformation != null) {
            this.m_offset.set(0.0f, -this.m_height, 0.0f);
        } else {
            this.m_hotCenter.set(this.m_hotX, this.m_hotY - this.m_height, 0.0f);
        }
    }

    public float getHotX() {
        return this.m_hotCenter.getX();
    }

    public float getHotY() {
        return this.m_hotCenter.getY() + this.m_height;
    }

    public void setHotPoint(float hotX, float hotY) {
        this.m_hotX = hotX;
        this.m_hotY = hotY;
        if (this.m_transformation == null) {
            this.m_hotCenter.set(hotX, hotY - this.m_height, 0.0f);
        } else {
            this.m_hotCenter.set(hotX, hotY, 0.0f);
        }
    }

    public void setGeometry(float x, float y, float w, float h) {
        this.m_position.set(x, y, this.m_posZ);
        this.setWidth(w);
        this.setHeight(h);
    }

    public float getSortPosition() {
        return this.m_posZ;
    }

    public boolean isFlip() {
        return this.m_flip;
    }

    public void setFlip(boolean flip) {
        if (this.m_flip != flip) {
            this.m_flip = flip;
            this.m_flipChanged = true;
        }
    }

    public void setScreenPosition(float posX, float posY) {
        this.m_position.set(posX, posY, this.m_posZ);
    }

    public void translate(float x, float y) {
        this.m_position.add(x, y, 0.0f);
    }

    public void rotate(float angleDeg) {
        this.m_rotation.add(angleDeg);
    }

    public void setRotation(float angleDeg) {
        this.m_rotation.setAngleDeg(angleDeg);
    }

    public Rotation2D getRotation() {
        return this.m_rotation;
    }

    public void scale(float x, float y) {
        this.m_scaling.mult(x, y);
    }

    public void setScale(float x, float y) {
        this.m_scaling.set(x, y);
    }

    public void rotateSkew(float x, float y) {
        this.m_rotateSkew.add(x, y);
    }

    public void setRotateSkew(float x, float y) {
        this.m_rotateSkew.set(x, y);
    }

    public Position3D getPositionMatrix() {
        return this.m_position;
    }

    public RotateSkew2D getRotateSkewMatrix() {
        return this.m_rotateSkew;
    }

    public Scaling2D getScaleMatrix() {
        return this.m_scaling;
    }

    public void setTransformation(Matrix2D matrix) {
        this.clearMatrices(5888);
        this.m_transformation = matrix;
        this.pushMatrixBack(this.m_transformation, 5888);
        this.pushMatrixBack(this.m_scaling, 5888);
        this.pushMatrixBack(this.m_hotCenter, 5888);
        this.pushMatrixBack(this.m_offset, 5888);
    }

    public Matrix2D getTransformation() {
        return this.m_transformation;
    }

    public void combineHotCenterAndOffset() {
        this.m_hotCenter.setX(this.m_hotCenter.getX() + this.m_offset.getX());
        this.m_hotCenter.setY(this.m_hotCenter.getY() + this.m_offset.getY());
        this.m_hotCenter.setZ(this.m_hotCenter.getZ() + this.m_offset.getZ());
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Mesh2D[").append(this.getPosX()).append(";").append(this.getPosY()).append(";").append(this.getPosZ()).append(" - ").append(this.m_width).append(";").append(this.m_height).append("] (").append(this.m_children.size()).append(" childs)\n");
        for (GLObject child : this.m_children) {
            buffer.append("\t").append(child.toString());
        }
        return buffer.toString();
    }

    public void reloadResource(ResourceContext resourceContext) {
        super.reloadResource(resourceContext);
    }

    public void unloadResource(ResourceContext resourceContext) {
        super.unloadResource(resourceContext);
    }

    public long estimateMemoryUsageInBytes() {
        return super.estimateMemoryUsageInBytes() + 64L + 64L + 32L + 12L;
    }

    public static class Mesh2DResourceContext
    extends ResourceContext {
    }
}

