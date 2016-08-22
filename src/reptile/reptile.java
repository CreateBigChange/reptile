package reptile;

/**
 * 天猫按照搜索进行爬去数据
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class reptile implements Runnable{
	public URL beginUrl=null;
	public HttpURLConnection httpurl =null;
	public String [] urlStr=new String[20];
	public int sum=0;
	public String name;
	public mysqlHardle mysqljdbc=null;
	public reptile(String url,String name){
		//初始化数据库 以及获取每一页店铺列表页面的20家店铺的url放在 urlStr
		mysqljdbc =new mysqlHardle();
		this.name =name;
		
				if("".equals(url)){
			//url="https://list.tmall.com/search_product.htm?type=pc&q=%C4%D0%D7%B0&totalPage=100&cat=50025174&sort=s&style=w&from=.list.pc_1_searchbutton&jumpto=1#J_Filter";
		}
		
		try {
			System.out.println(url);
			URL beginUrl = new URL(url);
			HttpURLConnection httpurl =  (HttpURLConnection) beginUrl.openConnection();
		
			httpurl.setRequestMethod("GET");
	        //Get请求不需要DoOutPut
			
			httpurl.setDoOutput(false);
			httpurl.setDoInput(true);
	        
			//设置连接超时时间和读取超时时间
			httpurl.setConnectTimeout(10000);
			httpurl.setReadTimeout(10000);
			httpurl.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpurl.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");  
			httpurl.setRequestProperty("Content-Language", "zh-cn");  
			httpurl.setRequestProperty("Connection", "keep-alive");  
			httpurl.setRequestProperty("Cache-Control", "no-cache");  
			
			//连接服务器  
			httpurl.connect(); 
			
			
			
			BufferedReader br =new BufferedReader(new InputStreamReader(httpurl.getInputStream(),"gbk")); 
			String str="";
			String tempStr="";

			//获取请求列表
			while(null!=(tempStr=br.readLine()))
				str+=tempStr;
			
			    Pattern pattern  = Pattern.compile("(<div class=\"shopHeader\">(.+?)href=\"(..+?)\")");
			    Matcher matcher = pattern.matcher(str);
				int count = matcher.groupCount();
				
				while(matcher.find())
				{
					urlStr[this.sum]="https://list.tmall.com/"+matcher.group(3)+"&spm=a220m.1000858.1000725.1.m5mDLG";
					this.sum++;
				}
				String strcontent="";
				
	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public  String getResponse(String urlStr,int order){
		//对每一家店铺搜索
		
		System.out.println("------------------------"+this.name+"获取----------第"+order+"家店铺的信息-----------------------------------------------");
		
		/******定义相关的预备变量********/
		HttpURLConnection httpurl=null;
		URL beginUrl=null;
		String  str="";
		String tempStr="";
		BufferedReader br=null;
		int totleNum =0;
		int currpage=1;
		mysqlHardle mysqldb=null;
		while(currpage==1||currpage<totleNum){
			System.out.println("--------------"+this.name+"获取--------第"+order+"家----第"+currpage+"页的信息-----------------------------");
			
			try {
				
				/******传入URL********/
					
					beginUrl = new URL(urlStr+"&s="+currpage*60);
					httpurl = (HttpURLConnection) beginUrl.openConnection();
					httpurl.setRequestMethod("GET");
				
				
				} catch (ProtocolException e1) {
					
					e1.printStackTrace();
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/******定义相关的头信息********/
				
				httpurl.setDoOutput(true); 
				
				httpurl.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
			
				httpurl.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");  
				
				httpurl.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");  
					
				httpurl.setRequestProperty("Cookie", "cna=IDI3EMOf/DkCAa8M4lOnL3wf; _med=dw:1366&dh:768&pw:1366&ph:768&ist:0; x=__ll%3D-1%26_ato%3D0; sm4=430100; cq=ccp%3D0; ck1=; hng=; _m_user_unitinfo_=unit|unsz; _m_unitapi_v_=1471184935107; _m_h5_tk=c2e88694830c36c2ad41700f67e54a53_1471837628675; _m_h5_tk_enc=86746b6dc82f87a6c2b2aab762ed46b4; swfstore=98735; _tb_token_=S5BUBZkqU9IS; uc3=nk2=U0yUUeGPwd9XhdvI&id2=UoCIRD4h4mTMwQ%3D%3D&vt3=F8dAS1ISHJ6%2Bn%2FQMvPY%3D&lg2=V32FPkk%2Fw0dUvg%3D%3D; uss=Vv1ycX84aXvD3wr2hYLojZ46tl14bS3faJOSF4nci1DGkqcF4xF8ZQATCw%3D%3D; lgc=0%5Cu5929%5Cu6DAF%5Cu84DD%5Cu836F%5Cu5E080; tracknick=0%5Cu5929%5Cu6DAF%5Cu84DD%5Cu836F%5Cu5E080; cookie2=dec5b3209ebe2d3b853356a77be7bf33; skt=260aac96002ff0ff; t=9274c663bb90436d629793b02386478d; tk_trace=1; tt=sec.taobao.com; whl=-1%260%260%260; pnm_cku822=081UW5TcyMNYQwiAiwQRHhBfEF8QXtHcklnMWc%3D%7CUm5OcktxTXhGfUh1TXJHfCo%3D%7CU2xMHDJ7G2AHYg8hAS8XKAYmCFQ1Uz9YJlxyJHI%3D%7CVGhXd1llXGZab1FqX2JaZVBrXGFDe0B1T3RMdUl0SnBFfER4VgA%3D%7CVWldfS0SMg41Di4SJwcpTTdadhd5XTFAMwMzFypbZQs1G00b%7CVmhIGCUFOBgkGiMXNws2AzcJKRUrECsLMQo%2FHyMdJh09BzgNWw0%3D%7CV25Tbk5zU2xMcEl1VWtTaUlwJg%3D%3D; res=scroll%3A1349*10893-client%3A1349*623-offset%3A1349*10893-screen%3A1366*768; otherx=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; isg=AtXVBeWPLq6xuwrZmlQbfjFO5NHsjzABZmf6kld6xcybrvWgHyKZtONgDgXi; l=ArS05oHrppanP6nKaa8nFQAkBHgm2dh3"); 
				
				try {
					
					httpurl.connect();
					
					if(httpurl.getResponseCode()!=200){
						
						System.out.println("出现code!=200问题,url:"+urlStr+"--- name:"+this.name+"store:"+order+",page:currpage"+currpage+"reason:code!=200");
						
						mysqljdbc.addErrorInfo("errorinfo", urlStr, this.name,String.valueOf(order),String.valueOf(currpage),String.valueOf(totleNum),"code!=200");
						
						return null;
					}
					br =new BufferedReader(new InputStreamReader(httpurl.getInputStream(),"gbk"));

					System.out.println("开始抓取:"+urlStr);
					
					while(null!=(tempStr=br.readLine())){
						str+=tempStr;
					}
					/************错误调试******************/
					if("".equals(str)){
						
						System.out.println("出现str=null问题,url:"+urlStr+"--- name:"+this.name+"store:"+order+",page:currpage"+currpage+"reason=str为空");
						mysqljdbc.addErrorInfo("errorinfo", urlStr, this.name,String.valueOf(order),String.valueOf(currpage),String.valueOf(totleNum),"str为空");
						return null;
						
					}
					
					/******找出页面总数目  <b class="ui-page-s-len">1/18</b> ********/
					Pattern Numpattern =Pattern.compile("<b class=\"ui-page-s-len\">\\d+/(\\d+)</b>"); 
					Matcher Nummatcher =Numpattern.matcher(str);
				
					if(Nummatcher.find()){
						totleNum=Integer.parseInt(Nummatcher.group(1));
						
					}
					
					/*************************找出具体的信息****************************************/
					Pattern pattern  =Pattern.compile("<div class=\"product\">(.+?)(<img data-ks-lazyload=\"(.+?)\" />)(.+?)</b>(.+?)</em>(.+?)<a href=\"(.+?)\"(.+?)title=\"(.+?)\"(.+?)<em>(.+?)</em>(.+?)rn=(.+?)\">(.+?)>(\\d+)</a></span>().+?");

					Matcher matcher  =pattern.matcher(str);
					
					System.out.println("开始分析");
					
					/****预定义变量****/
					String commitNum=null;
					String picUrl=null;
					String saleprice=null;
					String detail=null;
					String title=null;
					String saleNum=null;
					
					while(matcher.find()){
						picUrl=matcher.group(3);
						saleprice=matcher.group(5);
						detail=matcher.group(7);
						title=matcher.group(9);
						saleNum=matcher.group(11);
						commitNum=matcher.group(15);
						System.out.println("线程"+name+"当前店铺"+order+";当前页面"+currpage+"总页面数目为:"+totleNum);
						
						/*
						System.out.println("线程"+name+"->图片地址:"+matcher.group(3));
						System.out.println("线程"+name+"->售价:"+matcher.group(5));
						System.out.println("线程"+name+"->详情地址:"+matcher.group(7));
						System.out.println("线程"+name+"->标题:"+matcher.group(9));
						System.out.println("线程"+name+"->销售额:"+matcher.group(11));
						System.out.println("线程"+name+"->评价数:"+matcher.group(15));
						*/
						
						mysqljdbc.add("info", picUrl, saleprice, detail, title, saleNum, commitNum);	
					}
					currpage++;
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return str;
	}
	@Override
	public void run() {
		
		String strcontent=null;
		int errornum=0;
		for(int i=0;i<sum;){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/***遍历每一家店铺***/
			if("".equals(strcontent=getResponse(urlStr[i],i)))
			{
				errornum++;
				if(errornum==20)
				{
					System.out.println("出现问题,url:"+urlStr[i]+"--- name:"+this.name);
					//mysqljdbc.add("errorinfo", urlStr[i], this.name);
					errornum=0;
				}
			}
			else
			{
				i++;
			}		
		}	
	}
}