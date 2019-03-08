package com.gd.controller.common;

import com.gd.domain.SseData;
import com.gd.util.TimeUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections.set.SynchronizedSet;
import org.apache.xmlbeans.impl.common.ConcurrentReaderHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Controller
@RequestMapping("sse")
public class SSEControler {

    @Autowired(required = false)
    @Qualifier("sseBlockingQueue")
    private BlockingQueue<SseData> sseBlockingQueue;

    public class CusSseEmitter extends SseEmitter {
        public String getClientFlag() {
            return clientFlag;
        }

        public void setClientFlag(String clientFlag) {
            this.clientFlag = clientFlag;
        }

        private String clientFlag;
    }

    private Set<CusSseEmitter> sseEmitters = new HashSet<>();
    private volatile Set<CusSseEmitter> synchronizedSet = Collections.synchronizedSet(sseEmitters);

    /**
     * 订阅消息
     *
     * @param principal
     * @return
     */
    @RequestMapping("/subscribe")
    public SseEmitter SubscribeNotice(Principal principal) {
        String username = principal.getName();
        CusSseEmitter sseEmitter = new CusSseEmitter();
        sseEmitter.setClientFlag(username);
        synchronizedSet.add(sseEmitter);

        //pushData();

        sseEmitter.onCompletion(() -> {
            System.out.println(sseEmitter.hashCode() + "...onCompletion");
            synchronizedSet.remove(sseEmitter);
        });
        sseEmitter.onTimeout(() -> {
            System.out.println(sseEmitter.hashCode() + "...Timeout");
            sseEmitter.complete();
        });
        return sseEmitter;
    }

    @Async
    public void pushData() {
        System.out.println("thread id" + Thread.currentThread().getId());
        while (sseBlockingQueue.size() > 0) {
            //Gson gson = new GsonBuilder().setDateFormat(TimeUtils.getNormalFormat()).create();
            SseData pushdata = sseBlockingQueue.poll();
            //String s = gson.toJson(pushdata);
            if (pushdata != null && synchronizedSet.size() > 0) {
                for (CusSseEmitter cusSseEmitter : synchronizedSet) {
                    if (pushdata.getClientflag().equals(cusSseEmitter.getClientFlag())) {
                        try {
                            cusSseEmitter.send(pushdata);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
