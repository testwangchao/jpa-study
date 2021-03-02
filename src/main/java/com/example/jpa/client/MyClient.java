package com.example.jpa.client;

import com.dtflys.forest.annotation.Request;

import java.util.Map;

public interface MyClient {

    @Request(url = "https://adservice.sigmob.cn/extconfig?appId=2396&sdkVersion=2.25.4",
            type = "GET",
            dataType = "json"
    )
    Map<String,Object> helloForest();

}
