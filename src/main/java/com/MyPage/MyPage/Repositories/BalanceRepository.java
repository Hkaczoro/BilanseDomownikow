package com.MyPage.MyPage.Repositories;

import com.MyPage.MyPage.Models.Balance;
import com.MyPage.MyPage.Models.Squad;
import com.MyPage.MyPage.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BalanceRepository extends CrudRepository<Balance, Integer> {


    Balance findBySquadAndUser1AndUser2(Squad squad, User user1, User user2);

    Set<Balance> findByUser1AndSquad(User user1, Squad squad);
}
