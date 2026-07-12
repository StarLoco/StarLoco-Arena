/*    */ package com.ankamagames.graphics.isometric.lines;
/*    */ 
/*    */ import gnu.trove.TLongObjectHashMap;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.ListIterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Line
/*    */ {
/*    */   private int m_id;
/* 18 */   private TLongObjectHashMap<List<Segment>> m_segments = new TLongObjectHashMap();
/* 19 */   private List<SegmentMesh> m_meshes = new ArrayList<SegmentMesh>();
/*    */   private ListIterator<SegmentMesh> m_meshIterator;
/*    */   
/*    */   public Line(int id) {
/* 23 */     this.m_id = id;
/* 24 */     resetMeshIterator();
/*    */   }
/*    */   
/*    */   public void clear() {
/* 28 */     this.m_segments.clear();
/* 29 */     this.m_meshes.clear();
/* 30 */     resetMeshIterator();
/*    */   }
/*    */   
/*    */   public void addSegment(long handle, Segment segment) {
/* 34 */     if (!this.m_segments.contains(handle)) {
/* 35 */       this.m_segments.put(handle, new ArrayList());
/*    */     }
/* 37 */     ((List<Segment>)this.m_segments.get(handle)).add(segment);
/* 38 */     this.m_meshes.add(new SegmentMesh());
/*    */   }
/*    */   
/*    */   public List<Segment> getSegments(long handle) {
/* 42 */     return (List<Segment>)this.m_segments.get(handle);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 46 */     return this.m_id;
/*    */   }
/*    */   
/*    */   public void resetMeshIterator() {
/* 50 */     this.m_meshIterator = this.m_meshes.listIterator();
/*    */   }
/*    */   
/*    */   public SegmentMesh getNextMesh() {
/* 54 */     return this.m_meshIterator.next();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\lines\Line.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */