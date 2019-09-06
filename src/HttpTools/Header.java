package HttpTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Header {

    private final String name;
    private final List<String> values;

    @Override
    public boolean equals(Object obj) {

        if(obj.getClass().equals(Header.class)) {
            Header h2 = (Header) obj;
            return name.equals(h2.name) && values.containsAll(h2.values) && (values.size() == h2.values.size());
        }
        else {
            return false;
        }
    }

    public Header(String name, List<String> values) {
        this.name = name;
        this.values = values;

    }

    public Header(String name, String[] values) {
        this.name = name;
        this.values = Arrays.asList(values);
    }

    public Header(String name, String value) {
        this.name = name;
        List<String> list = new ArrayList<>();
        list.add(value);
        this.values = list;
    }

    static List<Header> getHeaders(Map<String,List<String>> headerMap) {

        List<Header> list = new ArrayList<>();

        for (String key:headerMap.keySet()) {
            Header h = new Header(key,headerMap.get(key));
            list.add(h);
        }

        return list;
    }

    public String getName() {
        return name;
    }

    public List<String> getValues() {
        return values;
    }
}
