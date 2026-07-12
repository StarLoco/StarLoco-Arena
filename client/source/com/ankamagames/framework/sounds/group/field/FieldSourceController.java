/*    */ package com.ankamagames.framework.sounds.group.field;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.maths.Vector3;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldSourceController
/*    */   extends Vector3
/*    */ {
/*    */   private FieldGroup m_owner;
/*    */   private int m_currentHashValue;
/*    */   
/*    */   public FieldSourceController() {}
/*    */   
/*    */   public FieldSourceController(Vector3 v) {
/* 31 */     super(v);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FieldSourceController(double[] v) {
/* 39 */     super(v);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FieldSourceController(double x, double y, double z) {
/* 49 */     super(x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FieldGroup getOwner() {
/* 57 */     return this.m_owner;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOwner(FieldGroup owner) {
/* 65 */     this.m_owner = owner;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Vector3 toIsometricListenerView(Vector3 listenerPos) {
/* 74 */     float px = getXf() - listenerPos.getXf();
/* 75 */     float py = getYf() - listenerPos.getYf();
/*    */     
/* 77 */     float rx = px - py;
/* 78 */     float ry = px + py;
/* 79 */     float rz = getZf();
/*    */     
/* 81 */     return new Vector3(rx, ry, rz);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCurrentHashValue() {
/* 90 */     return this.m_currentHashValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCurrentHashValue(int currentHashValue) {
/* 98 */     this.m_currentHashValue = currentHashValue;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\sounds\group\field\FieldSourceController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */