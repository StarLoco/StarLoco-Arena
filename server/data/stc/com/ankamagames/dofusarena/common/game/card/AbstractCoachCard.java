/*     */ package com.ankamagames.dofusarena.common.game.card;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContent;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractCoachCard<RC extends AbstractReferenceCoachCard>
/*     */   implements InventoryContent, Poolable
/*     */ {
/*  21 */   protected static final Logger m_logger = Logger.getLogger(AbstractCoachCard.class);
/*     */   
/*     */   protected RC m_referenceCard;
/*     */   
/*     */   protected final AbstractReferenceCoachCardManager<RC> m_referenceCoachCardManager;
/*     */   
/*     */   private short m_quantity;
/*     */   
/*     */   protected long m_uid;
/*     */   
/*     */   protected byte m_flags;
/*     */   public static final byte FLAG_LOCKED = 1;
/*     */   public static final byte FLAG_CURSED = 2;
/*     */   
/*     */   protected AbstractCoachCard(AbstractReferenceCoachCardManager<RC> coachCardManager)
/*     */   {
/*  37 */     this.m_referenceCoachCardManager = coachCardManager;
/*     */   }
/*     */   
/*     */   public short getQuantity() {
/*  41 */     return this.m_quantity;
/*     */   }
/*     */   
/*     */   public void setQuantity(short quantity) {
/*  45 */     this.m_quantity = ((short)Math.max(0, quantity));
/*     */   }
/*     */   
/*     */   public void updateQuantity(short quantityUpdate) {
/*  49 */     this.m_quantity = ((short)Math.max(0, this.m_quantity + quantityUpdate));
/*     */   }
/*     */   
/*     */   public final short getStackMaximumHeight() {
/*  53 */     return Short.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public long getUniqueId() {
/*  57 */     return this.m_uid;
/*     */   }
/*     */   
/*     */   public int getReferenceId() {
/*     */     try {
/*  62 */       return this.m_referenceCard.getReferenceId();
/*     */     } catch (Exception e) {
/*  64 */       e.printStackTrace(); }
/*  65 */     return 0;
/*     */   }
/*     */   
/*     */   public RC getReferenceCard()
/*     */   {
/*  70 */     return this.m_referenceCard;
/*     */   }
/*     */   
/*     */   public byte[] serialize() {
/*  74 */     byte[] b = new byte[13];
/*  75 */     ByteBuffer bf = ByteBuffer.wrap(b);
/*  76 */     bf.putInt(this.m_referenceCard.getId());
/*  77 */     bf.putLong(this.m_uid);
/*  78 */     bf.put(this.m_flags);
/*     */     
/*  80 */     return b;
/*     */   }
/*     */   
/*     */   public boolean unserialize(ByteBuffer buf) {
/*  84 */     boolean bOk = true;
/*  85 */     int cardId = buf.getInt();
/*  86 */     this.m_referenceCard = this.m_referenceCoachCardManager.get(cardId);
/*  87 */     if (this.m_referenceCard == null) {
/*  88 */       m_logger.error("Unable to unserialize AbstractCoachCard : referenceCard not found : " + cardId + " (" + this + ")");
/*  89 */       bOk = false;
/*     */     }
/*  91 */     this.m_uid = buf.getLong();
/*  92 */     this.m_flags = buf.get();
/*  93 */     return bOk;
/*     */   }
/*     */   
/*     */   public void onCheckOut() {
/*  97 */     this.m_referenceCard = null;
/*  98 */     this.m_quantity = 0;
/*  99 */     this.m_uid = 0L;
/* 100 */     this.m_flags = 0;
/*     */   }
/*     */   
/*     */   public void onCheckIn() {
/* 104 */     this.m_referenceCard = null;
/* 105 */     this.m_quantity = 0;
/* 106 */     this.m_uid = 0L;
/* 107 */     this.m_flags = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getSerializedSizeEstimate()
/*     */   {
/* 115 */     return 13;
/*     */   }
/*     */   
/*     */   public void setLocked(boolean bLocked) {
/* 119 */     if (bLocked) {
/* 120 */       this.m_flags = ((byte)(this.m_flags | 0x1));
/*     */     } else
/* 122 */       this.m_flags = ((byte)(this.m_flags & 0xFFFFFFFE));
/*     */   }
/*     */   
/*     */   public boolean isLocked() {
/* 126 */     return (this.m_flags & 0x1) != 0;
/*     */   }
/*     */   
/*     */   public void setCursed(boolean bCursed) {
/* 130 */     if (bCursed) {
/* 131 */       this.m_flags = ((byte)(this.m_flags | 0x2));
/*     */     } else
/* 133 */       this.m_flags = ((byte)(this.m_flags & 0xFFFFFFFD));
/*     */   }
/*     */   
/*     */   public boolean isCursed() {
/* 137 */     return (this.m_flags & 0x2) != 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\card\AbstractCoachCard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */