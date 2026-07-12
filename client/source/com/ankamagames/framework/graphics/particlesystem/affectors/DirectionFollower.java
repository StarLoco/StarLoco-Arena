/*    */ package com.ankamagames.framework.graphics.particlesystem.affectors;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.particlesystem.Particle;
/*    */ import com.ankamagames.framework.graphics.particlesystem.ParticleSystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DirectionFollower
/*    */   extends BaseAffector
/*    */ {
/* 16 */   private static double AXIAL_X_VECTOR = 1.0D;
/* 17 */   private static double AXIAL_Y_VECTOR = 0.5D;
/* 18 */   private static double AXIAL_Z_VECTOR = 0.2083333283662796D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void affect(Particle mesh, ParticleSystem particleSystem) {
/* 28 */     Object[] state = mesh.getState(this);
/*    */     
/* 30 */     Double[] currentCoord = { Double.valueOf(mesh.getX() + particleSystem.getX()), Double.valueOf(mesh.getY() + particleSystem.getY()), Double.valueOf(mesh.getZ() + particleSystem.getZ()) };
/*    */     
/* 32 */     if (state != null) {
/*    */       
/* 34 */       Double[] lastCoord = (Double[])state;
/*    */       
/* 36 */       double dX = currentCoord[0].doubleValue() - lastCoord[0].doubleValue();
/* 37 */       double dY = currentCoord[1].doubleValue() - lastCoord[1].doubleValue();
/* 38 */       double dZ = currentCoord[2].doubleValue() - lastCoord[2].doubleValue();
/*    */       
/* 40 */       double rX = (dX - dY) * AXIAL_X_VECTOR;
/* 41 */       double rY = -(dX + dY) * AXIAL_Y_VECTOR + dZ * AXIAL_Z_VECTOR;
/*    */       
/* 43 */       if (rY > 0.0D) {
/* 44 */         mesh.getMesh().setRotation(-((float)Math.toDegrees(Math.atan(rX / rY))));
/*    */       }
/* 46 */       else if (rY < 0.0D) {
/* 47 */         mesh.getMesh().setRotation(-((float)Math.toDegrees(Math.atan(rX / rY) - Math.PI)));
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     mesh.saveState(this, (Object[])currentCoord);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     return "Direction Follower";
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\affectors\DirectionFollower.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */