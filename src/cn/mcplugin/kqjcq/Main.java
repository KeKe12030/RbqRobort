package cn.mcplugin.kqjcq;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.sobte.cqp.jcq.entity.CQDebug;
import com.sobte.cqp.jcq.entity.CQImage;
import com.sobte.cqp.jcq.entity.Group;
import com.sobte.cqp.jcq.entity.ICQVer;
import com.sobte.cqp.jcq.entity.IMsg;
import com.sobte.cqp.jcq.entity.IRequest;
import com.sobte.cqp.jcq.event.JcqAppAbstract;

public class Main  extends JcqAppAbstract implements ICQVer, IMsg, IRequest {
	static int i = 0;
	static long time = 0L;
	static long time2 = 0L;
	static long time3 = 0L;
	static long time4 = 0L;
	static WHComeOn wh = new WHComeOn();
	public static void main(String[] args) {
		CQ = new CQDebug();
		Main m = new Main();
		m.startup();
		m.enable();
		m.exit();
	}
	@Override
	public int startup() {
		Thread t = new Thread(new SickWebSite());
		t.start();
		
		return 0;
	}

	@Override
	public int exit() {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int enable() {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int disable() {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int privateMsg(int subType, int msgId, long fromQQ, String msg, int font) {
		// TODO 自动生成的方法存根
		int i = 0;
		if(i%3 == 0) {//10个消息
			CQ.sendPrivateMsg(fromQQ, "？有色图吗，没有走开。");
		}
		if(msg.contains("机器人")) {
			CQ.sendPrivateMsg(fromQQ, "谁是机器人啊，我掏出来可不比你的小啊！");
		}
		i++;
		return MSG_IGNORE;
	}

	@Override
	public int groupMsg(int subType, int msgId, long fromGroup, long fromQQ, String fromAnonymous, String msg,
			int font) {
		if(msg.equals("rbq")) {
			CQ.sendGroupMsg(fromGroup, "叫我干嘛");
		}else if(msg.contains("地图") &&  (msg.contains("肺炎")
				|| msg.contains("武汉") || msg.contains("病毒") 
				|| msg.contains("感染")
				|| msg.contains("疫情") || msg.contains("瘟疫"))) {
			if(System.currentTimeMillis() - time > 10000 || time==0) {
				try {
					time = System.currentTimeMillis();
					CQ.sendGroupMsg(fromGroup, "正在为您下载地图..."+"\n"+"这可能需要几秒钟的时间"+"\n"+"请耐心等待\n"+"输入 武汉加油 可以为武汉点赞哦！");
					CQImage c = new CQImage(SickWebSite.getImageUrl());
					File f = new File("C:\\Users\\Administrator\\Desktop\\image\\map.jpg");
					c.download(f);
					CQ.sendGroupMsg(fromGroup, SickWebSite.getSickInfo()+CC.image(f));
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					CQ.sendGroupMsg(fromGroup, e.toString());
				}
			}else {
				CQ.sendGroupMsg(fromGroup, "十秒钟内只能查询一次疫情地图哦！\n"+"输入 武汉加油 可以为武汉点赞哦！");
			}

		}else if(msg.contains("菜单") || msg.contains("点歌")) {
			CQ.sendGroupMsg(fromGroup, "我说了我不是机器人！");
		}else if(msg.contains("女装")) {
			i++;
			if(i%10 == 0) {
				CQ.sendGroupMsg(fromGroup, "kk女装");
			}
		}else if(msg.equals("呵呵")) {
			CQ.sendGroupMsg(fromGroup, "呵呵你马");
		}else if(msg.contains("洗手") || msg.contains("搓手") || msg.contains("口罩") || msg.contains("助手")) {
			if(System.currentTimeMillis() - time4 > 10000 || time4==0) {
				time4 = System.currentTimeMillis();
				try {
					CQ.sendGroupMsg(fromGroup, CC.image(new File("C:\\Users\\Administrator\\Desktop\\image\\hand.jpg")));
				} catch (IOException e) {
					CQ.sendGroupMsg(fromGroup, e.toString());
				}
			}else {
				CQ.sendGroupMsg(fromGroup, "十秒钟内只能查询一次助手提醒哦！ \n"+"输入 武汉加油 可以为武汉点赞哦！");
			}
		}else if(msg.contains("地图") && (msg.contains("迁徙") || msg.contains("迁移") || msg.contains("转移"))) {
			File f = new File("C:\\Users\\Administrator\\Desktop\\image\\qianxi.jpg");
			try {
				if(System.currentTimeMillis() - time2 > 10000 || time2==0) {
					time2= System.currentTimeMillis();
					CQ.sendGroupMsg(fromGroup, "全国迁移热点地图"+"\n"+CC.image(f));
				}else {
					CQ.sendGroupMsg(fromGroup, "十秒钟内只能查询一次迁移地图哦！");
				}
			} catch (IOException e) {
				CQ.sendGroupMsg(fromGroup, e.toString());
			}
		}else if(msg.contains("武汉") && (msg.contains("加油") || msg.contains("点赞"))) {
			try {
				if(wh.zan(fromQQ) == 0) {
					CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+"\n"+ "人在全国心在汉！\n我为武汉加油"+"[CQ:emoji,id=128170]\n"+"点赞成功，今天收到"+wh.getNum()+"个赞\n"+"历史一共收到"+wh.getAllNum()+"个赞");
				}else {
					CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+"\n"+"您今天已经为武汉点过赞了"+"[CQ:emoji,id=10084]"+"历史一共有"+wh.getAllNum()+"人为武汉点过赞哦！\n"+"明天再来吧！");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				
		}else if(msg.contains("疫情") && !(msg.contains("地图") || msg.contains("全国"))) {
			String[] arr = msg.split("疫情");
			try {
				if(SickWebSite.staticHtml == null)
					CQ.sendGroupMsg(fromGroup,"疫情信息初始化\n"+"这可能需要几秒钟的时间 \n"+"请您耐心等待\n"+"输入 武汉加油 可以为武汉点赞哦！");
				CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+SickWebSite.getArea(arr[0]));
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return MSG_IGNORE;
	}

	@Override
	public int discussMsg(int subtype, int msgId, long fromDiscuss, long fromQQ, String msg, int font) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int groupUpload(int subType, int sendTime, long fromGroup, long fromQQ, String file) {
		CQ.sendGroupMsg(fromGroup, CC.at(fromQQ)+"我裙子都脱了你却让我看这个？(╯▔皿▔)╯");
		return MSG_IGNORE;
	}

	@Override
	public int groupAdmin(int subtype, int sendTime, long fromGroup, long beingOperateQQ) {
		if(subtype == 1) 
			CQ.sendGroupMsg(fromGroup, "群主扒下了"+CC.at(beingOperateQQ)+"的女装");
		else if(subtype == 2)
			CQ.sendGroupMsg(fromGroup, "群主给"+CC.at(beingOperateQQ)+"穿上了一套女装");
		return MSG_IGNORE;
	}

	@Override
	public int groupMemberDecrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
		// TODO 自动生成的方法存根
		if(subtype == 1) {
			CQ.sendGroupMsg(fromGroup, CC.at(beingOperateQQ)+"退下了裙子。");
		}
		CQ.sendGroupMsg(fromGroup, CC.at(beingOperateQQ)+"被"+CC.at(fromQQ)+" 强制退下了裙子");
		return MSG_IGNORE;
	}

	@Override
	public int groupMemberIncrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
		// TODO 自动生成的方法存根

		CQ.sendGroupMsg(fromGroup, "为大家寻觅到一只新的RBQ "+CC.at(beingOperateQQ)+" 快来聊(tiao)聊(jiao)天(ta)吧)");
		return MSG_IGNORE;
	}

	@Override
	public int friendAdd(int subtype, int sendTime, long fromQQ) {
		CQ.sendPrivateMsg(fromQQ, "嘛嘛说不能随便和RBQ说话的哦！");
		return MSG_IGNORE;
	}

	@Override
	public int requestAddFriend(int subtype, int sendTime, long fromQQ, String msg, String responseFlag) {
		CQ.setFriendAddRequest(responseFlag, REQUEST_REFUSE, null);//拒绝添加朋友
		return MSG_IGNORE;
	}

	@Override
	public int requestAddGroup(int subtype, int sendTime, long fromGroup, long fromQQ, String msg,
			String responseFlag) {
		// TODO 自动生成的方法存根
		if(!(fromQQ == 3352772828L)) {
			CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_INVITE, REQUEST_REFUSE, null);//拒绝加群
		}
		CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_INVITE, REQUEST_ADOPT, null);//拒绝加群
		return MSG_IGNORE;
	}

	@Override
	public String appInfo() {
		String AppID = "cn.mcplugin.kqjcq";
		return CQAPIVER + "," + AppID;
	}
	public static void sayJiangSu(String jiangsu) {
		List<Group> ls = CQ.getGroupList();
		for(Group g : ls) {
			CQ.sendGroupMsg(g.getId(), jiangsu);
			//CQ.sendGroupMsg(929806140L, jiangsu);
		}
	}
}
