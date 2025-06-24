package com.test.respository;

import com.test.entity.State;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StateRepository extends JpaRepository<State, Long>{


    State findBystateIdAndDeletedAtIsNull(Long stateId);

}
