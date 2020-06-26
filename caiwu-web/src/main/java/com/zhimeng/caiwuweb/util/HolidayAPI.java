package com.zhimeng.caiwuweb.util;

import java.io.IOException;
import java.util.List;

import com.github.agogs.holidayapi.api.APIConsumer;
import com.github.agogs.holidayapi.api.impl.HolidayAPIConsumer;
import com.github.agogs.holidayapi.model.Holiday;
import com.github.agogs.holidayapi.model.HolidayAPIResponse;
import com.github.agogs.holidayapi.model.QueryParams;

public class HolidayAPI {
    public static void main(String[] args) {
        APIConsumer consumer = new HolidayAPIConsumer("https://holidayapi.com/v1/holidays");

        //generate the wuery parameters
        QueryParams params = new QueryParams();
        params.key("a680f5e8-e1c9-4f67-9c36-34af3ae406b3").day(5)
                .month(5)
                .country(QueryParams.Country.CHINA)
                .year(2019)
                //JSON is the default format
                .format(QueryParams.Format.JSON)
                .pretty(true);

        try {
            //make the API call
            HolidayAPIResponse response = consumer.getHolidays(params);

            //check the status code of the API call
            int status = response.getStatus();
            if (status != 200) {

                //handle error scenario

            } else {

                //handle success scenario

                List<Holiday> holidays = response.getHolidays();
                System.out.println("打印数据");
                for (Holiday h : holidays) {
                    //do your thing
                    System.out.println(h.getName());
                    System.out.println(h.getIsPublic());
                    //System.out.println();
                }
            }
        } catch (IOException e) {
            //handle exception
        }
    }
}
