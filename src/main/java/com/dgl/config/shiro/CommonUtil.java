package com.dgl.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.dgl.common.Enum.CustomException;
import com.dgl.common.Enum.EnumErrorMsg;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * @author: hxy
 * @description: 本后台接口系统常用的json工具类
 * @date: 2017/10/24 10:12
 */
public class CommonUtil {

	/**
	 * 返回一个info为空对象的成功消息的json
	 */
	public static JSONObject successJson() {
		return successJson(new JSONObject());
	}

	/**
	 * 返回一个返回码为100的json
	 */
	public static JSONObject successJson(Object info) {
		JSONObject resultJson = new JSONObject();
		resultJson.put("code", EnumErrorMsg.OP_SUCCESS.getCode());
		resultJson.put("msg", EnumErrorMsg.OP_SUCCESS.getMsg());
		resultJson.put("info", info);
		return resultJson;
	}

	/**
	 * 返回错误信息JSON
	 */
	public static JSONObject errorJson(EnumErrorMsg errorEnum) {
		JSONObject resultJson = new JSONObject();
		resultJson.put("code", errorEnum.getCode());
		resultJson.put("msg", errorEnum.getMsg());
		resultJson.put("info", new JSONObject());
		return resultJson;
	}


	/**
	 * 查询分页结果后的封装工具方法
	 *
	 * @param requestJson 请求参数json,此json在之前调用fillPageParam 方法时,已经将pageRow放入
	 * @param list        查询分页对象list
	 * @param totalCount  查询出记录的总条数
	 */
	public static JSONObject successPage(final JSONObject requestJson, List<JSONObject> list, int totalCount) {
		int pageRow = requestJson.getIntValue("pageRow");
		int totalPage = getPageCounts(pageRow, totalCount);
		JSONObject result = successJson();
		JSONObject info = new JSONObject();
		info.put("list", list);
		info.put("totalCount", totalCount);
		info.put("totalPage", totalPage);
		result.put("info", info);
		return result;
	}

	/**
	 * 查询分页结果后的封装工具方法
	 *
	 * @param list 查询分页对象list
	 */
	public static JSONObject successPage(List<JSONObject> list) {
		JSONObject result = successJson();
		JSONObject info = new JSONObject();
		info.put("list", list);
		result.put("info", info);
		return result;
	}

	/**
	 * 获取总页数
	 *
	 * @param pageRow   每页行数
	 * @param itemCount 结果的总条数
	 */
	private static int getPageCounts(int pageRow, int itemCount) {
		if (itemCount == 0) {
			return 1;
		}
		return itemCount % pageRow > 0 ?
				itemCount / pageRow + 1 :
				itemCount / pageRow;
	}

	/**
	 * 将request参数值转为json
	 */
	public static JSONObject request2Json(HttpServletRequest request) {
		JSONObject requestJson = new JSONObject();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] pv = request.getParameterValues(paramName);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < pv.length; i++) {
				if (pv[i].length() > 0) {
					if (i > 0) {
						sb.append(",");
					}
					sb.append(pv[i]);
				}
			}
			requestJson.put(paramName, sb.toString());
		}
		return requestJson;
	}

	/**
	 * 将request转JSON
	 * 并且验证非空字段
	 */
	public static JSONObject convert2JsonAndCheckRequiredColumns(HttpServletRequest request, String requiredColumns) {
		JSONObject jsonObject = request2Json(request);
		hasAllRequired(jsonObject, requiredColumns);
		return jsonObject;
	}

	/**
	 * 验证是否含有全部必填字段
	 *
	 * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,name,telephone"
	 */
	public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) {
		if (StringUtils.isNotEmpty(requiredColumns)) {
			//验证字段非空
			String[] columns = requiredColumns.split(",");
			String missCol = "";
			for (String column : columns) {
				Object val = jsonObject.get(column.trim());
				if (StringTools.isNullOrEmpty(val)) {
					missCol += column + "  ";
				}
			}
			if (!StringTools.isNullOrEmpty(missCol)) {
				jsonObject.clear();
				jsonObject.put("code", EnumErrorMsg.TEXT_IS_BLANK.getCode());
				jsonObject.put("msg", EnumErrorMsg.TEXT_IS_BLANK.getMsg() + missCol.trim());
				throw new CustomException(jsonObject);
			}
		}
	}

	/**
	 * 在分页查询之前,为查询条件里加上分页参数
	 *
	 * @param paramObject    查询条件json
	 * @param defaultPageRow 默认的每页条数,即前端不传pageRow参数时的每页条数
	 */
	private static void fillPageParam(final JSONObject paramObject, int defaultPageRow) {
		int pageNum = paramObject.getIntValue("pageNum");
		pageNum = pageNum == 0 ? 1 : pageNum;
		int pageRow = paramObject.getIntValue("pageRow");
		pageRow = pageRow == 0 ? defaultPageRow : pageRow;
		paramObject.put("offSet", (pageNum - 1) * pageRow);
		paramObject.put("pageRow", pageRow);
		paramObject.put("pageNum", pageNum);
		//删除此参数,防止前端传了这个参数,pageHelper分页插件检测到之后,拦截导致SQL错误
		paramObject.remove("pageSize");
	}

	/**
	 * 分页查询之前的处理参数
	 * 没有传pageRow参数时,默认每页10条.
	 */
	public static void fillPageParam(final JSONObject paramObject) {
		fillPageParam(paramObject, 10);
	}

	/**
	 * 返回错误提示
	 * @param errorCode
	 * @param errorMessage
	 * @return
	 */
    public static JSONObject error2Json(String errorCode, String errorMessage) {
		JSONObject resultJson = new JSONObject();
		resultJson.put("status", errorCode);
		resultJson.put("msg", errorMessage);
		return resultJson;
	}
}
