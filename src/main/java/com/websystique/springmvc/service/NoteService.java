package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Note;

public interface NoteService {

	Note findById(int id);

	void saveNote(Note note);

	void deleteNote(Note note);

}
