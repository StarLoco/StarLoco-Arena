package org.fenggui;

import org.fenggui.event.FocusEvent;
import org.fenggui.event.KeyPressedEvent;
import org.fenggui.event.KeyReleasedEvent;
import org.fenggui.event.KeyTypedEvent;
import org.fenggui.event.mouse.MouseDraggedEvent;
import org.fenggui.event.mouse.MouseEnteredEvent;
import org.fenggui.event.mouse.MouseExitedEvent;
import org.fenggui.event.mouse.MousePressedEvent;
import org.fenggui.event.mouse.MouseReleasedEvent;
import org.fenggui.event.mouse.MouseWheelEvent;
import org.fenggui.layout.ILayoutData;
import org.fenggui.render.Graphics;
import org.fenggui.util.Dimension;

public interface IWidget {
  ILayoutData getLayoutData();
  
  IBasicContainer getParent();
  
  void mouseEntered(MouseEnteredEvent paramMouseEnteredEvent);
  
  void mouseExited(MouseExitedEvent paramMouseExitedEvent);
  
  void mousePressed(MousePressedEvent paramMousePressedEvent);
  
  void mouseMoved(int paramInt1, int paramInt2);
  
  void mouseDragged(MouseDraggedEvent paramMouseDraggedEvent);
  
  void mouseReleased(MouseReleasedEvent paramMouseReleasedEvent);
  
  void mouseWheel(MouseWheelEvent paramMouseWheelEvent);
  
  void keyPressed(KeyPressedEvent paramKeyPressedEvent);
  
  void keyReleased(KeyReleasedEvent paramKeyReleasedEvent);
  
  void keyTyped(KeyTypedEvent paramKeyTypedEvent);
  
  int getDisplayX();
  
  int getDisplayY();
  
  Display getDisplay();
  
  IWidget getWidget(int paramInt1, int paramInt2);
  
  void updateMinSize();
  
  void focusChanged(FocusEvent paramFocusEvent);
  
  Dimension getSize();
  
  Dimension getMinSize();
  
  int getX();
  
  int getY();
  
  void setX(int paramInt);
  
  void setY(int paramInt);
  
  boolean isTraversable();
  
  void removedFromWidgetTree();
  
  void setParent(IBasicContainer paramIBasicContainer);
  
  void addedToWidgetTree();
  
  void layout();
  
  void setSize(Dimension paramDimension);
  
  boolean isExpandable();
  
  boolean isShrinkable();
  
  void paint(Graphics paramGraphics);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\IWidget.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */