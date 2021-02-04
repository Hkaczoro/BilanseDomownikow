package com.MyPage.MyPage.Repositories;

import com.MyPage.MyPage.Models.History;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface HistoryRepository extends CrudRepository<History, Integer> {
}
