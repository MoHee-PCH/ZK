package controller;

import interceptor.AuthInterceptor;
import interceptor.downInterceptor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Active;
import model.News;
import model.Statute;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

public class NewsController extends Controller{
//	用户查看视图
	@ActionKey("/news")
	public void index(){
		setAttr("newsPage", News.news.paginate(getParaToInt(0, 1), 10));
		render("/news.html");
	}
	
//	admin管理视图
	@Before(AuthInterceptor.class)
	public void manage(){
		setAttr("newsPage", News.news.paginate(getParaToInt(0, 1), 10));
		render("/src/newsManage.html");
	}
	
//	删除刷新
	/*
	 * 添加一个拦截器 获得 actionkey
	 * 传值，交给 重定位
	 */
	public void delete(){
		News.news.deleteById(getParaToInt());
		redirect("/news/manage");
	}
	
	@Before(AuthInterceptor.class)
	public void add(){
		render("/editor/newsAdd.html");
	}
	
	//添加一个validator 验证信息是否有空缺
	public void upload(){
		System.out.println("upload");
		List<UploadFile> files = this.getFiles();
		for(int i=0;i<files.size();i++){
			String fileName = files.get(i).getFileName();
			System.out.println("filename: " + fileName);
			String uploadPath = files.get(i).getUploadPath();
			System.out.println("文件上传路径："+uploadPath);
			String path = uploadPath + "\\" + fileName;
			System.out.println("文件下载路径："+path);
		}
		News s = getModel(News.class);
		s.save();
		
//		截取内容显示
		String content = s.getContent();
		String cont;
		if(content.length() > 100){
			cont = content.substring(0, 100) + "...";
		}else{
			cont = content.substring(0, content.length()) + "...";
		}
		s.setCont(cont);
		
//		日期相关
		Date currentDate = new Date(System.currentTimeMillis());
		String[] dateStr = currentDate.toString().split(" ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String dateNowStr = sdf.format(currentDate);  
		String[] sdate = dateNowStr.toString().split(" ");
		s.setDate(sdate[0]);
		s.setDay( dateStr[2]);
		s.setMonth(dateStr[1]);
		s.update();
		
		System.out.println("s: " + s);
		redirect("/news/manage");
	}
	
	/**
	 * test for ueitor
	 */
	public void toEditor(){
		render("/editor/index.html");
	}
	public void upload2(){
		System.out.println("upload");
		List<UploadFile> files = this.getFiles();
		for(int i=0;i<files.size();i++){
			String fileName = files.get(i).getFileName();
			System.out.println("filename: " + fileName);
			String uploadPath = files.get(i).getUploadPath();
			System.out.println("文件上传路径："+uploadPath);
			String path = uploadPath + "\\" + fileName;
			System.out.println("文件下载路径："+path);
		}
		News s = getModel(News.class);
		System.out.println("s: " + s);
		s.save();
		
//		【封装】截取内容显示 
//		String content = s.getContent();
//		String cont;
//		if(content.length() > 100){
//			cont = content.substring(0, 100) + "...";
//		}else{
//			cont = content.substring(0, content.length()) + "...";
//		}
//		s.setCont(cont);
		
//		【封装】日期相关
		Date currentDate = new Date(System.currentTimeMillis());
		String[] dateStr = currentDate.toString().split(" ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String dateNowStr = sdf.format(currentDate);  
		String[] sdate = dateNowStr.toString().split(" ");
		s.setDate(sdate[0]);
		s.setDay( dateStr[2]);
		s.setMonth(dateStr[1]);
		s.update();
		
		System.out.println("s: " + s);
		redirect("/news/manage");
	}
	
	
	@Before(AuthInterceptor.class)
	public void edit() {
		News s = News.news.findById(getParaToInt());
		String content = s.getContent();
		System.out.println(content);
		content = content.replace("<br>","\n"); //old, new
		System.out.println(content);
		s.setContent(content);
		s.update();
		
//		更新cont
		String contet = s.getContent();
		String cont;
		if(contet.length() > 100){
			cont = contet.substring(0, 100) + "...";
		}else{
			cont = contet.substring(0, contet.length()) + "...";
		}
		s.setCont(cont);
		s.update();
		
		setAttr("news", News.news.findById(getParaToInt()));
		render("/editor/newsEdit.html");
	}
	
	@Before(AuthInterceptor.class)
	public void update() {
		System.out.println("getPara(id): " + getPara("News.id"));
		System.out.println("getParaToInt : "+getParaToInt());
		News st = getModel(News.class);
		System.out.println("st: " + st);
		
		System.out.println("update");
		List<UploadFile> files = this.getFiles();
		for(int i=0;i<files.size();i++){
			String fileName = files.get(i).getFileName();
			System.out.println("filename: " + fileName);
			String uploadPath = files.get(i).getUploadPath();
			System.out.println("文件上传路径："+uploadPath);
			String path = uploadPath + "\\" + fileName;
			System.out.println("文件下载路径："+path);
		}
		News s = getModel(News.class);
		System.out.println("s: " + s);
		String docpath = getPara("news.docpath"),
				  picpath  = getPara("news.picpath");
		s.setDocPath(docpath);
		s.setPicPath(picpath);

//		截取内容设置
		String content = s.getContent();
		String cont;
		if(content.length() > 100){
			cont = content.substring(0, 100) + "...";
		}else{
			cont = content.substring(0, content.length()) + "...";
		}
		s.setCont(cont);
	
//		日期设置
		Date currentDate = new Date(System.currentTimeMillis());
		String[] dateStr = currentDate.toString().split(" ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String dateNowStr = sdf.format(currentDate);  
		String[] sdate = dateNowStr.toString().split(" ");
		s.setDate(sdate[0]);
		s.setDay( dateStr[2]);
		s.setMonth(dateStr[1]);
		s.update();
		
		redirect("/news/manage");
	}
	
	@Before(AuthInterceptor.class)
	public void update2() {
		System.out.println("update");
		List<UploadFile> files = this.getFiles();
		for(int i=0;i<files.size();i++){
			String fileName = files.get(i).getFileName();
			System.out.println("filename: " + fileName);
			String uploadPath = files.get(i).getUploadPath();
			System.out.println("文件上传路径："+uploadPath);
			String path = uploadPath + "\\" + fileName;
			System.out.println("文件下载路径："+path);
		}
		News s = getModel(News.class);
		System.out.println("s: " + s);
		String docpath = getPara("news.docpath"),
				  picpath  = getPara("news.picpath");
		s.setDocPath(docpath);
		s.setPicPath(picpath);

//		截取内容设置
		String content = s.getContent();
		String cont;
		if(content.length() > 100){
			cont = content.substring(0, 100) + "...";
		}else{
			cont = content.substring(0, content.length()) + "...";
		}
		s.setCont(cont);
	
//		日期设置
		Date currentDate = new Date(System.currentTimeMillis());
		String[] dateStr = currentDate.toString().split(" ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String dateNowStr = sdf.format(currentDate);  
		String[] sdate = dateNowStr.toString().split(" ");
		s.setDate(sdate[0]);
		s.setDay( dateStr[2]);
		s.setMonth(dateStr[1]);
		s.update();
		
		redirect("/news/manage");
	}
	
//	用户查看视图
	public void detail(){
		News.news.findById(getParaToInt()).countAdd(getParaToInt());
		setAttr("source", News.news.findById(getParaToInt()));
		render("/detailSingleNews.html");
	}
	
	public void detailEdit(){
		setAttr("source", News.news.findById(getParaToInt()));
		render("/singleNews.html");
	}
	
	/*
	 * 下载：
	 * 1.数据库中读取文件名
	 * 2.与存储位置拼接成为文件地址
	 */
		@Before(downInterceptor.class)
		public void download(){
			int id = getParaToInt();
			String docpath = News.news.docPath(id);
			
			if(docpath.length() != 1){
				String path = PathKit.getWebRootPath() + "\\upload";
				System.out.println("path: " + path);
				File file = new File(path + "\\" + docpath);
				if(file.isFile()){
					renderFile(file);
				}else{
					System.out.println("not file");
					renderText("文件不在");
				}
			}else{
				renderText("meiyou");
			}
		}
		
		public void search(){
			System.out.println("search");
			String title = getPara("search");
			System.out.println("title: " + title);
			setAttr("statPage", Statute.stat.paginate(1, 10, title));
			setAttr("actPage", Active.act.paginate(1, 10, title));
			setAttr("newsPage", News.news.paginate(1, 10, title));
			render("/src/search.html");
//			render("/search.html");
		}
}
