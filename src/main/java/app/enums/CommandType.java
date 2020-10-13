package app.enums;

public enum CommandType {

    ADVANCE("a"), TURN_LEFT("l"), TURN_RIGHT("r"), QUIT("q");

    public final String value;

    CommandType(String value) {
        this.value = value;
    }

    public boolean equals(String value) {
        return this.value.equals(value);
    }


}
	

