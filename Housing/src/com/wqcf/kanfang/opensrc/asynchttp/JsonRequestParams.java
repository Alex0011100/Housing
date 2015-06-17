package com.wqcf.kanfang.opensrc.asynchttp;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.google.gson.Gson;


/**
 * 把上传的数据转换为json格式的
 * 仅支持string类型的转换
 * 例如: key=value    ==>     {"key":"value"}
 * 
 * @author 
 */
public class JsonRequestParams extends RequestParams {
	private Object criteria;
	
	/**
     * Constructs a new empty {@code JsonRequestParams} instance.
     */
	public JsonRequestParams() {
        this((Map<String, String>) null);
    }
	/**
	 * Constructs a new JsonRequestParams instance from a criteria
	 * @param criteria
	 */
	public JsonRequestParams(Object criteria) {
		this.criteria = criteria;
       // this(BeanUtils.criteria2jsonMap(criteria));
    }

    /**
     * Constructs a new JsonRequestParams instance containing the key/value string params from the
     * specified map.
     *
     * @param source the source key/value string map to add.
     */
    public JsonRequestParams(Map<String, String> source) {
       super(source);
    }

    /**
     * Constructs a new JsonRequestParams instance and populate it with a single initial key/value
     * string param.
     *
     * @param key   the key name for the intial param.
     * @param value the value string for the initial param.
     */
    public JsonRequestParams(final String key, final String value) {
      super(key,value);
    }

    /**
     * Constructs a new JsonRequestParams instance and populate it with multiple initial key/value
     * string param.
     *
     * @param keysAndValues a sequence of keys and values. Objects are automatically converted to
     *                      Strings (including the value {@code null}).
     * @throws IllegalArgumentException if the number of arguments isn't even.
     */
    public JsonRequestParams(Object ...keysAndValues) {
       super(keysAndValues);
    }
	@Override
	public HttpEntity getEntity(ResponseHandlerInterface progressHandler) throws IOException {
		if (streamParams.isEmpty() && fileParams.isEmpty()) {
			return createFormEntity();
		} else {
			return super.getEntity(progressHandler);
		}
	}

	private HttpEntity createFormEntity() {
		try {
			JSONObject params_list = null;
			if (criteria != null) {
				params_list = new JSONObject(new Gson().toJson(criteria));
			} else {
				params_list = new JSONObject();
			}
			for (BasicNameValuePair nameValuePair : getParamsList()) {
				String value = nameValuePair.getValue();
				if (value == null) {
					value = "";
				}
				params_list.put(nameValuePair.getName(), value);
			}
			StringEntity entity = null;
			if (params_list.length() > 0) {
				entity = new StringEntity(params_list.toString(),	HTTP.UTF_8);
				entity.setContentType(URLEncodedUtils.CONTENT_TYPE);
			}

			return entity;
		} catch (Exception e) {
			return null; // Actually cannot happen when using utf-8
		}
	}
}
