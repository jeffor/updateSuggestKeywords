package update_suggestion;

import java.util.HashMap;

/**定义 (关键字,uv) 存储类型*/
public class DataSet {

	public HashMap<String, Element> dict = new HashMap<String, Element>();

	public final class Element {
		public String word;
		public float uv;

		public Element(String word, float uv) {
			this.word = word;
			this.uv = uv;
		}
		
		@Override
		public String toString(){
			return word + " : " + uv;
		}
	}

}
