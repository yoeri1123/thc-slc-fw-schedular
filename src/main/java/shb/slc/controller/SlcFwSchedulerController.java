package shb.slc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shb.slc.service.SlcFWSchedulerService;

@RestController
@RequestMapping("/fw-schedular/v1")
public class SlcFwSchedulerController {

    SlcFWSchedulerService slcFWSchedulerService;

    @GetMapping("/schedular")
    public void retrySchedular(@RequestParam (value="retry_date") String retryDate){
        // web 에서 재처리 왔을 경우, schedular 에서 실패한 날짜를 기반으로 배치를 돌리게 해야 하는지 궁금.
        slcFWSchedulerService.SlcTotalschedular();
    }

}
