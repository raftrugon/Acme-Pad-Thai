package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.AdminService;
import services.BillService;
import services.SponsorService;
import domain.Bill;
import domain.Sponsor;

@Controller
@RequestMapping("/bill")
public class BillController {
	
	@Autowired
	private BillService billService;
	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Sponsor s= sponsorService.findByPrincipal();
		
		Boolean sponsorAdmin = false;
		
		Collection<Bill> bills;
		
		if(s!=null){
			bills = billService.findAvalaibleBills(s.getId());
			sponsorAdmin = true;
		}
		else{
			bills = billService.findAll();
			if(adminService.findByPrincipal()!=null)
				sponsorAdmin = true;
		}

		result = new ModelAndView("bill/list");
		result.addObject("bills", bills);
		result.addObject("sponsorAdmin", sponsorAdmin);
		
		return result;	
	}
	
	@RequestMapping(value="/issueBill", method= RequestMethod.GET)
	public ModelAndView issueBill(@RequestParam int billId, RedirectAttributes redirectAttrs){
		ModelAndView result;
	
		try {
			Assert.notNull(adminService.findByPrincipal());
			billService.issue(billId);

			redirectAttrs.addFlashAttribute("message", "bill.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
		} catch (Throwable oops) {

			redirectAttrs.addFlashAttribute("message", "bill.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}
		
		result= new ModelAndView("redirect:/bill/list.do");
		
		return result;
	}
	
	@RequestMapping(value="/payBill", method= RequestMethod.GET)
	public ModelAndView payBill(@RequestParam int billId, RedirectAttributes redirectAttrs){
		ModelAndView result;

		try {
			Bill b = billService.findOne(billId);
			Sponsor s = sponsorService.findByPrincipal();
			Assert.isTrue(b.getSponsor().equals(s));
			billService.pay(billId);

			redirectAttrs.addFlashAttribute("message", "bill.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
		} catch (Throwable oops) {

			redirectAttrs.addFlashAttribute("message", "bill.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}
		
		result= new ModelAndView("redirect:/bill/list.do");
		
		return result;
	}
	
}
