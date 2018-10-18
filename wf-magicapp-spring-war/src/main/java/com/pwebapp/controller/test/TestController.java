package com.pwebapp.controller.test;

import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pwebapp.database.procedure.PSysDate;

@Controller
public class TestController {

    private static final Logger logger = LogManager.getLogger(TestController.class);

   // @Autowired
   // private PSysDate pdbdate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String testMethod() {
   //     Date date = pdbdate.execute();
   //     logger.info(date);
        return "redirect:/auth/login";
    }
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String testMethod1() {
   //     Date date = pdbdate.execute();
   //     logger.info(date);
        return "redirect:/auth/login";
    }

}
