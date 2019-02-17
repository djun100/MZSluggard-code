package com.manzo.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.manzo.plugin.controller.SimpleFileController;
import com.manzo.plugin.utils.AndroidUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shenjianlin on 2018/8/30.
 * 导入Activity关联layout的控件
 */
public class SimpleGenerateCodeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        SimpleFileController.loadFileByDialog(anActionEvent);

        System.out.println("测试网络接口，发起请求");
        System.out.println("测试网络接口："+getJsonByInternet());

    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        AndroidUtils.layoutCodeCanLoad(e);
    }

    /**
     * 从网络获取json数据,(String byte[})

     * @return
     */
    public static String getJsonByInternet(){
        try {
            URL url = new URL("http://www.wanandroid.com/tools/mockapi/2046/hehe");
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if(200 == urlConnection.getResponseCode()){
                //得到输入流
                InputStream is =urlConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while(-1 != (len = is.read(buffer))){
                    baos.write(buffer,0,len);
                    baos.flush();
                }
                return baos.toString("utf-8");
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
