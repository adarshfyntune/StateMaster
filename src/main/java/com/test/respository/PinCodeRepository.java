package com.test.respository;

import com.test.entity.PinCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PinCodeRepository extends JpaRepository<PinCode, Long>, JpaSpecificationExecutor<PinCode> {

    Optional<PinCode> findByPinCode(Long pinCode);


}
