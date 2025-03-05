package com.candidate.dao;
import com.candidate.dto.CandidateApplicationDTO;
import com.candidate.model.CandidateApplicationModel;

public class CandidateApplicationMapper {

    // Convert DTO Request -> Model
    public static CandidateApplicationModel toModel(CandidateApplicationDTO.CandidateApplicationRequest request) {
        return new CandidateApplicationModel(
                0,  // Assuming ID is auto-generated
                request.getCandidateName(),
                request.getSkills(),
                request.getExperienceYears(),
                request.getAvailableFrom(),
                request.getAvailableTo()
        );
    }

    // Convert Model -> DAO (for Database Storage)
    public static CandidateApplicationDAO toDao(CandidateApplicationModel candidateApplicationModel) {
        return new CandidateApplicationDAO(
                candidateApplicationModel.getCandidateName(),
                candidateApplicationModel.getSkills(),
                candidateApplicationModel.getExperienceYears(),
                candidateApplicationModel.getAvailableFrom(),
                candidateApplicationModel.getAvailableTo()
        );
    }

    // Convert DAO -> Model (for Business Logic)
    public static CandidateApplicationModel toDomain(CandidateApplicationDAO candidateApplicationDao) {
        return new CandidateApplicationModel (
                candidateApplicationDao.getId(),
                candidateApplicationDao.getCandidateName(),
                candidateApplicationDao.getSkills(),
                candidateApplicationDao.getExperienceYears(),
                candidateApplicationDao.getAvailableFrom(),
                candidateApplicationDao.getAvailableTo()
        );
    }

    // Convert DAO -> DTO Response (for API)
    public static CandidateApplicationDTO.CandidateApplicationResponse toResponse(CandidateApplicationDAO dao) {
        CandidateApplicationDTO.CandidateApplicationResponse response = new CandidateApplicationDTO.CandidateApplicationResponse();
        response.setId(dao.getId());
        response.setCandidateName(dao.getCandidateName());
        response.setStatusMessage("Application retrieved successfully");
        return response;
    }
}
