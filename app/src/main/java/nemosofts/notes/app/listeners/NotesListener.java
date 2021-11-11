package nemosofts.notes.app.listeners;

import nemosofts.notes.app.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
