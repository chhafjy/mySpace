package com.caf.yeb.common.util.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author chenhaohao
 * @version 1.0
 * @date 2022/1/18 15:48
 */
@Slf4j
@Component
public class AvatarHelper {

    public String getDefaultAvatar() {
        List<String> list = new ArrayList<>();
        list.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_match%2F0%2F11630838341%2F0.jpg&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645084475&t=f85618cb1b3c8fe34b6aef7944cc3292");
        list.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F993ea15070a53bef495d64915c8dc5506c98824f.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645084595&t=815e51ee5ca2f8176a978be64ff289db");
        list.add("https://img1.baidu.com/it/u=2825714906,1616764684&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
        list.add("https://img2.baidu.com/it/u=497340828,56639319&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
        list.add("https://img1.baidu.com/it/u=2114684366,854047881&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
        list.add("https://img2.baidu.com/it/u=3774031207,3089103779&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400");
        list.add("https://img2.baidu.com/it/u=1058145678,2024811180&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
        list.add("https://img1.baidu.com/it/u=328262859,819651629&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
        list.add("https://img1.baidu.com/it/u=2782534764,3247176940&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
        list.add("https://img2.baidu.com/it/u=4072576812,863684918&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=800");
        Integer random = (int)(Math.random() * 10);
        log.info("random is {}", random);
        return list.get(random);
    }
}
