package com.tcha.pt_class.service;

import com.tcha.pt_class.dto.PtClassDto.Patch;
import com.tcha.pt_class.dto.PtClassDto.Response;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PtClassService {

    public void createPtClass(String trainerId, List<LocalDateTime> startTimeList) {
    }

    public void updatePtClass(String trainerId, Patch patchRequest) {
    }

    public Response findOnePtClass(String ptClassId) {

        return null;
    }

    public List<Response> findAllPtClasses() {

        return null;
    }

    public void deletePtClass(String trainerId, long ptClassId) {
    }
}
