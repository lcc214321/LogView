package cn.cloud.log;

import java.util.ArrayList;
import java.util.List;

public class test {
	public static class en1 {
		private List<String> list = new ArrayList<String>();
		private String name;
        public en1(){
        	list.add("123");
        }
		public List<String> getList() {
			return list;
		}

		public void setList(List<String> list) {
			this.list = list;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static void main(String[] args) {
		en1 d=new en1();
		List<String> listdist=d.getList();
		listdist.add("234");
		for(String l:d.getList()){
			System.out.println(l);
		}
	}
}
