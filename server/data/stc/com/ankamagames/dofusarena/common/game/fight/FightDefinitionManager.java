/*     */ package com.ankamagames.dofusarena.common.game.fight;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentEntry;
/*     */ import com.ankamagames.framework.fileFormat.xml.XMLDocumentAccessor;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.ByteArray;
/*     */ import gnu.trove.TByteObjectHashMap;
/*     */ import gnu.trove.TByteObjectIterator;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FightDefinitionManager
/*     */ {
/*  25 */   private TByteObjectHashMap<FightDefinition> m_fightDefinitions = new TByteObjectHashMap();
/*     */   
/*     */ 
/*  28 */   private static final Logger m_logger = Logger.getLogger(FightDefinitionManager.class);
/*     */   
/*  30 */   private static FightDefinitionManager m_instance = new FightDefinitionManager();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static FightDefinitionManager getInstance()
/*     */   {
/*  37 */     return m_instance;
/*     */   }
/*     */   
/*     */   public boolean initializeDefinitions(String commandFileName) {
/*  41 */     InputStream stream = null;
/*     */     
/*     */     try
/*     */     {
/*  45 */       URL jarUrl = new URL(commandFileName);
/*  46 */       stream = jarUrl.openStream();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  50 */       File file = new File(commandFileName);
/*     */       try {
/*  52 */         stream = new FileInputStream(file);
/*     */       } catch (FileNotFoundException e1) {
/*  54 */         e1.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*  58 */     return initializeDefinitions(stream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean initializeDefinitions(InputStream stream)
/*     */   {
/*  69 */     XMLDocumentAccessor accessor = XMLDocumentAccessor.getInstance();
/*  70 */     DocumentContainer document = accessor.getNewDocumentContainer();
/*     */     try
/*     */     {
/*  73 */       m_logger.info("Loading fightProperty file.");
/*  74 */       accessor.open(stream);
/*  75 */       accessor.read(document);
/*  76 */       accessor.close();
/*     */     } catch (Exception e) {
/*  78 */       e.printStackTrace();
/*  79 */       return false;
/*     */     }
/*  81 */     ArrayList<DocumentEntry> fightEntries = document.getEntriesByName("fight");
/*     */     
/*  83 */     if (fightEntries != null) {
/*  84 */       for (DocumentEntry processEntry : fightEntries) {
/*  85 */         DocumentEntry fightDescription = processEntry.getParameterByName("description");
/*  86 */         DocumentEntry fightId = processEntry.getParameterByName("id");
/*  87 */         DocumentEntry teamNumber = processEntry.getParameterByName("teamNumber");
/*  88 */         DocumentEntry coachByTeam = processEntry.getParameterByName("coachByTeam");
/*  89 */         DocumentEntry minFighterByTeam = processEntry.getParameterByName("minFighterByTeam");
/*  90 */         DocumentEntry maxFighterByTeam = processEntry.getParameterByName("maxFighterByTeam");
/*  91 */         DocumentEntry budget = processEntry.getParameterByName("budget");
/*  92 */         DocumentEntry training = processEntry.getParameterByName("training");
/*  93 */         DocumentEntry ladderIdParam = processEntry.getParameterByName("ladderId");
/*  94 */         DocumentEntry rankedParam = processEntry.getParameterByName("ranked");
/*     */         
/*  96 */         if ((fightDescription != null) && 
/*  97 */           (fightId != null) && 
/*  98 */           (teamNumber != null) && 
/*  99 */           (coachByTeam != null) && 
/* 100 */           (minFighterByTeam != null) && 
/* 101 */           (maxFighterByTeam != null) && 
/* 102 */           (budget != null) && 
/* 103 */           (training != null) && 
/* 104 */           (ladderIdParam != null) && 
/* 105 */           (rankedParam != null)) {
/* 106 */           byte id = fightId.getByteValue();
/* 107 */           byte tn = teamNumber.getByteValue();
/* 108 */           byte cbt = coachByTeam.getByteValue();
/* 109 */           byte minF = minFighterByTeam.getByteValue();
/* 110 */           byte maxF = maxFighterByTeam.getByteValue();
/* 111 */           int bud = budget.getIntValue();
/* 112 */           boolean train = training.getBooleanValue();
/* 113 */           int ladderId = ladderIdParam.getIntValue();
/* 114 */           boolean ranked = rankedParam.getBooleanValue();
/*     */           
/* 116 */           String descr = fightDescription.getStringValue();
/* 117 */           this.m_fightDefinitions.put(id, new FightDefinition(id, descr, tn, cbt, minF, maxF, bud, train, ladderId, ranked));
/*     */         } else {
/* 119 */           m_logger.error("Un ou plusieurs paramètres manquants");
/* 120 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 126 */     return true;
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
/*     */ 
/*     */   public byte[] findFightDefinitionFromParameters(byte teamNumber, byte numPlayer, byte budget, boolean training, int ladderId, boolean ranked)
/*     */   {
/* 141 */     ByteArray fightIds = new ByteArray();
/* 142 */     for (TByteObjectIterator<FightDefinition> it = this.m_fightDefinitions.iterator(); it.hasNext();) {
/* 143 */       it.advance();
/* 144 */       if (((FightDefinition)it.value()).parametersValidation(teamNumber, numPlayer, budget, training, ladderId, ranked)) {
/* 145 */         fightIds.put(it.key());
/*     */       }
/*     */     }
/* 148 */     return fightIds.toArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] findFightDefinitionFromParameters(FightDefinitionParameters parameters)
/*     */   {
/* 158 */     ByteArray fightIds = new ByteArray();
/* 159 */     for (TByteObjectIterator<FightDefinition> it = this.m_fightDefinitions.iterator(); it.hasNext();) {
/* 160 */       it.advance();
/* 161 */       if (((FightDefinition)it.value()).parametersValidation(parameters)) {
/* 162 */         fightIds.put(it.key());
/*     */       }
/*     */     }
/* 165 */     return fightIds.toArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FightDefinition getDefinitionFromFightTypeId(byte fightTypeId)
/*     */   {
/* 175 */     if (this.m_fightDefinitions.containsKey(fightTypeId)) {
/* 176 */       return (FightDefinition)this.m_fightDefinitions.get(fightTypeId);
/*     */     }
/* 178 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFightDefinition(byte fightTypeId, FightDefinition definition)
/*     */   {
/* 188 */     this.m_fightDefinitions.put(fightTypeId, definition);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TByteObjectHashMap<FightDefinition> getFightDefinitions()
/*     */   {
/* 197 */     return this.m_fightDefinitions;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fight\FightDefinitionManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */