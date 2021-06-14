
public class Aliens extends Character{
boolean moveRight;
boolean moveLeft;
boolean isVisible;


 public Aliens(int x, int y, int s) {
	 super(x,y,s);
	 
	 moveLeft = false;
	 moveRight = true;
	 isVisible = true;
 }
}
