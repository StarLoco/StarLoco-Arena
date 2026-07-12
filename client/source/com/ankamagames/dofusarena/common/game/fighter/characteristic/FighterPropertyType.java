/*    */ package com.ankamagames.dofusarena.common.game.fighter.characteristic;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.PropertyType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum FighterPropertyType
/*    */   implements PropertyType
/*    */ {
/* 12 */   INVISIBLE(1),
/* 13 */   STABILIZED(2),
/* 14 */   PETRIFIED(3),
/* 15 */   ROOTED(4);
/*    */   
/*    */   private byte m_propertyId;
/*    */ 
/*    */   
/*    */   FighterPropertyType(int propertyId) {
/* 21 */     this.m_propertyId = (byte)propertyId;
/*    */   }
/*    */   
/*    */   public byte getId() {
/* 25 */     return this.m_propertyId; } public static FighterPropertyType getPropertyFromId(Byte id) {
/*    */     byte b;
/*    */     int i;
/*    */     FighterPropertyType[] arrayOfFighterPropertyType;
/* 29 */     for (i = (arrayOfFighterPropertyType = values()).length, b = 0; b < i; ) { FighterPropertyType prop = arrayOfFighterPropertyType[b];
/* 30 */       if (prop.getId() == id.byteValue())
/* 31 */         return prop;  b++; }
/*    */     
/* 33 */     return null;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fighter\characteristic\FighterPropertyType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */