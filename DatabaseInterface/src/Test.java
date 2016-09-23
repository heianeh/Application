import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		Database db = new Database();
//		db.insertImage(new String[]{"asdfghjkl","c:/image/image1.jpg"});
//		db.insertImage(new String[]{"qwertyui","c:/image/image2.jpg"});
//		db.insertImage(new String[]{"zxcvbnm","c:/image/image3.jpg"});
//		db.insertImage(new String[]{"qwaszxc","c:/image/image4.jpg"});
//		db.insertImage(new String[]{"qweasdf","c:/image/image5.jpg"});
//		db.insertImage(new String[]{"dfghhk","c:/image/image6.jpg"});
//		db.insertImage(new String[]{"asdfg56","c:/image/image7.jpg"});
//		db.insertImage(new String[]{"56asdfg","c:/image/image8.jpg"});
		ArrayList<String[]> get = db.queryImage("zxc");
		System.out.println("zxc");
		for(int i=0; i<get.size(); i++) {
			System.out.println("Í¼Æ¬Ãû³Æ:"+get.get(i)[0]);
			System.out.println("Í¼Æ¬Î»ÖÃ:"+get.get(i)[1]);
		}
	}
}
