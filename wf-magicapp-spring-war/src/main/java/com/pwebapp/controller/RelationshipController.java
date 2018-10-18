package com.pwebapp.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pwebapp.database.util.Test;
import com.pwebapp.model.Relationship;

@Controller
public class RelationshipController {

    private static final Logger logger = LogManager.getLogger(RelationshipController.class);

    @Autowired
    private Test test;

    @RequestMapping(path = "/others/getrelationship", method = RequestMethod.GET)
    public String getRelationShip(Model model) {
        if (logger.isInfoEnabled()) {
            logger.info("Inside getReasons Methods");
        }
        test.disp();
        model.addAttribute("relationship", new Relationship());
        return "getrelationship.tiles";
    }

    @RequestMapping(path = "/others/addrelationship", method = RequestMethod.POST)
    public String addRelationShip(@Valid Relationship relationship, BindingResult result,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            if (logger.isInfoEnabled()) {
                logger.info("Error in Validation :=> " + result.getAllErrors());
            }
            return "addrelationship.tiles";
        }
        if (logger.isInfoEnabled()) {
            logger.info("Data :=> " + relationship);
        }
        attributes.addFlashAttribute("message", "Relationship added sucessfully");
        return "redirect:others/getrelationship.tiles";
    }
}
