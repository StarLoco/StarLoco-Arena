package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.IMouseClickListener;
import com.ankamagames.xulor.util.Alignment;
import com.ankamagames.xulor.util.Pixmap;

public abstract interface IPopupMenu
  extends IComponent
{
  public abstract void addLabel(String paramString, Pixmap paramPixmap);
  
  public abstract void addButton(String paramString, Pixmap paramPixmap, IMouseClickListener paramIMouseClickListener, boolean paramBoolean);
  
  public abstract void addSeparator();
  
  public abstract void show(int paramInt1, int paramInt2);
  
  public abstract void show();
  
  public abstract void setHotSpotPosition(Alignment paramAlignment);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IPopupMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */