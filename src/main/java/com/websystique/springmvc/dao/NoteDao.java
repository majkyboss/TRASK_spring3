package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Note;

public interface NoteDao {

	void saveNote(Note note);

	void deleteNote(Note note);

	Note findById(int id);

}
