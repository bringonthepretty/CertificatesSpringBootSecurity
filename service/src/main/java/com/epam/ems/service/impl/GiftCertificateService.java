package com.epam.ems.service.impl;

import com.epam.ems.service.api.Service;
import com.epam.ems.service.converter.api.Converter;
import com.epam.ems.service.converter.impl.GiftCertificateConverter;
import com.epam.ems.service.dto.giftcertificate.GiftCertificateDto;
import com.epam.ems.service.expecption.ServiceException;
import com.epam.ems.service.validator.api.Validator;
import com.epam.ems.service.validator.impl.GiftCertificateValidator;
import com.epam.esm.dao.api.Dao;
import com.epam.esm.dao.impl.GiftCertificateDao;
import com.epam.esm.dao.model.giftcertificate.GiftCertificate;
import com.epam.esm.dao.sqlgenerator.SqlGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GiftCertificateService implements Service<GiftCertificateDto> {

    Dao<GiftCertificate> dao = new GiftCertificateDao();
    Converter<GiftCertificate, GiftCertificateDto> converter = new GiftCertificateConverter();
    Validator<GiftCertificateDto> validator = new GiftCertificateValidator();

    @Override
    public GiftCertificateDto create(GiftCertificateDto value) throws ServiceException {
        validator.validate(value, false);
        GiftCertificate savedGiftCertificate = dao.saveEntity(converter.convert(value));

        if (Objects.isNull(savedGiftCertificate)){
            throw new ServiceException("Gift certificate wasn't created because of internal error");
        }

        return converter.convert(savedGiftCertificate);
    }

    @Override
    public Boolean update(GiftCertificateDto value) throws ServiceException {
        validator.validate(value, true);
        return dao.updateEntity(converter.convert(value));
    }

    @Override
    public Boolean delete(GiftCertificateDto value) throws ServiceException {
        validator.validate(value, true);
        return dao.deleteEntity(converter.convert(value));
    }

    @Override
    public GiftCertificateDto getById(Integer id) throws ServiceException {
        validator.validateIdNotNull(id);
        GiftCertificate result = dao.findEntityById(id);

        if (Objects.isNull(result)){
            throw new ServiceException("gift certificate is not found");
        }

        return converter.convert(result);
    }

    @Override
    public List<GiftCertificateDto> getAll() throws ServiceException {
        List<GiftCertificate> daoResult = dao.findAllEntities();

        if (daoResult.isEmpty()){
            throw new ServiceException("repository is empty or cant be accessed");
        }

        return daoResult.stream().map(converter::convert).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *
     * @param tagId id of tag to be searched, null if no need to search by tag
     * @param namePart part of name to be filtered, null if no need to filter by name part
     * @param descriptionPart part of description to be filtered, null if no need to filter description part
     * @param sortBy sort by code, null if no need to sort
     * @param ascending true for ascending sort, false if descending. ignored if sortBy is null
     * @return list of gift certificates that match parameters
     * @throws ServiceException if there is no gift certificates with provided parameters
     */
    public List<GiftCertificateDto> getAllWithParameters(Integer tagId, String namePart, String descriptionPart, SqlGenerator.SortByCode sortBy, Boolean ascending) throws ServiceException {
        List<GiftCertificate> daoResult = ((GiftCertificateDao)dao).findGiftCertificatesWithParameters(tagId, namePart, descriptionPart, sortBy, ascending);

        if (daoResult.isEmpty()){
            throw new ServiceException("there is no gift certificates with provided parameters");
        }

        return daoResult.stream().map(converter::convert).collect(Collectors.toCollection(ArrayList::new));
    }
}