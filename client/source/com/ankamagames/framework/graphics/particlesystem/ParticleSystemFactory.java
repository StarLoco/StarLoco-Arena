/*     */ package com.ankamagames.framework.graphics.particlesystem;public abstract class ParticleSystemFactory<P extends ParticleSystem> { protected void createParticleSystemFromTag(Tag tag, Object parent) { DefineParticleSystem defineParticleSystem; ParticleSystem particleSystem;
/*     */     Emitter emitter1;
/*     */     BaseAffector emitter;
/*     */     DefineBitmap defineBitmap;
/*     */     DefineSequence defineSequence;
/*     */     DefineEmitter defineEmitter;
/*     */     DefineParticleBitmapModel defineParticleBitmapModel;
/*     */     DefineParticleSequenceModel defineParticleSequenceModel;
/*     */     DefineColorFader defineColorFader;
/*     */     DefineDirectionFollower defineDirectionFollower;
/*     */     DefineAttractionForce defineAttractionForce;
/*     */     DefineRotorForce defineRotorForce;
/*     */     DefineLinearForce defineLinearForce;
/*     */     DefineFrictionalForce defineFrictionalForce;
/*     */     DefineDeformer defineAffector;
/*     */     DefineLifeCondition defineParticle;
/*     */     Emitter emitter2;
/*     */     ParticleBitmapModel particleBitmapModel;
/*     */     ParticleSequenceModel particle;
/*     */     ColorFader colorFader;
/*     */     DirectionFollower directionFollower;
/*     */     AttractionForce attractionForce;
/*     */     RotorForce rotorForce;
/*     */     LinearForce linearForce;
/*     */     FrictionalForce frictionalForce;
/*     */     Deformer deformer;
/*     */     LifeCondition condition;
/*  28 */     switch (tag.getCode()) {
/*     */       
/*     */       case 1:
/*  31 */         defineParticleSystem = (DefineParticleSystem)tag;
/*     */         
/*  33 */         defineParticleSystem.initializeParticleSystem((ParticleSystem)parent);
/*     */         
/*  35 */         for (Tag childTag : defineParticleSystem.getTags())
/*     */         {
/*  37 */           createParticleSystemFromTag(childTag, parent);
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/*  43 */         particleSystem = (ParticleSystem)parent;
/*  44 */         defineBitmap = (DefineBitmap)tag;
/*     */         
/*  46 */         particleSystem.addBitmap(defineBitmap.getBitmapId(), defineBitmap.getAlphaBitmap());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 12:
/*  51 */         particleSystem = (ParticleSystem)parent;
/*  52 */         defineSequence = (DefineSequence)tag;
/*     */         
/*  54 */         particleSystem.addSequence(defineSequence.getSequenceId(), defineSequence.getSequenceBuffer());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/*  59 */         particleSystem = (ParticleSystem)parent;
/*  60 */         defineEmitter = (DefineEmitter)tag;
/*     */         
/*  62 */         emitter2 = new Emitter(particleSystem);
/*  63 */         defineEmitter.initializeEmitter(emitter2);
/*     */         
/*  65 */         particleSystem.addEmitter(emitter2);
/*     */         
/*  67 */         for (Tag childTag : defineEmitter.getTags()) {
/*  68 */           createParticleSystemFromTag(childTag, emitter2);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 4:
/*  73 */         emitter1 = (Emitter)parent;
/*  74 */         defineParticleBitmapModel = (DefineParticleBitmapModel)tag;
/*     */         
/*  76 */         particleBitmapModel = new ParticleBitmapModel();
/*  77 */         defineParticleBitmapModel.initializeParticle(particleBitmapModel);
/*     */         
/*  79 */         emitter1.addParticleModel((ParticleModel)particleBitmapModel);
/*     */         break;
/*     */       
/*     */       case 13:
/*  83 */         emitter1 = (Emitter)parent;
/*  84 */         defineParticleSequenceModel = (DefineParticleSequenceModel)tag;
/*     */         
/*  86 */         particle = new ParticleSequenceModel();
/*  87 */         defineParticleSequenceModel.initializeParticle(particle);
/*     */         
/*  89 */         emitter1.addParticleModel((ParticleModel)particle);
/*     */         break;
/*     */       
/*     */       case 5:
/*  93 */         emitter1 = (Emitter)parent;
/*  94 */         defineColorFader = (DefineColorFader)tag;
/*     */         
/*  96 */         colorFader = new ColorFader();
/*  97 */         defineColorFader.initializeAffector(colorFader);
/*     */         
/*  99 */         emitter1.addAffector((BaseAffector)colorFader);
/*     */         
/* 101 */         for (Tag childTag : defineColorFader.getTags()) {
/* 102 */           createParticleSystemFromTag(childTag, colorFader);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 14:
/* 107 */         emitter1 = (Emitter)parent;
/* 108 */         defineDirectionFollower = (DefineDirectionFollower)tag;
/*     */         
/* 110 */         directionFollower = new DirectionFollower();
/* 111 */         defineDirectionFollower.initializeAffector(directionFollower);
/*     */         
/* 113 */         emitter1.addAffector((BaseAffector)directionFollower);
/*     */         
/* 115 */         for (Tag childTag : defineDirectionFollower.getTags()) {
/* 116 */           createParticleSystemFromTag(childTag, directionFollower);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 6:
/* 121 */         emitter1 = (Emitter)parent;
/* 122 */         defineAttractionForce = (DefineAttractionForce)tag;
/*     */         
/* 124 */         attractionForce = new AttractionForce();
/* 125 */         defineAttractionForce.initializeAffector(attractionForce);
/*     */         
/* 127 */         emitter1.addAffector((BaseAffector)attractionForce);
/*     */         
/* 129 */         for (Tag childTag : defineAttractionForce.getTags()) {
/* 130 */           createParticleSystemFromTag(childTag, attractionForce);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 8:
/* 135 */         emitter1 = (Emitter)parent;
/* 136 */         defineRotorForce = (DefineRotorForce)tag;
/*     */         
/* 138 */         rotorForce = new RotorForce();
/* 139 */         defineRotorForce.initializeAffector(rotorForce);
/*     */         
/* 141 */         emitter1.addAffector((BaseAffector)rotorForce);
/*     */         
/* 143 */         for (Tag childTag : defineRotorForce.getTags()) {
/* 144 */           createParticleSystemFromTag(childTag, rotorForce);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 7:
/* 149 */         emitter1 = (Emitter)parent;
/* 150 */         defineLinearForce = (DefineLinearForce)tag;
/*     */         
/* 152 */         linearForce = new LinearForce();
/* 153 */         defineLinearForce.initializeAffector(linearForce);
/*     */         
/* 155 */         emitter1.addAffector((BaseAffector)linearForce);
/*     */         
/* 157 */         for (Tag childTag : defineLinearForce.getTags()) {
/* 158 */           createParticleSystemFromTag(childTag, linearForce);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 9:
/* 163 */         emitter1 = (Emitter)parent;
/* 164 */         defineFrictionalForce = (DefineFrictionalForce)tag;
/*     */         
/* 166 */         frictionalForce = new FrictionalForce();
/* 167 */         defineFrictionalForce.initializeAffector(frictionalForce);
/*     */         
/* 169 */         emitter1.addAffector((BaseAffector)frictionalForce);
/*     */         
/* 171 */         for (Tag childTag : defineFrictionalForce.getTags()) {
/* 172 */           createParticleSystemFromTag(childTag, frictionalForce);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 10:
/* 177 */         emitter1 = (Emitter)parent;
/* 178 */         defineAffector = (DefineDeformer)tag;
/*     */         
/* 180 */         deformer = new Deformer();
/* 181 */         defineAffector.initializeAffector(deformer);
/*     */         
/* 183 */         emitter1.addAffector((BaseAffector)deformer);
/*     */         
/* 185 */         for (Tag childTag : defineAffector.getTags()) {
/* 186 */           createParticleSystemFromTag(childTag, deformer);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 11:
/* 191 */         emitter = (BaseAffector)parent;
/* 192 */         defineParticle = (DefineLifeCondition)tag;
/*     */         
/* 194 */         condition = new LifeCondition();
/* 195 */         defineParticle.initializeCondition(condition);
/*     */         
/* 197 */         emitter.addCondition((AffectorCondition)condition);
/*     */         break;
/*     */     }  }
/*     */ 
/*     */   
/*     */   public abstract P getFreeParticleSystem(String paramString); }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\ParticleSystemFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */