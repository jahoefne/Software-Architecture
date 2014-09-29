package model;

// describes the FigureMask Bitmasks
public class FigureMask implements IFigureMask{
    private final int id;
    private final int bitMask;
    private final boolean limited;

    private String unicode = "";

    final int parse(String bitMask) {
        int mask = 0;
        for (int i = 0; i < bitMask.toCharArray().length; i++) {
            if (bitMask.toCharArray()[i] == '1') {
                mask |= (1 << i);
            }
        }
        return mask;
    }

    public FigureMask(int id, String bitMask, boolean limited, String unicode) {
        this.bitMask = this.parse(bitMask);
        this.limited = limited;
        this.id = id;
        this.setUnicode(unicode);
    }

    public int getId() {
        return id;
    }

    public int getBitMask() {
        return bitMask;
    }

    public boolean isLimited() {
        return limited;
    }

    public String getUnicode() {
        return unicode;
    }

    public final void setUnicode(String unicode) {
        this.unicode = unicode;
    }
}
