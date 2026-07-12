/*    */ package com.ankamagames.framework.graphics.opengl.base.effects;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.shaders.ShaderProgram;
/*    */ import com.sun.opengl.cg.CGcontext;
/*    */ import com.sun.opengl.cg.CgGL;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShaderManager
/*    */ {
/* 21 */   private static final ShaderManager m_instance = new ShaderManager();
/*    */   
/*    */   private CGcontext m_cgContext;
/*    */   private ArrayList<ShaderProgram> m_vertexShaders;
/*    */   private ArrayList<ShaderProgram> m_pixelShaders;
/*    */   private String m_shadersBaseDirectory;
/*    */   
/*    */   public static ShaderManager getInstance()
/*    */   {
/* 30 */     return m_instance;
/*    */   }
/*    */   
/*    */   private ShaderManager() {
/* 34 */     this.m_vertexShaders = new ArrayList();
/* 35 */     this.m_pixelShaders = new ArrayList();
/* 36 */     this.m_cgContext = CgGL.cgCreateContext();
/* 37 */     this.m_shadersBaseDirectory = "";
/*    */   }
/*    */   
/*    */   public void addVertexShader(ShaderProgram shader) throws Exception {
/* 41 */     if (!this.m_vertexShaders.contains(shader)) {
/* 42 */       this.m_vertexShaders.add(shader);
/* 43 */       shader.setContext(this.m_cgContext);
/* 44 */       shader.initialize();
/*    */     }
/*    */   }
/*    */   
/*    */   public void addPixelShader(ShaderProgram shader) throws Exception {
/* 49 */     if (!this.m_pixelShaders.contains(shader)) {
/* 50 */       this.m_pixelShaders.add(shader);
/* 51 */       shader.setContext(this.m_cgContext);
/* 52 */       shader.initialize();
/*    */     }
/*    */   }
/*    */   
/*    */   public void enableVertexShader(ShaderProgram shader) throws Exception {
/* 57 */     addVertexShader(shader);
/* 58 */     shader.bind();
/* 59 */     shader.unbind();
/*    */   }
/*    */   
/*    */   public void disableVertexShader(ShaderProgram shader) {
/* 63 */     shader.unbind();
/*    */   }
/*    */   
/*    */   public void enablePixelShader(ShaderProgram shader) throws Exception {
/* 67 */     addPixelShader(shader);
/* 68 */     shader.bind();
/*    */   }
/*    */   
/*    */   public void disablePixelShader(ShaderProgram shader) {
/* 72 */     shader.unbind();
/*    */   }
/*    */   
/*    */   public ShaderProgram getVertexShaderFromName(String fileName) {
/* 76 */     for (ShaderProgram program : this.m_vertexShaders)
/* 77 */       if (program.getName().equals(fileName))
/* 78 */         return program;
/* 79 */     return null;
/*    */   }
/*    */   
/*    */   public ShaderProgram getPixelShaderFromName(String fileName) {
/* 83 */     for (ShaderProgram program : this.m_pixelShaders)
/* 84 */       if (program.getName().equals(fileName))
/* 85 */         return program;
/* 86 */     return null;
/*    */   }
/*    */   
/*    */   public String getShadersBaseDirectory() {
/* 90 */     return this.m_shadersBaseDirectory;
/*    */   }
/*    */   
/*    */   public void setShadersBaseDirectory(String shadersBaseDirectory)
/*    */   {
/* 95 */     this.m_shadersBaseDirectory = shadersBaseDirectory;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\effects\ShaderManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */