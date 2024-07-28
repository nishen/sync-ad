package cops.sync.ad.util;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;

public class BlindSSLSocketFactory extends SocketFactory
{
	private static SocketFactory blindFactory = null;

	static
	{
		TrustManager[] blindTrustMan = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers()
			{
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
			                               String authType) throws CertificateException
			{}

			public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
			                               String authType) throws CertificateException
			{}
		} };

		try
		{
			SSLContext sc = SSLContext.getInstance("TLSv1.2");
			sc.init(null, blindTrustMan, new java.security.SecureRandom());
			blindFactory = sc.getSocketFactory();
		}
		catch (GeneralSecurityException e)
		{
			e.printStackTrace();
		}
	}

	public static SocketFactory getDefault()
	{
		return new BlindSSLSocketFactory();
	}

	public Socket createSocket(String arg0, int arg1) throws IOException, UnknownHostException
	{
		return blindFactory.createSocket(arg0, arg1);
	}

	public Socket createSocket(InetAddress arg0, int arg1) throws IOException
	{
		return blindFactory.createSocket(arg0, arg1);
	}

	public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3) throws IOException,
	                                                                              UnknownHostException
	{
		return blindFactory.createSocket(arg0, arg1, arg2, arg3);
	}

	public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2, int arg3) throws IOException
	{
		return blindFactory.createSocket(arg0, arg1, arg2, arg3);
	}
}