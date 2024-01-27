package com.wefox.techtest.mapper;

import com.wefox.techtest.model.Payment;
import com.wefox.techtest.store.model.Account;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PaymentMapper {
    @Mapping(target = "accountId", source = ".", qualifiedByName = "extractId")
    Payment toDto(com.wefox.techtest.store.model.Payment payment);

    @Mapping(target = "createdAt", source = "payment.createdAt")
    com.wefox.techtest.store.model.Payment toModel(Payment payment, Account account);

    @Named("extractId")
    default String extractId(com.wefox.techtest.store.model.Payment payment) {
        return String.valueOf(payment.getAccount().getId());
    }

}
