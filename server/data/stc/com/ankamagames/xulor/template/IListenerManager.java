package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.IActivationListener;
import com.ankamagames.xulor.event.IFocusListener;
import com.ankamagames.xulor.event.IKeyPressedListener;
import com.ankamagames.xulor.event.IKeyReleasedListener;
import com.ankamagames.xulor.event.IMouseClickListener;
import com.ankamagames.xulor.event.IMouseDoubleClickListener;
import com.ankamagames.xulor.event.IMouseDraggedListener;
import com.ankamagames.xulor.event.IMouseEnteredListener;
import com.ankamagames.xulor.event.IMouseExitedListener;
import com.ankamagames.xulor.event.IMouseMovedListener;
import com.ankamagames.xulor.event.IMousePressedListener;
import com.ankamagames.xulor.event.IMouseReleasedListener;
import com.ankamagames.xulor.event.IMouseWheelListener;
import com.ankamagames.xulor.event.MouseReleasedEvent;
import java.util.Vector;

public abstract interface IListenerManager
{
  public abstract void setOnActivation(IActivationListener paramIActivationListener);
  
  public abstract void setOnFocus(IFocusListener paramIFocusListener);
  
  public abstract void setOnKeyPress(IKeyPressedListener paramIKeyPressedListener);
  
  public abstract void setOnKeyRelease(IKeyReleasedListener paramIKeyReleasedListener);
  
  public abstract void setOnMouseDrag(IMouseDraggedListener paramIMouseDraggedListener);
  
  public abstract void setOnMouseEnter(IMouseEnteredListener paramIMouseEnteredListener);
  
  public abstract void setOnMouseExit(IMouseExitedListener paramIMouseExitedListener);
  
  public abstract void setOnMouseMove(IMouseMovedListener paramIMouseMovedListener);
  
  public abstract void setOnMousePress(IMousePressedListener paramIMousePressedListener);
  
  public abstract void setOnDoubleClick(IMouseDoubleClickListener paramIMouseDoubleClickListener);
  
  public abstract void setOnClick(IMouseClickListener paramIMouseClickListener);
  
  public abstract void setOnMouseRelease(IMouseReleasedListener paramIMouseReleasedListener);
  
  public abstract void setOnMouseWheel(IMouseWheelListener paramIMouseWheelListener);
  
  public abstract Vector<IActivationListener> getOnActivation();
  
  public abstract Vector<IFocusListener> getOnFocus();
  
  public abstract Vector<IKeyPressedListener> getOnKeyPress();
  
  public abstract Vector<IKeyReleasedListener> getOnKeyRelease();
  
  public abstract Vector<IMouseDraggedListener> getOnMouseDrag();
  
  public abstract Vector<IMouseEnteredListener> getOnMouseEnter();
  
  public abstract Vector<IMouseExitedListener> getOnMouseExit();
  
  public abstract Vector<IMouseMovedListener> getOnMouseMove();
  
  public abstract Vector<IMousePressedListener> getOnMousePress();
  
  public abstract Vector<IMouseDoubleClickListener> getOnDoubleClick();
  
  public abstract Vector<IMouseClickListener> getOnClick();
  
  public abstract Vector<IMouseReleasedListener> getOnMouseRelease();
  
  public abstract Vector<IMouseWheelListener> getOnMouseWheel();
  
  public abstract boolean hasDoubleClickListener();
  
  public abstract void simpleClick(MouseReleasedEvent paramMouseReleasedEvent);
  
  public abstract void doubleClick(MouseReleasedEvent paramMouseReleasedEvent);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IListenerManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */