/*     */ package com.ankamagames.dofusarena.common.game.card;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContent;
/*     */ import com.ankamagames.dofusarena.common.constants.FighterCardType;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.GrowingArray;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.iterators.MergedIterator;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.BitSet;
/*     */ import java.util.Iterator;
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
/*     */ public abstract class AbstractFighterCard
/*     */   implements EffectContainer, InventoryContent
/*     */ {
/*  29 */   protected static final Logger m_logger = Logger.getLogger(AbstractFighterCard.class);
/*     */   
/*  31 */   private static final BitSet m_useTimeTrigger = new BitSet();
/*  32 */   private static final BitSet m_equippementTimeTrigger = new BitSet();
/*     */   
/*  34 */   static { m_useTimeTrigger.set(2001);
/*  35 */     m_equippementTimeTrigger.set(2002);
/*  36 */     m_equippementTimeTrigger.set(2003);
/*     */   }
/*     */   
/*     */ 
/*  40 */   private final GrowingArray<Effect> m_useTimeEffects = new GrowingArray();
/*  41 */   private final GrowingArray<Effect> m_equippementTimeEffects = new GrowingArray();
/*     */   protected final int m_id;
/*     */   protected final int m_actionPoints;
/*     */   protected final boolean m_useOnlyInLine;
/*     */   protected final int m_rangeMin;
/*     */   protected final int m_rangeMax;
/*     */   protected final boolean m_useTestLineOfSight;
/*     */   protected final boolean m_useTestCellFree;
/*     */   protected final int m_value;
/*     */   protected final boolean m_canUseWhenCarried;
/*     */   protected final boolean m_canUseWhenCarrying;
/*     */   protected boolean m_canBeUsed;
/*     */   private final FighterCardType m_type;
/*     */   
/*     */   protected AbstractFighterCard(int id, FighterCardType type, int actionPoints, boolean useOnlyInLine, int rangeMin, int rangeMax, boolean useTestLineOfSight, boolean useTestCellFree, int goldValue, boolean canUseWhenCarried, boolean canUseWhenCarrying)
/*     */   {
/*  57 */     this.m_id = id;
/*  58 */     this.m_type = type;
/*  59 */     this.m_actionPoints = actionPoints;
/*  60 */     this.m_useOnlyInLine = useOnlyInLine;
/*  61 */     this.m_rangeMin = rangeMin;
/*  62 */     this.m_rangeMax = rangeMax;
/*  63 */     this.m_useTestLineOfSight = useTestLineOfSight;
/*  64 */     this.m_useTestCellFree = useTestCellFree;
/*  65 */     this.m_value = goldValue;
/*  66 */     this.m_canUseWhenCarried = canUseWhenCarried;
/*  67 */     this.m_canUseWhenCarrying = canUseWhenCarrying;
/*  68 */     this.m_canBeUsed = false;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  72 */     return this.m_id;
/*     */   }
/*     */   
/*     */   public FighterCardType getType() {
/*  76 */     return this.m_type;
/*     */   }
/*     */   
/*     */   public int getValue() {
/*  80 */     return this.m_value;
/*     */   }
/*     */   
/*     */   public void release() {}
/*     */   
/*     */   public long getUniqueId() {
/*  86 */     return this.m_id;
/*     */   }
/*     */   
/*     */   public long getEffectContainerId() {
/*  90 */     return this.m_id;
/*     */   }
/*     */   
/*     */   public int getReferenceId() {
/*  94 */     return this.m_id;
/*     */   }
/*     */   
/*     */   public byte[] serialize() {
/*  98 */     byte[] b = new byte[4];
/*  99 */     ByteBuffer.wrap(b).putInt(this.m_id);
/* 100 */     return b;
/*     */   }
/*     */   
/*     */   public boolean unserialize(ByteBuffer buf) {
/* 104 */     throw new UnsupportedOperationException("AbstractFighterCard is static and can't be unserialized. Need to be get from a provider.");
/*     */   }
/*     */   
/*     */   public short getQuantity() {
/* 108 */     return 1;
/*     */   }
/*     */   
/*     */   public void setQuantity(short quantity) {
/* 112 */     throw new UnsupportedOperationException("FIghterCard can't be stacked");
/*     */   }
/*     */   
/*     */   public void updateQuantity(short quantityUpdate) {
/* 116 */     throw new UnsupportedOperationException("FIghterCard can't be stacked");
/*     */   }
/*     */   
/*     */   public short getStackMaximumHeight() {
/* 120 */     return 1;
/*     */   }
/*     */   
/*     */   public void addEffect(Effect effect)
/*     */   {
/* 125 */     if ("FIGHTER_CARD_USE".equals(effect.getContainerType().trim())) {
/* 126 */       this.m_useTimeEffects.add(effect);
/* 127 */       return;
/*     */     }
/*     */     
/* 130 */     if ("FIGHTER_CARD_EQUIP".equals(effect.getContainerType().trim())) {
/* 131 */       this.m_equippementTimeEffects.add(effect);
/* 132 */       return;
/*     */     }
/* 134 */     m_logger.error("Impossible d'ajouter un effet pour la carte " + this.m_id + " : type de parent invalide : " + effect.getContainerType());
/*     */   }
/*     */   
/*     */   public void addEffects(Effect[] effects) { Effect[] arrayOfEffect;
/* 138 */     int j = (arrayOfEffect = effects).length; for (int i = 0; i < j; i++) { Effect e = arrayOfEffect[i];
/* 139 */       addEffect(e);
/*     */     }
/*     */   }
/*     */   
/* 143 */   public int getContainerType() { return 12; }
/*     */   
/*     */   public Iterator<Effect> iterator()
/*     */   {
/* 147 */     return new MergedIterator(new Iterator[] { this.m_useTimeEffects.iterator(), this.m_equippementTimeEffects.iterator() });
/*     */   }
/*     */   
/*     */   public boolean isUsable() {
/* 151 */     return this.m_useTimeEffects.size() > 0;
/*     */   }
/*     */   
/*     */   public static int getSerializationAverageLength() {
/* 155 */     return 4;
/*     */   }
/*     */   
/*     */   public Iterable<Effect> effectsAtEquippementTimeIterator() {
/* 159 */     return this.m_equippementTimeEffects;
/*     */   }
/*     */   
/*     */   public Iterable<Effect> effectsAtUseTimeIterator() {
/* 163 */     return this.m_useTimeEffects;
/*     */   }
/*     */   
/*     */   public int getActionPoints() {
/* 167 */     return this.m_actionPoints;
/*     */   }
/*     */   
/*     */   public boolean useOnlyInLine() {
/* 171 */     return this.m_useOnlyInLine;
/*     */   }
/*     */   
/*     */   public int getRangeMin() {
/* 175 */     return this.m_rangeMin;
/*     */   }
/*     */   
/*     */   public int getRangeMax() {
/* 179 */     return this.m_rangeMax;
/*     */   }
/*     */   
/*     */   public boolean hasToTestLineOfSight() {
/* 183 */     return this.m_useTestLineOfSight;
/*     */   }
/*     */   
/*     */   public boolean hasToTestCellFree() {
/* 187 */     return this.m_useTestCellFree;
/*     */   }
/*     */   
/*     */   public boolean canUseWhenCarried() {
/* 191 */     return this.m_canUseWhenCarried;
/*     */   }
/*     */   
/*     */   public boolean canUseWhenCarrying() {
/* 195 */     return this.m_canUseWhenCarrying;
/*     */   }
/*     */   
/*     */   public GrowingArray<Effect> getUseTimeEffects() {
/* 199 */     return this.m_useTimeEffects;
/*     */   }
/*     */   
/*     */   public GrowingArray<Effect> getEquippementTimeEffects() {
/* 203 */     return this.m_equippementTimeEffects;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public InventoryContent getCopy()
/*     */   {
/*     */     try
/*     */     {
/* 213 */       return (AbstractFighterCard)clone();
/*     */     } catch (CloneNotSupportedException e) {
/* 215 */       throw new RuntimeException("Unable to copy AbstractFighterCard", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public InventoryContent getClone()
/*     */   {
/*     */     try
/*     */     {
/* 224 */       return (AbstractFighterCard)clone();
/*     */     } catch (CloneNotSupportedException e) {
/* 226 */       throw new RuntimeException("Unable to clone AbstractFighterCard", e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\card\AbstractFighterCard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */