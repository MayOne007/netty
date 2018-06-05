package zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


public class SimpleZkClient {

	private static final String connectString = "127.0.0.1:2181";
	private static final int sessionTimeout = 30000;

	public static ZooKeeper zkClient = null;

	public static void main(String[] args) {
		/*SimpleZkClient szk = new SimpleZkClient();
		try {
			szk.init();
			szk.getChildren();
			//szk.testExist();
			//szk.testCreate();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		ZooKeeper zk;
		ZooKeeper zk2;
		ZooKeeper zk3;
		try {
			zk = new ZooKeeper("localhost:2181", 3000, null);
			zk2 = new ZooKeeper("localhost:2182", 3000, null);
			zk3 = new ZooKeeper("localhost:2183", 3000, null);
	        System.out.println("=========创建节点===========");  
	      
			if(zk.exists("/test", false) == null)  
			{  
			    zk.create("/test", "znode1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);  
			}
			
	        System.out.println("=============查看节点是否安装成功===============");  
	        System.out.println(new String(zk2.getData("/test", false, null)));  
	          
	        System.out.println("=========修改节点的数据==========");  
	        zk2.setData("/test", "zNode2".getBytes(), -1);  
	        System.out.println("========查看修改的节点是否成功=========");  
	        System.out.println(new String(zk3.getData("/test", false, null)));  
	          
	        System.out.println("=======删除节点==========");  
	        zk3.delete("/test", -1);  
	        System.out.println("==========查看节点是否被删除============");  
	        System.out.println("节点状态：" + zk.exists("/test", false));  
	        zk.close();  
		} catch (IOException e) {
			e.printStackTrace();
		}  catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  
	}
	
	//@Before
	public void init() throws Exception {
		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				// 收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
				System.out.println(event.getType() + "---" + event.getPath());
				try {
					zkClient.getChildren("/", true);
				} catch (Exception e) {
				}
			}
		});

	}

	/**
	 * 数据的增删改查
	 * 
	 * @throws InterruptedException
	 * @throws KeeperException
	 */

	// 创建数据节点到zk中
	public void testCreate() throws KeeperException, InterruptedException {
		// 参数1：要创建的节点的路径 参数2：节点大数据 参数3：节点的权限 参数4：节点的类型
		zkClient.create("/eclipse", "hellozk".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		//上传的数据可以是任何类型，但都要转成byte[]
	}

	//判断znode是否存在
	//@Test	
	public void testExist() throws Exception{
		Stat stat = zkClient.exists("/eclipse", false);
		System.out.println(stat==null?"not exist":"exist");
		
		
	}
	
	// 获取子节点
	//@Test
	public void getChildren() throws Exception {
		List<String> children = zkClient.getChildren("/", true);
		for (String child : children) {
			System.out.println(child);
		}
		Thread.sleep(Long.MAX_VALUE);
	}

	//获取znode的数据
	//@Test
	public void getData() throws Exception {
		
		byte[] data = zkClient.getData("/app1", false, null);
		System.out.println(new String(data));
		
	}
	
	//删除znode
	//@Test
	public void deleteZnode() throws Exception {
		
		//参数2：指定要删除的版本，-1表示删除所有版本
		zkClient.delete("/eclipse", -1);
		
		
	}
	//删除znode
	//@Test
	public void setData() throws Exception {
		
		zkClient.setData("/app1", "imissyou angelababy".getBytes(), -1);
		
		byte[] data = zkClient.getData("/app1", false, null);
		System.out.println(new String(data));
		
	}
	
	
}