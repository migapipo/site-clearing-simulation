package app.enums;

public enum SquareType {

	PLAIN("o"), ROCKY("r"), TREE_REMOVABLE("t"), TREE_PRESERVED("T");

	private String squareType;

	SquareType(String squareType) {
		this.squareType = squareType;
	}

	public String getSquareType() {
		return squareType;
	}
	
}
