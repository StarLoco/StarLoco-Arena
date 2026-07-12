/*     */ package org.postgresql.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Hashtable;
/*     */ import org.postgresql.Driver;
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
/*     */ public class ServerErrorMessage
/*     */   implements Serializable
/*     */ {
/*  19 */   private static final Character SEVERITY = new Character('S');
/*  20 */   private static final Character MESSAGE = new Character('M');
/*  21 */   private static final Character DETAIL = new Character('D');
/*  22 */   private static final Character HINT = new Character('H');
/*  23 */   private static final Character POSITION = new Character('P');
/*  24 */   private static final Character WHERE = new Character('W');
/*  25 */   private static final Character FILE = new Character('F');
/*  26 */   private static final Character LINE = new Character('L');
/*  27 */   private static final Character ROUTINE = new Character('R');
/*  28 */   private static final Character SQLSTATE = new Character('C');
/*  29 */   private static final Character INTERNAL_POSITION = new Character('p');
/*  30 */   private static final Character INTERNAL_QUERY = new Character('q');
/*     */   
/*     */   private Hashtable m_mesgParts;
/*     */ 
/*     */   
/*     */   public ServerErrorMessage(String p_serverError) {
/*  36 */     char[] l_chars = p_serverError.toCharArray();
/*  37 */     int l_pos = 0;
/*  38 */     int l_length = l_chars.length;
/*  39 */     this.m_mesgParts = new Hashtable();
/*  40 */     while (l_pos < l_length) {
/*     */       
/*  42 */       char l_mesgType = l_chars[l_pos];
/*  43 */       if (l_mesgType != '\000') {
/*     */ 
/*     */         
/*  46 */         int l_startString = ++l_pos;
/*  47 */         while (l_chars[l_pos] != '\000' && l_pos < l_length)
/*     */         {
/*  49 */           l_pos++;
/*     */         }
/*  51 */         String l_mesgPart = new String(l_chars, l_startString, l_pos - l_startString);
/*  52 */         this.m_mesgParts.put(new Character(l_mesgType), l_mesgPart);
/*     */       } 
/*  54 */       l_pos++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSQLState() {
/*  60 */     return (String)this.m_mesgParts.get(SQLSTATE);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  65 */     return (String)this.m_mesgParts.get(MESSAGE);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSeverity() {
/*  70 */     return (String)this.m_mesgParts.get(SEVERITY);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDetail() {
/*  75 */     return (String)this.m_mesgParts.get(DETAIL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHint() {
/*  80 */     return (String)this.m_mesgParts.get(HINT);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPosition() {
/*  85 */     return getIntegerPart(POSITION);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWhere() {
/*  90 */     return (String)this.m_mesgParts.get(WHERE);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFile() {
/*  95 */     return (String)this.m_mesgParts.get(FILE);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLine() {
/* 100 */     return getIntegerPart(LINE);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRoutine() {
/* 105 */     return (String)this.m_mesgParts.get(ROUTINE);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInternalQuery() {
/* 110 */     return (String)this.m_mesgParts.get(INTERNAL_QUERY);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInternalPosition() {
/* 115 */     return getIntegerPart(INTERNAL_POSITION);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getIntegerPart(Character c) {
/* 120 */     String s = (String)this.m_mesgParts.get(c);
/* 121 */     if (s == null)
/* 122 */       return 0; 
/* 123 */     return Integer.parseInt(s);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 145 */     StringBuffer l_totalMessage = new StringBuffer();
/* 146 */     String l_message = (String)this.m_mesgParts.get(SEVERITY);
/* 147 */     if (l_message != null)
/* 148 */       l_totalMessage.append(l_message).append(": "); 
/* 149 */     l_message = (String)this.m_mesgParts.get(MESSAGE);
/* 150 */     if (l_message != null)
/* 151 */       l_totalMessage.append(l_message); 
/* 152 */     l_message = (String)this.m_mesgParts.get(DETAIL);
/* 153 */     if (l_message != null)
/* 154 */       l_totalMessage.append("\n  ").append(GT.tr("Detail: {0}", l_message)); 
/* 155 */     if (Driver.logInfo) {
/*     */       
/* 157 */       l_message = (String)this.m_mesgParts.get(HINT);
/* 158 */       if (l_message != null)
/* 159 */         l_totalMessage.append("\n  ").append(GT.tr("Hint: {0}", l_message)); 
/* 160 */       l_message = (String)this.m_mesgParts.get(POSITION);
/* 161 */       if (l_message != null)
/* 162 */         l_totalMessage.append("\n  ").append(GT.tr("Position: {0}", l_message)); 
/* 163 */       l_message = (String)this.m_mesgParts.get(WHERE);
/* 164 */       if (l_message != null)
/* 165 */         l_totalMessage.append("\n  ").append(GT.tr("Where: {0}", l_message)); 
/*     */     } 
/* 167 */     if (Driver.logDebug) {
/*     */       
/* 169 */       String l_internalQuery = (String)this.m_mesgParts.get(INTERNAL_QUERY);
/* 170 */       if (l_internalQuery != null)
/* 171 */         l_totalMessage.append("\n  ").append(GT.tr("Internal Query: {0}", l_internalQuery)); 
/* 172 */       String l_internalPosition = (String)this.m_mesgParts.get(INTERNAL_POSITION);
/* 173 */       if (l_internalPosition != null) {
/* 174 */         l_totalMessage.append("\n  ").append(GT.tr("Internal Position: {0}", l_internalPosition));
/*     */       }
/* 176 */       String l_file = (String)this.m_mesgParts.get(FILE);
/* 177 */       String l_line = (String)this.m_mesgParts.get(LINE);
/* 178 */       String l_routine = (String)this.m_mesgParts.get(ROUTINE);
/* 179 */       if (l_file != null || l_line != null || l_routine != null)
/* 180 */         l_totalMessage.append("\n  ").append(GT.tr("Location: File: {0}, Routine: {1}, Line: {2}", new Object[] { l_file, l_routine, l_line })); 
/* 181 */       l_message = (String)this.m_mesgParts.get(SQLSTATE);
/* 182 */       if (l_message != null) {
/* 183 */         l_totalMessage.append("\n  ").append(GT.tr("Server SQLState: {0}", l_message));
/*     */       }
/*     */     } 
/* 186 */     return l_totalMessage.toString();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresq\\util\ServerErrorMessage.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */