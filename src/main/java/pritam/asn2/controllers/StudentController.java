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


@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepo;

   
    @ModelAttribute("st")
    public List<Student> populateStudents() {
        return studentRepo.findAll();
    }

   
    @ModelAttribute("currentUrl")
    public String getCurrentUrl(HttpServletRequest request) {
        return request.getRequestURI();
    }

   
    @GetMapping("/students")
    public String getMainPage(Model model, HttpServletRequest request, HttpSession session) {
        return "/main";
    }

    
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

    
    @PostMapping("/students/delete/{sid}")
    public String deleteStudent(@PathVariable int sid, @RequestParam String redirectUrl) {
        studentRepo.deleteById(sid);
        return "redirect:" + redirectUrl;
    }

   
    @PostMapping("/students/delete/all")
    public String deleteAllStudents(@RequestParam String redirectUrl) {
        studentRepo.deleteAll();
        return "redirect:" + redirectUrl;
    }



    
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
