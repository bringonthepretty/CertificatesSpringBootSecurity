package com.epam.esm.controller.controller;

import com.epam.esm.service.dto.tag.TagDto;
import com.epam.esm.service.expecption.ServiceException;
import com.epam.esm.service.impl.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping()
    public ResponseEntity<List<TagDto>> getAll() throws ServiceException {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TagDto> getById(@PathVariable("id") Integer id) throws ServiceException {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) throws ServiceException {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> create(@RequestParam TagDto tagDto) throws ServiceException {
        service.create(tagDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Integer id, @RequestParam TagDto tagDto) throws ServiceException {
        tagDto.setId(id);
        service.update(tagDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler({ServiceException.class, DataAccessException.class})
    public ResponseEntity<String> handleException(Exception e) {
        JSONObject response = new JSONObject();
        if (e instanceof ServiceException serviceException) {
            response.put("message", serviceException.getExceptionCode());
            response.put("internalExceptionCode", serviceException.getExceptionCode().getExceptionCode());
            return new ResponseEntity<>(response.toString(), Optional.of(HttpStatus.valueOf(serviceException.getExceptionCode().getHttpStatus())).orElse(HttpStatus.INTERNAL_SERVER_ERROR));
        } else {
            response.put("message", ((DataAccessException) e).getMessage());
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
