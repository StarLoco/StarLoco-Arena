/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CommandPattern
/*    */ {
/*    */   private static final String CMD_PATTERN_SUFFIX = "(?:\\s+.*|$)";
/*    */   private static final String ARGS_PATTERN_POST_PREFIX = "\\s+";
/*    */   private String m_name;
/*    */   private Pattern m_cmdPattern;
/*    */   private Pattern m_argsPattern;
/* 26 */   private byte m_level = Byte.MIN_VALUE;
/*    */   
/*    */ 
/*    */ 
/*    */   public CommandPattern(String cmdRegex, String argsRegex)
/*    */   {
/* 32 */     this.m_name = "";
/*    */     
/* 34 */     String cmdFullRegex = "";
/* 35 */     if ((cmdRegex != null) && (cmdRegex.length() != 0) && 
/* 36 */       (!cmdRegex.endsWith("(?:\\s+.*|$)"))) {
/* 37 */       cmdFullRegex = cmdRegex + "(?:\\s+.*|$)";
/*    */     }
/*    */     
/* 40 */     this.m_cmdPattern = Pattern.compile(cmdFullRegex);
/*    */     
/* 42 */     String argFullRegex = "(" + cmdRegex + "){1}";
/* 43 */     if ((argsRegex != null) && (argsRegex.length() != 0) && 
/* 44 */       (!argsRegex.startsWith(argFullRegex + "\\s+"))) {
/* 45 */       argFullRegex = argFullRegex + "\\s+" + argsRegex;
/*    */     }
/*    */     
/* 48 */     this.m_argsPattern = Pattern.compile(argFullRegex);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 55 */     this.m_name = name;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 62 */     return this.m_name;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getLevel()
/*    */   {
/* 69 */     return this.m_level;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setLevel(byte level)
/*    */   {
/* 76 */     this.m_level = level;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Pattern getCmdPattern()
/*    */   {
/* 83 */     return this.m_cmdPattern;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Pattern getArgsPattern()
/*    */   {
/* 90 */     return this.m_argsPattern;
/*    */   }
/*    */   
/*    */   public abstract Command createInstance();
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\console\command\descriptors\CommandPattern.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */