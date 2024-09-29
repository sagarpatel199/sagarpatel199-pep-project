package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    //register new account
    public Account registerAccount(Account account){
        if(account.getPassword().length() < 4 || account.getUsername() == null || account.getUsername().trim().isBlank()) return null;

        if(accountDAO.getAccountByUsername(account.getUsername()) == null) return accountDAO.createAccount(account);
        return null;
    }

    public Account loginAccount(String username ,String password){
        Account tempAccount = accountDAO.getAccountByUsername(username);
        
         if( tempAccount != null  && tempAccount.getPassword().equals(password)) return accountDAO.getAccountByUsername(username);

        return null;
    }

    


}
