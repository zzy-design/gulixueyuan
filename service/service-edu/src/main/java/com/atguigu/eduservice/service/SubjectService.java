package com.atguigu.eduservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface SubjectService {
    void importSubjectData(MultipartFile file, SubjectService subjectService);
}
