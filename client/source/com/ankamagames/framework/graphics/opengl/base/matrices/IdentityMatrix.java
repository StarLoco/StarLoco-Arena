/*    */ package com.ankamagames.framework.graphics.opengl.base.matrices;
/*    */ 
/*    */ import javax.media.opengl.GL;
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
/*    */ public class IdentityMatrix
/*    */   implements GLMatrix
/*    */ {
/* 17 */   private static IdentityMatrix m_instance = new IdentityMatrix();
/*    */   public static GLMatrix getInstance() {
/* 19 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setup(GL gl) {
/* 35 */     gl.glLoadIdentity();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\matrices\IdentityMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */