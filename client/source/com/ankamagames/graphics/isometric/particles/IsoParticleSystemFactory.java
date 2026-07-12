/*     */ package com.ankamagames.graphics.isometric.particles;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.aps.APSDocument;
/*     */ import com.ankamagames.framework.graphics.aps.APSDocumentManager;
/*     */ import com.ankamagames.framework.graphics.particlesystem.ParticleSystem;
/*     */ import com.ankamagames.framework.graphics.particlesystem.ParticleSystemFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IsoParticleSystemFactory
/*     */   extends ParticleSystemFactory<IsoParticleSystem>
/*     */ {
/*  19 */   private static Logger m_logger = Logger.getLogger(IsoParticleSystemFactory.class);
/*     */   
/*  21 */   private static String PARTICLE_SYSTEM_EXTENSION = ".aps";
/*     */   
/*  23 */   private static final IsoParticleSystemFactory m_instance = new IsoParticleSystemFactory();
/*     */   
/*     */   public static IsoParticleSystemFactory getInstance() {
/*  26 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String m_path;
/*     */ 
/*     */ 
/*     */   
/*     */   public FreeParticleSystem getFreeParticleSystem(int particleSystemId) {
/*  37 */     return getFreeParticleSystem(String.valueOf(getPath()) + particleSystemId + PARTICLE_SYSTEM_EXTENSION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FreeParticleSystem getFreeParticleSystem(String filename) {
/*  46 */     FreeParticleSystem particleSystem = new FreeParticleSystem();
/*     */     
/*     */     try {
/*  49 */       APSDocument document = APSDocumentManager.getInstance().getDocument(filename);
/*     */       
/*  51 */       createParticleSystemFromTag(document.getTags().get(0), particleSystem);
/*     */     }
/*  53 */     catch (Exception e) {
/*  54 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  57 */     return particleSystem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CellParticleSystem getCellParticleSystem(int particleSystemId) {
/*     */     CellParticleSystem particleSystem;
/*  66 */     String fileName = String.valueOf(getPath()) + particleSystemId + PARTICLE_SYSTEM_EXTENSION;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  71 */       APSDocument document = APSDocumentManager.getInstance().getDocument(fileName);
/*     */       
/*  73 */       particleSystem = new CellParticleSystem();
/*  74 */       createParticleSystemFromTag(document.getTags().get(0), particleSystem);
/*     */     }
/*  76 */     catch (Exception e) {
/*  77 */       m_logger.error("Impossible de charger le système de particule : " + particleSystemId);
/*  78 */       return null;
/*     */     } 
/*     */     
/*  81 */     if (particleSystem.getSystemDuration() != 0)
/*     */     {
/*  83 */       m_logger.error("Le systeme de particule attaché à la cellule " + particleSystem.getX() + "/" + particleSystem.getY() + " possède une durée.");
/*     */     }
/*     */     
/*  86 */     return particleSystem;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPath() {
/*  91 */     return this.m_path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPath(String path) {
/*  99 */     if (!path.endsWith("/")) {
/* 100 */       path = String.valueOf(path) + "/";
/*     */     }
/* 102 */     this.m_path = path;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\particles\IsoParticleSystemFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */