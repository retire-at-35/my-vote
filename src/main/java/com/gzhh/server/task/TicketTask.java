package com.gzhh.server.task;

import com.gzhh.common.context.BaseContext;
import com.gzhh.server.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class TicketTask {
    @Autowired
    private UserMapper userMapper;
    //每天晚上12点给每一个用户的number+3
    @Transactional
    //@Scheduled(cron = "15 * * * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
    public void ticketTask() {
        log.info("票数+3");
        userMapper.addNumber();
    }

}
