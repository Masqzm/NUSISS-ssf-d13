package ssf.day13.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ssf.day13.models.Registration;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    
    // POST /registration
    @PostMapping
    public String postRegistration(
        @RequestBody MultiValueMap<String, String> form, 
        @RequestBody String rawBody,
        Model model) {        

        System.out.printf("form: %s\n", form);
        System.out.printf("rawBody: %s\n", rawBody);

        return "registered";
    }

    // POST /registration/obj
    @PostMapping("/obj")
    public String postRegistration(
        @Valid @ModelAttribute Registration reg, 
        BindingResult bindings, Model model) {      // NOTE: BindingResult must immediately follow @Valid param

        System.out.printf("binding errors: %b\n", bindings.hasErrors());

        // Go back to original page if form has invalid inputs
        if(bindings.hasErrors())
        {
            FieldError err = new FieldError("reg", "name", "This is an error message");
            bindings.addError(err);
            model.addAttribute("reg", reg);
            return "index";
        }

        System.out.printf("registration: %s\n", reg);

        model.addAttribute("email", reg.getEmail());
        model.addAttribute("reg", reg);

        return "registered";
    }
}
