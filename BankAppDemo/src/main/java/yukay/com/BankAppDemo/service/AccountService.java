package yukay.com.BankAppDemo.service;

import org.springframework.stereotype.Service;
import yukay.com.BankAppDemo.model.Account;
import yukay.com.BankAppDemo.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        return  accountRepository.save(account);
    }

    public Optional <Account> getAccount(Long accountNumber) {
        Optional <Account> account = accountRepository.findById(accountNumber);
        if (account.isEmpty()){
            throw new RuntimeException("Account not found");
        }
        return accountRepository.findById(accountNumber);
    }

   public List<Account> getAllAccount(){
        return accountRepository.findAll();
   }

    public String deleteAccount(Long accountNumber) {
        Optional <Account> account = accountRepository.findById(accountNumber);
        if (!account.isPresent()){
            return "Account does not exist";
        }
        accountRepository.deleteById(accountNumber);
        return "Account deleted successfully";
    }

    public Account deposite(Long accountNumber, Double balance) {
        Optional <Account> account = accountRepository.findByAccountNumber(accountNumber);
        if (!account.isPresent()){
            throw new RuntimeException("Account does not exist");
        }
       Account getAccount = account.get();
       getAccount.setBalance(getAccount.getBalance() + balance);
       accountRepository.save(getAccount);
       return getAccount;
    }

    public Account withdraw(Long accountNumber, Double balance) {
        Optional <Account> account = accountRepository.findByAccountNumber(accountNumber);
        if (account.isEmpty()){
            throw new RuntimeException("Account does not exist");
        }
       Account optionalAccount = account.get();
       optionalAccount.setBalance(optionalAccount.getBalance() - balance);
       accountRepository.save(optionalAccount);
       return optionalAccount;
    }


}
