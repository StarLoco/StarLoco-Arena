/*     */ package com.ankamagames.dofusarena.client.core.game.team;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.FighterManager;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.Breed;
/*     */ import com.ankamagames.dofusarena.common.game.team.TeamPreset;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
/*     */ import gnu.trove.TLongArrayList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
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
/*     */ public class EditableTeamPreset
/*     */   extends TeamPreset
/*     */   implements FieldProvider
/*     */ {
/*     */   public static final String NAME_FIELD = "name";
/*     */   public static final String FIGHTERS_FIELD = "fighters";
/*     */   public static final String VALUE_FIELD = "value";
/*  29 */   public static final String[] FIELDS = {
/*  30 */     "name", 
/*  31 */     "fighters", 
/*  32 */     "value" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getValue()
/*     */   {
/*  41 */     HashMap<Byte, Byte> breedCount = new HashMap();
/*     */     
/*  43 */     int value = 0;
/*  44 */     TLongArrayList fighterIds = getFightersIds();
/*  45 */     for (int i = 0; i < fighterIds.size(); i++) {
/*  46 */       fighter = FighterManager.getInstance().getFighter(fighterIds.getQuick(i));
/*  47 */       if (fighter != null) {
/*  48 */         fighter.computeValue();
/*  49 */         value += fighter.getValue();
/*  50 */         if (!breedCount.containsKey(Byte.valueOf(fighter.getBreed().getId()))) {
/*  51 */           breedCount.put(Byte.valueOf(fighter.getBreed().getId()), Byte.valueOf((byte)1));
/*     */         } else {
/*  53 */           byte count = ((Byte)breedCount.get(Byte.valueOf(fighter.getBreed().getId()))).byteValue();
/*  54 */           count = (byte)(count + 1);
/*  55 */           breedCount.put(Byte.valueOf(fighter.getBreed().getId()), Byte.valueOf(count));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  66 */     for (Fighter fighter = breedCount.values().iterator(); fighter.hasNext();) { byte count = ((Byte)fighter.next()).byteValue();
/*     */       
/*  68 */       int previousValue = 0;
/*  69 */       for (byte i = 1; i <= count - 1; i = (byte)(i + 1)) {
/*  70 */         previousValue += previousValue + i * 100;
/*     */       }
/*     */       
/*  73 */       value += previousValue;
/*     */     }
/*     */     
/*  76 */     return value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  86 */     boolean nameHasChanged = !name.equals(getName());
/*  87 */     super.setName(name);
/*  88 */     if (nameHasChanged)
/*     */     {
/*  90 */       setId((short)-1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void appendFieldValue(String fieldName, Object value) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getFieldValue(String fieldName)
/*     */   {
/* 109 */     if (fieldName.equals("name")) {
/* 110 */       return getName();
/*     */     }
/* 112 */     if (fieldName.equals("fighters"))
/*     */     {
/* 114 */       List<Fighter> fighters = new ArrayList();
/* 115 */       TLongArrayList fighterIds = getFightersIds();
/* 116 */       for (int i = 0; i < fighterIds.size(); i++) {
/* 117 */         Fighter fighter = FighterManager.getInstance().getFighter(fighterIds.getQuick(i));
/* 118 */         if (fighter != null) {
/* 119 */           fighters.add(fighter);
/*     */         }
/*     */       }
/* 122 */       return fighters.toArray();
/*     */     }
/* 124 */     if (fieldName.equals("value")) {
/* 125 */       return Integer.valueOf(getValue());
/*     */     }
/*     */     
/* 128 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getFields()
/*     */   {
/* 137 */     return FIELDS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFieldSynchronisable(String fieldName)
/*     */   {
/* 146 */     return fieldName.equals("name");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void prependFieldValue(String fieldName, Object value) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFieldValue(String fieldName, Object value)
/*     */   {
/* 165 */     if ((fieldName.equals("name")) && 
/* 166 */       ((value instanceof String))) {
/* 167 */       setName((String)value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static EditableTeamPreset createEditableTeamPreset(TeamPreset teamPreset)
/*     */   {
/* 177 */     EditableTeamPreset editableTeamPreset = new EditableTeamPreset();
/* 178 */     if (teamPreset != null) {
/* 179 */       editableTeamPreset.unserialize(teamPreset.serialize());
/*     */     }
/* 181 */     return editableTeamPreset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 191 */     return getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\team\EditableTeamPreset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */