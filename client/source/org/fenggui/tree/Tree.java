/*     */ package org.fenggui.tree;
/*     */ 
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.ScrollContainer;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.ToggableGroup;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.render.Pixmap;
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
/*     */ public class Tree<E>
/*     */   extends StandardWidget
/*     */ {
/*  40 */   private TreeAppearance appearance = null;
/*     */ 
/*     */ 
/*     */   
/*  44 */   private ITreeModel<E> model = null;
/*     */ 
/*     */ 
/*     */   
/*  48 */   private Record<E> root = null;
/*     */   
/*  50 */   private ToggableGroup<E> toggableWidgetGroup = new ToggableGroup(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMinSize() {
/*  58 */     setMinSize(getAppearance().getMinSizeHint());
/*     */     
/*  60 */     if (getParent() != null && getParent() instanceof ScrollContainer)
/*     */     
/*  62 */     { ScrollContainer parent = (ScrollContainer)getParent();
/*  63 */       parent.layout(); }
/*     */     
/*  65 */     else if (getParent() != null) { getParent().updateMinSize(); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tree() {
/*  74 */     this((ITreeModel<E>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tree(ITreeModel<E> model) {
/*  85 */     if (model != null) setModel(model);
/*     */     
/*  87 */     this.appearance = new TreeAppearance<E>(this);
/*     */     
/*  89 */     setupTheme(Tree.class);
/*  90 */     updateMinSize();
/*     */     
/*  92 */     if (getAppearance().getPlusIcon() == null || getAppearance().getMinusIcon() == null)
/*     */     {
/*  94 */       throw new IllegalArgumentException(
/*  95 */           "plusIcon == null || minusIcon == null! Make sure you load the icons in your theme!");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITreeModel<E> getModel() {
/* 107 */     return this.model;
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
/*     */   public void setModel(ITreeModel<E> model) {
/* 119 */     this.model = model;
/* 120 */     this.root = new Record<E>(model, model.getRoot());
/* 121 */     this.root.setExpandable((model.getNumberOfChildren(this.root.getNode()) > 0));
/* 122 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MousePressedEvent mp) {
/* 129 */     if (getModel() == null)
/*     */       return; 
/* 131 */     Pixmap minusIcon = getAppearance().getMinusIcon();
/*     */     
/* 133 */     int row = (getAppearance().getContentHeight() - mp.getLocalY((IWidget)this)) / getAppearance().getFont().getHeight();
/* 134 */     int x = mp.getLocalX((IWidget)this);
/* 135 */     Record<E> r = findRecord(this.root, row);
/*     */     
/* 137 */     if (r != null)
/*     */     {
/*     */       
/* 140 */       if (x > r.getOffset() && x < r.getOffset() + minusIcon.getWidth()) {
/*     */         
/* 142 */         if (r.getNumberOfChildren() == 0) {
/*     */           
/* 144 */           int n = this.model.getNumberOfChildren(r.getNode());
/* 145 */           for (int i = 0; i < n; i++)
/*     */           {
/* 147 */             Record<E> newRec = new Record<E>(this.model, this.model.getNode(r.getNode(), i));
/* 148 */             newRec.setExpandable((this.model.getNumberOfChildren(newRec.getNode()) > 0));
/* 149 */             newRec.setOffset(r.getOffset() + 15);
/* 150 */             r.addChild(newRec);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 155 */           r.removeAllChildren();
/*     */         } 
/* 157 */         updateMinSize();
/*     */       }
/* 159 */       else if (x > r.getOffset() + minusIcon.getWidth() + 15 && 
/* 160 */         x < r.getOffset() + minusIcon.getWidth() + 15 + getAppearance().getFont().getWidth(this.model.getText(r.getNode()))) {
/*     */         
/* 162 */         this.toggableWidgetGroup.setSelected(r, true);
/* 163 */         r.setSelected(true);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Record<E> findRecord(Record<E> node, int row) {
/* 172 */     if (node.row == row) return node; 
/* 173 */     for (Record<E> r : node.getChildren()) {
/*     */       
/* 175 */       Record<E> p = findRecord(r, row);
/* 176 */       if (p != null) return p; 
/*     */     } 
/* 178 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToggableGroup<E> getToggableWidgetGroup() {
/* 186 */     return this.toggableWidgetGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeAppearance getAppearance() {
/* 192 */     return this.appearance;
/*     */   }
/*     */ 
/*     */   
/*     */   public Record<E> getRoot() {
/* 197 */     return this.root;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\tree\Tree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */