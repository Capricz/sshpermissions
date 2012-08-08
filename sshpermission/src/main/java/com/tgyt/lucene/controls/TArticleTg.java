/**   
  * @Title: TArticleTg.java 
  * @Package com.tgyt.lucene.contrl 
  * @Description: 北京太谷雨田信息科技有限责任公司 版本所有
  * @author sunqiang sunqiangbingxue@sina.com
  * @date 2012-8-7 上午9:53:08 
  * @version V1.0   
  */

package com.tgyt.lucene.controls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tgyt.framework.controls.struts2.BaseTg;
import com.tgyt.lucene.biz.ITArticleService;

/** 
 * @ClassName: TArticleTg 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author sunqiang sunqiangbingxue@sina.com
 * @date 2012-8-7 上午9:53:08 
 *  
 */
@SuppressWarnings("serial")
@Scope("prototype")
@Controller(value="lucene.tarticleTgControl")
public class TArticleTg extends BaseTg{
	@Autowired
	private ITArticleService articleService;
	
	private String keyword;
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/** 
	  * @Title: index 
	  * @Description: 索引进入操作信息页面  
	  * @param @return
	  * @return String
	  * @throws 
	  */
	public String index(){
		return SUCCESS;
	}
	/** 
	  * @Title: createIndex 
	  * @Description: 为数据库中所有元素建立索引
	  * @param 
	  * @return void
	  * @throws 
	  */
	public void createIndex(){
		try{
			articleService.createIndex();
			outJsonPlainString(response, "{\"success\":true}");
		}catch(Exception e){
			e.printStackTrace();
			outJsonPlainString(response, "{\"error\":true}");
			
		}
	}
	public void searchIndex(){
		List<Document> docs=articleService.searchIndex(keyword, "");
		List list = new ArrayList();
		for(Document doc : docs) {
			Map<String, String> strMap = new HashMap<String, String>();
			strMap.put("title",doc.get("title"));
			strMap.put("id",doc.get("id"));
			strMap.put("content",doc.get("content"));
            list.add(strMap);
        }
		String json = JSONArray.fromObject(list).toString();
		System.out.println(json);
		outJsonPlainString(response,json);
	}
}
