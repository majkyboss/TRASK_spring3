package com.websystique.springmvc.controller;

import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.websystique.springmvc.service.BranchService;
import com.websystique.springmvc.service.NoteService;
import com.websystique.springmvc.service.RegStatusService;
import com.websystique.springmvc.service.RegistrationService;
import com.websystique.springmvc.service.RoleService;
import com.websystique.springmvc.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";

	private static final String JSP_PAGE_REGISTRATION_DETAIL_FORM = "registration";
	private static final String JSP_PAGE_REGISTRATION_DETAIL = "registrationDetail";
	private static final String JSP_PAGE_REGISTRATIONS_LIST = "registrationsList";
	private static final String JSP_PAGE_NOTE_DETAIL_FORM = "note";
	private static final String JSP_PAGE_NOTE_DETAIL = "noteDetail";

	// create_reg
	// edit_reg
	// show_regs_list
	// reg_details
	// add_note

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

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Role.class, "role",
				new PropertyEditorSupport() {
					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						// super.setAsText(text);
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
							// String[] ids = ((String) element).split("\\_");

							int agentId = Integer.parseInt((String) element);
							User user = userService.findById(agentId);

							// int branchId = Integer.parseInt(ids[1]);
							// Unit unit = unitService.findById(agentId,
							// branchId);
							// if (unit == null) {
							// unit = new Unit();
							// unit.setUser(user);
							// Branch b = branchService.findById(branchId);
							// unit.setBranch(b);
							// unitService.saveUnit(unit);
							// }
							//
							// return unit;
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
		// binder.registerCustomEditor(Set.class, "agentUnits", new
		// StringArrayPropertyEditor(","){
		//
		// @Override
		// public void setAsText(String text) throws IllegalArgumentException {
		// int agentId = Integer.parseInt(text);
		// Unit unit = unitService.findById(agentId);
		// setValue(unit);
		// }
		//
		// });

	}

	@RequestMapping(value = { "/create_reg" }, method = RequestMethod.GET)
	public String createRegistration(ModelMap model) {
		Registration newReg = new Registration();
		newReg.setRegDate(LocalDate.now());

		// User user = new User();
		// user.setName("testUser");
		// user.setId(5555);
		// Unit u = new Unit();
		// u.setUser(user);
		// newReg.setUnit(u);

		model.addAttribute("registration", newReg);

		// TODO set current logged user to registration
		loadUsersInBranchesToPage(model);

		loadStatusesToPage(model);

		return JSP_PAGE_REGISTRATION_DETAIL_FORM;
	}

	private void loadStatusesToPage(ModelMap model) {
		TreeMap<String, RegStatus> statuses = new TreeMap<String, RegStatus>();
		for (RegStatus status : statusService.findAllStatuses()) {
			statuses.put(status.getId() + "", status);
		}
		model.addAttribute("statuses", statuses);
	}

	private void loadUsersInBranchesToPage(ModelMap model) {
		Map<String, User> users = new TreeMap<String, User>();

		for (User user : userService.findAllUsersInBranches()) {
			users.put(user.getId() + "", user);
		}
		model.addAttribute("users", users);
	}

	private void loadUsersToPage(ModelMap model) {
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

		// load missing data
		// Branch regBranch =
		// branchService.findByAgentId(registration.getRegistrator().getId());
		// registration.setRegistratorBranch(regBranch);

		registration.setRegistratorBranch(registration.getRegistrator()
				.getCurrentBranch());

		registration.setRegDate(LocalDate.now());

		// TODO test if the registration is unique - check the ico and date
		// if (!regsService.isRegistrationUnique(registration.getIco(),
		// registration.getRegDate())) {
		// FieldError error = new FieldError("registration", "key",
		// messageSource.getMessage(
		// "non.unique.registration",
		// new String[] {
		// registration.getIco(),
		// registration.getRegDate().toString(
		// DATE_FORMAT_PATTERN) },
		// Locale.getDefault()));
		// result.addError(error);
		// return JSP_PAGE_REGISTRATION_DETAIL_FORM;
		// }

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

	@RequestMapping(value = { "/create_note" }, method = RequestMethod.POST)
	public String createNote(ModelMap model, Registration registration) {
		Note newNote = new Note();
		newNote.setRegistration(registration);
		model.addAttribute("note", newNote);
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
		noteService.saveNote(note);
		return JSP_PAGE_NOTE_DETAIL;
	}

	@RequestMapping("/del_note_{noteId}")
	public String deleteNote(ModelMap model, @PathVariable int noteId) {
		Note note = noteService.findById(noteId);
		Registration reg = (note == null) ? null : note.getRegistration();
		noteService.deleteNote(note);

		model.addAttribute("registration", reg);

		return JSP_PAGE_REGISTRATION_DETAIL;
	}
}
