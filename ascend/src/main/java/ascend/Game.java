package ascend;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	
	static Random rng=new Random();
	final int height = 32, width=64, roomAmount=10, maxRoomHeight=8, maxRoomWidth=8, minRoomHeight=4, minRoomWidth=4;
	Tile[][] field = new Tile[height][width];
	ArrayList<Room> rooms = new ArrayList<Room>(roomAmount);
	ArrayList<Tile> corridorTiles = new ArrayList<Tile>();
	
	
	//MAIN!!!!!
	public static void main(String[] args) {
		Game game = new Game();
		
		game.createField(); //adds tiles to field
		game.createMultipleRooms();
		game.setTileAttributes();
		printField(game);
	}
	
	//this method creates a field of tiles with Height and Width
	public void createField(){
		for(int y=0; y<height; y++){
			Tile[] row = new Tile[width];
			for(int x=0; x<width; x++){
				row[x]= new Tile(x, y);
			}
			field[y] = row;
		}
	}
	//this method prints the Tile char 'attribute' for each Tile in the field.
	static public void printField(Game game){
		for(int y=0; y<game.height; y++){
			for(int x=0; x<game.width; x++){
				System.out.print(game.field[y][x].attribute);
			}
			System.out.println();
		}
	}
	//this method creates one Room object within the boundaries of the field.
	public Room createRoom(){
		boolean isBuilding = true;
		int topLeftX, topLeftY, roomWidth, roomHeight; //x and y are the coordinates of the top left corner of the Room to be created
		loop: do{	
			topLeftX = rng.nextInt(width - 2) + 1;
			topLeftY =	rng.nextInt(height - 2) + 1;
			//ensure generated room size is between established mins and maxes
			roomWidth = rng.nextInt(maxRoomWidth - minRoomWidth) + minRoomWidth;
			roomHeight = rng.nextInt(maxRoomHeight - minRoomHeight) + minRoomWidth; 
			//test if new room would be out of bounds in field, or manifest at one of the outer edges
			if((topLeftX + roomWidth > width - 1 || topLeftY + roomHeight > height - 1)){
				continue loop;
			}
			isBuilding = false;
		} while(isBuilding);
		
		Room room = new Room(topLeftX, topLeftX+roomWidth, topLeftY, topLeftY+roomHeight);
		
		//add tiles from field to room tile arrays
		for(int a=topLeftY; a<(topLeftY + roomHeight); a++){ //a == row a in field
			for(int b=topLeftX; b< (topLeftX + roomWidth); b++){ //b == column b in row
				//System.out.print(room.tiles[x + roomWidth][y + roomHeight]);
				room.tiles.add(field[a][b]);
				
			}
		}
		return room;
	}
	
	public void createMultipleRooms(){
		int i = 0;
		while(i < roomAmount){
			boolean intersectionFound = false;
			Room newRoom = createRoom();
			if(rooms.size() == 0){
				rooms.add(newRoom);
				i++;
			} else {
				for(Room room : rooms){
					if(newRoom.intersects(room)){
						intersectionFound = true;
					}
				}
				if(!intersectionFound){
					rooms.add(newRoom);
					setCorridors(rooms.get(i-1), newRoom);
					i++;
					//draw corridor to rooms.get(i - 1)
					
				}
			}
		}
	}
	
	public void setCorridors(Room room1, Room room2){
		Tile center1 = room1.getRandomTile(), center2 =room2.getRandomTile();
		setHCorridor(center1.x, center2.x, center1.y);
		setVCorridor(center1.y, center2.y, center2.x);
	}
	
	public void setVCorridor(int y1, int y2, int x){
		int max = Math.max(y1,  y2);
		int min = Math.min(y1, y2);
		for(int i=min; i<=max; i++){
			corridorTiles.add(field[i][x]);
		}
	}
	
	public void setHCorridor(int x1, int x2, int y){
		int max = Math.max(x1,  x2);
		int min = Math.min(x1,  x2);
		for(int i=min; i<=max; i++){
			corridorTiles.add(field[y][i]);
		}
	}
	
	public void setTileAttributes(){
		for(Tile corridorTile : corridorTiles){
			corridorTile.attribute = ' ';
		}
		for(Room room : rooms){
			for(Tile roomTile : room.tiles){
				roomTile.attribute = ' ';
			}
		}
	}
}
