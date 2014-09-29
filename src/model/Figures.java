package model;

// Associates the bytes, which are internally used for the representation
// of the different cell states, with human readable ids.
// -> Improves readability of the Model & Controller code
// And also:
//Contains a bitmask used for validating moves and captures of enemy figures.
//There are 16 relevant directions a figure might be moved or might capture:
//- p - b -
//n o a c d
//- m x e -
//l l i g f
//- j - h -
//With these 16 values the movement direction of any type of figure may be
//described.
//Because movement and and capturing is not always identical (see pawn)
//another 16 bit mask is needed for describing the capture capabilities.
//
//Further one more bit is needed to describe if the figure may be moved an 
//unlimited number of steps in this direction or just one step
//
//This implementation uses a 32-bit int for representing the capture and the 
//movement mask and a boolean to define the movement limitation.
//This is combined in the class FigureMask.
public enum Figures {
    PawnBlack(
             -1,
            "00000000100000000000001000100000",
            true,
            "\u265F"),

    RookBlack(
            -2,
            "10001000100010001000100010001000",
            false,
            "\u265C"),
    KnightBlack(
            -3,
            "01010101010101010101010101010101",
            true,
            "\u265E"),

    BishopBlack(
             -4,
            "00100010001000100010001000100010",
            false,
            "\u265D"),

    QueenBlack(
             -5,
            "10101010101010101010101010101010",
            false,
            "\u265B"),

    KingBlack(
             -6,
            "10101010101010101010101010101010",
            true,
            "\u265A"),

    Empty(
             0,
            "00000000000000000000000000000000",
            true,
            ""),

    PawnWhite(
             1,
            "10000000000000000010000000000010",
            true,
            "\u2659"),

    RookWhite(
             2,
            "10001000100010001000100010001000",
            false,
            "\u2656"),

    KnightWhite(
             3,
            "01010101010101010101010101010101",
            true,
            "\u2658"),

    BishopWhite(
             4,
            "00100010001000100010001000100010",
            false,
            "\u2657"),

    QueenWhite(
             5,
            "10101010101010101010101010101010",
            false,
            "\u2655"),

    KingWhite(
             6,
            "10101010101010101010101010101010",
            true,
            "\u2654");


    private final IFigureMask mask;

    Figures(int id, String mask, boolean limited, String unicode) {
        this.mask = new FigureMask(id, mask, limited, unicode);
    }

    public int id() {
        return mask.getId();
    }

    public IFigureMask getMask() {
        return mask;
    }


    // for reverse lookup
    public static Figures lookUpID(int id) {
        for (Figures s : values()) {
            if (s.id() == id) {
                return s;
            }
        }
        return null;
    }

}