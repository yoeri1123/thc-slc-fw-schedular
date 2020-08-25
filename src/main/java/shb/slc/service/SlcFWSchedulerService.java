package shb.slc.service;


import kong.unirest.Unirest;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import kong.unirest.HttpResponse;
import shb.slc.amqp.RabbitmqProducer;
import shb.slc.domain.SlcFWSchedularActvLogDomainQ;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Component
@Slf4j
public class SlcFWSchedulerService {

    Logger logger= LoggerFactory.getLogger(SlcFWSchedulerService.class);

    @Autowired
    RabbitmqProducer rabbitmqProducer;

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
        logger.info("=======================================================================");
        logger.info("===============================Schdulering=============================");
        logger.info("=======================================================================");
        // Print prepost actv log
        callPrepostBatch(batchTime);
        callSwinfoBatch(batchTime);
        callSwuseBatch(batchTime);

    }

    public void callPrepostBatch(String batchTime){
        // Once a day batch
        SlcFWSchedularActvLogDomainQ domainQ=new SlcFWSchedularActvLogDomainQ(batchTime, "PrepostService");
        try{
            domainQ.setSchDescription("call Prepost Schedular Start");
            rabbitmqProducer.schedularActvLogDomainPublisher(domainQ);

            logger.info("call Prepost Schedular Start");

            HttpResponse httpResponse=(HttpResponse)Unirest.get(prepostUrl)
                    .queryString("batchdate", batchTime).asString();

            domainQ.setSchDescription(httpResponse.getBody().toString());
            rabbitmqProducer.schedularActvLogDomainPublisher(domainQ);

            logger.info(httpResponse.getBody().toString());

        }catch(Exception e){
            domainQ.setSchDescription(e.toString());
            rabbitmqProducer.schedularActvLogDomainPublisher(domainQ);

            logger.info(e.toString());
        }
    }

    public void callSwinfoBatch(String batchTime){
        // Once a day batch
        try{
            logger.info("call Swinfo Schedular Start");
            HttpResponse httpResponse=(HttpResponse)Unirest.get(swinfoUrl)
                    .queryString("batchdate", batchTime).asString();
            logger.info(httpResponse.getBody().toString());
        }catch(Exception e){
            logger.info(e.toString());
        }
    }

    public void callSwuseBatch(String batchTime){
        // Once a day batch
        try{
            logger.info("call Swuse Schedular Start");
            HttpResponse httpResponse=(HttpResponse)Unirest.get(swuseUrl)
                    .queryString("batchdate", batchTime).asString();
            logger.info(httpResponse.getBody().toString());
        }catch(Exception e){
            logger.info(e.toString());
        }
    }


}
