/*     */ package org.fenggui;
/*     */ 
/*     */ import org.fenggui.background.FunnyBackground;
/*     */ import org.fenggui.background.GradientBackground;
/*     */ import org.fenggui.background.PixmapBackground;
/*     */ import org.fenggui.background.PlainBackground;
/*     */ import org.fenggui.border.BevelBorder;
/*     */ import org.fenggui.border.PixmapBorder;
/*     */ import org.fenggui.border.PixmapBorder16;
/*     */ import org.fenggui.border.PlainBorder;
/*     */ import org.fenggui.border.TitledBorder;
/*     */ import org.fenggui.composites.Window;
/*     */ import org.fenggui.io.TypeRegister;
/*     */ import org.fenggui.layout.BorderLayout;
/*     */ import org.fenggui.layout.FormLayout;
/*     */ import org.fenggui.layout.GridLayout;
/*     */ import org.fenggui.layout.RowLayout;
/*     */ import org.fenggui.layout.StaticLayout;
/*     */ import org.fenggui.menu.Menu;
/*     */ import org.fenggui.menu.MenuBar;
/*     */ import org.fenggui.menu.MenuItem;
/*     */ import org.fenggui.render.Binding;
/*     */ import org.fenggui.render.Font;
/*     */ import org.fenggui.render.ITexture;
/*     */ import org.fenggui.render.Pixmap;
/*     */ import org.fenggui.switches.SetPixmapSwitch;
/*     */ import org.fenggui.table.ITableModel;
/*     */ import org.fenggui.table.Table;
/*     */ import org.fenggui.tree.Tree;
/*     */ import org.fenggui.util.Color;
/*     */ import org.fenggui.xml.theme.DefaultTheme;
/*     */ import org.fenggui.xml.theme.ITheme;
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
/*     */ public class FengGUI
/*     */ {
/*     */   public static final String VERSION = "Alpha 10";
/*  66 */   private static ITheme theme = (ITheme)new DefaultTheme();
/*     */   
/*  68 */   public static final TypeRegister TYPE_REGISTRY = new TypeRegister();
/*     */ 
/*     */   
/*     */   static {
/*  72 */     TYPE_REGISTRY.register("PixmapBackground", PixmapBackground.class);
/*  73 */     TYPE_REGISTRY.register("FunnyBackground", FunnyBackground.class);
/*  74 */     TYPE_REGISTRY.register("GradientBackground", GradientBackground.class);
/*  75 */     TYPE_REGISTRY.register("PlainBackground", PlainBackground.class);
/*     */     
/*  77 */     TYPE_REGISTRY.register("BevelBorder", BevelBorder.class);
/*  78 */     TYPE_REGISTRY.register("PixmapBorder", PixmapBorder.class);
/*  79 */     TYPE_REGISTRY.register("PixmapBorder16", PixmapBorder16.class);
/*  80 */     TYPE_REGISTRY.register("PlainBorder", PlainBorder.class);
/*  81 */     TYPE_REGISTRY.register("TitledBorder", TitledBorder.class);
/*  82 */     TYPE_REGISTRY.register("Window", Window.class);
/*  83 */     TYPE_REGISTRY.register("Menu", Menu.class);
/*  84 */     TYPE_REGISTRY.register("MenuBar", MenuBar.class);
/*  85 */     TYPE_REGISTRY.register("Button", Button.class);
/*  86 */     TYPE_REGISTRY.register("Canvas", Canvas.class);
/*  87 */     TYPE_REGISTRY.register("CheckBox", CheckBox.class);
/*  88 */     TYPE_REGISTRY.register("ComboBox", ComboBox.class);
/*  89 */     TYPE_REGISTRY.register("Container", Container.class);
/*  90 */     TYPE_REGISTRY.register("Display", Display.class);
/*  91 */     TYPE_REGISTRY.register("Label", Label.class);
/*  92 */     TYPE_REGISTRY.register("List", List.class);
/*  93 */     TYPE_REGISTRY.register("ProgressBar", ProgressBar.class);
/*  94 */     TYPE_REGISTRY.register("RadioButton", RadioButton.class);
/*  95 */     TYPE_REGISTRY.register("ScrollBar", ScrollBar.class);
/*  96 */     TYPE_REGISTRY.register("ScrollContainer", ScrollContainer.class);
/*  97 */     TYPE_REGISTRY.register("Slider", Slider.class);
/*  98 */     TYPE_REGISTRY.register("SplitContainer", SplitContainer.class);
/*  99 */     TYPE_REGISTRY.register("TextEditor", TextEditor.class);
/* 100 */     TYPE_REGISTRY.register("Tree", Tree.class);
/* 101 */     TYPE_REGISTRY.register("Table", Table.class);
/*     */     
/* 103 */     TYPE_REGISTRY.register("Font", Font.class);
/* 104 */     TYPE_REGISTRY.register("Pixmap", Pixmap.class);
/* 105 */     TYPE_REGISTRY.register("Color", Color.class);
/*     */     
/* 107 */     TYPE_REGISTRY.register("PixmapSwitch", SetPixmapSwitch.class);
/*     */     
/* 109 */     TYPE_REGISTRY.register("GridLayout", GridLayout.class);
/* 110 */     TYPE_REGISTRY.register("BorderLayout", BorderLayout.class);
/* 111 */     TYPE_REGISTRY.register("FormLayout", FormLayout.class);
/* 112 */     TYPE_REGISTRY.register("RowLayout", RowLayout.class);
/* 113 */     TYPE_REGISTRY.register("StaticLayout", StaticLayout.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ProgressBar createProgressBar(IContainer parent) {
/* 122 */     ProgressBar btn = new ProgressBar();
/*     */     
/* 124 */     parent.addWidget(btn);
/* 125 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComboBox createComboBox(IContainer parent) {
/* 134 */     ComboBox btn = new ComboBox();
/*     */     
/* 136 */     parent.addWidget(btn);
/* 137 */     return btn;
/*     */   }
/*     */   
/*     */   public static Button createButton(IContainer parent) {
/* 141 */     Button btn = new Button();
/*     */     
/* 143 */     parent.addWidget(btn);
/* 144 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Display createDisplay(Binding binding) {
/* 154 */     Display btn = new Display(binding);
/*     */     
/* 156 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Container createContainer(IContainer parent) {
/* 166 */     Container c = new Container();
/*     */     
/* 168 */     parent.addWidget(c);
/* 169 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Button createButton(IContainer parent, String text) {
/* 180 */     Button btn = new Button(text);
/*     */     
/* 182 */     parent.addWidget(btn);
/* 183 */     return btn;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Button createButton(String text) {
/* 188 */     Button btn = new Button(text);
/*     */     
/* 190 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Button createButton(IContainer parent, Pixmap image) {
/* 201 */     Button btn = createButton(parent);
/* 202 */     btn.setPixmap(image);
/* 203 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RadioButton createRadioButton(IContainer parent, ToggableGroup<?> group) {
/* 214 */     RadioButton btn = new RadioButton(group);
/*     */     
/* 216 */     parent.addWidget(btn);
/* 217 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RadioButton createRadioButton(IContainer parent, String text) {
/* 227 */     RadioButton btn = createRadioButton(parent, (ToggableGroup)null);
/* 228 */     btn.setText(text);
/* 229 */     return btn;
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
/*     */   public static RadioButton createRadioButton(IContainer parent, String text, ToggableGroup group) {
/* 241 */     RadioButton btn = createRadioButton(parent, group);
/* 242 */     btn.setText(text);
/* 243 */     btn.setRadioButtonGroup(group);
/* 244 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CheckBox createCheckBox(IContainer parent) {
/* 253 */     CheckBox btn = new CheckBox();
/*     */     
/* 255 */     parent.addWidget(btn);
/* 256 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CheckBox createCheckBox(IContainer parent, String text) {
/* 266 */     CheckBox btn = createCheckBox(parent);
/* 267 */     btn.setText(text);
/* 268 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Label createLabel(IContainer parent) {
/* 277 */     Label l = new Label();
/*     */     
/* 279 */     parent.addWidget(l);
/* 280 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MultiLineLabel createMultiLineLabel(IContainer parent) {
/* 289 */     MultiLineLabel l = new MultiLineLabel();
/*     */     
/* 291 */     parent.addWidget(l);
/* 292 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ListItem createListItem(List parent) {
/* 303 */     ListItem btn = new ListItem();
/* 304 */     parent.addItem(btn);
/* 305 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MenuBar createMenuBar(IContainer parent) {
/* 314 */     MenuBar menuBar = new MenuBar();
/*     */     
/* 316 */     parent.addWidget((IWidget)menuBar);
/* 317 */     return menuBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Menu createMenu(Display parent, boolean display) {
/* 326 */     Menu menu = new Menu();
/*     */     
/* 328 */     if (display) parent.addWidget((IWidget)menu); 
/* 329 */     return menu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Menu createMenu(Menu parent, String name, boolean display) {
/* 338 */     Menu menu = createMenu(parent.getDisplay(), display);
/* 339 */     parent.registerSubMenu(menu, name);
/* 340 */     return menu;
/*     */   }
/*     */   
/*     */   public static Menu createMenu(MenuBar parent, String name, boolean display) {
/* 344 */     Menu menu = createMenu(parent.getDisplay(), display);
/* 345 */     parent.registerSubMenu(menu, name);
/* 346 */     return menu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MenuItem createMenuItem(Menu parent, String name) {
/* 355 */     MenuItem item = new MenuItem(name);
/* 356 */     parent.addItem(item);
/* 357 */     return item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List createList(IContainer parent) {
/* 368 */     List btn = new List();
/*     */     
/* 370 */     parent.addWidget(btn);
/* 371 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Label createLabel(IContainer parent, String text) {
/* 381 */     Label btn = new Label();
/*     */     
/* 383 */     btn.setText(text);
/* 384 */     parent.addWidget(btn);
/* 385 */     return btn;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Label createLabel(String text) {
/* 390 */     Label btn = new Label(text);
/*     */     
/* 392 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Label createLabel(IContainer parent, ITexture image) {
/* 402 */     Label btn = createLabel(parent);
/* 403 */     btn.setPixmap(new Pixmap(image));
/* 404 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Label createLabel(IContainer parent, Pixmap pixmap) {
/* 414 */     Label btn = createLabel(parent);
/* 415 */     btn.setPixmap(pixmap);
/* 416 */     return btn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Label createLabel(IContainer parent, String text, ITexture image) {
/* 427 */     Label label = createLabel(parent, text);
/* 428 */     label.setPixmap(new Pixmap(image));
/* 429 */     return label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Label createLabel(IContainer parent, String text, Color textColor) {
/* 440 */     Label label = createLabel(parent, text);
/* 441 */     label.getAppearance().setTextColor(textColor);
/* 442 */     return label;
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
/*     */   public static Window createWindow(Display parent, boolean closeBtn, boolean maxBtn, boolean minBtn, boolean autoclose) {
/* 454 */     Window frame = new Window(closeBtn, maxBtn, minBtn, autoclose);
/*     */     
/* 456 */     parent.addWidget((IWidget)frame);
/* 457 */     return frame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Window createFrame(Display parent, String text, boolean autoclose) {
/* 468 */     Window frame = new Window(true, true, true, autoclose);
/*     */     
/* 470 */     frame.setTitle(text);
/* 471 */     parent.addWidget((IWidget)frame);
/* 472 */     return frame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Window createDialog(Display parent) {
/* 482 */     Window frame = new Window(true, false, false, true);
/*     */     
/* 484 */     parent.addWidget((IWidget)frame);
/* 485 */     return frame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Window createDialog(Display parent, String title) {
/* 495 */     Window frame = createDialog(parent);
/* 496 */     frame.setTitle(title);
/* 497 */     return frame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Slider createSlider(IContainer parent, boolean horizontal) {
/* 507 */     Slider s = new Slider(horizontal);
/*     */     
/* 509 */     parent.addWidget(s);
/* 510 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ScrollContainer createScrollContainer(IContainer parent) {
/* 519 */     ScrollContainer c = new ScrollContainer();
/*     */     
/* 521 */     parent.addWidget(c);
/* 522 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextEditor createTextArea(IContainer parent) {
/* 531 */     TextEditor c = new TextEditor();
/*     */     
/* 533 */     parent.addWidget(c);
/* 534 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextEditor createTextArea(IContainer parent, String text) {
/* 544 */     TextEditor c = createTextArea(parent);
/* 545 */     c.setText(text);
/* 546 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Canvas createCanvas(IContainer parent) {
/* 555 */     Canvas w = new Canvas();
/*     */     
/* 557 */     parent.addWidget(w);
/* 558 */     return w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IWidget createWidget(IContainer parent) {
/* 567 */     Widget w = new Widget();
/*     */     
/* 569 */     parent.addWidget(w);
/* 570 */     return w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ScrollBar createScrollBar(IContainer parent, boolean horizontal) {
/* 580 */     ScrollBar c = new ScrollBar(horizontal);
/*     */     
/* 582 */     parent.addWidget(c);
/* 583 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Table createTable(IContainer parent) {
/* 592 */     Table table = new Table();
/*     */     
/* 594 */     parent.addWidget((IWidget)table);
/* 595 */     return table;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Table createTable(IContainer parent, ITableModel model) {
/* 605 */     Table table = createTable(parent);
/* 606 */     table.setModel(model);
/* 607 */     return table;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextEditor createTextField(IContainer parent) {
/* 616 */     TextEditor tf = new TextEditor(false);
/*     */     
/* 618 */     parent.addWidget(tf);
/* 619 */     return tf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextEditor createTextField(IContainer parent, String text) {
/* 629 */     TextEditor td = createTextField(parent);
/* 630 */     td.setText(text);
/* 631 */     return td;
/*     */   }
/*     */ 
/*     */   
/*     */   public static SplitContainer createSplitContainer(IContainer parent, boolean horizontal) {
/* 636 */     SplitContainer sc = new SplitContainer(horizontal);
/*     */     
/* 638 */     parent.addWidget(sc);
/* 639 */     return sc;
/*     */   }
/*     */ 
/*     */   
/*     */   public static SplitContainer createSplitContainer(boolean horizontal) {
/* 644 */     SplitContainer sc = new SplitContainer(horizontal);
/*     */     
/* 646 */     return sc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ViewPort createViewPort(IContainer parent) {
/* 655 */     ViewPort p = new ViewPort();
/*     */     
/* 657 */     parent.addWidget(p);
/* 658 */     return p;
/*     */   }
/*     */ 
/*     */   
/*     */   public static VerticalList createVerticalList() {
/* 663 */     VerticalList v = new VerticalList();
/*     */     
/* 665 */     return v;
/*     */   }
/*     */   
/*     */   public static ITheme getTheme() {
/* 669 */     return theme;
/*     */   }
/*     */   
/*     */   public static void setTheme(ITheme theme) {
/* 673 */     FengGUI.theme = theme;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IWidget setUpAppearance(Widget toBeSetUp) {
/* 684 */     if (theme != null) theme.setUp(toBeSetUp); 
/* 685 */     return toBeSetUp;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\FengGUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */