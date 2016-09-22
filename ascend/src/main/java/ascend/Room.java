package ascend;
import java.util.*;

//this object has four integer that define the boundaries of the room
//zet 2d array om in normaal.
public class Room {
	int x1, x2, y1, y2;
	//Tile[][] tiles = new Tile[y2-y1+1][x2-x2+1];
	ArrayList<Tile> tiles = new ArrayList<Tile>((y2-y1+1)*(x2-x2+1));
	public Room(int x1, int x2, int y1, int y2){
		this.x1=x1;
		this.x2=x2;
		this.y1=y1;
		this.y2=y2;
	}
	
	public boolean intersects(Room room){
		return (this.x1 <= room.x2 && 
				this.x2 >= room.x1 && 
				this.y1 <= room.y2 && 
				this.y2 >= room.y1);
	}
	
	public Tile getRandomTile(){
		Random rng = new Random();
		return tiles.get(rng.nextInt(tiles.size() - 1));
	}
}
