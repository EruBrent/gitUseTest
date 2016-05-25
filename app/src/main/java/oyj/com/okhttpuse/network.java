package oyj.com.okhttpuse;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by oyj
 * 作用：
 */

public class network
{
    OkHttpClient client = new OkHttpClient();
    String result;
    Gson gson =new Gson();
    Weather weather = new Weather();

    public  HeWeatherdataserviceBean data = new HeWeatherdataserviceBean();
    /**
     * aqi : {"city":{"aqi":"54","co":"0","no2":"24","o3":"130","pm10":"54","pm25":"33","qlty":"良","so2":"4"}}
     * basic : {"city":"北京","cnty":"中国","id":"CN101010100","lat":"39.904000","lon":"116.391000","update":{"loc":"2016-05-24 20:51","utc":"2016-05-24 12:51"}}
     * daily_forecast : [{"astro":{"sr":"04:51","ss":"19:30"},"cond":{"code_d":"100","code_n":"300","txt_d":"晴","txt_n":"阵雨"},"date":"2016-05-24","hum":"12","pcpn":"0.0","pop":"39","pres":"1001","tmp":{"max":"33","min":"17"},"vis":"10","wind":{"deg":"286","dir":"北风","sc":"3-4","spd":"13"}},{"astro":{"sr":"04:51","ss":"19:31"},"cond":{"code_d":"101","code_n":"100","txt_d":"多云","txt_n":"晴"},"date":"2016-05-25","hum":"12","pcpn":"1.1","pop":"62","pres":"1002","tmp":{"max":"30","min":"18"},"vis":"10","wind":{"deg":"304","dir":"北风","sc":"4-5","spd":"23"}},{"astro":{"sr":"04:50","ss":"19:32"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-05-26","hum":"10","pcpn":"0.0","pop":"0","pres":"1010","tmp":{"max":"30","min":"16"},"vis":"10","wind":{"deg":"317","dir":"无持续风向","sc":"微风","spd":"10"}},{"astro":{"sr":"04:50","ss":"19:33"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-05-27","hum":"10","pcpn":"0.0","pop":"0","pres":"1006","tmp":{"max":"32","min":"19"},"vis":"10","wind":{"deg":"209","dir":"无持续风向","sc":"微风","spd":"0"}},{"astro":{"sr":"04:49","ss":"19:33"},"cond":{"code_d":"101","code_n":"104","txt_d":"多云","txt_n":"阴"},"date":"2016-05-28","hum":"11","pcpn":"0.1","pop":"0","pres":"1001","tmp":{"max":"30","min":"19"},"vis":"10","wind":{"deg":"193","dir":"无持续风向","sc":"微风","spd":"4"}},{"astro":{"sr":"04:49","ss":"19:34"},"cond":{"code_d":"104","code_n":"101","txt_d":"阴","txt_n":"多云"},"date":"2016-05-29","hum":"9","pcpn":"0.3","pop":"14","pres":"1005","tmp":{"max":"30","min":"19"},"vis":"10","wind":{"deg":"310","dir":"无持续风向","sc":"微风","spd":"9"}},{"astro":{"sr":"04:48","ss":"19:35"},"cond":{"code_d":"100","code_n":"101","txt_d":"晴","txt_n":"多云"},"date":"2016-05-30","hum":"7","pcpn":"0.0","pop":"2","pres":"1003","tmp":{"max":"31","min":"20"},"vis":"10","wind":{"deg":"306","dir":"无持续风向","sc":"微风","spd":"10"}}]
     * hourly_forecast : [{"date":"2016-05-24 22:00","hum":"25","pop":"26","pres":"1001","tmp":"27","wind":{"deg":"294","dir":"西北风","sc":"微风","spd":"9"}}]
     * now : {"cond":{"code":"101","txt":"多云"},"fl":"25","hum":"22","pcpn":"0","pres":"1000","tmp":"25","vis":"10","wind":{"deg":"220","dir":"西南风","sc":"5-6","spd":"25"}}
     * status : ok
     * suggestion : {"comf":{"brf":"较舒适","txt":"白天天气晴好，您在这种天气条件下，会感觉早晚凉爽、舒适，午后偏热。"},"cw":{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},"drsg":{"brf":"热","txt":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。"},"flu":{"brf":"少发","txt":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"},"sport":{"brf":"较适宜","txt":"天气较好，但由于风力较大，推荐您在室内进行低强度运动，若在户外运动请注意避风。"},"trav":{"brf":"适宜","txt":"天气较好，温度稍高，幸好风稍大，会缓解稍热的天气。适宜旅游，可不要错过机会呦！"},"uv":{"brf":"中等","txt":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"}}
     */

