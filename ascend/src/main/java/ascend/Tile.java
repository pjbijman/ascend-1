package ascend;
//this is the tile object. It has a x and a y.
public class Tile {
	int x, y;
	char attribute, roomAttribute; //op zich niet nodig: attribute kan worden aangepast
	public Tile(int x, int y){
		this.x=x;
		this.y=y;
		attribute = '#';
		roomAttribute = '.';
	}
}
