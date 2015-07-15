package com.websystique.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.NoteDao;
import com.websystique.springmvc.model.Note;

@Service("noteService")
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
	@Qualifier("noteDao")
	private NoteDao dao;

	@Override
	public Note findById(int id) {
		Note foundNote = dao.findById(id);
		return foundNote;
	}

	@Override
	public void saveNote(Note note) {
		dao.saveNote(note);
	}

	@Override
	public void deleteNote(Note note) {
		dao.deleteNote(note);
	}

}
