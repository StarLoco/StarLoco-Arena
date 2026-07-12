/*    */ package com.ankamagames.baseImpl.graphics.alea.display;
/*    */ 
/*    */ import com.ankamagames.graphics.isometric.IsoCamera;
/*    */ import com.ankamagames.graphics.isometric.IsoWorldScene;
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
/*    */ 
/*    */ public class AleaIsoCamera
/*    */   extends IsoCamera
/*    */ {
/* 21 */   private int m_cameraGroupInstanceId = 0;
/* 22 */   private int m_cameraGroupLevel = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AleaIsoCamera() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AleaIsoCamera(double worldX, double worldY, double altitude, IsoWorldScene scene) {
/* 37 */     super(worldX, worldY, altitude, scene);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCameraGroupInstanceId(int cameraGroupInstanceId) {
/* 45 */     this.m_cameraGroupInstanceId = cameraGroupInstanceId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCameraGroupInstanceId() {
/* 54 */     return this.m_cameraGroupInstanceId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCameraGroupLevel(int cameraGroupLevel) {
/* 61 */     this.m_cameraGroupLevel = cameraGroupLevel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCameraGroupLevel() {
/* 70 */     return this.m_cameraGroupLevel;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\display\AleaIsoCamera.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */