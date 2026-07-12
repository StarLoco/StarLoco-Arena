/*    */ package com.ankamagames.framework.graphics.aps.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagReader;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagWriter;
/*    */ import com.ankamagames.framework.graphics.particlesystem.affectors.BaseAffector;
/*    */ import com.ankamagames.framework.graphics.particlesystem.conditions.AffectorCondition;
/*    */ import com.ankamagames.framework.graphics.particlesystem.conditions.LifeCondition;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DefineBaseAffecor
/*    */   extends Tag
/*    */ {
/*    */   private ArrayList<Tag> m_tags;
/*    */   
/*    */   public DefineBaseAffecor() {}
/*    */   
/*    */   public DefineBaseAffecor(BaseAffector affector) {
/* 33 */     this.m_tags = new ArrayList<Tag>();
/*    */     
/* 35 */     if (affector.getConditions() != null)
/* 36 */       for (AffectorCondition condition : affector.getConditions()) {
/*    */         
/* 38 */         if (condition instanceof LifeCondition) {
/* 39 */           this.m_tags.add(new DefineLifeCondition((LifeCondition)condition));
/*    */         }
/*    */       }  
/*    */   }
/*    */   
/*    */   public void setTagsData(InputBitStream inStream, short version) throws IOException {
/* 45 */     this.m_tags = new ArrayList<Tag>();
/*    */     
/*    */     while (true) {
/* 48 */       Tag tag = TagReader.readTag(APSTagDecoder.getInstance(), inStream, version);
/*    */       
/* 50 */       if (tag.getCode() == 0) {
/*    */         break;
/*    */       }
/*    */       
/* 54 */       this.m_tags.add(tag);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 60 */     TagWriter.writeTags(outStream, this.m_tags);
/*    */   }
/*    */   
/*    */   public ArrayList<Tag> getTags() {
/* 64 */     return this.m_tags;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineBaseAffecor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */