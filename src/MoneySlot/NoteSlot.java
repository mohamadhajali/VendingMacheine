package MoneySlot;

import Money.Note;

public class NoteSlot {
    private NoteBalance noteBalance;
    public NoteSlot(){
        this.noteBalance = new NoteBalance();
    }

    public NoteBalance getNoteBalance() {
        return noteBalance;
    }

    public void setNoteBalance(NoteBalance noteBalance) {
        this.noteBalance = noteBalance;
    }
    public void UpdateNote(Note note){
        if(note.getCategory() == '$'){
            if(note.getNoteValue()==20){
                this.noteBalance.setNumOf20Doller(this.getNoteBalance().getNumOf20Doller()+1);
            }else if(note.getNoteValue()==50){
                this.noteBalance.setNumOf50Doller(this.getNoteBalance().getNumOf50Doller()+1);
            }else{
                System.out.println("Just 20 or 50 Doller only");
            }
        }else{
            System.out.println("Just Doller");
        }
    }
}
