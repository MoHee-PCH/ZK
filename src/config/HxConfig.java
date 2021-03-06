/**
 * 配置文件
 * 路由， 数据库， 拦截器， 验证器
 */
package config;

import model.Active;
import model.Doctor;
import model.News;
import model.Source;
import model.Statute;
import model.Subcenter;
import model.User;
import routes.AdminRoutes;
import routes.FrontRoutes;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;


public class HxConfig extends JFinalConfig {
	public void configConstant(Constants me){
		PropKit.use("hxConfig.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		//本身默认的编码为utf-8
		//me.setEncoding(others);
//		me.setBaseUploadPath(baseUploadPath);
//		文件下载基础路径
//		me.setBaseDownloadPath("E:\\Eclipse-JavaEE-test\\HX_System\\WebRoot\\upload");
	}
	
	public void configRoute(Routes me){
		me.add(new FrontRoutes());       //前端路由
		me.add(new AdminRoutes());     //后端路由
	}
	
//	配置一个通用的c3p0Plugin
	public static C3p0Plugin createC3p0Plugin() {
		String Url = PropKit.get("jdbcUrl");
		String User = PropKit.get("user");
		String Pwd = PropKit.get("password");
//		String Driver = PropKit.get("jdbcDriver"); //sqlserver 配置添加
//		return new C3p0Plugin(Url, User, Pwd.trim(), Driver);
		return new C3p0Plugin(Url, User, Pwd.trim());
		//trim() 函数移除字符串两侧的空白字符或其他预定义字符。功能除去字符串开头和末尾的空格或其他字符。
	} 
	
	public void configPlugin(Plugins me){
		C3p0Plugin C3p0Plugin = createC3p0Plugin();
		me.add(C3p0Plugin);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(C3p0Plugin);
		me.add(arp);
		
		arp.addMapping("user", User.class);
		arp.addMapping("src", Source.class);
		arp.addMapping("statute", Statute.class);
		arp.addMapping("activity", Active.class);
		arp.addMapping("news", News.class);
		arp.addMapping("doctor", Doctor.class);
		arp.addMapping("subcenter", Subcenter.class);
	}
	
	public void configInterceptor(Interceptors me){
//		me.add(new AuthInterceptor());
	}
	
	public void configHandler(Handlers me){
//		me.add(new ResourceHandler());
		me.add(new ContextPathHandler("CTX"));
	}
}
