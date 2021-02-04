package com.MyPage.MyPage.Repositories;

import com.MyPage.MyPage.Models.BalanceConfirmation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceConfirmationRepository extends CrudRepository<BalanceConfirmation, Integer> {

}
