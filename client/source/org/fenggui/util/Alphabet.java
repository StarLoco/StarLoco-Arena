/*     */ package org.fenggui.util;
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
/*     */ public class Alphabet
/*     */ {
/*  40 */   public static final Alphabet ENGLISH = new Alphabet();
/*     */ 
/*     */   
/*  43 */   public static final Alphabet GERMAN = new Alphabet(new char[] { 'Ä', 'Ö', 'Ü', 'ä', 'ö', 'ü', 'ß' });
/*     */ 
/*     */   
/*  46 */   public static final Alphabet FRENCH = new Alphabet(new char[] { 'À', 
/*  47 */         'Â', 
/*  48 */         'Æ', 'È', 'É', 'Ê', 'Ë', 'Î', 'Ï', 'Ô', 'Œ', 'Ù', 
/*  49 */         'Û', 'Ü', 'Ÿ', 'Ç', 
/*  50 */         'à', 'â', 'æ', 'è', 'é', 'ê', 'ë', 'î', 'ï', 'ô', 
/*  51 */         'œ', 'ù', 'û', 'ü', 'ÿ', 'ç' });
/*  52 */   public static final Alphabet ESTONIAN = new Alphabet(new char[] {
/*  53 */         'ö', 'ä', 'õ', 'ü', 
/*  54 */         'Ö', 'Ä', 'Õ', 'Ü'
/*     */       });
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static Alphabet defaultAlphabet = ENGLISH;
/*     */ 
/*     */   
/*     */   private char[] alphabet;
/*     */ 
/*     */   
/*     */   public static Alphabet getDefaultAlphabet() {
/*  66 */     return defaultAlphabet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDefaultAlphabet(Alphabet alphabet) {
/*  75 */     defaultAlphabet = alphabet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Alphabet() {
/*  86 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  93 */       .alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`1234567890-=~!@#$%^&*()_+¤[]{}\\|:;\"'<>,.?/ ".toCharArray(); } public Alphabet(char[] additionalChars) { this.alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`1234567890-=~!@#$%^&*()_+¤[]{}\\|:;\"'<>,.?/ ".toCharArray();
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
/* 104 */     String a = new String(additionalChars);
/* 105 */     a = String.valueOf(a) + new String(this.alphabet);
/* 106 */     this.alphabet = a.toCharArray(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] getAlphabet() {
/* 115 */     return this.alphabet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean valid(char c) {
/*     */     byte b;
/*     */     int i;
/*     */     char[] arrayOfChar;
/* 126 */     for (arrayOfChar = this.alphabet, b = 0, i = arrayOfChar.length; b < i; ) { char p = arrayOfChar[b];
/* 127 */       if (c == p) return true;  b++; }
/*     */     
/* 129 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\Alphabet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */