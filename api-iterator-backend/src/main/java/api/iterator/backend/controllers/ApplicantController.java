package api.iterator.backend.controllers;


import api.iterator.backend.business.logic.ApplicantBusinessImpl;
import api.iterator.backend.business.validators.ApplicantValidator;
import api.iterator.backend.exceptions.SystemException;
import api.iterator.backend.models.UserResponseModel;
import api.iterator.backend.models.displays.applicant.ApplicantEducationModel;
import api.iterator.backend.models.displays.applicant.ApplicantExperienceModel;
import api.iterator.backend.models.displays.applicant.SkillModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/applicant")
public class ApplicantController {
    private final ApplicantBusinessImpl applicantBusinessImpl;
    private final ApplicantValidator applicantValidator;

    public ApplicantController(ApplicantBusinessImpl applicantBusinessImpl, ApplicantValidator applicantValidator) {
        this.applicantBusinessImpl = applicantBusinessImpl;
        this.applicantValidator = applicantValidator;
    }

    @PutMapping("/edit-education/{applicant_id}")
    public ResponseEntity<UserResponseModel> editEducationApplicant(
            @RequestBody ApplicantEducationModel applicantEducationModel,
            @PathVariable("applicant_id") long applicantId
    ) throws SystemException {
        applicantValidator.checkApplicantExist(applicantId);
        applicantBusinessImpl.editEducation(applicantEducationModel, applicantId);

        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Edit education successfully!"),
                HttpStatus.OK
        );
    }

    @PutMapping("/edit-experiences/{applicant_id}")
    public ResponseEntity<UserResponseModel> editSkillApplicant(
            @RequestBody ArrayList<ApplicantExperienceModel> applicantExperienceModelArrayList,
            @PathVariable("applicant_id") long applicantId
    ) throws SystemException {
        applicantValidator.checkApplicantExist(applicantId);
        applicantBusinessImpl.editExperiences(applicantExperienceModelArrayList, applicantId);

        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Edit experiences successfully!"),
                HttpStatus.OK
        );
    }

    @PutMapping("/edit-skills/{applicant_id}")
    public ResponseEntity<UserResponseModel> editSkillSetsApplicant(
            @RequestBody List<SkillModel> skillModels,
            @PathVariable("applicant_id") long applicantId
    ) throws SystemException {
        applicantValidator.checkApplicantExist(applicantId);
        applicantBusinessImpl.editSkillSets(skillModels, applicantId);

        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Edit skill sets successfully!"),
                HttpStatus.OK
        );
    }
}

