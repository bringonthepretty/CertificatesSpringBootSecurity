package com.epam.esm.service.validator.impl;

import com.epam.esm.service.dto.giftcertificate.GiftCertificateDto;
import com.epam.esm.service.dto.order.OrderDto;
import com.epam.esm.service.dto.user.UserDto;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.ExceptionMessage;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.api.Validator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Validator implementation for OrderDto
 */

@Component
public class OrderValidator implements Validator<OrderDto> {

    @Override
    public void validate(OrderDto value, Boolean checkId) throws ServiceException {
        List<ExceptionMessage> exceptionMessages = new  ArrayList<>();

        if (Objects.isNull(value)){
            throw new ServiceException(ExceptionCode.VALIDATION_FAILED_EXCEPTION, ExceptionMessage.ORDER_CANNOT_BE_NULL);
        }

        if (checkId){
            validateIdNotNullAndPositive(value.getId());
        }

        validateUser(value.getUser(), exceptionMessages);
        validateGiftCertificate(value.getGiftCertificate(), exceptionMessages);

        if (!exceptionMessages.isEmpty()){
            throw new ServiceException(ExceptionCode.VALIDATION_FAILED_EXCEPTION, exceptionMessages);
        }
    }

    private void validateUser(UserDto user, List<ExceptionMessage> exceptionMessages) {
        if (Objects.isNull(user)){
            exceptionMessages.add(ExceptionMessage.ORDER_USER_CANNOT_BE_NULL);
            return;
        }
        if (Objects.isNull(user.getId())){
            exceptionMessages.add(ExceptionMessage.ORDER_USER_ID_CANNOT_BE_NULL);
            return;
        }
        if (user.getId() < 1){
            exceptionMessages.add(ExceptionMessage.ORDER_USER_ID_CANNOT_BE_NEGATIVE);
        }
    }

    private void validateGiftCertificate(GiftCertificateDto giftCertificate, List<ExceptionMessage> exceptionMessages) {
        if (Objects.isNull(giftCertificate)){
            exceptionMessages.add(ExceptionMessage.ORDER_GIFT_CERTIFICATE_CANNOT_BE_NULL);
            return;
        }
        if (Objects.isNull(giftCertificate.getId())){
            exceptionMessages.add(ExceptionMessage.ORDER_GIFT_CERTIFICATE_ID_CANNOT_BE_NULL);
            return;
        }
        if (giftCertificate.getId() < 1){
            exceptionMessages.add(ExceptionMessage.ORDER_GIFT_CERTIFICATE_ID_CANNOT_BE_NEGATIVE);
        }
    }
}
