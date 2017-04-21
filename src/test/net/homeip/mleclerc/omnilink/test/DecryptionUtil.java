package net.homeip.mleclerc.omnilink.test;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.NetworkHelper;

public class DecryptionUtil {
	private byte[] sessionKey;
	
	public static void main(String[] args) throws CommunicationException {
		new DecryptionUtil().run();
	}

	private void run() throws CommunicationException {
		byte[] privateKey = toByteArray("48-CE-F5-A9-95-3E-78-16-05-13-17-E9-13-5D-1C-11", "-");
		byte[] sessionId = toByteArray("a3:ad:92:49:eb", ":");
		sessionKey = NetworkHelper.createSessionKey(privateKey, sessionId);
		
		decrypt("Request", "00:04:20:00:c4:1b:f3:61:43:db:33:94:8f:15:8d:f0:2f:d3:59:38");
		decrypt("Response", "00:04:20:00:d7:f9:89:e6:b0:63:91:7a:ad:16:47:bf:7a:64:b4:d5:db:f0:ab:02:24:f9:f5:db:e0:cb:ea:5e:ab:0f:ce:bb:7b:a5:04:cd:44:d6:11:cb:b5:b4:6f:69:4a:09:d4:06");
		System.out.println();

		decrypt("Request", "00:05:20:00:ce:06:5c:89:95:96:4c:8d:8b:67:bf:0a:af:7a:47:88");
		decrypt("Response", "00:05:20:00:1b:d0:24:c8:4e:ea:6a:3b:69:bc:e0:b5:49:14:aa:67:1a:e0:ec:d7:3b:a2:b6:4b:8e:08:37:fb:97:a8:e2:89:d9:de:0d:1d:c9:ef:16:5c:f6:83:45:3a:80:9e:0f:0b:2e:b6:ca:21:1e:c3:18:fe:f2:18:e6:46:a4:2f:3b:5a:63:65:4d:c6:e1:89:55:6a:c4:1d:b5:db:6e:aa:1a:92:f6:17:ae:af:25:65:af:fd:3d:42:ca:c4:22:b8:28:fc:91:b5:8c:5e:03:30:63:bf:15:d3:f3:ba:56:18:a2:dd:1b:1f:76:ba:fd:81:6e:fd:18:2f:3d:33:55:ed:ea:72:bf:8d:46:8c:1e:b5:e6:9d:b5:04:ea:17:0b:9b:d5:47:1b:1f:76:ba:fd:81:6e:fd:18:2f:3d:33:55:ed:ea:72:09:94:90:a5:2b:9d:fd:6c:83:f9:fb:ed:ef:78:df:fc:1b:1f:76:ba:fd:81:6e:fd:18:2f:3d:33:55:ed:ea:72:75:8c:0d:83:d9:53:00:b9:e5:82:df:5e:d5:bc:22:16");
		System.out.println();
		
		decrypt("Request", "00:06:20:00:5e:e4:ed:3a:6e:64:e2:5e:e6:c2:50:70:1d:dd:f0:80");
		decrypt("Response", "00:06:20:00:4f:c7:68:8c:f5:46:9b:26:42:dd:db:08:d9:20:ba:3f:f4:30:2c:19:2c:ee:37:e3:91:4c:60:2e:1e:fa:9e:27:db:9e:76:e7:3b:e3:5f:86:5e:7d:95:08:c2:00:d0:1d:f4:30:2c:19:2c:ee:37:e3:91:4c:60:2e:1e:fa:9e:27:b7:95:ce:31:36:b4:bf:65:1e:8c:87:cc:5e:87:1e:7e:f4:30:2c:19:2c:ee:37:e3:91:4c:60:2e:1e:fa:9e:27:3a:ac:95:32:1e:59:4c:4b:4e:66:75:ad:5c:93:a3:8c:0a:64:20:e6:e6:72:bf:40:e7:e0:93:09:13:17:b3:6b:8f:09:a8:6c:dc:42:3c:0d:de:2a:2c:f0:38:6f:ae:1e:2b:5a:f3:c5:14:27:14:fc:35:55:10:7c:90:ce:72:3d:cf:a0:a9:fa:24:3c:be:82:4a:48:44:83:bf:71:81:22:51:d8:f7:a6:85:07:cb:fb:fe:be:f1:94:6b:ec:08:01:bf:cf:32:a5:56:d5:64:30:c3:b0:4f:52:24:d3:1f:12");
		System.out.println();
		
		decrypt("Request", "00:07:20:00:3d:b3:2d:07:42:25:e4:77:74:7c:e6:24:69:c2:ef:10");
		decrypt("Response", "00:07:20:00:6b:7d:44:2c:31:e2:53:af:b2:40:1c:fb:25:37:a7:0a:52:94:6f:e3:ae:14:88:cc:c0:ba:1a:6c:5e:16:a0:90:df:c0:86:f8:63:cd:c3:df:53:9e:7f:57:58:1b:aa:38:27:12:ba:af:19:c6:82:89:3b:00:0c:d4:60:bf:b6:47:7a:e2:68:86:61:17:61:da:ba:0a:b3:21:69:5a:bb:e6:26:05:ee:a6:4f:c8:3a:24:0a:49:b1:b5:29:7f:8c:24:f9:f0:d4:d1:b1:b9:79:bd:a2:8d:5d:97:a2:68:30:5b:e1:e2:8b:6d:83:46:c8:9e:a3:a1:e9:13:76:68:2c:d7:52:94:6f:e3:ae:14:88:cc:c0:ba:1a:6c:5e:16:a0:90:65:60:f8:9c:ac:4f:36:13:c9:b4:8e:23:71:6b:4d:42:f4:f9:7f:68:a2:ea:61:ca:e6:8b:0d:37:8f:85:c9:d4:6a:ed:1b:c7:64:63:62:c3:38:61:d5:49:e6:f8:a9:43:bd:31:1f:b7:73:a0:19:2e:be:53:77:79:1f:4d:a8:91");
		System.out.println();
		
		decrypt("Request", "00:08:20:00:cd:85:3f:1d:39:66:33:2c:fd:99:07:2b:e9:42:ae:3e");
		decrypt("Response", "00:08:20:00:3b:77:69:2c:d4:07:02:80:f1:78:b4:6d:9b:06:07:c4:c3:5f:06:6b:5d:3b:ad:0b:ff:94:88:c8:e8:8a:c9:74:be:6e:be:f1:a8:b6:5b:83:ae:68:75:8d:4f:95:0e:cc:ad:6f:52:3c:a6:57:b2:2a:0c:70:df:cb:b6:89:5d:12:ad:6f:52:3c:a6:57:b2:2a:0c:70:df:cb:b6:89:5d:12:ad:6f:52:3c:a6:57:b2:2a:0c:70:df:cb:b6:89:5d:12:ad:6f:52:3c:a6:57:b2:2a:0c:70:df:cb:b6:89:5d:12:ad:6f:52:3c:a6:57:b2:2a:0c:70:df:cb:b6:89:5d:12:ad:6f:52:3c:a6:57:b2:2a:0c:70:df:cb:b6:89:5d:12:e2:4e:88:68:83:30:3c:bc:7c:d2:6e:22:88:fc:a9:f4:9c:c3:05:81:bd:7d:bc:5d:94:00:26:4a:c7:91:b9:11:50:c5:27:ca:63:fa:4c:0c:3b:44:ae:0c:71:93:52:bc:3d:bb:fd:7c:b5:39:2b:89:dc:1a:a2:46:02:16:d1:8d");
		System.out.println();
		
		decrypt("Request", "00:09:20:00:de:17:de:b4:6a:38:16:da:18:ce:c0:48:7c:6a:08:88");
		decrypt("Response", "00:09:20:00:a5:e5:52:4c:86:be:cb:e5:ad:62:b1:6c:04:04:ce:9e:ba:63:b3:0a:06:f7:33:77:fc:8d:2c:c9:68:ac:a5:4e:47:de:79:48:a3:27:8d:23:00:64:d4:a9:28:5c:a4:3a:aa:06:be:8a:77:46:d1:d1:c5:1a:fe:43:92:f3:37:89:47:de:79:48:a3:27:8d:23:00:64:d4:a9:28:5c:a4:3a:3e:d9:9e:37:1f:91:97:e5:8f:ee:4c:90:a8:ff:48:a2:2a:2f:57:c3:8c:5f:64:7c:bb:07:71:7b:b2:e5:8b:45:58:8e:d2:d7:16:6d:6c:f2:71:2a:aa:75:d3:7d:b0:7c:ba:24:0e:51:9f:a3:c3:02:dd:6c:79:58:3a:5e:f4:b2:95:2c:e1:e0:00:5b:f6:0c:a5:ad:ef:e7:cc:b5:8c:66:66:17:7f:f9:54:83:54:7f:19:99:07:90:8a:a1:07:72:f8:a3:90:6d:2e:d8:3c:0e:fd:21:97:b9:83:45:16:6d:57:75:ba:58:c2:ec:36:e0:2b:34:85:35:03:f1:fc:82");
		System.out.println();
		
		decrypt("Request", "00:0a:20:00:68:a9:32:4d:c8:2a:1d:27:2c:26:b7:f1:db:dc:91:14");
		decrypt("Response", "00:0a:20:00:92:ca:ed:f9:8d:61:58:0a:45:a5:b0:59:ee:3e:fc:03:17:c4:34:4c:18:fa:4a:28:04:ea:ce:f5:4e:6a:3c:21:17:c4:34:4c:18:fa:4a:28:04:ea:ce:f5:4e:6a:3c:21:e9:77:93:e8:9f:e4:80:69:08:c6:d6:cd:44:ae:34:c2:ce:18:98:18:a0:dd:49:e9:7c:39:d3:b0:7d:8f:84:9d:7f:aa:48:27:2a:20:33:f2:8a:72:30:16:e9:8f:65:10:df:34:76:9e:ce:b6:e3:58:e5:da:c0:a8:a7:9f:49:bc:5d:86:a8:90:dc:8b:0f:89:68:b2:c1:ef:fc:20:7d:71:ff:13:d0:d8:ab:ea:f6:5e:c0:84:cc:bf:e6:5b:4b:b0:71:8c:dc:02:75:92:a8:07:de:dc:68:c4:42:d0:bd:8c:a6:1f:f4:d4:c1:8a:c5:7e:ae:06:f7:71:e0:05:82:22:e6:ca:cc:5f:3c:cf:a6:e1:b1:f1:38:57:7e:85:c4:16:7c:48:2d:bb:28:8e:e5:5e:36:9b:02:d0:2d:36:7a:6f");
		System.out.println();
		
		decrypt("Request", "00:0b:20:00:90:89:e1:6b:4e:1f:89:22:7f:19:76:ca:2a:5f:53:df");
		decrypt("Response", "00:0b:20:00:69:f7:f3:67:f7:36:3c:bc:0b:58:b3:66:bb:96:66:9c:b1:ad:88:a7:e2:cf:3b:df:ce:fb:68:64:e7:e1:3a:20:63:6c:05:9c:76:84:b6:0d:a6:50:f8:c7:80:49:a8:5b:63:6c:05:9c:76:84:b6:0d:a6:50:f8:c7:80:49:a8:5b:63:6c:05:9c:76:84:b6:0d:a6:50:f8:c7:80:49:a8:5b:63:6c:05:9c:76:84:b6:0d:a6:50:f8:c7:80:49:a8:5b:63:6c:05:9c:76:84:b6:0d:a6:50:f8:c7:80:49:a8:5b:63:6c:05:9c:76:84:b6:0d:a6:50:f8:c7:80:49:a8:5b:63:6c:05:9c:76:84:b6:0d:a6:50:f8:c7:80:49:a8:5b:63:6c:05:9c:76:84:b6:0d:a6:50:f8:c7:80:49:a8:5b:63:6c:05:9c:76:84:b6:0d:a6:50:f8:c7:80:49:a8:5b:63:6c:05:9c:76:84:b6:0d:a6:50:f8:c7:80:49:a8:5b:0c:0c:29:0b:d8:7c:30:7d:53:f4:50:94:63:22:a0:dc");
		System.out.println();
		
		decrypt("Request", "00:0c:20:00:98:1d:8e:47:55:12:37:1f:4e:6d:1f:9f:8c:f3:8e:be");
		decrypt("Response", "00:0c:20:00:e4:84:63:63:ee:7a:03:15:69:d5:fb:be:41:28:f0:dc:7d:46:d1:e2:cb:cc:fb:f7:46:cf:80:03:48:9a:f9:66:7d:46:d1:e2:cb:cc:fb:f7:46:cf:80:03:48:9a:f9:66:7d:46:d1:e2:cb:cc:fb:f7:46:cf:80:03:48:9a:f9:66:7d:46:d1:e2:cb:cc:fb:f7:46:cf:80:03:48:9a:f9:66:7d:46:d1:e2:cb:cc:fb:f7:46:cf:80:03:48:9a:f9:66:7d:46:d1:e2:cb:cc:fb:f7:46:cf:80:03:48:9a:f9:66:7d:46:d1:e2:cb:cc:fb:f7:46:cf:80:03:48:9a:f9:66:7d:46:d1:e2:cb:cc:fb:f7:46:cf:80:03:48:9a:f9:66:7d:46:d1:e2:cb:cc:fb:f7:46:cf:80:03:48:9a:f9:66:7d:46:d1:e2:cb:cc:fb:f7:46:cf:80:03:48:9a:f9:66:7d:46:d1:e2:cb:cc:fb:f7:46:cf:80:03:48:9a:f9:66:a1:79:0c:03:fb:34:05:87:4d:5d:2c:6e:93:3d:75:ce");
		System.out.println();
		
		decrypt("Request", "00:0d:20:00:40:7a:b0:13:29:63:df:96:fa:d4:9d:a6:49:ac:6f:ac");
		decrypt("Response", "00:0d:20:00:9c:10:e1:ba:73:d6:bc:05:43:bc:83:76:4a:7e:c0:64:d3:b6:56:ed:51:35:75:39:40:81:a0:cd:1b:45:5d:d9:d3:b6:56:ed:51:35:75:39:40:81:a0:cd:1b:45:5d:d9:d3:b6:56:ed:51:35:75:39:40:81:a0:cd:1b:45:5d:d9:d3:b6:56:ed:51:35:75:39:40:81:a0:cd:1b:45:5d:d9:d3:b6:56:ed:51:35:75:39:40:81:a0:cd:1b:45:5d:d9:d3:b6:56:ed:51:35:75:39:40:81:a0:cd:1b:45:5d:d9:d3:b6:56:ed:51:35:75:39:40:81:a0:cd:1b:45:5d:d9:d3:b6:56:ed:51:35:75:39:40:81:a0:cd:1b:45:5d:d9:d3:b6:56:ed:51:35:75:39:40:81:a0:cd:1b:45:5d:d9:d3:b6:56:ed:51:35:75:39:40:81:a0:cd:1b:45:5d:d9:d3:b6:56:ed:51:35:75:39:40:81:a0:cd:1b:45:5d:d9:b5:98:63:1b:a5:8a:21:01:66:c4:63:6f:48:64:8d:0b");
		System.out.println();

		decrypt("Request", "00:0e:20:00:2b:50:33:c3:79:cc:cb:86:88:46:28:8d:c4:4c:1c:20");
		decrypt("Response", "00:0e:20:00:dd:8a:29:24:39:c6:e9:ad:3a:e5:fc:e3:bc:1d:54:e0:11:a3:aa:83:c8:26:ab:d4:b3:61:83:29:cd:8e:fd:f5:11:a3:aa:83:c8:26:ab:d4:b3:61:83:29:cd:8e:fd:f5:11:a3:aa:83:c8:26:ab:d4:b3:61:83:29:cd:8e:fd:f5:11:a3:aa:83:c8:26:ab:d4:b3:61:83:29:cd:8e:fd:f5:11:a3:aa:83:c8:26:ab:d4:b3:61:83:29:cd:8e:fd:f5:11:a3:aa:83:c8:26:ab:d4:b3:61:83:29:cd:8e:fd:f5:11:a3:aa:83:c8:26:ab:d4:b3:61:83:29:cd:8e:fd:f5:11:a3:aa:83:c8:26:ab:d4:b3:61:83:29:cd:8e:fd:f5:11:a3:aa:83:c8:26:ab:d4:b3:61:83:29:cd:8e:fd:f5:11:a3:aa:83:c8:26:ab:d4:b3:61:83:29:cd:8e:fd:f5:11:a3:aa:83:c8:26:ab:d4:b3:61:83:29:cd:8e:fd:f5:41:95:1d:ee:cb:4b:67:d0:1a:7e:b4:c5:d2:ea:7c:f7");
		System.out.println();
		
		decrypt("Request", "00:0f:20:00:47:0d:83:7a:8d:0d:d0:55:0c:94:0e:fb:f1:d4:93:76");
		decrypt("Response", "00:0f:20:00:47:6b:77:51:d3:99:fb:f0:3b:f7:70:24:23:11:d1:5d:aa:f0:e6:67:f6:3a:9a:14:ff:8f:6d:fb:b9:04:b1:90:aa:f0:e6:67:f6:3a:9a:14:ff:8f:6d:fb:b9:04:b1:90:b5:02:18:bd:f3:09:9d:d4:4d:ab:f9:54:bb:65:db:f6");
		System.out.println();
		
		decrypt("Request", "00:10:20:00:27:b6:85:ce:71:09:e3:0e:c5:61:00:21:1a:67:4e:af");
		decrypt("Response", "00:10:20:00:1a:f3:2b:3e:f1:7f:ed:1d:2e:47:46:ef:10:eb:30:d8");
		System.out.println();
		
		decrypt("Request", "00:11:20:00:4d:58:41:82:1b:d9:c5:37:d5:a3:70:dd:a1:a1:d3:1e");
		decrypt("Response", "00:11:20:00:75:68:30:71:9c:bb:1f:7e:d9:59:5b:36:a5:28:60:f0:c5:63:eb:67:fc:3c:f6:d2:91:9c:7e:03:d7:bb:83:10:c5:63:eb:67:fc:3c:f6:d2:91:9c:7e:03:d7:bb:83:10:c5:63:eb:67:fc:3c:f6:d2:91:9c:7e:03:d7:bb:83:10:e5:fe:00:a3:ef:83:02:79:87:17:38:9b:cc:40:ce:cd:6d:63:55:9e:f4:6f:52:b3:cc:35:d1:56:a7:d8:f6:a1:d1:6f:4b:8d:9e:22:da:7e:6a:94:23:43:b7:d3:44:20:cc:bf:43:05:a7:49:6c:99:6c:10:8a:6f:23:4a:c3:08:6d:63:55:9e:f4:6f:52:b3:cc:35:d1:56:a7:d8:f6:a1:6d:63:55:9e:f4:6f:52:b3:cc:35:d1:56:a7:d8:f6:a1:6d:63:55:9e:f4:6f:52:b3:cc:35:d1:56:a7:d8:f6:a1:6d:63:55:9e:f4:6f:52:b3:cc:35:d1:56:a7:d8:f6:a1:05:cf:c2:2f:f4:0f:ca:1c:a8:10:19:d1:45:4d:12:30");
		System.out.println();
		
		decrypt("Request", "00:12:20:00:da:fe:d0:67:f9:5b:43:ae:e1:74:fb:9a:1d:01:56:28");
		decrypt("Response", "00:12:20:00:e1:d2:5a:7e:19:60:4b:80:09:ef:f2:8d:22:c4:d4:3f:0a:f0:0f:04:67:42:23:95:94:22:25:ec:2f:1e:0c:8e:0a:f0:0f:04:67:42:23:95:94:22:25:ec:2f:1e:0c:8e:0a:f0:0f:04:67:42:23:95:94:22:25:ec:2f:1e:0c:8e:0a:f0:0f:04:67:42:23:95:94:22:25:ec:2f:1e:0c:8e:0a:f0:0f:04:67:42:23:95:94:22:25:ec:2f:1e:0c:8e:0a:f0:0f:04:67:42:23:95:94:22:25:ec:2f:1e:0c:8e:0a:f0:0f:04:67:42:23:95:94:22:25:ec:2f:1e:0c:8e:0a:f0:0f:04:67:42:23:95:94:22:25:ec:2f:1e:0c:8e:0a:f0:0f:04:67:42:23:95:94:22:25:ec:2f:1e:0c:8e:0a:f0:0f:04:67:42:23:95:94:22:25:ec:2f:1e:0c:8e:0a:f0:0f:04:67:42:23:95:94:22:25:ec:2f:1e:0c:8e:48:71:90:5a:3a:83:5f:b7:81:84:fa:1c:1e:09:20:11");
		System.out.println();
		
		decrypt("Request", "00:13:20:00:ea:6d:5e:4c:3d:ee:e3:a0:77:14:07:24:7a:47:93:31");
		decrypt("Response", "00:13:20:00:b3:a5:fd:fe:fd:a9:d6:6c:50:ec:65:8c:aa:4a:c2:bd:6a:f5:12:6b:d4:5c:2e:16:e1:2e:be:4a:43:be:ae:bd:6a:f5:12:6b:d4:5c:2e:16:e1:2e:be:4a:43:be:ae:bd:6a:f5:12:6b:d4:5c:2e:16:e1:2e:be:4a:43:be:ae:bd:6a:f5:12:6b:d4:5c:2e:16:e1:2e:be:4a:43:be:ae:bd:6a:f5:12:6b:d4:5c:2e:16:e1:2e:be:4a:43:be:ae:bd:6a:f5:12:6b:d4:5c:2e:16:e1:2e:be:4a:43:be:ae:bd:6a:f5:12:6b:d4:5c:2e:16:e1:2e:be:4a:43:be:ae:bd:6a:f5:12:6b:d4:5c:2e:16:e1:2e:be:4a:43:be:ae:bd:6a:f5:12:6b:d4:5c:2e:16:e1:2e:be:4a:43:be:ae:bd:6a:f5:12:6b:d4:5c:2e:16:e1:2e:be:4a:43:be:ae:bd:6a:f5:12:6b:d4:5c:2e:16:e1:2e:be:4a:43:be:ae:bd:f6:41:57:43:c0:fa:97:1c:b1:ca:80:e3:ff:17:c1:8f");
		System.out.println();
		
		decrypt("Request", "00:14:20:00:bc:30:fb:8f:0d:16:7f:3c:5f:a8:fd:92:b4:11:a7:ae");
		decrypt("Response", "00:14:20:00:3b:b1:3a:db:11:94:9e:5f:a4:72:7e:02:f7:eb:16:a3:4b:10:9e:96:1c:27:19:e1:25:16:43:ce:54:b7:cd:15:4b:10:9e:96:1c:27:19:e1:25:16:43:ce:54:b7:cd:15:4b:10:9e:96:1c:27:19:e1:25:16:43:ce:54:b7:cd:15:4b:10:9e:96:1c:27:19:e1:25:16:43:ce:54:b7:cd:15:4b:10:9e:96:1c:27:19:e1:25:16:43:ce:54:b7:cd:15:4b:10:9e:96:1c:27:19:e1:25:16:43:ce:54:b7:cd:15:4b:10:9e:96:1c:27:19:e1:25:16:43:ce:54:b7:cd:15:4b:10:9e:96:1c:27:19:e1:25:16:43:ce:54:b7:cd:15:4b:10:9e:96:1c:27:19:e1:25:16:43:ce:54:b7:cd:15:4b:10:9e:96:1c:27:19:e1:25:16:43:ce:54:b7:cd:15:4b:10:9e:96:1c:27:19:e1:25:16:43:ce:54:b7:cd:15:68:95:5f:bb:b8:91:85:2b:99:2d:38:9c:95:38:0f:ba");
		System.out.println();
		
		decrypt("Request", "00:15:20:00:b4:82:a4:d9:8f:af:dd:d1:54:eb:00:b8:08:3f:9f:73");
		decrypt("Response", "00:15:20:00:3f:8a:c9:63:4a:0f:5b:7a:24:47:f1:1d:06:2e:18:85:29:3e:d9:06:4b:6f:b5:af:17:e7:d6:7e:9f:2c:f3:e0:29:3e:d9:06:4b:6f:b5:af:17:e7:d6:7e:9f:2c:f3:e0:29:3e:d9:06:4b:6f:b5:af:17:e7:d6:7e:9f:2c:f3:e0:29:3e:d9:06:4b:6f:b5:af:17:e7:d6:7e:9f:2c:f3:e0:29:3e:d9:06:4b:6f:b5:af:17:e7:d6:7e:9f:2c:f3:e0:29:3e:d9:06:4b:6f:b5:af:17:e7:d6:7e:9f:2c:f3:e0:29:3e:d9:06:4b:6f:b5:af:17:e7:d6:7e:9f:2c:f3:e0:29:3e:d9:06:4b:6f:b5:af:17:e7:d6:7e:9f:2c:f3:e0:29:3e:d9:06:4b:6f:b5:af:17:e7:d6:7e:9f:2c:f3:e0:29:3e:d9:06:4b:6f:b5:af:17:e7:d6:7e:9f:2c:f3:e0:29:3e:d9:06:4b:6f:b5:af:17:e7:d6:7e:9f:2c:f3:e0:45:f2:d0:11:2a:81:c1:ab:aa:fa:33:a7:b5:d2:09:e9");
		System.out.println();
		
		decrypt("Request", "00:16:20:00:df:9d:b6:21:b4:20:26:59:52:fa:12:b2:c8:ca:69:78");
		decrypt("Response", "00:16:20:00:29:6e:8c:0d:91:ba:3b:0f:f8:08:ef:72:07:2d:d0:db:04:7a:f9:17:6b:ff:9f:f0:dd:82:fd:53:b5:4d:62:ea");
		System.out.println();
		
		decrypt("Request", "00:17:20:00:96:12:57:28:f5:30:23:04:3b:ce:e5:14:cc:a5:fa:24");
		decrypt("Response", "00:17:20:00:c4:60:36:52:8e:3f:df:49:30:c2:c3:fd:af:33:e5:d4");
		System.out.println();
	}

