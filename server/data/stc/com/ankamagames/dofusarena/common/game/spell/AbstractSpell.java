/*     */ package com.ankamagames.dofusarena.common.game.spell;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.spell.BasicSpell;
/*     */ import com.ankamagames.framework.ai.criteria.Criterion;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.GrowingArray;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public abstract class AbstractSpell
/*     */   extends BasicSpell
/*     */   implements EffectContainer, InventoryContent
/*     */ {
/*  27 */   protected GrowingArray<Effect> m_effects = new GrowingArray();
/*     */   
/*     */   private final byte m_actionPoints;
/*     */   private final int m_breedId;
/*     */   private final byte m_castMaxPerTarget;
/*     */   private final byte m_castMaxPerTurn;
/*     */   private final byte m_castInterval;
/*     */   private final boolean m_testLineOfSight;
/*     */   private final boolean m_castOnlyInLine;
/*     */   private final boolean m_testFreeCell;
/*     */   private final byte m_rangeMin;
/*     */   private final byte m_rangeMax;
/*     */   private final int m_value;
/*     */   private final int m_target;
/*     */   private final List<Criterion> m_castCriterions;
/*  42 */   private boolean m_canBeCritical = false;
/*     */   
/*     */   public AbstractSpell(int id, int breedId, byte actionPoints, byte castMaxPerPlayer, byte castMaxPerTurn, byte castInterval, boolean lineOfSight, boolean castOnlyInLine, byte rangeMin, byte rangeMax, int goldValue, int target, boolean testFreeCell, List<Criterion> criterion)
/*     */   {
/*  46 */     super(id);
/*  47 */     this.m_breedId = breedId;
/*  48 */     this.m_actionPoints = actionPoints;
/*  49 */     this.m_castMaxPerTarget = castMaxPerPlayer;
/*  50 */     this.m_castMaxPerTurn = castMaxPerTurn;
/*  51 */     this.m_castInterval = castInterval;
/*  52 */     this.m_testLineOfSight = lineOfSight;
/*  53 */     this.m_castOnlyInLine = castOnlyInLine;
/*  54 */     this.m_rangeMin = ((byte)Math.max(0, Math.min(rangeMin, rangeMax)));
/*  55 */     this.m_rangeMax = ((byte)Math.max(0, Math.max(rangeMin, rangeMax)));
/*  56 */     this.m_value = goldValue;
/*  57 */     this.m_target = target;
/*  58 */     this.m_testFreeCell = testFreeCell;
/*  59 */     this.m_castCriterions = criterion;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addEffect(Effect effect)
/*     */   {
/*  66 */     this.m_effects.add(effect);
/*  67 */     if (effect.checkFlags(1L))
/*  68 */       this.m_canBeCritical = true;
/*     */   }
/*     */   
/*     */   public void addEffects(Effect[] effects) { Effect[] arrayOfEffect;
/*  72 */     int j = (arrayOfEffect = effects).length; for (int i = 0; i < j; i++) { Effect effect = arrayOfEffect[i];
/*  73 */       addEffect(effect);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setEffects(Effect[] effects) {
/*  78 */     this.m_canBeCritical = false;
/*  79 */     this.m_effects.clear();
/*  80 */     addEffects(effects);
/*     */   }
/*     */   
/*     */   public Effect getEffectById(int effectId)
/*     */   {
/*  85 */     for (Effect eff : this.m_effects) {
/*  86 */       if (eff.getEffectId() == effectId) {
/*  87 */         return eff;
/*     */       }
/*     */     }
/*  90 */     return null;
/*     */   }
/*     */   
/*     */   public GrowingArray<Effect> getEffects() {
/*  94 */     return this.m_effects;
/*     */   }
/*     */   
/*     */   public List<Criterion> getCastCriterions() {
/*  98 */     return this.m_castCriterions;
/*     */   }
/*     */   
/*     */   public boolean canCanBeCritical() {
/* 102 */     return this.m_canBeCritical;
/*     */   }
/*     */   
/*     */   public boolean castOnlyInLine()
/*     */   {
/* 107 */     return this.m_castOnlyInLine;
/*     */   }
/*     */   
/*     */   public long getEffectContainerId() {
/* 111 */     return getId();
/*     */   }
/*     */   
/*     */   public int getContainerType() {
/* 115 */     return 13;
/*     */   }
/*     */   
/*     */   public Iterator<Effect> iterator() {
/* 119 */     return this.m_effects.iterator();
/*     */   }
/*     */   
/*     */   public int getBreedId() {
/* 123 */     return this.m_breedId;
/*     */   }
/*     */   
/*     */   public byte getActionPoints() {
/* 127 */     return this.m_actionPoints;
/*     */   }
/*     */   
/*     */   public byte getCastMaxPerTarget() {
/* 131 */     return this.m_castMaxPerTarget;
/*     */   }
/*     */   
/*     */   public byte getCastMaxPerTurn() {
/* 135 */     return this.m_castMaxPerTurn;
/*     */   }
/*     */   
/*     */   public byte getMinCastInterval() {
/* 139 */     return this.m_castInterval;
/*     */   }
/*     */   
/*     */   public boolean hasToTestLineOfSight() {
/* 143 */     return this.m_testLineOfSight;
/*     */   }
/*     */   
/*     */   public boolean hasToTestFreeCell() {
/* 147 */     return this.m_testFreeCell;
/*     */   }
/*     */   
/*     */   public byte getRangeMin() {
/* 151 */     return this.m_rangeMin;
/*     */   }
/*     */   
/*     */   public byte getRangeMax() {
/* 155 */     return this.m_rangeMax;
/*     */   }
/*     */   
/*     */   public int getValue() {
/* 159 */     return this.m_value;
/*     */   }
/*     */   
/*     */   public byte getCastInterval() {
/* 163 */     return this.m_castInterval;
/*     */   }
/*     */   
/*     */   public int getTarget() {
/* 167 */     return this.m_target;
/*     */   }
/*     */   
/*     */   public void release() {}
/*     */   
/*     */   public long getUniqueId()
/*     */   {
/* 174 */     return getId();
/*     */   }
/*     */   
/*     */   public int getReferenceId() {
/* 178 */     return getId();
/*     */   }
/*     */   
/*     */   public byte[] serialize() {
/* 182 */     byte[] b = new byte[4];
/* 183 */     ByteBuffer.wrap(b).putInt(getId());
/* 184 */     return b;
/*     */   }
/*     */   
/*     */   public boolean unserialize(ByteBuffer buf) {
/* 188 */     throw new UnsupportedOperationException("AbstractSpell can't be unserialized. Need to be get from AbstractReferenceCoachCardManager");
/*     */   }
/*     */   
/*     */   public short getQuantity() {
/* 192 */     return 1;
/*     */   }
/*     */   
/*     */   public void setQuantity(short quantity) {
/* 196 */     throw new UnsupportedOperationException("Spell can't be stacked");
/*     */   }
/*     */   
/*     */   public void updateQuantity(short quantityUpdate) {
/* 200 */     throw new UnsupportedOperationException("Spell can't be stacked");
/*     */   }
/*     */   
/*     */   public short getStackMaximumHeight() {
/* 204 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public InventoryContent getCopy()
/*     */   {
/*     */     try
/*     */     {
/* 214 */       return (AbstractSpell)clone();
/*     */     } catch (CloneNotSupportedException e) {
/* 216 */       throw new RuntimeException("Unable to copy AbstractSpell", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public InventoryContent getClone()
/*     */   {
/*     */     try
/*     */     {
/* 225 */       return (AbstractSpell)clone();
/*     */     } catch (CloneNotSupportedException e) {
/* 227 */       throw new RuntimeException("Unable to clone AbstractSpell", e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\spell\AbstractSpell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */