package com.ead.authuser.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.ead.authuser.services.UtilsService;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    public String createUrlGetAllCoursesByUser(UUID userId, Pageable pageable) {
        return "/courses?userId=" + userId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }
}
