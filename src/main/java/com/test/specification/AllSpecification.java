package com.test.specification;

import com.test.dto.CititesDto.CityFilterDto;
import com.test.dto.PinDtos.PinCodeFilterDto;
import com.test.dto.StatesDto.StateFilterDto;
import com.test.entity.City;
import com.test.entity.PinCode;
import com.test.entity.State;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class AllSpecification {

    public static Specification<PinCode> getPinSpecification(PinCodeFilterDto filterDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterDto.getPincode() != null) {
                predicates.add(criteriaBuilder.equal(root.get("pincode"), filterDto.getPincode()));
            }

            if (filterDto.getCityName() != null) {
                Join<PinCode, City> cityJoin = root.join("city", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(root.get("city").get("cityName"), filterDto.getCityName()));
            }

            if (filterDto.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), filterDto.getStatus()));
            }

            if (filterDto.getCreatedAtFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), filterDto.getCreatedAtFrom()));
            }

            if (filterDto.getCreatedAtTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), filterDto.getCreatedAtTo()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public static Specification<City> getCitySpecification(CityFilterDto cityFilterDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (cityFilterDto.getCityName() != null) {
                predicates.add(criteriaBuilder.equal(root.get("cityName"), cityFilterDto.getCityName()));
            }

            if (cityFilterDto.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), cityFilterDto.getStatus()));
            }

            if (cityFilterDto.getCreatedAtFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), cityFilterDto.getCreatedAtFrom()));
            }

            if (cityFilterDto.getCreatedAtTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), cityFilterDto.getCreatedAtTo()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    public static Specification<State> getStateSpecification(StateFilterDto stateFilterDto) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(stateFilterDto.getStateName() != null){
                predicates.add(criteriaBuilder.equal(root.get("stateName"), stateFilterDto.getStateName()));
            }

            if (stateFilterDto.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), stateFilterDto.getStatus()));
            }

            if (stateFilterDto.getCreatedAtFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), stateFilterDto.getCreatedAtFrom()));
            }

            if (stateFilterDto.getCreatedAtTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), stateFilterDto.getCreatedAtTo()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }

}
