package pritam.asn2.controllers;

import java.util.*;

import jakarta.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;
import pritam.asn2.models.Student;
import pritam.asn2.models.StudentRepository;

/**
 * This class is a controller for the student database. HTTP requests like GET and POST can be sent here to interact with the database.
 */
@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepo;

    /**
     * This method is called before any other method in this class. It is used to populate the model with a list of all students in the database. This list can then be accessed in the view template using the name "st".
     *
     * This thing is called every time a method in this class is called. Which means, there are some security and performance concerns. But I guess it's fine for this project.
     */
    @ModelAttribute("st")
    public List<Student> populateStudents() {
        return studentRepo.findAll();
    }

    /**
     * A way to grab the current URL, using this to refresh the webpage after doing something.
     */
    @ModelAttribute("currentUrl")
    public String getCurrentUrl(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * Main page, the only page required for this project.
     */
    @GetMapping("/students")
    public String getMainPage(Model model, HttpServletRequest request, HttpSession session) {
        return "/main";
    }

    /**
     * Adds a new student to the system.
     */
    @PostMapping("/students/add")
    public String addStudent(@RequestParam Map<String, String> newStudent, @RequestParam String redirectUrl, HttpServletResponse response) {
        System.out.println("ADD student");
        String name = newStudent.get("name");
        int weight = Integer.parseInt(newStudent.get("weight"));
        int height = Integer.parseInt(newStudent.get("height"));
        String hairColor = newStudent.get("hairColor");
        float gpa = Float.parseFloat(newStudent.get("gpa"));
        studentRepo.save(new Student(name, weight, height, hairColor, gpa));
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "redirect:" + redirectUrl;
    }

    /**
     * Deletes a student from the system.
     *
     * In theory, the correct way to do this is to use a DELETE request. However, HTML forms only support GET and POST requests, and a DELETE request will need to be sent using JavaScript. Therefore, for simplicity, we use a POST request here.
     */
    @PostMapping("/students/delete/{sid}")
    public String deleteStudent(@PathVariable int sid, @RequestParam String redirectUrl) {
        studentRepo.deleteById(sid);
        return "redirect:" + redirectUrl;
    }

    /**
     * Deletes all students from the database.
     *
     * This is a dangerous operation and should not be used in a production environment. But this is just an assignment so it's gonna be helpful for the reviewer to test the application.
     */
    @PostMapping("/students/delete/all")
    public String deleteAllStudents(@RequestParam String redirectUrl) {
        studentRepo.deleteAll();
        return "redirect:" + redirectUrl;
    }



    /**
     * After receiving the sid, this method will find the student with that sid from the database.
     *
     * It will then change the student's attributes to the new values provided in the form. Finally, it will save the student back to the database.
     */
    @PostMapping("/students/edit/{sid}")
    public String editStudent(@PathVariable int sid, @RequestParam Map<String, String> newStudent, @RequestParam String redirectUrl) {
        Student student = studentRepo.findById(sid).get();
        student.setName(newStudent.get("name"));
        student.setWeight(Integer.parseInt(newStudent.get("weight")));
        student.setHeight(Integer.parseInt(newStudent.get("height")));
        student.setHairColor(newStudent.get("hairColor"));
        student.setGpa(Float.parseFloat(newStudent.get("gpa")));
        studentRepo.save(student);
        return "redirect:" + redirectUrl;
    }



    // -- Redirects for QoL --
    @GetMapping("/")
    public RedirectView redirectNull() {
        return new RedirectView("/students");
    }

    @GetMapping("/main")
    public RedirectView redirectMain() {
        return new RedirectView("/students");
    }

    @GetMapping("/students/")
    public RedirectView redirectStudents() {
        return new RedirectView("/students");
    }

    @GetMapping("/students/main")
    public RedirectView redirectStudentsMain() {
        return new RedirectView("/students");
    }
}
