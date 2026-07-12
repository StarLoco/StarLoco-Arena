/*    */ package com.ankamagames.framework.graphics.opengl.base.states;
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
/*    */ 
/*    */ 
/*    */ public class DefaultScenePreRenderStates
/*    */   implements GLRenderStates
/*    */ {
/*    */   public void setup(GL gl) {
/* 20 */     gl.glBlendFunc(1, 771);
/*    */     
/* 22 */     gl.glEnable(3042);
/*    */     
/* 24 */     gl.glDisable(2929);
/*    */     
/* 26 */     gl.glDepthMask(false);
/*    */     
/* 28 */     gl.glActiveTexture(33984);
/*    */     
/* 30 */     gl.glTexEnvf(8960, 8704, 34160.0F);
/* 31 */     gl.glTexEnvf(8960, 34161, 8448.0F);
/* 32 */     gl.glTexEnvf(8960, 34176, 33984.0F);
/* 33 */     gl.glTexEnvf(8960, 34163, 2.0F);
/*    */     
/* 35 */     gl.glEnableClientState(32884);
/* 36 */     gl.glEnableClientState(32886);
/* 37 */     gl.glEnableClientState(32888);
/*    */     
/* 39 */     gl.glEnable(3553);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\states\DefaultScenePreRenderStates.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */