/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.characteristic;
/*    */ 
/*    */ import java.util.HashMap;
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
/*    */ public abstract class AbstractPropertyManager<P extends PropertyType>
/*    */ {
/* 20 */   protected final HashMap<P, Byte> m_properties = new HashMap<P, Byte>();
/*    */ 
/*    */   
/*    */   public abstract byte[] serialize();
/*    */   
/*    */   public abstract void unserialize(byte[] paramArrayOfbyte);
/*    */   
/*    */   public boolean isActiveProperty(P type) {
/* 28 */     Byte value = this.m_properties.get(type);
/*    */     
/* 30 */     if (value == null) {
/* 31 */       return false;
/*    */     }
/* 33 */     if (value.byteValue() == 0) {
/* 34 */       return false;
/*    */     }
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte add(P type) {
/* 46 */     if (this.m_properties.containsKey(type)) {
/* 47 */       byte b = (byte)(((Byte)this.m_properties.get(type)).byteValue() + 1);
/* 48 */       this.m_properties.put(type, Byte.valueOf(b));
/* 49 */       return b;
/*    */     } 
/* 51 */     this.m_properties.put(type, Byte.valueOf((byte)1));
/* 52 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte substract(P type) {
/* 64 */     if (this.m_properties.containsKey(type)) {
/* 65 */       byte b = (byte)(((Byte)this.m_properties.get(type)).byteValue() - 1);
/* 66 */       if (b <= 0) {
/* 67 */         this.m_properties.remove(type);
/* 68 */         return 0;
/*    */       } 
/* 70 */       this.m_properties.put(type, Byte.valueOf(b));
/* 71 */       return b;
/*    */     } 
/*    */     
/* 74 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove(P type) {
/* 82 */     this.m_properties.remove(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 89 */     this.m_properties.clear();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\characteristic\AbstractPropertyManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */