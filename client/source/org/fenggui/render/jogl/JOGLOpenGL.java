/*     */ package org.fenggui.render.jogl;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.DoubleBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import javax.media.opengl.GL;
/*     */ import javax.media.opengl.glu.GLU;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JOGLOpenGL
/*     */   implements IOpenGL
/*     */ {
/*     */   private GL gl;
/*     */   private GLU glu;
/*     */   
/*     */   protected JOGLOpenGL(GL gl) {
/*  51 */     this.gl = gl;
/*  52 */     this.glu = new GLU();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModelMatrixMode() {
/*  59 */     this.gl.glMatrixMode(5888);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProjectionMatrixMode() {
/*  66 */     this.gl.glMatrixMode(5889);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushMatrix() {
/*  74 */     this.gl.glPushMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void popMatrix() {
/*  82 */     this.gl.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadIdentity() {
/*  89 */     this.gl.glLoadIdentity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushAllAttribs() {
/*  96 */     this.gl.glPushAttrib(1048575);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void popAllAttribs() {
/* 103 */     this.gl.glPopAttrib();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean[] getBoolean(IOpenGL.Attribute attrib) {
/* 110 */     int pname = getAttrib(attrib);
/* 111 */     ByteBuffer buf = ByteBuffer.allocateDirect(16);
/* 112 */     this.gl.glGetBooleanv(pname, buf);
/*     */     
/* 114 */     boolean[] result = new boolean[buf.capacity()];
/* 115 */     for (int i = 0; i < result.length; i++) {
/* 116 */       result[i] = (buf.get() == 1);
/*     */     }
/* 118 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getDouble(IOpenGL.Attribute attrib) {
/* 125 */     int pname = getAttrib(attrib);
/* 126 */     DoubleBuffer buf = BufferUtils.createDoubleBuffer(16);
/* 127 */     this.gl.glGetDoublev(pname, buf);
/*     */     
/* 129 */     double[] result = new double[buf.capacity()];
/* 130 */     for (int i = 0; i < result.length; i++) {
/* 131 */       result[i] = buf.get();
/*     */     }
/* 133 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getFloat(IOpenGL.Attribute attrib) {
/* 140 */     int pname = getAttrib(attrib);
/* 141 */     FloatBuffer buf = BufferUtils.createFloatBuffer(16);
/* 142 */     this.gl.glGetFloatv(pname, buf);
/*     */     
/* 144 */     float[] result = new float[buf.capacity()];
/* 145 */     for (int i = 0; i < result.length; i++) {
/* 146 */       result[i] = buf.get();
/*     */     }
/* 148 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getInt(IOpenGL.Attribute attrib) {
/* 155 */     int pname = getAttrib(attrib);
/* 156 */     IntBuffer buf = BufferUtils.createIntBuffer(16);
/* 157 */     this.gl.glGetIntegerv(pname, buf);
/*     */     
/* 159 */     int[] result = new int[buf.capacity()];
/* 160 */     for (int i = 0; i < result.length; i++) {
/* 161 */       result[i] = buf.get();
/*     */     }
/* 163 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(IOpenGL.Attribute attrib) {
/* 170 */     int pname = getAttrib(attrib);
/* 171 */     return this.gl.glGetString(pname);
/*     */   }
/*     */   
/*     */   public void enable(IOpenGL.Attribute attrib) {
/* 175 */     this.gl.glEnable(getAttrib(attrib));
/*     */   }
/*     */   
/*     */   public void disable(IOpenGL.Attribute attrib) {
/* 179 */     this.gl.glDisable(getAttrib(attrib));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableTexture2D(boolean b) {
/* 186 */     if (b) { this.gl.glEnable(3553); }
/* 187 */     else { this.gl.glDisable(3553); }
/*     */   
/*     */   }
/*     */   public void enableLighting(boolean b) {
/* 191 */     if (b) { this.gl.glEnable(2896); }
/* 192 */     else { this.gl.glDisable(2896); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setViewPort(int x, int y, int width, int height) {
/* 200 */     this.gl.glViewport(x, y, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDepthFunctionToLEqual() {
/* 208 */     this.gl.glDepthFunc(515);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateZ(float z) {
/* 216 */     this.gl.glTranslatef(0.0F, 0.0F, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rotate(float angle) {
/* 223 */     this.gl.glRotatef(angle, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureDecal() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void end() {
/* 237 */     this.gl.glEnd();
/*     */   }
/*     */   
/*     */   public int genLists(int range) {
/* 241 */     return this.gl.glGenLists(range);
/*     */   }
/*     */   
/*     */   public void startList(int list) {
/* 245 */     this.gl.glNewList(list, 4864);
/*     */   }
/*     */   
/*     */   public void endList() {
/* 249 */     this.gl.glEndList();
/*     */   }
/*     */   
/*     */   public void callList(int list) {
/* 253 */     this.gl.glCallList(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startQuads() {
/* 260 */     this.gl.glBegin(7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startLines() {
/* 267 */     this.gl.glBegin(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startTriangles() {
/* 275 */     this.gl.glBegin(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startTriangleStrip() {
/* 283 */     this.gl.glBegin(5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startTriangleFan() {
/* 291 */     this.gl.glBegin(6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startQuadStrip() {
/* 298 */     this.gl.glBegin(8);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startPoints() {
/* 303 */     this.gl.glBegin(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void vertex(float x, float y) {
/* 310 */     this.gl.glVertex2f(x, y);
/*     */   }
/*     */   
/*     */   public void rect(float x1, float y1, float x2, float y2) {
/* 314 */     this.gl.glRectf(x1, y1, x2, y2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void texCoord(float x, float y) {
/* 321 */     this.gl.glTexCoord2f(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void color(float red, float green, float blue, float alpha) {
/* 328 */     this.gl.glColor4f(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scale(float scaleX, float scaleY) {
/* 335 */     this.gl.glScalef(scaleX, scaleY, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTexEnvModeDecal() {
/* 343 */     this.gl.glTexEnvf(8960, 8704, 8449.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTexEnvModeModulate() {
/* 351 */     this.gl.glTexEnvf(8960, 8704, 8448.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateXY(int x, int y) {
/* 359 */     this.gl.glTranslatef(x, y, 0.0F);
/*     */   }
/*     */   
/*     */   public void setupBlending() {
/* 363 */     this.gl.glEnable(3042);
/* 364 */     this.gl.glBlendFunc(770, 771);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupStateVariables(boolean depthTestEnabled) {
/* 371 */     this.gl.glEnable(3042);
/* 372 */     this.gl.glBlendFunc(770, 771);
/*     */     
/* 374 */     if (depthTestEnabled) this.gl.glDisable(2929);
/*     */     
/* 376 */     this.gl.glShadeModel(7425);
/* 377 */     this.gl.glDisable(2896);
/* 378 */     this.gl.glDisable(2912);
/* 379 */     this.gl.glDisable(3024);
/*     */     
/* 381 */     this.gl.glEnable(3089);
/*     */     
/* 383 */     this.gl.glPolygonMode(1032, 6914);
/* 384 */     this.gl.glEnable(2977);
/* 385 */     this.gl.glDisable(2852);
/*     */ 
/*     */     
/* 388 */     this.gl.glTexParameteri(3553, 10242, 10496);
/* 389 */     this.gl.glTexParameteri(3553, 10243, 10496);
/*     */     
/* 391 */     this.gl.glTexParameteri(3553, 10240, 9728);
/* 392 */     this.gl.glTexParameteri(3553, 10241, 9728);
/* 393 */     this.gl.glTexEnvf(8960, 8704, 8449.0F);
/*     */ 
/*     */     
/* 396 */     this.gl.glDisable(2884);
/* 397 */     this.gl.glEnable(2977);
/* 398 */     this.gl.glFrontFace(2304);
/* 399 */     this.gl.glCullFace(1029);
/*     */ 
/*     */     
/* 402 */     this.gl.glDisable(3553);
/* 403 */     this.gl.glDisable(3552);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startLineStrip() {
/* 409 */     this.gl.glBegin(3);
/*     */   }
/*     */   
/*     */   public void startLineLoop() {
/* 413 */     this.gl.glBegin(2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void enableStipple() {
/* 418 */     this.gl.glEnable(2852);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disableStipple() {
/* 423 */     this.gl.glDisable(2852);
/*     */   }
/*     */ 
/*     */   
/*     */   public void lineStipple(int stretch, short pattern) {
/* 428 */     this.gl.glLineStipple(stretch, pattern);
/*     */   }
/*     */ 
/*     */   
/*     */   public void lineWidth(float width) {
/* 433 */     this.gl.glLineWidth(width);
/*     */   }
/*     */ 
/*     */   
/*     */   public void pointSize(float size) {
/* 438 */     this.gl.glPointSize(size);
/*     */   }
/*     */ 
/*     */   
/*     */   public void enableAlpha(boolean state) {
/* 443 */     if (state) {
/*     */       
/* 445 */       this.gl.glEnable(6406);
/*     */     }
/*     */     else {
/*     */       
/* 449 */       this.gl.glDisable(6406);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void readPixels(int x, int y, int width, int height, ByteBuffer bgr) {
/* 454 */     this.gl.glReadPixels(x, y, width, height, 32992, 
/* 455 */         5121, bgr);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOrtho2D(int left, int right, int bottom, int top) {
/* 460 */     this.glu.gluOrtho2D(left, right, bottom, top);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setScissor(int x, int width, int y, int height) {
/* 465 */     this.gl.glScissor(x, y, width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void activateTexture(int i) {
/* 470 */     this.gl.glActiveTexture(33984 + i);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotate(float angle, int x, int y, int z) {
/* 475 */     this.gl.glRotated(angle, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getAttrib(IOpenGL.Attribute attrib) {
/* 480 */     switch (attrib) {
/*     */       
/*     */       case null:
/* 483 */         return 2816;
/*     */       case LINE_WIDTH:
/* 485 */         return 2849;
/*     */       case POINT_SIZE:
/* 487 */         return 2833;
/*     */     } 
/* 489 */     return 0;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\jogl\JOGLOpenGL.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */