package zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ZKConnection {
    /**
     * server列表, 以逗号分割
     */
    protected String hosts = "192.168.0.52:2181,192.168.0.52:2182,192.168.0.52:2180";
    /**
     * 连接的超时时间, 毫秒
     */
    private static final int SESSION_TIMEOUT = 5000;
    private CountDownLatch connectedSignal = new CountDownLatch(1);
    protected ZooKeeper zk;

    /**
     * 连接zookeeper server
     */
    public void connect() throws Exception {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, new ConnWatcher());
        // 等待连接完成
        connectedSignal.await();
        System.out.println("测试完成");
    }

    public class ConnWatcher implements Watcher {
        public void process(WatchedEvent event) {
            // 连接建立, 回调process接口时, 其event.getState()为KeeperState.SyncConnected
        	 System.out.println("KeeperState.SyncConnected ："+KeeperState.SyncConnected);
            if (event.getState() == KeeperState.SyncConnected) {
                // 放开闸门, wait在connect方法上的线程将被唤醒
            	System.out.println("唤醒主线程");
                connectedSignal.countDown();
            }
        }
    }
    public static void main(String[] args) throws Exception {
    	ZKConnection zkCon = new ZKConnection();
    	zkCon.connect();
	}
}