    private List<HeWeatherdataserviceBean> HeWeatherdataservice;

    public network(String cityID){
        getData(cityID);
    }



    public void getData(String cityID)
    {
        final Request request = new Request.Builder()
                              .url("https://api.heweather.com/x3/weather?cityid=CN" +cityID+
                                  "&key=088b2162b2d44596b9f22a458a8e41e3 ")
                               .build();
         client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {}

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                StringBuilder sb = new StringBuilder(response.body().string());
                sb.deleteCharAt(11);
                sb.deleteCharAt(15);
                sb.delete(22,26);
                result = sb.toString();
                Type type = new TypeToken<List<HeWeatherdataserviceBean>>(){}.getType();
             weather = gson.fromJson(result,Weather.class);

                //String co =   data.getAqi().getCity().getCo();
                Log.d("TAF",result);
            }
        });

    }

    public List<HeWeatherdataserviceBean> getHeWeatherdataservice() { return HeWeatherdataservice;}

    public void setHeWeatherdataservice(List<HeWeatherdataserviceBean> HeWeatherdataservice) { this.HeWeatherdataservice = HeWeatherdataservice;}

    public static class HeWeatherdataserviceBean
    {
        /**
         * city : {"aqi":"54","co":"0","no2":"24","o3":"130","pm10":"54","pm25":"33","qlty":"良","so2":"4"}
         */

        private AqiBean aqi;
        /**
         * city : 北京
         * cnty : 中国
         * id : CN101010100
         * lat : 39.904000
         * lon : 116.391000
         * update : {"loc":"2016-05-24 20:51","utc":"2016-05-24 12:51"}
         */

        private BasicBean basic;
        /**
         * cond : {"code":"101","txt":"多云"}
         * fl : 25
         * hum : 22
         * pcpn : 0
         * pres : 1000
         * tmp : 25
         * vis : 10
         * wind : {"deg":"220","dir":"西南风","sc":"5-6","spd":"25"}
         */

        private NowBean now;
        private String status;
        /**
         * comf : {"brf":"较舒适","txt":"白天天气晴好，您在这种天气条件下，会感觉早晚凉爽、舒适，午后偏热。"}
         * cw : {"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"}
         * drsg : {"brf":"热","txt":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。"}
         * flu : {"brf":"少发","txt":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"}
         * sport : {"brf":"较适宜","txt":"天气较好，但由于风力较大，推荐您在室内进行低强度运动，若在户外运动请注意避风。"}
         * trav : {"brf":"适宜","txt":"天气较好，温度稍高，幸好风稍大，会缓解稍热的天气。适宜旅游，可不要错过机会呦！"}
         * uv : {"brf":"中等","txt":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"}
         */

        private SuggestionBean suggestion;
        /**
         * astro : {"sr":"04:51","ss":"19:30"}
         * cond : {"code_d":"100","code_n":"300","txt_d":"晴","txt_n":"阵雨"}
         * date : 2016-05-24
         * hum : 12
         * pcpn : 0.0
         * pop : 39
         * pres : 1001
         * tmp : {"max":"33","min":"17"}
         * vis : 10
         * wind : {"deg":"286","dir":"北风","sc":"3-4","spd":"13"}
         */

        private List<DailyForecastBean> daily_forecast;
        /**
         * date : 2016-05-24 22:00
         * hum : 25
         * pop : 26
         * pres : 1001
         * tmp : 27
         * wind : {"deg":"294","dir":"西北风","sc":"微风","spd":"9"}
         */

        private List<HourlyForecastBean> hourly_forecast;

        public static HeWeatherdataserviceBean objectFromData(String str)
        {

            return new Gson().fromJson(str, HeWeatherdataserviceBean.class);
        }

        public AqiBean getAqi() { return aqi;}

        public void setAqi(AqiBean aqi) { this.aqi = aqi;}

        public BasicBean getBasic() { return basic;}

        public void setBasic(BasicBean basic) { this.basic = basic;}

        public NowBean getNow() { return now;}

        public void setNow(NowBean now) { this.now = now;}

        public String getStatus() { return status;}

        public void setStatus(String status) { this.status = status;}

        public SuggestionBean getSuggestion() { return suggestion;}

        public void setSuggestion(SuggestionBean suggestion) { this.suggestion = suggestion;}

        public List<DailyForecastBean> getDaily_forecast() { return daily_forecast;}

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) { this.daily_forecast = daily_forecast;}

        public List<HourlyForecastBean> getHourly_forecast() { return hourly_forecast;}

        public void setHourly_forecast(List<HourlyForecastBean> hourly_forecast) { this.hourly_forecast = hourly_forecast;}

        public static class AqiBean
        {
            /**
             * aqi : 54
             * co : 0
             * no2 : 24
             * o3 : 130
             * pm10 : 54
             * pm25 : 33
             * qlty : 良
             * so2 : 4
             */

            private CityBean city;

            public static AqiBean objectFromData(String str)
            {

                return new Gson().fromJson(str, AqiBean.class);
            }

            public CityBean getCity() { return city;}

            public void setCity(CityBean city) { this.city = city;}

            public static class CityBean
            {
                private String aqi;
                private String co;
                private String no2;
                private String o3;
                private String pm10;
                private String pm25;
                private String qlty;
                private String so2;

                public static CityBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, CityBean.class);
                }

                public String getAqi() { return aqi;}

                public void setAqi(String aqi) { this.aqi = aqi;}

                public String getCo() { return co;}

                public void setCo(String co) { this.co = co;}

                public String getNo2() { return no2;}

                public void setNo2(String no2) { this.no2 = no2;}

                public String getO3() { return o3;}

                public void setO3(String o3) { this.o3 = o3;}

                public String getPm10() { return pm10;}

                public void setPm10(String pm10) { this.pm10 = pm10;}

                public String getPm25() { return pm25;}

                public void setPm25(String pm25) { this.pm25 = pm25;}

                public String getQlty() { return qlty;}

                public void setQlty(String qlty) { this.qlty = qlty;}

                public String getSo2() { return so2;}

                public void setSo2(String so2) { this.so2 = so2;}
            }
        }

        public static class BasicBean
        {
            private String city;
            private String cnty;
            private String id;
            private String lat;
            private String lon;
            /**
             * loc : 2016-05-24 20:51
             * utc : 2016-05-24 12:51
             */

            private UpdateBean update;

            public static BasicBean objectFromData(String str)
            {

                return new Gson().fromJson(str, BasicBean.class);
            }

            public String getCity() { return city;}

            public void setCity(String city) { this.city = city;}

            public String getCnty() { return cnty;}

            public void setCnty(String cnty) { this.cnty = cnty;}

            public String getId() { return id;}

            public void setId(String id) { this.id = id;}

            public String getLat() { return lat;}

            public void setLat(String lat) { this.lat = lat;}

            public String getLon() { return lon;}

            public void setLon(String lon) { this.lon = lon;}

            public UpdateBean getUpdate() { return update;}

            public void setUpdate(UpdateBean update) { this.update = update;}

            public static class UpdateBean
            {
                private String loc;
                private String utc;

                public static UpdateBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, UpdateBean.class);
                }

                public String getLoc() { return loc;}

                public void setLoc(String loc) { this.loc = loc;}

                public String getUtc() { return utc;}

                public void setUtc(String utc) { this.utc = utc;}
            }
        }

        public static class NowBean
        {
            /**
             * code : 101
             * txt : 多云
             */

            private CondBean cond;
            private String fl;
            private String hum;
            private String pcpn;
            private String pres;
            private String tmp;
            private String vis;
            /**
             * deg : 220
             * dir : 西南风
             * sc : 5-6
             * spd : 25
             */

            private WindBean wind;

            public static NowBean objectFromData(String str)
            {

                return new Gson().fromJson(str, NowBean.class);
            }

            public CondBean getCond() { return cond;}

            public void setCond(CondBean cond) { this.cond = cond;}

            public String getFl() { return fl;}

            public void setFl(String fl) { this.fl = fl;}

            public String getHum() { return hum;}

            public void setHum(String hum) { this.hum = hum;}

            public String getPcpn() { return pcpn;}

            public void setPcpn(String pcpn) { this.pcpn = pcpn;}

            public String getPres() { return pres;}

            public void setPres(String pres) { this.pres = pres;}

            public String getTmp() { return tmp;}

            public void setTmp(String tmp) { this.tmp = tmp;}

            public String getVis() { return vis;}

            public void setVis(String vis) { this.vis = vis;}

            public WindBean getWind() { return wind;}

            public void setWind(WindBean wind) { this.wind = wind;}

            public static class CondBean
            {
                private String code;
                private String txt;

                public static CondBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, CondBean.class);
                }

                public String getCode() { return code;}

                public void setCode(String code) { this.code = code;}

                public String getTxt() { return txt;}

                public void setTxt(String txt) { this.txt = txt;}
            }

            public static class WindBean
            {
                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public static WindBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, WindBean.class);
                }

                public String getDeg() { return deg;}

                public void setDeg(String deg) { this.deg = deg;}

                public String getDir() { return dir;}

                public void setDir(String dir) { this.dir = dir;}

                public String getSc() { return sc;}

                public void setSc(String sc) { this.sc = sc;}

                public String getSpd() { return spd;}

                public void setSpd(String spd) { this.spd = spd;}
            }
        }

        public static class SuggestionBean
        {
            /**
             * brf : 较舒适
             * txt : 白天天气晴好，您在这种天气条件下，会感觉早晚凉爽、舒适，午后偏热。
             */

            private ComfBean comf;
            /**
             * brf : 不宜
             * txt : 不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。
             */

            private CwBean cw;
            /**
             * brf : 热
             * txt : 天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。
             */

            private DrsgBean drsg;
            /**
             * brf : 少发
             * txt : 各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。
             */

            private FluBean flu;
            /**
             * brf : 较适宜
             * txt : 天气较好，但由于风力较大，推荐您在室内进行低强度运动，若在户外运动请注意避风。
             */

            private SportBean sport;
            /**
             * brf : 适宜
             * txt : 天气较好，温度稍高，幸好风稍大，会缓解稍热的天气。适宜旅游，可不要错过机会呦！
             */

            private TravBean trav;
            /**
             * brf : 中等
             * txt : 属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。
             */

            private UvBean uv;

            public static SuggestionBean objectFromData(String str)
            {

                return new Gson().fromJson(str, SuggestionBean.class);
            }

            public ComfBean getComf() { return comf;}

            public void setComf(ComfBean comf) { this.comf = comf;}

            public CwBean getCw() { return cw;}

            public void setCw(CwBean cw) { this.cw = cw;}

            public DrsgBean getDrsg() { return drsg;}

            public void setDrsg(DrsgBean drsg) { this.drsg = drsg;}

            public FluBean getFlu() { return flu;}

            public void setFlu(FluBean flu) { this.flu = flu;}

            public SportBean getSport() { return sport;}

            public void setSport(SportBean sport) { this.sport = sport;}

            public TravBean getTrav() { return trav;}

            public void setTrav(TravBean trav) { this.trav = trav;}

            public UvBean getUv() { return uv;}

            public void setUv(UvBean uv) { this.uv = uv;}

            public static class ComfBean
            {
                private String brf;
                private String txt;

                public static ComfBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, ComfBean.class);
                }

                public String getBrf() { return brf;}

                public void setBrf(String brf) { this.brf = brf;}

                public String getTxt() { return txt;}

                public void setTxt(String txt) { this.txt = txt;}
            }

            public static class CwBean
            {
                private String brf;
                private String txt;

                public static CwBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, CwBean.class);
                }

                public String getBrf() { return brf;}

                public void setBrf(String brf) { this.brf = brf;}

                public String getTxt() { return txt;}

                public void setTxt(String txt) { this.txt = txt;}
            }

            public static class DrsgBean
            {
                private String brf;
                private String txt;

                public static DrsgBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, DrsgBean.class);
                }

                public String getBrf() { return brf;}

                public void setBrf(String brf) { this.brf = brf;}

                public String getTxt() { return txt;}

                public void setTxt(String txt) { this.txt = txt;}
            }

            public static class FluBean
            {
                private String brf;
                private String txt;

                public static FluBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, FluBean.class);
                }

                public String getBrf() { return brf;}

                public void setBrf(String brf) { this.brf = brf;}

                public String getTxt() { return txt;}

                public void setTxt(String txt) { this.txt = txt;}
            }

            public static class SportBean
            {
                private String brf;
                private String txt;

                public static SportBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, SportBean.class);
                }

                public String getBrf() { return brf;}

                public void setBrf(String brf) { this.brf = brf;}

                public String getTxt() { return txt;}

                public void setTxt(String txt) { this.txt = txt;}
            }

            public static class TravBean
            {
                private String brf;
                private String txt;

                public static TravBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, TravBean.class);
                }

                public String getBrf() { return brf;}

                public void setBrf(String brf) { this.brf = brf;}

                public String getTxt() { return txt;}

                public void setTxt(String txt) { this.txt = txt;}
            }

            public static class UvBean
            {
                private String brf;
                private String txt;

                public static UvBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, UvBean.class);
                }

                public String getBrf() { return brf;}

                public void setBrf(String brf) { this.brf = brf;}

                public String getTxt() { return txt;}

                public void setTxt(String txt) { this.txt = txt;}
            }
        }

        public static class DailyForecastBean
        {
            /**
             * sr : 04:51
             * ss : 19:30
             */

            private AstroBean astro;
            /**
             * code_d : 100
             * code_n : 300
             * txt_d : 晴
             * txt_n : 阵雨
             */

            private CondBean cond;
            private String date;
            private String hum;
            private String pcpn;
            private String pop;
            private String pres;
            /**
             * max : 33
             * min : 17
             */

            private TmpBean tmp;
            private String vis;
            /**
             * deg : 286
             * dir : 北风
             * sc : 3-4
             * spd : 13
             */

            private WindBean wind;

            public static DailyForecastBean objectFromData(String str)
            {

                return new Gson().fromJson(str, DailyForecastBean.class);
            }

            public AstroBean getAstro() { return astro;}

            public void setAstro(AstroBean astro) { this.astro = astro;}

            public CondBean getCond() { return cond;}

            public void setCond(CondBean cond) { this.cond = cond;}

            public String getDate() { return date;}

            public void setDate(String date) { this.date = date;}

            public String getHum() { return hum;}

            public void setHum(String hum) { this.hum = hum;}

            public String getPcpn() { return pcpn;}

            public void setPcpn(String pcpn) { this.pcpn = pcpn;}

            public String getPop() { return pop;}

            public void setPop(String pop) { this.pop = pop;}

            public String getPres() { return pres;}

            public void setPres(String pres) { this.pres = pres;}

            public TmpBean getTmp() { return tmp;}

            public void setTmp(TmpBean tmp) { this.tmp = tmp;}

            public String getVis() { return vis;}

            public void setVis(String vis) { this.vis = vis;}

            public WindBean getWind() { return wind;}

            public void setWind(WindBean wind) { this.wind = wind;}

            public static class AstroBean
            {
                private String sr;
                private String ss;

                public static AstroBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, AstroBean.class);
                }

                public String getSr() { return sr;}

                public void setSr(String sr) { this.sr = sr;}

                public String getSs() { return ss;}

                public void setSs(String ss) { this.ss = ss;}
            }

            public static class CondBean
            {
                private String code_d;
                private String code_n;
                private String txt_d;
                private String txt_n;

                public static CondBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, CondBean.class);
                }

                public String getCode_d() { return code_d;}

                public void setCode_d(String code_d) { this.code_d = code_d;}

                public String getCode_n() { return code_n;}

                public void setCode_n(String code_n) { this.code_n = code_n;}

                public String getTxt_d() { return txt_d;}

                public void setTxt_d(String txt_d) { this.txt_d = txt_d;}

                public String getTxt_n() { return txt_n;}

                public void setTxt_n(String txt_n) { this.txt_n = txt_n;}
            }

            public static class TmpBean
            {
                private String max;
                private String min;

                public static TmpBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, TmpBean.class);
                }

                public String getMax() { return max;}

                public void setMax(String max) { this.max = max;}

                public String getMin() { return min;}

                public void setMin(String min) { this.min = min;}
            }

            public static class WindBean
            {
                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public static WindBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, WindBean.class);
                }

                public String getDeg() { return deg;}

                public void setDeg(String deg) { this.deg = deg;}

                public String getDir() { return dir;}

                public void setDir(String dir) { this.dir = dir;}

                public String getSc() { return sc;}

                public void setSc(String sc) { this.sc = sc;}

                public String getSpd() { return spd;}

                public void setSpd(String spd) { this.spd = spd;}
            }
        }

        public static class HourlyForecastBean
        {
            private String date;
            private String hum;
            private String pop;
            private String pres;
            private String tmp;
            /**
             * deg : 294
             * dir : 西北风
             * sc : 微风
             * spd : 9
             */

            private WindBean wind;

            public static HourlyForecastBean objectFromData(String str)
            {

                return new Gson().fromJson(str, HourlyForecastBean.class);
            }

            public String getDate() { return date;}

            public void setDate(String date) { this.date = date;}

            public String getHum() { return hum;}

            public void setHum(String hum) { this.hum = hum;}

            public String getPop() { return pop;}

            public void setPop(String pop) { this.pop = pop;}

            public String getPres() { return pres;}

            public void setPres(String pres) { this.pres = pres;}

            public String getTmp() { return tmp;}

            public void setTmp(String tmp) { this.tmp = tmp;}

            public WindBean getWind() { return wind;}

            public void setWind(WindBean wind) { this.wind = wind;}

            public static class WindBean
            {
                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public static WindBean objectFromData(String str)
                {

                    return new Gson().fromJson(str, WindBean.class);
                }

                public String getDeg() { return deg;}

                public void setDeg(String deg) { this.deg = deg;}

                public String getDir() { return dir;}

                public void setDir(String dir) { this.dir = dir;}

                public String getSc() { return sc;}

                public void setSc(String sc) { this.sc = sc;}

                public String getSpd() { return spd;}

                public void setSpd(String spd) { this.spd = spd;}
            }
        }
    }
}
