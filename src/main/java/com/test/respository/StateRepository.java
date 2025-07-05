package com.test.respository;

import com.test.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long>, JpaSpecificationExecutor<State> {


    State findBystateIdAndDeletedAtIsNull(Long stateId);

    Optional<State> findByStateNameIgnoreCase(String stateName);

}
