/*     */ package com.ankamagames.baseImpl.common.clientAndServer.utils;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class WordsModerator
/*     */ {
/*  19 */   public static final String[] FORBIDDEN_NAMES = new String[] { 
/*  20 */       "KAM", "TOT", "BO", "MANU", "LICHEN", "YAMATO", "XYO", "BEK", "GM", "MJ", "MOD" };
/*     */ 
/*     */   
/*  23 */   public static final String[] NAMES_STARTING_WITH = new String[] {
/*  24 */       "KAM-", "TOT-", "BO-", "MANU-", "LICHEN", "YAMATO", "BEK-", "MODER"
/*     */     };
/*     */   
/*  27 */   public static final String[] WORDS_EQUALS = new String[] {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  33 */       "BITE", "BITES", "SALOP", "SALOPE", "NIQUE", "HOMO", "HOMOS", "SUCE", 
/*  34 */       "CON", "CONNE", "CONS", "CONNES", "PD", "TG", 
/*     */       
/*  36 */       "WTF", "STFU", "ASS"
/*     */     };
/*     */   
/*  39 */   public static final String[] WORDS_CONTAINING = new String[] {
/*     */       
/*  41 */       "HTTP", "WWW", "FTP", 
/*     */ 
/*     */       
/*  44 */       "THXO", "IGHOT", "OURGAMES", 
/*     */ 
/*     */ 
/*     */       
/*  48 */       "COUILLE", "CONNAR", "VAGIN", "BATAR", "PUTE", "PUTIN", "PUTAIN", "SUCER", 
/*  49 */       "SUCEUR", "SUCEUSE", "ANUS", "NEGRE", "JUIF", "ARABE", "MERDE", "FDP", "ZOOPHIL", 
/*  50 */       "CHIER", "CROTTE", "ZIZI", "CACA", "PIPI", "BRANLAGE", "JOUIR", "POUFIASSE", "GROGNASSE", 
/*  51 */       "FOUTRE", "NTM", "TAMERE", "ENCULE", "PEDE", "CHIOTTE", "CLITO", 
/*  52 */       "ETRON", "GOUINE", "TURLUTTE", "CHIASSE", "LESBIENNE", "NIQUER", 
/*     */       
/*  54 */       "FUCK", "BITCH", "SUCK", "PENIS", "COCK", "NIGG", "CUNT", "FAG", "GAY", "SUCK", 
/*  55 */       "PISS", "CUNT", "CUM", "ARSE", "BIATCH", "BOOB", "DICK", "DILDO", "QUEER", 
/*  56 */       "FART", "WANKER", "GANGBANG", "LESBIAN", "SHIT", "POOP",
/*     */       
/*  58 */       "ARSCH", "BOLLERN", 
/*     */       
/*  60 */       "BAKA", "CHIPATAMA", "HENTAI", "KUSO", 
/*     */       
/*  62 */       "BALALAO", "CHUPAR", "ESPORRA", "QUECA",
/*     */       
/*  64 */       "ACABAR", "AGILIPOLLAO", "ALMEJA", 
/*     */ 
/*     */       
/*  67 */       "HITLER", "NAZI", "SEX", "IDIOT", "CUL", 
/*  68 */       "KKK", "KLUKLUXKLAN", "ADOLF", "MUSSOLINI", 
/*  69 */       "STALIN", "LENIN", "BUSH", "CLINTON", 
/*  70 */       "MICROSOFT", "LINUX", "WINDOWS", "MACINTOSH",
/*     */       
/*  72 */       "JESUS", "ALLAH", "MAHOMET", "BUDDHA", "EINSTEIN", "DIEU"
/*     */     };
/*     */   
/*     */   private static final Pattern m_namesPattern;
/*     */   
/*     */   public static final Pattern m_sentencesPattern;
/*     */   public static final Pattern m_chatSentencesPattern;
/*  79 */   public static final String[] m_charReplacement = new String[] { "&", "~", "#", "@", "£", "¤", "µ", "%", "§", "?", "!" };
/*     */   
/*     */   static {
/*  82 */     String p = "";
/*  83 */     for (int i = 0; i < WORDS_CONTAINING.length; i++) {
/*  84 */       if (i > 0)
/*  85 */         p = String.valueOf(p) + "|"; 
/*  86 */       p = String.valueOf(p) + WORDS_CONTAINING[i];
/*     */     } 
/*  88 */     String s = "((\\S*(" + p + ")\\S*)|(([^a-zA-Z0-9]|\\A)("; int j;
/*  89 */     for (j = 0; j < WORDS_EQUALS.length; j++) {
/*  90 */       if (j > 0)
/*  91 */         s = String.valueOf(s) + "|"; 
/*  92 */       s = String.valueOf(s) + WORDS_EQUALS[j];
/*     */     } 
/*  94 */     s = String.valueOf(s) + ")([^a-zA-Z0-9]|\\z)))";
/*  95 */     p = ".*(" + p + ").*";
/*     */     
/*  97 */     m_chatSentencesPattern = Pattern.compile(s, 2);
/*  98 */     m_sentencesPattern = Pattern.compile(p);
/*     */     
/* 100 */     for (j = 0; j < FORBIDDEN_NAMES.length; j++)
/* 101 */       p = String.valueOf(p) + "|" + FORBIDDEN_NAMES[j]; 
/* 102 */     if (NAMES_STARTING_WITH.length > 0) {
/* 103 */       p = String.valueOf(p) + "|(";
/* 104 */       for (j = 0; j < NAMES_STARTING_WITH.length; j++) {
/* 105 */         if (j > 0)
/* 106 */           p = String.valueOf(p) + "|"; 
/* 107 */         p = String.valueOf(p) + NAMES_STARTING_WITH[j];
/*     */       } 
/* 109 */       p = String.valueOf(p) + ").*";
/*     */     } 
/* 111 */     m_namesPattern = Pattern.compile(p);
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
/*     */   public static boolean validateName(String name) {
/* 125 */     return !(name != null && name.length() != 0 && m_namesPattern.matcher(name.toUpperCase()).matches());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean validateSentence(String sentence) {
/* 133 */     return !(sentence != null && sentence.length() != 0 && m_sentencesPattern.matcher(sentence.toUpperCase()).matches());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String makeValidSentence(String sentence) {
/* 141 */     if (sentence == null || sentence.trim().equals("")) return ""; 
/* 142 */     Matcher matcher = m_chatSentencesPattern.matcher(sentence.trim());
/*     */     
/* 144 */     String str = "";
/* 145 */     String result = "";
/* 146 */     while (matcher.find() && result.length() <= sentence.length()) {
/* 147 */       str = "";
/* 148 */       int taille = matcher.end() - matcher.start();
/*     */       
/* 150 */       for (int i = 0; i < taille; i++) {
/* 151 */         String alea = m_charReplacement[(int)(Math.random() * m_charReplacement.length)];
/* 152 */         str = String.valueOf(str) + alea;
/*     */       } 
/* 154 */       sentence = sentence.replace(matcher.group(), "{" + str + "}");
/*     */     } 
/*     */     
/* 157 */     return sentence;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServe\\utils\WordsModerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */