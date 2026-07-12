/*     */ package com.ankamagames.dofusarena.client.core.game.team;
/*     */ 
/*     */ import com.ankamagames.dofusarena.common.game.team.TeamPreset;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
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
/*     */ public class TeamPresetManager
/*     */ {
/*  25 */   private static TeamPresetManager m_instance = new TeamPresetManager();
/*     */   
/*     */   private HashMap<Short, TeamPreset> m_teamPresets;
/*     */   
/*  29 */   private EditableTeamPreset m_editableTeamPreset = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TeamPresetManager() {
/*  35 */     this.m_teamPresets = new HashMap<Short, TeamPreset>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TeamPresetManager getInstance() {
/*  42 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  49 */     this.m_teamPresets.clear();
/*  50 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("teamManagement.editableTeamPreset");
/*  51 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("teamManagement.teamPresetList");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  58 */     return this.m_teamPresets.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTeamPreset(TeamPreset teamPreset) {
/*  67 */     this.m_teamPresets.put(Short.valueOf(teamPreset.getId()), teamPreset);
/*  68 */     updateTeamPresetListProperty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTeamPresets(Iterable<TeamPreset> teamPresets) {
/*  77 */     this.m_teamPresets.clear();
/*  78 */     for (TeamPreset teamPreset : teamPresets) {
/*  79 */       addTeamPreset(teamPreset);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTeamPreset(short id) {
/*  89 */     this.m_teamPresets.remove(Short.valueOf(id));
/*  90 */     setEditableTeamPreset(EditableTeamPreset.createEditableTeamPreset((TeamPreset)null));
/*  91 */     updateTeamPresetListProperty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTeamPreset(TeamPreset teamPreset) {
/* 100 */     removeTeamPreset(teamPreset.getId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<TeamPreset> getTeamPresets() {
/* 107 */     return this.m_teamPresets.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TeamPreset getTeamPreset(Short id) {
/* 117 */     return this.m_teamPresets.get(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EditableTeamPreset getEditableTeamPreset() {
/* 124 */     return this.m_editableTeamPreset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEditableTeamPreset(EditableTeamPreset selectedTeamPreset) {
/* 133 */     this.m_editableTeamPreset = selectedTeamPreset;
/* 134 */     updateEditableTeamPresetProperty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EditableTeamPreset createEmptyEditableTeamPreset() {
/* 141 */     return new EditableTeamPreset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFighterDefinitionFromAllTeamPresets(long fighterInformationId) {
/* 150 */     Collection<TeamPreset> teamPresets = getTeamPresets();
/* 151 */     for (TeamPreset teamPreset : teamPresets) {
/* 152 */       if (teamPreset.contains(fighterInformationId)) {
/* 153 */         teamPreset.remove(fighterInformationId);
/*     */       }
/*     */     } 
/* 156 */     if (this.m_editableTeamPreset.contains(fighterInformationId)) {
/* 157 */       this.m_editableTeamPreset.remove(fighterInformationId);
/* 158 */       updateEditableTeamPresetProperty();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateEditableTeamPresetProperty() {
/* 166 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.editableTeamPreset", this.m_editableTeamPreset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTeamPresetListProperty() {
/* 173 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.teamPresetList", this.m_teamPresets.values());
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\team\TeamPresetManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */