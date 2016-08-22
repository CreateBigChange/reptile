package reptile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class test {
		public static void main(String args[]){
			String url="https://list.tmall.com/search_product.htm?type=pc&q=%C4%D0%D7%B0&totalPage=100&cat=50025174&sort=s&style=w&from=.list.pc_1_searchbutton&jumpto=";
			reptile r=null;
			//关键字搜索后的店铺列表页面
			for(int i=0;i<10;i++){
				r = new reptile(url+i,"Thread"+i+"#J_Filter");
				new Thread(r).start();
			}
		}
}
