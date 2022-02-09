package com.epam.esm.impl;

import com.epam.esm.dao.impl.GiftCertificateDao;
import com.epam.esm.dao.model.giftcertificate.GiftCertificate;
import com.epam.esm.dao.model.tag.Tag;
import com.epam.esm.dao.sqlgenerator.SqlGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class GiftCertificateDaoTest {

    @Test
    void saveTest() {
        Tag tag1 = new Tag(1, "firstTag");
        Tag tag2 = new Tag(9, "supeasfr new tag");
        GiftCertificate giftCertificate = new GiftCertificate(null, "name", "description", 200.1, 200L,
                LocalDateTime.now(), LocalDateTime.now().plusDays(110), List.of(tag1, tag2));
        GiftCertificateDao dao = new GiftCertificateDao();
        GiftCertificate result = dao.saveEntity(giftCertificate);
        System.out.println(result);
    }

    @Test
    void findAllTest(){
        GiftCertificateDao dao = new GiftCertificateDao();
        System.out.println(dao.findAllEntities());
    }

    @Test
    void findByIdTest(){
        GiftCertificateDao dao = new GiftCertificateDao();
        System.out.println(dao.findEntityById(14));
    }

    @Test
    void updateEntityTest(){
        GiftCertificateDao dao = new GiftCertificateDao();
        Tag tag1 = new Tag(1, "firstTag");
        Tag tag3 = new Tag(6, "supeasfr new tag");
        GiftCertificate giftCertificate = new GiftCertificate(11, "name", null, null, 250L,
                null, null, List.of(tag1, tag3));
        dao.updateEntity(giftCertificate);
        System.out.println(dao.findEntityById(11));
    }

    @Test
    void testGenerator(){
        GiftCertificateDao dao = new GiftCertificateDao();
        System.out.println(dao.findGiftCertificatesWithParameters(null, "am", null, SqlGenerator.SortByCode.SORT_BY_NAME, true));
    }
}