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

public interface IListenerManager {
  void setOnActivation(IActivationListener paramIActivationListener);
  
  void setOnFocus(IFocusListener paramIFocusListener);
  
  void setOnKeyPress(IKeyPressedListener paramIKeyPressedListener);
  
  void setOnKeyRelease(IKeyReleasedListener paramIKeyReleasedListener);
  
  void setOnMouseDrag(IMouseDraggedListener paramIMouseDraggedListener);
  
  void setOnMouseEnter(IMouseEnteredListener paramIMouseEnteredListener);
  
  void setOnMouseExit(IMouseExitedListener paramIMouseExitedListener);
  
  void setOnMouseMove(IMouseMovedListener paramIMouseMovedListener);
  
  void setOnMousePress(IMousePressedListener paramIMousePressedListener);
  
  void setOnDoubleClick(IMouseDoubleClickListener paramIMouseDoubleClickListener);
  
  void setOnClick(IMouseClickListener paramIMouseClickListener);
  
  void setOnMouseRelease(IMouseReleasedListener paramIMouseReleasedListener);
  
  void setOnMouseWheel(IMouseWheelListener paramIMouseWheelListener);
  
  Vector<IActivationListener> getOnActivation();
  
  Vector<IFocusListener> getOnFocus();
  
  Vector<IKeyPressedListener> getOnKeyPress();
  
  Vector<IKeyReleasedListener> getOnKeyRelease();
  
  Vector<IMouseDraggedListener> getOnMouseDrag();
  
  Vector<IMouseEnteredListener> getOnMouseEnter();
  
  Vector<IMouseExitedListener> getOnMouseExit();
  
  Vector<IMouseMovedListener> getOnMouseMove();
  
  Vector<IMousePressedListener> getOnMousePress();
  
  Vector<IMouseDoubleClickListener> getOnDoubleClick();
  
  Vector<IMouseClickListener> getOnClick();
  
  Vector<IMouseReleasedListener> getOnMouseRelease();
  
  Vector<IMouseWheelListener> getOnMouseWheel();
  
  boolean hasDoubleClickListener();
  
  void simpleClick(MouseReleasedEvent paramMouseReleasedEvent);
  
  void doubleClick(MouseReleasedEvent paramMouseReleasedEvent);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IListenerManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */