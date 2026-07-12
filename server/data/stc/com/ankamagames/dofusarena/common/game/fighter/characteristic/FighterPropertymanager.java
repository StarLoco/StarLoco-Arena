/*    */ package com.ankamagames.dofusarena.common.game.fighter.characteristic;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractPropertyManager;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.PropertyUpdateListener;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map.Entry;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FighterPropertymanager
/*    */   extends AbstractPropertyManager<FighterPropertyType>
/*    */ {
/* 20 */   protected static final Logger m_logger = Logger.getLogger(FighterPropertymanager.class);
/*    */   
/* 22 */   private PropertyUpdateListener<FighterPropertyType> m_listener = null;
/*    */   
/*    */ 
/*    */ 
/*    */   public static final byte PROPERTY_SIZE_IN_BYTE = 2;
/*    */   
/*    */ 
/*    */ 
/*    */   public void setListener(PropertyUpdateListener<FighterPropertyType> listener)
/*    */   {
/* 32 */     this.m_listener = listener;
/*    */   }
/*    */   
/*    */   private void dispatchUpdate(FighterPropertyType prop) {
/* 36 */     if (this.m_listener != null) {
/* 37 */       this.m_listener.onPropertyUpdated(prop);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] serialize()
/*    */   {
/* 47 */     ByteBuffer bb = ByteBuffer.allocate(1 + 2 * this.m_properties.size());
/* 48 */     bb.put((byte)this.m_properties.size());
/* 49 */     for (Map.Entry<FighterPropertyType, Byte> entry : this.m_properties.entrySet()) {
/* 50 */       bb.put(((FighterPropertyType)entry.getKey()).getId());
/* 51 */       bb.put(((Byte)entry.getValue()).byteValue());
/*    */     }
/* 53 */     return bb.array();
/*    */   }
/*    */   
/*    */   public void unserialize(byte[] data) {
/* 57 */     ByteBuffer bf = ByteBuffer.wrap(data);
/* 58 */     byte propertyCount = bf.get();
/* 59 */     for (byte i = 0; i < propertyCount; i = (byte)(i + 1)) {
/* 60 */       byte id = bf.get();
/* 61 */       byte propCount = bf.get();
/* 62 */       FighterPropertyType prop = FighterPropertyType.getPropertyFromId(Byte.valueOf(id));
/* 63 */       if (prop != null) {
/* 64 */         this.m_properties.put(prop, Byte.valueOf(propCount));
/*    */       } else
/* 66 */         m_logger.error("erreur à la désérialisation : property inconnue");
/*    */     }
/* 68 */     dispatchUpdate(null);
/*    */   }
/*    */   
/*    */   public byte add(FighterPropertyType type)
/*    */   {
/* 73 */     byte b = super.add(type);
/* 74 */     dispatchUpdate(type);
/* 75 */     return b;
/*    */   }
/*    */   
/*    */   public byte substract(FighterPropertyType type) {
/* 79 */     byte b = super.substract(type);
/* 80 */     dispatchUpdate(type);
/* 81 */     return b;
/*    */   }
/*    */   
/*    */   public void remove(FighterPropertyType type) {
/* 85 */     super.remove(type);
/* 86 */     dispatchUpdate(type);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 90 */     super.reset();
/* 91 */     dispatchUpdate(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fighter\characteristic\FighterPropertymanager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */