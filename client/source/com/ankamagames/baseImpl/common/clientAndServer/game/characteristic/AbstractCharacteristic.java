/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.characteristic;
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
/*    */ public abstract class AbstractCharacteristic
/*    */ {
/*    */   public abstract int value();
/*    */   
/*    */   public abstract int max();
/*    */   
/*    */   public abstract int min();
/*    */   
/*    */   public abstract void set(int paramInt);
/*    */   
/*    */   public abstract int add(int paramInt);
/*    */   
/*    */   public abstract int substract(int paramInt);
/*    */   
/*    */   public abstract int updateMaxValue(int paramInt);
/*    */   
/*    */   public abstract int updateMinValue(int paramInt);
/*    */   
/*    */   public abstract void setMax(int paramInt);
/*    */   
/*    */   public abstract void setMin(int paramInt);
/*    */   
/*    */   public abstract byte[] serialize();
/*    */   
/*    */   public abstract void unserialize(byte[] paramArrayOfbyte);
/*    */   
/*    */   public abstract void makeDefault();
/*    */   
/*    */   public boolean isPositive() {
/* 46 */     return (value() > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isNegative() {
/* 53 */     return (value() < 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isZero() {
/* 60 */     return (value() == 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isNonZero() {
/* 67 */     return (value() != 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isMaximum() {
/* 74 */     return (value() == max());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isMinimal() {
/* 81 */     return (value() == min());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void toMax() {
/* 88 */     set(max());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void toMin() {
/* 95 */     set(min());
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\characteristic\AbstractCharacteristic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */