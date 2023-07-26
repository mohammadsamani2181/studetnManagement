package com.studentManagement.service.impl;

import com.studentManagement.config.ApplicationConfig;
import com.studentManagement.config.PrincipalConfig;
import com.studentManagement.config.SchoolConfig;
import com.studentManagement.model.DTO.request.SchoolDTOUpdateRequest;
import com.studentManagement.model.DTO.response.SchoolDTOResponse;
import com.studentManagement.model.Principal;
import com.studentManagement.model.School;
import com.studentManagement.model.User;
import com.studentManagement.repository.PrincipalRepository;
import com.studentManagement.repository.SchoolRepository;
import com.studentManagement.repository.UserRepository;
import com.studentManagement.service.PrincipalService;
import com.studentManagement.service.SchoolService;
import com.studentManagement.service.mapper.SchoolDTOResponseMapper;
import com.studentManagement.service.validator.SchoolValidator;
import com.studentManagement.utils.ServiceUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service("SchoolServiceImpl")
public class SchoolServiceImpl implements SchoolService {
    private SchoolRepository schoolRepository;
    private SchoolDTOResponseMapper schoolDTOResponseMapper;
    private SchoolValidator schoolValidator;
    private SchoolConfig schoolConfig;
    private PrincipalConfig principalConfig;
    private PrincipalRepository principalRepository;
    private UserRepository userRepository;
    private ApplicationConfig applicationConfig;
    private PrincipalService principalService;

    public SchoolServiceImpl(SchoolRepository schoolRepository,
                             SchoolDTOResponseMapper schoolDTOResponseMapper,
                             SchoolValidator schoolValidator,
                             PrincipalRepository principalRepository,
                             UserRepository userRepository,
                             ApplicationConfig applicationConfig, PrincipalService principalService) {

        this.schoolRepository = schoolRepository;
        this.schoolDTOResponseMapper = schoolDTOResponseMapper;
        this.schoolValidator = schoolValidator;
        this.principalRepository = principalRepository;
        this.userRepository = userRepository;
        this.applicationConfig = applicationConfig;
        this.principalService = principalService;
        this.schoolConfig = SchoolConfig.getInstance();
        this.principalConfig = PrincipalConfig.getInstance();
    }

    @PostConstruct
    public void checkSchool() {
        schoolConfig.setSchoolRepository(schoolRepository);

        checkAdmin();

        checkPrincipal();
    }


    public void checkPrincipal() {
        principalConfig.setPrincipalRepository(principalRepository);
        Principal principal = principalConfig.getPrincipal();

        if (principal == null || principal.getInfo() == null) {
            School school = schoolConfig.getSchool();

            principal.setSchool(school);
            school.setPrincipal(principal);

            principalService.save(principal);
            schoolRepository.save(school);
        }
    }


    public void checkAdmin() {
        System.out.println("here2");
        if (userRepository.getAdmin().isEmpty()) {
            System.out.println("here3");

            User user = User.builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .email("Admin@Gmail.com")
                    .password(applicationConfig.passwordEncoder().encode("AdminPassword"))
                    .ssoId(123L)
                    .build();

            ServiceUtils.makeAdmin(user);

            userRepository.saveAndFlush(user);
        }
    }


    public void saveSchool(School school) {
        schoolRepository.save(school);
    }

    @Override
    public SchoolDTOResponse updateSchool(SchoolDTOUpdateRequest schoolDTOUpdateRequest) {

        School school = schoolValidator.updateSchool(schoolConfig.getSchool(), schoolDTOUpdateRequest);

        return schoolDTOResponseMapper.apply(schoolRepository.save(school));
    }
}
