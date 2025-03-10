package com.candidate.dao;
import com.candidate.dto.CandidateApplicationDTO;
import com.candidate.model.CandidateApplicationModel;
import com.candidate.exception.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Utility class for mapping between different representations of candidate applications.
 * Converts between DTO, DAO, and Model layers.
 */
public class CandidateApplicationMapper {
    private static final Logger log = LoggerFactory.getLogger(CandidateApplicationMapper.class);

    /**
     * Converts a {@link CandidateApplicationDTO.CandidateApplicationRequest} to a {@link CandidateApplicationModel}.
     *
     * @param request The DTO request containing candidate application data.
     * @return A {@link CandidateApplicationModel} representing the application in the business logic layer.
     */
    public static CandidateApplicationModel toModel(CandidateApplicationDTO.CandidateApplicationRequest request) {
        if (request == null) {
            throw new MappingException("DTO request cannot be null");
        }
        if (request.getCandidateName() == null || request.getCandidateName().isEmpty()) {
            throw new MappingException("Candidate name is missing or empty");
        }
        if (request.getSkills() == null || request.getSkills().isEmpty()) {
            throw new MappingException("Skills field is missing or empty");
        }
        return new CandidateApplicationModel(
                    0,
                    request.getCandidateName(),
                    request.getSkills(),
                    request.getExperienceYears(),
                    request.getAvailableFrom(),
                    request.getAvailableTo()
            );
    }

    /**
     * Converts a {@link CandidateApplicationModel} to a {@link CandidateApplicationDAO} for database storage.
     *
     * @param candidateApplicationModel The business logic model of the application.
     * @return A {@link CandidateApplicationDAO} representing the database entity.
     * @throws MappingException If the model is null or invalid.
     */
    public static CandidateApplicationDAO toDao(CandidateApplicationModel candidateApplicationModel) {
        if (candidateApplicationModel == null) {
            log.error("Mapping failure: CandidateApplicationModel is null.");
            throw new MappingException("Cannot map null CandidateApplicationModel to DAO.");
        }

        try {
            return new CandidateApplicationDAO(
                    candidateApplicationModel.getCandidateName(),
                    candidateApplicationModel.getSkills(),
                    candidateApplicationModel.getExperienceYears(),
                    candidateApplicationModel.getAvailableFrom(),
                    candidateApplicationModel.getAvailableTo()
            );
        } catch (Exception e) {
            log.error("Error occurred while mapping CandidateApplicationModel to DAO: {}", e.getMessage(), e);
            throw new MappingException("Invalid data format for mapping.");
        }
    }

    /**
     * Converts a {@link CandidateApplicationDAO} to a {@link CandidateApplicationModel} for business logic processing.
     *
     * @param candidateApplicationDao The database entity.
     * @return A {@link CandidateApplicationModel} representing the application in the service layer.
     * @throws MappingException If the DAO is null or contains invalid data.
     */
    public static CandidateApplicationModel toDomain(CandidateApplicationDAO candidateApplicationDao) throws MappingException {
        if (candidateApplicationDao == null) {
            log.error("Mapping failure: CandidateApplicationDAO is null.");
            throw new MappingException("Cannot map null CandidateApplicationDAO to CandidateApplicationModel.");
        }

        try {
            return new CandidateApplicationModel(
                    candidateApplicationDao.getId(),
                    candidateApplicationDao.getCandidateName(),
                    candidateApplicationDao.getSkills(),
                    candidateApplicationDao.getExperienceYears(),
                    candidateApplicationDao.getAvailableFrom(),
                    candidateApplicationDao.getAvailableTo()
            );
        } catch (Exception e) {
            log.error("Error occurred while mapping CandidateApplicationDAO to CandidateApplicationModel: {}", e.getMessage(), e);
            throw new MappingException("Invalid data format during DAO-to-Model mapping.");
        }
    }

    /**
     * Converts a {@link CandidateApplicationDAO} to a {@link CandidateApplicationDTO.CandidateApplicationResponse}
     * for API responses.
     *
     * @param dao The database entity representing the application.
     * @return A {@link CandidateApplicationDTO.CandidateApplicationResponse} containing the response data.
     * @throws MappingException If the DAO is null or contains invalid data.
     */
    public static CandidateApplicationDTO.CandidateApplicationResponse toResponse(CandidateApplicationDAO dao) throws MappingException {
        if (dao == null) {
            log.error("Mapping failure: CandidateApplicationDAO is null.");
            throw new MappingException("Cannot map null CandidateApplicationDAO to CandidateApplicationResponse.");
        }

        try {
            CandidateApplicationDTO.CandidateApplicationResponse response = new CandidateApplicationDTO.CandidateApplicationResponse();
            response.setId(dao.getId());
            response.setCandidateName(dao.getCandidateName());
            response.setStatusMessage("Application retrieved successfully");
            return response;
        } catch (Exception e) {
            log.error("Error occurred while mapping CandidateApplicationDAO to CandidateApplicationResponse: {}", e.getMessage(), e);
            throw new MappingException("Invalid data format during DAO-to-DTO mapping.");
        }
    }
}