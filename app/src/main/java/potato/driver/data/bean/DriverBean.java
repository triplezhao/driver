package potato.driver.data.bean;

//import

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import potato.driver.chips.base.BaseBean;
import potato.driver.data.db.DriverBeanDao;

public class DriverBean extends BaseBean implements Serializable{

	//propslist
	private String login_id;
	private String islogin;
	private String username;

	//set get list
	public void setLogin_id(String login_id){
		this.login_id=login_id;
	}

	public String getLogin_id(){
		return this.login_id;
	}

	public void setIslogin(String islogin){
		this.islogin=islogin;
	}

	public String getIslogin(){
		return this.islogin;
	}

	public void setUsername(String username){
		this.username=username;
	}

	public String getUsername(){
		return this.username;
	}


	//other
	//createFromCursor
	public static DriverBean  cursor2Bean(Cursor cursor){
		if (cursor == null) return null;
		DriverBean bean = new DriverBean();
		bean.setLogin_id(cursor.getString(cursor.getColumnIndex(DriverBeanDao.Columns.login_id)));
		bean.setIslogin(cursor.getString(cursor.getColumnIndex(DriverBeanDao.Columns.islogin)));
		bean.setUsername(cursor.getString(cursor.getColumnIndex(DriverBeanDao.Columns.username)));
		return bean;
	}

	//createFromJSON
	public static DriverBean  json2Bean(JSONObject json)throws JSONException{
		if (json == null) return null;
		DriverBean bean = new DriverBean();
		bean.setLogin_id(json.optString("login_id"));
		bean.setIslogin(json.optString("islogin"));
		bean.setUsername(json.optString("username"));
		return bean;
	}

	//createFromJSONArray
	public static ArrayList<DriverBean> jsonArray2BeanList(JSONArray jsonArray) throws JSONException {

		if (jsonArray == null) return null;

		ArrayList<DriverBean> list = new ArrayList<DriverBean>();

		int count = jsonArray.length();
		for (int i = 0; i < count; i++) {
			JSONObject jsonObj = jsonArray.optJSONObject(i);
			DriverBean entity = DriverBean.json2Bean(jsonObj);
			list.add(entity);
		}
		return list;
	}

	//buildContentValues
	public static ContentValues bean2CV(BaseBean baseBean) {
		DriverBean bean = new DriverBean();

		if (baseBean != null) {
			bean = (DriverBean) baseBean;
		}
		ContentValues values = new ContentValues();
		values.put(DriverBeanDao.Columns.login_id, bean.getLogin_id());
		values.put(DriverBeanDao.Columns.islogin, bean.getIslogin());
		values.put(DriverBeanDao.Columns.username, bean.getUsername());
		return values;
	}

}