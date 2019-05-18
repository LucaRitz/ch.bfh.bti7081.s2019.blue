package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.service.EmployeeMissionSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(EmployeeResource.PATH + "/misssions")
public class EmployeeMissionSubResource implements EmployeeMissionSubService {

    private final Mapper mapper;
    private final MissionRepository missionRepository;

    @Autowired
    public EmployeeMissionSubResource(Mapper mapper, MissionRepository missionRepository) {
        this.mapper = mapper;
        this.missionRepository = missionRepository;
    }

    @GetMapping(path = "/{employeeId}", produces = MediaType.APPLICATION_JSON)
    @Override
    public List<MissionDto> find(@PathVariable Integer employeeId,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        List<Mission> missions = missionRepository.findByHealthVisitorAndIntersectingDateRange(employeeId, start, end);
        return mapper.map(missions, MissionDto.class);
    }

}
