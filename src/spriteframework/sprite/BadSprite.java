package spriteframework.sprite;

import java.util.LinkedList;

public abstract class BadSprite extends Sprite {
	public LinkedList<BadSprite>  getBadnesses() {
		return null;
	}
	public boolean isDestroyed() {
		return false;
	}
	public void act () {
		//
	}
}
