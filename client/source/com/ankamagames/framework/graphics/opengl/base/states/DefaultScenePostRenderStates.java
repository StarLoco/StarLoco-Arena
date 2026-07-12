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
/*    */ public class DefaultScenePostRenderStates
/*    */   implements GLRenderStates
/*    */ {
/*    */   public void setup(GL gl) {
/* 18 */     gl.glDisable(3042);
/* 19 */     gl.glDisable(2929);
/* 20 */     gl.glDepthMask(false);
/* 21 */     gl.glDisable(34037);
/* 22 */     gl.glDisable(3553);
/* 23 */     gl.glDisableClientState(32884);
/* 24 */     gl.glDisableClientState(32886);
/* 25 */     gl.glDisableClientState(32888);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\states\DefaultScenePostRenderStates.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */