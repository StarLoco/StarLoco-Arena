/*     */ package org.postgresql.jdbc2;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class EscapedFunctions
/*     */ {
/*     */   public static final String ABS = "abs";
/*     */   public static final String ACOS = "acos";
/*     */   public static final String ASIN = "asin";
/*     */   public static final String ATAN = "atan";
/*     */   public static final String ATAN2 = "atan2";
/*     */   public static final String CEILING = "ceiling";
/*     */   public static final String COS = "cos";
/*     */   public static final String COT = "cot";
/*     */   public static final String DEGREES = "degrees";
/*     */   public static final String EXP = "exp";
/*     */   public static final String FLOOR = "floor";
/*     */   public static final String LOG = "log";
/*     */   public static final String LOG10 = "log10";
/*     */   public static final String MOD = "mod";
/*     */   public static final String PI = "pi";
/*     */   public static final String POWER = "power";
/*     */   public static final String RADIANS = "radians";
/*     */   public static final String RAND = "rand";
/*     */   public static final String ROUND = "round";
/*     */   public static final String SIGN = "sign";
/*     */   public static final String SIN = "sin";
/*     */   public static final String SQRT = "sqrt";
/*     */   public static final String TAN = "tan";
/*     */   public static final String TRUNCATE = "truncate";
/*     */   public static final String ASCII = "ascii";
/*     */   public static final String CHAR = "char";
/*     */   public static final String CONCAT = "concat";
/*     */   public static final String INSERT = "insert";
/*     */   public static final String LCASE = "lcase";
/*     */   public static final String LEFT = "left";
/*     */   public static final String LENGTH = "length";
/*     */   public static final String LOCATE = "locate";
/*     */   public static final String LTRIM = "ltrim";
/*     */   public static final String REPEAT = "repeat";
/*     */   public static final String REPLACE = "replace";
/*     */   public static final String RIGHT = "right";
/*     */   public static final String RTRIM = "rtrim";
/*     */   public static final String SPACE = "space";
/*     */   public static final String SUBSTRING = "substring";
/*     */   public static final String UCASE = "ucase";
/*     */   public static final String CURDATE = "curdate";
/*     */   public static final String CURTIME = "curtime";
/*     */   public static final String DAYNAME = "dayname";
/*     */   public static final String DAYOFMONTH = "dayofmonth";
/*     */   public static final String DAYOFWEEK = "dayofweek";
/*     */   public static final String DAYOFYEAR = "dayofyear";
/*     */   public static final String HOUR = "hour";
/*     */   public static final String MINUTE = "minute";
/*     */   public static final String MONTH = "month";
/*     */   public static final String MONTHNAME = "monthname";
/*     */   public static final String NOW = "now";
/*     */   public static final String QUARTER = "quarter";
/*     */   public static final String SECOND = "second";
/*     */   public static final String WEEK = "week";
/*     */   public static final String YEAR = "year";
/*     */   public static final String DATABASE = "database";
/*     */   public static final String IFNULL = "ifnull";
/*     */   public static final String USER = "user";
/* 102 */   private static Map functionMap = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Method getFunction(String functionName) {
/* 110 */     if (functionMap == null) {
/* 111 */       Method[] arrayMeths = EscapedFunctions.class.getDeclaredMethods();
/* 112 */       functionMap = new HashMap(arrayMeths.length * 2);
/* 113 */       for (int i = 0; i < arrayMeths.length; i++) {
/* 114 */         Method meth = arrayMeths[i];
/* 115 */         if (meth.getName().startsWith("sql"))
/* 116 */           functionMap.put(meth.getName().toLowerCase(), meth); 
/*     */       } 
/*     */     } 
/* 119 */     return (Method)functionMap.get("sql" + functionName.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String sqlceiling(List parsedArgs) throws SQLException {
/* 125 */     StringBuffer buf = new StringBuffer();
/* 126 */     buf.append("ceil(");
/* 127 */     if (parsedArgs.size() != 1) {
/* 128 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "ceiling"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 131 */     buf.append(parsedArgs.get(0));
/* 132 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqllog(List parsedArgs) throws SQLException {
/* 137 */     StringBuffer buf = new StringBuffer();
/* 138 */     buf.append("ln(");
/* 139 */     if (parsedArgs.size() != 1) {
/* 140 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "log"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 143 */     buf.append(parsedArgs.get(0));
/* 144 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqllog10(List parsedArgs) throws SQLException {
/* 149 */     StringBuffer buf = new StringBuffer();
/* 150 */     buf.append("log(");
/* 151 */     if (parsedArgs.size() != 1) {
/* 152 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "log10"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 155 */     buf.append(parsedArgs.get(0));
/* 156 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlpower(List parsedArgs) throws SQLException {
/* 161 */     StringBuffer buf = new StringBuffer();
/* 162 */     buf.append("pow(");
/* 163 */     if (parsedArgs.size() != 2) {
/* 164 */       throw new PSQLException(GT.tr("{0} function takes two and only two arguments.", "power"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 167 */     buf.append(parsedArgs.get(0)).append(',').append(parsedArgs.get(1));
/* 168 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlrand(List parsedArgs) throws SQLException {
/* 173 */     if (parsedArgs.size() == 0)
/* 174 */       return "random()"; 
/* 175 */     if (parsedArgs.size() == 1) {
/* 176 */       return "(setseed(" + parsedArgs.get(0) + ")*0+random())";
/*     */     }
/* 178 */     throw new PSQLException(GT.tr("rand function only takes zero or one argument(the seed)."), PSQLState.SYNTAX_ERROR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String sqltruncate(List parsedArgs) throws SQLException {
/* 185 */     StringBuffer buf = new StringBuffer();
/* 186 */     buf.append("trunc(");
/* 187 */     if (parsedArgs.size() != 2) {
/* 188 */       throw new PSQLException(GT.tr("{0} function takes two and only two arguments.", "truncate"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 191 */     buf.append(parsedArgs.get(0)).append(',').append(parsedArgs.get(1));
/* 192 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String sqlchar(List parsedArgs) throws SQLException {
/* 198 */     StringBuffer buf = new StringBuffer();
/* 199 */     buf.append("chr(");
/* 200 */     if (parsedArgs.size() != 1) {
/* 201 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "char"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 204 */     buf.append(parsedArgs.get(0));
/* 205 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlconcat(List parsedArgs) {
/* 210 */     StringBuffer buf = new StringBuffer();
/* 211 */     buf.append('(');
/* 212 */     for (int iArg = 0; iArg < parsedArgs.size(); iArg++) {
/* 213 */       buf.append(parsedArgs.get(iArg));
/* 214 */       if (iArg != parsedArgs.size() - 1)
/* 215 */         buf.append(" || "); 
/*     */     } 
/* 217 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlinsert(List parsedArgs) throws SQLException {
/* 222 */     StringBuffer buf = new StringBuffer();
/* 223 */     buf.append("overlay(");
/* 224 */     if (parsedArgs.size() != 4) {
/* 225 */       throw new PSQLException(GT.tr("{0} function takes four and only four argument.", "insert"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 228 */     buf.append(parsedArgs.get(0)).append(" placing ").append(parsedArgs.get(3));
/* 229 */     buf.append(" from ").append(parsedArgs.get(1)).append(" for ").append(parsedArgs.get(2));
/* 230 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqllcase(List parsedArgs) throws SQLException {
/* 235 */     StringBuffer buf = new StringBuffer();
/* 236 */     buf.append("lower(");
/* 237 */     if (parsedArgs.size() != 1) {
/* 238 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "lcase"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 241 */     buf.append(parsedArgs.get(0));
/* 242 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlleft(List parsedArgs) throws SQLException {
/* 247 */     StringBuffer buf = new StringBuffer();
/* 248 */     buf.append("substring(");
/* 249 */     if (parsedArgs.size() != 2) {
/* 250 */       throw new PSQLException(GT.tr("{0} function takes two and only two arguments.", "left"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 253 */     buf.append(parsedArgs.get(0)).append(" for ").append(parsedArgs.get(1));
/* 254 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqllength(List parsedArgs) throws SQLException {
/* 259 */     StringBuffer buf = new StringBuffer();
/* 260 */     buf.append("length(trim(trailing from ");
/* 261 */     if (parsedArgs.size() != 1) {
/* 262 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "length"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 265 */     buf.append(parsedArgs.get(0));
/* 266 */     return buf.append("))").toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqllocate(List parsedArgs) throws SQLException {
/* 271 */     if (parsedArgs.size() == 2)
/* 272 */       return "position(" + parsedArgs.get(0) + " in " + parsedArgs.get(1) + ")"; 
/* 273 */     if (parsedArgs.size() == 3) {
/* 274 */       String tmp = "position(" + parsedArgs.get(0) + " in substring(" + parsedArgs.get(1) + " from " + parsedArgs.get(2) + "))";
/* 275 */       return "(" + parsedArgs.get(2) + "*sign(" + tmp + ")+" + tmp + ")";
/*     */     } 
/* 277 */     throw new PSQLException(GT.tr("{0} function takes two or three arguments.", "locate"), PSQLState.SYNTAX_ERROR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String sqlltrim(List parsedArgs) throws SQLException {
/* 284 */     StringBuffer buf = new StringBuffer();
/* 285 */     buf.append("trim(leading from ");
/* 286 */     if (parsedArgs.size() != 1) {
/* 287 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "ltrim"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 290 */     buf.append(parsedArgs.get(0));
/* 291 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlright(List parsedArgs) throws SQLException {
/* 296 */     StringBuffer buf = new StringBuffer();
/* 297 */     buf.append("substring(");
/* 298 */     if (parsedArgs.size() != 2) {
/* 299 */       throw new PSQLException(GT.tr("{0} function takes two and only two arguments.", "right"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 302 */     buf.append(parsedArgs.get(0)).append(" from (length(").append(parsedArgs.get(0)).append(")+1-").append(parsedArgs.get(1));
/* 303 */     return buf.append("))").toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlrtrim(List parsedArgs) throws SQLException {
/* 308 */     StringBuffer buf = new StringBuffer();
/* 309 */     buf.append("trim(trailing from ");
/* 310 */     if (parsedArgs.size() != 1) {
/* 311 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "rtrim"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 314 */     buf.append(parsedArgs.get(0));
/* 315 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlspace(List parsedArgs) throws SQLException {
/* 320 */     StringBuffer buf = new StringBuffer();
/* 321 */     buf.append("repeat(' ',");
/* 322 */     if (parsedArgs.size() != 1) {
/* 323 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "space"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 326 */     buf.append(parsedArgs.get(0));
/* 327 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlsubstring(List parsedArgs) throws SQLException {
/* 332 */     if (parsedArgs.size() == 2)
/* 333 */       return "substr(" + parsedArgs.get(0) + "," + parsedArgs.get(1) + ")"; 
/* 334 */     if (parsedArgs.size() == 3) {
/* 335 */       return "substr(" + parsedArgs.get(0) + "," + parsedArgs.get(1) + "," + parsedArgs.get(2) + ")";
/*     */     }
/* 337 */     throw new PSQLException(GT.tr("{0} function takes two or three arguments.", "substring"), PSQLState.SYNTAX_ERROR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String sqlucase(List parsedArgs) throws SQLException {
/* 344 */     StringBuffer buf = new StringBuffer();
/* 345 */     buf.append("upper(");
/* 346 */     if (parsedArgs.size() != 1) {
/* 347 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "ucase"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 350 */     buf.append(parsedArgs.get(0));
/* 351 */     return buf.append(')').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlcurdate(List parsedArgs) throws SQLException {
/* 356 */     if (parsedArgs.size() != 0) {
/* 357 */       throw new PSQLException(GT.tr("{0} function doesn''t take any argument.", "curdate"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 360 */     return "current_date";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlcurtime(List parsedArgs) throws SQLException {
/* 365 */     if (parsedArgs.size() != 0) {
/* 366 */       throw new PSQLException(GT.tr("{0} function doesn''t take any argument.", "curtime"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 369 */     return "current_time";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqldayname(List parsedArgs) throws SQLException {
/* 374 */     if (parsedArgs.size() != 1) {
/* 375 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "dayname"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 378 */     return "to_char(" + parsedArgs.get(0) + ",'Day')";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqldayofmonth(List parsedArgs) throws SQLException {
/* 383 */     if (parsedArgs.size() != 1) {
/* 384 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "dayofmonth"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 387 */     return "extract(day from " + parsedArgs.get(0) + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String sqldayofweek(List parsedArgs) throws SQLException {
/* 393 */     if (parsedArgs.size() != 1) {
/* 394 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "dayofweek"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 397 */     return "extract(dow from " + parsedArgs.get(0) + ")+1";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqldayofyear(List parsedArgs) throws SQLException {
/* 402 */     if (parsedArgs.size() != 1) {
/* 403 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "dayofyear"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 406 */     return "extract(doy from " + parsedArgs.get(0) + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlhour(List parsedArgs) throws SQLException {
/* 411 */     if (parsedArgs.size() != 1) {
/* 412 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "hour"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 415 */     return "extract(hour from " + parsedArgs.get(0) + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlminute(List parsedArgs) throws SQLException {
/* 420 */     if (parsedArgs.size() != 1) {
/* 421 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "minute"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 424 */     return "extract(minute from " + parsedArgs.get(0) + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlmonth(List parsedArgs) throws SQLException {
/* 429 */     if (parsedArgs.size() != 1) {
/* 430 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "month"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 433 */     return "extract(month from " + parsedArgs.get(0) + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlmonthname(List parsedArgs) throws SQLException {
/* 438 */     if (parsedArgs.size() != 1) {
/* 439 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "monthname"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 442 */     return "to_char(" + parsedArgs.get(0) + ",'Month')";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlquarter(List parsedArgs) throws SQLException {
/* 447 */     if (parsedArgs.size() != 1) {
/* 448 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "quarter"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 451 */     return "extract(quarter from " + parsedArgs.get(0) + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlsecond(List parsedArgs) throws SQLException {
/* 456 */     if (parsedArgs.size() != 1) {
/* 457 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "second"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 460 */     return "extract(second from " + parsedArgs.get(0) + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlweek(List parsedArgs) throws SQLException {
/* 465 */     if (parsedArgs.size() != 1) {
/* 466 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "week"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 469 */     return "extract(week from " + parsedArgs.get(0) + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlyear(List parsedArgs) throws SQLException {
/* 474 */     if (parsedArgs.size() != 1) {
/* 475 */       throw new PSQLException(GT.tr("{0} function takes one and only one argument.", "year"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 478 */     return "extract(year from " + parsedArgs.get(0) + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqldatabase(List parsedArgs) throws SQLException {
/* 483 */     if (parsedArgs.size() != 0) {
/* 484 */       throw new PSQLException(GT.tr("{0} function doesn''t take any argument.", "database"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 487 */     return "current_database()";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqlifnull(List parsedArgs) throws SQLException {
/* 492 */     if (parsedArgs.size() != 2) {
/* 493 */       throw new PSQLException(GT.tr("{0} function takes two and only two arguments.", "ifnull"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 496 */     return "coalesce(" + parsedArgs.get(0) + "," + parsedArgs.get(1) + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sqluser(List parsedArgs) throws SQLException {
/* 501 */     if (parsedArgs.size() != 0) {
/* 502 */       throw new PSQLException(GT.tr("{0} function doesn''t take any argument.", "user"), PSQLState.SYNTAX_ERROR);
/*     */     }
/*     */     
/* 505 */     return "user";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc2\EscapedFunctions.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */