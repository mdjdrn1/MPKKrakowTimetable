package com.github.mdjdrn1.MPKKrakowTimetable.test;

import com.github.mdjdrn1.MPKKrakowTimetable.CracowLine;
import com.github.mdjdrn1.MPKKrakowTimetable.ILine;
import com.github.mdjdrn1.MPKKrakowTimetable.JsonSerializer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonSerializerTest
{
    @Test
    void is283JsonValid() throws Exception
    {
        String expected = "{\"line\":{\"number\":283,\"course\":[{\"direction\":\"Skawina\",\"timetable\":[{\"description\":\"Weekday\",\"hours\":[[],[],[],[],[\"15\",\"39\"],[\"05\",\"35\"],[\"06\",\"34\"],[\"03\",\"34\"],[\"04\",\"34\"],[\"12\",\"42\"],[\"22\"],[\"24\"],[\"30\"],[\"04\",\"22\",\"38\"],[\"06\",\"36\"],[\"06\",\"38\",\"58\"],[\"38\"],[\"08\",\"39\"],[\"08\",\"38\"],[\"08\",\"35\"],[\"30\"],[\"30\"],[\"30\"],[\"30\"]]},{\"description\":\"Saturdays\",\"hours\":[[],[],[],[],[\"55\"],[\"45\"],[\"30\"],[\"15\"],[\"09\",\"39\"],[\"14\",\"44\"],[\"18\",\"54\"],[\"44\"],[\"20\",\"54\"],[\"28\"],[\"04\",\"56\"],[\"31\"],[\"11\",\"54\"],[\"24\"],[\"10\"],[\"00\",\"40\"],[\"15\",\"45\"],[\"15\",\"48\"],[\"15\"],[\"15\"]]},{\"description\":\"Holidays\",\"hours\":[[],[],[],[],[\"55\"],[\"55\"],[\"59\"],[],[\"02\"],[\"03\"],[\"05\",\"57\"],[\"51\"],[\"51\"],[\"51\"],[\"58\"],[\"58\"],[],[\"01\"],[\"02\"],[\"02\"],[\"07\"],[\"05\"],[\"02\"],[\"05\"]]}],\"stop\":[{\"id\":2,\"name\":\"Czerwone Maki P+R\",\"onDemand\":false,\"delay\":1},{\"id\":3,\"name\":\"Bunscha\",\"onDemand\":false,\"delay\":2},{\"id\":4,\"name\":\"Babińskiego\",\"onDemand\":false,\"delay\":3},{\"id\":5,\"name\":\"Skotniki\",\"onDemand\":false,\"delay\":4},{\"id\":6,\"name\":\"Baczyńskiego\",\"onDemand\":false,\"delay\":5},{\"id\":7,\"name\":\"Wrony\",\"onDemand\":false,\"delay\":7},{\"id\":8,\"name\":\"Skawina Podlipki\",\"onDemand\":false,\"delay\":9},{\"id\":9,\"name\":\"Skawina Rzepnik\",\"onDemand\":false,\"delay\":10},{\"id\":10,\"name\":\"Skawina Cmentarz\",\"onDemand\":false,\"delay\":12},{\"id\":11,\"name\":\"Skawina Popiełuszki\",\"onDemand\":false,\"delay\":13},{\"id\":12,\"name\":\"Skawina Ajka\",\"onDemand\":false,\"delay\":14}]},{\"direction\":\"Czerwone Maki P+R\",\"timetable\":[{\"description\":\"Weekday\",\"hours\":[[],[],[],[],[\"35\"],[\"00\",\"30\",\"57\"],[\"30\"],[\"00\",\"30\"],[\"00\",\"30\"],[\"00\",\"20\",\"50\"],[\"50\"],[\"50\"],[\"30\"],[\"00\",\"30\"],[\"00\",\"30\"],[\"00\",\"30\"],[\"00\",\"20\"],[\"00\",\"30\"],[\"00\",\"30\"],[\"00\",\"30\"],[\"00\",\"50\"],[\"50\"],[\"50\"],[\"20\",\"50\"]]},{\"description\":\"Saturdays\",\"hours\":[[],[],[],[],[],[\"15\"],[\"05\",\"50\"],[\"36\"],[\"31\"],[\"00\",\"35\"],[\"08\",\"50\"],[\"30\"],[\"10\",\"41\"],[\"18\"],[\"10\",\"47\"],[\"25\"],[\"12\",\"38\"],[\"25\"],[\"12\",\"40\"],[\"25\"],[\"10\",\"40\"],[\"15\",\"39\"],[\"15\",\"35\"],[\"35\"]]},{\"description\":\"Holidays\",\"hours\":[[],[],[],[],[],[\"15\"],[\"15\"],[\"20\"],[\"28\"],[\"25\"],[\"30\"],[\"18\"],[\"13\"],[\"13\"],[\"13\"],[\"20\"],[\"20\"],[\"24\"],[\"22\"],[\"22\"],[\"27\"],[\"25\"],[\"23\"],[\"25\"]]}],\"stop\":[{\"id\":14,\"name\":\"Skawina Ajka\",\"onDemand\":false,\"delay\":1},{\"id\":15,\"name\":\"Skawina Cmentarz\",\"onDemand\":false,\"delay\":3},{\"id\":16,\"name\":\"Skawina Rzepnik\",\"onDemand\":false,\"delay\":5},{\"id\":17,\"name\":\"Skawina Podlipki\",\"onDemand\":false,\"delay\":6},{\"id\":18,\"name\":\"Wrony\",\"onDemand\":false,\"delay\":8},{\"id\":19,\"name\":\"Baczyńskiego\",\"onDemand\":false,\"delay\":1420},{\"id\":20,\"name\":\"Skotniki\",\"onDemand\":false,\"delay\":1422},{\"id\":21,\"name\":\"Babińskiego\",\"onDemand\":false,\"delay\":1423},{\"id\":22,\"name\":\"Bunscha\",\"onDemand\":false,\"delay\":1424}]}]}}";

        JsonSerializer jsonSerializer = new JsonSerializer();

        ILine line = new CracowLine(283);
        String actual = jsonSerializer.serializeLine(line);

        System.out.println(actual);

        assertThat(expected).isEqualTo(actual);
    }
}
