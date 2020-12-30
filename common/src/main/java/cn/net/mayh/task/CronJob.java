package cn.net.mayh.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by itw_liuzhou02 on 2018/12/5.
 * 定时任务
 */
@Slf4j
@Component
public class CronJob {





    @Scheduled(cron = "0 0 1 1 1,4,7,10 ?")
    public void generateReportList() {

    }

    /**
     * <li>创建人：itw_songkai01
     * <li>创建时间：2019/2/17 15:13
     * <li>创建目的：【定时查询交易日志的状态(每分钟查询一次)】
     * <li>修改目的：【修改人：修改目的，修改时间】
     * <li>参数列表：[]
     * <li>返回值类型：void
     **/
    @Scheduled(cron = "0 */1 * * * ?")
    public void queryLogStatus() {

    }

}
