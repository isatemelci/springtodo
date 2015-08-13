package tr.lkd.lyk2015.springtodo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tr.lkd.lyk2015.springtodo.dal.TodoDao;
import tr.lkd.lyk2015.springtodo.model.Todo;
import tr.lkd.lyk2015.springtodo.service.TodoService;

@Controller
@RequestMapping("/todo")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@RequestMapping("")
	public String list(Model model) {
		List<Todo> todos = todoService.getAll();

		model.addAttribute("todoList", todos);
		return "todoList";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute Todo todo) {

		return "createForm";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute Todo todo) {

		todoService.create(todo);

		return "redirect:/todo";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET, params = "id")
	public String edit(@ModelAttribute Todo todo, Model model,
			@RequestParam(value = "message", required = false) String message) {

		Todo todoNew = todoService.getById(todo.getId());

		model.addAttribute("todo", todoNew);
		model.addAttribute("message", message);

		return "updateForm";
	}

	// @RequestMapping(value = "/edit", method = RequestMethod.GET)
	// public String edit(@RequestParam("id") Long id, Model model) {
	//
	// Todo todo = todoService.getById(id);
	//
	// model.addAttribute("todo", todo);
	//
	// return "updateForm";
	// }

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute Todo todo, Model model) {

		todoService.update(todo);
		model.addAttribute("message", "success");
		return "redirect:/todo/update?id=" + todo.getId();
	}

	@RequestMapping(value = "/markasdone", method = RequestMethod.POST)
	public String markAsDone(@RequestParam("id") Long id) {

		todoService.markAsDone(id);

		return "redirect:/todo";
	}

}
