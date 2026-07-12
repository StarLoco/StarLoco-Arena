/*     */ package com.ankamagames.dofusarena.common.game.fighter;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.WordsModerator;
/*     */ import com.ankamagames.dofusarena.common.constants.DofusArenaConstants;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FighterInformation
/*     */   implements Poolable
/*     */ {
/*     */   public FighterInformation() {
/*  43 */     this.m_name = "";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  48 */     this.m_budget = 0;
/*     */   } protected static final Logger m_logger = Logger.getLogger(FighterInformation.class); protected ObjectPool m_pool; private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<FighterInformation>() { public FighterInformation makeObject() { return new FighterInformation(); } }
/*     */     ); private static final byte CURRENT_SERIALIZATION_VERSION = 1; private static final int DATA_SIZE = 6; protected byte m_breedId; public static FighterInformation checkOut(byte breedId, String name, byte skinIndex, byte sex, byte[] spellInventory, byte[] cardsInventory, short budget) {
/*  51 */     FighterInformation information = checkOut();
/*  52 */     information.m_breedId = breedId;
/*  53 */     information.m_name = name;
/*  54 */     information.m_skinIndex = skinIndex;
/*  55 */     information.m_sex = sex;
/*  56 */     information.m_spellsInventory = spellInventory;
/*  57 */     information.m_cardsInventory = cardsInventory;
/*  58 */     information.m_budget = budget;
/*  59 */     return information;
/*     */   }
/*     */   protected String m_name; protected byte m_skinIndex; protected byte m_sex; protected byte[] m_spellsInventory; protected byte[] m_cardsInventory; protected short m_budget;
/*     */   
/*     */   public static FighterInformation checkOut(byte[] data) {
/*  64 */     FighterInformation information = checkOut();
/*  65 */     information.unserialize(data);
/*  66 */     return information;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FighterInformation checkOut() {
/*     */     FighterInformation information;
/*     */     try {
/*  73 */       information = (FighterInformation)m_staticPool.borrowObject();
/*  74 */       information.m_pool = m_staticPool;
/*     */     }
/*  76 */     catch (Exception e) {
/*  77 */       information = new FighterInformation();
/*  78 */       information.m_pool = null;
/*  79 */       information.onCheckOut();
/*  80 */       m_logger.error("Erreur lors d'un checkOut sur un FighterInformation : " + e.getMessage());
/*     */     } 
/*     */     
/*  83 */     return information;
/*     */   }
/*     */   
/*     */   public void onCheckOut() {
/*  87 */     this.m_breedId = 0;
/*  88 */     this.m_name = "";
/*  89 */     this.m_skinIndex = 0;
/*  90 */     this.m_sex = 0;
/*  91 */     this.m_spellsInventory = null;
/*  92 */     this.m_cardsInventory = null;
/*  93 */     this.m_budget = 0;
/*     */   }
/*     */   
/*     */   public void onCheckIn() {
/*  97 */     this.m_breedId = 0;
/*  98 */     this.m_name = "";
/*  99 */     this.m_skinIndex = 0;
/* 100 */     this.m_sex = 0;
/* 101 */     this.m_spellsInventory = null;
/* 102 */     this.m_cardsInventory = null;
/* 103 */     this.m_budget = 0;
/*     */   }
/*     */   
/*     */   public void release() {
/* 107 */     if (this.m_pool != null) {
/*     */       try {
/* 109 */         this.m_pool.returnObject(this);
/* 110 */       } catch (Exception e) {
/* 111 */         m_logger.error("impossible de release l'objet");
/*     */       } 
/* 113 */       this.m_pool = null;
/*     */     } else {
/* 115 */       onCheckIn();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBreedId(byte breedId) {
/* 121 */     this.m_breedId = breedId;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/* 125 */     this.m_name = name;
/*     */   }
/*     */   
/*     */   public void setSkinIndex(byte skinIndex) {
/* 129 */     this.m_skinIndex = skinIndex;
/*     */   }
/*     */   
/*     */   public void setSex(byte sex) {
/* 133 */     this.m_sex = sex;
/*     */   }
/*     */   
/*     */   public byte getBreedId() {
/* 137 */     return this.m_breedId;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 141 */     return this.m_name;
/*     */   }
/*     */   
/*     */   public byte getSkinIndex() {
/* 145 */     return this.m_skinIndex;
/*     */   }
/*     */   
/*     */   public byte getSex() {
/* 149 */     return this.m_sex;
/*     */   }
/*     */   
/*     */   public byte[] getSerializedSpellsInventory() {
/* 153 */     return this.m_spellsInventory;
/*     */   }
/*     */   
/*     */   public void setSerializedSpellsInventory(byte[] spellsInventory) {
/* 157 */     this.m_spellsInventory = spellsInventory;
/*     */   }
/*     */   
/*     */   public byte[] getSerializedCardsInventory() {
/* 161 */     return this.m_cardsInventory;
/*     */   }
/*     */   
/*     */   public void setSerializedCardsInventory(byte[] cardsInventory) {
/* 165 */     this.m_cardsInventory = cardsInventory;
/*     */   }
/*     */   
/*     */   public void setBudget(short budget) {
/* 169 */     this.m_budget = budget;
/*     */   }
/*     */   
/*     */   public short getBudget() {
/* 173 */     return this.m_budget;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] serialize() {
/* 178 */     byte[] name = this.m_name.getBytes();
/* 179 */     int spellsInventoryLength = (this.m_spellsInventory != null) ? this.m_spellsInventory.length : 0;
/* 180 */     int cardsInventoryLength = (this.m_cardsInventory != null) ? this.m_cardsInventory.length : 0;
/* 181 */     byte[] data = new byte[7 + name.length + 2 + spellsInventoryLength + 2 + cardsInventoryLength];
/* 182 */     ByteBuffer bf = ByteBuffer.wrap(data);
/* 183 */     bf.put((byte)1);
/* 184 */     bf.putShort(this.m_budget);
/* 185 */     bf.put(this.m_breedId);
/* 186 */     bf.put((byte)name.length);
/* 187 */     bf.put(name);
/* 188 */     bf.put(this.m_sex);
/* 189 */     bf.put(this.m_skinIndex);
/* 190 */     if (spellsInventoryLength > 0) {
/* 191 */       bf.putShort((short)spellsInventoryLength);
/* 192 */       bf.put(this.m_spellsInventory);
/*     */     } else {
/* 194 */       bf.putShort((short)0);
/*     */     } 
/* 196 */     if (cardsInventoryLength > 0) {
/* 197 */       bf.putShort((short)cardsInventoryLength);
/* 198 */       bf.put(this.m_cardsInventory);
/*     */     } else {
/* 200 */       bf.putShort((short)0);
/*     */     } 
/* 202 */     return data;
/*     */   }

            public void unserialize(byte[] data) {
/*     */     byte[] name;
/*     */     short size;
/* 206 */     byte[] b = new byte[data.length];
/* 207 */     System.arraycopy(data, 0, b, 0, data.length);
/* 208 */     ByteBuffer buf = ByteBuffer.wrap(b);
/* 209 */     byte version = buf.get();
/* 210 */     switch (version) {
/*     */       case 1:
/* 212 */         this.m_budget = buf.getShort();
/* 213 */         this.m_breedId = buf.get();
/* 214 */         if (Breed.getBreedFromId(this.m_breedId) == null)
/* 215 */           this.m_breedId = Breed.FECA.getId(); 
/* 216 */         name = new byte[buf.get()];
/* 217 */         buf.get(name);
/* 218 */         this.m_name = new String(name);
/* 219 */         if (this.m_name.length() > 16 || 
/* 220 */           !DofusArenaConstants.FIGHTER_NAME_PATTERN.matcher(this.m_name).matches() || 
/* 221 */           !WordsModerator.validateName(this.m_name)) {
/* 222 */           this.m_name = "Noob";
/*     */         }
/* 224 */         setSex(buf.get());
/* 225 */         if (this.m_sex != 0 && this.m_sex != 1)
/* 226 */           this.m_sex = 0; 
/* 227 */         setSkinIndex(buf.get());
/* 228 */         if (this.m_skinIndex < 0 || this.m_skinIndex > 4)
/* 229 */           this.m_skinIndex = 0; 
/* 230 */         size = buf.getShort();
/* 231 */         this.m_spellsInventory = new byte[size];
/* 232 */         buf.get(this.m_spellsInventory);
/* 233 */         size = buf.getShort();
/* 234 */         this.m_cardsInventory = new byte[size];
/* 235 */         buf.get(this.m_cardsInventory);
/*     */         return;
/*     */     } 
/*     */     
/* 239 */     m_logger.error("Version de FighterInformation non traitée : " + version + " version actuelle : " + '\001');
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fighter\FighterInformation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */