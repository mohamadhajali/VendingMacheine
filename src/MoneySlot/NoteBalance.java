package MoneySlot;

public class NoteBalance {
    int numOf20Doller;
    int numOf50Doller;

    public NoteBalance() {
        this.numOf20Doller=0;
        this.numOf50Doller=0;
    }

    public int getNumOf20Doller() {
        return numOf20Doller;
    }

    public void setNumOf20Doller(int numOf20Doller) {
        this.numOf20Doller = numOf20Doller;
    }

    public int getNumOf50Doller() {
        return numOf50Doller;
    }

    public void setNumOf50Doller(int numOf50Doller) {
        this.numOf50Doller = numOf50Doller;
    }

    @Override
    public String toString() {
        return "NoteBalance{" +
                "numOf20Doller=" + numOf20Doller +
                ", numOf50Doller=" + numOf50Doller +
                '}';
    }
}
