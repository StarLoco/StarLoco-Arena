/*     */ package com.ankamagames.dofusarena.client.core.game.fighter;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ArrayInventory;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.StackInventory;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.ContentAlreadyPresentException;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.InventoryCapacityReachedException;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.PositionAlreadyUsedException;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCardManager;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.SpellManager;
/*     */ import com.ankamagames.dofusarena.common.constants.FighterCardType;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.Breed;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EditableFighter
/*     */   extends Fighter
/*     */ {
/*     */   public static final String BREED_SPELLS_FIELD = "breedSpells";
/*     */   public static final String WEAPON_CARDS_FIELD = "weaponCards";
/*     */   public static final String PET_CARDS_FIELD = "petCards";
/*     */   public static final String CLOAK_CARDS_FIELD = "cloakCards";
/*     */   public static final String HAT_CARDS_FIELD = "hatCards";
/*     */   public static final String DOFUS_CARDS_FIELD = "dofusCards";
/*  33 */   public static final String[] FIELDS = {
/*  34 */     "breedSpells", 
/*     */     
/*  36 */     "weaponCards", 
/*  37 */     "petCards", 
/*  38 */     "cloakCards", 
/*  39 */     "hatCards", 
/*  40 */     "dofusCards" };
/*     */   
/*     */ 
/*     */ 
/*  44 */   public static final String[] ALL_FIELDS = new String[FIELDS.length + Fighter.FIELDS.length];
/*  45 */   static { System.arraycopy(FIELDS, 0, ALL_FIELDS, 0, FIELDS.length);
/*  46 */     System.arraycopy(Fighter.FIELDS, 0, ALL_FIELDS, FIELDS.length, Fighter.FIELDS.length);
/*     */   }
/*     */   
/*  49 */   private static final String[] UPDATE_SPELL_PROPERTIES = { "spells", "value" };
/*  50 */   private static final String[] UPDATE_EQUIPMENT_PROPERTIES = {
/*  51 */     "weaponEquipment", 
/*  52 */     "petEquipment", 
/*  53 */     "cloakEquipment", 
/*  54 */     "hatEquipment", 
/*  55 */     "dofusEquipment", 
/*  56 */     "actorDescriptorLibrary", 
/*  57 */     "value" };
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPreviousSkinIndex()
/*     */   {
/*  63 */     int skinIndex = getSkinIndex() - 1;
/*  64 */     if (skinIndex < 0) {
/*  65 */       skinIndex = 4;
/*     */     }
/*  67 */     setSkinIndex((byte)skinIndex);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setNextSkinIndex()
/*     */   {
/*  74 */     int skinIndex = getSkinIndex() + 1;
/*  75 */     if (skinIndex > 4) {
/*  76 */       skinIndex = 0;
/*     */     }
/*  78 */     setSkinIndex((byte)skinIndex);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setNextDirection()
/*     */   {
/*  85 */     setDirection(getDirection().getNextDirection4(1));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPreviousDirection()
/*     */   {
/*  92 */     setDirection(getDirection().getNextDirection4(-1));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBreedFromId(byte breedId)
/*     */   {
/* 102 */     super.setBreedFromId(breedId);
/*     */     
/*     */ 
/* 105 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "actorDescriptorLibrary");
/* 106 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "backgroundUrl");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSex(byte sex)
/*     */   {
/* 116 */     super.setSex(sex);
/*     */     
/*     */ 
/* 119 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "actorDescriptorLibrary");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addSpell(Spell spell)
/*     */   {
/*     */     try
/*     */     {
/* 131 */       getSpellInventory().add(spell);
/*     */       
/*     */ 
/* 134 */       computeValue();
/*     */       
/*     */ 
/* 137 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, UPDATE_SPELL_PROPERTIES);
/*     */     }
/*     */     catch (InventoryCapacityReachedException localInventoryCapacityReachedException) {}catch (ContentAlreadyPresentException localContentAlreadyPresentException) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeSpell(Spell spell)
/*     */   {
/* 151 */     getSpellInventory().remove(spell);
/*     */     
/*     */ 
/* 154 */     computeValue();
/*     */     
/*     */ 
/* 157 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, UPDATE_SPELL_PROPERTIES);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addEquipment(FighterCard equipment, short position)
/*     */   {
/*     */     try
/*     */     {
/* 169 */       if (!getEquipmentInventory().isPositionFree(position)) {
/* 170 */         getEquipmentInventory().destroyAt(position);
/*     */       }
/*     */       
/*     */ 
/* 174 */       getEquipmentInventory().addAt(equipment, position);
/*     */       
/*     */ 
/* 177 */       computeValue();
/*     */       
/*     */ 
/* 180 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, UPDATE_EQUIPMENT_PROPERTIES);
/*     */     }
/*     */     catch (InventoryCapacityReachedException localInventoryCapacityReachedException) {}catch (ContentAlreadyPresentException localContentAlreadyPresentException) {}catch (PositionAlreadyUsedException localPositionAlreadyUsedException) {}
/*     */   }
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
/*     */   public void removeEquipment(FighterCard equipment)
/*     */   {
/* 196 */     getEquipmentInventory().remove(equipment);
/*     */     
/*     */ 
/* 199 */     computeValue();
/*     */     
/*     */ 
/* 202 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, UPDATE_EQUIPMENT_PROPERTIES);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getFields()
/*     */   {
/* 212 */     return ALL_FIELDS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getFieldValue(String fieldName)
/*     */   {
/* 222 */     if (fieldName.equals("breedSpells")) {
/* 223 */       return SpellManager.getInstance().getSpellsFromBreedId(getBreed().getId()).toArray();
/*     */     }
/*     */     
/* 226 */     if (fieldName.equals("weaponCards")) {
/* 227 */       return FighterCardManager.getInstance().getFighterCardsByType(FighterCardType.WEAPON).toArray();
/*     */     }
/* 229 */     if (fieldName.equals("petCards")) {
/* 230 */       return FighterCardManager.getInstance().getFighterCardsByType(FighterCardType.PET).toArray();
/*     */     }
/* 232 */     if (fieldName.equals("cloakCards")) {
/* 233 */       return FighterCardManager.getInstance().getFighterCardsByType(FighterCardType.CLOAK).toArray();
/*     */     }
/* 235 */     if (fieldName.equals("hatCards")) {
/* 236 */       return FighterCardManager.getInstance().getFighterCardsByType(FighterCardType.HAT).toArray();
/*     */     }
/* 238 */     if (fieldName.equals("dofusCards")) {
/* 239 */       return FighterCardManager.getInstance().getFighterCardsByType(FighterCardType.DOFUS).toArray();
/*     */     }
/*     */     
/* 242 */     return super.getFieldValue(fieldName);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFieldValue(String fieldName, Object value)
/*     */   {
/* 253 */     if ((fieldName.equals("name")) && 
/* 254 */       ((value instanceof String))) {
/* 255 */       setName((String)value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFieldSynchronisable(String fieldName)
/*     */   {
/* 267 */     return fieldName.equals("name");
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fighter\EditableFighter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */