/*    */ package com.ankamagames.framework.graphics.opengl.base.matrices.transformation3D;
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
/*    */ public class HotSpot3D
/*    */   extends Position3D
/*    */ {
/*    */   public void setup(GL gl) {
/* 17 */     if (this.m_x != 0.0F || this.m_y != 0.0F || this.m_z != 0.0F)
/* 18 */       gl.glTranslatef(-this.m_x, this.m_y, this.m_z); 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\matrices\transformation3D\HotSpot3D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */