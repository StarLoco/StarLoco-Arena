/*     */ package com.ankamagames.dofusarena.client.core.game.actor;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FightingTeam;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.TeamMate;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ArrayInventory;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileUtils;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.common.constants.FighterCardType;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.Breed;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.BaseDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.DescriptorLibraryManager;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.ModifiableDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.instances.DisplayObject;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.graphics.isometric.particles.FreeParticleSystem;
/*     */ import com.ankamagames.graphics.isometric.particles.IsoParticleSystemFactory;
/*     */ import com.ankamagames.graphics.isometric.particles.IsoParticleSystemManager;
/*     */ import java.util.ArrayList;
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
/*     */ public class FighterActor
/*     */   extends Actor
/*     */ {
/*     */   public static final String ACTIVE_FIGHTER_ANIMATION_STATIC = "AnimStatique-02";
/*     */   public static final String CARRY_ANIMATION_SUFFIX = "Porte";
/*     */   public static final String ANIMATION_RAISE = "Anim01";
/*     */   public static final String ANIMATION_POSE = "Anim02Porte";
/*     */   public static final String ANIMATION_THROW = "Anim03Porte";
/*  48 */   private static Logger m_logger = Logger.getLogger(FighterActor.class);
/*     */   
/*  50 */   private Fighter m_fighter = null;
/*     */   
/*     */ 
/*     */   private FreeParticleSystem m_activeParticleSystem;
/*     */   
/*     */ 
/*     */   private FreeParticleSystem m_teamParticleSystem;
/*     */   
/*     */ 
/*     */   private FreeParticleSystem m_rootParticleSystem;
/*     */   
/*     */ 
/*  62 */   protected static Material m_highLightColor = new Material();
/*     */   
/*  64 */   static { m_highLightColor.setSpecular(0.2F, 0.2F, 0.2F, 0.0F);
/*  65 */     m_highLightColor.setUseSpecular(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public FighterActor(Fighter fighter)
/*     */   {
/*  72 */     super(fighter.getId());
/*  73 */     this.m_fighter = fighter;
/*  74 */     setVisualHeight((short)6);
/*  75 */     setVisible(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Fighter getFighter()
/*     */   {
/*  82 */     return this.m_fighter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDescriptorLibrary(ModifiableDescriptorLibrary descriptorLibrary)
/*     */   {
/*  92 */     super.setDescriptorLibrary(descriptorLibrary);
/*  93 */     if (descriptorLibrary != null) {
/*  94 */       descriptorLibrary.setSaveOldDefinitions(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getJumpMaxAscendingHeight()
/*     */   {
/* 105 */     return this.m_fighter.getJumpMaxAscendingHeight();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getJumpMaxDescendingHeight()
/*     */   {
/* 115 */     if ((this.m_fighter.isCarried()) && (this.m_fighter.getCarriedByFighter() != null)) {
/* 116 */       return (short)Math.max(4, this.m_fighter.getCarriedByFighter().getStandardHeight());
/*     */     }
/* 118 */     return this.m_fighter.getJumpMaxDescendingHeight();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPath(PathFindResult pathResult, boolean recompute)
/*     */   {
/* 128 */     super.setPath(pathResult, recompute);
/*     */     
/*     */ 
/* 131 */     int[] lastStep = pathResult.getLastStep();
/* 132 */     if ((lastStep != null) && (lastStep.length == 3)) {
/* 133 */       getFighter().setPositionWithoutNotifyActor(lastStep[0], lastStep[1], (short)lastStep[2]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDirection(Direction8 direction)
/*     */   {
/* 145 */     super.setDirection(direction);
/* 146 */     getFighter().setDirectionWithoutNotifyActor(direction);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAnimation(String animation)
/*     */   {
/* 156 */     boolean needToUpdateProperty = !getAnimation().equals(animation);
/* 157 */     super.setAnimation(animation);
/* 158 */     if (needToUpdateProperty) {
/* 159 */       getFighter().updateActorLinkageProperty();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVisible(boolean visible)
/*     */   {
/* 170 */     super.setVisible(visible);
/* 171 */     if (!visible) {
/* 172 */       hideTeamParticleSystem();
/* 173 */       hideActiveParticleSystem();
/* 174 */       hideRootParticleSystem();
/*     */     } else {
/* 176 */       showTeamParticleSystem();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSkinIndex(byte skinIndex)
/*     */   {
/*     */     try
/*     */     {
/* 189 */       String skinFileName = DofusArenaConfiguration.getInstance().getString("fighterSkinPath");
/* 190 */       Breed breed = this.m_fighter.getBreed();
/* 191 */       skinFileName = String.format(skinFileName, new Object[] { Byte.valueOf(breed != null ? breed.getId() : 0), Byte.valueOf(this.m_fighter.getSex()), Byte.valueOf(skinIndex) });
/*     */       
/*     */ 
/* 194 */       BaseDescriptorLibrary library = DescriptorLibraryManager.getInstance().getDescriptorLibrary(skinFileName);
/* 195 */       if (library != null)
/*     */       {
/* 197 */         ArrayList<String> linkageNames = MobileUtils.getPartLinkageNamesFromFile(library);
/* 198 */         setPartDescriptor(library, (String[])linkageNames.toArray(new String[linkageNames.size()]));
/*     */       } else {
/* 200 */         m_logger.error(" La Librarie n'existe pas ou est illisible" + skinFileName);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 204 */       m_logger.error("Error dans setSkinIndex : ", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyEquipment(FighterCard equipment)
/*     */   {
/*     */     try
/*     */     {
/* 217 */       String equipmentFileName = DofusArenaConfiguration.getInstance().getString("fighterEquipmentPath");
/* 218 */       equipmentFileName = String.format(equipmentFileName, new Object[] { Integer.valueOf(equipment.getId()) });
/*     */       
/*     */ 
/* 221 */       BaseDescriptorLibrary library = DescriptorLibraryManager.getInstance().getDescriptorLibrary(equipmentFileName);
/*     */       
/*     */ 
/* 224 */       FighterActorEquipmentType equipmentType = FighterActorEquipmentType.getActorEquipmentTypeFromIndex(equipment.getType().getInventoryPosition());
/* 225 */       if (equipmentType != null) {
/* 226 */         setPartDescriptor(library, equipmentType.getLinkageNames());
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 230 */       m_logger.error("Erreur au chargement de l'équipment : " + equipment.getId() + ", " + e.toString());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void applyAllEquipments()
/*     */   {
/* 238 */     if (getFighter() != null) {
/* 239 */       ArrayInventory<FighterCard> equipmentInventory = getFighter().getEquipmentInventory();
/* 240 */       for (FighterCard equipment : equipmentInventory) {
/* 241 */         applyEquipment(equipment);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unapplyEquipment(FighterCard equipment)
/*     */   {
/* 252 */     FighterActorEquipmentType equipmentType = FighterActorEquipmentType.getActorEquipmentTypeFromIndex(equipment.getType().getInventoryPosition());
/* 253 */     if (equipmentType != null) {
/* 254 */       unsetPartDescriptor(equipmentType.getLinkageNames());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void unapplyAllEquipments()
/*     */   {
/*     */     FighterActorEquipmentType[] arrayOfFighterActorEquipmentType;
/* 262 */     int j = (arrayOfFighterActorEquipmentType = FighterActorEquipmentType.values()).length; for (int i = 0; i < j; i++) { FighterActorEquipmentType equipmentType = arrayOfFighterActorEquipmentType[i];
/* 263 */       unsetPartDescriptor(equipmentType.getLinkageNames());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void showTeamParticleSystem()
/*     */   {
/* 271 */     if ((this.m_fighter != null) && (this.m_teamParticleSystem == null)) {
/* 272 */       byte teamId = this.m_fighter.getTeamMate().getTeam().getId();
/* 273 */       if ((teamId >= 0) && (teamId < DofusArenaClientConstants.FIGHTER_TEAM_PARTICLE_SYSTEM_FILE_ID.length))
/*     */       {
/* 275 */         this.m_teamParticleSystem = IsoParticleSystemFactory.getInstance().getFreeParticleSystem(DofusArenaClientConstants.FIGHTER_TEAM_PARTICLE_SYSTEM_FILE_ID[teamId]);
/* 276 */         if (this.m_teamParticleSystem != null) {
/* 277 */           this.m_teamParticleSystem.setTarget(this);
/* 278 */           IsoParticleSystemManager.getInstance().addParticleSystem(this.m_teamParticleSystem);
/* 279 */           return;
/*     */         }
/*     */       }
/*     */     }
/* 283 */     m_logger.error("Impossible d'afficher le cercle d'équipe sur l'acteur id=" + getId());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void hideTeamParticleSystem()
/*     */   {
/* 290 */     if (this.m_teamParticleSystem != null) {
/* 291 */       IsoParticleSystemManager.getInstance().removeParticleSystem(this.m_teamParticleSystem.getId());
/* 292 */       this.m_teamParticleSystem = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void showActiveParticleSystem()
/*     */   {
/* 300 */     if (this.m_fighter != null) {
/* 301 */       byte teamId = this.m_fighter.getTeamMate().getTeam().getId();
/* 302 */       if ((teamId >= 0) && (teamId < DofusArenaClientConstants.FIGHTER_TEAM_PARTICLE_SYSTEM_FILE_ID.length))
/*     */       {
/*     */ 
/* 305 */         hideActiveParticleSystem();
/* 306 */         this.m_activeParticleSystem = IsoParticleSystemFactory.getInstance().getFreeParticleSystem(DofusArenaClientConstants.FIGHTER_ACTIVE_PARTICLE_SYSTEM_FILE_ID[teamId]);
/* 307 */         if (this.m_teamParticleSystem != null) {
/* 308 */           this.m_activeParticleSystem.setTarget(getFighter().getActor());
/* 309 */           IsoParticleSystemManager.getInstance().addParticleSystem(this.m_activeParticleSystem);
/* 310 */           return;
/*     */         }
/*     */       }
/*     */     }
/* 314 */     m_logger.error("Impossible d'afficher le cercle d'équipe sur l'acteur id=" + getId());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void hideActiveParticleSystem()
/*     */   {
/* 321 */     if (this.m_activeParticleSystem != null) {
/* 322 */       IsoParticleSystemManager.getInstance().removeParticleSystem(this.m_activeParticleSystem.getId());
/* 323 */       this.m_activeParticleSystem = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void showRootParticleSystem()
/*     */   {
/* 331 */     if (this.m_fighter != null) {
/* 332 */       hideRootParticleSystem();
/* 333 */       this.m_rootParticleSystem = IsoParticleSystemFactory.getInstance().getFreeParticleSystem(9004);
/* 334 */       if (this.m_rootParticleSystem != null) {
/* 335 */         this.m_rootParticleSystem.setTarget(getFighter().getActor());
/* 336 */         IsoParticleSystemManager.getInstance().addParticleSystem(this.m_rootParticleSystem);
/* 337 */         return;
/*     */       }
/*     */     }
/* 340 */     m_logger.error("Impossible d'afficher le symbole d'immobilisation sur l'acteur id=" + getId());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void hideRootParticleSystem()
/*     */   {
/* 347 */     if (this.m_rootParticleSystem != null) {
/* 348 */       IsoParticleSystemManager.getInstance().removeParticleSystem(this.m_rootParticleSystem.getId());
/* 349 */       this.m_rootParticleSystem = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void highlight()
/*     */   {
/* 357 */     if (this.m_displayObject != null)
/*     */     {
/* 359 */       this.m_displayObject.colorize(m_highLightColor);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void unhighlight()
/*     */   {
/* 367 */     if (this.m_displayObject != null) {
/* 368 */       this.m_displayObject.resetColor();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\actor\FighterActor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */