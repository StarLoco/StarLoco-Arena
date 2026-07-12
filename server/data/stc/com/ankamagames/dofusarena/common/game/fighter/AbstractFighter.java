/*     */ package com.ankamagames.dofusarena.common.game.fighter;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContextForUniqueEffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.State;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffectManager;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFight;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FighterSpecialEventListener;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FightingTeam;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.TeamMate;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ArrayInventory;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.StackInventory;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryObserver;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.part.basicImpl.FourSidedPartLocalisator;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.DiceRoll;
/*     */ import com.ankamagames.dofusarena.common.game.card.AbstractFighterCard;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
/*     */ import com.ankamagames.dofusarena.common.game.fight.AbstractFight;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristic;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterPropertyType;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterPropertymanager;
/*     */ import com.ankamagames.dofusarena.common.game.spell.AbstractSpell;
/*     */ import com.ankamagames.dofusarena.common.game.spell.SpellCastHistory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import gnu.trove.TIntObjectIterator;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractFighter
/*     */   implements BasicFighter, Poolable, InventoryObserver
/*     */ {
/*  50 */   private static int BINARY_AVERAGE_LENGTH = 101;
/*     */   
/*  52 */   protected static final Logger m_logger = Logger.getLogger(AbstractFighter.class);
/*     */   
/*     */   protected ObjectPool m_pool;
/*     */   protected long m_id;
/*     */   protected Breed m_breed;
/*  57 */   protected String m_name = "";
/*     */   protected byte m_skinIndex;
/*     */   protected byte m_sex;
/*     */   protected TeamMate<? extends AbstractFighter> m_teamMate;
/*  61 */   protected Direction8 m_direction = Direction8.SOUTH_EAST;
/*     */   
/*     */ 
/*     */   protected short m_value;
/*     */   
/*  66 */   protected final RunningEffectManager m_runningEffectManager = new RunningEffectManager();
/*  67 */   private final Point3 m_position = new Point3();
/*  68 */   protected final FourSidedPartLocalisator m_partLocalisator = new FourSidedPartLocalisator();
/*  69 */   protected final TIntObjectHashMap<AbstractCharacteristic> m_characteristics = new TIntObjectHashMap();
/*  70 */   protected final FighterPropertymanager m_properties = new FighterPropertymanager();
/*  71 */   protected final SpellCastHistory m_spellCastHistory = new SpellCastHistory();
/*  72 */   protected boolean m_isDead = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractFighter m_carriedFighter;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractFighter m_carriedByFighter;
/*     */   
/*     */ 
/*     */ 
/*     */   private FighterSpecialEventListener<AbstractFighter> m_specialEventListener;
/*     */   
/*     */ 
/*     */ 
/*  90 */   protected boolean m_checkin = false;
/*     */   
/*  92 */   public boolean isCheckin() { return this.m_checkin; }
/*     */   
/*     */ 
/*     */   public void onCheckOut()
/*     */   {
/*  97 */     this.m_checkin = false;
/*  98 */     this.m_id = 0L;
/*  99 */     this.m_breed = Breed.NONE;
/* 100 */     this.m_name = "";
/* 101 */     this.m_skinIndex = 0;
/* 102 */     this.m_sex = 0;
/* 103 */     this.m_teamMate = null;
/* 104 */     this.m_runningEffectManager.clear();
/* 105 */     this.m_position.reset();
/*     */     
/* 107 */     this.m_direction = Direction8.SOUTH_EAST;
/* 108 */     this.m_value = 0;
/* 109 */     this.m_partLocalisator.reset();
/* 110 */     this.m_spellCastHistory.reset();
/* 111 */     this.m_isDead = false;
/*     */     
/* 113 */     this.m_carriedByFighter = null;
/* 114 */     this.m_carriedFighter = null;
/* 115 */     this.m_properties.reset();
/*     */   }
/*     */   
/*     */   public void onCheckIn() {
/* 119 */     this.m_checkin = true;
/*     */     
/* 121 */     this.m_id = 0L;
/* 122 */     this.m_breed = Breed.NONE;
/* 123 */     this.m_name = "";
/* 124 */     this.m_skinIndex = 0;
/* 125 */     this.m_sex = 0;
/*     */     
/* 127 */     this.m_teamMate = null;
/*     */     
/* 129 */     this.m_runningEffectManager.destroyAll();
/*     */     
/* 131 */     this.m_position.reset();
/* 132 */     this.m_direction = null;
/* 133 */     this.m_value = 0;
/*     */     
/* 135 */     this.m_partLocalisator.reset();
/*     */     
/* 137 */     this.m_spellCastHistory.reset();
/* 138 */     this.m_isDead = false;
/*     */     
/* 140 */     this.m_carriedByFighter = null;
/* 141 */     this.m_carriedFighter = null;
/* 142 */     this.m_properties.reset();
/*     */   }
/*     */   
/*     */   protected AbstractFighter() { FighterCharacteristicType[] arrayOfFighterCharacteristicType;
/* 146 */     int j = (arrayOfFighterCharacteristicType = FighterCharacteristicType.values()).length; for (int i = 0; i < j; i++) { FighterCharacteristicType c = arrayOfFighterCharacteristicType[i];
/* 147 */       this.m_characteristics.put(c.getId(), new FighterCharacteristic(c, c.getLowerBound(), c.getUpperBound()));
/*     */     }
/*     */   }
/*     */   
/*     */   public void release()
/*     */   {
/* 153 */     if (this.m_pool != null) {
/*     */       try {
/* 155 */         this.m_pool.returnObject(this);
/*     */       } catch (Exception e) {
/* 157 */         m_logger.error("ne peut arriver normalement");
/*     */       }
/* 159 */       this.m_pool = null;
/*     */     } else {
/* 161 */       onCheckIn();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 174 */     return this.m_id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(long id)
/*     */   {
/* 183 */     this.m_id = id;
/*     */   }
/*     */   
/*     */   public abstract boolean isSummoned();
/*     */   
/*     */   public abstract List<? extends AbstractFighter> getChilds();
/*     */   
/*     */   public abstract AbstractFighter getFather();
/*     */   
/*     */   public int getChildsCount() {
/* 193 */     return getChilds().size();
/*     */   }
/*     */   
/*     */   public int getSummoningsCount() {
/* 197 */     List<? extends AbstractFighter> childs = getChilds();
/* 198 */     int summonings = 0;
/* 199 */     for (int i = 0; i < childs.size(); i++)
/* 200 */       if (((AbstractFighter)childs.get(i)).isSummoned())
/* 201 */         summonings++;
/* 202 */     return summonings;
/*     */   }
/*     */   
/*     */   public abstract Breed getBreed();
/*     */   
/*     */   public void setBreedFromId(byte breedId) {
/* 208 */     this.m_breed = Breed.getBreedFromId(breedId);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 212 */     return this.m_name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/* 216 */     this.m_name = name;
/*     */   }
/*     */   
/*     */ 
/*     */   public void changeLook(int lookId) {}
/*     */   
/*     */ 
/*     */   public void adaptLook(int lookId) {}
/*     */   
/*     */   public void restoreLastLook() {}
/*     */   
/*     */   public byte getSkinIndex()
/*     */   {
/* 229 */     return this.m_skinIndex;
/*     */   }
/*     */   
/*     */   public void setSkinIndex(byte skinIndex) {
/* 233 */     this.m_skinIndex = skinIndex;
/*     */   }
/*     */   
/*     */   public byte getSex() {
/* 237 */     return this.m_sex;
/*     */   }
/*     */   
/*     */   public void setSex(byte sex) {
/* 241 */     this.m_sex = sex;
/*     */   }
/*     */   
/*     */   public void setBreedAndSex(byte breedId, byte sex) {
/* 245 */     this.m_breed = Breed.getBreedFromId(breedId);
/* 246 */     this.m_sex = sex;
/*     */   }
/*     */   
/*     */   public void setPosition(Point3 pos) {
/* 250 */     setPosition(pos.getX(), pos.getY(), pos.getZ());
/*     */   }
/*     */   
/*     */   public void setPosition(int x, int y, short alt) {
/* 254 */     this.m_position.setX(x);
/* 255 */     this.m_position.setY(y);
/* 256 */     this.m_position.setZ(alt);
/*     */     
/* 258 */     if (this.m_carriedFighter != null)
/* 259 */       this.m_carriedFighter.setPosition(x, y, (short)(alt + getStandardHeight()));
/*     */   }
/*     */   
/*     */   public Point3 getPosition() {
/* 263 */     return this.m_position;
/*     */   }
/*     */   
/*     */   public Direction8 getDirection() {
/* 267 */     return this.m_direction;
/*     */   }
/*     */   
/*     */   public void setDirection(Direction direction) {
/* 271 */     this.m_direction = ((Direction8)direction);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getHeight()
/*     */   {
/* 279 */     return (short)(6 + (this.m_carriedFighter != null ? this.m_carriedFighter.getHeight() : 0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getStandardHeight()
/*     */   {
/* 287 */     return 6;
/*     */   }
/*     */   
/*     */   public short getJumpMaxAscendingHeight() {
/* 291 */     return 4;
/*     */   }
/*     */   
/*     */   public short getJumpMaxDescendingHeight() {
/* 295 */     if (isCarried())
/* 296 */       return (short)Math.max(4, getCarriedByFighter().getStandardHeight());
/* 297 */     return 4;
/*     */   }
/*     */   
/*     */   public float getMovementObstruction() {
/* 301 */     return 0.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean rollCriticalHitTest()
/*     */   {
/* 312 */     int diceLimit = getCharacteristicValue(FighterCharacteristicType.CRITICAL_RATE);
/* 313 */     return DiceRoll.roll(100) <= diceLimit;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean rollCriticalMissTest()
/*     */   {
/* 321 */     int diceLimit = getCharacteristicValue(FighterCharacteristicType.FUMBLE_RATE);
/* 322 */     return (diceLimit > 0) && (DiceRoll.roll(100) <= diceLimit);
/*     */   }
/*     */   
/*     */   public void setTeamMate(TeamMate teamMate) {
/* 326 */     this.m_teamMate = teamMate;
/*     */   }
/*     */   
/*     */   public TeamMate getTeamMate() {
/* 330 */     return this.m_teamMate;
/*     */   }
/*     */   
/*     */   public FightingTeam<? extends AbstractFighter> getTeam()
/*     */   {
/* 335 */     if (this.m_teamMate == null)
/* 336 */       return null;
/* 337 */     return this.m_teamMate.getTeam();
/*     */   }
/*     */   
/*     */   public boolean getFlag(int Flag) {
/* 341 */     return false;
/*     */   }
/*     */   
/*     */   public void switchFlag(int Flag) {}
/*     */   
/*     */   public void computeValue()
/*     */   {
/* 348 */     short value = 0;
/* 349 */     Breed breed = getBreed();
/* 350 */     if (breed != null)
/* 351 */       value = (short)(value + breed.getValue());
/* 352 */     if (getEquipmentInventory() != null) {
/* 353 */       for (AbstractFighterCard card : getEquipmentInventory())
/* 354 */         value = (short)(value + card.getValue());
/*     */     }
/* 356 */     if (getSpellInventory() != null) {
/* 357 */       for (AbstractSpell spell : getSpellInventory())
/* 358 */         value = (short)(value + spell.getValue());
/*     */     }
/* 360 */     this.m_value = value;
/*     */   }
/*     */   
/*     */   public short getValue() {
/* 364 */     return this.m_value;
/*     */   }
/*     */   
/*     */   public RunningEffectManager getRunningEffectManager() {
/* 368 */     return this.m_runningEffectManager;
/*     */   }
/*     */   
/*     */   public void initializeCharacteristics() {
/* 372 */     Breed breed = getBreed();
/* 373 */     if (breed == null)
/* 374 */       throw new IllegalArgumentException("Impossible d'initialiser un fighter : race d'id " + this.m_breed + " inconnue");
/* 375 */     for (TIntObjectIterator<AbstractCharacteristic> it = this.m_characteristics.iterator(); it.hasNext();) {
/* 376 */       it.advance();
/* 377 */       AbstractCharacteristic charac = (AbstractCharacteristic)it.value();
/* 378 */       charac.makeDefault();
/*     */     }
/* 380 */     getCharacteristic(FighterCharacteristicType.HP).setMax(breed.getBaseHp());
/* 381 */     getCharacteristic(FighterCharacteristicType.MP).setMax(breed.getBaseMp());
/* 382 */     getCharacteristic(FighterCharacteristicType.AP).setMax(breed.getBaseAp());
/* 383 */     getCharacteristic(FighterCharacteristicType.INIT).setMax(breed.getBaseInit());
/* 384 */     getCharacteristic(FighterCharacteristicType.AP).toMax();
/* 385 */     getCharacteristic(FighterCharacteristicType.HP).toMax();
/* 386 */     getCharacteristic(FighterCharacteristicType.MP).toMax();
/* 387 */     getCharacteristic(FighterCharacteristicType.INIT).toMax();
/* 388 */     getCharacteristic(FighterCharacteristicType.CRITICAL_RATE).set(breed.getBaseCH());
/* 389 */     getCharacteristic(FighterCharacteristicType.FUMBLE_RATE).set(breed.getBaseCM());
/*     */   }
/*     */   
/*     */   public AbstractCharacteristic getCharacteristic(CharacteristicType charac) {
/* 393 */     return (AbstractCharacteristic)this.m_characteristics.get(charac.getId());
/*     */   }
/*     */   
/*     */   public boolean hasCharacteristic(CharacteristicType charac) {
/* 397 */     return this.m_characteristics.contains(charac.getId());
/*     */   }
/*     */   
/*     */   public int getCharacteristicValue(CharacteristicType charac) throws UnsupportedOperationException
/*     */   {
/* 402 */     AbstractCharacteristic cha = (AbstractCharacteristic)this.m_characteristics.get(charac.getId());
/* 403 */     if (cha != null) {
/* 404 */       return cha.value();
/*     */     }
/* 406 */     throw new UnsupportedOperationException("caractéristique inexistante");
/*     */   }
/*     */   
/*     */ 
/*     */   public FighterPropertymanager getProperties()
/*     */   {
/* 412 */     return this.m_properties;
/*     */   }
/*     */   
/*     */   public FourSidedPartLocalisator getPartLocalisator() {
/* 416 */     if (this.m_direction == null)
/* 417 */       this.m_direction = Direction8.SOUTH_EAST;
/* 418 */     this.m_partLocalisator.update(this.m_position, this.m_direction);
/* 419 */     return this.m_partLocalisator;
/*     */   }
/*     */   
/*     */   public SpellCastHistory getSpellCastHistory()
/*     */   {
/* 424 */     return this.m_spellCastHistory;
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract AbstractFighter summonCreature(long paramLong, Point3 paramPoint3, int paramInt);
/*     */   
/*     */ 
/*     */   public abstract AbstractFighter summonDouble(long paramLong, Point3 paramPoint3);
/*     */   
/*     */ 
/*     */   public abstract AbstractFighter summonMirror(long paramLong, Point3 paramPoint3, int paramInt);
/*     */   
/*     */ 
/*     */   public void setActorInstanceID(short id) {}
/*     */   
/*     */ 
/*     */   public abstract StackInventory<? extends AbstractSpell> getSpellInventory();
/*     */   
/*     */ 
/*     */   public abstract StackInventory<? extends AbstractSpell> getTeamMateSpellInventory();
/*     */   
/*     */ 
/*     */   public abstract ArrayInventory<? extends AbstractFighterCard> getEquipmentInventory();
/*     */   
/*     */ 
/*     */   public boolean canJoinFight()
/*     */   {
/* 451 */     return !isOnFight();
/*     */   }
/*     */   
/*     */   public boolean shouldBeDead() {
/* 455 */     return (getCurrentFight() != null) && (getCharacteristic(FighterCharacteristicType.HP).isZero());
/*     */   }
/*     */   
/*     */   public boolean isDead() {
/* 459 */     return this.m_isDead;
/*     */   }
/*     */   
/*     */   public boolean isBlockingLOS(Object lineOfSightChecker) {
/* 463 */     if (!getProperties().isActiveProperty(FighterPropertyType.INVISIBLE)) {
/* 464 */       return true;
/*     */     }
/* 466 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isPotentialTarget()
/*     */   {
/* 475 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onJoinFight(BasicFight<? extends BasicFighter> fight)
/*     */   {
/* 486 */     this.m_specialEventListener = fight;
/*     */   }
/*     */   
/*     */   public abstract void onRemovedFromFight();
/*     */   
/*     */   public abstract void onSpecialFighterEvent(int paramInt);
/*     */   
/*     */   public abstract void onNowAbleToFight();
/*     */   
/*     */   public abstract void onNowUnableToFight();
/*     */   
/*     */   public void onDeath()
/*     */   {
/* 499 */     this.m_isDead = true;
/*     */     
/*     */ 
/* 502 */     if ((getFather() != null) && (!getFather().isDead())) {
/* 503 */       getFather().onChildDeath(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onChildDeath(AbstractFighter child)
/*     */   {
/* 509 */     List<? extends AbstractFighter> childs = getChilds();
/* 510 */     if (childs.contains(child)) {
/* 511 */       childs.remove(child);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract void onEffectUsed();
/*     */   
/*     */ 
/*     */   public void onStateApplied(State state) {}
/*     */   
/*     */ 
/*     */   public void onStateUnapplied(State state) {}
/*     */   
/*     */   public void initWithFighterInformation(FighterInformation info)
/*     */   {
/* 526 */     setName(info.getName());
/* 527 */     setBreedAndSex(info.getBreedId(), info.getSex());
/* 528 */     setSkinIndex(info.getSkinIndex());
/*     */     
/* 530 */     initializeCharacteristics();
/* 531 */     getSpellInventory().unserialize(info.getSerializedSpellsInventory());
/* 532 */     getEquipmentInventory().unserialize(info.getSerializedCardsInventory());
/* 533 */     computeValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean unserialize(ByteBuffer buffer)
/*     */   {
/* 544 */     boolean bError = false;
/*     */     try {
/* 546 */       setId(buffer.getLong());
/* 547 */       byte breedId = buffer.get();
/* 548 */       byte[] str = new byte[buffer.get()];
/* 549 */       buffer.get(str);
/* 550 */       setName(new String(str));
/* 551 */       byte sex = buffer.get();
/* 552 */       setBreedAndSex(breedId, sex);
/* 553 */       setSkinIndex(buffer.get());
/* 554 */       initializeCharacteristics();
/*     */       
/* 556 */       byte[] spells = new byte[buffer.getShort()];
/* 557 */       buffer.get(spells);
/* 558 */       getSpellInventory().unserialize(spells);
/*     */       
/* 560 */       byte[] equipments = new byte[buffer.getShort()];
/* 561 */       buffer.get(equipments);
/* 562 */       getEquipmentInventory().unserialize(equipments);
/*     */       
/* 564 */       computeValue();
/*     */     }
/*     */     catch (BufferUnderflowException e) {
/* 567 */       m_logger.error("pas assez de données pour completer la création d'un Fighter");
/* 568 */       return false;
/*     */     }
/* 570 */     return !bError;
/*     */   }
/*     */   
/*     */ 
/*     */   public byte[] serialize()
/*     */   {
/* 576 */     byte[] name = this.m_name.getBytes();
/* 577 */     byte[] serializedSpellInventory = getSpellInventory().serialize();
/* 578 */     byte[] serializedEquipmentInveotiory = getEquipmentInventory().serialize();
/* 579 */     ByteBuffer buffer = ByteBuffer.allocate(10 + name.length + 1 + 1 + 2 + serializedSpellInventory.length + 2 + serializedEquipmentInveotiory.length);
/* 580 */     buffer.putLong(getId());
/*     */     
/* 582 */     buffer.put(this.m_breed.getId());
/* 583 */     buffer.put((byte)name.length);
/* 584 */     buffer.put(name);
/* 585 */     buffer.put(this.m_sex);
/* 586 */     buffer.put(this.m_skinIndex);
/*     */     
/* 588 */     buffer.putShort((short)serializedSpellInventory.length);
/* 589 */     buffer.put(serializedSpellInventory);
/*     */     
/* 591 */     buffer.putShort((short)serializedEquipmentInveotiory.length);
/* 592 */     buffer.put(serializedEquipmentInveotiory);
/*     */     
/* 594 */     return buffer.array();
/*     */   }
/*     */   
/*     */   public static int getBinarySerializationAverageLength() {
/* 598 */     return BINARY_AVERAGE_LENGTH;
/*     */   }
/*     */   
/*     */   public abstract AbstractFight<? extends AbstractFighter> getCurrentFight();
/*     */   
/*     */   protected void applyCardEffects(AbstractFighterCard card) {
/* 604 */     if (card == null)
/*     */       return;
/*     */     EffectContext context;
/*     */     EffectContext context;
/* 608 */     if (getCurrentFight() != null) {
/* 609 */       context = getCurrentFight().getContext();
/*     */     } else {
/* 611 */       context = new EffectContextForUniqueEffectUser(this);
/*     */     }
/* 613 */     for (Effect effect : card.effectsAtEquippementTimeIterator()) {
/* 614 */       effect.execute(card, this, context, RunningEffectConstants.getInstance(), getPosition());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void unapplyCardEffects(AbstractFighterCard card) {
/* 619 */     if (card == null)
/* 620 */       return;
/* 621 */     getRunningEffectManager().removeLinkedToContainer(card);
/*     */   }
/*     */   
/*     */   public AbstractFighter getCarriedFighter()
/*     */   {
/* 626 */     return this.m_carriedFighter;
/*     */   }
/*     */   
/*     */   public void setCarriedFighter(AbstractFighter carriedFighter) {
/* 630 */     this.m_carriedFighter = carriedFighter;
/*     */   }
/*     */   
/*     */   public void setCarriedByFighter(AbstractFighter carriedByFighter)
/*     */   {
/* 635 */     this.m_carriedByFighter = carriedByFighter;
/*     */   }
/*     */   
/*     */   public AbstractFighter getCarriedByFighter() {
/* 639 */     return this.m_carriedByFighter;
/*     */   }
/*     */   
/*     */   public boolean isCarrying() {
/* 643 */     return this.m_carriedFighter != null;
/*     */   }
/*     */   
/*     */   public boolean isCarried() {
/* 647 */     return this.m_carriedByFighter != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean carry(AbstractFighter abstractFighter)
/*     */   {
/* 659 */     if ((!isCarrying()) && (abstractFighter != this) && (abstractFighter != null) && (!abstractFighter.isCarrying()) && (!abstractFighter.isCarried()) && (getHeight() < Short.MAX_VALUE)) {
/* 660 */       Point3 position = getPosition();
/* 661 */       abstractFighter.setPosition(position.getX(), position.getY(), (short)(position.getZ() + getStandardHeight()));
/* 662 */       setCarriedFighter(abstractFighter);
/* 663 */       abstractFighter.setCarriedByFighter(this);
/* 664 */       return true;
/*     */     }
/* 666 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean uncarry(Point3 pos)
/*     */   {
/* 675 */     if (isCarrying()) {
/* 676 */       this.m_carriedFighter.setPosition(pos);
/* 677 */       this.m_carriedFighter.setCarriedByFighter(null);
/* 678 */       setCarriedFighter(null);
/* 679 */       return true;
/*     */     }
/* 681 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void uncarry()
/*     */   {
/* 688 */     if (isCarrying()) {
/* 689 */       this.m_carriedFighter.setPosition(getPosition());
/* 690 */       this.m_carriedFighter.setCarriedByFighter(null);
/* 691 */       setCarriedFighter(null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fighter\AbstractFighter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */