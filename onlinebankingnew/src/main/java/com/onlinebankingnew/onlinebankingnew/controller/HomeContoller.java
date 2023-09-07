package com.onlinebankingnew.onlinebankingnew.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.onlinebankingnew.onlinebankingnew.entity.Account;
import com.onlinebankingnew.onlinebankingnew.entity.Customer;
import com.onlinebankingnew.onlinebankingnew.entity.PaymentTransferForm;
import com.onlinebankingnew.onlinebankingnew.entity.Transaction;
import com.onlinebankingnew.onlinebankingnew.service.AccountService;
import com.onlinebankingnew.onlinebankingnew.service.CustomerService;
import com.onlinebankingnew.onlinebankingnew.service.TransactionService;
import com.onlinebankingnew.onlinebankingnew.service.TransactionService;

@Controller
public class HomeContoller {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

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

        // if (customer != null && customer.getAccount()) {
        // account.setCustomer(customer); // Set the customer for the account
        // accountService.createAccount(account);
        // redirectAttributes.addFlashAttribute("accountCreated", true);
        // return "redirect:/welcome";
        // } else {
        // // Handle case where customer is not found
        // // You might want to redirect to an error page or show a message
        // return "";
        // }
        if (customer != null) {
            List<Account> accList = customer.getAccount();
            int currentAccountCount = 0;
            int savingsAccountCount = 0;

            for (Account acc : accList) {
                if (acc.getAccountType().equalsIgnoreCase("current")) {
                    currentAccountCount++;

                } else if (acc.getAccountType().equalsIgnoreCase("savings")) {
                    savingsAccountCount++;
                }
            }
            System.out.println(accList);

            if ((currentAccountCount < 1 && account.getAccountType().equalsIgnoreCase("current")) ||
                    (savingsAccountCount < 1 && account.getAccountType().equalsIgnoreCase("savings"))) {

                account.setCustomer(customer); // Set the customer for the account
                accountService.createAccount(account);
                redirectAttributes.addFlashAttribute("accountCreated", true);
                return "redirect:/welcome";
            } else {

                return "redirect:/error";
            }
        } else {

            return "redirect:/login";
        }
    }

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

    @GetMapping("/add-money")
    public String addMoneyForm() {

        return "add-money";
    }

    @PostMapping("/add-money")
    public String addMoneyForm2(@RequestParam("accountType") String accountType,
            @RequestParam("amount") double amount,Model model,
            HttpSession httpSession) {
        String username = httpSession.getAttribute("username").toString();
        Customer cust = customerService.getCustomerByUsername(username);
        List<Account> accList = accountService.getAccountsByCustomer(cust);
       
        for (Account acc : accList) {
            if (acc.getAccountType().equals(accountType)) {
                
                accountService.addMoneytoAccount(acc.getAccountId(), amount);
            }
        }


        model.addAttribute("message", "Money added successfully!"); // You can use this message in your Thymeleaf
                                                                    // template
        return "add-money";

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

    @GetMapping("/transaction-history")
    public String showTransactionHistory(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Model model, HttpSession session) {
        String username = session.getAttribute("username").toString();
        Customer customer = customerService.getCustomerByUsername(username);

        if (customer != null) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
            Page<Transaction> transactionsPage = transactionService
                    .getTransactionsByCustomerId(customer.getCustomerId(), pageable);
            // List<Transaction> transactions =
            // transactionService.getTransactionsByCustomerId(customer.getCustomerId());
            model.addAttribute("transactions", transactionsPage.getContent());
            model.addAttribute("totalPages", transactionsPage.getTotalPages());
            model.addAttribute("currentPage", page);
            return "transaction-history";
        }

        // Handle case where customer is not found
        // You might want to redirect to an error page or show a message
        return "redirect:/login"; // Or any other appropriate action
    }

}