package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import com.example.demo.Model.Medicine;
import com.example.demo.Model.User;
import com.example.demo.service.MedicineService;
import com.example.demo.service.UserService;

@Controller
public class LoginController implements ErrorController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value= "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }
	
	
	@RequestMapping(value= "/registration" , method =RequestMethod.GET)
	public String registration(Model model) {
		
		User user = new User();
        model.addAttribute("user", user);
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
	    
	    User userExists = userService.findUserByEmail(user.getEmail());
	    if (userExists != null) {
	        bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided");
	    }
	    
	    if (bindingResult.hasErrors()) {
	        return "registration";
	    }

	    userService.saveUser(user);
	    model.addAttribute("user", new User());

	    return "login";
	}

	
    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String home(Model model){
     
    	Authentication medistore = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(medistore.getName());
        model.addAttribute("userName", "Welcome  " + user.getName() + " " + user.getLastName()+",");

        return "home";
    }
    
    
    @Autowired
    private MedicineService medicineService;
    
    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String addMedicine(Model model) {
    	Medicine medicine = new Medicine();
    	model.addAttribute("medicine",medicine);
		return "add";
    	
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String createNewMedicine(@Valid Medicine medicine, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add"; 
        }

        Optional<Medicine> medicineExists = medicineService.findById(medicine.getId());
        if (medicineExists.isPresent()) {
            bindingResult.rejectValue("id", "error.medicine", "Medicine with this ID already exists");
            return "add"; 
        }

        medicineService.saveMedicine(medicine);
        model.addAttribute("medicine", new Medicine());

        return "add";
    }


    @RequestMapping(value="/medicines" ,method = RequestMethod.GET)
    public String listMedicines(Model model) {
        List<Medicine> medicines = medicineService.getAllMedicines();
        model.addAttribute("medicines", medicines);
        return "medicines";
    }
    
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteMedicineConfirmation(@PathVariable("id") int id, Model model) {
        Optional<Medicine> medicine = medicineService.findById(id);

        if (medicine.isPresent()) {
            model.addAttribute("medicine", medicine.get());
            return "delete";
        } else {
            return "redirect:/medicines";
        }
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteMedicine(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Optional<Medicine> medicine = medicineService.findById(id);

        if (medicine.isPresent()) {
            medicineService.deleteMedicine(id);
            redirectAttributes.addFlashAttribute("message", "Medicine deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("message", "Medicine not found");
        }

        return "redirect:/medicines";
    }
    
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Optional<Medicine> medicine = medicineService.findById(id);

        if (medicine.isPresent()) {
            model.addAttribute("medicine", medicine.get());
            return "update";
        } else {
            return "redirect:/medicines";
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateMedicine(@PathVariable("id") int id, 
                                 @ModelAttribute("medicine") @Valid Medicine updatedMedicine,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "update";
        }

        Optional<Medicine> existingMedicine = medicineService.findById(id);

        if (existingMedicine.isPresent()) {
            Medicine medicineToUpdate = existingMedicine.get();
            medicineToUpdate.setMname(updatedMedicine.getMname());
            medicineToUpdate.setDescription(updatedMedicine.getDescription());
            medicineToUpdate.setPrice(updatedMedicine.getPrice());
            medicineToUpdate.setExpdate(updatedMedicine.getExpdate());

            medicineService.saveMedicine(medicineToUpdate);

            redirectAttributes.addFlashAttribute("message", "Medicine updated successfully");
        } else {
            redirectAttributes.addFlashAttribute("message", "Medicine not found");
        }

        return "redirect:/medicines";
    }
    

    @RequestMapping("/error")
	public String handleError() {
		return "error";
		
	}


    
}