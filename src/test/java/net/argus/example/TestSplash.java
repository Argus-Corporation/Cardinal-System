package net.argus.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;


/**
 * A splash screen to show while the main program is loading. A typical use
 * is:
 * <pre>
 *
 *   public static void main(String[] args) {
 *     Splash s = new Splash(delay1);
 *     new MainProgram();
 *     s.dispose(delay2);
 *   }  
 *
 * </pre>
 * The first line creates a Splash that will appear until another frame
 * hides it (MainProgram), but at least during "delay1" milliseconds.<br>
 * To distroy the Splash you can either call "s.dispose()" or
 * "s.dispose(delay2)", that will actually show the Splash for "delay2"
 * milliseconds and only then hide it.<br>
 * The picture to show must be in a file called "splash.png".
 */ 
 public class TestSplash extends JWindow {

  
  /**
	 * 
	 */
	private static final long serialVersionUID = 672367736617100155L;

/**
   * Creates a Splash that will appear until another frame hides it, but at
   * least during "delay" milliseconds.
   * @param delay the delay in milliseconds
   */
  public TestSplash(int delay) {
    JPanel p = new JPanel();
    p.setLayout(new BorderLayout());
    p.add(new SplashPicture("/logo.png"));
    p.setBorder(BorderFactory.createMatteBorder(1, 50, 1, 50, Color.blue));
    getContentPane().add(p);
    setSize(250, 250);
    setLocationRelativeTo(null);
    setVisible(true);
    try {
      Thread.sleep(delay);
    }
    catch (Exception e) {}
  }
  
  
  /**
   * Shows the Splash during the specified time (in milliseconds) and then
   * hides it.
   * @param delay the delay in milliseconds
   */
  public void dispose(int delay) {
    dispose();
    TestSplash s = new TestSplash(delay);
    s.dispose();
  }

  
  /**
   * This class loads and shows a picture, that can be either in the same 
   * jar file than the program or not. If the picture is smaller than the 
   * available space, it will be centered. If the picture is bigger than
   * the available space, a zoom will be applied in order to fit exactly 
   * the space.
   */ 
  class SplashPicture extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1054565078862113181L;
	Image img;      
  
  
    public SplashPicture(String file) {
      img = new ImageIcon(getClass().getResource(file)).getImage();
      repaint(); 
    }
    
  
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (img == null) return;
      int w = img.getWidth(this);  
      int h = img.getHeight(this);
      boolean zoom = (w > getWidth() || h > getHeight());
      if (zoom) g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
      else g.drawImage(img, (getWidth()-w)/2, (getHeight()-h)/2, this);
    }
  }
  
  public static void main(String[] args) {
	//new TestSplash(1000);
	  
	  JWindow win = new JWindow();
	  win.setSize(1080, 1080);
	  win.setOpacity(0.5f);
	  
	  
	  win.add(new JLabel(new ImageIcon("D:\\Django\\Document 1\\Git\\res\\favicon.png")));
	  win.setVisible(true);
}
}