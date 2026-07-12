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
/*    */   private FighterPropertyType(int propertyId)
/*    */   {
/* 21 */     this.m_propertyId = ((byte)propertyId);
/*    */   }
/*    */   
/*    */ 
/* 25 */   public byte getId() { return this.m_propertyId; }
/*    */   
/*    */   public static FighterPropertyType getPropertyFromId(Byte id) {
/*    */     FighterPropertyType[] arrayOfFighterPropertyType;
/* 29 */     int j = (arrayOfFighterPropertyType = values()).length; for (int i = 0; i < j; i++) { FighterPropertyType prop = arrayOfFighterPropertyType[i];
/* 30 */       if (prop.getId() == id.byteValue())
/* 31 */         return prop;
/*    */     }
/* 33 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fighter\characteristic\FighterPropertyType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */