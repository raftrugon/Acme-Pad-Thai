/* ProfileController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {
	
	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;
				
		result = new ModelAndView("profile/action-1");
		
		return result;
	}
	
	// Action-2 ---------------------------------------------------------------		
	
	public static List<Integer> countPro2(Integer n){
		Integer i;
		List<Integer> lis = new ArrayList<Integer>();{
			for(i=0;i<=n;i++){
			lis.add(n);
			}
	}
		return lis;
}
	@RequestMapping("/action-2")
	public ModelAndView action2(@RequestParam(defaultValue="25")Integer count) {
		ModelAndView result;	
		result = new ModelAndView("profile/action-2");
		
		Integer i;
		String lis = "";
		
		if(count>=0){
			for(i=0;i<=count;i++){
				if(i==count)
					lis = lis + i.toString();
				else
					lis = lis + i.toString() + " , ";
			}
		}
		else{
			for(i=count;i<=0;i++){
				if(i==0)
					lis = lis + i.toString();
				else
					lis = lis + i.toString() + " , ";
			}
		}
			
		
		result.addObject("lis", lis);
		return result;
	}
	
	
	
	// Action-2 ---------------------------------------------------------------		
	
	@RequestMapping("/action-3")
	public ModelAndView action3() {
		throw new RuntimeException("Oops! An exception was thrown.");
	}
	
}