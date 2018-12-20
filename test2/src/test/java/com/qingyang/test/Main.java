package com.qingyang.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.qingyang.test.cglib.ConnectionImpl;
import com.qingyang.test.cglib.ConnectionInterceptor;
import com.qingyang.test.dao.AttachmentDataMapper;
import net.sf.cglib.proxy.Enhancer;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.sql.DataSource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Created by qingyang on 2018/1/22.
 */
public class Main {
    private static final String COUNT_KEY
            = Main.class.getName() + ".count";

    public enum RULES{
        TRIM,
        REPLACE,
        REPLACE_PATTERN,
        STRIP_END_UNTIL,
        STRIP_HEAD_UNTIL,
    }



    public static void main(String[] args) throws IOException{
        List<MyObj> mList = Lists.newArrayList();
        mList.add(new MyObj(1, "f"));
        mList.add(new MyObj(1, "df"));
        mList.add(new MyObj(1, "dff"));
        mList.add(new MyObj(1, "dff"));

        List<MyObj> list = ListUtil.removeDup(MyObj::getName, mList, "f");
        System.out.println(JSON.toJSONString(list));
    }

    /**
     * 大众－Bora (宝来) 1,6_2015年
     *大众－Bora (宝来)_2010年
     *大众－CC_2007年
     * @param modelName
     * @return
     */
    private static  String getRelModelName(String modelName){
        int from = modelName.indexOf("－");
        int to1 = modelName.indexOf("(");
        int to2 = modelName.indexOf("_");
        if(from < 0){
            return null;
        }
        if(to1 > 0 && to1 < to2){
            return modelName.substring(from + 1, to1).replace(" ", "");
        }else if(to1 < 0 && to2 > 0){
            return modelName.substring(from + 1, to2).replace(" ", "");
        }else if(to2 > 0 && to2 < to1){
            return modelName.substring(from + 1, to2).replace(" ", "");
        }
        return null;
    }

    public static String testTraceName(){
        return Main.class.getSimpleName() + ":" +Thread.currentThread().getStackTrace()[2].getMethodName();
    }
    private static boolean isExpire(Date updateTime){
        long startMills = updateTime.getTime();
        long now = System.currentTimeMillis();
        long interval = 30 * 24 * 60 * 60 * 1000L;
        return now - startMills > interval;
    }

    //具体调用方法;
    private static String sendDataPic(InputStream stream,String testUrl) {
        URL url = null;
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream in = null;
        String resultJson = "";
        try {
            url = new URL(testUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","multipart/form-data; boundary=Bounday---");
            connection.setRequestProperty("Cache-Control","no-cache");
            outputStream=connection.getOutputStream();
            in = stream;
            byte[] myByte = new byte[1024];
            int count = -1;
            while((count = in.read(myByte, 0, myByte.length)) != -1){
                outputStream.write(myByte,0, count);
                outputStream.flush();
            }

            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(b)) != -1) {
                byteOut.write(b, 0, read);
            }
            resultJson = new String(byteOut.toByteArray(), "UTF-8");
            in.close();
            byteOut.close();
            stream.close();
            outputStream.close();
        } catch (Exception e) {
            resultJson="";
            //记录异常日志;
        }finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    resultJson="";
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    resultJson="";
                }
            }
            if(connection != null){
                connection.disconnect();
            }
            return resultJson;
        }
    }


    private static String doClean(String str, RULES rule, String[] args, int i){
        String res = str;
        switch (rule){
            case TRIM:
                res = StringUtils.strip(str, args[i]);
                break;
            case REPLACE:
                res = StringUtils.replace(str, args[i], "");
                break;
            case REPLACE_PATTERN:
                res = StringUtils.replacePattern(str, args[i], "");
                break;
            case STRIP_END_UNTIL:
                int lpos = str.lastIndexOf(args[i]);
                if(lpos > 0){
                    res = str.substring(0, lpos + args[i].length());
                }
                break;
            case STRIP_HEAD_UNTIL:
                int pos = str.indexOf(args[i]);
                if(pos > 0){
                    res = str.substring(pos);
                }
                break;
        }
        return res;
    }

    private static String preHandleOeCode(String oeCode){
        if(StringUtils.isBlank(oeCode)){
            return null;
        }
        final String separator1 = "-";
        if(oeCode.contains(separator1)){
            String[] items =  StringUtils.split(oeCode, separator1, 2);
            oeCode = items[0];
        }
        final String separator2 = "/";
        if(oeCode.contains(separator2)){
            String[] items =  StringUtils.split(oeCode, separator2, 2);
            oeCode = items[0];
        }
        return StringUtils.replacePattern(oeCode, "[ +]", "");
    }
    public static String collectExceptionStackMsg(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String strs = sw.toString();
        return strs;
    }

    public static String getkey(MyObj obj){
        return obj.getName() + obj.getId();
    }

    public static Map<String, String> urlParser(String URL) {
        Map<String, String> mapRequest = new LinkedHashMap<String, String>();

        String[] arrSplit;

        String strUrlParam = truncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual;
            arrSplitEqual = strSplit.split("[=]");
            if (arrSplitEqual.length > 1) {
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    private static String truncateUrlPage(String strURL) {
        String   strAllParam = null;
        String[] arrSplit;

        strURL = strURL.trim().toLowerCase();

        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }



    public static void writeFile(byte[] b) throws Exception{
        String path = Main.class.getResource("/").getPath();
        File file = new File( path + "emp");
        FileOutputStream fos = new FileOutputStream(file, true);
        fos.write(b);
    }


    static String strTo16(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            sb.append("\\x");
            sb.append(Integer.toHexString(ch));
            if (i < s.length() - 1) {
                sb.append("\\x00");
            }
        }
        return sb.toString();
    }

    static class TestThread implements Runnable {
        private CountDownLatch cd;

        public TestThread(CountDownLatch cd) {
            this.cd = cd;
        }

        @Override
        public void run() {
            try {
                System.out.println("线程" + Thread.currentThread().getName() + "执行");
            } finally {
                cd.countDown();
            }

        }
    }


    void conuntDownLatch() throws Exception {
        CountDownLatch cd = new CountDownLatch(4);
        System.out.println("主线程开始");
        for (int i = 0; i < 4; i++) {
            new Thread(new TestThread(cd)).start();
        }
        cd.await();
        System.out.println("线程全部执行完成，返回主线程其他任务");
    }

    void cyclicBarrier() throws Exception {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(4, new Runnable() {
            @Override
            public void run() {
                System.out.println("各个零件全部安装完成，开始试飞");
            }
        });
        System.out.println("主线程开始");
        for (int i = 0; i < 3; i++) {
            final int j = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("零件" + j + "安装完成");
                    try {
                        cyclicBarrier.await(2, TimeUnit.SECONDS);
                    } catch (TimeoutException e) {
                        System.out.println("超时");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    System.out.println(j + "号机器上  报数据");
                }
            }).start();
        }
    }

    void springBeanRegistry(String[] args) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);
        ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) WebApplicationContextUtils.getWebApplicationContext(new ServletContext() {
            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public ServletContext getContext(String s) {
                return null;
            }

            @Override
            public int getMajorVersion() {
                return 0;
            }

            @Override
            public int getMinorVersion() {
                return 0;
            }

            @Override
            public int getEffectiveMajorVersion() {
                return 0;
            }

            @Override
            public int getEffectiveMinorVersion() {
                return 0;
            }

            @Override
            public String getMimeType(String s) {
                return null;
            }

            @Override
            public Set<String> getResourcePaths(String s) {
                return null;
            }

            @Override
            public URL getResource(String s) throws MalformedURLException {
                return null;
            }

            @Override
            public InputStream getResourceAsStream(String s) {
                return null;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            @Override
            public RequestDispatcher getNamedDispatcher(String s) {
                return null;
            }

            @Override
            public Servlet getServlet(String s) throws ServletException {
                return null;
            }

            @Override
            public Enumeration<Servlet> getServlets() {
                return null;
            }

            @Override
            public Enumeration<String> getServletNames() {
                return null;
            }

            @Override
            public void log(String s) {

            }

            @Override
            public void log(Exception e, String s) {

            }

            @Override
            public void log(String s, Throwable throwable) {

            }

            @Override
            public String getRealPath(String s) {
                return null;
            }

            @Override
            public String getServerInfo() {
                return null;
            }

            @Override
            public String getInitParameter(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getInitParameterNames() {
                return null;
            }

            @Override
            public boolean setInitParameter(String s, String s1) {
                return false;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public String getServletContextName() {
                return null;
            }

            @Override
            public ServletRegistration.Dynamic addServlet(String s, String s1) {
                return null;
            }

            @Override
            public ServletRegistration.Dynamic addServlet(String s, Servlet servlet) {
                return null;
            }

            @Override
            public ServletRegistration.Dynamic addServlet(String s, Class<? extends Servlet> aClass) {
                return null;
            }

            @Override
            public <T extends Servlet> T createServlet(Class<T> aClass) throws ServletException {
                return null;
            }

            @Override
            public ServletRegistration getServletRegistration(String s) {
                return null;
            }

            @Override
            public Map<String, ? extends ServletRegistration> getServletRegistrations() {
                return null;
            }

            @Override
            public FilterRegistration.Dynamic addFilter(String s, String s1) {
                return null;
            }

            @Override
            public FilterRegistration.Dynamic addFilter(String s, Filter filter) {
                return null;
            }

            @Override
            public FilterRegistration.Dynamic addFilter(String s, Class<? extends Filter> aClass) {
                return null;
            }

            @Override
            public <T extends Filter> T createFilter(Class<T> aClass) throws ServletException {
                return null;
            }

            @Override
            public FilterRegistration getFilterRegistration(String s) {
                return null;
            }

            @Override
            public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
                return null;
            }

            @Override
            public SessionCookieConfig getSessionCookieConfig() {
                return null;
            }

            @Override
            public void setSessionTrackingModes(Set<SessionTrackingMode> set) {

            }

            @Override
            public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
                return null;
            }

            @Override
            public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
                return null;
            }

            @Override
            public void addListener(String s) {

            }

            @Override
            public <T extends EventListener> void addListener(T t) {

            }

            @Override
            public void addListener(Class<? extends EventListener> aClass) {

            }

            @Override
            public <T extends EventListener> T createListener(Class<T> aClass) throws ServletException {
                return null;
            }

            @Override
            public JspConfigDescriptor getJspConfigDescriptor() {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }

            @Override
            public void declareRoles(String... strings) {

            }

            @Override
            public String getVirtualServerName() {
                return null;
            }
        });
        DefaultListableBeanFactory beanFactory = ((DefaultListableBeanFactory) ctx.getBeanFactory());
        builder.addPropertyValue("driver", args[1]);
        builder.addPropertyValue("url", args[2]);
        builder.addPropertyValue("password", args[3]);
        builder.addPropertyValue("username", args[4]);
        beanFactory.registerBeanDefinition("hyDatasource", builder.getBeanDefinition());
        SecureRandom random = new SecureRandom();

    }

    void cglib() {
        ConnectionImpl connection = new ConnectionImpl();
        ConnectionImpl connProxy = (ConnectionImpl) Enhancer.create(ConnectionImpl.class, new ConnectionInterceptor(connection));
        connProxy.connect();
    }

    static void  mybatis() throws Exception{
        System.out.println(Main.class.getClassLoader().getResource("").getPath());
        SqlSessionFactoryBuilder sb = new SqlSessionFactoryBuilder();
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream("mybatis-config.xml");

        SqlSessionFactory ssf = sb.build(inputStream);
        Properties properties = new Properties();
        properties.setProperty("driverClassName", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        properties.setProperty("url", "jdbc:sqlserver://192.168.1.79:1433;DatabaseName=Hynet");
        properties.setProperty("username", "sa");
        properties.setProperty("password", "xiaoka123%");
        DataSource ds;
        try {
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw e;
        }
        TransactionFactory tf = new JdbcTransactionFactory();
        Environment env = new Environment.Builder("erp").dataSource(ds).transactionFactory(tf).build();
        ssf.getConfiguration().setEnvironment(env);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("beginDate", new Date());
        map.put("endDate", new Date());
        map.put("code", "s100");
        AttachmentDataMapper mapper = ssf.openSession().getMapper(AttachmentDataMapper.class);
        Integer a = mapper.getLoginCount(map);
        System.out.println(a);
    }



}