	private void decrypt(String prefix, String text) throws CommunicationException {
		byte[] bytes = toByteArray(text, ":");
		int byteCount = bytes.length;
		
		short seqHigh = MessageHelper.getBits(bytes[0], 0, 8); // This is converts from byte to short in order to remove negative numbers
		short seqLow = MessageHelper.getBits(bytes[1], 0, 8);  // This is converts from byte to short in order to remove negative numbers
		int messageSeqNo = MessageHelper.createWord(seqHigh, seqLow);
		
		// Get the message type (8-bits) at index 2
		@SuppressWarnings("unused")
		int messageTypeVal = bytes[2];
		
		// Get the reserved section (8-bits) at index 3
		int reservedByte = bytes[3];
		if (reservedByte != 0)
		{
			throw new CommunicationException("Unexpected value for reserved section");
		}

		byte[] encryptedAppData = new byte[byteCount - 4];
		System.arraycopy(bytes, 4, encryptedAppData, 0, byteCount - 4);
		byte[] appData = NetworkHelper.decrypt(encryptedAppData, sessionKey, messageSeqNo);
		
		System.out.print(prefix + " " + messageSeqNo + ": ");
		for (int i = 0; i < appData.length; i++) {
			byte b = appData[i];
			System.out.print(Short.toString(b) + "|");
		}
		System.out.println();

		System.out.print(prefix + " " + messageSeqNo + ": ");
		for (int i = 0; i < appData.length; i++) {
			byte b = appData[i];
			System.out.print(((char) b) + "|");
		}
		System.out.println();
	}

	private byte[] toByteArray(String text, String separator) {
		String[] values = text.split(separator);
		byte[] ret = new byte[values.length];

		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			ret[i] = (byte) MessageHelper.hex2decimal(value);
		}

		return ret;
	}
}
