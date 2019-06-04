package com.simplicity.authserver.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.catalina.connector.Request;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.web.savedrequest.Enumerator;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Order(value = Integer.MIN_VALUE)

public class JsonToUrlEncodedAuthenticationFilter implements Filter {

    private final ObjectMapper mapper;

    public JsonToUrlEncodedAuthenticationFilter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        Field f = request.getClass().getDeclaredField("request");
        f.setAccessible(true);
        Request realRequest = (Request) f.get(request);

       //Request content type without spaces (inner spaces matter)
       //trim deletes spaces only at the beginning and at the end of the string
        String contentType = realRequest.getContentType().toLowerCase().chars()
                .mapToObj(c -> String.valueOf((char) c))
                .filter(x->!x.equals(" "))
                .collect(Collectors.joining());

        if ((contentType.equals(MediaType.APPLICATION_JSON_UTF8_VALUE.toLowerCase())||
                contentType.equals(MediaType.APPLICATION_JSON_VALUE.toLowerCase()))
                        && Objects.equals((realRequest).getServletPath(), "/oauth/token")) {

            InputStream is = realRequest.getInputStream();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is), 16384)) {
                String json = br.lines()
                        .collect(Collectors.joining(System.lineSeparator()));
                HashMap<String, String> result = mapper.readValue(json, HashMap.class);
                HashMap<String, String[]> r = new HashMap<>();

                for (String key : result.keySet()) {
                    String[] val = new String[1];
                    val[0] = result.get(key);
                    r.put(key, val);
                }
                String[] val = new String[1];
                val[0] = (realRequest).getMethod();
                r.put("_method", val);

                HttpServletRequest s = new MyServletRequestWrapper(((HttpServletRequest) request), r);
                chain.doFilter(s, response);
            }

        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    class MyServletRequestWrapper extends HttpServletRequestWrapper {
        private final HashMap<String, String[]> params;

        MyServletRequestWrapper(HttpServletRequest request, HashMap<String, String[]> params) {
            super(request);
            this.params = params;
        }

        @Override
        public String getParameter(String name) {
            if (this.params.containsKey(name)) {
                return this.params.get(name)[0];
            }
            return "";
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return this.params;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return new Enumerator<>(params.keySet());
        }

        @Override
        public String[] getParameterValues(String name) {
            return params.get(name);
        }
    }

//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Data
//    class JsonToHashMapConverter  {
//
//        private String source;
//        private final ObjectMapper jsonMapper = new ObjectMapper();
//
//        public Object convert(String source) throws IOException {
//            return jsonMapper.readValue(source, LinkedHashMap.class);
//        }
//    }
}
