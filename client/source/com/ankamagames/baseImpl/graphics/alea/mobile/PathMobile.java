/*     */ package com.ankamagames.baseImpl.graphics.alea.mobile;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle.MovementStyleManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle.PathMovementStyle;
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindMover;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindParameters;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFinder;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
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
/*     */ public class PathMobile
/*     */   extends Mobile
/*     */   implements PathFindMover, StyleMobile
/*     */ {
/*  28 */   public static final int[][] EIGHT_DIRECTION_SHIFT = new int[][] { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 0, -1 }, { -1 }, { 0, 1 }, { 1 }, { 1, 1 } };
/*  29 */   public static final int[][] FOUR_DIRECTION_SHIFT = new int[][] { { -1 }, { 0, -1 }, { 1 }, { 0, 1 } };
/*     */   
/*  31 */   private static int TIME_BETWEEN_POSITION_UPDATE = 35;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final short DEFAULT_MOBILE_JUMP_HEIGHT = 4;
/*     */ 
/*     */ 
/*     */   
/*  40 */   public int[][] m_directionShift = EIGHT_DIRECTION_SHIFT;
/*     */   
/*  42 */   private short m_jumpHeight = 4;
/*     */   
/*     */   private int m_currentPathStepIndex;
/*     */   
/*     */   private long m_lastTime;
/*     */   
/*     */   private long m_timeRest;
/*  49 */   private PathMovementStyle m_movementStyle = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PathFindResult m_currentPath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathMobile(long id) {
/*  63 */     super(id);
/*  64 */     setMovementStyle(MovementStyleManager.WALK_STYLE);
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
/*     */   public PathMobile(long id, double worldX, double worldY, double altitude) {
/*  76 */     super(id, worldX, worldY, altitude);
/*  77 */     setMovementStyle(MovementStyleManager.WALK_STYLE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathMobile(long id, double worldX, double worldY) {
/*  88 */     super(id, worldX, worldY);
/*  89 */     setMovementStyle(MovementStyleManager.WALK_STYLE);
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
/*     */ 
/*     */   
/*     */   public void process(AleaWorldScene scene, long realTime, int frameCount) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield m_timeRest : J
/*     */     //   4: lload_2
/*     */     //   5: ladd
/*     */     //   6: aload_0
/*     */     //   7: getfield m_lastTime : J
/*     */     //   10: lsub
/*     */     //   11: lstore #5
/*     */     //   13: lload #5
/*     */     //   15: getstatic com/ankamagames/baseImpl/graphics/alea/mobile/PathMobile.TIME_BETWEEN_POSITION_UPDATE : I
/*     */     //   18: i2l
/*     */     //   19: lcmp
/*     */     //   20: iflt -> 600
/*     */     //   23: iconst_0
/*     */     //   24: istore #7
/*     */     //   26: aload_0
/*     */     //   27: getfield m_currentPath : Lcom/ankamagames/framework/ai/pathfinder/PathFindResult;
/*     */     //   30: ifnull -> 581
/*     */     //   33: aload_0
/*     */     //   34: getfield m_currentPath : Lcom/ankamagames/framework/ai/pathfinder/PathFindResult;
/*     */     //   37: invokevirtual getPathLength : ()I
/*     */     //   40: aload_0
/*     */     //   41: getfield m_currentPathStepIndex : I
/*     */     //   44: if_icmple -> 573
/*     */     //   47: aload_0
/*     */     //   48: getfield m_currentPath : Lcom/ankamagames/framework/ai/pathfinder/PathFindResult;
/*     */     //   51: aload_0
/*     */     //   52: getfield m_currentPathStepIndex : I
/*     */     //   55: invokevirtual getPathStep : (I)[I
/*     */     //   58: astore #8
/*     */     //   60: aload_0
/*     */     //   61: getfield m_currentPath : Lcom/ankamagames/framework/ai/pathfinder/PathFindResult;
/*     */     //   64: invokevirtual getPathLength : ()I
/*     */     //   67: aload_0
/*     */     //   68: getfield m_currentPathStepIndex : I
/*     */     //   71: iconst_1
/*     */     //   72: iadd
/*     */     //   73: if_icmpgt -> 194
/*     */     //   76: aload_0
/*     */     //   77: aload #8
/*     */     //   79: getstatic com/ankamagames/framework/ai/pathfinder/PathFindResult.STEP_X : I
/*     */     //   82: iaload
/*     */     //   83: i2d
/*     */     //   84: putfield m_worldX : D
/*     */     //   87: aload_0
/*     */     //   88: aload #8
/*     */     //   90: getstatic com/ankamagames/framework/ai/pathfinder/PathFindResult.STEP_Y : I
/*     */     //   93: iaload
/*     */     //   94: i2d
/*     */     //   95: putfield m_worldY : D
/*     */     //   98: aload_0
/*     */     //   99: aload #8
/*     */     //   101: getstatic com/ankamagames/framework/ai/pathfinder/PathFindResult.STEP_Z : I
/*     */     //   104: iaload
/*     */     //   105: i2d
/*     */     //   106: putfield m_altitude : D
/*     */     //   109: aload_0
/*     */     //   110: invokevirtual getCarriedMobile : ()Lcom/ankamagames/baseImpl/graphics/alea/mobile/Mobile;
/*     */     //   113: ifnull -> 141
/*     */     //   116: aload_0
/*     */     //   117: invokevirtual getCarriedMobile : ()Lcom/ankamagames/baseImpl/graphics/alea/mobile/Mobile;
/*     */     //   120: aload_0
/*     */     //   121: getfield m_worldX : D
/*     */     //   124: aload_0
/*     */     //   125: getfield m_worldY : D
/*     */     //   128: aload_0
/*     */     //   129: getfield m_altitude : D
/*     */     //   132: aload_0
/*     */     //   133: invokevirtual getHeight : ()S
/*     */     //   136: i2d
/*     */     //   137: dadd
/*     */     //   138: invokevirtual setWorldPosition : (DDD)V
/*     */     //   141: aload_0
/*     */     //   142: getfield m_movementStyle : Lcom/ankamagames/baseImpl/graphics/alea/mobile/movementStyle/PathMovementStyle;
/*     */     //   145: invokeinterface onStandingOnLastCell : ()V
/*     */     //   150: lload #5
/*     */     //   152: aload_0
/*     */     //   153: getfield m_movementStyle : Lcom/ankamagames/baseImpl/graphics/alea/mobile/movementStyle/PathMovementStyle;
/*     */     //   156: invokeinterface getCellSpeed : ()I
/*     */     //   161: i2l
/*     */     //   162: lcmp
/*     */     //   163: iflt -> 564
/*     */     //   166: lload #5
/*     */     //   168: aload_0
/*     */     //   169: getfield m_movementStyle : Lcom/ankamagames/baseImpl/graphics/alea/mobile/movementStyle/PathMovementStyle;
/*     */     //   172: invokeinterface getCellSpeed : ()I
/*     */     //   177: i2l
/*     */     //   178: lsub
/*     */     //   179: lstore #5
/*     */     //   181: aload_0
/*     */     //   182: dup
/*     */     //   183: getfield m_currentPathStepIndex : I
/*     */     //   186: iconst_1
/*     */     //   187: iadd
/*     */     //   188: putfield m_currentPathStepIndex : I
/*     */     //   191: goto -> 33
/*     */     //   194: aload_0
/*     */     //   195: getfield m_currentPath : Lcom/ankamagames/framework/ai/pathfinder/PathFindResult;
/*     */     //   198: aload_0
/*     */     //   199: getfield m_currentPathStepIndex : I
/*     */     //   202: iconst_1
/*     */     //   203: iadd
/*     */     //   204: invokevirtual getPathStep : (I)[I
/*     */     //   207: astore #9
/*     */     //   209: aload #9
/*     */     //   211: getstatic com/ankamagames/framework/ai/pathfinder/PathFindResult.STEP_X : I
/*     */     //   214: iaload
/*     */     //   215: aload #8
/*     */     //   217: getstatic com/ankamagames/framework/ai/pathfinder/PathFindResult.STEP_X : I
/*     */     //   220: iaload
/*     */     //   221: isub
/*     */     //   222: istore #10
/*     */     //   224: aload #9
/*     */     //   226: getstatic com/ankamagames/framework/ai/pathfinder/PathFindResult.STEP_Y : I
/*     */     //   229: iaload
/*     */     //   230: aload #8
/*     */     //   232: getstatic com/ankamagames/framework/ai/pathfinder/PathFindResult.STEP_Y : I
/*     */     //   235: iaload
/*     */     //   236: isub
/*     */     //   237: istore #11
/*     */     //   239: aload #9
/*     */     //   241: getstatic com/ankamagames/framework/ai/pathfinder/PathFindResult.STEP_Z : I
/*     */     //   244: iaload
/*     */     //   245: aload #8
/*     */     //   247: getstatic com/ankamagames/framework/ai/pathfinder/PathFindResult.STEP_Z : I
/*     */     //   250: iaload
/*     */     //   251: isub
/*     */     //   252: istore #12
/*     */     //   254: iload #10
/*     */     //   256: i2d
/*     */     //   257: ldc2_w 2.0
/*     */     //   260: invokestatic pow : (DD)D
/*     */     //   263: iload #11
/*     */     //   265: i2d
/*     */     //   266: ldc2_w 2.0
/*     */     //   269: invokestatic pow : (DD)D
/*     */     //   272: dadd
/*     */     //   273: invokestatic sqrt : (D)D
/*     */     //   276: dstore #13
/*     */     //   278: lload #5
/*     */     //   280: l2d
/*     */     //   281: aload_0
/*     */     //   282: getfield m_movementStyle : Lcom/ankamagames/baseImpl/graphics/alea/mobile/movementStyle/PathMovementStyle;
/*     */     //   285: invokeinterface getCellSpeed : ()I
/*     */     //   290: i2d
/*     */     //   291: dload #13
/*     */     //   293: dmul
/*     */     //   294: dcmpl
/*     */     //   295: iflt -> 334
/*     */     //   298: lload #5
/*     */     //   300: l2d
/*     */     //   301: aload_0
/*     */     //   302: getfield m_movementStyle : Lcom/ankamagames/baseImpl/graphics/alea/mobile/movementStyle/PathMovementStyle;
/*     */     //   305: invokeinterface getCellSpeed : ()I
/*     */     //   310: i2d
/*     */     //   311: dload #13
/*     */     //   313: dmul
/*     */     //   314: dsub
/*     */     //   315: d2l
/*     */     //   316: lstore #5
/*     */     //   318: aload_0
/*     */     //   319: dup
/*     */     //   320: getfield m_currentPathStepIndex : I
/*     */     //   323: iconst_1
/*     */     //   324: iadd
/*     */     //   325: putfield m_currentPathStepIndex : I
/*     */     //   328: iconst_1
/*     */     //   329: istore #7
/*     */     //   331: goto -> 33
/*     */     //   334: lload #5
/*     */     //   336: l2f
/*     */     //   337: f2d
/*     */     //   338: aload_0
/*     */     //   339: getfield m_movementStyle : Lcom/ankamagames/baseImpl/graphics/alea/mobile/movementStyle/PathMovementStyle;
/*     */     //   342: invokeinterface getCellSpeed : ()I
/*     */     //   347: i2d
/*     */     //   348: dload #13
/*     */     //   350: dmul
/*     */     //   351: ddiv
/*     */     //   352: dstore #15
/*     */     //   354: aload_0
/*     */     //   355: aload #8
/*     */     //   357: getstatic com/ankamagames/framework/ai/pathfinder/PathFindResult.STEP_X : I
/*     */     //   360: iaload
/*     */     //   361: i2d
/*     */     //   362: dload #15
/*     */     //   364: iload #10
/*     */     //   366: i2d
/*     */     //   367: dmul
/*     */     //   368: dadd
/*     */     //   369: putfield m_worldX : D
/*     */     //   372: aload_0
/*     */     //   373: aload #8
/*     */     //   375: getstatic com/ankamagames/framework/ai/pathfinder/PathFindResult.STEP_Y : I
/*     */     //   378: iaload
/*     */     //   379: i2d
/*     */     //   380: dload #15
/*     */     //   382: iload #11
/*     */     //   384: i2d
/*     */     //   385: dmul
/*     */     //   386: dadd
/*     */     //   387: putfield m_worldY : D
/*     */     //   390: aload_0
/*     */     //   391: aload #8
/*     */     //   393: getstatic com/ankamagames/framework/ai/pathfinder/PathFindResult.STEP_Z : I
/*     */     //   396: iaload
/*     */     //   397: i2d
/*     */     //   398: dload #15
/*     */     //   400: iload #12
/*     */     //   402: i2d
/*     */     //   403: dmul
/*     */     //   404: dadd
/*     */     //   405: putfield m_altitude : D
/*     */     //   408: aload_0
/*     */     //   409: invokevirtual getCarriedMobile : ()Lcom/ankamagames/baseImpl/graphics/alea/mobile/Mobile;
/*     */     //   412: ifnull -> 440
/*     */     //   415: aload_0
/*     */     //   416: invokevirtual getCarriedMobile : ()Lcom/ankamagames/baseImpl/graphics/alea/mobile/Mobile;
/*     */     //   419: aload_0
/*     */     //   420: getfield m_worldX : D
/*     */     //   423: aload_0
/*     */     //   424: getfield m_worldY : D
/*     */     //   427: aload_0
/*     */     //   428: getfield m_altitude : D
/*     */     //   431: aload_0
/*     */     //   432: invokevirtual getHeight : ()S
/*     */     //   435: i2d
/*     */     //   436: dadd
/*     */     //   437: invokevirtual setWorldPosition : (DDD)V
/*     */     //   440: aload_0
/*     */     //   441: getfield m_movementStyle : Lcom/ankamagames/baseImpl/graphics/alea/mobile/movementStyle/PathMovementStyle;
/*     */     //   444: iload #12
/*     */     //   446: invokeinterface isAirImpulsionNeeded : (I)Z
/*     */     //   451: ifeq -> 521
/*     */     //   454: dload #15
/*     */     //   456: ldc2_w 0.5
/*     */     //   459: dcmpl
/*     */     //   460: ifle -> 472
/*     */     //   463: dconst_1
/*     */     //   464: dload #15
/*     */     //   466: dsub
/*     */     //   467: dstore #17
/*     */     //   469: goto -> 476
/*     */     //   472: dload #15
/*     */     //   474: dstore #17
/*     */     //   476: aload_0
/*     */     //   477: getfield m_movementStyle : Lcom/ankamagames/baseImpl/graphics/alea/mobile/movementStyle/PathMovementStyle;
/*     */     //   480: dload #15
/*     */     //   482: invokeinterface onMovingOnAir : (D)V
/*     */     //   487: iload #7
/*     */     //   489: ifeq -> 496
/*     */     //   492: aload_0
/*     */     //   493: invokevirtual forceReloadAnimation : ()V
/*     */     //   496: aload_0
/*     */     //   497: dup
/*     */     //   498: getfield m_altitude : D
/*     */     //   501: dload #17
/*     */     //   503: aload_0
/*     */     //   504: getfield m_movementStyle : Lcom/ankamagames/baseImpl/graphics/alea/mobile/movementStyle/PathMovementStyle;
/*     */     //   507: invokeinterface getAirImpulsion : ()I
/*     */     //   512: i2d
/*     */     //   513: dmul
/*     */     //   514: dadd
/*     */     //   515: putfield m_altitude : D
/*     */     //   518: goto -> 546
/*     */     //   521: aload_0
/*     */     //   522: getfield m_currentPath : Lcom/ankamagames/framework/ai/pathfinder/PathFindResult;
/*     */     //   525: invokevirtual getPathLength : ()I
/*     */     //   528: aload_0
/*     */     //   529: getfield m_currentPathStepIndex : I
/*     */     //   532: isub
/*     */     //   533: istore #17
/*     */     //   535: aload_0
/*     */     //   536: getfield m_movementStyle : Lcom/ankamagames/baseImpl/graphics/alea/mobile/movementStyle/PathMovementStyle;
/*     */     //   539: iload #17
/*     */     //   541: invokeinterface onMovingOnGround : (I)V
/*     */     //   546: aload_0
/*     */     //   547: getfield m_movementStyle : Lcom/ankamagames/baseImpl/graphics/alea/mobile/movementStyle/PathMovementStyle;
/*     */     //   550: iload #10
/*     */     //   552: i2d
/*     */     //   553: iload #11
/*     */     //   555: i2d
/*     */     //   556: invokestatic getDirection8FromVector : (DD)Lcom/ankamagames/framework/kernel/core/maths/Direction8;
/*     */     //   559: invokeinterface onDirectionChanged : (Lcom/ankamagames/framework/kernel/core/maths/Direction8;)V
/*     */     //   564: aload_0
/*     */     //   565: lload #5
/*     */     //   567: putfield m_timeRest : J
/*     */     //   570: goto -> 595
/*     */     //   573: aload_0
/*     */     //   574: aconst_null
/*     */     //   575: putfield m_currentPath : Lcom/ankamagames/framework/ai/pathfinder/PathFindResult;
/*     */     //   578: goto -> 595
/*     */     //   581: aload_0
/*     */     //   582: getfield m_movementStyle : Lcom/ankamagames/baseImpl/graphics/alea/mobile/movementStyle/PathMovementStyle;
/*     */     //   585: invokeinterface onWaiting : ()V
/*     */     //   590: aload_0
/*     */     //   591: lconst_0
/*     */     //   592: putfield m_timeRest : J
/*     */     //   595: aload_0
/*     */     //   596: lload_2
/*     */     //   597: putfield m_lastTime : J
/*     */     //   600: aload_0
/*     */     //   601: aload_1
/*     */     //   602: lload_2
/*     */     //   603: iload #4
/*     */     //   605: invokespecial process : (Lcom/ankamagames/baseImpl/graphics/alea/display/AleaWorldScene;JI)V
/*     */     //   608: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #100	-> 0
/*     */     //   #102	-> 13
/*     */     //   #103	-> 23
/*     */     //   #106	-> 26
/*     */     //   #108	-> 33
/*     */     //   #110	-> 47
/*     */     //   #114	-> 60
/*     */     //   #116	-> 76
/*     */     //   #117	-> 87
/*     */     //   #118	-> 98
/*     */     //   #120	-> 109
/*     */     //   #121	-> 116
/*     */     //   #124	-> 141
/*     */     //   #126	-> 150
/*     */     //   #127	-> 166
/*     */     //   #128	-> 181
/*     */     //   #129	-> 191
/*     */     //   #134	-> 194
/*     */     //   #135	-> 209
/*     */     //   #136	-> 224
/*     */     //   #137	-> 239
/*     */     //   #139	-> 254
/*     */     //   #141	-> 278
/*     */     //   #142	-> 298
/*     */     //   #143	-> 318
/*     */     //   #144	-> 328
/*     */     //   #145	-> 331
/*     */     //   #148	-> 334
/*     */     //   #150	-> 354
/*     */     //   #151	-> 372
/*     */     //   #152	-> 390
/*     */     //   #154	-> 408
/*     */     //   #155	-> 415
/*     */     //   #159	-> 440
/*     */     //   #163	-> 454
/*     */     //   #164	-> 463
/*     */     //   #166	-> 472
/*     */     //   #169	-> 476
/*     */     //   #171	-> 487
/*     */     //   #172	-> 492
/*     */     //   #174	-> 496
/*     */     //   #177	-> 521
/*     */     //   #178	-> 535
/*     */     //   #181	-> 546
/*     */     //   #184	-> 564
/*     */     //   #186	-> 573
/*     */     //   #188	-> 578
/*     */     //   #191	-> 581
/*     */     //   #192	-> 590
/*     */     //   #195	-> 595
/*     */     //   #198	-> 600
/*     */     //   #199	-> 608
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	609	0	this	Lcom/ankamagames/baseImpl/graphics/alea/mobile/PathMobile;
/*     */     //   0	609	1	scene	Lcom/ankamagames/baseImpl/graphics/alea/display/AleaWorldScene;
/*     */     //   0	609	2	realTime	J
/*     */     //   0	609	4	frameCount	I
/*     */     //   13	596	5	timeSinceLastMovement	J
/*     */     //   26	574	7	newPathCell	Z
/*     */     //   60	513	8	currentStep	[I
/*     */     //   209	355	9	nextStep	[I
/*     */     //   224	340	10	dx	I
/*     */     //   239	325	11	dy	I
/*     */     //   254	310	12	dz	I
/*     */     //   278	286	13	distance	D
/*     */     //   354	210	15	cellPositionPercent	D
/*     */     //   469	3	17	airImpulsion	D
/*     */     //   476	45	17	airImpulsion	D
/*     */     //   535	11	17	remainPathLength	I
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
/*     */ 
/*     */   
/*     */   public short getJumpMaxAscendingHeight() {
/* 209 */     return this.m_jumpHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getJumpMaxDescendingHeight() {
/* 218 */     return this.m_jumpHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[][] getDirectionShift() {
/* 226 */     return this.m_directionShift;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirectionShift(int[][] directionShift) {
/* 233 */     this.m_directionShift = directionShift;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getJumpHeight() {
/* 240 */     return this.m_jumpHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJumpHeight(short jumpHeight) {
/* 247 */     this.m_jumpHeight = jumpHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMovementStyle(String movementStyleKey) {
/* 255 */     PathMovementStyle movementStyle = MovementStyleManager.getInstance().getMovementStyle(movementStyleKey);
/*     */     
/* 257 */     if (movementStyle == null) {
/* 258 */       m_logger.error("Le style : " + movementStyle + " n'existe pas.");
/*     */       
/*     */       return;
/*     */     } 
/* 262 */     if (this.m_movementStyle != null) {
/* 263 */       this.m_movementStyle.setMobile(null);
/*     */     }
/* 265 */     movementStyle.setMobile(this);
/* 266 */     this.m_movementStyle = movementStyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathMovementStyle getMovementStyle() {
/* 273 */     return this.m_movementStyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPath(PathFindResult node, boolean recompute) {
/* 284 */     if (node.getPathLength() < 2) {
/*     */       return;
/*     */     }
/* 287 */     if (recompute)
/*     */     {
/*     */       
/* 290 */       if (this.m_currentPath != null)
/*     */       {
/*     */         
/* 293 */         if (this.m_currentPath.getPathLength() > this.m_currentPathStepIndex + 2) {
/*     */           
/* 295 */           int[] currentNode = this.m_currentPath.getPathStep(this.m_currentPathStepIndex + 1);
/*     */ 
/*     */           
/* 298 */           int[] lastStep = node.getLastStep();
/* 299 */           if (lastStep != null) {
/*     */             
/* 301 */             PathFindParameters defaultParameters = new PathFindParameters();
/* 302 */             defaultParameters.m_searchLimit = 1000;
/*     */             
/* 304 */             Point3 from = new Point3(currentNode[PathFindResult.STEP_X], currentNode[PathFindResult.STEP_Y], (short)currentNode[PathFindResult.STEP_Z]);
/* 305 */             Point3 to = new Point3(lastStep[PathFindResult.STEP_X], lastStep[PathFindResult.STEP_Y], (short)lastStep[PathFindResult.STEP_Z]);
/*     */ 
/*     */ 
/*     */             
/* 309 */             PathFinder pathFinder = PathFinder.checkOut();
/* 310 */             PathFindResult result = pathFinder.compute(this, (CellInformationProvider)WorldManager.getInstance(), from, to, defaultParameters);
/*     */             
/* 312 */             if (result.isPathFound()) {
/*     */               
/* 314 */               int mixPathLength = result.getPathLength() + 1;
/* 315 */               PathFindResult mixPath = new PathFindResult(mixPathLength);
/*     */               
/* 317 */               mixPath.setStep(0, this.m_currentPath.getPathStep(this.m_currentPathStepIndex));
/*     */               
/* 319 */               for (int i = 0; i < result.getPathLength(); i++) {
/* 320 */                 mixPath.setStep(i + 1, result.getPathStep(i));
/*     */               }
/*     */               
/* 323 */               this.m_currentPath = mixPath;
/* 324 */               this.m_currentPathStepIndex = 0;
/*     */               return;
/*     */             } 
/* 327 */             pathFinder.release();
/*     */           } 
/*     */         } else {
/* 330 */           if (this.m_currentPath.getPathLength() > this.m_currentPathStepIndex + 1) {
/*     */             
/* 332 */             int[] currentNode = this.m_currentPath.getPathStep(this.m_currentPathStepIndex);
/* 333 */             int[] newFirstNode = node.getPathStep(0);
/*     */ 
/*     */             
/* 336 */             if (currentNode[0] == newFirstNode[0] && currentNode[1] == newFirstNode[1]) {
/*     */               
/* 338 */               int j = node.getPathLength() + 1;
/* 339 */               PathFindResult pathFindResult = new PathFindResult(j);
/*     */               
/* 341 */               pathFindResult.setStep(0, this.m_currentPath.getPathStep(this.m_currentPathStepIndex));
/* 342 */               pathFindResult.setStep(1, this.m_currentPath.getPathStep(this.m_currentPathStepIndex + 1));
/*     */               
/* 344 */               for (int k = 1; k < node.getPathLength(); k++) {
/* 345 */                 pathFindResult.setStep(k + 1, node.getPathStep(k));
/*     */               }
/* 347 */               this.m_currentPath = pathFindResult;
/* 348 */               this.m_currentPathStepIndex = 0;
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 353 */             int mixPathLength = node.getPathLength() + 1;
/* 354 */             PathFindResult mixPath = new PathFindResult(mixPathLength);
/*     */             
/* 356 */             mixPath.setStep(0, this.m_currentPath.getPathStep(this.m_currentPathStepIndex));
/*     */             
/* 358 */             for (int i = 0; i < node.getPathLength(); i++) {
/* 359 */               mixPath.setStep(i + 1, node.getPathStep(i));
/*     */             }
/*     */             
/* 362 */             this.m_currentPath = mixPath;
/* 363 */             this.m_currentPathStepIndex = 0;
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 369 */           this.m_timeRest = 0L;
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/* 374 */     this.m_currentPath = node;
/* 375 */     this.m_currentPathStepIndex = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDestinationWorldX() {
/* 384 */     if (this.m_currentPath != null && this.m_currentPath.getPathLength() > this.m_currentPathStepIndex + 1) {
/* 385 */       return this.m_currentPath.getPathStep(this.m_currentPathStepIndex + 1)[0];
/*     */     }
/* 387 */     return (int)this.m_worldX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDestinationWorldY() {
/* 396 */     if (this.m_currentPath != null && this.m_currentPath.getPathLength() > this.m_currentPathStepIndex + 1) {
/* 397 */       return this.m_currentPath.getPathStep(this.m_currentPathStepIndex + 1)[1];
/*     */     }
/* 399 */     return (int)this.m_worldY;
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
/*     */   public void setWorldPosition(double worldX, double worldY, double altitude) {
/* 411 */     if (getMovementStyle().createPathOnSetPosition()) {
/* 412 */       PathFindResult path = new PathFindResult(2);
/* 413 */       path.setStep(0, (int)this.m_worldX, (int)this.m_worldY, (short)(int)this.m_altitude);
/* 414 */       path.setStep(1, (int)worldX, (int)worldY, (short)(int)altitude);
/*     */       
/* 416 */       setPath(path, true);
/*     */       
/*     */       return;
/*     */     } 
/* 420 */     super.setWorldPosition(worldX, worldY, altitude);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\mobile\PathMobile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */