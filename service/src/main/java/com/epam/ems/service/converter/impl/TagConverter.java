package com.epam.ems.service.converter.impl;

import com.epam.ems.service.converter.api.Converter;
import com.epam.ems.service.dto.tag.TagDto;
import com.epam.esm.dao.model.tag.Tag;

public class TagConverter implements Converter<Tag, TagDto> {
    @Override
    public Tag convert(TagDto value) {
        return new Tag(value.getId(), value.getName());
    }

    @Override
    public TagDto convert(Tag value) {
        return new TagDto(value.id(), value.name());
    }
}