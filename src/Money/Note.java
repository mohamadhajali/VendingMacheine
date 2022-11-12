package Money;

public class Note extends Money{
    int noteValue;
    public Note(int noteValue,String currency, char category) {
        super(currency, category);
        this.noteValue=noteValue;
    }

    public int getNoteValue() {
        return noteValue;
    }

    public void setNoteValue(int noteValue) {
        this.noteValue = noteValue;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteValue=" + noteValue +
                '}';
    }
}
