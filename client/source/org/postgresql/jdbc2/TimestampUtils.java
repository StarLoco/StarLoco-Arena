/*     */ package org.postgresql.jdbc2;
/*     */ 
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.TimeZone;
/*     */ import org.postgresql.util.GT;
/*     */ import org.postgresql.util.PSQLException;
/*     */ import org.postgresql.util.PSQLState;
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
/*     */ public class TimestampUtils
/*     */ {
/*  29 */   private StringBuffer sbuf = new StringBuffer();
/*     */   
/*  31 */   private Calendar defaultCal = new GregorianCalendar();
/*     */   
/*     */   private Calendar calCache;
/*     */   
/*     */   private int calCacheZone;
/*     */   private boolean min74;
/*     */   
/*     */   TimestampUtils(boolean min74) {
/*  39 */     this.min74 = min74;
/*     */   }
/*     */   
/*     */   private Calendar getCalendar(int sign, int hr, int min) {
/*  43 */     int unified = sign * (hr * 100 + min);
/*  44 */     if (this.calCache != null && this.calCacheZone == unified) {
/*  45 */       return this.calCache;
/*     */     }
/*  47 */     StringBuffer zoneID = new StringBuffer("GMT");
/*  48 */     zoneID.append((sign < 0) ? 45 : 43);
/*  49 */     if (hr < 10) zoneID.append('0'); 
/*  50 */     zoneID.append(hr);
/*  51 */     if (min < 10) zoneID.append('0'); 
/*  52 */     zoneID.append(min);
/*     */     
/*  54 */     TimeZone syntheticTZ = TimeZone.getTimeZone(zoneID.toString());
/*  55 */     this.calCache = new GregorianCalendar(syntheticTZ);
/*  56 */     this.calCacheZone = unified;
/*  57 */     return this.calCache;
/*     */   }
/*     */   
/*     */   private static class ParsedTimestamp { private ParsedTimestamp() {}
/*     */     
/*  62 */     int era = 1; boolean hasDate = false;
/*  63 */     int year = 1970;
/*  64 */     int month = 1;
/*     */     
/*     */     boolean hasTime = false;
/*  67 */     int day = 1;
/*  68 */     int hour = 0;
/*  69 */     int minute = 0;
/*  70 */     int second = 0;
/*  71 */     int nanos = 0;
/*     */     
/*  73 */     Calendar tz = null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ParsedTimestamp loadCalendar(Calendar defaultTz, String str, String type) throws SQLException {
/*  81 */     char[] s = str.toCharArray();
/*  82 */     int slen = s.length;
/*     */ 
/*     */     
/*  85 */     ParsedTimestamp result = new ParsedTimestamp();
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
/*     */     try {
/* 103 */       int start = skipWhitespace(s, 0);
/* 104 */       int end = firstNonDigit(s, start);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 109 */       if (charAt(s, end) == '-') {
/*     */ 
/*     */ 
/*     */         
/* 113 */         result.hasDate = true;
/*     */ 
/*     */         
/* 116 */         result.year = number(s, start, end);
/* 117 */         start = end + 1;
/*     */ 
/*     */         
/* 120 */         end = firstNonDigit(s, start);
/* 121 */         result.month = number(s, start, end);
/*     */         
/* 123 */         char sep = charAt(s, end);
/* 124 */         if (sep != '-') {
/* 125 */           throw new NumberFormatException("Expected date to be dash-separated, got '" + sep + "'");
/*     */         }
/* 127 */         start = end + 1;
/*     */ 
/*     */         
/* 130 */         end = firstNonDigit(s, start);
/* 131 */         result.day = number(s, start, end);
/*     */         
/* 133 */         start = skipWhitespace(s, end);
/*     */       } 
/*     */ 
/*     */       
/* 137 */       if (Character.isDigit(charAt(s, start))) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 142 */         result.hasTime = true;
/*     */ 
/*     */ 
/*     */         
/* 146 */         end = firstNonDigit(s, start);
/* 147 */         result.hour = number(s, start, end);
/*     */         
/* 149 */         char c1 = charAt(s, end);
/* 150 */         if (c1 != ':') {
/* 151 */           throw new NumberFormatException("Expected time to be colon-separated, got '" + c1 + "'");
/*     */         }
/* 153 */         start = end + 1;
/*     */ 
/*     */ 
/*     */         
/* 157 */         end = firstNonDigit(s, start);
/* 158 */         result.minute = number(s, start, end);
/*     */         
/* 160 */         c1 = charAt(s, end);
/* 161 */         if (c1 != ':') {
/* 162 */           throw new NumberFormatException("Expected time to be colon-separated, got '" + c1 + "'");
/*     */         }
/* 164 */         start = end + 1;
/*     */ 
/*     */ 
/*     */         
/* 168 */         end = firstNonDigit(s, start);
/* 169 */         result.second = number(s, start, end);
/* 170 */         start = end;
/*     */ 
/*     */         
/* 173 */         if (charAt(s, start) == '.') {
/* 174 */           end = firstNonDigit(s, start + 1);
/* 175 */           int num = number(s, start + 1, end);
/*     */           
/* 177 */           for (int numlength = end - start + 1; numlength < 9; numlength++) {
/* 178 */             num *= 10;
/*     */           }
/* 180 */           result.nanos = num;
/* 181 */           start = end;
/*     */         } 
/*     */         
/* 184 */         start = skipWhitespace(s, start);
/*     */       } 
/*     */ 
/*     */       
/* 188 */       char c = charAt(s, start);
/* 189 */       if (c == '-' || c == '+') {
/* 190 */         boolean bool; int tzsign = (c == '-') ? -1 : 1;
/*     */ 
/*     */         
/* 193 */         end = firstNonDigit(s, start + 1);
/* 194 */         int tzhr = number(s, start + 1, end);
/* 195 */         start = end;
/*     */         
/* 197 */         c = charAt(s, start);
/* 198 */         if (c == ':') {
/* 199 */           end = firstNonDigit(s, start + 1);
/* 200 */           bool = number(s, start + 1, end);
/* 201 */           start = end;
/*     */         } else {
/* 203 */           bool = false;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 209 */         result.tz = getCalendar(tzsign, tzhr, bool);
/*     */         
/* 211 */         start = skipWhitespace(s, start);
/*     */       } 
/*     */       
/* 214 */       if (result.hasDate && start < slen) {
/* 215 */         String eraString = new String(s, start, slen - start);
/* 216 */         if (eraString.startsWith("AD")) {
/* 217 */           result.era = 1;
/* 218 */           start += 2;
/* 219 */         } else if (eraString.startsWith("BC")) {
/* 220 */           result.era = 0;
/* 221 */           start += 2;
/*     */         } 
/*     */       } 
/*     */       
/* 225 */       if (start < slen) {
/* 226 */         throw new NumberFormatException("Trailing junk on timestamp: '" + new String(s, start, end - start) + "'");
/*     */       }
/* 228 */       if (!result.hasTime && !result.hasDate) {
/* 229 */         throw new NumberFormatException("Timestamp has neither date nor time");
/*     */       }
/*     */     } catch (NumberFormatException nfe) {
/* 232 */       throw new PSQLException(GT.tr("Bad value for type {0} : {1}", new Object[] { type, s }), PSQLState.BAD_DATETIME_FORMAT, nfe);
/*     */     } 
/*     */     
/* 235 */     return result;
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
/*     */   private static void showParse(String type, String what, Calendar cal, Date result, Calendar resultCal) {}
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
/*     */   private static void showString(String type, Calendar cal, Date value, String result) {}
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
/*     */   public synchronized Timestamp toTimestamp(Calendar cal, String s) throws SQLException {
/* 290 */     if (s == null) {
/* 291 */       return null;
/*     */     }
/* 293 */     int slen = s.length();
/*     */ 
/*     */     
/* 296 */     if (slen == 8 && s.equals("infinity")) {
/* 297 */       return new Timestamp(9223372036825200000L);
/*     */     }
/*     */     
/* 300 */     if (slen == 9 && s.equals("-infinity")) {
/* 301 */       return new Timestamp(-9223372036832400000L);
/*     */     }
/*     */     
/* 304 */     if (cal == null) {
/* 305 */       cal = this.defaultCal;
/*     */     }
/* 307 */     ParsedTimestamp ts = loadCalendar(cal, s, "timestamp");
/* 308 */     Calendar useCal = (ts.tz == null) ? cal : ts.tz;
/* 309 */     useCal.set(0, ts.era);
/* 310 */     useCal.set(1, ts.year);
/* 311 */     useCal.set(2, ts.month - 1);
/* 312 */     useCal.set(5, ts.day);
/* 313 */     useCal.set(11, ts.hour);
/* 314 */     useCal.set(12, ts.minute);
/* 315 */     useCal.set(13, ts.second);
/* 316 */     useCal.set(14, 0);
/*     */     
/* 318 */     Timestamp result = new Timestamp(useCal.getTime().getTime());
/* 319 */     result.setNanos(ts.nanos);
/* 320 */     showParse("timestamp", s, cal, result, useCal);
/* 321 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Time toTime(Calendar cal, String s) throws SQLException {
/* 326 */     if (s == null) {
/* 327 */       return null;
/*     */     }
/* 329 */     int slen = s.length();
/*     */ 
/*     */ 
/*     */     
/* 333 */     if ((slen == 8 && s.equals("infinity")) || (slen == 9 && s.equals("-infinity"))) {
/* 334 */       throw new PSQLException(GT.tr("Infinite value found for timestamp/date. This cannot be represented as time."), PSQLState.DATETIME_OVERFLOW);
/*     */     }
/*     */ 
/*     */     
/* 338 */     if (cal == null) {
/* 339 */       cal = this.defaultCal;
/*     */     }
/* 341 */     ParsedTimestamp ts = loadCalendar(cal, s, "time");
/*     */     
/* 343 */     Calendar useCal = (ts.tz == null) ? cal : ts.tz;
/* 344 */     useCal.set(11, ts.hour);
/* 345 */     useCal.set(12, ts.minute);
/* 346 */     useCal.set(13, ts.second);
/* 347 */     useCal.set(14, (ts.nanos + 500000) / 1000000);
/*     */     
/* 349 */     if (ts.hasDate) {
/*     */       
/* 351 */       useCal.set(0, ts.era);
/* 352 */       useCal.set(1, ts.year);
/* 353 */       useCal.set(2, ts.month - 1);
/* 354 */       useCal.set(5, ts.day);
/* 355 */       cal.setTime(new Date(useCal.getTime().getTime()));
/* 356 */       useCal = cal;
/*     */     } 
/*     */     
/* 359 */     useCal.set(0, 1);
/* 360 */     useCal.set(1, 1970);
/* 361 */     useCal.set(2, 0);
/* 362 */     useCal.set(5, 1);
/*     */     
/* 364 */     Time result = new Time(useCal.getTime().getTime());
/* 365 */     showParse("time", s, cal, result, useCal);
/* 366 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Date toDate(Calendar cal, String s) throws SQLException {
/* 371 */     if (s == null) {
/* 372 */       return null;
/*     */     }
/* 374 */     int slen = s.length();
/*     */ 
/*     */     
/* 377 */     if (slen == 8 && s.equals("infinity")) {
/* 378 */       return new Date(9223372036825200000L);
/*     */     }
/*     */     
/* 381 */     if (slen == 9 && s.equals("-infinity")) {
/* 382 */       return new Date(-9223372036832400000L);
/*     */     }
/*     */     
/* 385 */     if (cal == null) {
/* 386 */       cal = this.defaultCal;
/*     */     }
/* 388 */     ParsedTimestamp ts = loadCalendar(cal, s, "date");
/* 389 */     Calendar useCal = (ts.tz == null) ? cal : ts.tz;
/*     */     
/* 391 */     useCal.set(0, ts.era);
/* 392 */     useCal.set(1, ts.year);
/* 393 */     useCal.set(2, ts.month - 1);
/* 394 */     useCal.set(5, ts.day);
/*     */     
/* 396 */     if (ts.hasTime) {
/*     */       
/* 398 */       useCal.set(11, ts.hour);
/* 399 */       useCal.set(12, ts.minute);
/* 400 */       useCal.set(13, ts.second);
/* 401 */       useCal.set(14, (ts.nanos + 500000) / 1000000);
/* 402 */       cal.setTime(new Date(useCal.getTime().getTime()));
/* 403 */       useCal = cal;
/*     */     } 
/*     */     
/* 406 */     useCal.set(11, 0);
/* 407 */     useCal.set(12, 0);
/* 408 */     useCal.set(13, 0);
/* 409 */     useCal.set(14, 0);
/*     */     
/* 411 */     Date result = new Date(useCal.getTime().getTime());
/* 412 */     showParse("date", s, cal, result, useCal);
/* 413 */     return result;
/*     */   }
/*     */   
/*     */   public synchronized String toString(Calendar cal, Timestamp x) {
/* 417 */     if (cal == null) {
/* 418 */       cal = this.defaultCal;
/*     */     }
/* 420 */     cal.setTime(x);
/* 421 */     this.sbuf.setLength(0);
/*     */     
/* 423 */     if (x.getTime() == 9223372036825200000L) {
/* 424 */       this.sbuf.append("infinity");
/* 425 */     } else if (x.getTime() == -9223372036832400000L) {
/* 426 */       this.sbuf.append("-infinity");
/*     */     } else {
/* 428 */       appendDate(this.sbuf, cal);
/* 429 */       this.sbuf.append(' ');
/* 430 */       appendTime(this.sbuf, cal, x.getNanos());
/* 431 */       appendTimeZone(this.sbuf, cal);
/* 432 */       appendEra(this.sbuf, cal);
/*     */     } 
/*     */     
/* 435 */     showString("timestamp", cal, x, this.sbuf.toString());
/* 436 */     return this.sbuf.toString();
/*     */   }
/*     */   
/*     */   public synchronized String toString(Calendar cal, Date x) {
/* 440 */     if (cal == null) {
/* 441 */       cal = this.defaultCal;
/*     */     }
/* 443 */     cal.setTime(x);
/* 444 */     this.sbuf.setLength(0);
/*     */     
/* 446 */     if (x.getTime() == 9223372036825200000L) {
/* 447 */       this.sbuf.append("infinity");
/* 448 */     } else if (x.getTime() == -9223372036832400000L) {
/* 449 */       this.sbuf.append("-infinity");
/*     */     } else {
/* 451 */       appendDate(this.sbuf, cal);
/* 452 */       appendEra(this.sbuf, cal);
/* 453 */       appendTimeZone(this.sbuf, cal);
/*     */     } 
/*     */     
/* 456 */     showString("date", cal, x, this.sbuf.toString());
/*     */     
/* 458 */     return this.sbuf.toString();
/*     */   }
/*     */   
/*     */   public synchronized String toString(Calendar cal, Time x) {
/* 462 */     if (cal == null) {
/* 463 */       cal = this.defaultCal;
/*     */     }
/* 465 */     cal.setTime(x);
/* 466 */     this.sbuf.setLength(0);
/*     */     
/* 468 */     appendTime(this.sbuf, cal, cal.get(14) * 1000000);
/*     */ 
/*     */     
/* 471 */     if (this.min74) {
/* 472 */       appendTimeZone(this.sbuf, cal);
/*     */     }
/* 474 */     showString("time", cal, x, this.sbuf.toString());
/*     */     
/* 476 */     return this.sbuf.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendDate(StringBuffer sb, Calendar cal) {
/* 481 */     int l_year = cal.get(1);
/*     */ 
/*     */ 
/*     */     
/* 485 */     int l_yearlen = String.valueOf(l_year).length();
/* 486 */     for (int i = 4; i > l_yearlen; i--)
/*     */     {
/* 488 */       sb.append("0");
/*     */     }
/*     */     
/* 491 */     sb.append(l_year);
/* 492 */     sb.append('-');
/* 493 */     int l_month = cal.get(2) + 1;
/* 494 */     if (l_month < 10)
/* 495 */       sb.append('0'); 
/* 496 */     sb.append(l_month);
/* 497 */     sb.append('-');
/* 498 */     int l_day = cal.get(5);
/* 499 */     if (l_day < 10)
/* 500 */       sb.append('0'); 
/* 501 */     sb.append(l_day);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendTime(StringBuffer sb, Calendar cal, int nanos) {
/* 506 */     int hours = cal.get(11);
/* 507 */     if (hours < 10)
/* 508 */       sb.append('0'); 
/* 509 */     sb.append(hours);
/*     */     
/* 511 */     sb.append(':');
/* 512 */     int minutes = cal.get(12);
/* 513 */     if (minutes < 10)
/* 514 */       sb.append('0'); 
/* 515 */     sb.append(minutes);
/*     */     
/* 517 */     sb.append(':');
/* 518 */     int seconds = cal.get(13);
/* 519 */     if (seconds < 10)
/* 520 */       sb.append('0'); 
/* 521 */     sb.append(seconds);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 528 */     char[] decimalStr = { '0', '0', '0', '0', '0', '0', '0', '0', '0' };
/* 529 */     char[] nanoStr = Integer.toString(nanos).toCharArray();
/* 530 */     System.arraycopy(nanoStr, 0, decimalStr, decimalStr.length - nanoStr.length, nanoStr.length);
/* 531 */     sb.append('.');
/* 532 */     sb.append(decimalStr, 0, 6);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendTimeZone(StringBuffer sb, Calendar cal) {
/* 537 */     int offset = (cal.get(15) + cal.get(16)) / 1000 / 60;
/*     */     
/* 539 */     int absoff = Math.abs(offset);
/* 540 */     int hours = absoff / 60;
/* 541 */     int mins = absoff - hours * 60;
/*     */     
/* 543 */     sb.append((offset >= 0) ? " +" : " -");
/*     */     
/* 545 */     if (hours < 10)
/* 546 */       sb.append('0'); 
/* 547 */     sb.append(hours);
/*     */     
/* 549 */     if (mins < 10)
/* 550 */       sb.append('0'); 
/* 551 */     sb.append(mins);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendEra(StringBuffer sb, Calendar cal) {
/* 556 */     if (cal.get(0) == 0) {
/* 557 */       sb.append(" BC");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static int skipWhitespace(char[] s, int start) {
/* 563 */     int slen = s.length;
/* 564 */     for (int i = start; i < slen; i++) {
/* 565 */       if (!Character.isSpace(s[i]))
/* 566 */         return i; 
/*     */     } 
/* 568 */     return slen;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int firstNonDigit(char[] s, int start) {
/* 573 */     int slen = s.length;
/* 574 */     for (int i = start; i < slen; i++) {
/* 575 */       if (!Character.isDigit(s[i])) {
/* 576 */         return i;
/*     */       }
/*     */     } 
/* 579 */     return slen;
/*     */   }
/*     */   
/*     */   private static int number(char[] s, int start, int end) {
/* 583 */     if (start >= end) {
/* 584 */       throw new NumberFormatException();
/*     */     }
/* 586 */     int n = 0;
/* 587 */     for (int i = start; i < end; i++)
/*     */     {
/* 589 */       n = 10 * n + s[i] - 48;
/*     */     }
/* 591 */     return n;
/*     */   }
/*     */   
/*     */   private static char charAt(char[] s, int pos) {
/* 595 */     if (pos >= 0 && pos < s.length) {
/* 596 */       return s[pos];
/*     */     }
/* 598 */     return Character.MIN_VALUE;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc2\TimestampUtils.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */