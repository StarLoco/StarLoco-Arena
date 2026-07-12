/*    */ package com.ankamagames.graphics.isometric.highlight;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.states.GLRenderStates;
/*    */ import javax.media.opengl.GL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HighLightMeshPreRenderStates
/*    */   implements GLRenderStates
/*    */ {
/*    */   public void setup(GL gl)
/*    */   {
/* 15 */     gl.glBlendFunc(770, 771);
/* 16 */     gl.glTexEnvf(8960, 34163, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\highlight\HighLightMeshPreRenderStates.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */