/*    */ package com.ankamagames.baseImpl.graphics.alea.mobile;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.animation.descriptors.library.BaseDescriptorLibrary;
/*    */ import java.util.ArrayList;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
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
/*    */ public class MobileUtils
/*    */ {
/* 20 */   private static Pattern m_pattern = Pattern.compile("([0-7]{1})_([a-zA-Z0-9\\-]+)");
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
/*    */   public static ArrayList<String> getPartLinkageNamesFromFile(BaseDescriptorLibrary library) {
/* 32 */     if (library != null) {
/* 33 */       ArrayList<String> linkageNames = new ArrayList<String>();
/* 34 */       for (String linkage : library.getIndexedBuffer().getLinkages()) {
/* 35 */         Matcher matcher = m_pattern.matcher(linkage);
/* 36 */         if (matcher.find()) {
/* 37 */           String linkageName = matcher.group(2);
/* 38 */           if (!linkageNames.contains(linkageName)) {
/* 39 */             linkageNames.add(linkageName);
/*    */           }
/*    */         } 
/*    */       } 
/* 43 */       return linkageNames;
/*    */     } 
/* 45 */     return null;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\mobile\MobileUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */