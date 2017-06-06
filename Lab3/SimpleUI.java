package db;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Scanner;

public class SimpleUI {
	
	static DB db1;
	static Scanner 	s = new Scanner(System.in); 
	static String	id;	  //id
	static String  	name; //ѧ������
	static String  	cid;
	static int 		type; //type=1Ϊѧ����=0Ϊ����Ա
	
	public static void main(String[] args) {
		
		db1 = new DB();

		System.out.println(db1.connect());
				
		System.out.println("��ӭʹ��У��ͨ��ϵͳ��������"+ getTime());

		signORlog();
		if(type==1)onlineStudent();
		else onlineAdmin();
		
		db1.close();
	}
	private static int signORlog()
	{
		while(true){
			System.out.println("�����˻���¼������L�����û�ע��������S��");
			String a = s.nextLine();
			if(a.equals("L")){
				login();
				return 0; }
			else if(a.equals("S")){
				signup();
				return 0; }
			else{
				System.out.println("����ָ�"); }
		}
	}
	
	private static void login()
	{
		while(true){
			System.out.print("�˺ţ�");
			id = s.next();
			System.out.print("���룺");
			String pw = s.next();
			
			int a = db1.checkUser(id,pw);
			if(a==-1) {
				System.out.println("������Ϣ�����������룡");
				continue;
			}
			else{
				if(id.length()==4) type = 0;
				else type = 1;
				return;
			}
		}
	}
	
	private static void signup()
	{
		while(true){
			String pw;
			while(true)
			{
				System.out.print("�������˺�(ѧ����ʹ��6λѧ��)��");
				id = s.next();
				if(id.length()!=4 && id.length()!=6) {
					System.out.println("������˺ţ�");
					continue;
				}
				else break;
			}
			while(true)
			{
				System.out.print("���������룺");
				pw = s.next();
				if(pw.length()==0) {
					System.out.println("���벻��Ϊ�գ�");
					continue;
				}
				else break;
			}
			
			int a;
			if(id.length()==6){
				a = db1.signUp(id,pw);
			}
			else a = db1.signUpA(id,pw);
			
			if(a==-1){
				System.out.println("����ͬ���û����볢������ע�ᣡ");
				continue;
			}
			else{
				if(id.length()==4){
					type = 0;
					return ; }
				else{
					type = 1;
					//setInf();
					return ; }
			}
		}
	}
	private static void setInf()
	{	
		while(true)
		{
			System.out.print("������ѧԺ����");
			String c = s.next();
			System.out.print("����������    ��");
			String n = s.next();
			System.out.print("����������    ��");
			String age = s.next();
			
			int a = db1.fullInf(id, c, n, age);
			if(a==-1){
				System.out.println("�����ѧԺ����");
				continue; }
			else {
				System.out.println("�ɹ����������Ϣ��");
				return; }
		}
	}
	private static void aboutFriend()
	{
		db1.close();
		System.out.println(db1.connect());
		System.out.println("�ҵĺ���");
		String result = db1.showFriend(id);
		if(result.length()==0) return ;
		String[] friends = result.split("~");
		for(int i=0;i<friends.length;i++)
		{
			String myf = friends[i] ;
			System.out.println(myf);
		}
	}

	private static void notice()
	{
		db1.close();
		System.out.println(db1.connect());
		String notices = db1.showNotice(cid);
		if(notices.length()==0){
			System.out.println("û��ѧԺ���棡");
			return ;
		}
		//System.out.println(notices);
		String[] m = notices.split("~");
		for(int i=1;i<m.length;i++)
		{
			System.out.println((i) + ". " + (m[i].split("-"))[1]);
		}
		while(true)
		{
			System.out.println("���빫���Ų鿴������Ϣ,����0�˳���");
			int a = s.nextInt();
			if(a==0) return;
			else if(a>=m.length){
				System.out.println("����Ĺ����ţ�");
				continue;
			}
			else{
				String[] temp = m[a].split("-");
				String nid = temp[0];
				String n = db1.getNotice(nid);
				String[] tote = n.split("~");
				System.out.println("���⣺ " + tote[0]);
				System.out.println(tote[1]);
			}
		}
	}
	
	private static void findS()
	{
		db1.close();
		System.out.println(db1.connect());
		while(true){
			System.out.println("1:����ѧ�Ų�ѯ;2:����������ѯ;3.�˳�;");
			int a;
			a = s.nextInt();
			
			if(a==3) return;
			else if(a==1){
				System.out.println("������ѧ�ţ�");
				String ida = s.next();
				
				String na = db1.findName(ida);
				if(na.length()==0){
					System.out.print("�����ѧ��");
					continue;
				}
				else{
					System.out.print(na + " ����1��ӶԷ�Ϊ���ѡ����������˳�");
					int mf = s.nextInt();
					if(mf==1){
						db1.addFriend(id, ida);
					}
				}	
			}
			else if(a==2){
				System.out.println("������������");
				String ida = s.next();
				String na = db1.findNameID(ida);
				if(na.length()==0){
					System.out.print("�����ڵ��û���");
					continue;
				} 
				else{
					String[] idss = na.split("~");
					for(int i=0;i<idss.length;i++)
					{
						System.out.println(idss[i]);
					}
				}
			}
			else{
				System.out.print("�������");
				continue;
			}	
		}
	}
	private static void sendBox()
	{
		db1.close();
		System.out.println(db1.connect());
		System.out.println("�ѷ������ݣ�");
		String r = db1.checkSend(id);
		String[] rs = r.split("~");
		for(int i=1;i<rs.length;i++)
		{
			String[] temp = rs[i].split(":");
			System.out.println(i + temp[1]);
		}
		System.out.println("������ɾ����Ϣ������0��շ�����,�����������˳�");
		int a = s.nextInt();
		if(a==0) db1.cleanSend(id);
		else if(a>=rs.length){
			return;
		}
		else{
			String[] m = rs[a].split(":");  
			db1.deleteSend(m[0]);
		}
	}
	private static void recvBox()
	{
		db1.close();
		System.out.println(db1.connect());
		System.out.println("�ѽ������ݣ�");
		String r = db1.checkRecv(id);
		String[] rs = r.split("~");
		for(int i=1;i<rs.length;i++)
		{
			String[] temp = rs[i].split(":");
			System.out.println(i +"���ͷ�: " + temp[0] + " ����:" + temp[2]);
			System.out.println("���ݣ�" + temp[3]);
		}
		System.out.println("������ɾ����Ϣ������0����ռ��䣬���������˳�");
		int a = s.nextInt();
		if(a==0) db1.cleanRecv(id);
		else if(a>=rs.length){
			return;
		}
		else{
			String[] m = rs[a].split(":");  
			db1.deleteRecv(m[1]);
		}
	}
	private static void onlineStudent()
	{
		db1.close();
		System.out.println(db1.connect());
		name = db1.findName(id);
		System.out.println("��ӭ��½��" + name + "ͬѧ��");
		while(true){
			System.out.println("����ָ���ţ����в�����");
			System.out.println("1.�޸ĸ�����Ϣ");
			System.out.println("2.�ҵĺ���");
			System.out.println("3.�鿴ѧԺ����");
			System.out.println("4.��ѯͬѧ");
			System.out.println("5.������");
			System.out.println("6.�ռ���");
			System.out.println("7.����");
			System.out.println("0.�˳���¼");
			
			int choice = s.nextInt();
			switch(choice){
			case 1:
				setInf();
				break;
			case 2:
				aboutFriend();				
				break;
			case 3:
				cid = db1.myCID(id);
				notice();
				break;
			case 4:
				findS();
				break;
			case 5:
				sendBox();
				break;
			case 6:
				recvBox();
				break;
			case 7:
				System.out.print("�������ռ���ѧ�ţ�");
				String rid = s.next();
				if(db1.findID(rid)==-1){
					System.out.println("�����ѧ�ţ�");
					break;
				}
				else{
					String topic;
					while(true){
						System.out.print("���⣺");
						topic = s.next();
						if(topic.length()!=0) break;
						System.out.println("���ⲻӦ��Ϊ�գ�");
					}
					System.out.print("���ݣ�");
					String text = s.nextLine();
					text = s.nextLine();
					db1.sendMessage(id, rid, topic, text);
				}
				break;
			case 0:
				return;
			default:
				System.out.println("����ָ�");
			}
		}
	}
	
	private static void onlineAdmin()
	{
		db1.close();
		System.out.println(db1.connect());
		System.out.println("��ӭ��½������Ա" + id);
		
		while(true)
		{
			System.out.println("����ָ���ţ����в�����");
			System.out.println("1.�鿴ѧԺ��Ϣ");
			System.out.println("2.�鿴ѧԺ����");
			System.out.println("3.����ѧԺ����");
			System.out.println("4.ɾ��ѧ��");
			System.out.println("0.�˳���¼");
			
			int choice = s.nextInt();
			switch(choice)
			{
			case 1:
				//�˴�һ�������ѯ
				String cg = db1.collegeGroup();
				String[] cs = cg.split("~");
				if(cg.length()==0){
					System.out.println("NO INFORMATION.");
				}
				for(int i=0;i<cs.length;i++)
				{
					String[] temp = cs[i].split("-");
					System.out.println("ѧԺ���:" + temp[0] + " ѧԺ����:" + temp[1] + " ѧԺ����:" + temp[2]);
				}
				break;
			case 2:
				System.out.println("����ѧԺ��ţ�");
				cid= s.next();
				if(db1.count("college where cid=" + cid)==0)
				{
					System.out.println("�����ѧԺ��ţ�");
				}
				notice();
				break;
			case 3:
				System.out.println("����ѧԺ��ţ�");
				cid= s.next();
				if(db1.count("college where cid=" + cid)==0)
				{
					System.out.println("�����ѧԺ��ţ�");
				}
				String topic;
				while(true){
					System.out.print("���⣺");
					topic = s.next();
					if(topic.length()!=0) break;
					System.out.println("���ⲻӦ��Ϊ�գ�");
				}
				System.out.println("���ݣ�");
				String text = s.nextLine();
				text = s.nextLine();
				db1.oneNotice(id, topic, text,cid);
				break;
			case 4:
				System.out.print("������Ҫɾ����ѧ��ѧ�ţ�");
				String aa = s.next();
				if((db1.findName(aa)).length()==0){
					System.out.println("�����ѧ��ѧ�ţ�"); }
				else{
					db1.deleteStudent(aa);
				}
				break;
			case 0:
				return;
			default:
				System.out.println("����ָ�");
			}
		}	
	}

	/* ��ȡʱ�� Ϊ��λ yyyyMMdd */
	private static String getTime() 
	{
		String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		return date;
	}
}
