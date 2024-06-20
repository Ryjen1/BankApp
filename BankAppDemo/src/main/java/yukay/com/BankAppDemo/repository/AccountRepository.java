package yukay.com.BankAppDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yukay.com.BankAppDemo.model.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByAccountNumber(Long accountNumber);
}
