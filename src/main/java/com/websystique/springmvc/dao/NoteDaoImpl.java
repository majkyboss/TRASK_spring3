package com.websystique.springmvc.dao;

import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.Note;

@Repository("noteDao")
public class NoteDaoImpl extends AbstractDao implements NoteDao {

	@Override
	public void saveNote(Note note) {
		persist(note);
	}

	@Override
	public void deleteNote(Note note) {
		delete(note);
	}

	@Override
	public Note findById(int id) {
		Note foundNote = (Note) getSession().get(Note.class, id);
		return foundNote;
	}

}
