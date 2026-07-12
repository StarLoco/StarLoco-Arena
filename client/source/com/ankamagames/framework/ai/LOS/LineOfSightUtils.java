/*     */ package com.ankamagames.framework.ai.LOS;
/*     */ 
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import com.ankamagames.framework.ai.dataProvider.LineOfSightObstacleInformationProvider;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.kernel.core.maths.Vector3i;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class LineOfSightUtils
/*     */ {
/*  25 */   private static LineOfSightCheckParameters m_defaultCheckParameters = new LineOfSightCheckParameters();
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
/*     */   public static boolean check(Object lineOfSightChecker, CellInformationProvider cellInformationProvider, LineOfSightObstacleInformationProvider obstacleInformationProvider, Point3 from, Point3 to, LineOfSightCheckResult result, LineOfSightCheckParameters params) {
/*  51 */     if (!cellInformationProvider.getLineOfSightEndValidity(to.getX(), to.getY(), to.getZ())) {
/*  52 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  56 */     List<CellInput> cellInputs = getCellsInputs(from, to, params);
/*     */     
/*  58 */     List<Vector3i> cells = getCells(cellInputs);
/*     */     
/*  60 */     if (result != null) {
/*  61 */       result.m_cellInputs = cellInputs;
/*  62 */       result.m_checkedCells = cells;
/*     */     } 
/*     */     
/*  65 */     boolean bLineOfSightOk = true;
/*     */ 
/*     */ 
/*     */     
/*  69 */     for (CellInput input : cellInputs) {
/*  70 */       if (!cellInformationProvider.getLineOfSightValidity(input.coords[0], 
/*  71 */           input.coords[1], 
/*  72 */           (short)input.coords[2], 
/*  73 */           input.direction)) {
/*     */         
/*  75 */         if (params.m_returnObstacleCells && result != null) {
/*  76 */           if (result.m_blockingCells == null)
/*  77 */             result.m_blockingCells = new ArrayList<Point3>(); 
/*  78 */           Point3 p = new Point3(input.coords);
/*  79 */           if (!result.m_blockingCells.contains(p)) {
/*  80 */             result.m_blockingCells.add(p);
/*     */           }
/*     */         } 
/*  83 */         if (params.m_stopOnObstacle) {
/*  84 */           if (result != null)
/*  85 */             result.m_lineOfSightOk = false; 
/*  86 */           return false;
/*     */         } 
/*  88 */         bLineOfSightOk = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  94 */     if (obstacleInformationProvider != null) {
/*  95 */       for (int i = 0; i < cells.size(); i++) {
/*     */         
/*  97 */         Vector3i cell = cells.get(i);
/*     */ 
/*     */         
/* 100 */         if (cell.getX() != from.getX() || cell.getY() != from.getY()) {
/*     */ 
/*     */ 
/*     */           
/* 104 */           boolean ignorePotentialTarget = (params.m_ignoreTargetableObstaclesOnLastCell && cell.getX() == to.getX() && cell.getY() == to.getY());
/*     */           
/* 106 */           for (Iterator<? extends LineOfSightObstacle> it = obstacleInformationProvider.getLineOfSightObstacles(); it.hasNext(); ) {
/* 107 */             LineOfSightObstacle obstacle = it.next();
/*     */ 
/*     */             
/* 110 */             if (ignorePotentialTarget && obstacle.isPotentialTarget()) {
/*     */               continue;
/*     */             }
/*     */             
/* 114 */             if (cell.getX() == obstacle.getPosition().getX() && cell.getY() == obstacle.getPosition().getY())
/*     */             {
/* 116 */               if (cell.getZ() >= obstacle.getPosition().getZ() && cell.getZ() <= obstacle.getPosition().getZ() + obstacle.getHeight() && 
/* 117 */                 obstacle.isBlockingLOS(lineOfSightChecker)) {
/*     */ 
/*     */                 
/* 120 */                 if (params.m_returnObstacleCells && result != null) {
/* 121 */                   if (result.m_blockingCells == null)
/* 122 */                     result.m_blockingCells = new ArrayList<Point3>(); 
/* 123 */                   Point3 p = new Point3(cell.getX(), cell.getY(), (short)cell.getZ());
/* 124 */                   if (!result.m_blockingCells.contains(p)) {
/* 125 */                     result.m_blockingCells.add(p);
/*     */                   }
/*     */                 } 
/* 128 */                 if (params.m_stopOnObstacle) {
/* 129 */                   if (result != null)
/* 130 */                     result.m_lineOfSightOk = false; 
/* 131 */                   return false;
/*     */                 } 
/* 133 */                 bLineOfSightOk = false;
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (result != null)
/* 144 */       result.m_lineOfSightOk = bLineOfSightOk; 
/* 145 */     return bLineOfSightOk;
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
/*     */   public static boolean check(Object lineOfSightChecker, CellInformationProvider cellInformationProvider, LineOfSightObstacleInformationProvider obstacleInformationProvider, Point3 from, Point3 to, LineOfSightCheckResult result) {
/* 164 */     return check(lineOfSightChecker, 
/* 165 */         cellInformationProvider, 
/* 166 */         obstacleInformationProvider, 
/* 167 */         from, 
/* 168 */         to, 
/* 169 */         result, 
/* 170 */         m_defaultCheckParameters);
/*     */   }
/*     */   
/*     */   public static LineOfSightCheckParameters getDefaultCheckParameters() {
/* 174 */     return m_defaultCheckParameters;
/*     */   }
/*     */   
/*     */   public static void setDefaultCheckParameters(LineOfSightCheckParameters defaultCheckParameters) {
/* 178 */     if (defaultCheckParameters != null) {
/* 179 */       m_defaultCheckParameters = defaultCheckParameters;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Vector3i> getCells(List<CellInput> cellsInputs) {
/* 190 */     if (cellsInputs == null)
/* 191 */       return new ArrayList<Vector3i>(); 
/* 192 */     List<Vector3i> cells = new ArrayList<Vector3i>(cellsInputs.size());
/* 193 */     Vector3i v = new Vector3i();
/* 194 */     for (CellInput ci : cellsInputs) {
/* 195 */       v.set(ci.coords);
/* 196 */       if (!cells.contains(v)) {
/* 197 */         cells.add(v);
/* 198 */         v = new Vector3i();
/*     */       } 
/*     */     } 
/* 201 */     return cells;
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
/*     */   private static List<CellInput> getCellsInputs(Point3 from, Point3 to, LineOfSightCheckParameters params) {
/* 215 */     List<CellInput> inputs = new ArrayList<CellInput>();
/*     */     
/* 217 */     CellInput origin = new CellInput(from);
/* 218 */     CellInput dest = new CellInput(to);
/*     */     
/* 220 */     double[] deltas = new double[3];
/*     */     int coord;
/* 222 */     for (coord = 0; coord < 3; coord++) {
/* 223 */       deltas[coord] = (dest.coords[coord] - origin.coords[coord]);
/*     */     }
/*     */ 
/*     */     
/* 227 */     for (coord = 0; coord < 3; coord++) {
/*     */       
/* 229 */       double delta = deltas[coord];
/* 230 */       double axeIncrement = ((delta > 0.0D) ? true : -1);
/* 231 */       double increment = (delta > 0.0D) ? 0.5D : -0.5D;
/* 232 */       double firstAxe = origin.coords[coord] + increment;
/* 233 */       double lastAxe = dest.coords[coord] + increment;
/*     */ 
/*     */       
/* 236 */       for (double axe = firstAxe; axe != lastAxe; axe += axeIncrement) {
/* 237 */         double t = (axe - origin.coords[coord]) / delta;
/*     */ 
/*     */ 
/*     */         
/* 241 */         CellInput input = new CellInput();
/*     */         
/* 243 */         switch (coord) {
/*     */           case 0:
/* 245 */             input.direction = (delta > 0.0D) ? Direction8.SOUTH_EAST : Direction8.NORTH_WEST;
/*     */             break;
/*     */           case 1:
/* 248 */             input.direction = (delta > 0.0D) ? Direction8.SOUTH_WEST : Direction8.NORTH_EAST;
/*     */             break;
/*     */           case 2:
/* 251 */             input.direction = (delta > 0.0D) ? Direction8.TOP : Direction8.BOTTOM;
/*     */             break;
/*     */         } 
/*     */         
/* 255 */         for (int c = 0; c < 3; c++) {
/* 256 */           double val = (dest.coords[c] - origin.coords[c]) * t + origin.coords[c];
/*     */ 
/*     */ 
/*     */           
/* 260 */           if (c == coord) {
/* 261 */             input.coords[c] = (int)((delta > 0.0D) ? Math.ceil(val) : Math.floor(val));
/*     */ 
/*     */           
/*     */           }
/* 265 */           else if (val - (int)val == 0.5D) {
/* 266 */             input.coords[c] = (int)((deltas[c] > 0.0D) ? Math.ceil(val) : Math.floor(val));
/*     */           }
/*     */           else {
/*     */             
/* 270 */             input.coords[c] = (int)Math.round(val);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 275 */         inputs.add(input);
/*     */       } 
/*     */     } 
/* 278 */     return inputs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class CellInput
/*     */   {
/* 287 */     protected int[] coords = new int[3];
/*     */     
/*     */     protected Direction8 direction;
/*     */     
/*     */     public CellInput() {
/* 292 */       this.direction = Direction8.NONE;
/*     */     }
/*     */     
/*     */     public CellInput(Point3 pt) {
/* 296 */       this.coords[0] = pt.getX();
/* 297 */       this.coords[1] = pt.getY();
/* 298 */       this.coords[2] = pt.getZ();
/* 299 */       this.direction = Direction8.NONE;
/*     */     }
/*     */     
/*     */     public CellInput(int x, int y, int z, Direction8 direction) {
/* 303 */       this.coords[0] = x;
/* 304 */       this.coords[1] = y;
/* 305 */       this.coords[2] = z;
/* 306 */       this.direction = direction;
/*     */     }
/*     */     
/*     */     public int[] getCoords() {
/* 310 */       return this.coords;
/*     */     }
/*     */     
/*     */     public Direction8 getDirection() {
/* 314 */       return this.direction;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 318 */       return 
/* 319 */         '[' + 
/* 320 */         this.coords[0] + ", " + 
/* 321 */         this.coords[1] + ", " + 
/* 322 */         this.coords[2] + 
/* 323 */         " direction : " + 
/* 324 */         this.direction + 
/* 325 */         ']';
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\LOS\LineOfSightUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */