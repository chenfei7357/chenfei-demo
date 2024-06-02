package chenfeidemo.elasticjobdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		String[] cardColour1 = {"黑桃", "红桃", "梅花", "方片"};
		String[] cardsnumber1 = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
		List<String>  cardsToSelect =new ArrayList<>();

		for (int i = 0; i < cardColour1.length; i++) {
			for (int j = 0; j < cardsnumber1.length; j++) {
				cardsToSelect.add(cardColour1[i]+"-"+cardsnumber1[j]);
			}
		}
		System.out.println("=============================================");
		System.out.println("打乱前："+cardsToSelect.toString());
		System.out.println("=============================================");
		//随机打乱
		Collections.shuffle(cardsToSelect);
		System.out.println("=============================================");
		System.out.println("打乱后："+cardsToSelect.toString());
		System.out.println("=============================================");

		//玩家0001
		Player player01= new Player("0001","aa");
		List<String> list01 =new ArrayList<>();
		//玩家0002
		Player player02= new Player("0002","bb");
		List<String> list02 =new ArrayList<>();
		//发牌 每个人2张
		for (int i = 0; i < 4; i++) {
			if(i%2==0){
				list01.add(cardsToSelect.get(i));
			}else{
				list02.add(cardsToSelect.get(i));
			}
		}
		player01.setCards(list01);
		player02.setCards(list02);
		System.out.println("=============================================");
		System.out.println("玩家1："+player01.toString());
		System.out.println("=============================================");
		System.out.println("玩家2："+player02.toString());
		System.out.println("=============================================");

		//比较手里最大的牌子
		List<String> cards001 = player01.getCards();
		List<String> cards002 = player02.getCards();

		//获取每个人手里最大的牌
		String maxCards001="";
		int maxCards001Index=0;
		String maxCards002="";
		int maxCards002Index=0;

		//获取玩家0001最大的牌
		for (String s : cards001) {
			int index=Arrays.asList(cardsnumber1).indexOf(s.split("-")[1]);
			if(index>maxCards001Index){
				maxCards001=s;
			};
			maxCards001Index=index;
		}

		//获取玩家0002最大的牌
		for (String s : cards002) {
			int index=Arrays.asList(cardsnumber1).indexOf(s.split("-")[1]);
			if(index>maxCards002Index){
				maxCards002=s;
			};
			maxCards002Index=index;
		}
		System.out.println("=============================================");
		System.out.println("玩家1手里最大的牌："+maxCards001);
		System.out.println("玩家2手里最大的牌："+maxCards002);
		System.out.println("=============================================");


		//比较最大的牌的之间的大小 点数、花色的大小按数组下标大小

		//玩家0001花色
		String maxCardColour001 = maxCards001.split("-")[0];
		//玩家0001点数
		String maxCardNumber001 = maxCards001.split("-")[1];

		//玩家0002花色
		String maxCardColour002 = maxCards002.split("-")[0];
		//玩家0002点数
		String maxCardNumber002 = maxCards002.split("-")[1];

		if(Arrays.asList(cardsnumber1).indexOf(maxCardNumber001)>Arrays.asList(cardsnumber1).indexOf(maxCardNumber002)){
			System.out.println("玩家1手里最大的牌："+maxCards001+" 比玩家2手里最大的牌："+maxCards002+" 大");
		}else if(Arrays.asList(cardsnumber1).indexOf(maxCardNumber001)<Arrays.asList(cardsnumber1).indexOf(maxCardNumber002)){
			System.out.println("玩家1手里最大的牌："+maxCards001+" 比玩家2手里最大的牌："+maxCards002+" 小");
		}else{
			//点数一样大 比较花色
			if(Arrays.asList(cardColour1).indexOf(maxCardColour001)>Arrays.asList(cardColour1).indexOf(maxCardColour002)){
				System.out.println("玩家1手里最大的牌："+maxCards001+" 比玩家2手里最大的牌："+maxCards002+"大");
			}else if(Arrays.asList(cardColour1).indexOf(maxCardColour001)<Arrays.asList(cardColour1).indexOf(maxCardColour002)){
				System.out.println("玩家1手里最大的牌："+maxCards001+" 比玩家2手里最大的牌："+maxCards002+" 小");
			}else{
				System.out.println("玩家1手里最大的牌："+maxCards001+" 比玩家2手里最大的牌："+maxCards002+" 一样大");
			}

		}
	}
}
