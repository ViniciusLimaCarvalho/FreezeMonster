package spriteframework.sprite;


import java.util.LinkedList;

public class BadnessBoxSprite  extends BadSprite {
     LinkedList<BadSprite>  badnesses = new LinkedList<BadSprite>();
     
     void add(BadSprite b) {
    	 badnesses.add(b);
     }
     
     public LinkedList<BadSprite>  getBadnesses() {
    	 return badnesses;
     }
     
}
