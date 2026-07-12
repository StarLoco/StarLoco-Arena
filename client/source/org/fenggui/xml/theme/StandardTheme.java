/*    */ package org.fenggui.xml.theme;
/*    */ 
/*    */ import org.fenggui.Button;
/*    */ import org.fenggui.CheckBox;
/*    */ import org.fenggui.ComboBox;
/*    */ import org.fenggui.Container;
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.Label;
/*    */ import org.fenggui.List;
/*    */ import org.fenggui.ProgressBar;
/*    */ import org.fenggui.RadioButton;
/*    */ import org.fenggui.ScrollBar;
/*    */ import org.fenggui.ScrollContainer;
/*    */ import org.fenggui.Slider;
/*    */ import org.fenggui.SplitContainer;
/*    */ import org.fenggui.TabItemLabel;
/*    */ import org.fenggui.TextEditor;
/*    */ import org.fenggui.VerticalList;
/*    */ import org.fenggui.composites.Window;
/*    */ import org.fenggui.console.Console;
/*    */ import org.fenggui.menu.Menu;
/*    */ import org.fenggui.menu.MenuBar;
/*    */ import org.fenggui.table.Table;
/*    */ import org.fenggui.tree.Tree;
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
/*    */ public abstract class StandardTheme
/*    */   implements ITheme
/*    */ {
/*    */   public void setUp(IWidget widget) {
/* 57 */     if (widget instanceof Button) { setUp((Button)widget); }
/* 58 */     else if (widget instanceof Label) { setUp((Label)widget); }
/* 59 */     else if (widget instanceof ProgressBar) { setUp((ProgressBar)widget); }
/* 60 */     else if (widget instanceof ScrollBar) { setUp((ScrollBar)widget); }
/* 61 */     else if (widget instanceof Tree) { setUp((Tree)widget); }
/* 62 */     else if (widget instanceof Slider) { setUp((Slider)widget); }
/* 63 */     else if (widget instanceof MenuBar) { setUp((MenuBar)widget); }
/* 64 */     else if (widget instanceof Menu) { setUp((Menu)widget); }
/* 65 */     else if (widget instanceof Table) { setUp((Table)widget); }
/* 66 */     else if (widget instanceof TextEditor) { setUp((TextEditor)widget); }
/* 67 */     else if (widget instanceof Window) { setUp((Window)widget); }
/* 68 */     else if (widget instanceof List) { setUp((List)widget); }
/* 69 */     else if (widget instanceof ComboBox) { setUp((ComboBox)widget); }
/* 70 */     else if (widget instanceof CheckBox) { setUp((CheckBox)widget); }
/* 71 */     else if (widget instanceof SplitContainer) { setUp((SplitContainer)widget); }
/* 72 */     else if (widget instanceof VerticalList) { setUp((VerticalList)widget); }
/* 73 */     else if (widget instanceof RadioButton) { setUp((RadioButton)widget); }
/* 74 */     else if (widget instanceof TabItemLabel) { setUp((TabItemLabel)widget); }
/* 75 */     else if (widget instanceof Console) { setUp((Console)widget); }
/* 76 */     else { setUpUnknown(widget); }
/*    */   
/*    */   }
/*    */   
/*    */   public abstract void setUp(Button paramButton);
/*    */   
/*    */   public abstract void setUp(CheckBox paramCheckBox);
/*    */   
/*    */   public abstract void setUp(RadioButton paramRadioButton);
/*    */   
/*    */   public abstract void setUp(TextEditor paramTextEditor);
/*    */   
/*    */   public abstract void setUp(Tree paramTree);
/*    */   
/*    */   public abstract void setUp(Table paramTable);
/*    */   
/*    */   public abstract void setUp(ComboBox paramComboBox);
/*    */   
/*    */   public abstract void setUp(ScrollBar paramScrollBar);
/*    */   
/*    */   public abstract void setUp(Label paramLabel);
/*    */   
/*    */   public abstract void setUp(Window paramWindow);
/*    */   
/*    */   public abstract void setUp(Slider paramSlider);
/*    */   
/*    */   public abstract void setUp(ScrollContainer paramScrollContainer);
/*    */   
/*    */   public abstract void setUp(SplitContainer paramSplitContainer);
/*    */   
/*    */   public abstract void setUp(ProgressBar paramProgressBar);
/*    */   
/*    */   public abstract void setUp(Container paramContainer);
/*    */   
/*    */   public abstract void setUp(Menu paramMenu);
/*    */   
/*    */   public abstract void setUp(MenuBar paramMenuBar);
/*    */   
/*    */   public abstract void setUp(List paramList);
/*    */   
/*    */   public abstract void setUp(VerticalList paramVerticalList);
/*    */   
/*    */   public abstract void setUp(TabItemLabel paramTabItemLabel);
/*    */   
/*    */   public abstract void setUp(Console paramConsole);
/*    */   
/*    */   public abstract void setUpUnknown(IWidget paramIWidget);
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\xml\theme\StandardTheme.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */