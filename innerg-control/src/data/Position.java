package data;

/**
 * Cette classe contient des informations sur une position particulière
 * @author Nicolas
 *
 */
public class Position extends Data{
	
	private int id;
	private String name;
	
	public Position(float aX, float aY, float aZ, float oX, float oY, float oZ,
			int id, String name) {
		super(aX, aY, aZ, oX, oY, oZ);
		this.id = id;
		this.name = name;
	}
	
	
}
