package shb.slc.service;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class SlcFWSchedulerService {

    @Value("${prepost.url}")
    private String prepostUrl;

    @Value("${swinfo.url}")
    private String swinfoUrl;

    @Value("${swuse.url}")
    private String swuseUrl;


    @Scheduled(cron = "${schedular.period}")
    public void SlcTotalschedular(){
        callPrepostAPI();
        callSwinfoAPI();

    }

    public void callPrepostAPI(){
        // 하루에 한번
        try{
            HttpResponse httpResponse= (HttpResponse) Unirest.get(prepostUrl);
        }catch(Exception e){

        }
    }
    public void callSwinfoAPI(){
        // 하루에 한번
        try{
            HttpResponse httpResponse= (HttpResponse) Unirest.get(swinfoUrl);
        }catch(Exception e){

        }
    }
    public void callSwuseApi(){
        // 하루에 한번
        try{
            HttpResponse httpResponse= (HttpResponse) Unirest.get(swuseUrl);
        }catch(Exception e){

        }
    }


}
