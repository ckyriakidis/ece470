package com.hms.springbackendhms.util.diagnosis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

    @Autowired
    public DiagnosisService(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    public void addDiagnosis(Diagnosis diagnosis) {
        diagnosisRepository.save(diagnosis);
    }

    public void addListDiagnosis(List<Diagnosis> diagnosisList) {
        for(int i=0; i< (long) diagnosisList.size();i++) {
            diagnosisRepository.save(diagnosisList.get(i));
        }
    }
}
