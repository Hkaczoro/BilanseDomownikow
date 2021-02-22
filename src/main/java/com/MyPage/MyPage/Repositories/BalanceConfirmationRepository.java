package com.MyPage.MyPage.Repositories;

import com.MyPage.MyPage.Models.BalanceConfirmation;
import com.MyPage.MyPage.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BalanceConfirmationRepository extends CrudRepository<BalanceConfirmation, Integer> {

    BalanceConfirmation findById(int id);

}
