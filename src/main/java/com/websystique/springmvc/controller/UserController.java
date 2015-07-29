package com.websystique.springmvc.controller;

import java.beans.PropertyEditorSupport;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.websystique.springmvc.model.Note;
import com.websystique.springmvc.model.RegStatus;
import com.websystique.springmvc.model.Registration;
import com.websystique.springmvc.model.Role;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.security.AuthenticationFacade;
import com.websystique.springmvc.security.UserDetailsImpl;
import com.websystique.springmvc.service.BranchService;
import com.websystique.springmvc.service.NoteService;
import com.websystique.springmvc.service.RegStatusService;
import com.websystique.springmvc.service.RegistrationService;
import com.websystique.springmvc.service.RoleService;
import com.websystique.springmvc.service.UserService;

@Controller
@RequestMapping(value = { "/user" })
public class UserController {
	private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";

	private static final String JSP_PAGE_REGISTRATION_DETAIL_FORM = "registration";
	private static final String JSP_PAGE_REGISTRATION_DETAIL = "registrationDetail";
	private static final String JSP_PAGE_REGISTRATIONS_LIST = "registrationsList";
	private static final String JSP_PAGE_NOTE_DETAIL_FORM = "editNote";
	private static final String JSP_PAGE_NOTE_DETAIL = "noteDetail";

	@Autowired
	RegistrationService regsService;

	@Autowired
	NoteService noteService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	UserService userService;

	@Autowired
	RegStatusService statusService;

	@Autowired
	RoleService roleService;
	@Autowired
	BranchService branchService;

