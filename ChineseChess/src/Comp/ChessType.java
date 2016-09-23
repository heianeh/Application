package Comp;

public enum ChessType {
	//Seven types of chesses
	ROOKS, KNIGHTS, ELEPHANTS, MANDARINS, KING, CANNONS, PAWNS;
	
	public static ChessType getType(int type) {
		if(type == 1) {
			return ROOKS;
		} else if(type == 2) {
			return KNIGHTS;
		} else if(type == 3) {
			return ELEPHANTS;
		} else if(type == 4) {
			return MANDARINS;
		} else if(type == 5) {
			return KING;
		} else if(type == 6) {
			return CANNONS;
		} else {
			return PAWNS;
		}
	}
}
