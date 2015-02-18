package com.app.pug.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

/**
 * This class enables sending / exchange of information to a server usign the https protocol
 * @author MATIVO-PC
 *
 */
public class Loader {
	
	private HttpClient client;	
	private HttpResponse response;
	private InputStream stream;
	private StringBuffer buffer;
	
	public Loader() {
		client = null;
		response = null;
		stream = null;
		buffer = new StringBuffer();
	}
	
	public String checkForUpdates(String url) {
		try {
			HostnameVerifier verifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			client = new DefaultHttpClient();
			
			SchemeRegistry registry = new SchemeRegistry();
			SSLSocketFactory factory = SSLSocketFactory.getSocketFactory();
			factory.setHostnameVerifier((X509HostnameVerifier)verifier);
			registry.register(new Scheme("https", factory, 443));
			
			SingleClientConnManager manager = new SingleClientConnManager(client.getParams(), registry);
			client = new DefaultHttpClient(manager, client.getParams());
			HttpsURLConnection.setDefaultHostnameVerifier(verifier);
			
			HttpPost post = new HttpPost(url);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("check_version", "check_version"));
			post.setEntity(new UrlEncodedFormEntity(params));
			
			response = client.execute(post);
			stream = response.getEntity().getContent();
			if (stream != null) {
				decodeStream();
				return buffer.toString();
			}
			return "-1";
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
	}
	
	
	
	private void decodeStream() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		String line = "";
		while ((line = reader.readLine()) != null)
			buffer.append(line);
	}
}
