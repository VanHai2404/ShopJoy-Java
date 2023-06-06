package com.edu.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.shop.domain.Account;


@Repository
public interface AccountReposttory extends JpaRepository<Account, String> {
	
	 Page<Account> findByUsernameContaining(String username,Pageable pageable);
	

}
