package com.geeks.guitar;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//security
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Handles requests for the application home page.
 *
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		return "home";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String testHome(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/payU_Home", method = RequestMethod.GET)
	public String payU_Home(Locale locale, Model model) {
		logger.info("Welcome payU_Home! the client locale is "+ locale.toString());
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "payU_Home";
	}

	@RequestMapping(value = "/payU_Process", method = RequestMethod.POST)
	public String payU_Process(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Welcomess payU_Process! the client locale is "+ locale.toString());
		
		JavaIntegrationKit2 integrationKit = new JavaIntegrationKit2();
        try {
			Map<String, String> values = integrationKit.hashCalMethod(request, response);
			System.out.println(values.get("hash"));

			model.addAttribute("values", values);
        } catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "payU_Process";
	}
	
	@RequestMapping(value = "/payU_Success")
	public String payU_Success(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Welcomess payU_Success! the client locale is "+ locale.toString());
		
        try {
			System.out.println("Payment "+request.getParameter("status"));
			
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "payU_Success";
	}
	
	@RequestMapping(value = "/payU_Fail")
	public String payU_Fail(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Welcomess payU_Fail! the client locale is "+ locale.toString());
		
        try {
        	System.out.println("Payment "+request.getParameter("status"));
        	
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "payU_Fail";
	}
	

	
	
}
