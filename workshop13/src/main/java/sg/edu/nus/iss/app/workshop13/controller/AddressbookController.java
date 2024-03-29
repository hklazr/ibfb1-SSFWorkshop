package sg.edu.nus.iss.app.workshop13.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import sg.edu.nus.iss.app.workshop13.models.Contact;
import sg.edu.nus.iss.app.workshop13.util.Contacts;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping(path="/addressbook")
public class AddressbookController {
    @Autowired 
    Contacts ctcz;

    @Autowired
    ApplicationArguments appArgs;

    private static final String DEFAULT_DATADIR="/Users/kennethphang/Projects/ibfbatch1/workshop13/data";

    @GetMapping
    public String showAddrBookForm(Model model){
        model.addAttribute("contact", new Contact());
        return "addressbook";
    }

    @GetMapping(path="{contactId}")
    public String retrieveContactBy(@PathVariable String contactId, Model model){
        ctcz.getContactById(model, contactId, appArgs, DEFAULT_DATADIR);
        return "result";
    }

    @PostMapping
    public String saveContact(@Valid Contact contact, 
                BindingResult bindResult, Model model, HttpServletResponse response) throws IOException{
        if(bindResult.hasErrors())
            return "addressbook";

        if(!checkAgeInBetween(contact.getDateOfBirth())){
            ObjectError err = new ObjectError("dateOfBirth"
                        , "Age must be between 10 to 100 years old");
            bindResult.addError(err);
            return "addressbook";
        }
        ctcz.saveContact(contact, model, appArgs, DEFAULT_DATADIR);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "result";
    }

    private boolean checkAgeInBetween(LocalDate dob){
        int calculatedAge = 0;
        if(null != dob){
            calculatedAge = Period.between(dob, LocalDate.now()).getYears();
        }

        if(calculatedAge >= 10 && calculatedAge <=100){
            return true;
        }
        return false;
    }
    
}
