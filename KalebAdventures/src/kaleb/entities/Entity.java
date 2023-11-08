package kaleb.entities;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import kaleb.main.Game;
import kaleb.world.Camera;

public class Entity {
	public double x;
	public double y;
	protected int width;
	protected int height;
	protected int rangeWidth;
	protected int rangeHeight;
	public BufferedImage sprite;
	protected double speed;
	public boolean right, left, up, down;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.setX(x);
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	public void DestroySelf(Entity e) {
		Game.waveList.remove(e);
		Game.entities.remove(e);
		
	}
	public void defeat(Entity e) {
		Game.entities.remove(e);
	}
	public static boolean isColiddingAtk(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(((int)e1.getX()*Game.SCALE) - (e1.getRangeWidth()*Game.SCALE) / 2,(int)e1.getY()*Game.SCALE - (e1.getRangeHeight()*Game.SCALE) / 2 + (e1.getHeight()*Game.SCALE)/2, e1.getRangeWidth()*Game.SCALE, e1.getRangeHeight()*Game.SCALE);
		Rectangle e2Mask = new Rectangle(((int)e2.getX()*Game.SCALE) - (e2.getWidth()*Game.SCALE) / 2 ,(int)e2.getY()*Game.SCALE, e2.getWidth()*Game.SCALE, e2.getHeight()*Game.SCALE);
		
		return e1Mask.intersects(e2Mask);
	}
	public static boolean isColidding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle((int)e1.getX() - Camera.x,(int)e1.getY() - Camera.y,e1.getWidth(),e1.getHeight());
		Rectangle e2Mask = new Rectangle((int)e2.getX() - Camera.x,(int)e2.getY() - Camera.y,e2.getWidth() ,e2.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}
	public static boolean isColiddingPlayer(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle((int)e1.getX() *3,(int)e1.getY() *3,e1.getWidth(),e1.getHeight());
		Rectangle e2Mask = new Rectangle((int)e2.getX() *3  - Camera.x,(int)e2.getY()*3 - Camera.y,e2.getWidth()*3,e2.getHeight()*3);
		
		return e1Mask.intersects(e2Mask);
	}
	public static boolean isColidding2(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle((int)e1.getX() * 3 ,(int)e1.getY() * 3,e1.getWidth(),e1.getHeight());
		Rectangle e2Mask = new Rectangle((int)e2.getX(),(int)e2.getY(),e2.getWidth() ,e2.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}public static boolean isColiddingPokeball(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(((int)e1.getX() - 2) *3,((int)e1.getY() - 2) *3,e1.getWidth()*3,e1.getHeight()*3);
		Rectangle e2Mask = new Rectangle((int)e2.getX() *3  - Camera.x,(int)e2.getY()*3 - Camera.y,e2.getWidth()*3,e2.getHeight()*3);
		
		return e1Mask.intersects(e2Mask);
	}public static boolean isColidding3(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(((int)e1.getX()),((int)e1.getY()) ,e1.getWidth(),e1.getHeight());
		Rectangle e2Mask = new Rectangle((int)e2.getX(),(int)e2.getY(),e2.getWidth(),e2.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}
	public double calculateDistance(int x1, int y1, int x2, int y2){
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void tick() {

	}

	public void render(Graphics g) {
		
	}
	public void renderStr(Graphics g) {
		
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getRangeWidth() {
		return rangeWidth;
	}
	public void setRangeWidth(int rangeWidth) {
		this.rangeWidth = rangeWidth;
	}
	public int getRangeHeight() {
		return rangeHeight;
	}
	public void setRangeHeight(int rangeHeight) {
		this.rangeHeight = rangeHeight;
	}
	
	
}
