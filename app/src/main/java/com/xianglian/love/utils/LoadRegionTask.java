package com.xianglian.love.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.xianglian.love.model.JsonBean;
import com.xianglian.love.model.JsonBean.CityBean;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglong on 17/9/1.
 * 加载省市区
 */

public class LoadRegionTask extends AsyncTask<String, String, Integer> {
    private Context mContext;

    private LoadFileListener mListener;

    private List<JsonBean> mOptions1Items = new ArrayList<>();

    private List<List<CityBean>> mOptions2Items = new ArrayList<>();

    private List<List<List<CityBean>>> mOptions3Items = new ArrayList<>();

    public LoadRegionTask(Context context, LoadFileListener loadFileListener) {
        this.mContext = context;
        this.mListener = loadFileListener;
    }

    @Override
    protected void onPostExecute(Integer s) {
        super.onPostExecute(s);
        if (mListener != null)
            mListener.onRegionResult(mOptions1Items, mOptions2Items, mOptions3Items);

    }

    @Override
    protected Integer doInBackground(String... params) {
        return parseJsonData();
    }

    List<JsonBean> parseData(String result) {
        List<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            for (int i = 0; i < data.length(); i++) {
                Gson gson = new Gson();
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
//                JsonBean entity = JSON.parseObject(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    private int parseJsonData() {//解析数据
        String JsonData = new JsonUtils().getJson(mContext, "data.json");//获取assets目录下的json文件数据

        List<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        mOptions1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            List<CityBean> cityList = new ArrayList<>();//该省的城市列表（第二级）
            List<List<CityBean>> areaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getNext().size(); c++) {//遍历该省份的所有城市
                CityBean city = jsonBean.get(i).getNext().get(c);
                cityList.add(city);//添加城市

                List<CityBean> cityAreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getNext().get(c).getNext() == null
                        || jsonBean.get(i).getNext().get(c).getNext().size() == 0) {
                    cityAreaList.add(null);
                } else {

                    for (int d = 0; d < jsonBean.get(i).getNext().get(c).getNext().size(); d++) {//该城市对应地区所有数据
                        CityBean AreaName = jsonBean.get(i).getNext().get(c).getNext().get(d);
                        cityAreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                areaList.add(cityAreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            mOptions2Items.add(cityList);

            /**
             * 添加地区数据
             */
            mOptions3Items.add(areaList);
        }

        return -1;

    }

    public interface LoadFileListener {
        void onRegionResult(List<JsonBean> options1Items, List<List<CityBean>> options2Items,
                            List<List<List<CityBean>>> options3Items);
    }
}