	@Autowired
	@Qualifier("authenticationFacade")
	AuthenticationFacade authenticationFacade;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Role.class, "role",
				new PropertyEditorSupport() {
					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						int roleId = Integer.parseInt(text);
						Role role = roleService.findRoleById(roleId);
						setValue(role);
					}
				});
		binder.registerCustomEditor(User.class, "manager",
				new PropertyEditorSupport() {
					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						int userId = Integer.parseInt(text);
						User user = userService.findById(userId);
						setValue(user);
					}
				});

		binder.registerCustomEditor(TreeSet.class, "agents",
				new CustomCollectionEditor(Set.class) {
					@Override
					protected Object convertElement(Object element) {
						if (element instanceof String) {
							int agentId = Integer.parseInt((String) element);
							User user = userService.findById(agentId);
							return user;
						}

						return null;
					}
				});

		binder.registerCustomEditor(RegStatus.class, "regStatus",
				new PropertyEditorSupport() {
					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						int statusId = Integer.parseInt(text);
						RegStatus status = statusService.findById(statusId);
						setValue(status);
					}
				});

		binder.registerCustomEditor(User.class, "registrator",
				new PropertyEditorSupport() {
					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						int userId = Integer.parseInt(text);
						User user = userService.findById(userId);
						setValue(user);
					}
				});
	}

	@RequestMapping(value = { "/create_reg" }, method = RequestMethod.GET)
	public String createRegistration(ModelMap model) {
		Registration newReg = new Registration();
		newReg.setRegDate(LocalDate.now());

		model.addAttribute("registration", newReg);

		UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade
				.getAuthentication().getPrincipal();
		User currentUser = userService.findById(userDetails.getId());
		newReg.setRegistrator(currentUser);

		// static value user status with id 1 - supposed status 'new' has id 1
		newReg.setRegStatus(statusService.findById(1));

		return JSP_PAGE_REGISTRATION_DETAIL_FORM;
	}

	protected void loadStatusesToPageAsMap(ModelMap model) {
		Map<String, RegStatus> statuses = new TreeMap<String, RegStatus>();
		for (RegStatus status : statusService.findAllStatuses()) {
			statuses.put(status.getId() + "", status);
		}
		model.addAttribute("statuses", statuses);
	}

	protected void loadUsersInBranchesToPageAsMap(ModelMap model) {
		Map<String, User> users = new TreeMap<String, User>();

		for (User user : userService.findAllUsersInBranches()) {
			users.put(user.getId() + "", user);
		}
		model.addAttribute("users", users);
	}

	protected void loadAllUsersToPageAsMap(ModelMap model) {
		Map<String, User> users = new TreeMap<String, User>();
		for (User user : userService.findAllUsers()) {
			users.put(user.getId() + "", user);
		}
		model.addAttribute("users", users);
	}

	@RequestMapping(value = { "/create_reg", "/save_reg" }, method = RequestMethod.POST)
	public String saveRegistration(@Valid Registration registration,
			BindingResult result, ModelMap model) {
		if (result.hasErrors())
			return JSP_PAGE_REGISTRATION_DETAIL_FORM;

		registration.setRegistratorBranch(registration.getRegistrator()
				.getCurrentBranch());

//		registration.setRegDate(LocalDate.now());

		regsService.saveRegistration(registration);

		return "redirect:show_regs_list";
	}

	@RequestMapping(value = { "/edit_reg_{regDateString}_{ico}" })
	public String editRegistration(ModelMap model, @PathVariable String ico,
			@PathVariable String regDateString) {

		findAndSetReg(model, ico, regDateString);

		return JSP_PAGE_REGISTRATION_DETAIL_FORM;
	}

	private void findAndSetReg(ModelMap model, String ico, String regDateString) {
		LocalDate regDate = LocalDate.parse(regDateString,
				DateTimeFormat.forPattern(DATE_FORMAT_PATTERN));

		Registration reg = regsService.findByKey(ico, regDate);

		if (reg != null) {
			model.addAttribute("registration", reg);
		}
	}

	@RequestMapping(value = { "/reg_detail_{regDateString}_{ico}" })
	public String showRegistrationDetail(ModelMap model,
			@PathVariable String ico, @PathVariable String regDateString) {

		findAndSetReg(model, ico, regDateString);

		return JSP_PAGE_REGISTRATION_DETAIL;
	}

	@RequestMapping(value = { "/show_regs_list" })
	public String showRegistrations(ModelMap model) {
		List<Registration> regs = getRegistrations();
		if (regs == null) {
			regs = new LinkedList<Registration>();
		}

		model.addAttribute("registrations_list", regs);

		return JSP_PAGE_REGISTRATIONS_LIST;
	}

	/**
	 * gets the all registration from DB
	 * 
	 * @return
	 */
	protected List<Registration> getRegistrations() {
		return regsService.findAllRegistrations();
	}

	@RequestMapping(value = { "/del_reg_{regDateString}_{ico}" }, method = RequestMethod.GET)
	public String deleteRegistration(ModelMap model, @PathVariable String ico,
			@PathVariable String regDateString/* , BindingResult result */) {

		LocalDate regDate = LocalDate.parse(regDateString,
				DateTimeFormat.forPattern(DATE_FORMAT_PATTERN));

		Registration reg = regsService.findByKey(ico, regDate);
		regsService.deleteRegistration(reg);

		return "redirect:show_regs_list";
	}
	
	@RequestMapping(value = {"/add_note_{regDateString}_{regIco}"})
	public String addNote(ModelMap model, @PathVariable String regIco, @PathVariable String regDateString) {
		LocalDate regDate = LocalDate.parse(regDateString,
				DateTimeFormat.forPattern(DATE_FORMAT_PATTERN));
		
		Registration reg = regsService.findByKey(regIco, regDate);
		Note note = new Note();
		note.setCreatedDate(LocalDate.now());
		reg.getNotes().add(note);
		note.setRegistration(reg);
		noteService.saveNote(note);
		regsService.saveRegistration(reg);
		
		model.addAttribute("note", note);
		
		return JSP_PAGE_NOTE_DETAIL_FORM;
	}

	@RequestMapping(value = { "/edit_note_{noteId}" }, method = RequestMethod.GET)
	public String editNote(ModelMap model, @PathVariable int noteId) {
		Note note = noteService.findById(noteId);
		model.addAttribute("note", note);

		return JSP_PAGE_NOTE_DETAIL_FORM;
	}

	@RequestMapping(value = { "/save_note" }, method = RequestMethod.POST)
	public String saveNote(ModelMap model, @Valid Note note) {
		Note oldNote = noteService.findById(note.getId());
		note.setRegistration(oldNote.getRegistration());
		noteService.saveNote(note);
		
		Registration reg = note.getRegistration();
		
		return editRegistration(model, reg.getIco(), reg.getRegDate().toString(DateTimeFormat.forPattern(DATE_FORMAT_PATTERN)));
	}

	@RequestMapping("/del_note_{noteId}")
	public String deleteNote(ModelMap model, @PathVariable int noteId) {
		Note note = noteService.findById(noteId);
		Registration reg = (note == null) ? null : note.getRegistration();
		reg.getNotes().remove(note);
		regsService.saveRegistration(reg);
		noteService.deleteNote(note);

		return editRegistration(model, reg.getIco(), reg.getRegDate().toString(DateTimeFormat.forPattern(DATE_FORMAT_PATTERN)));
	}
}
