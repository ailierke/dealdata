package zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class ZkDemo {
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		// 创建一个与服务器的连接
		ZooKeeper zk = new ZooKeeper("192.168.0.52:2181,192.168.0.52:2182,192.168.0.52:2180", 60000, new Watcher() {
			// 监控所有被触发的事件
			// 当对目录节点监控状态打开时，一旦目录节点的状态发生变化，Watcher 对象的 process 方法就会被调用。
			public void process(WatchedEvent event) {
				System.out.println("start EVENT:" + event.getType());
			}
		});

		// 查看根节点
		// 获取指定 path 下的所有子目录节点，同样 getChildren方法也有一个重载方法可以设置特定的 watcher 监控子节点的状态
		System.out.println("ls / => " + zk.getChildren("/", true));

		// 判断某个 path 是否存在，并设置是否监控这个目录节点，这里的 watcher 是在创建 ZooKeeper 实例时指定的 watcher；
		// exists方法还有一个重载方法，可以指定特定的 watcher
		if (zk.exists("/node", true) == null) {
			// 创建一个给定的目录节点 path, 并给它设置数据；
			// CreateMode 标识有四种形式的目录节点，分别是：
			//     PERSISTENT：持久化目录节点，这个目录节点存储的数据不会丢失；
			//     PERSISTENT_SEQUENTIAL：顺序自动编号的目录节点，这种目录节点会根据当前已近存在的节点数自动加 1，然后返回给客户端已经成功创建的目录节点名；
			//     EPHEMERAL：临时目录节点，一旦创建这个节点的客户端与服务器端口也就是 session 超时，这种节点会被自动删除；
			//     EPHEMERAL_SEQUENTIAL：临时自动编号节点
			zk.create("/node", "conan".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("create /node conan");
			// 查看/node节点数据
			System.out.println("get /node => " + new String(zk.getData("/node", false, null)));
			// 查看根节点
			System.out.println("ls / => " + zk.getChildren("/", true));
		}

		// 创建一个子目录节点
		if (zk.exists("/node/sub1", true) == null) {
			zk.create("/node/sub1", "sub1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("create /node/sub1 sub1");
			// 查看node节点
			System.out.println("ls /node => " + zk.getChildren("/node", true));
		}

		// 修改节点数据
		if (zk.exists("/node", true) != null) {
			// 给 path 设置数据，可以指定这个数据的版本号，如果 version 为 -1 怎可以匹配任何版本
			zk.setData("/node", "changed".getBytes(), -1);
			// 查看/node节点数据
			// 获取这个 path 对应的目录节点存储的数据，数据的版本等信息可以通过 stat 来指定，同时还可以设置是否监控这个目录节点数据的状态
			System.out.println("get /node => " + new String(zk.getData("/node", false, null)));
		}

		// 删除节点
		if (zk.exists("/node/sub1", true) != null) {
			// 删除 path 对应的目录节点，version 为 -1 可以匹配任何版本，也就删除了这个目录节点所有数据
			zk.delete("/node/sub1", -1);
			zk.delete("/node", -1);
			// 查看根节点
			System.out.println("ls / => " + zk.getChildren("/", true));
		}

		// 关闭连接
		zk.close();
	}
}
