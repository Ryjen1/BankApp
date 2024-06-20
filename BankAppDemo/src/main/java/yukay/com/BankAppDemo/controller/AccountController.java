package yukay.com.BankAppDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yukay.com.BankAppDemo.model.Account;
import yukay.com.BankAppDemo.service.AccountService;

import java.util.List;

@Controller
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public String getAllAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccount();
        model.addAttribute("accounts", accounts);
        return "accounts";
    }

    @GetMapping("/accounts/new")
    public String showCreateAccountForm(Model model) {
        model.addAttribute("account", new Account());
        return "create_account";
    }

    @PostMapping("/accounts")
    public String createAccount(@ModelAttribute("account") Account account) {
        accountService.createAccount(account);
        return "redirect:/accounts";
    }

    @GetMapping("/accounts/edit/{accountNumber}")
    public String showEditAccountForm(@PathVariable Long accountNumber, Model model) {
        Account account = accountService.getAccount(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        model.addAttribute("account", account);
        return "edit_account";
    }

    @PostMapping("/accounts/update/{accountNumber}")
    public String updateAccount(@PathVariable Long accountNumber, @ModelAttribute("account") Account account) {
        Account existingAccount = accountService.getAccount(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        existingAccount.setFirstName(account.getFirstName());
        existingAccount.setLastName(account.getLastName());
        existingAccount.setBalance(account.getBalance());
        accountService.createAccount(existingAccount);
        return "redirect:/accounts";
    }

    @GetMapping("/accounts/delete/{accountNumber}")
    public String deleteAccount(@PathVariable Long accountNumber) {
        accountService.deleteAccount(accountNumber);
        return "redirect:/accounts";
    }

    @PostMapping("/accounts/deposit/{accountNumber}")
    public String deposit(@PathVariable Long accountNumber, @RequestParam Double amount) {
        accountService.deposite(accountNumber, amount);
        return "redirect:/accounts";
    }

    @PostMapping("/accounts/withdraw/{accountNumber}")
    public String withdraw(@PathVariable Long accountNumber, @RequestParam Double amount) {
        accountService.withdraw(accountNumber, amount);
        return "redirect:/accounts";
    }
}
