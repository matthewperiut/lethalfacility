package com.matthewperiut.lethalfacility.gen.roomcontrol;

public enum RoomEnum {
    NULL_ROOM("null_room"),
    STARTING_ROOM("starting_room"),
    RAIL_JUMP_ROOM("rail_jump_room"),
    EMPTY_ROOM("empty_room"),
    CORRIDOR("corridor");

    final String stringValue;

    RoomEnum() {
        this.stringValue = "null_room";
    }

    RoomEnum(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
