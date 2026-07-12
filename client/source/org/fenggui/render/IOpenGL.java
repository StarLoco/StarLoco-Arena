/*    */ package org.fenggui.render;public interface IOpenGL { void setModelMatrixMode(); void setProjectionMatrixMode(); void pushMatrix(); void popMatrix(); void loadIdentity(); void pushAllAttribs(); void popAllAttribs(); int[] getInt(Attribute paramAttribute); float[] getFloat(Attribute paramAttribute); boolean[] getBoolean(Attribute paramAttribute); double[] getDouble(Attribute paramAttribute); String getString(Attribute paramAttribute); void enable(Attribute paramAttribute); void disable(Attribute paramAttribute); void enableTexture2D(boolean paramBoolean); void setTexEnvModeDecal(); void setTexEnvModeModulate(); void setViewPort(int paramInt1, int paramInt2, int paramInt3, int paramInt4); void setOrtho2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4); void setDepthFunctionToLEqual();
/*    */   void translateZ(float paramFloat);
/*    */   void translateXY(int paramInt1, int paramInt2);
/*    */   void rotate(float paramFloat);
/*    */   void rotate(float paramFloat, int paramInt1, int paramInt2, int paramInt3);
/*    */   void setScissor(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*    */   void activateTexture(int paramInt);
/*    */   int genLists(int paramInt);
/*    */   void startList(int paramInt);
/*    */   void endList();
/*    */   void callList(int paramInt);
/*    */   void end();
/*    */   void startQuads();
/*    */   void startLines();
/*    */   void startLineStrip();
/*    */   void startLineLoop();
/*    */   void startTriangles();
/*    */   void startTriangleStrip();
/*    */   void startTriangleFan();
/*    */   void startQuadStrip();
/*    */   void startPoints();
/*    */   void vertex(float paramFloat1, float paramFloat2);
/*    */   void rect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*    */   void texCoord(float paramFloat1, float paramFloat2);
/*    */   void color(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*    */   void scale(float paramFloat1, float paramFloat2);
/*    */   void setupBlending();
/*    */   void enableLighting(boolean paramBoolean);
/*    */   void setupStateVariables(boolean paramBoolean);
/*    */   void lineWidth(float paramFloat);
/*    */   void pointSize(float paramFloat);
/*    */   void enableStipple();
/*    */   void disableStipple();
/*    */   void lineStipple(int paramInt, short paramShort);
/*    */   void enableAlpha(boolean paramBoolean);
/*    */   void readPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ByteBuffer paramByteBuffer);
/* 37 */   public enum Attribute { CURRENT_COLOR,
/* 38 */     LINE_WIDTH,
/* 39 */     POINT_SIZE; }
/*    */    }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\IOpenGL.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */