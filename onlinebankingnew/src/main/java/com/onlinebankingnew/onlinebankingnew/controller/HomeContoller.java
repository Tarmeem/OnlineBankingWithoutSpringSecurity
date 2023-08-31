package com.onlinebankingnew.onlinebankingnew.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.onlinebankingnew.onlinebankingnew.entity.Account;
import com.onlinebankingnew.onlinebankingnew.entity.Customer;
import com.onlinebankingnew.onlinebankingnew.entity.PaymentTransferForm;
import com.onlinebankingnew.onlinebankingnew.service.AccountService;
import com.onlinebankingnew.onlinebankingnew.service.CustomerService;

@Controller
public class HomeContoller {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String home() {
        return "login";
    }

    
    @GetMapping("/welcome")
    public String showWelcomePage(Model model, HttpSession session) {
        String username = session.getAttribute("username").toString();
        // ... (other logic)
        System.out.println(username);
        if (username != null) {
            model.addAttribute("username", username);
        }

        // ... (other logic)
        return "welcome";
    }

    @PostMapping("/welcome")
    public String validateCustomer(@ModelAttribute("customer") Customer customer,
            RedirectAttributes redirectAttributes, HttpSession session) {
        String username = customer.getCustomerName();
        String password = customer.getCustomerPassword();
        Customer cust = customerService.getCustomerByUsernameAndPassword(username, password);

        if (cust != null) {
            // redirectAttributes.addAttribute("username", username); // Add username as a
            // URL parameter
            session.setAttribute("username", username);
            return "redirect:/welcome";
        }

        return "login";
    }

    @GetMapping("/register")
    public String register(Model m) {
        m.addAttribute("nameExists", false);
        m.addAttribute("emailExists", false);
        m.addAttribute("phoneNoExists", false);

        return "register";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute("customer") Customer cust, Model model) {
        if (customerService.doesNameExist(cust.getCustomerName())) {
            model.addAttribute("nameExists", true);
            return "register";

        } else if (customerService.doesEmailExist(cust.getCustomerEmail())) {
            model.addAttribute("emailExists", true);
            return "register";
        } else if (customerService.doesPhoneNumberExist(cust.getPhoneNo())) {
            model.addAttribute("phoneNoExists", true);
            return "register";
        }
        System.out.print(cust);
        customerService.addCustomer(cust);
        return "redirect:register?success";
    }

    @GetMapping("/AccountDetails1")
    public String showAccountDetails(Model model, HttpSession session) {
        String username = session.getAttribute("username").toString();
        System.out.println(username);
        Customer customer = customerService.getCustomerByUsername(username);
        // System.out.println(customer);
        if (customer != null) {
            List<Account> accounts = accountService.getAllAccountById(customer.getCustomerId());
            model.addAttribute("accounts", accounts);
            return "AccountDetails";
        }
        // Handle case where customer is not found
        // You might want to redirect to an error page or show a message
        return "";

    }


    @GetMapping("/createaccount")
    public String AccountForm() {
        return "createAccount";
    }

    @PostMapping("/createaccount")
    public String createAccount(@ModelAttribute("account") Account account, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String username = session.getAttribute("username").toString();
        System.out.println(username);
        Customer customer = customerService.getCustomerByUsername(username);
        System.out.println(customer);

        if (customer != null) {
            account.setCustomer(customer); // Set the customer for the account
            accountService.createAccount(account);
            redirectAttributes.addFlashAttribute("accountCreated", true);
            return "redirect:/welcome";
        } else {
            // Handle case where customer is not found
            // You might want to redirect to an error page or show a message
            return "";
        }
    }

    @Controller
public class HomeController {

    // ... (other mappings)

    @GetMapping("/payment-transfer")
    public String showPaymentTransferForm(Model model) {
        // Fetch accounts and populate dropdown options
        List<Account> sourceAccounts = accountService.getAllAccounts();
        List<Account> targetAccounts = accountService.getAllAccounts();
        
        model.addAttribute("sourceAccounts", sourceAccounts);
        model.addAttribute("targetAccounts", targetAccounts);

        // Create a model attribute for the payment transfer form
        model.addAttribute("transferForm", new PaymentTransferForm());

        return "payment-transfer";
    }

    @PostMapping("/payment-transfer")
    public String transferAmount(@ModelAttribute("transferForm") PaymentTransferForm transferForm,
                                 Model model) {
        // Get source and target account IDs from the form
        Long sourceAccountId = transferForm.getSourceAccountId();
        Long targetAccountId = transferForm.getTargetAccountId();

        // Get amount from the form
        Double amount = transferForm.getAmount();

        if (sourceAccountId.equals(targetAccountId)) {
            model.addAttribute("errorOccurred", true);
            return "payment-transfer";
        }

        // Perform the transfer logic using accountService
        accountService.transferMoney(sourceAccountId, targetAccountId, amount);

        model.addAttribute("successOccurred", true);

        // Redirect back to payment-transfer with a success message
        return "redirect:/payment-transfer?success";
    }
}



}
