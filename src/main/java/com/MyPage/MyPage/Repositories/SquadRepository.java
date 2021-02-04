package com.MyPage.MyPage.Repositories;

import com.MyPage.MyPage.Models.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SquadRepository extends CrudRepository<Squad, Integer> {

    Squad findById(int ID);
}
