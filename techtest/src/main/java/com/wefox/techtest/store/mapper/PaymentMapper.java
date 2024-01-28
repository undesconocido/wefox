package com.wefox.techtest.store.mapper;

import com.wefox.techtest.domain.model.Payment;
import com.wefox.techtest.store.entity.Account;
import com.wefox.techtest.store.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "createdAt", ignore = true)
    PaymentEntity toEntity(Payment payment, Account account);

}
