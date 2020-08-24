package shb.slc.service;


import kong.unirest.Unirest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import kong.unirest.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Component
@Slf4j
public class SlcFWSchedulerService {

    @Value("${prepost.url}")
    private String prepostUrl;

    @Value("${swinfo.url}")
    private String swinfoUrl;

    @Value("${swuse.url}")
    private String swuseUrl;

    SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");


    @Scheduled(cron = "${schedular.period}")
    public void SlcTotalschedular(){
        Date time = new Date();
        String batchTime = format.format(time);
        log.info("=======================================================================");
        log.info("===============================Schdulering=============================");
        log.info("=======================================================================");
        callPrepostAPI(batchTime);
        callSwinfoAPI(batchTime);
        callSwuseApi(batchTime);

    }

    public void callPrepostAPI(String batchTime){
        // 하루에 한번
        try{
            log.info("call Prepost Schedular Start");
            HttpResponse httpResponse=(HttpResponse)Unirest.get(prepostUrl)
                    .queryString("batchdate", batchTime).asString();
            log.info(httpResponse.getBody().toString());
        }catch(Exception e){
            log.info(e.toString());
        }
    }
    public void callSwinfoAPI(String batchTime){
        // 하루에 한번
        try{
            log.info("call Swinfo Schedular Start");
            HttpResponse httpResponse=(HttpResponse)Unirest.get(swinfoUrl)
                    .queryString("batchdate", batchTime).asString();
            log.info(httpResponse.getBody().toString());
        }catch(Exception e){
            log.info(e.toString());
        }
    }
    public void callSwuseApi(String batchTime){
        // 하루에 한번
        try{
            log.info("call Swuse Schedular Start");
            HttpResponse httpResponse=(HttpResponse)Unirest.get(swuseUrl)
                    .queryString("batchdate", batchTime).asString();
            log.info(httpResponse.getBody().toString());
        }catch(Exception e){
            log.info(e.toString());
        }
    }


}
